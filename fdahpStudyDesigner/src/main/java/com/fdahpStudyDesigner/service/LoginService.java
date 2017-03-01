package com.fdahpStudyDesigner.service;

import javax.servlet.http.HttpServletRequest;

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.util.SessionObject;



/**
 * @author Vivek
 *
 */
public interface LoginService {
	
	public String sendPasswordResetLinkToMail(HttpServletRequest request, String email, String type)  throws Exception;
	
	public String changePassword(Integer userId, String newPassword, String oldPassword) throws Exception;
	
	public UserBO checkSecurityToken(String securityToken) throws Exception;
	
	public String authAndAddPassword(String securityToken, String accessCode, String password,UserBO userBO) throws Exception;
	
	public Boolean isUserEnabled(SessionObject sessionObject) throws Exception;
	
	public Boolean isFrocelyLogOutUser(SessionObject sessionObject) throws Exception;

}
