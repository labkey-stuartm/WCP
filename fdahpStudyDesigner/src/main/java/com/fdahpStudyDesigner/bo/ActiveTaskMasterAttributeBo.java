package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Ronalin
 *
 */
@Entity
@Table(name="active_task_master_attribute")
public class ActiveTaskMasterAttributeBo implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="active_task_master_attr_id")
	private Integer masterId;
	
	@Column(name = "task_type")
	private Integer taskType;
	

	@Column(name = "attribute_type")
	private String attributeType;
	
	@Column(name = "attribute_name")
	private String attributeName;
	
	@Column(name = "display_name")
	private Integer displayName;
	
	@Column(name = "attribute_data_type")
	private Integer attributeDataType;

	public Integer getMasterId() {
		return masterId;
	}

	public void setMasterId(Integer masterId) {
		this.masterId = masterId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Integer getDisplayName() {
		return displayName;
	}

	public void setDisplayName(Integer displayName) {
		this.displayName = displayName;
	}

	public Integer getAttributeDataType() {
		return attributeDataType;
	}

	public void setAttributeDataType(Integer attributeDataType) {
		this.attributeDataType = attributeDataType;
	}
}
