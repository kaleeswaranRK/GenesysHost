/**
 * 
 */
package com.scb.ivr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
//
//import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.scb.ivr.controller.ConfigController;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;

/**
 * @author TA
 *
 */

@Component
public class GetConfigProperties {
	@Autowired
	ConfigController configController;
	
	@Autowired
	Utilities utilities;
	
	@Value("${propertyfilepath}")
	private String propertyfilepath;
	
	//// Get and load all properties from file ( config, operations, parametric,
	//// payload )
	public Properties getServiceConfig(Properties properties) throws Exception {

		Properties serviceProperties = new Properties();
		serviceProperties.putAll(properties);

		String sessionId = properties.getProperty("sessionId");
		String serviceName = properties.getProperty("serviceName");
		String tpSystem = properties.getProperty("tpSystem");

		Logger sessionLogger = CustomLogger.getLogger(sessionId);

		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Config));
		
		
		//// LOAD DUMMY FLAG PARAMETERS
		try {
			if (properties != null && properties.getProperty(serviceName) != null) {

				String configPropertyStr = properties.getProperty(serviceName);
				String serviceConfigValues[] = configPropertyStr.split(",");

				if (serviceConfigValues.length >= 4) {
					serviceProperties.setProperty("dummyFlag", serviceConfigValues[0]);
					serviceProperties.setProperty("operationName", serviceConfigValues[1]);
					serviceProperties.setProperty("serviceId", serviceConfigValues[2]);
					serviceProperties.setProperty("serviceType", serviceConfigValues[3]);
				} else {
					serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
					sessionLogger.debug(utilities.getCurrentClassAndMethodName()
							+ " Config property value is not match with the expected format. Property is: "
							+ configPropertyStr);
				}
			} else {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " - " + properties.getProperty(serviceName)
						+ " is not found in config.properties.");
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			}
		} catch (Exception e) {
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			sessionLogger.debug(utilities.getCurrentClassAndMethodName()
					+ " Config.properties -> Exception occured while processing the config propery:" + serviceName
					+ ". " + e.getMessage());
		}

		properties = new Properties();
		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Operations));
		
		
		//// LOAD ENDPOINT PARAMETER
		try {
			if (properties != null && properties.getProperty("endPoint." + serviceName) != null) {
				String endPointString = properties.getProperty("endPoint." + serviceName);

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !utilities.isNullOrEmpty(endPoints[0])) {
					serviceProperties.setProperty("endPoint", endPoints[0].trim());
					serviceProperties.setProperty("timeOut", endPoints[1] != null ? endPoints[1].trim() : "20000");
				} else {
					serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
					sessionLogger.debug(utilities.getCurrentClassAndMethodName()
							+ " Operations.properties -> Incorrect endPoint params found for the service : "
							+ serviceName);
				}
			} else {
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ " Operations.properties -> No endPoint URL found for the service: " + serviceName);
			}

			if (properties != null && properties.getProperty("endPoint." + tpSystem + ".tokenUrl") != null) {
				String endPointString = properties.getProperty("endPoint." + tpSystem + ".tokenUrl");

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !utilities.isNullOrEmpty(endPoints[0])) {
					serviceProperties.setProperty("tokenURL", endPoints[0].trim());
					serviceProperties.setProperty("tokenTimeOut", endPoints[1] != null ? endPoints[1].trim() : "20000");
				}
			}
		} catch (Exception e) {
			sessionLogger.debug(utilities.getCurrentClassAndMethodName()
					+ " Exception occured while fetching endPoint in operations.properties. Service: " + serviceName
					+ " - " + e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		properties = new Properties();
		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Parametric));

		
		//// LOAD Parametric properties
		try {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();

				if (key.contains(tpSystem + ".")) {
					serviceProperties.put(key, value);
				}

				if (!key.contains(".")) {
					serviceProperties.put(key, value);
				}

			}

		} catch (Exception e) {
			sessionLogger.debug(utilities.getCurrentClassAndMethodName()
					+ " Exception occured while loading Parametric properties." + e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		//// LOAD PAYLOAD PARAMETER
		try {

			File file = new File(propertyfilepath + tpSystem + "Payload.properties");
			Properties propPayload = new Properties();
			InputStream input = new FileInputStream(file);
			propPayload.load(input);

			if (propPayload != null && propPayload.getProperty(serviceName) != null) {
				serviceProperties.setProperty("requestPayload", propPayload.getProperty(serviceName));
			} else {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " No request payload found in " + tpSystem
						+ "Payload.properties");
				serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
			}

		} catch (Exception e) {
			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Exception occured while loading payload properties."
					+ e.getMessage());
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		return serviceProperties;
	}
	
	public Properties getTokenParams(String tpSystem, String serviceName) {
		Properties serviceProperties = new Properties();
		serviceProperties.setProperty("serviceName", serviceName);
		serviceProperties.setProperty("tpSystem", tpSystem);

		Properties properties = new Properties();

		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Operations));
		//// LOAD ENDPOINT TOKEN PARAMETER
		try {
			if (properties != null && properties.getProperty("endPoint." + tpSystem + ".tokenUrl") != null) {
				String endPointString = properties.getProperty("endPoint." + tpSystem + ".tokenUrl");

				String endPoints[] = endPointString.split(";");
				if (endPoints.length >= 2 && !utilities.isNullOrEmpty(endPoints[0].trim())) {
					String tokenUrl = endPoints[0].trim();
					String tokenTimeOut = endPoints[1].trim();
					serviceProperties.setProperty("tokenURL", tokenUrl);
					serviceProperties.setProperty("tokenTimeOut", endPoints[1] != null ? tokenTimeOut : "20000");
				}
			}
		} catch (Exception e) {
			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		properties = new Properties();
		properties.putAll((Properties) configController.getConfigFileValues(GlobalConstants.Parametric));

		//// LOAD Parametric properties
		try {
			for (Map.Entry<Object, Object> entry : properties.entrySet()) {
				String key = entry.getKey().toString();
				String value = entry.getValue().toString();

				if (key.contains(tpSystem + ".")) {
					serviceProperties.put(key, value);
				}

				if (!key.contains(".")) {
					serviceProperties.put(key, value);
				}

			}

		} catch (Exception e) {

			serviceProperties.setProperty("ERROR_CODE", GlobalConstants.FAILURECODE);
		}

		return serviceProperties;
	}


}
