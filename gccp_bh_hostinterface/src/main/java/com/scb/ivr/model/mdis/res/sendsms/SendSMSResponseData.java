/**
 * 
 */
package com.scb.ivr.model.mdis.res.sendsms;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author TA
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class SendSMSResponseData {

	@JsonProperty("MDISResponse")
	private MDISResponse mdisResponse;

	@JsonProperty("errors")
	private SendSMS_Errors[] errors;

	private String message = null;

	public SendSMS_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(SendSMS_Errors[] errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@JsonProperty("MDISResponse")
	public MDISResponse getMdisResponse() {
		return mdisResponse;
	}

	@JsonProperty("MDISResponse")
	public void setMdisResponse(MDISResponse mdisResponse) {
		this.mdisResponse = mdisResponse;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
