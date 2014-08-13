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
import javax.ws.rs.core.MediaType;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.SEIS771.proj.EJB.PetstoreEJB;
import com.SEIS771.proj.exception.ValueNotFoundException;
import com.SEIS771.proj.model.AdoptionRequest;
import com.SEIS771.proj.model.Contract;
import com.SEIS771.proj.model.Submission;

@Path("/contracts")
public class ContractService {

	@EJB
	PetstoreEJB pejb;

	@GET
	@Path("/submission/{id}")
	public Contract getContractText(@PathParam("id") int id) {

		Submission s = pejb.getSubmissionById(id);

		Contract c = new Contract();
		c.fullContractText(s);
		c.setSubmission(s);

		return c;
	}
	
	@GET
	@Path("/adoption/{id}")
	public Contract getContractAdoptionText(@PathParam("id") int id) {

		AdoptionRequest a = pejb.getAdoptionRequestById(id);

		Contract c = new Contract();
		c.fullContractText(a);
		c.setAdoption(a);

		return c;
	}
	
	

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Contract> list() {
		List<Contract> contracts = null;
		contracts = pejb.getAllContracts();
		return contracts;
	}

	@POST
	@PermitAll
	@Consumes(MediaType.APPLICATION_JSON)
	public Contract createContract(Contract contract) {
		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(Contract.class);

			Marshaller marshaller = jc.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.setProperty("eclipselink.media-type", "application/json");
			marshaller.marshal(contract, System.out);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (contract.getSubmission() == null) {
			throw new ValueNotFoundException(
					"Submission ID is required to create and save a contract");
		}
		return pejb.createContract(contract);

	}
}
