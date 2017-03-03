package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="questionnaires_steps")
public class QuestionnairesStepsBo implements Serializable{

	private static final long serialVersionUID = -7908951701723989954L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="step_id")
	private Integer stepId;
	
	@Column(name="questionnaires_id")
	private Integer questionnairesId;
	
	@Column(name="instruction_form_id")
	private Integer instructionFormId;
	
	@Column(name="step_type")
	private String stepType;
	
	@Column(name="randomization")
	private String randomization;
	
	@Column(name="sequence_no")
	private Integer sequenceNo;

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public Integer getQuestionnairesId() {
		return questionnairesId;
	}

	public void setQuestionnairesId(Integer questionnairesId) {
		this.questionnairesId = questionnairesId;
	}

	public Integer getInstructionFormId() {
		return instructionFormId;
	}

	public void setInstructionFormId(Integer instructionFormId) {
		this.instructionFormId = instructionFormId;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getRandomization() {
		return randomization;
	}

	public void setRandomization(String randomization) {
		this.randomization = randomization;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}
	
}
