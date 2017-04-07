package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.bo.NotificationHistoryBO;

public interface NotificationDAO {
	
	public List<NotificationBO> getNotificationList(Integer studyId, String type) throws Exception;
	
	public NotificationBO getNotification(Integer notificationId) throws Exception;
	
	public Integer saveOrUpdateNotification(NotificationBO notificationBO, String notificationType, String buttonType);
	
	public String deleteNotification(Integer notificationIdForDelete);
	
	public List<NotificationHistoryBO> getNotificationHistoryList(Integer notificationId);
	
//	public String resendNotification(Integer notificationId);
	

}
