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
import com.scb.ivr.model.rkalees.cardLost_Req;
import com.scb.ivr.model.rkalees.cardLost_Res;
import com.scb.ivr.service.rkalees.RkaleesService;
import com.scb.ivr.util.GetConfigProperties;
import com.scb.ivr.util.Utilities;

@Component
@Service
public class RKALEESController {
	@Autowired
	Utilities utilities;

	@Autowired
	RkaleesService cardlost;

	@Autowired
	RkaleesService service;

	@Autowired
	ConfigController configController;

	@Autowired
	GetConfigProperties getConfigProperties;

	/// To Identify Customer using Account Number
	public cardLost_Res setCardLost(cardLost_Req req) {
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);

		cardLost_Res resObj = new cardLost_Res();
		String hostLoggerName = GlobalConstants.HostLog_RKALEES;
		String serviceName = "setCardLost";
		String trackingID = utilities.generateTrackingId();
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext()
				.getLogger(hostLoggerName);
		Logger sessionLogger = null;

		String startDate = dateTimeFormat.format(new Date());

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
			System.out.println(req.getCardNo());
			if (req.getCardNo()!=null&&!(req.getCardNo().isEmpty())) {
				
			if(req.getCardNo().length()==16) {
				if (req.getUserId()!=null&& !(req.getUserId().isEmpty())) {
			sessionId = req.getSessionId();
			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request from IVR: " + req);
			/// VALIDATE INPUT PARAMETER
			/****
			 * 
			 * @param acctNumber, mandatory and not null
			 * @param currency    code, mandatory and not null
			 * 
			 *                    validate input parameter whether acctNumber and currency
			 *                    is null or empty. if the input is null @return failure
			 *                    message.. otherwise flows to be continued.
			 * 
			 ****/

//			resObj = cardlost.cardLost(req);
//
//			if (resObj.getErrorcode() != null) {
//				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Input validation failed: "
//						+ resObj.getErrorcode() + " - " + resObj.getErrormessage());
//				return resObj;
//			}

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
			 * Load all mandatory properties. Load the properties from file based on service
			 * name.
			 * 
			 * @return config.properties, operation.properties, EBBSpayload.properties and
			 *         parametric.properties
			 * 
			 ****/
			System.out.println("property values");
			System.out.println("properties : "+properties);
			Properties serviceProperties = getConfigProperties.getServiceConfig(properties);
			System.out.println("service "+serviceProperties);
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
			req.setHost(GlobalConstants.TPSystem_RKALEES);

			/// ASSIGN ALL PARAMS TO HASHMAP
			Map<String, Object> inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProperties);
			inputParams.put("reqObj", req);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". InParams: " + inputParams);

			/// CALL SERVICE IMPLEMENTAION
			resObj = service.cardLost(inputParams);
				}
				else {
					resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_RELID_OR_CARD_EMPTY_700072);
					resObj.setErrormessage("profile Id number is Empty");
					sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ "profile Id number is Empty");
				}

		}
			else {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_CARD_NOT_VALID_700074);
				resObj.setErrormessage("Card number is not valid");
				sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
				+ "Card number is not valid");
			}
			}
			else {
				resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_RELID_OR_CARD_EMPTY_700072);
				resObj.setErrormessage("Card number is empty");
				sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
				+ "Card number is empty");
			}
		}

		catch (IOException e) {
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
			tpSystemLogger.info(utilities.getCurrentClassAndMethodName() + " *** Completed *** ");

		}
		resObj.setStartTime(startDate);
		resObj.setEndTime(dateTimeFormat.format(new Date()));
		sessionLogger.debug(
				utilities.getCurrentClassAndMethodName() + ". Request ended @" + dateTimeFormat.format(new Date()) + " , Time Duration : "
						+ utilities.getTimeDiffBW2Date(startDate, dateTimeFormat.format(new Date())) + " Seconds");
		return resObj;
	}
	
	
}
