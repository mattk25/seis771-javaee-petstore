package com.SEIS771.proj.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;


@Entity
public class Vaccinated {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
    
	@Temporal(TemporalType.DATE)
	@Column(name = "vaccinationDate")
	private Date vaccinationDate;
	
	@Column
	private Boolean isVaccinated;
	
	@OneToOne
	private Vaccine vaccine;
	
	@Column(length=30)
	private String tagNum;
	
	@XmlInverseReference(mappedBy="vaccines")
    @ManyToOne(optional=true)
    private MedicalRecord medicalRecord;
	
	public Date getVaccinationDate() {
		return vaccinationDate;
	}
	public void setVaccinationDate(Date vaccinationDate) {
		this.vaccinationDate = vaccinationDate;
	}
	public String getTagNum() {
		return tagNum;
	}
	public void setTagNum(String tagNum) {
		this.tagNum = tagNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public MedicalRecord getMedicalRecord() {
		return medicalRecord;
	}
	public void setMedicalRecord(MedicalRecord medicalRecord) {
		this.medicalRecord = medicalRecord;
	}
	public Boolean getIsVaccinated() {
		return isVaccinated;
	}
	public void setIsVaccinated(Boolean isVaccinated) {
		this.isVaccinated = isVaccinated;
	}
	public Vaccine getVaccine() {
		return vaccine;
	}
	public void setVaccine(Vaccine vaccine) {
		this.vaccine = vaccine;
	}
	
	}



