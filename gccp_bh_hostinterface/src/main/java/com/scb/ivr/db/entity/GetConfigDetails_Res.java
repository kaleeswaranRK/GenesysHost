package com.scb.ivr.db.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.service.generalvo.CommonOutput;

public class GetConfigDetails_Res extends CommonOutput {
	private List<CommonConfigDetails> commonConfigDetailsList;

	public List<CommonConfigDetails> getCommonConfigDetailsList() {
		return commonConfigDetailsList;
	}

	public void setCommonConfigDetailsList(List<CommonConfigDetails> getCommonConfigDetailsList) {
		this.commonConfigDetailsList = getCommonConfigDetailsList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
