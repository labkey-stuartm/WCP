package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;

public interface NotificationService {

	public List<NotificationBO> getNotificationList() throws Exception;
	
	public NotificationBO getNotification(Integer notificationId) throws Exception;
	
	public String saveOrUpdateNotification(NotificationBO notificationBO);
	
}
