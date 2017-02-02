/**
 * 
 */
package com.fdahpStudyDesigner.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author Vivek
 *
 */
@Entity
@Table(name = "users_password_history")
@NamedQueries({
	@NamedQuery(name = "getPaswordHistoryByUserId", query = "From UserPasswordHistory UPH WHERE UPH.userId =:userId ORDER BY UPH.createdDate")
})
public class UserPasswordHistory {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "password_history_id")
	private Integer passwordHistoryId;
	
	@Column(name="user_id")
	private Integer userId;
	
	@Column(name = "password")
	private String userPassword;
	
	@Column(name = "created_date")
	private String createdDate;

	public Integer getPasswordHistoryId() {
		return passwordHistoryId;
	}

	public void setPasswordHistoryId(Integer passwordHistoryId) {
		this.passwordHistoryId = passwordHistoryId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
}
