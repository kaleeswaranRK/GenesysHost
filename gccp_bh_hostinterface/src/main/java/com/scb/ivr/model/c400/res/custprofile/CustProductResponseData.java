package com.scb.ivr.model.c400.res.custprofile;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.scb.ivr.model.c400.res.custidentifycardnum.CustIdentificationCardNumData;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustProductResponseData {
	@JsonProperty("data")
	private List<CustIdentificationCardNumData> data;

	public List<CustIdentificationCardNumData> getData() {
		return data;
	}

	public void setData(List<CustIdentificationCardNumData> data) {
		this.data = data;
	}

}
