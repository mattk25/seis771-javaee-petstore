package com.SEIS771.proj.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({
		@NamedQuery(name = "getSpeciesByName", query = "SELECT s FROM Species s Where s.name = :name"),
		@NamedQuery(name = "getAllSpiciess", query = "SELECT s FROM Species s")

})
public class Species {

	@Column(length = 30, nullable = false)
	private String name;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	public List<Vaccine> vaccine;

	/**
	 * @return returns a list of vaccines appropriate for the selected species
	 */
	public List<Vaccine> getVaccine() {
		return vaccine;
	}

	/**
	 * @param vaccine
	 *            sets a list of vaccines appropriate for the selected species
	 */
	public void setVaccine(List<Vaccine> vaccine) {
		this.vaccine = vaccine;
	}

	/**
	 * @return gets the id of the selected species
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            sets the id of the selected species
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return gets the name of the selected species
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            sets the name of the selected species
	 */
	public void setName(String _name) {
		name = _name;
	}

}
