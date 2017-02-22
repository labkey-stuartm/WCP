package com.fdahpStudyDesigner.dao;

import java.util.ArrayList;
import java.util.HashMap;
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
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Repository
public class NotificationDAOImpl implements NotificationDAO{
	
	private static Logger logger = Logger.getLogger(NotificationDAOImpl.class);
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	@SuppressWarnings("unchecked")
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	private Transaction transaction = null;
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationBO> getNotificationList() throws Exception {
		logger.info("NotificationDAOImpl - getNotificationList() - Starts");
		List<NotificationBO> notificationList = null;
		List<Object[]> objList = null;
		Session session = null; 
		String queryString = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			queryString = "select n.notification_id ,n.study_id ,n.notification_text ,n.schedule_date, n.schedule_time from notification n";
			query = session.createSQLQuery(queryString);
			objList = query.list();
			if(null != objList && objList.size() > 0 ){
				notificationList = new ArrayList<NotificationBO>();
				for(Object[] obj:objList){
					NotificationBO notificationBO = new NotificationBO();
					notificationBO.setNotificationId(null != obj[0] ? (Integer)obj[0] : 0);
					notificationBO.setStudyId(null != obj[1] ? (Integer)(obj[1]) : 0);
					notificationBO.setNotificationText(null != obj[2] ? String.valueOf(obj[2]) : "");
					notificationBO.setScheduleDate(null != obj[3] ? String.valueOf(obj[3]) : "");
					notificationBO.setScheduleTime(null != obj[4] ? String.valueOf(obj[4]) : "");
					notificationList.add(notificationBO);
				}
			}
		}catch(Exception e){
			logger.error("NotificationDAOImpl - getNotificationList() - ERROR" , e);
		}finally{
			session.close();
		}
		logger.info("NotificationDAOImpl - getNotificationList() - Ends");
		return notificationList;
	}

	@Override
	public NotificationBO getNotification(Integer notificationId) throws Exception {
			logger.info("NotificationDAOImpl - getNotification() - Starts");
			Session session = null;
			Query query = null;
			NotificationBO notificationBO = null;
			try{
				session = hibernateTemplate.getSessionFactory().openSession();
				query = session.createQuery("from NotificationBO NBO where NBO.notificationId = " +notificationId);
				notificationBO = (NotificationBO) query.uniqueResult();
				if(null != notificationBO){
					notificationBO.setNotificationId(null != notificationBO.getNotificationId() ? notificationBO.getNotificationId() : 0);
					notificationBO.setNotificationText(null != notificationBO.getNotificationText() ? notificationBO.getNotificationText() : "");
					notificationBO.setScheduleDate(null != notificationBO.getScheduleDate() ? notificationBO.getScheduleDate() : "");
					notificationBO.setScheduleTime(null != notificationBO.getScheduleTime() ? notificationBO.getScheduleTime() : "");
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

	@Override
	public String saveOrUpdateNotification(NotificationBO notificationBO) {
		logger.info("NotificationDAOImpl - saveOrUpdateNotification() - Starts");
		Session session = null;
		NotificationBO notificationBOUpdate = null;
	    String message = fdahpStudyDesignerConstants.FAILURE;
		try{
				session = hibernateTemplate.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				if(notificationBO.getNotificationId() == null) {
					notificationBOUpdate = new NotificationBO();
					notificationBOUpdate.setNotificationText(notificationBO.getNotificationText().trim());
					notificationBOUpdate.setScheduleTime(notificationBO.getScheduleTime().trim());
					notificationBOUpdate.setScheduleDate(notificationBO.getScheduleDate().trim());
					notificationBOUpdate.setNotificationType("GT");
				} else {
					query = session.createQuery(" from NotificationBO NBO where NBO.notificationId = "+notificationBO.getNotificationId());
					notificationBOUpdate = (NotificationBO) query.uniqueResult();
					if (StringUtils.isNotBlank(notificationBO.getNotificationText())) {
						notificationBOUpdate.setNotificationText(notificationBO.getNotificationText().trim());
					}else {
						notificationBOUpdate.setNotificationText(notificationBOUpdate.getNotificationText().trim());
					}
					notificationBOUpdate.setNotificationSent(notificationBO.isNotificationSent());
					notificationBOUpdate.setScheduleTime(notificationBO.getScheduleTime().trim());
					notificationBOUpdate.setScheduleDate(notificationBO.getScheduleDate().trim());
				}
				session.saveOrUpdate(notificationBOUpdate);
				transaction.commit();
				message = fdahpStudyDesignerConstants.SUCCESS;
		} catch(Exception e){
			transaction.rollback();
			logger.error("NotificationDAOImpl - saveOrUpdateNotification - ERROR", e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("NotificationDAOImpl - saveOrUpdateNotification - Ends");
		return message;
	}
	
	@Override
	public String deleteNotification(Integer notificationIdForDelete) {
		logger.info("NotificationDAOImpl - deleteNotification() - Starts");
		Session session = null;
	    String message = fdahpStudyDesignerConstants.FAILURE;
	    int i = 0;
		try{
				session = hibernateTemplate.getSessionFactory().openSession();
				transaction = session.beginTransaction();
				if(notificationIdForDelete != null){
					query = session.createQuery("delete from NotificationBO NBO where NBO.notificationId = " +notificationIdForDelete);
					i = query.executeUpdate();
					if(i == 0){
						message = fdahpStudyDesignerConstants.FAILURE;
					}
				}
				transaction.commit();
				message = fdahpStudyDesignerConstants.SUCCESS;
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
	
//	public String resendNotification(Integer notificationId){
//		logger.info("NotificationDAOImpl - resendNotification() - Starts");
//		Session session = null;
//	    String message = fdahpStudyDesignerConstants.FAILURE;
//	    NotificationBO notificationBO = null;
//	    NotificationBO notification = null;
//		try{
//				session = hibernateTemplate.getSessionFactory().openSession();
//				transaction = session.beginTransaction();
//				if(notificationId != null){
//					query = session.createQuery(" from NotificationBO NBO where NBO.notificationId = "+notificationId);
//					notificationBO = (NotificationBO) query.uniqueResult();
//					if(notificationBO != null){
//						notification = new NotificationBO();
//						notification.setNotificationText(notificationBO.getNotificationText());
//						notification.setScheduleDate(fdahpStudyDesignerUtil.getCurrentDate());
//						notification.setScheduleTime(fdahpStudyDesignerUtil.getCurrentTime());
//						notification.setNotificationSent(false);
//						notification.setNotificationType("GT");
//						session.saveOrUpdate(notification);
//					}
//				}
//				transaction.commit();
//				message = fdahpStudyDesignerConstants.SUCCESS;
//		} catch(Exception e){
//			transaction.rollback();
//			logger.error("NotificationDAOImpl - resendNotification - ERROR", e);
//		}finally{
//			if(null != session){
//				session.close();
//			}
//		}
//		logger.info("NotificationDAOImpl - resendNotification - Ends");
//		return message;
//	}
}
