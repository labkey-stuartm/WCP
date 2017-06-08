/**
 * 
 */
package com.fdahpstudydesigner.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		ModelMap map = new ModelMap();
		StudyBo studyBo = null;
		String sucMsg = "";
		String errMsg = "";
		List<ActiveTaskBo> activeTasks = null;
		String activityStudyId = "";
		String actMsg = "";
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
				if(request.getSession().getAttribute(sessionStudyCount+"activeTaskInfoId") != null){
					request.getSession().removeAttribute(sessionStudyCount+"activeTaskInfoId");
				}
				if(request.getSession().getAttribute(sessionStudyCount+"actionType") != null){
					request.getSession().removeAttribute(sessionStudyCount+"actionType");
				}
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if (StringUtils.isEmpty(studyId)) {
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "0" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				} 
				String permission = (String) request.getSession().getAttribute(sessionStudyCount+"permission");
				if (StringUtils.isNotEmpty(studyId)) {
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					//Added for live version Start
					String isLive = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.IS_LIVE);
					if(StringUtils.isNotEmpty(isLive) && isLive.equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
						activityStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ACTIVE_TASK_STUDY_ID);
					}
					//Added for live version End
					if(StringUtils.isNotEmpty(activityStudyId)){
						activeTasks = studyActiveTasksService.getStudyActiveTasksByStudyId(activityStudyId);
					}else{
						activeTasks = studyActiveTasksService.getStudyActiveTasksByStudyId(studyId);
					}	
					boolean markAsComplete = true;
					actMsg = studyService.validateActivityComplete(studyId, FdahpStudyDesignerConstants.ACTIVITY_TYPE_ACTIVETASK);
					if(!actMsg.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS))
						markAsComplete = false; 
					map.addAttribute("permission", permission);
					map.addAttribute("markAsComplete", markAsComplete);
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
					map.addAttribute("activeTasks", activeTasks);
					map.addAttribute(FdahpStudyDesignerConstants.ACTIVITY_MESSAGE, actMsg);
					map.addAttribute("_S", sessionStudyCount);
					mav = new ModelAndView("studyActiveTaskListPage", map);
				}
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
	public ModelAndView viewScheduledActiveTask(HttpServletRequest request){
		logger.info("StudyActiveTaskController - viewScheduledActiveTask - Starts");
		ModelAndView mav = new ModelAndView("questionnairePage");
		ModelMap map = new ModelMap();
		String sucMsg = "";
		String errMsg = "";
		StudyBo studyBo = null;
		ActiveTaskBo activeTaskBo = null;
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
				String activeTaskId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskId"))?"":request.getParameter("activeTaskId");
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID, studyId);
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute("studyBo", studyBo);
				}
				if(StringUtils.isEmpty(activeTaskId)){
					activeTaskId = (String) request.getSession().getAttribute(sessionStudyCount+"activeTaskId");
					request.getSession().setAttribute(sessionStudyCount+"activeTaskId", activeTaskId);
				}
				if(null!=activeTaskId && !activeTaskId.isEmpty()){
					activeTaskBo=studyActiveTasksService.getActiveTaskById(Integer.valueOf(activeTaskId), studyBo.getCustomStudyId());
					if(activeTaskBo != null){
						map.addAttribute("customCount",activeTaskBo.getActiveTaskCustomScheduleBo().size());
						map.addAttribute("count",activeTaskBo.getActiveTaskFrequenciesList().size());
					}
					map.addAttribute("activeTaskBo", activeTaskBo);
				}
				map.addAttribute("_S", sessionStudyCount);
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
	public ModelAndView saveorUpdateActiveTaskSchedule(HttpServletRequest request , ActiveTaskBo activeTaskBo){
		logger.info("StudyActiveTaskController - saveorUpdateActiveTaskSchedule - Starts");
		ModelAndView mav = new ModelAndView("questionnairePage");
		List<String> dailyTimeList = new ArrayList<>();
		StudyBo studyBo  = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null && activeTaskBo != null){
				studyBo = studyService.getStudyById(activeTaskBo.getStudyId().toString(), sesObj.getUserId());
					if(activeTaskBo.getId() != null){
						activeTaskBo.setModifiedBy(sesObj.getUserId());
						activeTaskBo.setModifiedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						activeTaskBo.setCreatedBy(sesObj.getUserId());
						activeTaskBo.setCreatedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo, studyBo.getCustomStudyId());
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
		String customStudyId = "";
		List<String> dailyTimeList = new ArrayList<>();
		boolean durationFlag = true;
		String errorMessage = FdahpStudyDesignerConstants.FAILURE;
		HashMap<Integer,String> dailyTimeMapList = new HashMap<Integer,String>();
		StudyBo studyBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String activeTaskScheduleInfo = request.getParameter("activeTaskScheduleInfo");
				if(activeTaskScheduleInfo != null && !activeTaskScheduleInfo.isEmpty()){
					activeTaskBo = mapper.readValue(activeTaskScheduleInfo, ActiveTaskBo.class);
					if(activeTaskBo != null){
						studyBo = studyService.getStudyById(activeTaskBo.getStudyId().toString(), sesObj.getUserId());
						if(activeTaskBo.getId() != null){
							activeTaskBo.setModifiedBy(sesObj.getUserId());
							activeTaskBo.setModifiedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							activeTaskBo.setCreatedBy(sesObj.getUserId());
							activeTaskBo.setCreatedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
						}
						customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
						//Validation For dailyTime with duration (Start)
						/*if(StringUtils.isNotEmpty(activeTaskBo.getFrequency()) && activeTaskBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_DAILY)  
								&& activeTaskBo.getActiveTaskFrequenciesList()!=null && activeTaskBo.getActiveTaskFrequenciesList().size()>1){
							String duration = activeTaskBo.getFetalCickDuration();
							String[] hourMinute;
							Integer durationSeconds = 0;
							if(StringUtils.isNotEmpty(duration)){
								hourMinute = duration.split(":");
								durationSeconds = (Integer.valueOf(hourMinute[0]) * 60 * 60) + (Integer.valueOf(hourMinute[1])* 60);
							}
							for(ActiveTaskFrequencyBo activeTaskFrequencyBo: activeTaskBo.getActiveTaskFrequenciesList()){
								dailyTimeList.add(FdahpStudyDesignerUtil.getFormattedDate(FdahpStudyDesignerUtil.getCurrentDate()+" "+activeTaskFrequencyBo.getFrequencyTime(), 
										"yyyy-MM-dd h:mm a", FdahpStudyDesignerConstants.DB_SDF_DATE_TIME));
							}
							if(!dailyTimeList.isEmpty()){
							  Collections.sort(dailyTimeList);
							  int i =1;
							  for(String dailyTime: dailyTimeList){
								  dailyTimeMapList.put(i, dailyTime);
								  i++;
							  }
							  for(int j=1; j< dailyTimeMapList.size();j++){
								  int k = j+1;
								  if(!FdahpStudyDesignerUtil.compareDateCustomDateTime(dailyTimeMapList.get(j), dailyTimeMapList.get(k), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME
										  , durationSeconds)){
									  durationFlag = false;
									  break;
							      }
							  }
							}
						}*/
						//Validation For dailyTime with duration (End)
						//if(durationFlag){
						if(studyBo!=null){
							updateActiveTaskBo = studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo, studyBo.getCustomStudyId());
							if(updateActiveTaskBo != null){
								jsonobject.put("activeTaskId", updateActiveTaskBo.getId());
								if(updateActiveTaskBo.getActiveTaskFrequenciesBo() != null){
									jsonobject.put("activeTaskFrequenceId", updateActiveTaskBo.getActiveTaskFrequenciesBo().getId());
								}
								message = FdahpStudyDesignerConstants.SUCCESS;
							}
						}
						/*}else{
							errorMessage = FdahpStudyDesignerConstants.SCHEDULE_ERROR_MSG;
						}*/
					}
				}
			}
			jsonobject.put("message", message);
			//jsonobject.put("errorMessage", errorMessage);
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
		String sucMsg;
		String errMsg;
		try {
				SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
				if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
					String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
					String activeTaskInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId")) ? "" : request.getParameter("activeTaskInfoId");
					String actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType")) ? "":request.getParameter("actionType");
					if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG)){
						sucMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
						map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.SUC_MSG);
					}
					if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CURRENT_PAGE)){
						String currentPage = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CURRENT_PAGE);
						map.addAttribute(FdahpStudyDesignerConstants.CURRENT_PAGE, currentPage);
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CURRENT_PAGE);
					}
					if(null != request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG)){
						errMsg = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
						map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
						request.getSession().removeAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG);
					}
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					}
					if(StringUtils.isEmpty(activeTaskInfoId)) {
						activeTaskInfoId = (String) request.getSession().getAttribute(sessionStudyCount+"activeTaskInfoId");
					}else{
						request.getSession().setAttribute(sessionStudyCount+"activeTaskInfoId", activeTaskInfoId);
					}
					
					if(StringUtils.isEmpty(actionType)) {
						actionType = (String) request.getSession().getAttribute(sessionStudyCount+"actionType");
					}else{
						request.getSession().setAttribute(sessionStudyCount+"actionType", actionType);
					}
					map.addAttribute("_S", sessionStudyCount);
					if(StringUtils.isNotEmpty(studyId)){
						if(("add").equals(actionType)){
							map.addAttribute("actionPage", "add");
						}else if(("view").equals(actionType)){
							map.addAttribute("actionPage", "view");
						}else{
							map.addAttribute("actionPage", "addEdit");
						}
						studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
						activeTaskListBos = studyActiveTasksService.getAllActiveTaskTypes();
						map.addAttribute("activeTaskListBos", activeTaskListBos);
						map.addAttribute("studyBo", studyBo);
						if(StringUtils.isNotEmpty(activeTaskInfoId)){
							activeTaskBo = studyActiveTasksService.getActiveTaskById(Integer.parseInt(activeTaskInfoId), studyBo.getCustomStudyId());
							map.addAttribute("activeTaskBo", activeTaskBo);
						}
						mav = new ModelAndView("viewStudyActiveTask",map);
					} else {
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
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		ActiveTaskBo activeTaskBo = new ActiveTaskBo();
		StudyBo studyBo = null;
		String activeTaskInfoId;
		String typeOfActiveTask; 
		String actionType;
		List<ActiveTaskListBo> activeTaskListBos = new ArrayList<>();
		List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<>();
		List<String> timeRangeList = new ArrayList<>();
		List<StatisticImageListBo> statisticImageList = new ArrayList<>();
		List<ActivetaskFormulaBo> activetaskFormulaList = new ArrayList<>();
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				}
				activeTaskInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId")) ? "" : request.getParameter("activeTaskInfoId");
				typeOfActiveTask = FdahpStudyDesignerUtil.isEmpty(request.getParameter("typeOfActiveTask")) ? "" : request.getParameter("typeOfActiveTask");
				actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType")) ?"":request.getParameter("actionType");
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				activeTaskListBos = studyActiveTasksService.getAllActiveTaskTypes();
				map.addAttribute("activeTaskListBos", activeTaskListBos);
				map.addAttribute("studyBo", studyBo);
				if(StringUtils.isNotEmpty(activeTaskInfoId)){
					activeTaskBo = studyActiveTasksService.getActiveTaskById(Integer.parseInt(activeTaskInfoId), studyBo.getCustomStudyId());
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
					map.addAttribute("_S", sessionStudyCount);
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
	public ModelAndView saveOrUpdateActiveTaskContent(HttpServletRequest request , ActiveTaskBo activeTaskBo){
		logger.info("StudyActiveTasksController - saveOrUpdateActiveTaskContent - Starts");
		ModelAndView mav = new ModelAndView("redirect:/adminStudies/studyList.do");
		Map<String,String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		ActiveTaskBo addActiveTaskBo = null;
		List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<>();
		String buttonText = "";
		Integer activeTaskInfoId = 0;
		String currentPage = null; 
		String customStudyId = "";
		ModelMap map = new ModelMap();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			buttonText = FdahpStudyDesignerUtil.isEmpty(request.getParameter("buttonText")) ? "" : request.getParameter("buttonText");
			currentPage = FdahpStudyDesignerUtil.isEmpty(request.getParameter("currentPage")) ? "" : request.getParameter("currentPage");
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
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
					customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
					addActiveTaskBo = studyActiveTasksService.saveOrUpdateActiveTask(activeTaskBo, sesObj,customStudyId);
					map.addAttribute("_S", sessionStudyCount);
					if(addActiveTaskBo != null){
						if(addActiveTaskBo.getId()!=null){
							activeTaskInfoId = addActiveTaskBo.getId();
						}
						request.getSession().setAttribute(sessionStudyCount+"activeTaskInfoId", activeTaskInfoId.toString());
						if(StringUtils.isNotEmpty(buttonText) && 
								buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
							  request.getSession().setAttribute(sessionStudyCount+"sucMsg", "Active task updated successfully.");
							  return new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do", map);
							  
						}else{
							  request.getSession().setAttribute(sessionStudyCount+"sucMsg", propMap.get("save.study.success.message"));
							  request.getSession().setAttribute(sessionStudyCount + "currentPage", currentPage);
							  return new ModelAndView("redirect:/adminStudies/viewActiveTask.do"+"#"+currentPage, map);
						}
					}else{
						request.getSession().setAttribute(sessionStudyCount+FdahpStudyDesignerConstants.ERR_MSG, "Task not added successfully.");
						mav = new ModelAndView("redirect:/adminStudies/viewStudyActiveTasks.do"+"#"+currentPage, map);
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
		String customStudyId = "";
		String actMsg = "";
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
			if(sesObj!=null && sesObj.getStudySession() != null && sesObj.getStudySession().contains(sessionStudyCount)){
				String activeTaskInfoId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskInfoId"))?"":request.getParameter("activeTaskInfoId");
				String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
				if(!activeTaskInfoId.isEmpty() && !studyId.isEmpty()){
					message = studyActiveTasksService.deleteActiveTask(Integer.valueOf(activeTaskInfoId),Integer.valueOf(studyId),sesObj,customStudyId);
				}
				boolean markAsComplete = true;
				actMsg= studyService.validateActivityComplete(studyId, FdahpStudyDesignerConstants.ACTIVITY_TYPE_ACTIVETASK);
				if(!actMsg.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS))
					markAsComplete = false; 
				jsonobject.put("markAsComplete", markAsComplete);
				jsonobject.put(FdahpStudyDesignerConstants.ACTIVITY_MESSAGE, actMsg);
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
			PrintWriter out;
			String message = FdahpStudyDesignerConstants.FAILURE;
			boolean flag = false;
			try{
				HttpSession session = request.getSession();
				SessionObject userSession = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				Integer sessionStudyCount = StringUtils.isNumeric(request.getParameter("_S")) ? Integer.parseInt(request.getParameter("_S")) : 0 ;
				if(userSession!=null && userSession.getStudySession() != null && userSession.getStudySession().contains(sessionStudyCount)){
					String studyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.STUDY_ID);
					if(StringUtils.isEmpty(studyId)){
						studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					}
					String customStudyId = (String) request.getSession().getAttribute(sessionStudyCount+FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
					if(StringUtils.isEmpty(customStudyId)){
						customStudyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID)) ? "" : request.getParameter(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID);
					}
					String activeTaskAttName = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskAttName"))?"":request.getParameter("activeTaskAttName");
					String activeTaskAttIdVal = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskAttIdVal"))?"":request.getParameter("activeTaskAttIdVal");
					String activeTaskAttIdName = FdahpStudyDesignerUtil.isEmpty(request.getParameter("activeTaskAttIdName"))?"":request.getParameter("activeTaskAttIdName");
					flag = studyActiveTasksService.validateActiveTaskAttrById(Integer.parseInt(studyId), activeTaskAttName, activeTaskAttIdVal, activeTaskAttIdName, customStudyId);
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
		
		@RequestMapping("/adminStudies/activeTAskMarkAsCompleted.do")
		public ModelAndView activeTAskMarkAsCompleted(HttpServletRequest request) {
			logger.info("StudyActiveTasksController - activeTAskMarkAsCompleted() - Starts");
			ModelAndView mav = new ModelAndView("redirect:viewStudyActiveTasks.do");
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
					message = studyService.markAsCompleted(Integer.parseInt(studyId) , FdahpStudyDesignerConstants.ACTIVETASK_LIST,sesObj,customStudyId);
					map.addAttribute("_S", sessionStudyCount);
					if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
						request.getSession().setAttribute(sessionStudyCount+"sucMsg", propMap.get("complete.study.success.message"));
						mav = new ModelAndView("redirect:/adminStudies/getResourceList.do", map);
					}else{
						request.getSession().setAttribute(sessionStudyCount+"errMsg", "Unable to mark as complete.");
						mav = new ModelAndView("redirect:viewStudyActiveTasks.do", map);
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
					timeRangeList.addAll(getTimeRangeListForFrequencyTypeDaily(activeTaskBo));
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
		
		private List<String> getTimeRangeListForFrequencyTypeDaily(ActiveTaskBo activeTaskBo) {
			List<String> timeRangeList = new ArrayList<>();
			if(activeTaskBo.getActiveTaskCustomScheduleBo()!=null && activeTaskBo.getActiveTaskFrequenciesList().size()>1){
				timeRangeList.add(FdahpStudyDesignerConstants.MULTIPLE_TIMES_A_DAY);
			} else {
				timeRangeList.add(FdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_WEEK);
				timeRangeList.add(FdahpStudyDesignerConstants.DAYS_OF_THE_CURRENT_MONTH);
			}
			return timeRangeList;
		}
}
