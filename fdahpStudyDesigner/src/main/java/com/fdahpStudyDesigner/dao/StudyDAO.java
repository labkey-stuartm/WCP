package com.fdahpStudyDesigner.dao;

import java.util.HashMap;
/**
 * 
 * @author Ronalin
 *
 */
import java.util.List;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;

public interface StudyDAO {

	public List<StudyListBean> getStudyList(Integer userId) throws Exception;
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public String saveOrUpdateStudy(StudyBo studyBo) throws Exception;
	public StudyBo getStudyById(String studyId);
}
