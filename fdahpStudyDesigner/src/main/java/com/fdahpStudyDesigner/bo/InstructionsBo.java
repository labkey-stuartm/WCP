package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="instructions")
public class InstructionsBo implements Serializable {
	
	private static final long serialVersionUID = 1389506581768527442L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name="instruction_title",length=250)
	private String instructionTitle;
	
	@Column(name="instruction_text",length=2500)
	private String instructionText;
	
	@Column(name="button_text",length=150)
	private String buttonText;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_on")
	private String modifiedOn;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Transient
	private Integer questionnaireId;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getInstructionTitle() {
		return instructionTitle;
	}

	public void setInstructionTitle(String instructionTitle) {
		this.instructionTitle = instructionTitle;
	}

	public String getInstructionText() {
		return instructionText;
	}

	public void setInstructionText(String instructionText) {
		this.instructionText = instructionText;
	}

	public String getButtonText() {
		return buttonText;
	}

	public void setButtonText(String buttonText) {
		this.buttonText = buttonText;
	}

	public Integer getQuestionnaireId() {
		return questionnaireId;
	}

	public void setQuestionnaireId(Integer questionnaireId) {
		this.questionnaireId = questionnaireId;
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
}
