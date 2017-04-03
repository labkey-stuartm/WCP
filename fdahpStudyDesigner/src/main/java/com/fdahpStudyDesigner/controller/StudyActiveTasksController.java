/**
 * 
 */
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
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.ActiveTaskListBo;
import com.fdahpStudyDesigner.bo.ActiveTaskMasterAttributeBo;
import com.fdahpStudyDesigner.bo.ActivetaskFormulaBo;
import com.fdahpStudyDesigner.bo.StatisticImageListBo;
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
			if(request.getSession().getAttribute("activeTaskInfoId") != null){
				request.getSession().removeAttribute("activeTaskInfoId");
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
				mav = new ModelAndView("activeTaskScheduled",map);
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
			mav =  new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do");
		}catch(Exception e){ 
			logger.error("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Error",e);
		}
		logger.info("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Ends");
		return mav;
	}
	
	@RequestMapping(value="/adminStudies/saveActiveTaskSchedule.do",method=RequestMethod.POST)
	public void saveActiveTaskSchedule(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - saveQuestionnaireSchedule - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		ActiveTaskBo updateActiveTaskBo = null;
		ObjectMapper mapper = new ObjectMapper();
		ActiveTaskBo activeTaskBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String activeTaskScheduleInfo = request.getParameter("activeTaskScheduleInfo");
				if(activeTaskScheduleInfo != null && !activeTaskScheduleInfo.isEmpty()){
					activeTaskBo = mapper.readValue(activeTaskScheduleInfo, ActiveTaskBo.class);
					if(activeTaskBo != null){
						if(activeTaskBo.getId() != null){
							activeTaskBo.setModifiedBy(sesObj.getUserId());
							activeTaskBo.setModifiedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							activeTaskBo.setCreatedBy(sesObj.getUserId());
							activeTaskBo.setCreatedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
						}
						updateActiveTaskBo = studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo);
						if(updateActiveTaskBo != null){
							jsonobject.put("activeTaskId", updateActiveTaskBo.getId());
							if(updateActiveTaskBo.getActiveTaskFrequenciesBo() != null){
								jsonobject.put("activeTaskFrequenceId", updateActiveTaskBo.getActiveTaskFrequenciesBo().getId());
							}
							message = fdahpStudyDesignerConstants.SUCCESS;
						}
					}
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveQuestionnaireSchedule - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveQuestionnaireSchedule - Ends");
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
				if(fdahpStudyDesignerUtil.isEmpty(activeTaskInfoId)){
					activeTaskInfoId = (String) request.getSession().getAttribute("activeTaskInfoId");
				} else {
					request.getSession().setAttribute("activeTaskInfoId", activeTaskInfoId);
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					activeTaskListBos = studyActiveTasksService.getAllActiveTaskTypes();
					map.addAttribute("activeTaskListBos", activeTaskListBos);
					map.addAttribute("studyBo", studyBo);
					if(StringUtils.isNotEmpty(activeTaskInfoId)){
						activeTaskBo = studyActiveTasksService.getActiveTaskById(Integer.parseInt(activeTaskInfoId));
						map.addAttribute("activeTaskBo", activeTaskBo);
					}
					mav = new ModelAndView("viewStudyActiveTask",map);
				}else{
					mav = new ModelAndView("redirect:unauthorized.do");
				}
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
		ActiveTaskBo activeTaskBo = new ActiveTaskBo();
		StudyBo studyBo = null;
		String activeTaskInfoId="", typeOfActiveTask = "";
		List<ActiveTaskListBo> activeTaskListBos = new ArrayList<ActiveTaskListBo>();
		List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<ActiveTaskMasterAttributeBo>();
		List<String> timeRangeList = new ArrayList<String>();
		List<StatisticImageListBo> statisticImageList = new ArrayList<StatisticImageListBo>();
		List<ActivetaskFormulaBo> activetaskFormulaList = new ArrayList<ActivetaskFormulaBo>();
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true ? "" : request.getParameter("studyId");
				}
				activeTaskInfoId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId")) == true ? "" : request.getParameter("activeTaskInfoId");
				typeOfActiveTask = fdahpStudyDesignerUtil.isEmpty(request.getParameter("typeOfActiveTask")) == true ? "" : request.getParameter("typeOfActiveTask");
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				activeTaskListBos = studyActiveTasksService.getAllActiveTaskTypes();
				map.addAttribute("activeTaskListBos", activeTaskListBos);
				map.addAttribute("studyBo", studyBo);
				if(StringUtils.isNotEmpty(activeTaskInfoId)){
					activeTaskBo = studyActiveTasksService.getActiveTaskById(Integer.parseInt(activeTaskInfoId));
					typeOfActiveTask = activeTaskBo.getTaskTypeId().toString();
				}else{
					activeTaskBo = new ActiveTaskBo();
					activeTaskBo.setStudyId(Integer.parseInt(studyId));
					activeTaskBo.setTaskTypeId(Integer.parseInt(typeOfActiveTask));
				}
				if(activeTaskBo!=null && StringUtils.isNotEmpty(activeTaskBo.getFrequency())){
					switch (activeTaskBo.getFrequency()) {
					case fdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME:
						timeRangeList.add(fdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_WEEK);
						timeRangeList.add(fdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_MONTH);
						break;
					case fdahpStudyDesignerConstants.FREQUENCY_TYPE_DAILY:
						timeRangeList.add(fdahpStudyDesignerConstants.MULTIPLE_TIMES_A_DAY);
						break;

					case fdahpStudyDesignerConstants.FREQUENCY_TYPE_WEEKLY:
						timeRangeList.add(fdahpStudyDesignerConstants.WEEKS_OF_THE_CURRENT_MONTH);
						break;

					case fdahpStudyDesignerConstants.FREQUENCY_TYPE_MONTHLY:
						timeRangeList.add(fdahpStudyDesignerConstants.MONTHS_OF_THE_CURRENT_YEAR);
						break;

					case fdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE:
						timeRangeList.add(fdahpStudyDesignerConstants.RUN_BASED);
						break;
					 }
				  }else{
						timeRangeList.add(fdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_WEEK);
						timeRangeList.add(fdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_MONTH);
				  }
				statisticImageList = studyActiveTasksService.getStatisticImages();
				activetaskFormulaList = studyActiveTasksService.getActivetaskFormulas();
				if(StringUtils.isNotEmpty(typeOfActiveTask) && activeTaskListBos!=null && activeTaskListBos.size()>0){
					taskMasterAttributeBos = studyActiveTasksService.getActiveTaskMasterAttributesByType(typeOfActiveTask);
					if(taskMasterAttributeBos!=null && taskMasterAttributeBos.size()>0)
						activeTaskBo.setTaskMasterAttributeBos(taskMasterAttributeBos);
					map.addAttribute("activeTaskBo", activeTaskBo);
					map.addAttribute("timeRangeList", timeRangeList);
					map.addAttribute("statisticImageList", statisticImageList);
					map.addAttribute("activetaskFormulaList", activetaskFormulaList);
					for (ActiveTaskListBo activeTaskListBo : activeTaskListBos) {
						if (StringUtils.isNotEmpty(activeTaskListBo.getTaskName()) && activeTaskListBo.getActiveTaskListId()==Integer.parseInt(typeOfActiveTask)) {
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
								mav = new ModelAndView("viewFetalStudyActiveTask",map);
								break;
							}
						}
					  }
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
	@RequestMapping("/adminStudies/saveOrUpdateActiveTaskContent.do")
	public ModelAndView saveOrUpdateActiveTaskContent(HttpServletRequest request , HttpServletResponse response,ActiveTaskBo activeTaskBo){
		logger.info("StudyActiveTasksController - saveOrUpdateActiveTaskContent - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ActiveTaskBo addActiveTaskBo = null;
		ModelMap map = new ModelMap();
		List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<ActiveTaskMasterAttributeBo>();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				if(activeTaskBo != null){
					taskMasterAttributeBos = studyActiveTasksService.getActiveTaskMasterAttributesByType(activeTaskBo.getTaskTypeId().toString());
					if(taskMasterAttributeBos!=null && taskMasterAttributeBos.size()>0)
						activeTaskBo.setTaskMasterAttributeBos(taskMasterAttributeBos);
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
			logger.error("StudyActiveTasksController - saveOrUpdateActiveTaskContent - ERROR",e);
		}
		logger.info("StudyActiveTasksController - saveOrUpdateActiveTaskContent - Ends");
		return mav;
	}
	
	/**
	 * @author Ronalin
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/deleteActiveTask.do",method = RequestMethod.POST)
	public void deleteActiveTask(HttpServletRequest request ,HttpServletResponse response){
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
	
	/** 
	  * @author Ronalin
	  * validating particular StudyTask ShortTitle and Stat Task Id
	  * @param request , {@link HttpServletRequest}
	  * @param response , {@link HttpServletResponse}
	  * @throws IOException
	  * @return void
	  */
		@RequestMapping(value="/adminStudies/validateActiveTaskShortTitleId.do",  method = RequestMethod.POST)
		public void validateActiveTaskShortTitleId(HttpServletRequest request, HttpServletResponse response) throws IOException{
			logger.info("StudyController - validateActiveTaskShortTitleId() - Starts ");
			JSONObject jsonobject = new JSONObject();
			PrintWriter out = null;
			String message = fdahpStudyDesignerConstants.FAILURE;
			boolean flag = false;
			try{
				HttpSession session = request.getSession();
				SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
				if (userSession != null) {
					String studyId = (String) request.getSession().getAttribute("studyId");
					if(StringUtils.isEmpty(studyId)){
						studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true ? "" : request.getParameter("studyId");
					}
					String activeTaskAttName = fdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskAttName")) == true?"":request.getParameter("activeTaskAttName");
					String activeTaskAttIdVal = fdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskAttIdVal")) == true?"":request.getParameter("activeTaskAttIdVal");
					flag = studyActiveTasksService.validateActiveTaskAttrById(Integer.parseInt(studyId), activeTaskAttName, activeTaskAttIdVal);
					if(flag)
						message = fdahpStudyDesignerConstants.SUCCESS;
				}
			}catch (Exception e) {
				logger.error("StudyController - validateActiveTaskShortTitleId() - ERROR ", e);
			}
			logger.info("StudyController - validateActiveTaskShortTitleId() - Ends ");
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}
}
