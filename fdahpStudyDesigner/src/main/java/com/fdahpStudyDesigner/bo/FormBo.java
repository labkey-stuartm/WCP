package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="form")
public class FormBo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -902464262274352743L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="form_id")
	private Integer formId;
	
	@Column(name="form_order")
	private Integer formOrder;
	
	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getFormOrder() {
		return formOrder;
	}

	public void setFormOrder(Integer formOrder) {
		this.formOrder = formOrder;
	}

}