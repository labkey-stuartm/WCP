/**
 * 
 */
package com.fdahpStudyDesigner.dao;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fdahpStudyDesigner.bo.ConsentInfoBo;
import com.fdahpStudyDesigner.bo.FormBo;
import com.fdahpStudyDesigner.bo.FormMappingBo;
import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.QuestionnaireCustomScheduleBo;
import com.fdahpStudyDesigner.bo.QuestionnairesFrequenciesBo;
import com.fdahpStudyDesigner.bo.QuestionnairesStepsBo;
import com.fdahpStudyDesigner.bo.QuestionsBo;
import com.fdahpStudyDesigner.bo.QuestionsResponseTypeBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.util.FdahpStudyDesignerPreHandlerInterceptor;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

/**
 * @author Vivek
 *
 */
@Repository
public class StudyQuestionnaireDAOImpl implements StudyQuestionnaireDAO{
	private static Logger logger = Logger.getLogger(StudyQuestionnaireDAOImpl.class.getName());
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	String queryString = "";
	
	public StudyQuestionnaireDAOImpl() {
	}

	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	
	
	
	
	/**
	 * return  Questionnaires based on user's Study Id
	 * @author Vivek
	 * 
	 * @param studyId, studyId of the {@link StudyBo}
	 * @return List of {@link QuestionnaireBo}
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionnaireBo> getStudyQuestionnairesByStudyId(String studyId) {
		logger.info("StudyQuestionnaireDAOImpl - getStudyQuestionnairesByStudyId() - Starts");
		Session session = null;
		List<QuestionnaireBo> questionnaires = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if (StringUtils.isNotEmpty(studyId)) {
				query = session.getNamedQuery("getQuestionariesByStudyId").setInteger("studyId", Integer.parseInt(studyId));
				questionnaires = query.list();
			}
		} catch (Exception e) {
			logger.error(
					"StudyQuestionnaireDAOImpl - getStudyQuestionnairesByStudyId() - ERROR ", e);
		} finally {
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - getStudyQuestionnairesByStudyId() - Ends");
		return questionnaires;
	}




	/**
	 * @author Ravinder
	 * @param Integer : instructionId
	 * @return Object : InstructutionBo
	 * 
	 * This method is used get the instruction of an questionnaire in study
	 */
	@Override
	public InstructionsBo getInstructionsBo(Integer instructionId) {
		logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo() - Starts");
		Session session = null;
		InstructionsBo instructionsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			instructionsBo = (InstructionsBo) session.get(InstructionsBo.class, instructionId);
		}catch (Exception e) {
			logger.error("StudyQuestionnaireDAOImpl - getInstructionsBo() - ERROR ", e);
		} finally {
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo - Ends");
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
		logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo() - Starts");
		Session session = null;
		QuestionnairesStepsBo existedQuestionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			session.saveOrUpdate(instructionsBo);
			if(instructionsBo != null && instructionsBo.getId() != null){
				if(instructionsBo.getModifiedBy() == null){
					QuestionnairesStepsBo questionnairesStepsBo = new QuestionnairesStepsBo();
					questionnairesStepsBo.setQuestionnairesId(instructionsBo.getQuestionnaireId());
					questionnairesStepsBo.setInstructionFormId(instructionsBo.getId());
					questionnairesStepsBo.setStepType(fdahpStudyDesignerConstants.INSTRUCTION_STEP);
					if(instructionsBo.getQuestionnaireId() != null){
						int count = 0;
						session = hibernateTemplate.getSessionFactory().openSession();
						query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+instructionsBo.getQuestionnaireId()+" order by QSBO.sequenceNo DESC");
						query.setMaxResults(1);
						existedQuestionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
						if(existedQuestionnairesStepsBo != null){
							count = existedQuestionnairesStepsBo.getSequenceNo()+1;
						}else{
							count = count +1;
						}
						questionnairesStepsBo.setSequenceNo(count);
					}
					session.save(questionnairesStepsBo);
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo() - Starts");
		return instructionsBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer :questionnaireId
	 * @return Object : QuestionnaireBo
	 * 
	 * This method is used to get the questionnaire of an study by using the questionnaireId
	 */

	@SuppressWarnings("unchecked")
	@Override
	public QuestionnaireBo getQuestionnaireById(Integer questionnaireId) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireById() - Starts");
		Session session = null;
		QuestionnaireBo questionnaireBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			questionnaireBo = (QuestionnaireBo) session.get(QuestionnaireBo.class, questionnaireId);
			if(null != questionnaireBo){
				String searchQuery="";
				if(null!=questionnaireBo.getFrequency() && questionnaireBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE)){
					searchQuery = "From QuestionnaireCustomScheduleBo QCSBO where QCSBO.questionnairesId="+questionnaireBo.getId();
					query = session.createQuery(searchQuery);
					List<QuestionnaireCustomScheduleBo> questionnaireCustomScheduleList = query.list();
					questionnaireBo.setQuestionnaireCustomScheduleBo(questionnaireCustomScheduleList);
				}else{
					searchQuery = "From QuestionnairesFrequenciesBo QFBO where QFBO.questionnairesId="+questionnaireBo.getId();
					query = session.createQuery(searchQuery);
					if(questionnaireBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_DAILY)){
						List<QuestionnairesFrequenciesBo> questionnairesFrequenciesList = query.list();	
						questionnaireBo.setQuestionnairesFrequenciesList(questionnairesFrequenciesList);
					}else{
						QuestionnairesFrequenciesBo questionnairesFrequenciesBo = (QuestionnairesFrequenciesBo) query.uniqueResult();
						questionnaireBo.setQuestionnairesFrequenciesBo(questionnairesFrequenciesBo);
					}
					
				}
			}
			
		}catch(Exception e){
			logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireById() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireById() - Ends");
		return questionnaireBo;
	}

	/**
	 * @author Ravinder
	 * @param Object : Questionnaire
	 * @return Object : Questionnaire
	 * 
	 * This method is used to save the questionnaire information od an study
	 */

	@Override
	public QuestionnaireBo saveORUpdateQuestionnaire(QuestionnaireBo questionnaireBo) {
		logger.info("StudyQuestionnaireDAOImpl - saveORUpdateQuestionnaire() - Starts");
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(questionnaireBo);
			if(questionnaireBo.getType().equalsIgnoreCase(fdahpStudyDesignerConstants.SCHEDULE)){
				if(questionnaireBo != null &&  questionnaireBo.getId() != null){
					if(questionnaireBo.getQuestionnairesFrequenciesList() != null && questionnaireBo.getQuestionnairesFrequenciesList().size() > 0){
						String deleteQuery = "delete from questionnaires_custom_frequencies where questionnaires_id="+questionnaireBo.getId();
						query = session.createSQLQuery(deleteQuery);
						query.executeUpdate();
						String deleteQuery2 = "delete from questionnaires_frequencies where questionnaires_id="+questionnaireBo.getId();
						query = session.createSQLQuery(deleteQuery2);
						query.executeUpdate();
						for(QuestionnairesFrequenciesBo questionnairesFrequenciesBo : questionnaireBo.getQuestionnairesFrequenciesList()){
							if(questionnairesFrequenciesBo.getFrequencyTime() != null){
								if(questionnairesFrequenciesBo.getQuestionnairesId() == null){
									questionnairesFrequenciesBo.setId(null);
									questionnairesFrequenciesBo.setQuestionnairesId(questionnaireBo.getId());
								}
								session.saveOrUpdate(questionnairesFrequenciesBo);
							}
						} 
					}
					if(questionnaireBo.getQuestionnairesFrequenciesBo() != null){
						QuestionnairesFrequenciesBo questionnairesFrequenciesBo = questionnaireBo.getQuestionnairesFrequenciesBo();
						if(questionnairesFrequenciesBo.getFrequencyDate() != null || questionnairesFrequenciesBo.getFrequencyTime() != null || questionnaireBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME)){
							if(!questionnaireBo.getFrequency().equalsIgnoreCase(questionnaireBo.getPreviousFrequency())){
								String deleteQuery = "delete from questionnaires_custom_frequencies where questionnaires_id="+questionnaireBo.getId();
								query = session.createSQLQuery(deleteQuery);
								query.executeUpdate();
								String deleteQuery2 = "delete from questionnaires_frequencies where questionnaires_id="+questionnaireBo.getId();
								query = session.createSQLQuery(deleteQuery2);
								query.executeUpdate();
							}
							if(questionnairesFrequenciesBo.getQuestionnairesId() == null){
								questionnairesFrequenciesBo.setQuestionnairesId(questionnaireBo.getId());
							}
							if(questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate() != null && !questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate().isEmpty()){
								questionnairesFrequenciesBo.setFrequencyDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate())));
							}
							session.saveOrUpdate(questionnairesFrequenciesBo);
						}
					}
					if(questionnaireBo.getQuestionnaireCustomScheduleBo() != null && questionnaireBo.getQuestionnaireCustomScheduleBo().size() > 0){
						String deleteQuery = "delete from questionnaires_custom_frequencies where questionnaires_id="+questionnaireBo.getId();
						query = session.createSQLQuery(deleteQuery);
						query.executeUpdate();
						String deleteQuery2 = "delete from questionnaires_frequencies where questionnaires_id="+questionnaireBo.getId();
						query = session.createSQLQuery(deleteQuery2);
						query.executeUpdate();
						for(QuestionnaireCustomScheduleBo questionnaireCustomScheduleBo  : questionnaireBo.getQuestionnaireCustomScheduleBo()){
							if(questionnaireCustomScheduleBo.getFrequencyStartDate() != null && !questionnaireCustomScheduleBo.getFrequencyStartDate().isEmpty() && questionnaireCustomScheduleBo.getFrequencyEndDate() != null 
									&& !questionnaireCustomScheduleBo.getFrequencyEndDate().isEmpty() && questionnaireCustomScheduleBo.getFrequencyTime() != null){
								if(questionnaireCustomScheduleBo.getQuestionnairesId() == null){
									questionnaireCustomScheduleBo.setQuestionnairesId(questionnaireBo.getId());
								}
								questionnaireCustomScheduleBo.setFrequencyStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(questionnaireCustomScheduleBo.getFrequencyStartDate())));
								questionnaireCustomScheduleBo.setFrequencyEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(questionnaireCustomScheduleBo.getFrequencyEndDate())));
								session.saveOrUpdate(questionnaireCustomScheduleBo);
							}
						}
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.info("StudyQuestionnaireDAOImpl - saveORUpdateQuestionnaire() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - saveORUpdateQuestionnaire() - Ends");
		return questionnaireBo;
	}
	
	//public String deleteExistedSchedule(Integer questionInteger)
	/**
	 * @author Ravinder
	 * @param Integer : questionId
	 * @return Object  : QuestionBo
	 * 
	 * This method is used to get QuestionBo based on questionId in Study questionnaire
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public QuestionsBo getQuestionsById(Integer questionId) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionsById() - Starts");
		Session session = null;
		QuestionsBo questionsBo = null;
		List<QuestionsResponseTypeBo> questionResponseList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			questionsBo = (QuestionsBo) session.get(QuestionsBo.class, questionId);
			if(questionsBo != null){
				query = session.createQuery("FROM QuestionsResponseTypeBo QRBO where QRBO.questionId="+questionsBo.getId());
				questionResponseList = query.list();
				questionsBo.setQuestionResponseList(questionResponseList);
			}
		}catch (Exception e) {
			logger.error("StudyQuestionnaireDAOImpl - getQuestionsById() - ERROR ", e);
		} finally {
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - getQuestionsById() - Ends");
		return questionsBo;
	}
	/**
	 * @author Ravinder
	 * @param Object : QuestionBo
	 * @return Object :QuestionBo
	 *  This method is used to add the question step in questionnaire of an study
	 */
	@Override
	public QuestionsBo saveOrUpdateQuestion(QuestionsBo questionsBo) {
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateQuestion() - Starts");
		Session session = null;
		QuestionnairesStepsBo existedQuestionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(questionsBo);
			if(questionsBo != null && questionsBo.getId() != null){
				if(questionsBo.getQuestionResponseList() != null && questionsBo.getQuestionResponseList().size() > 0){
					for(QuestionsResponseTypeBo questionsResponseTypeBo : questionsBo.getQuestionResponseList()){
						if(questionsResponseTypeBo.getQuestionId() == null){
							questionsResponseTypeBo.setQuestionId(questionsBo.getId());
						}
						session.saveOrUpdate(questionsResponseTypeBo);
					}
				}
				if(questionsBo.getStepType() != null && questionsBo.getModifiedBy() == null){
					if(questionsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.QUESTION_STEP)){
						QuestionnairesStepsBo questionnairesStepsBo = new QuestionnairesStepsBo();
						questionnairesStepsBo.setQuestionnairesId(questionsBo.getQuestionnaireId());
						questionnairesStepsBo.setInstructionFormId(questionsBo.getId());
						questionnairesStepsBo.setStepType(fdahpStudyDesignerConstants.QUESTION_STEP);
						if(questionsBo.getQuestionnaireId() != null){
							int count = 0;
							query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionsBo.getQuestionnaireId()+" order by QSBO.sequenceNo DESC");
							query.setMaxResults(1);
							existedQuestionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
							if(existedQuestionnairesStepsBo != null){
								count = existedQuestionnairesStepsBo.getSequenceNo()+1;
							}else{
								count = count +1;
							}
							questionnairesStepsBo.setSequenceNo(count);
						}
						session.save(questionnairesStepsBo);
					}else if(questionsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.FORM_STEP)){
						FormBo formBo = new FormBo();
						session.saveOrUpdate(formBo);
						if(null!= formBo && formBo.getFormId() != null){
							FormMappingBo formMappingBo = new FormMappingBo();
							formMappingBo.setFormId(formBo.getFormId());
							formMappingBo.setQuestionId(questionsBo.getId());
							session.save(formMappingBo);
							QuestionnairesStepsBo questionnairesStepsBo = new QuestionnairesStepsBo();
							questionnairesStepsBo.setQuestionnairesId(questionsBo.getQuestionnaireId());
							questionnairesStepsBo.setInstructionFormId(formBo.getFormId());
							questionnairesStepsBo.setStepType(fdahpStudyDesignerConstants.FORM_STEP);
							if(questionsBo.getQuestionnaireId() != null){
								int count = 0;
								query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionsBo.getQuestionnaireId()+" order by QSBO.sequenceNo DESC");
								query.setMaxResults(1);
								existedQuestionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
								if(existedQuestionnairesStepsBo != null){
									count = existedQuestionnairesStepsBo.getSequenceNo()+1;
								}else{
									count = count +1;
								}
								questionnairesStepsBo.setSequenceNo(count);
							}
							session.save(questionnairesStepsBo);
						}
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateQuestion() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateQuestion() - Ends");
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
		logger.info("StudyQuestionnaireDAOImpl - reOrderQuestionnaireSteps() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Query query = null;
		int count = 0;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String updateQuery ="";
			query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.sequenceNo ="+oldOrderNumber);
			questionnairesStepsBo = (QuestionnairesStepsBo)query.uniqueResult();
			if(questionnairesStepsBo != null){
				if (oldOrderNumber < newOrderNumber) {
					updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo-1 where QSBO.questionnairesId="+questionnaireId+" and QSBO.sequenceNo <="+newOrderNumber+" and QSBO.sequenceNo >"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update QuestionnairesStepsBo q set q.sequenceNo="+ newOrderNumber+" where q.stepId="+questionnairesStepsBo.getStepId());
						count = query.executeUpdate();
						message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo+1 where QSBO.questionnairesId="+questionnaireId+" and QSBO.sequenceNo >="+newOrderNumber+" and QSBO.sequenceNo <"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update QuestionnairesStepsBo Q set Q.sequenceNo="+ newOrderNumber+" where Q.stepId="+questionnairesStepsBo.getStepId());
						count = query.executeUpdate();
						message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - reOrderQuestionnaireSteps() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - reOrderQuestionnaireSteps() - Ends");
		return message;
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
	public String deleteQuestionnaireStep(Integer stepId) {
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Query query = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			questionnairesStepsBo = (QuestionnairesStepsBo) session.get(QuestionnairesStepsBo.class, stepId);
			if(questionnairesStepsBo != null){
				if(questionnairesStepsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.INSTRUCTION_STEP)){
					String deleteQuery = "delete from InstructionsBo IBO where IBO.id="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
				}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.QUESTION_STEP)){
					String deleteQuery = "delete from QuestionsBo QBO where QBO.id="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
				}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.FORM_STEP)){
					String deleteQuery = "delete from QuestionsBo QBO where QBO.id IN (select FMBO.questionId from FormMappingBo FMBO where FMBO.formId="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
					String formMappingDelete = "delete from FormMappingBo FMBO where FMBO.formId="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(formMappingDelete);
					query.executeUpdate();
					String formDelete = "delete from FormBo FBO where FBO.formId="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(formDelete);
					query.executeUpdate();
				}
				session.delete(questionnairesStepsBo);
				message = fdahpStudyDesignerConstants.SUCCESS;
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep() - Ends");
		return message;
	}


	@Override
	public void getQuestionnaireStepList(Integer questionnaireId) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStepList() - Ends");
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" order by QSBO.sequenceNo";
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - getQuestionnaireStepList() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStepList() - Ends");
	}
}
