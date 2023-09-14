package com.scb.ivr.model.uaas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.uaas.res.generateotp.GenerateOtpResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class GenerateOTP_Res extends CommonOutput {

	private GenerateOtpResponseData otpResponse;

	public GenerateOtpResponseData getOtpResponse() {
		return otpResponse;
	}

	public void setOtpResponse(GenerateOtpResponseData otpResponse) {
		this.otpResponse = otpResponse;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
