package com.scb.ivr.db.service.impl;

import java.net.InetAddress;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

import org.apache.logging.log4j.Logger;
import org.hibernate.exception.JDBCConnectionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.scb.ivr.db.entity.AMIvrIntraction;
import com.scb.ivr.db.entity.AllBinDetails_Res;
//import com.scb.avaya.logging.CustomLogger;
import com.scb.ivr.db.entity.BinMaster_Res;
import com.scb.ivr.db.entity.BusinessHrsCheck_Req;
import com.scb.ivr.db.entity.BusinessHrsCheck_Res;
import com.scb.ivr.db.entity.CallLog_Req;
import com.scb.ivr.db.entity.CommonConfigDetails;
import com.scb.ivr.db.entity.ContextCTI_Req;
import com.scb.ivr.db.entity.GetConfigDetails_Res;
import com.scb.ivr.db.entity.IncomingPoints;
import com.scb.ivr.db.entity.IncomingPoints_Res;
import com.scb.ivr.db.entity.PublicHolidayCheck_Res;
import com.scb.ivr.db.entity.ServiceHours_Req;
import com.scb.ivr.db.entity.ServiceHours_Res;
import com.scb.ivr.db.entity.TransferPoints;
import com.scb.ivr.db.entity.TransferPoints_Res;
import com.scb.ivr.db.res.AMIvrIntraction_Res;
import com.scb.ivr.db.res.PrefereredLangCode_Res;
import com.scb.ivr.db.service.DBService;
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.log.custom.CustomLogger;
import com.scb.ivr.service.generalvo.CommonInput;
import com.scb.ivr.util.Utilities;

@Component
public class DBServiceImpl implements DBService {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	Utilities utilities;
	

	@Transactional(value = "transactionManager")
	@Override
	public PrefereredLangCode_Res getPreferredLanguageBasedOnCLI(Map<String, Object> inParams) throws Exception {
		
		PrefereredLangCode_Res prefereredLangCode_Res = new PrefereredLangCode_Res();
		String cli = inParams.get("cli").toString();

		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String langCode = "";

		try {
			String startTime = new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date());
			
			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("GET_PREFFERED_LANG_CODE_SP")
					.setParameter("p_cli_no", cli.trim());

			langCode = (String) procedureQuery.getOutputParameterValue("p_lang_code");
			String p_check = (String) procedureQuery.getOutputParameterValue("p_check");
			
			if (p_check.equalsIgnoreCase("Data Not Exist")) {
				prefereredLangCode_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				prefereredLangCode_Res.setErrormessage("Record Not Found");
				return prefereredLangCode_Res;
			}
			String endTime = new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date());

			prefereredLangCode_Res.setLangCode(langCode);
			prefereredLangCode_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
			prefereredLangCode_Res.setErrormessage(GlobalConstants.SUCCESS);

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Preferred Language Code for CLI : "
					+ langCode + " , DB connection Duration : " + utilities.getTimeDiffBW2Date(startTime, endTime)
					+ " seconds");
						
		} 
		catch (Exception e) {
			
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while calling preferred language stored procedure : ", e);
			
			throw new Exception(e.getLocalizedMessage());
		}
			
		return prefereredLangCode_Res;
			
	}

	@Override
	@Transactional(value = "transactionManager")
	public String setPreferredLanguage(Map<String, Object> inParams) throws Exception {

		String status = GlobalConstants.FAILURE;
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String cli = (String) inParams.get("cli");
		String relId = (String) inParams.get("relId");
		String langCode = (String) inParams.get("langCode");

		try {
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("INSERT_CUSTOMER_DTLS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN).setParameter(1, cli)
					.setParameter(2, langCode).setParameter(3, relId);

			procedureQuery.execute();
			
			status = GlobalConstants.SUCCESS;
			
			sessionLogger
					.debug(utilities.getCurrentClassAndMethodName() + " Customer info has been added successfully");

		} catch (Exception e) {
			e.printStackTrace();
			sessionLogger.error(
					utilities.getCurrentClassAndMethodName() + " Exception occurred while adding customer info : ", e);

			throw new Exception(e.getLocalizedMessage());
			
		}
		
		return status;
	}

	@Transactional(value = "transactionManager")
	@Override
	public String insertCallLog(CallLog_Req callLog_Req) throws Exception {

		Logger sessionLogger = callLog_Req != null && callLog_Req.getSession_id() != null
				? CustomLogger.getLogger(callLog_Req.getSession_id().toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));

		String status = "";
		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " UCID : " + callLog_Req.getUcid());

		try {
			StoredProcedureQuery insertDetails = em.createNamedStoredProcedureQuery("INSERT_CALL_LOG_DTLS_SP");
			insertDetails.setParameter("I_CLI", callLog_Req.getCli());
			insertDetails.setParameter("I_DNIS", callLog_Req.getDnis());
			insertDetails.setParameter("I_STARTTIME", callLog_Req.getStarttime());
			insertDetails.setParameter("I_ENDTIME", callLog_Req.getEndtime());
			insertDetails.setParameter("I_UCID", callLog_Req.getUcid());
			insertDetails.setParameter("I_RMN", callLog_Req.get_rmn());
			insertDetails.setParameter("I_IS_RMN", callLog_Req.getis_rmn());
			insertDetails.setParameter("I_BANK_CARD_LOAN", callLog_Req.get_bank_card_loan());
			insertDetails.setParameter("I_CUSTOMER_SEGMENT", callLog_Req.getCustomer_segment());
			insertDetails.setParameter("I_TRANSFER_OR_DISC", callLog_Req.getTransfer_or_disc());
			insertDetails.setParameter("I_ACC_CARD_ID", callLog_Req.getAcc_card_id());
			insertDetails.setParameter("I_SEG_CODE", callLog_Req.getSeg_code());
			insertDetails.setParameter("I_REL_ID", callLog_Req.getRel_id());
			insertDetails.setParameter("I_BLOCK_CODE", callLog_Req.getBlock_code());
			insertDetails.setParameter("I_LANGUAGE", callLog_Req.getLanguage());
			insertDetails.setParameter("I_CONTEXT_ID", callLog_Req.getContext_id());
			insertDetails.setParameter("I_LASTMENU", callLog_Req.getLastmenu());
			insertDetails.setParameter("I_TRANSFER_ATTRIBUTES", callLog_Req.getTransfer_attributes());
			insertDetails.setParameter("I_COUNTRY", callLog_Req.getConutry());
			insertDetails.setParameter("I_ONE_FA", callLog_Req.getOne_fa());
			insertDetails.setParameter("I_TWO_FA", callLog_Req.getTwo_fa());
			insertDetails.setParameter("I_VERIFIED_BY", callLog_Req.getVerified_by());
			insertDetails.setParameter("I_IDENTIFIED_BY", callLog_Req.getIdentified_by());
			insertDetails.setParameter("I_MENU_TRAVERSE", callLog_Req.getMenu_traverse());
			insertDetails.setParameter("I_CHANNEL", callLog_Req.getChannel());
			insertDetails.setParameter("I_INVOLUNTARY_REASON", callLog_Req.getInvoluntry_Reason());
			insertDetails.setParameter("I_AGENT_ID", callLog_Req.getAgent_id());
			insertDetails.setParameter("I_SESSION_ID", callLog_Req.getSession_id());
			insertDetails.setParameter("I_OTP_DEST", callLog_Req.getOtp_dest());
			insertDetails.setParameter("DISCONNECT_REASON", callLog_Req.getDisconnect_reason());

			insertDetails.execute();

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " CallLog added successfully");

			status = GlobalConstants.SUCCESS;
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			
			throw new Exception(e.getLocalizedMessage());
		}
		return status;
	}

	@Transactional(value = "transactionManager")
	@Override
	public void insertHostTransactions(Map<String, Object> input) {

		CommonInput commonInput = (CommonInput) input.get("reqObj");

		Logger sessionLogger = input != null && commonInput.getSessionId() != null
				? CustomLogger.getLogger(commonInput.getSessionId().toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String ucid= utilities.isNullOrEmpty(commonInput.getUcid()) ? "NA" : commonInput.getUcid();
		
		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started input Params. UCID : " + ucid
				+ ", Operation Name : " + commonInput.getServiceName() + ", Service ID : " + commonInput.getServiceId()
				+ ", Status Code : " + input.get("I_STATUS_CODE").toString() + ", Description : "
				+ input.get("I_STATUS_DESCRIPTION").toString() + ", Tracking ID : " + commonInput.getTrackingId());

		try {

			// StoredProcedureQuery procedureQuery =
			// em.createNamedStoredProcedureQuery("SP_INSERT_IVR_HOST_TX")

			StoredProcedureQuery procedureQuery = em.createNamedStoredProcedureQuery("INSERT_HOST_TRANSACTION_SP")
					.setParameter("I_UCID",
							utilities.isNullOrEmpty(commonInput.getUcid()) ? "NA" : commonInput.getUcid())
					.setParameter("I_HOST_OPERATION_NAME", commonInput.getServiceName())
					.setParameter("I_SERVICE_ID", commonInput.getServiceId())
					.setParameter("I_STARTTIME", commonInput.getRequestTime())
					.setParameter("I_ENDTIME", new SimpleDateFormat(GlobalConstants.DateTimeFormat).format(new Date()))
					.setParameter("I_STATUS_CODE", input.get("I_STATUS_CODE").toString())
					.setParameter("I_STATUS_DISCRIPTION", input.get("I_STATUS_DESCRIPTION").toString())
					.setParameter("I_TRACKING_ID", commonInput.getTrackingId())
					.setParameter("I_ID_TYPE", "").setParameter("I_SEGMENT", "12")
					.setParameter("I_HOTLINE",
							utilities.isNullOrEmpty(commonInput.getHotline()) ? "" : commonInput.getHotline())
					.setParameter("I_IP_ADDRESS",
							InetAddress.getLocalHost().getHostName() == null ? "NA"
									: InetAddress.getLocalHost().getHostName())
					.setParameter("I_TP_SYSTEM",
							utilities.isNullOrEmpty(commonInput.getHost()) ? "CUPD" : commonInput.getHost());
			procedureQuery.execute();

			// sessionLogger.debug(
			// utilities.getCurrentClassAndMethodName() + " Host Transactions has been
			// inserted successfully");

		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while inserting host transactions : ", e);
		}

	}

	@Transactional(value = "transactionManager")
	@Override
	public BinMaster_Res getCardDetailsBasedOnBin(Map<String, Object> inParams) {

		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String binNumber = (String) inParams.get("binNumber");

		BinMaster_Res binMaster_Res = new BinMaster_Res();

		try {
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("GET_BIN_MASTER_DTLS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.setParameter(1, binNumber);

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();

			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {

				for (Object[] rs : resultList) {
					
					if (rs != null && rs.length >= 12) {
						binMaster_Res.setBin(new Long(rs[0].toString()));
						binMaster_Res.setCardType(rs[1].toString());
						binMaster_Res.setCardName(rs[2].toString());
						binMaster_Res.setHost(rs[3] != null ? rs[3].toString() : "");

						binMaster_Res.setCard_name_desc(rs[4] != null ? rs[4].toString() : "");
						binMaster_Res.setCard_group(rs[5] != null ? rs[5].toString() : "");
						binMaster_Res.setCard_subgroup(rs[6] != null ? rs[6].toString() : "");
						binMaster_Res.setCompany_code(rs[7] != null ? rs[7].toString() : "");

						binMaster_Res.setCurrency_code(rs[8] != null ? rs[8].toString() : "");
						binMaster_Res.setCredit_limit_flag(rs[9] != null ? rs[9].toString() : "");
						binMaster_Res.setAvail_limit_flag(rs[10] != null ? rs[10].toString() : "");
						binMaster_Res.setUbpayment_flag(rs[11] != null ? rs[11].toString() : "");
						binMaster_Res.setCardProductType(rs[12] != null ? rs[12].toString() : "");

						binMaster_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
						binMaster_Res.setErrormessage(GlobalConstants.SUCCESS);

						break;
					} else {
						binMaster_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
						binMaster_Res.setErrormessage("NO DATA FOUND");
					}
				}
			} else {
				binMaster_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				binMaster_Res.setErrormessage("NO DATA FOUND");
			}
			
			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " BinMaster Details:" + binMaster_Res);

		} catch (Exception e) {
			binMaster_Res.setErrorcode(GlobalConstants.FAILURECODE);
			binMaster_Res.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while calling getCardDetailsBasedOnBin stored procedure : ", e);
		}
		return binMaster_Res;
	}
	
	@Transactional(value = "transactionManager")
	@Override
	public AllBinDetails_Res getAllBinDetails(Map<String, Object> inParams) {

		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		AllBinDetails_Res allBinDetails_Res=new AllBinDetails_Res();
		List<BinMaster_Res> binMasterList = new ArrayList<>();

		try {
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("GET_BIN_MASTER_DTLS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.setParameter(1, "ALL");

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();

			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {

				for (Object[] rs : resultList) {
					
					if (rs != null && rs.length >= 12) {
						BinMaster_Res binMaster_Res=new BinMaster_Res();
						binMaster_Res.setBin(new Long(rs[0].toString()));
						binMaster_Res.setCardType(rs[1].toString());
						binMaster_Res.setCardName(rs[2].toString());
						binMaster_Res.setHost(rs[3] != null ? rs[3].toString() : "");

						binMaster_Res.setCard_name_desc(rs[4] != null ? rs[4].toString() : "");
						binMaster_Res.setCard_group(rs[5] != null ? rs[5].toString() : "");
						binMaster_Res.setCard_subgroup(rs[6] != null ? rs[6].toString() : "");
						binMaster_Res.setCompany_code(rs[7] != null ? rs[7].toString() : "");

						binMaster_Res.setCurrency_code(rs[8] != null ? rs[8].toString() : "");
						binMaster_Res.setCredit_limit_flag(rs[9] != null ? rs[9].toString() : "");
						binMaster_Res.setAvail_limit_flag(rs[10] != null ? rs[10].toString() : "");
						binMaster_Res.setUbpayment_flag(rs[11] != null ? rs[11].toString() : "");
						binMaster_Res.setCardProductType(rs[12] != null ? rs[12].toString() : "");

						binMasterList.add(binMaster_Res);
						
						allBinDetails_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
						allBinDetails_Res.setErrormessage(GlobalConstants.SUCCESS);

					} else {
						allBinDetails_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
						allBinDetails_Res.setErrormessage("NO DATA FOUND");
					}
				}
				
				allBinDetails_Res.setBinMasterList(binMasterList);
			} else {
				allBinDetails_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				allBinDetails_Res.setErrormessage("NO DATA FOUND");
			}
			

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " BinMaster Details:" + allBinDetails_Res);

		} catch (Exception e) {
			allBinDetails_Res.setErrorcode(GlobalConstants.FAILURECODE);
			allBinDetails_Res.setErrormessage(GlobalConstants.FAILURE);
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while calling getCardDetailsBasedOnBin stored procedure : ", e);
		}
		return allBinDetails_Res;
	}


	@Transactional(value = "transactionManager")
	@Override
	public boolean isOfflineHrs() {
		Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));
		boolean is_offline_hrs = false;
		try {

			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("FOREX_OFFLINE_HRS_CHECK");
			procedureQuery.registerStoredProcedureParameter(1, String.class, ParameterMode.OUT);

			procedureQuery.execute();
			String O_IS_OFFLINE = null;

			if (procedureQuery.getOutputParameterValue(1) != null) {
				O_IS_OFFLINE = procedureQuery.getOutputParameterValue(1).toString();

				if (O_IS_OFFLINE != null && "Y".equals(O_IS_OFFLINE)) {
					is_offline_hrs = true;
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " IsOffline Hrs : true");
				}
			}
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
		}
		return is_offline_hrs;
	}

	@Transactional(value = "transactionManager")
	@Override
	public String languageReset(Map<String, Object> requestParams) throws Exception {
		String resetStatus = "";
		Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));
		try {
			
			String cli = requestParams.get("cli") != null ? requestParams.get("cli").toString() : "";

			StoredProcedureQuery sp = em.createStoredProcedureQuery("RESET_LANGUAGE_CUPD_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.setParameter(1, cli).registerStoredProcedureParameter(2, String.class, ParameterMode.OUT);

			sp.execute();

			if (sp.getOutputParameterValue(2) != null) {
				resetStatus = sp.getOutputParameterValue(2).toString();

				if (resetStatus != null && "Success".equals(resetStatus)) {
					// sessionLogger.info("resetStatus: true");
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " ResetStatus : true");
				}
				if (resetStatus != null && "NoDataFound".equals(resetStatus)) {
					// sessionLogger.info("resetStatus: NoDataFound");
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " ResetStatus : NoDataFound");
				}
			}

		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while getting RetrieveCallLog details: ", e);
			
			throw new Exception(e.getLocalizedMessage());
		}

		return resetStatus;

	}

	@Transactional(value = "transactionManager")
	@Override
	public PublicHolidayCheck_Res getPublicHolidayDetails(String dateStr) throws Exception {

		Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));
		PublicHolidayCheck_Res res = new PublicHolidayCheck_Res();
		res.setDateStr(dateStr);
		try {

			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("PUBLIC_HOLIDAY_CHECK_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.setParameter(1, dateStr);

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {
				Object[] rs = resultList.get(0);
				if (rs != null && rs.length >= 3) {
					res.setDateStr(rs[0].toString());
					res.setHolidayDescription(rs[1].toString());
					String isHoliday = rs[2].toString();
					if ("TRUE".equalsIgnoreCase(isHoliday)) {
						res.setPublicHoliday(true);
					}
				} else {
					res.setHolidayDescription("-");
				}

				res.setErrorcode(GlobalConstants.SUCCESSCODE);
				res.setErrormessage(GlobalConstants.SUCCESS);
			} else {
				res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				res.setErrormessage("Record Not Found");
			}


		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while calling preferred language stored procedure : ", e);
			
			throw new Exception(e.getLocalizedMessage());
		}

		return res;
	}

	@Transactional(value = "transactionManager")
	@Override
	public GetConfigDetails_Res getCommonConfigDetails(String tableName) throws Exception {

		Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));
		GetConfigDetails_Res res = new GetConfigDetails_Res();

		List<CommonConfigDetails> commonConfigDtlsList = new ArrayList<CommonConfigDetails>();

		
		try {

			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("GET_COMMON_CONFIG_DTLS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.setParameter(1, tableName);

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {
				for (Object[] rs : resultList) {
					CommonConfigDetails ccDtls = new CommonConfigDetails();
					if (rs != null && rs.length > 0) {
						int i = 0;

						ccDtls.setCol1(rs[i++].toString().trim());
						ccDtls.setCol2(rs[i++].toString().trim());
						ccDtls.setCol3(rs[i++].toString().trim());
						ccDtls.setCol4(rs[i++].toString().trim());
						ccDtls.setCol5(rs[i++].toString().trim());
						ccDtls.setCol6(rs[i++].toString().trim());
						ccDtls.setCol7(rs[i++].toString().trim());
						ccDtls.setCol8(rs[i++].toString().trim());
						ccDtls.setCol9(rs[i++].toString().trim());
						ccDtls.setCol10(rs[i++].toString().trim());
						ccDtls.setCol11(rs[i++].toString().trim());
						ccDtls.setCol12(rs[i++].toString().trim());
						ccDtls.setCol13(rs[i++].toString().trim());
						ccDtls.setCol14(rs[i++].toString().trim());
						ccDtls.setCol15(rs[i++].toString().trim());
						ccDtls.setDescription(rs[i++].toString().trim());
						ccDtls.setTableName(rs[i++].toString().trim());

					} else {
						ccDtls.setTableName("-");
					}
					commonConfigDtlsList.add(ccDtls);
				}
				res.setCommonConfigDetailsList(commonConfigDtlsList);
				res.setErrorcode(GlobalConstants.SUCCESSCODE);
				res.setErrormessage(GlobalConstants.SUCCESS);
			}else {
				res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				res.setErrormessage("Record Not Found");
			}
			

		} catch (Exception e) {
			sessionLogger.debug(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while calling getCommonConfigDetails stored procedure : ", e);
			
			throw new Exception(e.getLocalizedMessage());
		}
		return res;

	}

	@Transactional(value = "transactionManager")
	@Override
	public PublicHolidayCheck_Res holidayCheck(Map<String, Object> inParams) throws Exception {

		Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		PublicHolidayCheck_Res res = new PublicHolidayCheck_Res();

		res.setDateStr(inParams.get("currentTimeStamp").toString());
		res.setHolidayDescription("NA");
		res.setPublicHoliday(false);

		try {

			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("HOLIDAY_CHECK_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.setParameter(1, inParams.get("currentTimeStamp").toString())
					.setParameter(2, inParams.get("country").toString());

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {
				Object[] rs = resultList.get(0);
				if (rs != null && rs.length >= 3) {
					res.setHolidayDescription(rs[1].toString());
					String isHoliday = rs[2].toString();
					if ("TRUE".equalsIgnoreCase(isHoliday)) {
						res.setPublicHoliday(true);
					}
				}
				res.setErrorcode(GlobalConstants.SUCCESSCODE);
				res.setErrormessage(GlobalConstants.SUCCESS);
			}else {
				res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				res.setErrormessage("Record Not Found");
			}

		} catch (Exception e) {
			
			sessionLogger.debug(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while calling holidayCheck stored procedure : ", e);
			
			throw new Exception(e.getLocalizedMessage());
		}
		return res;

	}

	@Transactional(value = "transactionManager")
	@Override
	public ServiceHours_Res getServiceHours(ServiceHours_Req req) throws Exception {
		Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		ServiceHours_Res response = new ServiceHours_Res();
		try {

			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("GET_SERVICE_HOURS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT)
//					.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT)
//					.registerStoredProcedureParameter(7, String.class, ParameterMode.OUT)
//					.registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
					.setParameter(1, req.getTableName() != null ? req.getTableName() : "-");

			procedureQuery.execute();

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			
			if (resultList != null && resultList.size() > 0) {
				Object[] rs = resultList.get(0);
				if (rs != null && rs.length > 0) {
					response.setTableName(rs[0].toString());
					response.setServiceStartTime(rs[1].toString());
					response.setServiceEndTime(rs[2].toString());
					
					if(rs[3].toString() != null && rs[3].toString().equalsIgnoreCase("Y")) {
						response.setServiceHrs(true);
					}else {
						response.setServiceHrs(false);
					}
					

					if(rs[4].toString() != null) {
						response.setGenPrompt(rs[4].toString());
					}
					
					if(rs[5].toString() != null) {
						response.setSplPrompt(rs[5].toString());
					}
					
					if(rs[6].toString() != null && rs[6].toString().equalsIgnoreCase("Y")) {
						response.setPublicHoliday(true);
					}else {
						response.setPublicHoliday(false);
					}
					
					response.setErrorcode(GlobalConstants.SUCCESSCODE);
					response.setErrormessage(GlobalConstants.SUCCESS);
					
					
					
				} else {
					sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Data Not Found in DB ");
					response.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
					response.setErrormessage("Record Not Found");
				}
			} else {
				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Data Not Found in DB ");
				response.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				response.setErrormessage("Record Not Found");
			}
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while getting getServiceHours details : ", e);
			throw new Exception(e.getLocalizedMessage());
		}
		return response;
	}

	@Transactional(value = "transactionManager")
	@Override
	public void insertAMIvrDetails(AMIvrIntraction amivr, String method) {

		Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		try {

			StoredProcedureQuery insertDetails = em.createNamedStoredProcedureQuery("INSERT_CUST_TVAL_SP")
					.setParameter("I_RELID", amivr.getRelID())
					.setParameter("I_HASHEDVAL", amivr.getHashedVal() == null ? "" : amivr.getHashedVal())
					.setParameter("I_STATUS", amivr.getStatus())
					.setParameter("I_BLOCKEDDATE",
							amivr.getBlockedDate() == null ? "" : amivr.getBlockedDate().toString())
					.setParameter("I_CREATECHANGEDATE",
							amivr.getCreatechangeDate() == null ? "" : amivr.getCreatechangeDate().toString())
					.setParameter("I_TRIES", amivr.getTries())
					.setParameter("I_UNBLOCKEDDATE",
							amivr.getUnblockedDate() == null ? "" : amivr.getUnblockedDate().toString())
					.setParameter("O_METHOD", method);
			insertDetails.execute();
		} catch (Exception e) {
			sessionLogger.error(
					utilities.getCurrentClassAndMethodName() + " Exception occurred while Inserting AMIVR details : ",
					e);
		}
	}

	@Transactional(value = "transactionManager")
	@Override
	public AMIvrIntraction getAMIvrDetails(AMIvrIntraction amivr) {
		
		org.apache.logging.log4j.Logger sessionLogger = org.apache.logging.log4j.LogManager.getContext().getLogger(GlobalConstants.HostLog_DB);

		//Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));
		AMIvrIntraction amivr1 = new AMIvrIntraction();
		try {
			StoredProcedureQuery getDetails = em.createNamedStoredProcedureQuery("GET_CUST_TVAL_SP")
					.setParameter("I_RELID", amivr.getRelID())
					//.setParameter("O_METHOD", "CHANGEVERIFY")
					;
			getDetails.execute();
			String check = null;
			if (getDetails.getOutputParameterValue("O_CHECK").toString() != null)
				check = getDetails.getOutputParameterValue("O_CHECK").toString();
			if (check.equalsIgnoreCase("DATAFOUND")) {
				
				@SuppressWarnings("unchecked")
				List<Object[]> listObj = getDetails.getResultList();
				if(listObj.size() > 0) {
					Object[] obj = (Object[]) listObj.get(0);
					
					amivr1.setRelID((String)obj[0]);
					amivr1.setHashedVal((String)obj[1]);
					amivr1.setStatus((String)obj[2]);
					amivr1.setBlockedDate((String)obj[3]);
					amivr1.setCreatechangeDate((String)obj[4]);
					amivr1.setTries((String)obj[5]);
					amivr1.setUnblockedDate((String)obj[6]);
					amivr1.setCheck(getDetails.getOutputParameterValue("O_CHECK").toString());
					
					sessionLogger.debug(
							utilities.getCurrentClassAndMethodName() + " Retrieved AMIVR details From DB successfully ");
					
				}else {
					sessionLogger.error(
							utilities.getCurrentClassAndMethodName() + " GetAMivr Status Customer Data Not Found in DB ");
					amivr1.setCheck(check);
				}

			} else {
				sessionLogger.error(
						utilities.getCurrentClassAndMethodName() + " GetAMivr Status Customer Data Not Found in DB ");
				amivr1.setCheck(check);
			}
		} catch (Exception e) {
			sessionLogger.error(
					utilities.getCurrentClassAndMethodName() + " Exception occurred while get AMIVR details : ",
					e);
		}
		return amivr1;
	}
	
	@Transactional(value = "transactionManager")
	@Override
	public AMIvrIntraction_Res getAMIvrHostRes(AMIvrIntraction amivr) throws Exception {
		Logger sessionLogger = CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));
		
		AMIvrIntraction_Res amIvrIntraction_Res = new AMIvrIntraction_Res();
		AMIvrIntraction amivr1 = new AMIvrIntraction();
		try {
			StoredProcedureQuery getDetails = em.createNamedStoredProcedureQuery("GET_CUST_TVAL_SP")
					.setParameter("I_RELID", amivr.getRelID())
					//.setParameter("O_METHOD", "CHANGEVERIFY")
					;
			getDetails.execute();
			String check = null;
			if (getDetails.getOutputParameterValue("O_CHECK").toString() != null)
				check = getDetails.getOutputParameterValue("O_CHECK").toString();
			if (check.equalsIgnoreCase("DATAFOUND")) {
				
				@SuppressWarnings("unchecked")
				List<Object[]> listObj = getDetails.getResultList();
				if(listObj.size() > 0) {
					Object[] obj = (Object[]) listObj.get(0);
					
					amivr1.setRelID((String)obj[0]);
					amivr1.setHashedVal((String)obj[1]);
					amivr1.setStatus((String)obj[2]);
					amivr1.setBlockedDate((String)obj[3]);
					amivr1.setCreatechangeDate((String)obj[4]);
					amivr1.setTries((String)obj[5]);
					amivr1.setUnblockedDate((String)obj[6]);
					amivr1.setCheck(getDetails.getOutputParameterValue("O_CHECK").toString());
					
					sessionLogger.debug(
							utilities.getCurrentClassAndMethodName() + " Retrieved AMIVR details From DB successfully ");
					

					amIvrIntraction_Res.setErrorcode(GlobalConstants.SUCCESSCODE);
					amIvrIntraction_Res.setErrormessage(GlobalConstants.SUCCESS);
					amIvrIntraction_Res.setResponse(amivr1);
				}else {
					sessionLogger.error(
							utilities.getCurrentClassAndMethodName() + " GetAMivr Status Customer Data Not Found in DB ");
					amIvrIntraction_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
					amIvrIntraction_Res.setErrormessage("Record Not Found");
				}

				
			} else {
				sessionLogger.error(
						utilities.getCurrentClassAndMethodName() + " GetAMivr Status Customer Data Not Found in DB ");
				amivr1.setCheck(check);
				
				amIvrIntraction_Res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				amIvrIntraction_Res.setErrormessage("Record Not Found");
			}
		} catch (Exception e) {
			sessionLogger.error(
					utilities.getCurrentClassAndMethodName() + " Exception occurred while get AMIVR details : ",
					e);
			
			throw new Exception(e.getLocalizedMessage());
		}
		return amIvrIntraction_Res;
	}
		
	public IncomingPoints_Res getIncomingPoints(Map<String, Object> inParams) {
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		IncomingPoints_Res res = new IncomingPoints_Res();
		List<IncomingPoints> incomingPointsList = new ArrayList<>();

		try {

			String inputParam = inParams.get("inputParam") != null
					|| !"".equalsIgnoreCase(inParams.get("inputParam").toString())
							? inParams.get("inputParam").toString()
							: "ALL";
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("GET_INCOMING_POINTS_BH_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					//.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, inputParam);

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {
				for (Object[] rs : resultList) {

					if (rs != null && rs.length > 0) {
						int i = 0;

						IncomingPoints ip = new IncomingPoints();
						ip.setId(rs[i++].toString().trim());
						ip.setApplicationType(rs[i++].toString().trim());
						ip.setCallType(rs[i++].toString().trim());
						ip.setHotlineCode(rs[i++].toString().trim());
						ip.setHotlineName(rs[i++].toString().trim());
						ip.setIvrName(rs[i++].toString().trim());
						ip.setVdn(rs[i++].toString().trim());

						incomingPointsList.add(ip);

					}
				}

				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ " Retrieved Incoming Points details From DB successfully ");

				res.setIncomingPointsList(incomingPointsList);
				res.setErrorcode(GlobalConstants.SUCCESSCODE);
				res.setErrormessage(GlobalConstants.SUCCESS);

			} else {

				sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Incoming Point Data Not Found in DB ");
				res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				res.setErrormessage("Data Not Found");
			}

		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while get Incoming Points details : ", e);

			res.setErrorcode(GlobalConstants.FAILURECODE);
			res.setErrormessage(GlobalConstants.FAILURE);

		}

		return res;
	}
	
	public TransferPoints_Res getTransferPoints(Map<String, Object> inParams) {
		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		TransferPoints_Res res = new TransferPoints_Res();
		List<TransferPoints> transferPointsList = new ArrayList<>();

		try {

			/*String inputParam = inParams.get("inputParam") != null
					|| !"".equalsIgnoreCase(inParams.get("inputParam").toString())
							? inParams.get("inputParam").toString()
							: "ALL";*/
							
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("GET_TRANSFER_POINTS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					//.registerStoredProcedureParameter(2, Class.class, ParameterMode.REF_CURSOR)
					.setParameter(1, "");

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {
				for (Object[] rs : resultList) {

					if (rs != null && rs.length > 0) {
						int i = 0;

						TransferPoints tp = new TransferPoints();
						tp.setId(rs[i++].toString().trim());
						tp.setBusinessHourFlag(rs[i++].toString().trim());
						tp.setLanguage(rs[i++].toString().trim());
						tp.setQueuePriority(rs[i++].toString().trim());
						tp.setSegment(rs[i++].toString().trim());
						tp.setService(rs[i++].toString().trim());
						tp.setTransferPoint(rs[i++].toString().trim());

						transferPointsList.add(tp);

					}
				}

				sessionLogger.debug(utilities.getCurrentClassAndMethodName()
						+ " Retrieved Incoming Points details From DB successfully ");

				res.setTransferPointsList(transferPointsList);
				res.setErrorcode(GlobalConstants.SUCCESSCODE);
				res.setErrormessage(GlobalConstants.SUCCESS);

			} else {

				sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Transfer Point Data Not Found in DB ");
				res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				res.setErrormessage("Data Not Found");
			}

		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while get Transfer Points details : ", e);

			res.setErrorcode(GlobalConstants.FAILURECODE);
			res.setErrormessage(GlobalConstants.FAILURE);

		}

		return res;
	}

	@Override
	public BusinessHrsCheck_Res checkBusinessHours(BusinessHrsCheck_Req reqObj) {
		
		Logger sessionLogger = reqObj != null && reqObj.getSessionId() != null
				? CustomLogger.getLogger(reqObj.getSessionId().toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));
				
		BusinessHrsCheck_Res res = new BusinessHrsCheck_Res();

		try {

							
			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("BUSINESS_HOURS_CHECK_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(3, String.class, ParameterMode.IN)
					.registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(5, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(6, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(7, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(9, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(10, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(11, String.class, ParameterMode.OUT)
					.registerStoredProcedureParameter(12, String.class, ParameterMode.OUT)
					.setParameter(1, reqObj.getSegment())
					.setParameter(2, reqObj.getLanguage())
					.setParameter(3, reqObj.getProduct());


			procedureQuery.execute();
			
			res.setSegment(reqObj.getSegment());
			res.setLanguage(reqObj.getLanguage());
			res.setProduct(reqObj.getProduct());
			
			if("Y".equalsIgnoreCase(procedureQuery.getOutputParameterValue(4).toString())) {
				res.setHoliday(true);
			}else {
				res.setHoliday(false);
			}
			
			if("Y".equalsIgnoreCase(procedureQuery.getOutputParameterValue(5).toString())) {
				res.setServiceHrsCheckAvailable(true);
			}else {
				res.setServiceHrsCheckAvailable(false);
			}
			
			res.setServiceHrsStartTime(procedureQuery.getOutputParameterValue(6).toString());
			res.setServiceHrsEndTime(procedureQuery.getOutputParameterValue(7).toString());
			if("Y".equalsIgnoreCase(procedureQuery.getOutputParameterValue(8).toString())) {
				res.setServiceHrs(true);
			}else {
				res.setServiceHrs(false);
			}
			
			if("Y".equalsIgnoreCase(procedureQuery.getOutputParameterValue(9).toString())) {
				res.setVDNAvailable(true);
			}else {
				res.setVDNAvailable(false);
			}
			res.setVdnRoutingData(procedureQuery.getOutputParameterValue(10).toString());
			
			res.setGenPrompt(procedureQuery.getOutputParameterValue(11).toString());
			
			res.setSplPrompt(procedureQuery.getOutputParameterValue(12).toString());

			
			sessionLogger.debug(
					utilities.getCurrentClassAndMethodName() + " Retrieved Business Hrs Check From DB successfully ");
			

			res.setErrorcode(GlobalConstants.SUCCESSCODE);
			res.setErrormessage(GlobalConstants.SUCCESS);
			
			/*@SuppressWarnings("unchecked")
			List<Object[]> listObj = procedureQuery.getResultList();
			
			
			if(listObj.size() > 0) {
				Object[] obj = (Object[]) listObj.get(0);
				
				res.setSegment((String)obj[0]);
				res.setLanguage((String)obj[1]);
				res.setProduct((String)obj[2]);
				
				if("Y".equalsIgnoreCase((String)obj[3])) {
					res.setHoliday(true);
				}else {
					res.setHoliday(false);
				}
				
				if("Y".equalsIgnoreCase((String)obj[4])) {
					res.setServiceHrsCheckAvailable(true);
				}else {
					res.setServiceHrsCheckAvailable(false);
				}
				
				res.setServiceHrsStartTime((String)obj[5]);
				res.setServiceHrsEndTime((String)obj[6]);
				if("Y".equalsIgnoreCase((String)obj[7])) {
					res.setServiceHrs(true);
				}else {
					res.setServiceHrs(false);
				}
				
				if("Y".equalsIgnoreCase((String)obj[8])) {
					res.setVDNAvailable(true);
				}else {
					res.setVDNAvailable(false);
				}
				res.setVdnRoutingData((String)obj[9]);
				
				res.setGenPrompt((String)obj[10]);
				
				res.setSplPrompt((String)obj[11]);

				
				sessionLogger.debug(
						utilities.getCurrentClassAndMethodName() + " Retrieved Business Hrs Check From DB successfully ");
				

				res.setErrorcode(GlobalConstants.SUCCESSCODE);
				res.setErrormessage(GlobalConstants.SUCCESS);
			}else {
				sessionLogger.error(
						utilities.getCurrentClassAndMethodName() + " Business Hrs Check Data Not Found in DB ");
				res.setErrorcode(GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				res.setErrormessage("Record Not Found");
			}*/

		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName()
					+ " Exception occurred while Business Hrs Check details : ", e);

			res.setErrorcode(GlobalConstants.FAILURECODE);
			res.setErrormessage(GlobalConstants.FAILURE);

		}

		return res;

	}

	@Transactional(value = "transactionManager")
	@Override
	public String insertContextCTI(ContextCTI_Req contextCTI_Req) throws Exception {

		Logger sessionLogger = contextCTI_Req != null && contextCTI_Req.getSession_id() != null
				? CustomLogger.getLogger(contextCTI_Req.getSession_id().toString())
				: CustomLogger.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		String status = "";
		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " UCID : " + contextCTI_Req.getUcid());

		try {
			StoredProcedureQuery insertDetails = em.createStoredProcedureQuery("INSERT_CTI_CONTEXT_DTLS_SP")
			.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
			.registerStoredProcedureParameter(2, String.class, ParameterMode.IN)
			.setParameter(1, contextCTI_Req.getUcid())
			.setParameter(2, contextCTI_Req.getCti_context_data() == null ? "NA" : contextCTI_Req.getCti_context_data());

			insertDetails.execute();

			sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Context CTI added successfully");

			status = GlobalConstants.SUCCESS;
		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception Occured : ", e);
			throw new Exception(e.getLocalizedMessage());
		}
		return status;
	}

	
	public Map<String, String> getContextStoreDtls(Map<String, String> inParams) {

		Logger sessionLogger = inParams != null && inParams.get("sessionId") != null
				? CustomLogger.getLogger(inParams.get("sessionId").toString())
				: CustomLogger
						.getLogger(GlobalConstants.HostLog_DB + new SimpleDateFormat("yyyyMMdd").format(new Date()));

		sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " Started: " + inParams);

		Map<String, String> contextStoreDtlsRes = new HashMap<>();

		try {

			StoredProcedureQuery procedureQuery = em.createStoredProcedureQuery("GET_CTI_CONTEXT_DTLS_SP")
					.registerStoredProcedureParameter(1, String.class, ParameterMode.IN)
					.setParameter(1, inParams.get("ucid").toString());

			@SuppressWarnings("unchecked")
			List<Object[]> resultList = procedureQuery.getResultList();
			if (resultList != null && resultList.size() > 0 && resultList.get(0) != null) {
				for (Object[] rs : resultList) {

					if (rs != null && rs.length > 0) {
						int i = 0;

						contextStoreDtlsRes.put("UCID", rs[i++].toString().trim());
						contextStoreDtlsRes.put("CREATED_ON", rs[i++].toString().trim());
						contextStoreDtlsRes.put("CONTEXT_STORE", rs[i++].toString().trim());
						
					}
				}

				sessionLogger.debug(utilities.getCurrentClassAndMethodName() + " completed");
			} else {

				sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Transfer Point Data Not Found in DB ");
				contextStoreDtlsRes.put("ERROR_CODE", GlobalConstants.ERRORCODE_RECORD_NOT_FOUND_IN_DB_700031);
				contextStoreDtlsRes.put("ERROR_MSG", "Data Not Found");
			}

		} catch (Exception e) {
			sessionLogger.error(utilities.getCurrentClassAndMethodName() + " Exception occurred: ", e);

			contextStoreDtlsRes.put("ERROR_CODE", GlobalConstants.FAILURECODE);
			contextStoreDtlsRes.put("ERROR_MSG", GlobalConstants.FAILURE);

		}
		return contextStoreDtlsRes;

	}



}
