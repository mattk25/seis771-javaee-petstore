package com.SEIS771.proj.rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.eclipse.persistence.jaxb.rs.MOXyJsonProvider;
import org.glassfish.jersey.server.filter.RolesAllowedDynamicFeature;

@ApplicationPath("/api")
public class ApplicationConfig extends Application {
	public Set<Class<?>> getClasses() {
		HashSet<Class<?>> set = new HashSet<Class<?>>(2);
		set.add(AnimalService.class);
		set.add(PersonService.class);
		set.add(CareRecordService.class);
		set.add(SubmissionService.class);
		set.add(AdoptionRequestService.class);
		set.add(ContractService.class);
		set.add(FacilityService.class);
		set.add(MOXyJsonProvider.class);
		set.add(SecurityInterceptor.class);
		return set;
		//return new HashSet<Class<?>>(Arrays.asList(MyResource.class));
    }
}
