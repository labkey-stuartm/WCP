package com.fdahpStudyDesigner.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.maven.model.Model;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bean.FileUploadForm;
import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ComprehensionTestQuestionBo;
import com.fdahpStudyDesigner.bo.ConsentInfoBo;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudyPageBo;
import com.fdahpStudyDesigner.bo.StudySequenceBo;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.service.StudyService;
import com.fdahpStudyDesigner.service.UsersService;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

/**
 * @author Ronalin
 *
 */
@Controller
public class StudyController {

    private static Logger logger = Logger.getLogger(StudyController.class.getName());
	
	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	@Autowired
	private StudyService studyService;
	
	@Autowired
	private UsersService usersService;
	
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
		//List<UserBO> userList = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				studyBos = studyService.getStudyList(sesObj.getUserId());
				//userList = usersService.getUserList();
				//map.addAttribute("userList"+userList);
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
	
	/**@author Ronalin
	 * This method is used to navigate the study sequence
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/navigateStudy.do")
	public ModelAndView navigateStudy(HttpServletRequest request, Integer studyId){
		logger.info("StudyController - navigateStudy - Starts");
		ModelAndView mav = new ModelAndView("loginPage");
        StudyBo studyBo = null;	
		try{
			studyId = studyId == null ? 0 : studyId;
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				if(studyId == 0){
					studyId = (Integer) request.getSession().getAttribute("studyId");
				}
				studyBo = studyService.getStudyById(studyId.toString());
				if(studyBo!=null){
				    if(studyBo.getSequenceNumber()==1){
				    	request.getSession().setAttribute("studyId", studyBo.getId());
				    	return new ModelAndView("redirect:viewBasicInfo.do");
				    }
				}else{
			    	return new ModelAndView("redirect:viewBasicInfo.do");
				}
			}
		}catch(Exception e){
			logger.error("StudyController - navigateStudy - ERROR",e);
		}
		logger.info("StudyController - navigateStudy - Ends");
		return mav;
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
		ModelAndView mav = new ModelAndView("loginPage");
		ModelMap map = new ModelMap();
		HashMap<String, List<ReferenceTablesBo>> referenceMap = null;
		List<ReferenceTablesBo> categoryList = null;
		List<ReferenceTablesBo> researchSponserList = null;
		List<ReferenceTablesBo> dataPartnerList = null;
		StudyBo studyBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				/*if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId);
				}
				referenceMap = studyService.getreferenceListByCategory();
				if(referenceMap!=null && referenceMap.size()>0){
				for (String key : referenceMap.keySet()) {
					if (StringUtils.isNotEmpty(key)) {
						switch (key) {
						case fdahpStudyDesignerConstants.REFERENCE_TYPE_CATEGORIES:
							 categoryList = referenceMap.get(key);
							 break;
						case fdahpStudyDesignerConstants.REFERENCE_TYPE_RESEARCH_SPONSORS:
							researchSponserList = referenceMap.get(key);
 							break;
						case fdahpStudyDesignerConstants.REFERENCE_TYPE_DATA_PARTNER:
							dataPartnerList = referenceMap.get(key);
							break;
						default:
							break;
						}
					}
				  }
				}*/
				map.addAttribute("categoryList",categoryList);
				map.addAttribute("researchSponserList",researchSponserList);
				map.addAttribute("dataPartnerList",dataPartnerList);
				map.addAttribute("studyBo",studyBo);
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
	 * save or update baisc info page
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/saveOrUpdateBasicInfo.do")
	public ModelAndView saveOrUpdateBasicInfo(HttpServletRequest request, StudyBo studyBo, String buttonText){
		logger.info("StudyController - saveOrUpdateBasicInfo - Starts");
		ModelAndView mav = new ModelAndView("viewBasicInfo");
		String fileName = "", file="";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				
				if(studyBo.getId()==null){
					StudySequenceBo studySequenceBo = new StudySequenceBo();
					studySequenceBo.setBasicInfo(true);
					studyBo.setStatus(fdahpStudyDesignerConstants.STUDY_PRE_LAUNCH);
					//studyBo.setSequenceNumber(fdahpStudyDesignerConstants.SEQUENCE_NO_1);
					studyBo.setUserId(sesObj.getUserId());
				}
				if(studyBo.getFile()!=null){
					file= fdahpStudyDesignerUtil.getStandardFileName("STUDY",studyBo.getName(), studyBo.getCustomStudyId());
					fileName = fdahpStudyDesignerUtil.uploadImageFile(studyBo.getFile(),file, fdahpStudyDesignerConstants.STUDTYLOGO);
					studyBo.setThumbnailImage(fileName);
				}
				studyService.saveOrUpdateStudy(studyBo);
				if(StringUtils.isNotEmpty(buttonText) && buttonText.equalsIgnoreCase(fdahpStudyDesignerConstants.SAVE_BUTTON)){
					request.getSession().setAttribute("studyId", studyBo.getId());	
				    return new ModelAndView("redirect:viewBasicInfo.do");
				}else if(buttonText.equalsIgnoreCase(fdahpStudyDesignerConstants.COMPLETED_BUTTON)){
				  request.getSession().setAttribute("studyId", studyBo.getId());	
				  return new ModelAndView("redirect:viewSettingAndAdmins.do");
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
		ModelAndView mav = new ModelAndView("viewSettingAndAdmins");
		ModelMap map = new ModelMap();
		StudyBo studyBo = null;
		List<UserBO> userList = null;
		List<StudyListBean> studyPermissionList = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId);
					userList = usersService.getUserList();
					studyPermissionList = studyService.getStudyList(sesObj.getUserId());
					if(studyPermissionList!=null && studyPermissionList.size()>0){
						studyBo.setStudyPermissions(studyPermissionList);
					}
					map.addAttribute("userList", userList);
					map.addAttribute("studyBo",studyBo);
					mav = new ModelAndView("viewSettingAndAdmins", map);
				}else{
					request.getSession().setAttribute("studyId", studyId);
					return new ModelAndView("redirect:navigateStudy.do",map);
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
			PrintWriter out = null;
			String message = fdahpStudyDesignerConstants.FAILURE;
			boolean flag = false;
			try{
				HttpSession session = request.getSession();
				SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
				if (userSession != null) {
					String studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
					flag = studyService.deleteStudyPermissionById(userSession.getUserId(), studyId);
					if(flag)
						message = fdahpStudyDesignerConstants.SUCCESS;
				}
			}catch (Exception e) {
				logger.error("StudyController - removeStudyPermissionById() - ERROR ", e);
			}
			logger.info("StudyController - removeStudyPermissionById() - Ends ");
			jsonobject.put("message", message);
			response.setContentType("application/json");
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
				PrintWriter out = null;
				String message = fdahpStudyDesignerConstants.FAILURE;
				boolean flag = false;
				try{
					HttpSession session = request.getSession();
					SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
					if (userSession != null) {
						String studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
						String userIds = fdahpStudyDesignerUtil.isEmpty(request.getParameter("userIds")) == true?"":request.getParameter("userIds");
						flag = studyService.addStudyPermissionByuserIds(userSession.getUserId(), studyId, userIds);
						if(flag)
							message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}catch (Exception e) {
					logger.error("StudyController - addStudyPermissionByuserIds() - ERROR ", e);
				}
				logger.info("StudyController - addStudyPermissionByuserIds() - Ends ");
				jsonobject.put("message", message);
				response.setContentType("application/json");
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
		public ModelAndView saveOrUpdateSettingAndAdmins(HttpServletRequest request, StudyBo studyBo, String buttonText){
			logger.info("StudyController - saveOrUpdateSettingAndAdmins - Starts");
			ModelAndView mav = new ModelAndView("viewSettingAndAdmins");
			StudySequenceBo studySequenceBo = null;
			try{
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
				if(sesObj!=null){
					
					/*if(studyBo.getSequenceNumber()!=null && studyBo.getSequenceNumber() < 2){
						studyBo.setSequenceNumber(fdahpStudyDesignerConstants.SEQUENCE_NO_2);
						studyBo.setUserId(sesObj.getUserId());
					}*/
					if(studyBo.getStudySequenceBo()!=null){
						studySequenceBo = studyBo.getStudySequenceBo();
						studySequenceBo.setSettingAdmins(true);
					}
					studyService.saveOrUpdateStudy(studyBo);
					if(StringUtils.isNotEmpty(buttonText) && buttonText.equalsIgnoreCase(fdahpStudyDesignerConstants.SAVE_BUTTON)){
					  request.getSession().setAttribute("studyId", studyBo.getId());	
					  return new ModelAndView("redirect:viewSettingAndAdmins.do");
					}else if(buttonText.equalsIgnoreCase(fdahpStudyDesignerConstants.COMPLETED_BUTTON)){
					  request.getSession().setAttribute("studyId", studyBo.getId());	
					  return new ModelAndView("redirect:viewSettingAndAdmins.do");
					}  
				}
			}catch(Exception e){
				logger.error("StudyController - saveOrUpdateSettingAndAdmins - ERROR",e);
			}
			logger.info("StudyController - saveOrUpdateSettingAndAdmins - Ends");
			return mav;
		}
		
		

		/**
		 * Navigate from current to  Particular previous sequence  Study (cancel button for each study page)
		 * @author Ronalin
		 *  
		 * @param request , {@link HttpServletRequest}
		 * @return {@link ModelAndView} , dashBoardPage page
		 */
		@RequestMapping("/adminDashboard/navigatePreviousPage.do")
		public ModelAndView navigatePreviousPage(HttpServletRequest request){
			logger.info("StudyController - navigatePreviousPage - Starts");
			ModelAndView mav = new ModelAndView("redirect:studyList.do");
			try{
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
				if(sesObj!=null){
					String studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
					String currentSequenceNumber = fdahpStudyDesignerUtil.isEmpty(request.getParameter("sequenceNumber")) == true?"":request.getParameter("sequenceNumber");
					if(StringUtils.isNotEmpty(currentSequenceNumber)){
						Integer sequenceNumber = Integer.parseInt(currentSequenceNumber);
						switch (sequenceNumber) {
						case 1     :  mav = new ModelAndView("redirect:studyList.do");
						              break;
						case 2     :  request.getSession().setAttribute("studyId", studyId);	
			                          mav = new ModelAndView("redirect:viewBasicInfo.do");
			                          break;              
						case 3     :  request.getSession().setAttribute("studyId", studyId);	
						              mav = new ModelAndView("redirect:viewSettingAndAdmins.do");
                                      break;              
						default:      break;
						}
					}
				}
			}catch(Exception e){
				logger.error("StudyController - navigatePreviousPage - ERROR",e);
			}
			logger.info("StudyController - navigatePreviousPage - Ends");
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
			ModelAndView mav = new ModelAndView("overviewStudyPage");
			ModelMap map = new ModelMap();
			List<StudyPageBo> studyPageBos = null;
			StudyBo studyBo = null;
			try{
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
				if(sesObj!=null){
					String studyId = (String) request.getSession().getAttribute("studyId");
					if(StringUtils.isEmpty(studyId)){
						studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
					}
					if(StringUtils.isNotEmpty(studyId)){
						studyPageBos = studyService.getOverviewStudyPagesById(studyId);
						studyBo = studyService.getStudyById(studyId);
						map.addAttribute("studyPageBos",studyPageBos);
						map.addAttribute("studyBo",studyBo);
						mav = new ModelAndView("overviewStudyPages", map);
					}else{
						request.getSession().setAttribute("studyId", studyId);
						return new ModelAndView("redirect:navigateStudy.do",map);
					}
				}
			}catch(Exception e){
				logger.error("StudyController - overviewStudyPages - ERROR",e);
			}
			logger.info("StudyController - overviewStudyPages - Ends");
			return mav;
		}
		
		/** ajax call remove each page
		  * @author Ronalin
		  * Removing particular Study Overview Page for the current user Study
		  * @param request , {@link HttpServletRequest}
		  * @param response , {@link HttpServletResponse}
		  * @throws IOException
		  * @return void
		  */
			@RequestMapping("/adminStudies/removeStudyOverviewPageById.do")
			public void removeStudyOverviewPageById(HttpServletRequest request, HttpServletResponse response) throws IOException{
				logger.info("StudyController - removeStudyOverviewPageById() - Starts ");
				JSONObject jsonobject = new JSONObject();
				PrintWriter out = null;
				String message = fdahpStudyDesignerConstants.FAILURE;
				try{
					HttpSession session = request.getSession();
					SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
					if (userSession != null) {
						String studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
						String pageId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("pageId")) == true?"":request.getParameter("pageId");
						if(StringUtils.isNotEmpty(studyId) && StringUtils.isNotEmpty(pageId))
							message = studyService.deleteOverviewStudyPageById(studyId, pageId);
					}
				}catch (Exception e) {
					logger.error("StudyController - removeStudyOverviewPageById() - ERROR ", e);
				}
				logger.info("StudyController - removeStudyOverviewPageById() - Ends ");
				jsonobject.put("message", message);
				response.setContentType("application/json");
				out = response.getWriter();
				out.print(jsonobject);
			}
			
			/** ajax call save each studyPage by clicking on add Page button
			  * @author Ronalin
			  * Saving particular Study Overview Page for the current user Study
			  * @param request , {@link HttpServletRequest}
			  * @param response , {@link HttpServletResponse}
			  * @throws IOException
			  * @return void
			  */
				@RequestMapping("/adminStudies/saveStudyOverviewPage.do")
				public void saveStudyOverviewPage(HttpServletRequest request, HttpServletResponse response) throws IOException{
					logger.info("StudyController - saveStudyOverviewPage() - Starts ");
					JSONObject jsonobject = new JSONObject();
					PrintWriter out = null;
					String message = fdahpStudyDesignerConstants.FAILURE;
					Integer pageId = 0;
					try{
						HttpSession session = request.getSession();
						SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
						if (userSession != null) {
							String studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
							if(StringUtils.isNotEmpty(studyId))
								pageId = studyService.saveOverviewStudyPageById(studyId);
							    if(pageId>0)
							    	message = fdahpStudyDesignerConstants.FAILURE;
						}
					}catch (Exception e) {
						logger.error("StudyController - saveStudyOverviewPage() - ERROR ", e);
					}
					logger.info("StudyController - saveStudyOverviewPage() - Ends ");
					jsonobject.put("message", message);
					jsonobject.put("pageId", pageId);
					response.setContentType("application/json");
					out = response.getWriter();
					out.print(jsonobject);
				}
				
				/**
			     * @author Ronalin
				 * save or update study page
				 * @param request , {@link HttpServletRequest}
				 * @return {@link ModelAndView}
				 */
				@RequestMapping("/adminStudies/saveOrUpdateStudyOverviewPage.do")
				public ModelAndView saveOrUpdateStudyOverviewPage(HttpServletRequest request,@ModelAttribute("uploadForm") FileUploadForm uploadForm){
					logger.info("StudyController - saveOrUpdateStudyOverviewPage - Starts");
					ModelAndView mav = new ModelAndView("overviewStudyPage");
					StudyBo studyBo = null;
					StudySequenceBo studySequenceBo = null;
					try{
						SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
						String studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
						String pageIds = fdahpStudyDesignerUtil.isEmpty(request.getParameter("pageIds")) == true?"":request.getParameter("pageIds");
						String titles = fdahpStudyDesignerUtil.isEmpty(request.getParameter("titles")) == true?"":request.getParameter("titles");
						String descs= fdahpStudyDesignerUtil.isEmpty(request.getParameter("descs")) == true?"":request.getParameter("descs");
						String buttonText = fdahpStudyDesignerUtil.isEmpty(request.getParameter("buttonText")) == true?"":request.getParameter("buttonText");
						if(sesObj!=null){
							List<MultipartFile> files = uploadForm.getFiles();
							studyBo = studyService.getStudyById(studyId);
							studyService.saveOrUpdateOverviewStudyPages(studyId, pageIds, titles, descs, files);
							if(studyBo.getStudySequenceBo()!=null){
								studySequenceBo = studyBo.getStudySequenceBo();
								studySequenceBo.setOverView(true);
							}
							
							if(StringUtils.isNotEmpty(buttonText) && buttonText.equalsIgnoreCase(fdahpStudyDesignerConstants.SAVE_BUTTON)){
								  request.getSession().setAttribute("studyId", studyId);	
								  return new ModelAndView("redirect:overviewStudyPages.do");
							}else if(buttonText.equalsIgnoreCase(fdahpStudyDesignerConstants.COMPLETED_BUTTON)){
								  request.getSession().setAttribute("studyId", studyId);	
								  return new ModelAndView("redirect:viewSettingAndAdmins.do");/** this will go to next step**/
							}else{
								  request.getSession().setAttribute("studyId", studyId);	
							      return new ModelAndView("redirect:overviewStudyPages.do");
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
	 * @param response
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/consentListPage.do")
	public ModelAndView getConsentListPage(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyController - getConsentPage - Starts");
		ModelAndView mav = new ModelAndView("consentInfoListPage");
		ModelMap map = new ModelMap();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			List<ConsentInfoBo> consentInfoList = new ArrayList<ConsentInfoBo>();
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				}
				if(StringUtils.isNotEmpty(studyId)){
					consentInfoList = studyService.getConsentInfoList(Integer.valueOf(studyId));
					map.addAttribute("consentInfoList", consentInfoList);
					map.addAttribute("studyId", studyId);
				}
				mav = new ModelAndView("consentInfoListPage",map);
			}
		}catch(Exception e){
			logger.error("StudyController - getConsentPage - ERROR",e);
		}
		logger.info("StudyController - getConsentPage - Ends");
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			int oldOrderNumber = 0;
			int newOrderNumber = 0;
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				}
				String oldOrderNo = fdahpStudyDesignerUtil.isEmpty(request.getParameter("oldOrderNumber")) == true?"":request.getParameter("oldOrderNumber");
				String newOrderNo = fdahpStudyDesignerUtil.isEmpty(request.getParameter("newOrderNumber")) == true?"":request.getParameter("newOrderNumber");
				if((studyId != null && !studyId.isEmpty()) && !oldOrderNo.isEmpty() && !newOrderNo.isEmpty()){
					oldOrderNumber = Integer.valueOf(oldOrderNo);
					newOrderNumber = Integer.valueOf(newOrderNo);
					message = studyService.reOrderConsentInfoList(Integer.valueOf(studyId), oldOrderNumber, newOrderNumber);
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
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
	 */
	@RequestMapping("/adminStudies/deleteConsentInfo.do")
	public void deleteConsentInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyController - reOrderConsentInfo - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String consentInfoId = (String) request.getSession().getAttribute("consentInfoId");
				if(StringUtils.isEmpty(consentInfoId)){
					message = studyService.deleteConsentInfo(Integer.valueOf(consentInfoId));
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - reOrderConsentInfo - ERROR",e);
		}
		logger.info("StudyController - reOrderConsentInfo - Ends");
	}
	
	/**
	 * 
	 * @author Ravinder
	 * @param request
	 * @param response
	 * @param consentInfoBo
	 * @return
	 */
	@RequestMapping("/adminStudies/saveOrUpdateConsentInfo.do")
	public ModelAndView saveOrUpdateConsentInfo(HttpServletRequest request , HttpServletResponse response,ConsentInfoBo consentInfoBo){
		logger.info("StudyController - saveOrUpdateConsentInfo - Starts");
		ModelAndView mav = new ModelAndView("consentInfoListPage");
		ConsentInfoBo addConsentInfoBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				if(consentInfoBo != null){
					if(consentInfoBo.getStudyId() != null){
						int order = studyService.consentInfoOrder(consentInfoBo.getStudyId());
						consentInfoBo.setSequenceNo(order);
					}
					addConsentInfoBo = studyService.saveOrUpdateConsentInfo(consentInfoBo, sesObj);
					if(addConsentInfoBo != null){
						return new ModelAndView("redirect:/adminStudies/consentListPage.do");
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
	 * @param response
	 * @return
	 */
	@RequestMapping("/adminStudies/consentInfo.do")
	public ModelAndView getConsentPage(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyController - getConsentPage - Starts");
		ModelAndView mav = new ModelAndView("consentInfoPage");
		ModelMap map = new ModelMap();
		ConsentInfoBo consentInfoBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String consentInfoId = (String) request.getSession().getAttribute("consentInfoId");
				if(StringUtils.isEmpty(consentInfoId)){
					consentInfoBo = studyService.getConsentInfoById(Integer.valueOf(consentInfoId));
					map.addAttribute("consentInfoBo", consentInfoBo);
					mav = new ModelAndView("consentInfoPage",map);
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
	 * @return
	 */
	@RequestMapping("/adminStudies/comprehensionQuestionList.do")
	public ModelAndView getComprehensionQuestionList(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyController - getComprehensionQuestionList - Starts");
		ModelAndView mav = new ModelAndView("comprehensionTestQuestionListPage");
		ModelMap map = new ModelMap();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			List<ComprehensionTestQuestionBo> comprehensionTestQuestionList = new ArrayList<ComprehensionTestQuestionBo>();
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				}
				if(StringUtils.isNotEmpty(studyId)){
					comprehensionTestQuestionList = studyService.getComprehensionTestQuestionList(Integer.valueOf(studyId));
					map.addAttribute("comprehensionTestQuestionList", comprehensionTestQuestionList);
				}
				mav = new ModelAndView("comprehensionTestQuestionListPage",map);
			}
		}catch(Exception e){
			logger.error("StudyController - getComprehensionQuestionList - ERROR",e);
		}
		logger.info("StudyController - getComprehensionQuestionList - Ends");
		return mav;
	}
	
	@RequestMapping("/adminStudies/comprehensionQuestionPage.do")
	public ModelAndView getComprehensionQuestionPage(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyController - getConsentPage - Starts");
		ModelAndView mav = new ModelAndView("comprehensionQuestionPage");
		ModelMap map = new ModelMap();
		ComprehensionTestQuestionBo comprehensionTestQuestionBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String comprehensionQuestionId = (String) request.getSession().getAttribute("comprehensionQuestionId");
				if(StringUtils.isEmpty(comprehensionQuestionId)){
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String comprehensionQuestionId = (String) request.getSession().getAttribute("comprehensionQuestionId");
				if(StringUtils.isEmpty(comprehensionQuestionId)){
					message = studyService.deleteComprehensionTestQuestion(Integer.valueOf(comprehensionQuestionId));
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
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
	 * @param response
	 * @param consentInfoBo
	 * @return
	 */
	@RequestMapping("/adminStudies/saveOrUpdateComprehensionTestQuestion.do")
	public ModelAndView saveOrUpdateComprehensionTestQuestionPage(HttpServletRequest request , HttpServletResponse response,ComprehensionTestQuestionBo comprehensionTestQuestionBo){
		logger.info("StudyController - saveOrUpdateComprehensionTestQuestionPage - Starts");
		ModelAndView mav = new ModelAndView("consentInfoListPage");
		ComprehensionTestQuestionBo addComprehensionTestQuestionBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				if(comprehensionTestQuestionBo != null){
					if(comprehensionTestQuestionBo.getStudyId() != null){
						int order = studyService.comprehensionTestQuestionOrder(comprehensionTestQuestionBo.getStudyId());
						comprehensionTestQuestionBo.setSequenceNo(order);
					}
					if(comprehensionTestQuestionBo.getId() != null){
						comprehensionTestQuestionBo.setModifiedBy(sesObj.getUserId());
						comprehensionTestQuestionBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						comprehensionTestQuestionBo.setCreatedBy(sesObj.getUserId());
						comprehensionTestQuestionBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addComprehensionTestQuestionBo = studyService.saveOrUpdateComprehensionTestQuestion(comprehensionTestQuestionBo);
					if(addComprehensionTestQuestionBo != null){
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			int oldOrderNumber = 0;
			int newOrderNumber = 0;
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				String oldOrderNo = fdahpStudyDesignerUtil.isEmpty(request.getParameter("oldOrderNumber")) == true?"":request.getParameter("oldOrderNumber");
				String newOrderNo = fdahpStudyDesignerUtil.isEmpty(request.getParameter("newOrderNumber")) == true?"":request.getParameter("newOrderNumber");
				if((studyId != null && !studyId.isEmpty()) && !oldOrderNo.isEmpty() && !newOrderNo.isEmpty()){
					oldOrderNumber = Integer.valueOf(oldOrderNumber);
					newOrderNumber = Integer.valueOf(newOrderNo);
					message = studyService.reOrderComprehensionTestQuestion(Integer.valueOf(studyId), oldOrderNumber, newOrderNumber);
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyController - reOrderComprehensionTestQuestion - ERROR",e);
		}
		logger.info("StudyController - reOrderComprehensionTestQuestion - Ends");
	}
	
	/*------------------------------------Added By Vivek Start---------------------------------------------------*/
	/**
	 * view Eligibility page
	 * @author Vivek 
	 * 
	 * @param request, {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/viewStudyEligibilty.do")
	public ModelAndView viewStudyEligibilty(HttpServletRequest request) {
		logger.info("StudyController - overviewStudyPages - Starts");
		ModelAndView mav = new ModelAndView("overviewStudyPage");
		ModelMap map = new ModelMap();
		List<StudyPageBo> studyPageBos = null;
		StudyBo studyBo = null;
		try {
			SessionObject sesObj = (SessionObject) request.getSession()
					.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if (sesObj != null) {
				String studyId = (String) request.getSession().getAttribute("studyId");
				if (StringUtils.isEmpty(studyId)) {
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true ? "0" : request.getParameter("studyId");
				}
				if (StringUtils.isNotEmpty(studyId)) {
					studyPageBos = studyService.getOverviewStudyPagesById(studyId);
					studyBo = studyService.getStudyById(studyId);
					map.addAttribute("studyPageBos", studyPageBos);
					map.addAttribute("studyBo", studyBo);
					mav = new ModelAndView("overviewStudyPages", map);
				} else {
					request.getSession().setAttribute("studyId", studyId);
					return new ModelAndView("redirect:navigateStudy.do", map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyController - overviewStudyPages - ERROR", e);
		}
		logger.info("StudyController - overviewStudyPages - Ends");
		return mav;
	}
	
	/*------------------------------------Added By Vivek End---------------------------------------------------*/
}