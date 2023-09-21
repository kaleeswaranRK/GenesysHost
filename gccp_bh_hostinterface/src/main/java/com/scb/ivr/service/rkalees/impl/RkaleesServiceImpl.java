/**
 * 
 */
package com.scb.ivr.service.rkalees.impl;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scb.ivr.api.hosthelper.HostHelper;
import com.scb.ivr.controller.DBController;
import com.scb.ivr.exception.CustomException;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.rkalees.cardLost_Req;
import com.scb.ivr.model.rkalees.cardLost_Res;
import com.scb.ivr.model.rkalees.res.cardLost.CardLostResponseData;
import com.scb.ivr.service.rkalees.RkaleesService;
import com.scb.ivr.util.Utilities;

/**
 * @author TA
 *
 */

@Component
public class RkaleesServiceImpl implements RkaleesService {

	@Value("${RKALEESdummyFilePath}")
	private String dummyFilePath;

	@Value("${RKALEESdummyFileExtn}")
	private String dummyFileExtn;

	@Autowired
	Utilities utilities;

	@Autowired
	HostHelper hostHelper;

	@Autowired
	DBController dbController;

	@Override
	public cardLost_Res cardLost(Map<String, Object> inputParams) {
		System.out.println("cardLost Entry obj __________________" + inputParams);

		cardLost_Res resObj = new cardLost_Res();
		cardLost_Req reqObj = (cardLost_Req) inputParams.get("reqObj");
		Properties serviceProps = (Properties) inputParams.get("serviceProperties");
		String sessionId = reqObj.getSessionId();
		Logger sessionLogger = CustomLogger.getLogger(sessionId);

//		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext()
//				.getLogger(serviceProps.getProperty("hostLoggerName"));
		try {
			/// CHECK DUMMY FLAG
			/****
			 * 
			 * check the dummy flag from config.properties. if the dummy flag is Y, got
			 * response from file required path and return to the ivr. otherwise call to
			 * endPoint API.
			 * 
			 ****/

			if (GlobalConstants.Dummy_Flag_Y.equalsIgnoreCase(reqObj.getDummyFlag())) {

				String filePath = dummyFilePath + reqObj.getServiceName().toString() + "/" + reqObj.getCardNo()
						+ dummyFileExtn;

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". Processing dummy file : " + filePath);

				String responseString = "";
				try {
					responseString = new String(Files.readAllBytes(Paths.get(filePath)));
					ObjectMapper objectMapper = new ObjectMapper();
					objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
					CardLostResponseData data = objectMapper.readValue(responseString, CardLostResponseData.class);
					JsonObject cardDetails = JsonParser.parseString(responseString).getAsJsonObject()
							.getAsJsonObject("data");
					String customerId = cardDetails.getAsJsonObject("attributes").get("customer-id").getAsString();
					sessionLogger.debug(
							utilities.getCurrentClassAndMethodName() + "customerId from response : " + customerId);
					cardLost_Req req = (cardLost_Req) inputParams.get("reqObj");
					if (req.getUserId().equalsIgnoreCase(customerId)) {
						String cardStatus = cardDetails.getAsJsonObject("attributes").get("card-status").getAsString();
						sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Card Status : " + cardStatus);
						if ("BLOCKED".equalsIgnoreCase(cardStatus)) {
							resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_CARD_BLOCKED_700071);
							resObj.setErrormessage("Card is Already blocked");
							resObj.setResponse(data);
						} else {
							resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
							resObj.setErrormessage(GlobalConstants.SUCCESS);
							resObj.setResponse(data);
						}
					} else {
						resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_RELID_NOT_VALID_700073);
						resObj.setErrormessage("relID is Invalid");
					}
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				}
				sessionLogger
						.debug(utilities.getCurrentClassAndMethodName() + "Response String value : " + responseString);

			} else {
				Response responseMessage = null;
				System.out.println("entry.....");
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Original Lost card service Entry");
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Lost card service processing .......");
//				String xmlString = serviceProps.getProperty("requestPayload");
//				Map<String, Object> inputElemets = new HashMap<>();
//				inputElemets.put("cardNum", reqObj.getCardNo());
//				inputElemets.put("relID", reqObj.getUserId());
//				String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);
//				String url="http://localhost:8080/SCBServicecop/cardLostUpdate";
//
//				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);
//				resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
//				resObj.setErrormessage(GlobalConstants.SUCCESS);
				String xmlString = serviceProps.getProperty("requestPayload");
				Map<String, Object> inputElemets = new HashMap<>();
				inputElemets.put("cardNum", reqObj.getCardNo());
				inputElemets.put("relID", reqObj.getUserId());

				/***
				 * Load parameter to payload request
				 ***/
				String requestJson = utilities.injectDataIntoRequestString(xmlString, inputElemets);

				sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". request JSON is: " + requestJson);

				serviceProps.setProperty("requestBody", requestJson);
				reqObj.setRequestBody(requestJson);

//				//// Token Generation
//				String token = utilities.callOAuth2Token(serviceProps);
//
//				/// Token Validation
//				if (token == null || "".equals(token.trim())) {
//					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + ". The token is null/empty");
//
//					throw new CustomException(GlobalConstants.ERRORCODE_TOKEN_GEN_700014,
//							GlobalConstants.FAILURE + ". The token is null/empty");
//				}
//				serviceProps.setProperty("token", token);
//				reqObj.setToken(token);
//
//				///Endpoint Http Method 
//				serviceProps.setProperty("httpMethod", HttpMethod.POST);
//				inputParams.put("serviceProperties", serviceProps);
//
//				/// Header Parameter
//				/***
//				 * 
//				 * form Http Headers Parameter and pass this parameter to helper class. 
//				 * then the helper class call endpoint api with header parameter and return response
//				 * 
//				 * (for helper class reuse purpose)
//				 * ***/
//				Map<String, String> httpHeaderParams = new HashMap<>();
//				httpHeaderParams.put(HttpHeaders.CONTENT_TYPE, "application/vnd.api+json");
//				httpHeaderParams.put(HttpHeaders.ACCEPT, "application/vnd.api+json");
//				httpHeaderParams.put("X-Market", serviceProps.getProperty("requestCountry"));
//				httpHeaderParams.put("Transaction-ID", serviceProps.getProperty("trackingID"));
//				httpHeaderParams.put("channel", serviceProps.getProperty("channel"));
//
//				inputParams.put("httpHeaderParams", httpHeaderParams);
//
//				///Call EndPoint API
//				responseMessage = hostHelper.invokeHttpsWebservice(inputParams);
				try {
					HttpPost httpPost = new HttpPost(serviceProps.getProperty("endPoint"));
					httpPost.setHeader("Content-type", "application/json");
					StringEntity stringEntity = new StringEntity(reqObj.getRequestBody(), "UTF-8");
					stringEntity.setChunked(true);
					httpPost.setEntity(stringEntity);
					CloseableHttpClient httpClient = HttpClientBuilder.create().build();
					CloseableHttpResponse response = httpClient.execute(httpPost);
					System.out.println("host return ...");
					System.out.println("Response : " + response);
					String strResponse = "";

					if (response != null) {
						if (response.getStatusLine().getStatusCode() == 200
								|| response.getStatusLine().getStatusCode() == 201) {
							HttpEntity entity = response.getEntity();
							if (entity != null) {
								strResponse = EntityUtils.toString(entity);
								responseMessage = Response.status(response.getStatusLine().getStatusCode())
										.entity(strResponse).build();
								resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
								resObj.setErrormessage(GlobalConstants.SUCCESS);
							} else {
								resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
								resObj.setErrormessage(GlobalConstants.SUCCESS);
								sessionLogger.debug(utilities.getCurrentClassAndMethodName()
										+ " Error While Accessing HTTP Service : "
										+ response.getStatusLine().getStatusCode());
							}
						} else if (response.getStatusLine().getStatusCode() == 204) {
							resObj.setErrorcode(String.valueOf(response.getStatusLine().getStatusCode()));
							resObj.setErrormessage(GlobalConstants.ERRORCODE_HOST_CARD_NOT_VALID_700074);
						} else {
							resObj.setErrorcode(GlobalConstants.FAILURECODE);
							resObj.setErrormessage(GlobalConstants.FAILURE);
						}
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE);
						resObj.setErrormessage(GlobalConstants.FAILURE);
						sessionLogger.debug(
								utilities.getCurrentClassAndMethodName() + " Error While Accessing HTTP Service  ");
					}
					if (responseMessage != null && responseMessage.getEntity() != null
							&& !responseMessage.getEntity().toString().equalsIgnoreCase("")) {

						sessionLogger.info(utilities.getCurrentClassAndMethodName() + ". Response : "
								+ responseMessage.getEntity().toString());

						ObjectMapper objectMapper = new ObjectMapper();
						objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
						cardLost_Res dataObjects = objectMapper.readValue(responseMessage.getEntity().toString(),
								cardLost_Res.class);
						if (dataObjects != null && "200".equalsIgnoreCase(dataObjects.getErrorcode())
								&& dataObjects.getResponse() != null) {
							resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
							resObj.setErrormessage(GlobalConstants.SUCCESS);
							resObj.setResponse(dataObjects.getResponse());
						} else {
							resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
							resObj.setErrormessage(GlobalConstants.FAILURE + ". " + responseMessage.getStatus());
						}
					} else {
						resObj.setErrorcode(GlobalConstants.FAILURECODE_UNKNOWN);
						resObj.setErrormessage(GlobalConstants.FAILURE);
					}

				} catch (SocketException e) {
					sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
							+ ". Socket Exception MSG IS " + e.getMessage(), e);

					throw new SocketException(e.getMessage());
				} catch (SocketTimeoutException e) {
					sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
							+ ". Socket TimeOut Exception MSG IS " + e.getMessage(), e);

					throw new SocketTimeoutException(e.getMessage());
				} catch (ConnectTimeoutException e) {
					sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
							+ ". Connect TimeOut Exception MSG IS " + e.getMessage(), e);

					throw new ConnectTimeoutException(e.getMessage());
				} catch (IOException e) {
					sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
							+ ". IO Exception MSG IS " + e.getMessage(), e);

					throw new IOException(e.getMessage());
				} catch (IllegalArgumentException e) {
					sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
							+ ". Illegal Argument Exception MSG IS " + e.getMessage(), e);

					throw new IllegalArgumentException(e.getMessage());
				} catch (Exception e) {
					sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
							+ ". Exception MSG IS " + e.getMessage(), e);
				}
				System.out.println("Exit...");
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Original Lost card service Exit......");
			}

		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
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
				System.out.println("cardLost Exit obj __________________" + inputParams);

				dbController.insertHostTransactions(inputParams);
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " DB Host insertion completed ");
			} catch (Exception e) {
				// e.printStackTrace();
				sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
						+ " DB Host logging failed " + e.getMessage());
			}
			inputParams = null;
		}

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
