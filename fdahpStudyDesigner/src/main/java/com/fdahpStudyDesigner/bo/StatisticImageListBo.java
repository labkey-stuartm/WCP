package com.fdahpStudyDesigner.bo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 
 * @author Ronalin
 *
 */
@Entity
@Table(name="statictic_master_images")
public class StatisticImageListBo implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="statistic_image_id")
	private Integer statisticImageId;
	
	@Column(name = "value")
	private String value;

	public Integer getStatisticImageId() {
		return statisticImageId;
	}

	public void setStatisticImageId(Integer statisticImageId) {
		this.statisticImageId = statisticImageId;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
