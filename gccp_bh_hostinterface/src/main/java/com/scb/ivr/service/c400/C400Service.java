package com.scb.ivr.service.c400;

import java.util.Map;

import com.scb.ivr.model.c400.CreditCardBalance_Res;
import com.scb.ivr.model.c400.CreditCardList_Res;
import com.scb.ivr.model.c400.CustomerIdentificationCardNum_Res;
import com.scb.ivr.model.c400.CustomerProfile_Res;

public interface C400Service {

	public CustomerIdentificationCardNum_Res getCustomerIdentificationCardNum(Map<String, Object> inputParams);

	public CreditCardList_Res getCreditCardList(Map<String, Object> inputParams);

	public CreditCardBalance_Res getCreditCardBalance(Map<String, Object> inputParams);

	public CustomerProfile_Res getCustomerProfile(Map<String, Object> inputParams);

}
