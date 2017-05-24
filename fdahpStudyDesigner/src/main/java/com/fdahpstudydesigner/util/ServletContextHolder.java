package com.fdahpstudydesigner.util;


import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.fdahpstudydesigner.scheduler.FDASchedulerService;

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
//		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
	}

	/**
	 * @author Vivek
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
//		Logger logger = Logger.getLogger(FDASchedulerService.class
//				.getName());
//		logger.info("ServletContextHolder - contextDestroyed - Ends");
//		List<String> emailAddresses = Arrays.asList(FdahpStudyDesignerUtil
//				.getAppProperties().get("email.address.audit.failure")
//				.split("\\s*,\\s*"));
//		EmailNotification.sendEmailNotificationToMany(
//				"mail.server.shutdown.subject",
//				(String) FdahpStudyDesignerUtil.getAppProperties().get(
//						"mail.server.shutdown.content"), emailAddresses, null,
//				null);
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
