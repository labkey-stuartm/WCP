package com.fdahpstudydesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Pradyumn
 *
 */

@Entity
@Table(name = "resources")
@NamedQueries({
@NamedQuery(name = "getResourceInfo",query = "SELECT RBO FROM ResourceBO RBO WHERE RBO.id =:resourceInfoId"),
})
public class ResourceBO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4548349227102496191L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="study_id")
	private Integer studyId;
	
	@Column(name="title")
	private String title;
	
	@Column(name = "text_or_pdf", length = 1)
	private boolean textOrPdf;
	
	@Column(name="rich_text")
	private String richText;
	
	@Column(name="pdf_url")
	private String pdfUrl;
	
	@Column(name="pdf_name")
	private String pdfName;
	
	@Column(name = "resource_visibility", length = 1)
	private boolean resourceVisibility;
	
	@Column(name = "resource_type", length = 1)
	private boolean resourceType;
	
	@Column(name="time_period_from_days")
	private Integer timePeriodFromDays;
	
	@Column(name="time_period_to_days")
	private Integer timePeriodToDays;
	
	@Column(name="start_date")
	private String startDate;
	
	@Column(name="end_date")
	private String endDate;
	
	@Column(name="resource_text")
	private String resourceText;
	
	@Column(name = "action", length = 1)
	private boolean action;
	
	@Column(name = "study_protocol", length = 1)
	private boolean studyProtocol;
	
	@Column(name = "status", length = 1)
	private boolean status;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "created_on")
	private String createdOn;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "modified_on")
	private String modifiedOn;
	
	@Transient
	private MultipartFile pdfFile;
	
	@Column(name="anchor_date")
	private String anchorDate;
	

	public String getAnchorDate() {
		return anchorDate;
	}

	public void setAnchorDate(String anchorDate) {
		this.anchorDate = anchorDate;
	}

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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isTextOrPdf() {
		return textOrPdf;
	}

	public void setTextOrPdf(boolean textOrPdf) {
		this.textOrPdf = textOrPdf;
	}

	public String getRichText() {
		return richText;
	}

	public void setRichText(String richText) {
		this.richText = richText;
	}

	public String getPdfUrl() {
		return pdfUrl;
	}

	public void setPdfUrl(String pdfUrl) {
		this.pdfUrl = pdfUrl;
	}
	
	public String getPdfName() {
		return pdfName;
	}

	public void setPdfName(String pdfName) {
		this.pdfName = pdfName;
	}

	public boolean isResourceVisibility() {
		return resourceVisibility;
	}

	public void setResourceVisibility(boolean resourceVisibility) {
		this.resourceVisibility = resourceVisibility;
	}
	
	public boolean isResourceType() {
		return resourceType;
	}

	public void setResourceType(boolean resourceType) {
		this.resourceType = resourceType;
	}

	public Integer getTimePeriodFromDays() {
		return timePeriodFromDays;
	}

	public void setTimePeriodFromDays(Integer timePeriodFromDays) {
		this.timePeriodFromDays = timePeriodFromDays;
	}

	public Integer getTimePeriodToDays() {
		return timePeriodToDays;
	}

	public void setTimePeriodToDays(Integer timePeriodToDays) {
		this.timePeriodToDays = timePeriodToDays;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getResourceText() {
		return resourceText;
	}

	public void setResourceText(String resourceText) {
		this.resourceText = resourceText;
	}
	
	public boolean isAction() {
		return action;
	}

	public void setAction(boolean action) {
		this.action = action;
	}
	
	public boolean isStudyProtocol() {
		return studyProtocol;
	}

	public void setStudyProtocol(boolean studyProtocol) {
		this.studyProtocol = studyProtocol;
	}
	
	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
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

	public MultipartFile getPdfFile() {
		return pdfFile;
	}

	public void setPdfFile(MultipartFile pdfFile) {
		this.pdfFile = pdfFile;
	}

}
