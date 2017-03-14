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

	@Column(name="duration")
	private String duration;

	@Column(name="study_id")
	private Integer studyId;

	@Column(name="display_name")
	private String displayName;
	
	@Column(name="short_title")
	private String shortTitle;
	
	@Column(name="instruction")
	private String instruction;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_on")
	private String modifiedOn;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	

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

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getShortTitle() {
		return shortTitle;
	}

	public void setShortTitle(String shortTitle) {
		this.shortTitle = shortTitle;
	}

	public String getInstruction() {
		return instruction;
	}

	public void setInstruction(String instruction) {
		this.instruction = instruction;
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

	public List<ActiveTaskFrequencyBo> getActiveTaskFrequencyBos() {
		return activeTaskFrequencyBos;
	}

	public void setActiveTaskFrequencyBos(List<ActiveTaskFrequencyBo> activeTaskFrequencyBos) {
		this.activeTaskFrequencyBos = activeTaskFrequencyBos;
	}

	public List<ActiveTaskStepBo> getActiveTaskStepBos() {
		return activeTaskStepBos;
	}

	public void setActiveTaskStepBos(List<ActiveTaskStepBo> activeTaskStepBos) {
		this.activeTaskStepBos = activeTaskStepBos;
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