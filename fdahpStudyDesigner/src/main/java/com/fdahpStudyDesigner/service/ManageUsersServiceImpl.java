package com.fdahpStudyDesigner.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fdahpStudyDesigner.dao.ManageUsersDAO;

@Service
public class ManageUsersServiceImpl implements ManageUsersService {
	
	private static Logger logger = Logger.getLogger(ManageUsersServiceImpl.class);
	private ManageUsersDAO manageUsersDAO;

	@Autowired
	public void setManageUsersDAO(ManageUsersDAO manageUsersDAO) {
		this.manageUsersDAO = manageUsersDAO;
	}
	
}
