package com.fdahpStudyDesigner.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.UserBO;

public interface UsersService {
	
	public List<UserBO> getUserList();
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser);
	public UserBO getUserDetails(int userId);
	public String addOrUpdateUserDetails(HttpServletRequest request,UserBO userBO, String permissions,List<Integer> permissionList,String selectedStudies,String permissionValues);
	public List<RoleBO> getUserRoleList();
	public RoleBO getUserRole(int roleId);
	public List<Integer> getPermissionsByUserId(Integer userId);

}
