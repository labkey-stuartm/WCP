package com.fdahpStudyDesigner.bean;


public class StudyListBean {

	private Integer id;
	private String customStudyId;
	private String name;
    private String category;
	private String researchSponsor;
	private String projectLead;
	private boolean viewPermission;
	
	
	public StudyListBean(Integer id, String customStudyId, String name, String category, String researchSponsor,
			String projectLead, boolean viewPermission) {
		super();
		this.id = id;
		this.customStudyId = customStudyId;
		this.name = name;
		this.category = category;
		this.researchSponsor = researchSponsor;
		this.projectLead = projectLead;
		this.viewPermission = viewPermission;
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
	public String getProjectLead() {
		return projectLead;
	}
	public void setProjectLead(String projectLead) {
		this.projectLead = projectLead;
	}
	public boolean isViewPermission() {
		return viewPermission;
	}
	public void setViewPermission(boolean viewPermission) {
		this.viewPermission = viewPermission;
	}
	
}

