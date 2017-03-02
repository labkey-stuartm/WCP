package com.fdahpStudyDesigner.bean;




import org.springframework.web.multipart.MultipartFile;


public class StudyPageBean {
	
	private String pageId[];
	
	private String studyId;
	
	private String title[];
	
	private String imagePath[];
	
	private String description[];
	
	private MultipartFile multipartFiles[];
	
	private String originalFileName[];

	public String[] getPageId() {
		return pageId;
	}

	public void setPageId(String[] pageId) {
		this.pageId = pageId;
	}

	public String getStudyId() {
		return studyId;
	}

	public void setStudyId(String string) {
		this.studyId = string;
	}

	public String[] getTitle() {
		return title;
	}

	public void setTitle(String[] title) {
		this.title = title;
	}

	public String[] getImagePath() {
		return imagePath;
	}

	public void setImagePath(String[] imagePath) {
		this.imagePath = imagePath;
	}

	public String[] getDescription() {
		return description;
	}

	public void setDescription(String[] description) {
		this.description = description;
	}

	public MultipartFile[] getMultipartFiles() {
		return multipartFiles;
	}

	public void setMultipartFiles(MultipartFile[] multipartFiles) {
		this.multipartFiles = multipartFiles;
	}

	public String[] getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String[] originalFileName) {
		this.originalFileName = originalFileName;
	}
}
