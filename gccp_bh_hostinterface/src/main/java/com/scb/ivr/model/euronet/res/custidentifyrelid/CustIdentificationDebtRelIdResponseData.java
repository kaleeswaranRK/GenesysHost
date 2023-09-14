package com.scb.ivr.model.euronet.res.custidentifyrelid;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationDebtRelIdResponseData {

	@JsonProperty("errors")
	private CustIdentificationDebtRelId_Errors[] errors = null;

	private String message = null;

	@JsonProperty("header")
	private Header header;
	@JsonProperty("relationship-id")
	private String relationshipId;
	@JsonProperty("response-code")
	private String responseCode;
	@JsonProperty("response-text")
	private String responseText;
	@JsonProperty("card-block")
	private List<CardBlock> cardBlock = null;

	public CustIdentificationDebtRelId_Errors[] getErrors() {
		return errors;
	}

	public void setErrors(CustIdentificationDebtRelId_Errors[] errors) {
		this.errors = errors;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public String getRelationshipId() {
		return relationshipId;
	}

	public void setRelationshipId(String relationshipId) {
		this.relationshipId = relationshipId;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getResponseText() {
		return responseText;
	}

	public void setResponseText(String responseText) {
		this.responseText = responseText;
	}

	public List<CardBlock> getCardBlock() {
		return cardBlock;
	}

	public void setCardBlock(List<CardBlock> cardBlock) {
		this.cardBlock = cardBlock;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
