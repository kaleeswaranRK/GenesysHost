/**
 * 
 */
package com.scb.ivr.db.entity;

/**
 * @author TA
 *
 */
public class ContextCTI_Req {

	private String ucid;
	private String session_id;
	private String created_on;
	private String cti_context_data;

	public String getUcid() {
		return ucid;
	}

	public void setUcid(String ucid) {
		this.ucid = ucid;
	}

	public String getSession_id() {
		return session_id;
	}

	public void setSession_id(String session_id) {
		this.session_id = session_id;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getCti_context_data() {
		return cti_context_data;
	}

	public void setCti_context_data(String cti_context_data) {
		this.cti_context_data = cti_context_data;
	}

}
