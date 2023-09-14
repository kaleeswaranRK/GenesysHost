package com.scb.ivr.model.c400;

import com.scb.ivr.model.c400.res.ccresource.ResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

public class CardResource_Res extends CommonOutput{

	private ResponseData response;

	public ResponseData getResponse() {
		return response;
	}
	public void setResponse(ResponseData response) {
		this.response = response;
	}
	
}
