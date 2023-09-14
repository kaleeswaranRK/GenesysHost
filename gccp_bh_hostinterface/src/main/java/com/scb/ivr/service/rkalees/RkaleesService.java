/**
 * 
 */
package com.scb.ivr.service.rkalees;

import java.util.Map;

import com.scb.ivr.model.rkalees.cardLost_Res;

/**
 * @author TA
 *
 */
public interface RkaleesService{

	public cardLost_Res cardLost(Map<String, Object> inputParams);

}
