/**
 * 
 */
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

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.service.StudyActiveTasksService;
import com.fdahpStudyDesigner.service.StudyService;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

/**
 * @author Vivek
 *
 */
@Controller
public class StudyActiveTasksController {
	
	private static Logger logger = Logger.getLogger(StudyActiveTasksController.class.getName());
	
	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	@Autowired
	private StudyActiveTasksService studyActiveTasksService; 
	
	@Autowired
	private StudyService studyService;
	
	/**
	 * view active tasks page
	 * @author Vivek 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/viewStudyActiveTasks.do")
	public ModelAndView viewStudyActiveTasks(HttpServletRequest request) {
		logger.info("StudyActiveTasksController - viewStudyActiveTasks - Starts");
		ModelAndView mav = new ModelAndView("redirect:viewBasicInfo.do");
		ModelMap map = new ModelMap();
		StudyBo studyBo = null;
		String sucMsg = "";
		String errMsg = "";
		List<ActiveTaskBo> activeTasks = null;
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != request.getSession().getAttribute("sucMsg")){
				sucMsg = (String) request.getSession().getAttribute("sucMsg");
				map.addAttribute("sucMsg", sucMsg);
				request.getSession().removeAttribute("sucMsg");
			}
			if(null != request.getSession().getAttribute("errMsg")){
				errMsg = (String) request.getSession().getAttribute("errMsg");
				map.addAttribute("errMsg", errMsg);
				request.getSession().removeAttribute("errMsg");
			}
			
			String studyId = (String) request.getSession().getAttribute("studyId");
			if (StringUtils.isEmpty(studyId)) {
				studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true ? "0" : request.getParameter("studyId");
			} 
			if (StringUtils.isNotEmpty(studyId)) {
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				activeTasks = studyActiveTasksService.getStudyActiveTasksByStudyId(studyId);
				map.addAttribute("studyBo", studyBo);
				map.addAttribute("activeTasks", activeTasks);
				mav = new ModelAndView("studyActiveTaskListPage", map);
			} 
		} catch (Exception e) {
			logger.error("StudyActiveTasksController - viewStudyActiveTasks - ERROR", e);
		}
		logger.info("StudyActiveTasksController - viewStudyActiveTasks - Ends");
		return mav;
	}
}
