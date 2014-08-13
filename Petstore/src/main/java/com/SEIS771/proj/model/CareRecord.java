/**
 * 
 */
package com.SEIS771.proj.model;

/**
 * @author J Stark
 *
 */
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;

@Entity
public class CareRecord {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length=10)
	private boolean gaveFood;
	
	@Column(length=10)
	private boolean gaveWater;
	
	@Column(length=80)
	private String comment;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "recordTimeStamp")
	private java.util.Date  recordTimeStamp;
	
	@XmlInverseReference(mappedBy="records")
	  @ManyToOne(fetch=FetchType.LAZY)
	  @JoinColumn(name="ANIMAL_ID")
	  private Animal animal;
	
	/**
	 * @return gets the record ID
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return gets the boolean value of gaveFood
	 */
	public boolean getGaveFood() {
		return gaveFood;
	}
	/**
	 * @param _gaveFood  sets the boolean value of gaveFood
	 */
	public void setGaveFood(boolean _gaveFood) {
		this.gaveFood = _gaveFood;
	}
	/**
	 * @return gets the boolean value of gaveWater
	 */
	public boolean getGaveWater() {
		return gaveWater;
	}
	/**
	 * @param _GaveWater  sets  the boolean value of gaveWater
	 */
	public void setGaveWater(boolean _GaveWater) {
		this.gaveWater = _GaveWater;
	}
	public String getComment(){
		return comment;
	}
	public void setComment(String _comment){
		this.comment = _comment;
	}
	public Date getRecordTimeStamp() {
		return recordTimeStamp;
	}
	public void setRecordTimeStamp(Date _recordTimeStamp) {
		this.recordTimeStamp = _recordTimeStamp;
	}
	public Animal getAnimal() {
		return animal;
	}
	public void setAnimal(Animal animal) {
		this.animal = animal;
	}
	public void setId(int id) {
		this.id = id;
	}
}
