package com.fdahpStudyDesigner.util;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.dao.LoginDAOImpl;




/**
 * @author Vivek
 * @see {@link SimpleUrlAuthenticationSuccessHandler}
 */
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	private LoginDAOImpl loginDAO;

	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	@Autowired
	public void setLoginDAO(LoginDAOImpl loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.security.web.authentication.AbstractAuthenticationTargetUrlRequestHandler#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
	 */
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        UserBO userdetails = null;
		SessionObject sesObj = null;
		String projectName = propMap.get("project.name");
		   userdetails = loginDAO.getValidUserByEmail(authentication.getName());
		    sesObj = new SessionObject();
		    sesObj.setUserId(userdetails.getUserId());
		    sesObj.setFirstName(userdetails.getFirstName());
		    sesObj.setLastName(userdetails.getLastName());
		    sesObj.setLoginStatus(true);
		    sesObj.setCurrentHomeUrl("/"+projectName+targetUrl);
		    sesObj.setEmail(userdetails.getUserEmail());
		    sesObj.setUserPermissions(fdahpStudyDesignerUtil.getSessionUserRole(request));
		    sesObj.setPasswordExpairdedDateTime(userdetails.getPasswordExpairdedDateTime());
		    sesObj.setCreatedDate(userdetails.getCreatedOn());
		        if (response.isCommitted()) {
		            System.out.println("Can't redirect");
		            return;
		        }
		        request.getSession().setAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT, sesObj);
		        if(null != request.getSession(false).getAttribute("sucMsg")){
				   request.getSession(false).removeAttribute("sucMsg");
				}
				if(null != request.getSession(false).getAttribute("errMsg")){
				   request.getSession(false).removeAttribute("errMsg");
				}
				if(StringUtils.isNotBlank(request.getParameter("loginBackUrl"))){
				   String[] uri = request.getParameter("loginBackUrl").split(projectName);
				   targetUrl = uri[1];
				}
		        redirectStrategy.sendRedirect(request, response, targetUrl);
		    }
	
	/**
	 * Provide landing page URI as per User Role
	 * @author Vivek
	 * 
	 * @param authentication , {@link Authentication}
	 * @return {@link String} , the URI
	 */
	protected String determineTargetUrl(Authentication authentication) {
        String url = "";
        try{
        
        if (authentication!=null){
        	url = "/adminDashboard/viewDashBoard.do?action=landing";
        }else {
        	url = "/unauthorized.do";
       }
        }catch(Exception e){
        	e.printStackTrace();
        }
        return url;
    }
}
