package com.fdahpStudyDesigner.bo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Vivek
 *
 */
@Entity
@Table(name = "users")

@NamedQueries({
@NamedQuery(name = "getLoginUser", query = "select UBO from UserBO UBO where UBO.userEmail =:email and UBO.userPassword =:password and active = 1"),
@NamedQuery(name = "getUserList", query = "select UBO from UserBO UBO where UBO.userId !=:userId"),
@NamedQuery(name = "getUserByEmail",query = "select UBO from UserBO UBO where UBO.userEmail =:email"),
@NamedQuery(name = "getUserBySecurityToken",query = "select UBO from UserBO UBO where UBO.securityToken =:securityToken"),
@NamedQuery(name = "getUserByUserId", query = "select UBO from UserBO UBO where UBO.userId =:userId"),
@NamedQuery(name = "getUsers",query = "select UBO from UserBO UBO "),
@NamedQuery( name="updateScheduleFlag", query = "update UserBO UBO set UBO.enabled=:enabled,UBO.schedulerStatusFlag=:schedulerStatusFlag,UBO.pendingToDeactivate=:pendingToDeactivate where UBO.userId=:userId"),

})

public class UserBO implements Serializable{

	private static final long serialVersionUID = 135353554543L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name = "first_name")
	private String firstName;
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "email")
	private String userEmail;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "fax_number")
	private String faxNumber;

	@Column(name = "password")
	private String userPassword;
	
	@Column(name = "status", length = 1)
	private boolean enabled;
	
	@Column(name = "created_date_time")
	private String createdOn;
	
	@Column(name = "modified_date_time")
	private String modifiedOn;
	
	@Column(name = "created_by")
	private Integer createdBy;
	
	@Column(name = "modified_by")
	private Integer modifiedBy;
	
	@Column(name = "accountNonExpired", length = 1)
	private boolean accountNonExpired;
	
	@Column(name = "credentialsNonExpired", length = 1)
	private boolean credentialsNonExpired;
	
	@Column(name = "accountNonLocked", length = 1)
	private boolean accountNonLocked;

	@Column(name = "access_code")
	private String accessCode;
	
	@Column(name = "security_token")
	private String securityToken;
	
	@Column(name = "token_used")
	private Boolean tokenUsed;
	
	@Column(name = "token_expiry_date")
	private String tokenExpiryDate;
	
	@Column(name = "user_type")
	private String userType;
	
	@Column(name = "asp_hi_id")
	private Integer aspHiId;
	
	@Column(name = "super_admin_id")
	private Integer superAdminId = 0;
	
	@Column(name = "password_expairded_datetime")
	private String passwordExpairdedDateTime;
	
	@Column(name = "is_super_admin", length = 1)
	private boolean isSuperAdmin = false;
	
	@Column(name = "scheduler_status_flag")
	private Integer schedulerStatusFlag=1;
	
	@Column(name = "pending_to_deactivate", length = 1)
	private boolean pendingToDeactivate = false;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "user_permission_mapping", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false) },
			inverseJoinColumns = { @JoinColumn(name = "permission_id",
					nullable = false) })
	private Set<UserPermissions> permissionList = new HashSet<UserPermissions>(0);
	
	@Transient
	private String hiLogo;
	
	@Transient
	private String aspLogo;
	
	@Transient
	private Integer userTempId;
	
	@Transient
	private String aspHiName;
	
	@Transient
	private String aspHiCity;
	
	@Transient
	private String aspHiState;
	
	@Transient
	private String permissionsName;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the accountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @param accountNonExpired the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @param accountNonLocked the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @return the permissions
	 */
	public Set<UserPermissions> getPermissions() {
		return permissionList;
	}

	/**
	 * @param permissions the permissions to set
	 */
	public void setPermissions(Set<UserPermissions> permissionList) {
		this.permissionList = permissionList;
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * @return the accessCode
	 */
	public String getAccessCode() {
		return accessCode;
	}

	/**
	 * @param accessCode the accessCode to set
	 */
	public void setAccessCode(String accessCode) {
		this.accessCode = accessCode;
	}

	/**
	 * @return the securityToken
	 */
	public String getSecurityToken() {
		return securityToken;
	}

	/**
	 * @param securityToken the securityToken to set
	 */
	public void setSecurityToken(String securityToken) {
		this.securityToken = securityToken;
	}

	/**
	 * @return the tokenExpiryDate
	 */
	public String getTokenExpiryDate() {
		return tokenExpiryDate;
	}

	/**
	 * @param tokenExpiryDate the tokenExpiryDate to set
	 */
	public void setTokenExpiryDate(String tokenExpiryDate) {
		this.tokenExpiryDate = tokenExpiryDate;
	}

	/**
	 * @return the userType
	 */
	public String getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(String userType) {
		this.userType = userType;
	}

	/**
	 * @return the permissionList
	 */
	public Set<UserPermissions> getPermissionList() {
		return permissionList;
	}

	/**
	 * @param permissionList the permissionList to set
	 */
	public void setPermissionList(Set<UserPermissions> permissionList) {
		this.permissionList = permissionList;
	}

	/**
	 * @return the tokenUsed
	 */
	public Boolean getTokenUsed() {
		return tokenUsed;
	}

	/**
	 * @param tokenUsed the tokenUsed to set
	 */
	public void setTokenUsed(Boolean tokenUsed) {
		this.tokenUsed = tokenUsed;
	}

	public Integer getAspHiId() {
		return aspHiId;
	}

	public void setAspHiId(Integer aspHiId) {
		this.aspHiId = aspHiId;
	}
	
	public Integer getSuperAdminId() {
		return superAdminId;
	}

	public void setSuperAdminId(Integer superAdminId) {
		this.superAdminId = superAdminId;
	}

	public String getHiLogo() {
		return hiLogo;
	}

	public void setHiLogo(String hiLogo) {
		this.hiLogo = hiLogo;
	}

	public String getAspLogo() {
		return aspLogo;
	}

	public void setAspLogo(String aspLogo) {
		this.aspLogo = aspLogo;
	}

	public String getPasswordExpairdedDateTime() {
		return passwordExpairdedDateTime;
	}

	public void setPasswordExpairdedDateTime(String passwordExpairdedDateTime) {
		this.passwordExpairdedDateTime = passwordExpairdedDateTime;
	}

	public Integer getUserTempId() {
		return userTempId;
	}

	public void setUserTempId(Integer userTempId) {
		this.userTempId = userTempId;
	}

	public boolean isSuperAdmin() {
		return isSuperAdmin;
	}

	public void setSuperAdmin(boolean isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getAspHiName() {
		return aspHiName;
	}

	public void setAspHiName(String aspHiName) {
		this.aspHiName = aspHiName;
	}

	public String getPermissionsName() {
		return permissionsName;
	}

	public void setPermissionsName(String permissionsName) {
		this.permissionsName = permissionsName;
	}
	public Integer getSchedulerStatusFlag() {
		return schedulerStatusFlag;
	}

	public void setSchedulerStatusFlag(Integer schedulerStatusFlag) {
		this.schedulerStatusFlag = schedulerStatusFlag;
	}
	
	public boolean isPendingToDeactivate() {
		return pendingToDeactivate;
	}

	public void setPendingToDeactivate(boolean pendingToDeactivate) {
		this.pendingToDeactivate = pendingToDeactivate;
	}

	public String getAspHiCity() {
		return aspHiCity;
	}

	public void setAspHiCity(String aspHiCity) {
		this.aspHiCity = aspHiCity;
	}

	public String getAspHiState() {
		return aspHiState;
	}

	public void setAspHiState(String aspHiState) {
		this.aspHiState = aspHiState;
	}
	
	
}

