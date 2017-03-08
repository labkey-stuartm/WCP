package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;

public interface NotificationDAO {
	
	public List<NotificationBO> getNotificationList(String type) throws Exception;
	
	public NotificationBO getNotification(Integer notificationId) throws Exception;
	
	public String saveOrUpdateNotification(NotificationBO notificationBO, String notificationType);
	
	public String deleteNotification(Integer notificationIdForDelete);
	
//	public String resendNotification(Integer notificationId);
	

}
