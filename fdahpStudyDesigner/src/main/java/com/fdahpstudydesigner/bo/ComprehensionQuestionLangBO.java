package com.fdahpstudydesigner.bo;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "comprehension_test_question_lang")
public class ComprehensionQuestionLangBO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "study_id")
    private Integer studyId;

    @Column(name = "sequence_no")
    private Integer sequenceNo;

    @Column(name = "structure_of_correct_ans")
    private Boolean structureOfCorrectAns = true;

    @Column(name = "active")
    private Boolean active = true;

    @Column(name = "status")
    private Boolean status;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modified_on")
    private String modifiedOn;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStudyId() {
        return studyId;
    }

    public void setStudyId(Integer studyId) {
        this.studyId = studyId;
    }

    public Integer getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(Integer sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public Boolean getStructureOfCorrectAns() {
        return structureOfCorrectAns;
    }

    public void setStructureOfCorrectAns(Boolean structureOfCorrectAns) {
        this.structureOfCorrectAns = structureOfCorrectAns;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
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
