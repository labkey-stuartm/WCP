/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.util.List;
import java.util.TreeMap;

import com.fdahpStudyDesigner.bean.QuestionnaireStepBean;
import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.QuestionnairesFrequenciesBo;
import com.fdahpStudyDesigner.bo.QuestionsBo;

/**
 * @author Vivek
 *
 */
public interface StudyQuestionnaireService {
	public List<QuestionnaireBo> getStudyQuestionnairesByStudyId(String studyId);
	
	public InstructionsBo getInstructionsBo(Integer instructionId);
	public InstructionsBo saveOrUpdateInstructionsBo(InstructionsBo instructionsBo);
	
	public QuestionnaireBo saveORUpdateQuestionnaire(QuestionnaireBo questionnaireBo);
	public QuestionnaireBo saveOrUpdateQuestionnaireSchedule(QuestionnaireBo questionnaireBo); 
	public QuestionnaireBo getQuestionnaireById(Integer questionnaireId);
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId);
	
	public QuestionsBo saveOrUpdateQuestion(QuestionsBo questionsBo);
	public QuestionsBo getQuestionsById(Integer questionId);
	
	public String reOrderQuestionnaireSteps(Integer questionnaireId,int oldOrderNumber,int newOrderNumber);
	
	public TreeMap<Integer, QuestionnaireStepBean> getQuestionnaireStepList(Integer questionnaireId);
	public String checkQuestionnaireShortTitle(Integer studyId,String shortTitle);
	
}
