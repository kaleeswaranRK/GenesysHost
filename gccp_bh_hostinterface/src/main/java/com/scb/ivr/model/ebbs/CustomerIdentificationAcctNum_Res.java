package com.scb.ivr.model.ebbs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.ebbs.res.custidentifyacctnum.CustIdentificationAcctNumResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CustomerIdentificationAcctNum_Res extends CommonOutput {
	
	private CustIdentificationAcctNumResponseData response;

	public CustIdentificationAcctNumResponseData getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationAcctNumResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
