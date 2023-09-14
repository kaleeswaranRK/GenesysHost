/**
 * 
 */
package com.scb.ivr.service.mdis;

import java.util.Map;

import com.scb.ivr.model.mdis.SendSMS_Res;

/**
 * @author TA
 *
 */
public interface MdisService {

	public SendSMS_Res sendSMS(Map<String, Object> inputParams);

}
