package com.fdahpStudyDesigner.dao;

/**
 * 
 * @author Ronalin
 *
 */
import java.util.List;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.StudyBo;

public interface StudyDAO {

	public List<StudyListBean> getStudyList(Integer userId) throws Exception;
	public String saveOrUpdateStudy(StudyBo studyBo) throws Exception;
}
