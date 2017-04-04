package com.fdahpStudyDesigner.controller;

import java.io.PrintWriter;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bean.QuestionnaireStepBean;
import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.StudyBo;
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
		StudyBo studyBo = null;
		try{
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
			if(instructionId!= null && !instructionId.isEmpty()){
				instructionsBo = studyQuestionnaireService.getInstructionsBo(Integer.valueOf(instructionId));
				map.addAttribute("instructionsBo", instructionsBo);
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
					addInstructionsBo= studyQuestionnaireService.saveOrUpdateInstructionsBo(instructionsBo);
				}
				if(addInstructionsBo != null){
					request.getSession().setAttribute("sucMsg", "Instruction added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/viewStudyQuestionnaires.do",map);
				}else{
					request.getSession().setAttribute("errMsg", "Instruction not added successfully.");
					mav = new ModelAndView("redirect:/adminStudies/viewStudyQuestionnaires.do", map);
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
						}
						addInstructionsBo = studyQuestionnaireService.saveOrUpdateInstructionsBo(instructionsBo);
					}
				}
				if(addInstructionsBo != null){
					jsonobject.put("instructionId", addInstructionsBo.getId());
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
						for(Map.Entry<Integer,QuestionnaireStepBean> entry : qTreeMap.entrySet()) {
							  Integer key = entry.getKey();
							  QuestionnaireStepBean value = entry.getValue();
							  if(value.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.FORM_STEP)){
								 // List<QuestionnaireStepBean> formSteps = value.getFormList();
								  Map<Integer, QuestionnaireStepBean> formQuestionsMap = value.getFromMap();
								  for(Map.Entry<Integer,QuestionnaireStepBean> formquestion : formQuestionsMap.entrySet()) {
									  Integer sequenceNO = formquestion.getKey();
									  QuestionnaireStepBean questionnaireStepBean = formquestion.getValue();
									  System.out.println("#########################");
									  System.out.println("sequenceNO:"+sequenceNO);
									  System.out.println("form question title:"+questionnaireStepBean.getTitle());
									  System.out.println("#########################");
								  }
							  }
							  System.out.println(key + " => " + value.getTitle()+":"+value.getStepType()+"="+value.getStepId());
						}
					}
					map.addAttribute("questionnaireBo", questionnaireBo);
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
					addQuestionnaireBo = studyQuestionnaireService.saveORUpdateQuestionnaire(questionnaireBo);
					if(addQuestionnaireBo != null){
						request.getSession().setAttribute("sucMsg", "Consent added successfully.");
						mav = new ModelAndView("redirect:/adminStudies/viewQuestionnaire.do",map);
					}else{
						request.getSession().setAttribute("errMsg", "Consent not added successfully.");
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
						updateQuestionnaireBo = studyQuestionnaireService.saveORUpdateQuestionnaire(questionnaireBo);
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
		String message = fdahpStudyDesignerConstants.FAILURE;
		QuestionnaireBo questionnaireBo = null;
		Map<Integer, QuestionnaireStepBean> qTreeMap = new TreeMap<Integer, QuestionnaireStepBean>();
		ObjectMapper mapper = new ObjectMapper();
		JSONObject questionnaireJsonObject = null;
		try{
			SessionObject sesObj = (SessionObject) request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj!=null){
				String stepId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("stepId")) == true?"":request.getParameter("stepId");
				String questionnaireId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("questionnaireId")) == true?"":request.getParameter("questionnaireId");
				if(!stepId.isEmpty() && !questionnaireId.isEmpty()){
					message = studyQuestionnaireService.deleteQuestionnaireStep(Integer.valueOf(stepId),Integer.valueOf(questionnaireId));
					if(message.equalsIgnoreCase(fdahpStudyDesignerConstants.SUCCESS)){
						if(null!=questionnaireId && !questionnaireId.isEmpty()){
							questionnaireBo=studyQuestionnaireService.getQuestionnaireById(Integer.valueOf(questionnaireId));
								if(questionnaireBo != null){
									qTreeMap = studyQuestionnaireService.getQuestionnaireStepList(questionnaireBo.getId());
									questionnaireJsonObject = new JSONObject(mapper.writeValueAsString(qTreeMap));
									jsonobject.put("questionnaireJsonObject", questionnaireJsonObject);
								}
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
}
