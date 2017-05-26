package com.fdahpstudydesigner.dao;

import java.util.List;

import com.fdahpstudydesigner.bo.RoleBO;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.util.SessionObject;

public interface UsersDAO {
	
	public List<UserBO> getUserList();
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser,SessionObject userSession);
	public UserBO getUserDetails(int userId);
	public RoleBO getUserRole(int roleId);
	public String addOrUpdateUserDetails(UserBO userBO,String permissions,String selectedStudies,String permissionValues);
	public List<RoleBO> getUserRoleList();
	public List<Integer> getPermissionsByUserId(Integer userId);
	public String forceLogOut(SessionObject userSession);
	public List<String> getSuperAdminList();
	public UserBO getSuperAdminNameByEmailId(String emailId);
<<<<<<< HEAD
	public Integer getUserPermissionByUserId(Integer sessionUserId);
=======
	public List<String> getActiveUserEmailIds();
>>>>>>> d77467f64cfc3551ef5dbfa36392f17aad37a5b4

}
