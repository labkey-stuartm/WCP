package com.fdahpstudydesigner.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpstudydesigner.bo.RoleBO;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.dao.AuditLogDAO;
import com.fdahpstudydesigner.dao.UsersDAO;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
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
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser,SessionObject userSession) {
		logger.info("UsersServiceImpl - activateOrDeactivateUser() - Starts");
		String msg = FdahpStudyDesignerConstants.FAILURE;
		try{
			msg = usersDAO.activateOrDeactivateUser(userId, userStatus, loginUser,userSession);
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
	
	

	@Override
	public String addOrUpdateUserDetails(HttpServletRequest request,UserBO userBO, String permissions,List<Integer> permissionList,String selectedStudies,String permissionValues,SessionObject userSession){
		logger.info("UsersServiceImpl - addOrUpdateUserDetails() - Starts");
		UserBO userBO2 = null;
		String msg = FdahpStudyDesignerConstants.FAILURE;
		boolean addFlag = false;
		String activity = "";
		String activityDetail = ""; 
		//boolean emailIdChange = false;
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
}
