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
@Table(name = "eligibility_test")
public class EligibilityTestBo implements Serializable{

	private static final long serialVersionUID = -6517033483482921515L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "eligibility_id")
	private Integer eligibilityId;
	
	@Column(name = "short_title")
	private String shortTitle;
	
	@Column(name = "question")
	private String question;
	
	@Column(name = "response_format")
	private String responseFormat;
	
	@Column(name = "order")
	private Integer order;
	
	@Column(name = "status")
	private Integer status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getEligibilityId() {
		return eligibilityId;
	}

	public void setEligibilityId(Integer eligibilityId) {
		this.eligibilityId = eligibilityId;
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

	public String getResponseFormat() {
		return responseFormat;
	}

	public void setResponseFormat(String responseFormat) {
		this.responseFormat = responseFormat;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
