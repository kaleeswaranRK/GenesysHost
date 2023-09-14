package com.scb.ivr.model.rkalees.res.cardLost;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Meta {
	public int totalResourceCount;

	public int getTotalResourceCount() {
		return totalResourceCount;
	}

	public void setTotalResourceCount(int totalResourceCount) {
		this.totalResourceCount = totalResourceCount;
	}

	@Override
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
}
