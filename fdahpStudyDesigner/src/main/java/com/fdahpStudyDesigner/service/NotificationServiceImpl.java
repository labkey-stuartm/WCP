package com.fdahpStudyDesigner.service;

import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.dao.NotificationDAOImpl;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Service
public class NotificationServiceImpl implements  NotificationService{
	
private static Logger logger = Logger.getLogger(NotificationServiceImpl.class);
	
	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	private NotificationDAOImpl notificationDAOImpl;

	@Autowired
	public void setNotificationDAOImpl(NotificationDAOImpl notificationDAOImpl) {
		this.notificationDAOImpl = notificationDAOImpl;
	}

	@Override
	public List<NotificationBO> getNotificationList() throws Exception {
		logger.info("NotificationServiceImpl - getNotificationList() - Starts");
		List<NotificationBO> notificationList = null;
		try{
			notificationList = notificationDAOImpl.getNotificationList();
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
			notificationBO = notificationDAOImpl.getNotification(notificationId);
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - getNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - getNotification - Ends");
		return notificationBO;
	}
	
	@Override
	public String saveOrUpdateNotification(NotificationBO notificationBO){
		logger.info("NotificationServiceImpl - saveOrUpdateNotification - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try {
			message = notificationDAOImpl.saveOrUpdateNotification(notificationBO);
		} catch (Exception e) {
			logger.error("NotificationServiceImpl - saveOrUpdateNotification - ERROR", e);
		}
		logger.info("NotificationServiceImpl - saveOrUpdateNotification - Ends");
		return message;
	}

}
