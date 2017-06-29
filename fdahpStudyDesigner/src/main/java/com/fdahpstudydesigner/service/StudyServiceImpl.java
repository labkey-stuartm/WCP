package com.fdahpstudydesigner.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.fdahpstudydesigner.dao.AuditLogDAO;
import com.fdahpstudydesigner.dao.StudyDAO;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

/**
 * 
 * @author Ronalin
 *
 */
@Service
public class StudyServiceImpl implements StudyService {

	private static Logger logger = Logger.getLogger(StudyServiceImpl.class);
	private StudyDAO studyDAO;
	
	@Autowired
	private AuditLogDAO auditLogDAO;

	/**
	 * Setting DI
	 * @param studyDAO
	 */
    @Autowired
	public void setStudyDAO(StudyDAO studyDAO) {
		this.studyDAO = studyDAO;
	}
    
    /**
	 * return study List based on user 
	 * @author Ronalin
	 * 
	 * @param userId of the user
	 * @return the Study list
	 * @exception Exception
	 */
	@Override
	public List<StudyListBean> getStudyList(Integer userId) {
		logger.info("StudyServiceImpl - getStudyList() - Starts");
		List<StudyListBean> studyBos = null;
		try {
			if(userId!=null && userId!=0){
				studyBos  = studyDAO.getStudyList(userId);
			} 
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyList() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getStudyList() - Ends");
		return studyBos;
	}
	
	/**
	 * return active and live study List based on user 
	 * @author Pradyumn
	 * 
	 * @param userId of the user
	 * @return the Study list
	 * @exception Exception
	 */
	@Override
	public List<StudyListBean> getStudyListByUserId(Integer userId) {
		logger.info("StudyServiceImpl - getStudyListByUserId() - Starts");
		List<StudyListBean> studyListBeans = null;
		try{
			studyListBeans = studyDAO.getStudyListByUserId(userId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyListByUserId() - ERROR " , e);
		} 
		logger.info("StudyServiceImpl - getStudyListByUserId() - Ends");
		return studyListBeans;
	}
	
	/**
	 * return active and live study List
	 * @author Pradyumn
	 * 
	 * @return the Study list
	 * @exception Exception
	 */
	@Override
	public List<StudyBo> getAllStudyList() {
		logger.info("StudyServiceImpl - getAllStudyList() - Starts");
		List<StudyBo> studyBOList = null;
		try{
			studyBOList = studyDAO.getAllStudyList();
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getAllStudyList() - ERROR " , e);
		} 
		logger.info("StudyServiceImpl - getAllStudyList() - Ends");
		return studyBOList;
	}



	/**
	 * return reference List based on category
	 * @author Ronalin
	 * 
	 * @return the reference table List
	 * @exception Exception
	 */
	@Override
	public Map<String, List<ReferenceTablesBo>> getreferenceListByCategory() {
		logger.info("StudyServiceImpl - getreferenceListByCategory() - Starts");
		HashMap<String, List<ReferenceTablesBo>> referenceMap = null;
		try {
			referenceMap  = studyDAO.getreferenceListByCategory();
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyList() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getreferenceListByCategory() - Ends");
		return referenceMap;
	}




	/**
	 * return Study based on id
	 * @author Ronalin
	 * 
	 * @return StudyBo
	 * @exception Exception
	 */
	@Override
	public StudyBo getStudyById(String studyId, Integer userId) {
		logger.info("StudyServiceImpl - getStudyById() - Starts");
		StudyBo studyBo = null;
		try {
			studyBo  = studyDAO.getStudyById(studyId, userId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyById() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getStudyById() - Ends");
		return studyBo;
	}

	/**
	 * @author Ronalin
	 * Add/Update the Study
	 * @param StudyBo , {@link StudyBo}
	 * @return {@link String}
	 */
	@Override
	public String saveOrUpdateStudy(StudyBo studyBo, Integer userId, SessionObject sessionObject) {
		logger.info("StudyServiceImpl - saveOrUpdateStudy() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		try {
			message = studyDAO.saveOrUpdateStudy(studyBo, sessionObject);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - saveOrUpdateStudy() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - saveOrUpdateStudy() - Ends");
		return message;
	}




	/**
	 * return false or true of deleting record of studyPermission based on studyId and userId
	 * @author Ronalin
	 * 
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean deleteStudyPermissionById(Integer userId, String studyId) {
		logger.info("StudyServiceImpl - deleteStudyPermissionById() - Starts");
		boolean delFlag = false;
		try {
			delFlag = studyDAO.deleteStudyPermissionById(userId, studyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - deleteStudyPermissionById() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - deleteStudyPermissionById() - Ends");
		return delFlag;
	}




	/**
	 * return false or true of adding record of studyPermission based on studyId and userId
	 * @author Ronalin
	 * 
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds) {
		logger.info("StudyServiceImpl - addStudyPermissionByuserIds() - Starts");
		boolean delFlag = false;
		try {
			delFlag = studyDAO.addStudyPermissionByuserIds(userId, studyId, userIds);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - addStudyPermissionByuserIds() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - addStudyPermissionByuserIds() - Ends");
		return delFlag;
	}

	 /**
		 * return study overview pageList based on studyId 
		 * @author Ronalin
		 * 
		 * @param studyId of the StudyBo, Integer userId
		 * @return the Study list
		 * @exception Exception
	*/
	@Override
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId, Integer userId) {
		logger.info("StudyServiceImpl - getOverviewStudyPagesById() - Starts");
		List<StudyPageBo> studyPageBos = null;
		try {
			 studyPageBos = studyDAO.getOverviewStudyPagesById(studyId, userId);
			 if(null != studyPageBos && !studyPageBos.isEmpty()){
				 for(StudyPageBo s : studyPageBos){
					 if(FdahpStudyDesignerUtil.isNotEmpty(s.getImagePath())){
						 if(s.getImagePath().contains("?v=")){
							 String imagePathArr[] = s.getImagePath().split("\\?");
							 s.setImagePath(imagePathArr[0]+ "?v=" + new Date().getTime());
						 }else{
							 s.setImagePath(s.getImagePath() + "?v=" + new Date().getTime()); 
						 }
					 }
				 }
			 }
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getOverviewStudyPagesById() - ERROR " , e);
		}
		return studyPageBos;
	}

	/**
	 * @author Ronalin
	 * delete the Study Overview Page By Page Id
	 * @param studyId ,pageId
	 * @return {@link String}
	 */
	@Override
	public String deleteOverviewStudyPageById(String studyId, String pageId) {
		logger.info("StudyServiceImpl - deleteOverviewStudyPageById() - Starts");
		String message = "";
		try {
			message = studyDAO.deleteOverviewStudyPageById(studyId, pageId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - deleteOverviewStudyPageById() - ERROR " , e);
		}
		return message;
	}

	/**
	 * @author Ronalin
	 * Add/Update the Study Overview Pages
	 * @param studyPageBean {@link StudyPageBean}
	 * @return {@link String}
	 */
	@Override
	public String saveOrUpdateOverviewStudyPages(StudyPageBean studyPageBean ,SessionObject sesObj) {
		logger.info("StudyServiceImpl - saveOrUpdateOverviewStudyPages() - Starts");
		String message = "";
		try {
			if(studyPageBean.getMultipartFiles()!=null && studyPageBean.getMultipartFiles().length>0){
				String imagePath[]= new String[studyPageBean.getImagePath().length];
				for(int i=0;i<studyPageBean.getMultipartFiles().length;i++){
					String file;
					if(!studyPageBean.getMultipartFiles()[i].isEmpty()){
						if(FdahpStudyDesignerUtil.isNotEmpty(studyPageBean.getImagePath()[i])){
							file = studyPageBean.getImagePath()[i].replace("."+studyPageBean.getImagePath()[i].split("\\.")[studyPageBean.getImagePath()[i].split("\\.").length - 1], "");
						} else {
							file = FdahpStudyDesignerUtil.getStandardFileName("STUDY_PAGE_"+i, studyPageBean.getUserId()+"", studyPageBean.getStudyId());
						}
						imagePath[i] = FdahpStudyDesignerUtil.uploadImageFile(studyPageBean.getMultipartFiles()[i],file, FdahpStudyDesignerConstants.STUDTYPAGES);
					} else {
						imagePath[i] = studyPageBean.getImagePath()[i];
					}
				}
				studyPageBean.setImagePath(imagePath);
			}
			message = studyDAO.saveOrUpdateOverviewStudyPages(studyPageBean, sesObj);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - saveOrUpdateOverviewStudyPages() - ERROR " , e);
		}
		return message;
	}

	/**
	 * @author Ravinder
	 * @param Integer : studyId
	 * @return List :ConsentInfoList
	 *  This method used to get the consent info list of an study
	 */
	@Override
	public List<ConsentInfoBo> getConsentInfoList(Integer studyId) {
		logger.info("StudyServiceImpl - getConsentInfoList() - Starts");
		List<ConsentInfoBo> consentInfoList = null;
		try{
			consentInfoList = studyDAO.getConsentInfoList(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getConsentInfoList() - Error",e);
		}
		logger.info("StudyServiceImpl - getConsentInfoList() - Ends");
		return consentInfoList;
	}




	/**
	 * @author Ravinder
	 * @param Integer : consentInfoId
	 * @return String :SUCCESS or FAILURE
	 *  TThis method used to get the delete the consent information
	 */
	@Override
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId,SessionObject sessionObject,String customStudyId) {
		logger.info("StudyServiceImpl - deleteConsentInfo() - Starts");
		String message = null;
		try{
			message = studyDAO.deleteConsentInfo(consentInfoId,studyId,sessionObject,customStudyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - deleteConsentInfo() - Error",e);
		}
		logger.info("StudyServiceImpl - deleteConsentInfo() - Ends");
		return message;
	}




	/**
	 * @author Ravinder
	 * @param Integer studyId
	 * @param int oldOrderNumber
	 * @param int newOrderNumber
	 * @return String SUCCESS or FAILURE
	 * 
	 * This method is used to update the order of an consent info
	 */
	@Override
	public String reOrderConsentInfoList(Integer studyId, int oldOrderNumber,int newOrderNumber) {
		logger.info("StudyServiceImpl - reOrderConsentInfoList() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyDAO.reOrderConsentInfoList(studyId, oldOrderNumber, newOrderNumber);
		}catch(Exception e){
			logger.error("StudyServiceImpl - reOrderConsentInfoList() - Error",e);
		}
		logger.info("StudyServiceImpl - reOrderConsentInfoList() - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * 
	 * 
	 */
	@Override
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo,SessionObject sessionObject,String customStudyId) {
		logger.info("StudyServiceImpl - saveOrUpdateConsentInfo() - Starts");
		ConsentInfoBo updateConsentInfoBo = null;
		try{
			if(consentInfoBo != null){
				if(consentInfoBo.getId() != null){
					updateConsentInfoBo = studyDAO.getConsentInfoById(consentInfoBo.getId());
					updateConsentInfoBo.setModifiedBy(sessionObject.getUserId());
					updateConsentInfoBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
				}else{
					updateConsentInfoBo = new ConsentInfoBo();
					updateConsentInfoBo.setCreatedBy(sessionObject.getUserId());
					updateConsentInfoBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					updateConsentInfoBo.setActive(true);
				}
				if(consentInfoBo.getConsentItemType() != null){
					updateConsentInfoBo.setConsentItemType(consentInfoBo.getConsentItemType());
				}
				if(consentInfoBo.getContentType() != null){
					updateConsentInfoBo.setContentType(consentInfoBo.getContentType());
				}
				if(consentInfoBo.getBriefSummary() != null){
					updateConsentInfoBo.setBriefSummary(consentInfoBo.getBriefSummary().replaceAll("\"", "&#34;").replaceAll("\'", "&#39;"));
				}
				if(consentInfoBo.getElaborated() != null){
					updateConsentInfoBo.setElaborated(consentInfoBo.getElaborated());
				}
				if(consentInfoBo.getHtmlContent() != null){
					updateConsentInfoBo.setHtmlContent(consentInfoBo.getHtmlContent());
				}
				if(consentInfoBo.getUrl()!= null){
					updateConsentInfoBo.setUrl(consentInfoBo.getUrl());
				}
				if(consentInfoBo.getVisualStep()!=null){
					updateConsentInfoBo.setVisualStep(consentInfoBo.getVisualStep());
				}
				if(consentInfoBo.getSequenceNo() != null){
					updateConsentInfoBo.setSequenceNo(consentInfoBo.getSequenceNo());
				}
				if(consentInfoBo.getStudyId() != null){
					updateConsentInfoBo.setStudyId(consentInfoBo.getStudyId());
				}
				if(consentInfoBo.getDisplayTitle() != null){
					updateConsentInfoBo.setDisplayTitle(consentInfoBo.getDisplayTitle());
				}
				if(consentInfoBo.getType() != null){
					updateConsentInfoBo.setType(consentInfoBo.getType());
				}
				if(consentInfoBo.getConsentItemTitleId() != null){
					updateConsentInfoBo.setConsentItemTitleId(consentInfoBo.getConsentItemTitleId());
				}
				updateConsentInfoBo = studyDAO.saveOrUpdateConsentInfo(updateConsentInfoBo, sessionObject,customStudyId);
			}
			
		}catch(Exception e){
			logger.error("StudyServiceImpl - saveOrUpdateConsentInfo() - Error",e);
		}
		logger.info("StudyServiceImpl - saveOrUpdateConsentInfo() - Ends");
		return updateConsentInfoBo;
	}




	/**
	 * @author Ravinder
	 * @param Integer :ConsentInfoId
	 * @return Object :ConsentInfoBo
	 * 
	 * This method is used to get the consent info object based on consent info id 
	 */
	@Override
	public ConsentInfoBo getConsentInfoById(Integer consentInfoId) {
		logger.info("StudyServiceImpl - getConsentInfoById() - Starts");
		ConsentInfoBo consentInfoBo = null;
		try{
			consentInfoBo = studyDAO.getConsentInfoById(consentInfoId);
			if(consentInfoBo != null){
				consentInfoBo.setBriefSummary(consentInfoBo.getBriefSummary().replaceAll("(\\r|\\n|\\r\\n)+", "&#13;&#10;"));
			}
		}catch(Exception e){
			logger.error("StudyServiceImpl - getConsentInfoById() - Error",e);
		}
		logger.info("StudyServiceImpl - getConsentInfoById() - Ends");
		return consentInfoBo;
	}

	/**
	 * @author Ravinder
	 * @param studyId
	 * @return int count
	 * 
	 * This method is used to get the last order of an consent info of an study
	 */
	@Override
	public int consentInfoOrder(Integer studyId) {
		int count = 1;
		logger.info("StudyServiceImpl - consentInfoOrder() - Starts");
		try{
			count = studyDAO.consentInfoOrder(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - consentInfoOrder() - Error",e);
		}
		logger.info("StudyServiceImpl - consentInfoOrder() - Ends");
		return count;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer : studyId
	 * @return List : ComprehensionTestQuestions
	 * 
	 * This method is used to get the ComprehensionTest Questions
	 */
	@Override
	public List<ComprehensionTestQuestionBo> getComprehensionTestQuestionList(Integer studyId) {
		logger.info("StudyServiceImpl - getComprehensionTestQuestionList() - Starts");
		List<ComprehensionTestQuestionBo> comprehensionTestQuestionList = null;
		try{
			comprehensionTestQuestionList = studyDAO.getComprehensionTestQuestionList(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getComprehensionTestQuestionList() - Error",e);
		}
		logger.info("StudyServiceImpl - getComprehensionTestQuestionList() - Starts");
		return comprehensionTestQuestionList;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer :QuestionId
	 * @return Object : ComprehensionTestQuestionBo
	 * 
	 * This method is used to get the ComprehensionTestQuestion of an study
	 */
	@Override
	public ComprehensionTestQuestionBo getComprehensionTestQuestionById(Integer questionId) {
		logger.info("StudyServiceImpl - getComprehensionTestQuestionById() - Starts");
		ComprehensionTestQuestionBo comprehensionTestQuestionBo = null;
		try{
			comprehensionTestQuestionBo = studyDAO.getComprehensionTestQuestionById(questionId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getComprehensionTestQuestionById() - Error",e);
		}
		logger.info("StudyServiceImpl - getComprehensionTestQuestionById() - Ends");
		return comprehensionTestQuestionBo;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer  :questionId
	 * @return String : SUCCESS or FAILURE
	 * 
	 * This method is used to delete the Comprehension Test Question in a study
	 * 
	 */
	@Override
	public String deleteComprehensionTestQuestion(Integer questionId,Integer studyId,SessionObject sessionObject) {
		logger.info("StudyServiceImpl - deleteComprehensionTestQuestion() - Starts");
		String message = null;
		try{
			message = studyDAO.deleteComprehensionTestQuestion(questionId,studyId,sessionObject);
		}catch(Exception e){
			logger.error("StudyServiceImpl - deleteComprehensionTestQuestion() - Error",e);
		}
		logger.info("StudyServiceImpl - deleteComprehensionTestQuestion() - Ends");
		return message;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer : comprehensionQuestionId
	 * @param List : ComprehensionTestResponseBo List
	 * 
	 * This method is used to get the ComprehensionTestQuestion response of an study
	 */
	@Override
	public List<ComprehensionTestResponseBo> getComprehensionTestResponseList(Integer comprehensionQuestionId) {
		logger.info("StudyServiceImpl - getComprehensionTestResponseList() - Starts");
		List<ComprehensionTestResponseBo> comprehensionTestResponseLsit = null;
		try{
			comprehensionTestResponseLsit = studyDAO.getComprehensionTestResponseList(comprehensionQuestionId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getComprehensionTestResponseList() - ERROR", e);
		}
		logger.info("StudyServiceImpl - getComprehensionTestResponseList() - Starts");
		return comprehensionTestResponseLsit;
	}
	
	/**
	 * @author Ravinder
	 * @param Object : ComprehensionTestQuestionBo
	 * @return Object  :ComprehensionTestQuestionBo
	 * 
	 * This method is used to add the ComprehensionTestQuestion to the study
	 */
	@Override
	public ComprehensionTestQuestionBo saveOrUpdateComprehensionTestQuestion(ComprehensionTestQuestionBo comprehensionTestQuestionBo) {
		logger.info("StudyServiceImpl - getComprehensionTestResponseList() - Starts");
		ComprehensionTestQuestionBo updateComprehensionTestQuestionBo = null;
		try{
			if(comprehensionTestQuestionBo != null){
				if(comprehensionTestQuestionBo.getId() != null){
					updateComprehensionTestQuestionBo = studyDAO.getComprehensionTestQuestionById(comprehensionTestQuestionBo.getId());
				}else{
					updateComprehensionTestQuestionBo = new ComprehensionTestQuestionBo();
					updateComprehensionTestQuestionBo.setActive(true);
				}
				if(comprehensionTestQuestionBo.getStatus() != null){
					updateComprehensionTestQuestionBo.setStatus(comprehensionTestQuestionBo.getStatus());
				}
				if(comprehensionTestQuestionBo.getQuestionText() != null){
					updateComprehensionTestQuestionBo.setQuestionText(comprehensionTestQuestionBo.getQuestionText());
				}
				if(comprehensionTestQuestionBo.getStudyId() != null){
					updateComprehensionTestQuestionBo.setStudyId(comprehensionTestQuestionBo.getStudyId());
				}
				if(comprehensionTestQuestionBo.getSequenceNo() != null){
					updateComprehensionTestQuestionBo.setSequenceNo(comprehensionTestQuestionBo.getSequenceNo());
				}
				if(comprehensionTestQuestionBo.getStructureOfCorrectAns() != null){
					updateComprehensionTestQuestionBo.setStructureOfCorrectAns(comprehensionTestQuestionBo.getStructureOfCorrectAns());
				}
				if(comprehensionTestQuestionBo.getCreatedOn() != null){
					updateComprehensionTestQuestionBo.setCreatedOn(comprehensionTestQuestionBo.getCreatedOn());
				}
				if(comprehensionTestQuestionBo.getCreatedBy() != null){
					updateComprehensionTestQuestionBo.setCreatedBy(comprehensionTestQuestionBo.getCreatedBy());
				}
				if(comprehensionTestQuestionBo.getModifiedOn() != null){
					updateComprehensionTestQuestionBo.setModifiedOn(comprehensionTestQuestionBo.getModifiedOn());
				}
				if(comprehensionTestQuestionBo.getModifiedBy() != null){
					updateComprehensionTestQuestionBo.setModifiedBy(comprehensionTestQuestionBo.getModifiedBy());
				}
				if(comprehensionTestQuestionBo.getResponseList() != null && !comprehensionTestQuestionBo.getResponseList().isEmpty()){
					updateComprehensionTestQuestionBo.setResponseList(comprehensionTestQuestionBo.getResponseList());
				}
				updateComprehensionTestQuestionBo = studyDAO.saveOrUpdateComprehensionTestQuestion(updateComprehensionTestQuestionBo);
			}
			
		}catch(Exception e){
			logger.error("StudyServiceImpl - getComprehensionTestResponseList() - Error",e);
		}
		logger.info("StudyServiceImpl - getComprehensionTestResponseList() - Ends");
		return updateComprehensionTestQuestionBo;
	}
	
	/**
	 * @author Ravinder
	 * @param studyId
	 * @return int count
	 * 
	 * This method is used to get the last order of an comprehension Test Question of an study
	 */
	@Override
	public int comprehensionTestQuestionOrder(Integer studyId) {
		int count = 1;
		logger.info("StudyServiceImpl - comprehensionTestQuestionOrder() - Starts");
		try{
			count = studyDAO.comprehensionTestQuestionOrder(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - comprehensionTestQuestionOrder() - Error",e);
		}
		logger.info("StudyServiceImpl - comprehensionTestQuestionOrder() - Ends");
		return count;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer studyId
	 * @param int oldOrderNumber
	 * @param int newOrderNumber
	 * @return String SUCCESS or FAILURE
	 * 
	 * This method is used to update the order of an Comprehension Test Question
	 */
	@Override
	public String reOrderComprehensionTestQuestion(Integer studyId,	int oldOrderNumber, int newOrderNumber) {
		logger.info("StudyServiceImpl - reOrderComprehensionTestQuestion() - Starts");
		String message = FdahpStudyDesignerConstants.SUCCESS;
		try{
			message = studyDAO.reOrderComprehensionTestQuestion(studyId, oldOrderNumber, newOrderNumber);
		}catch(Exception e){
			logger.error("StudyServiceImpl - reOrderComprehensionTestQuestion() - Error",e);
		}
		logger.info("StudyServiceImpl - reOrderComprehensionTestQuestion - Ends");
		return message;
	}

	
	/*------------------------------------Added By Vivek Start---------------------------------------------------*/
	
	/**
	 * return  eligibility based on user's Study Id
	 * @author Vivek
	 * 
	 * @param studyId, studyId of the {@link StudyBo}
	 * @return {@link EligibilityBo}
	 * @exception Exception
	 */
	@Override
	public EligibilityBo getStudyEligibiltyByStudyId(String studyId) {
		logger.info("StudyServiceImpl - getStudyEligibiltyByStudyId() - Starts");
		EligibilityBo eligibilityBo = null;
		try {
			eligibilityBo = studyDAO.getStudyEligibiltyByStudyId(studyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyEligibiltyByStudyId() - ERROR ", e);
		}
		logger.info("StudyServiceImpl - getStudyEligibiltyByStudyId() - Ends");
		return eligibilityBo;
	}
	
	/**
	 * Save or update eligibility of study
	 * @author Vivek
	 * 
	 * @param eligibilityBo , {@link EligibilityBo}
	 * @return {@link String} , the status FdahpStudyDesignerConstants.SUCCESS or FdahpStudyDesignerConstants.FAILURE
	 * @exception Exception
	 */
	@Override
	public String saveOrUpdateStudyEligibilty(EligibilityBo eligibilityBo, SessionObject sesObj,String customStudyId) {
		logger.info("StudyServiceImpl - getStudyEligibiltyByStudyId() - Starts");
		String  result = FdahpStudyDesignerConstants.FAILURE;
		try {
			result = studyDAO.saveOrUpdateStudyEligibilty(eligibilityBo,sesObj,customStudyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyEligibiltyByStudyId() - ERROR ", e);
		}
		logger.info("StudyServiceImpl - getStudyEligibiltyByStudyId() - Ends");
		return result;
	}
	
	/*------------------------------------Added By Vivek End---------------------------------------------------*/

	/**
	 * return false or true of validating study Custom id
	 * @author Ronalin
	 * 
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean validateStudyId(String studyId) {
		logger.info("StudyServiceImpl - validateStudyId() - Starts");
		boolean flag = false;
		try {
			flag = studyDAO.validateStudyId(studyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - validateStudyId() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - validateStudyId() - Ends");
		return flag;
   }


	/**
	 * Save or update settings and admins of study
	 * @author Ronalin
	 * 
	 * @param studyBo , {@link studyBo}
	 * @return {@link String} , the status FdahpStudyDesignerConstants.SUCCESS or FdahpStudyDesignerConstants.FAILURE
	 * @exception Exception
	 */
	@Override
	public String saveOrUpdateStudySettings(StudyBo studyBo, SessionObject sesObj) {
		logger.info("StudyServiceImpl - saveOrUpdateStudySettings() - Starts");
		String  result = FdahpStudyDesignerConstants.FAILURE;
		try {
			result = studyDAO.saveOrUpdateStudySettings(studyBo, sesObj);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - saveOrUpdateStudySettings() - ERROR ", e);
		}
		logger.info("StudyServiceImpl - saveOrUpdateStudySettings() - Ends");
		return result;
	}




	/**
	 * @author Ravinder
	 * @return List : ConsentMasterInfoBo List
	 * This method is used get consent master data
	 */
	@Override
	public List<ConsentMasterInfoBo> getConsentMasterInfoList() {
		logger.info("StudyServiceImpl - getConsentMasterInfoList() - Starts");
		List<ConsentMasterInfoBo> consentMasterInfoList = null;
		try{
			consentMasterInfoList = studyDAO.getConsentMasterInfoList();
		}catch(Exception e){
			logger.error("StudyServiceImpl - getConsentMasterInfoList() - ERROR ", e);
		}
		logger.info("StudyServiceImpl - getConsentMasterInfoList() - Ends");
		return consentMasterInfoList;
	}
	
	/**
	 * @author Mohan
	 * @param studyId
	 * @return List<ConsentInfoBo>
	 * @throws Exception
	 */
	@Override
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId) {
		logger.info("INFO: StudyServiceImpl - getConsentInfoDetailsListByStudyId() :: Starts");
		List<ConsentInfoBo> consentInfoBoList = null;
		try{
			consentInfoBoList = studyDAO.getConsentInfoDetailsListByStudyId(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getConsentInfoDetailsListByStudyId() - ERROR", e);
		}
		logger.info("INFO: StudyServiceImpl - getConsentInfoDetailsListByStudyId() :: Ends");
		return consentInfoBoList;
	}

	@Override
	public ConsentBo saveOrCompleteConsentReviewDetails(ConsentBo consentBo, SessionObject sesObj,String customStudyId) {
		logger.info("INFO: StudyServiceImpl - saveOrCompleteConsentReviewDetails() :: Starts");
		ConsentBo updateConsentBo = null;
		try{
			if(consentBo.getId() != null){
				updateConsentBo = studyDAO.getConsentDetailsByStudyId(consentBo.getStudyId().toString());
			}else{
				updateConsentBo = new ConsentBo();
			}
			
			if(consentBo.getId() != null){
				updateConsentBo.setId(consentBo.getId());
			}
			
			if(consentBo.getStudyId() != null){
				updateConsentBo.setStudyId(consentBo.getStudyId());
			}
			
			if(consentBo.getComprehensionTestMinimumScore() != null){
				updateConsentBo.setComprehensionTestMinimumScore(consentBo.getComprehensionTestMinimumScore());
			}
			if(consentBo.getNeedComprehensionTest() != null){
				updateConsentBo.setNeedComprehensionTest(consentBo.getNeedComprehensionTest());
			}
			if(consentBo.getShareDataPermissions() != null){
				updateConsentBo.setShareDataPermissions(consentBo.getShareDataPermissions());
			}
			
			if(consentBo.getTitle() != null){
				updateConsentBo.setTitle(consentBo.getTitle());
			}
					
			if(consentBo.getTaglineDescription() != null){
				updateConsentBo.setTaglineDescription(consentBo.getTaglineDescription());
			}
			
			if(consentBo.getShortDescription() != null){
				updateConsentBo.setShortDescription(consentBo.getShortDescription());
			}
			
			if(consentBo.getTitle() != null){
				updateConsentBo.setTitle(consentBo.getTitle());
			}
			
			if(consentBo.getLongDescription() != null){
				updateConsentBo.setLongDescription(consentBo.getLongDescription());
			}
			
			if(consentBo.getLearnMoreText() != null){
				updateConsentBo.setLearnMoreText(consentBo.getLearnMoreText());
			}
			
			if(consentBo.getConsentDocType() != null){
				updateConsentBo.setConsentDocType(consentBo.getConsentDocType());
			}
			
			if(consentBo.getConsentDocContent() != null){
				updateConsentBo.setConsentDocContent(consentBo.getConsentDocContent());
			}
			
			if(consentBo.getAllowWithoutPermission() != null){
				updateConsentBo.setAllowWithoutPermission(consentBo.getAllowWithoutPermission());
			}
			
			if(consentBo.geteConsentFirstName() != null){
				updateConsentBo.seteConsentFirstName(consentBo.geteConsentFirstName());
			}
			
			if(consentBo.geteConsentLastName() != null){
				updateConsentBo.seteConsentLastName(consentBo.geteConsentLastName());
			}
			
			if(consentBo.geteConsentSignature() != null){
				updateConsentBo.seteConsentSignature(consentBo.geteConsentSignature());
			}
			
			if(consentBo.geteConsentAgree() != null){
				updateConsentBo.seteConsentAgree(consentBo.geteConsentAgree());
			}
			
			if(consentBo.geteConsentDatetime() != null){
				updateConsentBo.seteConsentDatetime(consentBo.geteConsentDatetime());
			}
			
			
			if(consentBo.getCreatedBy() != null){
				updateConsentBo.setCreatedBy(consentBo.getCreatedBy());
			}
			
			if(consentBo.getCreatedOn() != null){
				updateConsentBo.setCreatedOn(consentBo.getCreatedOn());
			}
			
			if(consentBo.getModifiedBy() != null){
				updateConsentBo.setModifiedBy(consentBo.getModifiedBy());
			}
			
			if(consentBo.getModifiedOn() != null){
				updateConsentBo.setModifiedOn(consentBo.getModifiedOn());
			}
			
			if(consentBo.getVersion() != null){
				updateConsentBo.setVersion(consentBo.getVersion());
			}
			
			if(consentBo.getType() != null){
				updateConsentBo.setType(consentBo.getType());
			}
			
			updateConsentBo = studyDAO.saveOrCompleteConsentReviewDetails(updateConsentBo, sesObj,customStudyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - saveOrCompleteConsentReviewDetails() :: ERROR", e);
		}
		logger.info("INFO: StudyServiceImpl - saveOrCompleteConsentReviewDetails() :: Ends");
		return updateConsentBo;
	}

	@Override
	public ConsentBo getConsentDetailsByStudyId(String studyId) {
		logger.info("INFO: StudyServiceImpl - getConsentDetailsByStudyId() :: Starts");
		ConsentBo consentBo = null;
		try{
			consentBo = studyDAO.getConsentDetailsByStudyId(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getConsentDetailsByStudyId() :: ERROR", e);
		}
		logger.info("INFO: StudyServiceImpl - getConsentDetailsByStudyId() :: Ends");
		return consentBo;
	}

	@Override
	public List<ResourceBO> getResourceList(Integer studyId) {
		logger.info("StudyServiceImpl - getResourceList() - Starts");
		List<ResourceBO> resourceBOList = null;
		try{
			resourceBOList = studyDAO.getResourceList(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getResourceList() - Error",e);
		}
		logger.info("StudyServiceImpl - getResourceList() - Ends");
		return resourceBOList;
	}
	
	@Override
	public List<ResourceBO> resourcesSaved(Integer studyId) {
		logger.info("StudyServiceImpl - resourcesSaved() - Starts");
		List<ResourceBO> resourceBOList = null;
		try{
			resourceBOList = studyDAO.resourcesSaved(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - resourcesSaved() - Error",e);
		}
		logger.info("StudyServiceImpl - resourcesSaved() - Ends");
		return resourceBOList;
	}

	@Override
	public String deleteResourceInfo(Integer resourceInfoId,SessionObject sesObj,String customStudyId) {
		logger.info("StudyServiceImpl - deleteConsentInfo() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		String activity = "";
		String activityDetail = ""; 
		ResourceBO resourceBO = null;
		try{
			resourceBO = studyDAO.getResourceInfo(resourceInfoId);
			if(null != resourceBO){
				message = studyDAO.deleteResourceInfo(resourceInfoId,resourceBO.isResourceVisibility());
			}
			if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
				activity = "Resource deleted";
				activityDetail = customStudyId+" -- Resource soft deleted";
				auditLogDAO.saveToAuditLog(null, null, sesObj, activity, activityDetail ,"StudyDAOImpl - deleteResourceInfo()");
			}
		}catch(Exception e){
			logger.error("StudyServiceImpl - deleteConsentInfo() - Error",e);
		}
		logger.info("StudyServiceImpl - deleteConsentInfo() - Ends");
		return message;
	}
	
	@Override
	public ResourceBO getResourceInfo(Integer resourceInfoId) {
		logger.info("StudyServiceImpl - getResourceInfo() - Starts");
		ResourceBO resourceBO = null;
		try{
			resourceBO = studyDAO.getResourceInfo(resourceInfoId);
			if(null != resourceBO){
				resourceBO.setStartDate(FdahpStudyDesignerUtil.isNotEmpty(resourceBO.getStartDate())?String.valueOf(FdahpStudyDesignerUtil.getFormattedDate(resourceBO.getStartDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE)):"");
				resourceBO.setEndDate(FdahpStudyDesignerUtil.isNotEmpty(resourceBO.getEndDate())?String.valueOf(FdahpStudyDesignerUtil.getFormattedDate(resourceBO.getEndDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE)):"");
			}
		}catch(Exception e){
			logger.error("StudyServiceImpl - getResourceInfo() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getResourceInfo() - Ends");
		return resourceBO;
	}
	
	@Override
	public Integer saveOrUpdateResource(ResourceBO resourceBO, SessionObject sesObj) {
		logger.info("StudyServiceImpl - saveOrUpdateResource() - Starts");
		Integer resourseId = 0;
		ResourceBO resourceBO2 = null;
		String fileName = "";
		String file="";
		NotificationBO notificationBO = null;
		StudyBo studyBo = null;
		try{
			studyBo = studyDAO.getStudyById(resourceBO.getStudyId().toString(),sesObj.getUserId());
			if(null == resourceBO.getId()){
				resourceBO2 = new ResourceBO();
				resourceBO2.setStudyId(resourceBO.getStudyId());
				resourceBO2.setCreatedBy(sesObj.getUserId());
				resourceBO2.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
				resourceBO2.setStatus(true);
			}else{ 
				resourceBO2 = getResourceInfo(resourceBO.getId());
				resourceBO2.setModifiedBy(sesObj.getUserId());
				resourceBO2.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
			}
			resourceBO2.setTitle(null != resourceBO.getTitle() ? resourceBO.getTitle().trim() : "");
			resourceBO2.setTextOrPdf(resourceBO.isTextOrPdf());
				resourceBO2.setRichText(null != resourceBO.getRichText() ? resourceBO.getRichText().trim() : "");
				if(resourceBO.getPdfFile() != null && !resourceBO.getPdfFile().isEmpty()){
					file = FdahpStudyDesignerUtil.getStandardFileName(FilenameUtils.removeExtension(resourceBO.getPdfFile().getOriginalFilename()), sesObj.getFirstName(),sesObj.getLastName()).replaceAll("\\W+", "_");
					fileName = FdahpStudyDesignerUtil.uploadImageFile(resourceBO.getPdfFile(),file, FdahpStudyDesignerConstants.RESOURCEPDFFILES);
					resourceBO2.setPdfUrl(fileName);
					resourceBO2.setPdfName(resourceBO.getPdfFile().getOriginalFilename());
				} else{
					resourceBO2.setPdfUrl(resourceBO.getPdfUrl());
					resourceBO2.setPdfName(resourceBO.getPdfName());
				}
			resourceBO2.setxDaysSign(resourceBO.isxDaysSign());
			resourceBO2.setyDaysSign(resourceBO.isyDaysSign());
			resourceBO2.setResourceVisibility(resourceBO.isResourceVisibility());
			resourceBO2.setResourceType(resourceBO.isResourceType());
			resourceBO2.setResourceText(null != resourceBO.getResourceText() ? resourceBO.getResourceText().trim() : "");
			resourceBO2.setTimePeriodFromDays(resourceBO.getTimePeriodFromDays());
			resourceBO2.setTimePeriodToDays(resourceBO.getTimePeriodToDays());
			resourceBO2.setStartDate(FdahpStudyDesignerUtil.isNotEmpty(resourceBO.getStartDate()) ? String.valueOf(FdahpStudyDesignerUtil.getFormattedDate(resourceBO.getStartDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.DB_SDF_DATE)):null);
			resourceBO2.setEndDate(FdahpStudyDesignerUtil.isNotEmpty(resourceBO.getEndDate())?String.valueOf(FdahpStudyDesignerUtil.getFormattedDate(resourceBO.getEndDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.DB_SDF_DATE)):null);
			resourceBO2.setAction(resourceBO.isAction());
			resourceBO2.setStudyProtocol(resourceBO.isStudyProtocol());
			resourseId = studyDAO.saveOrUpdateResource(resourceBO2);
			
			if(!resourseId.equals(0)){
				if(!resourceBO2.isStudyProtocol()){
					studyDAO.markAsCompleted(resourceBO2.getStudyId(), FdahpStudyDesignerConstants.RESOURCE, false, sesObj, studyBo.getCustomStudyId());
				}
					if(resourceBO.isAction()){
                        	 notificationBO = studyDAO.getNotificationByResourceId(resourseId);
     						boolean notiFlag;
     						if(null == notificationBO){
     							notiFlag = false;
     							notificationBO = new NotificationBO();
     							notificationBO.setStudyId(resourceBO2.getStudyId());
     							notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
     							notificationBO.setNotificationType(FdahpStudyDesignerConstants.NOTIFICATION_ST);
     							notificationBO.setNotificationSubType(FdahpStudyDesignerConstants.NOTIFICATION_SUBTYPE_RESOURCE);
     							notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
     							notificationBO.setResourceId(resourceBO2.getId());
     							notificationBO.setNotificationStatus(false);
     							notificationBO.setCreatedBy(sesObj.getUserId());
     							notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
     						}else{
     							notiFlag = true;
     							notificationBO.setModifiedBy(sesObj.getUserId());
     							notificationBO.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
     						}
     						notificationBO.setNotificationText(resourceBO2.getResourceText());
     						if(resourceBO2.isResourceType()){
     							notificationBO.setAnchorDate(true);
     							notificationBO.setxDays(resourceBO2.getTimePeriodFromDays());
     						}else{
     							notificationBO.setAnchorDate(false);
     							notificationBO.setxDays(null);
     						}
     						notificationBO.setScheduleDate(null);
     						notificationBO.setScheduleTime(null);
     						if(!resourceBO.isResourceType())
     							studyDAO.saveResourceNotification(notificationBO,notiFlag);
     						if(resourceBO.isResourceType() && resourceBO.getStartDate()!=null)
     						  studyDAO.saveResourceNotification(notificationBO,notiFlag);
					}
			}
		}catch(Exception e){
			logger.error("StudyServiceImpl - saveOrUpdateResource() - Error",e);
		}
		logger.info("StudyServiceImpl - saveOrUpdateResource() - Ends");
		return resourseId;
	}
	
	@Override
	public String markAsCompleted(int studyId, String markCompleted, SessionObject sesObj,String customStudyId) {
		logger.info("StudyServiceImpl - markAsCompleted() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyDAO.markAsCompleted(studyId, markCompleted, true, sesObj,customStudyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - markAsCompleted() - Error",e);
		}
		logger.info("StudyServiceImpl - markAsCompleted() - Ends");
		return message;
	}
	
	/**
	 * Kanchana
	 * @param studyId
	 * @return
	 */
	@Override
	public List<NotificationBO> getSavedNotification(Integer studyId) {
		logger.info("StudyServiceImpl - notificationSaved() - Starts");
		List<NotificationBO> notificationSavedList = null;
		try{
			notificationSavedList = studyDAO.getSavedNotification(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - notificationSaved() - Error",e);
		}
		logger.info("StudyServiceImpl - resourcesSaved() - Ends");
		return notificationSavedList;
	}
	/**
	 * Kanchana
	 * @param customStudyId
	 * @return
	 */
	@Override
	public StudyBo getStudyLiveStatusByCustomId(String customStudyId) {
		logger.info("StudyServiceImpl - getStudyLiveStatusByCustomId() - Starts");
		StudyBo studyLive = null;
		try{
			studyLive = studyDAO.getStudyLiveStatusByCustomId(customStudyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getStudyLiveStatusByCustomId() - Error",e);
		}
		logger.info("StudyServiceImpl - getStudyLiveStatusByCustomId() - Ends");
		return studyLive;
	}
	
	
	@Override
	public Checklist getchecklistInfo(Integer studyId) {
		logger.info("StudyServiceImpl - getchecklistInfo() - Starts");
		Checklist checklist = null;
		try{
			checklist = studyDAO.getchecklistInfo(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - getchecklistInfo() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getchecklistInfo() - Ends");
		return checklist;
	}
	
	@Override
	public Integer saveOrDoneChecklist(Checklist checklist,String actionBut, SessionObject sesObj,String customStudyId) {
		logger.info("StudyServiceImpl - saveOrDoneChecklist() - Starts");
		Integer checklistId = 0;
		Checklist checklistBO = null;
		String activity = "";
		String activityDetail = ""; 
		StudyBo studyBo = null;
		try{
			if(checklist.getChecklistId() == null){
				studyBo = studyDAO.getStudyById(checklist.getStudyId().toString(), sesObj.getUserId());
				checklist.setCustomStudyId(studyBo.getCustomStudyId());
				checklist.setCreatedBy(sesObj.getUserId());
				checklist.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
				activity = "Checklist created";
			}else{
				checklistBO = studyDAO.getchecklistInfo(checklist.getStudyId());
				checklist.setCustomStudyId(checklistBO.getCustomStudyId());
				checklist.setCreatedBy(checklistBO.getCreatedBy());
				checklist.setCreatedOn(checklistBO.getCreatedOn());
				checklist.setModifiedBy(sesObj.getUserId());
				checklist.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
				activity = "Checklist updated";
			}
			checklistId = studyDAO.saveOrDoneChecklist(checklist);
			if(!checklistId.equals(0)){
				if("save".equalsIgnoreCase(actionBut)){
					activityDetail = customStudyId+" -- Checklist saved as a draft as it is clicked on save";
					studyDAO.markAsCompleted(checklist.getStudyId(), FdahpStudyDesignerConstants.CHECK_LIST, false, sesObj,customStudyId);
				}else if("done".equalsIgnoreCase(actionBut)){
					activityDetail = customStudyId+" -- Checklist completed as it is clicked on done";
					studyDAO.markAsCompleted(checklist.getStudyId(), FdahpStudyDesignerConstants.CHECK_LIST, true, sesObj,customStudyId);
				}
					auditLogDAO.saveToAuditLog(null, null, sesObj, activity, activityDetail ,"StudyDAOImpl - saveOrDoneChecklist()");
			}
		}catch(Exception e){
			logger.error("StudyServiceImpl - saveOrDoneChecklist() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - saveOrDoneChecklist() - Ends");
		return checklistId;
	}

	@Override
	public String validateStudyAction(String studyId, String buttonText) {
		logger.info("StudyServiceImpl - validateStudyAction() - Starts");
		String message = "";
		try{
			message = studyDAO.validateStudyAction(studyId, buttonText);
		}catch(Exception e){
			logger.error("StudyServiceImpl - validateStudyAction() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - validateStudyAction() - Ends");
		return message;
	}

	@Override
	public String updateStudyActionOnAction(String studyId, String buttonText, SessionObject sesObj) {
		logger.info("StudyServiceImpl - updateStudyActionOnAction() - Starts");
		String message = "";
		try{
            if(StringUtils.isNotEmpty(studyId) &&  StringUtils.isNotEmpty(buttonText)){
            	message = studyDAO.updateStudyActionOnAction(studyId, buttonText, sesObj);
            }
		}catch(Exception e){
			logger.error("StudyServiceImpl - validateStudyAction() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - updateStudyActionOnAction() - Ends");
		return message;
	}



	@Override
	public String markAsCompleted(int studyId, String markCompleted,Boolean flag, SessionObject sesObj,String customStudyId) {
		logger.info("StudyServiceImpl - markAsCompleted() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyDAO.markAsCompleted(studyId, markCompleted, flag, sesObj,customStudyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - markAsCompleted() - Error",e);
		}
		logger.info("StudyServiceImpl - markAsCompleted() - Ends");
		return message;
	}
	
	/**
	 * return Study vesion on customStudyid
	 * @author Ronalin
	 * 
	 * @return StudyIdBean
	 * @exception Exception
	 */
	@Override
	public StudyIdBean getLiveVersion(String customStudyId){
		logger.info("StudyServiceImpl - getLiveVersion() - Starts");
		StudyIdBean studyIdBean = new StudyIdBean();
		try {
			studyIdBean  = studyDAO.getLiveVersion(customStudyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getLiveVersion() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getLiveVersion() - Ends");
		return studyIdBean;
	}

	@Override
	public List<ResourceBO> resourcesWithAnchorDate(Integer studyId) {
		logger.info("StudyServiceImpl - resourcesWithAnchorDate() - Starts");
		List<ResourceBO> resourceList = null;
		try{
			resourceList = studyDAO.resourcesWithAnchorDate(studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - resourcesWithAnchorDate() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - resourcesWithAnchorDate() - Ends");
		return resourceList;
	}

	@Override
	public String validateActivityComplete(String studyId, String action) {
		logger.info("StudyServiceImpl - validateActivityComplete() - Starts");
		String message = FdahpStudyDesignerConstants.SUCCESS;
		try{
			message = studyDAO.validateActivityComplete(studyId, action);
		}catch(Exception e){
			logger.error("StudyServiceImpl - validateActivityComplete() - Error",e);
		}
		logger.info("StudyServiceImpl - validateActivityComplete() - Ends");
		return message;
	}

	@Override
	public boolean deleteStudyByCustomStudyId(String customStudyId) {
		logger.info("StudyServiceImpl - deleteStudyByCustomStudyId() - Starts");
		boolean flag = false; 
		try{
			flag = studyDAO.deleteStudyByCustomStudyId(customStudyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - deleteStudyByCustomStudyId() - Error",e);
		}
		logger.info("StudyServiceImpl - deleteStudyByCustomStudyId() - Ends");
		return flag;
	}
}
