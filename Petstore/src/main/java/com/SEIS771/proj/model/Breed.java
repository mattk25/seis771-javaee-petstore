package com.SEIS771.proj.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;

@Entity
@NamedQueries({
	@NamedQuery(name="getBreedByName", query="SELECT b FROM Breed b Where b.name = :name"),
	@NamedQuery(name="getBreedsForSpecie", query="SELECT b FROM Breed b Where b.specie.id = :id")
})
public class Breed {
	
@Id
private String name;


@OneToOne
private Species specie;

/**
 * @return the name
 */
public String getName() {
	return name;
}
/**
 * @param name the name to set
 */
public void setName(String name) {
	this.name = name;
}

public String toString() {
	return "Breed = " + name;
}
public Species getSpecie() {
	return specie;
}
public void setSpecie(Species specie) {
	this.specie = specie;
}
}
