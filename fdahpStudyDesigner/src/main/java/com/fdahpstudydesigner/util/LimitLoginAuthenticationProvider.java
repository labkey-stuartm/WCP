/**
 * 
 */
package com.fdahpstudydesigner.util;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fdahpstudydesigner.bo.UserAttemptsBo;
import com.fdahpstudydesigner.dao.LoginDAOImpl;

/**
 * @author Vivek
 * @see {@link DaoAuthenticationProvider}
 *
 */

public class LimitLoginAuthenticationProvider extends  DaoAuthenticationProvider{
	
	private static Logger logger = Logger.getLogger(LimitLoginAuthenticationProvider.class.getName());
	
	private LoginDAOImpl loginDAO;
	
	
	@Autowired
	public void setLoginDAO(LoginDAOImpl loginDAO) {
		this.loginDAO = loginDAO;
	}
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) {
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		try {
			HttpServletRequest request= null;
/*			 RequestAttributes attribs = RequestContextHolder.getRequestAttributes()
			 attribs.get*/
			ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
			request = attributes.getRequest();
			//Authentication auth = super.authenticate(authentication);
			UsernamePasswordAuthenticationToken token  = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), authentication.getCredentials().toString().replaceAll(request.getParameter("_csrf"), ""), new ArrayList<GrantedAuthority>());
			//if reach here, means login success, else an exception will be thrown
			//reset the user_attempts
			Authentication auth = super.authenticate(token);
			loginDAO.resetFailAttempts(authentication.getName());
			return auth;

		  } catch (BadCredentialsException e) {

			//invalid login, update to user_attempts
			  loginDAO.updateFailAttempts(authentication.getName());
			throw e;

		  } catch (LockedException e){

			logger.error("LimitLoginAuthenticationProvider - authenticate - ERROR - this user is locked! ", e);
			String error = "";
			UserAttemptsBo userAttempts =
					loginDAO.getUserAttempts(authentication.getName());

			if(userAttempts!=null){
				//String lastAttempts = FdahpStudyDesignerUtil.getFormattedDate(userAttempts.getLastModified(), FdahpStudyDesignerConstants.GET_DATE_TIME, FdahpStudyDesignerConstants.UI_SDF_DATE_TIME);
				error = propMap.get("user.lock.msg");
			}else{
				error = e.getMessage();
			}

		  throw new LockedException(error);
		}
	}
	
}
