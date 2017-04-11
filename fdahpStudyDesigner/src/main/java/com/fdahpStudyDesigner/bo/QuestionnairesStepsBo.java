package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fdahpStudyDesigner.bean.QuestionnaireStepBean;

@Entity
@Table(name="questionnaires_steps")
@NamedQueries({
	@NamedQuery(name="getQuestionnaireStepSequenceNo", query="From QuestionnairesStepsBo QSBO where QSBO.questionnairesId=:questionnairesId order by QSBO.sequenceNo DESC"),
	@NamedQuery(name="getQuestionnaireStep", query="From QuestionnairesStepsBo QSBO where QSBO.instructionFormId=:instructionFormId and QSBO.stepType=:stepType"),
	@NamedQuery(name="getQuestionnaireStepList", query="From QuestionnairesStepsBo QSBO where QSBO.questionnairesId=:questionnaireId order by QSBO.sequenceNo"),
	@NamedQuery(name="checkQuestionnaireStepShortTitle", query="From QuestionnairesStepsBo QSBO where QSBO.questionnairesId=:questionnaireId and QSBO.stepType=:stepType and QSBO.stepShortTitle=:shortTitle"),
	@NamedQuery(name="getForwardQuestionnaireSteps", query="From QuestionnairesStepsBo QSBO where QSBO.questionnairesId=:questionnairesId and QSBO.sequenceNo >:sequenceNo"),
})
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
	
	@Column(name="sequence_no")
	private Integer sequenceNo;
	
	@Column(name="step_short_title")
	private String stepShortTitle;
	
	@Column(name="skiappable")
	private String skiappable;
	
	@Column(name="destination_step")
	private Integer destinationStep;
	
	@Column(name="repeatable")
	private String repeatable="No";
	
	@Column(name="repeatable_text")
	private String repeatableText;
	
	@Column(name="status")
	private Boolean status;
	
	@Transient
	private String type;
	
	@Transient
	private QuestionsBo questionsBo;
	
	@Transient
	private TreeMap<Integer, QuestionnaireStepBean> formQuestionMap = new TreeMap<>();

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

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getStepShortTitle() {
		return stepShortTitle;
	}

	public void setStepShortTitle(String stepShortTitle) {
		this.stepShortTitle = stepShortTitle;
	}

	public String getSkiappable() {
		return skiappable;
	}

	public void setSkiappable(String skiappable) {
		this.skiappable = skiappable;
	}

	public Integer getDestinationStep() {
		return destinationStep;
	}

	public void setDestinationStep(Integer destinationStep) {
		this.destinationStep = destinationStep;
	}

	public String getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(String repeatable) {
		this.repeatable = repeatable;
	}

	public String getRepeatableText() {
		return repeatableText;
	}

	public void setRepeatableText(String repeatableText) {
		this.repeatableText = repeatableText;
	}

	public QuestionsBo getQuestionsBo() {
		return questionsBo;
	}

	public void setQuestionsBo(QuestionsBo questionsBo) {
		this.questionsBo = questionsBo;
	}

	public TreeMap<Integer, QuestionnaireStepBean> getFormQuestionMap() {
		return formQuestionMap;
	}

	public void setFormQuestionMap(
			TreeMap<Integer, QuestionnaireStepBean> formQuestionMap) {
		this.formQuestionMap = formQuestionMap;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}
