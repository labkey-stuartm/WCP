package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Pradyumn
 *
 */

@Entity
@Table(name = "study_checklist")
@NamedQueries({
@NamedQuery(name = "getchecklistInfo",query = "SELECT CBO FROM Checklist CBO WHERE CBO.studyId =:studyId"),
})
public class Checklist implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7206666243059395497L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="checklist_id")
	private Integer checklistId;
	
	@Column(name="study_id")
	private Integer studyId;
	
	@Column(name = "checkbox1", length = 1)
	private boolean checkbox1;
	
	@Column(name = "checkbox2", length = 1)
	private boolean checkbox2;
	
	@Column(name = "checkbox3", length = 1)
	private boolean checkbox3;
	
	@Column(name = "checkbox4", length = 1)
	private boolean checkbox4;
	
	@Column(name = "checkbox5", length = 1)
	private boolean checkbox5;
	
	@Column(name = "checkbox6", length = 1)
	private boolean checkbox6;
	
	@Column(name = "checkbox7", length = 1)
	private boolean checkbox7;
	
	@Column(name = "checkbox8", length = 1)
	private boolean checkbox8;
	
	@Column(name = "checkbox9", length = 1)
	private boolean checkbox9;
	
	@Column(name = "checkbox10", length = 1)
	private boolean checkbox10;
	
	@Column(name = "study_version")
	private Integer studyVersion=1;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "modified_on")
	private String modifiedOn;

	public Integer getChecklistId() {
		return checklistId;
	}

	public void setChecklistId(Integer checklistId) {
		this.checklistId = checklistId;
	}

	public Integer getStudyId() {
		return studyId;
	}

	public void setStudyId(Integer studyId) {
		this.studyId = studyId;
	}

	public boolean isCheckbox1() {
		return checkbox1;
	}

	public void setCheckbox1(boolean checkbox1) {
		this.checkbox1 = checkbox1;
	}

	public boolean isCheckbox2() {
		return checkbox2;
	}

	public void setCheckbox2(boolean checkbox2) {
		this.checkbox2 = checkbox2;
	}

	public boolean isCheckbox3() {
		return checkbox3;
	}

	public void setCheckbox3(boolean checkbox3) {
		this.checkbox3 = checkbox3;
	}

	public boolean isCheckbox4() {
		return checkbox4;
	}

	public void setCheckbox4(boolean checkbox4) {
		this.checkbox4 = checkbox4;
	}

	public boolean isCheckbox5() {
		return checkbox5;
	}

	public void setCheckbox5(boolean checkbox5) {
		this.checkbox5 = checkbox5;
	}

	public boolean isCheckbox6() {
		return checkbox6;
	}

	public void setCheckbox6(boolean checkbox6) {
		this.checkbox6 = checkbox6;
	}

	public boolean isCheckbox7() {
		return checkbox7;
	}

	public void setCheckbox7(boolean checkbox7) {
		this.checkbox7 = checkbox7;
	}

	public boolean isCheckbox8() {
		return checkbox8;
	}

	public void setCheckbox8(boolean checkbox8) {
		this.checkbox8 = checkbox8;
	}

	public boolean isCheckbox9() {
		return checkbox9;
	}

	public void setCheckbox9(boolean checkbox9) {
		this.checkbox9 = checkbox9;
	}

	public boolean isCheckbox10() {
		return checkbox10;
	}

	public void setCheckbox10(boolean checkbox10) {
		this.checkbox10 = checkbox10;
	}

	public Integer getStudyVersion() {
		return studyVersion;
	}

	public void setStudyVersion(Integer studyVersion) {
		this.studyVersion = studyVersion;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
}
