package com.fdahpStudyDesigner.controller;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

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
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bean.QuestionnaireStepBean;
import com.fdahpStudyDesigner.bo.ActivetaskFormulaBo;
import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionResponseTypeMasterInfoBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.QuestionnairesStepsBo;
import com.fdahpStudyDesigner.bo.QuestionsBo;
import com.fdahpStudyDesigner.bo.StatisticImageListBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.service.StudyActiveTasksService;
import com.fdahpStudyDesigner.service.StudyQuestionnaireService;
import com.fdahpStudyDesigner.service.StudyService;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

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
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			request.getSession().removeAttribute("questionnaireId");
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG)){
				sucMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.SUC_MSG, sucMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.SUC_MSG);
			}
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.ERR_MSG);
			}
			
			String studyId = (String) request.getSession().getAttribute("studyId");
			if (StringUtils.isEmpty(studyId)) {
				studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true ? "0" : request.getParameter("studyId");
			} 
			if (StringUtils.isNotEmpty(studyId)) {
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				questionnaires = studyQuestionnaireService.getStudyQuestionnairesByStudyId(studyId);
				map.addAttribute("studyBo", studyBo);
				map.addAttribute("questionnaires", questionnaires);
				mav = new ModelAndView("studyQuestionaryListPage", map);
			} 
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
		logger.info("StudyQuestionnaireController - getInstructionsPage - Ends");
		ModelAndView mav = new ModelAndView("instructionsStepPage");
		String sucMsg = "";
		String errMsg = "";
		ModelMap map = new ModelMap();
		InstructionsBo instructionsBo = null;
		QuestionnaireBo questionnaireBo = null;
		StudyBo studyBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG)){
				sucMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.SUC_MSG, sucMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.SUC_MSG);
			}
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.ERR_MSG);
			}
			String instructionId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("instructionId")) == true?"":request.getParameter("instructionId");
			String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
			String studyId = (String) request.getSession().getAttribute("studyId");
			if(StringUtils.isEmpty(studyId)){
				studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				request.getSession().setAttribute("studyId", studyId);
			}
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				map.addAttribute("studyBo", studyBo);
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
				questionnaireBo = studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
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
			logger.info("StudyQuestionnaireController - getInstructionsPage - Error",e);
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
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(instructionsBo != null){
					if(instructionsBo.getId() != null){
						instructionsBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
						instructionsBo.setModifiedBy(sesObj.getUserId());
					}else{
						instructionsBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
						instructionsBo.setCreatedBy(sesObj.getUserId());
					}
					if(instructionsBo.getQuestionnairesStepsBo() != null){
						if(instructionsBo.getQuestionnairesStepsBo().getStepId() != null){
							instructionsBo.getQuestionnairesStepsBo().setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
							instructionsBo.getQuestionnairesStepsBo().setModifiedBy(sesObj.getUserId());
						}else{
							instructionsBo.getQuestionnairesStepsBo().setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
							instructionsBo.getQuestionnairesStepsBo().setCreatedBy(sesObj.getUserId());
						}
					}
					addInstructionsBo= studyQuestionnaireService.saveOrUpdateInstructionsBo(instructionsBo);
				}
				if(addInstructionsBo != null){
					if(instructionsBo.getId() != null){
						request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Instruction Updated successfully.");
					}else{
						request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Instruction added successfully.");
					}
					mav = new ModelAndView("redirect:/adminStudies/viewQuestionnaire.do",map);
				}else{
					request.getSession().setAttribute(fdahpStudyDesignerConstants.ERR_MSG, "Instruction not added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/instructionsStep.do", map);
				}
			}
		}catch(Exception e){
			logger.info("StudyQuestionnaireController - saveOrUpdateInstructionStep - Error",e);
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
		logger.info("StudyQuestionnaireController - saveInstructionStep - Ends");
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		InstructionsBo instructionsBo = null;
		ObjectMapper mapper = new ObjectMapper();
		InstructionsBo addInstructionsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String instructionsInfo = request.getParameter("instructionsInfo");
				if(null != instructionsInfo){
					instructionsBo = mapper.readValue(instructionsInfo, InstructionsBo.class);
					if(instructionsBo != null){
						if(instructionsBo.getId()!= null){
							instructionsBo.setModifiedBy(sesObj.getUserId());
							instructionsBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							instructionsBo.setCreatedBy(sesObj.getUserId());
							instructionsBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
							instructionsBo.setActive(true);
						}
						if(instructionsBo.getQuestionnairesStepsBo() != null){
							if(instructionsBo.getQuestionnairesStepsBo().getStepId() != null){
								instructionsBo.getQuestionnairesStepsBo().setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
								instructionsBo.getQuestionnairesStepsBo().setModifiedBy(sesObj.getUserId());
							}else{
								instructionsBo.getQuestionnairesStepsBo().setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
								instructionsBo.getQuestionnairesStepsBo().setCreatedBy(sesObj.getUserId());
							}
						}
						addInstructionsBo = studyQuestionnaireService.saveOrUpdateInstructionsBo(instructionsBo);
					}
				}
				if(addInstructionsBo != null){
					jsonobject.put("instructionId", addInstructionsBo.getId());
					jsonobject.put("stepId", addInstructionsBo.getQuestionnairesStepsBo().getStepId());
					message = fdahpStudyDesignerConstants.SUCCESS;
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
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				request.getSession().removeAttribute("instructionId");
				request.getSession().removeAttribute("formId");
				request.getSession().removeAttribute("questionId");
				if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(fdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(fdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(fdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(fdahpStudyDesignerConstants.ERR_MSG);
				}
				String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
					request.getSession().setAttribute("studyId", studyId);
				}
				if(StringUtils.isNotEmpty(studyId)){
					studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
					map.addAttribute("studyBo", studyBo);
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
						}
					}
					map.addAttribute("qTreeMap", qTreeMap);
					map.addAttribute("questionnaireBo", questionnaireBo);
					request.getSession().setAttribute("questionnaireId", questionnaireId);
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
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(questionnaireBo != null){
					if(questionnaireBo.getId() != null){
						questionnaireBo.setModifiedBy(sesObj.getUserId());
						questionnaireBo.setModifiedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						questionnaireBo.setCreatedBy(sesObj.getUserId());
						questionnaireBo.setCreatedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addQuestionnaireBo = studyQuestionnaireService.saveOrUpdateQuestionnaire(questionnaireBo);
					if(addQuestionnaireBo != null){
						if(questionnaireBo.getId() != null){
							request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Questionnaire Updated successfully.");
						}else{
							request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Questionnaire added successfully.");
						}
						mav = new ModelAndView("redirect:/adminStudies/viewStudyQuestionnaires.do",map);
					}else{
						request.getSession().setAttribute(fdahpStudyDesignerConstants.ERR_MSG, "Consent not added successfully.");
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		QuestionnaireBo updateQuestionnaireBo = null;
		ObjectMapper mapper = new ObjectMapper();
		QuestionnaireBo questionnaireBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String questionnaireScheduleInfo = request.getParameter("questionnaireScheduleInfo");
				if(questionnaireScheduleInfo != null && !questionnaireScheduleInfo.isEmpty()){
					questionnaireBo = mapper.readValue(questionnaireScheduleInfo, QuestionnaireBo.class);
					if(questionnaireBo != null){
						if(questionnaireBo.getId() != null){
							questionnaireBo.setModifiedBy(sesObj.getUserId());
							questionnaireBo.setModifiedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							questionnaireBo.setCreatedBy(sesObj.getUserId());
							questionnaireBo.setCreatedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
						}
						updateQuestionnaireBo = studyQuestionnaireService.saveOrUpdateQuestionnaire(questionnaireBo);
						if(updateQuestionnaireBo != null){
							jsonobject.put("questionnaireId", updateQuestionnaireBo.getId());
							if(updateQuestionnaireBo.getQuestionnairesFrequenciesBo() != null){
								jsonobject.put("questionnaireFrequenceId", updateQuestionnaireBo.getQuestionnairesFrequenciesBo().getId());
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
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/deleteQuestionnaireStep.do",method = RequestMethod.POST)
	public void deleteQuestionnaireStepInfo(HttpServletRequest request ,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - deleteQuestionnaireStepInfo - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = fdahpStudyDesignerConstants.SUCCESS;
		QuestionnaireBo questionnaireBo = null;
		Map<Integer, QuestionnaireStepBean> qTreeMap = new TreeMap<Integer, QuestionnaireStepBean>();
		ObjectMapper mapper = new ObjectMapper();
		JSONObject questionnaireJsonObject = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String stepId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("stepId")) == true?"":request.getParameter("stepId");
				String stepType = fdahpStudyDesignerUtil.isEmpty(request.getParameter("stepType")) == true?"":request.getParameter("stepType");
				String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
				if(!stepId.isEmpty() && !questionnaireId.isEmpty() && !stepType.isEmpty()){
					message = studyQuestionnaireService.deleteQuestionnaireStep(Integer.valueOf(stepId),Integer.valueOf(questionnaireId),stepType,sesObj);
					if(message.equalsIgnoreCase(fdahpStudyDesignerConstants.SUCCESS)){
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			int oldOrderNumber = 0;
			int newOrderNumber = 0;
			if(sesObj!=null){
				String questionnaireId = (String) request.getSession().getAttribute("questionnaireId");
				if(StringUtils.isEmpty(questionnaireId)){
					questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
				}
				String oldOrderNo = fdahpStudyDesignerUtil.isEmpty(request.getParameter("oldOrderNumber")) == true?"":request.getParameter("oldOrderNumber");
				String newOrderNo = fdahpStudyDesignerUtil.isEmpty(request.getParameter("newOrderNumber")) == true?"":request.getParameter("newOrderNumber");
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = (String) request.getSession().getAttribute("studyId");
				if(StringUtils.isEmpty(studyId)){
					studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
					request.getSession().setAttribute("studyId", studyId);
				}
				String shortTitle = fdahpStudyDesignerUtil.isEmpty(request.getParameter("shortTitle")) == true?"":request.getParameter("shortTitle");
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
				String stepType = fdahpStudyDesignerUtil.isEmpty(request.getParameter("stepType")) == true?"":request.getParameter("stepType");
				String shortTitle = fdahpStudyDesignerUtil.isEmpty(request.getParameter("shortTitle")) == true?"":request.getParameter("shortTitle");
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
		logger.info("StudyQuestionnaireController - getFormStepPage - Ends");
		ModelAndView mav = new ModelAndView("formStepPage");
		String sucMsg = "";
		String errMsg = "";
		ModelMap map = new ModelMap();
		QuestionnaireBo questionnaireBo = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		StudyBo studyBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			request.getSession().removeAttribute("questionId");
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG)){
				sucMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.SUC_MSG, sucMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.SUC_MSG);
			}
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.ERR_MSG);
			}
			String formId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("formId")) == true?"":request.getParameter("formId");
			String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
			String studyId = (String) request.getSession().getAttribute("studyId");
			if(StringUtils.isEmpty(studyId)){
				studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				request.getSession().setAttribute("studyId", studyId);
			}
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				map.addAttribute("studyBo", studyBo);
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
				questionnaireBo = studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
				map.addAttribute("questionnaireBo", questionnaireBo);
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(formId!= null && !formId.isEmpty()){
				questionnairesStepsBo = studyQuestionnaireService.getQuestionnaireStep(Integer.valueOf(formId), fdahpStudyDesignerConstants.FORM_STEP);
				if(questionnairesStepsBo != null){
					List<QuestionnairesStepsBo> destionationStepList = studyQuestionnaireService.getQuestionnairesStepsList(questionnairesStepsBo.getQuestionnairesId(), questionnairesStepsBo.getSequenceNo());
					map.addAttribute("destinationStepList", destionationStepList);
				}
				map.addAttribute("questionnairesStepsBo", questionnairesStepsBo);
				request.getSession().setAttribute("formId", formId);
			}
			map.addAttribute("questionnaireId", questionnaireId);
			mav = new ModelAndView("formStepPage",map);
		}catch(Exception e){
			logger.info("StudyQuestionnaireController - getFormStepPage - Error",e);
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
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(questionnairesStepsBo != null){
					if(questionnairesStepsBo.getStepId() != null){
						questionnairesStepsBo.setModifiedBy(sesObj.getUserId());
						questionnairesStepsBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						questionnairesStepsBo.setCreatedBy(sesObj.getUserId());
						questionnairesStepsBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addQuestionnairesStepsBo= studyQuestionnaireService.saveOrUpdateFromStepQuestionnaire(questionnairesStepsBo);
				}
				if(addQuestionnairesStepsBo != null){
					if(questionnairesStepsBo.getStepId() != null){
						request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Form Step updated successfully.");
					}else{
						request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Form Step added successfully.");
					}
					mav = new ModelAndView("redirect:/adminStudies/viewQuestionnaire.do",map);
				}else{
					request.getSession().setAttribute(fdahpStudyDesignerConstants.ERR_MSG, "Form not added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/formStep.do", map);
				}
			}
		}catch(Exception e){
			logger.info("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Error",e);
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
		logger.info("StudyQuestionnaireController - saveFormStep - Ends");
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		QuestionnairesStepsBo questionnairesStepsBo=null;
		ObjectMapper mapper = new ObjectMapper();
		QuestionnairesStepsBo addQuestionnairesStepsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String questionnaireStepInfo = request.getParameter("questionnaireStepInfo");
				if(null != questionnaireStepInfo){
					questionnairesStepsBo = mapper.readValue(questionnaireStepInfo, QuestionnairesStepsBo.class);
					if(questionnairesStepsBo != null){
						if(questionnairesStepsBo.getStepId() != null){
							questionnairesStepsBo.setModifiedBy(sesObj.getUserId());
							questionnairesStepsBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							questionnairesStepsBo.setCreatedBy(sesObj.getUserId());
							questionnairesStepsBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
						}
						addQuestionnairesStepsBo = studyQuestionnaireService.saveOrUpdateFromStepQuestionnaire(questionnairesStepsBo);
					}
				}
				if(addQuestionnairesStepsBo != null){
					jsonobject.put("stepId", addQuestionnairesStepsBo.getStepId());
					message = fdahpStudyDesignerConstants.SUCCESS;
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			int oldOrderNumber;
			int newOrderNumber;
			if(sesObj!=null){
				String formId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("formId")) == true?"":request.getParameter("formId");
				String oldOrderNo = fdahpStudyDesignerUtil.isEmpty(request.getParameter("oldOrderNumber")) == true?"":request.getParameter("oldOrderNumber");
				String newOrderNo = fdahpStudyDesignerUtil.isEmpty(request.getParameter("newOrderNumber")) == true?"":request.getParameter("newOrderNumber");
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		Map<Integer, QuestionnaireStepBean> qTreeMap = new TreeMap<>();
		ObjectMapper mapper = new ObjectMapper();
		JSONObject questionnaireJsonObject = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String formId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("formId")) == true?"":request.getParameter("formId");
				String questionId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionId")) == true?"":request.getParameter("questionId");
				if(!formId.isEmpty() && !questionId.isEmpty()){
					message = studyQuestionnaireService.deleteFromStepQuestion(Integer.valueOf(formId),Integer.valueOf(questionId),sesObj);
					if(message.equalsIgnoreCase(fdahpStudyDesignerConstants.SUCCESS)){
						questionnairesStepsBo=studyQuestionnaireService.getQuestionnaireStep(Integer.valueOf(formId), fdahpStudyDesignerConstants.FORM_STEP);
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
		logger.info("StudyQuestionnaireController - getQuestionStepPage - Ends");
		ModelAndView mav = new ModelAndView("questionStepPage");
		String sucMsg = "";
		String errMsg = "";
		ModelMap map = new ModelMap();
		QuestionnaireBo questionnaireBo = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		StudyBo studyBo = null;
		List<String> timeRangeList = new ArrayList<String>();
		List<StatisticImageListBo> statisticImageList = new ArrayList<>();
		List<ActivetaskFormulaBo> activetaskFormulaList = new ArrayList<>();
		List<QuestionResponseTypeMasterInfoBo> questionResponseTypeMasterInfoList = new ArrayList<>();
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG)){
				sucMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.SUC_MSG, sucMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.SUC_MSG);
			}
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.ERR_MSG);
			}
			String questionId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionId")) == true?"":request.getParameter("questionId");
			String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
			String studyId = (String) request.getSession().getAttribute("studyId");
			if(StringUtils.isEmpty(studyId)){
				studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				request.getSession().setAttribute("studyId", studyId);
			}
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				map.addAttribute("studyBo", studyBo);
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
				questionnaireBo = studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
				map.addAttribute("questionnaireBo", questionnaireBo);
				if(questionnaireBo!=null && StringUtils.isNotEmpty(questionnaireBo.getFrequency())){
					timeRangeList = fdahpStudyDesignerUtil.getTimeRangeList(questionnaireBo.getFrequency());
				}
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(questionId!= null && !questionId.isEmpty()){
				questionnairesStepsBo = studyQuestionnaireService.getQuestionnaireStep(Integer.valueOf(questionId), fdahpStudyDesignerConstants.QUESTION_STEP);
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
			map.addAttribute("timeRangeList", timeRangeList);
			map.addAttribute("statisticImageList", statisticImageList);
			map.addAttribute("activetaskFormulaList", activetaskFormulaList);
			map.addAttribute("questionnaireId", questionnaireId);
			map.addAttribute("questionResponseTypeMasterInfoList",questionResponseTypeMasterInfoList);
			mav = new ModelAndView("questionStepPage",map);
		}catch(Exception e){
			logger.info("StudyQuestionnaireController - getQuestionStepPage - Error",e);
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
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(questionnairesStepsBo != null){
					if(questionnairesStepsBo.getStepId() != null){
						questionnairesStepsBo.setModifiedBy(sesObj.getUserId());
						questionnairesStepsBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						questionnairesStepsBo.setCreatedBy(sesObj.getUserId());
						questionnairesStepsBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addQuestionnairesStepsBo= studyQuestionnaireService.saveOrUpdateQuestionStep(questionnairesStepsBo);
				}
				if(addQuestionnairesStepsBo != null){
					if(questionnairesStepsBo.getStepId() != null){
						request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Question Step updated successfully.");
					}else{
						request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Question Step added successfully.");
					}
					mav = new ModelAndView("redirect:/adminStudies/viewQuestionnaire.do",map);
				}else{
					request.getSession().setAttribute(fdahpStudyDesignerConstants.ERR_MSG, "Form not added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/questionStep.do", map);
				}
			}
		}catch(Exception e){
			logger.info("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Error",e);
		}
		logger.info("StudyQuestionnaireController - saveOrUpdateFormStepQuestionnaire - Ends");
		return mav;
	}
	
	/**
	 * @author Ravinder
	 * @param request
	 * @param response
	 */
	@RequestMapping(value="/adminStudies/saveQuestionStep.do")
	public void saveQuestionStep(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - saveQuestionStep - Ends");
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		QuestionnairesStepsBo questionnairesStepsBo=null;
		ObjectMapper mapper = new ObjectMapper();
		QuestionnairesStepsBo addQuestionnairesStepsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String questionnaireStepInfo = request.getParameter("questionnaireStepInfo");
				if(null != questionnaireStepInfo){
					questionnairesStepsBo = mapper.readValue(questionnaireStepInfo, QuestionnairesStepsBo.class);
					if(questionnairesStepsBo != null){
						if(questionnairesStepsBo.getStepId() != null){
							questionnairesStepsBo.setModifiedBy(sesObj.getUserId());
							questionnairesStepsBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							questionnairesStepsBo.setCreatedBy(sesObj.getUserId());
							questionnairesStepsBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
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
					message = fdahpStudyDesignerConstants.SUCCESS;
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
		logger.info("StudyQuestionnaireController - getFormStepQuestionPage - Ends");
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
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG)){
				sucMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.SUC_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.SUC_MSG, sucMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.SUC_MSG);
			}
			if(null != request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG)){
				errMsg = (String) request.getSession().getAttribute(fdahpStudyDesignerConstants.ERR_MSG);
				map.addAttribute(fdahpStudyDesignerConstants.ERR_MSG, errMsg);
				request.getSession().removeAttribute(fdahpStudyDesignerConstants.ERR_MSG);
			}
			String formId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("formId")) == true?"":request.getParameter("formId");
			String questionId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionId")) == true?"":request.getParameter("questionId");
			String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
			String studyId = (String) request.getSession().getAttribute("studyId");
			if(StringUtils.isEmpty(studyId)){
				studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				request.getSession().setAttribute("studyId", studyId);
			}
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = studyService.getStudyById(studyId, sesObj.getUserId());
				map.addAttribute("studyBo", studyBo);
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
				questionnaireBo = studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
				map.addAttribute("questionnaireBo", questionnaireBo);
				request.getSession().setAttribute("questionnaireId", questionnaireId);
			}
			if(formId!= null && !formId.isEmpty()){
				if(questionId != null && !questionId.isEmpty()){
					questionsBo = studyQuestionnaireService.getQuestionsById(Integer.valueOf(questionId));
					map.addAttribute("questionsBo", questionsBo);
					request.getSession().setAttribute("questionId", questionId);
				}
				map.addAttribute("formId", formId);
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
			logger.info("StudyQuestionnaireController - getFormStepQuestionPage - Error",e);
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
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				if(questionsBo != null){
					if(questionsBo.getId() != null){
						questionsBo.setModifiedBy(sesObj.getUserId());
						questionsBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					}else{
						questionsBo.setCreatedBy(sesObj.getUserId());
						questionsBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					}
					addQuestionsBo= studyQuestionnaireService.saveOrUpdateQuestion(questionsBo);
				}
				if(addQuestionsBo != null){
					if(addQuestionsBo.getId() != null){
						request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Form Question updated successfully.");
					}else{
						request.getSession().setAttribute(fdahpStudyDesignerConstants.SUC_MSG, "Form Question added successfully.");
					}
					mav = new ModelAndView("redirect:/adminStudies/formStep.do",map);
				}else{
					request.getSession().setAttribute(fdahpStudyDesignerConstants.ERR_MSG, "Form not added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/formQuestion.do", map);
				}
			}
		}catch(Exception e){
			logger.info("StudyQuestionnaireController - saveOrUpdateFormQuestion - Error",e);
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
	public void saveQuestion(HttpServletRequest request,HttpServletResponse response){
		logger.info("StudyQuestionnaireController - saveQuestion - Ends");
		String message = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		QuestionsBo questionsBo=null;
		ObjectMapper mapper = new ObjectMapper();
		QuestionsBo addQuestionsBo = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!= null){
				String questionnaireStepInfo = request.getParameter("questionInfo");
				if(null != questionnaireStepInfo){
					questionsBo = mapper.readValue(questionnaireStepInfo, QuestionsBo.class);
					if(questionsBo != null){
						if(questionsBo.getId() != null){
							questionsBo.setModifiedBy(sesObj.getUserId());
							questionsBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
						}else{
							questionsBo.setCreatedBy(sesObj.getUserId());
							questionsBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
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
					message = fdahpStudyDesignerConstants.SUCCESS;
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
		String message = fdahpStudyDesignerConstants.SUCCESS;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String studyId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("studyId")) == true?"":request.getParameter("studyId");
				String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
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
}
