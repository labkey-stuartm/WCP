package com.fdahpStudyDesigner.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.dao.StudyQuestionnaireDAO;

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
				if(instructionsBo.getButtonText() != null && !instructionsBo.getButtonText().isEmpty()){
					addOrUpdateInstructionsBo.setButtonText(instructionsBo.getButtonText());
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
	 * @param Object 
	 */
	@Override
	public QuestionnaireBo saveORUpdateQuestionnaire(QuestionnaireBo questionnaireBo) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public QuestionnaireBo saveOrUpdateQuestionnaireSchedule(QuestionnaireBo questionnaireBo) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
