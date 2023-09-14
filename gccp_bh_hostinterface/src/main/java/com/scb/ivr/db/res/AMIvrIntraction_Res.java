/**
 * 
 */
package com.scb.ivr.db.res;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.db.entity.AMIvrIntraction;
import com.scb.ivr.service.generalvo.CommonOutput;

/**
 * @author TA
 *
 */
public class AMIvrIntraction_Res extends CommonOutput {

	private AMIvrIntraction response;

	public AMIvrIntraction getResponse() {
		return response;
	}

	public void setResponse(AMIvrIntraction response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
