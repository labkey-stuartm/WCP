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
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_on")
	private String modifiedOn;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;

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

	/**
	 * @return the createdOn
	 */
	public String getCreatedOn() {
		return createdOn;
	}

	/**
	 * @param createdOn the createdOn to set
	 */
	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	/**
	 * @return the modifiedOn
	 */
	public String getModifiedOn() {
		return modifiedOn;
	}

	/**
	 * @param modifiedOn the modifiedOn to set
	 */
	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifiedBy
	 */
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
