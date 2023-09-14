package com.scb.ivr.service.euronet.impl;

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
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scb.avaya.logging.CustomLogger;
import com.scb.ivr.api.hosthelper.HostHelper;
import com.scb.ivr.controller.DBController;
import com.scb.ivr.exception.CustomException;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Req;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Res;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Req;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Res;
import com.scb.ivr.model.euronet.res.custidentifydebtcard.CustIdentificationDebtCardResponseData;
import com.scb.ivr.model.euronet.res.custidentifyrelid.CustIdentificationDebtRelIdResponseData;
import com.scb.ivr.service.euronet.EuronetService;
import com.scb.ivr.util.Utilities;

@Service
public class EuronetServiceImpl implements EuronetService {

	@Value("${EURONETdummyFilePath}")
	private String dummyFilePath;

	@Value("${EURONETdummyFileExtn}")
	private String dummyFileExtn;

	@Autowired
	Utilities utilities;

	@Autowired
	HostHelper hostHelper;
	
	@Autowired
	DBController dbController;

	@Override
	public CustomerIdentificationDebtCardNum_Res getCustomerIdentificationDebtCardNum(Map<String, Object> inputParams) {
		CustomerIdentificationDebtCardNum_Res resObj = new CustomerIdentificationDebtCardNum_Res();
		CustomerIdentificationDebtCardNum_Req reqObj = (CustomerIdentificationDebtCardNum_Req) inputParams
				.get("reqObj");

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

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getCardNumber()
						+ dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file : " + filePath);

				tpSystemLogger.info("Session ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ ". Processing dummy file : " + filePath);

				String responseString = "";
				
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				}
				
				//// Check Dummy Response whether Success/Failure
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				CustIdentificationDebtCardResponseData dummyResponse = objectMapper.readValue(responseString,
						CustIdentificationDebtCardResponseData.class);

				if (dummyResponse != null && dummyResponse.getResponseCode() != null) {
					responseMessage = Response.status(200).entity(responseString).build();
				} else {
					responseMessage = Response.status(400).entity(responseString).build();
				}

			} else {
				String xmlString = serviceProps.getProperty("requestPayload");

				SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.dateFormat);
				SimpleDateFormat timeFormat = new SimpleDateFormat(GlobalConstants.timeFormat);
				String sourceDate = dateFormat.format(new Date());
				String sourceTime = timeFormat.format(new Date());

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("card-number", reqObj.getCardNumber());
				inputElemets.put("user-id",serviceProps.getProperty("Euronet.header.user-id"));
				inputElemets.put("transaction-sequence-number", utilities.generateSequenceNumber());
				inputElemets.put("source-date", sourceDate);
				inputElemets.put("source-time", sourceTime);
				inputElemets.put("transmission-date", sourceDate);
				inputElemets.put("transmission-time", sourceTime);
				inputElemets.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());

				/// Form input Request
				/***
				 * Load parameter to payload request
				 * ***/
				
				String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);
				
				tpSystemLogger.info(" SESSION ID : " + reqObj.getSessionId() + " "
						+ utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);

				
				serviceProps.setProperty("requestBody", requestJson);
				reqObj.setRequestBody(requestJson);

				//// Token Generation
				String token = utilities.callOAuth2Token(serviceProps);

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
				 * then the helper class call endPoint api with header parameter and return response
				 * 
				 * (for helper class reuse purpose)
				 * ***/
				
				Map<String, String> httpHeaderParams = new HashMap<>();
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
				httpHeaderParams.put(HttpHeaders.ACCEPT, "application/json");
				httpHeaderParams.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());

				inputParams.put("httpHeaderParams", httpHeaderParams);

				///Call EndPoint API - Helper
				responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
			}

			if (responseMessage != null && responseMessage.getEntity() != null
					&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response : "
						+ responseMessage.getEntity().toString());

				tpSystemLogger
						.info(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
								+ ". Response : " + responseMessage.getEntity().toString());

				ObjectMapper objectMapper = new ObjectMapper();

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					CustIdentificationDebtCardResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationDebtCardResponseData.class);
					
					if (dataObjects != null) {
						if ("00".equals(dataObjects.getResponseCode())) {
							resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
//							resObj.setErrormessage(dataObjects != null && dataObjects.getResponseText() != null
//									? dataObjects.getResponseText()
//									: GlobalConstants.SUCCESS);
							
							resObj.setErrormessage(dataObjects != null && dataObjects.getResponseText() != null
									? GlobalConstants.SUCCESS
									: GlobalConstants.SUCCESS);

							resObj.setResponse(dataObjects);
						} else {
							resObj.setErrorcode(dataObjects != null
									&& dataObjects.getResponseCode() != null
											? dataObjects.getResponseCode()
											: GlobalConstants.FAILURECODE);
							resObj.setErrormessage(dataObjects != null
									&& dataObjects.getResponseText() != null
											? dataObjects.getResponseText()
											: GlobalConstants.FAILURE);
						}
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
					CustIdentificationDebtCardResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationDebtCardResponseData.class);

					if (dataObjects.getMessage() != null) {
						resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getMessage());
					} else if (dataObjects.getErrors() != null && dataObjects.getErrors().length > 0) {
						if(dataObjects.getErrors()[0].getCode() != null) {
							resObj.setErrorcode(dataObjects.getErrors()[0].getCode());
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getErrors()[0].getDetail());
						} else if(dataObjects.getErrors()[0].getStatus() != null) {
							resObj.setErrorcode(dataObjects.getErrors()[0].getStatus());
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getErrors()[0].getTitle());
						} else {
							resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
						}
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}

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
				 * 
				 * ***/
				
				inputParams.put("I_STATUS_CODE", resObj.getErrorcode());
				inputParams.put("I_STATUS_DESCRIPTION", resObj.getErrormessage());

				dbController.insertHostTransactions(inputParams);
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion completed ");
			} catch (Exception e) {
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
	public CustomerIdentificationDebtRelId_Res getCustomerIdentificationDebtRelId(Map<String, Object> inputParams) {
		CustomerIdentificationDebtRelId_Res resObj = new CustomerIdentificationDebtRelId_Res();
		CustomerIdentificationDebtRelId_Req reqObj = (CustomerIdentificationDebtRelId_Req) inputParams.get("reqObj");

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

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getRelId()
						+ dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file : " + filePath);
				tpSystemLogger.info("Session ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ ". Processing dummy file : " + filePath);

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				} 
				//// Check Dummy Response whether Success/Failure
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				CustIdentificationDebtRelIdResponseData dummyResponse = objectMapper.readValue(responseString,
						CustIdentificationDebtRelIdResponseData.class);

				if (dummyResponse != null && dummyResponse.getResponseCode() != null) {
					responseMessage = Response.status(200).entity(responseString).build();
				} else {
					responseMessage = Response.status(400).entity(responseString).build();
				}

			} else {

				String xmlString = serviceProps.getProperty("requestPayload");

				SimpleDateFormat dateFormat = new SimpleDateFormat(GlobalConstants.dateFormat);
				SimpleDateFormat timeFormat = new SimpleDateFormat(GlobalConstants.timeFormat);
				String sourceDate = dateFormat.format(new Date());
				String sourceTime = timeFormat.format(new Date());

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("relationship-id", reqObj.getRelId());
				inputElemets.put("user-id",serviceProps.getProperty("Euronet.header.user-id"));
				inputElemets.put("transaction-sequence-number", utilities.generateSequenceNumber());
				inputElemets.put("source-date", sourceDate.toString());
				inputElemets.put("source-time", sourceTime.toString());
				inputElemets.put("transmission-date", sourceDate.toString());
				inputElemets.put("transmission-time", sourceTime.toString());
				inputElemets.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());

				/***
				 * Load parameter to payload request
				 * ***/
				String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Request is: " + requestJson);

				tpSystemLogger.info("Session ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ ". Request is: " + requestJson);

				serviceProps.setProperty("requestBody", requestJson);
				reqObj.setRequestBody(requestJson);

				//// Token Generation
				String token = utilities.callOAuth2Token(serviceProps);

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
				 * then the helper class call endPoint api with header parameter and return response
				 * 
				 * (for helper class reuse purpose)
				 * ***/
				
				Map<String, String> httpHeaderParams = new HashMap<>();
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
				httpHeaderParams.put(HttpHeaders.ACCEPT, "application/json");
				httpHeaderParams.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());

				inputParams.put("httpHeaderParams", httpHeaderParams);

				///Call EndPoint API - Helper
				responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
			}

			if (responseMessage != null && responseMessage.getEntity() != null
					&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response : "
						+ responseMessage.getEntity().toString());

				tpSystemLogger
						.info(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
								+ ". Response : " + responseMessage.getEntity().toString());

				ObjectMapper objectMapper = new ObjectMapper();

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					CustIdentificationDebtRelIdResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationDebtRelIdResponseData.class);
					if (dataObjects != null) {
						if ("00".equals(dataObjects.getResponseCode())) {
							resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
//							resObj.setErrormessage(dataObjects != null && dataObjects.getResponseText() != null
//									? dataObjects.getResponseText()
//									: GlobalConstants.SUCCESS);
							
							resObj.setErrormessage(dataObjects != null && dataObjects.getResponseText() != null
									? GlobalConstants.SUCCESS
									: GlobalConstants.SUCCESS);

							resObj.setResponse(dataObjects);
						} else {
							resObj.setErrorcode(dataObjects != null && dataObjects.getResponseCode() != null
									? dataObjects.getResponseCode()
									: GlobalConstants.FAILURECODE);
							resObj.setErrormessage(dataObjects != null && dataObjects.getResponseText() != null
									? dataObjects.getResponseText()
									: GlobalConstants.FAILURE);
						}
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
					
					
				} else {
					CustIdentificationDebtRelIdResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationDebtRelIdResponseData.class);

					if (dataObjects.getMessage() != null) {
						resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getMessage());
					} else if (dataObjects.getErrors() != null && dataObjects.getErrors().length > 0) {
						if(dataObjects.getErrors()[0].getCode() != null) {
							resObj.setErrorcode(dataObjects.getErrors()[0].getCode());
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getErrors()[0].getDetail());
						} else if(dataObjects.getErrors()[0].getStatus() != null) {
							resObj.setErrorcode(dataObjects.getErrors()[0].getStatus());
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getErrors()[0].getTitle());
						} else {
							resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
						}
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
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
				 * 
				 * ***/
				
				inputParams.put("I_STATUS_CODE", resObj.getErrorcode());
				inputParams.put("I_STATUS_DESCRIPTION", resObj.getErrormessage());

				dbController.insertHostTransactions(inputParams);
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion completed ");
			} catch (Exception e) {
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
