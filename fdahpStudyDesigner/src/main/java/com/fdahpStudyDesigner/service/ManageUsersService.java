package com.fdahpStudyDesigner.service;

import java.util.List;
import com.fdahpStudyDesigner.bo.UserBO;

public interface ManageUsersService {
	
	public List<UserBO> getUserList();
	
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser);

}
