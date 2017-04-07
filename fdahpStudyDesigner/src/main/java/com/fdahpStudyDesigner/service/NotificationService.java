package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.bo.NotificationHistoryBO;

public interface NotificationService {

	public List<NotificationBO> getNotificationList(Integer studyId, String type) throws Exception;
	
	public NotificationBO getNotification(Integer notificationId) throws Exception;
	
	public Integer saveOrUpdateNotification(NotificationBO notificationBO, String notificationType, String buttonType);
	
	public String deleteNotification(Integer notificationIdForDelete);
	
	public List<NotificationHistoryBO> getNotificationHistoryList(Integer notificationId);
	
	/*public Integer resendNotification(Integer notificationId);*/
	
}
