package com.fdahpstudydesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="comprehension_test_response")
public class ComprehensionTestResponseBo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7739882770594873383L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="comprehension_test_question_id")
	private Integer comprehensionTestQuestionId;
	
	@Column(name="response_option")
	private String responseOption;
	
	@Column(name = "correct_answer")
	private boolean correctAnswer;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getComprehensionTestQuestionId() {
		return comprehensionTestQuestionId;
	}

	public void setComprehensionTestQuestionId(Integer comprehensionTestQuestionId) {
		this.comprehensionTestQuestionId = comprehensionTestQuestionId;
	}

	public String getResponseOption() {
		return responseOption;
	}

	public void setResponseOption(String responseOption) {
		this.responseOption = responseOption;
	}

	public boolean isCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(boolean correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
	
}
