/**
 * 
 */
package com.scb.ivr;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;
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

import com.scb.ivr.controller.CASASController;
import com.scb.ivr.model.casas.ChangeTPIN_Req;
import com.scb.ivr.model.casas.ChangeTPIN_Res;
import com.scb.ivr.model.casas.GenerateTPIN_Req;
import com.scb.ivr.model.casas.GenerateTPIN_Res;
import com.scb.ivr.model.casas.ValidateTPIN_Req;
import com.scb.ivr.model.casas.ValidateTPIN_Res;

/**
 * @author TA
 *
 */
/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

class CASASControllerTests {

	// @InjectMocks
	// CASASController controller;

//	@Before
//	public void init() {
//		MockitoAnnotations.openMocks(this);
//	}

	@ParameterizedTest(name = "{index}. userId: {0}, password : {1}, . {3} case")
	@CsvSource({ "620634090,1234,000000,Success" , ",1234,111111,Failure"})
	@DisplayName("To generate TPIN - generateTPIN")
	void generateTPIN(String userId, String passwd, String expectedValue) throws Exception {

		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		CASASController controller = (CASASController) context.getBean("CASASController");

		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		GenerateTPIN_Req reqObj = new GenerateTPIN_Req();
		GenerateTPIN_Res res = new GenerateTPIN_Res();
		
		reqObj.setUserid(userId);
		String pass = DigestUtils.sha256Hex(passwd);
		reqObj.setPassword(pass);
		reqObj.setSessionId(ucid);
		reqObj.setUcid(ucid);
		reqObj.setHotline("943894");

		res = controller.generateTPIN(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}

	@ParameterizedTest(name = "{index}. userId: {0}, password : {1}, . {3} case")
	@CsvSource({ "620634090,1234,000000,Success" , "620634090,1111,000118,Failure" , ",1234,111111,Failure"})
	@DisplayName("To Validae TPIN - validateTPIN")
	void validateTPIN(String userId, String passwd, String expectedValue) throws Exception {

		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		CASASController controller = (CASASController) context.getBean("CASASController");

		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		ValidateTPIN_Req reqObj = new ValidateTPIN_Req();
		ValidateTPIN_Res res = new ValidateTPIN_Res();
		
		reqObj.setUserid(userId);
		String pass = DigestUtils.sha256Hex(passwd);
		reqObj.setPassword(pass);
		reqObj.setSessionId(ucid);
		reqObj.setUcid(ucid);
		reqObj.setHotline("943894");
		
		res = controller.validateTPIN(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}
	
	@ParameterizedTest(name = "{index}. userId: {0}, password : {1}, . {3} case")
	@CsvSource({ "620634090,1234,4567,000000,Success" , "620634090,1111,4567,000118,Failure" , ",1234,4567,111111,Failure"})
	@DisplayName("To Change TPIN - changeTPIN")
	void changeTPIN(String userId, String oldPasswd, String newPasswd, String expectedValue) throws Exception {

		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		CASASController controller = (CASASController) context.getBean("CASASController");

		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		ChangeTPIN_Req reqObj = new ChangeTPIN_Req();
		ChangeTPIN_Res res = new ChangeTPIN_Res();
		
		reqObj.setUserid(userId);
		String pass = DigestUtils.sha256Hex(oldPasswd);
		reqObj.setOldPassword(pass);
		String pass1 = DigestUtils.sha256Hex(newPasswd);
		reqObj.setNewPassword(pass1);
		reqObj.setSessionId(ucid);
		reqObj.setUcid(ucid);
		reqObj.setHotline("943894");
		
		res = controller.changeTPIN(reqObj);

		assertEquals(expectedValue, res.getErrorcode());

	}
}
*/