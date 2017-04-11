package com.fdahpStudyDesigner.dao;

import org.antlr.runtime.DFA;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fdahpStudyDesigner.bo.AuditLogBO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Repository
public class AuditLogDAOImpl implements AuditLogDAO{
	
	
	private static Logger logger = Logger.getLogger(AuditLogDAOImpl.class);
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	

	@Override
	public String saveToAuditLog(Integer userId, String activity, String activityDetails, String classMethodName,String createdDateTime){
		logger.info("AuditLogDAOImpl - saveToAuditLog() - Starts");
		Session session = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		AuditLogBO auditLog = null;
		try{
				session = hibernateTemplate.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				if(userId != null && fdahpStudyDesignerUtil.isNotEmpty(activity) && fdahpStudyDesignerUtil.isNotEmpty(activity)
						&& fdahpStudyDesignerUtil.isNotEmpty(activityDetails) && fdahpStudyDesignerUtil.isNotEmpty(classMethodName) 
						&& fdahpStudyDesignerUtil.isNotEmpty(createdDateTime)){
					auditLog = new AuditLogBO();
					auditLog.setActivity(activity);
					auditLog.setActivityDetails(activityDetails);
					auditLog.setUserId(userId);
					auditLog.setClassMethodName(classMethodName);
					auditLog.setCreatedDateTime(createdDateTime);
					session.save(auditLog);
					transaction.commit();
					message = fdahpStudyDesignerConstants.SUCCESS;
				}
		} catch(Exception e){
			transaction.rollback();
			logger.error("AuditLogDAOImpl - saveToAuditLog - ERROR", e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("AuditLogDAOImpl - saveToAuditLog - Ends");
		return message;
	}

}
