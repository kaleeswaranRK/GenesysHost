package com.scb.ivr.model.euronet.res.custidentifydebtcard;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBlock {

	@JsonProperty("account-no")
	private String accountNo;
	@JsonProperty("encrypted-account-number")
	private String encryptedAccountNumber;
	@JsonProperty("account-currency")
	private String accountCurrency;
	@JsonProperty("primary-flag")
	private String primaryFlag;
	@JsonProperty("account-type")
	private String accountType;

	public String getAccountNo() {
		return accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getEncryptedAccountNumber() {
		return encryptedAccountNumber;
	}

	public void setEncryptedAccountNumber(String encryptedAccountNumber) {
		this.encryptedAccountNumber = encryptedAccountNumber;
	}

	public String getAccountCurrency() {
		return accountCurrency;
	}

	public void setAccountCurrency(String accountCurrency) {
		this.accountCurrency = accountCurrency;
	}

	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	

	@Override
    public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

}
