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
@Table(name = "user_permissions")
public class UserPermissions{


	private Integer userRoleId;
	
	private String permissions;

	
	private Set<UserBO> users;
	
	public UserPermissions() {
	}

	public UserPermissions(Set<UserBO> users, String permissions) {
		this.setUsers(users);
		this.setPermissions(permissions);
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
	
	@Column(name = "permissions", nullable = false, length = 45)
	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}

}
