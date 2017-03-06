package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the active_task_frequencies database table.
 * @author Vivek
 */
@Entity
@Table(name="active_task_frequencies")
@NamedQuery(name="ActiveTaskFrequencyBo.findAll", query="SELECT a FROM ActiveTaskFrequencyBo a")
public class ActiveTaskFrequencyBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="frequency_date")
	private String frequencyDate;

	@Column(name="frequency_time")
	private String frequencyTime;

	//bi-directional many-to-one association to ActiveTaskBo
	@ManyToOne
	@JoinColumn(name="active_task_id")
	private ActiveTaskBo activeTaskBo;

	public ActiveTaskFrequencyBo() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrequencyDate() {
		return this.frequencyDate;
	}

	public void setFrequencyDate(String frequencyDate) {
		this.frequencyDate = frequencyDate;
	}

	public String getFrequencyTime() {
		return this.frequencyTime;
	}

	public void setFrequencyTime(String frequencyTime) {
		this.frequencyTime = frequencyTime;
	}

	public ActiveTaskBo getActiveTask() {
		return this.activeTaskBo;
	}

	public void setActiveTask(ActiveTaskBo activeTaskBo) {
		this.activeTaskBo = activeTaskBo;
	}

}