package com.scb.ivr.model.euronet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.euronet.res.custidentifydebtcard.CustIdentificationDebtCardResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CustomerIdentificationDebtCardNum_Res extends CommonOutput {

	private CustIdentificationDebtCardResponseData response;

	public CustIdentificationDebtCardResponseData getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationDebtCardResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
