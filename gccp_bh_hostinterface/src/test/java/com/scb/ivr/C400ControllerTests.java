package com.scb.ivr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scb.ivr.controller.C400Controller;
import com.scb.ivr.model.c400.CreditCardBalance_Req;
import com.scb.ivr.model.c400.CreditCardBalance_Res;
import com.scb.ivr.model.c400.CreditCardList_Req;
import com.scb.ivr.model.c400.CreditCardList_Res;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Req;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Res;
import com.scb.ivr.model.c400.CustomerProfile_Req;
import com.scb.ivr.model.c400.CustomerProfile_Res;

//@RunWith(SpringRunner.class)
/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

class C400ControllerTests {

	// @InjectMocks
	// C400Controller controller;
	
	// //@Spy
	// @Mock
	// Utilities utilities;
	
	// @Mock
	// ValidateInputDetails validateInputDetails;

	@Before
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@ParameterizedTest(name = "{index}. CardNumber: {0} . {2} case")
	@CsvSource({ "5371780000198991,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To Identify Customer using Credit Card Number - getCustomerIdentificationCardNum")
	void getCustomerIdentificationCardNum(String id, String expectedValue) throws Exception {

		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		C400Controller controller = (C400Controller) context.getBean("c400Controller");

		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationCardNum_Req reqObj = new CustomerIdentificationCardNum_Req();
		CustomerIdentificationCardNum_Res res = new CustomerIdentificationCardNum_Res();

		reqObj.setCardNumber(id);
		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationCardNum(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}

	@ParameterizedTest(name = "{index}. CardNumber: {0} . {2} case")
	@CsvSource({ "5371780000198991,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To get List of Credit Cards - getCreditCardList")
	void getCreditCardList(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		C400Controller controller = (C400Controller) context.getBean("c400Controller");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CreditCardList_Req reqObj = new CreditCardList_Req();
		CreditCardList_Res res = new CreditCardList_Res();
		reqObj.setCardNumber(id);
		reqObj.setSessionId(ucid);

		res = controller.getCreditCardList(reqObj);
		assertEquals(expectedValue, res.getErrorcode());

	}

	@ParameterizedTest(name = "{index}. CardNumber: {0} . {2} case")
	@CsvSource({ "5371780000198991,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To get Credit Card Balance - getCreditCardBalance")
	void getCreditCardBalance(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		C400Controller controller = (C400Controller) context.getBean("c400Controller");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CreditCardBalance_Req reqObj = new CreditCardBalance_Req();
		CreditCardBalance_Res res = new CreditCardBalance_Res();
		reqObj.setCardNumber(id);
		reqObj.setSessionId(ucid);

		res = controller.getCreditCardBalance(reqObj);

		assertEquals(expectedValue, res.getErrorcode());
	}

	@ParameterizedTest(name = "{index}. Id : {0} . {2} case")
	@CsvSource({ "100000012,000000,Success", "01040895661,111111,failure", ",111111,failure" })
	@DisplayName("To Fetch Customer Profile & Product Information - getCustomerProfile")
	void getCustomerProfile(String id, String expectedValue) {
		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		C400Controller controller = (C400Controller) context.getBean("c400Controller");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerProfile_Req reqObj = new CustomerProfile_Req();
		CustomerProfile_Res res = new CustomerProfile_Res();
		reqObj.setRelId(id);
		reqObj.setSessionId(ucid);

		res = controller.getCustomerProfile(reqObj);
		assertEquals(expectedValue, res.getErrorcode());

	}

}*/
