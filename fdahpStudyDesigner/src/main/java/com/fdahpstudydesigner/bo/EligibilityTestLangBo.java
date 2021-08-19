package com.fdahpstudydesigner.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "eligibility_test_lang")
@NamedQuery(
    name = "EligibilityTestLangBo.findById",
    query =
        "SELECT ETB FROM EligibilityTestLangBo ETB WHERE ETB.active = true AND ETB.id=:eligibilityTestId ORDER BY ETB.sequenceNo")
public class EligibilityTestLangBo {

  @Id
  @Column(name = "id")
  private Integer id;

  @Column(name = "lang_code")
  private String langCode;

  @Column(name = "eligibility_id")
  private Integer eligibilityId;

  @Column(name = "question")
  private String question;

  @Column(name = "sequence_no")
  private Integer sequenceNo;

  @Column(name = "status")
  private Boolean status = false;

  @Column(name = "active")
  private Boolean active = true;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getLangCode() {
    return langCode;
  }

  public void setLangCode(String langCode) {
    this.langCode = langCode;
  }

  public Integer getEligibilityId() {
    return eligibilityId;
  }

  public void setEligibilityId(Integer eligibilityId) {
    this.eligibilityId = eligibilityId;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Integer getSequenceNo() {
    return sequenceNo;
  }

  public void setSequenceNo(Integer sequenceNo) {
    this.sequenceNo = sequenceNo;
  }

  public Boolean getStatus() {
    return status;
  }

  public void setStatus(Boolean status) {
    this.status = status;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }
}
