package com.scb.ivr.controller;

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

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.ivr.config.bean.dynamicmenu.Entry;
import com.scb.ivr.config.bean.dynamicmenu.Menu;
import com.scb.ivr.db.entity.GetConfigDetails_Res;
import com.scb.ivr.ehcache.service.UpdateCache;
import com.scb.ivr.global.constants.GlobalConstants;

/**
 * @author TA
 * @category Load config related services
 */

@Component
public class ConfigController {

	@Value("${propertyfilepath}")
	private String propertyfilepath;

	@Value("${propertyfilenames}")
	private String propertyfilenames;

	private Map<String, Long> file_modified_time  = new HashMap<>();

	@Autowired
	public UpdateCache updateCache;
	
	@Autowired
	public DBController dbControllerData;
	
	
//	@Autowired
//	private GetConfigDetailsRepository getConfigDetailsRepository;
	
	
	/**
	 * This method caches the configuration file values in ehcache based on key
	 * value pair
	 * 
	 * @param keyname
	 * @return Object based on keyname
	 */
	@Cacheable(value = "configfile", key = "#keyname", unless = "#result == null")
	public Object getConfigFileValues(String keyname) {
//		Logger tpSystemLogger = LogManager.getContext().getLogger(GlobalConstants.HostLog);
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(GlobalConstants.HostLog);
		tpSystemLogger.debug("Entering getConfigFileValues Keyname =: "+ propertyfilepath + keyname);
		InputStream input = null;
		try {
			tpSystemLogger.debug("********* Loading ehcache Key********** " + keyname);

			if (keyname.equalsIgnoreCase("Config.properties")) {
				//tpSystemLogger.debug("Path: " + propertyfilepath + keyname );
				File file = new File(propertyfilepath + keyname);
				Properties prop = new Properties();
				input = new FileInputStream(file);
				prop.load(input);
				
				//tpSystemLogger.debug("Leaving getConfigFileValues from 'Config' values"); 
				return prop;
			}else if (keyname.equalsIgnoreCase("Operations.properties")) {
				//tpSystemLogger.debug("Path: " + propertyfilepath + keyname);
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
			}else if(keyname.equalsIgnoreCase("dbconfig")) {
				Map<String, Object> inParams = new HashMap<String, Object>();
				inParams.put("tableName", "ALL");
				GetConfigDetails_Res getConfigDetails_Res= dbControllerData.getCommonConfigDetails(inParams);
				return getConfigDetails_Res;
			}else {
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
			tpSystemLogger.error("Exception in getConfigFileValues: ", e);
		}
		tpSystemLogger.debug("Leaving getConfigFileValues returning NULL");
		return null;
	}
	
	/**
	 * Scheduler method to check file modification time
	 */

	@Scheduled(fixedDelayString = "${fixed.delay}")
	public void schedulerToCheckFileModified() {
		
//		Logger tpSystemLogger = LogManager.getContext().getLogger(GlobalConstants.HostLog);
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(GlobalConstants.HostLog);

		tpSystemLogger.debug("Entering schedulerToCheckFileModified");
		 
		try {			
			//Checks and updates the recent modified timestamp for the property files
			for(String fileName : propertyfilenames.split(",")) {
				fileName = fileName.trim();
				if(fileName.isEmpty()) {
					continue;
				}
				
				File file = new File(propertyfilepath + fileName);

				long current_modified = file.lastModified();

				if (file_modified_time.get(fileName) == null) {
					file_modified_time.put(fileName, current_modified);
				}

				if (file_modified_time.get(fileName) == current_modified) {
					tpSystemLogger.debug(fileName + " is not modified");
				} else {
					tpSystemLogger.debug("*** file **** : " + propertyfilepath + fileName + " is modified. current Modified Date : " + current_modified);						
					updateCache.updateCacheValues(fileName);
					file_modified_time.replace(fileName, current_modified);
				}
			}

		} catch (Exception e) {
			tpSystemLogger.error("Exception in schedulerToCheckFileModified: ", e);
		}
		tpSystemLogger.debug("Completed schedulerToCheckFileModified");
	}

	/**
	 * Load menu json file and store the values in map
	 * 
	 * @return
	 */
	public Map<String, Map<String, String>> loadDynamicMenu(String keyName) {
		
//		Logger MQLog = LogManager.getContext().getLogger(GlobalConstants.HostLog);
		org.apache.logging.log4j.Logger MQLog = org.apache.logging.log4j.LogManager.getContext().getLogger(GlobalConstants.HostLog);

		Map<String, Map<String, String>> dynamicMenuMap = new HashMap<>();
		try {

			File file 					= new File(propertyfilepath + keyName);
			ObjectMapper objectMapper 	= new ObjectMapper();
			Menu menu 					= objectMapper.readValue(file, Menu.class);
			
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
