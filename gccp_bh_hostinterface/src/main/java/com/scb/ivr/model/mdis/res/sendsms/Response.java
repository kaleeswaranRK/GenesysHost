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
public class Response {

	@JsonProperty("StatusDesc")
	private String statusDesc;

	@JsonProperty("DstChannel")
	private String dstChannel;

	@JsonProperty("DstChannelAddr")
	private String dstChannelAddr;

	@JsonProperty("Appl")
	private String appl;

	@JsonProperty("MDISRef")
	private String mDISRef;

	@JsonProperty("DateTimeStamp")
	private String dateTimeStamp;

	@JsonProperty("StatusCode")
	private String statusCode;

	@JsonProperty("SourceRef")
	private String sourceRef;

	@JsonProperty("ProcessedProvider")
	private String processedProvider;

	@JsonProperty("StatusDesc")
	public String getStatusDesc() {
		return statusDesc;
	}

	@JsonProperty("StatusDesc")
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	@JsonProperty("DstChannel")
	public String getDstChannel() {
		return dstChannel;
	}

	@JsonProperty("DstChannel")
	public void setDstChannel(String dstChannel) {
		this.dstChannel = dstChannel;
	}

	@JsonProperty("DstChannelAddr")
	public String getDstChannelAddr() {
		return dstChannelAddr;
	}

	@JsonProperty("DstChannelAddr")
	public void setDstChannelAddr(String dstChannelAddr) {
		this.dstChannelAddr = dstChannelAddr;
	}

	@JsonProperty("Appl")
	public String getAppl() {
		return appl;
	}

	@JsonProperty("Appl")
	public void setAppl(String appl) {
		this.appl = appl;
	}

	@JsonProperty("MDISRef")
	public String getmDISRef() {
		return mDISRef;
	}

	@JsonProperty("MDISRef")
	public void setmDISRef(String mDISRef) {
		this.mDISRef = mDISRef;
	}

	@JsonProperty("DateTimeStamp")
	public String getDateTimeStamp() {
		return dateTimeStamp;
	}

	@JsonProperty("DateTimeStamp")
	public void setDateTimeStamp(String dateTimeStamp) {
		this.dateTimeStamp = dateTimeStamp;
	}

	@JsonProperty("StatusCode")
	public String getStatusCode() {
		return statusCode;
	}

	@JsonProperty("StatusCode")
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	@JsonProperty("SourceRef")
	public String getSourceRef() {
		return sourceRef;
	}

	@JsonProperty("SourceRef")
	public void setSourceRef(String sourceRef) {
		this.sourceRef = sourceRef;
	}

	@JsonProperty("ProcessedProvider")
	public String getProcessedProvider() {
		return processedProvider;
	}

	@JsonProperty("ProcessedProvider")
	public void setProcessedProvider(String processedProvider) {
		this.processedProvider = processedProvider;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
