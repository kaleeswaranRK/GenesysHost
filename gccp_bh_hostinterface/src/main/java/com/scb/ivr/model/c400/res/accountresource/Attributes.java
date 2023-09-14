package com.scb.ivr.model.c400.res.accountresource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Attributes {

	@JsonProperty("times-in-arrear")
	private Integer timesInArrear;
	@JsonProperty("interest-tier-rate-effective-date")
	private Object interestTierRateEffectiveDate;
	@JsonProperty("cash-standard-interest")
	private Object cashStandardInterest;
	@JsonProperty("last-consolidated-statement-status")
	private String lastConsolidatedStatementStatus;
	@JsonProperty("charge-off-status-change-date")
	private Object chargeOffStatusChangeDate;
	@JsonProperty("retail-amount-payment-reversal")
	private Object retailAmountPaymentReversal;
	@JsonProperty("cash-number-debit")
	private Object cashNumberDebit;
	@JsonProperty("online-cash-payment")
	private Object onlineCashPayment;
	@JsonProperty("interest-earned-tax-year-to-date")
	private Object interestEarnedTaxYearToDate;
	@JsonProperty("retail-member-bill-not-paid")
	private Object retailMemberBillNotPaid;
	@JsonProperty("thirteen-month-date")
	private Object thirteenMonthDate;
	@JsonProperty("retail-year-to-date-interest-paid")
	private Object retailYearToDateInterestPaid;
	@JsonProperty("interest-earned-year-to-date")
	private Object interestEarnedYearToDate;
	@JsonProperty("number-of-outstading-auth")
	private Object numberOfOutstadingAuth;
	@JsonProperty("high-balance")
	private Object highBalance;
	@JsonProperty("last-year-to-date-intrest-billed")
	private Object lastYearToDateIntrestBilled;
	@JsonProperty("write-off-days")
	private Object writeOffDays;
	@JsonProperty("retail-interest-option")
	private Integer retailInterestOption;
	@JsonProperty("months-balance")
	private Object monthsBalance;
	@JsonProperty("amount-last-advance")
	private Object amountLastAdvance;
	@JsonProperty("cash-interest-limit2")
	private Object cashInterestLimit2;
	@JsonProperty("cash-interest-limit3")
	private Object cashInterestLimit3;
	@JsonProperty("repayment-score")
	private String repaymentScore;
	@JsonProperty("current-year-to-date-service-charge")
	private Object currentYearToDateServiceCharge;
	@JsonProperty("collection-reason")
	private String collectionReason;
	@JsonProperty("date-last-accrued")
	private Object dateLastAccrued;
	@JsonProperty("waive-service-charge")
	private Integer waiveServiceCharge;
	@JsonProperty("account-close-date")
	private Object accountCloseDate;
	@JsonProperty("accrued-thru-date")
	private Object accruedThruDate;
	@JsonProperty("date-last-active")
	private Object dateLastActive;
	@JsonProperty("mobile-payment-date")
	private Object mobilePaymentDate;
	@JsonProperty("months-purchase-count")
	private Object monthsPurchaseCount;
	@JsonProperty("retail-accrued-interest")
	private Object retailAccruedInterest;
	@JsonProperty("days150-delinquency")
	private Object days150Delinquency;
	@JsonProperty("retail-last-year-to-date-interest-paid")
	private Object retailLastYearToDateInterestPaid;
	@JsonProperty("limit-changed-score")
	private String limitChangedScore;
	@JsonProperty("date-last-maintained")
	private Object dateLastMaintained;
	@JsonProperty("cash-amount-credit")
	private Object cashAmountCredit;
	@JsonProperty("last-payment-date")
	private Object lastPaymentDate;
	@JsonProperty("tax-fiscal-year-to-date")
	private Object taxFiscalYearToDate;
	@JsonProperty("skip-payment-date")
	private Object skipPaymentDate;
	@JsonProperty("status-change-flag")
	private Integer statusChangeFlag;
	@JsonProperty("number-of-insurance-policies")
	private Integer numberOfInsurancePolicies;
	@JsonProperty("payment-bucket")
	private List<Object> paymentBucket = null;
	@JsonProperty("cycle-due")
	private Integer cycleDue;
	@JsonProperty("cash-interest-limit1")
	private Object cashInterestLimit1;
	@JsonProperty("retail-interest-bill-not-paid-last-statement")
	private Object retailInterestBillNotPaidLastStatement;
	@JsonProperty("retail-negative-provisional-balance")
	private Object retailNegativeProvisionalBalance;
	@JsonProperty("retail-interest-adjustment1")
	private Integer retailInterestAdjustment1;
	@JsonProperty("retail-interest-rate3")
	private Object retailInterestRate3;
	@JsonProperty("retail-interest-rate2")
	private Object retailInterestRate2;
	@JsonProperty("cash-credit-adjust-bill-not-paid")
	private Object cashCreditAdjustBillNotPaid;
	@JsonProperty("date-last-advance")
	private Object dateLastAdvance;
	@JsonProperty("retail-interest-rate1")
	private Object retailInterestRate1;
	@JsonProperty("annual-fee")
	private Object annualFee;
	@JsonProperty("current-balance")
	private Object currentBalance;
	@JsonProperty("wallet-available-balance")
	private Object walletAvailableBalance;
	@JsonProperty("retail-interest-adjustment3")
	private Object retailInterestAdjustment3;
	@JsonProperty("unbilled-debit-amount")
	private Object unbilledDebitAmount;
	@JsonProperty("retail-interest-adjustment2")
	private Integer retailInterestAdjustment2;
	@JsonProperty("cash-interest-flag")
	private Integer cashInterestFlag;
	@JsonProperty("aggregate-last-year-to-date-days")
	private Object aggregateLastYearToDateDays;
	@JsonProperty("current-due")
	private Object currentDue;
	@JsonProperty("date-next-statement")
	private Object dateNextStatement;
	@JsonProperty("statement-flag")
	private Integer statementFlag;
	@JsonProperty("last-authorization-date")
	private Object lastAuthorizationDate;
	@JsonProperty("last-date-payment-due")
	private Object lastDatePaymentDue;
	@JsonProperty("waive-late-notice")
	private Integer waiveLateNotice;
	@JsonProperty("retail-standard-interest")
	private Object retailStandardInterest;
	@JsonProperty("cash-accrued-interest")
	private Object cashAccruedInterest;
	@JsonProperty("number-returned-checks")
	private Object numberReturnedChecks;
	@JsonProperty("first-late-notice-date")
	private Object firstLateNoticeDate;
	@JsonProperty("payment-table-high-balance")
	private Object paymentTableHighBalance;
	@JsonProperty("date-credit-balance-range")
	private Object dateCreditBalanceRange;
	@JsonProperty("billing-cycle")
	private Integer billingCycle;
	@JsonProperty("amount-auth-today")
	private Object amountAuthToday;
	@JsonProperty("source-code")
	private String sourceCode;
	@JsonProperty("negative-anticipated-provisional-interest-amount")
	private String negativeAnticipatedProvisionalInterestAmount;
	@JsonProperty("cash-interest-change-flag")
	private Integer cashInterestChangeFlag;
	@JsonProperty("aggregate-last-year-to-date-balance")
	private Object aggregateLastYearToDateBalance;
	@JsonProperty("user-code3")
	private String userCode3;
	@JsonProperty("cash-advance-fee")
	private Object cashAdvanceFee;
	@JsonProperty("user-code4")
	private String userCode4;
	@JsonProperty("duplicate-statement-number")
	private Object duplicateStatementNumber;
	@JsonProperty("last-transaction-date")
	private Object lastTransactionDate;
	@JsonProperty("cash-disputed-balance")
	private Object cashDisputedBalance;
	@JsonProperty("user-code2")
	private String userCode2;
	@JsonProperty("cash-interest-bill-not-paid")
	private Object cashInterestBillNotPaid;
	@JsonProperty("credit-balance-refund-days")
	private Object creditBalanceRefundDays;
	@JsonProperty("charge-off-status-flag")
	private Integer chargeOffStatusFlag;
	@JsonProperty("days180-delinquency")
	private Object days180Delinquency;
	@JsonProperty("account-number")
	private String accountNumber;
	@JsonProperty("retail-current-month-balance")
	private Object retailCurrentMonthBalance;
	@JsonProperty("account-cash-limit")
	private Object accountCashLimit;
	@JsonProperty("waive-joining-fee")
	private Integer waiveJoiningFee;
	@JsonProperty("online-outstanding-current-balance")
	private Object onlineOutstandingCurrentBalance;
	@JsonProperty("interest-earned-prior-year-to-date")
	private Object interestEarnedPriorYearToDate;
	@JsonProperty("retail-credit-adjust-bill-not-paid")
	private Object retailCreditAdjustBillNotPaid;
	@JsonProperty("cash-amount-debit")
	private Object cashAmountDebit;
	@JsonProperty("payment-pending")
	private String paymentPending;
	@JsonProperty("retail-number-debit")
	private Object retailNumberDebit;
	@JsonProperty("online-total-retail-instalment-current")
	private Object onlineTotalRetailInstalmentCurrent;
	@JsonProperty("consolidated-statement-status")
	private String consolidatedStatementStatus;
	@JsonProperty("card-fee-date")
	private Object cardFeeDate;
	@JsonProperty("date-last-rate-change")
	private Object dateLastRateChange;
	@JsonProperty("arrears-level")
	private Integer arrearsLevel;
	@JsonProperty("last-min-payment")
	private Object lastMinPayment;
	@JsonProperty("number-of-auth-today")
	private Object numberOfAuthToday;
	@JsonProperty("waive-annual-fee")
	private Integer waiveAnnualFee;
	@JsonProperty("cash-advance-outstanding-authorization")
	private Object cashAdvanceOutstandingAuthorization;
	@JsonProperty("date-last-retured-check")
	private Object dateLastReturedCheck;
	@JsonProperty("payment-skip-flag")
	private Integer paymentSkipFlag;
	@JsonProperty("date-into-collection")
	private Object dateIntoCollection;
	@JsonProperty("retail-amount-credit")
	private Object retailAmountCredit;
	@JsonProperty("last-payment-change")
	private Object lastPaymentChange;
	@JsonProperty("aggregate-retail-days")
	private Integer aggregateRetailDays;
	@JsonProperty("aggregate-year-to-date-balance")
	private Object aggregateYearToDateBalance;
	@JsonProperty("current-revolver-flag")
	private String currentRevolverFlag;
	@JsonProperty("pending-product-offering-table-exp-date")
	private Object pendingProductOfferingTableExpDate;
	@JsonProperty("thirteen-month-min-payment")
	private Object thirteenMonthMinPayment;
	@JsonProperty("product-offering-table-exp-date")
	private Object productOfferingTableExpDate;
	@JsonProperty("retail-negative-provisional-interest")
	private Object retailNegativeProvisionalInterest;
	@JsonProperty("date-prior-delinquency")
	private Object datePriorDelinquency;
	@JsonProperty("cash-interest-adjustment3")
	private Object cashInterestAdjustment3;
	@JsonProperty("bank-charges-fee")
	private Object bankChargesFee;
	@JsonProperty("cash-interest-adjustment2")
	private Integer cashInterestAdjustment2;
	@JsonProperty("second-late-notice-ctr")
	private Object secondLateNoticeCtr;
	@JsonProperty("cash-interest-adjustment1")
	private Integer cashInterestAdjustment1;
	@JsonProperty("online-total-avail-credit")
	private Object onlineTotalAvailCredit;
	@JsonProperty("date-last-cash-authorized")
	private Object dateLastCashAuthorized;
	@JsonProperty("duality-flag")
	private String dualityFlag;
	@JsonProperty("savings-account")
	private Object savingsAccount;
	@JsonProperty("cash-amount-payment-reversal")
	private Object cashAmountPaymentReversal;
	@JsonProperty("ownership-flag")
	private String ownershipFlag;
	@JsonProperty("interest-earned-accrued")
	private Object interestEarnedAccrued;
	@JsonProperty("delinquency-counter")
	private Object delinquencyCounter;
	@JsonProperty("last-payment-amount")
	private Object lastPaymentAmount;
	@JsonProperty("high-balance-date")
	private Object highBalanceDate;
	@JsonProperty("previous-revolver-flag")
	private String previousRevolverFlag;
	@JsonProperty("aggregate-cash-days")
	private Integer aggregateCashDays;
	@JsonProperty("cash-interest-bill-not-paid-last-statement")
	private Object cashInterestBillNotPaidLastStatement;
	@JsonProperty("online-cycle-to-date-payment-amount")
	private Object onlineCycleToDatePaymentAmount;
	@JsonProperty("gst-date-maint")
	private Object gstDateMaint;
	@JsonProperty("product-description")
	private String productDescription;
	@JsonProperty("user-account")
	private Object userAccount;
	@JsonProperty("waive-late-charge")
	private Integer waiveLateCharge;
	@JsonProperty("retail-balance")
	private Object retailBalance;
	@JsonProperty("user-date2")
	private Object userDate2;
	@JsonProperty("days210-delinquency")
	private Object days210Delinquency;
	@JsonProperty("total-interest")
	private Object totalInterest;
	@JsonProperty("online-outstanding-total-amount-due")
	private Object onlineOutstandingTotalAmountDue;
	@JsonProperty("interest-chain-sw")
	private Integer interestChainSw;
	@JsonProperty("cash-year-to-date-interest-billed")
	private Object cashYearToDateInterestBilled;
	@JsonProperty("thirteen-month-age-cd")
	private Integer thirteenMonthAgeCd;
	@JsonProperty("amount-prepaid")
	private Object amountPrepaid;
	@JsonProperty("last-payment-switch")
	private String lastPaymentSwitch;
	@JsonProperty("account-limit")
	private Object accountLimit;
	@JsonProperty("gst-tax-invoice-flag")
	private Integer gstTaxInvoiceFlag;
	@JsonProperty("product-offering-table-hist2")
	private Integer productOfferingTableHist2;
	@JsonProperty("product-offering-table-hist3")
	private Integer productOfferingTableHist3;
	@JsonProperty("online-credit-balance-refund-amount")
	private Object onlineCreditBalanceRefundAmount;
	@JsonProperty("thirteen-month-retail")
	private Object thirteenMonthRetail;
	@JsonProperty("product-offering-table-hist4")
	private Integer productOfferingTableHist4;
	@JsonProperty("product-offering-table-hist5")
	private Integer productOfferingTableHist5;
	@JsonProperty("statement-balance")
	private Object statementBalance;
	@JsonProperty("delinquencyHistories")
	private List<DelinquencyHistory> delinquencyHistories = null;
	@JsonProperty("beginning-cycle-due")
	private Integer beginningCycleDue;
	@JsonProperty("product-offering-table-hist1")
	private Integer productOfferingTableHist1;
	@JsonProperty("unbilled-credit-amount")
	private Object unbilledCreditAmount;
	@JsonProperty("statement-frequency")
	private Integer statementFrequency;
	@JsonProperty("restructure-balance")
	private Object restructureBalance;
	@JsonProperty("date-table-hi-balance")
	private Object dateTableHiBalance;
	@JsonProperty("overlimit-option")
	private String overlimitOption;
	@JsonProperty("account-block-code")
	private Object accountBlockCode;
	@JsonProperty("retail-interest-limit1")
	private Object retailInterestLimit1;
	@JsonProperty("bank-charges-rate")
	private Object bankChargesRate;
	@JsonProperty("negative-provisionalnterest-amount")
	private String negativeProvisionalnterestAmount;
	@JsonProperty("retail-interest-limit3")
	private Object retailInterestLimit3;
	@JsonProperty("retail-interest-limit2")
	private Object retailInterestLimit2;
	@JsonProperty("late-fee-date")
	private Object lateFeeDate;
	@JsonProperty("risk-level")
	private String riskLevel;
	@JsonProperty("second-late-notice-date")
	private Object secondLateNoticeDate;
	@JsonProperty("budget-multiple")
	private Object budgetMultiple;
	@JsonProperty("cash-beginning-balance")
	private Object cashBeginningBalance;
	@JsonProperty("days90-delinquency")
	private Object days90Delinquency;
	@JsonProperty("online-total-retail-instalment-billed-not-paid")
	private Object onlineTotalRetailInstalmentBilledNotPaid;
	@JsonProperty("pend-product-offering-table-select-code")
	private Integer pendProductOfferingTableSelectCode;
	@JsonProperty("dda-bank-code")
	private Object ddaBankCode;
	@JsonProperty("retail-interest-bill-not-paid")
	private Object retailInterestBillNotPaid;
	@JsonProperty("product-offering-table-select-code")
	private Integer productOfferingTableSelectCode;
	@JsonProperty("retail-amount-charged-off")
	private Object retailAmountChargedOff;
	@JsonProperty("last-standing-instruction-reject")
	private Object lastStandingInstructionReject;
	@JsonProperty("reage-request")
	private String reageRequest;
	@JsonProperty("override-code")
	private Object overrideCode;
	@JsonProperty("number-disputed-items")
	private Object numberDisputedItems;
	@JsonProperty("retail-disputed-balance")
	private Object retailDisputedBalance;
	@JsonProperty("duplicate-statement-expiry-date")
	private Object duplicateStatementExpiryDate;
	@JsonProperty("date-last-cycle")
	private Object dateLastCycle;
	@JsonProperty("ach-flag")
	private Integer achFlag;
	@JsonProperty("oline-retail-payment")
	private Object olineRetailPayment;
	@JsonProperty("grace-days")
	private Object graceDays;
	@JsonProperty("cash-interest-option")
	private Integer cashInterestOption;
	@JsonProperty("date-last-debit")
	private Object dateLastDebit;
	@JsonProperty("days120-delinquency")
	private Object days120Delinquency;
	@JsonProperty("permanent-collector")
	private String permanentCollector;
	@JsonProperty("projected-accrued-interest")
	private String projectedAccruedInterest;
	@JsonProperty("number-declined-today")
	private Object numberDeclinedToday;
	@JsonProperty("card-fee-disclosure-flag")
	private String cardFeeDisclosureFlag;
	@JsonProperty("previous-collection-reason")
	private String previousCollectionReason;
	@JsonProperty("interest-earned-aggregate-days")
	private Object interestEarnedAggregateDays;
	@JsonProperty("agreement-id")
	private Object agreementId;
	@JsonProperty("agreement-status")
	private Integer agreementStatus;
	@JsonProperty("total-arrear-amount")
	private Object totalArrearAmount;
	@JsonProperty("account-available-cash")
	private Object accountAvailableCash;
	@JsonProperty("dda-amount-due")
	private Object ddaAmountDue;
	@JsonProperty("retail-provisional-balance")
	private Object retailProvisionalBalance;
	@JsonProperty("aggregate-cash-balance")
	private Object aggregateCashBalance;
	@JsonProperty("payment-flag")
	private Integer paymentFlag;
	@JsonProperty("account-status")
	private Object accountStatus;
	@JsonProperty("thirteen-month-balance")
	private Object thirteenMonthBalance;
	@JsonProperty("cash-advance-rate")
	private Object cashAdvanceRate;
	@JsonProperty("cash-last-year-to-date-interest-paid")
	private Object cashLastYearToDateInterestPaid;
	@JsonProperty("cycle")
	private Integer cycle;
	@JsonProperty("prepay-cycles-left")
	private Integer prepayCyclesLeft;
	@JsonProperty("thirteen-month-crlimit")
	private Object thirteenMonthCrlimit;
	@JsonProperty("amount-last-debit")
	private Object amountLastDebit;
	@JsonProperty("account-open-date")
	private Object accountOpenDate;
	@JsonProperty("account-officer")
	private String accountOfficer;
	@JsonProperty("outstanding-authorization-amount")
	private Object outstandingAuthorizationAmount;
	@JsonProperty("cash-amount-charged-off")
	private Object cashAmountChargedOff;
	@JsonProperty("retail-amount-debit")
	private Object retailAmountDebit;
	@JsonProperty("collateral-code")
	private String collateralCode;
	@JsonProperty("cash-year-to-date-interest-paid")
	private Object cashYearToDateInterestPaid;
	@JsonProperty("dda-percentage")
	private Integer ddaPercentage;
	@JsonProperty("overlimit-flag")
	private Integer overlimitFlag;
	@JsonProperty("cash-current-month-balance")
	private Object cashCurrentMonthBalance;
	@JsonProperty("overlimit-amount-due")
	private Object overlimitAmountDue;
	@JsonProperty("retail-negative-anticipated-interest")
	private Object retailNegativeAnticipatedInterest;
	@JsonProperty("cash-number-credit")
	private Object cashNumberCredit;
	@JsonProperty("collection-block-code")
	private String collectionBlockCode;
	@JsonProperty("non-posting-cycle-to-date-used-cash")
	private Object nonPostingCycleToDateUsedCash;
	@JsonProperty("intrest-per-diem")
	private Object intrestPerDiem;
	@JsonProperty("date-last-delinquency")
	private Object dateLastDelinquency;
	@JsonProperty("currency-code")
	private String currencyCode;
	@JsonProperty("retail-provisional-interest")
	private Object retailProvisionalInterest;
	@JsonProperty("cash-interest-rate3")
	private Object cashInterestRate3;
	@JsonProperty("last-payment-aged-count")
	private Object lastPaymentAgedCount;
	@JsonProperty("cash-interest-rate2")
	private Object cashInterestRate2;
	@JsonProperty("behaviour-score")
	private String behaviourScore;
	@JsonProperty("checking-account")
	private Object checkingAccount;
	@JsonProperty("cash-interest-rate1")
	private Object cashInterestRate1;
	@JsonProperty("tax-fiscal-prior-year-to-date")
	private Object taxFiscalPriorYearToDate;
	@JsonProperty("date-last-reage")
	private Object dateLastReage;
	@JsonProperty("risk-change-date")
	private Object riskChangeDate;
	@JsonProperty("interest-earned-prior-tax-year-to-date")
	private Object interestEarnedPriorTaxYearToDate;
	@JsonProperty("date-last-retail-payment")
	private Object dateLastRetailPayment;
	@JsonProperty("cash-payment-cycle-to-date")
	private Object cashPaymentCycleToDate;
	@JsonProperty("gst-registration-number")
	private String gstRegistrationNumber;
	@JsonProperty("online-total-payment-today")
	private Object onlineTotalPaymentToday;
	@JsonProperty("prior-product-offering-table-exp-date")
	private Object priorProductOfferingTableExpDate;
	@JsonProperty("fixed-payment-amount")
	private Object fixedPaymentAmount;
	@JsonProperty("mobile-payment-indicator")
	private String mobilePaymentIndicator;
	@JsonProperty("closing-balance")
	private Object closingBalance;
	@JsonProperty("first-payment-default-flag")
	private Integer firstPaymentDefaultFlag;
	@JsonProperty("gst-residence-code")
	private Object gstResidenceCode;
	@JsonProperty("current-collector")
	private String currentCollector;
	@JsonProperty("retail-year-to-date-interest-billed")
	private Object retailYearToDateInterestBilled;
	@JsonProperty("thirteen-month-cash")
	private Object thirteenMonthCash;
	@JsonProperty("temp-agreement-id")
	private Object tempAgreementId;
	@JsonProperty("retail-interest-effective-flag")
	private Integer retailInterestEffectiveFlag;
	@JsonProperty("retail-miscellaneous-fee")
	private Integer retailMiscellaneousFee;
	@JsonProperty("retail-negative-anticipated-balance")
	private Object retailNegativeAnticipatedBalance;
	@JsonProperty("retail-pricing-campaign-code2")
	private Integer retailPricingCampaignCode2;
	@JsonProperty("ach-payment-expiry-date")
	private Object achPaymentExpiryDate;
	@JsonProperty("prior-product-offering-table-select-code")
	private Integer priorProductOfferingTableSelectCode;
	@JsonProperty("retail-pricing-campaign-code1")
	private Integer retailPricingCampaignCode1;
	@JsonProperty("retail-pricing-campaign-code4")
	private Integer retailPricingCampaignCode4;
	@JsonProperty("retail-pricing-campaign-code3")
	private Integer retailPricingCampaignCode3;
	@JsonProperty("retail-pricing-campaign-code5")
	private Object retailPricingCampaignCode5;
	@JsonProperty("service-program-id")
	private Object serviceProgramId;
	@JsonProperty("collection-histoy-flag")
	private String collectionHistoyFlag;
	@JsonProperty("payment-due-date")
	private Object paymentDueDate;
	@JsonProperty("thirteen-month-payment")
	private Object thirteenMonthPayment;
	@JsonProperty("waive-over-limit")
	private Integer waiveOverLimit;
	@JsonProperty("date-last-cash-payment")
	private Object dateLastCashPayment;
	@JsonProperty("saadique-flag")
	private Object saadiqueFlag;
	@JsonProperty("retail-misc-cycle-to-date")
	private Object retailMiscCycleToDate;
	@JsonProperty("total-amount-due")
	private Object totalAmountDue;
	@JsonProperty("days60-delinquency")
	private Object days60Delinquency;
	@JsonProperty("payoff-amount2")
	private String payoffAmount2;
	@JsonProperty("interest-earned-aggregate-balance")
	private Object interestEarnedAggregateBalance;
	@JsonProperty("product-offering-table-exp-date-hist2")
	private Object productOfferingTableExpDateHist2;
	@JsonProperty("product-offering-table-exp-date-hist1")
	private Object productOfferingTableExpDateHist1;
	@JsonProperty("product-offering-table-exp-date-hist4")
	private Object productOfferingTableExpDateHist4;
	@JsonProperty("product-offering-table-exp-date-hist3")
	private Object productOfferingTableExpDateHist3;
	@JsonProperty("number-of-standing-instruction-reject")
	private Integer numberOfStandingInstructionReject;
	@JsonProperty("product-offering-table-exp-date-hist5")
	private Object productOfferingTableExpDateHist5;
	@JsonProperty("amount-declined-today")
	private Object amountDeclinedToday;
	@JsonProperty("dispute-balance")
	private Object disputeBalance;
	@JsonProperty("high-balance-payment-table-date")
	private Object highBalancePaymentTableDate;
	@JsonProperty("product-code")
	private String productCode;
	@JsonProperty("old-behaviour-score")
	private String oldBehaviourScore;
	@JsonProperty("non-posting-cycle-to-date-used-amount")
	private Object nonPostingCycleToDateUsedAmount;
	@JsonProperty("cash-balance")
	private Object cashBalance;
	@JsonProperty("amount-last-purchase")
	private Object amountLastPurchase;
	@JsonProperty("dda-account-number")
	private Object ddaAccountNumber;
	@JsonProperty("date-last-statement")
	private Object dateLastStatement;
	@JsonProperty("charge-off-reason-code2")
	private String chargeOffReasonCode2;
	@JsonProperty("charge-off-reason-code1")
	private String chargeOffReasonCode1;
	@JsonProperty("wallet-limit")
	private Integer walletLimit;
	@JsonProperty("retail-beginning-balance")
	private Object retailBeginningBalance;
	@JsonProperty("projected-provisionalnterest-amount")
	private String projectedProvisionalnterestAmount;
	@JsonProperty("waive-prepay")
	private Integer waivePrepay;
	@JsonProperty("retail-previous-month-balance")
	private Object retailPreviousMonthBalance;
	@JsonProperty("aggregate-year-to-date-days")
	private Object aggregateYearToDateDays;
	@JsonProperty("annual-fee-disclosure-sw")
	private String annualFeeDisclosureSw;
	@JsonProperty("aggregate-retail-balance")
	private Object aggregateRetailBalance;
	@JsonProperty("conversion-balance")
	private Object conversionBalance;
	@JsonProperty("retail-service-bill-not-paid")
	private Integer retailServiceBillNotPaid;
	@JsonProperty("retail-interest-change-flag")
	private Integer retailInterestChangeFlag;
	@JsonProperty("days30-delinquency")
	private Object days30Delinquency;
	@JsonProperty("exposure-at-risk")
	private Object exposureAtRisk;
	@JsonProperty("user-code")
	private String userCode;
	@JsonProperty("payoff-amount")
	private String payoffAmount;
	@JsonProperty("available-credit2")
	private Object availableCredit2;
	@JsonProperty("available-account-limit")
	private Object availableAccountLimit;
	@JsonProperty("previous-override-code")
	private Object previousOverrideCode;
	@JsonProperty("cash-service-bill-not-paid")
	private Object cashServiceBillNotPaid;
	@JsonProperty("retail-number-credit")
	private Object retailNumberCredit;
	@JsonProperty("date-credit-balance")
	private Object dateCreditBalance;
	@JsonProperty("collection-control-flag")
	private String collectionControlFlag;
	@JsonProperty("retail-payment-cycle-to-date")
	private Object retailPaymentCycleToDate;
	@JsonProperty("available-cash2")
	private Object availableCash2;
	@JsonProperty("online-outstanding-statement-balance")
	private Object onlineOutstandingStatementBalance;
	@JsonProperty("cash-advance-percent-fee-cycle-to-date")
	private Object cashAdvancePercentFeeCycleToDate;
	@JsonProperty("retail-insurance-bill-not-paid")
	private Integer retailInsuranceBillNotPaid;
	@JsonProperty("current-finance-charge-waiver-count")
	private Integer currentFinanceChargeWaiverCount;
	@JsonProperty("current-late-charge-waiver-count")
	private Integer currentLateChargeWaiverCount;
	@JsonProperty("current-overlimit-fee-waiver-count")
	private Integer currentOverlimitFeeWaiverCount;
	@JsonProperty("online-finance-charge-waiver-count")
	private Integer onlineFinanceChargeWaiverCount;
	@JsonProperty("online-late-charge-waiver-count")
	private Integer onlineLateChargeWaiverCount;
	@JsonProperty("online-overlimit-fee-waiver-count")
	private Integer onlineOverlimitFeeWaiverCount;
	@JsonProperty("feewaiverDetails")
	private List<FeewaiverDetail> feewaiverDetails = null;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	@JsonProperty("times-in-arrear")
	public Integer getTimesInArrear() {
		return timesInArrear;
	}

	@JsonProperty("times-in-arrear")
	public void setTimesInArrear(Integer timesInArrear) {
		this.timesInArrear = timesInArrear;
	}

	@JsonProperty("interest-tier-rate-effective-date")
	public Object getInterestTierRateEffectiveDate() {
		return interestTierRateEffectiveDate;
	}

	@JsonProperty("interest-tier-rate-effective-date")
	public void setInterestTierRateEffectiveDate(Object interestTierRateEffectiveDate) {
		this.interestTierRateEffectiveDate = interestTierRateEffectiveDate;
	}

	@JsonProperty("cash-standard-interest")
	public Object getCashStandardInterest() {
		return cashStandardInterest;
	}

	@JsonProperty("cash-standard-interest")
	public void setCashStandardInterest(Object cashStandardInterest) {
		this.cashStandardInterest = cashStandardInterest;
	}

	@JsonProperty("last-consolidated-statement-status")
	public String getLastConsolidatedStatementStatus() {
		return lastConsolidatedStatementStatus;
	}

	@JsonProperty("last-consolidated-statement-status")
	public void setLastConsolidatedStatementStatus(String lastConsolidatedStatementStatus) {
		this.lastConsolidatedStatementStatus = lastConsolidatedStatementStatus;
	}

	@JsonProperty("charge-off-status-change-date")
	public Object getChargeOffStatusChangeDate() {
		return chargeOffStatusChangeDate;
	}

	@JsonProperty("charge-off-status-change-date")
	public void setChargeOffStatusChangeDate(Object chargeOffStatusChangeDate) {
		this.chargeOffStatusChangeDate = chargeOffStatusChangeDate;
	}

	@JsonProperty("retail-amount-payment-reversal")
	public Object getRetailAmountPaymentReversal() {
		return retailAmountPaymentReversal;
	}

	@JsonProperty("retail-amount-payment-reversal")
	public void setRetailAmountPaymentReversal(Object retailAmountPaymentReversal) {
		this.retailAmountPaymentReversal = retailAmountPaymentReversal;
	}

	@JsonProperty("cash-number-debit")
	public Object getCashNumberDebit() {
		return cashNumberDebit;
	}

	@JsonProperty("cash-number-debit")
	public void setCashNumberDebit(Object cashNumberDebit) {
		this.cashNumberDebit = cashNumberDebit;
	}

	@JsonProperty("online-cash-payment")
	public Object getOnlineCashPayment() {
		return onlineCashPayment;
	}

	@JsonProperty("online-cash-payment")
	public void setOnlineCashPayment(Object onlineCashPayment) {
		this.onlineCashPayment = onlineCashPayment;
	}

	@JsonProperty("interest-earned-tax-year-to-date")
	public Object getInterestEarnedTaxYearToDate() {
		return interestEarnedTaxYearToDate;
	}

	@JsonProperty("interest-earned-tax-year-to-date")
	public void setInterestEarnedTaxYearToDate(Object interestEarnedTaxYearToDate) {
		this.interestEarnedTaxYearToDate = interestEarnedTaxYearToDate;
	}

	@JsonProperty("retail-member-bill-not-paid")
	public Object getRetailMemberBillNotPaid() {
		return retailMemberBillNotPaid;
	}

	@JsonProperty("retail-member-bill-not-paid")
	public void setRetailMemberBillNotPaid(Object retailMemberBillNotPaid) {
		this.retailMemberBillNotPaid = retailMemberBillNotPaid;
	}

	@JsonProperty("thirteen-month-date")
	public Object getThirteenMonthDate() {
		return thirteenMonthDate;
	}

	@JsonProperty("thirteen-month-date")
	public void setThirteenMonthDate(Object thirteenMonthDate) {
		this.thirteenMonthDate = thirteenMonthDate;
	}

	@JsonProperty("retail-year-to-date-interest-paid")
	public Object getRetailYearToDateInterestPaid() {
		return retailYearToDateInterestPaid;
	}

	@JsonProperty("retail-year-to-date-interest-paid")
	public void setRetailYearToDateInterestPaid(Object retailYearToDateInterestPaid) {
		this.retailYearToDateInterestPaid = retailYearToDateInterestPaid;
	}

	@JsonProperty("interest-earned-year-to-date")
	public Object getInterestEarnedYearToDate() {
		return interestEarnedYearToDate;
	}

	@JsonProperty("interest-earned-year-to-date")
	public void setInterestEarnedYearToDate(Object interestEarnedYearToDate) {
		this.interestEarnedYearToDate = interestEarnedYearToDate;
	}

	@JsonProperty("number-of-outstading-auth")
	public Object getNumberOfOutstadingAuth() {
		return numberOfOutstadingAuth;
	}

	@JsonProperty("number-of-outstading-auth")
	public void setNumberOfOutstadingAuth(Object numberOfOutstadingAuth) {
		this.numberOfOutstadingAuth = numberOfOutstadingAuth;
	}

	@JsonProperty("high-balance")
	public Object getHighBalance() {
		return highBalance;
	}

	@JsonProperty("high-balance")
	public void setHighBalance(Object highBalance) {
		this.highBalance = highBalance;
	}

	@JsonProperty("last-year-to-date-intrest-billed")
	public Object getLastYearToDateIntrestBilled() {
		return lastYearToDateIntrestBilled;
	}

	@JsonProperty("last-year-to-date-intrest-billed")
	public void setLastYearToDateIntrestBilled(Object lastYearToDateIntrestBilled) {
		this.lastYearToDateIntrestBilled = lastYearToDateIntrestBilled;
	}

	@JsonProperty("write-off-days")
	public Object getWriteOffDays() {
		return writeOffDays;
	}

	@JsonProperty("write-off-days")
	public void setWriteOffDays(Object writeOffDays) {
		this.writeOffDays = writeOffDays;
	}

	@JsonProperty("retail-interest-option")
	public Integer getRetailInterestOption() {
		return retailInterestOption;
	}

	@JsonProperty("retail-interest-option")
	public void setRetailInterestOption(Integer retailInterestOption) {
		this.retailInterestOption = retailInterestOption;
	}

	@JsonProperty("months-balance")
	public Object getMonthsBalance() {
		return monthsBalance;
	}

	@JsonProperty("months-balance")
	public void setMonthsBalance(Object monthsBalance) {
		this.monthsBalance = monthsBalance;
	}

	@JsonProperty("amount-last-advance")
	public Object getAmountLastAdvance() {
		return amountLastAdvance;
	}

	@JsonProperty("amount-last-advance")
	public void setAmountLastAdvance(Object amountLastAdvance) {
		this.amountLastAdvance = amountLastAdvance;
	}

	@JsonProperty("cash-interest-limit2")
	public Object getCashInterestLimit2() {
		return cashInterestLimit2;
	}

	@JsonProperty("cash-interest-limit2")
	public void setCashInterestLimit2(Object cashInterestLimit2) {
		this.cashInterestLimit2 = cashInterestLimit2;
	}

	@JsonProperty("cash-interest-limit3")
	public Object getCashInterestLimit3() {
		return cashInterestLimit3;
	}

	@JsonProperty("cash-interest-limit3")
	public void setCashInterestLimit3(Object cashInterestLimit3) {
		this.cashInterestLimit3 = cashInterestLimit3;
	}

	@JsonProperty("repayment-score")
	public String getRepaymentScore() {
		return repaymentScore;
	}

	@JsonProperty("repayment-score")
	public void setRepaymentScore(String repaymentScore) {
		this.repaymentScore = repaymentScore;
	}

	@JsonProperty("current-year-to-date-service-charge")
	public Object getCurrentYearToDateServiceCharge() {
		return currentYearToDateServiceCharge;
	}

	@JsonProperty("current-year-to-date-service-charge")
	public void setCurrentYearToDateServiceCharge(Object currentYearToDateServiceCharge) {
		this.currentYearToDateServiceCharge = currentYearToDateServiceCharge;
	}

	@JsonProperty("collection-reason")
	public String getCollectionReason() {
		return collectionReason;
	}

	@JsonProperty("collection-reason")
	public void setCollectionReason(String collectionReason) {
		this.collectionReason = collectionReason;
	}

	@JsonProperty("date-last-accrued")
	public Object getDateLastAccrued() {
		return dateLastAccrued;
	}

	@JsonProperty("date-last-accrued")
	public void setDateLastAccrued(Object dateLastAccrued) {
		this.dateLastAccrued = dateLastAccrued;
	}

	@JsonProperty("waive-service-charge")
	public Integer getWaiveServiceCharge() {
		return waiveServiceCharge;
	}

	@JsonProperty("waive-service-charge")
	public void setWaiveServiceCharge(Integer waiveServiceCharge) {
		this.waiveServiceCharge = waiveServiceCharge;
	}

	@JsonProperty("account-close-date")
	public Object getAccountCloseDate() {
		return accountCloseDate;
	}

	@JsonProperty("account-close-date")
	public void setAccountCloseDate(Object accountCloseDate) {
		this.accountCloseDate = accountCloseDate;
	}

	@JsonProperty("accrued-thru-date")
	public Object getAccruedThruDate() {
		return accruedThruDate;
	}

	@JsonProperty("accrued-thru-date")
	public void setAccruedThruDate(Object accruedThruDate) {
		this.accruedThruDate = accruedThruDate;
	}

	@JsonProperty("date-last-active")
	public Object getDateLastActive() {
		return dateLastActive;
	}

	@JsonProperty("date-last-active")
	public void setDateLastActive(Object dateLastActive) {
		this.dateLastActive = dateLastActive;
	}

	@JsonProperty("mobile-payment-date")
	public Object getMobilePaymentDate() {
		return mobilePaymentDate;
	}

	@JsonProperty("mobile-payment-date")
	public void setMobilePaymentDate(Object mobilePaymentDate) {
		this.mobilePaymentDate = mobilePaymentDate;
	}

	@JsonProperty("months-purchase-count")
	public Object getMonthsPurchaseCount() {
		return monthsPurchaseCount;
	}

	@JsonProperty("months-purchase-count")
	public void setMonthsPurchaseCount(Object monthsPurchaseCount) {
		this.monthsPurchaseCount = monthsPurchaseCount;
	}

	@JsonProperty("retail-accrued-interest")
	public Object getRetailAccruedInterest() {
		return retailAccruedInterest;
	}

	@JsonProperty("retail-accrued-interest")
	public void setRetailAccruedInterest(Object retailAccruedInterest) {
		this.retailAccruedInterest = retailAccruedInterest;
	}

	@JsonProperty("days150-delinquency")
	public Object getDays150Delinquency() {
		return days150Delinquency;
	}

	@JsonProperty("days150-delinquency")
	public void setDays150Delinquency(Object days150Delinquency) {
		this.days150Delinquency = days150Delinquency;
	}

	@JsonProperty("retail-last-year-to-date-interest-paid")
	public Object getRetailLastYearToDateInterestPaid() {
		return retailLastYearToDateInterestPaid;
	}

	@JsonProperty("retail-last-year-to-date-interest-paid")
	public void setRetailLastYearToDateInterestPaid(Object retailLastYearToDateInterestPaid) {
		this.retailLastYearToDateInterestPaid = retailLastYearToDateInterestPaid;
	}

	@JsonProperty("limit-changed-score")
	public String getLimitChangedScore() {
		return limitChangedScore;
	}

	@JsonProperty("limit-changed-score")
	public void setLimitChangedScore(String limitChangedScore) {
		this.limitChangedScore = limitChangedScore;
	}

	@JsonProperty("date-last-maintained")
	public Object getDateLastMaintained() {
		return dateLastMaintained;
	}

	@JsonProperty("date-last-maintained")
	public void setDateLastMaintained(Object dateLastMaintained) {
		this.dateLastMaintained = dateLastMaintained;
	}

	@JsonProperty("cash-amount-credit")
	public Object getCashAmountCredit() {
		return cashAmountCredit;
	}

	@JsonProperty("cash-amount-credit")
	public void setCashAmountCredit(Object cashAmountCredit) {
		this.cashAmountCredit = cashAmountCredit;
	}

	@JsonProperty("last-payment-date")
	public Object getLastPaymentDate() {
		return lastPaymentDate;
	}

	@JsonProperty("last-payment-date")
	public void setLastPaymentDate(Object lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	@JsonProperty("tax-fiscal-year-to-date")
	public Object getTaxFiscalYearToDate() {
		return taxFiscalYearToDate;
	}

	@JsonProperty("tax-fiscal-year-to-date")
	public void setTaxFiscalYearToDate(Object taxFiscalYearToDate) {
		this.taxFiscalYearToDate = taxFiscalYearToDate;
	}

	@JsonProperty("skip-payment-date")
	public Object getSkipPaymentDate() {
		return skipPaymentDate;
	}

	@JsonProperty("skip-payment-date")
	public void setSkipPaymentDate(Object skipPaymentDate) {
		this.skipPaymentDate = skipPaymentDate;
	}

	@JsonProperty("status-change-flag")
	public Integer getStatusChangeFlag() {
		return statusChangeFlag;
	}

	@JsonProperty("status-change-flag")
	public void setStatusChangeFlag(Integer statusChangeFlag) {
		this.statusChangeFlag = statusChangeFlag;
	}

	@JsonProperty("number-of-insurance-policies")
	public Integer getNumberOfInsurancePolicies() {
		return numberOfInsurancePolicies;
	}

	@JsonProperty("number-of-insurance-policies")
	public void setNumberOfInsurancePolicies(Integer numberOfInsurancePolicies) {
		this.numberOfInsurancePolicies = numberOfInsurancePolicies;
	}

	@JsonProperty("payment-bucket")
	public List<Object> getPaymentBucket() {
		return paymentBucket;
	}

	@JsonProperty("payment-bucket")
	public void setPaymentBucket(List<Object> paymentBucket) {
		this.paymentBucket = paymentBucket;
	}

	@JsonProperty("cycle-due")
	public Integer getCycleDue() {
		return cycleDue;
	}

	@JsonProperty("cycle-due")
	public void setCycleDue(Integer cycleDue) {
		this.cycleDue = cycleDue;
	}

	@JsonProperty("cash-interest-limit1")
	public Object getCashInterestLimit1() {
		return cashInterestLimit1;
	}

	@JsonProperty("cash-interest-limit1")
	public void setCashInterestLimit1(Object cashInterestLimit1) {
		this.cashInterestLimit1 = cashInterestLimit1;
	}

	@JsonProperty("retail-interest-bill-not-paid-last-statement")
	public Object getRetailInterestBillNotPaidLastStatement() {
		return retailInterestBillNotPaidLastStatement;
	}

	@JsonProperty("retail-interest-bill-not-paid-last-statement")
	public void setRetailInterestBillNotPaidLastStatement(Object retailInterestBillNotPaidLastStatement) {
		this.retailInterestBillNotPaidLastStatement = retailInterestBillNotPaidLastStatement;
	}

	@JsonProperty("retail-negative-provisional-balance")
	public Object getRetailNegativeProvisionalBalance() {
		return retailNegativeProvisionalBalance;
	}

	@JsonProperty("retail-negative-provisional-balance")
	public void setRetailNegativeProvisionalBalance(Object retailNegativeProvisionalBalance) {
		this.retailNegativeProvisionalBalance = retailNegativeProvisionalBalance;
	}

	@JsonProperty("retail-interest-adjustment1")
	public Integer getRetailInterestAdjustment1() {
		return retailInterestAdjustment1;
	}

	@JsonProperty("retail-interest-adjustment1")
	public void setRetailInterestAdjustment1(Integer retailInterestAdjustment1) {
		this.retailInterestAdjustment1 = retailInterestAdjustment1;
	}

	@JsonProperty("retail-interest-rate3")
	public Object getRetailInterestRate3() {
		return retailInterestRate3;
	}

	@JsonProperty("retail-interest-rate3")
	public void setRetailInterestRate3(Object retailInterestRate3) {
		this.retailInterestRate3 = retailInterestRate3;
	}

	@JsonProperty("retail-interest-rate2")
	public Object getRetailInterestRate2() {
		return retailInterestRate2;
	}

	@JsonProperty("retail-interest-rate2")
	public void setRetailInterestRate2(Object retailInterestRate2) {
		this.retailInterestRate2 = retailInterestRate2;
	}

	@JsonProperty("cash-credit-adjust-bill-not-paid")
	public Object getCashCreditAdjustBillNotPaid() {
		return cashCreditAdjustBillNotPaid;
	}

	@JsonProperty("cash-credit-adjust-bill-not-paid")
	public void setCashCreditAdjustBillNotPaid(Object cashCreditAdjustBillNotPaid) {
		this.cashCreditAdjustBillNotPaid = cashCreditAdjustBillNotPaid;
	}

	@JsonProperty("date-last-advance")
	public Object getDateLastAdvance() {
		return dateLastAdvance;
	}

	@JsonProperty("date-last-advance")
	public void setDateLastAdvance(Object dateLastAdvance) {
		this.dateLastAdvance = dateLastAdvance;
	}

	@JsonProperty("retail-interest-rate1")
	public Object getRetailInterestRate1() {
		return retailInterestRate1;
	}

	@JsonProperty("retail-interest-rate1")
	public void setRetailInterestRate1(Object retailInterestRate1) {
		this.retailInterestRate1 = retailInterestRate1;
	}

	@JsonProperty("annual-fee")
	public Object getAnnualFee() {
		return annualFee;
	}

	@JsonProperty("annual-fee")
	public void setAnnualFee(Object annualFee) {
		this.annualFee = annualFee;
	}

	@JsonProperty("current-balance")
	public Object getCurrentBalance() {
		return currentBalance;
	}

	@JsonProperty("current-balance")
	public void setCurrentBalance(Object currentBalance) {
		this.currentBalance = currentBalance;
	}

	@JsonProperty("wallet-available-balance")
	public Object getWalletAvailableBalance() {
		return walletAvailableBalance;
	}

	@JsonProperty("wallet-available-balance")
	public void setWalletAvailableBalance(Object walletAvailableBalance) {
		this.walletAvailableBalance = walletAvailableBalance;
	}

	@JsonProperty("retail-interest-adjustment3")
	public Object getRetailInterestAdjustment3() {
		return retailInterestAdjustment3;
	}

	@JsonProperty("retail-interest-adjustment3")
	public void setRetailInterestAdjustment3(Object retailInterestAdjustment3) {
		this.retailInterestAdjustment3 = retailInterestAdjustment3;
	}

	@JsonProperty("unbilled-debit-amount")
	public Object getUnbilledDebitAmount() {
		return unbilledDebitAmount;
	}

	@JsonProperty("unbilled-debit-amount")
	public void setUnbilledDebitAmount(Object unbilledDebitAmount) {
		this.unbilledDebitAmount = unbilledDebitAmount;
	}

	@JsonProperty("retail-interest-adjustment2")
	public Integer getRetailInterestAdjustment2() {
		return retailInterestAdjustment2;
	}

	@JsonProperty("retail-interest-adjustment2")
	public void setRetailInterestAdjustment2(Integer retailInterestAdjustment2) {
		this.retailInterestAdjustment2 = retailInterestAdjustment2;
	}

	@JsonProperty("cash-interest-flag")
	public Integer getCashInterestFlag() {
		return cashInterestFlag;
	}

	@JsonProperty("cash-interest-flag")
	public void setCashInterestFlag(Integer cashInterestFlag) {
		this.cashInterestFlag = cashInterestFlag;
	}

	@JsonProperty("aggregate-last-year-to-date-days")
	public Object getAggregateLastYearToDateDays() {
		return aggregateLastYearToDateDays;
	}

	@JsonProperty("aggregate-last-year-to-date-days")
	public void setAggregateLastYearToDateDays(Object aggregateLastYearToDateDays) {
		this.aggregateLastYearToDateDays = aggregateLastYearToDateDays;
	}

	@JsonProperty("current-due")
	public Object getCurrentDue() {
		return currentDue;
	}

	@JsonProperty("current-due")
	public void setCurrentDue(Object currentDue) {
		this.currentDue = currentDue;
	}

	@JsonProperty("date-next-statement")
	public Object getDateNextStatement() {
		return dateNextStatement;
	}

	@JsonProperty("date-next-statement")
	public void setDateNextStatement(Object dateNextStatement) {
		this.dateNextStatement = dateNextStatement;
	}

	@JsonProperty("statement-flag")
	public Integer getStatementFlag() {
		return statementFlag;
	}

	@JsonProperty("statement-flag")
	public void setStatementFlag(Integer statementFlag) {
		this.statementFlag = statementFlag;
	}

	@JsonProperty("last-authorization-date")
	public Object getLastAuthorizationDate() {
		return lastAuthorizationDate;
	}

	@JsonProperty("last-authorization-date")
	public void setLastAuthorizationDate(Object lastAuthorizationDate) {
		this.lastAuthorizationDate = lastAuthorizationDate;
	}

	@JsonProperty("last-date-payment-due")
	public Object getLastDatePaymentDue() {
		return lastDatePaymentDue;
	}

	@JsonProperty("last-date-payment-due")
	public void setLastDatePaymentDue(Object lastDatePaymentDue) {
		this.lastDatePaymentDue = lastDatePaymentDue;
	}

	@JsonProperty("waive-late-notice")
	public Integer getWaiveLateNotice() {
		return waiveLateNotice;
	}

	@JsonProperty("waive-late-notice")
	public void setWaiveLateNotice(Integer waiveLateNotice) {
		this.waiveLateNotice = waiveLateNotice;
	}

	@JsonProperty("retail-standard-interest")
	public Object getRetailStandardInterest() {
		return retailStandardInterest;
	}

	@JsonProperty("retail-standard-interest")
	public void setRetailStandardInterest(Object retailStandardInterest) {
		this.retailStandardInterest = retailStandardInterest;
	}

	@JsonProperty("cash-accrued-interest")
	public Object getCashAccruedInterest() {
		return cashAccruedInterest;
	}

	@JsonProperty("cash-accrued-interest")
	public void setCashAccruedInterest(Object cashAccruedInterest) {
		this.cashAccruedInterest = cashAccruedInterest;
	}

	@JsonProperty("number-returned-checks")
	public Object getNumberReturnedChecks() {
		return numberReturnedChecks;
	}

	@JsonProperty("number-returned-checks")
	public void setNumberReturnedChecks(Object numberReturnedChecks) {
		this.numberReturnedChecks = numberReturnedChecks;
	}

	@JsonProperty("first-late-notice-date")
	public Object getFirstLateNoticeDate() {
		return firstLateNoticeDate;
	}

	@JsonProperty("first-late-notice-date")
	public void setFirstLateNoticeDate(Object firstLateNoticeDate) {
		this.firstLateNoticeDate = firstLateNoticeDate;
	}

	@JsonProperty("payment-table-high-balance")
	public Object getPaymentTableHighBalance() {
		return paymentTableHighBalance;
	}

	@JsonProperty("payment-table-high-balance")
	public void setPaymentTableHighBalance(Object paymentTableHighBalance) {
		this.paymentTableHighBalance = paymentTableHighBalance;
	}

	@JsonProperty("date-credit-balance-range")
	public Object getDateCreditBalanceRange() {
		return dateCreditBalanceRange;
	}

	@JsonProperty("date-credit-balance-range")
	public void setDateCreditBalanceRange(Object dateCreditBalanceRange) {
		this.dateCreditBalanceRange = dateCreditBalanceRange;
	}

	@JsonProperty("billing-cycle")
	public Integer getBillingCycle() {
		return billingCycle;
	}

	@JsonProperty("billing-cycle")
	public void setBillingCycle(Integer billingCycle) {
		this.billingCycle = billingCycle;
	}

	@JsonProperty("amount-auth-today")
	public Object getAmountAuthToday() {
		return amountAuthToday;
	}

	@JsonProperty("amount-auth-today")
	public void setAmountAuthToday(Object amountAuthToday) {
		this.amountAuthToday = amountAuthToday;
	}

	@JsonProperty("source-code")
	public String getSourceCode() {
		return sourceCode;
	}

	@JsonProperty("source-code")
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@JsonProperty("negative-anticipated-provisional-interest-amount")
	public String getNegativeAnticipatedProvisionalInterestAmount() {
		return negativeAnticipatedProvisionalInterestAmount;
	}

	@JsonProperty("negative-anticipated-provisional-interest-amount")
	public void setNegativeAnticipatedProvisionalInterestAmount(String negativeAnticipatedProvisionalInterestAmount) {
		this.negativeAnticipatedProvisionalInterestAmount = negativeAnticipatedProvisionalInterestAmount;
	}

	@JsonProperty("cash-interest-change-flag")
	public Integer getCashInterestChangeFlag() {
		return cashInterestChangeFlag;
	}

	@JsonProperty("cash-interest-change-flag")
	public void setCashInterestChangeFlag(Integer cashInterestChangeFlag) {
		this.cashInterestChangeFlag = cashInterestChangeFlag;
	}

	@JsonProperty("aggregate-last-year-to-date-balance")
	public Object getAggregateLastYearToDateBalance() {
		return aggregateLastYearToDateBalance;
	}

	@JsonProperty("aggregate-last-year-to-date-balance")
	public void setAggregateLastYearToDateBalance(Object aggregateLastYearToDateBalance) {
		this.aggregateLastYearToDateBalance = aggregateLastYearToDateBalance;
	}

	@JsonProperty("user-code3")
	public String getUserCode3() {
		return userCode3;
	}

	@JsonProperty("user-code3")
	public void setUserCode3(String userCode3) {
		this.userCode3 = userCode3;
	}

	@JsonProperty("cash-advance-fee")
	public Object getCashAdvanceFee() {
		return cashAdvanceFee;
	}

	@JsonProperty("cash-advance-fee")
	public void setCashAdvanceFee(Object cashAdvanceFee) {
		this.cashAdvanceFee = cashAdvanceFee;
	}

	@JsonProperty("user-code4")
	public String getUserCode4() {
		return userCode4;
	}

	@JsonProperty("user-code4")
	public void setUserCode4(String userCode4) {
		this.userCode4 = userCode4;
	}

	@JsonProperty("duplicate-statement-number")
	public Object getDuplicateStatementNumber() {
		return duplicateStatementNumber;
	}

	@JsonProperty("duplicate-statement-number")
	public void setDuplicateStatementNumber(Object duplicateStatementNumber) {
		this.duplicateStatementNumber = duplicateStatementNumber;
	}

	@JsonProperty("last-transaction-date")
	public Object getLastTransactionDate() {
		return lastTransactionDate;
	}

	@JsonProperty("last-transaction-date")
	public void setLastTransactionDate(Object lastTransactionDate) {
		this.lastTransactionDate = lastTransactionDate;
	}

	@JsonProperty("cash-disputed-balance")
	public Object getCashDisputedBalance() {
		return cashDisputedBalance;
	}

	@JsonProperty("cash-disputed-balance")
	public void setCashDisputedBalance(Object cashDisputedBalance) {
		this.cashDisputedBalance = cashDisputedBalance;
	}

	@JsonProperty("user-code2")
	public String getUserCode2() {
		return userCode2;
	}

	@JsonProperty("user-code2")
	public void setUserCode2(String userCode2) {
		this.userCode2 = userCode2;
	}

	@JsonProperty("cash-interest-bill-not-paid")
	public Object getCashInterestBillNotPaid() {
		return cashInterestBillNotPaid;
	}

	@JsonProperty("cash-interest-bill-not-paid")
	public void setCashInterestBillNotPaid(Object cashInterestBillNotPaid) {
		this.cashInterestBillNotPaid = cashInterestBillNotPaid;
	}

	@JsonProperty("credit-balance-refund-days")
	public Object getCreditBalanceRefundDays() {
		return creditBalanceRefundDays;
	}

	@JsonProperty("credit-balance-refund-days")
	public void setCreditBalanceRefundDays(Object creditBalanceRefundDays) {
		this.creditBalanceRefundDays = creditBalanceRefundDays;
	}

	@JsonProperty("charge-off-status-flag")
	public Integer getChargeOffStatusFlag() {
		return chargeOffStatusFlag;
	}

	@JsonProperty("charge-off-status-flag")
	public void setChargeOffStatusFlag(Integer chargeOffStatusFlag) {
		this.chargeOffStatusFlag = chargeOffStatusFlag;
	}

	@JsonProperty("days180-delinquency")
	public Object getDays180Delinquency() {
		return days180Delinquency;
	}

	@JsonProperty("days180-delinquency")
	public void setDays180Delinquency(Object days180Delinquency) {
		this.days180Delinquency = days180Delinquency;
	}

	@JsonProperty("account-number")
	public String getAccountNumber() {
		return accountNumber;
	}

	@JsonProperty("account-number")
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	@JsonProperty("retail-current-month-balance")
	public Object getRetailCurrentMonthBalance() {
		return retailCurrentMonthBalance;
	}

	@JsonProperty("retail-current-month-balance")
	public void setRetailCurrentMonthBalance(Object retailCurrentMonthBalance) {
		this.retailCurrentMonthBalance = retailCurrentMonthBalance;
	}

	@JsonProperty("account-cash-limit")
	public Object getAccountCashLimit() {
		return accountCashLimit;
	}

	@JsonProperty("account-cash-limit")
	public void setAccountCashLimit(Object accountCashLimit) {
		this.accountCashLimit = accountCashLimit;
	}

	@JsonProperty("waive-joining-fee")
	public Integer getWaiveJoiningFee() {
		return waiveJoiningFee;
	}

	@JsonProperty("waive-joining-fee")
	public void setWaiveJoiningFee(Integer waiveJoiningFee) {
		this.waiveJoiningFee = waiveJoiningFee;
	}

	@JsonProperty("online-outstanding-current-balance")
	public Object getOnlineOutstandingCurrentBalance() {
		return onlineOutstandingCurrentBalance;
	}

	@JsonProperty("online-outstanding-current-balance")
	public void setOnlineOutstandingCurrentBalance(Object onlineOutstandingCurrentBalance) {
		this.onlineOutstandingCurrentBalance = onlineOutstandingCurrentBalance;
	}

	@JsonProperty("interest-earned-prior-year-to-date")
	public Object getInterestEarnedPriorYearToDate() {
		return interestEarnedPriorYearToDate;
	}

	@JsonProperty("interest-earned-prior-year-to-date")
	public void setInterestEarnedPriorYearToDate(Object interestEarnedPriorYearToDate) {
		this.interestEarnedPriorYearToDate = interestEarnedPriorYearToDate;
	}

	@JsonProperty("retail-credit-adjust-bill-not-paid")
	public Object getRetailCreditAdjustBillNotPaid() {
		return retailCreditAdjustBillNotPaid;
	}

	@JsonProperty("retail-credit-adjust-bill-not-paid")
	public void setRetailCreditAdjustBillNotPaid(Object retailCreditAdjustBillNotPaid) {
		this.retailCreditAdjustBillNotPaid = retailCreditAdjustBillNotPaid;
	}

	@JsonProperty("cash-amount-debit")
	public Object getCashAmountDebit() {
		return cashAmountDebit;
	}

	@JsonProperty("cash-amount-debit")
	public void setCashAmountDebit(Object cashAmountDebit) {
		this.cashAmountDebit = cashAmountDebit;
	}

	@JsonProperty("payment-pending")
	public String getPaymentPending() {
		return paymentPending;
	}

	@JsonProperty("payment-pending")
	public void setPaymentPending(String paymentPending) {
		this.paymentPending = paymentPending;
	}

	@JsonProperty("retail-number-debit")
	public Object getRetailNumberDebit() {
		return retailNumberDebit;
	}

	@JsonProperty("retail-number-debit")
	public void setRetailNumberDebit(Object retailNumberDebit) {
		this.retailNumberDebit = retailNumberDebit;
	}

	@JsonProperty("online-total-retail-instalment-current")
	public Object getOnlineTotalRetailInstalmentCurrent() {
		return onlineTotalRetailInstalmentCurrent;
	}

	@JsonProperty("online-total-retail-instalment-current")
	public void setOnlineTotalRetailInstalmentCurrent(Object onlineTotalRetailInstalmentCurrent) {
		this.onlineTotalRetailInstalmentCurrent = onlineTotalRetailInstalmentCurrent;
	}

	@JsonProperty("consolidated-statement-status")
	public String getConsolidatedStatementStatus() {
		return consolidatedStatementStatus;
	}

	@JsonProperty("consolidated-statement-status")
	public void setConsolidatedStatementStatus(String consolidatedStatementStatus) {
		this.consolidatedStatementStatus = consolidatedStatementStatus;
	}

	@JsonProperty("card-fee-date")
	public Object getCardFeeDate() {
		return cardFeeDate;
	}

	@JsonProperty("card-fee-date")
	public void setCardFeeDate(Object cardFeeDate) {
		this.cardFeeDate = cardFeeDate;
	}

	@JsonProperty("date-last-rate-change")
	public Object getDateLastRateChange() {
		return dateLastRateChange;
	}

	@JsonProperty("date-last-rate-change")
	public void setDateLastRateChange(Object dateLastRateChange) {
		this.dateLastRateChange = dateLastRateChange;
	}

	@JsonProperty("arrears-level")
	public Integer getArrearsLevel() {
		return arrearsLevel;
	}

	@JsonProperty("arrears-level")
	public void setArrearsLevel(Integer arrearsLevel) {
		this.arrearsLevel = arrearsLevel;
	}

	@JsonProperty("last-min-payment")
	public Object getLastMinPayment() {
		return lastMinPayment;
	}

	@JsonProperty("last-min-payment")
	public void setLastMinPayment(Object lastMinPayment) {
		this.lastMinPayment = lastMinPayment;
	}

	@JsonProperty("number-of-auth-today")
	public Object getNumberOfAuthToday() {
		return numberOfAuthToday;
	}

	@JsonProperty("number-of-auth-today")
	public void setNumberOfAuthToday(Object numberOfAuthToday) {
		this.numberOfAuthToday = numberOfAuthToday;
	}

	@JsonProperty("waive-annual-fee")
	public Integer getWaiveAnnualFee() {
		return waiveAnnualFee;
	}

	@JsonProperty("waive-annual-fee")
	public void setWaiveAnnualFee(Integer waiveAnnualFee) {
		this.waiveAnnualFee = waiveAnnualFee;
	}

	@JsonProperty("cash-advance-outstanding-authorization")
	public Object getCashAdvanceOutstandingAuthorization() {
		return cashAdvanceOutstandingAuthorization;
	}

	@JsonProperty("cash-advance-outstanding-authorization")
	public void setCashAdvanceOutstandingAuthorization(Object cashAdvanceOutstandingAuthorization) {
		this.cashAdvanceOutstandingAuthorization = cashAdvanceOutstandingAuthorization;
	}

	@JsonProperty("date-last-retured-check")
	public Object getDateLastReturedCheck() {
		return dateLastReturedCheck;
	}

	@JsonProperty("date-last-retured-check")
	public void setDateLastReturedCheck(Object dateLastReturedCheck) {
		this.dateLastReturedCheck = dateLastReturedCheck;
	}

	@JsonProperty("payment-skip-flag")
	public Integer getPaymentSkipFlag() {
		return paymentSkipFlag;
	}

	@JsonProperty("payment-skip-flag")
	public void setPaymentSkipFlag(Integer paymentSkipFlag) {
		this.paymentSkipFlag = paymentSkipFlag;
	}

	@JsonProperty("date-into-collection")
	public Object getDateIntoCollection() {
		return dateIntoCollection;
	}

	@JsonProperty("date-into-collection")
	public void setDateIntoCollection(Object dateIntoCollection) {
		this.dateIntoCollection = dateIntoCollection;
	}

	@JsonProperty("retail-amount-credit")
	public Object getRetailAmountCredit() {
		return retailAmountCredit;
	}

	@JsonProperty("retail-amount-credit")
	public void setRetailAmountCredit(Object retailAmountCredit) {
		this.retailAmountCredit = retailAmountCredit;
	}

	@JsonProperty("last-payment-change")
	public Object getLastPaymentChange() {
		return lastPaymentChange;
	}

	@JsonProperty("last-payment-change")
	public void setLastPaymentChange(Object lastPaymentChange) {
		this.lastPaymentChange = lastPaymentChange;
	}

	@JsonProperty("aggregate-retail-days")
	public Integer getAggregateRetailDays() {
		return aggregateRetailDays;
	}

	@JsonProperty("aggregate-retail-days")
	public void setAggregateRetailDays(Integer aggregateRetailDays) {
		this.aggregateRetailDays = aggregateRetailDays;
	}

	@JsonProperty("aggregate-year-to-date-balance")
	public Object getAggregateYearToDateBalance() {
		return aggregateYearToDateBalance;
	}

	@JsonProperty("aggregate-year-to-date-balance")
	public void setAggregateYearToDateBalance(Object aggregateYearToDateBalance) {
		this.aggregateYearToDateBalance = aggregateYearToDateBalance;
	}

	@JsonProperty("current-revolver-flag")
	public String getCurrentRevolverFlag() {
		return currentRevolverFlag;
	}

	@JsonProperty("current-revolver-flag")
	public void setCurrentRevolverFlag(String currentRevolverFlag) {
		this.currentRevolverFlag = currentRevolverFlag;
	}

	@JsonProperty("pending-product-offering-table-exp-date")
	public Object getPendingProductOfferingTableExpDate() {
		return pendingProductOfferingTableExpDate;
	}

	@JsonProperty("pending-product-offering-table-exp-date")
	public void setPendingProductOfferingTableExpDate(Object pendingProductOfferingTableExpDate) {
		this.pendingProductOfferingTableExpDate = pendingProductOfferingTableExpDate;
	}

	@JsonProperty("thirteen-month-min-payment")
	public Object getThirteenMonthMinPayment() {
		return thirteenMonthMinPayment;
	}

	@JsonProperty("thirteen-month-min-payment")
	public void setThirteenMonthMinPayment(Object thirteenMonthMinPayment) {
		this.thirteenMonthMinPayment = thirteenMonthMinPayment;
	}

	@JsonProperty("product-offering-table-exp-date")
	public Object getProductOfferingTableExpDate() {
		return productOfferingTableExpDate;
	}

	@JsonProperty("product-offering-table-exp-date")
	public void setProductOfferingTableExpDate(Object productOfferingTableExpDate) {
		this.productOfferingTableExpDate = productOfferingTableExpDate;
	}

	@JsonProperty("retail-negative-provisional-interest")
	public Object getRetailNegativeProvisionalInterest() {
		return retailNegativeProvisionalInterest;
	}

	@JsonProperty("retail-negative-provisional-interest")
	public void setRetailNegativeProvisionalInterest(Object retailNegativeProvisionalInterest) {
		this.retailNegativeProvisionalInterest = retailNegativeProvisionalInterest;
	}

	@JsonProperty("date-prior-delinquency")
	public Object getDatePriorDelinquency() {
		return datePriorDelinquency;
	}

	@JsonProperty("date-prior-delinquency")
	public void setDatePriorDelinquency(Object datePriorDelinquency) {
		this.datePriorDelinquency = datePriorDelinquency;
	}

	@JsonProperty("cash-interest-adjustment3")
	public Object getCashInterestAdjustment3() {
		return cashInterestAdjustment3;
	}

	@JsonProperty("cash-interest-adjustment3")
	public void setCashInterestAdjustment3(Object cashInterestAdjustment3) {
		this.cashInterestAdjustment3 = cashInterestAdjustment3;
	}

	@JsonProperty("bank-charges-fee")
	public Object getBankChargesFee() {
		return bankChargesFee;
	}

	@JsonProperty("bank-charges-fee")
	public void setBankChargesFee(Object bankChargesFee) {
		this.bankChargesFee = bankChargesFee;
	}

	@JsonProperty("cash-interest-adjustment2")
	public Integer getCashInterestAdjustment2() {
		return cashInterestAdjustment2;
	}

	@JsonProperty("cash-interest-adjustment2")
	public void setCashInterestAdjustment2(Integer cashInterestAdjustment2) {
		this.cashInterestAdjustment2 = cashInterestAdjustment2;
	}

	@JsonProperty("second-late-notice-ctr")
	public Object getSecondLateNoticeCtr() {
		return secondLateNoticeCtr;
	}

	@JsonProperty("second-late-notice-ctr")
	public void setSecondLateNoticeCtr(Object secondLateNoticeCtr) {
		this.secondLateNoticeCtr = secondLateNoticeCtr;
	}

	@JsonProperty("cash-interest-adjustment1")
	public Integer getCashInterestAdjustment1() {
		return cashInterestAdjustment1;
	}

	@JsonProperty("cash-interest-adjustment1")
	public void setCashInterestAdjustment1(Integer cashInterestAdjustment1) {
		this.cashInterestAdjustment1 = cashInterestAdjustment1;
	}

	@JsonProperty("online-total-avail-credit")
	public Object getOnlineTotalAvailCredit() {
		return onlineTotalAvailCredit;
	}

	@JsonProperty("online-total-avail-credit")
	public void setOnlineTotalAvailCredit(Object onlineTotalAvailCredit) {
		this.onlineTotalAvailCredit = onlineTotalAvailCredit;
	}

	@JsonProperty("date-last-cash-authorized")
	public Object getDateLastCashAuthorized() {
		return dateLastCashAuthorized;
	}

	@JsonProperty("date-last-cash-authorized")
	public void setDateLastCashAuthorized(Object dateLastCashAuthorized) {
		this.dateLastCashAuthorized = dateLastCashAuthorized;
	}

	@JsonProperty("duality-flag")
	public String getDualityFlag() {
		return dualityFlag;
	}

	@JsonProperty("duality-flag")
	public void setDualityFlag(String dualityFlag) {
		this.dualityFlag = dualityFlag;
	}

	@JsonProperty("savings-account")
	public Object getSavingsAccount() {
		return savingsAccount;
	}

	@JsonProperty("savings-account")
	public void setSavingsAccount(Object savingsAccount) {
		this.savingsAccount = savingsAccount;
	}

	@JsonProperty("cash-amount-payment-reversal")
	public Object getCashAmountPaymentReversal() {
		return cashAmountPaymentReversal;
	}

	@JsonProperty("cash-amount-payment-reversal")
	public void setCashAmountPaymentReversal(Object cashAmountPaymentReversal) {
		this.cashAmountPaymentReversal = cashAmountPaymentReversal;
	}

	@JsonProperty("ownership-flag")
	public String getOwnershipFlag() {
		return ownershipFlag;
	}

	@JsonProperty("ownership-flag")
	public void setOwnershipFlag(String ownershipFlag) {
		this.ownershipFlag = ownershipFlag;
	}

	@JsonProperty("interest-earned-accrued")
	public Object getInterestEarnedAccrued() {
		return interestEarnedAccrued;
	}

	@JsonProperty("interest-earned-accrued")
	public void setInterestEarnedAccrued(Object interestEarnedAccrued) {
		this.interestEarnedAccrued = interestEarnedAccrued;
	}

	@JsonProperty("delinquency-counter")
	public Object getDelinquencyCounter() {
		return delinquencyCounter;
	}

	@JsonProperty("delinquency-counter")
	public void setDelinquencyCounter(Object delinquencyCounter) {
		this.delinquencyCounter = delinquencyCounter;
	}

	@JsonProperty("last-payment-amount")
	public Object getLastPaymentAmount() {
		return lastPaymentAmount;
	}

	@JsonProperty("last-payment-amount")
	public void setLastPaymentAmount(Object lastPaymentAmount) {
		this.lastPaymentAmount = lastPaymentAmount;
	}

	@JsonProperty("high-balance-date")
	public Object getHighBalanceDate() {
		return highBalanceDate;
	}

	@JsonProperty("high-balance-date")
	public void setHighBalanceDate(Object highBalanceDate) {
		this.highBalanceDate = highBalanceDate;
	}

	@JsonProperty("previous-revolver-flag")
	public String getPreviousRevolverFlag() {
		return previousRevolverFlag;
	}

	@JsonProperty("previous-revolver-flag")
	public void setPreviousRevolverFlag(String previousRevolverFlag) {
		this.previousRevolverFlag = previousRevolverFlag;
	}

	@JsonProperty("aggregate-cash-days")
	public Integer getAggregateCashDays() {
		return aggregateCashDays;
	}

	@JsonProperty("aggregate-cash-days")
	public void setAggregateCashDays(Integer aggregateCashDays) {
		this.aggregateCashDays = aggregateCashDays;
	}

	@JsonProperty("cash-interest-bill-not-paid-last-statement")
	public Object getCashInterestBillNotPaidLastStatement() {
		return cashInterestBillNotPaidLastStatement;
	}

	@JsonProperty("cash-interest-bill-not-paid-last-statement")
	public void setCashInterestBillNotPaidLastStatement(Object cashInterestBillNotPaidLastStatement) {
		this.cashInterestBillNotPaidLastStatement = cashInterestBillNotPaidLastStatement;
	}

	@JsonProperty("online-cycle-to-date-payment-amount")
	public Object getOnlineCycleToDatePaymentAmount() {
		return onlineCycleToDatePaymentAmount;
	}

	@JsonProperty("online-cycle-to-date-payment-amount")
	public void setOnlineCycleToDatePaymentAmount(Object onlineCycleToDatePaymentAmount) {
		this.onlineCycleToDatePaymentAmount = onlineCycleToDatePaymentAmount;
	}

	@JsonProperty("gst-date-maint")
	public Object getGstDateMaint() {
		return gstDateMaint;
	}

	@JsonProperty("gst-date-maint")
	public void setGstDateMaint(Object gstDateMaint) {
		this.gstDateMaint = gstDateMaint;
	}

	@JsonProperty("product-description")
	public String getProductDescription() {
		return productDescription;
	}

	@JsonProperty("product-description")
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	@JsonProperty("user-account")
	public Object getUserAccount() {
		return userAccount;
	}

	@JsonProperty("user-account")
	public void setUserAccount(Object userAccount) {
		this.userAccount = userAccount;
	}

	@JsonProperty("waive-late-charge")
	public Integer getWaiveLateCharge() {
		return waiveLateCharge;
	}

	@JsonProperty("waive-late-charge")
	public void setWaiveLateCharge(Integer waiveLateCharge) {
		this.waiveLateCharge = waiveLateCharge;
	}

	@JsonProperty("retail-balance")
	public Object getRetailBalance() {
		return retailBalance;
	}

	@JsonProperty("retail-balance")
	public void setRetailBalance(Object retailBalance) {
		this.retailBalance = retailBalance;
	}

	@JsonProperty("user-date2")
	public Object getUserDate2() {
		return userDate2;
	}

	@JsonProperty("user-date2")
	public void setUserDate2(Object userDate2) {
		this.userDate2 = userDate2;
	}

	@JsonProperty("days210-delinquency")
	public Object getDays210Delinquency() {
		return days210Delinquency;
	}

	@JsonProperty("days210-delinquency")
	public void setDays210Delinquency(Object days210Delinquency) {
		this.days210Delinquency = days210Delinquency;
	}

	@JsonProperty("total-interest")
	public Object getTotalInterest() {
		return totalInterest;
	}

	@JsonProperty("total-interest")
	public void setTotalInterest(Object totalInterest) {
		this.totalInterest = totalInterest;
	}

	@JsonProperty("online-outstanding-total-amount-due")
	public Object getOnlineOutstandingTotalAmountDue() {
		return onlineOutstandingTotalAmountDue;
	}

	@JsonProperty("online-outstanding-total-amount-due")
	public void setOnlineOutstandingTotalAmountDue(Object onlineOutstandingTotalAmountDue) {
		this.onlineOutstandingTotalAmountDue = onlineOutstandingTotalAmountDue;
	}

	@JsonProperty("interest-chain-sw")
	public Integer getInterestChainSw() {
		return interestChainSw;
	}

	@JsonProperty("interest-chain-sw")
	public void setInterestChainSw(Integer interestChainSw) {
		this.interestChainSw = interestChainSw;
	}

	@JsonProperty("cash-year-to-date-interest-billed")
	public Object getCashYearToDateInterestBilled() {
		return cashYearToDateInterestBilled;
	}

	@JsonProperty("cash-year-to-date-interest-billed")
	public void setCashYearToDateInterestBilled(Object cashYearToDateInterestBilled) {
		this.cashYearToDateInterestBilled = cashYearToDateInterestBilled;
	}

	@JsonProperty("thirteen-month-age-cd")
	public Integer getThirteenMonthAgeCd() {
		return thirteenMonthAgeCd;
	}

	@JsonProperty("thirteen-month-age-cd")
	public void setThirteenMonthAgeCd(Integer thirteenMonthAgeCd) {
		this.thirteenMonthAgeCd = thirteenMonthAgeCd;
	}

	@JsonProperty("amount-prepaid")
	public Object getAmountPrepaid() {
		return amountPrepaid;
	}

	@JsonProperty("amount-prepaid")
	public void setAmountPrepaid(Object amountPrepaid) {
		this.amountPrepaid = amountPrepaid;
	}

	@JsonProperty("last-payment-switch")
	public String getLastPaymentSwitch() {
		return lastPaymentSwitch;
	}

	@JsonProperty("last-payment-switch")
	public void setLastPaymentSwitch(String lastPaymentSwitch) {
		this.lastPaymentSwitch = lastPaymentSwitch;
	}

	@JsonProperty("account-limit")
	public Object getAccountLimit() {
		return accountLimit;
	}

	@JsonProperty("account-limit")
	public void setAccountLimit(Object accountLimit) {
		this.accountLimit = accountLimit;
	}

	@JsonProperty("gst-tax-invoice-flag")
	public Integer getGstTaxInvoiceFlag() {
		return gstTaxInvoiceFlag;
	}

	@JsonProperty("gst-tax-invoice-flag")
	public void setGstTaxInvoiceFlag(Integer gstTaxInvoiceFlag) {
		this.gstTaxInvoiceFlag = gstTaxInvoiceFlag;
	}

	@JsonProperty("product-offering-table-hist2")
	public Integer getProductOfferingTableHist2() {
		return productOfferingTableHist2;
	}

	@JsonProperty("product-offering-table-hist2")
	public void setProductOfferingTableHist2(Integer productOfferingTableHist2) {
		this.productOfferingTableHist2 = productOfferingTableHist2;
	}

	@JsonProperty("product-offering-table-hist3")
	public Integer getProductOfferingTableHist3() {
		return productOfferingTableHist3;
	}

	@JsonProperty("product-offering-table-hist3")
	public void setProductOfferingTableHist3(Integer productOfferingTableHist3) {
		this.productOfferingTableHist3 = productOfferingTableHist3;
	}

	@JsonProperty("online-credit-balance-refund-amount")
	public Object getOnlineCreditBalanceRefundAmount() {
		return onlineCreditBalanceRefundAmount;
	}

	@JsonProperty("online-credit-balance-refund-amount")
	public void setOnlineCreditBalanceRefundAmount(Object onlineCreditBalanceRefundAmount) {
		this.onlineCreditBalanceRefundAmount = onlineCreditBalanceRefundAmount;
	}

	@JsonProperty("thirteen-month-retail")
	public Object getThirteenMonthRetail() {
		return thirteenMonthRetail;
	}

	@JsonProperty("thirteen-month-retail")
	public void setThirteenMonthRetail(Object thirteenMonthRetail) {
		this.thirteenMonthRetail = thirteenMonthRetail;
	}

	@JsonProperty("product-offering-table-hist4")
	public Integer getProductOfferingTableHist4() {
		return productOfferingTableHist4;
	}

	@JsonProperty("product-offering-table-hist4")
	public void setProductOfferingTableHist4(Integer productOfferingTableHist4) {
		this.productOfferingTableHist4 = productOfferingTableHist4;
	}

	@JsonProperty("product-offering-table-hist5")
	public Integer getProductOfferingTableHist5() {
		return productOfferingTableHist5;
	}

	@JsonProperty("product-offering-table-hist5")
	public void setProductOfferingTableHist5(Integer productOfferingTableHist5) {
		this.productOfferingTableHist5 = productOfferingTableHist5;
	}

	@JsonProperty("statement-balance")
	public Object getStatementBalance() {
		return statementBalance;
	}

	@JsonProperty("statement-balance")
	public void setStatementBalance(Object statementBalance) {
		this.statementBalance = statementBalance;
	}

	@JsonProperty("delinquencyHistories")
	public List<DelinquencyHistory> getDelinquencyHistories() {
		return delinquencyHistories;
	}

	@JsonProperty("delinquencyHistories")
	public void setDelinquencyHistories(List<DelinquencyHistory> delinquencyHistories) {
		this.delinquencyHistories = delinquencyHistories;
	}

	@JsonProperty("beginning-cycle-due")
	public Integer getBeginningCycleDue() {
		return beginningCycleDue;
	}

	@JsonProperty("beginning-cycle-due")
	public void setBeginningCycleDue(Integer beginningCycleDue) {
		this.beginningCycleDue = beginningCycleDue;
	}

	@JsonProperty("product-offering-table-hist1")
	public Integer getProductOfferingTableHist1() {
		return productOfferingTableHist1;
	}

	@JsonProperty("product-offering-table-hist1")
	public void setProductOfferingTableHist1(Integer productOfferingTableHist1) {
		this.productOfferingTableHist1 = productOfferingTableHist1;
	}

	@JsonProperty("unbilled-credit-amount")
	public Object getUnbilledCreditAmount() {
		return unbilledCreditAmount;
	}

	@JsonProperty("unbilled-credit-amount")
	public void setUnbilledCreditAmount(Object unbilledCreditAmount) {
		this.unbilledCreditAmount = unbilledCreditAmount;
	}

	@JsonProperty("statement-frequency")
	public Integer getStatementFrequency() {
		return statementFrequency;
	}

	@JsonProperty("statement-frequency")
	public void setStatementFrequency(Integer statementFrequency) {
		this.statementFrequency = statementFrequency;
	}

	@JsonProperty("restructure-balance")
	public Object getRestructureBalance() {
		return restructureBalance;
	}

	@JsonProperty("restructure-balance")
	public void setRestructureBalance(Object restructureBalance) {
		this.restructureBalance = restructureBalance;
	}

	@JsonProperty("date-table-hi-balance")
	public Object getDateTableHiBalance() {
		return dateTableHiBalance;
	}

	@JsonProperty("date-table-hi-balance")
	public void setDateTableHiBalance(Object dateTableHiBalance) {
		this.dateTableHiBalance = dateTableHiBalance;
	}

	@JsonProperty("overlimit-option")
	public String getOverlimitOption() {
		return overlimitOption;
	}

	@JsonProperty("overlimit-option")
	public void setOverlimitOption(String overlimitOption) {
		this.overlimitOption = overlimitOption;
	}

	@JsonProperty("account-block-code")
	public Object getAccountBlockCode() {
		return accountBlockCode;
	}

	@JsonProperty("account-block-code")
	public void setAccountBlockCode(Object accountBlockCode) {
		this.accountBlockCode = accountBlockCode;
	}

	@JsonProperty("retail-interest-limit1")
	public Object getRetailInterestLimit1() {
		return retailInterestLimit1;
	}

	@JsonProperty("retail-interest-limit1")
	public void setRetailInterestLimit1(Object retailInterestLimit1) {
		this.retailInterestLimit1 = retailInterestLimit1;
	}

	@JsonProperty("bank-charges-rate")
	public Object getBankChargesRate() {
		return bankChargesRate;
	}

	@JsonProperty("bank-charges-rate")
	public void setBankChargesRate(Object bankChargesRate) {
		this.bankChargesRate = bankChargesRate;
	}

	@JsonProperty("negative-provisionalnterest-amount")
	public String getNegativeProvisionalnterestAmount() {
		return negativeProvisionalnterestAmount;
	}

	@JsonProperty("negative-provisionalnterest-amount")
	public void setNegativeProvisionalnterestAmount(String negativeProvisionalnterestAmount) {
		this.negativeProvisionalnterestAmount = negativeProvisionalnterestAmount;
	}

	@JsonProperty("retail-interest-limit3")
	public Object getRetailInterestLimit3() {
		return retailInterestLimit3;
	}

	@JsonProperty("retail-interest-limit3")
	public void setRetailInterestLimit3(Object retailInterestLimit3) {
		this.retailInterestLimit3 = retailInterestLimit3;
	}

	@JsonProperty("retail-interest-limit2")
	public Object getRetailInterestLimit2() {
		return retailInterestLimit2;
	}

	@JsonProperty("retail-interest-limit2")
	public void setRetailInterestLimit2(Object retailInterestLimit2) {
		this.retailInterestLimit2 = retailInterestLimit2;
	}

	@JsonProperty("late-fee-date")
	public Object getLateFeeDate() {
		return lateFeeDate;
	}

	@JsonProperty("late-fee-date")
	public void setLateFeeDate(Object lateFeeDate) {
		this.lateFeeDate = lateFeeDate;
	}

	@JsonProperty("risk-level")
	public String getRiskLevel() {
		return riskLevel;
	}

	@JsonProperty("risk-level")
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	@JsonProperty("second-late-notice-date")
	public Object getSecondLateNoticeDate() {
		return secondLateNoticeDate;
	}

	@JsonProperty("second-late-notice-date")
	public void setSecondLateNoticeDate(Object secondLateNoticeDate) {
		this.secondLateNoticeDate = secondLateNoticeDate;
	}

	@JsonProperty("budget-multiple")
	public Object getBudgetMultiple() {
		return budgetMultiple;
	}

	@JsonProperty("budget-multiple")
	public void setBudgetMultiple(Object budgetMultiple) {
		this.budgetMultiple = budgetMultiple;
	}

	@JsonProperty("cash-beginning-balance")
	public Object getCashBeginningBalance() {
		return cashBeginningBalance;
	}

	@JsonProperty("cash-beginning-balance")
	public void setCashBeginningBalance(Object cashBeginningBalance) {
		this.cashBeginningBalance = cashBeginningBalance;
	}

	@JsonProperty("days90-delinquency")
	public Object getDays90Delinquency() {
		return days90Delinquency;
	}

	@JsonProperty("days90-delinquency")
	public void setDays90Delinquency(Object days90Delinquency) {
		this.days90Delinquency = days90Delinquency;
	}

	@JsonProperty("online-total-retail-instalment-billed-not-paid")
	public Object getOnlineTotalRetailInstalmentBilledNotPaid() {
		return onlineTotalRetailInstalmentBilledNotPaid;
	}

	@JsonProperty("online-total-retail-instalment-billed-not-paid")
	public void setOnlineTotalRetailInstalmentBilledNotPaid(Object onlineTotalRetailInstalmentBilledNotPaid) {
		this.onlineTotalRetailInstalmentBilledNotPaid = onlineTotalRetailInstalmentBilledNotPaid;
	}

	@JsonProperty("pend-product-offering-table-select-code")
	public Integer getPendProductOfferingTableSelectCode() {
		return pendProductOfferingTableSelectCode;
	}

	@JsonProperty("pend-product-offering-table-select-code")
	public void setPendProductOfferingTableSelectCode(Integer pendProductOfferingTableSelectCode) {
		this.pendProductOfferingTableSelectCode = pendProductOfferingTableSelectCode;
	}

	@JsonProperty("dda-bank-code")
	public Object getDdaBankCode() {
		return ddaBankCode;
	}

	@JsonProperty("dda-bank-code")
	public void setDdaBankCode(Object ddaBankCode) {
		this.ddaBankCode = ddaBankCode;
	}

	@JsonProperty("retail-interest-bill-not-paid")
	public Object getRetailInterestBillNotPaid() {
		return retailInterestBillNotPaid;
	}

	@JsonProperty("retail-interest-bill-not-paid")
	public void setRetailInterestBillNotPaid(Object retailInterestBillNotPaid) {
		this.retailInterestBillNotPaid = retailInterestBillNotPaid;
	}

	@JsonProperty("product-offering-table-select-code")
	public Integer getProductOfferingTableSelectCode() {
		return productOfferingTableSelectCode;
	}

	@JsonProperty("product-offering-table-select-code")
	public void setProductOfferingTableSelectCode(Integer productOfferingTableSelectCode) {
		this.productOfferingTableSelectCode = productOfferingTableSelectCode;
	}

	@JsonProperty("retail-amount-charged-off")
	public Object getRetailAmountChargedOff() {
		return retailAmountChargedOff;
	}

	@JsonProperty("retail-amount-charged-off")
	public void setRetailAmountChargedOff(Object retailAmountChargedOff) {
		this.retailAmountChargedOff = retailAmountChargedOff;
	}

	@JsonProperty("last-standing-instruction-reject")
	public Object getLastStandingInstructionReject() {
		return lastStandingInstructionReject;
	}

	@JsonProperty("last-standing-instruction-reject")
	public void setLastStandingInstructionReject(Object lastStandingInstructionReject) {
		this.lastStandingInstructionReject = lastStandingInstructionReject;
	}

	@JsonProperty("reage-request")
	public String getReageRequest() {
		return reageRequest;
	}

	@JsonProperty("reage-request")
	public void setReageRequest(String reageRequest) {
		this.reageRequest = reageRequest;
	}

	@JsonProperty("override-code")
	public Object getOverrideCode() {
		return overrideCode;
	}

	@JsonProperty("override-code")
	public void setOverrideCode(Object overrideCode) {
		this.overrideCode = overrideCode;
	}

	@JsonProperty("number-disputed-items")
	public Object getNumberDisputedItems() {
		return numberDisputedItems;
	}

	@JsonProperty("number-disputed-items")
	public void setNumberDisputedItems(Object numberDisputedItems) {
		this.numberDisputedItems = numberDisputedItems;
	}

	@JsonProperty("retail-disputed-balance")
	public Object getRetailDisputedBalance() {
		return retailDisputedBalance;
	}

	@JsonProperty("retail-disputed-balance")
	public void setRetailDisputedBalance(Object retailDisputedBalance) {
		this.retailDisputedBalance = retailDisputedBalance;
	}

	@JsonProperty("duplicate-statement-expiry-date")
	public Object getDuplicateStatementExpiryDate() {
		return duplicateStatementExpiryDate;
	}

	@JsonProperty("duplicate-statement-expiry-date")
	public void setDuplicateStatementExpiryDate(Object duplicateStatementExpiryDate) {
		this.duplicateStatementExpiryDate = duplicateStatementExpiryDate;
	}

	@JsonProperty("date-last-cycle")
	public Object getDateLastCycle() {
		return dateLastCycle;
	}

	@JsonProperty("date-last-cycle")
	public void setDateLastCycle(Object dateLastCycle) {
		this.dateLastCycle = dateLastCycle;
	}

	@JsonProperty("ach-flag")
	public Integer getAchFlag() {
		return achFlag;
	}

	@JsonProperty("ach-flag")
	public void setAchFlag(Integer achFlag) {
		this.achFlag = achFlag;
	}

	@JsonProperty("oline-retail-payment")
	public Object getOlineRetailPayment() {
		return olineRetailPayment;
	}

	@JsonProperty("oline-retail-payment")
	public void setOlineRetailPayment(Object olineRetailPayment) {
		this.olineRetailPayment = olineRetailPayment;
	}

	@JsonProperty("grace-days")
	public Object getGraceDays() {
		return graceDays;
	}

	@JsonProperty("grace-days")
	public void setGraceDays(Object graceDays) {
		this.graceDays = graceDays;
	}

	@JsonProperty("cash-interest-option")
	public Integer getCashInterestOption() {
		return cashInterestOption;
	}

	@JsonProperty("cash-interest-option")
	public void setCashInterestOption(Integer cashInterestOption) {
		this.cashInterestOption = cashInterestOption;
	}

	@JsonProperty("date-last-debit")
	public Object getDateLastDebit() {
		return dateLastDebit;
	}

	@JsonProperty("date-last-debit")
	public void setDateLastDebit(Object dateLastDebit) {
		this.dateLastDebit = dateLastDebit;
	}

	@JsonProperty("days120-delinquency")
	public Object getDays120Delinquency() {
		return days120Delinquency;
	}

	@JsonProperty("days120-delinquency")
	public void setDays120Delinquency(Object days120Delinquency) {
		this.days120Delinquency = days120Delinquency;
	}

	@JsonProperty("permanent-collector")
	public String getPermanentCollector() {
		return permanentCollector;
	}

	@JsonProperty("permanent-collector")
	public void setPermanentCollector(String permanentCollector) {
		this.permanentCollector = permanentCollector;
	}

	@JsonProperty("projected-accrued-interest")
	public String getProjectedAccruedInterest() {
		return projectedAccruedInterest;
	}

	@JsonProperty("projected-accrued-interest")
	public void setProjectedAccruedInterest(String projectedAccruedInterest) {
		this.projectedAccruedInterest = projectedAccruedInterest;
	}

	@JsonProperty("number-declined-today")
	public Object getNumberDeclinedToday() {
		return numberDeclinedToday;
	}

	@JsonProperty("number-declined-today")
	public void setNumberDeclinedToday(Object numberDeclinedToday) {
		this.numberDeclinedToday = numberDeclinedToday;
	}

	@JsonProperty("card-fee-disclosure-flag")
	public String getCardFeeDisclosureFlag() {
		return cardFeeDisclosureFlag;
	}

	@JsonProperty("card-fee-disclosure-flag")
	public void setCardFeeDisclosureFlag(String cardFeeDisclosureFlag) {
		this.cardFeeDisclosureFlag = cardFeeDisclosureFlag;
	}

	@JsonProperty("previous-collection-reason")
	public String getPreviousCollectionReason() {
		return previousCollectionReason;
	}

	@JsonProperty("previous-collection-reason")
	public void setPreviousCollectionReason(String previousCollectionReason) {
		this.previousCollectionReason = previousCollectionReason;
	}

	@JsonProperty("interest-earned-aggregate-days")
	public Object getInterestEarnedAggregateDays() {
		return interestEarnedAggregateDays;
	}

	@JsonProperty("interest-earned-aggregate-days")
	public void setInterestEarnedAggregateDays(Object interestEarnedAggregateDays) {
		this.interestEarnedAggregateDays = interestEarnedAggregateDays;
	}

	@JsonProperty("agreement-id")
	public Object getAgreementId() {
		return agreementId;
	}

	@JsonProperty("agreement-id")
	public void setAgreementId(Object agreementId) {
		this.agreementId = agreementId;
	}

	@JsonProperty("agreement-status")
	public Integer getAgreementStatus() {
		return agreementStatus;
	}

	@JsonProperty("agreement-status")
	public void setAgreementStatus(Integer agreementStatus) {
		this.agreementStatus = agreementStatus;
	}

	@JsonProperty("total-arrear-amount")
	public Object getTotalArrearAmount() {
		return totalArrearAmount;
	}

	@JsonProperty("total-arrear-amount")
	public void setTotalArrearAmount(Object totalArrearAmount) {
		this.totalArrearAmount = totalArrearAmount;
	}

	@JsonProperty("account-available-cash")
	public Object getAccountAvailableCash() {
		return accountAvailableCash;
	}

	@JsonProperty("account-available-cash")
	public void setAccountAvailableCash(Object accountAvailableCash) {
		this.accountAvailableCash = accountAvailableCash;
	}

	@JsonProperty("dda-amount-due")
	public Object getDdaAmountDue() {
		return ddaAmountDue;
	}

	@JsonProperty("dda-amount-due")
	public void setDdaAmountDue(Object ddaAmountDue) {
		this.ddaAmountDue = ddaAmountDue;
	}

	@JsonProperty("retail-provisional-balance")
	public Object getRetailProvisionalBalance() {
		return retailProvisionalBalance;
	}

	@JsonProperty("retail-provisional-balance")
	public void setRetailProvisionalBalance(Object retailProvisionalBalance) {
		this.retailProvisionalBalance = retailProvisionalBalance;
	}

	@JsonProperty("aggregate-cash-balance")
	public Object getAggregateCashBalance() {
		return aggregateCashBalance;
	}

	@JsonProperty("aggregate-cash-balance")
	public void setAggregateCashBalance(Object aggregateCashBalance) {
		this.aggregateCashBalance = aggregateCashBalance;
	}

	@JsonProperty("payment-flag")
	public Integer getPaymentFlag() {
		return paymentFlag;
	}

	@JsonProperty("payment-flag")
	public void setPaymentFlag(Integer paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	@JsonProperty("account-status")
	public Object getAccountStatus() {
		return accountStatus;
	}

	@JsonProperty("account-status")
	public void setAccountStatus(Object accountStatus) {
		this.accountStatus = accountStatus;
	}

	@JsonProperty("thirteen-month-balance")
	public Object getThirteenMonthBalance() {
		return thirteenMonthBalance;
	}

	@JsonProperty("thirteen-month-balance")
	public void setThirteenMonthBalance(Object thirteenMonthBalance) {
		this.thirteenMonthBalance = thirteenMonthBalance;
	}

	@JsonProperty("cash-advance-rate")
	public Object getCashAdvanceRate() {
		return cashAdvanceRate;
	}

	@JsonProperty("cash-advance-rate")
	public void setCashAdvanceRate(Object cashAdvanceRate) {
		this.cashAdvanceRate = cashAdvanceRate;
	}

	@JsonProperty("cash-last-year-to-date-interest-paid")
	public Object getCashLastYearToDateInterestPaid() {
		return cashLastYearToDateInterestPaid;
	}

	@JsonProperty("cash-last-year-to-date-interest-paid")
	public void setCashLastYearToDateInterestPaid(Object cashLastYearToDateInterestPaid) {
		this.cashLastYearToDateInterestPaid = cashLastYearToDateInterestPaid;
	}

	@JsonProperty("cycle")
	public Integer getCycle() {
		return cycle;
	}

	@JsonProperty("cycle")
	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	@JsonProperty("prepay-cycles-left")
	public Integer getPrepayCyclesLeft() {
		return prepayCyclesLeft;
	}

	@JsonProperty("prepay-cycles-left")
	public void setPrepayCyclesLeft(Integer prepayCyclesLeft) {
		this.prepayCyclesLeft = prepayCyclesLeft;
	}

	@JsonProperty("thirteen-month-crlimit")
	public Object getThirteenMonthCrlimit() {
		return thirteenMonthCrlimit;
	}

	@JsonProperty("thirteen-month-crlimit")
	public void setThirteenMonthCrlimit(Object thirteenMonthCrlimit) {
		this.thirteenMonthCrlimit = thirteenMonthCrlimit;
	}

	@JsonProperty("amount-last-debit")
	public Object getAmountLastDebit() {
		return amountLastDebit;
	}

	@JsonProperty("amount-last-debit")
	public void setAmountLastDebit(Object amountLastDebit) {
		this.amountLastDebit = amountLastDebit;
	}

	@JsonProperty("account-open-date")
	public Object getAccountOpenDate() {
		return accountOpenDate;
	}

	@JsonProperty("account-open-date")
	public void setAccountOpenDate(Object accountOpenDate) {
		this.accountOpenDate = accountOpenDate;
	}

	@JsonProperty("account-officer")
	public String getAccountOfficer() {
		return accountOfficer;
	}

	@JsonProperty("account-officer")
	public void setAccountOfficer(String accountOfficer) {
		this.accountOfficer = accountOfficer;
	}

	@JsonProperty("outstanding-authorization-amount")
	public Object getOutstandingAuthorizationAmount() {
		return outstandingAuthorizationAmount;
	}

	@JsonProperty("outstanding-authorization-amount")
	public void setOutstandingAuthorizationAmount(Object outstandingAuthorizationAmount) {
		this.outstandingAuthorizationAmount = outstandingAuthorizationAmount;
	}

	@JsonProperty("cash-amount-charged-off")
	public Object getCashAmountChargedOff() {
		return cashAmountChargedOff;
	}

	@JsonProperty("cash-amount-charged-off")
	public void setCashAmountChargedOff(Object cashAmountChargedOff) {
		this.cashAmountChargedOff = cashAmountChargedOff;
	}

	@JsonProperty("retail-amount-debit")
	public Object getRetailAmountDebit() {
		return retailAmountDebit;
	}

	@JsonProperty("retail-amount-debit")
	public void setRetailAmountDebit(Object retailAmountDebit) {
		this.retailAmountDebit = retailAmountDebit;
	}

	@JsonProperty("collateral-code")
	public String getCollateralCode() {
		return collateralCode;
	}

	@JsonProperty("collateral-code")
	public void setCollateralCode(String collateralCode) {
		this.collateralCode = collateralCode;
	}

	@JsonProperty("cash-year-to-date-interest-paid")
	public Object getCashYearToDateInterestPaid() {
		return cashYearToDateInterestPaid;
	}

	@JsonProperty("cash-year-to-date-interest-paid")
	public void setCashYearToDateInterestPaid(Object cashYearToDateInterestPaid) {
		this.cashYearToDateInterestPaid = cashYearToDateInterestPaid;
	}

	@JsonProperty("dda-percentage")
	public Integer getDdaPercentage() {
		return ddaPercentage;
	}

	@JsonProperty("dda-percentage")
	public void setDdaPercentage(Integer ddaPercentage) {
		this.ddaPercentage = ddaPercentage;
	}

	@JsonProperty("overlimit-flag")
	public Integer getOverlimitFlag() {
		return overlimitFlag;
	}

	@JsonProperty("overlimit-flag")
	public void setOverlimitFlag(Integer overlimitFlag) {
		this.overlimitFlag = overlimitFlag;
	}

	@JsonProperty("cash-current-month-balance")
	public Object getCashCurrentMonthBalance() {
		return cashCurrentMonthBalance;
	}

	@JsonProperty("cash-current-month-balance")
	public void setCashCurrentMonthBalance(Object cashCurrentMonthBalance) {
		this.cashCurrentMonthBalance = cashCurrentMonthBalance;
	}

	@JsonProperty("overlimit-amount-due")
	public Object getOverlimitAmountDue() {
		return overlimitAmountDue;
	}

	@JsonProperty("overlimit-amount-due")
	public void setOverlimitAmountDue(Object overlimitAmountDue) {
		this.overlimitAmountDue = overlimitAmountDue;
	}

	@JsonProperty("retail-negative-anticipated-interest")
	public Object getRetailNegativeAnticipatedInterest() {
		return retailNegativeAnticipatedInterest;
	}

	@JsonProperty("retail-negative-anticipated-interest")
	public void setRetailNegativeAnticipatedInterest(Object retailNegativeAnticipatedInterest) {
		this.retailNegativeAnticipatedInterest = retailNegativeAnticipatedInterest;
	}

	@JsonProperty("cash-number-credit")
	public Object getCashNumberCredit() {
		return cashNumberCredit;
	}

	@JsonProperty("cash-number-credit")
	public void setCashNumberCredit(Object cashNumberCredit) {
		this.cashNumberCredit = cashNumberCredit;
	}

	@JsonProperty("collection-block-code")
	public String getCollectionBlockCode() {
		return collectionBlockCode;
	}

	@JsonProperty("collection-block-code")
	public void setCollectionBlockCode(String collectionBlockCode) {
		this.collectionBlockCode = collectionBlockCode;
	}

	@JsonProperty("non-posting-cycle-to-date-used-cash")
	public Object getNonPostingCycleToDateUsedCash() {
		return nonPostingCycleToDateUsedCash;
	}

	@JsonProperty("non-posting-cycle-to-date-used-cash")
	public void setNonPostingCycleToDateUsedCash(Object nonPostingCycleToDateUsedCash) {
		this.nonPostingCycleToDateUsedCash = nonPostingCycleToDateUsedCash;
	}

	@JsonProperty("intrest-per-diem")
	public Object getIntrestPerDiem() {
		return intrestPerDiem;
	}

	@JsonProperty("intrest-per-diem")
	public void setIntrestPerDiem(Object intrestPerDiem) {
		this.intrestPerDiem = intrestPerDiem;
	}

	@JsonProperty("date-last-delinquency")
	public Object getDateLastDelinquency() {
		return dateLastDelinquency;
	}

	@JsonProperty("date-last-delinquency")
	public void setDateLastDelinquency(Object dateLastDelinquency) {
		this.dateLastDelinquency = dateLastDelinquency;
	}

	@JsonProperty("currency-code")
	public String getCurrencyCode() {
		return currencyCode;
	}

	@JsonProperty("currency-code")
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	@JsonProperty("retail-provisional-interest")
	public Object getRetailProvisionalInterest() {
		return retailProvisionalInterest;
	}

	@JsonProperty("retail-provisional-interest")
	public void setRetailProvisionalInterest(Object retailProvisionalInterest) {
		this.retailProvisionalInterest = retailProvisionalInterest;
	}

	@JsonProperty("cash-interest-rate3")
	public Object getCashInterestRate3() {
		return cashInterestRate3;
	}

	@JsonProperty("cash-interest-rate3")
	public void setCashInterestRate3(Object cashInterestRate3) {
		this.cashInterestRate3 = cashInterestRate3;
	}

	@JsonProperty("last-payment-aged-count")
	public Object getLastPaymentAgedCount() {
		return lastPaymentAgedCount;
	}

	@JsonProperty("last-payment-aged-count")
	public void setLastPaymentAgedCount(Object lastPaymentAgedCount) {
		this.lastPaymentAgedCount = lastPaymentAgedCount;
	}

	@JsonProperty("cash-interest-rate2")
	public Object getCashInterestRate2() {
		return cashInterestRate2;
	}

	@JsonProperty("cash-interest-rate2")
	public void setCashInterestRate2(Object cashInterestRate2) {
		this.cashInterestRate2 = cashInterestRate2;
	}

	@JsonProperty("behaviour-score")
	public String getBehaviourScore() {
		return behaviourScore;
	}

	@JsonProperty("behaviour-score")
	public void setBehaviourScore(String behaviourScore) {
		this.behaviourScore = behaviourScore;
	}

	@JsonProperty("checking-account")
	public Object getCheckingAccount() {
		return checkingAccount;
	}

	@JsonProperty("checking-account")
	public void setCheckingAccount(Object checkingAccount) {
		this.checkingAccount = checkingAccount;
	}

	@JsonProperty("cash-interest-rate1")
	public Object getCashInterestRate1() {
		return cashInterestRate1;
	}

	@JsonProperty("cash-interest-rate1")
	public void setCashInterestRate1(Object cashInterestRate1) {
		this.cashInterestRate1 = cashInterestRate1;
	}

	@JsonProperty("tax-fiscal-prior-year-to-date")
	public Object getTaxFiscalPriorYearToDate() {
		return taxFiscalPriorYearToDate;
	}

	@JsonProperty("tax-fiscal-prior-year-to-date")
	public void setTaxFiscalPriorYearToDate(Object taxFiscalPriorYearToDate) {
		this.taxFiscalPriorYearToDate = taxFiscalPriorYearToDate;
	}

	@JsonProperty("date-last-reage")
	public Object getDateLastReage() {
		return dateLastReage;
	}

	@JsonProperty("date-last-reage")
	public void setDateLastReage(Object dateLastReage) {
		this.dateLastReage = dateLastReage;
	}

	@JsonProperty("risk-change-date")
	public Object getRiskChangeDate() {
		return riskChangeDate;
	}

	@JsonProperty("risk-change-date")
	public void setRiskChangeDate(Object riskChangeDate) {
		this.riskChangeDate = riskChangeDate;
	}

	@JsonProperty("interest-earned-prior-tax-year-to-date")
	public Object getInterestEarnedPriorTaxYearToDate() {
		return interestEarnedPriorTaxYearToDate;
	}

	@JsonProperty("interest-earned-prior-tax-year-to-date")
	public void setInterestEarnedPriorTaxYearToDate(Object interestEarnedPriorTaxYearToDate) {
		this.interestEarnedPriorTaxYearToDate = interestEarnedPriorTaxYearToDate;
	}

	@JsonProperty("date-last-retail-payment")
	public Object getDateLastRetailPayment() {
		return dateLastRetailPayment;
	}

	@JsonProperty("date-last-retail-payment")
	public void setDateLastRetailPayment(Object dateLastRetailPayment) {
		this.dateLastRetailPayment = dateLastRetailPayment;
	}

	@JsonProperty("cash-payment-cycle-to-date")
	public Object getCashPaymentCycleToDate() {
		return cashPaymentCycleToDate;
	}

	@JsonProperty("cash-payment-cycle-to-date")
	public void setCashPaymentCycleToDate(Object cashPaymentCycleToDate) {
		this.cashPaymentCycleToDate = cashPaymentCycleToDate;
	}

	@JsonProperty("gst-registration-number")
	public String getGstRegistrationNumber() {
		return gstRegistrationNumber;
	}

	@JsonProperty("gst-registration-number")
	public void setGstRegistrationNumber(String gstRegistrationNumber) {
		this.gstRegistrationNumber = gstRegistrationNumber;
	}

	@JsonProperty("online-total-payment-today")
	public Object getOnlineTotalPaymentToday() {
		return onlineTotalPaymentToday;
	}

	@JsonProperty("online-total-payment-today")
	public void setOnlineTotalPaymentToday(Object onlineTotalPaymentToday) {
		this.onlineTotalPaymentToday = onlineTotalPaymentToday;
	}

	@JsonProperty("prior-product-offering-table-exp-date")
	public Object getPriorProductOfferingTableExpDate() {
		return priorProductOfferingTableExpDate;
	}

	@JsonProperty("prior-product-offering-table-exp-date")
	public void setPriorProductOfferingTableExpDate(Object priorProductOfferingTableExpDate) {
		this.priorProductOfferingTableExpDate = priorProductOfferingTableExpDate;
	}

	@JsonProperty("fixed-payment-amount")
	public Object getFixedPaymentAmount() {
		return fixedPaymentAmount;
	}

	@JsonProperty("fixed-payment-amount")
	public void setFixedPaymentAmount(Object fixedPaymentAmount) {
		this.fixedPaymentAmount = fixedPaymentAmount;
	}

	@JsonProperty("mobile-payment-indicator")
	public String getMobilePaymentIndicator() {
		return mobilePaymentIndicator;
	}

	@JsonProperty("mobile-payment-indicator")
	public void setMobilePaymentIndicator(String mobilePaymentIndicator) {
		this.mobilePaymentIndicator = mobilePaymentIndicator;
	}

	@JsonProperty("closing-balance")
	public Object getClosingBalance() {
		return closingBalance;
	}

	@JsonProperty("closing-balance")
	public void setClosingBalance(Object closingBalance) {
		this.closingBalance = closingBalance;
	}

	@JsonProperty("first-payment-default-flag")
	public Integer getFirstPaymentDefaultFlag() {
		return firstPaymentDefaultFlag;
	}

	@JsonProperty("first-payment-default-flag")
	public void setFirstPaymentDefaultFlag(Integer firstPaymentDefaultFlag) {
		this.firstPaymentDefaultFlag = firstPaymentDefaultFlag;
	}

	@JsonProperty("gst-residence-code")
	public Object getGstResidenceCode() {
		return gstResidenceCode;
	}

	@JsonProperty("gst-residence-code")
	public void setGstResidenceCode(Object gstResidenceCode) {
		this.gstResidenceCode = gstResidenceCode;
	}

	@JsonProperty("current-collector")
	public String getCurrentCollector() {
		return currentCollector;
	}

	@JsonProperty("current-collector")
	public void setCurrentCollector(String currentCollector) {
		this.currentCollector = currentCollector;
	}

	@JsonProperty("retail-year-to-date-interest-billed")
	public Object getRetailYearToDateInterestBilled() {
		return retailYearToDateInterestBilled;
	}

	@JsonProperty("retail-year-to-date-interest-billed")
	public void setRetailYearToDateInterestBilled(Object retailYearToDateInterestBilled) {
		this.retailYearToDateInterestBilled = retailYearToDateInterestBilled;
	}

	@JsonProperty("thirteen-month-cash")
	public Object getThirteenMonthCash() {
		return thirteenMonthCash;
	}

	@JsonProperty("thirteen-month-cash")
	public void setThirteenMonthCash(Object thirteenMonthCash) {
		this.thirteenMonthCash = thirteenMonthCash;
	}

	@JsonProperty("temp-agreement-id")
	public Object getTempAgreementId() {
		return tempAgreementId;
	}

	@JsonProperty("temp-agreement-id")
	public void setTempAgreementId(Object tempAgreementId) {
		this.tempAgreementId = tempAgreementId;
	}

	@JsonProperty("retail-interest-effective-flag")
	public Integer getRetailInterestEffectiveFlag() {
		return retailInterestEffectiveFlag;
	}

	@JsonProperty("retail-interest-effective-flag")
	public void setRetailInterestEffectiveFlag(Integer retailInterestEffectiveFlag) {
		this.retailInterestEffectiveFlag = retailInterestEffectiveFlag;
	}

	@JsonProperty("retail-miscellaneous-fee")
	public Integer getRetailMiscellaneousFee() {
		return retailMiscellaneousFee;
	}

	@JsonProperty("retail-miscellaneous-fee")
	public void setRetailMiscellaneousFee(Integer retailMiscellaneousFee) {
		this.retailMiscellaneousFee = retailMiscellaneousFee;
	}

	@JsonProperty("retail-negative-anticipated-balance")
	public Object getRetailNegativeAnticipatedBalance() {
		return retailNegativeAnticipatedBalance;
	}

	@JsonProperty("retail-negative-anticipated-balance")
	public void setRetailNegativeAnticipatedBalance(Object retailNegativeAnticipatedBalance) {
		this.retailNegativeAnticipatedBalance = retailNegativeAnticipatedBalance;
	}

	@JsonProperty("retail-pricing-campaign-code2")
	public Integer getRetailPricingCampaignCode2() {
		return retailPricingCampaignCode2;
	}

	@JsonProperty("retail-pricing-campaign-code2")
	public void setRetailPricingCampaignCode2(Integer retailPricingCampaignCode2) {
		this.retailPricingCampaignCode2 = retailPricingCampaignCode2;
	}

	@JsonProperty("ach-payment-expiry-date")
	public Object getAchPaymentExpiryDate() {
		return achPaymentExpiryDate;
	}

	@JsonProperty("ach-payment-expiry-date")
	public void setAchPaymentExpiryDate(Object achPaymentExpiryDate) {
		this.achPaymentExpiryDate = achPaymentExpiryDate;
	}

	@JsonProperty("prior-product-offering-table-select-code")
	public Integer getPriorProductOfferingTableSelectCode() {
		return priorProductOfferingTableSelectCode;
	}

	@JsonProperty("prior-product-offering-table-select-code")
	public void setPriorProductOfferingTableSelectCode(Integer priorProductOfferingTableSelectCode) {
		this.priorProductOfferingTableSelectCode = priorProductOfferingTableSelectCode;
	}

	@JsonProperty("retail-pricing-campaign-code1")
	public Integer getRetailPricingCampaignCode1() {
		return retailPricingCampaignCode1;
	}

	@JsonProperty("retail-pricing-campaign-code1")
	public void setRetailPricingCampaignCode1(Integer retailPricingCampaignCode1) {
		this.retailPricingCampaignCode1 = retailPricingCampaignCode1;
	}

	@JsonProperty("retail-pricing-campaign-code4")
	public Integer getRetailPricingCampaignCode4() {
		return retailPricingCampaignCode4;
	}

	@JsonProperty("retail-pricing-campaign-code4")
	public void setRetailPricingCampaignCode4(Integer retailPricingCampaignCode4) {
		this.retailPricingCampaignCode4 = retailPricingCampaignCode4;
	}

	@JsonProperty("retail-pricing-campaign-code3")
	public Integer getRetailPricingCampaignCode3() {
		return retailPricingCampaignCode3;
	}

	@JsonProperty("retail-pricing-campaign-code3")
	public void setRetailPricingCampaignCode3(Integer retailPricingCampaignCode3) {
		this.retailPricingCampaignCode3 = retailPricingCampaignCode3;
	}

	@JsonProperty("retail-pricing-campaign-code5")
	public Object getRetailPricingCampaignCode5() {
		return retailPricingCampaignCode5;
	}

	@JsonProperty("retail-pricing-campaign-code5")
	public void setRetailPricingCampaignCode5(Object retailPricingCampaignCode5) {
		this.retailPricingCampaignCode5 = retailPricingCampaignCode5;
	}

	@JsonProperty("service-program-id")
	public Object getServiceProgramId() {
		return serviceProgramId;
	}

	@JsonProperty("service-program-id")
	public void setServiceProgramId(Object serviceProgramId) {
		this.serviceProgramId = serviceProgramId;
	}

	@JsonProperty("collection-histoy-flag")
	public String getCollectionHistoyFlag() {
		return collectionHistoyFlag;
	}

	@JsonProperty("collection-histoy-flag")
	public void setCollectionHistoyFlag(String collectionHistoyFlag) {
		this.collectionHistoyFlag = collectionHistoyFlag;
	}

	@JsonProperty("payment-due-date")
	public Object getPaymentDueDate() {
		return paymentDueDate;
	}

	@JsonProperty("payment-due-date")
	public void setPaymentDueDate(Object paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	@JsonProperty("thirteen-month-payment")
	public Object getThirteenMonthPayment() {
		return thirteenMonthPayment;
	}

	@JsonProperty("thirteen-month-payment")
	public void setThirteenMonthPayment(Object thirteenMonthPayment) {
		this.thirteenMonthPayment = thirteenMonthPayment;
	}

	@JsonProperty("waive-over-limit")
	public Integer getWaiveOverLimit() {
		return waiveOverLimit;
	}

	@JsonProperty("waive-over-limit")
	public void setWaiveOverLimit(Integer waiveOverLimit) {
		this.waiveOverLimit = waiveOverLimit;
	}

	@JsonProperty("date-last-cash-payment")
	public Object getDateLastCashPayment() {
		return dateLastCashPayment;
	}

	@JsonProperty("date-last-cash-payment")
	public void setDateLastCashPayment(Object dateLastCashPayment) {
		this.dateLastCashPayment = dateLastCashPayment;
	}

	@JsonProperty("saadique-flag")
	public Object getSaadiqueFlag() {
		return saadiqueFlag;
	}

	@JsonProperty("saadique-flag")
	public void setSaadiqueFlag(Object saadiqueFlag) {
		this.saadiqueFlag = saadiqueFlag;
	}

	@JsonProperty("retail-misc-cycle-to-date")
	public Object getRetailMiscCycleToDate() {
		return retailMiscCycleToDate;
	}

	@JsonProperty("retail-misc-cycle-to-date")
	public void setRetailMiscCycleToDate(Object retailMiscCycleToDate) {
		this.retailMiscCycleToDate = retailMiscCycleToDate;
	}

	@JsonProperty("total-amount-due")
	public Object getTotalAmountDue() {
		return totalAmountDue;
	}

	@JsonProperty("total-amount-due")
	public void setTotalAmountDue(Object totalAmountDue) {
		this.totalAmountDue = totalAmountDue;
	}

	@JsonProperty("days60-delinquency")
	public Object getDays60Delinquency() {
		return days60Delinquency;
	}

	@JsonProperty("days60-delinquency")
	public void setDays60Delinquency(Object days60Delinquency) {
		this.days60Delinquency = days60Delinquency;
	}

	@JsonProperty("payoff-amount2")
	public String getPayoffAmount2() {
		return payoffAmount2;
	}

	@JsonProperty("payoff-amount2")
	public void setPayoffAmount2(String payoffAmount2) {
		this.payoffAmount2 = payoffAmount2;
	}

	@JsonProperty("interest-earned-aggregate-balance")
	public Object getInterestEarnedAggregateBalance() {
		return interestEarnedAggregateBalance;
	}

	@JsonProperty("interest-earned-aggregate-balance")
	public void setInterestEarnedAggregateBalance(Object interestEarnedAggregateBalance) {
		this.interestEarnedAggregateBalance = interestEarnedAggregateBalance;
	}

	@JsonProperty("product-offering-table-exp-date-hist2")
	public Object getProductOfferingTableExpDateHist2() {
		return productOfferingTableExpDateHist2;
	}

	@JsonProperty("product-offering-table-exp-date-hist2")
	public void setProductOfferingTableExpDateHist2(Object productOfferingTableExpDateHist2) {
		this.productOfferingTableExpDateHist2 = productOfferingTableExpDateHist2;
	}

	@JsonProperty("product-offering-table-exp-date-hist1")
	public Object getProductOfferingTableExpDateHist1() {
		return productOfferingTableExpDateHist1;
	}

	@JsonProperty("product-offering-table-exp-date-hist1")
	public void setProductOfferingTableExpDateHist1(Object productOfferingTableExpDateHist1) {
		this.productOfferingTableExpDateHist1 = productOfferingTableExpDateHist1;
	}

	@JsonProperty("product-offering-table-exp-date-hist4")
	public Object getProductOfferingTableExpDateHist4() {
		return productOfferingTableExpDateHist4;
	}

	@JsonProperty("product-offering-table-exp-date-hist4")
	public void setProductOfferingTableExpDateHist4(Object productOfferingTableExpDateHist4) {
		this.productOfferingTableExpDateHist4 = productOfferingTableExpDateHist4;
	}

	@JsonProperty("product-offering-table-exp-date-hist3")
	public Object getProductOfferingTableExpDateHist3() {
		return productOfferingTableExpDateHist3;
	}

	@JsonProperty("product-offering-table-exp-date-hist3")
	public void setProductOfferingTableExpDateHist3(Object productOfferingTableExpDateHist3) {
		this.productOfferingTableExpDateHist3 = productOfferingTableExpDateHist3;
	}

	@JsonProperty("number-of-standing-instruction-reject")
	public Integer getNumberOfStandingInstructionReject() {
		return numberOfStandingInstructionReject;
	}

	@JsonProperty("number-of-standing-instruction-reject")
	public void setNumberOfStandingInstructionReject(Integer numberOfStandingInstructionReject) {
		this.numberOfStandingInstructionReject = numberOfStandingInstructionReject;
	}

	@JsonProperty("product-offering-table-exp-date-hist5")
	public Object getProductOfferingTableExpDateHist5() {
		return productOfferingTableExpDateHist5;
	}

	@JsonProperty("product-offering-table-exp-date-hist5")
	public void setProductOfferingTableExpDateHist5(Object productOfferingTableExpDateHist5) {
		this.productOfferingTableExpDateHist5 = productOfferingTableExpDateHist5;
	}

	@JsonProperty("amount-declined-today")
	public Object getAmountDeclinedToday() {
		return amountDeclinedToday;
	}

	@JsonProperty("amount-declined-today")
	public void setAmountDeclinedToday(Object amountDeclinedToday) {
		this.amountDeclinedToday = amountDeclinedToday;
	}

	@JsonProperty("dispute-balance")
	public Object getDisputeBalance() {
		return disputeBalance;
	}

	@JsonProperty("dispute-balance")
	public void setDisputeBalance(Object disputeBalance) {
		this.disputeBalance = disputeBalance;
	}

	@JsonProperty("high-balance-payment-table-date")
	public Object getHighBalancePaymentTableDate() {
		return highBalancePaymentTableDate;
	}

	@JsonProperty("high-balance-payment-table-date")
	public void setHighBalancePaymentTableDate(Object highBalancePaymentTableDate) {
		this.highBalancePaymentTableDate = highBalancePaymentTableDate;
	}

	@JsonProperty("product-code")
	public String getProductCode() {
		return productCode;
	}

	@JsonProperty("product-code")
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	@JsonProperty("old-behaviour-score")
	public String getOldBehaviourScore() {
		return oldBehaviourScore;
	}

	@JsonProperty("old-behaviour-score")
	public void setOldBehaviourScore(String oldBehaviourScore) {
		this.oldBehaviourScore = oldBehaviourScore;
	}

	@JsonProperty("non-posting-cycle-to-date-used-amount")
	public Object getNonPostingCycleToDateUsedAmount() {
		return nonPostingCycleToDateUsedAmount;
	}

	@JsonProperty("non-posting-cycle-to-date-used-amount")
	public void setNonPostingCycleToDateUsedAmount(Object nonPostingCycleToDateUsedAmount) {
		this.nonPostingCycleToDateUsedAmount = nonPostingCycleToDateUsedAmount;
	}

	@JsonProperty("cash-balance")
	public Object getCashBalance() {
		return cashBalance;
	}

	@JsonProperty("cash-balance")
	public void setCashBalance(Object cashBalance) {
		this.cashBalance = cashBalance;
	}

	@JsonProperty("amount-last-purchase")
	public Object getAmountLastPurchase() {
		return amountLastPurchase;
	}

	@JsonProperty("amount-last-purchase")
	public void setAmountLastPurchase(Object amountLastPurchase) {
		this.amountLastPurchase = amountLastPurchase;
	}

	@JsonProperty("dda-account-number")
	public Object getDdaAccountNumber() {
		return ddaAccountNumber;
	}

	@JsonProperty("dda-account-number")
	public void setDdaAccountNumber(Object ddaAccountNumber) {
		this.ddaAccountNumber = ddaAccountNumber;
	}

	@JsonProperty("date-last-statement")
	public Object getDateLastStatement() {
		return dateLastStatement;
	}

	@JsonProperty("date-last-statement")
	public void setDateLastStatement(Object dateLastStatement) {
		this.dateLastStatement = dateLastStatement;
	}

	@JsonProperty("charge-off-reason-code2")
	public String getChargeOffReasonCode2() {
		return chargeOffReasonCode2;
	}

	@JsonProperty("charge-off-reason-code2")
	public void setChargeOffReasonCode2(String chargeOffReasonCode2) {
		this.chargeOffReasonCode2 = chargeOffReasonCode2;
	}

	@JsonProperty("charge-off-reason-code1")
	public String getChargeOffReasonCode1() {
		return chargeOffReasonCode1;
	}

	@JsonProperty("charge-off-reason-code1")
	public void setChargeOffReasonCode1(String chargeOffReasonCode1) {
		this.chargeOffReasonCode1 = chargeOffReasonCode1;
	}

	@JsonProperty("wallet-limit")
	public Integer getWalletLimit() {
		return walletLimit;
	}

	@JsonProperty("wallet-limit")
	public void setWalletLimit(Integer walletLimit) {
		this.walletLimit = walletLimit;
	}

	@JsonProperty("retail-beginning-balance")
	public Object getRetailBeginningBalance() {
		return retailBeginningBalance;
	}

	@JsonProperty("retail-beginning-balance")
	public void setRetailBeginningBalance(Object retailBeginningBalance) {
		this.retailBeginningBalance = retailBeginningBalance;
	}

	@JsonProperty("projected-provisionalnterest-amount")
	public String getProjectedProvisionalnterestAmount() {
		return projectedProvisionalnterestAmount;
	}

	@JsonProperty("projected-provisionalnterest-amount")
	public void setProjectedProvisionalnterestAmount(String projectedProvisionalnterestAmount) {
		this.projectedProvisionalnterestAmount = projectedProvisionalnterestAmount;
	}

	@JsonProperty("waive-prepay")
	public Integer getWaivePrepay() {
		return waivePrepay;
	}

	@JsonProperty("waive-prepay")
	public void setWaivePrepay(Integer waivePrepay) {
		this.waivePrepay = waivePrepay;
	}

	@JsonProperty("retail-previous-month-balance")
	public Object getRetailPreviousMonthBalance() {
		return retailPreviousMonthBalance;
	}

	@JsonProperty("retail-previous-month-balance")
	public void setRetailPreviousMonthBalance(Object retailPreviousMonthBalance) {
		this.retailPreviousMonthBalance = retailPreviousMonthBalance;
	}

	@JsonProperty("aggregate-year-to-date-days")
	public Object getAggregateYearToDateDays() {
		return aggregateYearToDateDays;
	}

	@JsonProperty("aggregate-year-to-date-days")
	public void setAggregateYearToDateDays(Object aggregateYearToDateDays) {
		this.aggregateYearToDateDays = aggregateYearToDateDays;
	}

	@JsonProperty("annual-fee-disclosure-sw")
	public String getAnnualFeeDisclosureSw() {
		return annualFeeDisclosureSw;
	}

	@JsonProperty("annual-fee-disclosure-sw")
	public void setAnnualFeeDisclosureSw(String annualFeeDisclosureSw) {
		this.annualFeeDisclosureSw = annualFeeDisclosureSw;
	}

	@JsonProperty("aggregate-retail-balance")
	public Object getAggregateRetailBalance() {
		return aggregateRetailBalance;
	}

	@JsonProperty("aggregate-retail-balance")
	public void setAggregateRetailBalance(Object aggregateRetailBalance) {
		this.aggregateRetailBalance = aggregateRetailBalance;
	}

	@JsonProperty("conversion-balance")
	public Object getConversionBalance() {
		return conversionBalance;
	}

	@JsonProperty("conversion-balance")
	public void setConversionBalance(Object conversionBalance) {
		this.conversionBalance = conversionBalance;
	}

	@JsonProperty("retail-service-bill-not-paid")
	public Integer getRetailServiceBillNotPaid() {
		return retailServiceBillNotPaid;
	}

	@JsonProperty("retail-service-bill-not-paid")
	public void setRetailServiceBillNotPaid(Integer retailServiceBillNotPaid) {
		this.retailServiceBillNotPaid = retailServiceBillNotPaid;
	}

	@JsonProperty("retail-interest-change-flag")
	public Integer getRetailInterestChangeFlag() {
		return retailInterestChangeFlag;
	}

	@JsonProperty("retail-interest-change-flag")
	public void setRetailInterestChangeFlag(Integer retailInterestChangeFlag) {
		this.retailInterestChangeFlag = retailInterestChangeFlag;
	}

	@JsonProperty("days30-delinquency")
	public Object getDays30Delinquency() {
		return days30Delinquency;
	}

	@JsonProperty("days30-delinquency")
	public void setDays30Delinquency(Object days30Delinquency) {
		this.days30Delinquency = days30Delinquency;
	}

	@JsonProperty("exposure-at-risk")
	public Object getExposureAtRisk() {
		return exposureAtRisk;
	}

	@JsonProperty("exposure-at-risk")
	public void setExposureAtRisk(Object exposureAtRisk) {
		this.exposureAtRisk = exposureAtRisk;
	}

	@JsonProperty("user-code")
	public String getUserCode() {
		return userCode;
	}

	@JsonProperty("user-code")
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	@JsonProperty("payoff-amount")
	public String getPayoffAmount() {
		return payoffAmount;
	}

	@JsonProperty("payoff-amount")
	public void setPayoffAmount(String payoffAmount) {
		this.payoffAmount = payoffAmount;
	}

	@JsonProperty("available-credit2")
	public Object getAvailableCredit2() {
		return availableCredit2;
	}

	@JsonProperty("available-credit2")
	public void setAvailableCredit2(Object availableCredit2) {
		this.availableCredit2 = availableCredit2;
	}

	@JsonProperty("available-account-limit")
	public Object getAvailableAccountLimit() {
		return availableAccountLimit;
	}

	@JsonProperty("available-account-limit")
	public void setAvailableAccountLimit(Object availableAccountLimit) {
		this.availableAccountLimit = availableAccountLimit;
	}

	@JsonProperty("previous-override-code")
	public Object getPreviousOverrideCode() {
		return previousOverrideCode;
	}

	@JsonProperty("previous-override-code")
	public void setPreviousOverrideCode(Object previousOverrideCode) {
		this.previousOverrideCode = previousOverrideCode;
	}

	@JsonProperty("cash-service-bill-not-paid")
	public Object getCashServiceBillNotPaid() {
		return cashServiceBillNotPaid;
	}

	@JsonProperty("cash-service-bill-not-paid")
	public void setCashServiceBillNotPaid(Object cashServiceBillNotPaid) {
		this.cashServiceBillNotPaid = cashServiceBillNotPaid;
	}

	@JsonProperty("retail-number-credit")
	public Object getRetailNumberCredit() {
		return retailNumberCredit;
	}

	@JsonProperty("retail-number-credit")
	public void setRetailNumberCredit(Object retailNumberCredit) {
		this.retailNumberCredit = retailNumberCredit;
	}

	@JsonProperty("date-credit-balance")
	public Object getDateCreditBalance() {
		return dateCreditBalance;
	}

	@JsonProperty("date-credit-balance")
	public void setDateCreditBalance(Object dateCreditBalance) {
		this.dateCreditBalance = dateCreditBalance;
	}

	@JsonProperty("collection-control-flag")
	public String getCollectionControlFlag() {
		return collectionControlFlag;
	}

	@JsonProperty("collection-control-flag")
	public void setCollectionControlFlag(String collectionControlFlag) {
		this.collectionControlFlag = collectionControlFlag;
	}

	@JsonProperty("retail-payment-cycle-to-date")
	public Object getRetailPaymentCycleToDate() {
		return retailPaymentCycleToDate;
	}

	@JsonProperty("retail-payment-cycle-to-date")
	public void setRetailPaymentCycleToDate(Object retailPaymentCycleToDate) {
		this.retailPaymentCycleToDate = retailPaymentCycleToDate;
	}

	@JsonProperty("available-cash2")
	public Object getAvailableCash2() {
		return availableCash2;
	}

	@JsonProperty("available-cash2")
	public void setAvailableCash2(Object availableCash2) {
		this.availableCash2 = availableCash2;
	}

	@JsonProperty("online-outstanding-statement-balance")
	public Object getOnlineOutstandingStatementBalance() {
		return onlineOutstandingStatementBalance;
	}

	@JsonProperty("online-outstanding-statement-balance")
	public void setOnlineOutstandingStatementBalance(Object onlineOutstandingStatementBalance) {
		this.onlineOutstandingStatementBalance = onlineOutstandingStatementBalance;
	}

	@JsonProperty("cash-advance-percent-fee-cycle-to-date")
	public Object getCashAdvancePercentFeeCycleToDate() {
		return cashAdvancePercentFeeCycleToDate;
	}

	@JsonProperty("cash-advance-percent-fee-cycle-to-date")
	public void setCashAdvancePercentFeeCycleToDate(Object cashAdvancePercentFeeCycleToDate) {
		this.cashAdvancePercentFeeCycleToDate = cashAdvancePercentFeeCycleToDate;
	}

	@JsonProperty("retail-insurance-bill-not-paid")
	public Integer getRetailInsuranceBillNotPaid() {
		return retailInsuranceBillNotPaid;
	}

	@JsonProperty("retail-insurance-bill-not-paid")
	public void setRetailInsuranceBillNotPaid(Integer retailInsuranceBillNotPaid) {
		this.retailInsuranceBillNotPaid = retailInsuranceBillNotPaid;
	}

	@JsonProperty("current-finance-charge-waiver-count")
	public Integer getCurrentFinanceChargeWaiverCount() {
		return currentFinanceChargeWaiverCount;
	}

	@JsonProperty("current-finance-charge-waiver-count")
	public void setCurrentFinanceChargeWaiverCount(Integer currentFinanceChargeWaiverCount) {
		this.currentFinanceChargeWaiverCount = currentFinanceChargeWaiverCount;
	}

	@JsonProperty("current-late-charge-waiver-count")
	public Integer getCurrentLateChargeWaiverCount() {
		return currentLateChargeWaiverCount;
	}

	@JsonProperty("current-late-charge-waiver-count")
	public void setCurrentLateChargeWaiverCount(Integer currentLateChargeWaiverCount) {
		this.currentLateChargeWaiverCount = currentLateChargeWaiverCount;
	}

	@JsonProperty("current-overlimit-fee-waiver-count")
	public Integer getCurrentOverlimitFeeWaiverCount() {
		return currentOverlimitFeeWaiverCount;
	}

	@JsonProperty("current-overlimit-fee-waiver-count")
	public void setCurrentOverlimitFeeWaiverCount(Integer currentOverlimitFeeWaiverCount) {
		this.currentOverlimitFeeWaiverCount = currentOverlimitFeeWaiverCount;
	}

	@JsonProperty("online-finance-charge-waiver-count")
	public Integer getOnlineFinanceChargeWaiverCount() {
		return onlineFinanceChargeWaiverCount;
	}

	@JsonProperty("online-finance-charge-waiver-count")
	public void setOnlineFinanceChargeWaiverCount(Integer onlineFinanceChargeWaiverCount) {
		this.onlineFinanceChargeWaiverCount = onlineFinanceChargeWaiverCount;
	}

	@JsonProperty("online-late-charge-waiver-count")
	public Integer getOnlineLateChargeWaiverCount() {
		return onlineLateChargeWaiverCount;
	}

	@JsonProperty("online-late-charge-waiver-count")
	public void setOnlineLateChargeWaiverCount(Integer onlineLateChargeWaiverCount) {
		this.onlineLateChargeWaiverCount = onlineLateChargeWaiverCount;
	}

	@JsonProperty("online-overlimit-fee-waiver-count")
	public Integer getOnlineOverlimitFeeWaiverCount() {
		return onlineOverlimitFeeWaiverCount;
	}

	@JsonProperty("online-overlimit-fee-waiver-count")
	public void setOnlineOverlimitFeeWaiverCount(Integer onlineOverlimitFeeWaiverCount) {
		this.onlineOverlimitFeeWaiverCount = onlineOverlimitFeeWaiverCount;
	}

	@JsonProperty("feewaiverDetails")
	public List<FeewaiverDetail> getFeewaiverDetails() {
		return feewaiverDetails;
	}

	@JsonProperty("feewaiverDetails")
	public void setFeewaiverDetails(List<FeewaiverDetail> feewaiverDetails) {
		this.feewaiverDetails = feewaiverDetails;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}
}
