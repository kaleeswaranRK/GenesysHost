package com.scb.ivr.model.euronet.res.custidentifydebtcard;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Header {

	@JsonProperty("service-id")
	private String serviceId;
	@JsonProperty("channel-id")
	private String channelId;
	@JsonProperty("x-market")
	private String xMarket;
	@JsonProperty("transaction-sequence-number")
	private String transactionSequenceNumber;
	@JsonProperty("source-date")
	private String sourceDate;
	@JsonProperty("source-time")
	private String sourceTime;
	@JsonProperty("transmission-date")
	private String transmissionDate;
	@JsonProperty("transmission-time")
	private String transmissionTime;
	@JsonProperty("user-id")
	private String userId;
	@JsonProperty("message-version")
	private String messageVersion;
	@JsonProperty("signature")
	private String signature;
	
	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
