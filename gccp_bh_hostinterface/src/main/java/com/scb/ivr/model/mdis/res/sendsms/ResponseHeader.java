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
public class ResponseHeader {

	@JsonProperty("DateTimeStamp")
	private String dateTimeStamp;

	@JsonProperty("MDISAppId")
	private String mDISAppId;

	@JsonProperty("ResponseType")
	private String responseType;

	@JsonProperty("DateTimeStamp")
	public String getDateTimeStamp() {
		return dateTimeStamp;
	}

	@JsonProperty("DateTimeStamp")
	public void setDateTimeStamp(String dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}

	@JsonProperty("MDISAppId")
	public String getmDISAppId() {
		return mDISAppId;
	}

	@JsonProperty("MDISAppId")
	public void setmDISAppId(String mDISAppId) {
		this.mDISAppId = mDISAppId;
	}

	@JsonProperty("ResponseType")
	public String getResponseType() {
		return responseType;
	}

	@JsonProperty("ResponseType")
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
