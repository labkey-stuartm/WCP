package com.fdahpStudyDesigner.bo;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * @author Pradyumn
 *
 */

@Entity
@Table(name = "permissions")
public class UserPermissions{

	private Integer userRoleId;
	
	private String permission;

	
	private Set<UserBO> users;
	
	public UserPermissions() {
	}

	public UserPermissions(Set<UserBO> users, String permissions) {
		this.setUsers(users);
		this.setPermission(permissions);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "permission_id",
		unique = true, nullable = false)
	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	@ManyToMany(fetch = FetchType.EAGER)
	public Set<UserBO> getUsers() {
		return users;
	}

	public void setUsers(Set<UserBO> users) {
		this.users = users;
	}
	
	@Column(name = "permission", nullable = false, length = 45)
	public String getPermission() {
		return permission;
	}

	public void setPermission(String permission) {
		this.permission = permission;
	}
	

}
