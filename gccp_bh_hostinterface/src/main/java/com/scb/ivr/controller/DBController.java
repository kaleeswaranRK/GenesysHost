package com.scb.ivr.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scb.ivr.db.entity.AMIvrIntraction;
import com.scb.ivr.db.entity.AllBinDetails_Res;
//import com.scb.avaya.logging.CustomLogger;
import com.scb.ivr.db.entity.BinMaster_Res;
import com.scb.ivr.db.entity.BusinessHrsCheck_Req;
import com.scb.ivr.db.entity.BusinessHrsCheck_Res;
import com.scb.ivr.db.entity.CallLog_Req;
import com.scb.ivr.db.entity.ContextCTI_Req;
import com.scb.ivr.db.entity.ContextCTI_Res;
import com.scb.ivr.db.entity.GetConfigDetails_Res;
import com.scb.ivr.db.entity.IncomingPoints_Res;
import com.scb.ivr.db.entity.PublicHolidayCheck_Res;
import com.scb.ivr.db.entity.ServiceHours_Req;
import com.scb.ivr.db.entity.ServiceHours_Res;
import com.scb.ivr.db.entity.TransferPoints_Res;
import com.scb.ivr.db.res.AMIvrIntraction_Res;
import com.scb.ivr.db.res.CallLogUpdate_Res;
import com.scb.ivr.db.res.LanguageResetStatus_Res;
import com.scb.ivr.db.res.PrefereredLangCode_Res;
import com.scb.ivr.db.res.PrefereredLangUpdate_Res;
import com.scb.ivr.db.service.DBService;
import com.scb.ivr.exception.CustomException;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.service.generalvo.CommonInput;
import com.scb.ivr.util.Utilities;

/**
 * @author TA
 * @category Maintain DB related information
 */

@Component
public class DBController {

	@Autowired
	DBService dbService;

	@Autowired
	Utilities utilities;

	public PrefereredLangCode_Res getPreferredLanguageBasedOnCLI(Map<String, Object> inParams) {
		PrefereredLangCode_Res prefereredLangCode_Res = new PrefereredLangCode_Res();
		
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String cli = (String) inParams.get("cli");

		try {
			if (!utilities.isNullOrEmpty(cli)) {
				prefereredLangCode_Res = dbService.getPreferredLanguageBasedOnCLI(inParams);

			} else {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "CLI is null/Empty");

				throw new CustomException(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011,
						"CLI is null/Empty");
			}
		} catch (CustomException e) {
			prefereredLangCode_Res.setErrorcode(e.getErrorCode());
			prefereredLangCode_Res.setErrormessage(e.getErrorMsg());
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + "Exception occured ", e);
			prefereredLangCode_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			prefereredLangCode_Res.setErrormessage("DB Server problem");
		}
		sessionLogger.debug(prefereredLangCode_Res.toString());
		return prefereredLangCode_Res;
	}

	public PrefereredLangUpdate_Res setPreferredLanguage(Map<String, Object> inParams) {

		PrefereredLangUpdate_Res prefereredLangUpdate_Res = new PrefereredLangUpdate_Res();
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String relId = (String) inParams.get("relId");
		String langCode = (String) inParams.get("langCode");
		String cli = (String) inParams.get("cli");

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started input params : " + inParams);
		try {

			if ((relId != null && !"".equalsIgnoreCase(relId))
					&& (langCode != null && !"".equalsIgnoreCase(langCode))
					&& (cli != null && !"".equalsIgnoreCase(cli))) {
				String statusCode = dbService.setPreferredLanguage(inParams);
				prefereredLangUpdate_Res.setStatus(statusCode);
				prefereredLangUpdate_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
				prefereredLangUpdate_Res.setErrormessage(GlobalConstants.SUCCESS);
			} else {
				sessionLogger.debug(
						utilities.getCurrentClassAndMethodName() + " The given RelID or Lang Code or CLI is null/empty");

				throw new CustomException(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011,
						"The given RelID or Lang Code is null/empty");

			}
		} catch (CustomException e) {
			prefereredLangUpdate_Res.setErrorcode(e.getErrorCode());
			prefereredLangUpdate_Res.setErrormessage(e.getErrorMsg());
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			prefereredLangUpdate_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			prefereredLangUpdate_Res.setErrormessage("DB Server problem");
		}
		sessionLogger.debug(prefereredLangUpdate_Res.toString());
		return prefereredLangUpdate_Res;
	}

	public CallLogUpdate_Res insertCallLog(CallLog_Req call_log) {

		CallLogUpdate_Res callLogUpdate_Res = new CallLogUpdate_Res();
		
		Logger sessionLogger = call_log != null && call_log.getSession_id() != null
				? CustomLogger.getLogger(call_log.getSession_id().toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));

		String status = GlobalConstants.FAILURE;

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started Input Params : " + call_log);
		try {
			status = dbService.insertCallLog(call_log);
			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Status : " + status);
			callLogUpdate_Res.setStatus(status);
			callLogUpdate_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
			callLogUpdate_Res.setErrormessage(GlobalConstants.SUCCESS);
		} catch (CustomException e) {
			callLogUpdate_Res.setErrorcode(e.getErrorCode());
			callLogUpdate_Res.setErrormessage(e.getErrorMsg());
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			callLogUpdate_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			callLogUpdate_Res.setErrormessage("DB Server problem");
		}
		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Ended");
		
		sessionLogger.debug(callLogUpdate_Res.toString());
		return callLogUpdate_Res;

	}

	public void insertHostTransactions(Map<String, Object> input) {
		CommonInput commonInput = (CommonInput) input.get("reqObj");

		Logger sessionLogger = input != null && commonInput.getSessionId() != null
				? CustomLogger.getLogger(commonInput.getSessionId().toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		//sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started input Params : " + input);
		try {
			dbService.insertHostTransactions(input);
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
		}
	}

	public BinMaster_Res getCardDetailsBasedOnBin(Map<String, Object> inParams) {

		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String binNumber = (String) inParams.get("binNumber");

		BinMaster_Res binMaster_Res = new BinMaster_Res();
		
		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started input Params : " + inParams);
		try {

			if (!utilities.isNullOrEmpty(binNumber)) {
				binNumber = binNumber.trim();
				if (binNumber.length() >= 6) {
					binMaster_Res = dbService.getCardDetailsBasedOnBin(inParams);
				} else {
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Invalid BIN Number");
					throw new CustomException(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011,"Invalid BIN Number");
				}
			} else {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " BIN Number is null/Empty");
				throw new CustomException(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011,"BIN Number is null/Empty");
			}

		} catch (CustomException e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Invalid Field Details : ", e);
			binMaster_Res.setErrorcode(e.getErrorCode());
			binMaster_Res.setErrormessage(e.getErrorMsg());
		}catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			binMaster_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			binMaster_Res.setErrormessage("DB Server problem");
		}
		sessionLogger.debug(binMaster_Res.toString());
		return binMaster_Res;
	}

	public AllBinDetails_Res getAllBinDetails(Map<String, Object> inParams) {
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		AllBinDetails_Res allBinDetails_Res = new AllBinDetails_Res();
		
		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started input Params : " + inParams);
		try {

			allBinDetails_Res = dbService.getAllBinDetails(inParams);

		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			allBinDetails_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			allBinDetails_Res.setErrormessage(GlobalConstants.FAILURE);
		}
		sessionLogger.debug(allBinDetails_Res.toString());
		return allBinDetails_Res;
	}
	
	public boolean isOfflineHrs() {
		Logger tpSystemLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		boolean isOfflineHrs = false;
		try {
			tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Started / Check offline Hrs");
			isOfflineHrs = dbService.isOfflineHrs();
			tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Is Offline Hrs : " + isOfflineHrs);
		} catch (Exception e) {
			tpSystemLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
		}

		return isOfflineHrs;
	}

	public LanguageResetStatus_Res languageReset(Map<String, Object> requestParams) {
		LanguageResetStatus_Res languageResetStatus_Res = new LanguageResetStatus_Res();
		Logger tpSystemLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String status = "";
		try {
			tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , Language Reset");
			
			if(requestParams.get("cli") != null && !"".equalsIgnoreCase(requestParams.get("cli").toString())) {
				status = dbService.languageReset(requestParams);
				
				if("Success".equalsIgnoreCase(status)) {
					tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Language reset status : " + status);
					languageResetStatus_Res.setStatus(status);
					languageResetStatus_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
					languageResetStatus_Res.setErrormessage(GlobalConstants.SUCCESS);
				}else {
					tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Language reset status : " + status);
					
					throw new CustomException(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031, "Record Not Found");

				}
				
			}else {
				tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Cli is Empty or Null");

				throw new CustomException(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011, "Cli is Empty or Null");
			}
			
		} catch (CustomException e) {
			languageResetStatus_Res.setErrorcode(e.getErrorCode());
			languageResetStatus_Res.setErrormessage(e.getErrorMsg());
			
		} catch (Exception e) {
			tpSystemLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			languageResetStatus_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			languageResetStatus_Res.setErrormessage("DB Server problem");
			
		}
		return languageResetStatus_Res;
	}

	public PublicHolidayCheck_Res getPublicHolidayDetails(String dateStr) {
		PublicHolidayCheck_Res holidayRes = new PublicHolidayCheck_Res();
		Logger tpSystemLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			if(dateStr != null && !"".equalsIgnoreCase(dateStr)) {
				tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , Public holiday details");
				holidayRes = dbService.getPublicHolidayDetails(dateStr);
				tpSystemLogger.debug(
						utilities.getCurrentClassAndMethodName() + "  Public holiday details : " + holidayRes.toString());
			}else {
				tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Date is Empty or Null");

				throw new CustomException(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011, "Date is Empty or Null");
			}
			
		} catch (CustomException e) {
			holidayRes.setErrorcode(e.getErrorCode());
			holidayRes.setErrormessage(e.getErrorMsg());
			
		} catch (Exception e) {
			tpSystemLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			holidayRes.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			holidayRes.setErrormessage("DB Server problem");
		}
		return holidayRes;
	}

	public GetConfigDetails_Res getCommonConfigDetails(Map<String, Object> inParams) {
		
		String tableName = inParams.get("tableName").toString();
		
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		GetConfigDetails_Res configDetailsRes = new GetConfigDetails_Res();

		try {
			
			if(tableName != null && !"".equalsIgnoreCase(tableName)) {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , Config details");
				configDetailsRes = dbService.getCommonConfigDetails(tableName);
				sessionLogger.debug(
						utilities.getCurrentClassAndMethodName() + "  Config details : " + configDetailsRes.toString());
			} else {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Input Param is Empty or Null");

				throw new CustomException(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011, "Input Param is Empty or Null");
			}
			
		} catch (CustomException e) {
			configDetailsRes.setErrorcode(e.getErrorCode());
			configDetailsRes.setErrormessage(e.getErrorMsg());
			
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			configDetailsRes.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			configDetailsRes.setErrormessage("DB Server problem");
			
		}
		sessionLogger.debug(configDetailsRes.toString());
		return configDetailsRes;
	}

	public PublicHolidayCheck_Res holidayCheck(Map<String, Object> inParams) {
		PublicHolidayCheck_Res holidayRes = new PublicHolidayCheck_Res();
		Logger tpSystemLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			
			if(inParams.get("currentTimeStamp") != null &&
					!"".equalsIgnoreCase(inParams.get("currentTimeStamp").toString())) {
				tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , Holiday check");
				holidayRes = dbService.holidayCheck(inParams);
				tpSystemLogger.debug(
						utilities.getCurrentClassAndMethodName() + "  Holiday Check details : " + holidayRes.toString());
			}else {
				tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Input Param is Empty or Null");

				throw new CustomException(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011, "Input Param is Empty or Null");
			}
			
		} catch (CustomException e) {
			holidayRes.setErrorcode(e.getErrorCode());
			holidayRes.setErrormessage(e.getErrorMsg());
			
		} catch (Exception e) {
			tpSystemLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			holidayRes.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			holidayRes.setErrormessage("DB Server problem");
		}
		return holidayRes;
	}

	public ServiceHours_Res getServiceHours(ServiceHours_Req req) {
		ServiceHours_Res serviceHrsRes = new ServiceHours_Res();
		Logger tpSystemLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , Service Hours");
			serviceHrsRes = dbService.getServiceHours(req);
			tpSystemLogger.debug(
					utilities.getCurrentClassAndMethodName() + "  Service hours details : " + serviceHrsRes.toString());
		} catch (Exception e) {
			tpSystemLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			serviceHrsRes.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			serviceHrsRes.setErrormessage("DB Server problem");
		}
		return serviceHrsRes;
	}

	
	public void insertAMIvrHost(AMIvrIntraction amivr, String method) {
		Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , InsertAMIvr Status");
			dbService.insertAMIvrDetails(amivr, method);
			tpSystemLogger.debug(
					utilities.getCurrentClassAndMethodName() + "  InsertAMIvr Status Completed  " );
		} catch (Exception e) {
			tpSystemLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
		}
		
	}

	public AMIvrIntraction getAMIvrHost(AMIvrIntraction amivr) {
		AMIvrIntraction amIVRinteraction = new AMIvrIntraction();
		
		org.apache.logging.log4j.Logger tpSystemLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(GlobalConstants.HostLog_DB);

		//Logger tpSystemLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , get AMIvr Status : " + amivr.toString());
			amIVRinteraction = dbService.getAMIvrDetails(amivr);
			tpSystemLogger.debug(
					utilities.getCurrentClassAndMethodName() + "  get AMIvr Status  " );
		} catch (Exception e) {
			tpSystemLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
		}
		return amIVRinteraction;
	}
	
	public AMIvrIntraction_Res getAMIvrHostRes(AMIvrIntraction amivr) {
		AMIvrIntraction_Res amIVRinteraction_Res = new AMIvrIntraction_Res();
		Logger tpSystemLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			tpSystemLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , get AMIvr Status");
			amIVRinteraction_Res = dbService.getAMIvrHostRes(amivr);
			tpSystemLogger.debug(
					utilities.getCurrentClassAndMethodName() + "  get AMIvr Status  " );
		} catch (Exception e) {
			tpSystemLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			amIVRinteraction_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			amIVRinteraction_Res.setErrormessage("DB Server problem");
		}
		return amIVRinteraction_Res;
	}
	
	public IncomingPoints_Res getIncomingPoints(Map<String, Object> inParams) {
		IncomingPoints_Res incomingPoints_Res = new IncomingPoints_Res();
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , get IncomingPoints");
			incomingPoints_Res = dbService.getIncomingPoints(inParams);
			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + "  get IncomingPoints Status  " + incomingPoints_Res);
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			incomingPoints_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			incomingPoints_Res.setErrormessage("DB Server Problem");
		}
		return incomingPoints_Res;
	}

	public TransferPoints_Res getTransferPoints(Map<String, Object> inParams) {

		TransferPoints_Res transferPoints_Res = new TransferPoints_Res();
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , get TransferPoints");
			transferPoints_Res = dbService.getTransferPoints(inParams);
			sessionLogger.debug(
					utilities.getCurrentClassAndMethodName() + "  get TransferPoints Status  " + transferPoints_Res);
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			transferPoints_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			transferPoints_Res.setErrormessage("DB Server Problem");
		}
		return transferPoints_Res;
	}
	
	
	public BusinessHrsCheck_Res checkBusinessHours(BusinessHrsCheck_Req reqObj) {

		BusinessHrsCheck_Res res = new BusinessHrsCheck_Res();
		
		Logger sessionLogger = reqObj != null && reqObj.getSessionId() != null
				? CustomLogger.getLogger(reqObj.getSessionId().toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));


		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started Input Params : " + reqObj);
		try {
			res = dbService.checkBusinessHours(reqObj);
			
		}catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			res.setErrormessage("DB Server problem");
		}
		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Ended");
		
		sessionLogger.debug(res.toString());
		return res;
	}
	
	public ContextCTI_Res insertContextCTI(ContextCTI_Req contextCTI_Req) {

		ContextCTI_Res contextCTI_Res = new ContextCTI_Res();
		
		Logger sessionLogger = contextCTI_Req != null && contextCTI_Req.getSession_id() != null
				? CustomLogger.getLogger(contextCTI_Req.getSession_id().toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String status = GlobalConstants.FAILURE;

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started Input Params : " + contextCTI_Req);
		try {
			if(contextCTI_Req.getUcid() != null && !"".equalsIgnoreCase(contextCTI_Req.getUcid()) &&
					contextCTI_Req.getCti_context_data() != null && !"".equalsIgnoreCase(contextCTI_Req.getCti_context_data())) {
				status = dbService.insertContextCTI(contextCTI_Req);
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Status : " + status);
				contextCTI_Res.setStatus(status);
				contextCTI_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
				contextCTI_Res.setErrormessage(GlobalConstants.SUCCESS);
			}else {
				contextCTI_Res.setStatus(status);
				contextCTI_Res.setErrorcode(GlobalConstants.FAILURE);
				contextCTI_Res.setErrormessage(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			}
			
		} catch (CustomException e) {
			contextCTI_Res.setErrorcode(e.getErrorCode());
			contextCTI_Res.setErrormessage(e.getErrorMsg());
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			contextCTI_Res.setErrorcode(GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			contextCTI_Res.setErrormessage("DB Server problem");
		}
		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Ended");
		sessionLogger.debug(contextCTI_Res.toString());
		return contextCTI_Res;

	}
	
	public Map<String, String> getContextStoreDtls(Map<String, String> inParams) {

		Map<String, String> contextStoreDtlsRes = new HashMap<>();
		
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {
			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started , get TransferPoints");
			if(inParams.containsKey("ucid") && !"".equalsIgnoreCase(inParams.get("ucid").toString().trim())) {
				contextStoreDtlsRes = dbService.getContextStoreDtls(inParams);
				sessionLogger.debug(
						utilities.getCurrentClassAndMethodName() + " Res:" + contextStoreDtlsRes);
			}else {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " UCID Empty");
				contextStoreDtlsRes.put("ERROR_CODE", GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
				contextStoreDtlsRes.put("ERROR_DESC", "UCID Empty");
			}
			
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			contextStoreDtlsRes.put("ERROR_CODE", GlobalConstants.ERRORCODE_DB_EXCEPTION_700032);
			contextStoreDtlsRes.put("ERROR_DESC", "Error occured in DB");
		}
		sessionLogger.debug(contextStoreDtlsRes.toString());
		return contextStoreDtlsRes;
	}


}