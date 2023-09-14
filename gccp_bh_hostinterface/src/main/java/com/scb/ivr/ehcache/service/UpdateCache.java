package com.scb.ivr.ehcache.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.stream.Collectors;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.ivr.config.bean.dynamicmenu.Entry;
import com.scb.ivr.config.bean.dynamicmenu.Menu;
import com.scb.ivr.controller.DBController;
import com.scb.ivr.db.entity.GetConfigDetails_Res;
import com.scb.ivr.global.constants.GlobalConstants;

/**
 * @author TA
 *
 * 
 */

@Component
public class UpdateCache {

	@Value("${propertyfilepath}")
	private String propertyfilepath;
	
	@Autowired
	DBController dbControllerData;

	@CachePut(cacheNames = "configfile", key = "#keyname")
	public Object updateCacheValues(String keyname) {
		Logger tpSystemLogger = LogManager.getLogger(GlobalConstants.HostLog);
		tpSystemLogger.debug("Entering updateCacheValues: path: " + propertyfilepath + ", key: " + keyname);
		InputStream input = null;

		try {
			if (keyname.equalsIgnoreCase("Config.properties")) {
				File file = new File(propertyfilepath + keyname);
				Properties prop = new Properties();
				input = new FileInputStream(file);
				prop.load(input);

				return prop;
			} else if (keyname.equalsIgnoreCase("Operations.properties")) {
				File file = new File(propertyfilepath + keyname);
				Properties prop = new Properties();
				input = new FileInputStream(file);
				prop.load(input);

				return prop;

			} else if (keyname.equalsIgnoreCase("Parametric.properties")) {
				File file = new File(propertyfilepath + keyname);
				Properties prop = new Properties();
				input = new FileInputStream(file);
				prop.load(input);

				return prop;
			} else if (keyname.equalsIgnoreCase("menu.json")) {
				return loadDynamicMenu(keyname);
			} else if(keyname.equalsIgnoreCase("dbconfig")) {
				Map<String, Object> inParams = new HashMap<String, Object>();
				inParams.put("tableName", "ALL");
				GetConfigDetails_Res getConfigDetails_Res= dbControllerData.getCommonConfigDetails(inParams);
				return getConfigDetails_Res;
			}
			else {
				tpSystemLogger.debug("Trying to fetch other property file");
				File file = new File(propertyfilepath + keyname);
				if (file.exists()) {
					if (keyname.contains(".json")) {
						return new JSONObject(Files.lines(Paths.get(propertyfilepath + keyname))
								.collect(Collectors.joining(System.lineSeparator())));
					} else if (keyname.contains(".xml")) {
						return file;
					} else if (keyname.contains(".properties")) {
						Properties prop = new Properties();
						input = new FileInputStream(file);
						prop.load(input);
						return prop;
					} else {
						tpSystemLogger.debug("Trying to fetch other property file. The file extention is not found");
						return null;
					}
				} else {
					tpSystemLogger
							.debug("Given Config/Properties file does not exit in Path: " + propertyfilepath + keyname);
					return null;
				}

			}

		} catch (Exception e) {
			tpSystemLogger.error("Exception in updateCacheValues: ", e);
		} finally {
			try {
				input.close();
			} catch (Exception e) {
			}
		}
		return null;
	}

	/**
	 * Load menu json file and store the values in map
	 * 
	 * @return
	 */
	public Map<String, Map<String, String>> loadDynamicMenu(String keyName) {

		Logger MQLog = LogManager.getLogger(GlobalConstants.HostLog);
		Map<String, Map<String, String>> dynamicMenuMap = new HashMap<>();
		try {

			File file = new File(propertyfilepath + keyName);
			ObjectMapper objectMapper = new ObjectMapper();
			Menu menu = objectMapper.readValue(file, Menu.class);

			List<Entry> list = menu.getDynamicMenu().getCountry().getEntry();

			for (Entry entry : list) {
				String key = entry.getKey();
				com.scb.ivr.config.bean.dynamicmenu.Value value = entry.getValue();

				Map<String, String> valueMap = new HashMap<>();

				valueMap.put("PROMPTS", value.getPrompts().trim());
				valueMap.put("BARGEIN", value.getBargein().trim());
				valueMap.put("GRAMMARS", value.getGrammars().trim());
				valueMap.put("MENU_DESC", value.getMenusDescription().trim());
				valueMap.put("NEXT_NODE", value.getNextNode().trim());
				valueMap.put("NI_PROMPT", value.getNoInput().trim());
				valueMap.put("NM_PROMPT", value.getNoMatch().trim());
				valueMap.put("RETRY", value.getRetry().trim());
				valueMap.put("STATE_ID", value.getStateID().trim());
				valueMap.put("MENU_TIMEOUT", value.getTimeOut().trim());
				valueMap.put("MAXTRIES", value.getMaxtries().trim());
				valueMap.put("MAXTRIES_NEXTNODE", value.getMaxTriesNextNode().trim());
				valueMap.put("INTENT", value.getIntent().trim());

				dynamicMenuMap.put(key, valueMap);

			}
		} catch (Exception e) {
			MQLog.error("Exception in loadDynamicMenu: ", e);
		}
		return dynamicMenuMap;
	}

}
