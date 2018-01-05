package com.fdahpstudydesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The persistent class for the questionnaires_frequencies database table.
 * 
 * @author BTC
 *
 */
@Entity
@Table(name = "questionnaires_frequencies")
public class QuestionnairesFrequenciesBo implements Serializable {

	private static final long serialVersionUID = -1673441133422366930L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "questionnaires_id")
	private Integer questionnairesId;

	@Column(name = "frequency_date")
	private String frequencyDate;

	@Column(name = "frequency_time")
	private String frequencyTime;

	@Column(name = "is_launch_study")
	private Boolean isLaunchStudy = false;

	@Column(name = "is_study_life_time")
	private Boolean isStudyLifeTime = false;

	public String getFrequencyDate() {
		return frequencyDate;
	}

	public String getFrequencyTime() {
		return frequencyTime;
	}

	public Integer getId() {
		return id;
	}

	public Boolean getIsLaunchStudy() {
		return isLaunchStudy;
	}

	public Boolean getIsStudyLifeTime() {
		return isStudyLifeTime;
	}

	public Integer getQuestionnairesId() {
		return questionnairesId;
	}

	public void setFrequencyDate(String frequencyDate) {
		this.frequencyDate = frequencyDate;
	}

	public void setFrequencyTime(String frequencyTime) {
		this.frequencyTime = frequencyTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIsLaunchStudy(Boolean isLaunchStudy) {
		this.isLaunchStudy = isLaunchStudy;
	}

	public void setIsStudyLifeTime(Boolean isStudyLifeTime) {
		this.isStudyLifeTime = isStudyLifeTime;
	}

	public void setQuestionnairesId(Integer questionnairesId) {
		this.questionnairesId = questionnairesId;
	}

}
