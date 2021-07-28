package com.fdahpstudydesigner.bo;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * The persistent class for the users database table.
 *
 * @author BTC
 */

@Entity
@Table(name = "users")
@NamedQueries({@NamedQuery(name = "getUserByEmail", query = "select UBO from UserBO UBO where UBO.userEmail =:email"),
        @NamedQuery(name = "getUserById", query = "SELECT UBO FROM UserBO UBO WHERE UBO.userId =:userId"),
        @NamedQuery(name = "getUserBySecurityToken", query = "select UBO from UserBO UBO where UBO.securityToken =:securityToken"),})
public class UserBO implements Serializable {

    private static final long serialVersionUID = 135353554543L;

    @Column(name = "access_code")
    private String accessCode;

    @Column(name = "accountNonExpired", length = 1)
    private boolean accountNonExpired;

    @Column(name = "accountNonLocked", length = 1)
    private boolean accountNonLocked;

    @Column(name = "created_by")
    private Integer createdBy;

    @Column(name = "created_date")
    private String createdOn;

    @Column(name = "credentialsNonExpired", length = 1)
    private boolean credentialsNonExpired;

    @Column(name = "email_changed", columnDefinition = "TINYINT(1)")
    private Boolean emailChanged = false;

    @Column(name = "status", length = 1)
    private boolean enabled;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "force_logout")
    @Type(type = "yes_no")
    private boolean forceLogout = false;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "modified_by")
    private Integer modifiedBy;

    @Column(name = "modified_date")
    private String modifiedOn;

    @Column(name = "password_expairded_datetime")
    private String passwordExpairdedDateTime;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "user_permission_mapping", joinColumns = {
            @JoinColumn(name = "user_id", nullable = false)}, inverseJoinColumns = {
            @JoinColumn(name = "permission_id", nullable = false)})
    private Set<UserPermissions> permissionList = new HashSet<>(0);

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "role_id")
    private Integer roleId;

    @Transient
    private String roleName;

    @Column(name = "security_token")
    private String securityToken;

    @Column(name = "token_expiry_date")
    private String tokenExpiryDate;

    @Column(name = "token_used")
    private Boolean tokenUsed;

    @Column(name = "email")
    private String userEmail;

    @Transient
    private String userFullName;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "user_login_datetime")
    private String userLastLoginDateTime;

    @Column(name = "password")
    private String userPassword;

    @Column(name = "salt")
    private String salt;

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

    public Boolean getEmailChanged() {
        return emailChanged;
    }

    public void setEmailChanged(Boolean emailChanged) {
        this.emailChanged = emailChanged;
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

    public String getPasswordExpairdedDateTime() {
        return passwordExpairdedDateTime;
    }

    public void setPasswordExpairdedDateTime(String passwordExpairdedDateTime) {
        this.passwordExpairdedDateTime = passwordExpairdedDateTime;
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
     * @return the permissions
     */
    public Set<UserPermissions> getPermissions() {
        return permissionList;
    }

    /**
     * @param permissionList the permissions to set
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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
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

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserLastLoginDateTime() {
        return userLastLoginDateTime;
    }

    public void setUserLastLoginDateTime(String userLastLoginDateTime) {
        this.userLastLoginDateTime = userLastLoginDateTime;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isForceLogout() {
        return forceLogout;
    }

    public void setForceLogout(boolean forceLogout) {
        this.forceLogout = forceLogout;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }
}
