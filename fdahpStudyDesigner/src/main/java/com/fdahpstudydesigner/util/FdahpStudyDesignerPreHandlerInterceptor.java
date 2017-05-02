package com.fdahpstudydesigner.util;

import java.io.IOException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.fdahpstudydesigner.dao.AuditLogDAO;
import com.fdahpstudydesigner.service.LoginServiceImpl;

/**
 * @author 
 *
 */
public class FdahpStudyDesignerPreHandlerInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = Logger.getLogger(FdahpStudyDesignerPreHandlerInterceptor.class);
	
	private LoginServiceImpl loginService;
	
	@Autowired
	private AuditLogDAO auditLogDAO;
	
	/* Setter Injection */
	@Autowired
	public void setLoginService(LoginServiceImpl loginService) {
		this.loginService = loginService;
	}
	
	/**
	 * @param request
	 * @param response
	 * @param handler
	 * @return boolean
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		logger.info("FdahpStudyDesignerPreHandlerInterceptor - preHandle() - Starts");
		SessionObject session = null;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String defaultURL = (String)propMap.get("action.default.redirect.url");
		final String excludeActions = propMap.get("interceptor.urls");
		String uri = request.getRequestURI();
		boolean flag = false;
		String passwordExpiredDateTime = null;
		int passwordExpirationInDay =  Integer.parseInt(propMap.get("password.expiration.in.day"));
		String forceChangePasswordurl = (String)propMap.get("action.force.changepassword.url");
		String updatePassword = (String)propMap.get("action.force.updatepassword.url");
		boolean isUserEanbled = true;
		String sessionOutUrl = (String)propMap.get("action.logout.url");
		String inavtiveMsg = (String)propMap.get("user.inactive.msg");
		String actionLoginbackUrl = propMap.get("action.loginback.url");
		String timeoutMsg = propMap.get("user.session.timeout");
		String activity = "";
		String activityDetail = "";
		try {
			if(null != request.getSession()) {
				session = (SessionObject)request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			}
			// Allow some of the URL 
			String list[] = excludeActions.split(",");
			for (int i = 0; i < list.length; i++) {
				if (uri.endsWith(list[i].trim())) {
					flag =  true;
				}
			}
			/*if (!flag) {
				if (null == session){
					
				} 
			}*/
			
			int customSessionExpiredErrorCode = 901;
			boolean ajax = "XMLHttpRequest".equals(request.getHeader("X-Requested-With"));
			if (!flag) {
				if (null == session){
					if(ajax){
						response.sendError(customSessionExpiredErrorCode);
						logger.info("FdahpStudyDesignerPreHandlerInterceptor -preHandle(): "+uri + "");
						return false;
					}
					if(uri.contains(actionLoginbackUrl)){
						request.getSession(false).setAttribute("loginBackUrl", request.getScheme() + "://" +   // "http" + "://
					             request.getServerName() +       // "myhost"
					             ":" +                           // ":"
					             request.getServerPort() +       // "8080"
					             request.getRequestURI() +       // "/people"
					             "?" +                           // "?"
					             request.getQueryString());
					}else{
						activity = "User logout";
						activityDetail = "User is succussfully loged out.";
						auditLogDAO.saveToAuditLog(null, null, session, activity, activityDetail ,"FdahpStudyDesignerPreHandlerInterceptor - preHandle()");
					}
					response.sendRedirect(defaultURL);
					logger.info("FdahpStudyDesignerPreHandlerInterceptor -preHandle(): " + uri);
					return false;
				}else if(!ajax && !uri.contains(sessionOutUrl)){
					//Checking for password Expired Date Time from current Session
//				passwordExpiredDateTime = session.getPasswordExpairdedDateTime();
//				if(StringUtils.isNotBlank(passwordExpiredDateTime) && FdahpStudyDesignerUtil.addDaysToDate(FdahpStudyDesignerConstants.DB_SDF_DATE_TIME.parse(passwordExpiredDateTime), passwordExpirationInDay).before(FdahpStudyDesignerConstants.DB_SDF_DATE_TIME.parse(FdahpStudyDesignerUtil.getCurrentDateTime())) && !uri.contains(forceChangePasswordurl) && !uri.contains(updatePassword)){
//					response.sendRedirect(forceChangePasswordurl);
//					logger.info("FdahpStudyDesignerPreHandlerInterceptor -preHandle(): force change password");
//				}
					//Checking for force logout for current user
					Boolean forceLogout = loginService.isFrocelyLogOutUser(session);
					if(forceLogout){
						response.sendRedirect(sessionOutUrl+"?msg="+timeoutMsg);
						logger.info("FdahpStudyDesignerPreHandlerInterceptor -preHandle(): force logout");
						return false;
					}
				}
			} else if (uri.contains(defaultURL) && null != session) {
				response.sendRedirect(session.getCurrentHomeUrl());
			}
		} catch (IOException e) {
			logger.error("FdahpStudyDesignerPreHandlerInterceptor - preHandle()", e);
		}
		logger.info("FdahpStudyDesignerPreHandlerInterceptor - End Point: preHandle() - "+" : "+FdahpStudyDesignerUtil.getCurrentDateTime()+ " uri"+uri);
		return true;
}

}