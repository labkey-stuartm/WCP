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

}