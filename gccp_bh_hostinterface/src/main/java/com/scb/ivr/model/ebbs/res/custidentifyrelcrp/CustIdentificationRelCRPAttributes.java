package com.scb.ivr.model.ebbs.res.custidentifyrelcrp;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationRelCRPAttributes {

	@JsonProperty("service-indicator-code")
	private String serviceIndicatorCode;
	@JsonProperty("staff-no")
	private Object staffNo;
	@JsonProperty("furnishing-allowance")
	private Integer furnishingAllowance;
	@JsonProperty("referred-by-relationship-name")
	private Object referredByRelationshipName;
	@JsonProperty("meal-allowance")
	private Integer mealAllowance;
	@JsonProperty("morganisation-name")
	private Object morganisationName;
	@JsonProperty("children-allowance")
	private Integer childrenAllowance;
	@JsonProperty("customer-nationality-code")
	private String customerNationalityCode;
	@JsonProperty("dearness-allowance")
	private Integer dearnessAllowance;
	@JsonProperty("form-receipt-date")
	private Object formReceiptDate;
	@JsonProperty("position-title")
	private String positionTitle;
	@JsonProperty("multilingual-profiles")
	private Object multilingualProfiles;
	@JsonProperty("designation-code")
	private String designationCode;
	@JsonProperty("total-monthly-income")
	private Integer totalMonthlyIncome;
	@JsonProperty("conveyance-allowance")
	private Integer conveyanceAllowance;
	@JsonProperty("mdepartment")
	private Object mdepartment;
	@JsonProperty("preferred-card-name")
	private String preferredCardName;
	@JsonProperty("kyc-risk-reason")
	private Object kycRiskReason;
	@JsonProperty("variable-incentive")
	private Integer variableIncentive;
	@JsonProperty("date-of-incorporation")
	private Object dateOfIncorporation;
	@JsonProperty("spouse-monthly-income")
	private Integer spouseMonthlyIncome;
	@JsonProperty("salutation-code")
	private String salutationCode;
	@JsonProperty("mlast-name")
	private Object mlastName;
	@JsonProperty("arm-code-description")
	private Object armCodeDescription;
	@JsonProperty("interest-share-percentage")
	private Object interestSharePercentage;
	@JsonProperty("relationship-type")
	private String relationshipType;
	@JsonProperty("kyc-risk-reason-description")
	private Object kycRiskReasonDescription;
	@JsonProperty("middle-name")
	private String middleName;
	@JsonProperty("birth-place")
	private Object birthPlace;
	@JsonProperty("primary-profile")
	private Object primaryProfile;
	@JsonProperty("organisation-name")
	private Object organisationName;
	@JsonProperty("total-month-experience")
	private Object totalMonthExperience;
	@JsonProperty("monthly-deductions")
	private Integer monthlyDeductions;
	@JsonProperty("constitution-code")
	private String constitutionCode;
	@JsonProperty("social-allowance")
	private Integer socialAllowance;
	@JsonProperty("variable-overtime")
	private Integer variableOvertime;
	@JsonProperty("first-name")
	private String firstName;
	@JsonProperty("other-monthly-income")
	private Integer otherMonthlyIncome;
	@JsonProperty("mfull-name")
	private Object mfullName;
	@JsonProperty("qualification-code")
	private Object qualificationCode;
	@JsonProperty("monthly-commission")
	private Integer monthlyCommission;
	@JsonProperty("car-allowance")
	private Integer carAllowance;
	@JsonProperty("fixed-overtime")
	private Integer fixedOvertime;
	@JsonProperty("home-branch")
	private Object homeBranch;
	@JsonProperty("mfirst-name")
	private Object mfirstName;
	@JsonProperty("consolidated-statement-language")
	private Object consolidatedStatementLanguage;
	@JsonProperty("monthly-basic")
	private Integer monthlyBasic;
	@JsonProperty("bachelor-allowance")
	private Integer bachelorAllowance;
	@JsonProperty("customer-segment-code-description")
	private Object customerSegmentCodeDescription;
	@JsonProperty("mnature-of-business")
	private Object mnatureOfBusiness;
	@JsonProperty("arm-code")
	private Object armCode;
	@JsonProperty("employer-code")
	private Object employerCode;
	@JsonProperty("profession-code")
	private String professionCode;
	@JsonProperty("medical-allowance")
	private Integer medicalAllowance;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("msalutation-code")
	private Object msalutationCode;
	@JsonProperty("mposition-title")
	private Object mpositionTitle;
	@JsonProperty("bsbda-flag")
	private Object bsbdaFlag;
	@JsonProperty("referred-by-relationship-no")
	private Object referredByRelationshipNo;
	@JsonProperty("mplace-of-birth")
	private Object mplaceOfBirth;
	@JsonProperty("work-type")
	private String workType;
	@JsonProperty("gross-monthly-income")
	private Integer grossMonthlyIncome;
	@JsonProperty("utility-allowance")
	private Integer utilityAllowance;
	@JsonProperty("mmiddle-name")
	private Object mmiddleName;
	@JsonProperty("full-name")
	private String fullName;
	@JsonProperty("segment-code")
	private Object segmentCode;
	@JsonProperty("date-of-joining")
	private Object dateOfJoining;
	@JsonProperty("date-of-birth")
	private String dateOfBirth;
	@JsonProperty("resident-country")
	private String residentCountry;
	@JsonProperty("department")
	private Object department;
	@JsonProperty("last-name")
	private String lastName;
	@JsonProperty("sub-segment-code")
	private Object subSegmentCode;
	@JsonProperty("shift-allowance")
	private Integer shiftAllowance;
	@JsonProperty("last-review-date")
	private String lastReviewDate;
	@JsonProperty("monthly-allowance")
	private Integer monthlyAllowance;
	@JsonProperty("memployer-name")
	private Object memployerName;
	@JsonProperty("nature-of-business")
	private Object natureOfBusiness;
	@JsonProperty("mlanguage-code")
	private Object mlanguageCode;
	@JsonProperty("language-code-description")
	private Object languageCodeDescription;
	@JsonProperty("cost-of-living-allowance")
	private Integer costOfLivingAllowance;
	@JsonProperty("acquisition-channel")
	private Object acquisitionChannel;
	@JsonProperty("staff")
	private String staff;
	@JsonProperty("profile-type")
	private Object profileType;
	@JsonProperty("corp-cust")
	private String corpCust;
	@JsonProperty("relationship-status")
	private String relationshipStatus;
	@JsonProperty("profile-id")
	private String profileId;
	@JsonProperty("resident-status")
	private String residentStatus;
	@JsonProperty("monthly-net-income")
	private Integer monthlyNetIncome;
	@JsonProperty("status-flag")
	private String statusFlag;
	@JsonProperty("domicile-country")
	private String domicileCountry;
	@JsonProperty("other-allowance")
	private Integer otherAllowance;
	@JsonProperty("segment-code-description")
	private Object segmentCodeDescription;
	@JsonProperty("mpreferred-card-name")
	private Object mpreferredCardName;

	public String getServiceIndicatorCode() {
		return serviceIndicatorCode;
	}

	public void setServiceIndicatorCode(String serviceIndicatorCode) {
		this.serviceIndicatorCode = serviceIndicatorCode;
	}

	public Object getStaffNo() {
		return staffNo;
	}

	public void setStaffNo(Object staffNo) {
		this.staffNo = staffNo;
	}

	public Integer getFurnishingAllowance() {
		return furnishingAllowance;
	}

	public void setFurnishingAllowance(Integer furnishingAllowance) {
		this.furnishingAllowance = furnishingAllowance;
	}

	public Object getReferredByRelationshipName() {
		return referredByRelationshipName;
	}

	public void setReferredByRelationshipName(Object referredByRelationshipName) {
		this.referredByRelationshipName = referredByRelationshipName;
	}

	public Integer getMealAllowance() {
		return mealAllowance;
	}

	public void setMealAllowance(Integer mealAllowance) {
		this.mealAllowance = mealAllowance;
	}

	public Object getMorganisationName() {
		return morganisationName;
	}

	public void setMorganisationName(Object morganisationName) {
		this.morganisationName = morganisationName;
	}

	public Integer getChildrenAllowance() {
		return childrenAllowance;
	}

	public void setChildrenAllowance(Integer childrenAllowance) {
		this.childrenAllowance = childrenAllowance;
	}

	public String getCustomerNationalityCode() {
		return customerNationalityCode;
	}

	public void setCustomerNationalityCode(String customerNationalityCode) {
		this.customerNationalityCode = customerNationalityCode;
	}

	public Integer getDearnessAllowance() {
		return dearnessAllowance;
	}

	public void setDearnessAllowance(Integer dearnessAllowance) {
		this.dearnessAllowance = dearnessAllowance;
	}

	public Object getFormReceiptDate() {
		return formReceiptDate;
	}

	public void setFormReceiptDate(Object formReceiptDate) {
		this.formReceiptDate = formReceiptDate;
	}

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public Object getMultilingualProfiles() {
		return multilingualProfiles;
	}

	public void setMultilingualProfiles(Object multilingualProfiles) {
		this.multilingualProfiles = multilingualProfiles;
	}

	public String getDesignationCode() {
		return designationCode;
	}

	public void setDesignationCode(String designationCode) {
		this.designationCode = designationCode;
	}

	public Integer getTotalMonthlyIncome() {
		return totalMonthlyIncome;
	}

	public void setTotalMonthlyIncome(Integer totalMonthlyIncome) {
		this.totalMonthlyIncome = totalMonthlyIncome;
	}

	public Integer getConveyanceAllowance() {
		return conveyanceAllowance;
	}

	public void setConveyanceAllowance(Integer conveyanceAllowance) {
		this.conveyanceAllowance = conveyanceAllowance;
	}

	public Object getMdepartment() {
		return mdepartment;
	}

	public void setMdepartment(Object mdepartment) {
		this.mdepartment = mdepartment;
	}

	public String getPreferredCardName() {
		return preferredCardName;
	}

	public void setPreferredCardName(String preferredCardName) {
		this.preferredCardName = preferredCardName;
	}

	public Object getKycRiskReason() {
		return kycRiskReason;
	}

	public void setKycRiskReason(Object kycRiskReason) {
		this.kycRiskReason = kycRiskReason;
	}

	public Integer getVariableIncentive() {
		return variableIncentive;
	}

	public void setVariableIncentive(Integer variableIncentive) {
		this.variableIncentive = variableIncentive;
	}

	public Object getDateOfIncorporation() {
		return dateOfIncorporation;
	}

	public void setDateOfIncorporation(Object dateOfIncorporation) {
		this.dateOfIncorporation = dateOfIncorporation;
	}

	public Integer getSpouseMonthlyIncome() {
		return spouseMonthlyIncome;
	}

	public void setSpouseMonthlyIncome(Integer spouseMonthlyIncome) {
		this.spouseMonthlyIncome = spouseMonthlyIncome;
	}

	public String getSalutationCode() {
		return salutationCode;
	}

	public void setSalutationCode(String salutationCode) {
		this.salutationCode = salutationCode;
	}

	public Object getMlastName() {
		return mlastName;
	}

	public void setMlastName(Object mlastName) {
		this.mlastName = mlastName;
	}

	public Object getArmCodeDescription() {
		return armCodeDescription;
	}

	public void setArmCodeDescription(Object armCodeDescription) {
		this.armCodeDescription = armCodeDescription;
	}

	public Object getInterestSharePercentage() {
		return interestSharePercentage;
	}

	public void setInterestSharePercentage(Object interestSharePercentage) {
		this.interestSharePercentage = interestSharePercentage;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public Object getKycRiskReasonDescription() {
		return kycRiskReasonDescription;
	}

	public void setKycRiskReasonDescription(Object kycRiskReasonDescription) {
		this.kycRiskReasonDescription = kycRiskReasonDescription;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public Object getBirthPlace() {
		return birthPlace;
	}

	public void setBirthPlace(Object birthPlace) {
		this.birthPlace = birthPlace;
	}

	public Object getPrimaryProfile() {
		return primaryProfile;
	}

	public void setPrimaryProfile(Object primaryProfile) {
		this.primaryProfile = primaryProfile;
	}

	public Object getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(Object organisationName) {
		this.organisationName = organisationName;
	}

	public Object getTotalMonthExperience() {
		return totalMonthExperience;
	}

	public void setTotalMonthExperience(Object totalMonthExperience) {
		this.totalMonthExperience = totalMonthExperience;
	}

	public Integer getMonthlyDeductions() {
		return monthlyDeductions;
	}

	public void setMonthlyDeductions(Integer monthlyDeductions) {
		this.monthlyDeductions = monthlyDeductions;
	}

	public String getConstitutionCode() {
		return constitutionCode;
	}

	public void setConstitutionCode(String constitutionCode) {
		this.constitutionCode = constitutionCode;
	}

	public Integer getSocialAllowance() {
		return socialAllowance;
	}

	public void setSocialAllowance(Integer socialAllowance) {
		this.socialAllowance = socialAllowance;
	}

	public Integer getVariableOvertime() {
		return variableOvertime;
	}

	public void setVariableOvertime(Integer variableOvertime) {
		this.variableOvertime = variableOvertime;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public Integer getOtherMonthlyIncome() {
		return otherMonthlyIncome;
	}

	public void setOtherMonthlyIncome(Integer otherMonthlyIncome) {
		this.otherMonthlyIncome = otherMonthlyIncome;
	}

	public Object getMfullName() {
		return mfullName;
	}

	public void setMfullName(Object mfullName) {
		this.mfullName = mfullName;
	}

	public Object getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(Object qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public Integer getMonthlyCommission() {
		return monthlyCommission;
	}

	public void setMonthlyCommission(Integer monthlyCommission) {
		this.monthlyCommission = monthlyCommission;
	}

	public Integer getCarAllowance() {
		return carAllowance;
	}

	public void setCarAllowance(Integer carAllowance) {
		this.carAllowance = carAllowance;
	}

	public Integer getFixedOvertime() {
		return fixedOvertime;
	}

	public void setFixedOvertime(Integer fixedOvertime) {
		this.fixedOvertime = fixedOvertime;
	}

	public Object getHomeBranch() {
		return homeBranch;
	}

	public void setHomeBranch(Object homeBranch) {
		this.homeBranch = homeBranch;
	}

	public Object getMfirstName() {
		return mfirstName;
	}

	public void setMfirstName(Object mfirstName) {
		this.mfirstName = mfirstName;
	}

	public Object getConsolidatedStatementLanguage() {
		return consolidatedStatementLanguage;
	}

	public void setConsolidatedStatementLanguage(Object consolidatedStatementLanguage) {
		this.consolidatedStatementLanguage = consolidatedStatementLanguage;
	}

	public Integer getMonthlyBasic() {
		return monthlyBasic;
	}

	public void setMonthlyBasic(Integer monthlyBasic) {
		this.monthlyBasic = monthlyBasic;
	}

	public Integer getBachelorAllowance() {
		return bachelorAllowance;
	}

	public void setBachelorAllowance(Integer bachelorAllowance) {
		this.bachelorAllowance = bachelorAllowance;
	}

	public Object getCustomerSegmentCodeDescription() {
		return customerSegmentCodeDescription;
	}

	public void setCustomerSegmentCodeDescription(Object customerSegmentCodeDescription) {
		this.customerSegmentCodeDescription = customerSegmentCodeDescription;
	}

	public Object getMnatureOfBusiness() {
		return mnatureOfBusiness;
	}

	public void setMnatureOfBusiness(Object mnatureOfBusiness) {
		this.mnatureOfBusiness = mnatureOfBusiness;
	}

	public Object getArmCode() {
		return armCode;
	}

	public void setArmCode(Object armCode) {
		this.armCode = armCode;
	}

	public Object getEmployerCode() {
		return employerCode;
	}

	public void setEmployerCode(Object employerCode) {
		this.employerCode = employerCode;
	}

	public String getProfessionCode() {
		return professionCode;
	}

	public void setProfessionCode(String professionCode) {
		this.professionCode = professionCode;
	}

	public Integer getMedicalAllowance() {
		return medicalAllowance;
	}

	public void setMedicalAllowance(Integer medicalAllowance) {
		this.medicalAllowance = medicalAllowance;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Object getMsalutationCode() {
		return msalutationCode;
	}

	public void setMsalutationCode(Object msalutationCode) {
		this.msalutationCode = msalutationCode;
	}

	public Object getMpositionTitle() {
		return mpositionTitle;
	}

	public void setMpositionTitle(Object mpositionTitle) {
		this.mpositionTitle = mpositionTitle;
	}

	public Object getBsbdaFlag() {
		return bsbdaFlag;
	}

	public void setBsbdaFlag(Object bsbdaFlag) {
		this.bsbdaFlag = bsbdaFlag;
	}

	public Object getReferredByRelationshipNo() {
		return referredByRelationshipNo;
	}

	public void setReferredByRelationshipNo(Object referredByRelationshipNo) {
		this.referredByRelationshipNo = referredByRelationshipNo;
	}

	public Object getMplaceOfBirth() {
		return mplaceOfBirth;
	}

	public void setMplaceOfBirth(Object mplaceOfBirth) {
		this.mplaceOfBirth = mplaceOfBirth;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public Integer getGrossMonthlyIncome() {
		return grossMonthlyIncome;
	}

	public void setGrossMonthlyIncome(Integer grossMonthlyIncome) {
		this.grossMonthlyIncome = grossMonthlyIncome;
	}

	public Integer getUtilityAllowance() {
		return utilityAllowance;
	}

	public void setUtilityAllowance(Integer utilityAllowance) {
		this.utilityAllowance = utilityAllowance;
	}

	public Object getMmiddleName() {
		return mmiddleName;
	}

	public void setMmiddleName(Object mmiddleName) {
		this.mmiddleName = mmiddleName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Object getSegmentCode() {
		
		if (segmentCode == null || "".equalsIgnoreCase(segmentCode.toString())) {
			String modSegmentCode = "01";
			return modSegmentCode;
		}
		
		return segmentCode;
	}
	
	public void setSegmentCode(Object segmentCode) {
		this.segmentCode = segmentCode;
	}

	public Object getDateOfJoining() {
		return dateOfJoining;
	}

	public void setDateOfJoining(Object dateOfJoining) {
		this.dateOfJoining = dateOfJoining;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getResidentCountry() {
		return residentCountry;
	}

	public void setResidentCountry(String residentCountry) {
		this.residentCountry = residentCountry;
	}

	public Object getDepartment() {
		return department;
	}

	public void setDepartment(Object department) {
		this.department = department;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Object getSubSegmentCode() {
		return subSegmentCode;
	}

	public void setSubSegmentCode(Object subSegmentCode) {
		this.subSegmentCode = subSegmentCode;
	}

	public Integer getShiftAllowance() {
		return shiftAllowance;
	}

	public void setShiftAllowance(Integer shiftAllowance) {
		this.shiftAllowance = shiftAllowance;
	}

	public String getLastReviewDate() {
		return lastReviewDate;
	}

	public void setLastReviewDate(String lastReviewDate) {
		this.lastReviewDate = lastReviewDate;
	}

	public Integer getMonthlyAllowance() {
		return monthlyAllowance;
	}

	public void setMonthlyAllowance(Integer monthlyAllowance) {
		this.monthlyAllowance = monthlyAllowance;
	}

	public Object getMemployerName() {
		return memployerName;
	}

	public void setMemployerName(Object memployerName) {
		this.memployerName = memployerName;
	}

	public Object getNatureOfBusiness() {
		return natureOfBusiness;
	}

	public void setNatureOfBusiness(Object natureOfBusiness) {
		this.natureOfBusiness = natureOfBusiness;
	}

	public Object getMlanguageCode() {
		return mlanguageCode;
	}

	public void setMlanguageCode(Object mlanguageCode) {
		this.mlanguageCode = mlanguageCode;
	}

	public Object getLanguageCodeDescription() {
		return languageCodeDescription;
	}

	public void setLanguageCodeDescription(Object languageCodeDescription) {
		this.languageCodeDescription = languageCodeDescription;
	}

	public Integer getCostOfLivingAllowance() {
		return costOfLivingAllowance;
	}

	public void setCostOfLivingAllowance(Integer costOfLivingAllowance) {
		this.costOfLivingAllowance = costOfLivingAllowance;
	}

	public Object getAcquisitionChannel() {
		return acquisitionChannel;
	}

	public void setAcquisitionChannel(Object acquisitionChannel) {
		this.acquisitionChannel = acquisitionChannel;
	}

	public String getStaff() {
		return staff;
	}

	public void setStaff(String staff) {
		this.staff = staff;
	}

	public Object getProfileType() {
		return profileType;
	}

	public void setProfileType(Object profileType) {
		this.profileType = profileType;
	}

	public String getCorpCust() {
		return corpCust;
	}

	public void setCorpCust(String corpCust) {
		this.corpCust = corpCust;
	}

	public String getRelationshipStatus() {
		return relationshipStatus;
	}

	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getResidentStatus() {
		return residentStatus;
	}

	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}

	public Integer getMonthlyNetIncome() {
		return monthlyNetIncome;
	}

	public void setMonthlyNetIncome(Integer monthlyNetIncome) {
		this.monthlyNetIncome = monthlyNetIncome;
	}

	public String getStatusFlag() {
		return statusFlag;
	}

	public void setStatusFlag(String statusFlag) {
		this.statusFlag = statusFlag;
	}

	public String getDomicileCountry() {
		return domicileCountry;
	}

	public void setDomicileCountry(String domicileCountry) {
		this.domicileCountry = domicileCountry;
	}

	public Integer getOtherAllowance() {
		return otherAllowance;
	}

	public void setOtherAllowance(Integer otherAllowance) {
		this.otherAllowance = otherAllowance;
	}

	public Object getSegmentCodeDescription() {
		
		if (segmentCode == null || "".equalsIgnoreCase(segmentCode.toString())) {
			String modSegmentCodeDesc = "RETAIL";
			return modSegmentCodeDesc;
		}
		
		return segmentCodeDescription;
	}
	

	public void setSegmentCodeDescription(Object segmentCodeDescription) {
		this.segmentCodeDescription = segmentCodeDescription;
	}

	public Object getMpreferredCardName() {
		return mpreferredCardName;
	}

	public void setMpreferredCardName(Object mpreferredCardName) {
		this.mpreferredCardName = mpreferredCardName;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
