package com.fdahpStudyDesigner.bo;

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

@Entity
@Table(name="questions")
@NamedQueries({
	@NamedQuery(name="getQuestionStep", query="from QuestionsBo QBO where QBO.id=:stepId"),
	@NamedQuery(name="deletQuestion",query="delete from QuestionsBo QBO where QBO.id=:questionId")
})
public class QuestionsBo implements Serializable {

	private static final long serialVersionUID = 7281155550929426893L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="question")
	private String question;
	
	@Column(name="description")
	private String description;
	
	@Column(name="response_type")
	private Integer responseType;
	
	@Column(name="skippable")
	private String skippable;
	
	@Column(name="add_line_chart")
	private String addLineChart;
	
	@Column(name="line_chart_timerange")
	private String lineChartTimeRange;
	
	@Column(name="allow_rollback_chart")
	private String allowRollbackChart;
	
	@Column(name="chart_title")
	private String chartTitle;
	
	@Column(name="use_stastic_data")
	private String useStasticData;
	
	@Column(name="stat_short_name")
	private String statShortName;
	
	@Column(name="stat_display_name")
	private String statDisplayName;
	
	@Column(name="stat_diaplay_units")
	private String statDisplayUnits;
	
	@Column(name="stat_type")
	private String statType;
	
	@Column(name="stat_formula")
	private String statFormula;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_on")
	private String modifiedOn;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Transient
	private List<QuestionsResponseTypeBo> questionResponseList;
	
	@Transient
	private String stepType;
	
	@Transient
	private Integer questionnaireId;
	
	@Transient
	private Integer fromId;
	
	@Transient QuestionnairesStepsBo questionnairesStepsBo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Integer getResponseType() {
		return responseType;
	}

	public void setResponseType(Integer responseType) {
		this.responseType = responseType;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public List<QuestionsResponseTypeBo> getQuestionResponseList() {
		return questionResponseList;
	}

	public void setQuestionResponseList(
			List<QuestionsResponseTypeBo> questionResponseList) {
		this.questionResponseList = questionResponseList;
	}

	public String getStepType() {
		return stepType;
	}

	public void setStepType(String stepType) {
		this.stepType = stepType;
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSkippable() {
		return skippable;
	}

	public void setSkippable(String skippable) {
		this.skippable = skippable;
	}

	public String getAddLineChart() {
		return addLineChart;
	}

	public void setAddLineChart(String addLineChart) {
		this.addLineChart = addLineChart;
	}

	public String getLineChartTimeRange() {
		return lineChartTimeRange;
	}

	public void setLineChartTimeRange(String lineChartTimeRange) {
		this.lineChartTimeRange = lineChartTimeRange;
	}

	public String getAllowRollbackChart() {
		return allowRollbackChart;
	}

	public void setAllowRollbackChart(String allowRollbackChart) {
		this.allowRollbackChart = allowRollbackChart;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public String getUseStasticData() {
		return useStasticData;
	}

	public void setUseStasticData(String useStasticData) {
		this.useStasticData = useStasticData;
	}

	public String getStatShortName() {
		return statShortName;
	}

	public void setStatShortName(String statShortName) {
		this.statShortName = statShortName;
	}

	public String getStatDisplayName() {
		return statDisplayName;
	}

	public void setStatDisplayName(String statDisplayName) {
		this.statDisplayName = statDisplayName;
	}

	public String getStatDisplayUnits() {
		return statDisplayUnits;
	}

	public void setStatDisplayUnits(String statDisplayUnits) {
		this.statDisplayUnits = statDisplayUnits;
	}

	public String getStatType() {
		return statType;
	}

	public void setStatType(String statType) {
		this.statType = statType;
	}

	public String getStatFormula() {
		return statFormula;
	}

	public void setStatFormula(String statFormula) {
		this.statFormula = statFormula;
	}

	public Integer getFromId() {
		return fromId;
	}

	public void setFromId(Integer fromId) {
		this.fromId = fromId;
	}

	public QuestionnairesStepsBo getQuestionnairesStepsBo() {
		return questionnairesStepsBo;
	}

	public void setQuestionnairesStepsBo(QuestionnairesStepsBo questionnairesStepsBo) {
		this.questionnairesStepsBo = questionnairesStepsBo;
	}
	
}
