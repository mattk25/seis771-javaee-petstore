package com.SEIS771.proj.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.SEIS771.proj.EJB.PetstoreEJB;
import com.SEIS771.proj.exception.ValueNotFoundException;
import com.SEIS771.proj.model.CareRecord;

@Path("/animalCare")
public class CareRecordService {

	@EJB
	PetstoreEJB pejb;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/animal/{id}")
	public List<CareRecord> createCareRecord(@PathParam("id") int id, CareRecord careRecord) {
				return pejb.createCareRecord(id, careRecord);			
	}

	@GET
	@Path("/animalCare/{recordID}")
	@Produces(MediaType.APPLICATION_JSON)
	public CareRecord getRecordById(@PathParam("Id") int recordID) {
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(CareRecord.class);

			CareRecord test = pejb.getCareRecordById(recordID);

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
	

	@GET
	@Path("/animal/{animalID}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CareRecord> getCareRecordsByAnimalId(@PathParam("animalID") int animalID) {
		JAXBContext jc;
		System.out.println("MattK");
		try {
			jc = JAXBContext.newInstance(CareRecord.class);
			
			System.out.println("Animal ID: " + animalID);
			List<CareRecord> crs = pejb.getCareRecordsForAnimalById(animalID);
//			Marshaller marshaller = jc.createMarshaller();
//			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
//			marshaller.setProperty("eclipselink.media-type", "application/json");
//			marshaller.marshal(test, System.out);

			return crs;

		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}
}
