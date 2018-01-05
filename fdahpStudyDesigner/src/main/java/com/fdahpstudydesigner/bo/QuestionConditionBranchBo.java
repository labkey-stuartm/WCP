package com.fdahpstudydesigner.bo;

import java.io.Serializable;
import java.util.List;

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
 *
 * @author BTC The persistent class for the questionnaires database table.
 *
 */
@Entity
@Table(name = "question_condtion_branching")
@NamedQueries({ @NamedQuery(name = "getQuestionConditionBranchList", query = "from QuestionConditionBranchBo QCBO where QCBO.questionId=:questionId order by QCBO.sequenceNo ASC"), })
public class QuestionConditionBranchBo implements Serializable {

	private static final long serialVersionUID = 8189512029031610252L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "condition_id")
	private Integer conditionId;

	@Column(name = "question_id")
	private Integer questionId;

	@Column(name = "input_type")
	private String inputType;

	@Column(name = "input_type_value")
	private String inputTypeValue;

	@Column(name = "sequence_no")
	private Integer sequenceNo;

	@Column(name = "parent_sequence_no")
	private Integer parentSequenceNo;

	@Column(name = "active")
	private Boolean active;

	@Transient
	private List<QuestionConditionBranchBo> questionConditionBranchBos;

	public Boolean getActive() {
		return active;
	}

	public Integer getConditionId() {
		return conditionId;
	}

	public String getInputType() {
		return inputType;
	}

	public String getInputTypeValue() {
		return inputTypeValue;
	}

	public Integer getParentSequenceNo() {
		return parentSequenceNo;
	}

	public List<QuestionConditionBranchBo> getQuestionConditionBranchBos() {
		return questionConditionBranchBos;
	}

	public Integer getQuestionId() {
		return questionId;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public void setConditionId(Integer conditionId) {
		this.conditionId = conditionId;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void setInputTypeValue(String inputTypeValue) {
		this.inputTypeValue = inputTypeValue;
	}

	public void setParentSequenceNo(Integer parentSequenceNo) {
		this.parentSequenceNo = parentSequenceNo;
	}

	public void setQuestionConditionBranchBos(
			List<QuestionConditionBranchBo> questionConditionBranchBos) {
		this.questionConditionBranchBos = questionConditionBranchBos;
	}

	public void setQuestionId(Integer questionId) {
		this.questionId = questionId;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

}
