package com.fdahpstudydesigner.bo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "comprehension_test_response_lang")
public class ComprehensionResponseLangBo implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "question_id")
  private Integer questionId;

  @Column(name = "correct_answer")
  private Boolean correctAnswer;

  @Column(name = "response_option")
  private String responseOption;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Integer questionId) {
    this.questionId = questionId;
  }

  public Boolean getCorrectAnswer() {
    return correctAnswer;
  }

  public void setCorrectAnswer(Boolean correctAnswer) {
    this.correctAnswer = correctAnswer;
  }

  public String getResponseOption() {
    return responseOption;
  }

  public void setResponseOption(String responseOption) {
    this.responseOption = responseOption;
  }
}
