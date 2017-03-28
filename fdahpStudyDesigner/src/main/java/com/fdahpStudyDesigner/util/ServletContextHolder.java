/**
 * 
 */
package com.fdahpStudyDesigner.util;


import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;

/**
 * Created by luog on 11/03/14.
 */
@Controller
public class ServletContextHolder implements ServletContextAware {
	@Autowired
    private static ServletContext servletContext;
	
	@Autowired
    private static Properties prop;
	
    @Override
    public void setServletContext(ServletContext servletContext) {
        ServletContextHolder.servletContext = servletContext;
        new fdahpStudyDesignerUtil();
    }

    public static ServletContext getServletContext() {
        return servletContext;
    }

	public  Properties getProp() {
		return prop;
	}

	public  void setProp(Properties prop) {
		ServletContextHolder.prop = prop;
	}
}
