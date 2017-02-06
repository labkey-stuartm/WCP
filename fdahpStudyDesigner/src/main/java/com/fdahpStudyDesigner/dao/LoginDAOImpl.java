package com.fdahpStudyDesigner.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.security.authentication.LockedException;
import org.springframework.stereotype.Repository;

import com.fdahpStudyDesigner.bo.UserAttemptsBo;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.bo.UserPasswordHistory;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Repository
public class LoginDAOImpl implements LoginDAO {
	
	private static Logger logger = Logger.getLogger(LoginDAOImpl.class.getName());
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction trans = null;
	public LoginDAOImpl() {
	}
	
	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
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
		} catch (Exception e) {
			userBo = null;
			logger.error("LoginDAOImpl - getValidUserByEmail() - ERROR ", e);
		} finally {
			session.close();
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
	public String changePassword(Integer userId, String newPassword, String oldPassword) throws Exception{
		logger.info("LoginDAOImpl - changePassword() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		UserBO adminUserBO = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			trans = session.beginTransaction();
			query = session.getNamedQuery("getUserByUserId").setInteger("userId", userId);
			adminUserBO = (UserBO) query.uniqueResult();
			if(null != adminUserBO && fdahpStudyDesignerUtil.compairEncryptedPassword(adminUserBO.getUserPassword(), oldPassword)){
				newPassword = fdahpStudyDesignerUtil.getEncryptedPassword(newPassword);
				if(null != newPassword && !"".equals(newPassword)){
					adminUserBO.setUserPassword(newPassword);
				}
				adminUserBO.setModifiedBy(userId);
				adminUserBO.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDate());
				adminUserBO.setPasswordExpairdedDateTime(fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.format(new Date()));
				session.update(adminUserBO);
				trans.commit();
				message = fdahpStudyDesignerConstants.SUCCESS;
			} else {
				message = propMap.get("invalid.oldpassword.msg");
			}
		} catch (Exception e) {
			logger.error("LoginDAOImpl - changePassword() - ERROR " , e);
			trans.rollback();
		} finally {
			session.close();
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
		String result = fdahpStudyDesignerConstants.FAILURE;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			trans = session.beginTransaction();
			session.saveOrUpdate(userBO);
			trans.commit();
			this.resetFailAttempts(userBO.getUserEmail());
			result = fdahpStudyDesignerConstants.SUCCESS;
		} catch (Exception e) {
			trans.rollback();
			logger.error("LoginDAOImpl - updateUser() - ERROR " , e);
		} finally {
			session.close();
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
			session.close();
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
		Transaction transaction = null;
		String queryString = null;
		Boolean isAcountLocked = false;
		final Integer MAX_ATTEMPTS = Integer.valueOf(propMap.get("max.login.attempts"));
		try {
			attemptsBo = this.getUserAttempts(userEmailId);
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(attemptsBo == null){
				if (this.isUserExists(userEmailId)) {
					attemptsBo = new UserAttemptsBo();
					attemptsBo.setAttempts(1);
					attemptsBo.setUserEmail(userEmailId);
					attemptsBo.setLastModified(fdahpStudyDesignerUtil.getCurrentDateTime());
					session.save(attemptsBo);
				}
			} else {
				if (attemptsBo.getAttempts() + 1 >= MAX_ATTEMPTS) {
					// locked user
					queryString = "UPDATE UserBO set accountNonLocked = " + 0 + " where userEmail ='" + userEmailId+"'";
					session.createQuery(queryString).executeUpdate();
					// throw exception
					isAcountLocked = true;
				}
				if (this.isUserExists(userEmailId)) {
					attemptsBo.setAttempts(attemptsBo.getAttempts() + 1);
					attemptsBo.setUserEmail(userEmailId);
					attemptsBo.setLastModified(fdahpStudyDesignerUtil.getCurrentDateTime());
					session.update(attemptsBo);
				}
			}
			transaction.commit();
			if(isAcountLocked){
				throw new LockedException("User Account is locked!");
			}
		}
		catch (HibernateException e) {
			transaction.rollback();
			logger.error("LoginDAOImpl - updateUser() - ERROR " , e);
		} finally {
			if(session.isOpen()){
				session.close();
			}
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
		Transaction transaction = null;
		try {
			attemptsBo  = this.getUserAttempts(userEmailId);
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(attemptsBo != null){
				session.delete(attemptsBo);
				transaction.commit();
			}
		} catch (Exception e) {
			transaction.rollback();
			logger.error("LoginDAOImpl - resetFailAttempts() - ERROR " , e);
		} finally {
			session.close();
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
			session.close();
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
	public Boolean isUserEnabled(Integer userId) throws Exception {
		logger.info("LoginDAOImpl - isUserExists() - Starts");
		UserBO userBo = null;
		boolean result = false;
		Session session = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				userBo = (UserBO) session.getNamedQuery("getUserByUserId").setInteger("userId", userId).uniqueResult();
				if (userBo != null) {
					result = userBo.isEnabled();
				}
			}
			
		} catch (Exception e) {
			logger.error("LoginDAOImpl - isUserExists() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("LoginDAOImpl - isUserExists() - Ends");
		return result;
	}

	/**
	 * Update the new password in password history
	 * @author Vivek
	 * 
	 * @param userId , The user id of user
	 * @return {@link String} , the status AcuityLinkConstants.SUCCESS or AcuityLinkConstants.FAILURE
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String updatePasswordHistory(Integer userId, String userPassword) throws Exception {
		logger.info("LoginDAOImpl - updatePasswordHistory() - Starts");
		List<UserPasswordHistory>  passwordHistories= null;
		UserPasswordHistory savePasswordHistory = null;
		String result = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Integer passwordHistoryCount = Integer.parseInt(propMap.get("password.history.count"));
		Transaction transaction = null;
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
				savePasswordHistory.setCreatedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
				savePasswordHistory.setUserId(userId);
				savePasswordHistory.setUserPassword(userPassword);
				session.save(savePasswordHistory);
				transaction.commit();
				result = fdahpStudyDesignerConstants.SUCCESS;
			}
			
		} catch (Exception e) {
			transaction.rollback();
			logger.error("LoginDAOImpl - updatePasswordHistory() - ERROR " , e);
		} finally{
			session.close();
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
	public List<UserPasswordHistory> getPasswordHistory(Integer userId)
			throws Exception {
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
			session.close();
		}
		logger.info("LoginDAOImpl - updatePasswordHistory() - Ends");
		return passwordHistories;
	}
}
