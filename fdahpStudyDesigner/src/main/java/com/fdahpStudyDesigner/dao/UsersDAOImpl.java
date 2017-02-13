package com.fdahpStudyDesigner.dao;

import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

@Repository
public class UsersDAOImpl implements UsersDAO{
	
	private static Logger logger = Logger.getLogger(UsersDAOImpl.class);
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBO> getUserList() {
		logger.info("UsersDAOImpl - getUserList() - Starts");
		Session session = null;
		List<UserBO> userList = null;
		List<Object[]> objList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createSQLQuery(" SELECT u.first_name,u.last_name,u.email,r.role_name FROM users u,roles r WHERE r.role_id = u.role_id ");
			objList = query.list();
			if(null != objList && objList.size() > 0){
				userList = new ArrayList<UserBO>();
				for(Object[] obj:objList){
					UserBO userBO = new UserBO();
					userBO.setFirstName(null != obj[0] ? String.valueOf(obj[0]) : "");
					userBO.setLastName(null != obj[1] ? String.valueOf(obj[1]) : "");
					userBO.setUserEmail(null != obj[2] ? String.valueOf(obj[2]) : "");
					userBO.setRoleName(null != obj[3] ? String.valueOf(obj[3]) : "");
					userList.add(userBO);
				}
			}
		}catch(Exception e){
			logger.error("UsersDAOImpl - getUserList() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getUserList() - Ends");
		return userList;
	}

	@Override
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser) {
		logger.info("UsersDAOImpl - activateOrDeactivateUser() - Starts");
		String msg = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(userStatus == 0){
				userStatus = 1;
			}else{
				userStatus = 0;
			}
			query = session.createQuery(" UPDATE UserBO SET enabled = "+userStatus+" modifiedOn = now() AND modifiedBy = "+loginUser+" WHERE userId = "+userId );
			count = query.executeUpdate();
			transaction.commit();
			if(count > 0){
				msg = fdahpStudyDesignerConstants.SUCCESS;
			}
		}catch(Exception e){
			logger.error("UsersDAOImpl - activateOrDeactivateUser() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - activateOrDeactivateUser() - Ends");
		return msg;
	}

	@Override
	public UserBO getUserDetails(int userId) {
		logger.info("UsersDAOImpl - getUserDetails() - Starts");
		Session session = null;
		UserBO userBO = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getUserById").setInteger("userId", userId);
			userBO = (UserBO) query.uniqueResult();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getUserDetails() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getUserDetails() - Ends");
		return userBO;
	}

	@Override
	public RoleBO getUserRole(int roleId) {
		logger.info("UsersDAOImpl - getUserRole() - Starts");
		Session session = null;
		RoleBO roleBO = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getUserRoleByRoleId").setInteger("roleId", roleId);
			roleBO = (RoleBO) query.uniqueResult();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getUserRole() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getUserRole() - Ends");
		return roleBO;
	}
}
