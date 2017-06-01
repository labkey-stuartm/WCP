package com.fdahpstudydesigner.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;

import com.fdahpstudydesigner.bo.UserAttemptsBo;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.bo.UserPasswordHistory;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

@Repository
public class LoginDAOImpl implements LoginDAO {
	
	private static Logger logger = Logger.getLogger(LoginDAOImpl.class.getName());
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	
	@Autowired
	private AuditLogDAO auditLogDAO;
	
	public LoginDAOImpl() {
	}
	
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	/**
	 * Get valid user by email
	 * @author Vivek
	 * 
	 * @param email , the email id of the user
	 * @return {@link UserBO}
	 * @exception Exception
	 */
	@Override
	public UserBO getValidUserByEmail(String email) {
		logger.info("LoginDAOImpl - getValidUserByEmail() - Starts");
		UserBO userBo = null;
		Session session = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getUserByEmail").setString("email", email.trim());
			userBo = (UserBO) query.uniqueResult();
			if(userBo!=null){
				userBo.setUserLastLoginDateTime(FdahpStudyDesignerUtil.getCurrentDateTime());
				
			}
		} catch (Exception e) {
			userBo = null;
			logger.error("LoginDAOImpl - getValidUserByEmail() - ERROR ", e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - getValidUserByEmail() - Ends");
		return userBo;
	}
	
	/**
	 * This method change the user Password
	 * @author Vivek
	 * 
	 * @param userId , The database user ID of the user
	 * @param newPassword , The new password given by user
	 * @return {@link String}
	 * @exception Exception
	 */
	@Override
	public String changePassword(Integer userId, String newPassword, String oldPassword) {
		logger.info("LoginDAOImpl - changePassword() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		UserBO adminUserBO = null;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String encrNewPass = "";
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			query = session.getNamedQuery("getUserById").setInteger("userId", userId);
			adminUserBO = (UserBO) query.uniqueResult();
			if(null != adminUserBO && FdahpStudyDesignerUtil.compairEncryptedPassword(adminUserBO.getUserPassword(), oldPassword)){
				encrNewPass = FdahpStudyDesignerUtil.getEncryptedPassword(newPassword);
				if(null != encrNewPass && !"".equals(encrNewPass)){
					adminUserBO.setUserPassword(encrNewPass);
				}
				adminUserBO.setModifiedBy(userId);
				adminUserBO.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDate());
				adminUserBO.setPasswordExpairdedDateTime(new SimpleDateFormat(FdahpStudyDesignerConstants.DB_SDF_DATE_TIME).format(new Date()));
				session.update(adminUserBO);
				message = FdahpStudyDesignerConstants.SUCCESS;
			} else {
				message = propMap.get("invalid.oldpassword.msg");
			}
			transaction.commit();
		} catch (Exception e) {
			logger.error("LoginDAOImpl - changePassword() - ERROR " , e);
			if(transaction != null)
				transaction.rollback();
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - changePassword() - Ends");
		return message;
	}

	/**
	 * This method save or update  the user
	 * @author Vivek
	 * 
	 * @param userBO , the user Object of {@link UserBO}
	 * @return {@link String}
	 * @exception Exception
	 */
	@Override
	public String updateUser(UserBO userBO) {
		logger.info("LoginDAOImpl - updateUser() - Starts");
		Session session = null;
		String result = FdahpStudyDesignerConstants.FAILURE;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(userBO);
			transaction.commit();
			this.resetFailAttempts(userBO.getUserEmail());
			result = FdahpStudyDesignerConstants.SUCCESS;
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			logger.error("LoginDAOImpl - updateUser() - ERROR " , e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - updateUser() - Ends");
		return result;
	}

	/**
	 * This method get the user by security token
	 * @author Vivek
	 * 
	 * @param securityToken , The security token of user 
	 * @return {@link UserBO}
	 * @exception Exception
	 */
	@Override
	public UserBO getUserBySecurityToken(String securityToken) {
		logger.info("LoginDAOImpl - getUserBySecurityToken() - Starts");
		Session session = null;
		UserBO userBO = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			userBO = (UserBO) session.getNamedQuery("getUserBySecurityToken").setString("securityToken", securityToken).uniqueResult();
			if(!userBO.getSecurityToken().equals(securityToken)){
				userBO = null;
			}
		} catch (Exception e) {
			logger.error("LoginDAOImpl - getUserBySecurityToken() - ERROR " , e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - getUserBySecurityToken() - Ends");
		return userBO;
	}

	/**
	 * Update the fail attempts of user
	 * @author Vivek
	 * 
	 * @param userEmailId , The email id of user
	 * @return 
	 */
	@Override
	public void updateFailAttempts(String userEmailId) {
		logger.info("LoginDAOImpl - updateUser() - Starts");
		Session session = null;
		UserAttemptsBo attemptsBo = null;
		String queryString = null;
		Boolean isAcountLocked = false;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		final Integer MAX_ATTEMPTS = Integer.valueOf(propMap.get("max.login.attempts"));
		UserBO userBO = new UserBO();
		final Integer USER_LOCK_DURATION = Integer.valueOf(propMap.get("user.lock.duration.in.minutes"));
		try {
			attemptsBo = this.getUserAttempts(userEmailId);
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(attemptsBo == null){
				if (this.isUserExists(userEmailId)) {
					attemptsBo = new UserAttemptsBo();
					attemptsBo.setAttempts(1);
					attemptsBo.setUserEmail(userEmailId);
					attemptsBo.setLastModified(FdahpStudyDesignerUtil.getCurrentDateTime());
					session.save(attemptsBo);
				}
			} else {
				if (attemptsBo.getAttempts() + 1 >= MAX_ATTEMPTS) {
					// locked user
					/*queryString = "UPDATE UserBO set accountNonLocked = " + 0 + " where userEmail ='" + userEmailId+"'";
					session.createQuery(queryString).executeUpdate();*/
					// throw exception
					isAcountLocked = true;
				}
				if (this.isUserExists(userEmailId)) {
					if(attemptsBo.getAttempts() >= MAX_ATTEMPTS && new SimpleDateFormat(
							FdahpStudyDesignerConstants.DB_SDF_DATE_TIME)
							.parse(FdahpStudyDesignerUtil.addMinutes(attemptsBo.getLastModified(), USER_LOCK_DURATION))
					.before(new SimpleDateFormat(
							FdahpStudyDesignerConstants.DB_SDF_DATE_TIME)
							.parse(FdahpStudyDesignerUtil
									.getCurrentDateTime()))) {
						attemptsBo.setAttempts(1);
						attemptsBo.setUserEmail(userEmailId);
						attemptsBo.setLastModified(FdahpStudyDesignerUtil.getCurrentDateTime());
						session.update(attemptsBo);
						isAcountLocked = false;
					} else if(attemptsBo.getAttempts() < MAX_ATTEMPTS + 1) {
						attemptsBo.setAttempts(attemptsBo.getAttempts() + 1);
						attemptsBo.setUserEmail(userEmailId);
						attemptsBo.setLastModified(FdahpStudyDesignerUtil.getCurrentDateTime());
						session.update(attemptsBo);
					}
				}
			}
			userBO = (UserBO) session.getNamedQuery("getUserByEmail").setString("email", userEmailId).uniqueResult();
			if(userBO!=null){
				if(isAcountLocked){
					SessionObject sessionObject = new SessionObject();
				    sessionObject.setUserId(userBO.getUserId());
				    String activityDetails = FdahpStudyDesignerConstants.USER_LOCKED_ACTIVITY_DEATILS_MESSAGE.replace("&name", userEmailId);
	 				auditLogDAO.saveToAuditLog(session, transaction, sessionObject, FdahpStudyDesignerConstants.USER_LOCKED_ACTIVITY_MESSAGE, activityDetails, "LoginDAOImpl - updateUser()");
				}else{
						SessionObject sessionObject = new SessionObject();
					    sessionObject.setUserId(userBO.getUserId());
						auditLogDAO.saveToAuditLog(session, transaction, sessionObject, FdahpStudyDesignerConstants.PWD_FAIL_ACTIVITY_MESSAGE, FdahpStudyDesignerConstants.PWD_FAIL_ACTIVITY_DEATILS_MESSAGE, "LoginDAOImpl - updateUser()");
				}
			}
			transaction.commit();
		}
		catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			logger.error("LoginDAOImpl - updateUser() - ERROR " , e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		if(isAcountLocked){
			throw new LockedException(propMap.get("user.lock.msg"));
		}
		logger.info("LoginDAOImpl - updateUser() - Ends");
		
	}
	
	/**
	 * Reset the fail attempts of user when user successfully logged in
	 * @author Vivek
	 * 
	 * @param userEmailId , The email id of user
	 * @return 
	 */
	@Override
	public void resetFailAttempts(String userEmailId) {
		logger.info("LoginDAOImpl - resetFailAttempts() - Starts");
		Session session = null;
		UserAttemptsBo attemptsBo = null;
		try {
			attemptsBo  = this.getUserAttempts(userEmailId);
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(attemptsBo != null){
				session.delete(attemptsBo);
			}
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			logger.error("LoginDAOImpl - resetFailAttempts() - ERROR " , e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - resetFailAttempts() - Ends");
		
	}

	/**
	 * Get the fail login attempts of the user
	 * @author Vivek
	 * 
	 * @param userEmailId , The email id of user
	 * @return {@link UserAttemptsBo}
	 */
	@Override
	public UserAttemptsBo getUserAttempts(String userEmailId) {
		logger.info("LoginDAOImpl - getUserAttempts() - Starts");
		Session session = null;
		UserAttemptsBo attemptsBo = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			Query query = session.getNamedQuery("getUserAttempts").setString("userEmailId", userEmailId);
		//	String searchQuery = "select * from user_attempts where email_id='"+userEmailId+"'";
		//	Query query = session.createSQLQuery(searchQuery);
			attemptsBo = (UserAttemptsBo) query.uniqueResult();
		} catch (Exception e) {
			logger.error("LoginDAOImpl - getUserAttempts() - ERROR " , e);
		} finally {
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - getUserAttempts() - Ends");
		return attemptsBo;
	}
	
	/**
	 * Check is User Exists in the db
	 * @author Vivek
	 * 
	 * @param userEmailId , The email id of user
	 * @return boolean
	 */
	private boolean isUserExists(String userEmail) {
		logger.info("LoginDAOImpl - isUserExists() - Starts");
		UserBO userBo = null;
		boolean result = false;
		try {
			userBo = this.getValidUserByEmail(userEmail);
			if (userBo != null) {
				result = true;
			}
		} catch (Exception e) {
			logger.error("LoginDAOImpl - isUserExists() - ERROR " , e);
		}
		logger.info("LoginDAOImpl - isUserExists() - Ends");
		return result;
	}

	/**
	 * Check User status
	 * @author Vivek
	 * 
	 * @param userId , The user id of user
	 * @return {@link Boolean}
	 */
	@Override
	public Boolean isUserEnabled(Integer userId){
		logger.info("LoginDAOImpl - isUserExists() - Starts");
		UserBO userBo = null;
		boolean result = false;
		Session session = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				userBo = (UserBO) session.getNamedQuery("getUserById").setInteger("userId", userId).uniqueResult();
				if (userBo != null) {
					result = userBo.isEnabled();
				}
			}
			
		} catch (Exception e) {
			logger.error("LoginDAOImpl - isUserExists() - ERROR " , e);
		} finally{
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - isUserExists() - Ends");
		return result;
	}

	/**
	 * Update the new password in password history
	 * @author Vivek
	 * 
	 * @param userId , The user id of user
	 * @return {@link String} , the status FdahpStudyDesignerConstants.SUCCESS or FdahpStudyDesignerConstants.FAILURE
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String updatePasswordHistory(Integer userId, String userPassword){
		logger.info("LoginDAOImpl - updatePasswordHistory() - Starts");
		List<UserPasswordHistory>  passwordHistories= null;
		UserPasswordHistory savePasswordHistory = null;
		String result = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		Integer passwordHistoryCount = Integer.parseInt(propMap.get("password.history.count"));
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(userId!= null && userId != 0){
				passwordHistories =  session.getNamedQuery("getPaswordHistoryByUserId").setInteger("userId", userId).list();
				if (passwordHistories != null && passwordHistories.size() > (passwordHistoryCount - 1)) {
					for (int i = 0; i < ((passwordHistories.size() - passwordHistoryCount) + 1); i++) {
						session.delete(passwordHistories.get(i));
					}
				} 
				savePasswordHistory = new UserPasswordHistory();
				savePasswordHistory.setCreatedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
				savePasswordHistory.setUserId(userId);
				savePasswordHistory.setUserPassword(userPassword);
				session.save(savePasswordHistory);
				result = FdahpStudyDesignerConstants.SUCCESS;
			}
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			logger.error("LoginDAOImpl - updatePasswordHistory() - ERROR " , e);
		} finally{
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - updatePasswordHistory() - Ends");
		return result;
	}

	/**
	 * get the password history
	 * @author Vivek
	 * 
	 * @param userId , The user id of user
	 * @return List of {@link UserPasswordHistory} 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<UserPasswordHistory> getPasswordHistory(Integer userId) {
		logger.info("LoginDAOImpl - updatePasswordHistory() - Starts");
		List<UserPasswordHistory>  passwordHistories= null;
		Session session = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				passwordHistories =  session.getNamedQuery("getPaswordHistoryByUserId").setInteger("userId", userId).list();
			}
			
		} catch (Exception e) {
			logger.error("LoginDAOImpl - updatePasswordHistory() - ERROR " , e);
		} finally{
			if (session != null) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - updatePasswordHistory() - Ends");
		return passwordHistories;
	}
	
	/**
	 * Check the user is forcefully logout by Admin
	 * @author Vivek
	 * 
	 * @param userId , The user id of user
	 * @return {@link Boolean}
	 */
	@Override
	public Boolean isFrocelyLogOutUser(Integer userId){
		logger.info("LoginDAOImpl - isFrocelyLogOutUser() - Starts");
		UserBO userBo = null;
		boolean result = false;
		Session session = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				userBo = (UserBO) session.getNamedQuery("getUserById").setInteger("userId", userId).uniqueResult();
				if (userBo != null) {
					result = userBo.isForceLogout();
				}
			}
			
		} catch (Exception e) {
			logger.error("LoginDAOImpl - isFrocelyLogOutUser() - ERROR " , e);
		} finally{
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - isFrocelyLogOutUser() - Ends");
		return result;
	}
	
	/**
	 * Check the user is not logged in from long time logout by Admin
	 * @author Ronalin
	 * 
	 * @param sessionObject
	 * @return {@link Boolean}
	 */
	@SuppressWarnings("unchecked")
	public void  passwordLoginBlocked(){
		logger.info("LoginDAOImpl - passwordLoginBlocked() - Starts");
		Session session = null;
		List<Object[]> userBOList = null;
		List<Integer> userBOIdList = null;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		int lastLoginExpirationInDay =  Integer.parseInt("-"+propMap.get("lastlogin.expiration.in.day"));
		String lastLoginDateTime;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			lastLoginDateTime = new SimpleDateFormat(FdahpStudyDesignerConstants.DB_SDF_DATE_TIME ).format(FdahpStudyDesignerUtil.addDaysToDate(new Date(), lastLoginExpirationInDay + 1));
			userBOList = session.createSQLQuery("SELECT U.user_id, UP.permissions FROM users AS U  LEFT JOIN user_permission_mapping AS UPM ON U.user_id  = UPM.user_id LEFT JOIN user_permissions AS "
					+ "UP ON UPM.permission_id  = UP.permission_id  WHERE U.user_login_datetime < '"+lastLoginDateTime+"' AND U.status=1 GROUP BY U.user_id HAVING UP.permissions != 'ROLE_SUPERADMIN'").list();
			
			if(userBOList!=null && !userBOList.isEmpty()){
				userBOIdList = new ArrayList<>();
				for (Object[] objects : userBOList) {
					userBOIdList.add(Integer.parseInt(objects[0]+""));
				}
				if (!userBOIdList.isEmpty()) {
					session.createSQLQuery("Update users set status = 0 WHERE user_id in("+StringUtils.join(userBOIdList, ",")+")").executeUpdate();
				}
			}
			transaction.commit();
		} catch (Exception e) {
			if(transaction != null)
				transaction.rollback();
			logger.error("LoginDAOImpl - passwordLoginBlocked() - ERROR " , e);
		} finally{
			if (session != null && session.isOpen()) {
				session.close();
			}
		}
		logger.info("LoginDAOImpl - passwordLoginBlocked() - Ends");
	}
}
