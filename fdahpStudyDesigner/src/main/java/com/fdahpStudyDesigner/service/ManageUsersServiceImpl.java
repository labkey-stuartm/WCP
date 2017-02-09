package com.fdahpStudyDesigner.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.dao.ManageUsersDAO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

@Service
public class ManageUsersServiceImpl implements ManageUsersService {
	
	private static Logger logger = Logger.getLogger(ManageUsersServiceImpl.class);
	private ManageUsersDAO manageUsersDAO;

	@Autowired
	public void setManageUsersDAO(ManageUsersDAO manageUsersDAO) {
		this.manageUsersDAO = manageUsersDAO;
	}

	@Override
	public List<UserBO> getUserList() {
		logger.info("ManageUsersServiceImpl - getUserList() - Starts");
		List<UserBO> userList = null;
		try{
			userList = manageUsersDAO.getUserList();
		}catch(Exception e){
			logger.error("ManageUsersServiceImpl - getUserList() - ERROR",e);
		}
		logger.info("ManageUsersServiceImpl - getUserList() - Ends");
		return userList;
	}

	@Override
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser) {
		logger.info("ManageUsersServiceImpl - activateOrDeactivateUser() - Starts");
		String msg = fdahpStudyDesignerConstants.FAILURE;
		try{
			msg = manageUsersDAO.activateOrDeactivateUser(userId, userStatus, loginUser);
		}catch(Exception e){
			logger.error("ManageUsersServiceImpl - activateOrDeactivateUser() - ERROR",e);
		}
		logger.info("ManageUsersServiceImpl - activateOrDeactivateUser() - Ends");
		return msg;
	}
}
