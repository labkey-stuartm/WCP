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

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

@Repository
public class ManageUsersDAOImpl implements ManageUsersDAO{
	
	private static Logger logger = Logger.getLogger(ManageUsersDAOImpl.class);
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
		logger.info("ManageUsersDAOImpl - getUserList() - Starts");
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
			logger.error("ManageUsersDAOImpl - getUserList() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("ManageUsersDAOImpl - getUserList() - Ends");
		return userList;
	}

	@Override
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser) {
		logger.info("ManageUsersDAOImpl - activateOrDeactivateUser() - Starts");
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
			logger.error("ManageUsersDAOImpl - activateOrDeactivateUser() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("ManageUsersDAOImpl - activateOrDeactivateUser() - Ends");
		return msg;
	}
}
