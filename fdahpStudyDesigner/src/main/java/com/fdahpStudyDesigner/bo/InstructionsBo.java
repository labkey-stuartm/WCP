package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="instructions")
public class InstructionsBo implements Serializable {
	
	/**
	 * 
	 */
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
	private String button_text;

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

	public String getButton_text() {
		return button_text;
	}

	public void setButton_text(String button_text) {
		this.button_text = button_text;
	}
	
}
