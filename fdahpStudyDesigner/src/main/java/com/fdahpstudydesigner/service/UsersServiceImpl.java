package com.fdahpstudydesigner.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpstudydesigner.bo.RoleBO;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.dao.AuditLogDAO;
import com.fdahpstudydesigner.dao.UsersDAO;
import com.fdahpstudydesigner.util.EmailNotification;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

@Service
public class UsersServiceImpl implements UsersService {
	
	private static Logger logger = Logger.getLogger(UsersServiceImpl.class);
	
	@Autowired
	private UsersDAO usersDAO;
	
	@Autowired
	LoginService loginService;
	
	@Autowired
	private AuditLogDAO auditLogDAO;

	@Override
	public List<UserBO> getUserList() {
		logger.info("UsersServiceImpl - getUserList() - Starts");
		List<UserBO> userList = null;
		try{
			userList = usersDAO.getUserList();
		}catch(Exception e){
			logger.error("UsersServiceImpl - getUserList() - ERROR",e);
		}
		logger.info("UsersServiceImpl - getUserList() - Ends");
		return userList;
	}

	@Override
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser,SessionObject userSession,HttpServletRequest request) {
		logger.info("UsersServiceImpl - activateOrDeactivateUser() - Starts");
		String msg = FdahpStudyDesignerConstants.FAILURE;
		List<String> superAdminEmailList = null;
		Map<String, String> keyValueForSubject = null;
		String dynamicContent = "";
		boolean flag = false;
		UserBO userBo = null;
		UserBO adminFullNameIfSizeOne = null;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String customerCareMail = "";
		String status = "";
		
		try{
			msg = usersDAO.activateOrDeactivateUser(userId, userStatus, loginUser,userSession);
					superAdminEmailList = usersDAO.getSuperAdminList();
					userBo = usersDAO.getUserDetails(userId);
					if(msg.equals(FdahpStudyDesignerConstants.SUCCESS)){
						keyValueForSubject = new HashMap<String, String>();
					if(superAdminEmailList!=null && !superAdminEmailList.isEmpty()){
						if(userStatus == 1){
							status = "Deactivated";
						}else{
							status = "Active";
						}
						if(superAdminEmailList.size() == 1){
							for (String email : superAdminEmailList) {
								adminFullNameIfSizeOne = (UserBO) usersDAO.getSuperAdminNameByEmailId(email);
								keyValueForSubject.put("$admin", adminFullNameIfSizeOne.getFirstName()+" "+adminFullNameIfSizeOne.getLastName());
							}
						}else{
							keyValueForSubject.put("$admin", "Admin");
						}
						keyValueForSubject.put("$userStatus", status);
						keyValueForSubject.put("$sessionAdminFullName", userSession.getFirstName()+" "+userSession.getLastName());
						keyValueForSubject.put("$userEmail", userBo.getUserEmail());
						dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("mailForAdminUserUpdateContent", keyValueForSubject);
						flag = EmailNotification.sendEmailNotification("mailForAdminUserUpdateSubject", dynamicContent, null, superAdminEmailList, null);
					}
					if(Integer.valueOf(userStatus).equals(0)){
						if(userBo!=null && !userBo.isCredentialsNonExpired()){
							loginService.sendPasswordResetLinkToMail(request, userBo.getUserEmail(), "ReactivateMailAfterEnforcePassChange");
						}else{
							customerCareMail = propMap.get("email.address.customer.service");
							keyValueForSubject.put("$userFirstName", userBo.getFirstName());
							keyValueForSubject.put("$customerCareMail", customerCareMail);
							dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("mailForReactivatingUserContent", keyValueForSubject);
							flag = EmailNotification.sendEmailNotification("mailForReactivatingUserSubject", dynamicContent, userBo.getUserEmail(), null, null);
						}
					}
					}
		}catch(Exception e){
			logger.error("UsersServiceImpl - activateOrDeactivateUser() - ERROR",e);
		}
		logger.info("UsersServiceImpl - activateOrDeactivateUser() - Ends");
		return msg;
	}

	@Override
	public UserBO getUserDetails(Integer userId) {
		logger.info("UsersServiceImpl - getUserDetails() - Starts");
		UserBO userBO = null;
		try{
			userBO = usersDAO.getUserDetails(userId);
		}catch(Exception e){
			logger.error("UsersServiceImpl - getUserDetails() - ERROR",e);
		}
		logger.info("UsersServiceImpl - getUserDetails() - Ends");
		return userBO;
	}
	
	// kanchana
	@Override
	public Integer getUserPermissionByUserId(Integer sessionUserId) {
		logger.info("UsersServiceImpl - getUserPermissionByUserId() - Starts");
		Integer userId= null;
		try{
			userId = usersDAO.getUserPermissionByUserId(sessionUserId);
		}catch(Exception e){
			logger.error("UsersServiceImpl - getUserPermissionByUserId() - ERROR",e);
		}
		logger.info("UsersServiceImpl - getUserPermissionByUserId() - Ends");
		return userId;
	}
	
	

	@Override
	public String addOrUpdateUserDetails(HttpServletRequest request,UserBO userBO, String permissions,List<Integer> permissionList,String selectedStudies,String permissionValues,SessionObject userSession){
		logger.info("UsersServiceImpl - addOrUpdateUserDetails() - Starts");
		UserBO userBO2 = null;
		String msg = FdahpStudyDesignerConstants.FAILURE;
		boolean addFlag = false;
		String activity = "";
		String activityDetail = ""; 
		//boolean emailIdChange = false;
		List<String> superAdminEmailList = null;
		Map<String, String> keyValueForSubject = null;
		String dynamicContent = "";
		boolean flag = false;
		UserBO adminFullNameIfSizeOne = null;
		try{
			if(null == userBO.getUserId()){
				addFlag = true;
				userBO2 = new UserBO();
				userBO2.setFirstName(null != userBO.getFirstName() ? userBO.getFirstName().trim() : "");
				userBO2.setLastName(null != userBO.getLastName() ? userBO.getLastName().trim() : "");
				userBO2.setUserEmail(null != userBO.getUserEmail() ? userBO.getUserEmail().trim() : "");
				userBO2.setPhoneNumber(null != userBO.getPhoneNumber() ? userBO.getPhoneNumber().trim() : "");
				userBO2.setRoleId(userBO.getRoleId());
				userBO2.setCreatedBy(userBO.getCreatedBy());
				userBO2.setCreatedOn(userBO.getCreatedOn());
				userBO2.setEnabled(false);
				userBO2.setCredentialsNonExpired(true);
				userBO2.setAccountNonExpired(true);
				userBO2.setAccountNonLocked(true);
			}else{
				addFlag = false;
				userBO2 = usersDAO.getUserDetails(userBO.getUserId());
				userBO2.setFirstName(null != userBO.getFirstName() ? userBO.getFirstName().trim() : "");
				userBO2.setLastName(null != userBO.getLastName() ? userBO.getLastName().trim() : "");
				/*if(!userBO2.getUserEmail().equals(userBO.getUserEmail())){
					emailIdChange = true;
				}*/
				userBO2.setUserEmail(null != userBO.getUserEmail() ? userBO.getUserEmail().trim() : "");
				userBO2.setPhoneNumber(null != userBO.getPhoneNumber() ? userBO.getPhoneNumber().trim() : "");
				userBO2.setRoleId(userBO.getRoleId());
				userBO2.setModifiedBy(userBO.getModifiedBy());
				userBO2.setModifiedOn(userBO.getModifiedOn());
				userBO2.setEnabled(userBO.isEnabled());
				if(!userSession.getUserId().equals(userBO.getUserId())){
					userBO2.setForceLogout(true);
				}
			}
			msg = usersDAO.addOrUpdateUserDetails(userBO2,permissions,selectedStudies,permissionValues);
			if(msg.equals(FdahpStudyDesignerConstants.SUCCESS)){
				if(addFlag){
					activity = "User created";
					activityDetail = "User named "+userBO.getFirstName()+" "+userBO.getLastName()+" is newly added";
					msg = loginService.sendPasswordResetLinkToMail(request, userBO2.getUserEmail(), "USER");
				}
				if(!addFlag){
					activity = "User updated";
					activityDetail = "User details is being updated and the user get force logout if the user is active";
					/*if(emailIdChange){
						msg = loginService.sendPasswordResetLinkToMail(request, userBO2.getUserEmail(), "USER_EMAIL_UPDATE");
					}else{*/
						msg = loginService.sendPasswordResetLinkToMail(request, userBO2.getUserEmail(), "USER_UPDATE");
					/*}*/
				}
				auditLogDAO.saveToAuditLog(null, null, userSession, activity, activityDetail ,"UsersDAOImpl - addOrUpdateUserDetails()");
			
				superAdminEmailList = usersDAO.getSuperAdminList();
				if(msg.equals(FdahpStudyDesignerConstants.SUCCESS) && superAdminEmailList!=null && !superAdminEmailList.isEmpty()){
					keyValueForSubject = new HashMap<String, String>();
					if(superAdminEmailList.size() == 1){
						for (String email : superAdminEmailList) {
							adminFullNameIfSizeOne = (UserBO) usersDAO.getSuperAdminNameByEmailId(email);
							keyValueForSubject.put("$admin", adminFullNameIfSizeOne.getFirstName());
						}
					}else{
						keyValueForSubject.put("$admin", "Admin");
					}
					keyValueForSubject.put("$userEmail", userBO.getUserEmail());
					keyValueForSubject.put("$sessionAdminFullName", userSession.getFirstName()+" "+userSession.getLastName());
					if(addFlag){
						dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("mailForAdminUserCreateContent", keyValueForSubject);
						flag = EmailNotification.sendEmailNotification("mailForAdminUserCreateSubject", dynamicContent, null, superAdminEmailList, null);
					}else{
						String status = "";
						if(FdahpStudyDesignerUtil.isEmpty(userBO2.getUserPassword())){
							status = "Pending Activation";
						}else{
							if(userBO2.isEnabled()){
								status = "Active";
							}else{
								status = "Deactivated";
							}
						}
						keyValueForSubject.put("$userStatus", status);
						dynamicContent = FdahpStudyDesignerUtil.genarateEmailContent("mailForAdminUserUpdateContent", keyValueForSubject);
						flag = EmailNotification.sendEmailNotification("mailForAdminUserUpdateSubject", dynamicContent, null, superAdminEmailList, null);
					}
				}
			
			}
		}catch(Exception e){
			logger.error("UsersServiceImpl - addOrUpdateUserDetails() - ERROR",e);
		}
		logger.info("UsersServiceImpl - addOrUpdateUserDetails() - Ends");
		return msg;
	}
	
	@Override
	public String forceLogOut(SessionObject userSession) {
		logger.info("UsersServiceImpl - forceLogOut() - Starts");
		String msg = FdahpStudyDesignerConstants.FAILURE;
		try{
			msg = usersDAO.forceLogOut(userSession);
		}catch(Exception e){
			logger.error("UsersServiceImpl - forceLogOut() - ERROR",e);
		}
		logger.info("UsersServiceImpl - forceLogOut() - Ends");
		return msg;
	}

	@Override
	public List<RoleBO> getUserRoleList() {
		logger.info("UsersServiceImpl - getUserRoleList() - Starts");
		List<RoleBO> roleBOList = null;
		try{
			roleBOList = usersDAO.getUserRoleList();
		}catch(Exception e){
			logger.error("UsersServiceImpl - getUserRoleList() - ERROR",e);
		}
		logger.info("UsersServiceImpl - getUserRoleList() - Ends");
		return roleBOList;
	}
	
	
	/**
	 * Kanchana
	 */
	@Override
	public RoleBO getUserRole(int roleId) {
		logger.info("UsersServiceImpl - getUserRole() - Starts");
		RoleBO roleBO = null;
		try{
			roleBO = usersDAO.getUserRole(roleId);
		}catch(Exception e){
			logger.error("UsersServiceImpl - getUserRole() - ERROR",e);
		}
		logger.info("UsersServiceImpl - getUserRole() - Ends");
		return roleBO;
	}
	
	@Override
	public List<Integer> getPermissionsByUserId(Integer userId){
		logger.info("UsersServiceImpl - permissionsByUserId() - Starts");
		List<Integer> permissions = null;
		try{
			permissions = usersDAO.getPermissionsByUserId(userId);
		}catch(Exception e){
			logger.error("UsersServiceImpl - permissionsByUserId() - ERROR",e);
		}
		logger.info("UsersServiceImpl - permissionsByUserId() - Ends");
		return permissions;
	}
	
	@Override
	public List<String> getActiveUserEmailIds() {
		logger.info("UsersServiceImpl - getActiveUserEmailIds() - Starts");
		List<String> emails = null;
		try{
			emails = usersDAO.getActiveUserEmailIds();
		}catch(Exception e){
			logger.error("UsersServiceImpl - getActiveUserEmailIds() - ERROR",e);
		}
		logger.info("UsersServiceImpl - getActiveUserEmailIds() - Ends");
		return emails;
	}

	@Override
	public String enforcePasswordChange(Integer userId, String email) {
		logger.info("UsersServiceImpl - enforcePasswordChange() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		try{
			message = usersDAO.enforcePasswordChange(userId, email);
		}catch(Exception e){
			logger.error("UsersServiceImpl - enforcePasswordChange() - ERROR",e);
		}
		logger.info("UsersServiceImpl - enforcePasswordChange() - Ends");
		return message;
	}
}
