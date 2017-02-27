package com.fdahpStudyDesigner.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.StudyPermissionBO;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.dao.UsersDAO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

@Service
public class UsersServiceImpl implements UsersService {
	
	private static Logger logger = Logger.getLogger(UsersServiceImpl.class);
	
	@Autowired
	private UsersDAO usersDAO;

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
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser) {
		logger.info("UsersServiceImpl - activateOrDeactivateUser() - Starts");
		String msg = fdahpStudyDesignerConstants.FAILURE;
		try{
			msg = usersDAO.activateOrDeactivateUser(userId, userStatus, loginUser);
		}catch(Exception e){
			logger.error("UsersServiceImpl - activateOrDeactivateUser() - ERROR",e);
		}
		logger.info("UsersServiceImpl - activateOrDeactivateUser() - Ends");
		return msg;
	}

	@Override
	public UserBO getUserDetails(int userId) {
		logger.info("UsersServiceImpl - getUserDetails() - Starts");
		UserBO userBO = null;
		RoleBO roleBO = null;
		try{
			userBO = usersDAO.getUserDetails(userId);
			/*if(userBO != null){
				roleBO = usersDAO.getUserRole(userBO.getRoleId());
				if(null != roleBO){
					userBO.setRoleName(roleBO.getRoleName());
				}
			}*/
		}catch(Exception e){
			logger.error("UsersServiceImpl - getUserDetails() - ERROR",e);
		}
		logger.info("UsersServiceImpl - getUserDetails() - Ends");
		return userBO;
	}
	
	

	@Override
	public String addOrUpdateUserDetails(UserBO userBO, String permissions,List<Integer> permissionList,String selectedStudies,String permissionValues){
		logger.info("UsersServiceImpl - addOrUpdateUserDetails() - Starts");
		UserBO userBO2 = null;
		String msg = fdahpStudyDesignerConstants.FAILURE;
		List<Integer> permsList = null; 
		try{
			if(null == userBO.getUserId()){
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
				userBO2 = usersDAO.getUserDetails(userBO.getUserId());
				permsList = usersDAO.getPermissionsByUserId(userBO.getUserId());
				userBO2.setFirstName(null != userBO.getFirstName() ? userBO.getFirstName().trim() : "");
				userBO2.setLastName(null != userBO.getLastName() ? userBO.getLastName().trim() : "");
				userBO2.setUserEmail(null != userBO.getUserEmail() ? userBO.getUserEmail().trim() : "");
				userBO2.setPhoneNumber(null != userBO.getPhoneNumber() ? userBO.getPhoneNumber().trim() : "");
				userBO2.setRoleId(userBO.getRoleId());
				userBO2.setModifiedBy(userBO.getModifiedBy());
				userBO2.setModifiedOn(userBO.getModifiedOn());
				if(permissionList.size() != permsList.size() || !permissionList.containsAll(permsList)){
					userBO2.setForceLogout(true);
				}
			}
			msg = usersDAO.addOrUpdateUserDetails(userBO2,permissions,selectedStudies,permissionValues);
			/*if(msg.equals(fdahpStudyDesignerConstants.SUCCESS)){
				
			}*/
		}catch(Exception e){
			logger.error("UsersServiceImpl - addOrUpdateUserDetails() - ERROR",e);
		}
		logger.info("UsersServiceImpl - addOrUpdateUserDetails() - Ends");
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
