package com.scb.ivr.model.ebbs.res.custidentifyrmn;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationRMNProfile {
	@JsonProperty("profile-id")
	private String profileId;
	@JsonProperty("salutation-code")
	private Object salutationCode;
	@JsonProperty("full-name")
	private String fullName;
	@JsonProperty("gender")
	private String gender;
	@JsonProperty("resident-country")
	private String residentCountry;
	@JsonProperty("resident-status")
	private String residentStatus;
	@JsonProperty("profile-type")
	private Object profileType;
	@JsonProperty("relationship-type")
	private String relationshipType;
	@JsonProperty("relationship-status")
	private String relationshipStatus;
	@JsonProperty("first-name")
	private String firstName;
	@JsonProperty("last-name")
	private String lastName;
	@JsonProperty("middle-name")
	private Object middleName;
	@JsonProperty("date-of-birth")
	private String dateOfBirth;
	@JsonProperty("documents")
	private List<CustIdentificationRMNDocument> documents = null;
	@JsonProperty("contacts")
	private List<CustIdentificationRMNContact> contacts = null;

	public String getProfileId() {
		return profileId;
	}

	public void setProfileId(String profileId) {
		this.profileId = profileId;
	}

	public Object getSalutationCode() {
		return salutationCode;
	}

	public void setSalutationCode(Object salutationCode) {
		this.salutationCode = salutationCode;
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

	public String getResidentCountry() {
		return residentCountry;
	}

	public void setResidentCountry(String residentCountry) {
		this.residentCountry = residentCountry;
	}

	public String getResidentStatus() {
		return residentStatus;
	}

	public void setResidentStatus(String residentStatus) {
		this.residentStatus = residentStatus;
	}

	public Object getProfileType() {
		return profileType;
	}

	public void setProfileType(Object profileType) {
		this.profileType = profileType;
	}

	public String getRelationshipType() {
		return relationshipType;
	}

	public void setRelationshipType(String relationshipType) {
		this.relationshipType = relationshipType;
	}

	public String getRelationshipStatus() {
		return relationshipStatus;
	}

	public void setRelationshipStatus(String relationshipStatus) {
		this.relationshipStatus = relationshipStatus;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Object getMiddleName() {
		return middleName;
	}

	public void setMiddleName(Object middleName) {
		this.middleName = middleName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public List<CustIdentificationRMNDocument> getDocuments() {
		return documents;
	}

	public void setDocuments(List<CustIdentificationRMNDocument> documents) {
		this.documents = documents;
	}

	public List<CustIdentificationRMNContact> getContacts() {
		return contacts;
	}

	public void setContacts(List<CustIdentificationRMNContact> contacts) {
		this.contacts = contacts;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
