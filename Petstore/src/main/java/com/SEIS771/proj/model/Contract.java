package com.SEIS771.proj.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

@Entity
public class Contract {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Temporal(TemporalType.DATE)
	@Column(name = "dropOffDate")
	private java.util.Date  dropOffDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "pickUpDate")
	private java.util.Date  pickUpDate;
	
	@Column(length=30)
	private String signature;
	@Column(length=90)
	private String memoText;
	@Column(length=900)
	private String fullContractText;
	/**
	 * @return gets the submission ID
	 */
	
	@OneToOne(fetch=FetchType.EAGER, 
			optional=false)
	private Submission submission;
	
	@OneToOne(fetch=FetchType.EAGER, 
			optional=false)
	private AdoptionRequest adoptionRequest;
	
	/**
	 * @return the submission
	 */
	public Submission getSubmission() {
		return submission;
	}
	/**
	 * @param submission the submission to set
	 */
	public void setSubmission(Submission submission) {
		this.submission = submission;
	}
	/**
	 * @return get the drop off date
	 */
	public Date getDropOffDate() {
		return dropOffDate;
	}
	/**
	 * @param dropOffDate sets the dropOffDate to the passed in value
	 */
	public void setDropOffDate(Date dropOffDate) {
		this.dropOffDate = dropOffDate;
	}
	/**
	 * @return gets the signature 
	 */
	public String getSignature() {
		return signature;
	}
	/**
	 * @param signature set the signature to the passed in value
	 */
	public void setSignature(String signature) {
		this.signature = signature;
	}
	/**
	 * @return gets the comment text, this is the field that holds the string with
	 * admin's review comments about accepting or rejecting submittor's animal
	 */
	public String getText() {
		return memoText;
	}
	/**
	 * @param text  sets the text to the passed in value, this is the field that holds
	 *  the string with admin's review comments about accepting or rejecting submittor's animal
	 */
	public void setText(String text) {
		this.memoText = text;
	}
	/**
	 * @return gets the contract ID
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @return  this is the field that holds really big string
	 * to be printed or e-mailed to the submittor
	 */
	//public String fullContractText(Submission s) {
	public void fullContractText(Submission s) {

		fullContractText = "Hello " + s.getPerson().getFirstName()+ " "+s.getPerson().getLastName() + ",\n";
		fullContractText = fullContractText + "of "+ s.getPerson().getAddress().getStreet() +" "+s.getPerson().getAddress().getCity()+" "+s.getPerson().getAddress().getState()+ ",\n";
		fullContractText = fullContractText + "(email address: "+" "+s.getPerson().getEmail() + "),\n\n";
		fullContractText = fullContractText + "We can accept your animal. Our review comments are:  \n";
		fullContractText = fullContractText + memoText + "\n";
		fullContractText = fullContractText + "This contract is for accepting the following animal from you. \n\n";
		fullContractText = fullContractText +  "Name: "+s.getAnimal().getName()+", "+s.getAnimal().getBreed()+ ", Species: "+ s.getAnimal().getSpecie().getName()+", Sex: "+ s.getAnimal().getSex()+"\n\n"; 
		fullContractText = fullContractText + "This is a legal contract, you will be surrendering all rights to this animal. \n\n";
		fullContractText = fullContractText + "Please bring your animal to our facility the morning of "+ dropOffDate + ". \n";
		
		this.setFullContractText(fullContractText);
		//return fullContractText;
	}
	
	public void fullContractText(AdoptionRequest s) {

		fullContractText = "Hello " + s.getRequester().getFirstName()+ " "+s.getRequester().getLastName() + ",\n";
		fullContractText = fullContractText + "of "+ s.getRequester().getAddress().getStreet() +" "+s.getRequester().getAddress().getCity()+" "+s.getRequester().getAddress().getState()+ ",\n";
		fullContractText = fullContractText + "(email address: "+" "+s.getRequester().getEmail() + "),\n\n";
		fullContractText = fullContractText + "You agree to take care of this animal or we will sue you. We have a no return policy:  \n";
		fullContractText = fullContractText + memoText + "\n";
		fullContractText = fullContractText + "This contract is for the adoption of the following animal. \n\n";
		fullContractText = fullContractText +  "Name: "+s.getRequestedAnimal().getName()+", "+s.getRequestedAnimal().getBreed()+ ", Species: "+ s.getRequestedAnimal().getSpecie().getName()+", Sex: "+ s.getRequestedAnimal().getSex()+"\n\n"; 
		fullContractText = fullContractText + "This is a legal contract, you will be acception all responsibilities to this animal. \n\n";		
		this.setFullContractText(fullContractText);
		//return fullContractText;
	}
	
	public String getMemoText() {
		return memoText;
	}
	public void setMemoText(String memoText) {
		this.memoText = memoText;
	}
	public String getFullContractText() {
		return fullContractText;
	}
	public void setFullContractText(String fullContractText) {
		this.fullContractText = fullContractText;
	}
	public void setId(int id) {
		this.id = id;
	}
	public java.util.Date getPickUpDate() {
		return pickUpDate;
	}
	public void setPickUpDate(java.util.Date pickUpDate) {
		this.pickUpDate = pickUpDate;
	}
	public AdoptionRequest getAdoption() {
		return adoptionRequest;
	}
	public void setAdoption(AdoptionRequest adoption) {
		this.adoptionRequest = adoption;
	}
}
