package com.scb.ivr.model.ebbs.res.custcontact;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerContactResponseData {

	private CustomerContactDetail_Data[] data;

	private CustomerContactDetails_Errors[] errors;

	private String message;

	public CustomerContactDetail_Data[] getData() {
		return data;
	}

	public void setData(CustomerContactDetail_Data[] data) {
		this.data = data;
	}

	public CustomerContactDetails_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(CustomerContactDetails_Errors[] errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
