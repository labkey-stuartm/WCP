package com.fdahpstudydesigner.dao;

import java.util.List;

import org.hibernate.Session;

import com.fdahpstudydesigner.bean.ChangePasswordResponseBean;
import com.fdahpstudydesigner.bo.UserAttemptsBo;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.bo.UserPasswordHistory;

/**
 * @author BTC
 *
 */
public interface LoginDAO {

	public ChangePasswordResponseBean changePassword(Integer userId, String newPassword, String oldPassword);

	public List<UserPasswordHistory> getPasswordHistory(Integer userId);

	public UserAttemptsBo getUserAttempts(String userEmailId);

	public UserBO getUserBySecurityToken(String securityToken);

	public UserBO getValidUserByEmail(String email);

	public Boolean isFrocelyLogOutUser(Integer userId);

	public Boolean isUserEnabled(Integer userId);

	public void passwordLoginBlocked();

	public void resetFailAttempts(String userEmailId);

	public void updateFailAttempts(String userEmailId);

	public String updatePasswordHistory(Integer userId, String hashedPassword, String rawSalt);

	public void updateToHashedPassword(Integer userId, String hashedPassword, String rawSalt);

	public String updateUser(UserBO userBO);

}
