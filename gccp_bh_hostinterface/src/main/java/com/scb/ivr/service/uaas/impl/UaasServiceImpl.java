package com.scb.ivr.service.uaas.impl;

import java.io.IOException;
import java.net.Inet4Address;
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

import org.apache.commons.codec.binary.Base64;
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
import com.scb.ivr.model.uaas.res.generateapin.GenerateAPINResponseData;
import com.scb.ivr.model.uaas.res.generateccpin.GenerateCCPINResponseData;
import com.scb.ivr.model.uaas.res.generateotp.GenerateOTPVO;
import com.scb.ivr.model.uaas.res.generateotp.GenerateOtpResponseData;
import com.scb.ivr.model.uaas.res.generateotp.RandomChallengeVO;
import com.scb.ivr.model.uaas.res.validateapin.ValidateAPINResponseData;
import com.scb.ivr.model.uaas.res.validateccpin.ValidateCCPINResponseData;
import com.scb.ivr.model.uaas.res.validateotp.ValidateOTPResponseData;
import com.scb.ivr.service.generalvo.CommonInput;
import com.scb.ivr.service.uaas.UaasService;
import com.scb.ivr.util.GetConfigProperties;
import com.scb.ivr.util.Utilities;
	
@Service
public class UaasServiceImpl implements UaasService {

	@Value("${UAASdummyFilePath}")
	private String dummyFilePath;

	@Value("${UAASdummyFileExtn}")
	private String dummyFileExtn;

	@Autowired
	Utilities utilities;

	@Autowired
	HostHelper hostHelper;

	@Autowired
	DBController dbController;
	
	@Autowired
	GetConfigProperties getConfigProperties;

	@Override
	public GenerateOTP_Res generateOTP(Map<String, Object> inputParams) {
		GenerateOTP_Res resObj = new GenerateOTP_Res();
		GenerateOTP_Req reqObj = (GenerateOTP_Req) inputParams.get("reqObj");

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
			Response responseMessageRandom = null;
			
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties.
			 * if the dummy flag is Y, got response from file required path and return to the ivr.
			 * otherwise call to endPoint API.
			 * 
			 ****/
			
			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {
				/// Check Generate OTP File Path
				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getRelId()
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

				/// Check Generate Random Challenge File Path
				String serviceNameRandom = "getRandomChallenge";
				String filePathRandom = dummyFilePath + serviceNameRandom + "/" + reqObj.getRelId() + dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing Random dummy file : "
						+ filePathRandom);
				
				String responseStringRandom = "";
				try {
					responseStringRandom = new String(Files.readAllBytes(Paths.get(filePathRandom)));
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found (RandomChallenge) " + e.getMessage());
				} 
				
				responseMessageRandom = Response.status(200).entity(responseStringRandom).build();

			} else {		
				
				////GET RANDOM CHALLENGE RESPONSE
				responseMessageRandom = getRandomChallengeResponse(inputParams, reqObj.getRelId());
			}
			
			if (responseMessageRandom != null && responseMessageRandom.getEntity() != null
					&& !responseMessageRandom.getEntity().toString().equalsIgnoreCase("")) {

				sessionLogger.info(utilities.getCurrentClassAndMethodName()
						+ ". Response (Random Challenge API) : " + responseMessageRandom.getEntity().toString());

				tpSystemLogger
				.info(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
						+ ". Response : " + responseMessageRandom.getEntity().toString());
				
				ObjectMapper objectMapperRandom = new ObjectMapper();
				objectMapperRandom.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				RandomChallengeVO randomChallengeVo = objectMapperRandom.readValue(responseMessageRandom.getEntity().toString(),
						RandomChallengeVO.class);

				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				if ((responseMessageRandom.getStatus() == 200 || responseMessageRandom.getStatus() == 201)
						&& randomChallengeVo.getErrorMessage() != null
						&& "".equalsIgnoreCase(randomChallengeVo.getErrorMessage())) {

					////CALL GENERATE OTP SERVICE,IF DUMMY FLG IS N
					if (!"Y".equalsIgnoreCase(reqObj.getDummyFlag())) {
						responseMessage = getGenerateOTPResponse(inputParams);
					}		
					
					if (responseMessage != null && responseMessage.getEntity() != null
							&& !responseMessage.getEntity().toString().trim().equalsIgnoreCase("")) {
						
						sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response : "
								+ responseMessage.getEntity().toString());

						tpSystemLogger
								.info(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
										+ ". Response : " + responseMessage.getEntity().toString());
						
						ObjectMapper objectMapper = new ObjectMapper();
						objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
						GenerateOTPVO otpvo = objectMapper.readValue(responseMessage.getEntity().toString(),
								GenerateOTPVO.class);
						
						/***
						 * check status code from endPoint API response.
						 * 
						 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
						 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
						 * 
						 * ****/
						
						if ((responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201)
								&& otpvo.getErrorMessage() != null
								&& "".equalsIgnoreCase(otpvo.getErrorMessage())) {

							resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
							resObj.setErrormessage(GlobalConstants.SUCCESS);

							GenerateOtpResponseData responseData = new GenerateOtpResponseData();

							RandomChallengeVO randomChallengeVO = objectMapper
									.readValue(responseMessageRandom.getEntity().toString(), RandomChallengeVO.class);

							// base64Challenge
							responseData.setEncryptedBlock(randomChallengeVO.getBase64Challenge());
							// modulus
							responseData.setCode(randomChallengeVO.getModulus());
							// exponent
							responseData.setExponent(randomChallengeVO.getExponent());
							// key index
							responseData.setKeyIndex(randomChallengeVO.getKeyIndex());
							// sn
							responseData.setOtpSn(otpvo.getOtpSn());
							resObj.setOtpResponse(responseData);

							
						} else {
							if (otpvo.getStatusCode() != null) {
								resObj.setErrorcode(otpvo.getStatusCode());
								resObj.setErrormessage(otpvo.getErrorMessage());
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
					
					
				} else {
					if (randomChallengeVo.getStatusCode() != null) {
						resObj.setErrorcode(randomChallengeVo.getStatusCode());
						resObj.setErrormessage(randomChallengeVo.getErrorMessage());
					} else {
						resObj.setErrorcode(String.valueOf(responseMessageRandom.getStatus()));
						resObj.setErrormessage(GlobalConstants.FAILURE);
					}
				}

			} else {
				sessionLogger.info(
						utilities.getCurrentClassAndMethodName() + ". Empty Response in Random Challenge API");

				tpSystemLogger.info(" SESSION ID : " + reqObj.getSessionId() + " "
						+ utilities.getCurrentClassAndMethodName()
						+ ". Empty Response in Random Challenge API");

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
	
	private Response getGenerateOTPResponse(Map<String, Object> inputParams) throws Exception {
		
		Response responseMessage = null;
		
		GenerateOTP_Req reqObj = (GenerateOTP_Req) inputParams.get("reqObj");
		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(serviceProps.getProperty("hostLoggerName"));

		
		//// Generate OTP Call
		String xmlString = serviceProps.getProperty("requestPayload");

		/// Form REquest
		Map<String, Object> inputElemets = new HashMap<>();
		inputElemets.put("appId", serviceProps.getProperty("appId"));
		inputElemets.put("groupId", serviceProps.getProperty("groupId"));
		inputElemets.put("userId", reqObj.getRelId());
		String clientRequestId = (serviceProps.getProperty("trackingID")).substring(0,40);
		inputElemets.put("clientRequestId", clientRequestId);
		inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));

		inputElemets.put("mobile", reqObj.getMobileNumber());
		//SimpleDateFormat messageTemplateDate = utilities.getDateFormat(GlobalConstants.DateTimeFormat);
		//String msgTemp = messageTemplateDate.format(new Date()) + " " + serviceProps.getProperty("msgTemplate");
		String msgTemp =serviceProps.getProperty("msgTemplate");

		////Expiry OTP TimeSetup
		msgTemp = utilities.getOTPExpiryMsgTemplate(msgTemp);
		byte[] encodedmessageTemplate = Base64.encodeBase64(msgTemp.getBytes("UTF-8"));
		
		//inputElemets.put("msgTemplate", serviceProps.getProperty("msgTemplate"));
		if("Y".equalsIgnoreCase(serviceProps.getProperty("isEncoded"))){
			inputElemets.put("msgTemplate", new String(encodedmessageTemplate));
		}else {
			inputElemets.put("msgTemplate", msgTemp);
		}
		
		inputElemets.put("isEncoded", serviceProps.getProperty("isEncoded"));
		//inputElemets.put("resendFlag", serviceProps.getProperty("resendFlag"));

		inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
		inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

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
		String userName = serviceProps.getProperty("uaasUserName");
		String encPassword = serviceProps.getProperty("uaasPassword");
		String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
		
		String token = utilities.callBasicAuthToken(userName, password);

		/// Token Validation
		if (token == null || "".equals(token.trim())) {
			sessionLogger
					.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

			throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
					GlobalConstants.FAILURE + ". The required token is null/empty");
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
		httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
		//httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
		inputParams.put("httpHeaderParams", httpHeaderParams);

		///Call EndPoint API
		responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
		
		return responseMessage;
	}

	private Response getRandomChallengeResponse(Map<String, Object> inputParams, String userId) throws Exception {
		RandomChallengeVO randomChallengeVO = new RandomChallengeVO();
		Response responseMessage = null;

		CommonInput reqObj = (CommonInput) inputParams.get("reqObj");
		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);

		try {

			String serviceNameN = "getRandomChallenge";
			String trackingIDN = utilities.generateTrackingId();

			/// LOAD PROPERTIES
			Properties properties = new Properties();

			properties.put("sessionId", sessionId);
			properties.put("serviceName", serviceNameN);
			properties.put("trackingID", trackingIDN);
			properties.put("tpSystem", serviceProps.getProperty("tpSystem"));
			properties.put("hostLoggerName", serviceProps.getProperty("hostLoggerName"));

			/// LOAD SERVICE PROPERTIES
			serviceProps = new Properties();
			serviceProps = getConfigProperties.getServiceConfig(properties);

			properties.clear();

			if (serviceProps.getProperty("ERROR_CODE") != null
					&& GlobalConstants.FAILURECODE.equals(serviceProps.getProperty("ERROR_CODE"))) {
				sessionLogger.info(utilities.getCurrentClassAndMethodName()
						+ ". Error found in service properties (Random Challenge API)");

				randomChallengeVO.setStatusCode(GlobalConstants.FAILURECODE);
				randomChallengeVO.setErrorMessage(
						GlobalConstants.FAILURE + ". Error found in service properties (Random Challenge API)");
				return Response.status(400).entity(randomChallengeVO).build();
			}

			inputParams = new HashMap<String, Object>();
			inputParams.put("serviceProperties", serviceProps);
			inputParams.put("reqObj", reqObj);

			//// Generate Random Challenge Call
			String xmlString = serviceProps.getProperty("requestPayload");

			/// Form Request
			Map<String, Object> inputElemets = new HashMap<>();
			inputElemets.put("appId", serviceProps.getProperty("appId"));
			inputElemets.put("userId", userId);
			inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));
			inputElemets.put("groupId", serviceProps.getProperty("groupId"));
			String clientRequestId = (serviceProps.getProperty("trackingID")).substring(0,40);
			inputElemets.put("clientRequestId", clientRequestId);

			inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
			inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

			/***
			 * Load parameter to payload request
			 * ***/
			
			String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);

			sessionLogger.info(
					utilities.getCurrentClassAndMethodName() + ". Request is (Random Challenge API) : " + requestJson);

			// tpSystemLogger.info("Session ID : " + sessionId + " " +
			// utilities.getCurrentClassAndMethodName()
			// + ". Random Challenge Request is: " + requestJson);

			serviceProps.setProperty("requestBody", requestJson);
			reqObj.setRequestBody(requestJson);

			//// Token Generation
			String userName = serviceProps.getProperty("uaasUserName");
			String encPassword = serviceProps.getProperty("uaasPassword");
			String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
			String token = utilities.callBasicAuthToken(userName, password);

			/// Token Validation
			if (token == null || "".equals(token.trim())) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ ". The required token is null/empty (Random Challenge API)");

				randomChallengeVO.setStatusCode(GlobalConstants.ERRORCODE_TOKEN_GEN_700014);
				randomChallengeVO.setErrorMessage(
						GlobalConstants.FAILURE + ". The required token is null/empty (Random Challenge API)");
				return Response.status(400).entity(randomChallengeVO).build();
				
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
			httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
			//httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
			inputParams.put("httpHeaderParams", httpHeaderParams);

			///Call EndPoint API
			responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
						
			/*if (responseMessage != null && responseMessage.getEntity() != null
					&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

				ObjectMapper objectMapperRandom = new ObjectMapper();
				objectMapperRandom.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
				RandomChallengeVO randomChallengeVo = objectMapperRandom.readValue(responseMessageRandom.getEntity().toString(),
						RandomChallengeVO.class);
				
				if ((responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201)
						&& randomChallengeVO.getErrorMessage() != null
						&& "".equalsIgnoreCase(randomChallengeVO.getErrorMessage())) {
				} else {
					if (randomChallengeVo.getStatusCode() != null) {
						resObj.setErrorcode(randomChallengeVo.getStatusCode());
						resObj.setErrormessage(randomChallengeVo.getErrorMessage());
					} else {
						resObj.setErrorcode(String.valueOf(responseMessageRandom.getStatus()));
						resObj.setErrormessage(GlobalConstants.FAILURE);
					}
				}
				
			} else {
				sessionLogger.info(
						utilities.getCurrentClassAndMethodName() + ". Empty Response in Random Challenge API");

				tpSystemLogger.info(" SESSION ID : " + reqObj.getSessionId() + " "
						+ utilities.getCurrentClassAndMethodName() + ". Empty Response in Random Challenge API");

				throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
						"Response is Null, Setting Failure code");
			}*/


		} catch (Exception e) {
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Exception Occured when Random challenge API calling " + e.getMessage());
		}
		return responseMessage;
	}

	@Override
	public ValidateOTP_Res validateOTP(Map<String, Object> inputParams) {
		ValidateOTP_Res resObj = new ValidateOTP_Res();
		ValidateOTP_Req reqObj = (ValidateOTP_Req) inputParams.get("reqObj");

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

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getRelID()
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
				inputElemets.put("appId", serviceProps.getProperty("appId"));
				inputElemets.put("groupId", serviceProps.getProperty("groupId"));
				inputElemets.put("userId", reqObj.getRelID());
				String clientRequestId = (serviceProps.getProperty("trackingID")).substring(0,40);
				inputElemets.put("clientRequestId", clientRequestId);
				inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));

				inputElemets.put("otpSn", reqObj.getOtpSn());
				/// RSA Data encryption
				inputElemets.put("encOtp", utilities.encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(),
						reqObj.getEncryptedBlock(), reqObj.getOtp(), sessionId));

				inputElemets.put("purpose", serviceProps.getProperty("purpose"));
				inputElemets.put("keyIndex", reqObj.getKeyIndex());

				inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
				inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

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
				String userName = serviceProps.getProperty("uaasUserName");
				String encPassword = serviceProps.getProperty("uaasPassword");
				String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
				String token = utilities.callBasicAuthToken(userName, password);

				/// Token Validation
				if (token == null || "".equals(token.trim())) {
					sessionLogger
							.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The required token is null/empty");
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
				//httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
				//httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");

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

				ValidateOTPResponseData dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
						ValidateOTPResponseData.class);
				
				/***
				 * check status code from endPoint API response.
				 * 
				 * if the status code is 200 or 201, received success response. mapping the success code and return to IVR.
				 * otherwise error message received from endPoint. mapping that error message data and return to IVR.
				 * 
				 * ****/
				if ((responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201)
						&& dataObjects != null
						&& dataObjects.getErrorMessage() != null
						&& "".equalsIgnoreCase(dataObjects.getErrorMessage())) {
					
//					ValidateOTPResponseData dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
//							ValidateOTPResponseData.class);
							
					if (dataObjects != null) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setResponse(dataObjects);
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
//					ValidateOTPResponseData dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
//							ValidateOTPResponseData.class);
					
					if (dataObjects != null && dataObjects.getStatusCode() != null) {
						resObj.setErrorcode(dataObjects.getStatusCode());
						resObj.setErrormessage(dataObjects.getErrorMessage());
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
	public GenerateAPIN_Res generateAPIN(Map<String, Object> inputParams) {
		GenerateAPIN_Res resObj = new GenerateAPIN_Res();
		GenerateAPIN_Req reqObj = (GenerateAPIN_Req) inputParams.get("reqObj");

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

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getCardNo()
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
				
			    ////GET RANDOM CHALLENGE RESPONSE
				
				
				Response responseMessageRandom = getRandomChallengeResponse(inputParams, reqObj.getUserId());
				
				
				if (responseMessageRandom != null && responseMessageRandom.getEntity() != null
						&& !responseMessageRandom.getEntity().toString().equalsIgnoreCase("")) {

					sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Random challenge Response : "
							+ responseMessageRandom.getEntity().toString());

					tpSystemLogger
							.info(" SESSION ID : " + reqObj.getSessionId() + " " + utilities.getCurrentClassAndMethodName()
									+ ". Random challenge Response : " + responseMessageRandom.getEntity().toString());
					
					ObjectMapper objectMapperRandom = new ObjectMapper();
					objectMapperRandom.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
					RandomChallengeVO randomChallengeVo = objectMapperRandom.readValue(responseMessageRandom.getEntity().toString(),
							RandomChallengeVO.class);
					
					if ((responseMessageRandom.getStatus() == 200 || responseMessageRandom.getStatus() == 201)
							&& randomChallengeVo.getErrorMessage() != null
							&& "".equalsIgnoreCase(randomChallengeVo.getErrorMessage())) {
						
						
						
						/// Get Card Token
						Properties cardTokenProperties = getConfigProperties.getTokenParams(GlobalConstants.TPSystem_Euronet,
								reqObj.getServiceName());

						String cardToken = utilities.callOAuth2Token(cardTokenProperties);

						/// Card Token Validation
						if (cardToken == null || "".equals(cardToken.trim())) {
							sessionLogger.debug(
									utilities.getCurrentClassAndMethodName() + ". The required Card token is null/empty");

							throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
									GlobalConstants.FAILURE + ". The required Card token is null/empty");
						}

						
//						reqObj.setCardToken(cardToken);

						/// Load request
						String xmlString = serviceProps.getProperty("requestPayload");
						


						String sourceDate = new SimpleDateFormat(GlobalConstants.dateFormat).format(new Date());
						String sourceTime = new SimpleDateFormat(GlobalConstants.timeFormat).format(new Date());

						Map<String, Object> inputElemets = new HashMap<>();
						inputElemets.put("appId", serviceProps.getProperty("appId"));
						inputElemets.put("groupId", serviceProps.getProperty("groupId"));
						inputElemets.put("userId", reqObj.getUserId());
						inputElemets.put("clientRequestId", reqObj.getTrackingId().substring(0,40));
						inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));

						inputElemets.put("encCardPin", utilities.generateAPINEncCardPin(reqObj, randomChallengeVo));
						inputElemets.put("encCardInfo", utilities.generateAPINEncCardInfo(reqObj, randomChallengeVo, cardToken));
						inputElemets.put("txnRefNo", utilities.generateTxnRefNo());

//						inputElemets.put("otpSn", reqObj.getOtpSn());
//						inputElemets.put("encOtp", utilities.encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(),
//								reqObj.getEncryptedBlock(), reqObj.getEncOtp(), sessionId));
						
						inputElemets.put("purpose", serviceProps.getProperty("purpose"));
						inputElemets.put("keyIndex", randomChallengeVo.getKeyIndex());

						inputElemets.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());
						
						inputElemets.put("transaction-sequence-number", utilities.generateTxnRefNo());
						inputElemets.put("source-date", sourceDate);
						inputElemets.put("source-time", sourceTime);
						inputElemets.put("transmission-date", sourceDate);
						inputElemets.put("transmission-time", sourceTime);
						inputElemets.put("user-id", reqObj.getUserId());

						inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
						inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

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
						String userName = serviceProps.getProperty("uaasUserName");
						String encPassword = serviceProps.getProperty("uaasPassword");
						String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
						String token = utilities.callBasicAuthToken(userName, password);

						/// Token Validation
						if (token == null || "".equals(token.trim())) {
							sessionLogger
									.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

							throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
									GlobalConstants.FAILURE + ". The required token is null/empty");
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
						httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
						//httpHeaderParams.put(HttpHeaders.ACCEPT, "application/json");

						inputParams.put("httpHeaderParams", httpHeaderParams);

						///Call EndPoint API
						responseMessage = hostHelper.invokeHttpsWebservice(inputParams);


					} else {

						if (randomChallengeVo.getStatusCode() != null) {
							resObj.setErrorcode(randomChallengeVo.getStatusCode());
							resObj.setErrormessage(randomChallengeVo.getErrorMessage());
						} else {
							resObj.setErrorcode(String.valueOf(responseMessageRandom.getStatus()));
							resObj.setErrormessage(GlobalConstants.FAILURE);
						}

					}

				} else {
					sessionLogger.info(
							utilities.getCurrentClassAndMethodName() + ". Empty Response in Random Challenge API");

					tpSystemLogger.info(" SESSION ID : " + reqObj.getSessionId() + " "
							+ utilities.getCurrentClassAndMethodName() + ". Empty Response in Random Challenge API");

					throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
							"Response is Null, Setting Failure code");
				}
				
				
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
					GenerateAPINResponseData dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), GenerateAPINResponseData.class);
					if (dataObjects != null) {
						
						if(dataObjects.getStatusCode() != null ) {
							
							if("100".equalsIgnoreCase(dataObjects.getStatusCode())) {
								resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
								resObj.setErrormessage(GlobalConstants.SUCCESS);
								resObj.setResponse(dataObjects);
							}else {
								if(dataObjects.getPinResetInfo() != null && dataObjects.getPinResetInfo().geteNResponseCode() != null) {
									resObj.setErrorcode(dataObjects.getPinResetInfo().geteNResponseCode());
									resObj.setErrormessage(dataObjects.getPinResetInfo().geteNResponseDesc());
								}else {
									resObj.setErrorcode(dataObjects.getStatusCode());
									resObj.setErrormessage(dataObjects.getErrorMessage());
								}
								resObj.setResponse(dataObjects);
							}
							
						}else {
							resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
						}
						
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
					GenerateAPINResponseData dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), GenerateAPINResponseData.class);
					
					if (dataObjects.getStatusCode() != null) {
						if(dataObjects.getPinResetInfo() != null && dataObjects.getPinResetInfo().geteNResponseCode() != null) {
							resObj.setErrorcode(dataObjects.getPinResetInfo().geteNResponseCode());
							resObj.setErrormessage(dataObjects.getPinResetInfo().geteNResponseDesc());
						}else {
							resObj.setErrorcode(dataObjects.getStatusCode());
							resObj.setErrormessage(dataObjects.getErrorMessage());
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
	public ValidateAPIN_Res validateAPIN(Map<String, Object> inputParams) {
		ValidateAPIN_Res resObj = new ValidateAPIN_Res();
		ValidateAPIN_Req reqObj = (ValidateAPIN_Req) inputParams.get("reqObj");

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

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getCardNo()
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
				
				 ////GET RANDOM CHALLENGE RESPONSE
				Response responseMessageRandom = getRandomChallengeResponse(inputParams, reqObj.getUserId());

				if (responseMessageRandom != null && responseMessageRandom.getEntity() != null
						&& !responseMessageRandom.getEntity().toString().equalsIgnoreCase("")) {

					ObjectMapper objectMapperRandom = new ObjectMapper();
					objectMapperRandom.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
					RandomChallengeVO randomChallengeVo = objectMapperRandom.readValue(responseMessageRandom.getEntity().toString(),
							RandomChallengeVO.class);
					
					if ((responseMessageRandom.getStatus() == 200 || responseMessageRandom.getStatus() == 201)
							&& randomChallengeVo.getErrorMessage() != null
							&& "".equalsIgnoreCase(randomChallengeVo.getErrorMessage())) {
	
						/// Get Card Token
						Properties cardTokenProperties = getConfigProperties.getTokenParams(GlobalConstants.TPSystem_Euronet,
								reqObj.getServiceName());

						String cardToken = utilities.callOAuth2Token(cardTokenProperties);

						/// Card Token Validation
						if (cardToken == null || "".equals(cardToken.trim())) {
							sessionLogger.debug(
									utilities.getCurrentClassAndMethodName() + ". The required Card token is null/empty");

							throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
									GlobalConstants.FAILURE + ". The required Card token is null/empty");
						}

						/// Load request
						String xmlString = serviceProps.getProperty("requestPayload");

						String sourceDate = new SimpleDateFormat(GlobalConstants.dateFormat).format(new Date());
						String sourceTime = new SimpleDateFormat(GlobalConstants.timeFormat).format(new Date());

						Map<String, Object> inputElemets = new HashMap<>();

						inputElemets.put("appId", serviceProps.getProperty("appId"));
						inputElemets.put("groupId", serviceProps.getProperty("groupId"));
						inputElemets.put("userId", reqObj.getUserId());
						inputElemets.put("clientRequestId", reqObj.getTrackingId().substring(0,40));
						inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));

						inputElemets.put("encCardPin", utilities.validateAPINEncCardPin(reqObj, randomChallengeVo));
						inputElemets.put("encCardInfo", utilities.validateAPINEncCardInfo(reqObj, randomChallengeVo, cardToken));
						inputElemets.put("txnRefNo", utilities.generateTxnRefNo());

						inputElemets.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());

						inputElemets.put("transaction-sequence-number", utilities.generateTxnRefNo());
						inputElemets.put("source-date", sourceDate);
						inputElemets.put("source-time", sourceTime);
						inputElemets.put("transmission-date", sourceDate);
						inputElemets.put("transmission-time", sourceTime);
						

						inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
						inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

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
						String userName = serviceProps.getProperty("uaasUserName");
						String encPassword = serviceProps.getProperty("uaasPassword");
						String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
						String token = utilities.callBasicAuthToken(userName, password);

						/// Token Validation
						if (token == null || "".equals(token.trim())) {
							sessionLogger
									.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

							throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
									GlobalConstants.FAILURE + ". The required token is null/empty");
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
						httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/json");
						//httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");

						inputParams.put("httpHeaderParams", httpHeaderParams);

						///Call EndPoint API
						responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
					} else {

						if (randomChallengeVo.getStatusCode() != null) {
							resObj.setErrorcode(randomChallengeVo.getStatusCode());
							resObj.setErrormessage(randomChallengeVo.getErrorMessage());
						} else {
							resObj.setErrorcode(String.valueOf(responseMessageRandom.getStatus()));
							resObj.setErrormessage(GlobalConstants.FAILURE);
						}

					}

				} else {
					sessionLogger.info(
							utilities.getCurrentClassAndMethodName() + ". Empty Response in Random Challenge API");

					tpSystemLogger.info(" SESSION ID : " + reqObj.getSessionId() + " "
							+ utilities.getCurrentClassAndMethodName() + ". Empty Response in Random Challenge API");

					throw new CustomException(GlobalConstants.ERRORCODE_RESPONSE_IS_EMPTY_700015,
							"Response is Null, Setting Failure code");
				}
				

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
					ValidateAPINResponseData dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), ValidateAPINResponseData.class);
					if (dataObjects != null) {
						
						if (dataObjects.getStatusCode() != null) {

							if ("100".equalsIgnoreCase(dataObjects.getStatusCode())) {
								resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
								resObj.setErrormessage(GlobalConstants.SUCCESS);
								resObj.setResponse(dataObjects);
							} else {
								if(dataObjects.getPinAuthInfo() != null && dataObjects.getPinAuthInfo().geteNResponseCode() != null) {
									resObj.setErrorcode(dataObjects.getPinAuthInfo().geteNResponseCode());
									resObj.setErrormessage(dataObjects.getPinAuthInfo().geteNResponseDesc());
								}else {
									resObj.setErrorcode(dataObjects.getStatusCode());
									resObj.setErrormessage(dataObjects.getErrorMessage());
								}
								resObj.setResponse(dataObjects);
							}

						} else {
							resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
						}
					
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}
				} else {
					ValidateAPINResponseData dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), ValidateAPINResponseData.class);
					
					if (dataObjects.getStatusCode() != null) {
						
						if(dataObjects.getPinAuthInfo() != null && dataObjects.getPinAuthInfo().geteNResponseCode() != null) {
							resObj.setErrorcode(dataObjects.getPinAuthInfo().geteNResponseCode());
							resObj.setErrormessage(dataObjects.getPinAuthInfo().geteNResponseDesc());
						}else {
							resObj.setErrorcode(dataObjects.getStatusCode());
							resObj.setErrormessage(dataObjects.getErrorMessage());
						}
						resObj.setResponse(dataObjects);
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
	public GenerateCCPIN_Res generateCCPIN(Map<String, Object> inputParams) {
		GenerateCCPIN_Res resObj = new GenerateCCPIN_Res();
		GenerateCCPIN_Req reqObj = (GenerateCCPIN_Req) inputParams.get("reqObj");

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

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getCardNo()
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

				/// Get Card Token
				Properties cardTokenProperties = getConfigProperties.getTokenParams(GlobalConstants.TPSystem_C400,
						reqObj.getServiceName());

				String cardToken = utilities.callOAuth2Token(cardTokenProperties);

				/// Card Token Validation
				if (cardToken == null || "".equals(cardToken.trim())) {
					sessionLogger.debug(
							utilities.getCurrentClassAndMethodName() + ". The required Card token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The required Card token is null/empty");
				}

				reqObj.setCardToken(cardToken);

				/// Load request
				String xmlString = serviceProps.getProperty("requestPayload");

				String sourceDate = new SimpleDateFormat(GlobalConstants.dateFormat).format(new Date());
				String sourceTime = new SimpleDateFormat(GlobalConstants.timeFormat).format(new Date());

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("appId", serviceProps.getProperty("appId"));
				inputElemets.put("groupId", serviceProps.getProperty("groupId"));
				inputElemets.put("userId", reqObj.getUserId());
				inputElemets.put("clientRequestId", reqObj.getTrackingId());
				inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));

				inputElemets.put("encCardPin", utilities.generateCCPINEncCardPin(reqObj));
				inputElemets.put("encCardInfo", utilities.generateCCPINEncCardInfo(reqObj));
				inputElemets.put("txnRefNo", utilities.generateTxnRefNo());

				inputElemets.put("otpSn", reqObj.getOtpSn());
				inputElemets.put("encOtp", utilities.encryptDataRSA(reqObj.getExponent(), reqObj.getModulus(),
						reqObj.getEncryptedBlock(), reqObj.getEncOtp(), sessionId));
				inputElemets.put("purpose", serviceProps.getProperty("purpose"));
				// inputElemets.put("keyIndex", reqObj.getKeyIndex());

				inputElemets.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());
				inputElemets.put("transaction-sequence-number", reqObj.getTrackingId());
				inputElemets.put("source-date", sourceDate);
				inputElemets.put("source-time", sourceTime);
				inputElemets.put("transmission-date", sourceDate);
				inputElemets.put("transmission-time", sourceTime);
				inputElemets.put("user-id", reqObj.getUserId());

				inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
				inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

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
				String userName = serviceProps.getProperty("uaasUserName");
				String encPassword = serviceProps.getProperty("uaasPassword");
				String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
				String token = utilities.callBasicAuthToken(userName, password);

				/// Token Validation
				if (token == null || "".equals(token.trim())) {
					sessionLogger
							.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The required token is null/empty");
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
					GenerateCCPINResponseData dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), GenerateCCPINResponseData.class);
					if (dataObjects != null) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setResponse(dataObjects);
					} else {
						resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
						resObj.setErrormessage(GlobalConstants.FAILURE);
					}
				} else {
					resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
					resObj.setErrormessage(GlobalConstants.FAILURE);
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
	public ValidateCCPIN_Res validateCCPIN(Map<String, Object> inputParams) {
		ValidateCCPIN_Res resObj = new ValidateCCPIN_Res();
		ValidateCCPIN_Req reqObj = (ValidateCCPIN_Req) inputParams.get("reqObj");

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

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getCardNo()
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

				/// Get Card Token
				Properties cardTokenProperties = getConfigProperties.getTokenParams(GlobalConstants.TPSystem_C400,
						reqObj.getServiceName());

				String cardToken = utilities.callOAuth2Token(cardTokenProperties);

				/// Card Token Validation
				if (cardToken == null || "".equals(cardToken.trim())) {
					sessionLogger.debug(
							utilities.getCurrentClassAndMethodName() + ". The required Card token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The required Card token is null/empty");
				}

				reqObj.setCardToken(cardToken);

				/// Load request
				String xmlString = serviceProps.getProperty("requestPayload");

				String sourceDate = new SimpleDateFormat(GlobalConstants.dateFormat).format(new Date());
				String sourceTime = new SimpleDateFormat(GlobalConstants.timeFormat).format(new Date());

				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("appId", serviceProps.getProperty("appId"));
				inputElemets.put("groupId", serviceProps.getProperty("groupId"));
				inputElemets.put("userId", reqObj.getUserId());
				inputElemets.put("clientRequestId", reqObj.getTrackingId());
				inputElemets.put("apiKey", serviceProps.getProperty("apiKey"));

				inputElemets.put("encCardPin", utilities.validateCCPINEncCardPin(reqObj));
				inputElemets.put("encCardInfo", utilities.validateCCPINEncCardInfo(reqObj));
				inputElemets.put("txnRefNo", utilities.generateTxnRefNo());

				inputElemets.put("x-market", serviceProps.getProperty("requestCountry").toUpperCase());
				inputElemets.put("transaction-sequence-number", reqObj.getTrackingId());
				inputElemets.put("source-date", sourceDate);
				inputElemets.put("source-time", sourceTime);
				inputElemets.put("transmission-date", sourceDate);
				inputElemets.put("transmission-time", sourceTime);

				inputElemets.put("clientIP", Inet4Address.getLocalHost().getHostAddress());
				inputElemets.put("remoteIP", Inet4Address.getLocalHost().getHostAddress());

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
				String userName = serviceProps.getProperty("uaasUserName");
				String encPassword = serviceProps.getProperty("uaasPassword");
				String password = new String(java.util.Base64.getDecoder().decode(encPassword.getBytes()));
				String token = utilities.callBasicAuthToken(userName, password);

				/// Token Validation
				if (token == null || "".equals(token.trim())) {
					sessionLogger
							.debug(utilities.getCurrentClassAndMethodName() + ". The required token is null/empty");

					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
							GlobalConstants.FAILURE + ". The required token is null/empty");
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
					ValidateCCPINResponseData dataObjects = objectMapper
							.readValue(responseMessage.getEntity().toString(), ValidateCCPINResponseData.class);
					if (dataObjects != null) {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setResponse(dataObjects);
					} else {
						resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
						resObj.setErrormessage(GlobalConstants.FAILURE);
					}
				} else {
					resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
					resObj.setErrormessage(GlobalConstants.FAILURE);
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
