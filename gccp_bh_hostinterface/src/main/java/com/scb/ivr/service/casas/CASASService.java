/**
 * 
 */
package com.scb.ivr.service.casas;

import java.util.Map;

import com.scb.ivr.model.casas.ChangeTPIN_Res;
import com.scb.ivr.model.casas.GenerateTPIN_Res;
import com.scb.ivr.model.casas.ValidateTPIN_Res;

/**
 * @author TA
 *
 */
public interface CASASService {

	GenerateTPIN_Res generateTPIN(Map<String, Object> inputParams);

	ValidateTPIN_Res validateTPIN(Map<String, Object> inputParams);

	ChangeTPIN_Res changeTPIN(Map<String, Object> inputParams);

}
