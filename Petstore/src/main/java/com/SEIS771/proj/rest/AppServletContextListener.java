package com.SEIS771.proj.rest;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.SEIS771.proj.EJB.PetstoreEJB;

/** The concept here is that we want to create some default data before the web
 * application is actually started. This is the reason for implementing the ServletContextListener
 * @author Samuel Sokeye
 *
 */
public class AppServletContextListener implements ServletContextListener {
	private static final String PERSISTENCE_UNIT_NAME = "petstore";
	private static EntityManagerFactory factory;
	
	@EJB
    PetstoreEJB pejb;
	
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		pejb.createDefaultData();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		// TODO Auto-generated method stub
		System.out.println("Listener DESTROYED!!!!!!!");
	}
}
