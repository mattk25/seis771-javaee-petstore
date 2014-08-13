package com.SEIS771.proj.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

@Entity
@NamedQueries({ @NamedQuery(name = "getAllAnimals", query = "SELECT a FROM Animal a ORDER BY a.id") })
public class Animal extends BaseEntity {

	@Column(length = 30)
	private String name;
	@Column(length = 30)
	private String color;
	@Column(length = 1)
	private String sex;
	@Column(length = 30)
	private String temperment;

	@Column(length = 30)
	private String image = "default.jpg";

	@Column(length = 30)
	private String age = "1 year";
	@Column(length = 30)
	private Boolean isAvailable;		

	// An animal can only have one breed
	@OneToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.PERSIST)
	private Breed breed;

	// An animal can only belong to one specie
	@OneToOne(fetch = FetchType.EAGER, optional = true, cascade = CascadeType.PERSIST)
	private Species specie;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
	private MedicalRecord medicalRecord;

	@XmlInverseReference(mappedBy = "animal")
	@OneToOne(optional = true)
	private Submission submission;

	@OneToMany(mappedBy="animal", fetch = FetchType.LAZY)
	private List<CareRecord> records;
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Breed getBreed() {
		return breed;
	}

	public void setBreed(Breed breed) {
		this.breed = breed;
	}

	public Species getSpecie() {
		return specie;
	}

	public void setSpecie(Species specie) {
		this.specie = specie;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String toString() {
		return "Animal name: " + name + " color: " + color + " sex: " + sex
				+ " temperment: " + temperment;
	}

	public String getTemperment() {
		return temperment;
	}

	public void setTemperment(String temperment) {
		this.temperment = temperment;
	}

	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}

	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}

	public Submission getSubmission() {
		return submission;
	}

	public void setSubmission(Submission submission) {
		this.submission = submission;
	}

	public List<CareRecord> getRecords() {
		return records;
	}

	public void setRecords(List<CareRecord> records) {
		this.records = records;
	}
	
	public Boolean getIsAvailable() {
		return isAvailable;
	}

	public void setIsAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}
}
