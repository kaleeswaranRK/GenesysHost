package com.scb.ivr.model.ebbs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.ebbs.res.custidentifyrelcrp.CustIdentificationRelCRPResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CustomerProfileProduct_Res extends CommonOutput {

	private CustIdentificationRelCRPResponseData response;

	public CustIdentificationRelCRPResponseData getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationRelCRPResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
