package com.scb.ivr.model.ebbs;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.ebbs.res.custcontact.CustomerContactResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CustomerContactDetails_Res extends CommonOutput{
	
	private CustomerContactResponseData response;

	public CustomerContactResponseData getResponse() {
		return response;
	}

	public void setResponse(CustomerContactResponseData response) {
		this.response = response;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
