package com.SEIS771.proj.rest;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.SEIS771.proj.EJB.PetstoreEJB;
import com.SEIS771.proj.exception.ValueNotFoundException;
import com.SEIS771.proj.model.Person;
import com.SEIS771.proj.model.UserGroup;

@Path("/people")
public class PersonService {

	@EJB
	PetstoreEJB pejb;

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Person createUser(Person person) {

			if (person.getAddress() == null) {
				throw new ValueNotFoundException(
						"Address is required to create a person");
			}

			UserGroup group = pejb.getUserGroupByName(person.getGroup().getName());
			if (group.getName().equalsIgnoreCase("admin")) {
				throw new WebApplicationException(
						"Unauthorized to create admin user", 403);
			}
			
		return pejb.createPerson(person);
	}

	@GET
	@Path("/person/{username}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getUserById(@PathParam("username") String username) {
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(Person.class);

			Person test = pejb.getPersonByUsername(username);

			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty("eclipselink.media-type", "application/json");
			marshaller.marshal(test, System.out);

			return test;

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
