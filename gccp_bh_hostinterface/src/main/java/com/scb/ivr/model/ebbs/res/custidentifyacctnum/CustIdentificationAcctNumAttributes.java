package com.scb.ivr.model.ebbs.res.custidentifyacctnum;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationAcctNumAttributes {

	@JsonProperty("casa-customerlinks")
	private Object casaCustomerlinks;
	@JsonProperty("casa-profile")
	private CustIdentificationAcctNumCasaProfile casaProfile;
	@JsonProperty("casa-customers")
	private List<CustIdentificationAcctNumCasaCustomer> casaCustomers = null;
	@JsonProperty("casa-link-customers")
	private Object casaLinkCustomers;
	@JsonProperty("casa-master")
	private CustIdentificationAcctNumCasaMaster casaMaster;

	public Object getCasaCustomerlinks() {
		return casaCustomerlinks;
	}

	public void setCasaCustomerlinks(Object casaCustomerlinks) {
		this.casaCustomerlinks = casaCustomerlinks;
	}

	public CustIdentificationAcctNumCasaProfile getCasaProfile() {
		return casaProfile;
	}

	public void setCasaProfile(CustIdentificationAcctNumCasaProfile casaProfile) {
		this.casaProfile = casaProfile;
	}

	public List<CustIdentificationAcctNumCasaCustomer> getCasaCustomers() {
		return casaCustomers;
	}

	public void setCasaCustomers(List<CustIdentificationAcctNumCasaCustomer> casaCustomers) {
		this.casaCustomers = casaCustomers;
	}

	public Object getCasaLinkCustomers() {
		return casaLinkCustomers;
	}

	public void setCasaLinkCustomers(Object casaLinkCustomers) {
		this.casaLinkCustomers = casaLinkCustomers;
	}

	public CustIdentificationAcctNumCasaMaster getCasaMaster() {
		return casaMaster;
	}

	public void setCasaMaster(CustIdentificationAcctNumCasaMaster casaMaster) {
		this.casaMaster = casaMaster;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
