package com.scb.ivr.model.ebbs.res.custcontact;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerContactDetail_Data {

	private CustomerContactDetail_Attributes attributes;

	public CustomerContactDetail_Attributes getAttributes() {
		return attributes;
	}

	public void setAttributes(CustomerContactDetail_Attributes attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
