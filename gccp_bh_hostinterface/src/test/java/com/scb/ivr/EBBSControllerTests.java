package com.scb.ivr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized.Parameters;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scb.ivr.controller.EBBSController;
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

//@RunWith(SpringRunner.class)
/*@RunWith(SpringJUnit4ClassRunner.class)
// @RunWith(Parameterized.class)
@SpringBootTest

class EBBSControllerTests {

	// @InjectMocks
	// EBBSController controller;

	@Before
	public void init() {
		//System.setProperty("log4jpath", "");

		MockitoAnnotations.openMocks(this);
	}

	@ParameterizedTest(name = "{index}. cardNumber: {0}  and currency : {1} . {3} case")
	@CsvSource({ "01040895601,BHD,000000,Success", "010408951,BHD,111111,failure", "01040895601,CCT,111111,failure",
			",CCT,111111,failure" })
	@DisplayName("To Identify Customer using Account Number - getCustomerIdentificationAcctNum")
	
	void getCustomerIdentificationAcctNum(String id, String ccy, String expectedValue) throws Exception {

		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		EBBSController controller = (EBBSController) context.getBean("EBBSController");

		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationAcctNum_Req reqObj = new CustomerIdentificationAcctNum_Req();
		CustomerIdentificationAcctNum_Res res = new CustomerIdentificationAcctNum_Res();
		reqObj.setAcctNum(id);
		reqObj.setCurrency_code(ccy);
		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationAcctNum(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}

	@ParameterizedTest(name = "{index}. Id: {0} . {2} case")
	@CsvSource({ "860105792,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To Identify Customer using Relationship or CRP ID - getCustomerIdentificationRELCRP")
	void getCustomerIdentificationRELCRP(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationRELCRP_Req reqObj = new CustomerIdentificationRELCRP_Req();
		CustomerIdentificationRELCRP_Res res = new CustomerIdentificationRELCRP_Res();
		reqObj.setProfileId(id);
		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationRELCRP(reqObj);

		assertEquals(expectedValue, res.getErrorcode());
	}

	@ParameterizedTest(name = "{index}. Id: {0} . {2} case")
	@CsvSource({ "860105792,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("Fetch Customer Profile & Product Information - getCustomerProfileProduct")
	void getCustomerProfileProduct(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerProfileProduct_Req reqObj = new CustomerProfileProduct_Req();
		CustomerProfileProduct_Res res = new CustomerProfileProduct_Res();
		reqObj.setProfileId(id);
		reqObj.setSessionId(ucid);

		res = controller.getCustomerProfileProduct(reqObj);

		assertEquals(expectedValue, res.getErrorcode());
	}

	@ParameterizedTest(name = "{index}. Id: {0} . {2} case")
	@CsvSource({ "620634090,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To get Account Balance - getAccountBalanceCASA")
	void getAccountBalanceCASA(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		AccountBalanceCASA_Req reqObj = new AccountBalanceCASA_Req();
		AccountBalanceCASA_Res res = new AccountBalanceCASA_Res();
		reqObj.setProfileId(id);
		// reqObj.setProfileId("0100000000000082");
		reqObj.setSessionId(ucid);

		res = controller.getAccountBalanceCASA(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}

	@ParameterizedTest(name = "{index}. Id: {0} . {2} case")
	@CsvSource({ "620634090,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To get the List of Accounts - getAccountsCASA")
	void getAccountsCASA(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		AccountsCASA_Req reqObj = new AccountsCASA_Req();
		AccountsCASA_Res res = new AccountsCASA_Res();
		reqObj.setProfileId(id);
		reqObj.setSessionId(ucid);

		res = controller.getAccountsCASA(reqObj);
		assertEquals(expectedValue, res.getErrorcode());
	}

	@ParameterizedTest(name = "{index}. Mobile: {0} . {2} case")
	@CsvSource({ "97311223344,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To Identify Customer using Registered Mobile Number - getCustomerIdentificationRMN")
	void getCustomerIdentificationRMN(String mobile, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationRMN_Req reqObj = new CustomerIdentificationRMN_Req();
		CustomerIdentificationRMN_Res res = new CustomerIdentificationRMN_Res();
		reqObj.setMobile(mobile);
		// reqObj.setMobile("97311223344");

		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationRMN(reqObj);
		assertEquals(expectedValue, res.getErrorcode());

	}

	@ParameterizedTest(name = "{index}. Id: {0} . {2} case")
	@CsvSource({ "998811220,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To Identify KYC status - identifyKYC")
	void identifyKYC(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		IdentifyKYC_Req reqObj = new IdentifyKYC_Req();
		IdentifyKYC_Res res = new IdentifyKYC_Res();
		reqObj.setProfileId(id);
		reqObj.setSessionId(ucid);

		res = controller.identifyKYC(reqObj);
		assertEquals(expectedValue, res.getErrorcode());
	}

	@ParameterizedTest(name = "{index}. Id: {0} . {2} case")
	@CsvSource({ "860105792,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To get customer contact details - getCustomerContactDetails")
	void getCustomerContactDetails(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);

		EBBSController controller = (EBBSController) context.getBean("EBBSController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerContactDetails_Req reqObj = new CustomerContactDetails_Req();
		CustomerContactDetails_Res res = new CustomerContactDetails_Res();
		reqObj.setProfileId(id);
		reqObj.setSessionId(ucid);

		res = controller.getCustomerContactDetails(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}

}
*/