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
 * 
 * @author Ronalin
 *
 */
@Entity
@Table(name = "eligibility_test")
@NamedQueries({
		@NamedQuery(name = "EligibilityTestBo.findAll", query = "SELECT ETB FROM EligibilityTestBo ETB WHERE ETB.active = true ORDER BY ETB.sequenceNo"),
		@NamedQuery(name = "EligibilityTestBo.findById", query = "SELECT ETB FROM EligibilityTestBo ETB WHERE ETB.active = true AND ETB.id=:eligibilityTestId ORDER BY ETB.sequenceNo"),
		@NamedQuery(name = "EligibilityTestBo.findByIdByEligibilityId", query = "SELECT ETB FROM EligibilityTestBo ETB WHERE ETB.active = true AND ETB.eligibilityId=:eligibilityId ORDER BY ETB.sequenceNo"),
		@NamedQuery(name = "EligibilityTestBo.deleteById", query = "UPDATE EligibilityTestBo SET status = false WHERE eligibilityTestId=:eligibilityTestId ") })
public class EligibilityTestBo implements Serializable {

	private static final long serialVersionUID = -6517033483482921515L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "eligibility_id")
	private Integer eligibilityId;

	@Column(name = "short_title")
	private String shortTitle;

	@Column(name = "question")
	private String question;

	@Column(name = "response_format")
	private String responseFormat;

	@Column(name = "sequence_no")
	private Integer sequenceNo;

	@Column(name = "status")
	private Boolean status = false;
	
	@Column(name="active")
	private Boolean active = true;
	
	@Transient
	private String type;
	
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

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Boolean getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Boolean status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
