package com.scb.ivr.model.uaas.res.validateapin;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PinAuthInfo {

	@JsonProperty("gatewayTrackingId")
	private String gatewayTrackingId;

	@JsonProperty("ENTxnSequenceNo")
	private String eNTxnSequenceNo;

	@JsonProperty("ENResponseCode")
	private String eNResponseCode;

	@JsonProperty("ENResponseDesc")
	private String eNResponseDesc;

	@JsonProperty("HoganTxnSequenceNo")
	private String hoganTxnSequenceNo;

	@JsonProperty("HoganHttpCode")
	private String hoganHttpCode;

	@JsonProperty("HoganHttpStatus")
	private String hoganHttpStatus;

	@JsonProperty("HoganResponseCode")
	private String hoganResponseCode;

	@JsonProperty("HoganResponseDesc")
	private String hoganResponseDesc;

	@JsonProperty("HoganSystemId")
	private String hoganSystemId;

	public String getGatewayTrackingId() {
		return gatewayTrackingId;
	}

	public void setGatewayTrackingId(String gatewayTrackingId) {
		this.gatewayTrackingId = gatewayTrackingId;
	}

	public String geteNTxnSequenceNo() {
		return eNTxnSequenceNo;
	}

	public void seteNTxnSequenceNo(String eNTxnSequenceNo) {
		this.eNTxnSequenceNo = eNTxnSequenceNo;
	}

	public String geteNResponseCode() {
		return eNResponseCode;
	}

	public void seteNResponseCode(String eNResponseCode) {
		this.eNResponseCode = eNResponseCode;
	}

	public String geteNResponseDesc() {
		return eNResponseDesc;
	}

	public void seteNResponseDesc(String eNResponseDesc) {
		this.eNResponseDesc = eNResponseDesc;
	}

	public String getHoganTxnSequenceNo() {
		return hoganTxnSequenceNo;
	}

	public void setHoganTxnSequenceNo(String hoganTxnSequenceNo) {
		this.hoganTxnSequenceNo = hoganTxnSequenceNo;
	}

	public String getHoganHttpCode() {
		return hoganHttpCode;
	}

	public void setHoganHttpCode(String hoganHttpCode) {
		this.hoganHttpCode = hoganHttpCode;
	}

	public String getHoganHttpStatus() {
		return hoganHttpStatus;
	}

	public void setHoganHttpStatus(String hoganHttpStatus) {
		this.hoganHttpStatus = hoganHttpStatus;
	}

	public String getHoganResponseCode() {
		return hoganResponseCode;
	}

	public void setHoganResponseCode(String hoganResponseCode) {
		this.hoganResponseCode = hoganResponseCode;
	}

	public String getHoganResponseDesc() {
		return hoganResponseDesc;
	}

	public void setHoganResponseDesc(String hoganResponseDesc) {
		this.hoganResponseDesc = hoganResponseDesc;
	}

	public String getHoganSystemId() {
		return hoganSystemId;
	}

	public void setHoganSystemId(String hoganSystemId) {
		this.hoganSystemId = hoganSystemId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
