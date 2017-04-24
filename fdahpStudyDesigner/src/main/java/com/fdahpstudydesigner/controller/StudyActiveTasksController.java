/**
 * 
 */
package com.fdahpstudydesigner.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import com.fdahpstudydesigner.bo.ActiveTaskBo;
import com.fdahpstudydesigner.bo.ActiveTaskListBo;
import com.fdahpstudydesigner.bo.ActiveTaskMasterAttributeBo;
import com.fdahpstudydesigner.bo.ActivetaskFormulaBo;
import com.fdahpstudydesigner.bo.StatisticImageListBo;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.service.StudyActiveTasksService;
import com.fdahpstudydesigner.service.StudyService;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

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
			if(request.getSession().getAttribute("activeTaskInfoId") != null){
				request.getSession().removeAttribute("activeTaskInfoId");
			}
			if(request.getSession().getAttribute("actionType") != null){
				request.getSession().removeAttribute("actionType");
			}
			String studyId = (String) request.getSession().getAttribute("studyId");
			if (StringUtils.isEmpty(studyId)) {
				studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) ? "0" : request.getParameter("studyId");
			} 
			String permission = (String) request.getSession().getAttribute("permission");
			if (StringUtils.isNotEmpty(studyId)) {
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				activeTasks = studyActiveTasksService.getStudyActiveTasksByStudyId(studyId);
				boolean markAsComplete = true;
				if(activeTasks != null && activeTasks.size() > 0){
					for(ActiveTaskBo activeTaskBo : activeTasks){
						if(!activeTaskBo.isAction()){
							markAsComplete = false;
							break;
						}
					}
				}
				map.addAttribute("permission", permission);
				map.addAttribute("markAsComplete", markAsComplete);
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
	
	/**
	 * Navigate to the scheduled active task page 
	 * @author Vivek 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @param response , {@link HttpServletResponse}
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value="/adminStudies/viewScheduledActiveTask.do")
	public ModelAndView viewScheduledActiveTask(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyActiveTaskController - viewScheduledActiveTask - Starts");
		ModelAndView mav = new ModelAndView("questionnairePage");
		ModelMap map = new ModelMap();
		String sucMsg = "";
		String errMsg = "";
		StudyBo studyBo = null;
		ActiveTaskBo activeTaskBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
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
				String activeTaskId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskId"))?"":request.getParameter("activeTaskId");
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) ?"":request.getParameter("studyId");
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
	
	/**
	 * Mark as complete action to the active task schedule 
	 * @author Vivek 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @param response , {@link HttpServletResponse}
	 * 
	 * @return {@link ModelAndView}
	 */
	@RequestMapping(value="/adminStudies/saveOrUpdateActiveTaskSchedule.do",method=RequestMethod.POST)
	public ModelAndView saveorUpdateActiveTaskSchedule(HttpServletRequest request , HttpServletResponse response,ActiveTaskBo activeTaskBo){
		logger.info("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Starts");
		ModelAndView mav = new ModelAndView("questionnairePage");
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(activeTaskBo != null){
					if(activeTaskBo.getId() != null){
						activeTaskBo.setModifiedBy(sesObj.getUserId());
						activeTaskBo.setModifiedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						activeTaskBo.setCreatedBy(sesObj.getUserId());
						activeTaskBo.setCreatedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo);
				}
			}
			mav =  new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do");
		}catch(Exception e){ 
			logger.error("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Error",e);
		}
		logger.info("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Ends");
		return mav;
	}
	
	/**
	 * Save action to the active task schedule 
	 * @author Vivek 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @param response , {@link HttpServletResponse}
	 * 
	 * @return 
	 */
	@RequestMapping(value="/adminStudies/saveActiveTaskSchedule.do",method=RequestMethod.POST)
	public void saveActiveTaskSchedule(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyActiveTasksController - saveQuestionnaireSchedule - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		ActiveTaskBo updateActiveTaskBo = null;
		ObjectMapper mapper = new ObjectMapper();
		ActiveTaskBo activeTaskBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String activeTaskScheduleInfo = request.getParameter("activeTaskScheduleInfo");
				if(activeTaskScheduleInfo != null && !activeTaskScheduleInfo.isEmpty()){
					activeTaskBo = mapper.readValue(activeTaskScheduleInfo, ActiveTaskBo.class);
					if(activeTaskBo != null){
						if(activeTaskBo.getId() != null){
							activeTaskBo.setModifiedBy(sesObj.getUserId());
							activeTaskBo.setModifiedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							activeTaskBo.setCreatedBy(sesObj.getUserId());
							activeTaskBo.setCreatedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
						}
						updateActiveTaskBo = studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo);
						if(updateActiveTaskBo != null){
							jsonobject.put("activeTaskId", updateActiveTaskBo.getId());
							if(updateActiveTaskBo.getActiveTaskFrequenciesBo() != null){
								jsonobject.put("activeTaskFrequenceId", updateActiveTaskBo.getActiveTaskFrequenciesBo().getId());
							}
							message = FdahpStudyDesignerConstants.SUCCESS;
						}
					}
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyActiveTasksController - saveQuestionnaireSchedule - Error",e);
		}
		logger.info("StudyActiveTasksController - saveQuestionnaireSchedule - Ends");
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
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		ActiveTaskBo activeTaskBo = null;
		StudyBo studyBo = null;
		List<ActiveTaskListBo> activeTaskListBos = new ArrayList<>();
		try {
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				String studyId = (String) request.getSession().getAttribute("studyId");
				String activeTaskInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId")) ? "" : request.getParameter("activeTaskInfoId");
				String actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType")) ? "":request.getParameter("actionType");
				
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) ? "" : request.getParameter("studyId");
				}
				if(StringUtils.isEmpty(activeTaskInfoId)) {
					activeTaskInfoId = (String) request.getSession().getAttribute("activeTaskInfoId");
					//request.getSession().removeAttribute("activeTaskInfoId");
				}else{
					request.getSession().setAttribute("activeTaskInfoId", activeTaskInfoId);
				}
				
				if(StringUtils.isEmpty(actionType)) {
					actionType = (String) request.getSession().getAttribute("actionType");
//					request.getSession().removeAttribute("actionType");
				}else{
					request.getSession().setAttribute("actionType", actionType);
				}

				if(StringUtils.isNotEmpty(studyId)){
					if(actionType.equals("add")){
						map.addAttribute("actionPage", "add");
					}else if(actionType.equals("view")){
						map.addAttribute("actionPage", "view");
					}else{
						map.addAttribute("actionPage", "addEdit");
					}
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					activeTaskListBos = studyActiveTasksService.getAllActiveTaskTypes();
					map.addAttribute("activeTaskListBos", activeTaskListBos);
					map.addAttribute("studyBo", studyBo);
					if(StringUtils.isNotEmpty(activeTaskInfoId)){
						activeTaskBo = studyActiveTasksService.getActiveTaskById(Integer.parseInt(activeTaskInfoId));
						map.addAttribute("activeTaskBo", activeTaskBo);
					}
					mav = new ModelAndView("viewStudyActiveTask",map);
				} else {
					mav = new ModelAndView("redirect:unauthorized.do");
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
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		ActiveTaskBo activeTaskBo = new ActiveTaskBo();
		StudyBo studyBo = null;
		String activeTaskInfoId="", typeOfActiveTask = "", actionType= "";
		List<ActiveTaskListBo> activeTaskListBos = new ArrayList<>();
		List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<>();
		List<String> timeRangeList = new ArrayList<>();
		List<StatisticImageListBo> statisticImageList = new ArrayList<>();
		List<ActivetaskFormulaBo> activetaskFormulaList = new ArrayList<>();
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) ? "" : request.getParameter("studyId");
				}
				activeTaskInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId")) ? "" : request.getParameter("activeTaskInfoId");
				typeOfActiveTask = FdahpStudyDesignerUtil.isEmpty(request.getParameter("typeOfActiveTask")) ? "" : request.getParameter("typeOfActiveTask");
				actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType")) ?"":request.getParameter("actionType");
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
				if(StringUtils.isNotEmpty(actionType)){
					activeTaskBo.setActionPage(actionType);
					map.addAttribute("actionPage", actionType);
				}
				timeRangeList = this.getTimeRangeList(activeTaskBo);
				statisticImageList = studyActiveTasksService.getStatisticImages();
				activetaskFormulaList = studyActiveTasksService.getActivetaskFormulas();
				if(StringUtils.isNotEmpty(typeOfActiveTask) && activeTaskListBos!=null && !activeTaskListBos.isEmpty()){
					taskMasterAttributeBos = studyActiveTasksService.getActiveTaskMasterAttributesByType(typeOfActiveTask);
					if(taskMasterAttributeBos!=null && !taskMasterAttributeBos.isEmpty())
						activeTaskBo.setTaskMasterAttributeBos(taskMasterAttributeBos);
					map.addAttribute("activeTaskBo", activeTaskBo);
					map.addAttribute("timeRangeList", timeRangeList);
					map.addAttribute("statisticImageList", statisticImageList);
					map.addAttribute("activetaskFormulaList", activetaskFormulaList);
					for (ActiveTaskListBo activeTaskListBo : activeTaskListBos) {
						if (StringUtils.isNotEmpty(activeTaskListBo.getTaskName()) && activeTaskListBo.getActiveTaskListId()==Integer.parseInt(typeOfActiveTask)) {
							switch (activeTaskListBo.getTaskName()) {
							case FdahpStudyDesignerConstants.FETAL_KICK_COUNTER:
								 mav = new ModelAndView("viewFetalStudyActiveTask",map);
								 break;
							case FdahpStudyDesignerConstants.TOWER_OF_HANOI:
								mav = new ModelAndView("viewTowerStudyActiveTask",map);
	 							break;
							case FdahpStudyDesignerConstants.SPATIAL_SPAN_MEMORY:
								mav = new ModelAndView("viewSpatialStudyActiveTask",map);
								break;
							default:
								mav = new ModelAndView("viewFetalStudyActiveTask",map);
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
		Map<String,String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		ActiveTaskBo addActiveTaskBo = null;
		List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<>();
		String buttonText = "";
		Integer activeTaskInfoId = 0;
		String currentPage = null; 
		String actionPage = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			buttonText = FdahpStudyDesignerUtil.isEmpty(request.getParameter("buttonText")) ? "" : request.getParameter("buttonText");
			currentPage = FdahpStudyDesignerUtil.isEmpty(request.getParameter("currentPage")) ? "" : "#"+request.getParameter("currentPage");
			actionPage = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionPage")) ? "" : request.getParameter("actionPage");
			if(sesObj!=null){
				if(activeTaskBo != null){
					activeTaskBo.setButtonText(buttonText);
					taskMasterAttributeBos = studyActiveTasksService.getActiveTaskMasterAttributesByType(activeTaskBo.getTaskTypeId().toString());
					if(taskMasterAttributeBos!=null && !taskMasterAttributeBos.isEmpty())
						activeTaskBo.setTaskMasterAttributeBos(taskMasterAttributeBos);
					if(StringUtils.isNotBlank(buttonText)){
						if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.SAVE_BUTTON)){
							activeTaskBo.setAction(false);
						}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
							activeTaskBo.setAction(true);
						}
					}
					addActiveTaskBo = studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo, sesObj);
					if(addActiveTaskBo != null){
						if(addActiveTaskBo.getId()!=null){
							activeTaskInfoId = addActiveTaskBo.getId();
						}
						if(StringUtils.isNotEmpty(buttonText) && 
								buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
							  request.getSession().setAttribute("sucMsg", propMap.get("complete.study.success.message"));
							  return new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do");
							  
						}else{
							  request.getSession().setAttribute("sucMsg", propMap.get("save.study.success.message"));
							  return new ModelAndView("redirect:/adminStudies/viewActiveTask.do"+currentPage);
						}
					}else{
						request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, "Task not added successfully.");
						mav = new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do"+currentPage);
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
		String message = FdahpStudyDesignerConstants.FAILURE;
		List<ActiveTaskBo> activeTasks = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String activeTaskInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId"))?"":request.getParameter("activeTaskInfoId");
				String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId"))?"":request.getParameter("studyId");
				if(!activeTaskInfoId.isEmpty() && !studyId.isEmpty()){
					message = studyActiveTasksService.deleteActiveTask(Integer.valueOf(activeTaskInfoId),Integer.valueOf(studyId));
				}
				activeTasks = studyActiveTasksService.getStudyActiveTasksByStudyId(studyId);
				boolean markAsComplete = true;
				if(activeTasks != null && activeTasks.size() > 0){
					for(ActiveTaskBo activeTaskBo : activeTasks){
						if(!activeTaskBo.isAction()){
							markAsComplete = false;
							break;
						}
					}
				}
				jsonobject.put("markAsComplete", markAsComplete);
				
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
			logger.info("StudyActiveTasksController - validateActiveTaskShortTitleId() - Starts ");
			JSONObject jsonobject = new JSONObject();
			PrintWriter out = null;
			String message = FdahpStudyDesignerConstants.FAILURE;
			boolean flag = false;
			try{
				HttpSession session = request.getSession();
				SessionObject userSession = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				if (userSession != null) {
					String studyId = (String) request.getSession().getAttribute("studyId");
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) ? "" : request.getParameter("studyId");
					}
					String activeTaskAttName = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskAttName"))?"":request.getParameter("activeTaskAttName");
					String activeTaskAttIdVal = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskAttIdVal"))?"":request.getParameter("activeTaskAttIdVal");
					String activeTaskAttIdName = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskAttIdName"))?"":request.getParameter("activeTaskAttIdName");
					flag = studyActiveTasksService.validateActiveTaskAttrById(Integer.parseInt(studyId), activeTaskAttName, activeTaskAttIdVal, activeTaskAttIdName);
					if(flag)
						message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}catch (Exception e) {
				logger.error("StudyActiveTasksController - validateActiveTaskShortTitleId() - ERROR ", e);
			}
			logger.info("StudyActiveTasksController - validateActiveTaskShortTitleId() - Ends ");
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}
		
		@SuppressWarnings("unused")
		@RequestMapping("/adminStudies/activeTAskMarkAsCompleted.do")
		public ModelAndView activeTAskMarkAsCompleted(HttpServletRequest request) {
			logger.info("StudyActiveTasksController - activeTAskMarkAsCompleted() - Starts");
			ModelAndView mav = new ModelAndView("redirect:viewStudyActiveTasks.do");
			ModelMap map = new ModelMap();
			String message = FdahpStudyDesignerConstants.FAILURE;
			@SuppressWarnings("unchecked")
			Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
			try {
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				if(sesObj!=null){
					String studyId = (String) request.getSession().getAttribute("studyId");
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) ? "" : request.getParameter("studyId");
					}
					message = studyService.markAsCompleted(Integer.parseInt(studyId) , FdahpStudyDesignerConstants.ACTIVETASK_LIST,sesObj);	
					if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
						request.getSession().setAttribute("sucMsg", propMap.get("complete.study.success.message"));
						mav = new ModelAndView("redirect:/adminStudies/getResourceList.do");
					}else{
						request.getSession().setAttribute("errMsg", "Unable to mark as complete.");
						mav = new ModelAndView("redirect:viewStudyActiveTasks.do");
					}
				}
			} catch (Exception e) {
				logger.error("StudyActiveTasksController - consentMarkAsCompleted() - ERROR", e);
			}
			logger.info("StudyActiveTasksController - consentMarkAsCompleted() - Ends");
			return mav;
		}
		
		public List<String> getTimeRangeList(ActiveTaskBo activeTaskBo){
			List<String> timeRangeList = new ArrayList<>();
			if(activeTaskBo!=null && StringUtils.isNotEmpty(activeTaskBo.getFrequency())){
				switch (activeTaskBo.getFrequency()) {
				case FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME:
					timeRangeList.add(FdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_WEEK);
					timeRangeList.add(FdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_MONTH);
					break;
				case FdahpStudyDesignerConstants.FREQUENCY_TYPE_DAILY:
					if(activeTaskBo.getActiveTaskCustomScheduleBo()!=null && activeTaskBo.getActiveTaskCustomScheduleBo().size()>1){
						timeRangeList.add(FdahpStudyDesignerConstants.MULTIPLE_TIMES_A_DAY);
					}else{
						timeRangeList.add(FdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_WEEK);
						timeRangeList.add(FdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_MONTH);
					}
					break;

				case FdahpStudyDesignerConstants.FREQUENCY_TYPE_WEEKLY:
					timeRangeList.add(FdahpStudyDesignerConstants.WEEKS_OF_THE_CURRENT_MONTH);
					break;

				case FdahpStudyDesignerConstants.FREQUENCY_TYPE_MONTHLY:
					timeRangeList.add(FdahpStudyDesignerConstants.MONTHS_OF_THE_CURRENT_YEAR);
					break;

				case FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE:
					timeRangeList.add(FdahpStudyDesignerConstants.RUN_BASED);
					break;
				default: break;
				 }
			  }else{
					timeRangeList.add(FdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_WEEK);
					timeRangeList.add(FdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_MONTH);
			  }
			return timeRangeList;
		}
}
