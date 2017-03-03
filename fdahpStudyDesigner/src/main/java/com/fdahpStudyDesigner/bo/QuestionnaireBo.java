package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the questionnaires database table.
 * 
 */
@Entity
@Table(name="questionnaires")
@NamedQueries({
	@NamedQuery(name="QuestionnaireBo.findAll", query="SELECT q FROM QuestionnaireBo q"),
	@NamedQuery(name = "getQuestionariesByStudyId", query = " From QuestionnaireBo QBO WHERE QBO.studyId =:studyId")
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
	
	@Column(name="createdDate")
	private String createdDate;
	
	@Column(name="createdBy")
	private String createdBy;
    
	@Column(name="modifiedDate")
	private String modifiedDate;
    
	@Column(name="modifiedBy")
	private String modifiedBy;
	
	
	public QuestionnaireBo() {
	}

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

	/**
	 * @return the createdDate
	 */
	public String getCreatedDate() {
		return createdDate;
	}

	/**
	 * @param createdDate the createdDate to set
	 */
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/**
	 * @return the modifiedDate
	 */
	public String getModifiedDate() {
		return modifiedDate;
	}

	/**
	 * @param modifiedDate the modifiedDate to set
	 */
	public void setModifiedDate(String modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	/**
	 * @return the modifiedBy
	 */
	public String getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
}