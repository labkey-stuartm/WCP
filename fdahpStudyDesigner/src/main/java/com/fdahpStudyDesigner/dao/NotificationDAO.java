package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.bo.NotificationHistoryBO;
import com.fdahpStudyDesigner.util.SessionObject;

public interface NotificationDAO {
	
	public List<NotificationBO> getNotificationList(int studyId, String type) throws Exception;
	
	public NotificationBO getNotification(int notificationId) throws Exception;
	
	public Integer saveOrUpdateOrResendNotification(NotificationBO notificationBO, String notificationType, String buttonType, SessionObject sessionObject);
	
	public String deleteNotification(int notificationIdForDelete, SessionObject sessionObject, String notificationType);
	
	public List<NotificationHistoryBO> getNotificationHistoryList(Integer notificationId);
	
	public List<NotificationHistoryBO> getNotificationHistoryListNoDateTime(int notificationId);
	
//	public String resendNotification(Integer notificationId);
	

}
