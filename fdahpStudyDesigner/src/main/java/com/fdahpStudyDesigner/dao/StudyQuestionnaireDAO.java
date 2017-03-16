package com.fdahpStudyDesigner.dao;

import java.util.List;
import java.util.TreeMap;

import com.fdahpStudyDesigner.bean.QuestionnaireStepBean;
import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.QuestionsBo;

public interface StudyQuestionnaireDAO {
	public List<QuestionnaireBo> getStudyQuestionnairesByStudyId(String studyId);
	
	public InstructionsBo getInstructionsBo(Integer instructionId);
	public InstructionsBo saveOrUpdateInstructionsBo(InstructionsBo instructionsBo);
	
	public QuestionnaireBo getQuestionnaireById(Integer questionnaireId);
	public QuestionnaireBo saveORUpdateQuestionnaire(QuestionnaireBo questionnaireBo);
	public QuestionsBo getQuestionsById(Integer questionId);
	public QuestionsBo saveOrUpdateQuestion(QuestionsBo questionsBo);
	
	public String reOrderQuestionnaireSteps(Integer questionnaireId,int oldOrderNumber,int newOrderNumber);
	public String deleteQuestionnaireStep(Integer stepId);
	
	public TreeMap<Integer, QuestionnaireStepBean> getQuestionnaireStepList(Integer questionnaireId);
}
