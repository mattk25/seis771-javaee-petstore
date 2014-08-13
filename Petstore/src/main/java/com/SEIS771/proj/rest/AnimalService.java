package com.SEIS771.proj.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.SEIS771.proj.EJB.PetstoreEJB;
import com.SEIS771.proj.model.Animal;
import com.SEIS771.proj.model.Breed;
import com.SEIS771.proj.model.Species;
import com.SEIS771.proj.model.Vaccine;

@Path("/animals")
public class AnimalService {

	@EJB
	PetstoreEJB pejb;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{specie}/vaccines")
	public List<Vaccine> getVaccinesForSpecie(@PathParam("specie") String name) {
		System.out.println(name);
		List<Vaccine> data = pejb.getVaccinesForSpecie(name);
		return data;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{specie}/breeds")
	public List<Breed> getBreedsForSpecie(@PathParam("specie") String name) {
		System.out.println(name);
		List<Breed> data = pejb.getBreedsForSpecie(name);
		return data;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/species")
	public List<Species> getSpecies() {
		List<Species> data = pejb.getSpecies();
		return data;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Animal createAnimal(Animal animal) throws Exception {
		animal.toString();

		Animal a = pejb.createAnimal(animal);
		JAXBContext jc = JAXBContext.newInstance(Animal.class);

		Marshaller marshaller = jc.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		marshaller.setProperty("eclipselink.media-type", "application/json");
		marshaller.marshal(a, System.out);

		return a;
	}

	@GET
	@PermitAll
	@Produces(MediaType.APPLICATION_JSON)
	public List<Animal> list(@Context UriInfo info,
			@QueryParam("offset") int offset, @QueryParam("page") int page) {
		System.out.println(page);

		List<Animal> animals = pejb
				.getAvailableAnimalsForAdoption(offset, page);
		System.out.println(animals.size());
		return animals;
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/animal/{id}")
	public Animal updateAnimal(Animal animal) throws Exception {
		Animal a = pejb.updateAnimal(animal);
		return a;
	}
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/animal/{id}")
	public Animal getAnimalByID(@PathParam("id")int id) {
		return pejb.getAnimalById(id);
	}
	// @GET
	// //@Produces(MediaType.TEXT_PLAIN)
	// @Produces(MediaType.APPLICATION_JSON)
	// public List<Animal> getIt() throws Exception {
	// JAXBContext jc = JAXBContext.newInstance(Animal.class);
	// List<Animal> data = pejb.getAnimals();
	//
	// Marshaller marshaller = jc.createMarshaller();
	// marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
	// marshaller.setProperty("eclipselink.media-type", "application/json");
	// marshaller.marshal(data, System.out);
	//
	//
	// return data;
	// }
}
