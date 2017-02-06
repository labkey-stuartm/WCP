package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Ronalin
 *
 */
@Entity
@Table(name = "eligibility")
public class EligibilityBo implements Serializable{

	private static final long serialVersionUID = -8985485973006714523L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "study_id")
	private Integer studyId;
	
	@Column(name = "eligibility_mechanism")
	private Integer eligibilityMechanism;
	
	@Column(name = "instructional_text")
	private String instructionalText;
	
	@Column(name = "failure_outcome_text")
	private String failureOutcomeText;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getEligibilityMechanism() {
		return eligibilityMechanism;
	}

	public void setEligibilityMechanism(Integer eligibilityMechanism) {
		this.eligibilityMechanism = eligibilityMechanism;
	}

	public String getInstructionalText() {
		return instructionalText;
	}

	public void setInstructionalText(String instructionalText) {
		this.instructionalText = instructionalText;
	}

	public String getFailureOutcomeText() {
		return failureOutcomeText;
	}

	public void setFailureOutcomeText(String failureOutcomeText) {
		this.failureOutcomeText = failureOutcomeText;
	}
	
}
