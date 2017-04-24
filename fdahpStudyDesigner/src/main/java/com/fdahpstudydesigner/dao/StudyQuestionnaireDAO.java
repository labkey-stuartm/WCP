package com.fdahpstudydesigner.dao;

import java.util.List;
import java.util.SortedMap;

import com.fdahpstudydesigner.bean.QuestionnaireStepBean;
import com.fdahpstudydesigner.bo.InstructionsBo;
import com.fdahpstudydesigner.bo.QuestionResponseTypeMasterInfoBo;
import com.fdahpstudydesigner.bo.QuestionnaireBo;
import com.fdahpstudydesigner.bo.QuestionnairesStepsBo;
import com.fdahpstudydesigner.bo.QuestionsBo;
import com.fdahpstudydesigner.util.SessionObject;

public interface StudyQuestionnaireDAO {
	public List<QuestionnaireBo> getStudyQuestionnairesByStudyId(String studyId);
	
	public InstructionsBo getInstructionsBo(Integer instructionId);
	public InstructionsBo saveOrUpdateInstructionsBo(InstructionsBo instructionsBo);
	
	public QuestionnaireBo getQuestionnaireById(Integer questionnaireId);
	public QuestionnaireBo saveORUpdateQuestionnaire(QuestionnaireBo questionnaireBo);
	public QuestionsBo getQuestionsById(Integer questionId);
	public QuestionsBo saveOrUpdateQuestion(QuestionsBo questionsBo);
	
	public String reOrderQuestionnaireSteps(Integer questionnaireId,int oldOrderNumber,int newOrderNumber);
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId,String stepType,SessionObject sessionObject);
	
	public SortedMap<Integer, QuestionnaireStepBean> getQuestionnaireStepList(Integer questionnaireId);
	public String checkQuestionnaireShortTitle(Integer studyId,String shortTitle);
	
	public QuestionnairesStepsBo getQuestionnaireStep(Integer stepId,String stepType);
	public String checkQuestionnaireStepShortTitle(Integer questionnaireId,String stepType,String shortTitle);
	
	public List<QuestionResponseTypeMasterInfoBo> getQuestionReponseTypeList();
	public QuestionnairesStepsBo saveOrUpdateFromQuestionnaireStep(QuestionnairesStepsBo questionnairesStepsBo); 
	
	public String reOrderFormStepQuestions(Integer formId,int oldOrderNumber,int newOrderNumber);
	public String deleteFromStepQuestion(Integer formId,Integer questionId,SessionObject sessionObject);
	
	public List<QuestionnairesStepsBo> getQuestionnairesStepsList(Integer questionnaireId,Integer sequenceNo);
	
	public QuestionnairesStepsBo saveOrUpdateQuestionStep(QuestionnairesStepsBo questionnairesStepsBo);
	public String deleteQuestuionnaireInfo(Integer studyId,Integer questionnaireId,SessionObject sessionObject);
	public String checkFromQuestionShortTitle(Integer questionnaireId,String shortTitle);
	
	public Boolean isAnchorDateExistsForStudy(Integer studyId);
	public Boolean isQuestionnairesCompleted(Integer studyId);
}
