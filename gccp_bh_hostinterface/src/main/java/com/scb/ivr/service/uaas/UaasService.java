package com.scb.ivr.service.uaas;

import java.util.Map;

import com.scb.ivr.model.uaas.GenerateAPIN_Res;
import com.scb.ivr.model.uaas.GenerateCCPIN_Res;
import com.scb.ivr.model.uaas.GenerateOTP_Res;
import com.scb.ivr.model.uaas.ValidateAPIN_Res;
import com.scb.ivr.model.uaas.ValidateCCPIN_Res;
import com.scb.ivr.model.uaas.ValidateOTP_Res;

public interface UaasService {

	GenerateOTP_Res generateOTP(Map<String, Object> inputParams);

	ValidateOTP_Res validateOTP(Map<String, Object> inputParams);

	GenerateAPIN_Res generateAPIN(Map<String, Object> inputParams);

	ValidateAPIN_Res validateAPIN(Map<String, Object> inputParams);

	GenerateCCPIN_Res generateCCPIN(Map<String, Object> inputParams);

	ValidateCCPIN_Res validateCCPIN(Map<String, Object> inputParams);


}
