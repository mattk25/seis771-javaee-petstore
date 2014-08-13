package com.SEIS771.proj.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

@Entity
@NamedQueries({
    @NamedQuery(name = "getSubmissionsByStatus", query = "SELECT s FROM Submission s WHERE s.status = :status ORDER BY s.id"),
    @NamedQuery(name = "getAllSubmissions", query = "SELECT s from Submission s")
})
public class Submission {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Temporal(TemporalType.DATE)
	@Column(length=30)
	private Date submitDate;
	@Column(length=30)
	private String status;
	@Column(length=256)
	private String submissionContraints;
	@Column(length=256)
	private String staffComments;
	
	@OneToOne(optional=true, fetch = FetchType.EAGER)
	private Person staff;
	
	@OneToOne
	private Person person;
	
	@XmlInverseReference(mappedBy="submission")
	@OneToOne
	private Contract contract;
	
	@ManyToOne(fetch=FetchType.LAZY, cascade = CascadeType.PERSIST)
	  @JoinColumn(name="FACILITY_ID")
	  private Facility facility;
	
	
	/**
	 * @return the staffComments
	 */
	public String getStaffComments() {
		return staffComments;
	}
	/**
	 * @param staffComments the staffComments to set
	 */
	public void setStaffComments(String staffComments) {
		this.staffComments = staffComments;
	}
	/**
	 * @return the contract
	 */
	public Contract getContract() {
		return contract;
	}
	/**
	 * @param contract the contract to set
	 */
	public void setContract(Contract contract) {
		this.contract = contract;
	}
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	private Animal animal;
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSubmissionConstraint() {
		return submissionContraints;
	}
	public void setSubmissionConstraint(String constraint) {
		this.submissionContraints = constraint;
	}
	public int getId() {
		return id;
	}
	public String getSubmissionContraints() {
		return submissionContraints;
	}
	public void setSubmissionContraints(String submissionContraints) {
		this.submissionContraints = submissionContraints;
	}
	public Person getPerson() {
		return person;
	}
	public void setPerson(Person person) {
		this.person = person;
	}

	public void setId(int id) {
		this.id = id;
	}
	public Person getStaff() {
		return staff;
	}
	public void setStaff(Person staff) {
		this.staff = staff;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	public Animal getAnimal() {
		return animal;
	}
	public Facility getFacility() {
		return facility;
	}
	public void setFacility(Facility facility) {
		this.facility = facility;
	}
	
	
}
