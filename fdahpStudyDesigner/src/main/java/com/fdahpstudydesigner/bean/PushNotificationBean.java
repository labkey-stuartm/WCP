/**
 * 
 */
package com.fdahpstudydesigner.bean;

import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;


/**
 * @author Vivek
 *
 */
public class PushNotificationBean {

	private Integer notificationId = 0;
	
	private String notificationText = "";

	private String customStudyId = "";

	private String notificationType = "ST";

	private String notificationSubType = "Announcement";

	private String notificationTitle = FdahpStudyDesignerUtil.getAppProperties().get("push.notification.title");
	
	/**
	 * @return the notificationText
	 */
	public String getNotificationText() {
		return notificationText;
	}

	/**
	 * @param notificationText the notificationText to set
	 */
	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}

	/**
	 * @return the customStudyId
	 */
	public String getCustomStudyId() {
		return customStudyId;
	}

	/**
	 * @param customStudyId the customStudyId to set
	 */
	public void setCustomStudyId(String customStudyId) {
		this.customStudyId = customStudyId;
	}

	/**
	 * @return the notificationType
	 */
	public String getNotificationType() {
		return notificationType;
	}

	/**
	 * @param notificationType the notificationType to set
	 */
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	/**
	 * @return the notificationSubType
	 */
	public String getNotificationSubType() {
		return notificationSubType;
	}

	/**
	 * @param notificationSubType the notificationSubType to set
	 */
	public void setNotificationSubType(String notificationSubType) {
		this.notificationSubType = notificationSubType;
	}

	/**
	 * @return the notificationTitle
	 */
	public String getNotificationTitle() {
		return notificationTitle;
	}

	/**
	 * @param notificationTitle the notificationTitle to set
	 */
	public void setNotificationTitle(String notificationTitle) {
		this.notificationTitle = notificationTitle;
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}
}
