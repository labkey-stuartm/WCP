package com.fdahpstudydesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Eligibility  of  Study, specify the eligibility mechanism by Admin 
 * @author Ronalin
 * 
 */
@Entity
@Table(name = "eligibility")
@NamedQueries({
	@NamedQuery(name = "getEligibiltyById", query = " From EligibilityBo EBO WHERE EBO.id =:id"),
	@NamedQuery(name = "getEligibiltyByStudyId", query = " From EligibilityBo EBO WHERE EBO.studyId =:studyId")
})
public class EligibilityBo implements Serializable{

	private static final long serialVersionUID = -8985485973006714523L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "study_id")
	private Integer studyId;
	
	@Column(name = "eligibility_mechanism")
	private Integer eligibilityMechanism = 1;
	
	@Column(name = "instructional_text")
	private String instructionalText;
	
	@Column(name = "failure_outcome_text")
	private String failureOutcomeText;
	
	@Column(name = "study_version")
	private Integer studyVersion=1;

	@Transient
	private String actionType;
	
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

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public Integer getStudyVersion() {
		return studyVersion;
	}

	public void setStudyVersion(Integer studyVersion) {
		this.studyVersion = studyVersion;
	}
	
}
