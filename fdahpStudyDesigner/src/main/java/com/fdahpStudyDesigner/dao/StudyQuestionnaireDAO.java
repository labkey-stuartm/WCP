package com.fdahpStudyDesigner.dao;

import java.util.List;

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
}
