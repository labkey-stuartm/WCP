package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.StudyPermissionBO;
import com.fdahpStudyDesigner.bo.UserBO;

public interface UsersDAO {
	
	public List<UserBO> getUserList();
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser);
	public UserBO getUserDetails(int userId);
	public RoleBO getUserRole(int roleId);
	public String addOrUpdateUserDetails(UserBO userBO,String permissions,String selectedStudies,String permissionValues);
	public List<RoleBO> getUserRoleList();
	public List<Integer> getPermissionsByUserId(Integer userId);

}
