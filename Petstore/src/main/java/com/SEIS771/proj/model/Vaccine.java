package com.SEIS771.proj.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


@Entity
@NamedQueries({
	@NamedQuery(name="getVaccinesForSpecie", query="SELECT s FROM Vaccine s Where s.specie.id = :id")
})
public class Vaccine {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	@Column(length=30)
	private String name;
	
	@ManyToOne(fetch=FetchType.LAZY)    
    public Species specie;

	/**
	 * @return return the auto-generated id for the vaccine
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id  bypasses auto-generating the id for the vaccine, and sets it
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return return the name of the vaccine
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name set the name of the vaccine
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return gets the specie that the vaccine can be administered to
	 */
	public Species getSpecie() {
		return specie;
	}

	/**
	 * @param specie sets the specie that the vaccine can be administered to
	 */
	public void setSpecie(Species specie) {
		this.specie = specie;
	}
	
	
}



