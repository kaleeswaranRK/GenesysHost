package com.scb.ivr.model.c400;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.c400.res.custidentifycardnum.CustIdentificationCardNumResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CustomerIdentificationCardNum_Res extends CommonOutput {

	private CustIdentificationCardNumResponseData response;

	public CustIdentificationCardNumResponseData getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationCardNumResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
