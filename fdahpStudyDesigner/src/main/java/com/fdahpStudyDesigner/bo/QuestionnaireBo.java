package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;


/**
 * The persistent class for the questionnaires database table.
 * 
 */
@Entity
@Table(name="questionnaires")
@NamedQueries({
	@NamedQuery(name="QuestionnaireBo.findAll", query="SELECT q FROM QuestionnaireBo q"),
	@NamedQuery(name = "getQuestionariesByStudyId", query = " From QuestionnaireBo QBO WHERE QBO.studyId =:studyId and QBO.active=1"),
	@NamedQuery(name = "checkQuestionnaireShortTitle", query = "From QuestionnaireBo QBO where QBO.studyId=:studyId and QBO.shortTitle=:shortTitle")
})
public class QuestionnaireBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;

	@Column(name="frequency")
	private String frequency;

	@Column(name="study_id")
	private Integer studyId;

	@Column(name="study_lifetime_end")
	private String studyLifetimeEnd;

	@Column(name="study_lifetime_start")
	private String studyLifetimeStart;

	@Column(name="title")
	private String title;
	
	@Column(name="created_by")
	private Integer createdBy;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="modified_by")
	private Integer modifiedBy;
	
	@Column(name="modified_date")
	private String modifiedDate;
	
	@Column(name="repeat_questionnaire")
	private Integer repeatQuestionnaire;
	
	@Column(name="day_of_the_week")
	private String dayOfTheWeek;
	
	@Column(name="short_title")
	private String shortTitle;
	
	@Column(name="branching")
	private Boolean branching=false;
	
	@Column(name = "study_version")
	private Integer studyVersion=1;
	
	@Column(name="active")
	private Boolean active; 
	
	@Column(name="status")
	private Boolean status;
	
	@Transient
	private String previousFrequency;
	
	@Transient
	private String type;
	
	@Transient
	private List<QuestionnairesFrequenciesBo> questionnairesFrequenciesList = new ArrayList<QuestionnairesFrequenciesBo>();
	
	@Transient 
	private QuestionnairesFrequenciesBo questionnairesFrequenciesBo = new QuestionnairesFrequenciesBo();
	
	@Transient
	private List<QuestionnaireCustomScheduleBo> questionnaireCustomScheduleBo = new ArrayList<QuestionnaireCustomScheduleBo>();

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFrequency() {
		return this.frequency;
	}

	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	public Integer getStudyId() {
		return this.studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getStudyLifetimeEnd() {
		return this.studyLifetimeEnd;
	}

	public void setStudyLifetimeEnd(String studyLifetimeEnd) {
		this.studyLifetimeEnd = studyLifetimeEnd;
	}

	public String getStudyLifetimeStart() {
		return this.studyLifetimeStart;
	}

	public void setStudyLifetimeStart(String studyLifetimeStart) {
		this.studyLifetimeStart = studyLifetimeStart;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<QuestionnairesFrequenciesBo> getQuestionnairesFrequenciesList() {
		return questionnairesFrequenciesList;
	}

	public void setQuestionnairesFrequenciesList(
			List<QuestionnairesFrequenciesBo> questionnairesFrequenciesList) {
		this.questionnairesFrequenciesList = questionnairesFrequenciesList;
	}

	public QuestionnairesFrequenciesBo getQuestionnairesFrequenciesBo() {
		return questionnairesFrequenciesBo;
	}

	public void setQuestionnairesFrequenciesBo(
			QuestionnairesFrequenciesBo questionnairesFrequenciesBo) {
		this.questionnairesFrequenciesBo = questionnairesFrequenciesBo;
	}

	public List<QuestionnaireCustomScheduleBo> getQuestionnaireCustomScheduleBo() {
		return questionnaireCustomScheduleBo;
	}

	public void setQuestionnaireCustomScheduleBo(
			List<QuestionnaireCustomScheduleBo> questionnaireCustomScheduleBo) {
		this.questionnaireCustomScheduleBo = questionnaireCustomScheduleBo;
	}

	public Integer getRepeatQuestionnaire() {
		return repeatQuestionnaire;
	}

	public void setRepeatQuestionnaire(Integer repeatQuestionnaire) {
		this.repeatQuestionnaire = repeatQuestionnaire;
	}

	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	public String getPreviousFrequency() {
		return previousFrequency;
	}

	public void setPreviousFrequency(String previousFrequency) {
		this.previousFrequency = previousFrequency;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public Boolean getBranching() {
		return branching;
	}

	public void setBranching(Boolean branching) {
		this.branching = branching;
	}

	public Integer getStudyVersion() {
		return studyVersion;
	}

	public void setStudyVersion(Integer studyVersion) {
		this.studyVersion = studyVersion;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}
	
}