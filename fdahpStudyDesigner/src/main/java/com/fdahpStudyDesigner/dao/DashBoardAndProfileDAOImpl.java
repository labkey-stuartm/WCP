package com.fdahpStudyDesigner.dao;

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
public class DashBoardAndProfileDAOImpl implements DashBoardAndProfileDAO{
	
	private static Logger logger = Logger.getLogger(DashBoardAndProfileDAOImpl.class);
	HibernateTemplate hibernateTemplate;

	private Transaction trans = null;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Override
	public String updateProfileDetails(UserBO userBO ,int userId) {
		logger.info("DashBoardAndProfileDAOImpl - updateProfileDetails() - Starts");
		Session session = null;
	    Query query = null;
	    String message = fdahpStudyDesignerConstants.FAILURE;
	    UserBO userBO1 = null;
		try{
				session = hibernateTemplate.getSessionFactory().openSession();
				trans = session.beginTransaction();
				/*-------------------------Update AcuityAdmin-----------------------*/
				query = session.createQuery(" from UserBO UBO where UBO.userId = " + userId + " ");
				userBO1 = (UserBO) query.uniqueResult();
				userBO1.setFirstName(null != userBO.getFirstName().trim() ? userBO.getFirstName().trim() : "");
				userBO1.setLastName(null != userBO.getLastName().trim() ? userBO.getLastName().trim() : "");
				userBO1.setUserEmail(null != userBO.getUserEmail().trim() ? userBO.getUserEmail().trim() : "");
				userBO1.setPhoneNumber(null != userBO.getPhoneNumber().trim() ? userBO.getPhoneNumber().trim() : "");
				userBO1.setModifiedBy(null != userBO.getModifiedBy() ? userBO.getModifiedBy() : 0);
				userBO1.setModifiedOn(null != userBO.getModifiedOn() ? userBO.getModifiedOn() : "");
				session.update(userBO1);
				trans.commit();
				message = fdahpStudyDesignerConstants.SUCCESS;
		}catch(Exception e){
			trans.rollback();
			logger.error("DashBoardAndProfileDAOImpl - updateProfileDetails - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("DashBoardAndProfileDAOImpl - updateProfileDetails - Ends");
		return message;
	}

}
