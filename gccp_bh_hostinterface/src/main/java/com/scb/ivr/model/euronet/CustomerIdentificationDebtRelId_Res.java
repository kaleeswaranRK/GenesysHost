package com.scb.ivr.model.euronet;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.euronet.res.custidentifyrelid.CustIdentificationDebtRelIdResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CustomerIdentificationDebtRelId_Res extends CommonOutput {

	private CustIdentificationDebtRelIdResponseData response;

	public CustIdentificationDebtRelIdResponseData getResponse() {
		return response;
	}

	public void setResponse(CustIdentificationDebtRelIdResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
