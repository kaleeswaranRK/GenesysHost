package com.scb.ivr.model.ebbs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.ebbs.res.custidentifyrmn.CustIdentificationRMNResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CustomerIdentificationRMN_Res extends CommonOutput {

	private CustIdentificationRMNResponseData response;

	public CustIdentificationRMNResponseData getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationRMNResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
