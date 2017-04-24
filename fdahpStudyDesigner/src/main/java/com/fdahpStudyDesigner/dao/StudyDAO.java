package com.fdahpStudyDesigner.dao;

import java.util.HashMap;
/**
 * 
 * @author Ronalin
 *
 */
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

public interface StudyDAO {

	public List<StudyListBean> getStudyList(Integer userId);
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory();
	public String saveOrUpdateStudy(StudyBo studyBo);
	public StudyBo getStudyById(String studyId, Integer userId);
	public boolean deleteStudyPermissionById(Integer userId, String studyId);
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds);
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId, Integer userId);
	public Integer saveOverviewStudyPageById(String studyId);
	public String deleteOverviewStudyPageById(String studyId, String pageId);
	public String saveOrUpdateOverviewStudyPages(StudyPageBean studyPageBean);
	
	public List<ConsentInfoBo> getConsentInfoList(Integer studyId);
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId,SessionObject sessionObject);
	public String reOrderConsentInfoList(Integer studyId,int oldOrderNumber,int newOrderNumber);
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo);
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
	public List<StudyBo> getStudies(int usrId);
	public boolean validateStudyId(String studyId);
	
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId);
	public String saveOrUpdateStudySettings(StudyBo studyBo);
	
	public List<ConsentMasterInfoBo> getConsentMasterInfoList();
	public ConsentBo saveOrCompleteConsentReviewDetails(ConsentBo consentBo, SessionObject sesObj);
	public ConsentBo getConsentDetailsByStudyId(String studyId);
	
	public List<ResourceBO> getResourceList(Integer studyId);
	public String deleteResourceInfo(Integer resourceInfoId);
	public ResourceBO getResourceInfo(Integer resourceInfoId);
	public Integer saveOrUpdateResource(ResourceBO resourceBO);
	public List<ResourceBO> resourcesSaved(Integer studyId);
	public String markAsCompleted(int studyId, String markCompleted, boolean flag, SessionObject sesObj);
	public List<NotificationBO> getSavedNotification(Integer studyId);
	public String saveResourceNotification(NotificationBO notificationBO);
	
	public Checklist getchecklistInfo(Integer studyId);
	public Integer saveOrDoneChecklist(Checklist checklist);
	
	public String validateStudyAction(String studyId, String buttonText);
	public String updateStudyActionOnAction(String studyId, String buttonText);
}
