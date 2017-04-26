package com.fdahpstudydesigner.dao;

import java.util.List;

import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.NotificationHistoryBO;
import com.fdahpstudydesigner.util.SessionObject;

public interface NotificationDAO {
	
	public List<NotificationBO> getNotificationList(int studyId, String type);
	
	public NotificationBO getNotification(int notificationId);
	
	public Integer saveOrUpdateOrResendNotification(NotificationBO notificationBO, String notificationType, String buttonType, SessionObject sessionObject);
	
	public String deleteNotification(int notificationIdForDelete, SessionObject sessionObject, String notificationType);
	
	public List<NotificationHistoryBO> getNotificationHistoryList(Integer notificationId);
	
	public List<NotificationHistoryBO> getNotificationHistoryListNoDateTime(int notificationId);
	

}
