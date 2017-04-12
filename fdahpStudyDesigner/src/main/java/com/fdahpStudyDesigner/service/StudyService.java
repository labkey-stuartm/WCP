package com.fdahpStudyDesigner.service;

import java.util.HashMap;
import java.util.List;












import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bean.StudyPageBean;
import com.fdahpStudyDesigner.bo.Checklist;
import com.fdahpStudyDesigner.bo.ComprehensionTestQuestionBo;
import com.fdahpStudyDesigner.bo.ComprehensionTestResponseBo;
import com.fdahpStudyDesigner.bo.ConsentBo;
import com.fdahpStudyDesigner.bo.ConsentInfoBo;
import com.fdahpStudyDesigner.bo.ConsentMasterInfoBo;
import com.fdahpStudyDesigner.bo.EligibilityBo;
import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.ResourceBO;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudyPageBo;
import com.fdahpStudyDesigner.util.SessionObject;

public interface StudyService {

	public List<StudyListBean> getStudyList(Integer userId) throws Exception;
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public StudyBo getStudyById(String studyId, Integer userId);
	public String saveOrUpdateStudy(StudyBo studyBo, Integer userId) throws Exception;
	public boolean deleteStudyPermissionById(Integer userId, String studyId) throws Exception;
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds) throws Exception;
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId, Integer userId) throws Exception;
	public Integer saveOverviewStudyPageById(String studyId) throws Exception;
	public String deleteOverviewStudyPageById(String studyId, String pageId) throws Exception;
	public String saveOrUpdateOverviewStudyPages(StudyPageBean studyPageBean);
	
	public List<ConsentInfoBo> getConsentInfoList(Integer studyId);
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId,SessionObject sessionObject);
	public String reOrderConsentInfoList(Integer studyId,int oldOrderNumber,int newOrderNumber);
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo,SessionObject sessionObject);
	public ConsentInfoBo getConsentInfoById(Integer consentInfoId);
	public int consentInfoOrder(Integer studyId);
	public List<ComprehensionTestQuestionBo> getComprehensionTestQuestionList(Integer studyId);
	public ComprehensionTestQuestionBo getComprehensionTestQuestionById(Integer questionId);
	public String deleteComprehensionTestQuestion(Integer questionId,Integer studyId);
	public List<ComprehensionTestResponseBo> getComprehensionTestResponseList(Integer comprehensionQuestionId);
	public ComprehensionTestQuestionBo saveOrUpdateComprehensionTestQuestion(ComprehensionTestQuestionBo comprehensionTestQuestionBo);
	public int comprehensionTestQuestionOrder(Integer studyId);
	public String reOrderComprehensionTestQuestion(Integer studyId,int oldOrderNumber,int newOrderNumber);
	
	public EligibilityBo getStudyEligibiltyByStudyId(String studyId);
	public String saveOrUpdateStudyEligibilty(EligibilityBo eligibilityBo);
	public List<StudyBo> getStudies(int userId);
	public boolean validateStudyId(String studyId) throws Exception;
	
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId) throws Exception;
	public String saveOrUpdateStudySettings(StudyBo studyBo);
	
	public List<ConsentMasterInfoBo> getConsentMasterInfoList();
	
	public ConsentBo saveOrCompleteConsentReviewDetails(ConsentBo consentBo, SessionObject sesObj) throws Exception;
	public ConsentBo getConsentDetailsByStudyId(String studyId) throws Exception;
	
	public List<ResourceBO> getResourceList(Integer studyId);
	public String deleteResourceInfo(Integer resourceInfoId);
	public ResourceBO getResourceInfo(Integer resourceInfoId);
	public Integer saveOrUpdateResource(ResourceBO resourceBO, SessionObject sesObj);
	public List<ResourceBO> resourcesSaved(Integer studyId);
	public String markAsCompleted(Integer studyId, String markCompleted);
	public List<NotificationBO> notificationSaved(Integer studyId);
	
	public Checklist getchecklistInfo(Integer studyId);
	public Integer saveOrDoneChecklist(Checklist checklist,String actionBut,SessionObject sesObj);
	public String validateStudyAction(String studyId, String buttonText);
}
