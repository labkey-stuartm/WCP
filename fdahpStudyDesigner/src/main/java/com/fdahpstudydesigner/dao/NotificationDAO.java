package com.fdahpstudydesigner.dao;

import com.fdahpstudydesigner.bean.PushNotificationBean;
import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.NotificationHistoryBO;
import com.fdahpstudydesigner.bo.NotificationLangBO;
import com.fdahpstudydesigner.util.SessionObject;
import java.util.List;

public interface NotificationDAO {

  public String deleteNotification(
      int notificationIdForDelete, SessionObject sessionObject, String notificationType);

  public NotificationBO getNotification(int notificationId);

  public List<NotificationHistoryBO> getNotificationHistoryListNoDateTime(int notificationId);

  public List<NotificationBO> getNotificationList(int studyId, String type);

  public List<PushNotificationBean> getPushNotificationList(String date, String time);

  public Integer saveOrUpdateOrResendNotification(
      NotificationBO notificationBO,
      String notificationType,
      String buttonType,
      SessionObject sessionObject);

  public List<String> getGatwayAppList();

  NotificationLangBO getNotificationLang(int notificationId, String lang);

  List<NotificationLangBO> getNotificationLangList(int studyId, String langCode);

  List<NotificationLangBO> getNotificationLangByNotificationId(int notificationId);
}
