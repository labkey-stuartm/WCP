package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Pradyumn
 *
 */

@Entity
@Table(name = "study_permission")
public class StudyPermissionBO implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer studyPermissionId;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name="study_id")
	private Integer studyId;
	
	@Column(name = "view_permission", length = 1)
	private boolean viewPermission;
	
	@Column(name = "project_lead")
	private String projectLead;

	public Integer getStudyPermissionId() {
		return studyPermissionId;
	}

	public void setStudyPermissionId(Integer studyPermissionId) {
		this.studyPermissionId = studyPermissionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public boolean isViewPermission() {
		return viewPermission;
	}

	public void setViewPermission(boolean viewPermission) {
		this.viewPermission = viewPermission;
	}

	public String getProjectLead() {
		return projectLead;
	}

	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}
}
