package com.scb.ivr.model.c400.res.custidentifycardnum;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustIdentificationCardNumData implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public CustIdentificationCardNumAttributes attributes;

	public CustIdentificationCardNumAttributes getAttributes() {
		return attributes;
	}

	public void setAttributes(CustIdentificationCardNumAttributes attributes) {
		this.attributes = attributes;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
