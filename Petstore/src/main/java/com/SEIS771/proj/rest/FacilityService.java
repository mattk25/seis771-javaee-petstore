package com.SEIS771.proj.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.SEIS771.proj.EJB.PetstoreEJB;
import com.SEIS771.proj.model.Animal;
import com.SEIS771.proj.model.Facility;


@Path("/facilities")
public class FacilityService {
	
	@EJB
	PetstoreEJB pejb;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Facility> getFacilities() {
		List<Facility> data = pejb.getFacilities();
		return data;
	}
	
}
