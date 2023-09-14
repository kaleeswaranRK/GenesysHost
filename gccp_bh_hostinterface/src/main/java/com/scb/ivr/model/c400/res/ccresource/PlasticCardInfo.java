package com.scb.ivr.model.c400.res.ccresource;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;


public class PlasticCardInfo implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("embossing-index") 
    public int embossingIndex;
    @JsonProperty("embossed-date") 
    public Date embossedDate;
    @JsonProperty("embosser-name") 
    public String embosserName;
    @JsonProperty("activation-status") 
    public String activationStatus;
    @JsonProperty("card-activation-time") 
    public int cardActivationTime;
    @JsonProperty("contactless-ind") 
    public String contactlessInd;
    @JsonProperty("card-sequence-nbr") 
    public int cardSequenceNbr;
    @JsonProperty("card-relationship") 
    public String cardRelationship;
    @JsonProperty("plastic-supplementary-opt") 
    public int plasticSupplementaryOpt;
    @JsonProperty("card-feature") 
    public int cardFeature;
    @JsonProperty("nummber-of-plastic-embossed") 
    public int nummberOfPlasticEmbossed;
    @JsonProperty("number-of-plastic-returned") 
    public int numberOfPlasticReturned;
    @JsonProperty("card-previous-action") 
    public int cardPreviousAction;
    @JsonProperty("embossing-date-last-requested") 
    public Date embossingDateLastRequested;
    
	public int getEmbossingIndex() {
		return embossingIndex;
	}
	public void setEmbossingIndex(int embossingIndex) {
		this.embossingIndex = embossingIndex;
	}
	public Date getEmbossedDate() {
		return embossedDate;
	}
	public void setEmbossedDate(Date embossedDate) {
		this.embossedDate = embossedDate;
	}
	public String getEmbosserName() {
		return embosserName;
	}
	public void setEmbosserName(String embosserName) {
		this.embosserName = embosserName;
	}
	public String getActivationStatus() {
		return activationStatus;
	}
	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}
	public int getCardActivationTime() {
		return cardActivationTime;
	}
	public void setCardActivationTime(int cardActivationTime) {
		this.cardActivationTime = cardActivationTime;
	}
	public String getContactlessInd() {
		return contactlessInd;
	}
	public void setContactlessInd(String contactlessInd) {
		this.contactlessInd = contactlessInd;
	}
	public int getCardSequenceNbr() {
		return cardSequenceNbr;
	}
	public void setCardSequenceNbr(int cardSequenceNbr) {
		this.cardSequenceNbr = cardSequenceNbr;
	}
	public String getCardRelationship() {
		return cardRelationship;
	}
	public void setCardRelationship(String cardRelationship) {
		this.cardRelationship = cardRelationship;
	}
	public int getPlasticSupplementaryOpt() {
		return plasticSupplementaryOpt;
	}
	public void setPlasticSupplementaryOpt(int plasticSupplementaryOpt) {
		this.plasticSupplementaryOpt = plasticSupplementaryOpt;
	}
	public int getCardFeature() {
		return cardFeature;
	}
	public void setCardFeature(int cardFeature) {
		this.cardFeature = cardFeature;
	}
	public int getNummberOfPlasticEmbossed() {
		return nummberOfPlasticEmbossed;
	}
	public void setNummberOfPlasticEmbossed(int nummberOfPlasticEmbossed) {
		this.nummberOfPlasticEmbossed = nummberOfPlasticEmbossed;
	}
	public int getNumberOfPlasticReturned() {
		return numberOfPlasticReturned;
	}
	public void setNumberOfPlasticReturned(int numberOfPlasticReturned) {
		this.numberOfPlasticReturned = numberOfPlasticReturned;
	}
	public int getCardPreviousAction() {
		return cardPreviousAction;
	}
	public void setCardPreviousAction(int cardPreviousAction) {
		this.cardPreviousAction = cardPreviousAction;
	}
	public Date getEmbossingDateLastRequested() {
		return embossingDateLastRequested;
	}
	public void setEmbossingDateLastRequested(Date embossingDateLastRequested) {
		this.embossingDateLastRequested = embossingDateLastRequested;
	}
	@Override
	public String toString() {
    	return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
    
    
}
