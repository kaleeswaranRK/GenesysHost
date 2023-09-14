package com.scb.ivr.model.ebbs.res.identifykyc;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentifyKYCAttributes {
	@JsonProperty("cdd-next-review-date")
	private String cddNextReviewDate;
	@JsonProperty("country-risk-code-change-date")
	private Object countryRiskCodeChangeDate;
	@JsonProperty("country-last-review-date")
	private Object countryLastReviewDate;
	@JsonProperty("system-risk-level")
	private Object systemRiskLevel;
	@JsonProperty("sdd-eligible-status")
	private Object sddEligibleStatus;
	@JsonProperty("country-kyc-status")
	private Object countryKycStatus;
	@JsonProperty("country-next-review-date")
	private Object countryNextReviewDate;
	@JsonProperty("cdd-review-status")
	private String cddReviewStatus;
	@JsonProperty("last-kyc-risk-level-change-date")
	private Object lastKycRiskLevelChangeDate;
	@JsonProperty("country-risk-reason")
	private Object countryRiskReason;
	@JsonProperty("last-kyc-risk-code-change-date")
	private Object lastKycRiskCodeChangeDate;
	@JsonProperty("country-cdd-reason")
	private List<Object> countryCddReason = null;
	@JsonProperty("last-kyc-status-change-date")
	private Object lastKycStatusChangeDate;
	@JsonProperty("country-kyc-rev-status-change-date")
	private Object countryKycRevStatusChangeDate;
	@JsonProperty("cdd-last-review-date")
	private String cddLastReviewDate;
	@JsonProperty("additional-risk-factor-code")
	private Object additionalRiskFactorCode;
	@JsonProperty("cdd-last-reviewed-by")
	private String cddLastReviewedBy;
	@JsonProperty("cdd-reference-number")
	private Object cddReferenceNumber;
	@JsonProperty("record-state")
	private Object recordState;
	@JsonProperty("sdd-eligible-date")
	private Object sddEligibleDate;
	@JsonProperty("country-risk")
	private Object countryRisk;
	@JsonProperty("cdd-risk-rating")
	private String cddRiskRating;
	@JsonProperty("cdd-risk-rating-date")
	private String cddRiskRatingDate;
	@JsonProperty("profile-id")
	private String profileId;
	@JsonProperty("cdd-review-flag")
	private String cddReviewFlag;
	@JsonProperty("cdd-reason")
	private List<IdentifyKYCCddReason> cddReason = null;

	public String getCddNextReviewDate() {
		return cddNextReviewDate;
	}

	public void setCddNextReviewDate(String cddNextReviewDate) {
		this.cddNextReviewDate = cddNextReviewDate;
	}

	public Object getCountryRiskCodeChangeDate() {
		return countryRiskCodeChangeDate;
	}

	public void setCountryRiskCodeChangeDate(Object countryRiskCodeChangeDate) {
		this.countryRiskCodeChangeDate = countryRiskCodeChangeDate;
	}

	public Object getCountryLastReviewDate() {
		return countryLastReviewDate;
	}

	public void setCountryLastReviewDate(Object countryLastReviewDate) {
		this.countryLastReviewDate = countryLastReviewDate;
	}

	public Object getSystemRiskLevel() {
		return systemRiskLevel;
	}

	public void setSystemRiskLevel(Object systemRiskLevel) {
		this.systemRiskLevel = systemRiskLevel;
	}

	public Object getSddEligibleStatus() {
		return sddEligibleStatus;
	}

	public void setSddEligibleStatus(Object sddEligibleStatus) {
		this.sddEligibleStatus = sddEligibleStatus;
	}

	public Object getCountryKycStatus() {
		return countryKycStatus;
	}

	public void setCountryKycStatus(Object countryKycStatus) {
		this.countryKycStatus = countryKycStatus;
	}

	public Object getCountryNextReviewDate() {
		return countryNextReviewDate;
	}

	public void setCountryNextReviewDate(Object countryNextReviewDate) {
		this.countryNextReviewDate = countryNextReviewDate;
	}

	public String getCddReviewStatus() {
		return cddReviewStatus;
	}

	public void setCddReviewStatus(String cddReviewStatus) {
		this.cddReviewStatus = cddReviewStatus;
	}

	public Object getLastKycRiskLevelChangeDate() {
		return lastKycRiskLevelChangeDate;
	}

	public void setLastKycRiskLevelChangeDate(Object lastKycRiskLevelChangeDate) {
		this.lastKycRiskLevelChangeDate = lastKycRiskLevelChangeDate;
	}

	public Object getCountryRiskReason() {
		return countryRiskReason;
	}

	public void setCountryRiskReason(Object countryRiskReason) {
		this.countryRiskReason = countryRiskReason;
	}

	public Object getLastKycRiskCodeChangeDate() {
		return lastKycRiskCodeChangeDate;
	}

	public void setLastKycRiskCodeChangeDate(Object lastKycRiskCodeChangeDate) {
		this.lastKycRiskCodeChangeDate = lastKycRiskCodeChangeDate;
	}

	public List<Object> getCountryCddReason() {
		return countryCddReason;
	}

	public void setCountryCddReason(List<Object> countryCddReason) {
		this.countryCddReason = countryCddReason;
	}

	public Object getLastKycStatusChangeDate() {
		return lastKycStatusChangeDate;
	}

	public void setLastKycStatusChangeDate(Object lastKycStatusChangeDate) {
		this.lastKycStatusChangeDate = lastKycStatusChangeDate;
	}

	public Object getCountryKycRevStatusChangeDate() {
		return countryKycRevStatusChangeDate;
	}

	public void setCountryKycRevStatusChangeDate(Object countryKycRevStatusChangeDate) {
		this.countryKycRevStatusChangeDate = countryKycRevStatusChangeDate;
	}

	public String getCddLastReviewDate() {
		return cddLastReviewDate;
	}

	public void setCddLastReviewDate(String cddLastReviewDate) {
		this.cddLastReviewDate = cddLastReviewDate;
	}

	public Object getAdditionalRiskFactorCode() {
		return additionalRiskFactorCode;
	}

	public void setAdditionalRiskFactorCode(Object additionalRiskFactorCode) {
		this.additionalRiskFactorCode = additionalRiskFactorCode;
	}

	public String getCddLastReviewedBy() {
		return cddLastReviewedBy;
	}

	public void setCddLastReviewedBy(String cddLastReviewedBy) {
		this.cddLastReviewedBy = cddLastReviewedBy;
	}

	public Object getCddReferenceNumber() {
		return cddReferenceNumber;
	}

	public void setCddReferenceNumber(Object cddReferenceNumber) {
		this.cddReferenceNumber = cddReferenceNumber;
	}

	public Object getRecordState() {
		return recordState;
	}

	public void setRecordState(Object recordState) {
		this.recordState = recordState;
	}

	public Object getSddEligibleDate() {
		return sddEligibleDate;
	}

	public void setSddEligibleDate(Object sddEligibleDate) {
		this.sddEligibleDate = sddEligibleDate;
	}

	public Object getCountryRisk() {
		return countryRisk;
	}

	public void setCountryRisk(Object countryRisk) {
		this.countryRisk = countryRisk;
	}

	public String getCddRiskRating() {
		return cddRiskRating;
	}

	public void setCddRiskRating(String cddRiskRating) {
		this.cddRiskRating = cddRiskRating;
	}

	public String getCddRiskRatingDate() {
		return cddRiskRatingDate;
	}

	public void setCddRiskRatingDate(String cddRiskRatingDate) {
		this.cddRiskRatingDate = cddRiskRatingDate;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getCddReviewFlag() {
		return cddReviewFlag;
	}

	public void setCddReviewFlag(String cddReviewFlag) {
		this.cddReviewFlag = cddReviewFlag;
	}

	public List<IdentifyKYCCddReason> getCddReason() {
		return cddReason;
	}

	public void setCddReason(List<IdentifyKYCCddReason> cddReason) {
		this.cddReason = cddReason;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
