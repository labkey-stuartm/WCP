package com.fdahpStudyDesigner.bean;


public class StudyListBean {

	private Integer id;
	private String customStudyId;
	private String name;
    private String category;
	private String researchSponsor;
	private Integer projectLead;
	private boolean viewPermission;
	private String projectLeadName;
	private String status;
	private String createdOn;
	
	public StudyListBean(Integer id, String customStudyId, String name, String category, String researchSponsor,
			Integer projectLead, boolean viewPermission, String status, String createdOn) {
		super();
		this.id = id;
		this.customStudyId = customStudyId;
		this.name = name;
		this.category = category;
		this.researchSponsor = researchSponsor;
		this.projectLead = projectLead;
		this.viewPermission = viewPermission;
		this.status = status;
		this.createdOn = createdOn;
	}


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


	public Integer getProjectLead() {
		return projectLead;
	}


	public void setProjectLead(Integer projectLead) {
		this.projectLead = projectLead;
	}


	public boolean isViewPermission() {
		return viewPermission;
	}


	public void setViewPermission(boolean viewPermission) {
		this.viewPermission = viewPermission;
	}


	public String getProjectLeadName() {
		return projectLeadName;
	}


	public void setProjectLeadName(String projectLeadName) {
		this.projectLeadName = projectLeadName;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getCreatedOn() {
		return createdOn;
	}


	public void setCreatedOn(String createdOn) {
		this.createdOn = createdOn;
	}
	
}

