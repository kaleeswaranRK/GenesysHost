package com.scb.ivr.db.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name = "COMMON_CONFIG_DTLS")
@Component
public class CommonConfigDetails implements Serializable {

	@Id
	@Column(name = "ID")
	public int id;

	@Column(name = "TABLENAME")
	public String tableName;

	@Column(name = "COL1")
	public String col1;

	@Column(name = "COL2")
	public String col2;

	@Column(name = "COL3")
	public String col3;

	@Column(name = "COL4")
	public String col4;

	@Column(name = "COL5")
	public String col5;

	@Column(name = "COL6")
	public String col6;

	@Column(name = "COL7")
	public String col7;

	@Column(name = "COL8")
	public String col8;

	@Column(name = "COL9")
	public String col9;

	@Column(name = "COL10")
	public String col10;

	@Column(name = "COL11")
	public String col11;

	@Column(name = "COL12")
	public String col12;

	@Column(name = "COL13")
	public String col13;

	@Column(name = "COL14")
	public String col14;

	@Column(name = "COL15")
	public String col15;

	@Column(name = "DESCRIPTION")
	public String description;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getCol1() {
		return col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	public String getCol2() {
		return col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	public String getCol3() {
		return col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	public String getCol4() {
		return col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	public String getCol5() {
		return col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	public String getCol6() {
		return col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	public String getCol7() {
		return col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	public String getCol8() {
		return col8;
	}

	public void setCol8(String col8) {
		this.col8 = col8;
	}

	public String getCol9() {
		return col9;
	}

	public void setCol9(String col9) {
		this.col9 = col9;
	}

	public String getCol10() {
		return col10;
	}

	public void setCol10(String col10) {
		this.col10 = col10;
	}

	public String getCol11() {
		return col11;
	}

	public void setCol11(String col11) {
		this.col11 = col11;
	}

	public String getCol12() {
		return col12;
	}

	public void setCol12(String col12) {
		this.col12 = col12;
	}

	public String getCol13() {
		return col13;
	}

	public void setCol13(String col13) {
		this.col13 = col13;
	}

	public String getCol14() {
		return col14;
	}

	public void setCol14(String col14) {
		this.col14 = col14;
	}

	public String getCol15() {
		return col15;
	}

	public void setCol15(String col15) {
		this.col15 = col15;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {

		return tableName + "," + col1 + "," + col2 + "," + col3 + "," + col4 + "," + col5 + "," + col6 + "," + col7
				+ "," + col8 + "," + col9 + "," + col10 + "," + col11 + "," + col12 + "," + col13 + "," + col14 + ","
				+ col15 + "," + description;
	}

}
