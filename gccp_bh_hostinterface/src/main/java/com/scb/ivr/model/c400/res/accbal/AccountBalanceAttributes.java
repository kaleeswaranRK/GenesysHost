package com.scb.ivr.model.c400.res.accbal;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountBalanceAttributes {

	@JsonProperty("customer-id")
	private String customerId;

	@JsonProperty("card-num")
	private String cardNum;

	@JsonProperty("currency-code")
	private String currencyCode;

	@JsonProperty("current-balance")
	private String currentBalance;

	@JsonProperty("statement-balance")
	private String statementBalance;

	@JsonProperty("payment-due-date")
	private String paymentDueDate;

	@JsonProperty("outstanding-authorization-amount")
	private String outstandingAuthAmount;

	@JsonProperty("total-amount-due")
	private String totalAmountDue;

	//////////
	
//	@JsonProperty("account-number")
//	private String accountNumber;
//
//	@JsonProperty("account-status")
//	private String accountStatus;
//
//	@JsonProperty("product-code")
//	private String productCode;
//
//	@JsonProperty("product-description")
//	private String productDescription;

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getCurrentBalance() {
		return getConvertedBalanceFormat(currencyCode, currentBalance);
	}

	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}

	public String getStatementBalance() {
		return getConvertedBalanceFormat(currencyCode, statementBalance);
	}

	public void setStatementBalance(String statementBalance) {
		this.statementBalance = statementBalance;
	}

	public String getPaymentDueDate() {

		if (paymentDueDate != null && !"".equalsIgnoreCase(paymentDueDate)) {
			try {
				DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ",
						Locale.ENGLISH);
				DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
				LocalDate date = LocalDate.parse(paymentDueDate, inputFormatter);
				String formattedDate = outputFormatter.format(date);
				return formattedDate;
			} catch (Exception e) {
				return paymentDueDate;
			}

		}
		return paymentDueDate;
	}

	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public String getOutstandingAuthAmount() {
		return getConvertedBalanceFormat(currencyCode, outstandingAuthAmount);
	}

	public void setOutstandingAuthAmount(String outstandingAuthAmount) {
		this.outstandingAuthAmount = outstandingAuthAmount;
	}

	public String getTotalAmountDue() {
		return getConvertedBalanceFormat(currencyCode, totalAmountDue);
	}

	public void setTotalAmountDue(String totalAmountDue) {
		this.totalAmountDue = totalAmountDue;
	}

//	public String getAccountNumber() {
//		return accountNumber;
//	}
//
//	public void setAccountNumber(String accountNumber) {
//		this.accountNumber = accountNumber;
//	}
//
//	public String getAccountStatus() {
//		return accountStatus;
//	}
//
//	public void setAccountStatus(String accountStatus) {
//		this.accountStatus = accountStatus;
//	}
//
//	public String getProductCode() {
//		return productCode;
//	}
//
//	public void setProductCode(String productCode) {
//		this.productCode = productCode;
//	}
//
//	public String getProductDescription() {
//		return productDescription;
//	}
//
//	public void setProductDescription(String productDescription) {
//		this.productDescription = productDescription;
//	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

	public String getConvertedBalanceFormat(String ccyCode, String balanceAmount) {
		if (ccyCode != null) {
			if ("BHD".equalsIgnoreCase(ccyCode)) {
				DecimalFormat df = new DecimalFormat("###0.000");

				if (balanceAmount != null) {
					return String.valueOf(df.format(Double.parseDouble(balanceAmount)));
				} else {
					return balanceAmount;
				}
			} else {
				DecimalFormat df = new DecimalFormat("###0.00");

				if (balanceAmount != null) {
					return String.valueOf(df.format(Double.parseDouble(balanceAmount)));
				} else {
					return balanceAmount;
				}
			}
		} else {
			return balanceAmount;
		}
	}
}
