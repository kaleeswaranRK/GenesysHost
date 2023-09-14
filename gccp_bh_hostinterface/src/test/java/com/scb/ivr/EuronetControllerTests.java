/**
 * 
 */
package com.scb.ivr;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;

import com.scb.ivr.controller.EuronetController;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Req;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Res;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Req;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Res;

/**
 * @author TA
 *
 */
/*
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest

class EuronetControllerTests {

	// @InjectMocks
	// EuronetController EuronetController;

	@Before
	public void init() {
		//System.setProperty("log4jpath", "");
		MockitoAnnotations.openMocks(this);
	}
    
    @Autowired
    ApplicationContext context;
    

	@ParameterizedTest(name = "{index}. cardNumber: {0} . {2} case")
	@CsvSource({ "4600416000000426,000000,Success","5543520000001234,000014,Failure", ",700011,Failure"})
	@DisplayName("To Identify Customer using Debit Card Number - getCustomerIdentificationDebtCardNum")
	//@Test
	void getCustomerIdentificationDebtCardNum(String CardNumber, String expectedValue) throws Exception {

		EuronetController controller = Mockito.mock(EuronetController.class);

//		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
//		EuronetController controller = (EuronetController) context.getBean("euronetController");

		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationDebtCardNum_Req reqObj = new CustomerIdentificationDebtCardNum_Req();
		CustomerIdentificationDebtCardNum_Res res = new CustomerIdentificationDebtCardNum_Res();
		reqObj.setCardNumber(CardNumber);
		reqObj.setUserId("TESTUSER");
		reqObj.setSessionId(ucid);

	    Mockito.when(controller.getCustomerIdentificationDebtCardNum(reqObj)).thenReturn(null);
	    controller = context.getBean(EuronetController.class);


		res = controller.getCustomerIdentificationDebtCardNum(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}

	
	@ParameterizedTest(name = "{index}. relID: {0} . {2} case")
	@CsvSource({ "700803343,000000,Success","555435200,000025,Failure", ",700011,Failure"})
	@DisplayName("To Identify Customer using Relationship Id - getCustomerIdentificationDebtRelId")
	
	void getCustomerIdentificationDebtRelId(String relID, String expectedValue) throws Exception {

		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		EuronetController controller = (EuronetController) context.getBean("euronetController");

		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		CustomerIdentificationDebtRelId_Req reqObj = new CustomerIdentificationDebtRelId_Req();
		CustomerIdentificationDebtRelId_Res res = new CustomerIdentificationDebtRelId_Res();
		reqObj.setRelId(relID);
		reqObj.setSessionId(ucid);

		res = controller.getCustomerIdentificationDebtRelId(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}
}
*/