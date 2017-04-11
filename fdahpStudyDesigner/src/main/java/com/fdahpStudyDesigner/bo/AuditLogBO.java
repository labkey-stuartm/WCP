package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * Kanchana 
 * 
 */
@Entity
@Table(name = "audit_log")
public class AuditLogBO implements Serializable{
	
	private static final long serialVersionUID = -1122573644412620653L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="audit_log_id")
	private Integer auditLogId;
	
	@Column(name = "user_id")
	private Integer userId;
	
	@Column(name = "activity")
	private String activity;
	
	@Column(name = "activity_details")
	private String activityDetails;
	
	@Column(name = "created_date_time")
	private String createdDateTime;
	
	@Column(name = "class_method_name")
	private String notificationType;

	public Integer getAuditLogId() {
		return auditLogId;
	}

	public void setAuditLogId(Integer auditLogId) {
		this.auditLogId = auditLogId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getActivityDetails() {
		return activityDetails;
	}

	public void setActivityDetails(String activityDetails) {
		this.activityDetails = activityDetails;
	}

	public String getCreatedDateTime() {
		return createdDateTime;
	}

	public void setCreatedDateTime(String createdDateTime) {
		this.createdDateTime = createdDateTime;
	}

	public String getNotificationType() {
		return notificationType;
	}

	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
	
	
}