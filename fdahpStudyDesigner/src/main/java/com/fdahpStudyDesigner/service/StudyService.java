package com.fdahpStudyDesigner.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudyPageBo;

public interface StudyService {

	public List<StudyListBean> getStudyList(Integer userId) throws Exception;
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public StudyBo getStudyById(String studyId);
	public String saveOrUpdateStudy(StudyBo studyBo) throws Exception;
	public boolean deleteStudyPermissionById(Integer userId, String studyId) throws Exception;
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds) throws Exception;
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId) throws Exception;
	public Integer saveOverviewStudyPageById(String studyId) throws Exception;
	public String deleteOverviewStudyPageById(String studyId, String pageId) throws Exception;
	public String saveOrUpdateOverviewStudyPages(String studyId, String pageIds, String titles, String descs, List<MultipartFile> files);
	public List<StudyBo> getStudies();
}
