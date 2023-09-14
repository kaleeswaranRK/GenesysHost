package com.scb.ivr.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.scb.ivr.exception.CustomException;
//import com.scb.avaya.logging.CustomLogger;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.uaas.GenerateAPIN_Req;
import com.scb.ivr.model.uaas.GenerateAPIN_Res;
import com.scb.ivr.model.uaas.GenerateCCPIN_Req;
import com.scb.ivr.model.uaas.GenerateCCPIN_Res;
import com.scb.ivr.model.uaas.GenerateOTP_Req;
import com.scb.ivr.model.uaas.GenerateOTP_Res;
import com.scb.ivr.model.uaas.ValidateAPIN_Req;
import com.scb.ivr.model.uaas.ValidateAPIN_Res;
import com.scb.ivr.model.uaas.ValidateCCPIN_Req;
import com.scb.ivr.model.uaas.ValidateCCPIN_Res;
import com.scb.ivr.model.uaas.ValidateOTP_Req;
import com.scb.ivr.model.uaas.ValidateOTP_Res;
import com.scb.ivr.service.uaas.UaasService;
import com.scb.ivr.util.GetConfigProperties;
import com.scb.ivr.util.Utilities;
import com.scb.ivr.util.ValidateInputDetails;

/**
 * @author TA
 * @category UAAS create/inquire SMS, PIN related information
 */

@Component
@Service
public class UAASController {

	@Autowired
	Utilities utilities;

	@Autowired
	ValidateInputDetails validateInput;

	@Autowired
	ConfigController configController;

	@Autowired
	UaasService service;
	
	@Autowired
	GetConfigProperties getConfigProperties;

	/// To generate OTP
	public GenerateOTP_Res generateOTP(GenerateOTP_Req req) {

		GenerateOTP_Res resObj = new GenerateOTP_Res();
		
		String hostLoggerName = GlobalConstants.HostLog_UAAS;
		String serviceName = "generateOTP";
		String trackingID = utilities.generateTrackingId();

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(hostLoggerName);
		Logger sessionLogger = null;

		SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());

		String sessionId = "";

		try {

			/// VALIDATE SESSION ID
			/****
			 * 
			 * Validate the session id. if the session id is null, generate tracking id.
			 * post that tracking id as session id then create a session logger.
			 * 
			 * otherwise create the session logger.
			 * 
			 ****/
			
			if (req != null && req.getSessionId() != null && !"".equals(req.getSessionId().trim())) {
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". SessionId: " + req.getSessionId());
			} else {
				req.setSessionId(trackingID);
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". SessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}
			sessionId = req.getSessionId();

			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			resObj = validateInput.generateOTP(req);
			/****
			 * 
			 * @param relID, mandatory and not null
			 * 
			 * validate input parameter whether relID is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/

			if (resObj.getErrorcode() != null) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
				return resObj;
			}

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.generateOTP(inputParams);

		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NamingException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NAMINGEXCEPTION_700003);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Naming Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Custom Exception occured.", e);
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception occured.", e);

		} finally {
			if (sessionLogger != null) {
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + " Total Time Duration : "
								+ utilities.getTimeDiffBW2Date(startDate,
										new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
								+ " Seconds");
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response to IVR:" + resObj);
			}
		}

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed ***");
		return resObj;
	}

	/// To validate OTP
	public ValidateOTP_Res validateOTP(ValidateOTP_Req req) {

		ValidateOTP_Res resObj = new ValidateOTP_Res();

		
		String hostLoggerName = GlobalConstants.HostLog_UAAS;
		String serviceName = "validateOTP";
		String trackingID = utilities.generateTrackingId();

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(hostLoggerName);
		Logger sessionLogger = null;

		SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());

		String sessionId = "";

		try {

			/// VALIDATE SESSION ID
			/****
			 * 
			 * Validate the session id. if the session id is null, generate tracking id.
			 * post that tracking id as session id then create a session logger.
			 * 
			 * otherwise create the session logger.
			 * 
			 ****/
			
			if (req != null && req.getSessionId() != null && !"".equals(req.getSessionId().trim())) {
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". SessionId: " + req.getSessionId());
			} else {
				req.setSessionId(trackingID);
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". SessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}
			sessionId = req.getSessionId();

			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			/****
			 * 
			 * @param relID, mandatory and not null
			 * 
			 * validate input parameter whether relID is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/
			
			resObj = validateInput.validateOTP(req);

			if (resObj.getErrorcode() != null) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
				return resObj;
			}

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.validateOTP(inputParams);

		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NamingException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NAMINGEXCEPTION_700003);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Naming Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Custom Exception occured.", e);
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception occured.", e);

		} finally {
			if (sessionLogger != null) {
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + " Total Time Duration : "
								+ utilities.getTimeDiffBW2Date(startDate,
										new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
								+ " Seconds");
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response to IVR:" + resObj);
			}
		}

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed ***");
		return resObj;
	}

	/// Generate APIN
	public GenerateAPIN_Res generateAPIN(GenerateAPIN_Req req) {

		GenerateAPIN_Res resObj = new GenerateAPIN_Res();
		
		String hostLoggerName = GlobalConstants.HostLog_UAAS;
		String serviceName = "generateAPIN";
		String trackingID = utilities.generateTrackingId();

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(hostLoggerName);
		Logger sessionLogger = null;

		SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());

		String sessionId = "";
		try {

			/// VALIDATE SESSION ID
			/****
			 * 
			 * Validate the session id. if the session id is null, generate tracking id.
			 * post that tracking id as session id then create a session logger.
			 * 
			 * otherwise create the session logger.
			 * 
			 ****/
			
			if (req != null && req.getSessionId() != null && !"".equals(req.getSessionId().trim())) {
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". SessionId: " + req.getSessionId());
			} else {
				req.setSessionId(trackingID);
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". SessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}
			sessionId = req.getSessionId();

			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			
			/****
			 * 
			 * @param cardNumber, mandatory and not null
			 * 
			 * validate input parameter whether cardNumber is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/
			
			resObj = validateInput.generateAPIN(req);

			if (resObj.getErrorcode() != null) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
				return resObj;
			}

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.generateAPIN(inputParams);

		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NamingException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NAMINGEXCEPTION_700003);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Naming Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Custom Exception occured.", e);
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception occured.", e);

		} finally {
			if (sessionLogger != null) {
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + " Total Time Duration : "
								+ utilities.getTimeDiffBW2Date(startDate,
										new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
								+ " Seconds");
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response to IVR:" + resObj);
			}
		}

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed ***");
		return resObj;
	}

	/// Change APIN
	public GenerateAPIN_Res changeAPIN(GenerateAPIN_Req req) {

		GenerateAPIN_Res resObj = new GenerateAPIN_Res();

		
		String hostLoggerName = GlobalConstants.HostLog_UAAS;
		String serviceName = "changeAPIN";
		String trackingID = utilities.generateTrackingId();

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(hostLoggerName);
		Logger sessionLogger = null;

		SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());

		String sessionId = "";
		try {

			/// VALIDATE SESSION ID
			/****
			 * 
			 * Validate the session id. if the session id is null, generate tracking id.
			 * post that tracking id as session id then create a session logger.
			 * 
			 * otherwise create the session logger.
			 * 
			 ****/
			
			if (req != null && req.getSessionId() != null && !"".equals(req.getSessionId().trim())) {
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". SessionId: " + req.getSessionId());
			} else {
				req.setSessionId(trackingID);
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". SessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}
			sessionId = req.getSessionId();

			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			/****
			 * 
			 * @param cardNumber, mandatory and not null
			 * 
			 * validate input parameter whether cardNumber is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/
			
			resObj = validateInput.generateAPIN(req);

			if (resObj.getErrorcode() != null) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
				return resObj;
			}

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.generateAPIN(inputParams);

		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NamingException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NAMINGEXCEPTION_700003);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Naming Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Custom Exception occured.", e);
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception occured.", e);

		} finally {
			if (sessionLogger != null) {
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + " Total Time Duration : "
								+ utilities.getTimeDiffBW2Date(startDate,
										new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
								+ " Seconds");
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response to IVR:" + resObj);
			}
		}

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed ***");
		return resObj;
	}

	/// Validate APIN
	public ValidateAPIN_Res validateAPIN(ValidateAPIN_Req req) {

		ValidateAPIN_Res resObj = new ValidateAPIN_Res();
		
		String hostLoggerName = GlobalConstants.HostLog_UAAS;
		String serviceName = "validateAPIN";
		String trackingID = utilities.generateTrackingId();

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(hostLoggerName);
		Logger sessionLogger = null;

		SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());

		String sessionId = "";
		try {

			/// VALIDATE SESSION ID
			/****
			 * 
			 * Validate the session id. if the session id is null, generate tracking id.
			 * post that tracking id as session id then create a session logger.
			 * 
			 * otherwise create the session logger.
			 * 
			 ****/
			
			if (req != null && req.getSessionId() != null && !"".equals(req.getSessionId().trim())) {
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". SessionId: " + req.getSessionId());
			} else {
				req.setSessionId(trackingID);
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". SessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}
			sessionId = req.getSessionId();

			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			/****
			 * 
			 * @param cardNumber, mandatory and not null
			 * 
			 * validate input parameter whether cardNumber is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/
			resObj = validateInput.validateAPIN(req);

			if (resObj.getErrorcode() != null) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
				return resObj;
			}

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.validateAPIN(inputParams);

		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NamingException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NAMINGEXCEPTION_700003);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Naming Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Custom Exception occured.", e);
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception occured.", e);

		} finally {
			if (sessionLogger != null) {
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + " Total Time Duration : "
								+ utilities.getTimeDiffBW2Date(startDate,
										new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
								+ " Seconds");
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response to IVR:" + resObj);
			}
		}

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed ***");
		return resObj;
	}

	/// Generate CCPIN
	public GenerateCCPIN_Res generateCCPIN(GenerateCCPIN_Req req) {

		GenerateCCPIN_Res resObj = new GenerateCCPIN_Res();

		
		String hostLoggerName = GlobalConstants.HostLog_UAAS;
		String serviceName = "generateCCPIN";
		String trackingID = utilities.generateTrackingId();

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(hostLoggerName);
		Logger sessionLogger = null;

		SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());

		String sessionId = "";
		try {

			/// VALIDATE SESSION ID
			/****
			 * 
			 * Validate the session id. if the session id is null, generate tracking id.
			 * post that tracking id as session id then create a session logger.
			 * 
			 * otherwise create the session logger.
			 * 
			 ****/
			
			if (req != null && req.getSessionId() != null && !"".equals(req.getSessionId().trim())) {
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". SessionId: " + req.getSessionId());
			} else {
				req.setSessionId(trackingID);
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". SessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}
			sessionId = req.getSessionId();

			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			/****
			 * 
			 * @param cardNumber, mandatory and not null
			 * 
			 * validate input parameter whether cardNumber is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/
			resObj = validateInput.generateCCPIN(req);

			if (resObj.getErrorcode() != null) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
				return resObj;
			}

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.generateCCPIN(inputParams);

		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NamingException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NAMINGEXCEPTION_700003);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Naming Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Custom Exception occured.", e);
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception occured.", e);

		} finally {
			if (sessionLogger != null) {
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + " Total Time Duration : "
								+ utilities.getTimeDiffBW2Date(startDate,
										new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
								+ " Seconds");
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response to IVR:" + resObj);
			}
		}

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed ***");
		return resObj;
	}

	/// Change CCPIN
	public GenerateCCPIN_Res changeCCPIN(GenerateCCPIN_Req req) {

		GenerateCCPIN_Res resObj = new GenerateCCPIN_Res();

		
		String hostLoggerName = GlobalConstants.HostLog_UAAS;
		String serviceName = "changeCCPIN";
		String trackingID = utilities.generateTrackingId();

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(hostLoggerName);
		Logger sessionLogger = null;

		SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());

		String sessionId = "";
		try {

			/// VALIDATE SESSION ID
			/****
			 * 
			 * Validate the session id. if the session id is null, generate tracking id.
			 * post that tracking id as session id then create a session logger.
			 * 
			 * otherwise create the session logger.
			 * 
			 ****/
			
			if (req != null && req.getSessionId() != null && !"".equals(req.getSessionId().trim())) {
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". SessionId: " + req.getSessionId());
			} else {
				req.setSessionId(trackingID);
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". SessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}
			sessionId = req.getSessionId();

			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			/****
			 * 
			 * @param cardNumber, mandatory and not null
			 * 
			 * validate input parameter whether cardNumber is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/
			resObj = validateInput.generateCCPIN(req);

			if (resObj.getErrorcode() != null) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
				return resObj;
			}

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.generateCCPIN(inputParams);

		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NamingException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NAMINGEXCEPTION_700003);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Naming Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Custom Exception occured.", e);
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception occured.", e);

		} finally {
			if (sessionLogger != null) {
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + " Total Time Duration : "
								+ utilities.getTimeDiffBW2Date(startDate,
										new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
								+ " Seconds");
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response to IVR:" + resObj);
			}
		}

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed ***");
		return resObj;
	}

	/// Change APIN
	public ValidateCCPIN_Res validateCCPIN(ValidateCCPIN_Req req) {

		ValidateCCPIN_Res resObj = new ValidateCCPIN_Res();
		
		String hostLoggerName = GlobalConstants.HostLog_UAAS;
		String serviceName = "validateCCPIN";
		String trackingID = utilities.generateTrackingId();

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(hostLoggerName);
		Logger sessionLogger = null;

		SimpleDateFormat formatter = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String startDate = formatter.format(new Date());

		String sessionId = "";
		try {

			/// VALIDATE SESSION ID
			/****
			 * 
			 * Validate the session id. if the session id is null, generate tracking id.
			 * post that tracking id as session id then create a session logger.
			 * 
			 * otherwise create the session logger.
			 * 
			 ****/
			
			if (req != null && req.getSessionId() != null && !"".equals(req.getSessionId().trim())) {
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". SessionId: " + req.getSessionId());
			} else {
				req.setSessionId(trackingID);
				sessionLogger = CustomLogger.getLogger(req.getSessionId());
				if (sessionLogger == null) {
					throw new CustomException(GlobalConstants.FAILURECODE, "sessionLogger is NULL");
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". SessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}
			sessionId = req.getSessionId();

			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			/****
			 * 
			 * @param cardNumber, mandatory and not null
			 * 
			 * validate input parameter whether cardNumber is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/
			
			resObj = validateInput.validateCCPIN(req);

			if (resObj.getErrorcode() != null) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
				return resObj;
			}

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", req.getSessionId());
			properties.put("serviceName", serviceName);
			properties.put("trackingID", trackingID);
			properties.put("tpSystem", GlobalConstants.TPSystem_UAAS);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, UAASpayload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);

			if (serviceProperties.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProperties.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Error found in service properties ");

				throw new CustomException(GlobalConstants.ERRORCODE_SERVICE_PROP_NOT_FOUND_700012,
						"Exception occurs while fetching environment properties");
			}

			/// CLEAR PROPERTIES
			properties.clear();

			/// ASSIGN PARAMETER
			req.setTrackingId(trackingID);
			req.setServiceName(serviceName);
			req.setDummyFlag(serviceProperties.getProperty("dummyFlag"));
			req.setServiceId(serviceProperties.getProperty("serviceId"));
			req.setEndPoint(serviceProperties.getProperty("endPoint"));
			req.setTimeout(serviceProperties.getProperty("timeOut"));
			req.setRequestTime(startDate);
			req.setApiName(serviceName);
			req.setHost(GlobalConstants.TPSystem_UAAS);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.validateCCPIN(inputParams);

		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NamingException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NAMINGEXCEPTION_700003);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Naming Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Custom Exception occured.", e);
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception occured.", e);

		} finally {
			if (sessionLogger != null) {
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + " Total Time Duration : "
								+ utilities.getTimeDiffBW2Date(startDate,
										new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
								+ " Seconds");
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response to IVR:" + resObj);
			}
		}

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed ***");
		return resObj;
	}

}
