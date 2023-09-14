/**
 * 
 */
package com.scb.ivr.db.entity;

import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.scb.ivr.service.generalvo.CommonOutput;

/**
 * @author TA
 *
 */
public class IncomingPoints_Res extends CommonOutput {

	private List<IncomingPoints> incomingPointsList = null;

	public List<IncomingPoints> getIncomingPointsList() {
		return incomingPointsList;
	}

	public void setIncomingPointsList(List<IncomingPoints> incomingPointsList) {
		this.incomingPointsList = incomingPointsList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
