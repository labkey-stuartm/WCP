package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.UserAttemptsBo;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.bo.UserPasswordHistory;


/**
 * @author Vivek
 *
 */
public interface LoginDAO {
	
	public UserBO getValidUserByEmail(String email);
	
	public String changePassword(Integer userId, String newPassword, String oldPassword);
	
	public String  updateUser(UserBO userBO);
	
	public UserBO  getUserBySecurityToken(String securityToken);
	
	public void updateFailAttempts(String userEmailId);
	
	public void resetFailAttempts(String userEmailId);
	
	public UserAttemptsBo getUserAttempts(String userEmailId);
	
	public Boolean isUserEnabled(Integer userId);
	
	public String updatePasswordHistory(Integer userId, String userPassword);
	
	public List<UserPasswordHistory> getPasswordHistory(Integer userId);
	
	public Boolean isFrocelyLogOutUser(Integer userId);
	
}
