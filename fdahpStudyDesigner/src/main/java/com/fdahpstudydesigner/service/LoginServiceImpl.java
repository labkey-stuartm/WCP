package com.fdahpstudydesigner.service;

import java.text.SimpleDateFormat;
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

import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.bo.UserPasswordHistory;
import com.fdahpstudydesigner.dao.AuditLogDAO;
import com.fdahpstudydesigner.dao.LoginDAOImpl;
import com.fdahpstudydesigner.util.EmailNotification;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

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


	/**
	 * Setting DI
	 * @param loginDAO
	 */
	@Autowired
	public void setLoginDAO(LoginDAOImpl loginDAO) {
		this.loginDAO = loginDAO;
	}
	
	@Autowired
	private AuditLogDAO auditLogDAO;
	
	/** 
	 * Send the user password to user email
	 * @author BTC
	 * 
	 * @param HttpServletRequest
	 * @param request , {@link HttpServletRequest}
	 * @param email , The Email id of user
	 * @param type , the type of user
	 * @return {@link String} , the status FdahpStudyDesignerConstants.SUCCESS or FdahpStudyDesignerConstants.FAILURE
	 */
	@Override
	public String sendPasswordResetLinkToMail(HttpServletRequest request, String email, String type)  {
		logger.info("LoginServiceImpl - sendPasswordResetLinkToMail - Starts");
		@SuppressWarnings("unchecked")
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String passwordResetToken = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		boolean flag = false;
		UserBO userdetails = null;
		String accessCode = "";
		Map<String, String> keyValueForSubject = null;
		Map<String, String> keyValueForSubject2 = null;
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
						userdetails.setTokenExpiryDate(FdahpStudyDesignerUtil.addHours(FdahpStudyDesignerUtil.getCurrentDateTime(), passwordResetLinkExpirationInDay));
					} 
					if(!"USER_UPDATE".equals(type) && !"USER_EMAIL_UPDATE".equals(type)){
						message = loginDAO.updateUser(userdetails);
					}else{
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
					if(FdahpStudyDesignerConstants.SUCCESS.equals(message)){
						acceptLinkMail = propMap.get("acceptLinkMail");
						keyValueForSubject = new HashMap<String, String>();
						keyValueForSubject2 = new HashMap<String, String>();
						keyValueForSubject.put("$firstName", userdetails.getFirstName());
						keyValueForSubject2.put("$firstName", userdetails.getFirstName());
						keyValueForSubject.put("$lastName", userdetails.getLastName());
						keyValueForSubject.put("$accessCode", accessCode);
						keyValueForSubject.put("$passwordResetLink", acceptLinkMail+passwordResetToken);
						customerCareMail = propMap.get("email.address.customer.service");
						keyValueForSubject.put("$customerCareMail", customerCareMail);
						keyValueForSubject2.put("$customerCareMail", customerCareMail);
						keyValueForSubject2.put("$newUpdatedMail", userdetails.getUserEmail());
						contact = propMap.get("phone.number.to");
						keyValueForSubject.put("$contact", contact);
						if("USER".equals(type)){
							dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("passwordResetLinkForUserContent", keyValueForSubject);
							flag = EmailNotification.sendEmailNotification("passwordResetLinkForUserSubject", dynamicContent, email, null, null);
						}else if("USER_UPDATE".equals(type)){
							dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("mailForUserUpdateContent", keyValueForSubject2);
							flag = EmailNotification.sendEmailNotification("mailForUserUpdateSubject", dynamicContent, email, null, null);
						}else if("USER_EMAIL_UPDATE".equals(type)){
							dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("mailForUserEmailUpdateContent", keyValueForSubject2);
							flag = EmailNotification.sendEmailNotification("mailForUserEmailUpdateSubject", dynamicContent, email, null, null);
						}else{
							dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("passwordResetLinkContent", keyValueForSubject);
							flag = EmailNotification.sendEmailNotification("passwordResetLinkSubject", dynamicContent, email, null, null);
						}
						if(flag){
							message = FdahpStudyDesignerConstants.SUCCESS;
						}
					}
				}
				}
		} catch (Exception e) {
			logger.error("LoginServiceImpl - sendPasswordResetLinkToMail - ERROR " , e);
		}
		logger.info("LoginServiceImpl - sendPasswordResetLinkToMail - Ends");
		return message;
	}
	
	/** 
	 * Change the user password
	 * @author BTC
	 * 
	 * @param userId , The id of the user
	 * @param newPassword , The new password added by user
	 * @return {@link String} , the status FdahpStudyDesignerConstants.SUCCESS or FdahpStudyDesignerConstants.FAILURE
	 */
	@Override
	public String changePassword(Integer userId, String newPassword, String oldPassword,SessionObject sesObj){
		logger.info("LoginServiceImpl - changePassword() - Starts");
		@SuppressWarnings("unchecked")
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String message = FdahpStudyDesignerConstants.FAILURE;
		String oldPasswordError = propMap.get("old.password.error.msg");
		String passwordCount = propMap.get("password.history.count");
		List<UserPasswordHistory> passwordHistories = null;
		Boolean isValidPassword = true;
		String activity = "";
		String activityDetail = ""; 
		 try {
			 passwordHistories = loginDAO.getPasswordHistory(userId);
				if(passwordHistories != null && !passwordHistories.isEmpty()){
					for (UserPasswordHistory userPasswordHistory : passwordHistories) {
						if(FdahpStudyDesignerUtil.compairEncryptedPassword(userPasswordHistory.getUserPassword(), newPassword)){
							isValidPassword = false;
							break;
						}
					}
				}
				if(isValidPassword){
					message = loginDAO.changePassword(userId, newPassword, oldPassword);
					if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
						loginDAO.updatePasswordHistory(userId, FdahpStudyDesignerUtil.getEncryptedPassword(newPassword));
						activity = "Change password";
						activityDetail = "Admin successfully changed his password";
						auditLogDAO.saveToAuditLog(null, null, sesObj, activity, activityDetail ,"LoginDAOImpl - changePassword()");
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
                FdahpStudyDesignerUtil.buildUserAuthority(user.getPermissions());

		return FdahpStudyDesignerUtil.buildUserForAuthentication(user, authorities);
	}
	/** 
	 * Validate the security token for forgot password link before check
	 * @author Vivek
	 *  
	 * @param  securityToken , the security token of the forgot password link
	 * @return {@link Boolean} , isValid 
	 */
	@Override
	public UserBO checkSecurityToken(String securityToken) {
		UserBO userBO =null;
		logger.info("LoginServiceImpl - checkSecurityToken() - Starts");
		Date securityTokenExpiredDate= null;
		UserBO chkBO = null;
		try {
			userBO = loginDAO.getUserBySecurityToken(securityToken);
			if(null != userBO && !userBO.getTokenUsed()){
				if(userBO.isEnabled()){
					securityTokenExpiredDate = new SimpleDateFormat(FdahpStudyDesignerConstants.DB_SDF_DATE_TIME).parse(userBO.getTokenExpiryDate());
					if(securityTokenExpiredDate.after(new SimpleDateFormat(FdahpStudyDesignerConstants.DB_SDF_DATE_TIME).parse(FdahpStudyDesignerUtil.getCurrentDateTime()))){
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
			String password,UserBO userBO2,SessionObject sesObj) {
		UserBO userBO =null;
		logger.info("LoginServiceImpl - checkSecurityToken() - Starts");
		@SuppressWarnings("unchecked")
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		boolean isValid = false;
		boolean isIntialPasswordSetUp = false;
		Map<String, String> keyValueForSubject = null;
		String dynamicContent = "";
		List<Integer> userIds = null;
		String encodedUrl = "";
		String result = FdahpStudyDesignerConstants.FAILURE;
		String invalidAccessCodeError = propMap.get("invalid.access.code.error.msg");
		String oldPasswordError = propMap.get("old.password.error.msg");
		String passwordCount = propMap.get("password.history.count");
		List<UserPasswordHistory> passwordHistories = null;
		Boolean isValidPassword = true;
		String activity = "";
		String activityDetail = "";
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
								if(FdahpStudyDesignerUtil.compairEncryptedPassword(userPasswordHistory.getUserPassword(), password)){
									isValidPassword = false;
									break;
								}
							}
						}
						if(isValidPassword){
							if(userBO2 != null && StringUtils.isNotEmpty(userBO2.getFirstName())){
								userBO.setFirstName(null != userBO2.getFirstName() ? userBO2.getFirstName().trim() : "");
								userBO.setLastName(null != userBO2.getLastName() ? userBO2.getLastName().trim() : "");
								userBO.setPhoneNumber(null != userBO2.getPhoneNumber() ? userBO2.getPhoneNumber().trim() : "");
								activity = "User registration";
								activityDetail = "User named "+userBO2.getFirstName()+" "+userBO2.getLastName()+" is successfully registered";
							}else{
								activity = "Forgot password";
								activityDetail = "User successfully created the new password";
							}
							userBO.setUserPassword(FdahpStudyDesignerUtil.getEncryptedPassword(password));
							userBO.setTokenUsed(true);
							userBO.setEnabled(true);
							userBO.setAccountNonExpired(true);
							userBO.setAccountNonLocked(true);
							userBO.setCredentialsNonExpired(true);
							userBO.setPasswordExpairdedDateTime(new SimpleDateFormat(FdahpStudyDesignerConstants.DB_SDF_DATE_TIME).format(new Date()));
							result = loginDAO.updateUser(userBO);
							if(result.equals(FdahpStudyDesignerConstants.SUCCESS)){
								loginDAO.updatePasswordHistory(userBO.getUserId(), userBO.getUserPassword());
								isValid = true;
								auditLogDAO.saveToAuditLog(null, null, sesObj, activity, activityDetail ,"LoginDAOImpl - updateUser()");
							}
						} else {
							result = oldPasswordError.replace("$countPass", passwordCount);
						}
					} else {
						result = invalidAccessCodeError;
					}
					if(isIntialPasswordSetUp && isValid){
						List<String> cc = new ArrayList<>();
						cc.add(propMap.get("email.address.cc"));
						userBO = loginDAO.getValidUserByEmail(userBO.getUserEmail());
						keyValueForSubject = new HashMap<>();
						dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("newASPInitialPasswordSetupContent", keyValueForSubject);
						EmailNotification.sendEmailNotification("newASPInitialPasswordSetupSubject", dynamicContent, propMap.get("email.address.to"), cc, null);
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

	/** 
	 * Check the current session user is forcefully logout by Admin
	 * @author BTC
	 * 
	 * @param sessionObject , {@link SessionObject}
	 * @return {@link Boolean} , isValid 
	 */
	@Override
	public Boolean isFrocelyLogOutUser(SessionObject sessionObject) {
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
