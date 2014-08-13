package com.SEIS771.proj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Adoption {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length=30)
	private int personID;
	@Column(length=30) 
	private int animalID;
	@Column(length=30)
	private int lastVisit;
	@Column(length=30)
	private String status;
	@Column(length=256)
	private String AdoptConstraint;
	public int getPersonID() {
		return personID;
	}
	public void setPersonID(int personID) {
		this.personID = personID;
	}
	public int getAnimalID() {
		return animalID;
	}
	public void setAnimalID(int animalID) {
		this.animalID = animalID;
	}
	public int getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(int lastVisit) {
		this.lastVisit = lastVisit;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getAdoptConstraint() {
		return AdoptConstraint;
	}
	public void setAdoptConstraint(String AdoptConstraint) {
		this.AdoptConstraint = AdoptConstraint;
	}
	public int getId() {
		return id;
	}
	
}
