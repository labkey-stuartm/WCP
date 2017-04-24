package com.fdahpstudydesigner.dao;

import java.util.List;

import com.fdahpstudydesigner.bo.UserAttemptsBo;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.bo.UserPasswordHistory;


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
