/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.QuestionnairesFrequenciesBo;

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
	public String deleteQuestionnaireStep(Integer stepId);
}
