/**
 * 
 */
package com.scb.ivr.db.entity;

/**
 * @author TA
 *
 */

/*@Entity
@Table(name = "BIN_MASTER_DTLS")
@NamedStoredProcedureQueries({
		@NamedStoredProcedureQuery(name = "binMaster.getBinMasterDtls", procedureName = "BIN_DETAILS_SP", resultClasses = BinMasterEntity.class, parameters = {
				@StoredProcedureParameter(mode = ParameterMode.IN, name = "BIN_NUMBER", type = String.class),
				@StoredProcedureParameter(mode = ParameterMode.REF_CURSOR, name = "BIN_REF", type = void.class), }) })
@Component*/
public class BinMasterEntity {

	/*@Id*/
	private Long bin;
	private String card_type;
	private String card_name;
	private String host;
	private String card_name_desc;
	private String card_group;
	private String card_subgroup;
	private String company_code;
	private String currency_code;
	private String credit_limit_flag;
	private String avail_limit_flag;
	private String ubpayment_flag;
	private String card_prod_type;

	

	public Long getBin() {
		return bin;
	}

	public void setBin(Long bin) {
		this.bin = bin;
	}

	public String getCard_type() {
		return card_type;
	}

	public void setCard_type(String card_type) {
		this.card_type = card_type;
	}

	public String getCard_name() {
		return card_name;
	}

	public void setCard_name(String card_name) {
		this.card_name = card_name;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getCard_name_desc() {
		return card_name_desc;
	}

	public void setCard_name_desc(String card_name_desc) {
		this.card_name_desc = card_name_desc;
	}

	public String getCard_group() {
		return card_group;
	}

	public void setCard_group(String card_group) {
		this.card_group = card_group;
	}

	public String getCard_subgroup() {
		return card_subgroup;
	}

	public void setCard_subgroup(String card_subgroup) {
		this.card_subgroup = card_subgroup;
	}

	public String getCompany_code() {
		return company_code;
	}

	public void setCompany_code(String company_code) {
		this.company_code = company_code;
	}

	public String getCurrency_code() {
		return currency_code;
	}

	public void setCurrency_code(String currency_code) {
		this.currency_code = currency_code;
	}

	public String getCredit_limit_flag() {
		return credit_limit_flag;
	}

	public void setCredit_limit_flag(String credit_limit_flag) {
		this.credit_limit_flag = credit_limit_flag;
	}

	public String getAvail_limit_flag() {
		return avail_limit_flag;
	}

	public void setAvail_limit_flag(String avail_limit_flag) {
		this.avail_limit_flag = avail_limit_flag;
	}

	public String getUbpayment_flag() {
		return ubpayment_flag;
	}

	public void setUbpayment_flag(String ubpayment_flag) {
		this.ubpayment_flag = ubpayment_flag;
	}

	public String getCard_prod_type() {
		return card_prod_type;
	}

	public void setCard_prod_type(String card_prod_type) {
		this.card_prod_type = card_prod_type;
	}

	@Override
	public String toString() {
		return "BinMasterEntity [id="  + ", bin=" + bin + ", card_type=" + card_type + ", card_name=" + card_name
				+ ", host=" + host + ", card_name_desc=" + card_name_desc + ", card_group=" + card_group
				+ ", card_subgroup=" + card_subgroup + ", company_code=" + company_code + ", currency_code="
				+ currency_code + ", credit_limit_flag=" + credit_limit_flag + ", avail_limit_flag=" + avail_limit_flag
				+ ", ubpayment_flag=" + ubpayment_flag + ", card_prod_type=" + card_prod_type + "]";
	}

}
