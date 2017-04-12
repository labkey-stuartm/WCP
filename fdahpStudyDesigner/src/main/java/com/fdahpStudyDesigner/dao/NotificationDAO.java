package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.bo.NotificationHistoryBO;
import com.fdahpStudyDesigner.util.SessionObject;

public interface NotificationDAO {
	
	public List<NotificationBO> getNotificationList(Integer studyId, String type) throws Exception;
	
	public NotificationBO getNotification(Integer notificationId) throws Exception;
	
	public Integer saveOrUpdateOrResendNotification(NotificationBO notificationBO, String notificationType, String buttonType, SessionObject sessionObject);
	
	public String deleteNotification(Integer notificationIdForDelete, SessionObject sessionObject, String notificationType);
	
	public List<NotificationHistoryBO> getNotificationHistoryList(Integer notificationId);
	
	public List<NotificationHistoryBO> getNotificationHistoryListNoDateTime(Integer notificationId);
	
//	public String resendNotification(Integer notificationId);
	

}
