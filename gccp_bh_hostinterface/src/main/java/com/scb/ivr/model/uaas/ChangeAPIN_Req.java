package com.scb.ivr.model.uaas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.service.generalvo.CommonInput;

public class ChangeAPIN_Req extends CommonInput {

	private String cardNo;

	private String cardExpiryDate;

	private String oldPin;

	private String newPin;

	private String confirmNewPin;

	private String userId;

	private String encCardPin;

	private String encCardInfo;

	private String otpSn;

	private String encOtp;

	private String dateOfBirth;// (Format: yyyyMMdd)

	private String expiryDate; // (Format: yyyy-MM)

	private String cvv;

	private String nric;

	private String embossedName;

	private String creditLimit;

	private String cardToken;

	private String modulus;

	private String exponent;

	private String base64Challenge;

	private String encryptedBlock;

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

	public String getOldPin() {
		return oldPin;
	}

	public void setOldPin(String oldPin) {
		this.oldPin = oldPin;
	}

	public String getNewPin() {
		return newPin;
	}

	public void setNewPin(String newPin) {
		this.newPin = newPin;
	}

	public String getConfirmNewPin() {
		return confirmNewPin;
	}

	public void setConfirmNewPin(String confirmNewPin) {
		this.confirmNewPin = confirmNewPin;
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

	public String getOtpSn() {
		return otpSn;
	}

	public void setOtpSn(String otpSn) {
		this.otpSn = otpSn;
	}

	public String getEncOtp() {
		return encOtp;
	}

	public void setEncOtp(String encOtp) {
		this.encOtp = encOtp;
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

	public String getCardToken() {
		return cardToken;
	}

	public void setCardToken(String cardToken) {
		this.cardToken = cardToken;
	}

	public String getModulus() {
		return modulus;
	}

	public void setModulus(String modulus) {
		this.modulus = modulus;
	}

	public String getExponent() {
		return exponent;
	}

	public void setExponent(String exponent) {
		this.exponent = exponent;
	}

	public String getBase64Challenge() {
		return base64Challenge;
	}

	public void setBase64Challenge(String base64Challenge) {
		this.base64Challenge = base64Challenge;
	}

	public String getEncryptedBlock() {
		return encryptedBlock;
	}

	public void setEncryptedBlock(String encryptedBlock) {
		this.encryptedBlock = encryptedBlock;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
