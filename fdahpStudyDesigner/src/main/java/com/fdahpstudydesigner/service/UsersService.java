package com.fdahpstudydesigner.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fdahpstudydesigner.bo.RoleBO;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.util.SessionObject;

public interface UsersService {
	
	public List<UserBO> getUserList();
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser,SessionObject userSession);
	public UserBO getUserDetails(Integer userId);
	public String addOrUpdateUserDetails(HttpServletRequest request,UserBO userBO, String permissions,List<Integer> permissionList,String selectedStudies,String permissionValues,SessionObject userSession);
	public List<RoleBO> getUserRoleList();
	public RoleBO getUserRole(int roleId);
	public List<Integer> getPermissionsByUserId(Integer userId);
	public String forceLogOut(SessionObject userSession);
	public Integer getUserPermissionByUserId(Integer sessionUserId);
	public List<String> getActiveUserEmailIds();
	public String enforcePasswordChange(Integer userId, String email);
}
