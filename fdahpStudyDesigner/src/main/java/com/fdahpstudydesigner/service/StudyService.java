package com.fdahpstudydesigner.service;

import java.util.List;
import java.util.Map;

import com.fdahpstudydesigner.bean.StudyListBean;
import com.fdahpstudydesigner.bean.StudyPageBean;
import com.fdahpstudydesigner.bo.Checklist;
import com.fdahpstudydesigner.bo.ComprehensionTestQuestionBo;
import com.fdahpstudydesigner.bo.ComprehensionTestResponseBo;
import com.fdahpstudydesigner.bo.ConsentBo;
import com.fdahpstudydesigner.bo.ConsentInfoBo;
import com.fdahpstudydesigner.bo.ConsentMasterInfoBo;
import com.fdahpstudydesigner.bo.EligibilityBo;
import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.ReferenceTablesBo;
import com.fdahpstudydesigner.bo.ResourceBO;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.bo.StudyPageBo;
import com.fdahpstudydesigner.util.SessionObject;

public interface StudyService {

	public List<StudyListBean> getStudyList(Integer userId);
	public Map<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public StudyBo getStudyById(String studyId, Integer userId);
	public String saveOrUpdateStudy(StudyBo studyBo, Integer userId);
	public boolean deleteStudyPermissionById(Integer userId, String studyId);
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds);
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId, Integer userId);
	public Integer saveOverviewStudyPageById(String studyId);
	public String deleteOverviewStudyPageById(String studyId, String pageId);
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
	public boolean validateStudyId(String studyId);
	
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId);
	public String saveOrUpdateStudySettings(StudyBo studyBo);
	
	public List<ConsentMasterInfoBo> getConsentMasterInfoList();
	
	public ConsentBo saveOrCompleteConsentReviewDetails(ConsentBo consentBo, SessionObject sesObj);
	public ConsentBo getConsentDetailsByStudyId(String studyId);
	
	public List<ResourceBO> getResourceList(Integer studyId);
	public String deleteResourceInfo(Integer resourceInfoId,SessionObject sesObj);
	public ResourceBO getResourceInfo(Integer resourceInfoId);
	public Integer saveOrUpdateResource(ResourceBO resourceBO, SessionObject sesObj);
	public List<ResourceBO> resourcesSaved(Integer studyId);
	public String markAsCompleted(int studyId, String markCompleted, SessionObject sesObj);
	public List<NotificationBO> getSavedNotification(Integer studyId);
	
	public Checklist getchecklistInfo(Integer studyId);
	public Integer saveOrDoneChecklist(Checklist checklist,String actionBut,SessionObject sesObj);
	public String validateStudyAction(String studyId, String buttonText);
	public String updateStudyActionOnAction(String studyId, String buttonText);
	public String markAsCompleted(int studyId, String markCompleted,Boolean flag, SessionObject sesObj);
}
