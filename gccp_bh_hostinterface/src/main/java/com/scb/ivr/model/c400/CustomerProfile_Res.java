package com.scb.ivr.model.c400;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.scb.ivr.model.c400.res.custprofile.CustProductResponseData;
import com.scb.ivr.model.c400.res.custprofile.CustProfileResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerProfile_Res extends CommonOutput {

	private CustProductResponseData custProduct = null;

	private CustProfileResponseData custProfile;

	public CustProductResponseData getCustProduct() {
		return custProduct;
	}

	public void setCustProduct(CustProductResponseData custProduct) {
		this.custProduct = custProduct;
	}

	public CustProfileResponseData getCustProfile() {
		return custProfile;
	}

	public void setCustProfile(CustProfileResponseData custProfile) {
		this.custProfile = custProfile;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
