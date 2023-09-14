package com.scb.ivr.model.c400.res.custprofile;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scb.ivr.model.ebbs.res.custidentifyrelcrp.CustIdentificationRelCRPDatum;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustProfileResponseData {

	@JsonProperty("data")
	private List<CustIdentificationRelCRPDatum> data = null;

	public List<CustIdentificationRelCRPDatum> getData() {
		return data;
	}

	public void setData(List<CustIdentificationRelCRPDatum> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
