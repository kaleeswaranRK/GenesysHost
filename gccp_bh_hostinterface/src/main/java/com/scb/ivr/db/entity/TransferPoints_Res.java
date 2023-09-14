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
public class TransferPoints_Res extends CommonOutput {

	private List<TransferPoints> transferPointsList = null;

	public List<TransferPoints> getTransferPointsList() {
		return transferPointsList;
	}

	public void setTransferPointsList(List<TransferPoints> transferPointsList) {
		this.transferPointsList = transferPointsList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}