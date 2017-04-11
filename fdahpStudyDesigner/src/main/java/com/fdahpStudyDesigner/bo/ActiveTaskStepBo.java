package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the active_task_steps database table.
 * @author Vivek
 */
@Entity
@Table(name="active_task_steps")
@NamedQuery(name="ActiveTaskStepBo.findAll", query="SELECT a FROM ActiveTaskStepBo a")
public class ActiveTaskStepBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="step_id")
	private Integer stepId;

	@Column(name="active_task_stepscol")
	private String activeTaskStepscol;

	@Column(name="sd_live_form_id")
	private String sdLiveFormId;

	@Column(name="sequence_no")
	private int sequenceNo;

	@Column(name="active_task_id")
	private Integer activetaskId;
	
	@Column(name = "study_version")
	private Integer studyVersion=1;

	public ActiveTaskStepBo() {
	}

	public Integer getStepId() {
		return this.stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getActiveTaskStepscol() {
		return this.activeTaskStepscol;
	}

	public void setActiveTaskStepscol(String activeTaskStepscol) {
		this.activeTaskStepscol = activeTaskStepscol;
	}

	public String getSdLiveFormId() {
		return this.sdLiveFormId;
	}

	public void setSdLiveFormId(String sdLiveFormId) {
		this.sdLiveFormId = sdLiveFormId;
	}

	public int getSequenceNo() {
		return this.sequenceNo;
	}

	public void setSequenceNo(int sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Integer getActivetaskId() {
		return activetaskId;
	}

	public void setActivetaskId(Integer activetaskId) {
		this.activetaskId = activetaskId;
	}

	public Integer getStudyVersion() {
		return studyVersion;
	}

	public void setStudyVersion(Integer studyVersion) {
		this.studyVersion = studyVersion;
	}

}