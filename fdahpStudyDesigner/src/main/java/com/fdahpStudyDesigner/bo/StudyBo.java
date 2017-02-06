package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Ronalin
 *
 */
@Entity
@Table(name = "studies")
public class StudyBo implements Serializable{
	
	private static final long serialVersionUID = 2147840266295837728L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "custom_study_id")
	private String customStudyId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "full_name")
	private String fullName;
	
	@Column(name = "type")
	private String type;
	
	@Column(name = "platform")
	private String platform;
	
	@Column(name = "category")
	private String category;
	
	@Column(name = "research_sponsor")
	private String researchSponsor;
	
	@Column(name = "data_partner")
	private String dataPartner;
	
	@Column(name = "tentative_duration")
	private Integer tentativeDuration;
	
	@Column(name = "tentative_duration_weekmonth")
	private String tentativeDurationWeekmonth;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "enrolling_participants")
	private String enrollingParticipants;
	
	@Column(name = "retain_participant")
	private String retainParticipant;
	
	@Column(name = "allow_rejoin")
	private String allowRejoin;
	
	@Column(name = "irb_review")
	private String irbReview;
	
	@Column(name = "inbox_email_address")
	private String inboxEmailAddress;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_on")
	private String modifiedOn;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "status")
	private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

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

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getResearchSponsor() {
		return researchSponsor;
	}

	public void setResearchSponsor(String researchSponsor) {
		this.researchSponsor = researchSponsor;
	}

	public String getDataPartner() {
		return dataPartner;
	}

	public void setDataPartner(String dataPartner) {
		this.dataPartner = dataPartner;
	}

	public Integer getTentativeDuration() {
		return tentativeDuration;
	}

	public void setTentativeDuration(Integer tentativeDuration) {
		this.tentativeDuration = tentativeDuration;
	}

	public String getTentativeDurationWeekmonth() {
		return tentativeDurationWeekmonth;
	}

	public void setTentativeDurationWeekmonth(String tentativeDurationWeekmonth) {
		this.tentativeDurationWeekmonth = tentativeDurationWeekmonth;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnrollingParticipants() {
		return enrollingParticipants;
	}

	public void setEnrollingParticipants(String enrollingParticipants) {
		this.enrollingParticipants = enrollingParticipants;
	}

	public String getRetainParticipant() {
		return retainParticipant;
	}

	public void setRetainParticipant(String retainParticipant) {
		this.retainParticipant = retainParticipant;
	}

	public String getAllowRejoin() {
		return allowRejoin;
	}

	public void setAllowRejoin(String allowRejoin) {
		this.allowRejoin = allowRejoin;
	}

	public String getIrbReview() {
		return irbReview;
	}

	public void setIrbReview(String irbReview) {
		this.irbReview = irbReview;
	}

	public String getInboxEmailAddress() {
		return inboxEmailAddress;
	}

	public void setInboxEmailAddress(String inboxEmailAddress) {
		this.inboxEmailAddress = inboxEmailAddress;
	}

	public String getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}

	public String getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(String modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
