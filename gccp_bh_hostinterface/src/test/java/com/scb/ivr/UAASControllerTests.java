package com.scb.ivr;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.scb.ivr.controller.UAASController;
import com.scb.ivr.model.uaas.GenerateOTP_Req;
import com.scb.ivr.model.uaas.GenerateOTP_Res;
import com.scb.ivr.model.uaas.ValidateOTP_Req;
import com.scb.ivr.model.uaas.ValidateOTP_Res;

/**
 * @author TA
 *
 */

/*@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

class UAASControllerTests {

	// @InjectMocks
	// UAASController controller;

//	@Before
//	public void init() {
//		MockitoAnnotations.openMocks(this);
//	}

	@ParameterizedTest(name = "{index}. MobileNumber: {0}, RelID : {1}, . {3} case")
	@CsvSource({ "+919840745994,360332842,000000,Success"})
	//@CsvSource({ "+919840745,360332842,1024,Failure"})
	//@CsvSource({ ",360332842,333333,Failure"})
	//@CsvSource({ "+919840745994,360332842,1368,Failure"})
	@DisplayName("To generate OTP - generateOTP")
	void generateOTP(String mobile, String relId, String expectedValue) throws Exception {

		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		UAASController controller = (UAASController) context.getBean("UAASController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		GenerateOTP_Req reqObj = new GenerateOTP_Req();
		GenerateOTP_Res res = new GenerateOTP_Res();
		reqObj.setMobileNumber(mobile);
		reqObj.setRelId(relId);
		reqObj.setLanguage("english");
		reqObj.setUcid(ucid);
		reqObj.setHotline("943894");
		reqObj.setSessionId(ucid);

		res = controller.generateOTP(reqObj);
		assertEquals(expectedValue, res.getErrorcode());
	}
	
	@ParameterizedTest(name = "6. RelID: {0}, otp : {1}, . {3} case")
	//@CsvSource({ "360332842,324567,000000,Success"})
	//@CsvSource({ ",324567,333333,Failure"})
	//@CsvSource({ "360332842,324567,1308,Failure"})
	//@CsvSource({ "360332842,324567,1307,Failure"})
	//@CsvSource({ "360332842,324567,1330,Failure"})
	//@CsvSource({ "360332842,324567,1368,Failure"})
	@DisplayName("To Validate OTP - validateOTP")
	void validateOTP(String relID, String otp, String expectedValue) throws Exception {

		ApplicationContext context = SpringApplication.run(SpringBootMain.class);
		UAASController controller = (UAASController) context.getBean("UAASController");
		String ucid = "0000" + new SimpleDateFormat("ddMMyyyyhhmmssss").format(new Date());

		ValidateOTP_Req reqObj = new ValidateOTP_Req();
		ValidateOTP_Res res = new ValidateOTP_Res();
		
		reqObj.setRelID(relID);
		reqObj.setUcid(ucid);
		reqObj.setHotline("943894");
		reqObj.setModulus(
				"b6b44e815345da9297e595cc88bd6ba7ac5fe4d08bb7aa795951b71b093ad0e0bde4b638570ca038ba0d73222c62286c6a926793f88ae411280c3b92ac5c79d2c142d4a600a376420e101a31936697c2adb56ee0e96be21320eacfefe7f97fd5ad0e59bbf1ff75efa2ae50b6fdb3acd46e89fd993efe4fbf775a071c19d9d694c6f97a47fdd64df0b87338aa839db9b4d5cc907de7c409c5fd7db5921123081b766f795afcb70a7fce5b953a9c4b557b5cebed277cec522fe752c09706d571ebb6620ebdece8c6cd66fc146f562dc5b674cba1361afc6bbe2f050558071b691a0e67e6e57bc9abb8ee64971c23ef14302ab27643fcfad50e123af630c9bc77d9");
		reqObj.setEncryptedBlock(
				"MTY2MTQxODQ2OTEzOV8tX1ZOVl8tX0hLXy1fMzYwMzMyODQyXy1fOTZxZzJSOTBfLV9aT1dvN3pNVmdYZVZ3TG1aRC9NcjhxR3RFd1J4U0gyWm5QNUdaUFdoblAwPQ==");
		reqObj.setExponent("10001");
		reqObj.setKeyIndex("3");
		reqObj.setOtp(otp);
		reqObj.setOtpSn("312210");
		reqObj.setSessionId(ucid);

		res = controller.validateOTP(reqObj);

		assertEquals(expectedValue, res.getErrorcode());
	}


}
*/