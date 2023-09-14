package com.scb.ivr.model.rkalees;

import com.scb.ivr.service.generalvo.CommonInput;

public class cardLost_Req extends CommonInput{
	private String cardNo;

	private String userId;

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	

}
