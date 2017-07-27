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
	
	public InstructionsBo getInstructionsBo(Integer instructionId,String questionnaireShortTitle,String customStudyId,Integer questionnaireId);
	public InstructionsBo saveOrUpdateInstructionsBo(InstructionsBo instructionsBo, SessionObject sessionObject,String customStudyId);
	
	public QuestionnaireBo getQuestionnaireById(Integer questionnaireId,String customStudyId);
	public QuestionnaireBo saveORUpdateQuestionnaire(QuestionnaireBo questionnaireBo, SessionObject sessionObject,String customStudyId);
	public QuestionsBo getQuestionsById(Integer questionId,String questionnaireShortTitle);
	public QuestionsBo saveOrUpdateQuestion(QuestionsBo questionsBo);
	
	public String reOrderQuestionnaireSteps(Integer questionnaireId,int oldOrderNumber,int newOrderNumber);
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId,String stepType,SessionObject sessionObject,String customStudyId);
	
	public SortedMap<Integer, QuestionnaireStepBean> getQuestionnaireStepList(Integer questionnaireId);
	public String checkQuestionnaireShortTitle(Integer studyId,String shortTitle,String customStudyId);
	
	public QuestionnairesStepsBo getQuestionnaireStep(Integer stepId,String stepType, String questionnaireShortTitle,String customStudyId,Integer questionnaireId);
	public String checkQuestionnaireStepShortTitle(Integer questionnaireId,String stepType,String shortTitle,String questionnaireShortTitle,String customStudyId);
	
	public List<QuestionResponseTypeMasterInfoBo> getQuestionReponseTypeList();
	public QuestionnairesStepsBo saveOrUpdateFromQuestionnaireStep(QuestionnairesStepsBo questionnairesStepsBo, SessionObject sesObj,String customStudyId); 
	
	public String reOrderFormStepQuestions(Integer formId,int oldOrderNumber,int newOrderNumber);
	public String deleteFromStepQuestion(Integer formId,Integer questionId,SessionObject sessionObject,String customStudyId);
	
	public List<QuestionnairesStepsBo> getQuestionnairesStepsList(Integer questionnaireId,Integer sequenceNo);
	
	public QuestionnairesStepsBo saveOrUpdateQuestionStep(QuestionnairesStepsBo questionnairesStepsBo, SessionObject sessionObject,String customStudyId);
	public String deleteQuestuionnaireInfo(Integer studyId,Integer questionnaireId,SessionObject sessionObject,String customStudyId);
	public String checkFromQuestionShortTitle(Integer questionnaireId,String shortTitle,String questionnaireShortTitle,String customStudyId);
	
	public Boolean isAnchorDateExistsForStudy(Integer studyId,String customStudyId);
	public Boolean isQuestionnairesCompleted(Integer studyId);
	public String checkStatShortTitle(Integer studyId,String shortTitle,String customStudyId);
	
	public String checkQuestionnaireResponseTypeValidation(Integer studyId, String customStudyId);
	public String validateLineChartSchedule(Integer questionnaireId,String frequency);
	public String validateRepetableFormQuestionStats(Integer formId);
}
