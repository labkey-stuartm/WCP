package com.fdahpStudyDesigner.service;

import java.util.HashMap;
import java.util.List;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;

public interface StudyService {

	public List<StudyListBean> getStudyList(Integer userId) throws Exception;
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public StudyBo getStudyById(String studyId);
	public String saveOrUpdateStudy(StudyBo studyBo) throws Exception;
	public boolean deleteStudyPermissionById(Integer userId, String studyId) throws Exception;
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds) throws Exception;
}
