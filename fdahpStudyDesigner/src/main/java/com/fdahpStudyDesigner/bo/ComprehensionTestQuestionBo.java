package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="comprehension_test_question")
public class ComprehensionTestQuestionBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4092393873968937668L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="question_text")
	private String questionText;
	
	@Column(name="study_id")
	private Integer studyId;
	
	@Column(name = "sequence_no")
	private Integer sequenceNo;
	
	@Column(name = "structure_of_correct_ans")
	private Boolean structureOfCorrectAns;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_on")
	private String modifiedOn;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "study_version")
	private Integer studyVersion=1;
	
	@Transient
	private List<ComprehensionTestResponseBo> responseList;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public Integer getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(Integer sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public Boolean isStructureOfCorrectAns() {
		return structureOfCorrectAns;
	}

	public void setStructureOfCorrectAns(Boolean structureOfCorrectAns) {
		this.structureOfCorrectAns = structureOfCorrectAns;
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

	public List<ComprehensionTestResponseBo> getResponseList() {
		return responseList;
	}

	public void setResponseList(List<ComprehensionTestResponseBo> responseList) {
		this.responseList = responseList;
	}

	public Integer getStudyVersion() {
		return studyVersion;
	}

	public void setStudyVersion(Integer studyVersion) {
		this.studyVersion = studyVersion;
	}
	
}
