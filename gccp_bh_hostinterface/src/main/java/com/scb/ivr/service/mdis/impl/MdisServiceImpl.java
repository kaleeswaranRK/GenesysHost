/**
 * 
 */
package com.scb.ivr.service.mdis.impl;

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
import java.util.UUID;

import javax.ws.rs.HttpMethod;
import javax.ws.rs.core.Response;

import org.apache.http.HttpHeaders;
import org.apache.http.ParseException;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.ivr.api.hosthelper.HostHelper;
import com.scb.ivr.controller.DBController;
import com.scb.ivr.exception.CustomException;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.mdis.SendSMS_Req;
import com.scb.ivr.model.mdis.SendSMS_Res;
import com.scb.ivr.model.mdis.res.sendsms.SendSMSResponseData;
import com.scb.ivr.service.mdis.MdisService;
import com.scb.ivr.util.Utilities;

/**
 * @author TA
 *
 */

@Component
public class MdisServiceImpl implements MdisService {

	@Value("${MDISdummyFilePath}")
	private String dummyFilePath;

	@Value("${MDISdummyFileExtn}")
	private String dummyFileExtn;

	@Autowired
	Utilities utilities;

	@Autowired
	HostHelper hostHelper;

	@Autowired
	DBController dbController;

	@Override
	public SendSMS_Res sendSMS(Map<String, Object> inputParams) {

		SendSMS_Res resObj = new SendSMS_Res();
		SendSMS_Req reqObj = (SendSMS_Req) inputParams.get("reqObj");

		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(GlobalConstants.DateTimeFormat);
		String requestInitiatedTimestamp = dateTimeFormat.format(new Date());

		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext()
				.getLogger(serviceProps.getProperty("hostLoggerName"));

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Request started @" + requestInitiatedTimestamp
				+ ", dummyFlag: " + reqObj.getDummyFlag());
		try {
			Response responseMessage = null;
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties. if the dummy flag is Y, got
			 * response from file required path and return to the ivr. otherwise call to
			 * endPoint API.
			 * 
			 ****/

			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getMobileNumber()
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

				inputElemets.put("SourceApp", serviceProps.getProperty("SourceApp"));
				inputElemets.put("SourceCtry", serviceProps.getProperty("SourceCtry"));
				inputElemets.put("Priority", serviceProps.getProperty("Priority"));
				inputElemets.put("BodyTmplFlag", serviceProps.getProperty("BodyTmplFlag"));
				inputElemets.put("UTFMessage", serviceProps.getProperty("UTFMessage"));

				inputElemets.put("SourceRef", UUID.randomUUID().toString());
				String dateTimeStamp = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(new Date());
				inputElemets.put("DateTimeStamp", dateTimeStamp);

				inputElemets.put("MobileNumber", reqObj.getMobileNumber());
				inputElemets.put("Language", reqObj.getLanguage());
				inputElemets.put("SMSBody", reqObj.getSmsBody());

				/***
				 * Load parameter to payload request
				 ***/
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
				 * form Http Headers Parameter and pass this parameter to helper class. then the
				 * helper class call endpoint api with header parameter and return response
				 * 
				 * (for helper class reuse purpose)
				 ***/

				Map<String, String> httpHeaderParams = new HashMap<>();
				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/xml");
				httpHeaderParams.put(HttpHeaders.ACCEPT, "application/xml");

				inputParams.put("httpHeaderParams", httpHeaderParams);

				/// Call EndPoint API
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
				 * if the status code is 200 or 201, received success response. mapping the
				 * success code and return to IVR. otherwise error message received from
				 * endPoint. mapping that error message data and return to IVR.
				 * 
				 ****/

				if (responseMessage.getStatus() == 200 || responseMessage.getStatus() == 201) {

					String jsonConvertedMessage = utilities
							.convertXMLintoJsonString(responseMessage.getEntity().toString());

					SendSMSResponseData dataObjects = objectMapper.readValue(jsonConvertedMessage,
							SendSMSResponseData.class);

					if (dataObjects != null && dataObjects.getMdisResponse() != null
							&& dataObjects.getMdisResponse().getResponse() != null) {

						if ("NEW".equalsIgnoreCase(dataObjects.getMdisResponse().getResponse().getStatusCode())) {
							resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
							resObj.setErrormessage(GlobalConstants.SUCCESS);
						} else {
							resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_SMS_DEL_FAILED_700051);
							resObj.setErrormessage(dataObjects.getMdisResponse().getResponse().getStatusDesc());
						}

					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}

				} else if (responseMessage.getStatus() == 400 || responseMessage.getStatus() == 401) {

					String jsonConvertedMessage = utilities
							.convertXMLintoJsonString(responseMessage.getEntity().toString());

					SendSMSResponseData dataObjects = null;
					
					if(!"".equalsIgnoreCase(jsonConvertedMessage)) {
						dataObjects = objectMapper.readValue(jsonConvertedMessage,
								SendSMSResponseData.class);
					}else {
						dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
								SendSMSResponseData.class);
					}
					

					if (dataObjects != null) {
						if (dataObjects.getMdisResponse() != null
								&& dataObjects.getMdisResponse().getResponse() != null) {
							resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_SMS_DEL_FAILED_700051);
							resObj.setErrormessage(dataObjects.getMdisResponse().getResponse().getStatusDesc());
						} else if (dataObjects.getMessage() != null) {
							resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getMessage());
						}  else {
							resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
						}
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
					}

				} else {
					SendSMSResponseData dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
							SendSMSResponseData.class);
					if (dataObjects != null) {
						if (dataObjects.getMessage() != null) {
							resObj.setErrorcode(String.valueOf(responseMessage.getStatus()));
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + dataObjects.getMessage());
						} else if (dataObjects.getErrors() != null && dataObjects.getErrors().length > 0) {

							if (dataObjects.getErrors()[0].getCode() != null) {
								resObj.setErrorcode(dataObjects.getErrors()[0].getCode());
								resObj.setErrormessage(
										GlobalConstants.FAILURE + ". " + dataObjects.getErrors()[0].getDetail());
							} else if (dataObjects.getErrors()[0].getStatus() != null) {
								resObj.setErrorcode(dataObjects.getErrors()[0].getStatus());
								resObj.setErrormessage(
										GlobalConstants.FAILURE + ". " + dataObjects.getErrors()[0].getTitle());
							} else {
								resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
								resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
							}

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
				 ***/

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
		 ***/

		if (!GlobalConstants.SUCCESSCODE.equalsIgnoreCase(resObj.getErrorcode())) {
			resObj.setErrorcode(utilities.getErrorCodeInFormat(resObj.getErrorcode()));
		}

		return resObj;

	}

}
