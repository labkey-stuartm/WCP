package com.fdahpstudydesigner.bo;

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
 * 
 * @author BTC
 * The persistent class for the question_responsetype_master_info database table.
 *
 */
@Entity
@Table(name="question_responsetype_master_info")
@NamedQueries({
@NamedQuery(name = "getResponseTypes",query = "from QuestionResponseTypeMasterInfoBo QRTMBO"),
})
public class QuestionResponseTypeMasterInfoBo implements Serializable {

	private static final long serialVersionUID = -2666359241071290949L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="response_type")
	private String responseType;
	
	@Column(name="description")
	private String description;
	
	@Column(name="data_type")
	private String dataType;
	
	@Column(name="dashboard_allowed")
	private Boolean dashBoardAllowed;
	
	@Column(name="choice_based_branching")
	private Boolean choinceBasedBraching;
	
	@Column(name="formula_based_logic")
	private Boolean formulaBasedLogic;
	
	@Column(name="healthkit_alternative")
	private Boolean healthkitAlternative;
	
	@Column(name="anchor_date")
	private Boolean anchorDate;
	
	@Column(name="response_type_code")
	private String responseTypeCode;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getResponseType() {
		return responseType;
	}

	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Boolean getDashBoardAllowed() {
		return dashBoardAllowed;
	}

	public void setDashBoardAllowed(Boolean dashBoardAllowed) {
		this.dashBoardAllowed = dashBoardAllowed;
	}

	public Boolean getChoinceBasedBraching() {
		return choinceBasedBraching;
	}

	public void setChoinceBasedBraching(Boolean choinceBasedBraching) {
		this.choinceBasedBraching = choinceBasedBraching;
	}

	public Boolean getFormulaBasedLogic() {
		return formulaBasedLogic;
	}

	public void setFormulaBasedLogic(Boolean formulaBasedLogic) {
		this.formulaBasedLogic = formulaBasedLogic;
	}

	public Boolean getHealthkitAlternative() {
		return healthkitAlternative;
	}

	public void setHealthkitAlternative(Boolean healthkitAlternative) {
		this.healthkitAlternative = healthkitAlternative;
	}

	public Boolean getAnchorDate() {
		return anchorDate;
	}

	public void setAnchorDate(Boolean anchorDate) {
		this.anchorDate = anchorDate;
	}
}
