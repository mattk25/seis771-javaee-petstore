package com.SEIS771.proj.rest;

import java.util.List;

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

import com.SEIS771.proj.EJB.PetstoreEJB;
import com.SEIS771.proj.exception.ValueNotFoundException;
import com.SEIS771.proj.model.Animal;
import com.SEIS771.proj.model.Person;
import com.SEIS771.proj.model.Submission;
import com.SEIS771.proj.utils.EmailUtility;

@Path("/submissions")
public class SubmissionService {
	@EJB
	PetstoreEJB pejb;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Submission createSubmission(Submission submission) throws Exception {

		Animal a = submission.getAnimal();
		a.setSubmission(null);

		return pejb.createSubmission(submission);

	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/submission/{sid}/facility/{fid}")
	public Submission assignSubmissionToFacility(Submission submission, @PathParam("fid") int fid) throws Exception {
		

		Submission s = pejb.assignFacilityToSubmission(submission);
		return s;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Submission> list(@Context UriInfo info,
			@QueryParam("status") String status,
			@QueryParam("offset") int offset, @QueryParam("page") int page) {
		System.out.println(page);

		List<Submission> subs = null;
		if (status == null) {
			subs = pejb.getAllSubmission();
		} else {
			subs = pejb.getAvailableSubmissionsByStatus(status, offset, page);
		}
		return subs;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("user/{userId}")
	public List<Submission> getUserSubmissions(
			@PathParam("userId") Integer userId) {
		return pejb.getSubmissionsByUserId(userId.intValue());
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/{id}")
	public Submission getSubmission(@PathParam("id") Integer submissionId)
			throws Exception {
		Submission submission = pejb.getSubmissionById(submissionId);
		return submission;
	}
	
	private String buildRejectionMessage(Submission s) {
		return "Hi " 
		        + s.getPerson().getFirstName()
				+ "\n Your submission <a href='http://localhost:8080/Petstore/#/submissions/submission/"
				+ s.getId() + "'> " 
				+ s.getId() 
				+ "</a> has been rejected"
				+ "\n Reason: " 
				+ s.getStaffComments();
		
	}
	
	private String buildApprovalMessage(Submission s) {
		return "Hi "
				+ s.getPerson().getFirstName()
				+ "\n Your submission <a href='http://localhost:8080/Petstore/#/submissions/submission/"
				+ s.getId()
				+ "'> "
				+ s.getId()
				+ "</a> has been approved"
				+ "\n Please login to <a href='http://localhost:8080/Petstore/#/contracts/submission/"
				+ s.getId() + "'> here </a>"
				+ "\n to schedule a drop off time";
		
	}
	
	private String buildRejectionSubject(Submission s){
		return "Submission " + s.getId() + " for " + s.getAnimal().getName() + " rejected";
	}
	
	private String buildApprovalSubject(Submission s){
		return "Submission " + s.getId() + " for " + s.getAnimal().getName() + " approved";
		
	}
	
	private void updateAndSendEmail(Submission submission, boolean approve) throws Exception {
		Submission s = pejb.updateSubmission(submission);
		Person p = s.getPerson();
		String subject, message;
		if(approve){
			subject = buildApprovalSubject(s);
			message = buildApprovalMessage(s);
		}
		else {
			subject = buildRejectionSubject(s);
			message = buildRejectionMessage(s);
		}
		EmailUtility.sendEmail(p.getEmail(), subject, message);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}/approve")
	public void sendApprovalEmail(Submission submission) throws Exception {
		updateAndSendEmail(submission, true);
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/{id}/reject")
	public void sendRejectionEmail(Submission submission) throws Exception {
		updateAndSendEmail(submission, false);
	}
}
