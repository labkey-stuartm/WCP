package com.fdahpstudydesigner.bo;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "studies_lang")
public class StudyLanguageBO implements Serializable {

    @EmbeddedId
    private StudyLanguagePK studyLanguagePK;

    @Column(name = "custom_study_id")
    private String customStudyId;

    @Column(name = "name")
    private String name;

    @Column(name = "full_name")
    private String full_name;

    @Column(name = "study_tagline")
    private String study_tagline;

    @Column(name = "description")
    private String description;

    @Column(name = "allow_rejoin_text")
    private String allow_rejoin_text;

    @Column(name = "media_link")
    private String media_link;

    @Column(name = "instructional_text")
    private Integer instructionalText;

    @Column(name = "question_text")
    private String questionText;

    @Column(name = "correct_answer")
    private Boolean correctAnswer;

    @Column(name = "consent_doc_content")
    private String consent_doc_content;

    @Column(name = "agreement_of_consent")
    private String agreementOfConsent;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_on")
    private String createdOn;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modified_on")
    private String modifiedOn;

    public String getCustomStudyId() {
        return customStudyId;
    }

    public void setCustomStudyId(String customStudyId) {
        this.customStudyId = customStudyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getStudy_tagline() {
        return study_tagline;
    }

    public void setStudy_tagline(String study_tagline) {
        this.study_tagline = study_tagline;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAllow_rejoin_text() {
        return allow_rejoin_text;
    }

    public void setAllow_rejoin_text(String allow_rejoin_text) {
        this.allow_rejoin_text = allow_rejoin_text;
    }

    public String getMedia_link() {
        return media_link;
    }

    public void setMedia_link(String media_link) {
        this.media_link = media_link;
    }

    public Integer getInstructionalText() {
        return instructionalText;
    }

    public void setInstructionalText(Integer instructionalText) {
        this.instructionalText = instructionalText;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getConsent_doc_content() {
        return consent_doc_content;
    }

    public void setConsent_doc_content(String consent_doc_content) {
        this.consent_doc_content = consent_doc_content;
    }

    public String getAgreementOfConsent() {
        return agreementOfConsent;
    }

    public void setAgreementOfConsent(String agreementOfConsent) {
        this.agreementOfConsent = agreementOfConsent;
    }

    public StudyLanguagePK getStudyLanguagePK() {
        return studyLanguagePK;
    }

    public void setStudyLanguagePK(StudyLanguagePK studyLanguagePK) {
        this.studyLanguagePK = studyLanguagePK;
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
