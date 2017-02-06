package com.fdahpStudyDesigner.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.fdahpStudyDesigner.service.ManageUsersService;

@Controller
public class ManageUsersController {
	
	private static Logger logger = Logger.getLogger(ManageUsersController.class.getName());
	
	private ManageUsersService manageUsersService;

	@Autowired
	public void setManageUsersService(ManageUsersService manageUsersService) {
		this.manageUsersService = manageUsersService;
	}

}
