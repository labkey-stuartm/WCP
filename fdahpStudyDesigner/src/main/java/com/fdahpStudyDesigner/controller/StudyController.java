package com.fdahpStudyDesigner.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.service.StudyServiceImpl;
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
	
	private StudyServiceImpl studyService;
	/* Setter Injection */
    @Autowired
	public void setStudyService(StudyServiceImpl studyService) {
    	this.studyService = studyService;
	}
    
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
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				studyBos = studyService.getStudyList(sesObj.getUserId());
				map.addAttribute("studyBos", studyBos);
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
				}
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
		ModelMap map = new ModelMap();
		String fileName = "", file="";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				
				if(studyBo.getSequenceNumber()==null){
					studyBo.setSequenceNumber(1);
					studyBo.setUserId(sesObj.getUserId());
				}
				if(studyBo.getFile()!=null){
					file= fdahpStudyDesignerUtil.getStandardFileName("STUDY",studyBo.getName(), studyBo.getCustomStudyId());
					fileName = fdahpStudyDesignerUtil.uploadImageFile(studyBo.getFile(),file, fdahpStudyDesignerConstants.STUDTYLOGO);
					studyBo.setThumbnailImage(fileName);
				}
				studyService.saveOrUpdateStudy(studyBo);
				if(StringUtils.isNotEmpty(buttonText) && buttonText.equalsIgnoreCase(fdahpStudyDesignerConstants.SAVE_BUTTON))
				  mav = new ModelAndView("studyListPage", map);
				else if(buttonText.equalsIgnoreCase(fdahpStudyDesignerConstants.COMPLETED_BUTTON)){
				  request.getSession().setAttribute("studyId", studyBo.getId());	
				  return new ModelAndView("redirect:viewSettingAndAdmins.do",map);
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
	 * add baisc info page
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/viewSettingAndAdmins.do")
	public ModelAndView viewSettingAndAdmins(HttpServletRequest request){
		logger.info("StudyController - viewSettingAndAdmins - Starts");
		ModelAndView mav = new ModelAndView("viewSettingAndAdmins");
		ModelMap map = new ModelMap();
		StudyBo studyBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId);
				map.addAttribute("studyBo",studyBo);
				mav = new ModelAndView("viewSettingAndAdmins", map);
				}else{
					map.addAttribute("studyId",studyId);
					return new ModelAndView("redirect:navigateStudy.do",map);
				}
			}
		}catch(Exception e){
			logger.error("StudyController - viewSettingAndAdmins - ERROR",e);
		}
		logger.info("StudyController - viewSettingAndAdmins - Ends");
		return mav;
	}
	
    
}
