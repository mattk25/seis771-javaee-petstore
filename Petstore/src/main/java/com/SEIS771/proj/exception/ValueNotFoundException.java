package com.SEIS771.proj.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

public class ValueNotFoundException extends WebApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValueNotFoundException(String message) {
		super(Response.status(Response.Status.BAD_REQUEST).entity(message).type("text/plain").build());
	}
}
