package com.fdahpStudyDesigner.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.dao.UsersDAO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

@Service
public class UsersServiceImpl implements UsersService {
	
	private static Logger logger = Logger.getLogger(UsersServiceImpl.class);
	private UsersDAO usersDAO;

	@Autowired
	public void setUsersDAO(UsersDAO usersDAO) {
		this.usersDAO = usersDAO;
	}

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
			if(userBO != null){
				roleBO = usersDAO.getUserRole(userBO.getRoleId());
				userBO.setRoleName(roleBO.getRoleName());
			}
		}catch(Exception e){
			logger.error("UsersServiceImpl - getUserDetails() - ERROR",e);
		}
		logger.info("UsersServiceImpl - getUserDetails() - Ends");
		return userBO;
	}
}
