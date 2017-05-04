package com.fdahpstudydesigner.dao;

import java.util.HashMap;
/**
 * 
 * @author Ronalin
 *
 */
import java.util.List;

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
import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.ReferenceTablesBo;
import com.fdahpstudydesigner.bo.ResourceBO;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.bo.StudyPageBo;
import com.fdahpstudydesigner.bo.StudyVersionBo;
import com.fdahpstudydesigner.util.SessionObject;

public interface StudyDAO {

	public List<StudyListBean> getStudyList(Integer userId);
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public String saveOrUpdateStudy(StudyBo studyBo, SessionObject sessionObject);
	public StudyBo getStudyById(String studyId, Integer userId);
	public boolean deleteStudyPermissionById(Integer userId, String studyId);
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds);
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId, Integer userId);
	public String deleteOverviewStudyPageById(String studyId, String pageId);
	public String saveOrUpdateOverviewStudyPages(StudyPageBean studyPageBean, SessionObject sesObj);
	
	public List<ConsentInfoBo> getConsentInfoList(Integer studyId);
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId,SessionObject sessionObject);
	public String reOrderConsentInfoList(Integer studyId,int oldOrderNumber,int newOrderNumber);
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo, SessionObject sesObj);
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
	public String saveOrUpdateStudyEligibilty(EligibilityBo eligibilityBo, SessionObject sesObj);
	public List<StudyBo> getStudies(int usrId);
	public boolean validateStudyId(String studyId);
	
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId);
	public String saveOrUpdateStudySettings(StudyBo studyBo, SessionObject sesObj);
	
	public List<ConsentMasterInfoBo> getConsentMasterInfoList();
	public ConsentBo saveOrCompleteConsentReviewDetails(ConsentBo consentBo, SessionObject sesObj);
	public ConsentBo getConsentDetailsByStudyId(String studyId);
	
	public List<ResourceBO> getResourceList(Integer studyId);
	public String deleteResourceInfo(Integer resourceInfoId);
	public ResourceBO getResourceInfo(Integer resourceInfoId);
	public Integer saveOrUpdateResource(ResourceBO resourceBO);
	public String saveResourceNotification(NotificationBO notificationBO,boolean notiFlag);
	public List<ResourceBO> resourcesSaved(Integer studyId);
	public String markAsCompleted(int studyId, String markCompleted, boolean flag, SessionObject sesObj);
	public List<NotificationBO> getSavedNotification(Integer studyId);
	public NotificationBO getNotificationByResourceId(Integer resourseId);
	public List<ResourceBO> resourcesWithAnchorDate(Integer studyId);
	
	public Checklist getchecklistInfo(Integer studyId);
	public Integer saveOrDoneChecklist(Checklist checklist);
	
	public String validateStudyAction(String studyId, String buttonText);
	public String updateStudyActionOnAction(String studyId, String buttonText, SessionObject sesObj);
	public StudyIdBean getLiveVersion(String customStudyId);
}
