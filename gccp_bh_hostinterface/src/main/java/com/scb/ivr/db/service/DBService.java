package com.scb.ivr.db.service;

import java.util.Map;

import com.scb.ivr.db.entity.AMIvrIntraction;
import com.scb.ivr.db.entity.AllBinDetails_Res;
import com.scb.ivr.db.entity.BinMaster_Res;
import com.scb.ivr.db.entity.BusinessHrsCheck_Req;
import com.scb.ivr.db.entity.BusinessHrsCheck_Res;
import com.scb.ivr.db.entity.CallLog_Req;
import com.scb.ivr.db.entity.ContextCTI_Req;
import com.scb.ivr.db.entity.GetConfigDetails_Res;
import com.scb.ivr.db.entity.IncomingPoints_Res;
import com.scb.ivr.db.entity.PublicHolidayCheck_Res;
import com.scb.ivr.db.entity.ServiceHours_Req;
import com.scb.ivr.db.entity.ServiceHours_Res;
import com.scb.ivr.db.entity.TransferPoints_Res;
import com.scb.ivr.db.res.AMIvrIntraction_Res;
import com.scb.ivr.db.res.PrefereredLangCode_Res;

public interface DBService {

	PrefereredLangCode_Res getPreferredLanguageBasedOnCLI(Map<String, Object> inParams) throws Exception;

	String setPreferredLanguage(Map<String, Object> inParams) throws Exception;

	String insertCallLog(CallLog_Req call_log) throws Exception;

	void insertHostTransactions(Map<String, Object> input);

	BinMaster_Res getCardDetailsBasedOnBin(Map<String, Object> inParams);

	AllBinDetails_Res getAllBinDetails(Map<String, Object> inParams);

	boolean isOfflineHrs();

	String languageReset(Map<String, Object> requestParams) throws Exception;

	PublicHolidayCheck_Res getPublicHolidayDetails(String dateStr) throws Exception;

	GetConfigDetails_Res getCommonConfigDetails(String tableName) throws Exception;

	PublicHolidayCheck_Res holidayCheck(Map<String, Object> inParams) throws Exception;

	ServiceHours_Res getServiceHours(ServiceHours_Req req) throws Exception;

	void insertAMIvrDetails(AMIvrIntraction amivr, String method);

	AMIvrIntraction getAMIvrDetails(AMIvrIntraction amivr);

	AMIvrIntraction_Res getAMIvrHostRes(AMIvrIntraction amivr) throws Exception;;

	IncomingPoints_Res getIncomingPoints(Map<String, Object> inParams);

	TransferPoints_Res getTransferPoints(Map<String, Object> inParams);

	BusinessHrsCheck_Res checkBusinessHours(BusinessHrsCheck_Req reqObj);

	String insertContextCTI(ContextCTI_Req contextCTI_Req) throws Exception;
	
	Map<String, String> getContextStoreDtls(Map<String, String> inParams);

}
