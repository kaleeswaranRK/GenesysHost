/**
 * 
 */
package com.scb.ivr.service.rkalees.impl;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

import org.apache.http.ParseException;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scb.ivr.api.hosthelper.HostHelper;
import com.scb.ivr.controller.DBController;
import com.scb.ivr.exception.CustomException;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.model.c400.res.accbal.AccountBalanceResponseData;
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
		System.out.println("cardLost Entry obj __________________"+inputParams);

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
					CardLostResponseData data = objectMapper.readValue(responseString,
							CardLostResponseData.class);
					JsonObject cardDetails =JsonParser.parseString(responseString).getAsJsonObject().getAsJsonObject("data");
					String customerId = cardDetails.getAsJsonObject("attributes").get("customer-id").getAsString();
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "customerId from response : " + customerId);
					cardLost_Req req = (cardLost_Req)inputParams.get("reqObj");
					if(req.getUserId().equalsIgnoreCase(customerId)) {
					String cardStatus = cardDetails.getAsJsonObject("attributes").get("card-status").getAsString();
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Card Status : " + cardStatus);
					if ("BLOCKED".equalsIgnoreCase(cardStatus)) {
						resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_CARD_BLOCKED_700071);
						resObj.setErrormessage("Card is Already blocked");
						resObj.setResponse(data);
					}
					else {
						resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
						resObj.setErrormessage(GlobalConstants.SUCCESS);
						resObj.setResponse(data);
					}
					}
					else {
						resObj.setErrorcode(GlobalConstants.ERRORCODE_HOST_RELID_NOT_VALID_700073);
						resObj.setErrormessage("relID is Invalid");
					}
				} catch (Exception e) {
					throw new CustomException(GlobalConstants.ERRORCODE_DUMMY_FILE_RESPONSE_NOT_FOUND_700013,
							"The required File not Found " + e.getMessage());
				}
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Response String value : "+responseString);
				
			} else {
				
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Original Lost card service Entry");
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Lost card service processing .......");
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "Original Lost card service Exit");
				resObj.setErrorcode(GlobalConstants.SUCCESSCODE);
				resObj.setErrormessage(GlobalConstants.SUCCESS);
			}


		} catch (NullPointerException e) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_NULLPOINTEREXCEPTION_700004);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Exception is : Null Exception occured");
			sessionLogger.error("SESSION ID : " + sessionId + " " + utilities.getCurrentClassAndMethodName()
					+ " Null Exception occured.", e);
		}catch (CustomException e) {
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
				inputParams.put("I_STATUS_DESCRIPTION", resObj.getErrorcode());
				System.out.println("cardLost Exit obj __________________"+inputParams);

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
