package com.fdahpStudyDesigner.bo;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Kanchana 
 * 
 */
@Entity
@Table(name = "notification")
public class NotificationBO implements Serializable{

	private static final long serialVersionUID = 3634540541782531200L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="notification_id")
	private Integer notificationId;
	
	@Column(name = "study_id")
	private Integer studyId;
	
	@Column(name = "notification_text")
	private String notificationText;
	
	@Column(name = "custom_study_id")
	private String customStudyId;
	
	@Column(name = "schedule_date")
	private String scheduleDate;
	
	@Column(name = "schedule_time")
	private String scheduleTime;
	
	@Column(name="notification_sent", length = 1)
	private boolean notificationSent = false;

	@Column(name = "notification_type")
	private String notificationType;
	
	@Column(name = "notification_sent_date_time")
	private String notificationSentDateTime;
	
	@Transient
	private String actionPage;
	
	@Transient
	private String notificationSentDate;
	
	@Transient
	private String notificationSentTime;
	
	public String getNotificationSentDate() {
		return notificationSentDate;
	}

	public void setNotificationSentDate(String notificationSentDate) {
		this.notificationSentDate = notificationSentDate;
	}

	public String getNotificationSentTime() {
		return notificationSentTime;
	}

	public void setNotificationSentTime(String notificationSentTime) {
		this.notificationSentTime = notificationSentTime;
	}

	public Integer getNotificationId() {
		return notificationId;
	}

	public void setNotificationId(Integer notificationId) {
		this.notificationId = notificationId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getNotificationText() {
		return notificationText;
	}

	public void setNotificationText(String notificationText) {
		this.notificationText = notificationText;
	}
	
	public String getCustomStudyId() {
		return customStudyId;
	}

	public void setCustomStudyId(String customStudyId) {
		this.customStudyId = customStudyId;
	}

	public String getScheduleDate() {
		return scheduleDate;
	}

	public void setScheduleDate(String scheduleDate) {
		this.scheduleDate = scheduleDate;
	}

	public String getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(String scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}

	public String getNotificationSentDateTime() {
		return notificationSentDateTime;
	}

	public void setNotificationSentDateTime(String notificationSentDateTime) {
		this.notificationSentDateTime = notificationSentDateTime;
	}

	public boolean isNotificationSent() {
		return notificationSent;
	}

	public void setNotificationSent(boolean notificationSent) {
		this.notificationSent = notificationSent;
	}

	public String getActionPage() {
		return actionPage;
	}

	public void setActionPage(String actionPage) {
		this.actionPage = actionPage;
	}

	

}
