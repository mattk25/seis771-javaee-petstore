package com.SEIS771.proj.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name="getUserAdoptionRequests", query="SELECT ar FROM AdoptionRequest ar Where ar.requester.userName =:username")
	
})
public class AdoptionRequest {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@OneToOne(optional=false, fetch = FetchType.EAGER)
	private Person requester;
	
	@OneToOne(optional=true, fetch = FetchType.EAGER)
	private Animal requestedAnimal;  // if the requester wants a specific animal
	
	@OneToMany( fetch = FetchType.EAGER)
	public List<Animal> suggestedAnimals;
	
	@Temporal(TemporalType.DATE)
	@Column(length=30)
	private Date submitDate;
	
	@Column(length=30)
	private String status;  // status of this request 
	@Column(length=30)
	private String animalAge;  // age of ideal animal
	@Column(length=30)
	private boolean haveChildren;   // requester has children  T/F
	@Column(length=30)
	private String color;      // color of ideal animal
	@Column(length=30)
	private String species;    // species of ideal animal
	@Column(length=30)
	private String breed;      // breed of ideal animal      
	@Column(length=30)
	private String sex;        // sex of ideal animal
	@Column(length=30)
	private boolean isNeutered;    //  ideal animal is neutered T/F
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAnimalAge() {
		return animalAge;
	}
	public void setAnimalAge(String animalAge){
		this.animalAge = animalAge;
	}
	public boolean getHaveChildren() {
		return haveChildren;
	}
    public void setHaveChildren(boolean haveChildren) {
    	this.haveChildren = haveChildren;
    }
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSpecies() {
		return species;
	}
	public void setSpecies(String species){
		this.species = species;
	}
	public String getBreed() {
		return breed;
	}
	public void setBreed(String breed) {
		this.breed = breed;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public boolean getIsNeutered() {
		return isNeutered;
	}
	public void setIsNeutered(boolean isNeutered) {
		this.isNeutered = isNeutered;
	}
	public int getId() {
		return id;
	}
	public Person getRequester() {
		return requester;
	}
	public void setRequester(Person requester) {
		this.requester = requester;
	}
	public Animal getRequestedAnimal() {
		return requestedAnimal;
	}
	public void setRequestedAnimal(Animal requestedAnimal) {
		this.requestedAnimal = requestedAnimal;
	}
	public List<Animal> getSuggestedAnimals() {
		return suggestedAnimals;
	}
	public void setSuggestedAnimals(List<Animal> suggestedAnimals) {
		this.suggestedAnimals = suggestedAnimals;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setNeutered(boolean isNeutered) {
		this.isNeutered = isNeutered;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	
}
