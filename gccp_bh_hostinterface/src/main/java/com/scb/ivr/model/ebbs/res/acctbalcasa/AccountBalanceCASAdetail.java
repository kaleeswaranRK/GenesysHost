package com.scb.ivr.model.ebbs.res.acctbalcasa;

import java.text.DecimalFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceCASAdetail {

	@JsonProperty("casa-currency-code")
	private String casaCurrencyCode;
	@JsonProperty("casa-number")
	private String casaNumber;
	@JsonProperty("product-code")
	private String productCode;
	@JsonProperty("casa-short-name")
	private String casaShortName;
	@JsonProperty("casa-title")
	private String casaTitle;
	@JsonProperty("account-current-status")
	private String accountCurrentStatus;
	@JsonProperty("master-number")
	private String masterNumber;
	@JsonProperty("primary-flag")
	private String primaryFlag;
	@JsonProperty("account-branch")
	private String accountBranch;
	@JsonProperty("institutional-code")
	private String institutionalCode;
	@JsonProperty("operating-instructions")
	private String operatingInstructions;
	@JsonProperty("ledger-balance")
	private String ledgerBalance;
	@JsonProperty("availablebalance-withlimit")
	private String availablebalanceWithlimit;
	@JsonProperty("availablebalance-withoutlimit")
	private String availablebalanceWithoutlimit;
	@JsonProperty("product-description")
	private String productDescription;
	@JsonProperty("product-category")
	private String productCategory;
	@JsonProperty("instclass-description")
	private String instclassDescription;
	@JsonProperty("splprd-flag")
	private String splprdFlag;
	@JsonProperty("segment-code")
	private String segmentCode;
	@JsonProperty("staff-class")
	private String staffClass;
	@JsonProperty("iban")
	private String iban;
	@JsonProperty("account-category")
	private String accountCategory;
	@JsonProperty("accopen-date")
	private String accopenDate;
	@JsonProperty("is-linked")
	private String isLinked;
	@JsonProperty("customer-segmentcode")
	private String customerSegmentcode;
	@JsonProperty("retail-limit-lien")
	private String retailLimitLien;
	@JsonProperty("master-limit-lien")
	private String masterLimitLien;
	@JsonProperty("limit-or-lien")
	private String limitOrLien;

	public String getCasaCurrencyCode() {
		return casaCurrencyCode;
	}

	public void setCasaCurrencyCode(String casaCurrencyCode) {
		this.casaCurrencyCode = casaCurrencyCode;
	}

	public String getCasaNumber() {
		return casaNumber;
	}

	public void setCasaNumber(String casaNumber) {
		this.casaNumber = casaNumber;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCasaShortName() {
		return casaShortName;
	}

	public void setCasaShortName(String casaShortName) {
		this.casaShortName = casaShortName;
	}

	public String getCasaTitle() {
		return casaTitle;
	}

	public void setCasaTitle(String casaTitle) {
		this.casaTitle = casaTitle;
	}

	public String getAccountCurrentStatus() {
		return accountCurrentStatus;
	}

	public void setAccountCurrentStatus(String accountCurrentStatus) {
		this.accountCurrentStatus = accountCurrentStatus;
	}

	public String getMasterNumber() {
		return masterNumber;
	}

	public void setMasterNumber(String masterNumber) {
		this.masterNumber = masterNumber;
	}

	public String getPrimaryFlag() {
		return primaryFlag;
	}

	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}

	public String getAccountBranch() {
		return accountBranch;
	}

	public void setAccountBranch(String accountBranch) {
		this.accountBranch = accountBranch;
	}

	public String getInstitutionalCode() {
		return institutionalCode;
	}

	public void setInstitutionalCode(String institutionalCode) {
		this.institutionalCode = institutionalCode;
	}

	public String getOperatingInstructions() {
		return operatingInstructions;
	}

	public void setOperatingInstructions(String operatingInstructions) {
		this.operatingInstructions = operatingInstructions;
	}

	public String getLedgerBalance() {
		return getConvertedBalanceFormat(casaCurrencyCode, ledgerBalance);
	}

	public void setLedgerBalance(String ledgerBalance) {
		this.ledgerBalance = ledgerBalance;
	}

	public String getAvailablebalanceWithlimit() {
		return getConvertedBalanceFormat(casaCurrencyCode, availablebalanceWithlimit);
	}

	public void setAvailablebalanceWithlimit(String availablebalanceWithlimit) {
		this.availablebalanceWithlimit = availablebalanceWithlimit;
	}

	public String getAvailablebalanceWithoutlimit() {
		return getConvertedBalanceFormat(casaCurrencyCode, availablebalanceWithoutlimit);
	}

	public void setAvailablebalanceWithoutlimit(String availablebalanceWithoutlimit) {
		this.availablebalanceWithoutlimit = availablebalanceWithoutlimit;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getInstclassDescription() {
		return instclassDescription;
	}

	public void setInstclassDescription(String instclassDescription) {
		this.instclassDescription = instclassDescription;
	}

	public String getSplprdFlag() {
		return splprdFlag;
	}

	public void setSplprdFlag(String splprdFlag) {
		this.splprdFlag = splprdFlag;
	}

	public String getSegmentCode() {
		return segmentCode;
	}

	public void setSegmentCode(String segmentCode) {
		this.segmentCode = segmentCode;
	}

	public String getStaffClass() {
		return staffClass;
	}

	public void setStaffClass(String staffClass) {
		this.staffClass = staffClass;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getAccountCategory() {
		return accountCategory;
	}

	public void setAccountCategory(String accountCategory) {
		this.accountCategory = accountCategory;
	}

	public String getAccopenDate() {
		return accopenDate;
	}

	public void setAccopenDate(String accopenDate) {
		this.accopenDate = accopenDate;
	}

	public String getIsLinked() {
		return isLinked;
	}

	public void setIsLinked(String isLinked) {
		this.isLinked = isLinked;
	}

	public String getCustomerSegmentcode() {
		return customerSegmentcode;
	}

	public void setCustomerSegmentcode(String customerSegmentcode) {
		this.customerSegmentcode = customerSegmentcode;
	}

	public String getRetailLimitLien() {
		return retailLimitLien;
	}

	public void setRetailLimitLien(String retailLimitLien) {
		this.retailLimitLien = retailLimitLien;
	}

	public String getMasterLimitLien() {
		return masterLimitLien;
	}

	public void setMasterLimitLien(String masterLimitLien) {
		this.masterLimitLien = masterLimitLien;
	}

	public String getLimitOrLien() {
		return limitOrLien;
	}

	public void setLimitOrLien(String limitOrLien) {
		this.limitOrLien = limitOrLien;
	}
	
	public String getConvertedBalanceFormat(String ccyCode, String balanceAmount) {
		if (ccyCode != null) {
			if ("BHD".equalsIgnoreCase(ccyCode)) {
				DecimalFormat df = new DecimalFormat("###0.000");

				if (balanceAmount != null && !"".equalsIgnoreCase(balanceAmount)) {
					return String.valueOf(df.format(Double.parseDouble(balanceAmount)));
				} else {
					//return String.valueOf(df.format(Double.parseDouble("0")));
					return balanceAmount;
				}
			} else {
				DecimalFormat df = new DecimalFormat("###0.00");

				if (balanceAmount != null && !"".equalsIgnoreCase(balanceAmount)) {
					return String.valueOf(df.format(Double.parseDouble(balanceAmount)));
				} else {
					//return String.valueOf(df.format(Double.parseDouble("0")));
					return balanceAmount;
				}
			}
		} else {
			return balanceAmount;
		}
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
