package com.fdahpstudydesigner.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.NotificationHistoryBO;
import com.fdahpstudydesigner.dao.NotificationDAO;
import com.fdahpstudydesigner.dao.StudyDAO;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

@Service
public class NotificationServiceImpl implements  NotificationService{
	
private static Logger logger = Logger.getLogger(NotificationServiceImpl.class);
	
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	
	@Autowired
	private StudyDAO studyDAO;

	public void setStudyDAO(StudyDAO studyDAO) {
		this.studyDAO = studyDAO;
	}

	@Override
	public List<NotificationBO> getNotificationList(int studyId, String type) {
		logger.info("NotificationServiceImpl - getNotificationList() - Starts");
		List<NotificationBO> notificationList = null;
		try{
			notificationList = notificationDAO.getNotificationList(studyId, type);
		}catch(Exception e){
			logger.error("NotificationServiceImpl - getNotificationList() - ERROR " , e);
		}
		logger.info("NotificationServiceImpl - getNotificationList() - Ends , e");
		return notificationList;
	}
	
	@Override
	public NotificationBO getNotification(int notificationId){
		logger.info("NotificationServiceImpl - getNotification - Starts");
		NotificationBO notificationBO  = null;
		try {
			notificationBO = notificationDAO.getNotification(notificationId);
			if(null != notificationBO){
				notificationBO.setScheduleDate(FdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleDate())?String.valueOf( FdahpStudyDesignerUtil.getFormattedDate(notificationBO.getScheduleDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE)):"");
				notificationBO.setScheduleTime(FdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleTime())?String.valueOf( FdahpStudyDesignerUtil.getFormattedDate(notificationBO.getScheduleTime(), FdahpStudyDesignerConstants.DB_SDF_TIME, FdahpStudyDesignerConstants.SDF_TIME)):"");
			}
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - getNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - getNotification - Ends");
		return notificationBO;
	}
	
	public List<NotificationHistoryBO> getNotificationHistoryList(Integer notificationId){
		logger.info("NotificationServiceImpl - getNotificationHistoryList() - Starts");
		List<NotificationHistoryBO> notificationHistoryList = null;
		try{
			notificationHistoryList = notificationDAO.getNotificationHistoryList(notificationId);
			if(notificationHistoryList != null && notificationHistoryList.size() > 0){
				for (NotificationHistoryBO notificationHistoryBO : notificationHistoryList) {
					if(notificationHistoryBO.getNotificationSentDateTime()!=null){
						String date = FdahpStudyDesignerUtil.getFormattedDate(notificationHistoryBO.getNotificationSentDateTime(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.UI_SDF_DATE); // 8/29/2011
						String time = FdahpStudyDesignerUtil.getFormattedDate(notificationHistoryBO.getNotificationSentDateTime(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.SDF_TIME); // 11:16:12 AM
						notificationHistoryBO.setNotificationSentdtTime("Last Sent on "+date+" at "+time);
					}
				}
			}
		}catch(Exception e){
			logger.error("NotificationServiceImpl - getNotificationHistoryList - ERROR",e);
			
		}
		logger.info("NotificationServiceImpl - getNotificationHistoryList - Ends");
		return notificationHistoryList;
	}
	
	public List<NotificationHistoryBO> getNotificationHistoryListNoDateTime(int notificationId){
		logger.info("NotificationServiceImpl - getNotificationHistoryListNoDateTime() - Starts");
		List<NotificationHistoryBO> notificationHistoryListNoDateTime = null;
		try{
			notificationHistoryListNoDateTime = notificationDAO.getNotificationHistoryListNoDateTime(notificationId);
			if(notificationHistoryListNoDateTime != null && notificationHistoryListNoDateTime.size() > 0){
				for (NotificationHistoryBO notificationHistoryBO : notificationHistoryListNoDateTime) {
					if(notificationHistoryBO.getNotificationSentDateTime()!=null){
						String date = FdahpStudyDesignerUtil.getFormattedDate(notificationHistoryBO.getNotificationSentDateTime(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.UI_SDF_DATE); // 8/29/2011
						String time = FdahpStudyDesignerUtil.getFormattedDate(notificationHistoryBO.getNotificationSentDateTime(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.SDF_TIME); // 11:16:12 AM
						notificationHistoryBO.setNotificationSentdtTime("Last Sent on "+date+" at "+time);
					}
				}
			}
		}catch(Exception e){
			logger.error("NotificationServiceImpl - getNotificationHistoryListNoDateTime - ERROR",e);
			
		}
		logger.info("NotificationServiceImpl - getNotificationHistoryListNoDateTime - Ends");
		return notificationHistoryListNoDateTime;
	}
	
	@Override
	public Integer saveOrUpdateOrResendNotification(NotificationBO notificationBO, String notificationType, String buttonType, SessionObject sessionObject){
		logger.info("NotificationServiceImpl - saveOrUpdateNotification - Starts");
		Integer notificationId = 0;
		try {
			if(notificationBO != null){
				notificationId = notificationDAO.saveOrUpdateOrResendNotification(notificationBO, notificationType, buttonType, sessionObject);
				if(notificationType.equals(FdahpStudyDesignerConstants.STUDYLEVEL) && !notificationId.equals(0) && !notificationBO.isNotificationAction()){
						studyDAO.markAsCompleted(notificationBO.getStudyId(), FdahpStudyDesignerConstants.NOTIFICATION, false, sessionObject);
				}
			}
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - saveOrUpdateNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - saveOrUpdateNotification - Ends");
		return notificationId;
	}

	@Override
	public String deleteNotification(int notificationIdForDelete, SessionObject sessionObject, String notificationType) {
		logger.info("NotificationServiceImpl - deleteNotification - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		try {
			message = notificationDAO.deleteNotification(notificationIdForDelete, sessionObject, notificationType);
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - deleteNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - deleteNotification - Ends");
		return message;
	}

}
