package com.fdahpStudyDesigner.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bean.QuestionnaireStepBean;
import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionResponseTypeMasterInfoBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.QuestionnaireCustomScheduleBo;
import com.fdahpStudyDesigner.bo.QuestionnairesFrequenciesBo;
import com.fdahpStudyDesigner.bo.QuestionnairesStepsBo;
import com.fdahpStudyDesigner.bo.QuestionsBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.dao.StudyQuestionnaireDAO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

/**
 * 
 * @author Vivek
 *
 */
@Service
public class StudyQuestionnaireServiceImpl implements StudyQuestionnaireService{
	private static Logger logger = Logger.getLogger(StudyQuestionnaireServiceImpl.class);
	
	@Autowired
	private StudyQuestionnaireDAO studyQuestionnaireDAO;
	

	/*------------------------------------Added By Vivek Start---------------------------------------------------*/
	/**
	 * return  Questionnaires based on user's Study Id
	 * @author Vivek
	 * 
	 * @param studyId , studyId of the {@link StudyBo}
	 * @return List of {@link QuestionnaireBo}
	 * @exception Exception
	 */
	@Override
	public List<QuestionnaireBo> getStudyQuestionnairesByStudyId(String studyId) {
		logger.info("StudyQuestionnaireServiceImpl - getStudyQuestionnairesByStudyId() - Starts");
		List<QuestionnaireBo> questionnaires = null;
		try {
			questionnaires = studyQuestionnaireDAO.getStudyQuestionnairesByStudyId(studyId);
		} catch (Exception e) {
			logger.error("StudyQuestionnaireServiceImpl - getStudyQuestionnairesByStudyId() - ERROR ", e);
		}
		logger.info("StudyQuestionnaireServiceImpl - getStudyQuestionnairesByStudyId() - Ends");
		return questionnaires;
	}
	/*------------------------------------Added By Vivek End---------------------------------------------------*/


	/**
	 * @author Ravinder
	 * @param Integer : instructionId
	 * @return Object : InstructutionBo
	 * 
	 * This method is used get the instruction of an questionnaire in study
	 */
	@Override
	public InstructionsBo getInstructionsBo(Integer instructionId) {
		logger.info("StudyQuestionnaireServiceImpl - getInstructionsBo - Starts");
		InstructionsBo instructionsBo = null;
		try{
			instructionsBo = studyQuestionnaireDAO.getInstructionsBo(instructionId);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - getInstructionsBo - ERROR ", e);
		}
		logger.info("StudyQuestionnaireServiceImpl - getInstructionsBo - Ends");
		return instructionsBo;
	}
	
	/**
	 * @author Ravinder
	 * @param Object  :InstructionBo
	 * @return Object : InstructioBo
	 * 
	 * This method is used to save the instruction step of an questionnaire in study
	 */
	@Override
	public InstructionsBo saveOrUpdateInstructionsBo(InstructionsBo instructionsBo) {
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateInstructionsBo - Starts");
		InstructionsBo addOrUpdateInstructionsBo = null;
		try{
			if(null != instructionsBo){
				if(instructionsBo.getId() != null){
					addOrUpdateInstructionsBo = studyQuestionnaireDAO.getInstructionsBo(instructionsBo.getId());
				}else{
					addOrUpdateInstructionsBo = new InstructionsBo();
				}
				if(instructionsBo.getInstructionText() != null && !instructionsBo.getInstructionText().isEmpty()){
					addOrUpdateInstructionsBo.setInstructionText(instructionsBo.getInstructionText());
				}
				if(instructionsBo.getInstructionTitle() != null && !instructionsBo.getInstructionTitle().isEmpty()){
					addOrUpdateInstructionsBo.setInstructionTitle(instructionsBo.getInstructionTitle());
				}
				if(instructionsBo.getCreatedOn() != null && !instructionsBo.getCreatedOn().isEmpty()){
					addOrUpdateInstructionsBo.setCreatedOn(instructionsBo.getCreatedOn()); 
				}
				if(instructionsBo.getCreatedBy() != null){
					addOrUpdateInstructionsBo.setCreatedBy(instructionsBo.getCreatedBy());
				}
				if(instructionsBo.getModifiedOn() != null && !instructionsBo.getModifiedOn().isEmpty()){
					addOrUpdateInstructionsBo.setModifiedOn(instructionsBo.getModifiedOn());
				}
				if(instructionsBo.getModifiedBy() != null){
					addOrUpdateInstructionsBo.setModifiedBy(instructionsBo.getModifiedBy());
				}
				if(instructionsBo.getQuestionnaireId() != null){
					addOrUpdateInstructionsBo.setQuestionnaireId(instructionsBo.getQuestionnaireId());
				}
				if(instructionsBo.getQuestionnairesStepsBo() != null){
					addOrUpdateInstructionsBo.setQuestionnairesStepsBo(instructionsBo.getQuestionnairesStepsBo());
				}
				addOrUpdateInstructionsBo = studyQuestionnaireDAO.saveOrUpdateInstructionsBo(addOrUpdateInstructionsBo);
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - saveOrUpdateInstructionsBo - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateInstructionsBo - Ends");
		return addOrUpdateInstructionsBo;
	}

	/**
	 * @author Ravinder
	 * @param Object : Questionnaire
	 * @return Object : Questionnaire
	 * 
	 * This method is used to save the questionnaire information od an study
	 */
	@Override
	public QuestionnaireBo saveOrUpdateQuestionnaire(QuestionnaireBo questionnaireBo) {
		logger.info("StudyQuestionnaireServiceImpl - saveORUpdateQuestionnaire - Starts");
		QuestionnaireBo addQuestionnaireBo = null;
		try{
			if(null != questionnaireBo){
				if(questionnaireBo.getId() != null){
					addQuestionnaireBo = studyQuestionnaireDAO.getQuestionnaireById(questionnaireBo.getId());
				}else{
					addQuestionnaireBo = new QuestionnaireBo();
				}
				if(questionnaireBo.getStudyId() != null){
					addQuestionnaireBo.setStudyId(questionnaireBo.getStudyId());
				}
				if(questionnaireBo.getStudyLifetimeStart() != null && !questionnaireBo.getStudyLifetimeStart().isEmpty()){
					addQuestionnaireBo.setStudyLifetimeStart(fdahpStudyDesignerConstants.SD_DATE_FORMAT.format(fdahpStudyDesignerConstants.SDF_DATE_FORMAT.parse(questionnaireBo.getStudyLifetimeStart())));
				}
				if(questionnaireBo.getStudyLifetimeEnd()!= null && !questionnaireBo.getStudyLifetimeEnd().isEmpty()){
					addQuestionnaireBo.setStudyLifetimeEnd(fdahpStudyDesignerConstants.SD_DATE_FORMAT.format(fdahpStudyDesignerConstants.SDF_DATE_FORMAT.parse(questionnaireBo.getStudyLifetimeEnd())));
				}
				if(questionnaireBo.getFrequency() != null){
					addQuestionnaireBo.setFrequency(questionnaireBo.getFrequency());
				}
				if(questionnaireBo.getTitle() != null){
					addQuestionnaireBo.setTitle(questionnaireBo.getTitle());
				}
				if(questionnaireBo.getShortTitle() != null){
					addQuestionnaireBo.setShortTitle(questionnaireBo.getShortTitle());
				}
				if(questionnaireBo.getCreatedDate() != null){
					addQuestionnaireBo.setCreatedDate(questionnaireBo.getCreatedDate());
				}
				if(questionnaireBo.getCreatedBy() != null){
					addQuestionnaireBo.setCreatedBy(questionnaireBo.getCreatedBy());
				}
				if(questionnaireBo.getModifiedDate() != null){
					addQuestionnaireBo.setModifiedDate(questionnaireBo.getModifiedDate());
				}
				if(questionnaireBo.getModifiedBy() != null){
					addQuestionnaireBo.setModifiedBy(questionnaireBo.getModifiedBy());
				}
				if(questionnaireBo.getRepeatQuestionnaire() != null){
					addQuestionnaireBo.setRepeatQuestionnaire(questionnaireBo.getRepeatQuestionnaire());
				}
				if(questionnaireBo.getDayOfTheWeek() != null){
					addQuestionnaireBo.setDayOfTheWeek(questionnaireBo.getDayOfTheWeek());
				}
				if(questionnaireBo.getType() != null){
					addQuestionnaireBo.setType(questionnaireBo.getType());
				}
				if(questionnaireBo.getFrequency() != null){
					if(!questionnaireBo.getFrequency().equalsIgnoreCase(questionnaireBo.getPreviousFrequency())){
						addQuestionnaireBo.setQuestionnaireCustomScheduleBo(questionnaireBo.getQuestionnaireCustomScheduleBo());
						addQuestionnaireBo.setQuestionnairesFrequenciesList(questionnaireBo.getQuestionnairesFrequenciesList());
						if(questionnaireBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME)){
							if(questionnaireBo.getQuestionnairesFrequenciesBo() != null){
								if(questionnaireBo.getQuestionnairesFrequenciesBo().getIsLaunchStudy()){
									addQuestionnaireBo.setStudyLifetimeStart(null);
								}
								if(questionnaireBo.getQuestionnairesFrequenciesBo().getIsStudyLifeTime()){
									addQuestionnaireBo.setStudyLifetimeEnd(null);
								}
							}
						}
						addQuestionnaireBo.setQuestionnairesFrequenciesBo(questionnaireBo.getQuestionnairesFrequenciesBo());
					}else{
						if(questionnaireBo.getQuestionnaireCustomScheduleBo() != null && !questionnaireBo.getQuestionnaireCustomScheduleBo().isEmpty()){
							addQuestionnaireBo.setQuestionnaireCustomScheduleBo(questionnaireBo.getQuestionnaireCustomScheduleBo());
						}
						if(questionnaireBo.getQuestionnairesFrequenciesList() != null && !questionnaireBo.getQuestionnairesFrequenciesList().isEmpty()){
							addQuestionnaireBo.setQuestionnairesFrequenciesList(questionnaireBo.getQuestionnairesFrequenciesList());
						}
						if(questionnaireBo.getQuestionnairesFrequenciesBo()!= null){
							if(questionnaireBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME)){
								if(questionnaireBo.getQuestionnairesFrequenciesBo().getIsLaunchStudy()){
									addQuestionnaireBo.setStudyLifetimeStart(null);
								}
								if(questionnaireBo.getQuestionnairesFrequenciesBo().getIsStudyLifeTime()){
									addQuestionnaireBo.setStudyLifetimeEnd(null);
								}
							}
							addQuestionnaireBo.setQuestionnairesFrequenciesBo(questionnaireBo.getQuestionnairesFrequenciesBo());
						}
					}
				}
				if(questionnaireBo.getPreviousFrequency() != null){
					addQuestionnaireBo.setPreviousFrequency(questionnaireBo.getPreviousFrequency());
				}
				addQuestionnaireBo = studyQuestionnaireDAO.saveORUpdateQuestionnaire(addQuestionnaireBo);
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - saveORUpdateQuestionnaire - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - saveORUpdateQuestionnaire - Ends");
		return addQuestionnaireBo;
	}

	/**
	 * @author Ravinder
	 * @param Object : QuestionnaireBo
	 * @param @object : QuestionnaireBo
	 * 
	 * This method is used to save the questionnaire schedule information of an study
	 */
	@Override
	public QuestionnaireBo saveOrUpdateQuestionnaireSchedule(QuestionnaireBo questionnaireBo) {
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateQuestionnaireSchedule - Starts");
		QuestionnaireBo addQuestionnaireBo = null;
		try{
			addQuestionnaireBo = studyQuestionnaireDAO.saveORUpdateQuestionnaire(questionnaireBo);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - saveOrUpdateQuestionnaireSchedule - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateQuestionnaireSchedule - Ends");
		return addQuestionnaireBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer :questionnaireId
	 * @return Object : QuestionnaireBo
	 * 
	 * This method is used to get the questionnaire of an study by using the questionnaireId
	 */
	@Override
	public QuestionnaireBo getQuestionnaireById(Integer questionnaireId) {
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateQuestionnaireSchedule - Starts");
		QuestionnaireBo questionnaireBo=null;
		try{
			questionnaireBo = studyQuestionnaireDAO.getQuestionnaireById(questionnaireId);
			if(null != questionnaireBo){
				if(questionnaireBo.getStudyLifetimeStart() != null && !questionnaireBo.getStudyLifetimeStart().isEmpty()){
					questionnaireBo.setStudyLifetimeStart(fdahpStudyDesignerConstants.SDF_DATE_FORMAT.format(new SimpleDateFormat("yyyy-MM-dd").parse(questionnaireBo.getStudyLifetimeStart())));
				}
				if(questionnaireBo.getStudyLifetimeEnd() != null && !questionnaireBo.getStudyLifetimeEnd().isEmpty()){
					questionnaireBo.setStudyLifetimeEnd(fdahpStudyDesignerConstants.SDF_DATE_FORMAT.format(new SimpleDateFormat("yyyy-MM-dd").parse(questionnaireBo.getStudyLifetimeEnd())));
				}
				if(questionnaireBo.getQuestionnairesFrequenciesBo() != null && questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate() != null){
					questionnaireBo.getQuestionnairesFrequenciesBo().setFrequencyDate(fdahpStudyDesignerConstants.SDF_DATE_FORMAT.format(new SimpleDateFormat("yyyy-MM-dd").parse(questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate())));
				}
				if(questionnaireBo.getQuestionnairesFrequenciesList() != null && !questionnaireBo.getQuestionnairesFrequenciesList().isEmpty()){
					for(QuestionnairesFrequenciesBo questionnairesFrequenciesBo : questionnaireBo.getQuestionnairesFrequenciesList()){
						if(questionnairesFrequenciesBo.getFrequencyDate() != null){
							questionnairesFrequenciesBo.setFrequencyDate(fdahpStudyDesignerConstants.SDF_DATE_FORMAT.format(new SimpleDateFormat("yyyy-MM-dd").parse(questionnairesFrequenciesBo.getFrequencyDate())));
						}
					}
				}
				if(questionnaireBo.getQuestionnaireCustomScheduleBo() != null && !questionnaireBo.getQuestionnaireCustomScheduleBo().isEmpty()){
					for(QuestionnaireCustomScheduleBo questionnaireCustomScheduleBo : questionnaireBo.getQuestionnaireCustomScheduleBo()){
						if(questionnaireCustomScheduleBo.getFrequencyStartDate() != null){
							questionnaireCustomScheduleBo.setFrequencyStartDate(fdahpStudyDesignerConstants.SDF_DATE_FORMAT.format(new SimpleDateFormat("yyyy-MM-dd").parse(questionnaireCustomScheduleBo.getFrequencyStartDate())));
						}
						if(questionnaireCustomScheduleBo.getFrequencyEndDate() != null){
							questionnaireCustomScheduleBo.setFrequencyEndDate(fdahpStudyDesignerConstants.SDF_DATE_FORMAT.format(new SimpleDateFormat("yyyy-MM-dd").parse(questionnaireCustomScheduleBo.getFrequencyEndDate())));
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - saveOrUpdateQuestionnaireSchedule - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateQuestionnaireSchedule - Ends");
		return questionnaireBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer : stepId
	 * @return String SUCCESS or FAILURE
	 * 
	 * This method is used to delete the questionnaire step
	 * 
	 */
	@Override
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId,String stepType) {
		logger.info("StudyQuestionnaireServiceImpl - deleteQuestionnaireStep - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyQuestionnaireDAO.deleteQuestionnaireStep(stepId,questionnaireId,stepType);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - deleteQuestionnaireStep - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - deleteQuestionnaireStep - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * @param Object : QuestionBo
	 * @return Object :QuestionBo
	 *  This method is used to add the question step in questionnaire of an study
	 */
	@Override
	public QuestionsBo saveOrUpdateQuestion(QuestionsBo questionsBo) {
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateQuestion - Starts");
		QuestionsBo addQuestionsBo = null;
		try{
			if(null != questionsBo){
				if(questionsBo.getId() != null){
					addQuestionsBo = studyQuestionnaireDAO.getQuestionsById(questionsBo.getId());
				}else{
					addQuestionsBo = new QuestionsBo();
				}
				if(questionsBo.getQuestion() != null){
					addQuestionsBo.setQuestion(questionsBo.getQuestion());
				}
				if(questionsBo.getResponseType() != null){
					addQuestionsBo.setResponseType(questionsBo.getResponseType());
				}
				if(questionsBo.getCreatedOn() != null){
					addQuestionsBo.setCreatedOn(questionsBo.getCreatedOn());
				}
				if(questionsBo.getCreatedBy() != null){
					addQuestionsBo.setCreatedBy(questionsBo.getCreatedBy());
				}
				if(questionsBo.getModifiedOn() != null){
					addQuestionsBo.setModifiedOn(questionsBo.getModifiedOn());
				}
				if(questionsBo.getModifiedBy() != null){
					addQuestionsBo.setModifiedBy(questionsBo.getModifiedBy());
				}
				if(questionsBo.getQuestionResponseList() != null && !questionsBo.getQuestionResponseList().isEmpty()){
					addQuestionsBo.setQuestionResponseList(questionsBo.getQuestionResponseList());
				}
				addQuestionsBo = studyQuestionnaireDAO.saveOrUpdateQuestion(addQuestionsBo);
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - saveOrUpdateQuestion - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateQuestion - Ends");
		return addQuestionsBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer : questionId
	 * @return Object  : QuestionBo
	 * 
	 * This method is used to get QuestionBo based on questionId in Study questionnaire
	 * 
	 */
	@Override
	public QuestionsBo getQuestionsById(Integer questionId) {
		logger.info("StudyQuestionnaireServiceImpl - getQuestionsById - Starts");
		QuestionsBo questionsBo = null;
		try{
			questionsBo = studyQuestionnaireDAO.getQuestionsById(questionId);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - getQuestionsById - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - getQuestionsById - Ends");
		return questionsBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer questionnaireId
	 * @param int oldOrderNumber
	 * @param int newOrderNumber
	 * @return String SUCCESS or FAILURE
	 * 
	 * This method is used to update the order of an questionnaire steps
	 */
	@Override
	public String reOrderQuestionnaireSteps(Integer questionnaireId,int oldOrderNumber, int newOrderNumber) {
		logger.info("StudyQuestionnaireServiceImpl - reOrderQuestionnaireSteps - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyQuestionnaireDAO.reOrderQuestionnaireSteps(questionnaireId, oldOrderNumber, newOrderNumber);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - reOrderQuestionnaireSteps - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - reOrderQuestionnaireSteps - Starts");
		return message;
	}

	/**
	 * @author Ravinder
	 * @param Integer questionnaireId
	 * @return Map : TreeMap<Integer, QuestionnaireStepBean>
	 * 
	 * This method is used to get the all the step inside questionnaire 
	 */
	@Override
	public SortedMap<Integer, QuestionnaireStepBean> getQuestionnaireStepList(Integer questionnaireId) {
		logger.info("StudyQuestionnaireServiceImpl - getQuestionnaireStepList() - Starts");
		SortedMap<Integer, QuestionnaireStepBean> questionnaireStepMap = null;
		try{
			questionnaireStepMap = studyQuestionnaireDAO.getQuestionnaireStepList(questionnaireId);
			if(questionnaireStepMap != null){
				List<QuestionResponseTypeMasterInfoBo>	questionResponseTypeMasterInfoList =studyQuestionnaireDAO.getQuestionReponseTypeList();
				if(questionResponseTypeMasterInfoList != null && !questionResponseTypeMasterInfoList.isEmpty()){
					 for(QuestionResponseTypeMasterInfoBo questionResponseTypeMasterInfoBo : questionResponseTypeMasterInfoList){
						 for(Entry<Integer, QuestionnaireStepBean> entry : questionnaireStepMap.entrySet()){
							 QuestionnaireStepBean questionnaireStepBean = entry.getValue();
							 if(questionResponseTypeMasterInfoBo.getId().equals(questionnaireStepBean.getResponseType())){
								 questionnaireStepBean.setResponseTypeText(questionResponseTypeMasterInfoBo.getResponseType());
							 }
							 /*if(entry.getValue().getFromMap() != null){
								 for(Entry<Integer, QuestionnaireStepBean> entryKey : entry.getValue().getFromMap().entrySet()){
									 if(questionResponseTypeMasterInfoBo.getId().equals(entryKey.getValue().getResponseType())){
										 questionnaireStepBean.setResponseTypeText(questionResponseTypeMasterInfoBo.getResponseType());
									 }
								 }
							 }*/
						 }
					 }
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - getQuestionnaireStepList - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - getQuestionnaireStepList() - Ends");
		return questionnaireStepMap;
	}

	/**
	 * @author Ravinder
	 * @param Integer:studyId
	 * @param String : shortTitle
	 * @return String : SUCCESS or FAILURE
	 * 
	 * This method is used to check the if the questionnaire short title existed ot not in a study
	 *
	 */
	@Override
	public String checkQuestionnaireShortTitle(Integer studyId,String shortTitle) {
		logger.info("StudyQuestionnaireServiceImpl - checkQuestionnaireShortTitle() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyQuestionnaireDAO.checkQuestionnaireShortTitle(studyId, shortTitle);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - getQuestionnaireStepList - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - checkQuestionnaireShortTitle() - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * @param Integer : QuestionnaireId
	 * @param String : stepType
	 * @param String : shortTitle
	 */
	@Override
	public String checkQuestionnaireStepShortTitle(Integer questionnaireId,String stepType, String shortTitle) {
		logger.info("StudyQuestionnaireServiceImpl - checkQuestionnaireStepShortTitle - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyQuestionnaireDAO.checkQuestionnaireStepShortTitle(questionnaireId, stepType, shortTitle);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - checkQuestionnaireStepShortTitle - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - checkQuestionnaireStepShortTitle - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * 
	 * This method is used to get the Resonse Type Master Data
	 */
	@Override
	public List<QuestionResponseTypeMasterInfoBo> getQuestionReponseTypeList() {
		logger.info("StudyQuestionnaireServiceImpl - getQuestionReponseTypeList - Starts");
		List<QuestionResponseTypeMasterInfoBo> questionResponseTypeMasterInfoList = null;
		try{
			questionResponseTypeMasterInfoList = studyQuestionnaireDAO.getQuestionReponseTypeList();
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - getQuestionReponseTypeList - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - getQuestionReponseTypeList - Ends");
		return questionResponseTypeMasterInfoList;
	}

	/**
	 * @author Ravinder
	 * @param Object : QuestionnaireStepBo
	 * @return Object : QuestionnaireStepBo
	 * 
	 * This method is used to save the form step questionnaire
	 */
	@Override
	public QuestionnairesStepsBo saveOrUpdateFromStepQuestionnaire(QuestionnairesStepsBo questionnairesStepsBo) {
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateFromStepQuestionnaire - Starts");
		QuestionnairesStepsBo addOrUpdateQuestionnairesStepsBo = null;
		try{
			addOrUpdateQuestionnairesStepsBo = studyQuestionnaireDAO.saveOrUpdateFromQuestionnaireStep(questionnairesStepsBo);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - saveOrUpdateFromStepQuestionnaire - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - saveOrUpdateFromStepQuestionnaire - Starts");
		return addOrUpdateQuestionnairesStepsBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer : fromId
	 * @param int : oldOrderNumber
	 * @param int : newOrderNumber
	 * @return String : SUCCESS or FAILURE
	 */
	@Override
	public String reOrderFormStepQuestions(Integer formId, int oldOrderNumber,
			int newOrderNumber) {
		logger.info("StudyQuestionnaireServiceImpl - reOrderFormStepQuestions - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyQuestionnaireDAO.reOrderFormStepQuestions(formId, oldOrderNumber, newOrderNumber);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - reOrderFormStepQuestions - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - reOrderFormStepQuestions - Starts");
		return message;
	}

	/**
	 * @author Ravinder
	 * @param Integer formId
	 * @param Integer questionId;
	 * @return String SUCESS or FAILURE
	 * 
	 * This method is used to delete the question inside the form step
	 */
	@Override
	public String deleteFromStepQuestion(Integer formId, Integer questionId) {
		logger.info("StudyQuestionnaireServiceImpl - deleteFromStepQuestion - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			message = studyQuestionnaireDAO.deleteFromStepQuestion(formId, questionId);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - deleteFromStepQuestion - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - deleteFromStepQuestion - Ends");
		return message;
	}

	/**@author Ravinder
	 * @param Integer : stepId
	 * @return Object : questionnaireStepBo
	 * This method is used to get the step information of questionnaire in a study
	 */
	@Override
	public QuestionnairesStepsBo getQuestionnaireStep(Integer stepId,String stepType) {
		logger.info("StudyQuestionnaireServiceImpl - getQuestionnaireStep - Starts");
		QuestionnairesStepsBo questionnairesStepsBo=null;
		try{
			questionnairesStepsBo = studyQuestionnaireDAO.getQuestionnaireStep(stepId, stepType);
			if(questionnairesStepsBo != null){
				if(stepType.equalsIgnoreCase(fdahpStudyDesignerConstants.FORM_STEP)){
					if(questionnairesStepsBo.getFormQuestionMap() != null){
						List<QuestionResponseTypeMasterInfoBo>	questionResponseTypeMasterInfoList =studyQuestionnaireDAO.getQuestionReponseTypeList();
						if(questionResponseTypeMasterInfoList != null && !questionResponseTypeMasterInfoList.isEmpty()){
							 for(QuestionResponseTypeMasterInfoBo questionResponseTypeMasterInfoBo : questionResponseTypeMasterInfoList){
								 for(Entry<Integer, QuestionnaireStepBean> entry : questionnairesStepsBo.getFormQuestionMap().entrySet()){
									 QuestionnaireStepBean questionnaireStepBean = entry.getValue();
									 if(questionnaireStepBean.getResponseType()!= null && questionnaireStepBean.getResponseType().equals(questionResponseTypeMasterInfoBo.getId())){
										 questionnaireStepBean.setResponseTypeText(questionResponseTypeMasterInfoBo.getResponseType());
									 }
								 }
							 }
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - getQuestionnaireStep - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - getQuestionnaireStep - Ends");
		return questionnairesStepsBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer : questionnaireId
	 * @return List : QuestionnaireStepList
	 * This method is used to get the forward question step of an questionnaire based on sequence no
	 */
	@Override
	public List<QuestionnairesStepsBo> getQuestionnairesStepsList(Integer questionnaireId,Integer sequenceNo) {
		logger.info("StudyQuestionnaireServiceImpl - getQuestionnairesStepsList - Starts");
		List<QuestionnairesStepsBo> questionnairesStepsList = null;
		try{
			questionnairesStepsList = studyQuestionnaireDAO.getQuestionnairesStepsList(questionnaireId, sequenceNo);
		}catch(Exception e){
			logger.error("StudyQuestionnaireServiceImpl - getQuestionnairesStepsList - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - getQuestionnairesStepsList - Starts");
		return questionnairesStepsList;
	}
	
}
