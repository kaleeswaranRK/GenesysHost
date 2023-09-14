package com.scb.ivr.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.naming.NamingException;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.scb.ivr.exception.CustomException;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.ebbs.CustomerIdentificationAcctNum_Req;
import com.scb.ivr.model.ebbs.CustomerIdentificationAcctNum_Res;
import com.scb.ivr.service.ebbs.EbbsService;
import com.scb.ivr.util.GetConfigProperties;
import com.scb.ivr.util.Utilities;
import com.scb.ivr.util.ValidateInputDetails;

@Component
@Service
public class RKALEESController {
	@Autowired
	Utilities utilities;

	@Autowired
	ValidateInputDetails validateInput;

	@Autowired
	EbbsService service;
	
	@Autowired
	ConfigController configController;
	
	@Autowired
	GetConfigProperties getConfigProperties;

	/// To Identify Customer using Account Number
	public CustomerIdentificationAcctNum_Res setCardLost(CustomerIdentificationAcctNum_Req req) {

		CustomerIdentificationAcctNum_Res resObj = new CustomerIdentificationAcctNum_Res();

		
		String hostLoggerName = GlobalConstants.HostLog_RKALEES;
		String serviceName = "setCardLost";
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
						+ ". sessionId is NULL, setting trackingId as SessionId: " + trackingID);
			}

			sessionId = req.getSessionId();
			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);

			/// VALIDATE INPUT PARAMETER
			/****
			 * 
			 * @param acctNumber, mandatory and not null
			 * @param currency code, mandatory and not null
			 * 
			 * validate input parameter whether acctNumber and currency is null or empty.
			 * if the input is null @return failure message..
			 * otherwise flows to be continued.
			 * 
			 ****/
			
			resObj = validateInput.getCustomerIdentificationAcctNum(req);

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
			properties.put("tpSystem", GlobalConstants.TPSystem_RKALEES);
			properties.put("hostLoggerName", hostLoggerName);

			/// LOAD SERVICE PROPERTIES
			/****
			 * 
			 * Load all mandatory properties.
			 * Load the properties from file based on service name.
			 * @return config.properties, operation.properties, EBBSpayload.properties and parametric.properties
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
			req.setHost(GlobalConstants.TPSystem_Ebbs);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			///CALL SERVICE IMPLEMENTAION
			resObj = service.getCustomerIdentificationAcctNum(inputParams);

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

		tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed *** ");
		return resObj;
	}
}
