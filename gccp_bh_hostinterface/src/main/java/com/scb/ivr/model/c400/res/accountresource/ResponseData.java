package com.scb.ivr.model.c400.res.accountresource;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponseData implements Serializable {

	private static final long serialVersionUID = 1443900278618999949L;

	@JsonProperty("data")
	private List<Datum> data = null;
	
	@JsonProperty("meta")
	private Meta meta;
	
	public List<Datum> getData() {
		return data;
	}
	public void setData(List<Datum> data) {
		this.data = data;
	}

	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}
}
