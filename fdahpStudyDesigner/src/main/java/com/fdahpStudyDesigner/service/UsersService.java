package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.UserBO;

public interface UsersService {
	
	public List<UserBO> getUserList();
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser);
	public UserBO getUserDetails(int userId);
	public String addOrUpdateUserDetails(UserBO userBO);
	public List<RoleBO> getUserRoleList();
	public RoleBO getUserRole(int roleId);

}
