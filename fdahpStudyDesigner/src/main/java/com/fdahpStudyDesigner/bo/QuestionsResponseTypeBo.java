package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the questions_response_type database table.
 * @author Vivek
 */
@Entity
@Table(name="questions_response_type")
@NamedQuery(name="QuestionsResponseTypeBo.findAll", query="SELECT q FROM QuestionsResponseTypeBo q")
public class QuestionsResponseTypeBo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;

	@Column(name="parameter_name")
	private String parameterName;

	@Column(name="parameter_value")
	private String parameterValue;

	@Column(name="question_id")
	private int questionId;

	public QuestionsResponseTypeBo() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getParameterName() {
		return this.parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getParameterValue() {
		return this.parameterValue;
	}

	public void setParameterValue(String parameterValue) {
		this.parameterValue = parameterValue;
	}

	public int getQuestionId() {
		return this.questionId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

}