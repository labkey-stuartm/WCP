package com.fdahpstudydesigner.bo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;

/**
 * @author Pradyumn
 *
 */

@Entity
@Table(name = "master_data")
@NamedQueries({
@NamedQuery(name = "getMasterDataByType",query = "select MDBO from MasterDataBO MDBO where MDBO.type =:type"),
})
public class MasterDataBO {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer id;
	
	@Column(name="type")
	private String type;
	
	@Column(name="terms_text")
	private String termsText;
	
	@Column(name="privacy_policy_text")
	private String privacyPolicyText;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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
