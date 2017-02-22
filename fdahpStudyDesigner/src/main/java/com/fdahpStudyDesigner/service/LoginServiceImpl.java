package com.fdahpStudyDesigner.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.bo.UserPasswordHistory;
import com.fdahpStudyDesigner.dao.LoginDAOImpl;
import com.fdahpStudyDesigner.util.EmailNotification;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

/**
 * @author BTC
 *
 */
@Service
public class LoginServiceImpl implements LoginService, UserDetailsService {
	
	private static Logger logger = Logger.getLogger(LoginServiceImpl.class.getName());
	public LoginServiceImpl() {
	}
	private LoginDAOImpl loginDAO;

	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;

	/**
	 * Setting DI
	 * @param loginDAO
	 */
	@Autowired
	public void setLoginDAO(LoginDAOImpl loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	/** 
	 * Send the user password to user email
	 * @author BTC
	 * 
	 * @param HttpServletRequest
	 * @param request , {@link HttpServletRequest}
	 * @param email , The Email id of user
	 * @param type , the type of user
	 * @return {@link String} , the status AcuityLinkConstants.SUCCESS or AcuityLinkConstants.FAILURE
	 */
	@Override
	public String sendPasswordResetLinkToMail(HttpServletRequest request, String email, String type)  throws Exception {
		logger.info("LoginServiceImpl - sendPasswordResetLinkToMail() - Starts");
		String passwordResetToken = "";
		String message = fdahpStudyDesignerConstants.FAILURE;
		boolean flag = false;
		UserBO userdetails = null;
		String accessCode = "";
		Map<String, String> keyValueForSubject = null;
		String dynamicContent = "";
		String acceptLinkMail = "";
		int passwordResetLinkExpirationInDay =  Integer.parseInt(propMap.get("password.resetLink.expiration.in.hour"));
		String customerCareMail = "";
		String contact = "";
		try {
			passwordResetToken = RandomStringUtils.randomAlphanumeric(10);
			accessCode = RandomStringUtils.randomAlphanumeric(6);
			if(!StringUtils.isEmpty(passwordResetToken)){
				userdetails = loginDAO.getValidUserByEmail(email);
				if(null != userdetails){
					
					userdetails.setSecurityToken(passwordResetToken);
					userdetails.setAccessCode(accessCode);
					userdetails.setTokenUsed(false);
					if(userdetails.isEnabled()){
						userdetails.setTokenExpiryDate(fdahpStudyDesignerUtil.addHours(fdahpStudyDesignerUtil.getCurrentDateTime(), passwordResetLinkExpirationInDay));
					} 
					message = loginDAO.updateUser(userdetails);
					if(fdahpStudyDesignerConstants.SUCCESS.equals(message)){
						acceptLinkMail = propMap.get("acceptLinkMail");
						keyValueForSubject = new HashMap<String, String>();
						keyValueForSubject.put("$firstName", userdetails.getFirstName());
						keyValueForSubject.put("$lastName", userdetails.getLastName());
						keyValueForSubject.put("$accessCode", accessCode);
						keyValueForSubject.put("$passwordResetLink", acceptLinkMail+passwordResetToken);
						customerCareMail = propMap.get("email.address.customer.service");
						keyValueForSubject.put("$customerCareMail", customerCareMail);
						contact = propMap.get("phone.number.to");
						keyValueForSubject.put("$contact", contact);
						dynamicContent = fdahpStudyDesignerUtil.genarateEmailContent("passwordResetLinkContent", keyValueForSubject);
						flag = EmailNotification.sendEmailNotification("passwordResetLinkSubject", dynamicContent, email, null, null);
						if(flag){
							message = fdahpStudyDesignerConstants.SUCCESS;
						}
					}
				}
				}
		} catch (Exception e) {
			logger.error("LoginServiceImpl - sendPasswordResetLinkToMail() - ERROR " , e);
		}
		logger.info("LoginServiceImpl - sendPasswordResetLinkToMail() - Ends");
		return message;
	}
	
	/** 
	 * Change the user password
	 * @author BTC
	 * 
	 * @param userId , The id of the user
	 * @param newPassword , The new password added by user
	 * @return {@link String} , the status AcuityLinkConstants.SUCCESS or AcuityLinkConstants.FAILURE
	 */
	@Override
	public String changePassword(Integer userId, String newPassword, String oldPassword) throws Exception{
		logger.info("LoginServiceImpl - changePassword() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		String oldPasswordError = propMap.get("old.password.error.msg");
		String passwordCount = propMap.get("password.history.count");
		List<UserPasswordHistory> passwordHistories = null;
		Boolean isValidPassword = true;
		 try {
			 passwordHistories = loginDAO.getPasswordHistory(userId);
				if(passwordHistories != null && !passwordHistories.isEmpty()){
					for (UserPasswordHistory userPasswordHistory : passwordHistories) {
						if(fdahpStudyDesignerUtil.compairEncryptedPassword(userPasswordHistory.getUserPassword(), newPassword)){
							isValidPassword = false;
							break;
						}
					}
				}
				if(isValidPassword){
					message = loginDAO.changePassword(userId, newPassword, oldPassword);
					if(message.equals(fdahpStudyDesignerConstants.SUCCESS)){
						loginDAO.updatePasswordHistory(userId, fdahpStudyDesignerUtil.getEncryptedPassword(newPassword));
					}
				}else {
					message = oldPasswordError.replace("$countPass", passwordCount);
				}
		} catch (Exception e) {
			logger.error("LoginServiceImpl - changePassword() - ERROR " , e);
		}
		 logger.info("LoginServiceImpl - changePassword() - Ends");
		 return message;
	}

	/** 
	 * Get the spring security user details by user email  
	 * @author Vivek
	 * 
	 * @param userEmail , the user email id
	 * @return {@link UserDetails} , the spring security user details
	 */
	@Override
	public UserDetails loadUserByUsername(String userEmail) throws UsernameNotFoundException {
		UserBO user = loginDAO.getValidUserByEmail(userEmail);
		
		List<GrantedAuthority> authorities =
                fdahpStudyDesignerUtil.buildUserAuthority(user.getPermissions());

		return fdahpStudyDesignerUtil.buildUserForAuthentication(user, authorities);
	}
	/** 
	 * Validate the security token for forgot password link before check
	 * @author Vivek
	 *  
	 * @param  securityToken , the security token of the forgot password link
	 * @return {@link Boolean} , isValid 
	 */
	@Override
	public UserBO checkSecurityToken(String securityToken) throws Exception {
		UserBO userBO =null;
		logger.info("LoginServiceImpl - checkSecurityToken() - Starts");
		Date securityTokenExpiredDate= null;
		UserBO chkBO = null;
		try {
			userBO = loginDAO.getUserBySecurityToken(securityToken);
			if(null != userBO && !userBO.getTokenUsed()){
				if(userBO.isEnabled()){
					securityTokenExpiredDate = fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.parse(userBO.getTokenExpiryDate());
					if(securityTokenExpiredDate.after(fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.parse(fdahpStudyDesignerUtil.getCurrentDateTime()))){
						chkBO = userBO;
					}
				} else {
					chkBO = userBO;
				}
			}
		} catch (Exception e) {
			logger.error("LoginServiceImpl - checkSecurityToken() - ERROR " , e);
		}
		logger.info("LoginServiceImpl - checkSecurityToken() - Ends");
		return chkBO;
	}
	
	/** 
	 * Validate the security token, access code and change the password
	 * @author BTC
	 * 
	 * @param securityToken , the security token of the forgot password link
	 * @param accessCode , the access code from the forget password email
	 * @param password , the new password added by user
	 * @return {@link Boolean} , isValid 
	 */
	@Override
	public String authAndAddPassword(String securityToken, String accessCode,
			String password) throws Exception {
		UserBO userBO =null;
		logger.info("LoginServiceImpl - checkSecurityToken() - Starts");
		boolean isValid = false;
		boolean isIntialPasswordSetUp = false;
		Map<String, String> keyValueForSubject = null;
		String dynamicContent = "";
		List<Integer> userIds = null;
		String encodedUrl = "";
		String result = fdahpStudyDesignerConstants.FAILURE;
		String invalidAccessCodeError = propMap.get("invalid.access.code.error.msg");
		String oldPasswordError = propMap.get("old.password.error.msg");
		String passwordCount = propMap.get("password.history.count");
		List<UserPasswordHistory> passwordHistories = null;
		Boolean isValidPassword = true;
		try {
			//if(password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!\"#$%&'()*+,-.:;<=>?@[\\]^_`{|}~])[A-Za-z\\d!\"#$%&'()*+,-.:;<=>?@[\\]^_`{|}~]{7,13}")){
				userBO = loginDAO.getUserBySecurityToken(securityToken);
				if(null != userBO){
					if(StringUtils.isBlank(userBO.getUserPassword())){
						isIntialPasswordSetUp = true;
					}
					if(userBO.getAccessCode().equals(accessCode)){
						passwordHistories = loginDAO.getPasswordHistory(userBO.getUserId());
						if(passwordHistories != null && !passwordHistories.isEmpty()){
							for (UserPasswordHistory userPasswordHistory : passwordHistories) {
								if(fdahpStudyDesignerUtil.compairEncryptedPassword(userPasswordHistory.getUserPassword(), password)){
									isValidPassword = false;
									break;
								}
							}
						}
						if(isValidPassword){
							userBO.setUserPassword(fdahpStudyDesignerUtil.getEncryptedPassword(password));
							userBO.setTokenUsed(true);
							userBO.setEnabled(true);
							userBO.setAccountNonExpired(true);
							userBO.setAccountNonLocked(true);
							userBO.setCredentialsNonExpired(true);
							userBO.setPasswordExpairdedDateTime(fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.format(new Date()));
							result = loginDAO.updateUser(userBO);
							if(result.equals(fdahpStudyDesignerConstants.SUCCESS)){
								loginDAO.updatePasswordHistory(userBO.getUserId(), userBO.getUserPassword());
								isValid = true;
							}
						} else {
							result = oldPasswordError.replace("$countPass", passwordCount);
						}
					} else {
						result = invalidAccessCodeError;
					}
					if(isIntialPasswordSetUp && isValid){
						List<String> cc = new ArrayList<String>();
						cc.add(propMap.get("email.address.cc"));
						userBO = loginDAO.getValidUserByEmail(userBO.getUserEmail());
						//encodedUrl = fdahpStudyDesignerUtil.getEncodedStringByBase64(propMap.get("admin.to.view.asp.viewProfile.page")+userBO.getAspHiId()+"&"+fdahpStudyDesignerConstants.REDIRECT_SESSION_PARAM_NAME+fdahpStudyDesignerConstants.DEFAULT+"&chkRefreshflag=y");
						keyValueForSubject = new HashMap<String, String>();
						dynamicContent = fdahpStudyDesignerUtil.genarateEmailContent("newASPInitialPasswordSetupContent", keyValueForSubject);
						EmailNotification.sendEmailNotification("newASPInitialPasswordSetupSubject", dynamicContent, propMap.get("email.address.to"), cc, null);
						//userIds = loginDAO.acuityLinkAdminIdsOnPermission(AcuityLinkConstants.ROLE_ADMIN_ACUITY_MANAGE_ASP); 
					}
				}
			//}
		} catch (Exception e) {
			logger.error("LoginServiceImpl - checkSecurityToken() - ERROR " , e);
		}
		logger.info("LoginServiceImpl - checkSecurityToken() - Ends");
		return result;
	}
	
	/** 
	 * Check the current session user is status with respect to it super user
	 * @author BTC
	 * 
	 * @param sessionObject , {@link SessionObject}
	 * @return {@link Boolean} , isValid 
	 */
	@Override
	public Boolean isUserEnabled(SessionObject sessionObject) {
		logger.info("LoginServiceImpl - isUserEnabled() - Starts");
		Boolean isUserEnabled = true;
		try {
			if(sessionObject.isSuperAdmin()){
				isUserEnabled  = loginDAO.isUserEnabled(sessionObject.getUserId());
			} else if(!sessionObject.isSuperAdmin() && sessionObject.getSuperAdminId() != null){
				if(!(loginDAO.isUserEnabled(sessionObject.getUserId())) || !(loginDAO.isUserEnabled(sessionObject.getSuperAdminId()))){
					isUserEnabled = false;
				}
			}
		} catch (Exception e) {
			logger.error("LoginServiceImpl - isUserEnabled() - ERROR " , e);
		}
		logger.info("LoginServiceImpl - isUserEnabled() - Ends");
		return isUserEnabled;
	}

	@Override
	public Boolean isFrocelyLogOutUser(SessionObject sessionObject)
			throws Exception {
		logger.info("LoginServiceImpl - isFrocelyLogOutUser() - Starts");
		Boolean isFrocelyLogOut = false;
		try {
			isFrocelyLogOut  = loginDAO.isFrocelyLogOutUser(sessionObject.getUserId());
		} catch (Exception e) {
			logger.error("LoginServiceImpl - isFrocelyLogOutUser() - ERROR " , e);
		}
		logger.info("LoginServiceImpl - isFrocelyLogOutUser() - Ends");
		return isFrocelyLogOut;
	}

	
	
}
