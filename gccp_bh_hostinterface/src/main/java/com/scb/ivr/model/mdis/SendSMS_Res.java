/**
 * 
 */
package com.scb.ivr.model.mdis;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.model.mdis.res.sendsms.SendSMSResponseData;
import com.scb.ivr.service.generalvo.CommonOutput;

/**
 * @author TA
 *
 */
public class SendSMS_Res extends CommonOutput {

	private SendSMSResponseData response;

	public SendSMSResponseData getResponse() {
		return response;
	}

	public void setResponse(SendSMSResponseData response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
