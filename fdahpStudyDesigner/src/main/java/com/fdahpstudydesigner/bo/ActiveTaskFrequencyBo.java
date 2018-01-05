package com.fdahpstudydesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * The persistent class for the active_task_frequencies database table.
 *
 * @author Vivek
 */
@Entity
@Table(name = "active_task_frequencies")
@NamedQuery(name = "ActiveTaskFrequencyBo.findAll", query = "SELECT a FROM ActiveTaskFrequencyBo a")
public class ActiveTaskFrequencyBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "active_task_id")
	private Integer activeTaskId;

	@Column(name = "frequency_date")
	private String frequencyDate;

	@Column(name = "frequency_time")
	private String frequencyTime;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "is_launch_study")
	private Boolean isLaunchStudy = false;

	@Column(name = "is_study_life_time")
	private Boolean isStudyLifeTime = false;

	public ActiveTaskFrequencyBo() {
		// Do nothing
	}

	public Integer getActiveTaskId() {
		return activeTaskId;
	}

	public String getFrequencyDate() {
		return this.frequencyDate;
	}

	public String getFrequencyTime() {
		return this.frequencyTime;
	}

	public Long getId() {
		return this.id;
	}

	/**
	 * @return the isLaunchStudy
	 */
	public Boolean getIsLaunchStudy() {
		return isLaunchStudy;
	}

	/**
	 * @return the isStudyLifeTime
	 */
	public Boolean getIsStudyLifeTime() {
		return isStudyLifeTime;
	}

	public void setActiveTaskId(Integer activeTaskId) {
		this.activeTaskId = activeTaskId;
	}

	public void setFrequencyDate(String frequencyDate) {
		this.frequencyDate = frequencyDate;
	}

	public void setFrequencyTime(String frequencyTime) {
		this.frequencyTime = frequencyTime;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @param isLaunchStudy
	 *            the isLaunchStudy to set
	 */
	public void setIsLaunchStudy(Boolean isLaunchStudy) {
		this.isLaunchStudy = isLaunchStudy;
	}

	/**
	 * @param isStudyLifeTime
	 *            the isStudyLifeTime to set
	 */
	public void setIsStudyLifeTime(Boolean isStudyLifeTime) {
		this.isStudyLifeTime = isStudyLifeTime;
	}

}