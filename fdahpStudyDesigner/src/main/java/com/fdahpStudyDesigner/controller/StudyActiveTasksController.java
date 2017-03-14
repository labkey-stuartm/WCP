/**
 * 
 */
package com.fdahpStudyDesigner.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.ActiveTaskListBo;
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
	
	@RequestMapping(value="/adminStudies/viewScheduledActiveTask.do")
	public ModelAndView getActiveTaskPage(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyActiveTaskController - viewScheduledActiveTask - Starts");
		ModelAndView mav = new ModelAndView("questionnairePage");
		ModelMap map = new ModelMap();
		String sucMsg = "";
		String errMsg = "";
		StudyBo studyBo = null;
		ActiveTaskBo activeTaskBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
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
				String activeTaskId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskId")) == true?"":request.getParameter("activeTaskId");
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
					request.getSession().setAttribute("studyId", studyId);
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute("studyBo", studyBo);
				}
				if(StringUtils.isEmpty(activeTaskId)){
					activeTaskId = (String) request.getSession().getAttribute("activeTaskId");
					request.getSession().setAttribute("activeTaskId", activeTaskId);
				}
				if(null!=activeTaskId && !activeTaskId.isEmpty()){
					activeTaskBo=studyActiveTasksService.getActiveTaskById(Integer.valueOf(activeTaskId));
					if(activeTaskBo != null){
						map.addAttribute("customCount",activeTaskBo.getActiveTaskCustomScheduleBo().size());
						map.addAttribute("count",activeTaskBo.getActiveTaskFrequenciesList().size());
					}
					map.addAttribute("activeTaskBo", activeTaskBo);
				}
				mav = new ModelAndView("questionnairePage",map);
			}
		}catch(Exception e){
			logger.error("StudyActiveTaskController - viewScheduledActiveTask - Error", e);
		}
		logger.info("StudyActiveTaskController - viewScheduledActiveTask - Ends");
		return mav;
	}
	
	@RequestMapping(value="/adminStudies/saveOrUpdateActiveTaskSchedule.do",method=RequestMethod.POST)
	public ModelAndView saveorUpdateActiveTaskSchedule(HttpServletRequest request , HttpServletResponse response,ActiveTaskBo activeTaskBo){
		logger.info("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Starts");
		ModelAndView mav = new ModelAndView("questionnairePage");
		ModelMap map = new ModelMap();
		ActiveTaskBo addActiveTaskBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(activeTaskBo != null){
					if(activeTaskBo.getId() != null){
						activeTaskBo.setModifiedBy(sesObj.getUserId());
						activeTaskBo.setModifiedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						activeTaskBo.setCreatedBy(sesObj.getUserId());
						activeTaskBo.setCreatedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addActiveTaskBo = studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo);
				}
			}
			mav =  new ModelAndView("redirect:/adminStudies/viewActiveTask.do");
		}catch(Exception e){ 
			logger.error("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Error",e);
		}
		logger.info("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Ends");
		return mav;
	}
	
	/**
	 * 
	 * @author Ronalin 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 * view Study Active Task
	 */
	@RequestMapping("/adminStudies/viewActiveTask.do")
	public ModelAndView viewActiveTask(HttpServletRequest request) {
		logger.info("StudyActiveTasksController - viewActiveTask() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do");
		ModelMap map = new ModelMap();
		ActiveTaskBo activeTaskBo = null;
		StudyBo studyBo = null;
		List<ActiveTaskListBo> activeTaskListBos = new ArrayList<ActiveTaskListBo>();
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true ? "" : request.getParameter("studyId");
				}
				String activeTaskInfoId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId")) == true ? "" : request.getParameter("activeTaskInfoId");
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				activeTaskListBos = studyActiveTasksService.getAllActiveTaskTypes();
				map.addAttribute("activeTaskListBos", activeTaskListBos);
				map.addAttribute("studyBo", studyBo);
				if(StringUtils.isNotEmpty(activeTaskInfoId)){
					activeTaskBo = studyActiveTasksService.getActiveTaskById(Integer.parseInt(activeTaskInfoId));
					map.addAttribute("activeTaskBo", activeTaskBo);
				}
				mav = new ModelAndView("viewStudyActiveTask",map);
			}
		} catch (Exception e) {
			logger.error("StudyActiveTasksController - viewActiveTask() - ERROR", e);
		}
		logger.info("StudyActiveTasksController - viewActiveTask() - Ends");
		return mav;
	}
	
	
	/**
	 * 
	 * @author Ronalin 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 * navigate to  particular type of Active Task
	 */
	@RequestMapping("/adminStudies/navigateContentActiveTask.do")
	public ModelAndView navigateContentActiveTask(HttpServletRequest request) {
		logger.info("StudyActiveTasksController - navigateContentActiveTask() - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do");
		ModelMap map = new ModelMap();
		ActiveTaskBo activeTaskBo = null;
		StudyBo studyBo = null;
		List<ActiveTaskListBo> activeTaskListBos = new ArrayList<ActiveTaskListBo>();
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true ? "" : request.getParameter("studyId");
				}
				String activeTaskInfoId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId")) == true ? "" : request.getParameter("activeTaskInfoId");
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				activeTaskListBos = studyActiveTasksService.getAllActiveTaskTypes();
				map.addAttribute("activeTaskListBos", activeTaskListBos);
				map.addAttribute("studyBo", studyBo);
				if(StringUtils.isNotEmpty(activeTaskInfoId)){
					activeTaskBo = studyActiveTasksService.getActiveTaskById(Integer.parseInt(activeTaskInfoId));
					map.addAttribute("activeTaskBo", activeTaskBo);
					if(activeTaskListBos!=null && activeTaskListBos.size()>0){
						for (ActiveTaskListBo activeTaskListBo : activeTaskListBos) {
							if (StringUtils.isNotEmpty(activeTaskListBo.getTaskName()) && activeTaskListBo.getActiveTaskListId()==activeTaskBo.getTaskType()) {
								switch (activeTaskListBo.getTaskName()) {
								case fdahpStudyDesignerConstants.FETAL_KICK_COUNTER:
									 mav = new ModelAndView("viewFetalStudyActiveTask",map);
									 break;
								case fdahpStudyDesignerConstants.TOWER_OF_HANOI:
									mav = new ModelAndView("viewTowerStudyActiveTask",map);
		 							break;
								case fdahpStudyDesignerConstants.SPATIAL_SPAN_MEMORY:
									mav = new ModelAndView("viewSpatialStudyActiveTask",map);
									break;
								default:
									break;
								}
							}
						  }
						}
				}else{
					mav = new ModelAndView("viewFetalStudyActiveTask",map);
				}
			}
		} catch (Exception e) {
			logger.error("StudyActiveTasksController - navigateContentActiveTask() - ERROR", e);
		}
		logger.info("StudyActiveTasksController - navigateContentActiveTask() - Ends");
		return mav;
	}
	
	/**
	 * 
	 * @author Ronalin
	 * @param request
	 * @param response
	 * @param ActiveTaskBo
	 * @return
	 */
	@RequestMapping("/adminStudies/saveOrUpdateActiveTask.do")
	public ModelAndView saveOrUpdateActiveTask(HttpServletRequest request , HttpServletResponse response,ActiveTaskBo activeTaskBo){
		logger.info("StudyActiveTasksController - saveOrUpdateActiveTask - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ActiveTaskBo addActiveTaskBo = null;
		ModelMap map = new ModelMap();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				if(activeTaskBo != null){
					addActiveTaskBo = studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo, sesObj);
					if(addActiveTaskBo != null){
						request.getSession().setAttribute("sucMsg", "Task added successfully.");
						mav = new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do",map);
					}else{
						request.getSession().setAttribute("errMsg", "Task not added successfully.");
						mav = new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do", map);
					}
				}
			}	
		}catch(Exception e){
			logger.error("StudyActiveTasksController - saveOrUpdateActiveTask - ERROR",e);
		}
		logger.info("StudyActiveTasksController - saveOrUpdateActiveTask - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/deleteActiveTask.do",method = RequestMethod.POST)
	public void deleteConsentInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyActiveTasksController - deleteActiveTask - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String activeTaskInfoId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId")) == true?"":request.getParameter("activeTaskInfoId");
				String studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				if(!activeTaskInfoId.isEmpty() && !studyId.isEmpty()){
					message = studyActiveTasksService.deleteActiveTask(Integer.valueOf(activeTaskInfoId),Integer.valueOf(studyId));
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyActiveTasksController - deleteActiveTask - ERROR",e);
		}
		logger.info("StudyActiveTasksController - deleteActiveTask - Ends");
	}
}
