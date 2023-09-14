package com.scb.ivr.model.c400.res.ccresource;

import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Attributes {
	
	@JsonProperty("org-num") 
    public String orgNum;
    
	@JsonProperty("exception-response-code") 
    public String exceptionResponseCode;
    
	@JsonProperty("perpetual-pin-flag") 
    public String perpetualPinFlag;
    
	@JsonProperty("alternate-block-code") 
    public String alternateBlockCode;
    
	@JsonProperty("exception-purge-date") 
    public String exceptionPurgeDate;
    
	@JsonProperty("card-limit") 
    public String cardLimit;
    
	@JsonProperty("block-code") 
    public String blockCode;
    
	@JsonProperty("card-creation-date") 
    public String cardCreationDate;
    
	@JsonProperty("reissue-action") 
    public String reissueAction;
    
	public ArrayList<PlasticCardInfo> plasticCardInfos;

	@JsonProperty("block-code-date") 
    public String blockCodeDate;
    
	@JsonProperty("transfer-org-number") 
    public String transferOrgNumber;
    
	@JsonProperty("expiry-date") 
    public String expiryDate;
    
	@JsonProperty("last-expiry-date") 
    public String lastExpiryDate;
    
	@JsonProperty("cash-advance-flag") 
    public String cashAdvanceFlag;
    
	@JsonProperty("transfer-account-number") 
    public int transferAccountNumber;
    
	@JsonProperty("sub-product-code") 
    public String subProductCode;
    
	@JsonProperty("block-status") 
    public String blockStatus;
    
	@JsonProperty("card-available-cash-limit") 
    public String cardAvailableCashLimit;
    
	@JsonProperty("warning-bulletin-date") 
    public String warningBulletinDate;
    
	@JsonProperty("combo-card-account") 
    public int comboCardAccount;
    
	@JsonProperty("pin-last-request-date") 
    public Date pinLastRequestDate;
    
	@JsonProperty("product-description") 
    public String productDescription;
    
	@JsonProperty("first-usage-flag") 
    public String firstUsageFlag;
    
	@JsonProperty("transfer-indicator") 
    public String transferIndicator;
    
	@JsonProperty("card-validity-status") 
    public String cardValidityStatus;
    
	@JsonProperty("combo-card-branch") 
    public int comboCardBranch;
    
	@JsonProperty("card-closed-date") 
    public String cardClosedDate;
    
	@JsonProperty("available-card-limit") 
    public String availableCardLimit;
    
	@JsonProperty("transfer-effective-date") 
    public String transferEffectiveDate;
    
	@JsonProperty("alternate-block-code-date") 
    public String alternateBlockCodeDate;
    
	@JsonProperty("transfer-product-code") 
    public String transferProductCode;
    
	@JsonProperty("customer-id") 
    public String customerId;
    
	@JsonProperty("card-status") 
    public String cardStatus;
    
	@JsonProperty("combo-card-sequence") 
    public int comboCardSequence;
    
	@JsonProperty("region-codes") 
    public String regionCodes;
    
	@JsonProperty("card-num") 
    public String cardNum;
    
	@JsonProperty("original-product-code") 
    public String originalProductCode;
    
	@JsonProperty("product-code") 
    public String productCode;
    
	@JsonProperty("card-cash-limit") 
    public String cardCashLimit;
    
	@JsonProperty("card-open-date") 
    public String cardOpenDate;
    
	public ArrayList<Last25BlockCode> last25BlockCodes;
    
	@JsonProperty("security-token-card") 
    public String securityTokenCard;
    
	public String getOrgNum() {
		return orgNum;
	}
	public void setOrgNum(String orgNum) {
		this.orgNum = orgNum;
	}
    
	public String getExceptionResponseCode() {
		return exceptionResponseCode;
	}
	public void setExceptionResponseCode(String exceptionResponseCode) {
		this.exceptionResponseCode = exceptionResponseCode;
	}
    
	public String getPerpetualPinFlag() {
		return perpetualPinFlag;
	}
	public void setPerpetualPinFlag(String perpetualPinFlag) {
		this.perpetualPinFlag = perpetualPinFlag;
	}

	public String getAlternateBlockCode() {
		return alternateBlockCode;
	}
	public void setAlternateBlockCode(String alternateBlockCode) {
		this.alternateBlockCode = alternateBlockCode;
	}

	public String getExceptionPurgeDate() {
		return exceptionPurgeDate;
	}
	public void setExceptionPurgeDate(String exceptionPurgeDate) {
		this.exceptionPurgeDate = exceptionPurgeDate;
	}

	public String getCardLimit() {
		return cardLimit;
	}
	public void setCardLimit(String cardLimit) {
		this.cardLimit = cardLimit;
	}
	
	public String getBlockCode() {
		return blockCode;
	}
	public void setBlockCode(String blockCode) {
		this.blockCode = blockCode;
	}
	
	public String getCardCreationDate() {
		return cardCreationDate;
	}
	public void setCardCreationDate(String cardCreationDate) {
		this.cardCreationDate = cardCreationDate;
	}
	
	public String getReissueAction() {
		return reissueAction;
	}
	public void setReissueAction(String reissueAction) {
		this.reissueAction = reissueAction;
	}
	
	public ArrayList<PlasticCardInfo> getPlasticCardInfos() {
		return plasticCardInfos;
	}
	public void setPlasticCardInfos(ArrayList<PlasticCardInfo> plasticCardInfos) {
		this.plasticCardInfos = plasticCardInfos;
	}
	
	public String getBlockCodeDate() {
		return blockCodeDate;
	}
	public void setBlockCodeDate(String blockCodeDate) {
		this.blockCodeDate = blockCodeDate;
	}
	public String getTransferOrgNumber() {
		return transferOrgNumber;
	}
	public void setTransferOrgNumber(String transferOrgNumber) {
		this.transferOrgNumber = transferOrgNumber;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getLastExpiryDate() {
		return lastExpiryDate;
	}
	public void setLastExpiryDate(String lastExpiryDate) {
		this.lastExpiryDate = lastExpiryDate;
	}
	public String getCashAdvanceFlag() {
		return cashAdvanceFlag;
	}
	public void setCashAdvanceFlag(String cashAdvanceFlag) {
		this.cashAdvanceFlag = cashAdvanceFlag;
	}
	public int getTransferAccountNumber() {
		return transferAccountNumber;
	}
	public void setTransferAccountNumber(int transferAccountNumber) {
		this.transferAccountNumber = transferAccountNumber;
	}
	public String getSubProductCode() {
		return subProductCode;
	}
	public void setSubProductCode(String subProductCode) {
		this.subProductCode = subProductCode;
	}
	public String getBlockStatus() {
		return blockStatus;
	}
	public void setBlockStatus(String blockStatus) {
		this.blockStatus = blockStatus;
	}
	public String getCardAvailableCashLimit() {
		return cardAvailableCashLimit;
	}
	public void setCardAvailableCashLimit(String cardAvailableCashLimit) {
		this.cardAvailableCashLimit = cardAvailableCashLimit;
	}
	public String getWarningBulletinDate() {
		return warningBulletinDate;
	}
	public void setWarningBulletinDate(String warningBulletinDate) {
		this.warningBulletinDate = warningBulletinDate;
	}
	public int getComboCardAccount() {
		return comboCardAccount;
	}
	public void setComboCardAccount(int comboCardAccount) {
		this.comboCardAccount = comboCardAccount;
	}
	public Date getPinLastRequestDate() {
		return pinLastRequestDate;
	}
	public void setPinLastRequestDate(Date pinLastRequestDate) {
		this.pinLastRequestDate = pinLastRequestDate;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getFirstUsageFlag() {
		return firstUsageFlag;
	}
	public void setFirstUsageFlag(String firstUsageFlag) {
		this.firstUsageFlag = firstUsageFlag;
	}
	public String getTransferIndicator() {
		return transferIndicator;
	}
	public void setTransferIndicator(String transferIndicator) {
		this.transferIndicator = transferIndicator;
	}
	public String getCardValidityStatus() {
		return cardValidityStatus;
	}
	public void setCardValidityStatus(String cardValidityStatus) {
		this.cardValidityStatus = cardValidityStatus;
	}
	public int getComboCardBranch() {
		return comboCardBranch;
	}
	public void setComboCardBranch(int comboCardBranch) {
		this.comboCardBranch = comboCardBranch;
	}
	public String getCardClosedDate() {
		return cardClosedDate;
	}
	public void setCardClosedDate(String cardClosedDate) {
		this.cardClosedDate = cardClosedDate;
	}
	public String getAvailableCardLimit() {
		return availableCardLimit;
	}
	public void setAvailableCardLimit(String availableCardLimit) {
		this.availableCardLimit = availableCardLimit;
	}
	public String getTransferEffectiveDate() {
		return transferEffectiveDate;
	}
	public void setTransferEffectiveDate(String transferEffectiveDate) {
		this.transferEffectiveDate = transferEffectiveDate;
	}
	public String getAlternateBlockCodeDate() {
		return alternateBlockCodeDate;
	}
	public void setAlternateBlockCodeDate(String alternateBlockCodeDate) {
		this.alternateBlockCodeDate = alternateBlockCodeDate;
	}
	public String getTransferProductCode() {
		return transferProductCode;
	}
	public void setTransferProductCode(String transferProductCode) {
		this.transferProductCode = transferProductCode;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getCardStatus() {
		return cardStatus;
	}
	public void setCardStatus(String cardStatus) {
		this.cardStatus = cardStatus;
	}
	public int getComboCardSequence() {
		return comboCardSequence;
	}
	public void setComboCardSequence(int comboCardSequence) {
		this.comboCardSequence = comboCardSequence;
	}
	public String getRegionCodes() {
		return regionCodes;
	}
	public void setRegionCodes(String regionCodes) {
		this.regionCodes = regionCodes;
	}
	public String getCardNum() {
		return cardNum;
	}
	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}
	public String getOriginalProductCode() {
		return originalProductCode;
	}
	public void setOriginalProductCode(String originalProductCode) {
		this.originalProductCode = originalProductCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getCardCashLimit() {
		return cardCashLimit;
	}
	public void setCardCashLimit(String cardCashLimit) {
		this.cardCashLimit = cardCashLimit;
	}
	public String getCardOpenDate() {
		return cardOpenDate;
	}
	public void setCardOpenDate(String cardOpenDate) {
		this.cardOpenDate = cardOpenDate;
	}
	public ArrayList<Last25BlockCode> getLast25BlockCodes() {
		return last25BlockCodes;
	}
	public void setLast25BlockCodes(ArrayList<Last25BlockCode> last25BlockCodes) {
		this.last25BlockCodes = last25BlockCodes;
	}
	public String getSecurityTokenCard() {
		return securityTokenCard;
	}
	public void setSecurityTokenCard(String securityTokenCard) {
		this.securityTokenCard = securityTokenCard;
	}

	@Override
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    
}
