package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;

public interface NotificationService {

	public List<NotificationBO> getNotificationList(Integer studyId, String type) throws Exception;
	
	public NotificationBO getNotification(Integer notificationId) throws Exception;
	
	public Integer saveOrUpdateNotification(NotificationBO notificationBO, String notificationType);
	
	public String deleteNotification(Integer notificationIdForDelete);
	
	public Integer resendNotification(Integer notificationId);
	
}
