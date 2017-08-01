package com.fdahpstudydesigner.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
/**
 * @author Ronalin
 *
 */
@Entity
@Table(name = "study_activity_version")
public class StudyActivityVersionBo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="study_activity_id")
	private Integer studyActivityId;
	
	@Column(name="custom_study_id")
	private String customStudyId;
	
	@Column(name = "study_version")
	private Float studyVersion;
	
	@Column(name="activity_type")
	private String activityType;
	
	@Column(name="short_title")
	private String shortTitle;
	
	@Column(name = "activity_id")
	private Integer activityId;

	public Integer getStudyActivityId() {
		return studyActivityId;
	}

	public void setStudyActivityId(Integer studyActivityId) {
		this.studyActivityId = studyActivityId;
	}

	public String getCustomStudyId() {
		return customStudyId;
	}

	public void setCustomStudyId(String customStudyId) {
		this.customStudyId = customStudyId;
	}

	public Float getStudyVersion() {
		return studyVersion;
	}

	public void setStudyVersion(Float studyVersion) {
		this.studyVersion = studyVersion;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public Integer getActivityId() {
		return activityId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}
}
