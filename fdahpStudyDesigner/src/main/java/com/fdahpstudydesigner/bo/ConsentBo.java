package com.fdahpstudydesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * 
 * @author Ronalin
 *
 */
@Entity
@Table(name="consent")
@NamedQueries({
	@NamedQuery(name = "getConsentByStudyId", query = " From ConsentBo CBO WHERE CBO.studyId =:studyId order by CBO.createdOn DESC"),
	@NamedQuery(name = "updateStudyConsentVersion", query = "UPDATE ConsentBo SET live=2 WHERE customStudyId=:customStudyId"),
})
public class ConsentBo implements Serializable{

	private static final long serialVersionUID = 5564057544960167010L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="id")
	private Integer id;
	
	@Column(name = "study_id")
	private Integer studyId;
	
	@Column(name = "comprehension_test_minimum_score")
	private Integer comprehensionTestMinimumScore;
	
	@Column(name = "need_comprehension_test")
	private String  needComprehensionTest;
	
	@Column(name = "share_data_permissions")
	private String shareDataPermissions;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "tagline_description")
	private String taglineDescription;
	
	@Column(name = "short_description")
	private String shortDescription;
	
	@Column(name = "long_description")
	private String longDescription;
	
	
	@Column(name = "learn_more_text")
	private String learnMoreText;
	
	@Column(name = "consent_doc_type")
	private String consentDocType;
	
	@Column(name = "consent_doc_content")
	private String consentDocContent;
	
	@Column(name = "allow_without_permission")
	private String allowWithoutPermission;
	
	@Column(name = "html_consent")
	private String htmlConsent;
	
	@Column(name = "e_consent_firstname")
	private String eConsentFirstName="Yes";
	
	@Column(name = "e_consent_lastname")
	private String eConsentLastName="Yes";
	
	@Column(name = "e_consent_agree")
	private String eConsentAgree="Yes";
	
	@Column(name = "e_consent_signature")
	private String eConsentSignature="Yes";
	
	@Column(name = "e_consent_datetime")
	private String eConsentDatetime="Yes";
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_on")
	private String modifiedOn;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "version")
	private Float version = 0f;
	
	@Column(name = "custom_study_id")
	private String customStudyId;
	
	@Column(name = "is_live")
	private Integer live = 0;
	
	@Column(name="aggrement_of_consent")
	private String aggrementOfTheConsent;
	
	@Transient
	private String type;
	
	@Transient
	private String comprehensionTest;

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

	public Integer getComprehensionTestMinimumScore() {
		return comprehensionTestMinimumScore;
	}

	public void setComprehensionTestMinimumScore(
			Integer comprehensionTestMinimumScore) {
		this.comprehensionTestMinimumScore = comprehensionTestMinimumScore;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTaglineDescription() {
		return taglineDescription;
	}

	public void setTaglineDescription(String taglineDescription) {
		this.taglineDescription = taglineDescription;
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

	public String getLongDescription() {
		return longDescription;
	}

	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	public String getLearnMoreText() {
		return learnMoreText;
	}

	public void setLearnMoreText(String learnMoreText) {
		this.learnMoreText = learnMoreText;
	}

	public String getConsentDocType() {
		return consentDocType;
	}

	public void setConsentDocType(String consentDocType) {
		this.consentDocType = consentDocType;
	}

	public String getConsentDocContent() {
		return consentDocContent;
	}

	public void setConsentDocContent(String consentDocContent) {
		this.consentDocContent = consentDocContent;
	}

	public String getHtmlConsent() {
		return htmlConsent;
	}

	public void setHtmlConsent(String htmlConsent) {
		this.htmlConsent = htmlConsent;
	}

	public String geteConsentFirstName() {
		return eConsentFirstName;
	}

	public void seteConsentFirstName(String eConsentFirstName) {
		this.eConsentFirstName = eConsentFirstName;
	}

	public String geteConsentLastName() {
		return eConsentLastName;
	}

	public void seteConsentLastName(String eConsentLastName) {
		this.eConsentLastName = eConsentLastName;
	}

	public String geteConsentAgree() {
		return eConsentAgree;
	}

	public void seteConsentAgree(String eConsentAgree) {
		this.eConsentAgree = eConsentAgree;
	}

	public String geteConsentSignature() {
		return eConsentSignature;
	}

	public void seteConsentSignature(String eConsentSignature) {
		this.eConsentSignature = eConsentSignature;
	}

	public String geteConsentDatetime() {
		return eConsentDatetime;
	}

	public void seteConsentDatetime(String eConsentDatetime) {
		this.eConsentDatetime = eConsentDatetime;
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

	public Float getVersion() {
		return version;
	}

	public void setVersion(Float version) {
		this.version = version;
	}
	
	public String getCustomStudyId() {
		return customStudyId;
	}

	public void setCustomStudyId(String customStudyId) {
		this.customStudyId = customStudyId;
	}

	public Integer getLive() {
		return live;
	}

	public void setLive(Integer live) {
		this.live = live;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNeedComprehensionTest() {
		return needComprehensionTest;
	}

	public void setNeedComprehensionTest(String needComprehensionTest) {
		this.needComprehensionTest = needComprehensionTest;
	}

	public String getComprehensionTest() {
		return comprehensionTest;
	}

	public void setComprehensionTest(String comprehensionTest) {
		this.comprehensionTest = comprehensionTest;
	}

	public String getShareDataPermissions() {
		return shareDataPermissions;
	}

	public void setShareDataPermissions(String shareDataPermissions) {
		this.shareDataPermissions = shareDataPermissions;
	}

	public String getAllowWithoutPermission() {
		return allowWithoutPermission;
	}

	public void setAllowWithoutPermission(String allowWithoutPermission) {
		this.allowWithoutPermission = allowWithoutPermission;
	}

	public String getAggrementOfTheConsent() {
		return aggrementOfTheConsent;
	}

	public void setAggrementOfTheConsent(String aggrementOfTheConsent) {
		this.aggrementOfTheConsent = aggrementOfTheConsent;
	}
	
	
}
