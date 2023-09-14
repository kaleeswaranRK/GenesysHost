package com.scb.ivr.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.scb.ivr.global.constants.GlobalConstants;
import com.scb.ivr.model.c400.CreditCardBalance_Req;
import com.scb.ivr.model.c400.CreditCardBalance_Res;
import com.scb.ivr.model.c400.CreditCardList_Req;
import com.scb.ivr.model.c400.CreditCardList_Res;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Req;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Res;
import com.scb.ivr.model.c400.CustomerProfile_Req;
import com.scb.ivr.model.c400.CustomerProfile_Res;
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
import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Req;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Res;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Req;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Res;
import com.scb.ivr.model.mdis.SendSMS_Req;
import com.scb.ivr.model.mdis.SendSMS_Res;
import com.scb.ivr.model.uaas.ChangeAPIN_Req;
import com.scb.ivr.model.uaas.ChangeAPIN_Res;
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

@Component
public class ValidateInputDetails {

	@Autowired
	Utilities utilities;

	public CustomerIdentificationCardNum_Res getCustomerIdentificationCardNum(CustomerIdentificationCardNum_Req req) {

		CustomerIdentificationCardNum_Res resObj = new CustomerIdentificationCardNum_Res();
		if (utilities.isNullOrEmpty(req.getCardNumber())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Card number is Null/Empty");
		}
		return resObj;
	}

	public CreditCardList_Res getCreditCardList(CreditCardList_Req req) {
		CreditCardList_Res resObj = new CreditCardList_Res();
		if (utilities.isNullOrEmpty(req.getCustomer_id())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Customer Id is Null/Empty");
		}
		return resObj;
	}

	public CreditCardBalance_Res getCreditCardBalance(CreditCardBalance_Req req) {
		CreditCardBalance_Res resObj = new CreditCardBalance_Res();
		if (utilities.isNullOrEmpty(req.getCardNumber())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Card number is Null/Empty");
		}
		return resObj;
	}
	
	public CustomerProfile_Res getCustomerProfile(CustomerProfile_Req req) {
		CustomerProfile_Res resObj = new CustomerProfile_Res();
		if (utilities.isNullOrEmpty(req.getRelId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". RelID is Null/Empty");
		}
		return resObj;
	}

	public CustomerIdentificationAcctNum_Res getCustomerIdentificationAcctNum(CustomerIdentificationAcctNum_Req req) {
		CustomerIdentificationAcctNum_Res resObj = new CustomerIdentificationAcctNum_Res();
		if (utilities.isNullOrEmpty(req.getAcctNum())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Account number is Null/Empty");
			return resObj;
		}

		if (utilities.isNullOrEmpty(req.getCurrency_code())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Currency Code is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	public CustomerIdentificationRELCRP_Res getCustomerIdentificationRELCRP(CustomerIdentificationRELCRP_Req req) {
		CustomerIdentificationRELCRP_Res resObj = new CustomerIdentificationRELCRP_Res();
		if (utilities.isNullOrEmpty(req.getProfileId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Profile ID is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public CustomerProfileProduct_Res getCustomerProfileProduct(CustomerProfileProduct_Req req) {
		CustomerProfileProduct_Res resObj = new CustomerProfileProduct_Res();
		if (utilities.isNullOrEmpty(req.getProfileId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Profile ID is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public AccountBalanceCASA_Res getAccountBalanceCASA(AccountBalanceCASA_Req req) {
		AccountBalanceCASA_Res resObj = new AccountBalanceCASA_Res();
		if (utilities.isNullOrEmpty(req.getProfileId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Profile ID is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public AccountsCASA_Res getAccountsCASA(AccountsCASA_Req req) {
		AccountsCASA_Res resObj = new AccountsCASA_Res();
		if (utilities.isNullOrEmpty(req.getProfileId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Profile ID is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public CustomerIdentificationRMN_Res getCustomerIdentificationRMN(CustomerIdentificationRMN_Req req) {
		CustomerIdentificationRMN_Res resObj = new CustomerIdentificationRMN_Res();
		if (utilities.isNullOrEmpty(req.getMobile())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Mobile Number is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public IdentifyKYC_Res identifyKYC(IdentifyKYC_Req req) {
		IdentifyKYC_Res resObj = new IdentifyKYC_Res();
		if (utilities.isNullOrEmpty(req.getProfileId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Profile ID is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public CustomerContactDetails_Res getCustomerContactDetails(CustomerContactDetails_Req req) {
		CustomerContactDetails_Res resObj = new CustomerContactDetails_Res();
		if (utilities.isNullOrEmpty(req.getProfileId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Profile ID is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public CustomerIdentificationDebtCardNum_Res getCustomerIdentificationDebtCardNum(
			CustomerIdentificationDebtCardNum_Req req) {
		CustomerIdentificationDebtCardNum_Res resObj = new CustomerIdentificationDebtCardNum_Res();
		if (utilities.isNullOrEmpty(req.getCardNumber())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Card number is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public CustomerIdentificationDebtRelId_Res getCustomerIdentificationDebtRelId(
			CustomerIdentificationDebtRelId_Req req) {
		CustomerIdentificationDebtRelId_Res resObj = new CustomerIdentificationDebtRelId_Res();
		if (utilities.isNullOrEmpty(req.getRelId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". RelID is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public GenerateOTP_Res generateOTP(GenerateOTP_Req req) {
		GenerateOTP_Res resObj = new GenerateOTP_Res();
		if (utilities.isNullOrEmpty(req.getRelId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". RelID is Null/Empty");
			return resObj;
		}

		if (utilities.isNullOrEmpty(req.getMobileNumber())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Mobile Number is Null/Empty");
			return resObj;
		}

		return resObj;
	}

	public ValidateOTP_Res validateOTP(ValidateOTP_Req req) {
		ValidateOTP_Res resObj = new ValidateOTP_Res();
		if (utilities.isNullOrEmpty(req.getRelID())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". RelID is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	public GenerateAPIN_Res generateAPIN(GenerateAPIN_Req req) {
		GenerateAPIN_Res resObj = new GenerateAPIN_Res();
		if (utilities.isNullOrEmpty(req.getCardNo())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Card number is Null/Empty");
			return resObj;
		}
		
		if (utilities.isNullOrEmpty(req.getUserId())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". User ID is Null/Empty");
			return resObj;
		}
		
		if (utilities.isNullOrEmpty(req.getNewPin())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". New PIN is Null/Empty");
			return resObj;
		}
		
		if (utilities.isNullOrEmpty(req.getConfirmNewPin())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Confirm New PIN is Null/Empty");
			return resObj;
		}
		
		if (utilities.isNullOrEmpty(req.getCardExpiryDate())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Expiry Date is Null/Empty");
			return resObj;
		}
		
		if (utilities.isNullOrEmpty(req.getExpiryDate())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Expiry Date is Null/Empty");
			return resObj;
		}
		
		if (!req.getNewPin().equalsIgnoreCase(req.getConfirmNewPin())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". New PIN and Confirm New PIN are mismatched");
			return resObj;
		}
		
		return resObj;
	}

	public ChangeAPIN_Res changeAPIN(ChangeAPIN_Req req) {
		ChangeAPIN_Res resObj = new ChangeAPIN_Res();
		if (utilities.isNullOrEmpty(req.getCardNo())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Card number is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	public ValidateAPIN_Res validateAPIN(ValidateAPIN_Req req) {
		ValidateAPIN_Res resObj = new ValidateAPIN_Res();
		if (utilities.isNullOrEmpty(req.getCardNo())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Card number is Null/Empty");
			return resObj;
		}

		if (utilities.isNullOrEmpty(req.getPin())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". PIN is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	public GenerateCCPIN_Res generateCCPIN(GenerateCCPIN_Req req) {
		GenerateCCPIN_Res resObj = new GenerateCCPIN_Res();
		if (utilities.isNullOrEmpty(req.getCardNo())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Card number is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	public ValidateCCPIN_Res validateCCPIN(ValidateCCPIN_Req req) {
		ValidateCCPIN_Res resObj = new ValidateCCPIN_Res();
		if (utilities.isNullOrEmpty(req.getCardNo())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Card number is Null/Empty");
			return resObj;
		}

		if (utilities.isNullOrEmpty(req.getPin())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". PIN is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	public GenerateTPIN_Res generateTPIN(GenerateTPIN_Req req) {
		GenerateTPIN_Res resObj = new GenerateTPIN_Res();
		if (utilities.isNullOrEmpty(req.getUserid())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". User ID is Null/Empty");
			return resObj;
		}

		if (utilities.isNullOrEmpty(req.getPassword())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Password is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	public ValidateTPIN_Res validateTPIN(ValidateTPIN_Req req) {
		ValidateTPIN_Res resObj = new ValidateTPIN_Res();
		if (utilities.isNullOrEmpty(req.getUserid())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". User ID is Null/Empty");
			return resObj;
		}

		if (utilities.isNullOrEmpty(req.getPassword())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Password is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	public ChangeTPIN_Res changeTPIN(ChangeTPIN_Req req) {
		ChangeTPIN_Res resObj = new ChangeTPIN_Res();
		if (utilities.isNullOrEmpty(req.getUserid())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". User ID is Null/Empty");
			return resObj;
		}

		if (utilities.isNullOrEmpty(req.getOldPassword())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Old Password is Null/Empty");
			return resObj;
		}
		
		if (utilities.isNullOrEmpty(req.getNewPassword())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". New Password is Null/Empty");
			return resObj;
		}
		return resObj;
	}

	
	public SendSMS_Res sendSMS(SendSMS_Req req) {
		SendSMS_Res resObj = new SendSMS_Res();
		if (utilities.isNullOrEmpty(req.getMobileNumber())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Mobile Number is Null/Empty");
			return resObj;
		}

		if (utilities.isNullOrEmpty(req.getLanguage())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". Language is Null/Empty");
			return resObj;
		}
		
		if (utilities.isNullOrEmpty(req.getSmsBody())) {
			resObj.setErrorcode(GlobalConstants.ERRORCODE_INVALID_INPUTS_FROM_IVR_700011);
			resObj.setErrormessage(GlobalConstants.FAILURE + ". SMS Body is Null/Empty");
			return resObj;
		}
		return resObj;

	}

}
