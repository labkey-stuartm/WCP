/**
 *
 */
package com.fdahpstudydesigner.bean;

/**
 * @author Vivek
 *
 */
public class StudySessionBean {
	private Integer sessionStudyCount;
	private String studyId;
	private String permission;
	private String isLive;

	/**
	 * @return the isLive
	 */
	public String getIsLive() {
		return isLive;
	}

	/**
	 * @return the permission
	 */
	public String getPermission() {
		return permission;
	}

	/**
	 * @return the sessionStudyCount
	 */
	public Integer getSessionStudyCount() {
		return sessionStudyCount;
	}

	/**
	 * @return the studyId
	 */
	public String getStudyId() {
		return studyId;
	}

	/**
	 * @param isLive
	 *            the isLive to set
	 */
	public void setIsLive(String isLive) {
		this.isLive = isLive;
	}

	/**
	 * @param permission
	 *            the permission to set
	 */
	public void setPermission(String permission) {
		this.permission = permission;
	}

	/**
	 * @param sessionStudyCount
	 *            the sessionStudyCount to set
	 */
	public void setSessionStudyCount(Integer sessionStudyCount) {
		this.sessionStudyCount = sessionStudyCount;
	}

	/**
	 * @param studyId
	 *            the studyId to set
	 */
	public void setStudyId(String studyId) {
		this.studyId = studyId;
	}

}
