package com.fdahpstudydesigner.service;

import java.util.List;
import java.util.Map;

import com.fdahpstudydesigner.bean.StudyIdBean;
import com.fdahpstudydesigner.bean.StudyListBean;
import com.fdahpstudydesigner.bean.StudyPageBean;
import com.fdahpstudydesigner.bo.Checklist;
import com.fdahpstudydesigner.bo.ComprehensionTestQuestionBo;
import com.fdahpstudydesigner.bo.ComprehensionTestResponseBo;
import com.fdahpstudydesigner.bo.ConsentBo;
import com.fdahpstudydesigner.bo.ConsentInfoBo;
import com.fdahpstudydesigner.bo.ConsentMasterInfoBo;
import com.fdahpstudydesigner.bo.EligibilityBo;
import com.fdahpstudydesigner.bo.EligibilityTestBo;
import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.ReferenceTablesBo;
import com.fdahpstudydesigner.bo.ResourceBO;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.bo.StudyPageBo;
import com.fdahpstudydesigner.bo.StudyPermissionBO;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.util.SessionObject;

public interface StudyService {

	public List<StudyListBean> getStudyList(Integer userId);
	public List<StudyListBean> getStudyListByUserId(Integer userId);
	public List<StudyBo> getAllStudyList();
	public Map<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public StudyBo getStudyById(String studyId, Integer userId);
	public String saveOrUpdateStudy(StudyBo studyBo, Integer userId, SessionObject sessionObject);
	public boolean deleteStudyPermissionById(Integer userId, String studyId);
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds);
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId, Integer userId);
	public String deleteOverviewStudyPageById(String studyId, String pageId);
	public String saveOrUpdateOverviewStudyPages(StudyPageBean studyPageBean, SessionObject sesObj);
	
	public List<ConsentInfoBo> getConsentInfoList(Integer studyId);
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId,SessionObject sessionObject,String customStudyId);
	public String reOrderConsentInfoList(Integer studyId,int oldOrderNumber,int newOrderNumber);
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo,SessionObject sessionObject,String customStudyId);
	public ConsentInfoBo getConsentInfoById(Integer consentInfoId);
	public int consentInfoOrder(Integer studyId);
	public List<ComprehensionTestQuestionBo> getComprehensionTestQuestionList(Integer studyId);
	public ComprehensionTestQuestionBo getComprehensionTestQuestionById(Integer questionId);
	public String deleteComprehensionTestQuestion(Integer questionId,Integer studyId,SessionObject sessionObject);
	public List<ComprehensionTestResponseBo> getComprehensionTestResponseList(Integer comprehensionQuestionId);
	public ComprehensionTestQuestionBo saveOrUpdateComprehensionTestQuestion(ComprehensionTestQuestionBo comprehensionTestQuestionBo);
	public int comprehensionTestQuestionOrder(Integer studyId);
	public String reOrderComprehensionTestQuestion(Integer studyId,int oldOrderNumber,int newOrderNumber);
	
	public EligibilityBo getStudyEligibiltyByStudyId(String studyId);
	public String saveOrUpdateStudyEligibilty(EligibilityBo eligibilityBo, SessionObject sesObj,String customStudyId);
	public boolean validateStudyId(String studyId);
	
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId);
	public String saveOrUpdateStudySettings(StudyBo studyBo, SessionObject sesObj,String userIds,String permissions,String projectLead);
	
	public List<ConsentMasterInfoBo> getConsentMasterInfoList();
	
	public ConsentBo saveOrCompleteConsentReviewDetails(ConsentBo consentBo, SessionObject sesObj,String customStudyId);
	public ConsentBo getConsentDetailsByStudyId(String studyId);
	
	public List<ResourceBO> getResourceList(Integer studyId);
	public String deleteResourceInfo(Integer resourceInfoId,SessionObject sesObj,String customStudyId,int studyId);
	public ResourceBO getResourceInfo(Integer resourceInfoId);
	public Integer saveOrUpdateResource(ResourceBO resourceBO, SessionObject sesObj);
	public List<ResourceBO> resourcesSaved(Integer studyId);
	public String markAsCompleted(int studyId, String markCompleted, SessionObject sesObj,String customStudyId);
	public List<ResourceBO> resourcesWithAnchorDate(Integer studyId);
	public List<NotificationBO> getSavedNotification(Integer studyId);
	public ResourceBO getStudyProtocol(Integer studyId);
	
	public Checklist getchecklistInfo(Integer studyId);
	public Integer saveOrDoneChecklist(Checklist checklist,String actionBut,SessionObject sesObj,String customStudyId);
	public String validateStudyAction(String studyId, String buttonText);
	public String updateStudyActionOnAction(String studyId, String buttonText, SessionObject sesObj);
	public String markAsCompleted(int studyId, String markCompleted,Boolean flag, SessionObject sesObj,String customStudyId);
	public StudyIdBean getLiveVersion(String customStudyId);
	public StudyBo getStudyLiveStatusByCustomId(String customStudyId);
	public String validateActivityComplete(String studyId, String action);
	
    public Integer saveOrUpdateEligibilityTestQusAns(EligibilityTestBo eligibilityTestBo, Integer studyId, SessionObject sessionObject,String customStudyId);
    public String deleteEligibilityTestQusAnsById(Integer eligibilityTestId, Integer studyId, SessionObject sessionObject,String customStudyId);
    public EligibilityTestBo viewEligibilityTestQusAnsById(Integer eligibilityTestId);
    public List<EligibilityTestBo> viewEligibilityTestQusAnsByEligibilityId(Integer eligibilityId);
    public String reorderEligibilityTestQusAns(Integer eligibilityId,int oldOrderNumber,int newOrderNumber, Integer studyId);
    public String validateEligibilityTestKey(Integer eligibilityTestId, String shortTitle);

	public boolean deleteStudyByCustomStudyId(String customStudyId);
	public boolean resetDraftStudyByCustomStudyId(String customStudyId);
	
	public List<UserBO> getActiveNonAddedUserList(Integer studyId,Integer userId);
	public List<StudyPermissionBO> getAddedUserListToStudy(Integer studyId,Integer userId);
	public String checkActiveTaskTypeValidation(Integer studyId);
	public boolean copyliveStudyByCustomStudyId(String customStudyId, SessionObject sesObj);
	public String reOrderResourceList(Integer studyId, int oldOrderNumber,int newOrderNumber);
	public int resourceOrder(Integer studyId) ;
}
