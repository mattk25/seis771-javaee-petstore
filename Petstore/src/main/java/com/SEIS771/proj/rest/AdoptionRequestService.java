package com.SEIS771.proj.rest;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.TreeMap;

import javax.ws.rs.Consumes;
import javax.ws.rs.ForbiddenException;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ejb.EJB;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

import com.SEIS771.proj.EJB.PetstoreEJB;
import com.SEIS771.proj.exception.ValueNotFoundException;
import com.SEIS771.proj.model.AdoptionRequest;
import com.SEIS771.proj.model.Animal;
import com.SEIS771.proj.model.Person;
import com.SEIS771.proj.utils.EmailUtility;

import edu.emory.mathcs.backport.java.util.Collections;

@Path("/adoptionRequest")
public class AdoptionRequestService {

	@EJB
	PetstoreEJB pejb;
	
	@POST 
	@Consumes(MediaType.APPLICATION_JSON)
	public AdoptionRequest createAdoptionRequest(AdoptionRequest adoptionRequest) throws Exception  {
		Person requester = adoptionRequest.getRequester();
		if(requester == null){
			throw new ValueNotFoundException("Requestor ID required");
		}
		// if this is an adoption for specific animal , validate user has not already created a request for same animal
		Animal requestedAnimal = adoptionRequest.getRequestedAnimal();
		if(requestedAnimal != null && pejb.createdRequestForAnimal(requester, requestedAnimal)){
			throw new ForbiddenException("You have already created a request to adopt this animal");
		}
		AdoptionRequest a = pejb.createAdoptionRequest(adoptionRequest);

		return a;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public AdoptionRequest getAdoptionRequestById(@PathParam("id") int id) 
		throws Exception {
			AdoptionRequest adoptionRequest = pejb.getAdoptionRequestById(id);
		return adoptionRequest;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<AdoptionRequest> getAllAdoptionRequests() {
		List<AdoptionRequest> data = pejb.getAllAdoptionRequests();
		return data;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/adoption/{adoptionId}/bestMatches")
	public  Map getFiveBestAdoptionMatches(@PathParam("adoptionId") int adoptionId) {
		AdoptionRequest ar = pejb.getAdoptionRequestById(adoptionId);  //this  adoption request is compared to
		List<Animal> data = pejb.getAdoptableAnimals();                // these animals
		int score = 0;
		Map rankedAnimals = new HashMap();
		
		for (Iterator<Animal> iter = data.iterator(); iter.hasNext();) {
			if (iter.next().getSubmission().getStatus() == "available") {
				if (ar.getAnimalAge() == iter.next().getAge()) {score = score +1;}
				if (ar.getColor() == iter.next().getColor()) {score = score +1;} 
				if (ar.getSpecies() == iter.next().getSpecie().getName()) {score = score +1;} 
				if (ar.getBreed() == iter.next().getBreed().getName()) {score = score +1;}  
				if (ar.getSex() == iter.next().getSex()) {score = score +1;}   
				if (ar.getIsNeutered() == iter.next().getMedicalRecord().getIsneutered()) {score = score +1;} 
			}
			rankedAnimals.put(score, iter );
			score = 0;
		}
		// now put this into a reverse ordered treeMap so we can get the top 5 scores
		Map<Integer, Animal > treeMap = new TreeMap(Collections.reverseOrder());
		treeMap.putAll(rankedAnimals);
		int index = 0;
		Iterator<Integer> iter2= treeMap.keySet().iterator();
		while (iter2.hasNext()) {
			if(index>4) {iter2.remove();}        // just remove anything past the first 5 item
		}  
		return treeMap ; //rankedAnimals;   // data;
	}
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/user/{userId}")
	public List<AdoptionRequest> getUserAdoptionRequests(@PathParam("userId") int userId) throws Exception {
		return pejb.getUserAdoptionRequests(userId);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}/match")
	public void sendApprovalEmail(AdoptionRequest adoptionRequest) throws Exception {
		AdoptionRequest ar = pejb.updateAdoptionRequest (adoptionRequest);
		Person p = ar.getRequester();
		
		String subject, message;
		subject = "Adoption Request " + ar.getId() + " approved";
		message = "Hi "
				+ ar.getRequester().getFirstName()
				+ "\n Your request <a href='http://localhost:8080/Petstore/#/adoptionRequest/"
				+ ar.getId()
				+ "'> "
				+ "</a> has been approved"
				+ "\n Please login to <a href='http://localhost:8080/Petstore/#/adoptionRequest/"
				+ ar.getId() + "'> here </a>"
				+ "\n to schedule a pickup time";
		EmailUtility.sendEmail(p.getEmail(), subject, message);
	}
}
