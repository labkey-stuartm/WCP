package com.fdahpStudyDesigner.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bean.StudyListBean;
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
	
	private StudyServiceImpl StudyService;
	/* Setter Injection */
    @Autowired
	public void setStudyService(StudyServiceImpl studyService) {
		StudyService = studyService;
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
				studyBos = StudyService.getStudyList(sesObj.getUserId());
				map.addAttribute("studyBos", studyBos);
				mav = new ModelAndView("studyListPage", map);
			}
		}catch(Exception e){
			logger.error("StudyController - getStudies - ERROR",e);
		}
		logger.info("StudyController - getStudies - Ends");
		return mav;
	}
	
	/**
     * @author Ronalin
	 * Getting Study list
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/addBasicInfo.do")
	public ModelAndView addBasicInfo(HttpServletRequest request, StudyBo studyBo){
		logger.info("StudyController - addBasicInfo - Starts");
		ModelAndView mav = new ModelAndView("loginPage");
		ModelMap map = new ModelMap();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				
				
				//studyBo
				
				
				mav = new ModelAndView("studyListPage", map);
			}
		}catch(Exception e){
			logger.error("StudyController - addBasicInfo - ERROR",e);
		}
		logger.info("StudyController - addBasicInfo - Ends");
		return mav;
	}
	
	
	
	
    
}
