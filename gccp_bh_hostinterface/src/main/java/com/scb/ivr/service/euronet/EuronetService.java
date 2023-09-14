package com.scb.ivr.service.euronet;

import java.util.Map;

import com.scb.ivr.model.euronet.CustomerIdentificationDebtCardNum_Res;
import com.scb.ivr.model.euronet.CustomerIdentificationDebtRelId_Res;

public interface EuronetService {

	CustomerIdentificationDebtCardNum_Res getCustomerIdentificationDebtCardNum(Map<String, Object> inputParams);

	CustomerIdentificationDebtRelId_Res getCustomerIdentificationDebtRelId(Map<String, Object> inputParams);

}
