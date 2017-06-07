package com.fdahpstudydesigner.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpstudydesigner.bean.StudyIdBean;
import com.fdahpstudydesigner.bean.StudyListBean;
import com.fdahpstudydesigner.bean.StudyPageBean;
import com.fdahpstudydesigner.bean.StudySessionBean;
import com.fdahpstudydesigner.bo.Checklist;
import com.fdahpstudydesigner.bo.ComprehensionTestQuestionBo;
import com.fdahpstudydesigner.bo.ConsentBo;
import com.fdahpstudydesigner.bo.ConsentInfoBo;
import com.fdahpstudydesigner.bo.ConsentMasterInfoBo;
import com.fdahpstudydesigner.bo.EligibilityBo;
import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.NotificationHistoryBO;
import com.fdahpstudydesigner.bo.ReferenceTablesBo;
import com.fdahpstudydesigner.bo.ResourceBO;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.bo.StudyPageBo;
import com.fdahpstudydesigner.bo.StudySequenceBo;
import com.fdahpstudydesigner.service.NotificationService;
import com.fdahpstudydesigner.service.StudyQuestionnaireService;
import com.fdahpstudydesigner.service.StudyService;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

/**
 * @author Ronalin
 *
 */
@Controller
public class StudyController {

    private static Logger logger = Logger.getLogger(StudyController.class.getName());
	
	@Autowired
	private StudyService studyService;
	
	@Autowired
	private NotificationService notificationService;
	
	@Autowired
	private StudyQuestionnaireService studyQuestionnaireService;
	
	/**
     * @author Ronalin
	 * Getting Study list
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/studyList.do")
	public ModelAndView getStudies(HttpServletRequest request){
		logger.info("StudyController - getStudies - Starts");
		ModelAndView mav = new ModelAndView("loginPage");
		ModelMap map = new ModelMap();
		List<StudyListBean> studyBos = null;
		String sucMsg = "";
		String errMsg = "";
        String actionSucMsg = "";
		try{	
            SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.ERR_MSG);
				}
				if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.ACTION_SUC_MSG)){
					actionSucMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.ACTION_SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ACTION_SUC_MSG, actionSucMsg);
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.ACTION_SUC_MSG);
				}
				if(request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID) != null){
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.STUDY_ID);
				}
				if(request.getSession().getAttribute(FdahpStudyDesignerConstants.PERMISSION) != null){
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.PERMISSION);
				}
				if(request.getSession().getAttribute(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID) != null){
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				}
				if(request.getSession().getAttribute(FdahpStudyDesignerConstants.IS_LIVE) != null){
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.IS_LIVE);
				}
				if(request.getSession().getAttribute(FdahpStudyDesignerConstants.CONSENT_STUDY_ID) != null){
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.CONSENT_STUDY_ID);
				}
				/*if(request.getSession().getAttribute(FdahpStudyDesignerConstants.ACTIVITY_STUDY_ID) != null){
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.ACTIVITY_STUDY_ID);
				}*/
				if(request.getSession().getAttribute(FdahpStudyDesignerConstants.ACTIVE_TASK_STUDY_ID) != null){
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.ACTIVE_TASK_STUDY_ID);
				}
				if(request.getSession().getAttribute(FdahpStudyDesignerConstants.QUESTIONNARIE_STUDY_ID) != null){
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.QUESTIONNARIE_STUDY_ID);
				}
				studyBos = studyService.getStudyList(sesObj.getUserId());
				map.addAttribute("studyBos", studyBos);
				map.addAttribute("studyListId","true"); 
				mav = new ModelAndView("studyListPage", map);
			}
		}catch(Exception e){
			logger.error("StudyController - getStudies - ERROR",e);
		}
		logger.info("StudyController - getStudies - Ends");
		return mav;
	}
	
	@RequestMapping("/adminStudies/viewStudyDetails.do")
	public ModelAndView viewStudyDetails(HttpServletRequest request){
		Integer sessionStudyCount;
		ModelMap map = new ModelMap();
		ModelAndView modelAndView = new ModelAndView("redirect:/adminStudies/studyList.do");
		String  studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
		String  permission = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.PERMISSION))? "" : request.getParameter(FdahpStudyDesignerConstants.PERMISSION);
		String isLive = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.IS_LIVE))? "" : request.getParameter(FdahpStudyDesignerConstants.IS_LIVE);
		SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
		List<Integer> studySessionList = new ArrayList<>();
		List<StudySessionBean> studySessionBeans = new ArrayList<>();
		StudySessionBean studySessionBean = null;
		try {
			sessionStudyCount = (Integer) (request.getSession().getAttribute("sessionStudyCount") != null ? request.getSession().getAttribute("sessionStudyCount") : 0);
			if(sesObj != null) {
				if(sesObj.getStudySessionBeans() != null && !sesObj.getStudySessionBeans().isEmpty())
					for (StudySessionBean sessionBean : sesObj.getStudySessionBeans()) {
						if(sessionBean != null && sessionBean.getPermission().equals(permission) && sessionBean.getIsLive().equals(isLive) && sessionBean.getStudyId().equals(studyId)) {
							studySessionBean = sessionBean;
						}
					}
				if(studySessionBean != null){
					sessionStudyCount = studySessionBean.getSessionStudyCount();
				} else {
					++sessionStudyCount;
					if(sesObj.getStudySession() != null && !sesObj.getStudySession().isEmpty()) {
						studySessionList.addAll(sesObj.getStudySession());
					}
					studySessionList.add(sessionStudyCount);
					sesObj.setStudySession(studySessionList);
					
					if(sesObj.getStudySessionBeans() != null && !sesObj.getStudySessionBeans().isEmpty()) {
						studySessionBeans.addAll(sesObj.getStudySessionBeans());
					}
					studySessionBean = new StudySessionBean();
					studySessionBean.setIsLive(isLive);
					studySessionBean.setPermission(permission);
					studySessionBean.setSessionStudyCount(sessionStudyCount);
					studySessionBean.setStudyId(studyId);
					studySessionBeans.add(studySessionBean);
					sesObj.setStudySessionBeans(studySessionBeans);
				}
			}
			
			map.addAttribute("_S", sessionStudyCount);
			request.getSession().setAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT, sesObj);
			request.getSession().setAttribute("sessionStudyCount", sessionStudyCount);
			request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID, studyId);
			request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION, permission);
			request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_LIVE, isLive);
			modelAndView = new ModelAndView("redirect:/adminStudies/viewBasicInfo.do", map);
		} catch (Exception e) {
			logger.error("StudyController - viewStudyDetails - ERROR", e);
		}
		return modelAndView;
	}
	/**
     * @author Ronalin
	 * add baisc info page
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/viewBasicInfo.do")
	public ModelAndView viewBasicInfo(HttpServletRequest request){
		logger.info("StudyController - viewBasicInfo - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		HashMap<String, List<ReferenceTablesBo>> referenceMap = null;
		List<ReferenceTablesBo> categoryList = null;
		List<ReferenceTablesBo> researchSponserList = null;
		List<ReferenceTablesBo> dataPartnerList = null;
		StudyBo studyBo = null;
		String sucMsg = "";
		String errMsg = "";
		ConsentBo consentBo = null;
		StudyIdBean studyIdBean = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				String  studyId = (String) (FdahpStudyDesignerUtil.isEmpty((String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID))? "" : request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID));
//				if(FdahpStudyDesignerUtil.isEmpty(studyId)){
//					studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
//				} else {
//					request.getSession().setAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
//				}
				
				String  permission = (String) (FdahpStudyDesignerUtil.isEmpty((String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION))? "" : request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION));
//				if(FdahpStudyDesignerUtil.isEmpty(permission)){
//					permission = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.PERMISSION);
//				} else {
//					request.getSession().setAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
//				}
				
				String isLive = (String) (FdahpStudyDesignerUtil.isEmpty((String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_LIVE))? "" : request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_LIVE));
//				if(FdahpStudyDesignerUtil.isNotEmpty(isLive)){
//					request.getSession().setAttribute(FdahpStudyDesignerConstants.IS_LIVE, isLive);
//				}else{
//					request.getSession().removeAttribute(FdahpStudyDesignerConstants.IS_LIVE);
//				}
				
				if(FdahpStudyDesignerUtil.isEmpty(isLive)){
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_LIVE);
				}
				
				if(FdahpStudyDesignerUtil.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					if(StringUtils.isNotEmpty(isLive) && isLive.equalsIgnoreCase(FdahpStudyDesignerConstants.YES) && studyBo!=null){
						studyIdBean  = studyService.getLiveVersion(studyBo.getCustomStudyId());
						if(studyIdBean!=null){
							consentBo = studyService.getConsentDetailsByStudyId(studyIdBean.getConsentStudyId().toString());
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_STUDY_ID, studyIdBean.getConsentStudyId().toString());
							//request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTIVITY_STUDY_ID, studyIdBean.getActivityStudyId().toString());
							if(studyIdBean.getActivetaskStudyId()!=null)
							 request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTIVE_TASK_STUDY_ID, studyIdBean.getActivetaskStudyId().toString());
							if(studyIdBean.getQuestionnarieStudyId()!=null)
							 request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.QUESTIONNARIE_STUDY_ID, studyIdBean.getQuestionnarieStudyId().toString());
						}
					}else{
						consentBo = studyService.getConsentDetailsByStudyId(studyId);
					}
					//get consentId if exists for studyId
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID);
					if( consentBo != null){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID, consentBo.getId());
					}else{
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID);
					}
				}
				if(studyBo == null){
					studyBo = new StudyBo();
				}else if(studyBo!=null && StringUtils.isNotEmpty(studyBo.getCustomStudyId())){
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
				}
				referenceMap = (HashMap<String, List<ReferenceTablesBo>>) studyService.getreferenceListByCategory();
				if(referenceMap!=null && referenceMap.size()>0){
				for (String key : referenceMap.keySet()) {
					if (StringUtils.isNotEmpty(key)) {
						switch (key) {
						case FdahpStudyDesignerConstants.REFERENCE_TYPE_CATEGORIES:
							 categoryList = referenceMap.get(key);
							 break;
						case FdahpStudyDesignerConstants.REFERENCE_TYPE_RESEARCH_SPONSORS:
							researchSponserList = referenceMap.get(key);
 							break;
						case FdahpStudyDesignerConstants.REFERENCE_TYPE_DATA_PARTNER:
							dataPartnerList = referenceMap.get(key);
							break;
						default:
							break;
						}
					}
				  }
				}
				map.addAttribute("categoryList",categoryList);
				map.addAttribute("researchSponserList",researchSponserList);
				map.addAttribute("dataPartnerList",dataPartnerList);
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO,studyBo);
				map.addAttribute("createStudyId","true");
				map.addAttribute(FdahpStudyDesignerConstants.PERMISSION,permission); 
				map.addAttribute("_S", sessionStudyCount);
				mav = new ModelAndView("viewBasicInfo", map);
				
			}
		}catch(Exception e){
			logger.error("StudyController - viewBasicInfo - ERROR",e);
		}
		logger.info("StudyController - viewBasicInfo - Ends");
		return mav;
	}
	
	/** 
	  * @author Ronalin
	  * validating particular Study custom Id
	  * @param request , {@link HttpServletRequest}
	  * @param response , {@link HttpServletResponse}
	  * @throws IOException
	  * @return void
	  */
		@RequestMapping(value="/adminStudies/validateStudyId.do",  method = RequestMethod.POST)
		public void validateStudyId(HttpServletRequest request, HttpServletResponse response) throws IOException{
			logger.info("StudyController - validateStudyId() - Starts ");
			JSONObject jsonobject = new JSONObject();
			PrintWriter out;
			String message = FdahpStudyDesignerConstants.FAILURE;
			boolean flag = false;
			try{
				HttpSession session = request.getSession();
				SessionObject userSession = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				if (userSession != null) {
					String customStudyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("customStudyId"))?"":request.getParameter("customStudyId");
					flag = studyService.validateStudyId(customStudyId);
					if(flag)
						message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}catch (Exception e) {
				logger.error("StudyController - validateStudyId() - ERROR ", e);
			}
			logger.info("StudyController - validateStudyId() - Ends ");
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}
	
	/**
     * @author Ronalin
	 * save or update baisc info page
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/saveOrUpdateBasicInfo.do")
	public ModelAndView saveOrUpdateBasicInfo(HttpServletRequest request,@ModelAttribute(FdahpStudyDesignerConstants.STUDY_BO) StudyBo studyBo){
		logger.info("StudyController - saveOrUpdateBasicInfo - Starts");
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		String fileName = "";
		String file="";
		String buttonText = "";
		String message = FdahpStudyDesignerConstants.FAILURE;
		ModelMap map = new ModelMap();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			buttonText = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT)) ? "" : request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(studyBo.getId()==null){
					StudySequenceBo studySequenceBo = new StudySequenceBo();
					studySequenceBo.setBasicInfo(true);
					studyBo.setStudySequenceBo(studySequenceBo);
					studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_PRE_LAUNCH);
				}
				studyBo.setUserId(sesObj.getUserId());
				if(studyBo.getFile()!=null && !studyBo.getFile().isEmpty()){
					if(FdahpStudyDesignerUtil.isNotEmpty(studyBo.getThumbnailImage())){
						file = studyBo.getThumbnailImage().replace("."+studyBo.getThumbnailImage().split("\\.")[studyBo.getThumbnailImage().split("\\.").length - 1], "");
					} else {
						file = FdahpStudyDesignerUtil.getStandardFileName("STUDY",studyBo.getName(), studyBo.getCustomStudyId());
					}
					fileName = FdahpStudyDesignerUtil.uploadImageFile(studyBo.getFile(),file, FdahpStudyDesignerConstants.STUDTYLOGO);
					studyBo.setThumbnailImage(fileName);
				} 
				studyBo.setButtonText(buttonText);
				message = studyService.saveOrUpdateStudy(studyBo, sesObj.getUserId(), sesObj);
				request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId()+"");
				map.addAttribute("_S", sessionStudyCount);
				if(FdahpStudyDesignerConstants.SUCCESS.equals(message)) {
					if(studyBo!=null && StringUtils.isNotEmpty(studyBo.getCustomStudyId())){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
					}
					if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
						  request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
						  return new ModelAndView("redirect:viewSettingAndAdmins.do", map);
					}else{
						  request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));  
						  return new ModelAndView("redirect:viewBasicInfo.do", map);
					}
				}else {
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Error in set BasicInfo.");
					return new ModelAndView("redirect:viewBasicInfo.do", map);
				}
			}
		}catch(Exception e){
			logger.error("StudyController - saveOrUpdateBasicInfo - ERROR",e);
		}
		logger.info("StudyController - saveOrUpdateBasicInfo - Ends");
		return mav;
	}
	
	/**
     * @author Ronalin
	 * view baisc info page
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/viewSettingAndAdmins.do")
	public ModelAndView viewSettingAndAdmins(HttpServletRequest request){
		logger.info("StudyController - viewSettingAndAdmins - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		StudyBo studyBo = null;
		String sucMsg = "";
		String errMsg = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				String  studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				if(FdahpStudyDesignerUtil.isEmpty(studyId)){
					studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				}
				String permission = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION);
				map.addAttribute("_S", sessionStudyCount);
				if(FdahpStudyDesignerUtil.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO,studyBo);
					map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
					mav = new ModelAndView(FdahpStudyDesignerConstants.VIEW_SETTING_AND_ADMINS, map);
				}else{
					return new ModelAndView("redirect:studyList.do");
				}
			}
		}catch(Exception e){
			logger.error("StudyController - viewSettingAndAdmins - ERROR",e);
		}
		logger.info("StudyController - viewSettingAndAdmins - Ends");
		return mav;
	}
	
	/** 
	  * @author Ronalin
	  * Removing particular Study permission for the current user
	  * @param request , {@link HttpServletRequest}
	  * @param response , {@link HttpServletResponse}
	  * @throws IOException
	  * @return void
	  */
		@RequestMapping("/adminStudies/removeStudyPermissionById.do")
		public void removeStudyPermissionById(HttpServletRequest request, HttpServletResponse response) throws IOException{
			logger.info("StudyController - removeStudyPermissionById() - Starts ");
			JSONObject jsonobject = new JSONObject();
			PrintWriter out;
			String message = FdahpStudyDesignerConstants.FAILURE;
			boolean flag = false;
			try{
				HttpSession session = request.getSession();
				SessionObject userSession = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				if (userSession != null) {
					String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					flag = studyService.deleteStudyPermissionById(userSession.getUserId(), studyId);
					if(flag)
						message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}catch (Exception e) {
				logger.error("StudyController - removeStudyPermissionById() - ERROR ", e);
			}
			logger.info("StudyController - removeStudyPermissionById() - Ends ");
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}
		
		/** 
		  * @author Ronalin
		  * Adding particular Study permission for the users
		  * @param request , {@link HttpServletRequest}
		  * @param response , {@link HttpServletResponse}
		  * @throws IOException
		  * @return void
		  */
			@RequestMapping("/adminStudies/addStudyPermissionByuserIds.do")
			public void addStudyPermissionByuserIds(HttpServletRequest request, HttpServletResponse response) throws IOException{
				logger.info("StudyController - addStudyPermissionByuserIds() - Starts ");
				JSONObject jsonobject = new JSONObject();
				PrintWriter out;
				String message = FdahpStudyDesignerConstants.FAILURE;
				boolean flag = false;
				try{
					HttpSession session = request.getSession();
					SessionObject userSession = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
					if (userSession != null) {
						String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
						String userIds = FdahpStudyDesignerUtil.isEmpty(request.getParameter("userIds"))?"":request.getParameter("userIds");
						flag = studyService.addStudyPermissionByuserIds(userSession.getUserId(), studyId, userIds);
						if(flag)
							message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}catch (Exception e) {
					logger.error("StudyController - addStudyPermissionByuserIds() - ERROR ", e);
				}
				logger.info("StudyController - addStudyPermissionByuserIds() - Ends ");
				jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
				response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
				out = response.getWriter();
				out.print(jsonobject);
			}
		
		/**
	     * @author Ronalin
		 * save or update setting and admins page
		 * @param request , {@link HttpServletRequest}
		 * @return {@link ModelAndView}
		 */
		@RequestMapping("/adminStudies/saveOrUpdateSettingAndAdmins.do")
		public ModelAndView saveOrUpdateSettingAndAdmins(HttpServletRequest request, StudyBo studyBo){
			logger.info("StudyController - saveOrUpdateSettingAndAdmins - Starts");
			Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
			ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
			String message = FdahpStudyDesignerConstants.FAILURE;
			ModelMap map = new ModelMap();
			try{
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
				if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
					String buttonText = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT)) ? "" : request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT);
					studyBo.setButtonText(buttonText);
					studyBo.setUserId(sesObj.getUserId());
					message = studyService.saveOrUpdateStudySettings(studyBo, sesObj);
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId()+"");
					map.addAttribute("_S", sessionStudyCount);
					if(FdahpStudyDesignerConstants.SUCCESS.equals(message)) {
						if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
							return new ModelAndView("redirect:overviewStudyPages.do", map);
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
							return new ModelAndView("redirect:viewSettingAndAdmins.do", map);
						}
					}else {
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Error in set Setting and Admins.");
						return new ModelAndView("redirect:viewSettingAndAdmins.do", map);
					}
				}
			}catch(Exception e){
				logger.error("StudyController - saveOrUpdateSettingAndAdmins - ERROR",e);
			}
			logger.info("StudyController - saveOrUpdateSettingAndAdmins - Ends");
			return mav;
		}
		/**
	     * @author Ronalin
		 * view Overview Study page
		 * @param request , {@link HttpServletRequest}
		 * @return {@link ModelAndView}
		 */
		@RequestMapping("/adminStudies/overviewStudyPages.do")
		public ModelAndView overviewStudyPages(HttpServletRequest request){
			logger.info("StudyController - overviewStudyPages - Starts");
			ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
			ModelMap map = new ModelMap();
			List<StudyPageBo> studyPageBos = null;
			StudyBo studyBo = null;
			String sucMsg = "";
			String errMsg = "";
			StudyPageBean studyPageBean = new StudyPageBean();
			try{
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
				if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
					if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
						sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
						map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					}
					if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
						errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
						map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					}
					String  studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					if(FdahpStudyDesignerUtil.isEmpty(studyId)){
						studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
					}
					String permission = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION);
					if(StringUtils.isNotEmpty(studyId)){
						studyPageBos = studyService.getOverviewStudyPagesById(studyId, sesObj.getUserId());
						studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
						studyPageBean.setStudyId(studyBo.getId().toString());
						map.addAttribute("studyPageBos",studyPageBos);
						map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO,studyBo);
						map.addAttribute("studyPageBean", studyPageBean);
						map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
						map.addAttribute("_S", sessionStudyCount);
						mav = new ModelAndView("overviewStudyPages", map);
					}else{
						return new ModelAndView("redirect:studyList.do");
					}
				}
			}catch(Exception e){
				logger.error("StudyController - overviewStudyPages - ERROR",e);
			}
			logger.info("StudyController - overviewStudyPages - Ends");
			return mav;
		}
				/**
			     * @author Ronalin
				 * save or update study page
				 * @param request , {@link HttpServletRequest}
				 * @return {@link ModelAndView}
				 */
				@RequestMapping("/adminStudies/saveOrUpdateStudyOverviewPage.do")
				public ModelAndView saveOrUpdateStudyOverviewPage(HttpServletRequest request,StudyPageBean studyPageBean){
					logger.info("StudyController - saveOrUpdateStudyOverviewPage - Starts");
					Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
					String message = FdahpStudyDesignerConstants.FAILURE;
					ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
					ModelMap map = new ModelMap();
					try{
						SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
						String buttonText = studyPageBean.getActionType();
						Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
						if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
							studyPageBean.setUserId(sesObj.getUserId());
							message = studyService.saveOrUpdateOverviewStudyPages(studyPageBean ,sesObj);
							map.addAttribute("_S", sessionStudyCount);
							if(FdahpStudyDesignerConstants.SUCCESS.equals(message)) {
								if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
									request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
									return new ModelAndView("redirect:viewStudyEligibilty.do", map);
								}else{
									request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
									return new ModelAndView("redirect:overviewStudyPages.do", map);
								}
							}else {
								request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Error in setting Overview.");
								 return new ModelAndView("redirect:overviewStudyPages.do", map);
							}
						}
					}catch(Exception e){
						logger.error("StudyController - saveOrUpdateStudyOverviewPage - ERROR",e);
					}
					logger.info("StudyController - saveOrUpdateStudyOverviewPage - Ends");
					return mav;
				}
				
	/**
	 * @author Ravinder			
	 * @param request
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/consentListPage.do")
	public ModelAndView getConsentListPage(HttpServletRequest request){
		logger.info("StudyController - getConsentListPage - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		StudyBo studyBo = null;
		ConsentBo consentBo = null;
		String sucMsg = "";
		String errMsg = "";
		String consentStudyId = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
			if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
				sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
				request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
			}
			if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
			}
			List<ConsentInfoBo> consentInfoList;
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				String permission = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				//Added for live version Start
				String isLive = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_LIVE);
				if(StringUtils.isNotEmpty(isLive) && isLive.equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
					consentStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_STUDY_ID);
				}
				//Added for live version End
				if(StringUtils.isNotEmpty(studyId)){
					boolean markAsComplete = true;
					if(StringUtils.isNotEmpty(consentStudyId)){
						consentInfoList = studyService.getConsentInfoList(Integer.valueOf(consentStudyId));
					}else{
						consentInfoList = studyService.getConsentInfoList(Integer.valueOf(studyId));
					}
					if(consentInfoList != null && !consentInfoList.isEmpty()){
						for(ConsentInfoBo conInfoBo : consentInfoList){
							if(!conInfoBo.getStatus()){
								markAsComplete = false;
								break;
							}
						}
					}
					map.addAttribute("markAsComplete", markAsComplete);
					map.addAttribute(FdahpStudyDesignerConstants.CONSENT_INFO_LIST, consentInfoList);
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					
					//get consentbo details by studyId
					if(StringUtils.isNotEmpty(consentStudyId)){
					    consentBo = studyService.getConsentDetailsByStudyId(consentStudyId);
					}else{
						consentBo = studyService.getConsentDetailsByStudyId(studyId);
					}
					if( consentBo != null){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID, consentBo.getId());
						map.addAttribute(FdahpStudyDesignerConstants.CONSENT_ID, consentBo.getId());
					}
				}
				map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
				map.addAttribute("_S", sessionStudyCount);
				mav = new ModelAndView(FdahpStudyDesignerConstants.CONSENT_INFO_LIST_PAGE,map);
			}
		}catch(Exception e){
			logger.error("StudyController - getConsentListPage - ERROR",e);
		}
		logger.info("StudyController - getConsentListPage - Ends");
		return mav;
		
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/reOrderConsentInfo.do", method = RequestMethod.POST)
	public void reOrderConsentInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyController - reOrderConsentInfo - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			int oldOrderNumber;
			int newOrderNumber;
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				String oldOrderNo = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.OLD_ORDER_NUMBER))?"0":request.getParameter(FdahpStudyDesignerConstants.OLD_ORDER_NUMBER);
				String newOrderNo = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.NEW_ORDER_NUMBER))?"0":request.getParameter(FdahpStudyDesignerConstants.NEW_ORDER_NUMBER);
				if((studyId != null && !studyId.isEmpty()) && !oldOrderNo.isEmpty() && !newOrderNo.isEmpty()){
					oldOrderNumber = Integer.valueOf(oldOrderNo);
					newOrderNumber = Integer.valueOf(newOrderNo);
					message = studyService.reOrderConsentInfoList(Integer.valueOf(studyId), oldOrderNumber, newOrderNumber);
				}
			}
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - reOrderConsentInfo - ERROR",e);
		}
		logger.info("StudyController - reOrderConsentInfo - Ends");
	}
	
	/**
	 * @author Ravinder			
	 * @param request
	 * @param response
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/reloadConsentListPage.do")
	public void reloadConsentListPage(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyController - reloadConsentListPage - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		ObjectMapper mapper = new ObjectMapper();
		JSONArray consentJsonArray = null;
		List<ConsentInfoBo> consentInfoList = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isNotEmpty(studyId)){
					consentInfoList = studyService.getConsentInfoList(Integer.valueOf(studyId));
					if(consentInfoList!= null && !consentInfoList.isEmpty()){
						consentJsonArray = new JSONArray(mapper.writeValueAsString(consentInfoList));
					}
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
				jsonobject.put(FdahpStudyDesignerConstants.CONSENT_INFO_LIST,consentJsonArray);
			}
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - reloadConsentListPage - ERROR",e);
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			if(out != null){
				out.print(jsonobject);
			}
		}
		logger.info("StudyController - reloadConsentListPage - Ends");
		
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/deleteConsentInfo.do",method = RequestMethod.POST)
	public void deleteConsentInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyController - deleteConsentInfo - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		String customStudyId = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String consentInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.CONSENT_INFO_ID))?"":request.getParameter(FdahpStudyDesignerConstants.CONSENT_INFO_ID);
				String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				if(!consentInfoId.isEmpty() && !studyId.isEmpty()){
					message = studyService.deleteConsentInfo(Integer.valueOf(consentInfoId),Integer.valueOf(studyId),sesObj,customStudyId);
				}
			}
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - deleteConsentInfo - ERROR",e);
		}
		logger.info("StudyController - deleteConsentInfo - Ends");
	}
	
	/**
	 * 
	 * @author Ravinder
	 * @param request
	 * @param consentInfoBo
	 * @return
	 */
	@RequestMapping("/adminStudies/saveOrUpdateConsentInfo.do")
	public ModelAndView saveOrUpdateConsentInfo(HttpServletRequest request,ConsentInfoBo consentInfoBo){
		logger.info("StudyController - saveOrUpdateConsentInfo - Starts");
		ModelAndView mav = new ModelAndView(FdahpStudyDesignerConstants.CONSENT_INFO_LIST_PAGE);
		ConsentInfoBo addConsentInfoBo = null;
		ModelMap map = new ModelMap();
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String customStudyId = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(consentInfoBo != null){
					if(consentInfoBo.getStudyId() != null && consentInfoBo.getId() == null){
						int order = studyService.consentInfoOrder(consentInfoBo.getStudyId());
						consentInfoBo.setSequenceNo(order);
					}
					customStudyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
					addConsentInfoBo = studyService.saveOrUpdateConsentInfo(consentInfoBo, sesObj,customStudyId);
					if(addConsentInfoBo != null){
						if(consentInfoBo.getId() != null){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get("update.consent.success.message"));
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get("save.consent.success.message"));
						}
						map.addAttribute("_S", sessionStudyCount);
						mav = new ModelAndView("redirect:/adminStudies/consentListPage.do",map);
					}else{
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Consent not added successfully.");
						map.addAttribute("_S", sessionStudyCount);
						mav = new ModelAndView("redirect:/adminStudies/consentListPage.do", map);
					}
				}
			}	
		}catch(Exception e){
			logger.error("StudyController - saveOrUpdateConsentInfo - ERROR",e);
		}
		logger.info("StudyController - saveOrUpdateConsentInfo - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @return
	 */
	@RequestMapping("/adminStudies/consentInfo.do")
	public ModelAndView getConsentPage(HttpServletRequest request){
		logger.info("StudyController - getConsentPage - Starts");
		ModelAndView mav = new ModelAndView(FdahpStudyDesignerConstants.CONSENT_INFO_PAGE);
		ModelMap map = new ModelMap();
		ConsentInfoBo consentInfoBo = null;
		StudyBo studyBo = null;
		List<ConsentInfoBo> consentInfoList = new ArrayList<>();
		List<ConsentMasterInfoBo> consentMasterInfoList = new ArrayList<>();
		String sucMsg = "";
		String errMsg = "";
		String consentStudyId = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				String consentInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.CONSENT_INFO_ID))?"":request.getParameter(FdahpStudyDesignerConstants.CONSENT_INFO_ID);
				String actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.ACTION_TYPE))?"":request.getParameter(FdahpStudyDesignerConstants.ACTION_TYPE);
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID, studyId);
				}
				//Added for live version Start
				String isLive = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_LIVE);
				if(StringUtils.isNotEmpty(isLive) && isLive.equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
					consentStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_STUDY_ID);
				}
				//Added for live version End
				if(StringUtils.isEmpty(consentInfoId)){
					consentInfoId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_INFO_ID);
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_INFO_ID, consentInfoId);
				}
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
				if(!studyId.isEmpty()){
					if(StringUtils.isNotEmpty(consentStudyId)){
						consentInfoList = studyService.getConsentInfoList(Integer.valueOf(consentStudyId));
					}else{
						consentInfoList = studyService.getConsentInfoList(Integer.valueOf(studyId));
					}
					if(("view").equals(actionType)){
						map.addAttribute(FdahpStudyDesignerConstants.ACTION_PAGE, "view");
					}else{
						map.addAttribute(FdahpStudyDesignerConstants.ACTION_PAGE, "addEdit");
					}
					consentMasterInfoList = studyService.getConsentMasterInfoList();
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					map.addAttribute("consentMasterInfoList", consentMasterInfoList);
					if(consentMasterInfoList != null && !consentMasterInfoList.isEmpty()){
						map.addAttribute(FdahpStudyDesignerConstants.CONSENT_INFO_LIST, consentInfoList);
					}
				}
				if(consentInfoId != null && !consentInfoId.isEmpty()){
					consentInfoBo = studyService.getConsentInfoById(Integer.valueOf(consentInfoId));
					map.addAttribute("consentInfoBo", consentInfoBo);
				}
				map.addAttribute("_S", sessionStudyCount);
				mav = new ModelAndView(FdahpStudyDesignerConstants.CONSENT_INFO_PAGE,map);
			}
		}catch(Exception e){
			logger.error("StudyController - getConsentPage - Error",e);
		}
		logger.info("StudyController - getConsentPage - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @return
	 */
	@RequestMapping("/adminStudies/comprehensionQuestionList.do")
	public ModelAndView getComprehensionQuestionList(HttpServletRequest request){
		logger.info("StudyController - getComprehensionQuestionList - Starts");
		ModelAndView mav = new ModelAndView("comprehensionListPage");
		ModelMap map = new ModelMap();
		StudyBo studyBo=null;
		ConsentBo consentBo = null;
		String sucMsg = "";
		String errMsg = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.SUC_MSG)){
				sucMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.SUC_MSG);
				map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.SUC_MSG);
			}
			if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.ERR_MSG);
			}
			List<ComprehensionTestQuestionBo> comprehensionTestQuestionList;
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				if(StringUtils.isNotEmpty(studyId)){
					comprehensionTestQuestionList = studyService.getComprehensionTestQuestionList(Integer.valueOf(studyId));
					map.addAttribute("comprehensionTestQuestionList", comprehensionTestQuestionList);
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					
					//get consentId if exists for studyId
					consentBo = studyService.getConsentDetailsByStudyId(studyId);
					if( consentBo != null){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.CONSENT_ID, consentBo.getId());
						map.addAttribute(FdahpStudyDesignerConstants.CONSENT_ID, consentBo.getId());
						map.addAttribute("comprehensionTestMinimumScore", consentBo.getComprehensionTestMinimumScore());
					}
				}
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
				mav = new ModelAndView("comprehensionListPage",map);
			}
		}catch(Exception e){
			logger.error("StudyController - getComprehensionQuestionList - ERROR",e);
		}
		logger.info("StudyController - getComprehensionQuestionList - Ends");
		return mav;
	}
	
	@RequestMapping("/adminStudies/comprehensionQuestionPage.do")
	public ModelAndView getComprehensionQuestionPage(HttpServletRequest request){
		logger.info("StudyController - getConsentPage - Starts");
		ModelAndView mav = new ModelAndView("comprehensionQuestionPage");
		ModelMap map = new ModelMap();
		ComprehensionTestQuestionBo comprehensionTestQuestionBo = null;
		String sucMsg = "";
		String errMsg = "";
		StudyBo studyBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.SUC_MSG)){
				sucMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.SUC_MSG);
				map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.SUC_MSG);
			}
			if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.ERR_MSG);
			}
			if(sesObj!=null){
				String comprehensionQuestionId =  FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.COMPREHENSION_QUESTION_ID))?"":request.getParameter(FdahpStudyDesignerConstants.COMPREHENSION_QUESTION_ID);
				String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					request.getSession().setAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
				}
				if(StringUtils.isNotEmpty(comprehensionQuestionId)){
					comprehensionTestQuestionBo = studyService.getComprehensionTestQuestionById(Integer.valueOf(comprehensionQuestionId));
					map.addAttribute("comprehensionQuestionBo", comprehensionTestQuestionBo);
					mav = new ModelAndView("comprehensionQuestionPage",map);
				}
			}
		}catch(Exception e){
			logger.error("StudyController - getConsentPage - Error",e);
		}
		logger.info("StudyController - getConsentPage - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping("/adminStudies/deleteComprehensionQuestion.do")
	public void deleteComprehensionTestQuestion(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyController - deleteComprehensionTestQuestion - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String comprehensionQuestionId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.COMPREHENSION_QUESTION_ID)) ?"":request.getParameter(FdahpStudyDesignerConstants.COMPREHENSION_QUESTION_ID);
				String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isNotEmpty(comprehensionQuestionId) && StringUtils.isNotEmpty(studyId)){
					message = studyService.deleteComprehensionTestQuestion(Integer.valueOf(comprehensionQuestionId),Integer.valueOf(studyId));
				}
			}
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - deleteComprehensionTestQuestion - ERROR",e);
		}
		logger.info("StudyController - deleteComprehensionTestQuestion - Ends");
	}
	
	/**
	 * 
	 * @author Ravinder
	 * @param request
	 * @param consentInfoBo
	 * @return
	 */
	@RequestMapping("/adminStudies/saveOrUpdateComprehensionTestQuestion.do")
	public ModelAndView saveOrUpdateComprehensionTestQuestionPage(HttpServletRequest request,ComprehensionTestQuestionBo comprehensionTestQuestionBo){
		logger.info("StudyController - saveOrUpdateComprehensionTestQuestionPage - Starts");
		ModelAndView mav = new ModelAndView(FdahpStudyDesignerConstants.CONSENT_INFO_LIST_PAGE);
		ComprehensionTestQuestionBo addComprehensionTestQuestionBo = null;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				if(comprehensionTestQuestionBo != null){
					if(comprehensionTestQuestionBo.getStudyId() != null){
						int order = studyService.comprehensionTestQuestionOrder(comprehensionTestQuestionBo.getStudyId());
						comprehensionTestQuestionBo.setSequenceNo(order);
					}
					if(comprehensionTestQuestionBo.getId() != null){
						comprehensionTestQuestionBo.setModifiedBy(sesObj.getUserId());
						comprehensionTestQuestionBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						comprehensionTestQuestionBo.setCreatedBy(sesObj.getUserId());
						comprehensionTestQuestionBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addComprehensionTestQuestionBo = studyService.saveOrUpdateComprehensionTestQuestion(comprehensionTestQuestionBo);
					if(addComprehensionTestQuestionBo != null){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
						return new ModelAndView("redirect:/adminStudies/comprehensionQuestionList.do");
					}else{
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Unable to add Question added.");
						return new ModelAndView("redirect:/adminStudies/comprehensionQuestionList.do");
					}
				}
			}
		}catch(Exception e){
			logger.error("StudyController - saveOrUpdateComprehensionTestQuestionPage - ERROR",e);
		}
		logger.info("StudyController - saveOrUpdateComprehensionTestQuestionPage - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping("/adminStudies/reOrderComprehensionTestQuestion.do")
	public void reOrderComprehensionTestQuestion(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyController - reOrderComprehensionTestQuestion - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			int oldOrderNumber;
			int newOrderNumber;
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				String oldOrderNo = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.OLD_ORDER_NUMBER))?"":request.getParameter(FdahpStudyDesignerConstants.OLD_ORDER_NUMBER);
				String newOrderNo = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.NEW_ORDER_NUMBER))?"":request.getParameter(FdahpStudyDesignerConstants.NEW_ORDER_NUMBER);
				if((studyId != null && !studyId.isEmpty()) && !oldOrderNo.isEmpty() && !newOrderNo.isEmpty()){
					oldOrderNumber = Integer.valueOf(oldOrderNo);
					newOrderNumber = Integer.valueOf(newOrderNo);
					message = studyService.reOrderComprehensionTestQuestion(Integer.valueOf(studyId), oldOrderNumber, newOrderNumber);
				}
			}
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - reOrderComprehensionTestQuestion - ERROR",e);
		}
		logger.info("StudyController - reOrderComprehensionTestQuestion - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/saveConsentInfo.do")
	public void saveConsentInfo(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyController - saveConsentInfo - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		ConsentInfoBo addConsentInfoBo = null;
		ObjectMapper mapper = new ObjectMapper();
		ConsentInfoBo consentInfoBo = null;
		String customStudyId = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			String conInfo = request.getParameter("consentInfo");
			if(null != conInfo){
				 consentInfoBo = mapper.readValue(conInfo, ConsentInfoBo.class);
			}
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(consentInfoBo != null){
					if(consentInfoBo.getStudyId() != null && consentInfoBo.getId() == null){
						int order = studyService.consentInfoOrder(consentInfoBo.getStudyId());
						consentInfoBo.setSequenceNo(order);
					}
					customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
					addConsentInfoBo = studyService.saveOrUpdateConsentInfo(consentInfoBo, sesObj,customStudyId);
					if(addConsentInfoBo != null){
						jsonobject.put(FdahpStudyDesignerConstants.CONSENT_INFO_ID, addConsentInfoBo.getId());
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}
			}
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - saveConsentInfo - ERROR",e);
		}
		logger.info("StudyController - saveConsentInfo - Ends");
	}
	
	/**
	 * @author Ravinder			
	 * @param request
	 * @param response
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/reloadComprehensionQuestionListPage.do")
	public void reloadComprehensionQuestionListPage(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyController - reloadConsentListPage - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		ObjectMapper mapper = new ObjectMapper();
		JSONArray comprehensionJsonArray = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			List<ComprehensionTestQuestionBo> comprehensionTestQuestionList;
			if(sesObj!=null){
				String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isNotEmpty(studyId)){
					comprehensionTestQuestionList = studyService.getComprehensionTestQuestionList(Integer.valueOf(studyId));
					if(comprehensionTestQuestionList!= null && !comprehensionTestQuestionList.isEmpty()){
						comprehensionJsonArray = new JSONArray(mapper.writeValueAsString(comprehensionTestQuestionList));
					}
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
				jsonobject.put("comprehensionTestQuestionList",comprehensionJsonArray);
			}
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - reloadConsentListPage - ERROR",e);
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			if(out != null){
				out.print(jsonobject);
			}
		}
		logger.info("StudyController - reloadConsentListPage - Ends");
		
	}
	
	@RequestMapping("/adminStudies/consentMarkAsCompleted.do")
	public ModelAndView consentMarkAsCompleted(HttpServletRequest request) {
		logger.info("StudyController - consentMarkAsCompleted() - Starts");
		ModelAndView mav = new ModelAndView("redirect:studyList.do");
		ModelMap map = new ModelMap();
		String message = FdahpStudyDesignerConstants.FAILURE;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String customStudyId = "";
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				message = studyService.markAsCompleted(Integer.parseInt(studyId) , FdahpStudyDesignerConstants.CONESENT, sesObj,customStudyId);	
				if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
					map.addAttribute("_S", sessionStudyCount);
					mav = new ModelAndView("redirect:consentReview.do",map);
				}else{
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, FdahpStudyDesignerConstants.UNABLE_TO_MARK_AS_COMPLETE);
					map.addAttribute("_S", sessionStudyCount);
					mav = new ModelAndView("redirect:consentListPage.do",map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - consentMarkAsCompleted() - ERROR", e);
		}
		logger.info("StudyController - consentMarkAsCompleted() - Ends");
		return mav;
	}
	
	@RequestMapping("/adminStudies/consentReviewMarkAsCompleted.do")
	public ModelAndView consentReviewMarkAsCompleted(HttpServletRequest request) {
		logger.info("StudyController - consentReviewMarkAsCompleted() - Starts");
		ModelAndView mav = new ModelAndView("redirect:studyList.do");
		ModelMap map = new ModelMap();
		String message = FdahpStudyDesignerConstants.FAILURE;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String customStudyId = "";
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				message = studyService.markAsCompleted(Integer.parseInt(studyId) , FdahpStudyDesignerConstants.CONESENT_REVIEW, sesObj,customStudyId);	
				map.addAttribute("_S", sessionStudyCount);
				if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
					mav = new ModelAndView("redirect:viewStudyQuestionnaires.do", map);
				}else{
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, FdahpStudyDesignerConstants.UNABLE_TO_MARK_AS_COMPLETE);
					mav = new ModelAndView("redirect:consentReview.do", map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - consentReviewMarkAsCompleted() - ERROR", e);
		}
		logger.info("StudyController - consentReviewMarkAsCompleted() - Ends");
		return mav;
	}
	
	/*------------------------------------Added By Vivek Start---------------------------------------------------*/
	/**
	 * view Eligibility page
	 * @author Vivek 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/viewStudyEligibilty.do")
	public ModelAndView viewStudyEligibilty(HttpServletRequest request) {
		logger.info("StudyController - viewStudyEligibilty - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		StudyBo studyBo = null;
		String sucMsg = "";
		String errMsg = "";
		EligibilityBo eligibilityBo = null;
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)) {
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				
				if (StringUtils.isEmpty(studyId)) {
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "0" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				} 
				String permission = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION);
				if (StringUtils.isNotEmpty(studyId)) {
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					eligibilityBo = studyService.getStudyEligibiltyByStudyId(studyId);
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					if(eligibilityBo == null){
						eligibilityBo = new EligibilityBo();
						eligibilityBo.setStudyId(Integer.parseInt(studyId));
						eligibilityBo.setInstructionalText(FdahpStudyDesignerConstants.ELIGIBILITY_TOKEN_TEXT_DEFAULT);
					}
					map.addAttribute("eligibility", eligibilityBo);
					map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
					map.addAttribute("_S", sessionStudyCount);
					mav = new ModelAndView("studyEligibiltyPage", map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - viewStudyEligibilty - ERROR", e);
		}
		logger.info("StudyController - viewStudyEligibilty - Ends");
		return mav;
	}
	
	/**
	 * save or update Study Eligibility
	 * @author Vivek 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @param eligibilityBo , {@link EligibilityBo}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/saveOrUpdateStudyEligibilty.do")
	public ModelAndView saveOrUpdateStudyEligibilty(HttpServletRequest request, EligibilityBo eligibilityBo) {
		logger.info("StudyController - saveOrUpdateStudyEligibilty - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		String result = FdahpStudyDesignerConstants.FAILURE;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String customStudyId = "";
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)) {
				if (eligibilityBo != null) {
					if (eligibilityBo.getId() != null) {
						eligibilityBo.setModifiedBy(sesObj.getUserId());
						eligibilityBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					} else {
						eligibilityBo.setCreatedBy(sesObj.getUserId());
						eligibilityBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
					result = studyService.saveOrUpdateStudyEligibilty(eligibilityBo, sesObj,customStudyId);
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID, eligibilityBo.getStudyId()+"");
				}
				map.addAttribute("_S", sessionStudyCount);
				if(FdahpStudyDesignerConstants.SUCCESS.equals(result)) {
					if(eligibilityBo != null && ("save").equals(eligibilityBo.getActionType())){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
						mav = new ModelAndView("redirect:viewStudyEligibilty.do", map);
					}else{
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
						mav = new ModelAndView("redirect:consentListPage.do", map);
					}	
				}else {
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Error in set Eligibility.");
					mav = new ModelAndView("redirect:viewStudyEligibilty.do", map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - saveOrUpdateStudyEligibilty - ERROR", e);
		}
		logger.info("StudyController - saveOrUpdateStudyEligibilty - Ends");
		return mav;
	}
	/*------------------------------------Added By Vivek End---------------------------------------------------*/
	
	/*----------------------------------------added by MOHAN T starts----------------------------------------*/
	/**
	 * @author Mohan
	 * @param request
	 * @return ModelAndView
	 * 
	 * Description : This method is used to get the details of consent review and e-consent by studyId
	 */
	@RequestMapping("/adminStudies/consentReview.do")
	public ModelAndView getConsentReviewAndEConsentPage(HttpServletRequest request){
		logger.info("INFO: StudyController - getConsentReviewAndEConsentPage() :: Starts");
		ModelAndView mav = new ModelAndView(FdahpStudyDesignerConstants.CONSENT_INFO_PAGE);
		ModelMap map = new ModelMap();
		SessionObject sesObj = null;
		String studyId = "";
		List<ConsentInfoBo> consentInfoBoList = null;
		StudyBo studyBo = null;
		ConsentBo consentBo = null;
		String consentId = "";
		String sucMsg = "";
		String errMsg = "";
		String consentStudyId = "";
		try{
			sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if( sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				
				if( request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID) != null){
					studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID).toString();
				}
				
				if( request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID) != null){
					consentId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID).toString();
				}
				
				if(StringUtils.isEmpty(studyId)){
					studyId = StringUtils.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				/*if(StringUtils.isEmpty(consentId)){
					consentId = StringUtils.isEmpty(request.getParameter(FdahpStudyDesignerConstants.CONSENT_ID))?"":request.getParameter(FdahpStudyDesignerConstants.CONSENT_ID);
				}*/
				//Added for live version Start
				String isLive = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_LIVE);
				if(StringUtils.isNotEmpty(isLive) && isLive.equalsIgnoreCase(sessionStudyCount+FdahpStudyDesignerConstants.YES)){
					consentStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_STUDY_ID);
				}
				//Added for live version End
				if(StringUtils.isNotEmpty(studyId)){
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID, studyId);
					if(StringUtils.isNotEmpty(consentStudyId)){
						consentInfoBoList = studyService.getConsentInfoDetailsListByStudyId(consentStudyId);
					}else{
						consentInfoBoList = studyService.getConsentInfoDetailsListByStudyId(studyId);
					}	
					if( null != consentInfoBoList && !consentInfoBoList.isEmpty()){
						map.addAttribute(FdahpStudyDesignerConstants.CONSENT_INFO_LIST, consentInfoBoList);
					}else{
						map.addAttribute(FdahpStudyDesignerConstants.CONSENT_INFO_LIST, "");
					}
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					
					//get consentId if exists for studyId
					consentBo = studyService.getConsentDetailsByStudyId(studyId);
					if( consentBo != null){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID, consentBo.getId());
						map.addAttribute(FdahpStudyDesignerConstants.CONSENT_ID, consentBo.getId());
					}
					
					String permission = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION);
					if(StringUtils.isNotEmpty(permission) && ("view").equals(permission)){
						map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, "view");
					}else{
						map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, "addEdit");
					}
				}
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
				//map.addAttribute(FdahpStudyDesignerConstants.CONSENT_ID, consentId);
				map.addAttribute("consentBo", consentBo);
				map.addAttribute("_S", sessionStudyCount);
				mav = new ModelAndView("consentReviewAndEConsentPage", map);
			}
		}catch(Exception e){
			logger.error("StudyController - getConsentReviewAndEConsentPage() - ERROR ", e);
		}
		logger.info("INFO: StudyController - getConsentReviewAndEConsentPage() :: Ends");
		return mav;
	}
	
	
	/**
	 * @author Mohan
	 * @param request
	 * @param response
	 * @param consentInfoBo
	 * @return ModelAndView
	 * 
	 * Description : This method is used to save the consent eview and E-consent info for study 
	 */
	@RequestMapping("/adminStudies/saveConsentReviewAndEConsentInfo.do")
	public void saveConsentReviewAndEConsentInfo(HttpServletRequest request, HttpServletResponse response){
		logger.info("INFO: StudyController - saveConsentReviewAndEConsentInfo() :: Starts");
		ConsentBo consentBo = null;
		String consentInfoParamName = "";
		ObjectMapper mapper = new ObjectMapper();
		JSONObject jsonobj = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		String studyId = "";
		String consentId = "";
		String customStudyId = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				consentInfoParamName = request.getParameter("consentInfo");
				if(StringUtils.isNotEmpty(consentInfoParamName)){
					consentBo = mapper.readValue(consentInfoParamName, ConsentBo.class);
					if(consentBo != null){
						customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
						consentBo = studyService.saveOrCompleteConsentReviewDetails(consentBo, sesObj,customStudyId);
						studyId = StringUtils.isEmpty(String.valueOf(consentBo.getStudyId()))?"":String.valueOf(consentBo.getStudyId());
						consentId = StringUtils.isEmpty(String.valueOf(consentBo.getId()))?"":String.valueOf(consentBo.getId());
						
						//setting consentId in requestSession
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID);
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CONSENT_ID, consentBo.getId());
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}
			}
			jsonobj.put(FdahpStudyDesignerConstants.MESSAGE, message);
			jsonobj.put(FdahpStudyDesignerConstants.STUDY_ID, studyId);
			jsonobj.put(FdahpStudyDesignerConstants.CONSENT_ID, consentId);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobj);
		}catch(Exception e){
			logger.error("StudyController - saveConsentReviewAndEConsentInfo() - ERROR ", e);
		}
		logger.info("INFO: StudyController - saveConsentReviewAndEConsentInfo() :: Ends");
	}
	
	/*----------------------------------------added by MOHAN T ends----------------------------------------*/
	
	/**
	 * @author Pradyumn			
	 * @param request
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/getResourceList.do")
	public ModelAndView getResourceList(HttpServletRequest request){
		logger.info("StudyController - getResourceList() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		String sucMsg = "";
		String errMsg = "";
		String resourceErrMsg = "";
		List<ResourceBO> resourceBOList = null;
		List<ResourceBO> resourcesSavedList = null;
		ResourceBO studyProtocolResourceBO = null;
		StudyBo studyBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				if(null!= request.getSession().getAttribute(sessionStudyCount+"resourceErrMsg")){
					resourceErrMsg = (String) request.getSession().getAttribute(sessionStudyCount+"resourceErrMsg");
					map.addAttribute("resourceErrMsg", resourceErrMsg);
					request.getSession().removeAttribute(sessionStudyCount+"resourceErrMsg");
				}
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				String permission = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION);
				if(StringUtils.isNotEmpty(studyId)){
					resourceBOList = studyService.getResourceList(Integer.valueOf(studyId));
					for(ResourceBO rBO:resourceBOList){
						if(rBO.isStudyProtocol()){
							studyProtocolResourceBO = new ResourceBO();
							studyProtocolResourceBO.setId(rBO.getId());
						}
					}
					resourcesSavedList = studyService.resourcesSaved(Integer.valueOf(studyId));
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					map.addAttribute("resourceBOList", resourceBOList);
					map.addAttribute("resourcesSavedList", resourcesSavedList);
					map.addAttribute("studyProtocolResourceBO", studyProtocolResourceBO);
				}
				map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
				map.addAttribute("_S", sessionStudyCount);
				mav = new ModelAndView("resourceListPage",map);
			}
		}catch(Exception e){
			logger.error("StudyController - getResourceList() - ERROR",e);
		}
		logger.info("StudyController - getResourceList() - Ends");
		return mav;
		
	}
	
	/**
	 * @author Pradyumn
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/deleteResourceInfo",method = RequestMethod.POST)
	public void deleteResourceInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyController - deleteResourceInfo() - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		List<ResourceBO> resourcesSavedList = null;
		String customStudyId = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String resourceInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.RESOURCE_INFO_ID))?"":request.getParameter(FdahpStudyDesignerConstants.RESOURCE_INFO_ID);
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				if(!resourceInfoId.isEmpty()){
					message = studyService.deleteResourceInfo(Integer.valueOf(resourceInfoId),sesObj,customStudyId);
				}
				resourcesSavedList = studyService.resourcesSaved(Integer.valueOf(studyId));
				if(!resourcesSavedList.isEmpty()){
					jsonobject.put("resourceSaved", true);
				}else{
					jsonobject.put("resourceSaved", false);
				}
			}
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - deleteResourceInfo() - ERROR",e);
		}
		logger.info("StudyController - deleteConsentInfo() - Ends");
	}
	
	/**
	 * add or edit Study Resource
	 * @author Pradyumn 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/addOrEditResource.do")
	public ModelAndView addOrEditResource(HttpServletRequest request) {
		logger.info("StudyController - addOrEditResource() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		ResourceBO resourceBO = null;
		StudyBo studyBo = null;
		String sucMsg = "";
		String errMsg = "";
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				String resourceInfoId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.RESOURCE_INFO_ID);
				if(StringUtils.isEmpty(resourceInfoId)){
					resourceInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.RESOURCE_INFO_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.RESOURCE_INFO_ID);
				}
				String studyProtocol = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL);
				if(StringUtils.isEmpty(studyProtocol)){
					studyProtocol = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL)) ? "" : request.getParameter(FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL);
				}
				String action = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_ON);
				if(StringUtils.isEmpty(action)){
					action = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.ACTION_ON)) ? "" : request.getParameter(FdahpStudyDesignerConstants.ACTION_ON);
				}
				map.addAttribute("_S", sessionStudyCount);
				if(!FdahpStudyDesignerUtil.isEmpty(action)){
					if(!("").equals(resourceInfoId)){
						resourceBO = studyService.getResourceInfo(Integer.parseInt(resourceInfoId));
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.RESOURCE_INFO_ID);
					}
					if(null != studyProtocol && !("").equals(studyProtocol) && (FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL).equalsIgnoreCase(studyProtocol)){
						map.addAttribute(FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL, FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL);
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL);
					}
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					map.addAttribute("resourceBO", resourceBO);
					map.addAttribute(FdahpStudyDesignerConstants.ACTION_ON, action);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_ON);
					mav = new ModelAndView("addOrEditResourcePage",map);
				}else{
					mav = new ModelAndView("redirect:getResourceList.do", map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - addOrEditResource() - ERROR", e);
		}
		logger.info("StudyController - addOrEditResource() - Ends");
		return mav;
	}
	
	/**
	 * save or update Study Resource
	 * @author Pradyumn 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @param resourceBO , {@link ResourceBO}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/saveOrUpdateResource.do")
	public ModelAndView saveOrUpdateResource(HttpServletRequest request, ResourceBO resourceBO) {
		logger.info("StudyController - saveOrUpdateResource() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		Integer resourseId = 0;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		ModelMap map = new ModelMap();
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String textOrPdfParam = FdahpStudyDesignerUtil.isEmpty(request.getParameter("textOrPdfParam"))?"":request.getParameter("textOrPdfParam");
				String resourceVisibilityParam = FdahpStudyDesignerUtil.isEmpty(request.getParameter("resourceVisibilityParam"))?"":request.getParameter("resourceVisibilityParam");
				String buttonText = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT))?"":request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT);
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				String studyProtocol = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL)) ? "" : request.getParameter(FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL);
				String action = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.ACTION_ON)) ? "" : request.getParameter(FdahpStudyDesignerConstants.ACTION_ON);
				String resourceTypeParm = FdahpStudyDesignerUtil.isEmpty(request.getParameter("resourceTypeParm"))?"":request.getParameter("resourceTypeParm");
				if (resourceBO != null) {
					if(!("").equals(buttonText)){
						if(("save").equalsIgnoreCase(buttonText)){
							resourceBO.setAction(false);
						}else if(("done").equalsIgnoreCase(buttonText)){
							resourceBO.setAction(true);
						}
					}
					if(!("").equals(studyProtocol) && (FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL).equalsIgnoreCase(studyProtocol)){
						resourceBO.setStudyProtocol(true);
					}else{
						resourceBO.setStudyProtocol(false);
					}
					resourceBO.setStudyId(Integer.parseInt(studyId));
					resourceBO.setTextOrPdf(("0").equals(textOrPdfParam) ? false : true);
					resourceBO.setResourceVisibility(("0").equals(resourceVisibilityParam) ? false : true);
					if(!resourceBO.isResourceVisibility()){
						resourceBO.setResourceType("0".equals(resourceTypeParm) ? false : true);
					}else{
						resourceBO.setResourceType(false);
					}
					resourseId = studyService.saveOrUpdateResource(resourceBO, sesObj);	
				}
				if(!resourseId.equals(0)){
					if(resourceBO != null && resourceBO.getId() == null){
						if(("save").equalsIgnoreCase(buttonText)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, "Resource successfully added.");
						}
					}else{
						if(("save").equalsIgnoreCase(buttonText)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, "Resource successfully updated.");
						}
					}
				}else{
					if(resourceBO != null && resourceBO.getId() == null){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Failed to add resource.");
					}else{
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Failed to update resource.");
					}
				}
				map.addAttribute("_S", sessionStudyCount);
				if(("save").equalsIgnoreCase(buttonText)){
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_ON, action);
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.RESOURCE_INFO_ID, resourseId+"");
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_STUDY_PROTOCOL, studyProtocol+"");
					mav = new ModelAndView("redirect:addOrEditResource.do", map);
				}else{
					mav = new ModelAndView("redirect:getResourceList.do", map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - saveOrUpdateResource() - ERROR", e);
		}
		logger.info("StudyController - saveOrUpdateResource() - Ends");
		return mav;
	}
	
	/**
	 * Set Mark as completed
	 * @author Pradyumn 
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/resourceMarkAsCompleted.do")
	public ModelAndView resourceMarkAsCompleted(HttpServletRequest request) {
		logger.info("StudyController - saveOrUpdateResource() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String customStudyId = "";
		List<ResourceBO> resourceList;
		Boolean isAnchorDateExistsForStudy;
		ModelMap map = new ModelMap();
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				resourceList = studyService.resourcesWithAnchorDate(Integer.parseInt(studyId));
				if(resourceList!=null && !resourceList.isEmpty()){
					isAnchorDateExistsForStudy = studyQuestionnaireService.isAnchorDateExistsForStudy(Integer.parseInt(studyId));
					if(isAnchorDateExistsForStudy){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}else{
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
				map.addAttribute("_S", sessionStudyCount);
				if(message.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS)){
					message = studyService.markAsCompleted(Integer.parseInt(studyId), FdahpStudyDesignerConstants.RESOURCE, sesObj,customStudyId);	
					if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
						mav = new ModelAndView("redirect:viewStudyNotificationList.do", map);
					}else{
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, FdahpStudyDesignerConstants.UNABLE_TO_MARK_AS_COMPLETE);
						mav = new ModelAndView("redirect:getResourceList.do", map);
					}
				}else{
					request.getSession().setAttribute(sessionStudyCount+"resourceErrMsg", FdahpStudyDesignerConstants.RESOURCE_ANCHOR_ERROR_MSG);
					mav = new ModelAndView("redirect:getResourceList.do", map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - resourceMarkAsCompleted() - ERROR", e);
		}
		logger.info("StudyController - resourceMarkAsCompleted() - Ends");
		return mav;
	}
	
	 /*Study notification starts*/
	
	@RequestMapping("/adminStudies/viewStudyNotificationList.do")
	public ModelAndView viewStudyNotificationList(HttpServletRequest request){
		logger.info("StudyController - viewNotificationList() - Starts");
		ModelMap map = new ModelMap();
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		String sucMsg = "";
		String errMsg = "";
		List<NotificationBO> notificationList = null;
		List<NotificationBO> notificationSavedList = null;
		StudyBo studyBo = null;
		StudyBo studyLive = null;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sessionObject != null && sessionObject.getStudySession() != null && sessionObject.getStudySession().contains(sessionStudyCount)) {
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				String type = FdahpStudyDesignerConstants.STUDYLEVEL;
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				String permission = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				if(StringUtils.isNotEmpty(studyId)){
					notificationList = notificationService.getNotificationList(Integer.valueOf(studyId) ,type);
					for (NotificationBO notification : notificationList) {
						if(!notification.isNotificationSent() && notification.getNotificationScheduleType().equals(FdahpStudyDesignerConstants.NOTIFICATION_NOTIMMEDIATE)){
							notification.setCheckNotificationSendingStatus("Not sent");
						}else if(!notification.isNotificationSent() && notification.getNotificationScheduleType().equals(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE)){
							notification.setCheckNotificationSendingStatus("Sending");
						}else if(notification.isNotificationSent()){
							notification.setCheckNotificationSendingStatus("Sent");
						}
						
					}
					studyBo = studyService.getStudyById(studyId, sessionObject.getUserId());
					if(studyBo != null && FdahpStudyDesignerConstants.STUDY_ACTIVE.equals(studyBo.getStatus())){
						studyLive = studyService.getStudyLiveStatusByCustomId(studyBo.getCustomStudyId());
					} else {
						studyLive = studyBo;
					}
					notificationSavedList = studyService.getSavedNotification(Integer.valueOf(studyId));
					map.addAttribute("notificationList", notificationList);
					map.addAttribute("studyLive", studyLive);
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					map.addAttribute("notificationSavedList", notificationSavedList);
				}
				map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
				map.addAttribute("_S", sessionStudyCount);
				mav = new ModelAndView("studyNotificationList", map);
			}
		}catch(Exception e){
			logger.error("StudyController - viewStudyNotificationList() - ERROR ", e);
		}
		logger.info("StudyController - viewStudyNotificationList() - ends");
		return mav;
	}
	
	@RequestMapping("/adminStudies/getStudyNotification.do")
	public ModelAndView getStudyNotification(HttpServletRequest request){
		logger.info("StudyController - getStudyNotification - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		NotificationBO notificationBO = null;
		List<NotificationHistoryBO> notificationHistoryNoDateTime = null;
		StudyBo studyBo = null;
		String sucMsg = "";
		String errMsg = "";
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sessionObject != null && sessionObject.getStudySession() != null && sessionObject.getStudySession().contains(sessionStudyCount)) {
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				String notificationId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.NOTIFICATIONID);
				if(StringUtils.isEmpty(notificationId)){
					notificationId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID))? "" : request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID);
				}
				String chkRefreshflag = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CHKREFRESHFLAG);
				if(StringUtils.isEmpty(chkRefreshflag)){
					chkRefreshflag = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.CHKREFRESHFLAG))? "" : request.getParameter(FdahpStudyDesignerConstants.CHKREFRESHFLAG);
				}
				String actionType = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_TYPE);
				if(StringUtils.isEmpty(actionType)){
					actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.ACTION_TYPE))? "" : request.getParameter(FdahpStudyDesignerConstants.ACTION_TYPE);
				}
				String notificationText = FdahpStudyDesignerUtil.isEmpty(request.getParameter("notificationText"))?"":request.getParameter("notificationText");
				map.addAttribute("_S", sessionStudyCount);
				if(!"".equals(chkRefreshflag)){
					String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					}
					studyBo = studyService.getStudyById(studyId, sessionObject.getUserId());
					if(!"".equals(notificationId)){
						notificationBO = notificationService.getNotification(Integer.parseInt(notificationId));
						notificationHistoryNoDateTime = notificationService.getNotificationHistoryListNoDateTime(Integer.parseInt(notificationId));
						if("edit".equals(actionType)){
							notificationBO.setActionPage("edit");
						}else if(FdahpStudyDesignerConstants.ADDORCOPY.equals(actionType)){
							notificationBO.setActionPage(FdahpStudyDesignerConstants.ADDORCOPY);
						}else if(FdahpStudyDesignerConstants.RESEND.equals(actionType)){
							if(notificationBO.isNotificationSent()){
								notificationBO.setScheduleDate("");
								notificationBO.setScheduleTime("");
							}
							notificationBO.setActionPage(FdahpStudyDesignerConstants.RESEND);
						}else{
							notificationBO.setActionPage("view");
						}
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.NOTIFICATIONID);
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_TYPE);
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CHKREFRESHFLAG);
					}else if(!"".equals(notificationText) && "".equals(notificationId)){
						notificationBO = new NotificationBO();
						notificationBO.setNotificationText(notificationText);
						notificationBO.setActionPage(FdahpStudyDesignerConstants.ADDORCOPY);
					}else if("".equals(notificationText) && "".equals(notificationId)){
						notificationBO = new NotificationBO();
						notificationBO.setActionPage(FdahpStudyDesignerConstants.ADDORCOPY);
					}
					map.addAttribute("notificationBO", notificationBO);
					map.addAttribute("notificationHistoryNoDateTime", notificationHistoryNoDateTime);
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					mav = new ModelAndView("addOrEditStudyNotification",map);
				}
				else {
					mav = new ModelAndView("redirect:viewStudyNotificationList.do", map);
				}
			}
		}catch(Exception e){
			logger.error("StudyController - getStudyNotification - ERROR", e);

		}
		logger.info("StudyController - getStudyNotification - Ends");
		return mav;
	}
	
	
	
	@RequestMapping("/adminStudies/saveOrUpdateStudyNotification.do")
	public ModelAndView saveOrUpdateStudyNotification(HttpServletRequest request, NotificationBO notificationBO){
		logger.info("StudyController - saveOrUpdateStudyNotification - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		Integer notificationId = 0;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String customStudyId = "";
		
		try{
			HttpSession session = request.getSession();
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			SessionObject sessionObject = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sessionObject != null && sessionObject.getStudySession() != null && sessionObject.getStudySession().contains(sessionStudyCount)) {
				String notificationType = "Study level";
				String currentDateTime = FdahpStudyDesignerUtil.isEmpty(request.getParameter("currentDateTime"))?"":request.getParameter("currentDateTime");
				String buttonType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("buttonType"))?"":request.getParameter("buttonType");
				String actionPage = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_PAGE);
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				if(StringUtils.isEmpty(actionPage)){
					actionPage = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.ACTION_PAGE))? "" : request.getParameter(FdahpStudyDesignerConstants.ACTION_PAGE);
				}
				if(notificationBO!=null){
					if(!"".equals(buttonType)){
						if("save".equalsIgnoreCase(buttonType)){
							notificationBO.setNotificationDone(false);
							notificationBO.setNotificationAction(false);
						}else if("done".equalsIgnoreCase(buttonType) || FdahpStudyDesignerConstants.RESEND.equalsIgnoreCase(buttonType)){
							notificationBO.setNotificationDone(true);
							notificationBO.setNotificationAction(true);
						}
					}
					if(FdahpStudyDesignerConstants.NOTIFICATION_NOTIMMEDIATE.equals(currentDateTime)){
						notificationBO.setScheduleDate(FdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleDate())?String.valueOf(FdahpStudyDesignerUtil.getFormattedDate(notificationBO.getScheduleDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.DB_SDF_DATE)):"");
						notificationBO.setScheduleTime(FdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleTime())?String.valueOf(FdahpStudyDesignerUtil.getFormattedDate(notificationBO.getScheduleTime(), FdahpStudyDesignerConstants.SDF_TIME, FdahpStudyDesignerConstants.DB_SDF_TIME)):"");
						notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_NOTIMMEDIATE);
					} else if(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE.equals(currentDateTime)){
						notificationBO.setScheduleDate(FdahpStudyDesignerUtil.getCurrentDate());
						notificationBO.setScheduleTime(FdahpStudyDesignerUtil.getCurrentTime());
						notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
					} else{
						notificationBO.setScheduleDate("");
						notificationBO.setScheduleTime("");
						notificationBO.setNotificationScheduleType("0");
					}
					String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					}
					if(StringUtils.isNotEmpty(studyId)){
						StudyBo studyBo  = studyService.getStudyById(studyId, 0);
						if(studyBo!=null){
							notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
							notificationBO.setStudyId(Integer.valueOf(studyId));
						}
					}
					if(notificationBO.getNotificationId() == null){
						notificationBO.setCreatedBy(sessionObject.getUserId());
						notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						notificationBO.setModifiedBy(sessionObject.getUserId());
						notificationBO.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					notificationId = notificationService.saveOrUpdateOrResendNotification(notificationBO, notificationType, buttonType, sessionObject,customStudyId);
				}
				if(!notificationId.equals(0)){
					if(notificationBO.getNotificationId() == null){
						if("save".equalsIgnoreCase(buttonType)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get("save.notification.success.message"));
						}
					}else{
						if("save".equalsIgnoreCase(buttonType)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
						}else if(FdahpStudyDesignerConstants.RESEND.equalsIgnoreCase(buttonType)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get("resend.notification.success.message"));
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get("update.notification.success.message"));
						}
					}
				}else{
					if("save".equalsIgnoreCase(buttonType) && notificationBO.getNotificationId() == null){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, propMap.get("save.notification.error.message"));
					}else if(FdahpStudyDesignerConstants.RESEND.equalsIgnoreCase(buttonType)){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, propMap.get("resend.notification.error.message"));
					}else{
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, propMap.get("update.notification.error.message"));
					}
				}
				map.addAttribute("_S", sessionStudyCount);
				if("save".equalsIgnoreCase(buttonType) && !FdahpStudyDesignerConstants.ADDORCOPY.equals(actionPage)){
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.NOTIFICATIONID, notificationId+"");
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CHKREFRESHFLAG, "Y"+"");
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_TYPE, "edit"+"");
					mav = new ModelAndView("redirect:getStudyNotification.do",map);
				}else if("save".equalsIgnoreCase(buttonType) && FdahpStudyDesignerConstants.ADDORCOPY.equals(actionPage)){
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.NOTIFICATIONID, notificationId+"");
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CHKREFRESHFLAG, "Y"+"");
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_TYPE, FdahpStudyDesignerConstants.ADDORCOPY+"");
					mav = new ModelAndView("redirect:getStudyNotification.do",map);
				}else{
					mav = new ModelAndView("redirect:/adminStudies/viewStudyNotificationList.do", map);
				}
			}
		}catch(Exception e){
			logger.error("StudyController - saveOrUpdateStudyNotification - ERROR", e);

		}
		logger.info("StudyController - saveOrUpdateStudyNotification - Ends");
		return mav;
	}
	
	@RequestMapping("/adminStudies/deleteStudyNotification.do")
	public ModelAndView deleteStudyNotification(HttpServletRequest request){
		logger.info("StudyController - deleteStudyNotification - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		ModelMap map = new ModelMap();
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sessionObject != null && sessionObject.getStudySession() != null && sessionObject.getStudySession().contains(sessionStudyCount)) {
				String notificationId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID))?"":request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID);
				if(null != notificationId){
						String notificationType = FdahpStudyDesignerConstants.STUDYLEVEL;
						message = notificationService.deleteNotification(Integer.parseInt(notificationId), sessionObject, notificationType);
						if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get("delete.notification.success.message"));
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, propMap.get("delete.notification.error.message"));
						}
						map.addAttribute("_S", sessionStudyCount);
						mav = new ModelAndView("redirect:/adminStudies/viewStudyNotificationList.do", map);
				}
			}
		}catch(Exception e){
			logger.error("StudyController - deleteStudyNotification - ERROR", e);

		}
		return mav;
	}
	
	@RequestMapping("/adminStudies/notificationMarkAsCompleted.do")
	public ModelAndView notificationMarkAsCompleted(HttpServletRequest request) {
		logger.info("StudyController - notificationMarkAsCompleted() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String message = FdahpStudyDesignerConstants.FAILURE;
		String customStudyId = "";
		ModelMap map = new ModelMap();
		try {
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)) {
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				String markCompleted = FdahpStudyDesignerConstants.NOTIFICATION;
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				message = studyService.markAsCompleted(Integer.parseInt(studyId) , markCompleted, sesObj,customStudyId);
				map.addAttribute("_S", sessionStudyCount);
				if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
					mav = new ModelAndView("redirect:getChecklist.do", map);
				}else{
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, FdahpStudyDesignerConstants.UNABLE_TO_MARK_AS_COMPLETE);
					mav = new ModelAndView("redirect:viewStudyNotificationList.do", map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - notificationMarkAsCompleted() - ERROR", e);
		}
		logger.info("StudyController - notificationMarkAsCompleted() - Ends");
		return mav;
	}
	
	/*Study notification ends*/
	
	/*Study CheckList Starts*/
	/**
	 * @author Pradyumn			
	 * @param request
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/getChecklist.do")
	public ModelAndView getChecklist(HttpServletRequest request){
		logger.info("StudyController - getChecklist() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		String sucMsg = "";
		String errMsg = "";
		StudyBo studyBo = null;
		Checklist checklist = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)) {
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				}
				map.addAttribute("_S", sessionStudyCount);
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				String permission = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.PERMISSION);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					checklist = studyService.getchecklistInfo(Integer.valueOf(studyId));
				}
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
				map.addAttribute("checklist", checklist);
				map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
				mav = new ModelAndView("checklist",map);
			}
		}catch(Exception e){
			logger.error("StudyController - getChecklist() - ERROR",e);
		}
		logger.info("StudyController - getChecklist() - Ends");
		return mav;
		
	}
	
	/**
	 * Save or Done Checklist
	 * @author Pradyumn 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @param checklist , {@link Checklist}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/saveOrDoneChecklist.do")
	public ModelAndView saveOrDoneChecklist(HttpServletRequest request,Checklist checklist) {
		logger.info("StudyController - saveOrDoneChecklist() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		Integer checklistId = 0;
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		String customStudyId = "";
		ModelMap map = new ModelMap();
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)) {
				String actionBut = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionBut")) ? "" : request.getParameter("actionBut");
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				checklist.setStudyId(Integer.valueOf(studyId));
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				checklistId = studyService.saveOrDoneChecklist(checklist,actionBut,sesObj,customStudyId);
				map.addAttribute("_S", sessionStudyCount);
				if(!checklistId.equals(0)){
					if(checklist.getChecklistId() == null){
						if(("save").equalsIgnoreCase(actionBut)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, "Checklist successfully added.");
						}
					}else{
						if(("save").equalsIgnoreCase(actionBut)){
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.SAVE_STUDY_SUCCESS_MESSAGE));
						}else{
							request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, "Checklist successfully updated.");
						}
					}
				}else{
					if(checklist.getChecklistId() == null){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Failed to add checklist.");
					}else{
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Failed to update checklist.");
					}
				}
				mav = new ModelAndView("redirect:getChecklist.do", map);
			}
		} catch (Exception e) {
			logger.error("StudyController - saveOrDoneChecklist() - ERROR", e);
		}
		logger.info("StudyController - saveOrDoneChecklist() - Ends");
		return mav;
	}
	
	/*Study checkList ends*/
	/**
     * @author Ronalin
	 * Getting Actions
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/actionList.do")
	public ModelAndView actionList(HttpServletRequest request){
		logger.info("StudyController - actionList - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		String errMsg = "";
		StudyBo studyBo = null;
		StudyBo liveStudyBo = null;
		String actionSucMsg = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)) {
			if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_SUC_MSG)){
				actionSucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_SUC_MSG);
				map.addAttribute(FdahpStudyDesignerConstants.ACTION_SUC_MSG, actionSucMsg);
				request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_SUC_MSG);
			}
			if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
			}
				String  studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				if(FdahpStudyDesignerUtil.isEmpty(studyId)){
					studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				}
				String permission = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.PERMISSION);
				if(FdahpStudyDesignerUtil.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					liveStudyBo = studyService.getStudyLiveStatusByCustomId(studyBo.getCustomStudyId());
					
					map.addAttribute("_S", sessionStudyCount);
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO,studyBo);
					map.addAttribute(FdahpStudyDesignerConstants.PERMISSION, permission);
					map.addAttribute("liveStudyBo", liveStudyBo);
					mav = new ModelAndView("actionList", map);
				}else{
					return new ModelAndView("redirect:/adminStudies/studyList.do");
				}
			}
		}catch(Exception e){
			logger.error("StudyController - actionList - ERROR",e);
		}
		logger.info("StudyController - actionList - Ends");
		return mav;
	}
	
	/** 
	  * @author Ronalin
	  * validating particular action should be update for each study or not
	  * @param request , {@link HttpServletRequest}
	  * @param response , {@link HttpServletResponse}
	  * @throws IOException
	  * @return void
	  */
		@RequestMapping(value="/adminStudies/validateStudyAction.do",  method = RequestMethod.POST)
		public void validateStudyAction(HttpServletRequest request, HttpServletResponse response) throws IOException{
			logger.info("StudyActiveTasksController - validateStudyAction() - Starts ");
			JSONObject jsonobject = new JSONObject();
			PrintWriter out;
			String message = FdahpStudyDesignerConstants.FAILURE;
			Checklist checklist = null;
			String checkListMessage = "No";
			String checkFailureMessage = "";
			try{
				HttpSession session = request.getSession();
				SessionObject userSession = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
				if(userSession != null && userSession.getStudySession() != null && userSession.getStudySession().contains(sessionStudyCount)) {
					String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					}
					String buttonText = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT))?"":request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT);
					if(StringUtils.isNotEmpty(buttonText)){
						//validation and success/error message should send to actionListPAge
						if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH) || buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES) || buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PUBLISH))
						   message = studyService.validateStudyAction(studyId, buttonText);
						else
						   message = FdahpStudyDesignerConstants.SUCCESS; 
						checklist = studyService.getchecklistInfo(Integer.valueOf(studyId));
						if(checklist!=null){
							if((checklist.isCheckbox1() && checklist.isCheckbox2()) && (checklist.isCheckbox3() && checklist.isCheckbox4())){
									checkListMessage = "Yes";
							}else{
								checkListMessage = "No";
							}
							if(checkListMessage.equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
								if((checklist.isCheckbox3() && checklist.isCheckbox4()) && (checklist.isCheckbox5() && checklist.isCheckbox6())){
									checkListMessage = "Yes";
							    }else{
								checkListMessage = "No";
							    }
							}
							if(checkListMessage.equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
								if((checklist.isCheckbox5() && checklist.isCheckbox6()) && (checklist.isCheckbox7() && checklist.isCheckbox8())){
									checkListMessage = "Yes";
								}else{
									checkListMessage = "No";
								}
							}
							if(checkListMessage.equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
								if(checklist.isCheckbox9() && checklist.isCheckbox10()){
								    checkListMessage = "Yes";
								}else{
									checkListMessage = "No";
								}
							}
						}
						if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH))
						    checkFailureMessage = FdahpStudyDesignerConstants.LUNCH_CHECKLIST_ERROR_MSG;
						else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES))
							checkFailureMessage = FdahpStudyDesignerConstants.PUBLISH_UPDATE_CHECKLIST_ERROR_MSG;
						else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_RESUME))
							checkFailureMessage = FdahpStudyDesignerConstants.RESUME_CHECKLIST_ERROR_MSG;
						else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PUBLISH))
							checkListMessage = "Yes";
					}
					
				}
			}catch (Exception e) {
				logger.error("StudyActiveTasksController - validateStudyAction() - ERROR ", e);
			}
			logger.info("StudyActiveTasksController - validateStudyAction() - Ends ");
			jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
			jsonobject.put("checkListMessage", checkListMessage);
			jsonobject.put("checkFailureMessage", checkFailureMessage);
			response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
			out = response.getWriter();
			out.print(jsonobject);
		}
		
		@RequestMapping("/adminStudies/updateStudyAction.do")
		public ModelAndView updateStudyActionOnAction(HttpServletRequest request) {
			logger.info("StudyController - updateStudyActionOnAction() - Starts");
			ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
			ModelMap map = new ModelMap();
			Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
			String message = FdahpStudyDesignerConstants.FAILURE;
			String successMessage = "";
			String actionSucMsg = "";
			try {
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
				if(sesObj != null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)) {
					String	studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					String buttonText = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT))?"":request.getParameter(FdahpStudyDesignerConstants.BUTTON_TEXT);
					if(StringUtils.isNotEmpty(studyId) && StringUtils.isNotEmpty(buttonText)){
						message = studyService.updateStudyActionOnAction(studyId, buttonText, sesObj);
						if(message.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS)){
							if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PUBLISH)){
								successMessage = FdahpStudyDesignerConstants.ACTION_PUBLISH_SUCCESS_MSG;
							}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UNPUBLISH)){
								successMessage = FdahpStudyDesignerConstants.ACTION_UNPUBLISH_SUCCESS_MSG;
							}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH)){
								successMessage = FdahpStudyDesignerConstants.ACTION_LUNCH_SUCCESS_MSG;
							}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES)){
								successMessage = FdahpStudyDesignerConstants.ACTION_UPDATES_SUCCESS_MSG;
							}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_RESUME)){
								successMessage = FdahpStudyDesignerConstants.ACTION_RESUME_SUCCESS_MSG;
							}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PAUSE)){
								successMessage = FdahpStudyDesignerConstants.ACTION_PAUSE_SUCCESS_MSG;
							}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_DEACTIVATE)){
								successMessage = FdahpStudyDesignerConstants.ACTION_DEACTIVATE_SUCCESS_MSG;
							}
							if(request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_SUC_MSG) != null){
								map.addAttribute(FdahpStudyDesignerConstants.ACTION_SUC_MSG, actionSucMsg);
								request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_SUC_MSG);
							}
							map.addAttribute("_S", sessionStudyCount);
							if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_DEACTIVATE) || buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH)
									|| buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES)){
								request.getSession().setAttribute(FdahpStudyDesignerConstants.ACTION_SUC_MSG, successMessage);
								mav = new ModelAndView("redirect:/adminStudies/studyList.do");
							}else{
								request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTION_SUC_MSG, successMessage);
								mav = new ModelAndView("redirect:/adminStudies/actionList.do", map);
							}
						}else{
							request.getSession().setAttribute(sessionStudyCount+"errMsg", "Unable to mark as complete. due to no change in Study");
						}
					}
				}
			} catch (Exception e) {
				logger.error("StudyController - updateStudyActionOnAction() - ERROR", e);
			}
			logger.info("StudyController - updateStudyActionOnAction() - Ends");
			return mav;
		}
		
		@RequestMapping("/adminStudies/questionnaireMarkAsCompleted.do")
		public ModelAndView questionnaireMarkAsCompleted(HttpServletRequest request) {
			logger.info("StudyController - questionnaireMarkAsCompleted() - Starts");
			ModelAndView mav = new ModelAndView("redirect:studyList.do");
			String message = FdahpStudyDesignerConstants.FAILURE;
			Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
			String customStudyId = "";
			ModelMap map = new ModelMap();
			try {
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
				if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
					String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					}
					customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
					message = studyService.markAsCompleted(Integer.parseInt(studyId) , FdahpStudyDesignerConstants.QUESTIONNAIRE, sesObj,customStudyId);	
					if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG, propMap.get(FdahpStudyDesignerConstants.COMPLETE_STUDY_SUCCESS_MESSAGE));
						map.addAttribute("_S", sessionStudyCount);
						mav = new ModelAndView("redirect:viewStudyActiveTasks.do",map);
					}else{
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, FdahpStudyDesignerConstants.UNABLE_TO_MARK_AS_COMPLETE);
						map.addAttribute("_S", sessionStudyCount);
						mav = new ModelAndView("redirect:viewStudyQuestionnaires.do",map);
					}
				}
			} catch (Exception e) {
				logger.error("StudyController - questionnaireMarkAsCompleted() - ERROR", e);
			}
			logger.info("StudyController - questionnaireMarkAsCompleted() - Ends");
			return mav;
		}
		
		/**
		 * @author Ronalin
		 * @param request
		 * @param response
		 * This method is used to validate the questionnaire have response type scale for android platform 
		 */
		@RequestMapping(value="/adminStudies/studyPlatformValidation",method = RequestMethod.POST)
		public void studyPlatformValidation(HttpServletRequest request ,HttpServletResponse response){
			logger.info("StudyController - studyPlatformValidation() - Starts");
			JSONObject jsonobject = new JSONObject();
			PrintWriter out = null;
			String message = FdahpStudyDesignerConstants.FAILURE;
			String errorMessage = "";
			try{
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
				if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
					String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					}
					message = studyQuestionnaireService.checkQuestionnaireResponseTypeValidation(Integer.parseInt(studyId));
					if(message.equals(FdahpStudyDesignerConstants.SUCCESS))
						errorMessage = FdahpStudyDesignerConstants.PLATFORM_ERROR_MSG_ANDROID;
				}
				jsonobject.put(FdahpStudyDesignerConstants.MESSAGE, message);
				jsonobject.put("errorMessage", errorMessage);
				response.setContentType(FdahpStudyDesignerConstants.APPLICATION_JSON);
				out = response.getWriter();
				out.print(jsonobject);
			}catch(Exception e){
				logger.error("StudyController - studyPlatformValidation() - ERROR",e);
			}
			logger.info("StudyController - studyPlatformValidation() - Ends");
		}
		
		/**
		 * @author Vivek
		 * @param request
		 * @param response
		 * This method is used to validate the questionnaire have response type scale for android platform 
		 */
		@RequestMapping(value="/downloadPdf.do",method = RequestMethod.POST)
		public void downloadPdf(HttpServletRequest request, HttpServletResponse response) {
				Map<String, String> configMap = FdahpStudyDesignerUtil.getAppProperties();
				InputStream is = null;
			   try {
				  String fileName = (request.getParameter("fileName")) == null ? "": request.getParameter("fileName");
				  String fileFolder = (request.getParameter("fileFolder")) == null ? "": request.getParameter("fileFolder");
				  String currentPath = configMap.get("fda.currentPath")!= null ? System.getProperty((String) configMap.get("fda.currentPath")): "";
			      String rootPath = currentPath.replace('\\', '/')+ configMap.get("fda.imgUploadPath");
			      File pdfFile = new File(rootPath + fileFolder + "/" + fileName);
			      is = new FileInputStream(pdfFile);
			      response.setContentType("application/pdf");
			      response.setContentLength((int)pdfFile.length());
//			      response.setHeader("Content-Transfer-Encoding", "binary");
			      response.setHeader("Content-Disposition","inline; filename=\""+fileName+"\"");
			      IOUtils.copy(is, response.getOutputStream());
			      response.flushBuffer();
			      is.close();
			    } catch (Exception e) {
			    	logger.error("StudyController - studyPlatformValidation() - ERROR", e);
			    }
			}
}