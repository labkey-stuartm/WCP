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
@NamedQueries({ 
	@NamedQuery(name="ActiveTaskBo.findAll", query="SELECT ATB FROM ActiveTaskBo ATB"), 
	@NamedQuery(name="ActiveTaskBo.getActiveTasksByByStudyId", query="SELECT ATB FROM ActiveTaskBo ATB where ATB.studyId =:studyId"),
})
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
}