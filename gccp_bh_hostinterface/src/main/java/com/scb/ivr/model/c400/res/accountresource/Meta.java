package com.scb.ivr.model.c400.res.accountresource;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Meta {

	@JsonProperty("totalResourceCount")
	private Integer totalResourceCount;
	
	public Integer getTotalResourceCount() {
		return totalResourceCount;
	}
	public void setTotalResourceCount(Integer totalResourceCount) {
		this.totalResourceCount = totalResourceCount;
	}

}
