package com.SEIS771.proj.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

@Entity
public class Facility {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length=30)
	private String facilityName;
	@Column(length=30)
	private int cageCount;
	@Column
	private int maxCages;
	
	@OneToOne(fetch=FetchType.EAGER, 
			optional=false, cascade = CascadeType.PERSIST)
	private Address address;
	
	@XmlInverseReference(mappedBy="group")
	@OneToMany(mappedBy="facility")
	private List<Submission> submissions;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFacilityName() {
		return facilityName;
	}

	public void setFacilityName(String facilityName) {
		this.facilityName = facilityName;
	}

	public int getCageCount() {
		return cageCount;
	}

	public void setCageCount(int cageCount) {
		this.cageCount = cageCount;
	}

	public int getMaxCages() {
		return maxCages;
	}

	public void setMaxCages(int maxCages) {
		this.maxCages = maxCages;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<Submission> getSubmissions() {
		return submissions;
	}

	public void setSubmissions(List<Submission> submissions) {
		this.submissions = submissions;
	}


	
	
	
	

	
}
