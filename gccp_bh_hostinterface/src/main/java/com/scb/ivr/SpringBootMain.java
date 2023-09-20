package com.scb.ivr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scb.ivr.controller.C400Controller;
import com.scb.ivr.controller.CASASController;
import com.scb.ivr.controller.ConfigController;
import com.scb.ivr.controller.DBController;
import com.scb.ivr.controller.EBBSController;
import com.scb.ivr.controller.EuronetController;
import com.scb.ivr.controller.MDISController;
import com.scb.ivr.controller.RKALEESController;
import com.scb.ivr.controller.UAASController;
import com.scb.ivr.db.entity.AMIvrIntraction;
import com.scb.ivr.db.entity.AllBinDetails_Res;
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
import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.model.c400.CreditCardBalance_Req;
import com.scb.ivr.model.c400.CreditCardBalance_Res;
import com.scb.ivr.model.c400.CreditCardList_Req;
import com.scb.ivr.model.c400.CreditCardList_Res;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Req;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Res;
import com.scb.ivr.model.c400.CustomerProfile_Req;
import com.scb.ivr.model.c400.CustomerProfile_Res;
import com.scb.ivr.model.c400.res.accbal.AccountBalanceDatum;
import com.scb.ivr.model.casas.ChangeTPIN_Req;
import com.scb.ivr.model.casas.ChangeTPIN_Res;
import com.scb.ivr.model.casas.GenerateTPIN_Req;
import com.scb.ivr.model.casas.GenerateTPIN_Res;
import com.scb.ivr.model.casas.ValidateTPIN_Req;
import com.scb.ivr.model.casas.ValidateTPIN_Res;
import com.scb.ivr.model.ebbs.AccountBalanceCASA_Req;
import com.scb.ivr.model.ebbs.AccountBalanceCASA_Res;
import com.scb.ivr.model.ebbs.AccountsCASA_Req;
import com.scb.ivr.model.ebbs.AccountsCASA_Res;
import com.scb.ivr.model.ebbs.CustomerContactDetails_Req;
import com.scb.ivr.model.ebbs.CustomerContactDetails_Res;
import com.scb.ivr.model.ebbs.CustomerIdentificationAcctNum_Req;
import com.scb.ivr.model.ebbs.CustomerIdentificationAcctNum_Res;
import com.scb.ivr.model.ebbs.CustomerIdentificationRELCRP_Req;
import com.scb.ivr.model.ebbs.CustomerIdentificationRELCRP_Res;
import com.scb.ivr.model.ebbs.CustomerIdentificationRMN_Req;
import com.scb.ivr.model.ebbs.CustomerIdentificationRMN_Res;
import com.scb.ivr.model.ebbs.CustomerProfileProduct_Req;
import com.scb.ivr.model.ebbs.CustomerProfileProduct_Res;
import com.scb.ivr.model.ebbs.IdentifyKYC_Req;
import com.scb.ivr.model.ebbs.IdentifyKYC_Res;
import com.scb.ivr.model.ebbs.res.acctbalcasa.AccountBalanceCASAdetail;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Req;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Res;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Req;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Res;
import com.scb.ivr.model.mdis.SendSMS_Req;
import com.scb.ivr.model.mdis.SendSMS_Res;
import com.scb.ivr.model.rkalees.cardLost_Req;
import com.scb.ivr.model.rkalees.cardLost_Res;
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

@SpringBootApplication
public class SpringBootMain {

	public static void main(String[] args) throws Exception {
		System.setProperty("log4jpath", "D:/Genesys/HostLogs");
		System.setProperty("logxmlPath", "D:/Subbu/config/log4j2.xml");

		ApplicationContext context = SpringApplication.run(SpringBootMain.class, args);

		SpringBootMain sb1 = new SpringBootMain();
		// String logpath = System.getenv("logPath");

		// System.setProperty("log4jpath", logpath);

		// System.setProperty("log4jpath", "C:\\Users\\1665257\\OneDrive - Standard
		// Chartered Bank\\Documents\\Raja\\BH\\logs\\");
		// System.setProperty("log4jpath",
		// "C:\\Users\\1665256\\Kalaivanan\\BH\\LOGS\\");
//		System.setProperty("log4jpath", "C:\\Users\\1636022\\Documents\\Ganesh\\BH\\Logs\\");

		/**** RKALEES ****/
//		sb1.setCardLost(context);

		/**** cards400 ****/
		// sb1.getCustomerIdentificationCardNum(context);
		// sb1.getCreditCardList(context);
		// sb1.getCreditCardBalance(context);

		// sb1.getCreditCardBalanceList(context);

		/**** EBBS ****/
		// sb1.getCustomerIdentificationAcctNum(context);
		// sb1.getCustomerIdentificationRELCRP(context);
		// sb1.getCustomerProfileProduct(context);
		// sb1.getAccountBalanceCASA(context);
		// sb1.getAccountsCASA(context);
		// sb1.getAccountsCASAList(context);
		// sb1.getCustomerIdentificationRMN(context);
		// sb1.identifyKYC(context);
		// sb1.getCustomerContactDetails(context);

		/**** Euronet ****/
		// sb1.getCustomerIdentificationDebtCardNum(context);
		// sb1.getCustomerIdentificationDebtRelId(context);

		/**** UAAS ****/
		// sb1.generateOTP(context);
		// sb1.validateOTP(context);
		// sb1.generateAPIN(context);
		// sb1.changeAPIN(context);
		// sb1.validateAPIN(context);
		// sb1.generateCCPIN(context);
		// sb1.changeCCPIN(context);
		// sb1.validateCCPIN(context);

		/**** DB ****/
		// sb1.getPreferredLanguageBasedOnCLI(context);
		// sb1.getCardDetailsBasedOnBin(context);
		// sb1.getAllBinDetails(context);
		// sb1.setPreferredLanguage(context);
		// sb1.insertCallLog(context);
		// sb1.getCommonConfigDetails(context);
		// sb1.getServiceHours(context);
		// sb1.getIncomingPoint(context);
		// sb1.getTransferPoint(context);
		// sb1.checkBusinessHours(context);

		// sb1.isOfflineHrs(context);
		// sb1.languageReset(context);
		// sb1.getPublicHolidayDetails(context);
		// sb1.holidayCheck(context);
		// sb1.getAMIvrHost(context);
		// sb1.insertContextCTI(context);
		// sb1.getContextStoreDtls(context);
		// sb1.getAMIvrHostRes(context);

		/**** CASAS ****/
		// sb1.generateTPIN(context);
		// sb1.validateTPIN(context);
		// sb1.changeTPIN(context);

		/**** MDIS ****/
		// sb1.sendSMS(context);

		/**** Config Controller ****/
		// sb1.configDynamicMenu(context);

		// sb1.test(context);

	}

	/*
	 * private void test(ApplicationContext context) throws Exception {
	 * C400Controller controller = (C400Controller)
	 * context.getBean("c400Controller"); controller.getTest();
	 * 
	 * }
	 */

	private void getCustomerIdentificationCardNum(ApplicationContext context) throws Exception {
		C400Controller controller = (C400Controller) context.getBean("c400Controller");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationCardNum_Req reqObj = new CustomerIdentificationCardNum_Req();
		CustomerIdentificationCardNum_Res res = new CustomerIdentificationCardNum_Res();
		/// reqObj.setCardNumber("5371780000198991");
		reqObj.setCardNumber("5371780000198991");
		reqObj.setSessionId(ucid);
		reqObj.setHotline("89899899001");

		res = controller.getCustomerIdentificationCardNum(reqObj);
		System.out.println("*********CustomerIdentificationCardNum Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********CustomerIdentificationCardNum Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void setCardLost(ApplicationContext context) throws Exception {
		RKALEESController controller = (RKALEESController) context.getBean("RKALEESController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		cardLost_Req reqObj = new cardLost_Req();
		cardLost_Res res = new cardLost_Res();
		reqObj.setCardNo("5371780000198991");
		reqObj.setSessionId(ucid);
		reqObj.setUcid(ucid);
		reqObj.setHotline("89899899001");
		reqObj.setUserId("100000012");
		res = controller.setCardLost(reqObj);
		System.out.println("*********setCardLost Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********setCardLost Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void getCreditCardList(ApplicationContext context) {

		C400Controller controller = (C400Controller) context.getBean("c400Controller");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CreditCardList_Req reqObj = new CreditCardList_Req();
		CreditCardList_Res res = new CreditCardList_Res();
		reqObj.setCustomer_id("810219158");
		// reqObj.setSessionId(ucid);

		res = controller.getCreditCardList(reqObj);
		System.out.println("*********CustomerIdentificationCardNum Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********CustomerIdentificationCardNum Response END:**********");
		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * List<String> dataList = Arrays.asList
		 * ("830593829","820604909","861004078","930800729","830804242","780909356",
		 * "840701683","721225047","721225047","670922595","761233997");
		 * 
		 * LinkedHashMap<String, String> linkedHM=new LinkedHashMap<>();
		 * 
		 * for (int i=0;i<dataList.size();i++) { C400Controller controller =
		 * (C400Controller) context.getBean("c400Controller"); String ucid = "0000" +
		 * new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
		 * 
		 * CreditCardList_Req reqObj = new CreditCardList_Req(); CreditCardList_Res res
		 * = new CreditCardList_Res(); reqObj.setCustomer_id(dataList.get(i));
		 * reqObj.setSessionId(ucid);
		 * 
		 * res = controller.getCreditCardList(reqObj);
		 * 
		 * if(res.getErrorcode().equalsIgnoreCase(GlobalConstants.SUCCESSCODE)) {
		 * linkedHM.put(dataList.get(i), "Success"); }else {
		 * linkedHM.put(dataList.get(i), "Failure"); } }
		 * 
		 * System.out.println(linkedHM);
		 */

	}

	private void getCreditCardBalance(ApplicationContext context) {
		C400Controller controller = (C400Controller) context.getBean("c400Controller");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CreditCardBalance_Req reqObj = new CreditCardBalance_Req();
		CreditCardBalance_Res res = new CreditCardBalance_Res();
		reqObj.setCardNumber("4138610000114465");
		reqObj.setSessionId(ucid);

		res = controller.getCreditCardBalance(reqObj);
		System.out.println("*********Credit Card Account Resource Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());

		System.out.println("*********Credit Card Account Resource Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void getCreditCardBalanceList(ApplicationContext context) {
		/*
		 * C400Controller controller = (C400Controller)
		 * context.getBean("c400Controller"); String ucid = "0000" + new
		 * SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
		 * 
		 * CreditCardBalance_Req reqObj = new CreditCardBalance_Req();
		 * CreditCardBalance_Res res = new CreditCardBalance_Res();
		 * reqObj.setCardNumber("5371780000198991"); reqObj.setSessionId(ucid);
		 * 
		 * res = controller.getCreditCardBalance(reqObj);
		 * System.out.println("*********Credit Card Resource Response:**********");
		 * System.out.println("Error code : " + res.getErrorcode());
		 * System.out.println("Error MSG : " + res.getErrormessage());
		 * 
		 * System.out.println("*********Credit Card Resource Response END:**********");
		 * 
		 * ObjectMapper obj = new ObjectMapper(); try {
		 * System.out.println(obj.writeValueAsString(res)); } catch
		 * (JsonProcessingException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */

		/*
		 * List<String>
		 * list=Arrays.asList("70515721","620634090","760314551","480309086","650010132"
		 * ,"841332940","881008389","630439524","600013839","641202083","660022320",
		 * "720615780","971204543","660008033","530207338","380107112","530032163",
		 * "440201373","600713601","671226746","830593829","820604909","861004078",
		 * "930800729","830804242","780909356","840701683","721225047","670922595",
		 * "761233997","681201746","150255225","650911660","550415572","200745030",
		 * "811116336","630634360","761210148","811113515","740246950","750594004",
		 * "650911660","550415572","200745030","700703802","460602241","200059684",
		 * "821008269","450113345","460106449","480106860","531110672","551110309",
		 * "620056304","650204115","671104667","611203219","710905424","640110061",
		 * "650017595","670800090","226449719","206160727","206251684","206253997",
		 * "490901379","500061203","460025716","260102717","480017220","330014439",
		 * "640104878","200154261","101176322","101177412","101177802","101178072",
		 * "200287261","101178852","101178902","640569820","101179042","781152615",
		 * "091103592","790182190","206406017","216292240");
		 * 
		 * HashMap<String, String> hmap = new HashMap<>(); List<String> arr = new
		 * ArrayList<>(); for(int i=0; i<list.size();i++) { C400Controller controller =
		 * (C400Controller) context.getBean("c400Controller"); String ucid = "0000" +
		 * new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
		 * 
		 * CreditCardBalance_Req reqObj = new CreditCardBalance_Req();
		 * CreditCardBalance_Res res = new CreditCardBalance_Res();
		 * reqObj.setCardNumber(list.get(i)); reqObj.setSessionId(ucid);
		 * 
		 * res = controller.getCreditCardBalance(reqObj);
		 * 
		 * StringBuilder str=new StringBuilder();
		 * if(res.getErrorcode().equals(GlobalConstants.SUCCESSCODE)) {
		 * List<AccountBalanceDatum> casaDetailsList = res.getResponse().getData();
		 * 
		 * for (AccountBalanceDatum casaDetails : casaDetailsList) {
		 * str.append(reqObj.getCardNumber()+ "\t\t");
		 * str.append("'"+casaDetails.getAttributes().getCardNum() +"\t"
		 * +"'"+casaDetails.getAttributes().getAccountNumber() +"\t"
		 * +casaDetails.getAttributes().getCurrencyCode()+"\t"
		 * +casaDetails.getAttributes().getAccountStatus()+"\t"+casaDetails.
		 * getAttributes().getProductCode()+"\t"+
		 * casaDetails.getAttributes().getProductDescription()+"\n\n"); } }
		 * 
		 * arr.add(str.toString()); //hmap.put(list.get(i), str.toString());
		 * 
		 * 
		 * } for (String s : arr) { System.out.println(s);
		 * 
		 * }
		 */

	}

	private void getCustomerProfile(ApplicationContext context) {
		C400Controller controller = (C400Controller) context.getBean("c400Controller");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerProfile_Req reqObj = new CustomerProfile_Req();
		CustomerProfile_Res res = new CustomerProfile_Res();
		reqObj.setRelId("100000012");
		reqObj.setSessionId(ucid);

		res = controller.getCustomerProfile(reqObj);
		System.out.println("*********getCustomerProfile Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********getCustomerProfile Response END:**********");

		res.getCustProduct();
		res.getCustProfile();
		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void getCustomerIdentificationAcctNum(ApplicationContext context) {
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationAcctNum_Req reqObj = new CustomerIdentificationAcctNum_Req();
		CustomerIdentificationAcctNum_Res res = new CustomerIdentificationAcctNum_Res();
		// reqObj.setAcctNum("0104089560");
		reqObj.setAcctNum("18903823601");
		// reqObj.setAcctNum("16903823607");
		reqObj.setCurrency_code("BHD");
		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationAcctNum(reqObj);
		System.out.println("*********Ebbs Customer Identification_AcctNum  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());

		/*
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getCasaCurrencyCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getCasaNumber();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getProductCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getCasaShortName();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getCasaTitle();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getSegmentCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAccountBranch();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getBranchName();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAccountOpenDate();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAccountClassCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getConsolidatedStatementFlag();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAccountClosureReason();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAccountCloseDate();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAccountCurrentStatus();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getLastDebitTransactionDate();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getLastCreditTransactionDate();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getMasterNumber();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAccountCategory();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getGlDepartmentId();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAutoClose();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getWaiverExcessInterestFlag();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getCreditInterestIndicator();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getDebitInterestIndicator();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getIncludeInterestPool();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getDebitInterestProductCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getInterestInSuspense();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getInterestCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getBulkCharges();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getAdviseInterestIndicator();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getSuppressStatement();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getCreditInterestProductCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getInterestStatementIndicator();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getSuppressAdvice();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getIgnoreChargeAdvice();
		 * res.getResponse().getData().get(0).getAttributes().getCasaProfile().
		 * getProductDescription();
		 * 
		 * res.getResponse().getData().get(0).getAttributes().getCasaCustomers().get(0).
		 * getProfileId();
		 * res.getResponse().getData().get(0).getAttributes().getCasaCustomers().get(0).
		 * getPrimaryFlag();
		 * res.getResponse().getData().get(0).getAttributes().getCasaCustomers().get(0).
		 * getFullName();
		 * 
		 * res.getResponse().getData().get(0).getAttributes().getCasaMaster().
		 * getOperatingInstructions();
		 * res.getResponse().getData().get(0).getAttributes().getCasaMaster().getIsStaff
		 * (); res.getResponse().getData().get(0).getAttributes().getCasaMaster().
		 * getIsicCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaMaster().
		 * getInstitutionalClassCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaMaster().
		 * getCustomerTypeCode();
		 * res.getResponse().getData().get(0).getAttributes().getCasaMaster().getArmName
		 * ();
		 * res.getResponse().getData().get(0).getAttributes().getCasaMaster().getArmCode
		 * (); res.getResponse().getData().get(0).getAttributes().getCasaMaster().
		 * getAccountType();
		 */

		System.out.println("*********Customer Identification_AcctNum  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void getCustomerIdentificationRELCRP(ApplicationContext context) {
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationRELCRP_Req reqObj = new CustomerIdentificationRELCRP_Req();
		CustomerIdentificationRELCRP_Res res = new CustomerIdentificationRELCRP_Res();
		// reqObj.setProfileId("860105792");
		// reqObj.setProfileId("490700306");
		// reqObj.setProfileId("510113443");
		/// Segment Code
		reqObj.setProfileId("091103592");
		// reqObj.setProfileId("510113443");
		// reqObj.setProfileId("860515974");
		// reqObj.setProfileId("871382733");
		reqObj.setSessionId(ucid);
		reqObj.setHotline("");

		res = controller.getCustomerIdentificationRELCRP(reqObj);
		System.out.println("*********Ebbs Customer IdentificationRELCRP  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());

		System.out.println("*********Customer IdentificationRELCRP  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void getCustomerProfileProduct(ApplicationContext context) {
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerProfileProduct_Req reqObj = new CustomerProfileProduct_Req();
		CustomerProfileProduct_Res res = new CustomerProfileProduct_Res();
		// reqObj.setProfileId("860105792");
		reqObj.setProfileId("810219158");
		// reqObj.setProfileId("0100000000000082");
		reqObj.setSessionId(ucid);
		reqObj.setHotline("");

		res = controller.getCustomerProfileProduct(reqObj);
		System.out.println("*********Ebbs CustomerProfileProduct  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********CustomerProfileProduct  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void getAccountBalanceCASA(ApplicationContext context) {
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		AccountBalanceCASA_Req reqObj = new AccountBalanceCASA_Req();
		AccountBalanceCASA_Res res = new AccountBalanceCASA_Res();
		reqObj.setProfileId("870515721");
		// reqObj.setProfileId("0100000000000082");
		reqObj.setSessionId(ucid);

		res = controller.getAccountBalanceCASA(reqObj);
		System.out.println("*********Ebbs AccountBalance_CASA  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********AccountBalance_CASA  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void getAccountsCASA(ApplicationContext context) {
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		AccountsCASA_Req reqObj = new AccountsCASA_Req();
		AccountsCASA_Res res = new AccountsCASA_Res();
		// reqObj.setProfileId("510113443");
		reqObj.setProfileId("850506778");
		reqObj.setSessionId(ucid);

		res = controller.getAccountsCASA(reqObj);
		System.out.println("*********Ebbs AccountS_CASA  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********AccountS_CASA  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void getAccountsCASAList(ApplicationContext context) {

		List<String> list = Arrays.asList("70515721", "620634090", "760314551", "480309086", "650010132", "841332940",
				"881008389", "630439524", "600013839", "641202083", "660022320", "720615780", "971204543", "660008033",
				"530207338", "380107112", "530032163", "440201373", "600713601", "671226746", "830593829", "820604909",
				"861004078", "930800729", "830804242", "780909356", "840701683", "721225047", "670922595", "761233997",
				"681201746", "150255225", "650911660", "550415572", "200745030", "811116336", "630634360", "761210148",
				"811113515", "740246950", "750594004", "650911660", "550415572", "200745030", "700703802", "460602241",
				"200059684", "821008269", "450113345", "460106449", "480106860", "531110672", "551110309", "620056304",
				"650204115", "671104667", "611203219", "710905424", "640110061", "650017595", "670800090", "226449719",
				"206160727", "206251684", "206253997", "490901379", "500061203", "460025716", "260102717", "480017220",
				"330014439", "640104878", "200154261", "101176322", "101177412", "101177802", "101178072", "200287261",
				"101178852", "101178902", "640569820", "101179042", "781152615", "091103592", "790182190", "206406017",
				"216292240");

		HashMap<String, String> hmap = new HashMap<>();
		List<String> arr = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			EBBSController controller = (EBBSController) context.getBean("EBBSController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			AccountsCASA_Req reqObj = new AccountsCASA_Req();
			AccountsCASA_Res res = new AccountsCASA_Res();
			// reqObj.setProfileId("510113443");
			reqObj.setProfileId(list.get(i).trim());
			reqObj.setSessionId(ucid);

			res = controller.getAccountsCASA(reqObj);
			StringBuilder str = new StringBuilder();
			if (res.getErrorcode().equals(GlobalConstants.SUCCESSCODE)) {
				List<AccountBalanceCASAdetail> casaDetailsList = res.getResponse().getData().get(0).getAttributes()
						.getResponse().getCustomerdetails().getCasadetails();

				for (AccountBalanceCASAdetail casaDetails : casaDetailsList) {
					str.append(reqObj.getProfileId() + "\t\t");
					str.append(casaDetails.getCasaNumber() + "\t" + casaDetails.getCasaCurrencyCode() + "\t"
							+ casaDetails.getAccountCurrentStatus() + "\t" + casaDetails.getProductCode() + "\t"
							+ casaDetails.getProductDescription() + "\t" + casaDetails.getPrimaryFlag() + "\t"
							+ casaDetails.getSegmentCode() + "\n\n");
				}
			}

			arr.add(str.toString());
			// hmap.put(list.get(i), str.toString());

		}
		for (String s : arr) {
			System.out.println(s);

		}
		// System.out.println("Total Data-->" + hmap);

	}

	private void getCustomerIdentificationRMN(ApplicationContext context) {
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationRMN_Req reqObj = new CustomerIdentificationRMN_Req();
		CustomerIdentificationRMN_Res res = new CustomerIdentificationRMN_Res();
		// reqObj.setMobile("1234567890");
		// reqObj.setMobile("97311223344");
		// reqObj.setMobile("11223344");
		reqObj.setMobile("9401233");

		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationRMN(reqObj);
		System.out.println("*********Ebbs CustomerIdentification_RMN  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********CustomerIdentification_RMN  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void identifyKYC(ApplicationContext context) {
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		IdentifyKYC_Req reqObj = new IdentifyKYC_Req();
		IdentifyKYC_Res res = new IdentifyKYC_Res();
		// reqObj.setProfileId("860105792");
		reqObj.setProfileId("860105792");
		reqObj.setSessionId(ucid);

		res = controller.identifyKYC(reqObj);
		System.out.println("*********Ebbs IdentifyKYC  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********IdentifyKYC  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void getCustomerContactDetails(ApplicationContext context) {
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerContactDetails_Req reqObj = new CustomerContactDetails_Req();
		CustomerContactDetails_Res res = new CustomerContactDetails_Res();
		// reqObj.setProfileId("998811220");
		reqObj.setProfileId("760314551");
		reqObj.setSessionId(ucid);

		res = controller.getCustomerContactDetails(reqObj);
		System.out.println("*********Ebbs CustomerContactDetails  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());

		System.out.println("*********CustomerContactDetails  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void getCustomerIdentificationDebtCardNum(ApplicationContext context) {
		EuronetController controller = (EuronetController) context.getBean("euronetController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationDebtCardNum_Req reqObj = new CustomerIdentificationDebtCardNum_Req();
		CustomerIdentificationDebtCardNum_Res res = new CustomerIdentificationDebtCardNum_Res();
		// reqObj.setCardNumber("4057495100234703");
		// reqObj.setCardNumber("4600416000000426");
		// reqObj.setCardNumber("4057495100234588");
		// reqObj.setCardNumber("4057495100236088");
		// reqObj.setCardNumber("4057495100236062");
		reqObj.setCardNumber("4057495011672611");
		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationDebtCardNum(reqObj);

		System.out.println("*********Euronet CustomerIdentificationDebtCardNum  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********CustomerIdentificationDebtCardNum  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void getCustomerIdentificationDebtRelId(ApplicationContext context) {
		EuronetController controller = (EuronetController) context.getBean("euronetController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationDebtRelId_Req reqObj = new CustomerIdentificationDebtRelId_Req();
		CustomerIdentificationDebtRelId_Res res = new CustomerIdentificationDebtRelId_Res();
		reqObj.setRelId("861002636");
		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationDebtRelId(reqObj);
		System.out.println("*********Euronet getCustomerIdentificationDebtRelId  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********getCustomerIdentificationDebtRelId  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	private void generateOTP(ApplicationContext context) {
		UAASController controller = (UAASController) context.getBean("UAASController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		GenerateOTP_Req reqObj = new GenerateOTP_Req();
		GenerateOTP_Res res = new GenerateOTP_Res();
		// reqObj.setMobileNumber("+919840745994");// +8613924631761
		// reqObj.setMobileNumber("+919944542347");//Vignesh
		// reqObj.setMobileNumber("+919940919798");//Dhilip
		// reqObj.setMobileNumber("+919884523196");//Srikanth
		// reqObj.setMobileNumber("+919677347714");
		reqObj.setMobileNumber("+919884716867");
		// reqObj.setMobileNumber("+919894433922");//Ganesh
		// reqObj.setRelId("04F78228121");// 000351122
		reqObj.setRelId("870515721");
		/// dummy
		// reqObj.setRelId("300585254");

		reqObj.setLanguage("E");// cantonese_mandarin_english
		reqObj.setUcid(ucid);
		reqObj.setHotline("943894");
		reqObj.setSessionId(ucid);

		System.out.println("Req Obj : " + reqObj.toString());

		res = controller.generateOTP(reqObj);
		System.out.println("*********Uaas generateOTP  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());

		System.out.println("*********generateOTP  Response END:**********");

//		System.out.println(res.getOtpResponse().getCode());
//		System.out.println(res.getOtpResponse().getEncryptedBlock());
//		System.out.println(res.getOtpResponse().getExponent());
//		System.out.println(res.getOtpResponse().getOtpSn());

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void validateOTP(ApplicationContext context) {
		UAASController controller = (UAASController) context.getBean("UAASController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		ValidateOTP_Req reqObj = new ValidateOTP_Req();
		ValidateOTP_Res res = new ValidateOTP_Res();

		/// dummy
		reqObj.setRelID("870515721");

		// reqObj.setRelID("01700416015917");
		reqObj.setUcid(ucid);
		reqObj.setHotline("943894");
		reqObj.setModulus(
				"b9b62ae9f9a363e9f5820a6e1cbb8c8af595b873b87d1a45f81ae7fc259e12642f5a00889fe1bc681ee1420223ab1147a32b67b1e25f91f3526d441cea54b40ea839f2345484f7660710d0b1253a9a4ab21638e1d7fcf27eefaae1031082b966ead994e9d2f89b3a021e8fb36cfbd54e2a5d6748c056e8c381ac3ebb098c77131f9d26c855f50b3513c07ade361e3b107146214d80d751ac5a1d940d8e32800b116ba18921cd5f7af2d2378701cef6bab28de17636492a670f1d8ad6bc2157ef084acd93932ede274d582e3088b57d0c579315966f97a9d8664cde081ff7b772e6a88c7f81ba1f8a3f235496a9ab43e0bf2a3fdd714d3495a14487c81bcc0233");
		reqObj.setEncryptedBlock(
				"MTY3MDE1MzEyMzQ2N18tX0lWUl8tX0JIXy1fODcwNTE1NzIxXy1fK2NPRDBpYlBfLV84a3RsSTl5WVpjemE1M3NDK2lsSDRDd1o2ZWFXQ3lMRzJCS2VoNVkyN2djPQ==");
		reqObj.setExponent("10001");
		reqObj.setKeyIndex("3");
		// reqObj.setOtp("288726");
		reqObj.setOtp("714209");
		reqObj.setOtpSn("10292939");
		reqObj.setSessionId(ucid);

		for (int i = 0; i < 3; i++) {
			res = controller.validateOTP(reqObj);
		}

		System.out.println("*********Uaas validateOTP  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());

		System.out.println("*********validateOTP  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void generateAPIN(ApplicationContext context) {
		/*
		 * UAASController controller = (UAASController)
		 * context.getBean("UAASController"); String ucid = "0000" + new
		 * SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
		 * 
		 * GenerateAPIN_Req reqObj = new GenerateAPIN_Req(); GenerateAPIN_Res res = new
		 * GenerateAPIN_Res(); reqObj.setUcid(ucid); reqObj.setUserId("781152615");
		 * 
		 * reqObj.setCardNo("4057495100235262"); reqObj.setOldPin("");
		 * reqObj.setNewPin("2430"); reqObj.setConfirmNewPin("2430");
		 * reqObj.setCardExpiryDate("1027");
		 * 
		 * reqObj.setDateOfBirth("");// (Format: yyyyMMdd)
		 * reqObj.setExpiryDate("1027");// (Format: yyyy-MM) reqObj.setNric("");
		 * reqObj.setEmbossedName(""); reqObj.setCreditLimit(""); reqObj.setCvv("");
		 * reqObj.setSessionId(ucid);
		 * 
		 * res = controller.generateAPIN(reqObj);
		 * System.out.println("*********Uaas generateAPIN  Response:**********");
		 * System.out.println("Error code : " + res.getErrorcode());
		 * System.out.println("Error MSG : " + res.getErrormessage());
		 * System.out.println("*********generateAPIN  Response END:**********");
		 * 
		 * ObjectMapper obj = new ObjectMapper(); try {
		 * System.out.println(obj.writeValueAsString(res)); } catch
		 * (JsonProcessingException e) { e.printStackTrace(); }
		 */

		/*
		 * List<String> listData=Arrays.asList( "650911660:4057495100234828:4861",
		 * "550415572:4057495100234836:1072", "200745030:4057495100234844:3253",
		 * "811116336:4057495100234851:1327", "630634360:4057495100234869:4614",
		 * "761210148:4057495100234877:2071", "811113515:4057495100234885:5413",
		 * "740246950:4057495100234893:3530", "750594004:4057495100234901:2581");
		 */
		/* List<String> listData=Arrays.asList("681201746:4057495100235031:0251"); */
		/*
		 * List<String> listData=Arrays.asList("700703802:4057495100234919:5910",
		 * "460602241:4057495100234927:2854", "200059684:4057495100234935:5030",
		 * "821008269:4057495100234943:0995", "450113345:4057495100234950:8423",
		 * "460106449:4057495100234968:0205", "480106860:4057495100234976:7313",
		 * "531110672:4057495100234984:7743", "551110309:4057495100234992:7432",
		 * "620056304:4057495100235007:1053", "650204115:4057495100235015:8531",
		 * "671104667:4057495100235023:3190");
		 */

		/*
		 * List<String> listData=Arrays.asList("681201746:4057495100235031:0251",
		 * "640214290:4057495100235049:2503","790182190:4057495100235056:8423",
		 * "840179073:4057495100235064:2046","280500289:4057495100235072:7651",
		 * "560108109:4057495100235080:9992","690739141:4057495100235098:0716",
		 * "001218042:4057495100235106:5991","010107657:4057495100235114:4096",
		 * "010204946:4057495100235122:5443","010901299:4057495100235130:2150",
		 * "010901728:4057495100235148:2375","021012628:4057495100235155:3251",
		 * "050202570:4057495100235163:2905","060602570:4057495100235171:6621",
		 * "091103592:4057495100235189:3693","101195252:4057495100235197:1014",
		 * "590113798:4057495100235205:7434","870502204:4057495100235213:4060",
		 * "660171970:4057495100235221:3303","730236420:4057495100235239:6637",
		 * "741017130:4057495100235247:0506","751202797:4057495100235254:3547",
		 * "781152615:4057495100235262:2430","941115518:4057495100235270:3538",
		 * "710543468:4057495100235288:8810","750161132:4057495100235296:5994",
		 * "700161732:4057495100235304:4606","790749718:4057495100235312:0702",
		 * "870103938:4057495100235320:5850","620639326:4057495100235338:4748",
		 * "830101187:4057495100235346:5049","800209486:4057495100235353:5310",
		 * "780404920:4057495100235361:7213","800610008:4057495100235379:0435",
		 * "750431458:4057495100235387:4085","720703964:4057495100235395:1540",
		 * "700500405:4057495100235403:2252","730816990:4057495100235411:6513",
		 * "780615867:4057495100235429:2782","690800916:4057495100235437:7570",
		 * "910503630:4057495100235445:0140",
		 * "500060290:4057495100235452:2457","821213695:4057495100235460:8301",
		 * "750745851:4057495100235478:6202");
		 */

		// Data6
		/*
		 * List<String> listData=Arrays.asList(
		 * "910507252:4057495100235486:1027","710905424:4057495100235494:2547",
		 * "800255992:4057495100235502:6173","461201593:4057495100235510:3422",
		 * "371100747:4057495100235528:6630","420105875:4057495100235536:9686",
		 * "680111751:4057495100235544:1152","660008033:4057495100235551:1114",
		 * "971204543:4057495100235569:2231","660600544:4057495100235577:2489",
		 * "631005242:4057495100235585:4400","570171040:4057495100235593:3153",
		 * "571015425:4057495100235601:5241","206253997:4057495100235619:1331",
		 * "680728643:4057495100235627:1010","421200995:4057495100235635:2555");
		 */

		// Data7
//		List<String> listData=Arrays.asList("150255225:4057495100235643:0145",
//				"200287261:4057495100235650:5510","380300761:4057495100235668:1416","800125827:4057495100235676:3960","620440309:4057495100235684:6422","810219158:4057495100235692:6594","720460573:4057495100235700:6443","860915727:4057495100235718:4274","510037011:4057495100235726:7609","640037526:4057495100235734:2024","460009362:4057495100235742:2130","600126617:4057495100235759:3142","640569820:4057495100235767:3757","550324496:4057495100235775:0111","760320020:4057495100235783:2349","811202160:4057495100235791:6074");

		// Data 8
//		List<String> listData=Arrays.asList("490901379:4057495100235809:1323","500061203:4057495100235817:1351",
//				"460025716:4057495100235825:2346","640110061:4057495100235833:2700",
//				"650017595:4057495100235841:3257","260102717:4057495100235858:9403",
//				"480017220:4057495100235866:2703","330014439:4057495100235874:7557");

		// Data 9
		// List<String>
		// listData=Arrays.asList("640104878:4057495100235882:9914","200154261:4057495100235890:3243");

		// Data 10
		// List<String>
		// listData=Arrays.asList("206160727:4057495100235908:8221","206251684:4057495100235916:4590");

		// Sheet 1
		List<String> listData = Arrays.asList(
		// "620634090:4057495100235924:5050",
		// "760314551:4057495100235932:5050",
		// "480309086:4057495100235940:5050",
		// "861002636:4057495011672611:3030"
		// "091103592:4057495100236286:5050"
		// "841332940:4057495100235965:5050"

		//// Prod
		// "861002636:4057495011672611:7974"
		);

		// List<String>
		// listData=Arrays.asList("091103592:4057495100236286:5050","091103592:4057495100235999:5050","201401242:4057495100236005:5050","201401242:4057495100236013:5050","201401242:4057495100236021:5050","209022605:4057495100236039:5050","209022605:4057495100236047:5050","209022605:4057495100236054:5050","209022606:4057495100236062:5050","209022606:4057495100236070:5050","200280313:4057495100236088:5050","200952826:4057495100236096:5050","880303301:4057495100236104:5050","680329501:4057495100236112:5050","209046089:4057495100236120:5050","209046858:4057495100236138:5050","209046858:4057495100236146:5050","209046858:4057495100236153:5050","570040205:4057495100236161:5050","590003615:4057495100236179:5050","206448070:4057495100236187:5050","206453899:4057495100236195:5050","680329501:4057495100236203:5050","550324496:4057495100236211:5050","850506778:4057495100236229:5050","821014129:4057495100236237:5050","410504149:4057495100236245:5050","410504149:4057495100236252:5050","206448070:4057495100236260:5050","206448070:4057495100236278:5050");

		// List<String>
		// listData=Arrays.asList("209046858:4057495100236153:5050","209046858:4057495100236138:5050","209046089:4057495100236120:5050","209046858:4057495100236146:5050");
		HashMap<String, String> hashMap = new HashMap<>();

		for (int i = 0; i < listData.size(); i++) {
			UAASController controller = (UAASController) context.getBean("UAASController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			GenerateAPIN_Req reqObj = new GenerateAPIN_Req();
			GenerateAPIN_Res res = new GenerateAPIN_Res();
			reqObj.setUcid(ucid);
			reqObj.setUserId(listData.get(i).split(":")[0]);

			reqObj.setCardNo(listData.get(i).split(":")[1]);
			reqObj.setOldPin("");
			reqObj.setNewPin(listData.get(i).split(":")[2]);
			reqObj.setConfirmNewPin(listData.get(i).split(":")[2]);
			reqObj.setCardExpiryDate("1126");

			reqObj.setDateOfBirth("");// (Format: yyyyMMdd)
			reqObj.setExpiryDate("1126");// (Format: yyyy-MM)
			reqObj.setNric("");
			reqObj.setEmbossedName("");
			reqObj.setCreditLimit("");
			reqObj.setCvv("");
			reqObj.setSessionId(ucid);

			res = controller.generateAPIN(reqObj);

			if (res.getErrorcode().equalsIgnoreCase(GlobalConstants.SUCCESSCODE)) {
				hashMap.put(listData.get(i).split(":")[1], "Success");
			} else {
				hashMap.put(listData.get(i).split(":")[1], "Failure");
			}
		}

		System.out.println("HashPIN DATA-->" + hashMap);
	}

	private void changeAPIN(ApplicationContext context) {
//		UAASController controller = (UAASController) context.getBean("UAASController");
//		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
//
//		GenerateAPIN_Req reqObj = new GenerateAPIN_Req();
//		GenerateAPIN_Res res = new GenerateAPIN_Res();
//		reqObj.setUcid(ucid);
//		reqObj.setUserId("01F403189");
//
//		reqObj.setCardNo("4057495000044103");
//		reqObj.setOldPin("123");
//		reqObj.setNewPin("1234");
//		reqObj.setConfirmNewPin("1235");
//		reqObj.setCardExpiryDate("0126");
//
//		reqObj.setDateOfBirth("1976-12-24");// (Format: yyyyMMdd)
//		reqObj.setExpiryDate("202501");// (Format: yyyyMM)
//		reqObj.setNric("BH");
//		reqObj.setEmbossedName("Name Here");
//		reqObj.setCreditLimit("");
//		reqObj.setCvv("123");
//		reqObj.setSessionId(ucid);
//
//		res = controller.changeAPIN(reqObj);
//		System.out.println("*********Uaas changeAPIN  Response:**********");
//		System.out.println("Error code : " + res.getErrorcode());
//		System.out.println("Error MSG : " + res.getErrormessage());
//		System.out.println("*********changeAPIN  Response END:**********");
//
//		ObjectMapper obj = new ObjectMapper();
//		try {
//			System.out.println(obj.writeValueAsString(res));
//		} catch (JsonProcessingException e) {
//			e.printStackTrace();
//		}

		UAASController controller = (UAASController) context.getBean("UAASController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		GenerateAPIN_Req reqObj = new GenerateAPIN_Req();
		GenerateAPIN_Res res = new GenerateAPIN_Res();
		reqObj.setUcid(ucid);
		reqObj.setUserId("01F403189");

//		reqObj.setOtpSn("143759");
//		reqObj.setEncOtp("340658");
//
//		reqObj.setModulus(
//				"b6b44e815345da9297e595cc88bd6ba7ac5fe4d08bb7aa795951b71b093ad0e0bde4b638570ca038ba0d73222c62286c6a926793f88ae411280c3b92ac5c79d2c142d4a600a376420e101a31936697c2adb56ee0e96be21320eacfefe7f97fd5ad0e59bbf1ff75efa2ae50b6fdb3acd46e89fd993efe4fbf775a071c19d9d694c6f97a47fdd64df0b87338aa839db9b4d5cc907de7c409c5fd7db5921123081b766f795afcb70a7fce5b953a9c4b557b5cebed277cec522fe752c09706d571ebb6620ebdece8c6cd66fc146f562dc5b674cba1361afc6bbe2f050558071b691a0e67e6e57bc9abb8ee64971c23ef14302ab27643fcfad50e123af630c9bc77d9");
//		reqObj.setExponent("10001");
//		reqObj.setBase64Challenge(
//				"MTY2MzEzMjczODEwM18tX0lWUl8tX0JIXy1fSVZSQkhLVVNSMDFfLV9hVzVzSUtqU18tX0lCY3dRWXlpaSthb3lHcGZUR1BiUk1SdjUvenIxcGdhY3RIbVcxZFAwN3M9");
//		reqObj.setEncryptedBlock(
//				"MTY2MzA1NzA3NjAzM18tX0lWUl8tX0JIXy1fSVZSQkhLVVNSMDFfLV9TVHp0b0xETl8tX3ZVSG9nOU1WSk9qM0JHT2hyTHZDVklBVWh2WXZZaklMVHZ4OGxPWG1lNFk9");

//		reqObj.setKeyIndex("3");
		reqObj.setCardNo("4057495000044103");
		reqObj.setOldPin("");
		reqObj.setNewPin("1235");
		reqObj.setConfirmNewPin("1235");
		reqObj.setCardExpiryDate("1226");

		reqObj.setDateOfBirth("");// (Format: yyyyMMdd)
		reqObj.setExpiryDate("1226");// (Format: yyyy-MM)
		reqObj.setNric("");
		reqObj.setEmbossedName("");
		reqObj.setCreditLimit("");
		reqObj.setCvv("");
		reqObj.setSessionId(ucid);

		res = controller.changeAPIN(reqObj);
		System.out.println("*********Uaas generateAPIN  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********generateAPIN  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void validateAPIN(ApplicationContext context) {

		/*
		 * UAASController controller = (UAASController)
		 * context.getBean("UAASController"); String ucid = "0000" + new
		 * SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
		 * 
		 * ValidateAPIN_Req reqObj = new ValidateAPIN_Req(); ValidateAPIN_Res res = new
		 * ValidateAPIN_Res(); reqObj.setUcid(ucid);
		 * 
		 * reqObj.setUserId("781152615");
		 * 
		 * reqObj.setCardNo("4057495100235262"); reqObj.setPin("2430");
		 * reqObj.setCardExpiryDate("1027");
		 * 
		 * reqObj.setDateOfBirth("");// (Format: yyyyMMdd)
		 * reqObj.setExpiryDate("1027");// (Format: yyyy-MM) reqObj.setNric("");
		 * reqObj.setEmbossedName(""); reqObj.setCreditLimit(""); reqObj.setCvv("");
		 * reqObj.setSessionId(ucid);
		 * 
		 * res = controller.validateAPIN(reqObj);
		 * System.out.println("*********Uaas validateAPIN  Response:**********");
		 * System.out.println("Error code : " + res.getErrorcode());
		 * System.out.println("Error MSG : " + res.getErrormessage());
		 * System.out.println("*********validateAPIN  Response END:**********");
		 * 
		 * ObjectMapper obj = new ObjectMapper(); try {
		 * System.out.println(obj.writeValueAsString(res)); } catch
		 * (JsonProcessingException e) { e.printStackTrace(); }
		 */

		/*
		 * List<String> listData=Arrays.asList( "650911660:4057495100234828:4861",
		 * "550415572:4057495100234836:1072", "200745030:4057495100234844:3253",
		 * "811116336:4057495100234851:1327", "630634360:4057495100234869:4614",
		 * "761210148:4057495100234877:2071", "811113515:4057495100234885:5413",
		 * "740246950:4057495100234893:3530", "750594004:4057495100234901:2581");
		 */

		/* List<String> listData=Arrays.asList("780909356:4057495100234778:0431"); */

//		List<String> listData=Arrays.asList("640214290:4057495100235049:2503");

		/*
		 * List<String> listData=Arrays.asList("681201746:4057495100235031:0251",
		 * "640214290:4057495100235049:2503","790182190:4057495100235056:8423",
		 * "840179073:4057495100235064:2046","280500289:4057495100235072:7651",
		 * "560108109:4057495100235080:9992","690739141:4057495100235098:0716",
		 * "001218042:4057495100235106:5991","010107657:4057495100235114:4096",
		 * "010204946:4057495100235122:5443","010901299:4057495100235130:2150",
		 * "010901728:4057495100235148:2375","021012628:4057495100235155:3251",
		 * "050202570:4057495100235163:2905","060602570:4057495100235171:6621",
		 * "091103592:4057495100235189:3693","101195252:4057495100235197:1014",
		 * "590113798:4057495100235205:7434","870502204:4057495100235213:4060",
		 * "660171970:4057495100235221:3303","730236420:4057495100235239:6637",
		 * "741017130:4057495100235247:0506","751202797:4057495100235254:3547",
		 * "781152615:4057495100235262:2430","941115518:4057495100235270:3538",
		 * "710543468:4057495100235288:8810","750161132:4057495100235296:5994",
		 * "700161732:4057495100235304:4606","790749718:4057495100235312:0702",
		 * "870103938:4057495100235320:5850","620639326:4057495100235338:4748",
		 * "830101187:4057495100235346:5049","800209486:4057495100235353:5310",
		 * "780404920:4057495100235361:7213","800610008:4057495100235379:0435",
		 * "750431458:4057495100235387:4085","720703964:4057495100235395:1540",
		 * "700500405:4057495100235403:2252","730816990:4057495100235411:6513",
		 * "780615867:4057495100235429:2782","690800916:4057495100235437:7570",
		 * "910503630:4057495100235445:0140",
		 * "500060290:4057495100235452:2457","821213695:4057495100235460:8301",
		 * "750745851:4057495100235478:6202");
		 */

		/*
		 * List<String> listData=Arrays.asList(
		 * "910507252:4057495100235486:1027","710905424:4057495100235494:2547",
		 * "800255992:4057495100235502:6173","461201593:4057495100235510:3422",
		 * "371100747:4057495100235528:6630","420105875:4057495100235536:9686",
		 * "680111751:4057495100235544:1152","660008033:4057495100235551:1114",
		 * "971204543:4057495100235569:2231","660600544:4057495100235577:2489",
		 * "631005242:4057495100235585:4400","570171040:4057495100235593:3153",
		 * "571015425:4057495100235601:5241","206253997:4057495100235619:1331",
		 * "680728643:4057495100235627:1010","421200995:4057495100235635:2555");
		 */

		// List<String>
		// listData=Arrays.asList("206160727:4057495100235908:8221","206251684:4057495100235916:4590");

//		List<String> listData=Arrays.asList("206160727:4057495100235908:8221");

//		List<String> listData=Arrays.asList("870515721:4057495100234711:5050");

		// Sheet 1
		List<String> listData = Arrays.asList(
				// "620634090:4057495100235924:5050",
				// "760314551:4057495100235932:5050",
				// "480309086:4057495100235940:5050"
				// "650010132:4011111111111111:5050"
				//// Prod
				"861002636:4057495011672611:7974"
		// "861002636:4057495011672611:5050"
		);

		LinkedHashMap<String, String> hamap = new LinkedHashMap<>();
		for (int i = 0; i < listData.size(); i++) {
			UAASController controller = (UAASController) context.getBean("UAASController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			ValidateAPIN_Req reqObj = new ValidateAPIN_Req();
			ValidateAPIN_Res res = new ValidateAPIN_Res();
			reqObj.setUcid(ucid);

			reqObj.setUserId(listData.get(i).split(":")[0]);

			reqObj.setCardNo(listData.get(i).split(":")[1]);
			reqObj.setPin(listData.get(i).split(":")[2]);
			reqObj.setCardExpiryDate("1126");

			reqObj.setDateOfBirth("");// (Format: yyyyMMdd)
			reqObj.setExpiryDate("1126");// (Format: yyyy-MM)
			reqObj.setNric("");
			reqObj.setEmbossedName("");
			reqObj.setCreditLimit("");
			reqObj.setCvv("");
			reqObj.setSessionId(ucid);

			res = controller.validateAPIN(reqObj);

			if (res.getErrorcode().equals(GlobalConstants.SUCCESSCODE)) {
				hamap.put(listData.get(i).split(":")[0], "Success");
			} else {
				hamap.put(listData.get(i).split(":")[0], "Failure");
			}
		}

		System.out.println(hamap);

	}

	private void generateCCPIN(ApplicationContext context) {
		UAASController controller = (UAASController) context.getBean("UAASController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		GenerateCCPIN_Req reqObj = new GenerateCCPIN_Req();
		GenerateCCPIN_Res res = new GenerateCCPIN_Res();
		reqObj.setUcid(ucid);
		reqObj.setUserId("01F403189");

		reqObj.setOtpSn("143759");
		reqObj.setEncOtp("340658");

		reqObj.setModulus(
				"b6b44e815345da9297e595cc88bd6ba7ac5fe4d08bb7aa795951b71b093ad0e0bde4b638570ca038ba0d73222c62286c6a926793f88ae411280c3b92ac5c79d2c142d4a600a376420e101a31936697c2adb56ee0e96be21320eacfefe7f97fd5ad0e59bbf1ff75efa2ae50b6fdb3acd46e89fd993efe4fbf775a071c19d9d694c6f97a47fdd64df0b87338aa839db9b4d5cc907de7c409c5fd7db5921123081b766f795afcb70a7fce5b953a9c4b557b5cebed277cec522fe752c09706d571ebb6620ebdece8c6cd66fc146f562dc5b674cba1361afc6bbe2f050558071b691a0e67e6e57bc9abb8ee64971c23ef14302ab27643fcfad50e123af630c9bc77d9");
		reqObj.setExponent("10001");
		reqObj.setBase64Challenge(
				"'MTY0NTc3NTgxNzIyMl8tX1ZOVl8tX0hLXy1fMDAwMTAwOTkwXy1fcjMvZUNiMlNfLV9BQXNYYXBIOUlyUDJIWHBpTlJSMlFNSDBYTXhVREdhRXpRZm5WSjlwV1NZPQ==");
		reqObj.setEncryptedBlock(
				"MTYxNTQ2MTAyMDIzNl8tX0lWUl8tX0NOXy1fMDE3MDA0MTYwMTU5MTdfLV90VUVaL1N1VF8tX0t2VUhIVFJVVk1zcUUvVlhYR2N4OGFlYXdpY3FzYUYwUjc2OUtoUHV5UTA9");

		reqObj.setCardNo("5336412067045212");
		reqObj.setOldPin("123");
		reqObj.setNewPin("1234");
		reqObj.setConfirmNewPin("1234");
		reqObj.setCardExpiryDate("0126");

		reqObj.setDateOfBirth("1976-12-24");// (Format: yyyyMMdd)
		reqObj.setExpiryDate("202501");// (Format: yyyyMM)
		reqObj.setNric("BH");
		reqObj.setEmbossedName("Name Here");
		reqObj.setCreditLimit("");
		reqObj.setCvv("123");
		reqObj.setSessionId(ucid);

		res = controller.generateCCPIN(reqObj);
		System.out.println("*********Uaas generateCCPIN  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********generateCCPIN  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void changeCCPIN(ApplicationContext context) {
		UAASController controller = (UAASController) context.getBean("UAASController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		GenerateCCPIN_Req reqObj = new GenerateCCPIN_Req();
		GenerateCCPIN_Res res = new GenerateCCPIN_Res();
		reqObj.setUcid(ucid);
		reqObj.setUserId("01F403189");

		reqObj.setOtpSn("143759");
		reqObj.setEncOtp("340658");

		reqObj.setModulus(
				"b6b44e815345da9297e595cc88bd6ba7ac5fe4d08bb7aa795951b71b093ad0e0bde4b638570ca038ba0d73222c62286c6a926793f88ae411280c3b92ac5c79d2c142d4a600a376420e101a31936697c2adb56ee0e96be21320eacfefe7f97fd5ad0e59bbf1ff75efa2ae50b6fdb3acd46e89fd993efe4fbf775a071c19d9d694c6f97a47fdd64df0b87338aa839db9b4d5cc907de7c409c5fd7db5921123081b766f795afcb70a7fce5b953a9c4b557b5cebed277cec522fe752c09706d571ebb6620ebdece8c6cd66fc146f562dc5b674cba1361afc6bbe2f050558071b691a0e67e6e57bc9abb8ee64971c23ef14302ab27643fcfad50e123af630c9bc77d9");
		reqObj.setExponent("10001");
		reqObj.setBase64Challenge(
				"'MTY0NTc3NTgxNzIyMl8tX1ZOVl8tX0hLXy1fMDAwMTAwOTkwXy1fcjMvZUNiMlNfLV9BQXNYYXBIOUlyUDJIWHBpTlJSMlFNSDBYTXhVREdhRXpRZm5WSjlwV1NZPQ==");
		reqObj.setEncryptedBlock(
				"MTYxNTQ2MTAyMDIzNl8tX0lWUl8tX0NOXy1fMDE3MDA0MTYwMTU5MTdfLV90VUVaL1N1VF8tX0t2VUhIVFJVVk1zcUUvVlhYR2N4OGFlYXdpY3FzYUYwUjc2OUtoUHV5UTA9");

		reqObj.setCardNo("5336412067045212");
		reqObj.setOldPin("123");
		reqObj.setNewPin("1234");
		reqObj.setConfirmNewPin("1234");
		reqObj.setCardExpiryDate("0126");

		reqObj.setDateOfBirth("1976-12-24");// (Format: yyyyMMdd)
		reqObj.setExpiryDate("202501");// (Format: yyyyMM)
		reqObj.setNric("BH");
		reqObj.setEmbossedName("Name Here");
		reqObj.setCreditLimit("");
		reqObj.setCvv("123");
		reqObj.setSessionId(ucid);

		res = controller.changeCCPIN(reqObj);
		System.out.println("*********Uaas changeCCPIN  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********changeCCPIN  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void validateCCPIN(ApplicationContext context) {
		UAASController controller = (UAASController) context.getBean("UAASController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		ValidateCCPIN_Req reqObj = new ValidateCCPIN_Req();
		ValidateCCPIN_Res res = new ValidateCCPIN_Res();
		reqObj.setUcid(ucid);
		reqObj.setUserId("01F403189");

		reqObj.setModulus(
				"b6b44e815345da9297e595cc88bd6ba7ac5fe4d08bb7aa795951b71b093ad0e0bde4b638570ca038ba0d73222c62286c6a926793f88ae411280c3b92ac5c79d2c142d4a600a376420e101a31936697c2adb56ee0e96be21320eacfefe7f97fd5ad0e59bbf1ff75efa2ae50b6fdb3acd46e89fd993efe4fbf775a071c19d9d694c6f97a47fdd64df0b87338aa839db9b4d5cc907de7c409c5fd7db5921123081b766f795afcb70a7fce5b953a9c4b557b5cebed277cec522fe752c09706d571ebb6620ebdece8c6cd66fc146f562dc5b674cba1361afc6bbe2f050558071b691a0e67e6e57bc9abb8ee64971c23ef14302ab27643fcfad50e123af630c9bc77d9");
		reqObj.setExponent("10001");
		reqObj.setBase64Challenge(
				"'MTY0NTc3NTgxNzIyMl8tX1ZOVl8tX0hLXy1fMDAwMTAwOTkwXy1fcjMvZUNiMlNfLV9BQXNYYXBIOUlyUDJIWHBpTlJSMlFNSDBYTXhVREdhRXpRZm5WSjlwV1NZPQ==");
		reqObj.setEncryptedBlock(
				"MTYxNTQ2MTAyMDIzNl8tX0lWUl8tX0NOXy1fMDE3MDA0MTYwMTU5MTdfLV90VUVaL1N1VF8tX0t2VUhIVFJVVk1zcUUvVlhYR2N4OGFlYXdpY3FzYUYwUjc2OUtoUHV5UTA9");

		reqObj.setCardNo("5336412067045212");
		reqObj.setPin("1234");
		reqObj.setCardExpiryDate("0126");

		reqObj.setDateOfBirth("1976-12-24");// (Format: yyyyMMdd)
		reqObj.setExpiryDate("202501");// (Format: yyyyMM)
		reqObj.setNric("BH");
		reqObj.setEmbossedName("Name Here");
		reqObj.setCreditLimit("");
		reqObj.setCvv("123");
		reqObj.setSessionId(ucid);

		res = controller.validateCCPIN(reqObj);
		System.out.println("*********Uaas validateCCPIN  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********validateCCPIN  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}

	private void sendSMS(ApplicationContext context) {
		MDISController controller = (MDISController) context.getBean("MDISController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		SendSMS_Req reqObj = new SendSMS_Req();
		SendSMS_Res res = new SendSMS_Res();

		reqObj.setUcid(ucid);
		reqObj.setSessionId(ucid);
		reqObj.setHotline("987897");

		reqObj.setMobileNumber("+919884716867");
		reqObj.setLanguage("ENG");
		reqObj.setSmsBody(
				"Dear Customer, your Telephone PIN (Password) has been successfully verified. Please contact us, if this was not initiated by you-StanChart");

		res = controller.sendSMS(reqObj);
		System.out.println("*********MDIS sendSMS  Response:**********");
		System.out.println("Error code : " + res.getErrorcode());
		System.out.println("Error MSG : " + res.getErrormessage());
		System.out.println("*********sendSMS  Response END:**********");

		ObjectMapper obj = new ObjectMapper();
		try {
			System.out.println(obj.writeValueAsString(res));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

	}

	public void getPreferredLanguageBasedOnCLI(ApplicationContext context) {
		System.out.println(" getPreferredLanguageBasedOnCLI Start ");

		try {

			DBController dbController = (DBController) context.getBean("DBController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("cli", "12345");
			inParams.put("sessionId", ucid);
			PrefereredLangCode_Res prefereredLangCode_Res = dbController.getPreferredLanguageBasedOnCLI(inParams);
			prefereredLangCode_Res.getErrorcode();
			prefereredLangCode_Res.getErrormessage();
			prefereredLangCode_Res.getLangCode();
			System.out.println("Prefered language based on cli : " + prefereredLangCode_Res.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPreferredLanguage(ApplicationContext context) {
		System.out.println(" insertorUpdateCustomerInfoBasedOnCLI Start ");

		try {

			DBController dbController = (DBController) context.getBean("DBController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("cli", "88888");
			inParams.put("relId", "860105792");
			inParams.put("langCode", "E");
			inParams.put("sessionId", ucid);
			PrefereredLangUpdate_Res prefereredLangUpdate_Res = dbController.setPreferredLanguage(inParams);
			prefereredLangUpdate_Res.getErrorcode();
			prefereredLangUpdate_Res.getErrormessage();
			prefereredLangUpdate_Res.getStatus();
			System.out.println("insertorUpdateCustomerInfoBasedOnCLI : " + prefereredLangUpdate_Res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertCallLog(ApplicationContext context) {
		System.out.println(" insertCallLog Start ");

		try {

			DBController dbController = (DBController) context.getBean("DBController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
			Date date = new Date();
			String starttime = formatter.format(date);

			CallLog_Req callLog_Req = new CallLog_Req();
			Date starttime_ = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(starttime);

			callLog_Req.setCli("+860000000003");
			callLog_Req.setDnis("8100005");
			callLog_Req.setStarttime(starttime_);
			callLog_Req.setEndtime(starttime_);
			callLog_Req.setUcid("11342047521604742556");
			callLog_Req.setI_rmn("NA");
			callLog_Req.set_is_rmn("Y");
			callLog_Req.set_bank_card_loan("C");
			callLog_Req.setCustomer_segment("Premium");
			callLog_Req.setTransfer_or_disc("DISC");
			callLog_Req.setSeg_code("NA");

			callLog_Req.setAcc_card_id("NA");
			callLog_Req.setRel_id("0100000000000082");
			callLog_Req.setBlock_code("B");
			callLog_Req.setLanguage("E");
			callLog_Req.setContext_id("UvQl78dbQACyLPdldxhuGw");

			callLog_Req.setLastmenu("NON_RMN_MAINMENU");
			callLog_Req.setTransfer_attributes("8001004");
			callLog_Req.setConutry("BH");
			callLog_Req.setOne_fa("CCPIN");
			callLog_Req.setTwo_fa("OTP");
			callLog_Req.setVerified_by("CCPIN");
			callLog_Req.setIdentified_by("CC");
			callLog_Req.setMenu_traverse(
					":LANGMENU:TRANSFER_MENU:EMERGENCY_TRANSFER_MENU:TRANSFER_MENU:MNU_MSGCHK_02:NON_RMN_MAINMENU");
			callLog_Req.setChannel("IVR");
			callLog_Req.setInvoluntry_Reason("NA");
			callLog_Req.setAgent_id("8001004");
			callLog_Req.setSession_id(ucid);

			callLog_Req.setOtp_dest("NA");
			callLog_Req.setDisconnect_reason("NA");

			CallLogUpdate_Res callLogUpdate_Res = dbController.insertCallLog(callLog_Req);

			callLogUpdate_Res.getErrorcode();
			callLogUpdate_Res.getErrormessage();
			callLogUpdate_Res.getStatus();

			System.out.println("Status: " + callLogUpdate_Res);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getCardDetailsBasedOnBin(ApplicationContext context) {
		System.out.println(" getCardDetailsBasedOnBin Start ");

		try {

			DBController dbController = (DBController) context.getBean("DBController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("binNumber", "484081");
			inParams.put("sessionId", ucid);
			BinMaster_Res binMaster_Res = dbController.getCardDetailsBasedOnBin(inParams);
			binMaster_Res.getErrorcode();
			binMaster_Res.getErrormessage();
			System.out.println("getCardDetailsBasedOn Bin : " + binMaster_Res.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getAllBinDetails(ApplicationContext context) {
		System.out.println(" getCardDetailsBasedOnBin Start ");

		try {

			DBController dbController = (DBController) context.getBean("DBController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("sessionId", ucid);
			AllBinDetails_Res allBinDetails_Res = dbController.getAllBinDetails(inParams);
			System.out.println("getAllBinDetails : " + allBinDetails_Res.toString());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void isOfflineHrs(ApplicationContext context) {
		System.out.println("Is Offline Hrs");

		try {
			DBController dbController = (DBController) context.getBean("DBController");

			boolean isOfflineHrs = dbController.isOfflineHrs();

			System.out.println("Is Offline Hrs : " + isOfflineHrs);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void languageReset(ApplicationContext context) {

		System.out.println("Language Reset");

		try {
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
			DBController dbController = (DBController) context.getBean("DBController");

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("cli", "12346");
			inParams.put("sessionId", ucid);
			LanguageResetStatus_Res languageResetStatus_Res = dbController.languageReset(inParams);

			System.out.println("Language reset status : " + languageResetStatus_Res);

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(languageResetStatus_Res));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getPublicHolidayDetails(ApplicationContext context) {
		System.out.println("Public Holiday Details");

		try {
			DBController dbController = (DBController) context.getBean("DBController");

			PublicHolidayCheck_Res holidayCheckRes = dbController.getPublicHolidayDetails("2022-04-01");

			System.out.println("Public Holiday Details : " + holidayCheckRes.toString());

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(holidayCheckRes));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getCommonConfigDetails(ApplicationContext context) {
		System.out.println("Common Config Details");

		try {
			DBController dbController = (DBController) context.getBean("DBController");

			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("sessionId", ucid);
			inParams.put("tableName", "MSGFLAG,DATEF");
			GetConfigDetails_Res configDetailsRes = dbController.getCommonConfigDetails(inParams);

			System.out.println("Common Config Details : " + configDetailsRes.toString());

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(configDetailsRes));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

			if ("000000".equalsIgnoreCase(configDetailsRes.getErrorcode())) {
				System.out.println("Okay");
			} else if ("700032".equalsIgnoreCase(configDetailsRes.getErrorcode())) {
				System.out.println("NotOkay");
			} else {
				System.out.println("else");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void holidayCheck(ApplicationContext context) {
		System.out.println("Holiday Check");

		try {
			DBController dbController = (DBController) context.getBean("DBController");

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("currentTimeStamp", "25-12-2021");
			inParams.put("country", "");

			PublicHolidayCheck_Res holidayCheckRes = dbController.holidayCheck(inParams);

			System.out.println("Holiday Check : " + holidayCheckRes.toString());

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(holidayCheckRes));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void getServiceHours(ApplicationContext context) {
		System.out.println("Service Hrs");

		try {
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
			DBController dbController = (DBController) context.getBean("DBController");

			ServiceHours_Req serviceHrsReq = new ServiceHours_Req();

			serviceHrsReq.setTableName("SERVICE_HOURS");
			serviceHrsReq.setSessionId(ucid);

			ServiceHours_Res serviveHrsRes = dbController.getServiceHours(serviceHrsReq);

			System.out.println("Service Hrs : " + serviveHrsRes.toString());

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(serviveHrsRes));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getAMIvrHost(ApplicationContext context) {
		System.out.println("get TPIN Details");

		try {
			DBController dbController = (DBController) context.getBean("DBController");

			AMIvrIntraction amIvrIntraction = new AMIvrIntraction();
			// amIvrIntraction.setRelID("620634090");
			amIvrIntraction.setRelID("123456");

			AMIvrIntraction amIvrIntraction_Res = dbController.getAMIvrHost(amIvrIntraction);

			System.out.println("get TPIN Details : " + amIvrIntraction_Res.toString());

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(amIvrIntraction_Res));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getAMIvrHostRes(ApplicationContext context) {
		System.out.println("get TPIN Details");

		try {
			DBController dbController = (DBController) context.getBean("DBController");

			AMIvrIntraction amIvrIntraction = new AMIvrIntraction();
			amIvrIntraction.setRelID("620634090");

			AMIvrIntraction_Res amIvrIntraction_Res = dbController.getAMIvrHostRes(amIvrIntraction);

			System.out.println("get TPIN Details : " + amIvrIntraction_Res.toString());

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(amIvrIntraction_Res));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getIncomingPoint(ApplicationContext context) {
		System.out.println("get Incoming Point Details");

		try {
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
			DBController dbController = (DBController) context.getBean("DBController");

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("inputParam", "8810123");
			inParams.put("sessionId", ucid);

			IncomingPoints_Res incomingPoints_Res = dbController.getIncomingPoints(inParams);

			System.out.println("get Incoming Point Details : " + incomingPoints_Res.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getTransferPoint(ApplicationContext context) {
		System.out.println("get Transfer Point Details");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		try {
			DBController dbController = (DBController) context.getBean("DBController");

			Map<String, Object> inParams = new HashMap<String, Object>();
			inParams.put("sessionId", ucid);
			TransferPoints_Res transferPoints_Res = dbController.getTransferPoints(inParams);

			System.out.println("get Transfer Point Details : " + transferPoints_Res.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void checkBusinessHours(ApplicationContext context) {
		System.out.println("get Check Business Hours Details");

		try {
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
			DBController dbController = (DBController) context.getBean("DBController");

			BusinessHrsCheck_Req reqObj = new BusinessHrsCheck_Req();
			reqObj.setSegment("PriorityBanking");
			reqObj.setLanguage("English");
			reqObj.setProduct("Staff");
			reqObj.setSessionId(ucid);

			BusinessHrsCheck_Res businessHrsCheck_Res = dbController.checkBusinessHours(reqObj);

			System.out.println("get Check Business Hours Details : " + businessHrsCheck_Res.toString());

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(businessHrsCheck_Res));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void insertContextCTI(ApplicationContext context) {
		System.out.println(" insertCallLog Start ");

		try {

			DBController dbController = (DBController) context.getBean("DBController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			ContextCTI_Req contextCTI_Req = new ContextCTI_Req();

			contextCTI_Req.setUcid(ucid);
			contextCTI_Req.setSession_id(ucid);
			contextCTI_Req.setCti_context_data(
					"{ \"CLI\": \"NA\", \"RMN\": \"NA\", \"HOTLINE\": \"8100064\", \"DNIS\": \"NA\", \"REL_ID\": \"630439524\", \"IDENTF_TYPE\": \"NA\", \"IDENTF_NUM\": \"NA\", \"CPR_ID\": \"NA\", \"1FA\": \"OTP|S\", \"2FA\": \"NA|NA\", \"AVL_AUTH_TYPE\": \"TPIN|\", \"GENDER\": \"NA\", \"DOB\": \"1963-04-27\", \"NATIONALITY\": \"HONGKONG\", \"LANG\": \"E|NO\", \"2PLUSONE\": \"N\", \"SCORE\": \"NA\", \"KYC\" : \"Y\", \"TRF\": \"NA\", \"RESIDENCE\": \"HK\", \"SALES_PITCH\": \"NA\", \"CBFAILURE\": \"NA\", \"IDENTIFIED_BY\": \"NA\", \"INTENT\": \"|NA\", \"IVR_TIME\": 0, \"QUEUE_TIME\": \"\", \"SEGMENT\": \"PER\", \"IVR_ENDTIME\": \"2022-10-19 13:02:47\", \"CALLBACK\": \"NA\", \"UPDATE_TIME\": \"2022-09-06 14:01:11\", \"COMPLAINT\": \"NA\", \"LAST_ACCESS_MNU\" : \"\" }");

			ContextCTI_Res contextCTI_Res = dbController.insertContextCTI(contextCTI_Req);

			contextCTI_Res.getErrorcode();
			contextCTI_Res.getErrormessage();
			contextCTI_Res.getStatus();

			System.out.println("Status: " + contextCTI_Res);
			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(contextCTI_Res));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void getContextStoreDtls(ApplicationContext context) {
		System.out.println(" insertCallLog Start ");

		try {

			DBController dbController = (DBController) context.getBean("DBController");
			String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
			String sessionId = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

			HashMap<String, String> inParams = new HashMap<>();

			inParams.put("ucid", "10001004361668235983");
			inParams.put("sessionId", sessionId);
			Map<String, String> resObj = dbController.getContextStoreDtls(inParams);

			String ucidData = resObj.get("UCID");
			String createdOn = resObj.get("CREATED_ON");
			String contextStore = resObj.get("CONTEXT_STORE");

			System.out.println("Response: " + resObj);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateTPIN(ApplicationContext context) {

		GenerateTPIN_Req requestDetails = new GenerateTPIN_Req();
		GenerateTPIN_Res responseDetails = new GenerateTPIN_Res();
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
		try {

			CASASController casasController = (CASASController) context.getBean("CASASController");

			requestDetails.setUserid("209022606");
			String pass = DigestUtils.sha256Hex("1234");
			requestDetails.setPassword(pass);
			requestDetails.setSessionId(ucid);
			requestDetails.setUcid(ucid);
			requestDetails.setHotline("943894");

			responseDetails = casasController.generateTPIN(requestDetails);

			System.out.println("*********CASAS GenerateTPIN  Response:**********");
			System.out.println("Error code : " + responseDetails.getErrorcode());
			System.out.println("Error MSG : " + responseDetails.getErrormessage());
			System.out.println("*********CASAS GenerateTPIN  Response END:**********");

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(responseDetails));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void changeTPIN(ApplicationContext context) {

		ChangeTPIN_Req requestDetails = new ChangeTPIN_Req();
		ChangeTPIN_Res responseDetails = new ChangeTPIN_Res();
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
		try {

			CASASController casasController = (CASASController) context.getBean("CASASController");

			requestDetails.setUserid("781152615");
			String pass = DigestUtils.sha256Hex("1234");
			requestDetails.setOldPassword(pass);
			String pass1 = DigestUtils.sha256Hex("1235");
			requestDetails.setNewPassword(pass1);
			requestDetails.setSessionId(ucid);
			requestDetails.setUcid(ucid);
			requestDetails.setHotline("943894");

			responseDetails = casasController.changeTPIN(requestDetails);

			System.out.println("*********CASAS ChangeTPIN  Response:**********");
			System.out.println("Error code : " + responseDetails.getErrorcode());
			System.out.println("Error MSG : " + responseDetails.getErrormessage());
			System.out.println("*********CASAS ChangeTPIN  Response END:**********");

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(responseDetails));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public void validateTPIN(ApplicationContext context) {

		ValidateTPIN_Req requestDetails = new ValidateTPIN_Req();
		ValidateTPIN_Res responseDetails = new ValidateTPIN_Res();
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());
		try {

			CASASController casasController = (CASASController) context.getBean("CASASController");

			requestDetails.setUserid("78115261531");
			String pass = DigestUtils.sha256Hex("1234");
			requestDetails.setPassword(pass);
			requestDetails.setSessionId(ucid);
			requestDetails.setUcid(ucid);
			requestDetails.setHotline("943894");

			responseDetails = casasController.validateTPIN(requestDetails);

			System.out.println("*********CASAS ValidateTPIN  Response:**********");
			System.out.println("Error code : " + responseDetails.getErrorcode());
			System.out.println("Error MSG : " + responseDetails.getErrormessage());
			System.out.println("*********CASAS ValidateTPIN  Response END:**********");

			ObjectMapper obj = new ObjectMapper();
			try {
				System.out.println(obj.writeValueAsString(responseDetails));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void configDynamicMenu(ApplicationContext context) {
		ConfigController controller = (ConfigController) context.getBean("configController");
		Map<String, Map<String, String>> mapData = (Map<String, Map<String, String>>) controller
				.getConfigFileValues("menu.json");

		Map<String, String> mapSubData = mapData.get("BH_MN_0004");
		System.out.println("Data---->" + mapSubData.toString());
		String bargain = mapSubData.get("PROMPTS");
		System.out.println("Data---->" + bargain);

	}

}
