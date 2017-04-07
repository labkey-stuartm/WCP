package com.fdahpStudyDesigner.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.dao.NotificationDAO;
import com.fdahpStudyDesigner.dao.StudyDAO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

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
	public List<NotificationBO> getNotificationList(Integer studyId, String type) throws Exception {
		logger.info("NotificationServiceImpl - getNotificationList() - Starts");
		List<NotificationBO> notificationList = null;
		try{
			notificationList = notificationDAO.getNotificationList(studyId, type);
			/*if(null != notificationList && notificationList.size() > 0){
				for(NotificationBO notificationBO : notificationList){
					notificationBO.setScheduleDate(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleDate())?String.valueOf(fdahpStudyDesignerConstants.UI_SDF_DATE.format(fdahpStudyDesignerConstants.DB_SDF_DATE.parse(notificationBO.getScheduleDate()))):"");
					notificationBO.setScheduleTime(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleTime())?String.valueOf(fdahpStudyDesignerConstants.UI_SDF_TIME.format(fdahpStudyDesignerConstants.DB_SDF_TIME.parse(notificationBO.getScheduleTime()))):"");
				}
			}*/
		}catch(Exception e){
			logger.error("NotificationServiceImpl - getNotificationList() - ERROR " , e);
		}
		logger.info("NotificationServiceImpl - getNotificationList() - Ends , e");
		return notificationList;
	}
	
	@Override
	public NotificationBO getNotification(Integer notificationId){
		logger.info("NotificationServiceImpl - getNotification - Starts");
		NotificationBO notificationBO  = null;
		try {
			notificationBO = notificationDAO.getNotification(notificationId);
			if(null != notificationBO){
				notificationBO.setScheduleDate(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleDate())?String.valueOf(fdahpStudyDesignerConstants.UI_SDF_DATE.format(fdahpStudyDesignerConstants.DB_SDF_DATE.parse(notificationBO.getScheduleDate()))):"");
				notificationBO.setScheduleTime(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleTime())?String.valueOf(fdahpStudyDesignerConstants.SDF_TIME.format(fdahpStudyDesignerConstants.DB_SDF_TIME.parse(notificationBO.getScheduleTime()))):"");
			}
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - getNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - getNotification - Ends");
		return notificationBO;
	}
	
	@Override
	public Integer saveOrUpdateNotification(NotificationBO notificationBO, String notificationType){
		logger.info("NotificationServiceImpl - saveOrUpdateNotification - Starts");
		Integer notificationId = 0;
		try {
			if(notificationBO != null){
				notificationId = notificationDAO.saveOrUpdateNotification(notificationBO, notificationType);
				if(notificationType.equals("studyNotification")){
					if(notificationId.equals(fdahpStudyDesignerConstants.SUCCESS) && !notificationBO.isNotificationAction()){
						studyDAO.markAsCompleted(notificationBO.getStudyId(), fdahpStudyDesignerConstants.NOTIFICATION, false);
					}
				}
			}
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - saveOrUpdateNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - saveOrUpdateNotification - Ends");
		return notificationId;
	}

	@Override
	public String deleteNotification(Integer notificationIdForDelete) {
		logger.info("NotificationServiceImpl - deleteNotification - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try {
			message = notificationDAO.deleteNotification(notificationIdForDelete);
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - deleteNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - deleteNotification - Ends");
		return message;
	}
	
	public Integer resendNotification(Integer notificationId) {
		logger.info("NotificationServiceImpl - resendNotification - Starts");
		Integer notificationResendId = 0;
		NotificationBO notificationBO = null;
		try {
			notificationBO = new NotificationBO();
			notificationBO.setNotificationId(notificationId);
			notificationBO.setScheduleDate(fdahpStudyDesignerUtil.getCurrentDate());
			notificationBO.setScheduleTime(fdahpStudyDesignerUtil.getCurrentTime());
			notificationResendId = notificationDAO.saveOrUpdateNotification(notificationBO,"");
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - resendNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - resendNotification - Ends");
		return notificationResendId;
	}

}
