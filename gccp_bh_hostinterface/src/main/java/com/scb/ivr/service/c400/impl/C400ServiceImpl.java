package com.scb.ivr.service.c400.impl;

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
import com.fasterxml.jackson.core.json.JsonReadFeature;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.scb.avaya.logging.CustomLogger;
import com.scb.ivr.api.hosthelper.HostHelper;
import com.scb.ivr.controller.DBController;
import com.scb.ivr.exception.CustomException;
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
import com.scb.ivr.model.c400.res.accbal.AccountBalanceResponseData;
import com.scb.ivr.model.c400.res.custidentifycardnum.CustIdentificationCardNumResponseData;
import com.scb.ivr.model.c400.res.custprofile.CustProductResponseData;
import com.scb.ivr.service.c400.C400Service;
import com.scb.ivr.util.Utilities;

@Service
public class C400ServiceImpl implements C400Service {

	@Value("${C400dummyFilePath}")
	private String dummyFilePath;

	@Value("${C400dummyFileExtn}")
	private String dummyFileExtn;

	@Autowired
	Utilities utilities;

	@Autowired
	HostHelper hostHelper;

	@Autowired
	DBController dbController;

	@Override
	public CustomerIdentificationCardNum_Res getCustomerIdentificationCardNum(Map<String, Object> inputParams) {
		CustomerIdentificationCardNum_Res resObj = new CustomerIdentificationCardNum_Res();
		CustomerIdentificationCardNum_Req reqObj = (CustomerIdentificationCardNum_Req) inputParams.get("reqObj");

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(serviceProps.getProperty("hostLoggerName"));

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());

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

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file: " + filePath);

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				}
				
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				CustIdentificationCardNumResponseData dataObjects = objectMapper
						.readValue(responseString, CustIdentificationCardNumResponseData.class);

				if (dataObjects != null && dataObjects.getData() != null && dataObjects.getData().size() > 0) {
					responseMessage = Response.status(200).entity(responseString).build();
				} else {
					responseMessage = Response.status(400).entity(responseString).build();
				}
				
			} else {
				String xmlString = serviceProps.getProperty("requestPayload");

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("cardNum", reqObj.getCardNumber());

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
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". The token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The token is null/empty");
				}
				serviceProps.setProperty("token", token);
				reqObj.setToken(token);

				///Endpoint Http Method 
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
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
				httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
				httpHeaderParams.put("X-Market", serviceProps.getProperty("requestCountry"));
				httpHeaderParams.put("Transaction-ID", serviceProps.getProperty("trackingID"));
				httpHeaderParams.put("channel", serviceProps.getProperty("channel"));

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

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					CustIdentificationCardNumResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationCardNumResponseData.class);
					if (dataObjects != null) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setResponse(dataObjects);
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
					CustIdentificationCardNumResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationCardNumResponseData.class);

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
	public CreditCardList_Res getCreditCardList(Map<String, Object> inputParams) {
		CreditCardList_Res resObj = new CreditCardList_Res();
		CreditCardList_Req reqObj = (CreditCardList_Req) inputParams.get("reqObj");

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(serviceProps.getProperty("hostLoggerName"));

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Request started @" + requestInitiatedTimestamp
				+ ", dummyFlag: " + reqObj.getDummyFlag());
		try {
			Response responseMessage = null;
			
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties.
			 * if the dummy flag is Y, got response from file required path and return to the ivr.
			 * otherwise call to endpoint API.
			 * 
			 ****/
			
			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getCustomer_id()
						+ dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file : " + filePath);

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				} 
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				CustIdentificationCardNumResponseData dataObjects = objectMapper.readValue(responseString,
						CustIdentificationCardNumResponseData.class);

				if (dataObjects != null && dataObjects.getData() != null && dataObjects.getData().size() > 0) {
					responseMessage = Response.status(200).entity(responseString).build();
				} else {
					responseMessage = Response.status(400).entity(responseString).build();
				}

			} else {
				String xmlString = serviceProps.getProperty("requestPayload");

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("customer-id", reqObj.getCustomer_id());

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
				 * then the helper class call endpoint api with header parameter and return response
				 * 
				 * (for helper class reuse purpose)
				 * ***/
				
				Map<String, String> httpHeaderParams = new HashMap<>();
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
				httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
				httpHeaderParams.put("X-Market", serviceProps.getProperty("requestCountry"));
				httpHeaderParams.put("Transaction-ID", serviceProps.getProperty("trackingID"));
				httpHeaderParams.put("channel", serviceProps.getProperty("channel"));

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

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					CustIdentificationCardNumResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationCardNumResponseData.class);
					if (dataObjects != null) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setResponse(dataObjects);
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
					CustIdentificationCardNumResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationCardNumResponseData.class);
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
	public CreditCardBalance_Res getCreditCardBalance(Map<String, Object> inputParams) {
		CreditCardBalance_Res resObj = new CreditCardBalance_Res();
		CreditCardBalance_Req reqObj = (CreditCardBalance_Req) inputParams.get("reqObj");

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(serviceProps.getProperty("hostLoggerName"));

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Request started @" + requestInitiatedTimestamp
				+ ", dummyFlag: " + reqObj.getDummyFlag());
		try {
			Response responseMessage = null;
			
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties.
			 * if the dummy flag is Y, got response from file required path and return to the ivr.
			 * otherwise call to endpoint API.
			 * 
			 ****/
			
			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getCardNumber()
						+ dummyFileExtn;

				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file Path " + filePath);

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				} 
				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				AccountBalanceResponseData dataObjects = objectMapper.readValue(responseString,
						AccountBalanceResponseData.class);

				if (dataObjects != null && dataObjects.getData() != null && dataObjects.getData().size() > 0) {
					responseMessage = Response.status(200).entity(responseString).build();
				} else {
					responseMessage = Response.status(400).entity(responseString).build();
				}

			} else {
				String xmlString = serviceProps.getProperty("requestPayload");

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("cardNum", reqObj.getCardNumber());

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
				 * then the helper class call endpoint api with header parameter and return response
				 * 
				 * (for helper class reuse purpose)
				 * ***/
				
				Map<String, String> httpHeaderParams = new HashMap<>();
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
				httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
				httpHeaderParams.put("X-Market", serviceProps.getProperty("requestCountry"));
				httpHeaderParams.put("Transaction-ID", serviceProps.getProperty("trackingID"));
				httpHeaderParams.put("channel", serviceProps.getProperty("channel"));

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

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				objectMapper.enable(JsonReadFeature.ALLOW_LEADING_ZEROS_FOR_NUMBERS.mappedFeature());


				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					AccountBalanceResponseData dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), AccountBalanceResponseData.class);
					
					if (dataObjects != null) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setResponse(dataObjects);
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
					AccountBalanceResponseData dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), AccountBalanceResponseData.class);
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
					utilities.getCurrentClassAndMethodName() + ". REQUEST ENDED ON:" + endTime + " , Time Duration : "
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
	public CustomerProfile_Res getCustomerProfile(Map<String, Object> inputParams) {
		CustomerProfile_Res resObj = new CustomerProfile_Res();
		CustomerProfile_Req reqObj = (CustomerProfile_Req) inputParams.get("reqObj");

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(serviceProps.getProperty("hostLoggerName"));

		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Request started @" + requestInitiatedTimestamp
				+ ", dummyFlag: " + reqObj.getDummyFlag());

		try {
			Response responseMessage = null;
			
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties.
			 * if the dummy flag is Y, got response from file required path and return to the ivr.
			 * otherwise call to endpoint API.
			 * 
			 ****/
			
			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getRelId()
						+ dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file: " + filePath);

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
				inputElemets.put("customer-id", reqObj.getRelId());

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
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". The token is null/empty");

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
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
				httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
				httpHeaderParams.put("X-Market", serviceProps.getProperty("requestCountry"));
				httpHeaderParams.put("Transaction-ID", serviceProps.getProperty("trackingID"));
				httpHeaderParams.put("channel", serviceProps.getProperty("channel"));

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

				ObjectMapper objectMapper = new ObjectMapper();
				objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				
				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {
					CustProductResponseData dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
							CustProductResponseData.class);
					if (dataObjects != null) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setCustProduct(dataObjects);
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
					CustIdentificationCardNumResponseData dataObjects = objectMapper.readValue(
							responseMessage.getEntity().toString(), CustIdentificationCardNumResponseData.class);

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
