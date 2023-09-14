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
public class MDISResponse {

	@JsonProperty("Response")
	private Response Response;
	
	@JsonProperty("ResponseHeader")
	private ResponseHeader responseHeader;

	@JsonProperty("Response")
	public Response getResponse() {
		return Response;
	}

	@JsonProperty("Response")
	public void setResponse(Response response) {
		Response = response;
	}

	@JsonProperty("ResponseHeader")
	public ResponseHeader getResponseHeader() {
		return responseHeader;
	}

	@JsonProperty("ResponseHeader")
	public void setResponseHeader(ResponseHeader responseHeader) {
		this.responseHeader = responseHeader;
	}
	
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
