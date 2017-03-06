package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the active_task database table.
 * @author Vivek
 */
@Entity
@Table(name="active_task")
@NamedQuery(name="ActiveTaskBo.findAll", query="SELECT a FROM ActiveTaskBo a")
public class ActiveTaskBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="active_task_lifetime_end")
	private String activeTaskLifetimeEnd;

	@Column(name="active_task_lifetime_start")
	private String activeTaskLifetimeStart;

	private String duration;

	@Column(name="study_id")
	private int studyId;

	@Column(name="task_name")
	private String taskName;

	//bi-directional many-to-one association to ActiveTaskFrequencyBo
	@OneToMany(mappedBy="activeTaskBo")
	private List<ActiveTaskFrequencyBo> activeTaskFrequencyBos;

	//bi-directional many-to-one association to ActiveTaskStepBo
	@OneToMany(mappedBy="activeTaskBo")
	private List<ActiveTaskStepBo> activeTaskStepBos;

	public ActiveTaskBo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActiveTaskLifetimeEnd() {
		return this.activeTaskLifetimeEnd;
	}

	public void setActiveTaskLifetimeEnd(String activeTaskLifetimeEnd) {
		this.activeTaskLifetimeEnd = activeTaskLifetimeEnd;
	}

	public String getActiveTaskLifetimeStart() {
		return this.activeTaskLifetimeStart;
	}

	public void setActiveTaskLifetimeStart(String activeTaskLifetimeStart) {
		this.activeTaskLifetimeStart = activeTaskLifetimeStart;
	}

	public String getDuration() {
		return this.duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public int getStudyId() {
		return this.studyId;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public List<ActiveTaskFrequencyBo> getActiveTaskFrequencies() {
		return this.activeTaskFrequencyBos;
	}

	public void setActiveTaskFrequencies(List<ActiveTaskFrequencyBo> activeTaskFrequencyBos) {
		this.activeTaskFrequencyBos = activeTaskFrequencyBos;
	}

	public ActiveTaskFrequencyBo addActiveTaskFrequency(ActiveTaskFrequencyBo activeTaskFrequencyBo) {
		getActiveTaskFrequencies().add(activeTaskFrequencyBo);
		activeTaskFrequencyBo.setActiveTask(this);

		return activeTaskFrequencyBo;
	}

	public ActiveTaskFrequencyBo removeActiveTaskFrequency(ActiveTaskFrequencyBo activeTaskFrequencyBo) {
		getActiveTaskFrequencies().remove(activeTaskFrequencyBo);
		activeTaskFrequencyBo.setActiveTask(null);

		return activeTaskFrequencyBo;
	}

	public List<ActiveTaskStepBo> getActiveTaskSteps() {
		return this.activeTaskStepBos;
	}

	public void setActiveTaskSteps(List<ActiveTaskStepBo> activeTaskStepBos) {
		this.activeTaskStepBos = activeTaskStepBos;
	}

	public ActiveTaskStepBo addActiveTaskStep(ActiveTaskStepBo activeTaskStepBo) {
		getActiveTaskSteps().add(activeTaskStepBo);
		activeTaskStepBo.setActiveTask(this);

		return activeTaskStepBo;
	}

	public ActiveTaskStepBo removeActiveTaskStep(ActiveTaskStepBo activeTaskStepBo) {
		getActiveTaskSteps().remove(activeTaskStepBo);
		activeTaskStepBo.setActiveTask(null);

		return activeTaskStepBo;
	}

}