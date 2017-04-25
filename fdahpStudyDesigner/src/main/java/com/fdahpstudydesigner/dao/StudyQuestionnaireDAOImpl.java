/**
 * 
 */
package com.fdahpstudydesigner.dao;

import java.io.File;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.fdahpstudydesigner.bean.QuestionnaireStepBean;
import com.fdahpstudydesigner.bo.FormBo;
import com.fdahpstudydesigner.bo.FormMappingBo;
import com.fdahpstudydesigner.bo.InstructionsBo;
import com.fdahpstudydesigner.bo.QuestionReponseTypeBo;
import com.fdahpstudydesigner.bo.QuestionResponseSubTypeBo;
import com.fdahpstudydesigner.bo.QuestionResponseTypeMasterInfoBo;
import com.fdahpstudydesigner.bo.QuestionnaireBo;
import com.fdahpstudydesigner.bo.QuestionnaireCustomScheduleBo;
import com.fdahpstudydesigner.bo.QuestionnairesFrequenciesBo;
import com.fdahpstudydesigner.bo.QuestionnairesStepsBo;
import com.fdahpstudydesigner.bo.QuestionsBo;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

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
				query = session.getNamedQuery("getQuestionnaireStep").setInteger("instructionFormId", instructionsBo.getId()).setString("stepType", FdahpStudyDesignerConstants.INSTRUCTION_STEP);
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
					questionnairesStepsBo.setActive(true);
					questionnairesStepsBo.setDestinationStep(0);
				}
				questionnairesStepsBo.setQuestionnairesId(instructionsBo.getQuestionnaireId());
				questionnairesStepsBo.setInstructionFormId(instructionsBo.getId());
				questionnairesStepsBo.setStepType(FdahpStudyDesignerConstants.INSTRUCTION_STEP);
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
				if(instructionsBo.getQuestionnairesStepsBo().getCreatedOn() != null){
					questionnairesStepsBo.setCreatedOn(instructionsBo.getQuestionnairesStepsBo().getCreatedOn());
				}
				if(instructionsBo.getQuestionnairesStepsBo().getCreatedBy() != null){
					questionnairesStepsBo.setCreatedBy(instructionsBo.getQuestionnairesStepsBo().getCreatedBy());
				}
				if(instructionsBo.getQuestionnairesStepsBo().getModifiedOn() != null){
					questionnairesStepsBo.setModifiedOn(instructionsBo.getQuestionnairesStepsBo().getModifiedOn());
				}
				if(instructionsBo.getQuestionnairesStepsBo().getModifiedBy() != null){
					questionnairesStepsBo.setModifiedBy(instructionsBo.getQuestionnairesStepsBo().getModifiedBy());
				}
				if(instructionsBo.getType() != null){
					if(instructionsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_SAVE)){
						questionnairesStepsBo.setStatus(false);
					}else if(instructionsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_COMPLETE)){
						questionnairesStepsBo.setStatus(true);
					}
				}
				int count = 0;
				if(instructionsBo.getQuestionnaireId() != null && questionnairesStepsBo.getStepId() == null){
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
				instructionsBo.setQuestionnairesStepsBo(questionnairesStepsBo);
				if(questionnairesStepsBo != null && count > 0){
					String updateQuery="update QuestionnairesStepsBo QSBO set QSBO.destinationStep="+questionnairesStepsBo.getStepId()+" where "
							+ "QSBO.destinationStep=0 and QSBO.sequenceNo="+(count-1)+" and QSBO.questionnairesId="+instructionsBo.getQuestionnaireId();
					session.createQuery(updateQuery).executeUpdate();
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - getInstructionsBo() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo() - Ends");
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
					if(questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE)){
						searchQuery = "From QuestionnaireCustomScheduleBo QCSBO where QCSBO.questionnairesId="+questionnaireBo.getId();
						query = session.createQuery(searchQuery);
						List<QuestionnaireCustomScheduleBo> questionnaireCustomScheduleList = query.list();
						questionnaireBo.setQuestionnaireCustomScheduleBo(questionnaireCustomScheduleList);
					}else{
						searchQuery = "From QuestionnairesFrequenciesBo QFBO where QFBO.questionnairesId="+questionnaireBo.getId();
						query = session.createQuery(searchQuery);
						if(questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_DAILY)){
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
			logger.error("StudyQuestionnaireDAOImpl - getQuestionnaireById() - Error",e);
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
			if(questionnaireBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.SCHEDULE)){
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
						if(questionnairesFrequenciesBo.getFrequencyDate() != null || questionnairesFrequenciesBo.getFrequencyTime() != null || questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME)){
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
								questionnairesFrequenciesBo.setFrequencyDate(FdahpStudyDesignerUtil.getFormattedDate(questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate(), FdahpStudyDesignerConstants.SDF_DATE_FORMAT, FdahpStudyDesignerConstants.SD_DATE_FORMAT));
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
								questionnaireCustomScheduleBo.setFrequencyStartDate(FdahpStudyDesignerUtil.getFormattedDate(questionnaireCustomScheduleBo.getFrequencyStartDate(), FdahpStudyDesignerConstants.SDF_DATE_FORMAT, FdahpStudyDesignerConstants.SD_DATE_FORMAT));
								questionnaireCustomScheduleBo.setFrequencyEndDate(FdahpStudyDesignerUtil.getFormattedDate(questionnaireCustomScheduleBo.getFrequencyEndDate(), FdahpStudyDesignerConstants.SDF_DATE_FORMAT, FdahpStudyDesignerConstants.SD_DATE_FORMAT));
								session.saveOrUpdate(questionnaireCustomScheduleBo);
							}
						}
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - saveORUpdateQuestionnaire() - Error",e);
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
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			questionsBo = (QuestionsBo) session.get(QuestionsBo.class, questionId);
			if(questionsBo != null){
				QuestionReponseTypeBo questionReponseTypeBo = null;
				query = session.getNamedQuery("getQuestionResponse").setInteger("questionsResponseTypeId", questionsBo.getId());
				questionReponseTypeBo = (QuestionReponseTypeBo) query.uniqueResult();
				if(questionReponseTypeBo != null){
					if(questionReponseTypeBo.getStyle() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getStyle())){
						questionReponseTypeBo.setStyle(questionReponseTypeBo.getStyle());
						if(questionReponseTypeBo.getStyle().equalsIgnoreCase("Date")){
							if(questionReponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMinDate())){
								questionReponseTypeBo.setMinDate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(questionReponseTypeBo.getMinDate())));
							}
							if(questionReponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMaxDate())){
								questionReponseTypeBo.setMaxDate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(questionReponseTypeBo.getMaxDate())));
							}
							if(questionReponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getDefaultDate())){
								questionReponseTypeBo.setDefaultDate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(questionReponseTypeBo.getDefaultDate())));
							}
						}else if(questionReponseTypeBo.getStyle().equalsIgnoreCase("Date-Time")){
							if(questionReponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMinDate())){
								questionReponseTypeBo.setMinDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(questionReponseTypeBo.getMinDate())));
							}
							if(questionReponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMaxDate())){
								questionReponseTypeBo.setMaxDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(questionReponseTypeBo.getMaxDate())));
							}
							if(questionReponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getDefaultDate())){
								questionReponseTypeBo.setDefaultDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(questionReponseTypeBo.getDefaultDate())));
							}
						}
					}
				}
				questionsBo.setQuestionReponseTypeBo(questionReponseTypeBo);
				
				List<QuestionResponseSubTypeBo> questionResponseSubTypeList = null;
				query = session.getNamedQuery("getQuestionSubResponse").setInteger("responseTypeId", questionsBo.getId());
				questionResponseSubTypeList =  query.list();
				questionsBo.setQuestionResponseSubTypeList(questionResponseSubTypeList);
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
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(questionsBo);
			if(questionsBo != null && questionsBo.getId() != null && questionsBo.getFromId() != null){
				QuestionReponseTypeBo addQuestionReponseTypeBo = getQuestionsResponseTypeBo(questionsBo.getQuestionReponseTypeBo(), session);
				if(addQuestionReponseTypeBo != null){
					if(addQuestionReponseTypeBo.getQuestionsResponseTypeId() == null){
						addQuestionReponseTypeBo.setQuestionsResponseTypeId(questionsBo.getId());
					}
					session.saveOrUpdate(addQuestionReponseTypeBo);
				}
				if(questionsBo.getQuestionResponseSubTypeList() != null && !questionsBo.getQuestionResponseSubTypeList().isEmpty()){
					if(questionsBo.getResponseType() == 4 || questionsBo.getResponseType() == 3 ||
							questionsBo.getResponseType() == 6 || questionsBo.getResponseType() == 5){
						String deletQuesry = "Delete From QuestionResponseSubTypeBo QRSTBO where QRSTBO.responseTypeId="+questionsBo.getId();
						session.createQuery(deletQuesry).executeUpdate();
						for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionsBo.getQuestionResponseSubTypeList()){
							if((questionResponseSubTypeBo.getText() != null && !questionResponseSubTypeBo.getText().isEmpty()) &&
									(questionResponseSubTypeBo.getValue() != null && !questionResponseSubTypeBo.getValue().isEmpty())){
								questionResponseSubTypeBo.setResponseTypeId(questionsBo.getId());
								questionResponseSubTypeBo.setActive(true);
								session.save(questionResponseSubTypeBo);
							}
						}
					}else{
						for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionsBo.getQuestionResponseSubTypeList()){
							questionResponseSubTypeBo.setResponseTypeId(questionsBo.getId());
							questionResponseSubTypeBo.setActive(true);
							session.saveOrUpdate(questionResponseSubTypeBo);
						}	
					}
				}
				if(!questionsBo.getStatus()){
					query = session.getNamedQuery("getQuestionnaireStep").setInteger("instructionFormId", questionsBo.getFromId()).setString("stepType", FdahpStudyDesignerConstants.FORM_STEP);
					QuestionnairesStepsBo questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
					if(questionnairesStepsBo != null){
						if(questionnairesStepsBo.getStatus()){
							questionnairesStepsBo.setStatus(false);
							session.saveOrUpdate(questionnairesStepsBo);
						}
					}
				}
				query = session.getNamedQuery("getFormMappingBO").setInteger("questionId", questionsBo.getId());
				FormMappingBo formMappingBo = (FormMappingBo) query.uniqueResult();
				if(formMappingBo == null){
					formMappingBo = new FormMappingBo();
					formMappingBo.setFormId(questionsBo.getFromId());
					formMappingBo.setQuestionId(questionsBo.getId());
					int sequenceNo = 0;
					query = session.createQuery("From FormMappingBo FMBO where FMBO.formId="+questionsBo.getFromId()+" order by FMBO.sequenceNo DESC");
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
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - saveOrUpdateQuestion() - Error",e);
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
	@SuppressWarnings("unchecked")
	@Override
	public String reOrderQuestionnaireSteps(Integer questionnaireId,int oldOrderNumber, int newOrderNumber) {
		logger.info("StudyQuestionnaireDAOImpl - reOrderQuestionnaireSteps() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		List<QuestionnairesStepsBo> questionnaireStepList = null;
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
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo+1 where QSBO.questionnairesId="+questionnaireId+" and QSBO.sequenceNo >="+newOrderNumber+" and QSBO.sequenceNo <"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update QuestionnairesStepsBo Q set Q.sequenceNo="+ newOrderNumber+" where Q.stepId="+questionnairesStepsBo.getStepId());
						count = query.executeUpdate();
						//Reset destination steps in Questionnaire Starts
						String searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId + " order by QSBO.sequenceNo ASC";
						questionnaireStepList = session.createQuery(searchQuery).list();
						
						if(null != questionnaireStepList && !questionnaireStepList.isEmpty()){
							if(questionnaireStepList.size() == 1){
								questionnaireStepList.get(0).setDestinationStep(0);
								questionnaireStepList.get(0).setSequenceNo(1);
								session.update(questionnaireStepList.get(0));
							} else {
								int i;
								for(i = 0; i < questionnaireStepList.size() - 1; i++){
									questionnaireStepList.get(i).setDestinationStep(questionnaireStepList.get(i+1).getStepId());
									questionnaireStepList.get(i).setSequenceNo(i+1);
									session.update(questionnaireStepList.get(i));
								}
								questionnaireStepList.get(i).setDestinationStep(0);
								questionnaireStepList.get(i).setSequenceNo(i+1);
								session.update(questionnaireStepList.get(i));
							}
						}
						//Reset destination steps in Questionnaire Ends
						message = FdahpStudyDesignerConstants.SUCCESS;
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
	@SuppressWarnings("unchecked")
	@Override
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId,String stepType,SessionObject sessionObject) {
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Query query = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		List<QuestionnairesStepsBo> questionnaireStepList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.instructionFormId="+stepId+" and QSBO.questionnairesId="+questionnaireId+" and QSBO.stepType='"+stepType+"'";
			questionnairesStepsBo = (QuestionnairesStepsBo) session.createQuery(searchQuery).uniqueResult();
			if(questionnairesStepsBo != null){
				String updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo-1,QSBO.modifiedBy="+sessionObject.getUserId()+",QSBO.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QSBO.questionnairesId="+questionnairesStepsBo.getQuestionnairesId()+" and QSBO.sequenceNo >="+questionnairesStepsBo.getSequenceNo();
				query = session.createQuery(updateQuery);
				query.executeUpdate();
				if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.INSTRUCTION_STEP)){
					String deleteQuery = "Update InstructionsBo IBO set IBO.active=0,IBO.modifiedBy="+sessionObject.getUserId()+",IBO.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where IBO.id="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
				}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTION_STEP)){
					String deleteQuery = "Update QuestionsBo QBO set QBO.active=0,QBO.modifiedBy="+sessionObject.getUserId()+",QBO.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.id="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
				}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.FORM_STEP)){
					String subQuery = "select FMBO.questionId from FormMappingBo FMBO where FMBO.formId="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(subQuery);
					if(query.list() != null && !query.list().isEmpty()){
						String deleteQuery = "Update QuestionsBo QBO set QBO.active=0,QBO.modifiedBy="+sessionObject.getUserId()+",QBO.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.id IN ("+subQuery+")";
						query = session.createQuery(deleteQuery);
						query.executeUpdate();
					}
					String formMappingDelete = "delete from FormMappingBo FMBO where FMBO.formId="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(formMappingDelete);
					query.executeUpdate();
					String formDelete = "Update FormBo FBO set FBO.active=0,FBO.modifiedBy="+sessionObject.getUserId()+",FBO.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where FBO.formId="+questionnairesStepsBo.getInstructionFormId();
					query = session.createQuery(formDelete);
					query.executeUpdate();
				}
				session.delete(questionnairesStepsBo);
				
				/*// Update sequence number 
				if(null != questionnairesStepsBo.getDestinationStep() && questionnairesStepsBo.getDestinationStep() >= 0){
					String updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo-1 where QSBO.questionnairesId="+questionnairesStepsBo.getQuestionnairesId()+" and QSBO.sequenceNo >="+questionnairesStepsBo.getSequenceNo();
					query = session.createQuery(updateQuery);
					query.executeUpdate();
				}*/
				
				//Reset destination steps in Questionnaire Starts
				searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId + " order by QSBO.sequenceNo ASC";
				
				questionnaireStepList = session.createQuery(searchQuery).list();
				if(null != questionnaireStepList && questionnaireStepList.size() > 0){
					if(questionnaireStepList.size() == 1){
						questionnaireStepList.get(0).setDestinationStep(0);
						questionnaireStepList.get(0).setSequenceNo(1);
						session.update(questionnaireStepList.get(0));
					} else {
						int i;
						for(i = 0; i < questionnaireStepList.size() - 1; i++){
							questionnaireStepList.get(i).setDestinationStep(questionnaireStepList.get(i+1).getStepId());
							questionnaireStepList.get(i).setSequenceNo(i+1);
							session.update(questionnaireStepList.get(i));
						}
						questionnaireStepList.get(i).setDestinationStep(0);
						questionnaireStepList.get(i).setSequenceNo(i+1);
						session.update(questionnaireStepList.get(i));
					}
				}
				//Reset destination steps in Questionnaire Ends
				
				message = FdahpStudyDesignerConstants.SUCCESS;
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
		Map<Integer, String> destinationText = new HashMap<>(); 
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getQuestionnaireStepList").setInteger("questionnaireId", questionnaireId);
			questionnairesStepsList = query.list();
			List<Integer> instructionIdList = new ArrayList<>();
	        List<Integer> questionIdList = new ArrayList<>();
	        List<Integer> formIdList = new ArrayList<>();
	        Map<String, Integer> destinationMap = new HashMap<>();
	        destinationText.put(0, "Completion Step");
	        for(QuestionnairesStepsBo questionaireSteps : questionnairesStepsList){
	         destinationText.put(questionaireSteps.getStepId(), questionaireSteps.getSequenceNo()+":"+questionaireSteps.getStepShortTitle());
	         switch (questionaireSteps.getStepType()) {
		          case FdahpStudyDesignerConstants.INSTRUCTION_STEP: instructionIdList.add(questionaireSteps.getInstructionFormId());
		                          sequenceNoMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+FdahpStudyDesignerConstants.INSTRUCTION_STEP, questionaireSteps.getSequenceNo());
		                          destinationMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+FdahpStudyDesignerConstants.INSTRUCTION_STEP, questionaireSteps.getDestinationStep());
		                          break;
		          case FdahpStudyDesignerConstants.QUESTION_STEP: questionIdList.add(questionaireSteps.getInstructionFormId());
		                          sequenceNoMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+FdahpStudyDesignerConstants.QUESTION_STEP, questionaireSteps.getSequenceNo());
		                          destinationMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+FdahpStudyDesignerConstants.QUESTION_STEP, questionaireSteps.getDestinationStep());
		                          break;
		          case FdahpStudyDesignerConstants.FORM_STEP: formIdList.add(questionaireSteps.getInstructionFormId());
		                          sequenceNoMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+FdahpStudyDesignerConstants.FORM_STEP, questionaireSteps.getSequenceNo());
		                          destinationMap.put(String.valueOf(questionaireSteps.getInstructionFormId())+FdahpStudyDesignerConstants.FORM_STEP, questionaireSteps.getDestinationStep());
		                          break;
		         default: break;
	          }
	        }
	        if(!instructionIdList.isEmpty()){
		            List<InstructionsBo> instructionsList = null;
		            query = session.createQuery(" from InstructionsBo IBO where IBO.active=1 and IBO.id in ("+StringUtils.join(instructionIdList, ",")+")");
		            instructionsList = query.list();
		            if(instructionsList != null && !instructionsList.isEmpty()){
		            	for(InstructionsBo instructionsBo : instructionsList){
		            		QuestionnaireStepBean questionnaireStepBean = new QuestionnaireStepBean();
		            		questionnaireStepBean.setStepId(instructionsBo.getId());
		            		questionnaireStepBean.setStepType(FdahpStudyDesignerConstants.INSTRUCTION_STEP);
		            		questionnaireStepBean.setSequenceNo(sequenceNoMap.get(instructionsBo.getId()+FdahpStudyDesignerConstants.INSTRUCTION_STEP));
		            		questionnaireStepBean.setTitle(instructionsBo.getInstructionTitle());
		            		questionnaireStepBean.setStatus(instructionsBo.getStatus());
		            		questionnaireStepBean.setDestinationStep(destinationMap.get(instructionsBo.getId()+FdahpStudyDesignerConstants.INSTRUCTION_STEP));
		            		questionnaireStepBean.setDestinationText(destinationText.get(destinationMap.get(instructionsBo.getId()+FdahpStudyDesignerConstants.INSTRUCTION_STEP)));
		            		qTreeMap.put(sequenceNoMap.get(instructionsBo.getId()+FdahpStudyDesignerConstants.INSTRUCTION_STEP), questionnaireStepBean);
		            	}
		            }
	        }
	        if(!questionIdList.isEmpty()){
	        	List<QuestionsBo> questionsList = null;
	            query = session.createQuery(" from QuestionsBo QBO where QBO.active=1 and QBO.id in ("+StringUtils.join(questionIdList, ",")+")");
	            questionsList = query.list();
	            if( questionsList != null && !questionsList.isEmpty()){
	            	for(QuestionsBo questionsBo : questionsList){
	            		QuestionnaireStepBean questionnaireStepBean = new QuestionnaireStepBean();
	            		questionnaireStepBean.setStepId(questionsBo.getId());
	            		questionnaireStepBean.setStepType(FdahpStudyDesignerConstants.QUESTION_STEP);
	            		questionnaireStepBean.setSequenceNo(sequenceNoMap.get(questionsBo.getId()+FdahpStudyDesignerConstants.QUESTION_STEP));
	            		questionnaireStepBean.setTitle(questionsBo.getQuestion());
	            		questionnaireStepBean.setResponseType(questionsBo.getResponseType());
	            		questionnaireStepBean.setLineChart(questionsBo.getAddLineChart());
	            		questionnaireStepBean.setStatData(questionsBo.getUseStasticData());
	            		questionnaireStepBean.setStatus(questionsBo.getStatus());
	            		questionnaireStepBean.setDestinationStep(destinationMap.get(questionsBo.getId()+FdahpStudyDesignerConstants.QUESTION_STEP));
	            		questionnaireStepBean.setUseAnchorDate(questionsBo.getUseAnchorDate());
	            		questionnaireStepBean.setDestinationText(destinationText.get(destinationMap.get(questionsBo.getId()+FdahpStudyDesignerConstants.QUESTION_STEP)));
	            		qTreeMap.put(sequenceNoMap.get(questionsBo.getId()+FdahpStudyDesignerConstants.QUESTION_STEP), questionnaireStepBean);
	            	}
	            }
		     }
	        if(!formIdList.isEmpty()){
	            String fromQuery = "select f.form_id,f.question_id,f.sequence_no, q.id, q.question,q.response_type,q.add_line_chart,q.use_stastic_data,q.status,q.use_anchor_date from questions q, form_mapping f where q.id=f.question_id and q.active=1 and f.form_id IN ("+StringUtils.join(formIdList, ",")+") order by f.form_id";
	            Iterator iterator = session.createSQLQuery(fromQuery).list().iterator();
	            List result = session.createSQLQuery(fromQuery).list();
	            logger.info("@@@@@@@@result size :"+result.size());
	            for(int i=0;i<formIdList.size();i++){
	            	QuestionnaireStepBean fQuestionnaireStepBean = new QuestionnaireStepBean();
	            	TreeMap<Integer, QuestionnaireStepBean> formQuestionMap = new TreeMap<>();
	            	 while (iterator.hasNext()) {
	 	            	Object[] objects = (Object[]) iterator.next();
	 	            	Integer formId = (Integer) objects[0];
	 	            	Integer sequenceNo = (Integer) objects[2]; 
	 	            	Integer questionId = (Integer) objects[3];
	 	            	String questionText = (String) objects[4];
	 	            	Integer responseType = (Integer) objects[5];
	 	            	String lineChart = (String) objects[6];
	 	            	String statData = (String) objects[7];
	 	            	Boolean status = (Boolean) objects[8];
	 	            	Boolean useAnchorDate = (Boolean) objects[9];
	 	            	if(formIdList.get(i).equals(formId)){
	 	            		QuestionnaireStepBean questionnaireStepBean = new QuestionnaireStepBean();
	            			questionnaireStepBean.setStepId(formId);
	            			questionnaireStepBean.setQuestionInstructionId(questionId);
	            			questionnaireStepBean.setTitle(questionText);
	            			questionnaireStepBean.setSequenceNo(sequenceNo);
	            			questionnaireStepBean.setStepType(FdahpStudyDesignerConstants.FORM_STEP);
	            			questionnaireStepBean.setResponseType(responseType);
	            			questionnaireStepBean.setLineChart(lineChart);
	            			questionnaireStepBean.setStatData(statData);
	            			questionnaireStepBean.setStatus(status);
	            			questionnaireStepBean.setUseAnchorDate(useAnchorDate);
	            			formQuestionMap.put(sequenceNo, questionnaireStepBean);
	 	            	}
	            	 }
	            	 fQuestionnaireStepBean.setStepId(formIdList.get(i));
	 	             fQuestionnaireStepBean.setStepType(FdahpStudyDesignerConstants.FORM_STEP);
	 	             fQuestionnaireStepBean.setSequenceNo(sequenceNoMap.get(formIdList.get(i)+FdahpStudyDesignerConstants.FORM_STEP));
	 	             fQuestionnaireStepBean.setFromMap(formQuestionMap);
	 	             fQuestionnaireStepBean.setDestinationStep(destinationMap.get(formIdList.get(i)+FdahpStudyDesignerConstants.FORM_STEP));
	 	             fQuestionnaireStepBean.setDestinationText(destinationText.get(destinationMap.get(formIdList.get(i)+FdahpStudyDesignerConstants.FORM_STEP)));
	            	 qTreeMap.put(sequenceNoMap.get(formIdList.get(i)+FdahpStudyDesignerConstants.FORM_STEP), fQuestionnaireStepBean);
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
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		QuestionnaireBo questionnaireBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("checkQuestionnaireShortTitle").setInteger("studyId", studyId).setString("shortTitle", shortTitle);
			questionnaireBo = (QuestionnaireBo) query.uniqueResult();
			if(questionnaireBo != null){
				message = FdahpStudyDesignerConstants.SUCCESS;
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
				if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTION_STEP)){
					QuestionsBo questionsBo= null; 
					query = session.getNamedQuery("getQuestionStep").setInteger("stepId", stepId);
					questionsBo = (QuestionsBo) query.uniqueResult();
					if(questionsBo != null && questionsBo.getId() != null){
						QuestionReponseTypeBo questionReponseTypeBo = null;
						query = session.getNamedQuery("getQuestionResponse").setInteger("questionsResponseTypeId", questionsBo.getId());
						questionReponseTypeBo = (QuestionReponseTypeBo) query.uniqueResult();
						if(questionReponseTypeBo != null){
							if(questionReponseTypeBo.getStyle() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getStyle())){
								questionReponseTypeBo.setStyle(questionReponseTypeBo.getStyle());
								if(questionReponseTypeBo.getStyle().equalsIgnoreCase("Date")){
									if(questionReponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMinDate())){
										questionReponseTypeBo.setMinDate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(questionReponseTypeBo.getMinDate())));
									}
									if(questionReponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMaxDate())){
										questionReponseTypeBo.setMaxDate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(questionReponseTypeBo.getMaxDate())));
									}
									if(questionReponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getDefaultDate())){
										questionReponseTypeBo.setDefaultDate(new SimpleDateFormat("MM/dd/yyyy").format(new SimpleDateFormat("yyyy-MM-dd").parse(questionReponseTypeBo.getDefaultDate())));
									}
								}else if(questionReponseTypeBo.getStyle().equalsIgnoreCase("Date-Time")){
									if(questionReponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMinDate())){
										questionReponseTypeBo.setMinDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(questionReponseTypeBo.getMinDate())));
									}
									if(questionReponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMaxDate())){
										questionReponseTypeBo.setMaxDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(questionReponseTypeBo.getMaxDate())));
									}
									if(questionReponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getDefaultDate())){
										questionReponseTypeBo.setDefaultDate(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(questionReponseTypeBo.getDefaultDate())));
									}
								}
							}
						}
						questionnairesStepsBo.setQuestionReponseTypeBo(questionReponseTypeBo);
						
						List<QuestionResponseSubTypeBo> questionResponseSubTypeList = null;
						query = session.getNamedQuery("getQuestionSubResponse").setInteger("responseTypeId", questionsBo.getId());
						questionResponseSubTypeList =  query.list();
						/*if(questionResponseSubTypeList != null && !questionResponseSubTypeList.isEmpty()){
							for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionResponseSubTypeList){
								if(questionResponseSubTypeBo != null && questionResponseSubTypeBo.getImage() != null){
									questionResponseSubTypeBo.setImage();
								}
							}
						}*/
						questionnairesStepsBo.setQuestionResponseSubTypeList(questionResponseSubTypeList);
						
					}
					questionnairesStepsBo.setQuestionsBo(questionsBo);
				}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.FORM_STEP)){
					String fromQuery = "select f.form_id,f.question_id,f.sequence_no, q.id, q.question,q.response_type,q.add_line_chart,q.use_stastic_data,q.status from questions q, form_mapping f where q.id=f.question_id and f.form_id="+stepId+" order by f.form_id";
					Iterator iterator = session.createSQLQuery(fromQuery).list().iterator();
		            List result = session.createSQLQuery(fromQuery).list();
		            TreeMap<Integer, QuestionnaireStepBean> formQuestionMap = new TreeMap<>();
	            	boolean isDone=true;
		            while (iterator.hasNext()) {
	 	            	Object[] objects = (Object[]) iterator.next();
	 	            	Integer formId = (Integer) objects[0];
	 	            	Integer sequenceNo = (Integer) objects[2]; 
	 	            	Integer questionId = (Integer) objects[3];
	 	            	String questionText = (String) objects[4];
	 	            	Integer responseType = (Integer) objects[5];
	 	            	String lineChart = (String) objects[6];
	 	            	String statData = (String) objects[7];
	 	            	Boolean status = (Boolean) objects[8];
	 	            	QuestionnaireStepBean questionnaireStepBean = new QuestionnaireStepBean();
            			questionnaireStepBean.setStepId(formId);
            			questionnaireStepBean.setQuestionInstructionId(questionId);
            			questionnaireStepBean.setTitle(questionText);
            			questionnaireStepBean.setSequenceNo(sequenceNo);
            			questionnaireStepBean.setStepType(FdahpStudyDesignerConstants.FORM_STEP);
            			questionnaireStepBean.setResponseType(responseType);
            			questionnaireStepBean.setLineChart(lineChart);
            			questionnaireStepBean.setStatData(statData);
            			formQuestionMap.put(sequenceNo, questionnaireStepBean);
            			if(!status){
            				isDone = false;
            			}
	            	 }
		            questionnairesStepsBo.setStatus(isDone);
	            	questionnairesStepsBo.setFormQuestionMap(formQuestionMap);
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
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireStepShortTitle() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("checkQuestionnaireStepShortTitle").setInteger("questionnaireId", questionnaireId).setString("shortTitle", shortTitle);
			questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
			if(questionnairesStepsBo != null){
				message = FdahpStudyDesignerConstants.SUCCESS;
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
		QuestionnairesStepsBo addOrUpdateQuestionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(questionnairesStepsBo != null){
				if(questionnairesStepsBo.getStepId() != null){
					addOrUpdateQuestionnairesStepsBo = (QuestionnairesStepsBo) session.get(QuestionnairesStepsBo.class, questionnairesStepsBo.getStepId());
				}else{
					addOrUpdateQuestionnairesStepsBo = new QuestionnairesStepsBo();
					addOrUpdateQuestionnairesStepsBo.setActive(true);
					addOrUpdateQuestionnairesStepsBo.setDestinationStep(0);
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
				if(questionnairesStepsBo.getQuestionnairesId() != null){
					addOrUpdateQuestionnairesStepsBo.setQuestionnairesId(questionnairesStepsBo.getQuestionnairesId());
				}
				if(questionnairesStepsBo.getInstructionFormId() != null){
					addOrUpdateQuestionnairesStepsBo.setInstructionFormId(questionnairesStepsBo.getInstructionFormId());
				}
				if(questionnairesStepsBo.getStepType() != null){
					addOrUpdateQuestionnairesStepsBo.setStepType(questionnairesStepsBo.getStepType());
				}
				if(questionnairesStepsBo.getCreatedOn() != null){
					addOrUpdateQuestionnairesStepsBo.setCreatedOn(questionnairesStepsBo.getCreatedOn());
				}
				if(questionnairesStepsBo.getCreatedBy() != null){
					addOrUpdateQuestionnairesStepsBo.setCreatedBy(questionnairesStepsBo.getCreatedBy());
				}
				if(questionnairesStepsBo.getModifiedOn() != null){
					addOrUpdateQuestionnairesStepsBo.setModifiedOn(questionnairesStepsBo.getModifiedOn());
				}
				if(questionnairesStepsBo.getModifiedBy() != null){
					addOrUpdateQuestionnairesStepsBo.setModifiedBy(questionnairesStepsBo.getModifiedBy());
				}
				if(questionnairesStepsBo.getType() != null){
					if(questionnairesStepsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_SAVE)){
						addOrUpdateQuestionnairesStepsBo.setStatus(false);
					}else if(questionnairesStepsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_COMPLETE)){
						addOrUpdateQuestionnairesStepsBo.setStatus(true);
					}
				}
				int count = 0;
				if(addOrUpdateQuestionnairesStepsBo.getQuestionnairesId() != null && addOrUpdateQuestionnairesStepsBo.getStepId() == null){
					FormBo formBo = new FormBo();
					formBo.setActive(true);
					formBo.setCreatedOn(addOrUpdateQuestionnairesStepsBo.getCreatedOn());
					formBo.setCreatedBy(addOrUpdateQuestionnairesStepsBo.getCreatedBy());
					session.saveOrUpdate(formBo);
					addOrUpdateQuestionnairesStepsBo.setQuestionnairesId(addOrUpdateQuestionnairesStepsBo.getQuestionnairesId());
					addOrUpdateQuestionnairesStepsBo.setInstructionFormId(formBo.getFormId());
					addOrUpdateQuestionnairesStepsBo.setStepType(FdahpStudyDesignerConstants.FORM_STEP);
					QuestionnairesStepsBo existedQuestionnairesStepsBo = null;
					query = session.getNamedQuery("getQuestionnaireStepSequenceNo").setInteger("questionnairesId", addOrUpdateQuestionnairesStepsBo.getQuestionnairesId());
					query.setMaxResults(1);
					existedQuestionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
					if(existedQuestionnairesStepsBo != null){
						count = existedQuestionnairesStepsBo.getSequenceNo()+1;
					}else{
						count = count +1;
					}
					addOrUpdateQuestionnairesStepsBo.setSequenceNo(count);
					
				}
				session.saveOrUpdate(addOrUpdateQuestionnairesStepsBo);
				if(addOrUpdateQuestionnairesStepsBo != null && count > 0){
					String updateQuery="update QuestionnairesStepsBo QSBO set QSBO.destinationStep="+addOrUpdateQuestionnairesStepsBo.getStepId()+" where "
							+ "QSBO.destinationStep=0 and QSBO.sequenceNo="+(count-1)+" and QSBO.questionnairesId="+addOrUpdateQuestionnairesStepsBo.getQuestionnairesId();
					session.createQuery(updateQuery).executeUpdate();
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - saveOrUpdateFromQuestionnaireStep() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateFromQuestionnaireStep() - Ends");
		return addOrUpdateQuestionnairesStepsBo;
	}

	@Override
	public String reOrderFormStepQuestions(Integer formId, int oldOrderNumber,int newOrderNumber) {
		logger.info("StudyQuestionnaireDAOImpl - reOrderFormStepQuestions() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
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
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update FormMappingBo FMBO set FMBO.sequenceNo=FMBO.sequenceNo+1 where FMBO.formId="+formId+" and FMBO.sequenceNo >="+newOrderNumber+" and FMBO.sequenceNo <"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.getNamedQuery("updateFromQuestionSequenceNo").setInteger("newOrderNumber", newOrderNumber).setInteger("id", formMappingBo.getId());
						count = query.executeUpdate();
						message = FdahpStudyDesignerConstants.SUCCESS;
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
	public String deleteFromStepQuestion(Integer formId, Integer questionId,SessionObject sessionObject) {
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		logger.info("StudyQuestionnaireDAOImpl - deleteFromStepQuestion() - Starts");
		FormMappingBo formMappingBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			query = session.getNamedQuery("getFormQuestion").setInteger("formId", formId).setInteger("questionId", questionId);
			formMappingBo = (FormMappingBo) query.uniqueResult();
			if(formMappingBo != null){
				String updateQuery = "update FormMappingBo FMBO set FMBO.sequenceNo=FMBO.sequenceNo-1 where FMBO.formId="+formMappingBo.getFormId()+" and FMBO.sequenceNo >="+formMappingBo.getSequenceNo();
				query = session.createQuery(updateQuery);
				query.executeUpdate();
				
				String deleteQuery = "Update QuestionsBo QBO set QBO.active=0,QBO.modifiedBy="+sessionObject.getUserId()+",QBO.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.id="+questionId;
				query = session.createQuery(deleteQuery);
				query.executeUpdate();
				
				session.delete(formMappingBo);
				
				message = FdahpStudyDesignerConstants.SUCCESS;
			}
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
	/**
	 * @author Ravinder
	 * @param Integer : questionnaireId
	 * @return List : QuestionnaireStepList
	 * This method is used to get the forward question step of an questionnaire based on sequence no
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<QuestionnairesStepsBo> getQuestionnairesStepsList(Integer questionnaireId, Integer sequenceNo) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStepList() - Starts");
		Session session = null;
		List<QuestionnairesStepsBo> questionnairesStepsList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getForwardQuestionnaireSteps").setInteger("questionnairesId", questionnaireId).setInteger("sequenceNo", sequenceNo);
			questionnairesStepsList = query.list();
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - deleteFromStepQuestion() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		return questionnairesStepsList;
	}
	/**
	 * @author Ravinder
	 * @param Object : QuestionnaireStepBo
	 * @return Object : QuestionnaireStepBo
	 * This method is used to save the question step in questionnaire 
	 */
	@Override
	public QuestionnairesStepsBo saveOrUpdateQuestionStep(QuestionnairesStepsBo questionnairesStepsBo) {
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateQuestionStep() - Starts");
		Session session = null;
		QuestionnairesStepsBo addOrUpdateQuestionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(questionnairesStepsBo != null){
				if(questionnairesStepsBo.getStepId() != null){
					addOrUpdateQuestionnairesStepsBo = (QuestionnairesStepsBo) session.get(QuestionnairesStepsBo.class, questionnairesStepsBo.getStepId());
				}else{
					addOrUpdateQuestionnairesStepsBo = new QuestionnairesStepsBo();
					addOrUpdateQuestionnairesStepsBo.setActive(true);
					addOrUpdateQuestionnairesStepsBo.setDestinationStep(0);
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
				if(questionnairesStepsBo.getQuestionnairesId() != null){
					addOrUpdateQuestionnairesStepsBo.setQuestionnairesId(questionnairesStepsBo.getQuestionnairesId());
				}
				if(questionnairesStepsBo.getInstructionFormId() != null){
					addOrUpdateQuestionnairesStepsBo.setInstructionFormId(questionnairesStepsBo.getInstructionFormId());
				}
				if(questionnairesStepsBo.getStepType() != null){
					addOrUpdateQuestionnairesStepsBo.setStepType(questionnairesStepsBo.getStepType());
				}
				if(questionnairesStepsBo.getType() != null){
					if(questionnairesStepsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_SAVE)){
						addOrUpdateQuestionnairesStepsBo.setStatus(false);
					}else if(questionnairesStepsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_COMPLETE)){
						addOrUpdateQuestionnairesStepsBo.setStatus(true);
					}
				}
				int count = 0;
				if(questionnairesStepsBo.getQuestionsBo() != null){
					addOrUpdateQuestionnairesStepsBo.setQuestionnairesId(addOrUpdateQuestionnairesStepsBo.getQuestionnairesId());
					QuestionsBo questionsBo = questionnairesStepsBo.getQuestionsBo();
					session.saveOrUpdate(questionsBo);
					addOrUpdateQuestionnairesStepsBo.setQuestionsBo(questionsBo);
					if(questionsBo != null && questionsBo.getId() != null && questionnairesStepsBo.getQuestionReponseTypeBo() != null){
						QuestionReponseTypeBo questionResponseTypeBo = getQuestionsResponseTypeBo(questionnairesStepsBo.getQuestionReponseTypeBo(), session);
						if(questionResponseTypeBo != null ){
							if(questionResponseTypeBo.getQuestionsResponseTypeId() == null){
								questionResponseTypeBo.setQuestionsResponseTypeId(questionsBo.getId());
							}
							session.saveOrUpdate(questionResponseTypeBo);
						}
						addOrUpdateQuestionnairesStepsBo.setQuestionReponseTypeBo(questionResponseTypeBo);
						if(questionnairesStepsBo.getQuestionResponseSubTypeList() != null && !questionnairesStepsBo.getQuestionResponseSubTypeList().isEmpty()){
							if(questionnairesStepsBo.getQuestionsBo().getResponseType() == 4 || questionnairesStepsBo.getQuestionsBo().getResponseType() == 3 ||
									questionnairesStepsBo.getQuestionsBo().getResponseType() == 6 || questionnairesStepsBo.getQuestionsBo().getResponseType() == 5){
								String deletQuesry = "Delete From QuestionResponseSubTypeBo QRSTBO where QRSTBO.responseTypeId="+questionsBo.getId();
								session.createQuery(deletQuesry).executeUpdate();
								for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionnairesStepsBo.getQuestionResponseSubTypeList()){
									if((questionResponseSubTypeBo.getText() != null && !questionResponseSubTypeBo.getText().isEmpty()) &&
											(questionResponseSubTypeBo.getValue() != null && !questionResponseSubTypeBo.getValue().isEmpty())){
										String fileName;
										if(questionResponseSubTypeBo.getImageFile() != null ){
											if(questionResponseSubTypeBo.getImage() != null && !questionResponseSubTypeBo.getImage().isEmpty()){
												questionResponseSubTypeBo.setImage(questionResponseSubTypeBo.getImage());
											}else{
												fileName = FdahpStudyDesignerUtil.getStandardFileName("QUESTIONNAIRE_PAGE",questionResponseSubTypeBo.getImageFile().getOriginalFilename(), String.valueOf(questionnairesStepsBo.getQuestionsBo().getId()));
												String imagePath = FdahpStudyDesignerUtil.uploadImageFile(questionResponseSubTypeBo.getImageFile(), fileName, FdahpStudyDesignerConstants.QUESTIONNAIRE);
												questionResponseSubTypeBo.setImage(imagePath);
											}
											
										}
										if(questionResponseSubTypeBo.getSelectImageFile() != null){
											if(questionResponseSubTypeBo.getSelectedImage() != null && !questionResponseSubTypeBo.getSelectedImage().isEmpty()){
												questionResponseSubTypeBo.setSelectedImage(questionResponseSubTypeBo.getSelectedImage());
											}else{
												fileName = FdahpStudyDesignerUtil.getStandardFileName("QUESTIONNAIRE_PAGE",questionResponseSubTypeBo.getSelectImageFile().getOriginalFilename(), String.valueOf(questionnairesStepsBo.getQuestionsBo().getId()));
												String imagePath = FdahpStudyDesignerUtil.uploadImageFile(questionResponseSubTypeBo.getSelectImageFile(), fileName, FdahpStudyDesignerConstants.QUESTIONNAIRE);
												questionResponseSubTypeBo.setSelectedImage(imagePath);
											}
										}
										questionResponseSubTypeBo.setResponseTypeId(questionsBo.getId());
										questionResponseSubTypeBo.setActive(true);
										session.save(questionResponseSubTypeBo);
									}
								}
							}else{
								for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionnairesStepsBo.getQuestionResponseSubTypeList()){
									questionResponseSubTypeBo.setResponseTypeId(questionsBo.getId());
									questionResponseSubTypeBo.setActive(true);
									session.saveOrUpdate(questionResponseSubTypeBo);
								}	
							}
						}
					}
					
					if(questionsBo != null){
						addOrUpdateQuestionnairesStepsBo.setInstructionFormId(questionsBo.getId());
					}
					if(addOrUpdateQuestionnairesStepsBo.getQuestionnairesId() != null && addOrUpdateQuestionnairesStepsBo.getStepId() == null){
						QuestionnairesStepsBo existedQuestionnairesStepsBo = null;
						query = session.getNamedQuery("getQuestionnaireStepSequenceNo").setInteger("questionnairesId", addOrUpdateQuestionnairesStepsBo.getQuestionnairesId());
						query.setMaxResults(1);
						existedQuestionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
						if(existedQuestionnairesStepsBo != null){
							count = existedQuestionnairesStepsBo.getSequenceNo()+1;
						}else{
							count = count +1;
						}
						addOrUpdateQuestionnairesStepsBo.setSequenceNo(count);
					}
				}
				session.saveOrUpdate(addOrUpdateQuestionnairesStepsBo);
				if(addOrUpdateQuestionnairesStepsBo != null && count > 0){
					String updateQuery="update QuestionnairesStepsBo QSBO set QSBO.destinationStep="+addOrUpdateQuestionnairesStepsBo.getStepId()+" where "
							+ "QSBO.destinationStep=0 and QSBO.sequenceNo="+(count-1)+" and QSBO.questionnairesId="+addOrUpdateQuestionnairesStepsBo.getQuestionnairesId();
					session.createQuery(updateQuery).executeUpdate();
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - saveOrUpdateQuestionStep() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateQuestionStep() - Ends");
		return addOrUpdateQuestionnairesStepsBo;
	}
	
	public QuestionReponseTypeBo getQuestionsResponseTypeBo(QuestionReponseTypeBo questionsResponseTypeBo,Session session){
		logger.info("StudyQuestionnaireDAOImpl - getQuestionsResponseTypeBo() - Starts");
		QuestionReponseTypeBo addOrUpdateQuestionsResponseTypeBo=null;
		try{
			if(questionsResponseTypeBo != null && session != null){
				if(questionsResponseTypeBo.getResponseTypeId() != null){
					addOrUpdateQuestionsResponseTypeBo = (QuestionReponseTypeBo) session.get(QuestionReponseTypeBo.class, questionsResponseTypeBo.getResponseTypeId());
				}else{
					addOrUpdateQuestionsResponseTypeBo = new QuestionReponseTypeBo();
					addOrUpdateQuestionsResponseTypeBo.setActive(true);
				}
				if(questionsResponseTypeBo.getQuestionsResponseTypeId() != null){
					addOrUpdateQuestionsResponseTypeBo.setQuestionsResponseTypeId(questionsResponseTypeBo.getQuestionsResponseTypeId());
				}
				if(questionsResponseTypeBo.getMinValue() != null){
					addOrUpdateQuestionsResponseTypeBo.setMinValue(questionsResponseTypeBo.getMinValue());
				}
				if(questionsResponseTypeBo.getMaxValue() != null){
					addOrUpdateQuestionsResponseTypeBo.setMaxValue(questionsResponseTypeBo.getMaxValue());
				}
				if(questionsResponseTypeBo.getDefaultValue() != null){
					addOrUpdateQuestionsResponseTypeBo.setDefaultValue(questionsResponseTypeBo.getDefaultValue());
				}
				if(questionsResponseTypeBo.getStep() != null){
					addOrUpdateQuestionsResponseTypeBo.setStep(questionsResponseTypeBo.getStep());
				}
				if(questionsResponseTypeBo.getVertical() != null){
					addOrUpdateQuestionsResponseTypeBo.setVertical(questionsResponseTypeBo.getVertical());
				}
				if(questionsResponseTypeBo.getMinDescription() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMinDescription())){
					addOrUpdateQuestionsResponseTypeBo.setMinDescription(questionsResponseTypeBo.getMinDescription());
				}
				if(questionsResponseTypeBo.getMaxDescription() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMaxDescription())){
					addOrUpdateQuestionsResponseTypeBo.setMaxDescription(questionsResponseTypeBo.getMaxDescription());
				}
				if(questionsResponseTypeBo.getMinImage() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMinImage())){
					addOrUpdateQuestionsResponseTypeBo.setMinImage(questionsResponseTypeBo.getMinImage());
				}
				if(questionsResponseTypeBo.getMaxImage() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMaxImage())){
					addOrUpdateQuestionsResponseTypeBo.setMaxImage(questionsResponseTypeBo.getMaxImage());
				}
				if(questionsResponseTypeBo.getMaxFractionDigits() != null){
					addOrUpdateQuestionsResponseTypeBo.setMaxFractionDigits(questionsResponseTypeBo.getMaxFractionDigits());
				}
				if(questionsResponseTypeBo.getTextChoices() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getTextChoices())){
					addOrUpdateQuestionsResponseTypeBo.setTextChoices(questionsResponseTypeBo.getTextChoices());
				}
				if(questionsResponseTypeBo.getSelectionStyle() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getSelectionStyle())){
					addOrUpdateQuestionsResponseTypeBo.setSelectionStyle(questionsResponseTypeBo.getSelectionStyle());			
				}
				if(questionsResponseTypeBo.getImageSize() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getImageSize())){
					addOrUpdateQuestionsResponseTypeBo.setImageSize(questionsResponseTypeBo.getImageSize());
				}
				if(questionsResponseTypeBo.getStyle() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getStyle())){
					addOrUpdateQuestionsResponseTypeBo.setStyle(questionsResponseTypeBo.getStyle());
					if(questionsResponseTypeBo.getStyle().equalsIgnoreCase("Date")){
						if(questionsResponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMinDate())){
							Date minDate = new SimpleDateFormat("MM/dd/yyyy").parse(questionsResponseTypeBo.getMinDate());
							logger.info("minDate:"+new SimpleDateFormat("yyyy-MM-dd").format(minDate)+":"+questionsResponseTypeBo.getMinDate());
							addOrUpdateQuestionsResponseTypeBo.setMinDate(new SimpleDateFormat("yyyy-MM-dd").format(minDate));
						}
						if(questionsResponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMaxDate())){
							Date maxDate = new SimpleDateFormat("MM/dd/yyyy").parse(questionsResponseTypeBo.getMaxDate());
							logger.info("maxDate:"+new SimpleDateFormat("yyyy-MM-dd").format(maxDate));
							addOrUpdateQuestionsResponseTypeBo.setMaxDate(new SimpleDateFormat("yyyy-MM-dd").format(maxDate));
						}
						if(questionsResponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getDefaultDate())){
							Date defaultDate = new SimpleDateFormat("MM/dd/yyyy").parse(questionsResponseTypeBo.getDefaultDate());
							logger.info("defaultDate:"+new SimpleDateFormat("yyyy-MM-dd").format(defaultDate));
							addOrUpdateQuestionsResponseTypeBo.setDefaultDate(new SimpleDateFormat("yyyy-MM-dd").format(defaultDate));
						}
					}else if(questionsResponseTypeBo.getStyle().equalsIgnoreCase("Date-Time")){
						if(questionsResponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMinDate())){
							addOrUpdateQuestionsResponseTypeBo.setMinDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(questionsResponseTypeBo.getMinDate())));
						}
						if(questionsResponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMaxDate())){
							addOrUpdateQuestionsResponseTypeBo.setMaxDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(questionsResponseTypeBo.getMaxDate())));
						}
						if(questionsResponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getDefaultDate())){
							addOrUpdateQuestionsResponseTypeBo.setDefaultDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").parse(questionsResponseTypeBo.getDefaultDate())));
						}
					}
				}
				if(questionsResponseTypeBo.getPlaceholder() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getPlaceholder())){
					addOrUpdateQuestionsResponseTypeBo.setPlaceholder(questionsResponseTypeBo.getPlaceholder());
				}
				if(questionsResponseTypeBo.getUnit() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getUnit())){
					addOrUpdateQuestionsResponseTypeBo.setUnit(questionsResponseTypeBo.getUnit());
				}
				if(questionsResponseTypeBo.getMaxLength() != null ){
					addOrUpdateQuestionsResponseTypeBo.setMaxLength(questionsResponseTypeBo.getMaxLength());	
				}
				if(questionsResponseTypeBo.getValidationRegex() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getValidationRegex())){
					addOrUpdateQuestionsResponseTypeBo.setValidationRegex(questionsResponseTypeBo.getValidationRegex());
				}
				if(questionsResponseTypeBo.getInvalidMessage() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getInvalidMessage())){
					addOrUpdateQuestionsResponseTypeBo.setInvalidMessage(questionsResponseTypeBo.getInvalidMessage());
				}
				if(questionsResponseTypeBo.getMultipleLines() != null){
					addOrUpdateQuestionsResponseTypeBo.setMultipleLines(questionsResponseTypeBo.getMultipleLines());
				}
				if(questionsResponseTypeBo.getMeasurementSystem() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMeasurementSystem())){
					addOrUpdateQuestionsResponseTypeBo.setMeasurementSystem(questionsResponseTypeBo.getMeasurementSystem());
				}
				if(questionsResponseTypeBo.getUseCurrentLocation() != null){
					addOrUpdateQuestionsResponseTypeBo.setUseCurrentLocation(questionsResponseTypeBo.getUseCurrentLocation());
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - getQuestionsResponseTypeBo() - Error",e);
		}
		logger.info("StudyQuestionnaireDAOImpl - getQuestionsResponseTypeBo() - Ends");
		return addOrUpdateQuestionsResponseTypeBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer : studyId
	 * @param Integer : questionnaireId
	 * 
	 * @return String : SUCCESS or FAILURE
	 */
	@Override
	public String deleteQuestuionnaireInfo(Integer studyId,Integer questionnaireId, SessionObject sessionObject) {
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestuionnaireInfo() - Starts");
		Session session = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String deleteQuery = "Update QuestionnaireBo QBO set QBO.active=0,QBO.modifiedBy="+sessionObject.getUserId()+",QBO.modifiedDate='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.studyId="+studyId+" and QBO.id="+questionnaireId;
			query = session.createQuery(deleteQuery);
			count = query.executeUpdate();
			if(count > 0){
				message = FdahpStudyDesignerConstants.SUCCESS;
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - deleteQuestuionnaireInfo() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestuionnaireInfo() - Ends");
		return message;
	}
	
	/**
	 * @author Ravinder
	 * @param String : shortTitle
	 * @param Integer : questionnaireId
	 * @return String SUCCESS or FAILUE
	 */
	@Override
	public String checkFromQuestionShortTitle(Integer questionnaireId,String shortTitle) {
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireStepShortTitle() - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("checkQuestionnaireStepShortTitle").setInteger("questionnaireId", questionnaireId).setString("shortTitle", shortTitle);
			questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
			if(questionnairesStepsBo != null){
				message = FdahpStudyDesignerConstants.SUCCESS;
			}else{
				String searchQuuery = "From QuestionsBo QBO where QBO.id IN (select f.questionId from FormMappingBo f where f.formId in"
						+ " (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.stepType='Form' and QSBO.active=1) and QBO.active=1 and QBO.shortTitle='"+shortTitle+"')";
				QuestionsBo questionsBo = (QuestionsBo) session.createQuery(searchQuuery).uniqueResult();			
				if(questionsBo != null){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
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

	@Override
	public Boolean isAnchorDateExistsForStudy(Integer studyId) {
		logger.info("StudyQuestionnaireDAOImpl - isAnchorDateExistsForStudy() - starts");
		boolean isExists = false;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = "select count(q.use_anchor_date) from questions q where q.id in ((select qsq.instruction_form_id from questionnaires_steps qsq where qsq.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and qsq.active=1 and qsq.questionnaires_id in "
					+ "(select qq.id from questionnaires qq where qq.study_id="+studyId+" and qq.active=1))) and q.use_anchor_date=1 and q.active=1";
			BigInteger count = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
			System.out.println("count:"+count +" searchQuery:"+searchQuery);
			if(count.intValue() > 0){
				isExists = true;
			}else{
				String subQuery = "select count(q.use_anchor_date) from questions q where q.id in (select fm.question_id from form_mapping fm where fm.form_id in"
								 +"( select f.form_id from form f where f.active=1 and f.form_id in (select qsf.instruction_form_id from questionnaires_steps qsf where qsf.step_type='"+FdahpStudyDesignerConstants.FORM_STEP+"' and qsf.questionnaires_id in "
								 +"(select qf.id from questionnaires qf where qf.study_id="+studyId+")))) and q.use_anchor_date=1";
				BigInteger subCount = (BigInteger) session.createSQLQuery(subQuery).uniqueResult();
				System.out.println("subCount:"+subCount +" subQuery:"+subQuery);
				if(subCount != null && subCount.intValue() > 0){
					isExists = true;
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - isAnchorDateExistsForStudy() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - isAnchorDateExistsForStudy() - Ends");
		return isExists;
	}

	/**
	 * @author Ravinder
	 * @param Integer : studyId
	 * @return Boolean true r false
	 */
	@Override
	public Boolean isQuestionnairesCompleted(Integer studyId) {
		logger.info("StudyQuestionnaireDAOImpl - isAnchorDateExistsForStudy() - starts");
		boolean isExists = true;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = "select sum(q.status = 0) as no from questionnaires_steps q where q.questionnaires_id in (select id from questionnaires where study_id="+studyId+" and active=1) and q.active=1";
			BigDecimal count = (BigDecimal) session.createSQLQuery(searchQuery).uniqueResult();
			if(count !=null && count.intValue() > 0){
				isExists = false;
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - isAnchorDateExistsForStudy() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - isAnchorDateExistsForStudy() - Ends");
		return isExists;
	}
}
