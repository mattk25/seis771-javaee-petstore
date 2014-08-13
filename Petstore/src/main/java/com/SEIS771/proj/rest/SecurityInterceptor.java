package com.SEIS771.proj.rest;

import java.lang.annotation.Annotation;

import javax.annotation.Priority;
import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.Priorities;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.server.ExtendedUriInfo;

import com.SEIS771.proj.EJB.PetstoreEJB;
import com.SEIS771.proj.model.UserGroup;

import edu.emory.mathcs.backport.java.util.Arrays;

import java.lang.reflect.Method;

@Stateless
@Provider
@Priority(Priorities.AUTHENTICATION)

/** The SecurityInterceptor attemps to always authenticate the request or resource
 * 
 * @author Samuel Sokeye
 *
 */
public class SecurityInterceptor implements
		javax.ws.rs.container.ContainerRequestFilter {

	@Override
	public void filter(final ContainerRequestContext rc) {

		final ExtendedUriInfo extUri = (ExtendedUriInfo) rc.getUriInfo();
		Method m = extUri.getMatchedResourceMethod().getInvocable().getHandlingMethod();

		try {
			final PetstoreEJB pejb = (PetstoreEJB) new InitialContext().lookup("java:global/Petstore/PetstoreEJB");
			final String auth = rc.getHeaderString("Authorization");
			final String[] lap;
			
			if (auth != null) {
				lap = BasicAuth.decode(auth);

				// If login or password fail
				if (lap == null || lap.length != 2) {
					throw new WebApplicationException(Status.UNAUTHORIZED);
				}
			} else {
				lap = new String[2];
				lap[0] = "unauthorized";
				lap[1] = "na";
			}
			
			
			if (m.isAnnotationPresent(PermitAll.class)) {

			} else if (m.isAnnotationPresent(DenyAll.class)) {
				throw new WebApplicationException(Status.FORBIDDEN);
			} else if (m.isAnnotationPresent(RolesAllowed.class)) {
				RolesAllowed ra = m.getAnnotation(RolesAllowed.class);
				String[] roles = ra.value();

				UserGroup ug = pejb.getUserGroupByName(lap[0]);
				if (ug == null) {
					throw new WebApplicationException(Status.FORBIDDEN);
				} else {
					if (!Arrays.asList(roles).contains(ug.getName())) {
						throw new WebApplicationException(Status.FORBIDDEN);
					}
				}

				authenticate(pejb, lap[0], lap[1]);
			} else {
				authenticate(pejb, lap[0], lap[1]);
			}
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void authenticate(PetstoreEJB pejb, String user, String pw) {
		// TODO Auto-generated method stub
		if(user.equals("unauthorized")) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
		boolean authorize = pejb.authenticate(user, pw);
		if (!authorize) {
			throw new WebApplicationException(Status.UNAUTHORIZED);
		}
	}

}