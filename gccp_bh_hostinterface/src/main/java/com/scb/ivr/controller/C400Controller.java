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

import com.scb.ivr.ehcache.service.UpdateCache;
import com.scb.ivr.exception.CustomException;
//import com.scb.avaya.logging.CustomLogger;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.c400.CreditCardBalance_Req;
import com.scb.ivr.model.c400.CreditCardBalance_Res;
import com.scb.ivr.model.c400.CreditCardList_Req;
import com.scb.ivr.model.c400.CreditCardList_Res;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Req;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Res;
import com.scb.ivr.model.c400.CustomerProfile_Req;
import com.scb.ivr.model.c400.CustomerProfile_Res;
import com.scb.ivr.model.c400.res.custprofile.CustProfileResponseData;
import com.scb.ivr.model.ebbs.CustomerIdentificationRELCRP_Req;
import com.scb.ivr.model.ebbs.CustomerIdentificationRELCRP_Res;
import com.scb.ivr.service.c400.C400Service;
import com.scb.ivr.util.GetConfigProperties;
import com.scb.ivr.util.Utilities;
import com.scb.ivr.util.ValidateInputDetails;

/**
 * @author TA
 * @category Cards400 related inquiry and update the details
 */

@Component
@Service
public class C400Controller {

	@Autowired
	Utilities utilities;

	@Autowired
	ValidateInputDetails validateInput;

	@Autowired
	ConfigController configController;

	@Autowired
	C400Service service;

	@Autowired
	EBBSController ebbsController;
	
	@Autowired
	GetConfigProperties getConfigProperties;
	
	@Autowired
	UpdateCache updateCache;

	/*public void getTest() {
		Map<String, Map<String, String>> map = (Map<String, Map<String, String>>) updateCache.updateCacheValues("menu.json");
		System.out.println(map);
		

		for(String key:map.keySet()) {
			System.out.println(key + ": " + map.get(key));	
		}
	}*/
	/// To Identify Customer using Credit Card Number
	public CustomerIdentificationCardNum_Res getCustomerIdentificationCardNum(CustomerIdentificationCardNum_Req req) {

		CustomerIdentificationCardNum_Res resObj = new CustomerIdentificationCardNum_Res();
		
		String hostLoggerName = GlobalConstants.HostLog_C400;
		String serviceName = "getCustomerIdentificationCardNum";
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
			
			resObj = validateInput.getCustomerIdentificationCardNum(req);

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
			properties.put("tpSystem", GlobalConstants.TPSystem_C400);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, C400payload.properties and parametric.properties
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
			req.setHost(GlobalConstants.TPSystem_C400);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.getCustomerIdentificationCardNum(inputParams);

		}catch (IOException e) {
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

	/// To get List of Credit Cards
	public CreditCardList_Res getCreditCardList(CreditCardList_Req req) {

		CreditCardList_Res resObj = new CreditCardList_Res();
		
		String hostLoggerName = GlobalConstants.HostLog_C400;
		String serviceName = "getCreditCardList";
		String trackingID = utilities.generateTrackingId();

		//Logger tpSystemLogger = LogManager.getContext().getLogger(hostLoggerName);
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
			
			resObj = validateInput.getCreditCardList(req);

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
			properties.put("tpSystem", GlobalConstants.TPSystem_C400);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory fields from properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, C400payload.properties and parametric.properties
			 * 
			 ****/
			
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);
			System.out.println("service property : "+serviceProperties);
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
			req.setHost(GlobalConstants.TPSystem_C400);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.getCreditCardList(inputParams);

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

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + "*** Completed **** ");
		return resObj;
	}

	/// To get Credit Card Balance
	public CreditCardBalance_Res getCreditCardBalance(CreditCardBalance_Req req) {

		CreditCardBalance_Res resObj = new CreditCardBalance_Res();
		
		String hostLoggerName = GlobalConstants.HostLog_C400;
		String serviceName = "getCreditCardBalance";
		String trackingID = utilities.generateTrackingId();

		//Logger tpSystemLogger = LogManager.getContext().getLogger(hostLoggerName);
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
			
			resObj = validateInput.getCreditCardBalance(req);

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
			properties.put("tpSystem", GlobalConstants.TPSystem_C400);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, C400payload.properties and parametric.properties
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
			req.setHost(GlobalConstants.TPSystem_C400);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.getCreditCardBalance(inputParams);

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

	/// To Identify Customer using Credit Card Number
	public CustomerProfile_Res getCustomerProfile(CustomerProfile_Req req) {

		CustomerProfile_Res resObj = new CustomerProfile_Res();
		
		String hostLoggerName = GlobalConstants.HostLog_C400;
		String serviceName = "getCustomerProfile";
		String trackingID = utilities.generateTrackingId();

		//Logger tpSystemLogger = LogManager.getContext().getLogger(hostLoggerName);
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
			
			resObj = validateInput.getCustomerProfile(req);

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
			properties.put("tpSystem", GlobalConstants.TPSystem_C400);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, C400payload.properties and parametric.properties
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
			req.setHost(GlobalConstants.TPSystem_C400);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.getCustomerProfile(inputParams);

			if (resObj.getErrorcode() != null && GlobalConstants.SUCCESSCODE.equals(resObj.getErrorcode())) {
				sessionLogger.debug("Customer Product details got successfully");

				/// Get Customer Profile Details
				CustomerIdentificationRELCRP_Req requestProfileReq = new CustomerIdentificationRELCRP_Req();
				CustomerIdentificationRELCRP_Res requestProfileRes = new CustomerIdentificationRELCRP_Res();
				 requestProfileReq.setProfileId(req.getRelId());
				//requestProfileReq.setProfileId("860105792");
				requestProfileReq.setSessionId(req.getSessionId());

				requestProfileRes = ebbsController.getCustomerIdentificationRELCRP(requestProfileReq);

				if (requestProfileRes.getErrorcode() != null
						&& requestProfileRes.getErrorcode().equalsIgnoreCase(GlobalConstants.SUCCESSCODE)) {

					sessionLogger.debug("Customer Profile details got successfully");

					CustProfileResponseData custProfileResponseData = new CustProfileResponseData();
					custProfileResponseData.setData(requestProfileRes.getResponse().getData());

					resObj.setCustProfile(custProfileResponseData);
				} else {

					sessionLogger.debug("Customer Profile details got failed");
					resObj.setErrorcode(requestProfileRes.getErrorcode());
					resObj.setErrormessage(requestProfileRes.getErrormessage());
					resObj.setCustProduct(null);
				}
			} else {
				sessionLogger.debug("Customer Product details got failed");
				return resObj;
			}

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
