/**
 * 
 */
package com.scb.ivr.model.casas;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.service.generalvo.CommonOutput;

/**
 * @author TA
 *
 */
public class ValidateTPIN_Res extends CommonOutput {
	private String code;
	private boolean isBlocked;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
