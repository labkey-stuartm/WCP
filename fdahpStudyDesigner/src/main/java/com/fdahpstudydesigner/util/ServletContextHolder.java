package com.fdahpstudydesigner.util;


import java.util.TimeZone;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * @author Vivek
 */

public class ServletContextHolder implements ServletContextListener {

	private static ServletContext servletContext;

	/**
	 * @author Vivek
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContextHolder.setServletContext(sce.getServletContext());
		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		
	}

	/**
	 * @return the servletContext
	 */
	public static ServletContext getServletContext() {
		return servletContext;
	}

	/**
	 * @param servletContext the servletContext to set
	 */
	public static void setServletContext(ServletContext servletContext) {
		ServletContextHolder.servletContext = servletContext;
	}
	
	

}
