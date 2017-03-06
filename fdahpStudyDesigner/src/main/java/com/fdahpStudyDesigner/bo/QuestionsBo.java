package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="questions")
public class QuestionsBo implements Serializable {

	private static final long serialVersionUID = 7281155550929426893L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="short_title")
	private String shortTitle;
	
	@Column(name="question")
	private String question;
	
	@Column(name="mandatory")
	private Boolean mandatory=false;
	
	@Column(name="skip_and_return")
	private Boolean skipAndReturn=false;
	
	@Column(name="phi")
	private Boolean phi=false;
	
	@Column(name="otc")
	private Boolean otc=false;
	
	@Column(name="demographics")
	private Boolean demographics=false;
	
	@Column(name="randomize")
	private String randomize;
	
	@Column(name="data_for_health")
	private Boolean dataForHealth;
	
	@Column(name="health_data_type")
	private String healthDataType;
	
	@Column(name="time_range")
	private String timeRange;
	
	@Column(name="response_type")
	private String responseType;
	
	@Column(name="condition_definition")
	private String conditionDefinition;
	
	@Column(name="define_condition")
	private String defineCondition;
	
	@Column(name="pass_fail")
	private String passFail;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	public Boolean getSkipAndReturn() {
		return skipAndReturn;
	}

	public void setSkipAndReturn(Boolean skipAndReturn) {
		this.skipAndReturn = skipAndReturn;
	}

	public Boolean getPhi() {
		return phi;
	}

	public void setPhi(Boolean phi) {
		this.phi = phi;
	}

	public Boolean getOtc() {
		return otc;
	}

	public void setOtc(Boolean otc) {
		this.otc = otc;
	}

	public Boolean getDemographics() {
		return demographics;
	}

	public void setDemographics(Boolean demographics) {
		this.demographics = demographics;
	}

	public String getRandomize() {
		return randomize;
	}

	public void setRandomize(String randomize) {
		this.randomize = randomize;
	}

	public Boolean getDataForHealth() {
		return dataForHealth;
	}

	public void setDataForHealth(Boolean dataForHealth) {
		this.dataForHealth = dataForHealth;
	}

	public String getHealthDataType() {
		return healthDataType;
	}

	public void setHealthDataType(String healthDataType) {
		this.healthDataType = healthDataType;
	}

	public String getTimeRange() {
		return timeRange;
	}

	public void setTimeRange(String timeRange) {
		this.timeRange = timeRange;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getConditionDefinition() {
		return conditionDefinition;
	}

	public void setConditionDefinition(String conditionDefinition) {
		this.conditionDefinition = conditionDefinition;
	}

	public String getDefineCondition() {
		return defineCondition;
	}

	public void setDefineCondition(String defineCondition) {
		this.defineCondition = defineCondition;
	}

	public String getPassFail() {
		return passFail;
	}

	public void setPassFail(String passFail) {
		this.passFail = passFail;
	}
	
}
