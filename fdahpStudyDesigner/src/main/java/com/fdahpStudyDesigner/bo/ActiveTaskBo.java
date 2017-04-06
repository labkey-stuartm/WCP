package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.*;

import java.util.ArrayList;
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

	@Column(name="frequency")
	private String frequency;
	
	@Column(name="duration")
	private String duration;

	@Column(name="study_id")
	private Integer studyId;

	@Column(name="task_title")
	private String title;
	
	@Column(name="created_by")
	private Integer createdBy;
	
	@Column(name="created_date")
	private String createdDate;
	
	@Column(name="modified_by")
	private Integer modifiedBy;
	
	@Column(name="modified_date")
	private String modifiedDate;
	
	@Column(name="repeat_active_task")
	private Integer repeatActiveTask;
	
	@Column(name="day_of_the_week")
	private String dayOfTheWeek;
	
	@Transient
	private String previousFrequency;
	
	@Transient
	private String type;
	
	@Transient
	private List<ActiveTaskFrequencyBo> activeTaskFrequenciesList = new ArrayList<ActiveTaskFrequencyBo>();
	
	@Transient 
	private ActiveTaskFrequencyBo activeTaskFrequenciesBo = new ActiveTaskFrequencyBo();
	
	@Transient
	private List<ActiveTaskCustomScheduleBo> activeTaskCustomScheduleBo = new ArrayList<ActiveTaskCustomScheduleBo>();
	
	@Column(name = "task_type_id")
	private Integer taskTypeId;
	
	@Column(name="display_name")
	private String displayName;
	
	@Column(name="short_title")
	private String shortTitle;
	
	@Column(name="instruction")
	private String instruction;
	
	@Column(name = "action", length = 1)
	private boolean action;
	
	@Transient
	List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<ActiveTaskMasterAttributeBo>();
	
	@Transient
	List<ActiveTaskAtrributeValuesBo> taskAttributeValueBos = new ArrayList<ActiveTaskAtrributeValuesBo>();
	
	@Transient
	private String buttonText;
	
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
		return this.studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}
	
	public Integer getTaskTypeId() {
		return taskTypeId;
	}

	public void setTaskTypeId(Integer taskTypeId) {
		this.taskTypeId = taskTypeId;
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
	
	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}

	public List<ActiveTaskMasterAttributeBo> getTaskMasterAttributeBos() {
		return taskMasterAttributeBos;
	}

	public void setTaskMasterAttributeBos(List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos) {
		this.taskMasterAttributeBos = taskMasterAttributeBos;
	}

	public List<ActiveTaskAtrributeValuesBo> getTaskAttributeValueBos() {
		return taskAttributeValueBos;
	}

	public void setTaskAttributeValueBos(List<ActiveTaskAtrributeValuesBo> taskAttributeValueBos) {
		this.taskAttributeValueBos = taskAttributeValueBos;
	}
	

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	/**
	 * @return the createdBy
	 */
	public Integer getCreatedBy() {
		return createdBy;
	}

	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
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
	 * @return the modifiedBy
	 */
	public Integer getModifiedBy() {
		return modifiedBy;
	}

	/**
	 * @param modifiedBy the modifiedBy to set
	 */
	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
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
	 * @return the dayOfTheWeek
	 */
	public String getDayOfTheWeek() {
		return dayOfTheWeek;
	}

	/**
	 * @param dayOfTheWeek the dayOfTheWeek to set
	 */
	public void setDayOfTheWeek(String dayOfTheWeek) {
		this.dayOfTheWeek = dayOfTheWeek;
	}

	/**
	 * @return the previousFrequency
	 */
	public String getPreviousFrequency() {
		return previousFrequency;
	}

	/**
	 * @param previousFrequency the previousFrequency to set
	 */
	public void setPreviousFrequency(String previousFrequency) {
		this.previousFrequency = previousFrequency;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the activeTaskFrequenciesList
	 */
	public List<ActiveTaskFrequencyBo> getActiveTaskFrequenciesList() {
		return activeTaskFrequenciesList;
	}

	/**
	 * @param activeTaskFrequenciesList the activeTaskFrequenciesList to set
	 */
	public void setActiveTaskFrequenciesList(
			List<ActiveTaskFrequencyBo> activeTaskFrequenciesList) {
		this.activeTaskFrequenciesList = activeTaskFrequenciesList;
	}

	/**
	 * @return the activeTaskFrequenciesBo
	 */
	public ActiveTaskFrequencyBo getActiveTaskFrequenciesBo() {
		return activeTaskFrequenciesBo;
	}

	/**
	 * @param activeTaskFrequenciesBo the activeTaskFrequenciesBo to set
	 */
	public void setActiveTaskFrequenciesBo(
			ActiveTaskFrequencyBo activeTaskFrequenciesBo) {
		this.activeTaskFrequenciesBo = activeTaskFrequenciesBo;
	}

	/**
	 * @return the activeTaskCustomScheduleBo
	 */
	public List<ActiveTaskCustomScheduleBo> getActiveTaskCustomScheduleBo() {
		return activeTaskCustomScheduleBo;
	}

	/**
	 * @param activeTaskCustomScheduleBo the activeTaskCustomScheduleBo to set
	 */
	public void setActiveTaskCustomScheduleBo(
			List<ActiveTaskCustomScheduleBo> activeTaskCustomScheduleBo) {
		this.activeTaskCustomScheduleBo = activeTaskCustomScheduleBo;
	}

	/**
	 * @return the repeatActiveTask
	 */
	public Integer getRepeatActiveTask() {
		return repeatActiveTask;
	}

	/**
	 * @param repeatActiveTask the repeatActiveTask to set
	 */
	public void setRepeatActiveTask(Integer repeatActiveTask) {
		this.repeatActiveTask = repeatActiveTask;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

}