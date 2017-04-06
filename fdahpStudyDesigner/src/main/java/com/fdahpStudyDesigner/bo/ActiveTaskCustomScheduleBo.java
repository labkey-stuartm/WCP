package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the active_task_custom_frequencies database table.
 * 
 */
@Entity
@Table(name="active_task_custom_frequencies")
@NamedQuery(name="ActiveTaskCustomScheduleBo.findAll", query="SELECT a FROM ActiveTaskCustomScheduleBo a")
public class ActiveTaskCustomScheduleBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="active_task_id")
	private Integer activeTaskId;

	@Column(name="frequency_end_date")
	private String frequencyEndDate;

	@Column(name="frequency_start_date")
	private String frequencyStartDate;

	@Column(name="frequency_time")
	private String frequencyTime;

	public ActiveTaskCustomScheduleBo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getActiveTaskId() {
		return this.activeTaskId;
	}

	public void setActiveTaskId(Integer activeTaskId) {
		this.activeTaskId = activeTaskId;
	}

	public String getFrequencyEndDate() {
		return this.frequencyEndDate;
	}

	public void setFrequencyEndDate(String frequencyEndDate) {
		this.frequencyEndDate = frequencyEndDate;
	}

	public String getFrequencyStartDate() {
		return this.frequencyStartDate;
	}

	public void setFrequencyStartDate(String frequencyStartDate) {
		this.frequencyStartDate = frequencyStartDate;
	}

	public String getFrequencyTime() {
		return this.frequencyTime;
	}

	public void setFrequencyTime(String frequencyTime) {
		this.frequencyTime = frequencyTime;
	}

}