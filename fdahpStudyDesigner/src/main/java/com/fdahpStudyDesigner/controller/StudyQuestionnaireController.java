package com.fdahpStudyDesigner.controller;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bo.ConsentInfoBo;
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
	
	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
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
			map.addAttribute("studyBo", studyBo);
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

}
