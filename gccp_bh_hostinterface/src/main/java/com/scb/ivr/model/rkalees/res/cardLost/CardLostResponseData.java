package com.scb.ivr.model.rkalees.res.cardLost;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown=true)
public class CardLostResponseData {

	@JsonProperty("data")
	private List<Data> data;
	
	@JsonProperty("meta")
	private Meta meta;
	
	@JsonProperty("links")
	private List<Links> links;

	public List<Data> getData() {
		return data;
	}
	public void setData(List<Data> data) {
		this.data = data;
	}

	public Meta getMeta() {
		return meta;
	}
	public void setMeta(Meta meta) {
		this.meta = meta;
	}

	public List<Links> getLinks() {
		return links;
	}
	public void setLinks(List<Links> links) {
		this.links = links;
	}
	@Override
	public String toString() {
		return "CardLostResponseData [data=" + data + ", meta=" + meta + ", links=" + links + "]";
	}
	

}
