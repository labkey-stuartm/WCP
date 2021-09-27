package com.fdahpstudydesigner.service;

import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.NotificationHistoryBO;
import com.fdahpstudydesigner.bo.NotificationLangBO;
import com.fdahpstudydesigner.util.SessionObject;
import java.util.List;

public interface NotificationService {

  public String deleteNotification(
      int notificationIdForDelete, SessionObject sessionObject, String notificationType);

  public NotificationBO getNotification(int notificationId);

  public List<NotificationHistoryBO> getNotificationHistoryListNoDateTime(int notificationId);

  public List<NotificationBO> getNotificationList(int studyId, String type);

  List<NotificationLangBO> getNotificationLangList(
      int studyId, String langCode, List<NotificationBO> notificationBOList, int userId);

  public Integer saveOrUpdateOrResendNotification(
      NotificationBO notificationBO,
      String notificationType,
      String buttonType,
      SessionObject sessionObject,
      String customStudyId,
      String language);

  public List<String> getGatwayAppList();

  NotificationLangBO getNotificationLang(int notificationId, String lang);
}
