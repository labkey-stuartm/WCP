/**
 * 
 */
package com.fdahpstudydesigner.service;

import java.util.List;
import java.util.SortedMap;

import com.fdahpstudydesigner.bean.QuestionnaireStepBean;
import com.fdahpstudydesigner.bo.InstructionsBo;
import com.fdahpstudydesigner.bo.QuestionResponseTypeMasterInfoBo;
import com.fdahpstudydesigner.bo.QuestionnaireBo;
import com.fdahpstudydesigner.bo.QuestionnairesStepsBo;
import com.fdahpstudydesigner.bo.QuestionsBo;
import com.fdahpstudydesigner.util.SessionObject;

/**
 * @author Vivek
 *
 */
public interface StudyQuestionnaireService {
	public List<QuestionnaireBo> getStudyQuestionnairesByStudyId(String studyId);
	
	public InstructionsBo getInstructionsBo(Integer instructionId);
	public InstructionsBo saveOrUpdateInstructionsBo(InstructionsBo instructionsBo, SessionObject sessionObject);
	
	public QuestionnaireBo saveOrUpdateQuestionnaire(QuestionnaireBo questionnaireBo, SessionObject sessionObject);
	public QuestionnaireBo saveOrUpdateQuestionnaireSchedule(QuestionnaireBo questionnaireBo, SessionObject sessionObject); 
	public QuestionnaireBo getQuestionnaireById(Integer questionnaireId);
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId,String stepType,SessionObject sessionObject);
	
	public QuestionsBo saveOrUpdateQuestion(QuestionsBo questionsBo, SessionObject sesObj);
	public QuestionsBo getQuestionsById(Integer questionId);
	
	public String reOrderQuestionnaireSteps(Integer questionnaireId,int oldOrderNumber,int newOrderNumber);
	
	public SortedMap<Integer, QuestionnaireStepBean> getQuestionnaireStepList(Integer questionnaireId);
	public String checkQuestionnaireShortTitle(Integer studyId,String shortTitle);
	public String checkQuestionnaireStepShortTitle(Integer questionnaireId,String stepType,String shortTitle);
	
	public List<QuestionResponseTypeMasterInfoBo> getQuestionReponseTypeList();
	
	public QuestionnairesStepsBo saveOrUpdateFromStepQuestionnaire(QuestionnairesStepsBo questionnairesStepsBo, SessionObject sesObj);
	public String reOrderFormStepQuestions(Integer formId,int oldOrderNumber,int newOrderNumber);
	public String deleteFromStepQuestion(Integer formId,Integer questionId,SessionObject sessionObject);
	public QuestionnairesStepsBo getQuestionnaireStep(Integer stepId,String stepType);
	
	public List<QuestionnairesStepsBo> getQuestionnairesStepsList(Integer questionnaireId,Integer sequenceNo);
	
	public QuestionnairesStepsBo saveOrUpdateQuestionStep(QuestionnairesStepsBo questionnairesStepsBo, SessionObject sessionObject);
	public String deletQuestionnaire(Integer studyId,Integer questionnaireId,SessionObject sessionObject);
	public String checkFromQuestionShortTitle(Integer questionnaireId,String shortTitle);
	
	public Boolean isAnchorDateExistsForStudy(Integer studyId);
	public Boolean isQuestionnairesCompleted(Integer studyId);
}
