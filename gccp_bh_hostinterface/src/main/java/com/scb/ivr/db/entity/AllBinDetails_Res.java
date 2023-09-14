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
public class AllBinDetails_Res extends CommonOutput {
	private List<BinMaster_Res> binMasterList;

	public List<BinMaster_Res> getBinMasterList() {
		return binMasterList;
	}

	public void setBinMasterList(List<BinMaster_Res> binMasterList) {
		this.binMasterList = binMasterList;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
