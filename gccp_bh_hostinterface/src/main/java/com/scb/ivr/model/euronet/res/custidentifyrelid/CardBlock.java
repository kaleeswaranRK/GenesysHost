package com.scb.ivr.model.euronet.res.custidentifyrelid;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CardBlock {

	@JsonProperty("card-number")
	private String cardNumber;
	@JsonProperty("card-sequence-number")
	private String cardSequenceNumber;
	@JsonProperty("card-token")
	private String cardToken;
	@JsonProperty("encrypted-data")
	private String encryptedData;
	@JsonProperty("card-scheme-id")
	private String cardSchemeId;
	@JsonProperty("card-product-type")
	private String cardProductType;
	@JsonProperty("card-type")
	private String cardType;
	@JsonProperty("card-sub-type")
	private String cardSubType;
	@JsonProperty("account-block")
	private List<AccountBlock> accountBlock = null;
	@JsonProperty("card-status")
	private String cardStatus;
	@JsonProperty("reason-code")
	private String reasonCode;
	@JsonProperty("reason-code-desc")
	private String reasonCodeDesc;
	@JsonProperty("pin-retry-exceed-flag")
	private String pinRetryExceedFlag;
	@JsonProperty("card-expiry")
	private String cardExpiry;
	@JsonProperty("emboss-name")
	private String embossName;
	@JsonProperty("card-category")
	private String cardCategory;
	@JsonProperty("card-replace-ind")
	private String cardReplaceInd;
	@JsonProperty("wallet-details")
	private List<WalletDetail> walletDetails = null;

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getCardSequenceNumber() {
		return cardSequenceNumber;
	}

	public void setCardSequenceNumber(String cardSequenceNumber) {
		this.cardSequenceNumber = cardSequenceNumber;
	}

	public String getCardToken() {
		return cardToken;
	}

	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}

	public String getEncryptedData() {
		return encryptedData;
	}

	public void setEncryptedData(String encryptedData) {
		this.encryptedData = encryptedData;
	}

	public String getCardSchemeId() {
		return cardSchemeId;
	}

	public void setCardSchemeId(String cardSchemeId) {
		this.cardSchemeId = cardSchemeId;
	}

	public String getCardProductType() {
		return cardProductType;
	}

	public void setCardProductType(String cardProductType) {
		this.cardProductType = cardProductType;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getCardSubType() {
		return cardSubType;
	}

	public void setCardSubType(String cardSubType) {
		this.cardSubType = cardSubType;
	}

	public List<AccountBlock> getAccountBlock() {
		return accountBlock;
	}

	public void setAccountBlock(List<AccountBlock> accountBlock) {
		this.accountBlock = accountBlock;
	}

	public String getCardStatus() {
		return cardStatus;
	}

	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}

	public String getReasonCode() {
		return reasonCode;
	}

	public void setReasonCode(String reasonCode) {
		this.reasonCode = reasonCode;
	}

	public String getReasonCodeDesc() {
		return reasonCodeDesc;
	}

	public void setReasonCodeDesc(String reasonCodeDesc) {
		this.reasonCodeDesc = reasonCodeDesc;
	}

	public String getPinRetryExceedFlag() {
		return pinRetryExceedFlag;
	}

	public void setPinRetryExceedFlag(String pinRetryExceedFlag) {
		this.pinRetryExceedFlag = pinRetryExceedFlag;
	}

	public String getCardExpiry() {
		return cardExpiry;
	}

	public void setCardExpiry(String cardExpiry) {
		this.cardExpiry = cardExpiry;
	}

	public String getEmbossName() {
		return embossName;
	}

	public void setEmbossName(String embossName) {
		this.embossName = embossName;
	}

	public String getCardCategory() {
		return cardCategory;
	}

	public void setCardCategory(String cardCategory) {
		this.cardCategory = cardCategory;
	}

	public String getCardReplaceInd() {
		return cardReplaceInd;
	}

	public void setCardReplaceInd(String cardReplaceInd) {
		this.cardReplaceInd = cardReplaceInd;
	}

	public List<WalletDetail> getWalletDetails() {
		return walletDetails;
	}

	public void setWalletDetails(List<WalletDetail> walletDetails) {
		this.walletDetails = walletDetails;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
