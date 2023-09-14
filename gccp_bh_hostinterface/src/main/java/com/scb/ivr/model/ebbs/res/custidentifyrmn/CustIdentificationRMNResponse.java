package com.scb.ivr.model.ebbs.res.custidentifyrmn;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationRMNResponse {

	@JsonProperty("profile")
	private List<CustIdentificationRMNProfile> profile = null;
	@JsonProperty("matched-by-name-dob")
	private String matchedByNameDob;
	@JsonProperty("matched-by-documents-dob")
	private String matchedByDocumentsDob;
	@JsonProperty("matched-by-documents")
	private String matchedByDocuments;
	@JsonProperty("matched-by-mobile")
	private String matchedByMobile;
	@JsonProperty("matched-by-email")
	private String matchedByEmail;
	@JsonProperty("matched-by-mobile-dob")
	private String matchedByMobileDob;
	@JsonProperty("matched-by-email-dob")
	private String matchedByEmailDob;

	public List<CustIdentificationRMNProfile> getProfile() {
		return profile;
	}

	public void setProfile(List<CustIdentificationRMNProfile> profile) {
		this.profile = profile;
	}

	public String getMatchedByNameDob() {
		return matchedByNameDob;
	}

	public void setMatchedByNameDob(String matchedByNameDob) {
		this.matchedByNameDob = matchedByNameDob;
	}

	public String getMatchedByDocumentsDob() {
		return matchedByDocumentsDob;
	}

	public void setMatchedByDocumentsDob(String matchedByDocumentsDob) {
		this.matchedByDocumentsDob = matchedByDocumentsDob;
	}

	public String getMatchedByDocuments() {
		return matchedByDocuments;
	}

	public void setMatchedByDocuments(String matchedByDocuments) {
		this.matchedByDocuments = matchedByDocuments;
	}

	public String getMatchedByMobile() {
		return matchedByMobile;
	}

	public void setMatchedByMobile(String matchedByMobile) {
		this.matchedByMobile = matchedByMobile;
	}

	public String getMatchedByEmail() {
		return matchedByEmail;
	}

	public void setMatchedByEmail(String matchedByEmail) {
		this.matchedByEmail = matchedByEmail;
	}

	public String getMatchedByMobileDob() {
		return matchedByMobileDob;
	}

	public void setMatchedByMobileDob(String matchedByMobileDob) {
		this.matchedByMobileDob = matchedByMobileDob;
	}

	public String getMatchedByEmailDob() {
		return matchedByEmailDob;
	}

	public void setMatchedByEmailDob(String matchedByEmailDob) {
		this.matchedByEmailDob = matchedByEmailDob;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
