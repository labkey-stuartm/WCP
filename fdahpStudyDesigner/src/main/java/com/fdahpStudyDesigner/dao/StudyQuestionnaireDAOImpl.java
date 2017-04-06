/**
 * 
 */
package com.fdahpStudyDesigner.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.w3c.dom.ls.LSInput;

import com.fdahpStudyDesigner.bean.QuestionnaireStepBean;
import com.fdahpStudyDesigner.bo.ConsentInfoBo;
import com.fdahpStudyDesigner.bo.FormBo;
import com.fdahpStudyDesigner.bo.FormMappingBo;
import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionResponseTypeMasterInfoBo;
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
			if(session != null){
				session.close();
			}
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
		logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo - Starts");
		Session session = null;
		InstructionsBo instructionsBo = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			instructionsBo = (InstructionsBo) session.get(InstructionsBo.class, instructionId);
			if(instructionsBo != null){
				query = session.getNamedQuery("getQuestionnaireStep").setInteger("instructionFormId", instructionsBo.getId()).setString("stepType", fdahpStudyDesignerConstants.INSTRUCTION_STEP);
				questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
				instructionsBo.setQuestionnairesStepsBo(questionnairesStepsBo);
			}
		}catch (Exception e) {
			logger.error("StudyQuestionnaireDAOImpl - getInstructionsBo() - ERROR ", e);
		} finally {
			if(session != null){
				session.close();
			}
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
			QuestionnairesStepsBo questionnairesStepsBo = null;
			if(instructionsBo != null && instructionsBo.getId() != null && instructionsBo.getQuestionnairesStepsBo() != null){
				if(instructionsBo.getQuestionnairesStepsBo().getStepId() != null){
					questionnairesStepsBo = (QuestionnairesStepsBo) session.get(QuestionnairesStepsBo.class, instructionsBo.getQuestionnairesStepsBo().getStepId());
				}else{
					questionnairesStepsBo = new QuestionnairesStepsBo();
				}
				questionnairesStepsBo.setQuestionnairesId(instructionsBo.getQuestionnaireId());
				questionnairesStepsBo.setInstructionFormId(instructionsBo.getId());
				questionnairesStepsBo.setStepType(fdahpStudyDesignerConstants.INSTRUCTION_STEP);
				if(instructionsBo.getQuestionnairesStepsBo().getStepShortTitle() != null && !instructionsBo.getQuestionnairesStepsBo().getStepShortTitle().isEmpty()){
					questionnairesStepsBo.setStepShortTitle(instructionsBo.getQuestionnairesStepsBo().getStepShortTitle());
				}
				if(instructionsBo.getQuestionnairesStepsBo().getSkiappable() != null && !instructionsBo.getQuestionnairesStepsBo().getSkiappable().isEmpty()){
					questionnairesStepsBo.setSkiappable(instructionsBo.getQuestionnairesStepsBo().getSkiappable());
				}
				if(instructionsBo.getQuestionnairesStepsBo().getRepeatable() != null && !instructionsBo.getQuestionnairesStepsBo().getRepeatable().isEmpty()){
					questionnairesStepsBo.setRepeatable(instructionsBo.getQuestionnairesStepsBo().getRepeatable());
				}
				if(instructionsBo.getQuestionnairesStepsBo().getRepeatableText() != null && !instructionsBo.getQuestionnairesStepsBo().getRepeatableText().isEmpty()){
					questionnairesStepsBo.setRepeatableText(instructionsBo.getQuestionnairesStepsBo().getRepeatableText());
				}
				if(instructionsBo.getQuestionnairesStepsBo().getDestinationStep() != null){
					questionnairesStepsBo.setDestinationStep(instructionsBo.getQuestionnairesStepsBo().getDestinationStep());
				}
				if(instructionsBo.getQuestionnaireId() != null && questionnairesStepsBo.getStepId() == null){
					int count = 0;
					session = hibernateTemplate.getSessionFactory().openSession();
					query = session.getNamedQuery("getQuestionnaireStepSequenceNo").setInteger("questionnairesId", instructionsBo.getQuestionnaireId());
					query.setMaxResults(1);
					existedQuestionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
					if(existedQuestionnairesStepsBo != null){
						count = existedQuestionnairesStepsBo.getSequenceNo()+1;
					}else{
						count = count +1;
					}
					questionnairesStepsBo.setSequenceNo(count);
				}
				session.saveOrUpdate(questionnairesStepsBo);
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
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
				if(null!= questionnaireBo.getFrequency() && !questionnaireBo.getFrequency().isEmpty()){
					if(questionnaireBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE)){
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
				
			}
			
		}catch(Exception e){
			logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireById() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
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
					if(questionnaireBo.getQuestionnairesFrequenciesList() != null && !questionnaireBo.getQuestionnairesFrequenciesList().isEmpty()){
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
					if(questionnaireBo.getQuestionnaireCustomScheduleBo() != null && !questionnaireBo.getQuestionnaireCustomScheduleBo().isEmpty()){
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
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - saveORUpdateQuestionnaire() - Ends");
		return questionnaireBo;
	}
	
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
			if(session != null){
				session.close();
			}
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
			if(questionsBo != null && questionsBo.getId() != null && questionsBo.getStepType() != null){
					if(questionsBo.getQuestionnairesStepsBo() != null && questionsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.QUESTION_STEP)){
						QuestionnairesStepsBo questionnairesStepsBo = null;
						if(questionsBo.getQuestionnairesStepsBo().getStepId() != null){
							questionnairesStepsBo = (QuestionnairesStepsBo) session.get(QuestionnairesStepsBo.class, questionsBo.getQuestionnairesStepsBo().getStepId());
						}else{
							questionnairesStepsBo = new QuestionnairesStepsBo();
						}
						if(questionsBo.getQuestionnairesStepsBo().getStepShortTitle() != null && !questionsBo.getQuestionnairesStepsBo().getStepShortTitle().isEmpty()){
							questionnairesStepsBo.setStepShortTitle(questionsBo.getQuestionnairesStepsBo().getStepShortTitle());
						}
						if(questionsBo.getQuestionnairesStepsBo().getSkiappable() != null && !questionsBo.getQuestionnairesStepsBo().getSkiappable().isEmpty()){
							questionnairesStepsBo.setSkiappable(questionsBo.getQuestionnairesStepsBo().getSkiappable());
						}
						if(questionsBo.getQuestionnairesStepsBo().getRepeatable() != null && !questionsBo.getQuestionnairesStepsBo().getRepeatable().isEmpty()){
							questionnairesStepsBo.setRepeatable(questionsBo.getQuestionnairesStepsBo().getRepeatable());
						}
						if(questionsBo.getQuestionnairesStepsBo().getRepeatableText() != null && !questionsBo.getQuestionnairesStepsBo().getRepeatableText().isEmpty()){
							questionnairesStepsBo.setRepeatableText(questionsBo.getQuestionnairesStepsBo().getRepeatableText());
						}
						if(questionsBo.getQuestionnairesStepsBo().getDestinationStep() != null){
							questionnairesStepsBo.setDestinationStep(questionsBo.getQuestionnairesStepsBo().getDestinationStep());
						}
						questionnairesStepsBo.setQuestionnairesId(questionsBo.getQuestionnaireId());
						questionnairesStepsBo.setInstructionFormId(questionsBo.getId());
						questionnairesStepsBo.setStepType(fdahpStudyDesignerConstants.QUESTION_STEP);
						if(questionsBo.getQuestionnaireId() != null && questionnairesStepsBo.getStepId() == null){
							int count = 0;
							query = session.getNamedQuery("getQuestionnaireStepSequenceNo").setInteger("questionnairesId", questionnairesStepsBo.getQuestionnairesId());
							query.setMaxResults(1);
							existedQuestionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
							if(existedQuestionnairesStepsBo != null){
								count = existedQuestionnairesStepsBo.getSequenceNo()+1;
							}else{
								count = count +1;
							}
							questionnairesStepsBo.setSequenceNo(count);
						}
						session.saveOrUpdate(questionnairesStepsBo);
					}
					if(questionsBo.getFromId() != null){
						query = session.getNamedQuery("getFormMappingBO").setInteger("questionId", questionsBo.getId());
						FormMappingBo formMappingBo = (FormMappingBo) query.uniqueResult();
						if(formMappingBo == null){
							formMappingBo = new FormMappingBo();
							formMappingBo.setFormId(questionsBo.getFromId());
							formMappingBo.setQuestionId(questionsBo.getId());
							int sequenceNo = 0;
							query = session.createQuery("From FormMappingBo FMBO where FMBO.questionId="+questionsBo.getId()+" order by FMBO.sequenceNo DESC");
							query.setMaxResults(1);
							FormMappingBo existedFormMappingBo = (FormMappingBo) query.uniqueResult();
							if(existedFormMappingBo != null){
								sequenceNo = existedFormMappingBo.getSequenceNo()+1;
							}else{
								sequenceNo = sequenceNo +1;
							}
							formMappingBo.setSequenceNo(sequenceNo);
							session.save(formMappingBo);
						}
					}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateQuestion() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
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
			if(session != null){
				session.close();
			}
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
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId,String stepType) {
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Query query = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.instructionFormId="+stepId+" and QSBO.questionnairesId="+questionnaireId+" and QSBO.stepType='"+stepType+"'";
			questionnairesStepsBo = (QuestionnairesStepsBo) session.createQuery(searchQuery).uniqueResult();
			if(questionnairesStepsBo != null){
				String updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo-1 where QSBO.questionnairesId="+questionnairesStepsBo.getQuestionnairesId()+" and QSBO.sequenceNo >="+questionnairesStepsBo.getSequenceNo();
				query = session.createQuery(updateQuery);
				query.executeUpdate();
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
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep() - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * @param Integer questionnaireId
	 * @return Map : TreeMap<Integer, QuestionnaireStepBean>
	 * 
	 * This method is used to get the all the step inside questionnaire 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public SortedMap<Integer, QuestionnaireStepBean> getQuestionnaireStepList(Integer questionnaireId) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStepList() - Ends");
		Session session = null;
		List<QuestionnairesStepsBo> questionnairesStepsList = null;
		Map<String, Integer> sequenceNoMap = new HashMap<>();
		SortedMap<Integer, QuestionnaireStepBean> qTreeMap = new TreeMap<>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getQuestionnaireStepList").setInteger("questionnaireId", questionnaireId);
			questionnairesStepsList = query.list();
			List<Integer> instructionIdList = new ArrayList<>();
	        List<Integer> questionIdList = new ArrayList<>();
	        List<Integer> formIdList = new ArrayList<>();
	        for(QuestionnairesStepsBo questionaireSteps : questionnairesStepsList){
	         switch (questionaireSteps.getStepType()) {
		          case fdahpStudyDesignerConstants.INSTRUCTION_STEP: instructionIdList.add(questionaireSteps.getInstructionFormId());
		                          sequenceNoMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+fdahpStudyDesignerConstants.INSTRUCTION_STEP, questionaireSteps.getSequenceNo());
		                          break;
		          case fdahpStudyDesignerConstants.QUESTION_STEP: questionIdList.add(questionaireSteps.getInstructionFormId());
		                          sequenceNoMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+fdahpStudyDesignerConstants.QUESTION_STEP, questionaireSteps.getSequenceNo());
		                          break;
		          case fdahpStudyDesignerConstants.FORM_STEP: formIdList.add(questionaireSteps.getInstructionFormId());
		                          sequenceNoMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+fdahpStudyDesignerConstants.FORM_STEP, questionaireSteps.getSequenceNo());
		                          break;
		         default: break;
	          }
	        }
	        if(!instructionIdList.isEmpty()){
		            List<InstructionsBo> instructionsList = null;
		            query = session.createQuery(" from InstructionsBo IBO where IBO.id in ("+StringUtils.join(instructionIdList, ",")+")");
		            instructionsList = query.list();
		            if(instructionsList != null && !instructionsList.isEmpty()){
		            	for(InstructionsBo instructionsBo : instructionsList){
		            		QuestionnaireStepBean questionnaireStepBean = new QuestionnaireStepBean();
		            		questionnaireStepBean.setStepId(instructionsBo.getId());
		            		questionnaireStepBean.setStepType(fdahpStudyDesignerConstants.INSTRUCTION_STEP);
		            		questionnaireStepBean.setSequenceNo(sequenceNoMap.get(instructionsBo.getId()+fdahpStudyDesignerConstants.INSTRUCTION_STEP));
		            		questionnaireStepBean.setTitle(instructionsBo.getInstructionTitle());
		            		qTreeMap.put(sequenceNoMap.get(instructionsBo.getId()+fdahpStudyDesignerConstants.INSTRUCTION_STEP), questionnaireStepBean);
		            	}
		            }
	        }
	        if(!questionIdList.isEmpty()){
	        	List<QuestionsBo> questionsList = null;
	            query = session.createQuery(" from QuestionsBo QBO where QBO.id in ("+StringUtils.join(questionIdList, ",")+")");
	            questionsList = query.list();
	            if( questionsList != null && !questionsList.isEmpty()){
	            	for(QuestionsBo questionsBo : questionsList){
	            		QuestionnaireStepBean questionnaireStepBean = new QuestionnaireStepBean();
	            		questionnaireStepBean.setStepId(questionsBo.getId());
	            		questionnaireStepBean.setStepType(fdahpStudyDesignerConstants.QUESTION_STEP);
	            		questionnaireStepBean.setSequenceNo(sequenceNoMap.get(questionsBo.getId()+fdahpStudyDesignerConstants.QUESTION_STEP));
	            		questionnaireStepBean.setTitle(questionsBo.getQuestion());
	            		qTreeMap.put(sequenceNoMap.get(questionsBo.getId()+fdahpStudyDesignerConstants.QUESTION_STEP), questionnaireStepBean);
	            	}
	            }
		     }
	        if(!formIdList.isEmpty()){
	            String fromQuery = "select f.form_id,f.question_id,f.sequence_no, q.id, q.question from questions q, form_mapping f where q.id=f.question_id and f.form_id IN ("+StringUtils.join(formIdList, ",")+") order by f.form_id";
	            Iterator iterator = session.createSQLQuery(fromQuery).list().iterator();
	            List result = session.createSQLQuery(fromQuery).list();
	            logger.info("result size :"+result.size());
	            for(int i=0;i<formIdList.size();i++){
	            	QuestionnaireStepBean fQuestionnaireStepBean = new QuestionnaireStepBean();
	            	TreeMap<Integer, QuestionnaireStepBean> formQuestionMap = new TreeMap<>();
	            	 while (iterator.hasNext()) {
	 	            	Object[] objects = (Object[]) iterator.next();
	 	            	Integer formId = (Integer) objects[0];
	 	            	Integer sequenceNo = (Integer) objects[2]; 
	 	            	Integer questionId = (Integer) objects[3];
	 	            	String questionText = (String) objects[4];
	 	            	if(formIdList.get(i).equals(formId)){
	 	            		QuestionnaireStepBean questionnaireStepBean = new QuestionnaireStepBean();
	            			questionnaireStepBean.setStepId(formId);
	            			questionnaireStepBean.setQuestionInstructionId(questionId);
	            			questionnaireStepBean.setTitle(questionText);
	            			questionnaireStepBean.setSequenceNo(sequenceNo);
	            			questionnaireStepBean.setStepType(fdahpStudyDesignerConstants.FORM_STEP);
	            			formQuestionMap.put(sequenceNo, questionnaireStepBean);
	 	            	}
	            	 }
	            	 fQuestionnaireStepBean.setStepId(formIdList.get(i));
	 	             fQuestionnaireStepBean.setStepType(fdahpStudyDesignerConstants.FORM_STEP);
	 	             fQuestionnaireStepBean.setSequenceNo(sequenceNoMap.get(formIdList.get(i)+fdahpStudyDesignerConstants.FORM_STEP));
	 	             fQuestionnaireStepBean.setFromMap(formQuestionMap);
	            	 qTreeMap.put(sequenceNoMap.get(formIdList.get(i)+fdahpStudyDesignerConstants.FORM_STEP), fQuestionnaireStepBean);
	            }
	         }
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - getQuestionnaireStepList() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStepList() - Ends");
		return qTreeMap;
	}

	/**
	 * @author Ravinder
	 * @param Integer:studyId
	 * @param String : shortTitle
	 * @return String : SUCCESS or FAILURE
	 * 
	 *  This method is used to check the if the questionnaire short title existed or not in a study
	 */
	@Override
	public String checkQuestionnaireShortTitle(Integer studyId,String shortTitle) {
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireShortTitle() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		QuestionnaireBo questionnaireBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			query = session.getNamedQuery("checkQuestionnaireShortTitle").setInteger("studyId", studyId).setString("shortTitle", shortTitle);
			questionnaireBo = (QuestionnaireBo) query.uniqueResult();
			if(questionnaireBo != null){
				message = fdahpStudyDesignerConstants.SUCCESS;
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - checkQuestionnaireShortTitle() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireShortTitle() - Ends");
		return message;
	}

	/**@author Ravinder
	 * @param Integer : stepId
	 * @return Object : questionnaireStepBo
	 * This method is used to get the step information of questionnaire in a study
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public QuestionnairesStepsBo getQuestionnaireStep(Integer stepId,String stepType) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStep() - Starts");
		Session session = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getQuestionnaireStep").setInteger("instructionFormId", stepId).setString("stepType", stepType);
			questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
			if(null != questionnairesStepsBo && questionnairesStepsBo.getStepType() != null){
				if(questionnairesStepsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.INSTRUCTION_STEP)){
					InstructionsBo instructionsBo = null;
					query = session.getNamedQuery("getInstructionStep").setInteger("stepId", stepId);
					instructionsBo = (InstructionsBo) query.uniqueResult();
				}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.QUESTION_STEP)){
					QuestionsBo questionsBo= null; 
					query = session.getNamedQuery("getQuestionStep").setInteger("stepId", stepId);
					questionsBo = (QuestionsBo) query.uniqueResult();
				}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(fdahpStudyDesignerConstants.FORM_STEP)){
					String fromQuery = "select f.form_id,f.question_id,f.sequence_no, q.id, q.question from questions q, form_mapping f where q.id=f.question_id and f.form_id="+stepId+" order by f.form_id";
					Iterator iterator = session.createSQLQuery(fromQuery).list().iterator();
		            List result = session.createSQLQuery(fromQuery).list();
		            TreeMap<Integer, QuestionnaireStepBean> formQuestionMap = new TreeMap<>();
	            	while (iterator.hasNext()) {
	 	            	Object[] objects = (Object[]) iterator.next();
	 	            	Integer formId = (Integer) objects[0];
	 	            	Integer sequenceNo = (Integer) objects[2]; 
	 	            	Integer questionId = (Integer) objects[3];
	 	            	String questionText = (String) objects[4];
	 	            	QuestionnaireStepBean questionnaireStepBean = new QuestionnaireStepBean();
            			questionnaireStepBean.setStepId(formId);
            			questionnaireStepBean.setQuestionInstructionId(questionId);
            			questionnaireStepBean.setTitle(questionText);
            			questionnaireStepBean.setSequenceNo(sequenceNo);
            			questionnaireStepBean.setStepType(fdahpStudyDesignerConstants.FORM_STEP);
            			formQuestionMap.put(sequenceNo, questionnaireStepBean);
	            	 }
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - getQuestionnaireStep() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStep() - Ends");
		return questionnairesStepsBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer : QuestionnaireId
	 * @param String : stepType
	 * @param String : shortTitle
	 */
	@Override
	public String checkQuestionnaireStepShortTitle(Integer questionnaireId,	String stepType, String shortTitle) {
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireStepShortTitle() - Ends");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			query = session.getNamedQuery("checkQuestionnaireStepShortTitle").setInteger("questionnaireId", questionnaireId).setString("stepType", stepType).setString("shortTitle", shortTitle);
			questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
			if(questionnairesStepsBo != null){
				message = fdahpStudyDesignerConstants.SUCCESS;
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - checkQuestionnaireStepShortTitle() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireStepShortTitle() - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * 
	 * This method is used to get the Resonse Type Master Data
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionResponseTypeMasterInfoBo> getQuestionReponseTypeList() {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionReponseTypeList() - Starts");
		Session session = null;
		List<QuestionResponseTypeMasterInfoBo> questionResponseTypeMasterInfoBos = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getResponseTypes");
			questionResponseTypeMasterInfoBos = query.list();
		} catch (Exception e) {
			logger.error(
					"StudyQuestionnaireDAOImpl - getQuestionReponseTypeList() - ERROR ", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - getQuestionReponseTypeList() - Ends");
		return questionResponseTypeMasterInfoBos;
	}
	/**
	 * @author Ravinder
	 * @param Object : QuestionnaireStepBo
	 * @return Object : QuestionnaireStepBo
	 * This method is used to save the form step in questionnaire 
	 */
	@Override
	public QuestionnairesStepsBo saveOrUpdateFromQuestionnaireStep(QuestionnairesStepsBo questionnairesStepsBo) {
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateFromQuestionnaireStep() - Starts");
		Session session = null;
		QuestionnairesStepsBo existedQuestionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(questionnairesStepsBo != null){
				QuestionnairesStepsBo addOrUpdateQuestionnairesStepsBo = null;
				if(questionnairesStepsBo.getStepId() != null){
					addOrUpdateQuestionnairesStepsBo = (QuestionnairesStepsBo) session.get(QuestionnairesStepsBo.class, questionnairesStepsBo.getStepId());
				}else{
					addOrUpdateQuestionnairesStepsBo = new QuestionnairesStepsBo();
				}
				if(questionnairesStepsBo.getStepShortTitle() != null && !questionnairesStepsBo.getStepShortTitle().isEmpty()){
					addOrUpdateQuestionnairesStepsBo.setStepShortTitle(questionnairesStepsBo.getStepShortTitle());
				}
				if(questionnairesStepsBo.getSkiappable() != null && !questionnairesStepsBo.getSkiappable().isEmpty()){
					addOrUpdateQuestionnairesStepsBo.setSkiappable(questionnairesStepsBo.getSkiappable());
				}
				if(questionnairesStepsBo.getRepeatable() != null && !questionnairesStepsBo.getRepeatable().isEmpty()){
					addOrUpdateQuestionnairesStepsBo.setRepeatable(questionnairesStepsBo.getRepeatable());
				}
				if(questionnairesStepsBo.getRepeatableText() != null && !questionnairesStepsBo.getRepeatableText().isEmpty()){
					addOrUpdateQuestionnairesStepsBo.setRepeatableText(questionnairesStepsBo.getRepeatableText());
				}
				if(questionnairesStepsBo.getDestinationStep() != null){
					addOrUpdateQuestionnairesStepsBo.setDestinationStep(questionnairesStepsBo.getDestinationStep());
				}
				if(questionnairesStepsBo.getQuestionnairesId() != null && questionnairesStepsBo.getStepId() == null){
					FormBo formBo = new FormBo();
					session.saveOrUpdate(formBo);
					questionnairesStepsBo.setQuestionnairesId(questionnairesStepsBo.getQuestionnairesId());
					questionnairesStepsBo.setInstructionFormId(formBo.getFormId());
					questionnairesStepsBo.setStepType(fdahpStudyDesignerConstants.FORM_STEP);
					int count = 0;
					query = session.getNamedQuery("getQuestionnaireStepSequenceNo").setInteger("questionnairesId", questionnairesStepsBo.getQuestionnairesId());
					query.setMaxResults(1);
					existedQuestionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
					if(existedQuestionnairesStepsBo != null){
						count = existedQuestionnairesStepsBo.getSequenceNo()+1;
					}else{
						count = count +1;
					}
					questionnairesStepsBo.setSequenceNo(count);
				}
				session.saveOrUpdate(questionnairesStepsBo);
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateFromQuestionnaireStep() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateFromQuestionnaireStep() - Ends");
		return questionnairesStepsBo;
	}

	@Override
	public String reOrderFormStepQuestions(Integer formId, int oldOrderNumber,int newOrderNumber) {
		logger.info("StudyQuestionnaireDAOImpl - reOrderFormStepQuestions() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		FormMappingBo formMappingBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String updateQuery ="";
			query = session.getNamedQuery("getFromByIdAndSequenceNo").setInteger("formId", formId).setInteger("oldOrderNumber", oldOrderNumber);
			formMappingBo = (FormMappingBo)query.uniqueResult();
			if(formMappingBo != null){
				if (oldOrderNumber < newOrderNumber) {
					updateQuery = "update FormMappingBo FMBO set FMBO.sequenceNo=FMBO.sequenceNo-1 where FMBO.formId="+formId+" and FMBO.sequenceNo <="+newOrderNumber+" and FMBO.sequenceNo >"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.getNamedQuery("updateFromQuestionSequenceNo").setInteger("newOrderNumber", newOrderNumber).setInteger("id", formMappingBo.getId());
						count = query.executeUpdate();
						message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update FormMappingBo FMBO set FMBO.sequenceNo=FMBO.sequenceNo+1 where FMBO.formId="+formId+" and FMBO.sequenceNo >="+newOrderNumber+" and FMBO.sequenceNo <"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.getNamedQuery("updateFromQuestionSequenceNo").setInteger("newOrderNumber", newOrderNumber).setInteger("id", formMappingBo.getId());
						count = query.executeUpdate();
						message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - reOrderFormStepQuestions() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - reOrderFormStepQuestions() - Ends");
		return message;
	}

	@Override
	public String deleteFromStepQuestion(Integer formId, Integer questionId) {
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		logger.info("StudyQuestionnaireDAOImpl - deleteFromStepQuestion() - Ends");
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			query = session.getNamedQuery("deletQuestion").setInteger("questionId", questionId);
			query.executeUpdate();
			
			query = session.getNamedQuery("deleteFormQuestion").setInteger("formId", formId).setInteger("questionId", questionId);
			query.executeUpdate();
			message = fdahpStudyDesignerConstants.SUCCESS;
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - deleteFromStepQuestion() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - deleteFromStepQuestion() - Ends");
		return message;
	}
}
