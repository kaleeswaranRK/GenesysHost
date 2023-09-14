package com.scb.ivr.service.ebbs;

import java.util.Map;

import com.scb.ivr.model.ebbs.AccountBalanceCASA_Res;
import com.scb.ivr.model.ebbs.AccountsCASA_Res;
import com.scb.ivr.model.ebbs.CustomerContactDetails_Res;
import com.scb.ivr.model.ebbs.CustomerIdentificationAcctNum_Res;
import com.scb.ivr.model.ebbs.CustomerIdentificationRELCRP_Res;
import com.scb.ivr.model.ebbs.CustomerIdentificationRMN_Res;
import com.scb.ivr.model.ebbs.CustomerProfileProduct_Res;
import com.scb.ivr.model.ebbs.IdentifyKYC_Res;

public interface EbbsService {

	CustomerIdentificationAcctNum_Res getCustomerIdentificationAcctNum(Map<String, Object> inputParams);

	CustomerIdentificationRELCRP_Res getCustomerIdentificationRELCRP(Map<String, Object> inputParams);

	CustomerProfileProduct_Res getCustomerProfileProduct(Map<String, Object> inputParams);

	AccountBalanceCASA_Res getAccountBalanceCASA(Map<String, Object> inputParams);

	AccountsCASA_Res getAccountsCASA(Map<String, Object> inputParams);

	CustomerIdentificationRMN_Res getCustomerIdentificationRMN(Map<String, Object> inputParams);

	IdentifyKYC_Res identifyKYC(Map<String, Object> inputParams);

	CustomerContactDetails_Res getCustomerContactDetails(Map<String, Object> inputParams);
	
}
