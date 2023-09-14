package com.scb.ivr.model.uaas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.service.generalvo.CommonInput;

public class ValidateAPIN_Req extends CommonInput {

	private String cardNo;

	private String cardExpiryDate;

	private String Pin;

	private String userId;

	private String encCardPin;

	private String encCardInfo;

	private String dateOfBirth;// (Format: yyyyMMdd)

	private String expiryDate; // (Format: yyyy-MM)

	private String cvv;

	private String nric;

	private String embossedName;

	private String creditLimit;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCardExpiryDate() {
		return cardExpiryDate;
	}

	public void setCardExpiryDate(String cardExpiryDate) {
		this.cardExpiryDate = cardExpiryDate;
	}

	public String getPin() {
		return Pin;
	}

	public void setPin(String pin) {
		Pin = pin;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getEncCardPin() {
		return encCardPin;
	}

	public void setEncCardPin(String encCardPin) {
		this.encCardPin = encCardPin;
	}

	public String getEncCardInfo() {
		return encCardInfo;
	}

	public void setEncCardInfo(String encCardInfo) {
		this.encCardInfo = encCardInfo;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public String getNric() {
		return nric;
	}

	public void setNric(String nric) {
		this.nric = nric;
	}

	public String getEmbossedName() {
		return embossedName;
	}

	public void setEmbossedName(String embossedName) {
		this.embossedName = embossedName;
	}

	public String getCreditLimit() {
		return creditLimit;
	}

	public void setCreditLimit(String creditLimit) {
		this.creditLimit = creditLimit;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
