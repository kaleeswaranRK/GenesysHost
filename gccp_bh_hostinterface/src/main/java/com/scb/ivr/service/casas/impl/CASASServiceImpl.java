/**
 * 
 */
package com.scb.ivr.service.casas.impl;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.conn.ConnectTimeoutException;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.scb.ivr.api.hosthelper.HostHelper;
import com.scb.ivr.controller.DBController;
import com.scb.ivr.db.entity.AMIvrIntraction;
import com.scb.ivr.exception.CustomException;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.casas.ChangeTPIN_Req;
import com.scb.ivr.model.casas.ChangeTPIN_Res;
import com.scb.ivr.model.casas.GenerateTPIN_Req;
import com.scb.ivr.model.casas.GenerateTPIN_Res;
import com.scb.ivr.model.casas.ValidateTPIN_Req;
import com.scb.ivr.model.casas.ValidateTPIN_Res;
import com.scb.ivr.service.casas.CASASService;
import com.scb.ivr.util.Utilities;

/**
 * @author TA
 *
 */
@Service
public class CASASServiceImpl implements CASASService{

	@Value("${CASASdummyFilePath}")
	private String dummyFilePath;

	@Value("${CASASdummyFileExtn}")
	private String dummyFileExtn;

	@Autowired
	Utilities utilities;

	@Autowired
	HostHelper hostHelper;
	
	@Autowired
	DBController dbController;

	@Override
	public GenerateTPIN_Res generateTPIN(Map<String, Object> inputParams) {
		GenerateTPIN_Res resObj = new GenerateTPIN_Res();
		GenerateTPIN_Req reqObj = (GenerateTPIN_Req) inputParams.get("reqObj");

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(serviceProps.getProperty("hostLoggerName"));

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Request started @" + requestInitiatedTimestamp
				+ ", dummyFlag: " + reqObj.getDummyFlag());
		try {
			Response responseMessage = null;
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties.
			 * if the dummy flag is Y, got response from file required path and return to the ivr.
			 * otherwise call to endPoint API.
			 * 
			 ****/
			
			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getUserid()
						+ dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file : " + filePath);

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				} 
				responseMessage = Response.status(200).entity(responseString).build();

			} else {
				String xmlString = serviceProps.getProperty("requestPayload");

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("userId", reqObj.getUserid());
				inputElemets.put("sha256Password", reqObj.getPassword());
				inputElemets.put("key_alias",
						serviceProps.getProperty("key_alias") != null
								&& !serviceProps.getProperty("key_alias").equalsIgnoreCase("")
										? serviceProps.getProperty("key_alias")
										: "");
				inputElemets.put("sessionID", reqObj.getSessionId());
				inputElemets.put("usecaseID", reqObj.getTrackingId());

				/***
				 * Load parameter to payload request
				 * ***/
				String requestXML = utilities.injectDataIntoRequestString(xmlString, inputElemets);

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request is: " + requestXML);

				tpSystemLogger.info("Session ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ ". Request is: " + requestXML);

				serviceProps.setProperty("requestBody", requestXML);
				reqObj.setRequestBody(requestXML);

				//// Token Generation
				String token = utilities.callBasicAuthToken("", "");

				/// Token Validation
				if (token == null || "".equals(token.trim())) {
					sessionLogger
							.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The token is null/empty");
				}
				serviceProps.setProperty("token", token);
				reqObj.setToken(token);

				serviceProps.setProperty("httpMethod", HttpMethod.POST);
				
				inputParams.put("serviceProperties", serviceProps);

				/// Header Parameter
				/***
				 * 
				 * form Http Headers Parameter and pass this parameter to helper class. 
				 * then the helper class call endpoint api with header parameter and return response
				 * 
				 * (for helper class reuse purpose)
				 * ***/
				
				Map<String, String> httpHeaderParams = new HashMap<>();
//				httpHeaderParams.put(HttpHeaders.USER_AGENT, "Mozilla/5.0");
//				httpHeaderParams.put(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
//				httpHeaderParams.put(HttpHeaders.CONTENT_LENGTH, requestXML != null ? Integer.toString(requestXML.length()):"0");
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "text/xml; charset=utf-8");
				httpHeaderParams.put("SOAPAction", "encrypt");

				inputParams.put("httpHeaderParams", httpHeaderParams);

				///Call EndPoint API
				responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
			}

			if (responseMessage != null && responseMessage.getEntity() != null
					&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response : "
						+ responseMessage.getEntity().toString());

				tpSystemLogger
						.info(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
								+ ". Response : " + responseMessage.getEntity().toString());

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					
					String responseXML = responseMessage.getEntity().toString();
					String encryptResponse = StringUtils.substringBetween(responseXML, "<encryptReturn xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">", "</encryptReturn>");
					if(encryptResponse==null || StringUtils.isEmpty(encryptResponse)){
						String exception= StringUtils.substringBetween(responseXML, "<faultcode>", "</faultcode>");
						exception = exception.replaceAll("soapenv:", "");
						sessionLogger.debug("Exception:"+ exception);
						resObj.setErrorcode(GlobalConstants.FAILURECODE);
						resObj.setErrormessage(GlobalConstants.FAILURE);
					}else{
						AMIvrIntraction amivr1 = new AMIvrIntraction();
						amivr1.setRelID(reqObj.getUserid());
						amivr1.setHashedVal(encryptResponse);
						amivr1.setStatus("ACTIVE");
						amivr1.setCreatechangeDate(requestInitiatedTimestamp);
						amivr1.setTries("0");
						dbController.insertAMIvrHost(amivr1,"SETRESET");
						
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
					}
				} else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
			} else {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Empty Response");

				tpSystemLogger.info(" SESSION ID : " + reqObj.getSessionId() + " "
						+ utilities.getCurrentClassAndMethodName() + ". Empty Response");

				throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
						"Response is Null, Setting Failure code");
			}

			String endTime = dateTimeFormat.format(new Date());
			resObj.setEndTime(endTime);

			sessionLogger.debug(
					utilities.getCurrentClassAndMethodName() + ". Request ended @" + endTime + " , Time Duration : "
							+ utilities.getTimeDiffBW2Date(requestInitiatedTimestamp, endTime) + " Seconds");
		} catch (SocketException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Socket Exception occured.", e);
		} catch (SocketTimeoutException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Socket TimeOut Exception occured.", e);
		} catch (JsonParseException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Json Parse Exception occured.", e);
		} catch (ConnectTimeoutException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (ParseException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Parse Exception occured.", e);
		} catch (IllegalArgumentException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Illegal Argument Exception occured.", e);

		} catch (URISyntaxException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " URI Syntax Exception occured.", e);

		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Exception occured.", e);

		} finally {
			try {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion started ");

				/**
				 * Load actual status code and stored to the database. 
				 * ***/
				
				inputParams.put("I_STATUS_CODE", resObj.getErrorcode());
				inputParams.put("I_STATUS_DESCRIPTION", resObj.getErrormessage());

				dbController.insertHostTransactions(inputParams);
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion completed ");
			} catch (Exception e) {
				// e.printStackTrace();
				sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ " DB Host logging failed " + e.getMessage());
			}
			inputParams = null;
		}
		resObj.setStartTime(requestInitiatedTimestamp);
		resObj.setEndTime(dateTimeFormat.format(new Date()));

		/**
		 * Load default status code and return to IVR.
		 * 
		 * ***/
		
		if (!GlobalConstants.SUCCESSCODE.equalsIgnoreCase(resObj.getErrorcode())) {
			resObj.setErrorcode(utilities.getErrorCodeInFormat(resObj.getErrorcode()));
		}

		return resObj;

	}

	@Override
	public ValidateTPIN_Res validateTPIN(Map<String, Object> inputParams) {
		ValidateTPIN_Res resObj = new ValidateTPIN_Res();
		ValidateTPIN_Req reqObj = (ValidateTPIN_Req) inputParams.get("reqObj");

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(serviceProps.getProperty("hostLoggerName"));

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Request started @" + requestInitiatedTimestamp
				+ ", dummyFlag: " + reqObj.getDummyFlag());
		
		AMIvrIntraction amivr = new AMIvrIntraction();
		try {
			Response responseMessage = null;
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties.
			 * if the dummy flag is Y, got response from file required path and return to the ivr.
			 * otherwise call to endPoint API.
			 * 
			 ****/
			
			
			///Retrieve BlockCode max tries from parametric parameters
			String blockCodeMax_Tries = serviceProps.getProperty("blockCodeMax_Tries");

			System.out.println("MaxTries---->"+blockCodeMax_Tries);
			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getUserid()
						+ dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file : " + filePath);

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				} 
				responseMessage = Response.status(200).entity(responseString).build();
				
				amivr.setRelID(reqObj.getUserid());
				amivr = dbController.getAMIvrHost(amivr);
				if (amivr == null || amivr.getCheck() == null || amivr.getCheck().equalsIgnoreCase("")
						|| !amivr.getCheck().equalsIgnoreCase("DATAFOUND")) {
					resObj.setStartTime(requestInitiatedTimestamp);
					resObj.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
					resObj.setErrormessage("Customer Data not found in DB");

					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Customer Data not found in DB");

					return resObj;
				}
				
				if(Integer.parseInt(amivr.getTries()) >= Integer.parseInt(blockCodeMax_Tries)) {
					resObj.setStartTime(requestInitiatedTimestamp);
					resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_TPIN_BLOCKED_700041);
					resObj.setErrormessage("TPIN is blocked - Reached Max attempts");
					resObj.setBlocked(true);

					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". TPIN is blocked - Reached Max attempts");

					return resObj;
				}


			} else {
				
				String xmlString = serviceProps.getProperty("requestPayload");

				/***
				 * check whether the AMIVR relId already in DB
				 * Get AMIVR Details. It helps to send encPassword to endpoint. and maintain the 
				 * maximum tries in DB based on the error
				 * ***/
				amivr.setRelID(reqObj.getUserid());
				amivr = dbController.getAMIvrHost(amivr);
				if (amivr == null || amivr.getCheck() == null || amivr.getCheck().equalsIgnoreCase("")
						|| !amivr.getCheck().equalsIgnoreCase("DATAFOUND")) {
					resObj.setStartTime(requestInitiatedTimestamp);
					resObj.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
					resObj.setErrormessage("Customer Data not found in DB");

					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Customer Data not found in DB");

					return resObj;
				}
				
				if(Integer.parseInt(amivr.getTries()) >= Integer.parseInt(blockCodeMax_Tries)) {
					resObj.setStartTime(requestInitiatedTimestamp);
					resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_TPIN_BLOCKED_700041);
					resObj.setErrormessage("TPIN is blocked - Reached Max attempts");
					resObj.setBlocked(true);

					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". TPIN is blocked - Reached Max attempts");

					return resObj;
				}
				
				String encPassword = amivr.getHashedVal();

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("userId", reqObj.getUserid());
				inputElemets.put("sha256Password", reqObj.getPassword());
				inputElemets.put("encPassword",
						encPassword != null && !encPassword.equalsIgnoreCase("") ? encPassword : "");
				inputElemets.put("key_alias",
						serviceProps.getProperty("key_alias") != null
								&& !serviceProps.getProperty("key_alias").equalsIgnoreCase("")
										? serviceProps.getProperty("key_alias")
										: "");
				inputElemets.put("sessionID", reqObj.getSessionId());
				inputElemets.put("usecaseID", reqObj.getTrackingId());

				/***
				 * Load parameter to payload request
				 * ***/
				String requestXML = utilities.injectDataIntoRequestString(xmlString, inputElemets);

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request is: " + requestXML);

				tpSystemLogger.info("Session ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ ". Request is: " + requestXML);

				serviceProps.setProperty("requestBody", requestXML);
				reqObj.setRequestBody(requestXML);

				//// Token Generation
				String token = utilities.callBasicAuthToken("", "");

				/// Token Validation
				if (token == null || "".equals(token.trim())) {
					sessionLogger
							.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The token is null/empty");
				}
				serviceProps.setProperty("token", token);
				reqObj.setToken(token);

				serviceProps.setProperty("httpMethod", HttpMethod.POST);
				
				inputParams.put("serviceProperties", serviceProps);

				/// Header Parameter
				/***
				 * 
				 * form Http Headers Parameter and pass this parameter to helper class. 
				 * then the helper class call endpoint api with header parameter and return response
				 * 
				 * (for helper class reuse purpose)
				 * ***/
				
				Map<String, String> httpHeaderParams = new HashMap<>();
//				httpHeaderParams.put(HttpHeaders.USER_AGENT, "Mozilla/5.0");
//				httpHeaderParams.put(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
//				httpHeaderParams.put(HttpHeaders.CONTENT_LENGTH, requestXML != null ? Integer.toString(requestXML.length()):"0");
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "text/xml; charset=utf-8");
				httpHeaderParams.put("SOAPAction", "verify");

				inputParams.put("httpHeaderParams", httpHeaderParams);

				///Call EndPoint API
				responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
			}

			if (responseMessage != null && responseMessage.getEntity() != null
					&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response : "
						+ responseMessage.getEntity().toString());

				tpSystemLogger
						.info(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
								+ ". Response : " + responseMessage.getEntity().toString());

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					
					String responseXML = responseMessage.getEntity().toString();
					
					String verifyResponse = StringUtils.substringBetween(responseXML, "<verifyReturn xsi:type=\"soapenc:string\" xmlns:soapenc=\"http://schemas.xmlsoap.org/soap/encoding/\">", "</verifyReturn>");
					if(verifyResponse==null || StringUtils.isEmpty(verifyResponse)){
						String exception= StringUtils.substringBetween(responseXML, "<faultcode>", "</faultcode>");
						exception = exception.replaceAll("soapenv:", "");
						sessionLogger.debug("Exception:"+ exception);
						resObj.setErrorcode(GlobalConstants.FAILURECODE);
						resObj.setErrormessage(GlobalConstants.FAILURE);
					}else{
						AMIvrIntraction amivr1 = new AMIvrIntraction();
						amivr1.setRelID(reqObj.getUserid());

						if(verifyResponse.equalsIgnoreCase("100")){
							resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
							resObj.setErrormessage(GlobalConstants.SUCCESS);
							if(Integer.parseInt(amivr.getTries()) < Integer.parseInt(blockCodeMax_Tries)){
								amivr1.setStatus("ACTIVE");
								amivr1.setTries("0");
								resObj.setBlocked(false);
							}
							else{
								amivr1.setTries(blockCodeMax_Tries);
								amivr1.setStatus("BLOCKED");
								sessionLogger.debug("Already Reached Max attempts");
								resObj.setBlocked(true);
							}
							dbController.insertAMIvrHost(amivr1,"VERIFYWITHOUTBLOCK");
							sessionLogger.debug("Code :"+verifyResponse+" - password validation success");
						}else if(verifyResponse.equalsIgnoreCase("118")){
							
							int previousAlertMaxTriesCount = Integer.parseInt(blockCodeMax_Tries) - 1;
							if(amivr.getTries().equalsIgnoreCase(String.valueOf(previousAlertMaxTriesCount))){
								amivr1.setBlockedDate(requestInitiatedTimestamp);
								amivr1.setStatus("BLOCKED");
								amivr1.setTries(blockCodeMax_Tries);
								sessionLogger.debug("TPIN is blocked - Reached Max attempts ");
								verifyResponse = "1180";
								resObj.setBlocked(true);
								dbController.insertAMIvrHost(amivr1,"VERIFYWITHBLOCK");
								
								resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_TPIN_BLOCKED_700041);
								resObj.setErrormessage("TPIN is blocked - Reached Max attempts");
								resObj.setBlocked(true);
								sessionLogger.debug("Code :"+verifyResponse+" - TPIN is blocked - Reached Max attempts");
								return resObj;
							}
							else if(amivr.getTries().equalsIgnoreCase(blockCodeMax_Tries)){
								amivr1.setTries(blockCodeMax_Tries);
								amivr1.setStatus("BLOCKED");
								dbController.insertAMIvrHost(amivr1,"VERIFYWITHOUTBLOCK");
								sessionLogger.debug("Already Reached Max attempts");
								resObj.setBlocked(true);
							}
							else{
								int triesCount = Integer.parseInt(amivr.getTries());
								triesCount++;
								amivr1.setTries(String.valueOf(triesCount));
								amivr1.setStatus("ACTIVE");
								dbController.insertAMIvrHost(amivr1,"VERIFYWITHBLOCK");
								resObj.setBlocked(false);
							}
							String errorcode ="";
							if(verifyResponse.equalsIgnoreCase("1180")){
								errorcode = String.format("%06d", Integer.parseInt("118"));
							}
							else{
								errorcode = String.format("%06d", Integer.parseInt(verifyResponse));
							}
							
							resObj.setErrorcode(errorcode);
							resObj.setErrormessage("VALIDATION FAILED");
							sessionLogger.debug("Code :"+verifyResponse+" - password validation failed");
						}else if(verifyResponse.equalsIgnoreCase("102")){
							String errorcode = String.format("%06d", Integer.parseInt(verifyResponse));
							resObj.setErrorcode(errorcode);
							resObj.setErrormessage("DECRYPTION FAILED");
							sessionLogger.debug("Code :"+verifyResponse+" - decryption failed");
						}

						resObj.setCode(verifyResponse);
					}
				} else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
			} else {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Empty Response");

				tpSystemLogger.info(" SESSION ID : " + reqObj.getSessionId() + " "
						+ utilities.getCurrentClassAndMethodName() + ". Empty Response");

				throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
						"Response is Null, Setting Failure code");
			}

			String endTime = dateTimeFormat.format(new Date());
			resObj.setEndTime(endTime);

			sessionLogger.debug(
					utilities.getCurrentClassAndMethodName() + ". Request ended @" + endTime + " , Time Duration : "
							+ utilities.getTimeDiffBW2Date(requestInitiatedTimestamp, endTime) + " Seconds");
		} catch (SocketException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Socket Exception occured.", e);
		} catch (SocketTimeoutException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Socket TimeOut Exception occured.", e);
		} catch (JsonParseException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Json Parse Exception occured.", e);
		} catch (ConnectTimeoutException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (ParseException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Parse Exception occured.", e);
		} catch (IllegalArgumentException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Illegal Argument Exception occured.", e);

		} catch (URISyntaxException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " URI Syntax Exception occured.", e);

		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Exception occured.", e);

		} finally {
			try {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion started ");

				/**
				 * Load actual status code and stored to the database. 
				 * ***/
				
				inputParams.put("I_STATUS_CODE", resObj.getErrorcode());
				inputParams.put("I_STATUS_DESCRIPTION", resObj.getErrormessage());

				dbController.insertHostTransactions(inputParams);
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion completed ");
			} catch (Exception e) {
				// e.printStackTrace();
				sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ " DB Host logging failed " + e.getMessage());
			}
			inputParams = null;
		}
		resObj.setStartTime(requestInitiatedTimestamp);
		resObj.setEndTime(dateTimeFormat.format(new Date()));

		/**
		 * Load default status code and return to IVR.
		 * 
		 * ***/
		
		if (!GlobalConstants.SUCCESSCODE.equalsIgnoreCase(resObj.getErrorcode())) {
			resObj.setErrorcode(utilities.getErrorCodeInFormat(resObj.getErrorcode()));
		}

		return resObj;

	}

	@Override
	public ChangeTPIN_Res changeTPIN(Map<String, Object> inputParams) {
		ChangeTPIN_Res resObj = new ChangeTPIN_Res();
		ChangeTPIN_Req reqObj = (ChangeTPIN_Req) inputParams.get("reqObj");

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(serviceProps.getProperty("hostLoggerName"));

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Request started @" + requestInitiatedTimestamp
				+ ", dummyFlag: " + reqObj.getDummyFlag());
		
		AMIvrIntraction amivr = new AMIvrIntraction();
		try {
			Response responseMessage = null;
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties.
			 * if the dummy flag is Y, got response from file required path and return to the ivr.
			 * otherwise call to endPoint API.
			 * 
			 ****/
			
			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getUserid()
						+ dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file : " + filePath);

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				} 
				responseMessage = Response.status(200).entity(responseString).build();
				
				/***
				 * check whether the AMIVR relId already in DB
				 * Get AMIVR Details. It helps to send encPassword to endpoint. and maintain the 
				 * maximum tries in DB based on the error
				 * ***/
				amivr.setRelID(reqObj.getUserid());
				amivr = dbController.getAMIvrHost(amivr);
				if (amivr == null || amivr.getCheck() == null || amivr.getCheck().equalsIgnoreCase("")
						|| !amivr.getCheck().equalsIgnoreCase("DATAFOUND")) {
					resObj.setStartTime(requestInitiatedTimestamp);
					resObj.setErrorcode(GlobalConstants.FAILURECODE);
					resObj.setErrormessage("CUSTOMER DATA NOT FOUND IN DB");

					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Customer Data not found in DB");

					return resObj;
				}

			} else {
				
				String xmlString = serviceProps.getProperty("requestPayload");

				/***
				 * check whether the AMIVR relId already in DB
				 * Get AMIVR Details. It helps to send encPassword to endpoint. and maintain the 
				 * maximum tries in DB based on the error
				 * ***/
				amivr.setRelID(reqObj.getUserid());
				amivr = dbController.getAMIvrHost(amivr);
				if (amivr == null || amivr.getCheck() == null || amivr.getCheck().equalsIgnoreCase("")
						|| !amivr.getCheck().equalsIgnoreCase("DATAFOUND")) {
					resObj.setStartTime(requestInitiatedTimestamp);
					resObj.setErrorcode(GlobalConstants.FAILURECODE);
					resObj.setErrormessage("CUSTOMER DATA NOT FOUND IN DB");

					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Customer Data not found in DB");

					return resObj;
				}
				
				String encPassword = amivr.getHashedVal();

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("userId", reqObj.getUserid());
				inputElemets.put("sha256OldPassword", reqObj.getOldPassword());
				inputElemets.put("sha256NewPassword", reqObj.getNewPassword());
				inputElemets.put("encPassword",
						encPassword != null && !encPassword.equalsIgnoreCase("") ? encPassword : "");
				inputElemets.put("key_alias",
						serviceProps.getProperty("key_alias") != null
								&& !serviceProps.getProperty("key_alias").equalsIgnoreCase("")
										? serviceProps.getProperty("key_alias")
										: "");
				inputElemets.put("sessionID", reqObj.getSessionId());
				inputElemets.put("usecaseID", reqObj.getTrackingId());

				/***
				 * Load parameter to payload request
				 * ***/
				String requestXML = utilities.injectDataIntoRequestString(xmlString, inputElemets);

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request is: " + requestXML);

				tpSystemLogger.info("Session ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ ". Request is: " + requestXML);

				serviceProps.setProperty("requestBody", requestXML);
				reqObj.setRequestBody(requestXML);

				//// Token Generation
				String token = utilities.callBasicAuthToken("", "");

				/// Token Validation
				if (token == null || "".equals(token.trim())) {
					sessionLogger
							.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The token is null/empty");
				}
				serviceProps.setProperty("token", token);
				reqObj.setToken(token);

				serviceProps.setProperty("httpMethod", HttpMethod.POST);
				inputParams.put("serviceProperties", serviceProps);

				/// Header Parameter
				/***
				 * 
				 * form Http Headers Parameter and pass this parameter to helper class. 
				 * then the helper class call endpoint api with header parameter and return response
				 * 
				 * (for helper class reuse purpose)
				 * ***/
				
				Map<String, String> httpHeaderParams = new HashMap<>();
//				httpHeaderParams.put(HttpHeaders.USER_AGENT, "Mozilla/5.0");
//				httpHeaderParams.put(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.5");
//				httpHeaderParams.put(HttpHeaders.CONTENT_LENGTH, requestXML != null ? Integer.toString(requestXML.length()):"0");
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "text/xml; charset=utf-8");
				httpHeaderParams.put("SOAPAction", "ChangePasswordTPin");

				inputParams.put("httpHeaderParams", httpHeaderParams);

				///Call EndPoint API
				responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
			}

			if (responseMessage != null && responseMessage.getEntity() != null
					&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response : "
						+ responseMessage.getEntity().toString());

				tpSystemLogger
						.info(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
								+ ". Response : " + responseMessage.getEntity().toString());

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					
					String responseXML = responseMessage.getEntity().toString();
					String[] changePassResponse = StringUtils.substringsBetween(responseXML, "<value xsi:type=\"soapenc:string\">", "</value>");
					if(changePassResponse==null || StringUtils.isAllEmpty(changePassResponse)){
						String exception= StringUtils.substringBetween(responseXML, "<faultcode>", "</faultcode>");
						exception = exception.replaceAll("soapenv:", "");
						sessionLogger.debug("Exception:"+ exception);
						resObj.setErrorcode(GlobalConstants.FAILURECODE);
						resObj.setErrormessage(GlobalConstants.FAILURE);
					} else {
						if (changePassResponse[0].equalsIgnoreCase("100")) {
							AMIvrIntraction amivr1 = new AMIvrIntraction();
							amivr1.setRelID(reqObj.getUserid());
							amivr1.setHashedVal(changePassResponse[1]);
							amivr1.setStatus("ACTIVE");
							amivr1.setCreatechangeDate(requestInitiatedTimestamp);
							amivr1.setTries("0");
							dbController.insertAMIvrHost(amivr1, "SETRESET");
							sessionLogger.debug("Code :" + changePassResponse[0] + " - password validation success");

							resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
							resObj.setErrormessage(GlobalConstants.SUCCESS);
							
						} else if (changePassResponse[0].equalsIgnoreCase("118")
								|| changePassResponse[0].equalsIgnoreCase("1180")) {
							sessionLogger.debug("Code :" + changePassResponse[0] + " - password validation failed");
							resObj.setErrorcode(String.format("%06d", Integer.parseInt("118")));
							resObj.setErrormessage("Code :" + changePassResponse[0] + " - password validation failed");
							
						} else if (changePassResponse[0].equalsIgnoreCase("102")) {
							sessionLogger.debug("Code :" + changePassResponse[0] + " - decryption failed");
							resObj.setErrorcode(String.format("%06d", Integer.parseInt("102")));
							resObj.setErrormessage("Code :" + changePassResponse[0] + " - decryption failed");
							
						} else {
							sessionLogger.debug("Code :" + changePassResponse[0] + " - decryption failed");
							resObj.setErrorcode(String.format("%06d", Integer.parseInt(changePassResponse[0])));
							resObj.setErrormessage("Code :" + changePassResponse[0] + " - failed");
							
						}

						resObj.setCode(changePassResponse[0]);

					}
				} else {
					resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
					resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
				}
			} else {
				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Empty Response");

				tpSystemLogger.info(" SESSION ID : " + reqObj.getSessionId() + " "
						+ utilities.getCurrentClassAndMethodName() + ". Empty Response");

				throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
						"Response is Null, Setting Failure code");
			}

			String endTime = dateTimeFormat.format(new Date());
			resObj.setEndTime(endTime);

			sessionLogger.debug(
					utilities.getCurrentClassAndMethodName() + ". Request ended @" + endTime + " , Time Duration : "
							+ utilities.getTimeDiffBW2Date(requestInitiatedTimestamp, endTime) + " Seconds");
		} catch (SocketException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETEXCEPTION_700007);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket Closed");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Socket Exception occured.", e);
		} catch (SocketTimeoutException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_SOCKETTIMEOUTEXCEPTION_700008);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Socket timeout Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Socket TimeOut Exception occured.", e);
		} catch (JsonParseException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_JSONPARSEEXCEPTION_700009);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : JSON Parse occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Json Parse Exception occured.", e);
		} catch (ConnectTimeoutException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_CONNECTTIMEOUTEXCEPTION_700010);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Connect TimeOut Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (IOException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_IOEXCEPTION_700002);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : IO Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " IO Exception occured.", e);
		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		} catch (ParseException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_PARSEEXCEPTION_700005);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Parse Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Parse Exception occured.", e);
		} catch (IllegalArgumentException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Illegal Argument Exception Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Illegal Argument Exception occured.", e);

		} catch (URISyntaxException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_URISYNTAXEXCEPTION_700006);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : URI syntax Exception Occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " URI Syntax Exception occured.", e);

		} catch (CustomException e) {
			resObj.setErrorcode(e.getErrorCode());
			resObj.setErrormessage(e.getErrorMsg());
		} catch (Exception e) {
			resObj.setErrorcode(GlobalConstants.FAILURECODE);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception ");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
			+ " Exception occured.", e);

		} finally {
			try {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion started ");

				/**
				 * Load actual status code and stored to the database. 
				 * ***/
				
				inputParams.put("I_STATUS_CODE", resObj.getErrorcode());
				inputParams.put("I_STATUS_DESCRIPTION", resObj.getErrormessage());

				dbController.insertHostTransactions(inputParams);
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion completed ");
			} catch (Exception e) {
				// e.printStackTrace();
				sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ " DB Host logging failed " + e.getMessage());
			}
			inputParams = null;
		}
		resObj.setStartTime(requestInitiatedTimestamp);
		resObj.setEndTime(dateTimeFormat.format(new Date()));

		/**
		 * Load default status code and return to IVR.
		 * 
		 * ***/
		
		if (!GlobalConstants.SUCCESSCODE.equalsIgnoreCase(resObj.getErrorcode())) {
			resObj.setErrorcode(utilities.getErrorCodeInFormat(resObj.getErrorcode()));
		}

		return resObj;
	}

}
