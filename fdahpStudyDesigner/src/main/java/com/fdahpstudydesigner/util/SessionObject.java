package com.fdahpstudydesigner.util;

import java.io.Serializable;


/**
 * @author 
 * 
 */
public class SessionObject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9080727824545069556L;
	
	private Integer userId = 0;
	private String userName = "";
	private String firstName = "";
	private String lastName = "";
	private String email = "";	
	private String phoneNumber = "";	
	private String currentHomeUrl = "";
	private String userType = "";
	private String userPermissions = "";
	private boolean loginStatus = false;
	private String passwordExpairdedDateTime;
	private boolean isSuperAdmin = false;
	private Integer superAdminId = 0;
	private Boolean isAdminstrating = false;
	private Integer adminstratorId = 0;
	private String createdDate = "";
	private Integer auditLogUniqueId = 0;
	private String termsText = "";
	private String privacyPolicyText = "";
	
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getCurrentHomeUrl() {
		return currentHomeUrl;
	}
	public void setCurrentHomeUrl(String currentHomeUrl) {
		this.currentHomeUrl = currentHomeUrl;
	}
	public boolean isLoginStatus() {
		return loginStatus;
	}
	public void setLoginStatus(boolean loginStatus) {
		this.loginStatus = loginStatus;
	}
	public String getUserPermissions() {
		return userPermissions;
	}
	public void setUserPermissions(String userPermissions) {
		this.userPermissions = userPermissions;
	}
	public String getPasswordExpairdedDateTime() {
		return passwordExpairdedDateTime;
	}
	public void setPasswordExpairdedDateTime(String passwordExpairdedDateTime) {
		this.passwordExpairdedDateTime = passwordExpairdedDateTime;
	}
	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}
	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}
	public Integer getSuperAdminId() {
		return superAdminId;
	}
	public void setSuperAdminId(Integer superAdminId) {
		this.superAdminId = superAdminId;
	}
	public Boolean getIsAdminstrating() {
		return isAdminstrating;
	}
	public void setIsAdminstrating(Boolean isAdminstrating) {
		this.isAdminstrating = isAdminstrating;
	}
	public Integer getAdminstratorId() {
		return adminstratorId;
	}
	public void setAdminstratorId(Integer adminstratorId) {
		this.adminstratorId = adminstratorId;
	}
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public Integer getAuditLogUniqueId() {
		return auditLogUniqueId;
	}
	public void setAuditLogUniqueId(Integer auditLogUniqueId) {
		this.auditLogUniqueId = auditLogUniqueId;
	}
	public String getTermsText() {
		return termsText;
	}
	public void setTermsText(String termsText) {
		this.termsText = termsText;
	}
	public String getPrivacyPolicyText() {
		return privacyPolicyText;
	}
	public void setPrivacyPolicyText(String privacyPolicyText) {
		this.privacyPolicyText = privacyPolicyText;
	}
}
