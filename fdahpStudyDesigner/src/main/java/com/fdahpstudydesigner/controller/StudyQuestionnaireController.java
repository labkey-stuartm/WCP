package com.fdahpstudydesigner.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpstudydesigner.bean.QuestionnaireStepBean;
import com.fdahpstudydesigner.bo.ActivetaskFormulaBo;
import com.fdahpstudydesigner.bo.InstructionsBo;
import com.fdahpstudydesigner.bo.QuestionResponseSubTypeBo;
import com.fdahpstudydesigner.bo.QuestionResponseTypeMasterInfoBo;
import com.fdahpstudydesigner.bo.QuestionnaireBo;
import com.fdahpstudydesigner.bo.QuestionnairesStepsBo;
import com.fdahpstudydesigner.bo.QuestionsBo;
import com.fdahpstudydesigner.bo.StatisticImageListBo;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.service.StudyActiveTasksService;
import com.fdahpstudydesigner.service.StudyQuestionnaireService;
import com.fdahpstudydesigner.service.StudyService;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

/**
 * 
 * @author Vivek
 *
 */
@Controller
public class StudyQuestionnaireController {
private static Logger logger = Logger.getLogger(StudyQuestionnaireController.class.getName());
	
	
	@Autowired
	private StudyService studyService;
	
	@Autowired
	private StudyQuestionnaireService studyQuestionnaireService;
	
	@Autowired
	private StudyActiveTasksService studyActiveTasksService; 
	
	
	/**
	 * view Questionnaires page
	 * @author Vivek 
	 * 
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView}
	 */
	@RequestMapping("/adminStudies/viewStudyQuestionnaires.do")
	public ModelAndView viewStudyQuestionnaires(HttpServletRequest request) {
		logger.info("StudyQuestionnaireController - viewStudyQuestionnaires - Starts");
		ModelAndView mav = new ModelAndView("redirect:viewBasicInfo.do");
		ModelMap map = new ModelMap();
		StudyBo studyBo = null;
		String sucMsg = "";
		String errMsg = "";
		List<QuestionnaireBo> questionnaires = null;
		try {
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			request.getSession().removeAttribute("questionnaireId");
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
			
			String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
			String permission = (String) request.getSession().getAttribute("permission");
			if (StringUtils.isEmpty(studyId)) {
				studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) == true ? "0" : request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
			} 
			if (StringUtils.isNotEmpty(studyId)) {
				request.getSession().removeAttribute("actionType");
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				questionnaires = studyQuestionnaireService.getStudyQuestionnairesByStudyId(studyId);
				boolean markAsComplete = true;
				if(questionnaires != null){
					for(QuestionnaireBo questionnaireBo : questionnaires){
						if(questionnaireBo.getStatus() != null && !questionnaireBo.getStatus()){
							markAsComplete = false;
						}
					}
					
				}
				if(markAsComplete){
					markAsComplete = studyQuestionnaireService.isQuestionnairesCompleted(Integer.valueOf(studyId));
				}
				map.addAttribute("markAsComplete", markAsComplete);
				if(!markAsComplete){
					studyService.markAsCompleted(Integer.valueOf(studyId), FdahpStudyDesignerConstants.QUESTIONNAIRE, false, sesObj);
				}
			} 
			map.addAttribute("permission", permission);
			map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
			map.addAttribute("questionnaires", questionnaires);
			mav = new ModelAndView("studyQuestionaryListPage", map);
		} catch (Exception e) {
			logger.error("StudyQuestionnaireController - viewStudyQuestionnaires - ERROR", e);
		}
		logger.info("StudyQuestionnaireController - viewStudyQuestionnaires - Ends");
		return mav;
	}
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/adminStudies/instructionsStep.do")
	public ModelAndView getInstructionsPage(HttpServletRequest request , HttpServletResponse response){
		logger.info("StudyQuestionnaireController - getInstructionsPage - Starts");
		ModelAndView mav = new ModelAndView("instructionsStepPage");
		String sucMsg = "";
		String errMsg = "";
		ModelMap map = new ModelMap();
		InstructionsBo instructionsBo = null;
		QuestionnaireBo questionnaireBo = null;
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
			String instructionId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("instructionId")) == true?"":request.getParameter("instructionId");
			String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
			String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
			
			request.getSession().removeAttribute("actionTypeForQuestionPage");
			String actionType = (String) request.getSession().getAttribute("actionType");
			if(StringUtils.isEmpty(actionType)){
				actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType"))? "" : request.getParameter("actionType");
			}
			
			String actionTypeForQuestionPage = (String) request.getSession().getAttribute("actionTypeForQuestionPage");
			if(StringUtils.isEmpty(actionTypeForQuestionPage)){
				actionTypeForQuestionPage = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionTypeForQuestionPage"))? "" : request.getParameter("actionTypeForQuestionPage");
				if("edit".equals(actionTypeForQuestionPage)){
					request.getSession().setAttribute("actionTypeForQuestionPage", "edit");
				}else if("view".equals(actionTypeForQuestionPage)){
					request.getSession().setAttribute("actionTypeForQuestionPage", "view");
				}else{
					request.getSession().setAttribute("actionTypeForQuestionPage", "add");
				}
			}
			
			if(StringUtils.isEmpty(studyId)){
				studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) == true?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				request.getSession().setAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
			}
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
			}
			if(StringUtils.isEmpty(instructionId)){
				instructionId = (String) request.getSession().getAttribute("instructionId");
				request.getSession().setAttribute("instructionId", instructionId);
			}
			if(StringUtils.isEmpty(questionnaireId)){
				questionnaireId = (String) request.getSession().getAttribute("questionnaireId");
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(StringUtils.isNotEmpty(questionnaireId)){
				request.getSession().removeAttribute("actionType");
				questionnaireBo = studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
				if("edit".equals(actionType)){
					request.getSession().setAttribute("actionType", "edit");
				}else if("view".equals(actionType)){
					request.getSession().setAttribute("actionType", "view");
				}else{
					request.getSession().setAttribute("actionType", "add");
				}
				request.getSession().setAttribute("questionnaireId", questionnaireId);
				map.addAttribute("questionnaireBo", questionnaireBo);
			}
			if(instructionId!= null && !instructionId.isEmpty()){
				instructionsBo = studyQuestionnaireService.getInstructionsBo(Integer.valueOf(instructionId));
				if(instructionsBo != null && instructionsBo.getQuestionnairesStepsBo() != null){
					List<QuestionnairesStepsBo> questionnairesStepsList = studyQuestionnaireService.getQuestionnairesStepsList(instructionsBo.getQuestionnairesStepsBo().getQuestionnairesId(), instructionsBo.getQuestionnairesStepsBo().getSequenceNo());
					map.addAttribute("destinationStepList", questionnairesStepsList);
				}
				map.addAttribute("instructionsBo", instructionsBo);
				request.getSession().setAttribute("instructionId", instructionId);
			}
			map.addAttribute("questionnaireId", questionnaireId);
			mav = new ModelAndView("instructionsStepPage",map);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - getInstructionsPage - Error",e);
		}
		logger.info("StudyQuestionnaireController - getInstructionsPage - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 * @param instructionsBo
	 * @return
	 */
	@RequestMapping("/adminStudies/saveOrUpdateInstructionStep.do")
	public ModelAndView saveOrUpdateInstructionStep(HttpServletRequest request ,HttpServletResponse response,InstructionsBo instructionsBo){
		logger.info("StudyQuestionnaireController - saveOrUpdateInstructionStep - Starts");
		ModelAndView mav = new ModelAndView("instructionsStepPage");
		ModelMap map = new ModelMap();
		InstructionsBo addInstructionsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(instructionsBo != null){
					if(instructionsBo.getId() != null){
						instructionsBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						instructionsBo.setModifiedBy(sesObj.getUserId());
					}else{
						instructionsBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						instructionsBo.setCreatedBy(sesObj.getUserId());
					}
					if(instructionsBo.getQuestionnairesStepsBo() != null){
						if(instructionsBo.getQuestionnairesStepsBo().getStepId() != null){
							instructionsBo.getQuestionnairesStepsBo().setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
							instructionsBo.getQuestionnairesStepsBo().setModifiedBy(sesObj.getUserId());
						}else{
							instructionsBo.getQuestionnairesStepsBo().setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
							instructionsBo.getQuestionnairesStepsBo().setCreatedBy(sesObj.getUserId());
						}
					}
					addInstructionsBo= studyQuestionnaireService.saveOrUpdateInstructionsBo(instructionsBo);
				}
				if(addInstructionsBo != null){
					if(instructionsBo.getId() != null){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, FdahpStudyDesignerConstants.INSTRUCTION_UPDATED_SUCCESSFULLY);
					}else{
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, FdahpStudyDesignerConstants.INSTRUCTION_ADDED_SUCCESSFULLY);
					}
					mav = new ModelAndView("redirect:/adminStudies/viewQuestionnaire.do",map);
				}else{
					request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, FdahpStudyDesignerConstants.INSTRUCTION_UPDATED_SUCCESSFULLY);
					mav = new ModelAndView("redirect:/adminStudies/instructionsStep.do", map);
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveOrUpdateInstructionStep - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveOrUpdateInstructionStep - Ends");
		return mav;
	}
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/saveInstructionStep.do")
	public void saveInstructionStep(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - saveInstructionStep - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		InstructionsBo instructionsBo = null;
		ObjectMapper mapper = new ObjectMapper();
		InstructionsBo addInstructionsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String instructionsInfo = request.getParameter("instructionsInfo");
				if(null != instructionsInfo){
					instructionsBo = mapper.readValue(instructionsInfo, InstructionsBo.class);
					if(instructionsBo != null){
						if(instructionsBo.getId()!= null){
							instructionsBo.setModifiedBy(sesObj.getUserId());
							instructionsBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							instructionsBo.setCreatedBy(sesObj.getUserId());
							instructionsBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
							instructionsBo.setActive(true);
						}
						if(instructionsBo.getQuestionnairesStepsBo() != null){
							if(instructionsBo.getQuestionnairesStepsBo().getStepId() != null){
								instructionsBo.getQuestionnairesStepsBo().setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								instructionsBo.getQuestionnairesStepsBo().setModifiedBy(sesObj.getUserId());
							}else{
								instructionsBo.getQuestionnairesStepsBo().setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								instructionsBo.getQuestionnairesStepsBo().setCreatedBy(sesObj.getUserId());
							}
						}
						addInstructionsBo = studyQuestionnaireService.saveOrUpdateInstructionsBo(instructionsBo);
					}
				}
				if(addInstructionsBo != null){
					jsonobject.put("instructionId", addInstructionsBo.getId());
					jsonobject.put("stepId", addInstructionsBo.getQuestionnairesStepsBo().getStepId());
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveInstructionStep - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveInstructionStep - Ends");
	}
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/adminStudies/viewQuestionnaire.do")
	public ModelAndView getQuestionnairePage(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - getQuestionnairePage - Starts");
		ModelAndView mav = new ModelAndView("questionnairePage");
		ModelMap map = new ModelMap();
		String sucMsg = "";
		String errMsg = "";
		StudyBo studyBo = null;
		QuestionnaireBo questionnaireBo = null;
		Map<Integer, QuestionnaireStepBean> qTreeMap = new TreeMap<Integer, QuestionnaireStepBean>();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				request.getSession().removeAttribute("actionTypeForQuestionPage");
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.INSTRUCTION_ID);
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.FORM_ID);
				request.getSession().removeAttribute(FdahpStudyDesignerConstants.QUESTION_ID);
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
				String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
				String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
				String permission = (String) request.getSession().getAttribute("permission");
				
				String actionType = (String) request.getSession().getAttribute("actionType");
				if(StringUtils.isEmpty(actionType)){
					actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType"))? "" : request.getParameter("actionType");
				}
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					request.getSession().setAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
				}
				if(StringUtils.isEmpty(questionnaireId)){
					questionnaireId = (String) request.getSession().getAttribute("questionnaireId");
					request.getSession().setAttribute("questionnaireId", questionnaireId);
				}
				if(null!=questionnaireId && !questionnaireId.isEmpty()){
					questionnaireBo=studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
					if(questionnaireBo != null){
						map.addAttribute("customCount",questionnaireBo.getQuestionnaireCustomScheduleBo().size());
						map.addAttribute("count",questionnaireBo.getQuestionnairesFrequenciesList().size());
						qTreeMap = studyQuestionnaireService.getQuestionnaireStepList(questionnaireBo.getId());
						if(qTreeMap != null){
							boolean isDone =true;
							for(Entry<Integer, QuestionnaireStepBean> entry : qTreeMap.entrySet()){
								 QuestionnaireStepBean questionnaireStepBean = entry.getValue();
								 if(questionnaireStepBean.getStatus() != null && !questionnaireStepBean.getStatus()){
									 isDone = false;
									 break;
								 }
								 if(entry.getValue().getFromMap() != null){
									 for(Entry<Integer, QuestionnaireStepBean> entryKey : entry.getValue().getFromMap().entrySet()){
										 if(!entryKey.getValue().getStatus()){
											 isDone = false;
											 break;
										 }
									 }
								 }
							 }
							map.addAttribute("isDone", isDone);
							if(!isDone && StringUtils.isNotEmpty(studyId)){
									studyService.markAsCompleted(Integer.valueOf(studyId), FdahpStudyDesignerConstants.QUESTIONNAIRE, false, sesObj);
							}
						}
					}
					if("edit".equals(actionType)){
						map.addAttribute("actionType", "edit");
						request.getSession().setAttribute("actionType", "edit");
					}else {
						map.addAttribute("actionType", "view");
						request.getSession().setAttribute("actionType", "view");
					}
					map.addAttribute("permission", permission);
					map.addAttribute("qTreeMap", qTreeMap);
					map.addAttribute("questionnaireBo", questionnaireBo);
					request.getSession().setAttribute("questionnaireId", questionnaireId);
				}
				if("add".equals(actionType)){
					map.addAttribute("actionType", "add");
					request.getSession().setAttribute("actionType", "add");
				}
				mav = new ModelAndView("questionnairePage",map);
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - getQuestionnairePage - Error",e);
		}
		logger.info("StudyQuestionnaireController - getQuestionnairePage - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 * @param questionnaireBo
	 * @return
	 */
	@RequestMapping(value="/adminStudies/saveorUpdateQuestionnaireSchedule.do",method=RequestMethod.POST)
	public ModelAndView saveorUpdateQuestionnaireSchedule(HttpServletRequest request , HttpServletResponse response,QuestionnaireBo questionnaireBo){
		logger.info("StudyQuestionnaireController - saveorUpdateQuestionnaireSchedule - Starts");
		ModelAndView mav = new ModelAndView("questionnairePage");
		ModelMap map = new ModelMap();
		QuestionnaireBo addQuestionnaireBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(questionnaireBo != null){
					if(questionnaireBo.getId() != null){
						questionnaireBo.setModifiedBy(sesObj.getUserId());
						questionnaireBo.setModifiedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						questionnaireBo.setCreatedBy(sesObj.getUserId());
						questionnaireBo.setCreatedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addQuestionnaireBo = studyQuestionnaireService.saveOrUpdateQuestionnaire(questionnaireBo);
					if(addQuestionnaireBo != null){
						if(questionnaireBo.getId() != null){
							request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Questionnaire Updated successfully.");
						}else{
							request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Questionnaire added successfully.");
						}
						mav = new ModelAndView("redirect:/adminStudies/viewStudyQuestionnaires.do",map);
					}else{
						request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, "Consent not added successfully.");
						mav = new ModelAndView("redirect:/adminStudies/viewQuestionnaire.do", map);
					}
				}
			}
		}catch(Exception e){ 
			logger.error("StudyQuestionnaireController - saveorUpdateQuestionnaireSchedule - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveorUpdateQuestionnaireSchedule - Ends");
		return mav;
	}
	
	@RequestMapping(value="/adminStudies/saveQuestionnaireSchedule.do",method=RequestMethod.POST)
	public void saveQuestionnaireSchedule(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - saveQuestionnaireSchedule - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		QuestionnaireBo updateQuestionnaireBo = null;
		ObjectMapper mapper = new ObjectMapper();
		QuestionnaireBo questionnaireBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String questionnaireScheduleInfo = request.getParameter("questionnaireScheduleInfo");
				if(questionnaireScheduleInfo != null && !questionnaireScheduleInfo.isEmpty()){
					questionnaireBo = mapper.readValue(questionnaireScheduleInfo, QuestionnaireBo.class);
					if(questionnaireBo != null){
						if(questionnaireBo.getId() != null){
							questionnaireBo.setModifiedBy(sesObj.getUserId());
							questionnaireBo.setModifiedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							questionnaireBo.setCreatedBy(sesObj.getUserId());
							questionnaireBo.setCreatedDate(FdahpStudyDesignerUtil.getCurrentDateTime());
						}
						updateQuestionnaireBo = studyQuestionnaireService.saveOrUpdateQuestionnaire(questionnaireBo);
						if(updateQuestionnaireBo != null){
							jsonobject.put("questionnaireId", updateQuestionnaireBo.getId());
							if(updateQuestionnaireBo.getQuestionnairesFrequenciesBo() != null){
								jsonobject.put("questionnaireFrequenceId", updateQuestionnaireBo.getQuestionnairesFrequenciesBo().getId());
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
			logger.error("StudyQuestionnaireController - saveQuestionnaireSchedule - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveQuestionnaireSchedule - Ends");
	}
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/deleteQuestionnaireStep.do",method = RequestMethod.POST)
	public void deleteQuestionnaireStepInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - deleteQuestionnaireStepInfo - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		QuestionnaireBo questionnaireBo = null;
		Map<Integer, QuestionnaireStepBean> qTreeMap = new TreeMap<Integer, QuestionnaireStepBean>();
		ObjectMapper mapper = new ObjectMapper();
		JSONObject questionnaireJsonObject = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String stepId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("stepId"))?"":request.getParameter("stepId");
				String stepType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("stepType"))?"":request.getParameter("stepType");
				String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
				if(!stepId.isEmpty() && !questionnaireId.isEmpty() && !stepType.isEmpty()){
					message = studyQuestionnaireService.deleteQuestionnaireStep(Integer.valueOf(stepId),Integer.valueOf(questionnaireId),stepType,sesObj);
					if(message.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS)){
						questionnaireBo=studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
						if(questionnaireBo != null){
							qTreeMap = studyQuestionnaireService.getQuestionnaireStepList(questionnaireBo.getId());
							questionnaireJsonObject = new JSONObject(mapper.writeValueAsString(qTreeMap));
							jsonobject.put("questionnaireJsonObject", questionnaireJsonObject);
						}
					}
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - deleteQuestionnaireStepInfo - ERROR",e);
		}
		logger.info("StudyQuestionnaireController - deleteQuestionnaireStepInfo - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/reOrderQuestionnaireStepInfo.do", method = RequestMethod.POST)
	public void reOrderQuestionnaireStepInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - reOrderQuestionnaireStepInfo - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			int oldOrderNumber = 0;
			int newOrderNumber = 0;
			if(sesObj!=null){
				String questionnaireId = (String) request.getSession().getAttribute("questionnaireId");
				if(StringUtils.isEmpty(questionnaireId)){
					questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
				}
				String oldOrderNo = FdahpStudyDesignerUtil.isEmpty(request.getParameter("oldOrderNumber"))?"":request.getParameter("oldOrderNumber");
				String newOrderNo = FdahpStudyDesignerUtil.isEmpty(request.getParameter("newOrderNumber"))?"":request.getParameter("newOrderNumber");
				if((questionnaireId != null && !questionnaireId.isEmpty()) && !oldOrderNo.isEmpty() && !newOrderNo.isEmpty()){
					oldOrderNumber = Integer.valueOf(oldOrderNo);
					newOrderNumber = Integer.valueOf(newOrderNo);
					message = studyQuestionnaireService.reOrderQuestionnaireSteps(Integer.valueOf(questionnaireId), oldOrderNumber, newOrderNumber);
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - reOrderQuestionnaireStepInfo - ERROR",e);
		}
		logger.info("StudyQuestionnaireController - reOrderQuestionnaireStepInfo - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/validateQuestionnaireKey.do", method = RequestMethod.POST)
	public void validateQuestionnaireShortTitle(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - validateQuestionnaireShortTitle - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
				if(StringUtils.isEmpty(studyId)){
					studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
					request.getSession().setAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
				}
				String shortTitle = FdahpStudyDesignerUtil.isEmpty(request.getParameter("shortTitle"))?"":request.getParameter("shortTitle");
				if((studyId != null && !studyId.isEmpty()) && !shortTitle.isEmpty()){
					message = studyQuestionnaireService.checkQuestionnaireShortTitle(Integer.valueOf(studyId), shortTitle);
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - validateQuestionnaireShortTitle - ERROR",e);
		}
		logger.info("StudyQuestionnaireController - validateQuestionnaireShortTitle - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/validateQuestionnaireStepKey.do", method = RequestMethod.POST)
	public void validateQuestionnaireStepShortTitle(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - validateQuestionnaireShortTitle - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
				String stepType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("stepType"))?"":request.getParameter("stepType");
				String shortTitle = FdahpStudyDesignerUtil.isEmpty(request.getParameter("shortTitle"))?"":request.getParameter("shortTitle");
				if(!questionnaireId.isEmpty() && !stepType.isEmpty() && !shortTitle.isEmpty()){
					message = studyQuestionnaireService.checkQuestionnaireStepShortTitle(Integer.valueOf(questionnaireId), stepType, shortTitle);
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - validateQuestionnaireStepShortTitle - ERROR",e);
		}
		logger.info("StudyQuestionnaireController - validateQuestionnaireStepShortTitle - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/adminStudies/formStep.do")
	public ModelAndView getFormStepPage(HttpServletRequest request , HttpServletResponse response){
		logger.info("StudyQuestionnaireController - getFormStepPage - Starts");
		ModelAndView mav = new ModelAndView("formStepPage");
		String sucMsg = "";
		String errMsg = "";
		ModelMap map = new ModelMap();
		QuestionnaireBo questionnaireBo = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		StudyBo studyBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			request.getSession().removeAttribute("questionId");
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
			request.getSession().removeAttribute("actionTypeForFormStep");
			String actionType = (String) request.getSession().getAttribute("actionType");
			if(StringUtils.isEmpty(actionType)){
				actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType"))? "" : request.getParameter("actionType");
			}
			
			String actionTypeForQuestionPage = (String) request.getSession().getAttribute("actionTypeForQuestionPage");
			if(StringUtils.isEmpty(actionTypeForQuestionPage)){
				actionTypeForQuestionPage = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionTypeForQuestionPage"))? "" : request.getParameter("actionTypeForQuestionPage");
				if("edit".equals(actionTypeForQuestionPage)){
					request.getSession().setAttribute("actionTypeForQuestionPage", "edit");
				}else if("view".equals(actionTypeForQuestionPage)){
					request.getSession().setAttribute("actionTypeForQuestionPage", "view");
				}else{
					request.getSession().setAttribute("actionTypeForQuestionPage", "add");
				}
			}
			
			String formId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("formId"))?"":request.getParameter("formId");
			String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
			String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
			if(StringUtils.isEmpty(studyId)){
				studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) == true?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				request.getSession().setAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
			}
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
			}
			if(StringUtils.isEmpty(formId)){
				formId = (String) request.getSession().getAttribute("formId");
				request.getSession().setAttribute("formId", formId);
			}
			if(StringUtils.isEmpty(questionnaireId)){
				questionnaireId = (String) request.getSession().getAttribute("questionnaireId");
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(StringUtils.isNotEmpty(questionnaireId)){
				request.getSession().removeAttribute("actionType");
				questionnaireBo = studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
				map.addAttribute("questionnaireBo", questionnaireBo);
				if("edit".equals(actionType)){
					request.getSession().setAttribute("actionType", "edit");
				}else if("view".equals(actionType)){
					request.getSession().setAttribute("actionType", "view");
				}else{
					request.getSession().setAttribute("actionType", "add");
				}
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(formId!= null && !formId.isEmpty()){
				questionnairesStepsBo = studyQuestionnaireService.getQuestionnaireStep(Integer.valueOf(formId), FdahpStudyDesignerConstants.FORM_STEP);
				if(questionnairesStepsBo != null){
					List<QuestionnairesStepsBo> destionationStepList = studyQuestionnaireService.getQuestionnairesStepsList(questionnairesStepsBo.getQuestionnairesId(), questionnairesStepsBo.getSequenceNo());
					map.addAttribute("destinationStepList", destionationStepList);
					if(!questionnairesStepsBo.getStatus() && StringUtils.isNotEmpty(studyId)){
							studyService.markAsCompleted(Integer.valueOf(studyId),FdahpStudyDesignerConstants.QUESTIONNAIRE,false,sesObj);
					}
				}
				map.addAttribute("questionnairesStepsBo", questionnairesStepsBo);
				request.getSession().setAttribute("formId", formId);
			}
			map.addAttribute("questionnaireId", questionnaireId);
			mav = new ModelAndView("formStepPage",map);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - getFormStepPage - Error",e);
		}
		logger.info("StudyQuestionnaireController - getFormStepPage - Ends");
		return mav;
	}
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping("/adminStudies/saveOrUpdateFromStepQuestionnaire.do")
	public ModelAndView saveOrUpdateFormStepQuestionnaire(HttpServletRequest request,HttpServletResponse response,QuestionnairesStepsBo questionnairesStepsBo){
		logger.info("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Starts");
		ModelAndView mav = new ModelAndView("instructionsStepPage");
		ModelMap map = new ModelMap();
		QuestionnairesStepsBo addQuestionnairesStepsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(questionnairesStepsBo != null){
					if(questionnairesStepsBo.getStepId() != null){
						questionnairesStepsBo.setModifiedBy(sesObj.getUserId());
						questionnairesStepsBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						questionnairesStepsBo.setCreatedBy(sesObj.getUserId());
						questionnairesStepsBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addQuestionnairesStepsBo= studyQuestionnaireService.saveOrUpdateFromStepQuestionnaire(questionnairesStepsBo);
				}
				if(addQuestionnairesStepsBo != null){
					if(questionnairesStepsBo.getStepId() != null){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Form Step updated successfully.");
					}else{
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Form Step added successfully.");
					}
					mav = new ModelAndView("redirect:/adminStudies/viewQuestionnaire.do",map);
				}else{
					request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, "Form not added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/formStep.do", map);
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Ends");
		return mav;
	}
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/saveFromStep.do")
	public void saveFormStep(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - saveFormStep - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		QuestionnairesStepsBo questionnairesStepsBo=null;
		ObjectMapper mapper = new ObjectMapper();
		QuestionnairesStepsBo addQuestionnairesStepsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String questionnaireStepInfo = request.getParameter("questionnaireStepInfo");
				if(null != questionnaireStepInfo){
					questionnairesStepsBo = mapper.readValue(questionnaireStepInfo, QuestionnairesStepsBo.class);
					if(questionnairesStepsBo != null){
						if(questionnairesStepsBo.getStepId() != null){
							questionnairesStepsBo.setModifiedBy(sesObj.getUserId());
							questionnairesStepsBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							questionnairesStepsBo.setCreatedBy(sesObj.getUserId());
							questionnairesStepsBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						}
						addQuestionnairesStepsBo = studyQuestionnaireService.saveOrUpdateFromStepQuestionnaire(questionnairesStepsBo);
					}
				}
				if(addQuestionnairesStepsBo != null){
					jsonobject.put("stepId", addQuestionnairesStepsBo.getStepId());
					jsonobject.put("formId", addQuestionnairesStepsBo.getInstructionFormId());
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveFormStep - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveFormStep - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/reOrderFormQuestions.do", method = RequestMethod.POST)
	public void reOrderFromStepQuestionsInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - reOrderQuestionnaireStepInfo - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			int oldOrderNumber;
			int newOrderNumber;
			if(sesObj!=null){
				String formId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("formId"))?"":request.getParameter("formId");
				String oldOrderNo = FdahpStudyDesignerUtil.isEmpty(request.getParameter("oldOrderNumber"))?"":request.getParameter("oldOrderNumber");
				String newOrderNo = FdahpStudyDesignerUtil.isEmpty(request.getParameter("newOrderNumber"))?"":request.getParameter("newOrderNumber");
				if(!formId.isEmpty() && !oldOrderNo.isEmpty() && !newOrderNo.isEmpty()){
					oldOrderNumber = Integer.valueOf(oldOrderNo);
					newOrderNumber = Integer.valueOf(newOrderNo);
					message = studyQuestionnaireService.reOrderFormStepQuestions(Integer.valueOf(formId), oldOrderNumber, newOrderNumber);
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - reOrderQuestionnaireStepInfo - ERROR",e);
		}
		logger.info("StudyQuestionnaireController - reOrderQuestionnaireStepInfo - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/deleteFormQuestion.do",method = RequestMethod.POST)
	public void deleteFormQuestionInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - deleteFormQuestionInfo - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		Map<Integer, QuestionnaireStepBean> qTreeMap = new TreeMap<>();
		ObjectMapper mapper = new ObjectMapper();
		JSONObject questionnaireJsonObject = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String formId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("formId"))?"":request.getParameter("formId");
				String questionId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionId"))?"":request.getParameter("questionId");
				if(!formId.isEmpty() && !questionId.isEmpty()){
					message = studyQuestionnaireService.deleteFromStepQuestion(Integer.valueOf(formId),Integer.valueOf(questionId),sesObj);
					if(message.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS)){
						questionnairesStepsBo=studyQuestionnaireService.getQuestionnaireStep(Integer.valueOf(formId), FdahpStudyDesignerConstants.FORM_STEP);
						if(questionnairesStepsBo != null){
							qTreeMap = questionnairesStepsBo.getFormQuestionMap();
							questionnaireJsonObject = new JSONObject(mapper.writeValueAsString(qTreeMap));
							jsonobject.put("questionnaireJsonObject", questionnaireJsonObject);
						}
					}
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - deleteFormQuestionInfo - ERROR",e);
		}
		logger.info("StudyQuestionnaireController - deleteFormQuestionInfo - Ends");
	}
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/adminStudies/questionStep.do")
	public ModelAndView getQuestionStepPage(HttpServletRequest request , HttpServletResponse response){
		logger.info("StudyQuestionnaireController - getQuestionStepPage - starts");
		ModelAndView mav = new ModelAndView("questionStepPage");
		String sucMsg = "";
		String errMsg = "";
		ModelMap map = new ModelMap();
		QuestionnaireBo questionnaireBo = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		StudyBo studyBo = null;
		List<String> timeRangeList = new ArrayList<>();
		List<StatisticImageListBo> statisticImageList = new ArrayList<>();
		List<ActivetaskFormulaBo> activetaskFormulaList = new ArrayList<>();
		List<QuestionResponseTypeMasterInfoBo> questionResponseTypeMasterInfoList = new ArrayList<>();
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
			String questionId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionId"))?"":request.getParameter("questionId");
			String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
			String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
			String permission = (String) request.getSession().getAttribute("permission");
			request.getSession().removeAttribute("actionTypeForQuestionPage");
			String actionType = (String) request.getSession().getAttribute("actionType");
			if(StringUtils.isEmpty(actionType)){
				actionType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType"))? "" : request.getParameter("actionType");
			}
			
			String actionTypeForQuestionPage = (String) request.getSession().getAttribute("actionTypeForQuestionPage");
			if(StringUtils.isEmpty(actionTypeForQuestionPage)){
				actionTypeForQuestionPage = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionTypeForQuestionPage"))? "" : request.getParameter("actionTypeForQuestionPage");
				if("edit".equals(actionTypeForQuestionPage)){
					request.getSession().setAttribute("actionTypeForQuestionPage", "edit");
				}else if("view".equals(actionTypeForQuestionPage)){
					request.getSession().setAttribute("actionTypeForQuestionPage", "view");
				}else{
					request.getSession().setAttribute("actionTypeForQuestionPage", "add");
				}
			}
			
			
			if(StringUtils.isEmpty(studyId)){
				studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				request.getSession().setAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
			}
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				boolean isExists = studyQuestionnaireService.isAnchorDateExistsForStudy(Integer.valueOf(studyId));
				map.addAttribute("isAnchorDate",isExists);
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
			}
			if(StringUtils.isEmpty(questionId)){
				questionId = (String) request.getSession().getAttribute("questionId");
				request.getSession().setAttribute("questionId", questionId);
			}
			if(StringUtils.isEmpty(questionnaireId)){
				questionnaireId = (String) request.getSession().getAttribute("questionnaireId");
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(StringUtils.isNotEmpty(questionnaireId)){
				request.getSession().removeAttribute("actionType");
				questionnaireBo = studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
				map.addAttribute("questionnaireBo", questionnaireBo);
				if(questionnaireBo!=null && StringUtils.isNotEmpty(questionnaireBo.getFrequency())){
					timeRangeList = FdahpStudyDesignerUtil.getTimeRangeList(questionnaireBo.getFrequency());
				}
				if("edit".equals(actionType)){
					request.getSession().setAttribute("actionType", "edit");
				}else if("view".equals(actionType)){
					request.getSession().setAttribute("actionType", "view");
				}else{
					request.getSession().setAttribute("actionType", "add");
				}
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(questionId!= null && !questionId.isEmpty()){
				questionnairesStepsBo = studyQuestionnaireService.getQuestionnaireStep(Integer.valueOf(questionId), FdahpStudyDesignerConstants.QUESTION_STEP);
				if(questionnairesStepsBo != null){
					List<QuestionnairesStepsBo> destionationStepList = studyQuestionnaireService.getQuestionnairesStepsList(questionnairesStepsBo.getQuestionnairesId(), questionnairesStepsBo.getSequenceNo());
					map.addAttribute("destinationStepList", destionationStepList);
				}
				map.addAttribute("questionnairesStepsBo", questionnairesStepsBo);
				request.getSession().setAttribute("questionId", questionId);
			}
			statisticImageList = studyActiveTasksService.getStatisticImages();
			activetaskFormulaList = studyActiveTasksService.getActivetaskFormulas();
			questionResponseTypeMasterInfoList = studyQuestionnaireService.getQuestionReponseTypeList();
			map.addAttribute("permission", permission);
			map.addAttribute("timeRangeList", timeRangeList);
			map.addAttribute("statisticImageList", statisticImageList);
			map.addAttribute("activetaskFormulaList", activetaskFormulaList);
			map.addAttribute("questionnaireId", questionnaireId);
			map.addAttribute("questionResponseTypeMasterInfoList",questionResponseTypeMasterInfoList);
			mav = new ModelAndView("questionStepPage",map);
			/*response.setHeader("Content-Disposition", "inline;filename=\"QUESTIONNAIRE_PAGE_29_04242017060429\"");
			OutputStream out = response.getOutputStream();
			response.setContentType("image/jpg");
			IOUtils.copy(questionnairesStepsBo.getQuestionResponseSubTypeList().get(1).getImageContent().getBinaryStream(), out);
			out.flush();
			out.close();*/
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - getQuestionStepPage - Error",e);
		}
		logger.info("StudyQuestionnaireController - getQuestionStepPage - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping("/adminStudies/saveOrUpdateQuestionStepQuestionnaire.do")
	public ModelAndView saveOrUpdateQuestionStepQuestionnaire(HttpServletRequest request,HttpServletResponse response,QuestionnairesStepsBo questionnairesStepsBo){
		logger.info("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Starts");
		ModelAndView mav = new ModelAndView("instructionsStepPage");
		ModelMap map = new ModelMap();
		QuestionnairesStepsBo addQuestionnairesStepsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(questionnairesStepsBo != null){
					if(questionnairesStepsBo.getStepId() != null){
						questionnairesStepsBo.setModifiedBy(sesObj.getUserId());
						questionnairesStepsBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						questionnairesStepsBo.setCreatedBy(sesObj.getUserId());
						questionnairesStepsBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addQuestionnairesStepsBo= studyQuestionnaireService.saveOrUpdateQuestionStep(questionnairesStepsBo);
				}
				if(addQuestionnairesStepsBo != null){
					if(questionnairesStepsBo.getStepId() != null){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Question Step updated successfully.");
					}else{
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Question Step added successfully.");
					}
					mav = new ModelAndView("redirect:/adminStudies/viewQuestionnaire.do",map);
				}else{
					request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, "Form not added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/questionStep.do", map);
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/saveQuestionStep.do",method = RequestMethod.POST )
	public void saveQuestionStep(HttpServletResponse response,MultipartHttpServletRequest multipleRequest){
		logger.info("StudyQuestionnaireController - saveQuestionStep - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		QuestionnairesStepsBo questionnairesStepsBo=null;
		ObjectMapper mapper = new ObjectMapper();
		QuestionnairesStepsBo addQuestionnairesStepsBo = null;
		try{
			SessionObject sesObj = (SessionObject) multipleRequest.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String questionnaireStepInfo = multipleRequest.getParameter("questionnaireStepInfo");
				Iterator<String> itr =  multipleRequest.getFileNames();
				HashMap<String, MultipartFile> fileMap = new HashMap<>();
				while(itr.hasNext()){
					CommonsMultipartFile mpf = (CommonsMultipartFile) multipleRequest.getFile(itr.next());
					fileMap.put(mpf.getFileItem().getFieldName(), mpf);
				}
				if(null != questionnaireStepInfo){
					questionnairesStepsBo = mapper.readValue(questionnaireStepInfo, QuestionnairesStepsBo.class);
					if(questionnairesStepsBo != null){
						if(questionnairesStepsBo.getStepId() != null){
							questionnairesStepsBo.setModifiedBy(sesObj.getUserId());
							questionnairesStepsBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							questionnairesStepsBo.setCreatedBy(sesObj.getUserId());
							questionnairesStepsBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						}
						if(questionnairesStepsBo.getQuestionsBo() != null && questionnairesStepsBo.getQuestionsBo().getResponseType() != null && questionnairesStepsBo.getQuestionsBo().getResponseType() == 5){
							if(questionnairesStepsBo.getQuestionResponseSubTypeList() != null && !questionnairesStepsBo.getQuestionResponseSubTypeList().isEmpty()){
								for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionnairesStepsBo.getQuestionResponseSubTypeList()){
									String key1 = "imageFile[" + questionResponseSubTypeBo.getImageId() + "]";
									String key2 = "selectImageFile[" + questionResponseSubTypeBo.getImageId() + "]";
									if(fileMap != null && fileMap.get(key1) != null){
										questionResponseSubTypeBo.setImageFile(fileMap.get(key1));
									}
									if(fileMap != null && fileMap.get(key2) != null){
										questionResponseSubTypeBo.setSelectImageFile(fileMap.get(key2));
									}
								}
							}
						}
						addQuestionnairesStepsBo = studyQuestionnaireService.saveOrUpdateQuestionStep(questionnairesStepsBo);
					}
				}
				if(addQuestionnairesStepsBo != null){
					jsonobject.put("stepId", addQuestionnairesStepsBo.getStepId());
					
					if(addQuestionnairesStepsBo.getQuestionsBo() != null){
						jsonobject.put("questionId", addQuestionnairesStepsBo.getQuestionsBo().getId());
						
					}
					if(addQuestionnairesStepsBo.getQuestionReponseTypeBo() != null){
						jsonobject.put("questionResponseId", addQuestionnairesStepsBo.getQuestionReponseTypeBo().getResponseTypeId());
						jsonobject.put("questionsResponseTypeId", addQuestionnairesStepsBo.getQuestionReponseTypeBo().getQuestionsResponseTypeId());
					}
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveQuestionStep - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveQuestionStep - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/adminStudies/formQuestion.do")
	public ModelAndView getFormStepQuestionPage(HttpServletRequest request , HttpServletResponse response){
		logger.info("StudyQuestionnaireController - getFormStepQuestionPage - Starts");
		ModelAndView mav = new ModelAndView("questionPage");
		String sucMsg = "";
		String errMsg = "";
		ModelMap map = new ModelMap();
		QuestionnaireBo questionnaireBo = null;
		StudyBo studyBo = null;
		QuestionsBo questionsBo = null;
		List<String> timeRangeList = new ArrayList<String>();
		List<StatisticImageListBo> statisticImageList = new ArrayList<>();
		List<ActivetaskFormulaBo> activetaskFormulaList = new ArrayList<>();
		List<QuestionResponseTypeMasterInfoBo> questionResponseTypeMasterInfoList = new ArrayList<>();
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
			
			request.getSession().removeAttribute("actionTypeForFormStep");
			String actionTypeForQuestionPage = (String) request.getSession().getAttribute("actionTypeForQuestionPage");
			if(StringUtils.isEmpty(actionTypeForQuestionPage)){
				actionTypeForQuestionPage = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionTypeForQuestionPage"))? "" : request.getParameter("actionTypeForQuestionPage");
			}
			
			String actionTypeForFormStep = (String) request.getSession().getAttribute("actionTypeForFormStep");
			if(StringUtils.isEmpty(actionTypeForFormStep)){
				actionTypeForFormStep = FdahpStudyDesignerUtil.isEmpty(request.getParameter("actionTypeForFormStep"))? "" : request.getParameter("actionTypeForFormStep");
				if("edit".equals(actionTypeForFormStep)){
					map.addAttribute("actionTypeForFormStep", "edit");
					request.getSession().setAttribute("actionTypeForFormStep", "edit");
				}else if("view".equals(actionTypeForFormStep)){
					map.addAttribute("actionTypeForFormStep", "view");
					request.getSession().setAttribute("actionTypeForFormStep", "view");
				}else {
					map.addAttribute("actionTypeForFormStep", "add");
					request.getSession().setAttribute("actionTypeForFormStep", "add");
				}
			}
			
			String formId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("formId"))?"":request.getParameter("formId");
			String questionId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionId"))?"":request.getParameter("questionId");
			String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
			String studyId = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.STUDY_ID);
			if(StringUtils.isEmpty(studyId)){
				studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID))?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				request.getSession().setAttribute(FdahpStudyDesignerConstants.STUDY_ID, studyId);
			}
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				boolean isExists = studyQuestionnaireService.isAnchorDateExistsForStudy(Integer.valueOf(studyId));
				map.addAttribute("isAnchorDate",isExists);
				map.addAttribute(FdahpStudyDesignerConstants.STUDY_BO, studyBo);
			}
			if(StringUtils.isEmpty(formId)){
				formId = (String) request.getSession().getAttribute("formId");
				request.getSession().setAttribute("formId", formId);
			}
			if(StringUtils.isEmpty(questionId)){
				questionId = (String) request.getSession().getAttribute("questionId");
				request.getSession().setAttribute("questionId", questionId);
			}
			if(StringUtils.isEmpty(questionnaireId)){
				questionnaireId = (String) request.getSession().getAttribute("questionnaireId");
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(StringUtils.isNotEmpty(questionnaireId)){
				request.getSession().removeAttribute("actionTypeForQuestionPage");
				questionnaireBo = studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
				map.addAttribute("questionnaireBo", questionnaireBo);
				if("edit".equals(actionTypeForQuestionPage)){
					request.getSession().setAttribute("actionTypeForQuestionPage", "edit");
				}else if("view".equals(actionTypeForQuestionPage)){
					request.getSession().setAttribute("actionTypeForQuestionPage", "view");
				}else{
					request.getSession().setAttribute("actionTypeForQuestionPage", "add");
				}
				request.getSession().setAttribute("questionnaireId", questionnaireId);
				if(questionnaireBo!=null && StringUtils.isNotEmpty(questionnaireBo.getFrequency())){
					timeRangeList = FdahpStudyDesignerUtil.getTimeRangeList(questionnaireBo.getFrequency());
				}
			}
			if(formId!= null && !formId.isEmpty()){
				if(questionId != null && !questionId.isEmpty()){
					questionsBo = studyQuestionnaireService.getQuestionsById(Integer.valueOf(questionId));
					map.addAttribute("questionsBo", questionsBo);
					request.getSession().setAttribute("questionId", questionId);
					QuestionnairesStepsBo questionnairesStepsBo = studyQuestionnaireService.getQuestionnaireStep(Integer.valueOf(formId), FdahpStudyDesignerConstants.FORM_STEP);
					if(questionnairesStepsBo != null){
						List<QuestionnairesStepsBo> destionationStepList = studyQuestionnaireService.getQuestionnairesStepsList(questionnairesStepsBo.getQuestionnairesId(), questionnairesStepsBo.getSequenceNo());
						map.addAttribute("destinationStepList", destionationStepList);
					}
				}
				map.addAttribute("formId", formId);
				request.getSession().setAttribute("formId", formId);
			}
			statisticImageList = studyActiveTasksService.getStatisticImages();
			activetaskFormulaList = studyActiveTasksService.getActivetaskFormulas();
			questionResponseTypeMasterInfoList = studyQuestionnaireService.getQuestionReponseTypeList();
			map.addAttribute("timeRangeList", timeRangeList);
			map.addAttribute("statisticImageList", statisticImageList);
			map.addAttribute("activetaskFormulaList", activetaskFormulaList);
			map.addAttribute("questionResponseTypeMasterInfoList",questionResponseTypeMasterInfoList);
			mav = new ModelAndView("questionPage",map);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - getFormStepQuestionPage - Error",e);
		}
		logger.info("StudyQuestionnaireController - getFormStepQuestionPage - Ends");
		return mav;
	}
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping("/adminStudies/saveOrUpdateFromQuestion.do")
	public ModelAndView saveOrUpdateFormQuestion(HttpServletRequest request,HttpServletResponse response,QuestionsBo questionsBo){
		logger.info("StudyQuestionnaireController - saveOrUpdateFormQuestion - Starts");
		ModelAndView mav = new ModelAndView("instructionsStepPage");
		ModelMap map = new ModelMap();
		QuestionsBo addQuestionsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(questionsBo != null){
					if(questionsBo.getId() != null){
						questionsBo.setModifiedBy(sesObj.getUserId());
						questionsBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						questionsBo.setCreatedBy(sesObj.getUserId());
						questionsBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addQuestionsBo= studyQuestionnaireService.saveOrUpdateQuestion(questionsBo);
				}
				if(addQuestionsBo != null){
					if(addQuestionsBo.getId() != null){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Form Question updated successfully.");
					}else{
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, "Form Question added successfully.");
					}
					mav = new ModelAndView("redirect:/adminStudies/formStep.do",map);
				}else{
					request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, "Form not added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/formQuestion.do", map);
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveOrUpdateFormQuestion - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveOrUpdateFormQuestion - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/saveQuestion.do")
	public void saveQuestion(HttpServletRequest request,HttpServletResponse response,MultipartHttpServletRequest multipleRequest){
		logger.info("StudyQuestionnaireController - saveQuestion - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		QuestionsBo questionsBo=null;
		ObjectMapper mapper = new ObjectMapper();
		QuestionsBo addQuestionsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String questionnaireStepInfo = request.getParameter("questionInfo");
				Iterator<String> itr =  multipleRequest.getFileNames();
				HashMap<String, MultipartFile> fileMap = new HashMap<>();
				while(itr.hasNext()){
					CommonsMultipartFile mpf = (CommonsMultipartFile) multipleRequest.getFile(itr.next());
					fileMap.put(mpf.getFileItem().getFieldName(), mpf);
				}
				if(null != questionnaireStepInfo){
					questionsBo = mapper.readValue(questionnaireStepInfo, QuestionsBo.class);
					if(questionsBo != null){
						if(questionsBo.getId() != null){
							questionsBo.setModifiedBy(sesObj.getUserId());
							questionsBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							questionsBo.setCreatedBy(sesObj.getUserId());
							questionsBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						}
						if(questionsBo.getResponseType() != null && questionsBo.getResponseType() == 5){
							if(questionsBo.getQuestionResponseSubTypeList() != null && !questionsBo.getQuestionResponseSubTypeList().isEmpty()){
								for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionsBo.getQuestionResponseSubTypeList()){
									String key1 = "imageFile[" + questionResponseSubTypeBo.getImageId() + "]";
									String key2 = "selectImageFile[" + questionResponseSubTypeBo.getImageId() + "]";
									if(fileMap != null && fileMap.get(key1) != null){
										questionResponseSubTypeBo.setImageFile(fileMap.get(key1));
									}
									if(fileMap != null && fileMap.get(key2) != null){
										questionResponseSubTypeBo.setSelectImageFile(fileMap.get(key2));
									}
								}
							}
						}
						addQuestionsBo = studyQuestionnaireService.saveOrUpdateQuestion(questionsBo);
					}
				}
				if(addQuestionsBo != null){
					jsonobject.put("questionId", addQuestionsBo.getId());
					if(addQuestionsBo.getQuestionReponseTypeBo() != null){
						jsonobject.put("questionResponseId", addQuestionsBo.getQuestionReponseTypeBo().getResponseTypeId());
						jsonobject.put("questionsResponseTypeId", addQuestionsBo.getQuestionReponseTypeBo().getQuestionsResponseTypeId());
					}
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - saveQuestion - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveQuestion - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/deleteQuestionnaire.do",method = RequestMethod.POST)
	public void deleteQuestionnaireInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - deleteQuestionnaireInfo - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = FdahpStudyDesignerConstants.SUCCESS;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.STUDY_ID)) == true?"":request.getParameter(FdahpStudyDesignerConstants.STUDY_ID);
				String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
				if(!studyId.isEmpty() && !questionnaireId.isEmpty()){
					message = studyQuestionnaireService.deletQuestionnaire(Integer.valueOf(studyId), Integer.valueOf(questionnaireId), sesObj);
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - deleteQuestionnaireInfo - ERROR",e);
		}
		logger.info("StudyQuestionnaireController - deleteQuestionnaireInfo - Ends");
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/validateQuestionKey.do", method = RequestMethod.POST)
	public void validateQuestionShortTitle(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - validateQuestionShortTitle - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String questionnaireId = FdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId"))?"":request.getParameter("questionnaireId");
				String shortTitle = FdahpStudyDesignerUtil.isEmpty(request.getParameter("shortTitle"))?"":request.getParameter("shortTitle");
				if(!questionnaireId.isEmpty() &&  !shortTitle.isEmpty()){
					message = studyQuestionnaireService.checkFromQuestionShortTitle(Integer.valueOf(questionnaireId), shortTitle);
				}
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch(Exception e){
			logger.error("StudyQuestionnaireController - validateQuestionShortTitle - ERROR",e);
		}
		logger.info("StudyQuestionnaireController - validateQuestionShortTitle - Ends");
	}
}
