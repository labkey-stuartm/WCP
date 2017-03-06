package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;

public interface NotificationService {

	public List<NotificationBO> getNotificationList(String type) throws Exception;
	
	public NotificationBO getNotification(Integer notificationId) throws Exception;
	
	public String saveOrUpdateNotification(NotificationBO notificationBO);
	
	public String deleteNotification(Integer notificationIdForDelete);
	
	public String resendNotification(Integer notificationId);
	
}
