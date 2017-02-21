package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;

public interface NotificationDAO {
	
	public List<NotificationBO> getNotificationList() throws Exception;
	
	public NotificationBO getNotification(Integer notificationId) throws Exception;
	
	public String saveOrUpdateNotification(NotificationBO notificationBO);
	
	public String deleteNotification(Integer notificationIdForDelete);
	
//	public String resendNotification(Integer notificationId);
	

}
