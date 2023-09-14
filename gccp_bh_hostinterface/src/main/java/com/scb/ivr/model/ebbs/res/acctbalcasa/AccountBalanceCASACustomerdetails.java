package com.scb.ivr.model.ebbs.res.acctbalcasa;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceCASACustomerdetails {

	@JsonProperty("casadetails")
	private List<AccountBalanceCASAdetail> casadetails = null;
	@JsonProperty("profile-id")
	private String profileId;
	@JsonProperty("first-name")
	private String firstName;
	@JsonProperty("middle-name")
	private String middleName;
	@JsonProperty("last-name")
	private String lastName;
	@JsonProperty("full-name")
	private String fullName;
	@JsonProperty("gender")
	private String gender;

	public List<AccountBalanceCASAdetail> getCasadetails() {
		return casadetails;
	}

	public void setCasadetails(List<AccountBalanceCASAdetail> casadetails) {
		this.casadetails = casadetails;
	}

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
