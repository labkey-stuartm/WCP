package com.fdahpStudyDesigner.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * @author Vivek
 */
@Controller
public class ServletContextHolder implements ServletContextAware {
	@Autowired
	private static ServletContext servletContext;

	
	/**
	 * @author Vivek
	 * @see org.springframework.web.context.ServletContextAware#setServletContext(javax.servlet.ServletContext)
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		ServletContextHolder.servletContext = servletContext;
		new fdahpStudyDesignerUtil();
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}
}
