package com.fdahpStudyDesigner.bean;

import java.util.List;

public class QuestionnaireStepBean {

	private Integer stepId;
	private String stepType;
	private String title;
	private Integer sequenceNo;
	private Integer questionInstructionId;
	
	List<QuestionnaireStepBean> formList;

	public Integer getStepId() {
		return stepId;
	}

	public void setStepId(Integer stepId) {
		this.stepId = stepId;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Integer getQuestionInstructionId() {
		return questionInstructionId;
	}

	public void setQuestionInstructionId(Integer questionInstructionId) {
		this.questionInstructionId = questionInstructionId;
	}

	public List<QuestionnaireStepBean> getFormList() {
		return formList;
	}

	public void setFormList(List<QuestionnaireStepBean> formList) {
		this.formList = formList;
	}
	
}
