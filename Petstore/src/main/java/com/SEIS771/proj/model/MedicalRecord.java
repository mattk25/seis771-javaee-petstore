package com.SEIS771.proj.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

@Entity
public class MedicalRecord {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length=30)
	private String animalCondition;
	@Column(length=30)
	private String vetName;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "lastVisit")
	private Date lastVisit;
	
	@Column(length=30)
	private Boolean isneutered;
	
	@Column
	private Boolean needsVetVisit;
	
	@XmlInverseReference(mappedBy="medicalRecord")
	@OneToOne
	private Animal animal;
	
	@OneToMany(cascade = CascadeType.PERSIST)
	private List<Vaccinated> vaccines;
	//@Column(length=30)
	//private Vaccinated vaccinated;
	
	//@OneToMany(cascade = CascadeType.ALL, mappedBy = "Vaccinated")
//	@OneToMany( mappedBy="Vaccinated")
//	 private Collection<Vaccinated> VaccinatedCollection;
	
	
	
	public String getAnimalCondition() {
		return animalCondition;
	}
	public void setAnimalCondition(String condition) {
		this.animalCondition = condition;
	}
	public String getVetName() {
		return vetName;
	}
	public void setVetName(String vetName) {
		this.vetName = vetName;
	}
	public Date getLastVisit() {
		return lastVisit;
	}
	public void setLastVisit(Date lastVisit) {
		this.lastVisit = lastVisit;
	}
	public Boolean getIsneutered() {
		return isneutered;
	}
	public void setIsneutered(Boolean isneutered) {
		this.isneutered = isneutered;
	}
//	
//	public Vaccinated getVaccinated() {
//		return vaccinated;
//	}
//	
//	//@OneToMany(targetEntity= Vaccinated.class, mappedBy="MedicalRecord" )
//	public void setVaccinated(Vaccinated vaccinated) {
//		this.vaccinated = vaccinated;
//	}
	public int getId() {
		return id;
	}
	public List<Vaccinated> getVaccines() {
		return vaccines;
	}
	public void setVaccines(List<Vaccinated> vaccines) {
		this.vaccines = vaccines;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Animal getAnimal() {
		return animal;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	public Boolean getNeedsVetVisit() {
		return needsVetVisit;
	}
	public void setNeedsVetVisit(Boolean needsVetVisit) {
		this.needsVetVisit = needsVetVisit;
	}
	
}
