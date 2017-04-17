package com.fdahpStudyDesigner.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.bo.NotificationHistoryBO;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Repository
public class NotificationDAOImpl implements NotificationDAO{
	
	private static Logger logger = Logger.getLogger(NotificationDAOImpl.class);
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@Autowired
	private AuditLogDAO auditLogDAO;
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationBO> getNotificationList(int studyId, String type) throws Exception {
		logger.info("NotificationDAOImpl - getNotificationList() - Starts");
		List<NotificationBO> notificationList = null;
		Session session = null; 
		String queryString = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(fdahpStudyDesignerConstants.STUDYLEVEL.equals(type) && studyId !=0){
				queryString = "from NotificationBO NBO where NBO.studyId = "+studyId+" and NBO.notificationType = 'ST' and NBO.notificationStatus = 0 order by NBO.notificationId desc";
				query = session.createQuery(queryString);
				notificationList = query.list();
			}else {
				queryString = "from NotificationBO NBO where NBO.studyId = "+studyId+" and NBO.notificationType = 'GT' and NBO.notificationStatus = 0 order by NBO.notificationId desc";
				query = session.createQuery(queryString);
				notificationList = query.list();
			}
		}catch(Exception e){
			logger.error("NotificationDAOImpl - getNotificationList() - ERROR" , e);
		}finally{
			if(null!=session){
				session.close();
			}
		}
		logger.info("NotificationDAOImpl - getNotificationList() - Ends");
		return notificationList;
	}

	@Override
	public NotificationBO getNotification(int notificationId) throws Exception {
			logger.info("NotificationDAOImpl - getNotification() - Starts");
			Session session = null;
			String queryString = null;
			NotificationBO notificationBO = null;
			try{
				session = hibernateTemplate.getSessionFactory().openSession();
				queryString = "from NotificationBO NBO where NBO.notificationId = " +notificationId;
				query = session.createQuery(queryString);
				notificationBO = (NotificationBO) query.uniqueResult();
				if(null != notificationBO){
					notificationBO.setNotificationId(null != notificationBO.getNotificationId() ? notificationBO.getNotificationId() : 0);
					notificationBO.setNotificationText(null != notificationBO.getNotificationText() ? notificationBO.getNotificationText() : "");
					notificationBO.setScheduleDate(null != notificationBO.getScheduleDate() ? notificationBO.getScheduleDate() : "");
					notificationBO.setScheduleTime(null != notificationBO.getScheduleTime() ? notificationBO.getScheduleTime() : "");
					notificationBO.setNotificationSent(notificationBO.isNotificationSent());
					notificationBO.setNotificationScheduleType(null != notificationBO.getNotificationScheduleType() ? notificationBO.getNotificationScheduleType() : "");
					if("immediate".equalsIgnoreCase(notificationBO.getNotificationScheduleType())){
						notificationBO.setScheduleDate("");
						notificationBO.setScheduleTime("");
					}
				}
			}catch(Exception e){
				logger.error("NotificationDAOImpl - getNotification - ERROR",e);
			}finally{
				if(null != session){
					session.close();
				}
			}
			logger.info("NotificationDAOImpl - getNotification - Ends");
			return notificationBO;
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationHistoryBO> getNotificationHistoryList(Integer notificationId){
			logger.info("NotificationDAOImpl - getNotificationHistoryList() - Starts");
			Session session = null;
			String queryString = null;
			List<NotificationHistoryBO> notificationHistoryList = null;
			try{
				session = hibernateTemplate.getSessionFactory().openSession();
				queryString = "from NotificationHistoryBO NHBO where NHBO.notificationId = " +notificationId;
				query = session.createQuery(queryString);
				notificationHistoryList = query.list();
			}catch(Exception e){
				logger.error("NotificationDAOImpl - getNotificationHistoryList - ERROR",e);
			}finally{
				if(null != session){
					session.close();
				}
			}
			logger.info("NotificationDAOImpl - getNotificationHistoryList - Ends");
			return notificationHistoryList;
		}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationHistoryBO> getNotificationHistoryListNoDateTime(int notificationId){
			logger.info("NotificationDAOImpl - getNotificationHistoryListNoDateTime() - Starts");
			Session session = null;
			String queryString = null;
			List<NotificationHistoryBO> notificationHistoryListNoDateTime = null;
			try{
				session = hibernateTemplate.getSessionFactory().openSession();
				queryString = "from NotificationHistoryBO NHBO where NHBO.notificationSentDateTime <> null and NHBO.notificationId = " +notificationId;
				query = session.createQuery(queryString);
				notificationHistoryListNoDateTime = query.list();
			}catch(Exception e){
				logger.error("NotificationDAOImpl - getNotificationHistoryListNoDateTime - ERROR",e);
			}finally{
				if(null != session){
					session.close();
				}
			}
			logger.info("NotificationDAOImpl - getNotificationHistoryListNoDateTime - Ends");
			return notificationHistoryListNoDateTime;
		}
	
	
	@Override
	public Integer saveOrUpdateOrResendNotification(NotificationBO notificationBO, String notificationType, String buttonType, SessionObject sessionObject) {
		logger.info("NotificationDAOImpl - saveOrUpdateOrResendNotification() - Starts");
		Session session = null;
		NotificationBO notificationBOUpdate = null;
		Integer notificationId = 0;
		NotificationHistoryBO notificationHistoryBO = null;
		try{
				session = hibernateTemplate.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				if(notificationBO.getNotificationId() == null) {
					notificationBOUpdate = new NotificationBO();
					notificationBOUpdate.setNotificationText(notificationBO.getNotificationText().trim());
					notificationBOUpdate.setCreatedBy(notificationBO.getCreatedBy());
					notificationBOUpdate.setCreatedOn(notificationBO.getCreatedOn());
					notificationBOUpdate.setNotificationScheduleType(notificationBO.getNotificationScheduleType());
					if("".equals(notificationBO.getScheduleTime())){
						notificationBOUpdate.setScheduleTime(null);
					}else {
						notificationBOUpdate.setScheduleTime(notificationBO.getScheduleTime());
					}
					if("".equals(notificationBO.getScheduleDate())){
						notificationBOUpdate.setScheduleDate(null);
					}else {
						notificationBOUpdate.setScheduleDate(notificationBO.getScheduleDate());
					}
					
					if(notificationType.equals(fdahpStudyDesignerConstants.STUDYLEVEL)){
						notificationBOUpdate.setNotificationDone(notificationBO.isNotificationDone());
						notificationBOUpdate.setNotificationType("ST");
						notificationBOUpdate.setStudyId(notificationBO.getStudyId());
						notificationBOUpdate.setNotificationAction(notificationBO.isNotificationAction());
					}else{
						notificationBOUpdate.setNotificationType("GT");
						notificationBOUpdate.setStudyId(0);
						notificationBOUpdate.setNotificationAction(false);
						notificationBOUpdate.setNotificationDone(true);
					}
					notificationId = (Integer) session.save(notificationBOUpdate);
					
					notificationHistoryBO = new NotificationHistoryBO();
					notificationHistoryBO.setNotificationId(notificationId);
					session.save(notificationHistoryBO);
					
				} else {
					query = session.createQuery(" from NotificationBO NBO where NBO.notificationId = "+notificationBO.getNotificationId());
					notificationBOUpdate = (NotificationBO) query.uniqueResult();
					
					if (StringUtils.isNotBlank(notificationBO.getNotificationText())) {
						notificationBOUpdate.setNotificationText(notificationBO.getNotificationText().trim());
					}else {
						notificationBOUpdate.setNotificationText(notificationBOUpdate.getNotificationText().trim());
					}
					notificationBOUpdate.setModifiedBy(notificationBO.getModifiedBy());
					notificationBOUpdate.setModifiedOn(notificationBO.getModifiedOn());
					notificationBOUpdate.setStudyId(notificationBOUpdate.getStudyId());
					notificationBOUpdate.setNotificationSent(notificationBO.isNotificationSent());
					notificationBOUpdate.setNotificationScheduleType(notificationBO.getNotificationScheduleType());
					if(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleTime())){
						notificationBOUpdate.setScheduleTime(notificationBO.getScheduleTime());
					}else{
						notificationBOUpdate.setScheduleTime(null);
					}
					if(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleDate())){
						notificationBOUpdate.setScheduleDate(notificationBO.getScheduleDate());
					}else{
						notificationBOUpdate.setScheduleDate(null);
					}
					if(notificationType.equals(fdahpStudyDesignerConstants.STUDYLEVEL)){
						notificationBOUpdate.setNotificationDone(notificationBO.isNotificationDone());
						notificationBOUpdate.setNotificationType("ST");
						notificationBOUpdate.setNotificationAction(notificationBO.isNotificationAction());
					}else{
						notificationBOUpdate.setNotificationDone(notificationBOUpdate.isNotificationDone());
						notificationBOUpdate.setNotificationType("GT");
						notificationBOUpdate.setNotificationAction(notificationBOUpdate.isNotificationAction());
					}
					session.update(notificationBOUpdate);
					notificationId = notificationBOUpdate.getNotificationId(); 
					session.flush();
					
					if("resend".equals(buttonType)){
						notificationHistoryBO = new NotificationHistoryBO();
						notificationHistoryBO.setNotificationId(notificationBOUpdate.getNotificationId());
						session.save(notificationHistoryBO);
						
					}
				}
				transaction.commit();
				 if(notificationId!=null){
					 String activitydetails = "";
					 if("add".equals(buttonType)){
						 activitydetails = "Notification created";
					 }else if("update".equals(buttonType)){
						 activitydetails = "Notification updated";
					 }else if("resend".equals(buttonType)){
						 activitydetails = "Notification resend";
					 }else if("save".equals(buttonType)){
						 activitydetails = "Notification saved but not eligible for mark as completed action untill unless it is DONE";
					 }else if("done".equals(buttonType)){
						 activitydetails = "Notification done and eligible for mark as completed action";
					 }
					 auditLogDAO.saveToAuditLog(session, sessionObject, notificationType, activitydetails, "NotificationDAOImpl - saveOrUpdateOrResendNotification");
				 }
		} catch(Exception e){
			transaction.rollback();
			logger.error("NotificationDAOImpl - saveOrUpdateOrResendNotification() - ERROR", e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("NotificationDAOImpl - saveOrUpdateOrResendNotification - Ends");
		return notificationId;
	}
	
	@Override
	public String deleteNotification(int notificationIdForDelete, SessionObject sessionObject, String notificationType) {
		logger.info("NotificationDAOImpl - deleteNotification() - Starts");
		Session session = null;
	    String message = fdahpStudyDesignerConstants.FAILURE;
	    String queryString="";
	    int i = 0;
		try{
				session = hibernateTemplate.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				if(notificationIdForDelete != 0){
					queryString = "update NotificationBO NBO set NBO.modifiedBy = "+sessionObject.getUserId()+", NBO.modifiedOn = now(), NBO.notificationStatus = 1 ,NBO.notificationDone = 1 ,NBO.notificationAction = 1 where NBO.notificationId = " +notificationIdForDelete;
						query = session.createQuery(queryString);
						i = query.executeUpdate();
						if(i > 0){
							message = fdahpStudyDesignerConstants.SUCCESS;
						}
				}
				transaction.commit();
				message = auditLogDAO.saveToAuditLog(session, sessionObject, notificationType, "Notification deleted","NotificationDAOImpl - deleteNotification");
		} catch(Exception e){
			transaction.rollback();
			logger.error("NotificationDAOImpl - deleteNotification - ERROR", e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("NotificationDAOImpl - deleteNotification - Ends");
		return message;
	}
	
}
