package com.fdahpStudyDesigner.dao;

import java.util.HashMap;
/**
 * 
 * @author Ronalin
 *
 */
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ComprehensionTestQuestionBo;
import com.fdahpStudyDesigner.bo.ComprehensionTestResponseBo;
import com.fdahpStudyDesigner.bo.ConsentInfoBo;
import com.fdahpStudyDesigner.bo.EligibilityBo;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudyPageBo;
import com.fdahpStudyDesigner.bo.StudySequenceBo;

public interface StudyDAO {

	public List<StudyListBean> getStudyList(Integer userId) throws Exception;
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public String saveOrUpdateStudy(StudyBo studyBo) throws Exception;
	public StudyBo getStudyById(String studyId);
	public boolean deleteStudyPermissionById(Integer userId, String studyId);
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds) throws Exception;
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId) throws Exception;
	public Integer saveOverviewStudyPageById(String studyId) throws Exception;
	public String deleteOverviewStudyPageById(String studyId, String pageId) throws Exception;
	public String saveOrUpdateOverviewStudyPages(String studyId, String pageIds, String titles, String descs, List<MultipartFile> files);
	
	public List<ConsentInfoBo> getConsentInfoList(Integer studyId);
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId);
	public String reOrderConsentInfoList(Integer studyId,int oldOrderNumber,int newOrderNumber);
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo);
	public ConsentInfoBo getConsentInfoById(Integer consentInfoId);
	public int consentInfoOrder(Integer studyId);
	public List<ComprehensionTestQuestionBo> getComprehensionTestQuestionList(Integer studyId);
	public ComprehensionTestQuestionBo getComprehensionTestQuestionById(Integer questionId);
	public String deleteComprehensionTestQuestion(Integer questionId);
	public List<ComprehensionTestResponseBo> getComprehensionTestResponseList(Integer comprehensionQuestionId);
	public ComprehensionTestQuestionBo saveOrUpdateComprehensionTestQuestion(ComprehensionTestQuestionBo comprehensionTestQuestionBo);
	public int comprehensionTestQuestionOrder(Integer studyId);
	public String reOrderComprehensionTestQuestion(Integer studyId,int oldOrderNumber,int newOrderNumber);
	
	public EligibilityBo getStudyEligibiltyByStudyId(String studyId);
	public String saveOrUpdateStudyEligibilty(EligibilityBo eligibilityBo);
	public List<StudyBo> getStudies(int usrId);
	public boolean validateStudyId(String studyId) throws Exception;
	
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId) throws Exception;
}
