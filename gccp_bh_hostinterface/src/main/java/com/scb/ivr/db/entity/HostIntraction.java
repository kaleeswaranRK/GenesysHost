package com.scb.ivr.db.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQueries;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.ParameterMode;
import javax.persistence.StoredProcedureParameter;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.stereotype.Component;

@Entity
@Table(name = "HOST_TRANSACTION")
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "INSERT_HOST_TRANSACTION_SP", procedureName = "INSERT_HOST_TRANSACTION_SP", resultClasses = {
				HostIntraction.class }, parameters = {
						@StoredProcedureParameter(name = "I_UCID", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_HOST_OPERATION_NAME", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_SERVICE_ID", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_STARTTIME", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_ENDTIME", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_STATUS_CODE", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_STATUS_DISCRIPTION", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_TRACKING_ID", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_ID_TYPE", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_SEGMENT", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_HOTLINE", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_IP_ADDRESS", type = String.class, mode = ParameterMode.IN),
						@StoredProcedureParameter(name = "I_TP_SYSTEM", type = String.class, mode = ParameterMode.IN),

		}) })

@Component
public class HostIntraction implements Serializable

{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3145205556667442916L;

	@Id
	@Column(name = "UCID")
	private String uCID;

	@Column(name = "HOST_OPERATION_NAME")
	private String hOST_OPERATION_NAME;

	@Column(name = "SERVICE_ID")
	private String sERVICE_ID;

	@Column(name = "STARTTIME")
	private Date sTARTTIME;

	@Column(name = "ENDTIME")
	private Date eNDTIME;

	@Column(name = "STATUS_CODE")
	private String sTATUS_CODE;

	@Column(name = "STATUS_DISCRIPTION")
	private String sTATUS_DISCRIPTION;

	@Column(name = "TRACKING_ID")
	private String tRACKING_ID;

	@Column(name = "ID_TYPE")
	private String iD_TYPE;

	@Column(name = "SEGMENT")
	private Number sEGMENT;

	@Column(name = "HOTLINE")
	private Number hOTLINE;

	@Column(name = "IP_ADDRESS")
	private String iP_ADDRESS;

	@Column(name = "I_TP_SYSTEM")
	private String tP_SYSTEM;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
