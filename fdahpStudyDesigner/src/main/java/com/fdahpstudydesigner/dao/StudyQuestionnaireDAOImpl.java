/**
 * 
 */
package com.fdahpstudydesigner.dao;

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

import org.apache.commons.collections.set.CompositeSet.SetMutator;
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
import com.fdahpstudydesigner.bo.ActiveTaskAtrributeValuesBo;
import com.fdahpstudydesigner.bo.ActiveTaskBo;
import com.fdahpstudydesigner.bo.FormBo;
import com.fdahpstudydesigner.bo.FormMappingBo;
import com.fdahpstudydesigner.bo.HealthKitKeysInfo;
import com.fdahpstudydesigner.bo.InstructionsBo;
import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.QuestionReponseTypeBo;
import com.fdahpstudydesigner.bo.QuestionResponseSubTypeBo;
import com.fdahpstudydesigner.bo.QuestionResponseTypeMasterInfoBo;
import com.fdahpstudydesigner.bo.QuestionnaireBo;
import com.fdahpstudydesigner.bo.QuestionnaireCustomScheduleBo;
import com.fdahpstudydesigner.bo.QuestionnairesFrequenciesBo;
import com.fdahpstudydesigner.bo.QuestionnairesStepsBo;
import com.fdahpstudydesigner.bo.QuestionsBo;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.bo.StudyPageBo;
import com.fdahpstudydesigner.bo.StudyVersionBo;
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
	private AuditLogDAO auditLogDAO;
	
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
	public List<QuestionnaireBo> getStudyQuestionnairesByStudyId(String studyId,Boolean isLive) {
		logger.info("StudyQuestionnaireDAOImpl - getStudyQuestionnairesByStudyId() - Starts");
		Session session = null;
		List<QuestionnaireBo> questionnaires = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if (StringUtils.isNotEmpty(studyId)) {
				if(isLive){
					String searchQuery = "From QuestionnaireBo QBO WHERE QBO.customStudyId ='"+studyId+"' and QBO.active=1 and QBO.live=1 order by QBO.createdDate DESC";
					query = session.createQuery(searchQuery);
				}else{
					query = session.getNamedQuery("getQuestionariesByStudyId").setInteger("studyId", Integer.parseInt(studyId));
				}
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
	public InstructionsBo getInstructionsBo(Integer instructionId,String questionnaireShortTitle,String customStudyId,Integer questionnaireId) {
		logger.info("StudyQuestionnaireDAOImpl - getInstructionsBo - Starts");
		Session session = null;
		InstructionsBo instructionsBo = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			instructionsBo = (InstructionsBo) session.get(InstructionsBo.class, instructionId);
			if(instructionsBo != null){
				if(questionnaireId != null){
					query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.instructionFormId="+instructionsBo.getId()+" and QSBO.stepType='"+FdahpStudyDesignerConstants.INSTRUCTION_STEP+"' and QSBO.active=1 and QSBO.questionnairesId="+questionnaireId);
				}else{
					query = session.getNamedQuery("getQuestionnaireStep").setInteger("instructionFormId", instructionsBo.getId()).setString("stepType", FdahpStudyDesignerConstants.INSTRUCTION_STEP);
				}
				//query = session.getNamedQuery("getQuestionnaireStep").setInteger("instructionFormId", instructionsBo.getId()).setString("stepType", FdahpStudyDesignerConstants.INSTRUCTION_STEP);
				questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
				
				if(StringUtils.isNotEmpty(questionnaireShortTitle)){
					//Duplicate ShortTitle per QuestionnaireStepBo Start 
					BigInteger shortTitleCount = (BigInteger)session.createSQLQuery("select count(*) from questionnaires_steps qs where qs.questionnaires_id  "
							+ "in(select q.id from questionnaires q where q.short_title='"+questionnaireShortTitle+"' and q.active=1 and q.is_live=1 and q.custom_study_id='"+customStudyId+"') "
							+ "and qs.step_short_title = '"+questionnairesStepsBo.getStepShortTitle()+"' and qs.active=1").uniqueResult();
					if(shortTitleCount!=null && shortTitleCount.intValue() > 0)
						questionnairesStepsBo.setIsShorTitleDuplicate(shortTitleCount.intValue());
					else
						questionnairesStepsBo.setIsShorTitleDuplicate(0);
				}else{
						questionnairesStepsBo.setIsShorTitleDuplicate(0);
				}
					//Duplicate ShortTitle per QuestionnaireStepBo End
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
	public InstructionsBo saveOrUpdateInstructionsBo(InstructionsBo instructionsBo, SessionObject sessionObject,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateInstructionsBo() - Starts");
		Session session = null;
		QuestionnairesStepsBo existedQuestionnairesStepsBo = null;
		String activitydetails = "";
		String activity = "";
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
						activity = FdahpStudyDesignerConstants.INSTRUCTION_ACTIVITY + " saved.";
						activitydetails = "Content saved for  instruction Step. (Step Key  = "+instructionsBo.getInstructionTitle()+", Study ID = "+customStudyId+")";
						query = session.createSQLQuery("update questionnaires q set q.status=0 where q.id="+questionnairesStepsBo.getQuestionnairesId());
						query.executeUpdate();
					}else if(instructionsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_COMPLETE)){
						questionnairesStepsBo.setStatus(true);
						activity = FdahpStudyDesignerConstants.INSTRUCTION_ACTIVITY + " marked Done.";
						activitydetails = "Instruction step succesfully checked for minimum content completeness and marked 'Done'. (Step Key  = "+instructionsBo.getInstructionTitle()+", Study ID = "+customStudyId+")";
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
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - saveOrUpdateInstructionsBo");
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - saveOrUpdateInstructionsBo() - Error",e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateInstructionsBo() - Ends");
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
	public QuestionnaireBo getQuestionnaireById(Integer questionnaireId, String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireById() - Starts");
		Session session = null;
		QuestionnaireBo questionnaireBo = null;
		
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			questionnaireBo = (QuestionnaireBo) session.get(QuestionnaireBo.class, questionnaireId);
			if(null != questionnaireBo){
				if(StringUtils.isNotEmpty(customStudyId)){
				//Duplicate ShortTitle per QuestionnaireBo Start
				BigInteger shortTitleCount = (BigInteger)session.createSQLQuery("select count(*) from questionnaires "
						+ "where short_title='"+questionnaireBo.getShortTitle()+"' and custom_study_id = '"+customStudyId+"' and active=1 and is_live=1").uniqueResult();
				if(shortTitleCount!=null && shortTitleCount.intValue() > 0)
					questionnaireBo.setShortTitleDuplicate(shortTitleCount.intValue());
				else
					questionnaireBo.setShortTitleDuplicate(0);
				}else{
					questionnaireBo.setShortTitleDuplicate(0);
				}
				//Duplicate ShortTitle per QuestionnaireBo End 
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
				if(questionnaireBo.getVersion()!=null){
					questionnaireBo.setQuestionnarieVersion(" (V"+questionnaireBo.getVersion()+")");
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
	public QuestionnaireBo saveORUpdateQuestionnaire(QuestionnaireBo questionnaireBo, SessionObject sessionObject,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - saveORUpdateQuestionnaire() - Starts");
		Session session = null;
		String activitydetails = "";
		String activity = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(questionnaireBo);
			if(questionnaireBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.SCHEDULE)){
				if(questionnaireBo != null &&  questionnaireBo.getId() != null){
					if(questionnaireBo.getQuestionnairesFrequenciesList() != null && !questionnaireBo.getQuestionnairesFrequenciesList().isEmpty()){
						/*String deleteQuery = "delete from questionnaires_custom_frequencies where questionnaires_id="+questionnaireBo.getId();
						query = session.createSQLQuery(deleteQuery);
						query.executeUpdate();
						String deleteQuery2 = "delete from questionnaires_frequencies where questionnaires_id="+questionnaireBo.getId();
						query = session.createSQLQuery(deleteQuery2);
						query.executeUpdate();*/
						query=session.createSQLQuery("CALL deleteQuestionnaireFrequencies(:questionnaireId)").setInteger("questionnaireId", questionnaireBo.getId());
					    query.executeUpdate();
						for(QuestionnairesFrequenciesBo questionnairesFrequenciesBo : questionnaireBo.getQuestionnairesFrequenciesList()){
							if(questionnairesFrequenciesBo.getFrequencyTime() != null){
								questionnairesFrequenciesBo.setFrequencyTime(FdahpStudyDesignerUtil.getFormattedDate(questionnairesFrequenciesBo.getFrequencyTime(), FdahpStudyDesignerConstants.SDF_TIME, FdahpStudyDesignerConstants.UI_SDF_TIME));
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
						if(!questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_DAILY) && !questionnaireBo.getFrequency().equalsIgnoreCase(questionnaireBo.getPreviousFrequency())){
							/*String deleteQuery = "delete from questionnaires_custom_frequencies where questionnaires_id="+questionnaireBo.getId();
							query = session.createSQLQuery(deleteQuery);
							query.executeUpdate();
							String deleteQuery2 = "delete from questionnaires_frequencies where questionnaires_id="+questionnaireBo.getId();
							query = session.createSQLQuery(deleteQuery2);
							query.executeUpdate();*/
							query=session.createSQLQuery("CALL deleteQuestionnaireFrequencies(:questionnaireId)").setInteger("questionnaireId", questionnaireBo.getId());
						    query.executeUpdate();
						}
						if(questionnairesFrequenciesBo.getFrequencyDate() != null || questionnairesFrequenciesBo.getFrequencyTime() != null || questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME)){
							if(questionnairesFrequenciesBo.getQuestionnairesId() == null){
								questionnairesFrequenciesBo.setQuestionnairesId(questionnaireBo.getId());
							}
							if(questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate() != null && !questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate().isEmpty()){
								questionnairesFrequenciesBo.setFrequencyDate(FdahpStudyDesignerUtil.getFormattedDate(questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.SD_DATE_FORMAT));
							}
							if(questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyTime() != null && !questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyTime().isEmpty()){
								questionnaireBo.getQuestionnairesFrequenciesBo().setFrequencyTime(FdahpStudyDesignerUtil.getFormattedDate(questionnaireBo.getQuestionnairesFrequenciesBo().getFrequencyTime(), FdahpStudyDesignerConstants.SDF_TIME, FdahpStudyDesignerConstants.UI_SDF_TIME));
							}
							session.saveOrUpdate(questionnairesFrequenciesBo);
						}
					}
					if(questionnaireBo.getQuestionnaireCustomScheduleBo() != null && !questionnaireBo.getQuestionnaireCustomScheduleBo().isEmpty()){
						/*String deleteQuery = "delete from questionnaires_custom_frequencies where questionnaires_id="+questionnaireBo.getId();
						query = session.createSQLQuery(deleteQuery);
						query.executeUpdate();
						String deleteQuery2 = "delete from questionnaires_frequencies where questionnaires_id="+questionnaireBo.getId();
						query = session.createSQLQuery(deleteQuery2);
						query.executeUpdate();*/
						query=session.createSQLQuery("CALL deleteQuestionnaireFrequencies(:questionnaireId)").setInteger("questionnaireId", questionnaireBo.getId());
					    query.executeUpdate();
						for(QuestionnaireCustomScheduleBo questionnaireCustomScheduleBo  : questionnaireBo.getQuestionnaireCustomScheduleBo()){
							if(questionnaireCustomScheduleBo.getFrequencyStartDate() != null && !questionnaireCustomScheduleBo.getFrequencyStartDate().isEmpty() && questionnaireCustomScheduleBo.getFrequencyEndDate() != null 
									&& !questionnaireCustomScheduleBo.getFrequencyEndDate().isEmpty() && questionnaireCustomScheduleBo.getFrequencyTime() != null){
								if(questionnaireCustomScheduleBo.getQuestionnairesId() == null){
									questionnaireCustomScheduleBo.setQuestionnairesId(questionnaireBo.getId());
								}
								questionnaireCustomScheduleBo.setFrequencyStartDate(FdahpStudyDesignerUtil.getFormattedDate(questionnaireCustomScheduleBo.getFrequencyStartDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.SD_DATE_FORMAT));
								questionnaireCustomScheduleBo.setFrequencyEndDate(FdahpStudyDesignerUtil.getFormattedDate(questionnaireCustomScheduleBo.getFrequencyEndDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.SD_DATE_FORMAT));
								if(questionnaireCustomScheduleBo.getFrequencyTime() != null && !questionnaireCustomScheduleBo.getFrequencyTime().isEmpty()){
									questionnaireCustomScheduleBo.setFrequencyTime(FdahpStudyDesignerUtil.getFormattedDate(questionnaireCustomScheduleBo.getFrequencyTime(), FdahpStudyDesignerConstants.SDF_TIME, FdahpStudyDesignerConstants.UI_SDF_TIME));
								}
								session.saveOrUpdate(questionnaireCustomScheduleBo);
							}
						}
					}
				}
			}
			if(!questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME)){
				String updateFromQuery = "update questions QBO,form_mapping f,questionnaires_steps QSBO SET QBO.use_anchor_date = 0 where "
						+ "QBO.id=f.question_id and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireBo.getId()+" and QSBO.active=1 "
								+ "and QSBO.step_type='"+FdahpStudyDesignerConstants.FORM_STEP+"' and QBO.active=1";
				query = session.createSQLQuery(updateFromQuery);
				query.executeUpdate();
				
				String updateQuestionSteps = "Update questions QBO,questionnaires_steps QSBO SET QBO.use_anchor_date = 0 where QBO.id=QSBO.instruction_form_id"
						+ " and QSBO.questionnaires_id="+questionnaireBo.getId()+" and QSBO.active=1 and"
								+ " QSBO.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and QBO.active=1";
				query = session.createSQLQuery(updateQuestionSteps);
				query.executeUpdate();
				
			}
			if(questionnaireBo.getFrequency() != null && questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_DAILY)){
				if(questionnaireBo.getCurrentFrequency() != null && questionnaireBo.getPreviousFrequency() != null && !questionnaireBo.getCurrentFrequency().equalsIgnoreCase(questionnaireBo.getPreviousFrequency())){
					updateLineChartSchedule(questionnaireBo.getId(), questionnaireBo.getCurrentFrequency(), sessionObject, session, transaction, customStudyId);	
				}
			}else if(questionnaireBo.getPreviousFrequency() != null && !questionnaireBo.getFrequency().equalsIgnoreCase(questionnaireBo.getPreviousFrequency())){
				updateLineChartSchedule(questionnaireBo.getId(), questionnaireBo.getFrequency(), sessionObject, session, transaction, customStudyId);	
			}
			
			activity = "Content saved for questionnaire.";
			activitydetails = "Content saved for questionnaire. (Study ID = "+customStudyId+").";
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - saveORUpdateQuestionnaire");
			
			if(questionnaireBo!=null && questionnaireBo.getStatus()){
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sessionObject.getUserId(), FdahpStudyDesignerConstants.DRAFT_QUESTIONNAIRE, questionnaireBo.getStudyId());
				//Notification Purpose needed Started
				queryString = " From StudyBo where customStudyId='"+customStudyId+"' and live=1";
				StudyBo studyBo = (StudyBo)session.createQuery(queryString).uniqueResult();
				if(studyBo!=null){
						queryString = " From StudyBo where id="+questionnaireBo.getStudyId();
						StudyBo draftStudyBo = (StudyBo)session.createQuery(queryString).uniqueResult();
					    NotificationBO notificationBO = null;
					    queryString = "From NotificationBO where questionnarieId="+questionnaireBo.getId() + "and studyId="+questionnaireBo.getStudyId();
					    notificationBO =  (NotificationBO)session.createQuery(queryString).setMaxResults(1).uniqueResult();
					    if(notificationBO==null){
						notificationBO = new NotificationBO();
						notificationBO.setStudyId(questionnaireBo.getStudyId());
						notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
						notificationBO.setNotificationType(FdahpStudyDesignerConstants.NOTIFICATION_ST);
						notificationBO.setNotificationSubType(FdahpStudyDesignerConstants.NOTIFICATION_SUBTYPE_ACTIVITY);
						notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
						notificationBO.setQuestionnarieId(questionnaireBo.getId());
						notificationBO.setNotificationStatus(false);
						notificationBO.setCreatedBy(sessionObject.getUserId());
						notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						notificationBO.setNotificationSent(false);
					    }else{
 							notificationBO.setModifiedBy(sessionObject.getUserId());
 							notificationBO.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					    }
					    notificationBO.setNotificationText(FdahpStudyDesignerConstants.NOTIFICATION_ACTIVETASK_TEXT.replace("$shortTitle", questionnaireBo.getTitle()).replace("$customId", draftStudyBo.getName()));
					    if(!notificationBO.isNotificationSent())
					    session.saveOrUpdate(notificationBO);
					    /*NotificationBO notificationBO = null;
						queryString = "From NotificationBO where "
									+ "notificationSubType='"+FdahpStudyDesignerConstants.NOTIFICATION_SUBTYPE_ACTIVITY+"' "
									+ "and customStudyId='"+draftStudyBo.getCustomStudyId()+"' and notificationSent=false";
							notificationBO =  (NotificationBO)session.createQuery(queryString).setMaxResults(1).uniqueResult();
							if(notificationBO == null){
								notificationBO = new NotificationBO();
								notificationBO.setStudyId(questionnaireBo.getStudyId());
								notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
								notificationBO.setNotificationType(FdahpStudyDesignerConstants.NOTIFICATION_ST);
								notificationBO.setNotificationSubType(FdahpStudyDesignerConstants.NOTIFICATION_SUBTYPE_ACTIVITY);
								notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
								//notificationBO.setQuestionnarieId(questionnaireBo.getId());
								notificationBO.setNotificationStatus(false);
								notificationBO.setCreatedBy(sessionObject.getUserId());
								notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								notificationBO.setNotificationSent(false);
								notificationBO.setNotificationText(FdahpStudyDesignerConstants.NOTIFICATION_ACTIVETASK_TEXT_PUBLISH.replace("$customId", draftStudyBo.getName()));
								session.save(notificationBO);
						}*/
				}
				//Notification Purpose needed End
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
	public QuestionsBo getQuestionsById(Integer questionId,String questionnaireShortTitle) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionsById() - Starts");
		Session session = null;
		QuestionsBo questionsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			questionsBo = (QuestionsBo) session.get(QuestionsBo.class, questionId);
			if(questionsBo != null){
				try{
					if(StringUtils.isNotEmpty(questionnaireShortTitle)){
						
						//Duplicate ShortTitle per questionsBo Start 
						BigInteger quesionshortTitleCount = (BigInteger)session.createSQLQuery("select count(*) From questions QBO,form_mapping f,questionnaires_steps QSBO,questionnaires Q where QBO.id=f.question_id and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.short_title='"+questionnaireShortTitle+"'"
								+ " and Q.active=1 and Q.is_live=1 and QSBO.step_type='Form' and QBO.short_title='"+questionsBo.getShortTitle()+"' and QBO.active=1").uniqueResult();
						if(quesionshortTitleCount!=null && quesionshortTitleCount.intValue() > 0)
							questionsBo.setIsShorTitleDuplicate(quesionshortTitleCount.intValue());
						else
							questionsBo.setIsShorTitleDuplicate(0);
						//Duplicate ShortTitle per questionsBo End 
						
						//Duplicate statShortTitle per questionsBo Start 
						if(StringUtils.isNotEmpty(questionsBo.getStatShortName())){
							BigInteger quesionStatshortTitleCount = (BigInteger)session.createSQLQuery("select count(*) From questions QBO,form_mapping f,questionnaires_steps QSBO,questionnaires Q where QBO.id=f.question_id and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.short_title='"+questionnaireShortTitle+"'"
									+ " and Q.active=1 and Q.is_live=1 and QSBO.step_type='Form' and QBO.stat_short_name='"+questionsBo.getStatShortName()+"' and QBO.active=1").uniqueResult();
							if(quesionStatshortTitleCount!=null && quesionStatshortTitleCount.intValue() > 0)
							questionsBo.setIsStatShortNameDuplicate(quesionStatshortTitleCount.intValue());
						else
							questionsBo.setIsStatShortNameDuplicate(0);
						}
						
						//Duplicate statShortTitle per questionsBo Ends 
					}else{
						questionsBo.setIsStatShortNameDuplicate(0);
						questionsBo.setIsShorTitleDuplicate(0);
					}
				}catch(Exception e){
					logger.error("StudyQuestionnaireDAOImpl - getQuestionsById() - SUB  ERROR ", e);
				}
				QuestionReponseTypeBo questionReponseTypeBo = null;
				logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStep() - questionsResponseTypeId:"+questionsBo.getId());
				query = session.getNamedQuery("getQuestionResponse").setInteger("questionsResponseTypeId", questionsBo.getId());
				query.setMaxResults(1);
				questionReponseTypeBo = (QuestionReponseTypeBo) query.uniqueResult();
					if(questionReponseTypeBo != null && questionReponseTypeBo.getStyle() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getStyle())){
						questionReponseTypeBo.setStyle(questionReponseTypeBo.getStyle());
						if((FdahpStudyDesignerConstants.DATE).equalsIgnoreCase(questionReponseTypeBo.getStyle())){
							if(questionReponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMinDate())){
								questionReponseTypeBo.setMinDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getMinDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE));
							}
							if(questionReponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMaxDate())){
								questionReponseTypeBo.setMaxDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getMaxDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE));
							}
							if(questionReponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getDefaultDate())){
								questionReponseTypeBo.setDefaultDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getDefaultDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE));
							}
						}else if((FdahpStudyDesignerConstants.DATE_TIME).equalsIgnoreCase(questionReponseTypeBo.getStyle())){
							if(questionReponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMinDate())){
								questionReponseTypeBo.setMinDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getMinDate(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.REQUIRED_DATE_TIME));
							}
							if(questionReponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMaxDate())){
								questionReponseTypeBo.setMaxDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getMaxDate(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.REQUIRED_DATE_TIME));
							}
							if(questionReponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getDefaultDate())){
								questionReponseTypeBo.setDefaultDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getDefaultDate(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.REQUIRED_DATE_TIME));
							}
						}
					}
				questionsBo.setQuestionReponseTypeBo(questionReponseTypeBo);
				
				List<QuestionResponseSubTypeBo> questionResponseSubTypeList = null;
				query = session.getNamedQuery("getQuestionSubResponse").setInteger("responseTypeId", questionsBo.getId());
				questionResponseSubTypeList =  query.list();
				if(null != questionResponseSubTypeList && !questionResponseSubTypeList.isEmpty()){
					 for(QuestionResponseSubTypeBo s : questionResponseSubTypeList){
						 if(FdahpStudyDesignerUtil.isNotEmpty(s.getImage())){
							 if(s.getImage().contains("?v=")){
								 String imagePathArr[] = s.getImage().split("\\?");
								 s.setImage(imagePathArr[0]+ "?v=" + new Date().getTime());
							 }else{
								 s.setImage(s.getImage() + "?v=" + new Date().getTime()); 
							 }
						 }
						 if(FdahpStudyDesignerUtil.isNotEmpty(s.getSelectedImage())){
							 if(s.getSelectedImage().contains("?v=")){
								 String imagePathArr[] = s.getSelectedImage().split("\\?");
								 s.setSelectedImage(imagePathArr[0]+ "?v=" + new Date().getTime());
							 }else{
								 s.setSelectedImage(s.getSelectedImage() + "?v=" + new Date().getTime()); 
							 } 
						 }
					 }
				 }
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
				questionsBo.setQuestionReponseTypeBo(addQuestionReponseTypeBo);
				if(questionsBo.getQuestionResponseSubTypeList() != null && !questionsBo.getQuestionResponseSubTypeList().isEmpty()){
					String deletQuesry = "Delete From QuestionResponseSubTypeBo QRSTBO where QRSTBO.responseTypeId="+questionsBo.getId();
					session.createQuery(deletQuesry).executeUpdate();
					if(questionsBo.getResponseType() == 4 || questionsBo.getResponseType() == 3 ||
							questionsBo.getResponseType() == 6 || questionsBo.getResponseType() == 5){
						int i=0;
						for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionsBo.getQuestionResponseSubTypeList()){
							if((questionResponseSubTypeBo.getText() != null && !questionResponseSubTypeBo.getText().isEmpty()) &&
									(questionResponseSubTypeBo.getValue() != null && !questionResponseSubTypeBo.getValue().isEmpty())){
								String fileName;
								if(questionResponseSubTypeBo.getImageFile() != null ){
									if(questionResponseSubTypeBo.getImage() != null && !questionResponseSubTypeBo.getImage().isEmpty()){
										questionResponseSubTypeBo.setImage(questionResponseSubTypeBo.getImage());
									}else{
										fileName = FdahpStudyDesignerUtil.getStandardFileName(FdahpStudyDesignerConstants.FORM_STEP_IMAGE+i,questionResponseSubTypeBo.getImageFile().getOriginalFilename(), String.valueOf(questionsBo.getId()));
										String imagePath = FdahpStudyDesignerUtil.uploadImageFile(questionResponseSubTypeBo.getImageFile(), fileName, FdahpStudyDesignerConstants.QUESTIONNAIRE);
										questionResponseSubTypeBo.setImage(imagePath);
									}
									
								}
								if(questionResponseSubTypeBo.getSelectImageFile() != null){
									if(questionResponseSubTypeBo.getSelectedImage() != null && !questionResponseSubTypeBo.getSelectedImage().isEmpty()){
										questionResponseSubTypeBo.setSelectedImage(questionResponseSubTypeBo.getSelectedImage());
									}else{
										fileName = FdahpStudyDesignerUtil.getStandardFileName(FdahpStudyDesignerConstants.FORM_STEP_SELECTEDIMAGE+i,questionResponseSubTypeBo.getSelectImageFile().getOriginalFilename(), String.valueOf(questionsBo.getId()));
										String imagePath = FdahpStudyDesignerUtil.uploadImageFile(questionResponseSubTypeBo.getSelectImageFile(), fileName, FdahpStudyDesignerConstants.QUESTIONNAIRE);
										questionResponseSubTypeBo.setSelectedImage(imagePath);
									}
								}
								questionResponseSubTypeBo.setResponseTypeId(questionsBo.getId());
								questionResponseSubTypeBo.setActive(true);
								session.save(questionResponseSubTypeBo);
							}
							i=i+1;
						}
					}else{
						for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionsBo.getQuestionResponseSubTypeList()){
							questionResponseSubTypeBo.setResponseTypeId(questionsBo.getId());
							questionResponseSubTypeBo.setActive(true);
							session.save(questionResponseSubTypeBo);
						}	
					}
				}
				if(!questionsBo.getStatus()){
					if(questionsBo.getQuestionnaireId() != null){
						query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.instructionFormId="+questionsBo.getFromId()+" and QSBO.stepType='"+FdahpStudyDesignerConstants.FORM_STEP+"' and QSBO.active=1 and QSBO.questionnairesId="+questionsBo.getQuestionnaireId());
					}else{
						query = session.getNamedQuery("getQuestionnaireStep").setInteger("instructionFormId", questionsBo.getFromId()).setString("stepType", FdahpStudyDesignerConstants.FORM_STEP);
					}
					
					QuestionnairesStepsBo questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
						if(questionnairesStepsBo != null && questionnairesStepsBo.getStatus()){
							questionnairesStepsBo.setStatus(false);
							session.saveOrUpdate(questionnairesStepsBo);
							query = session.createSQLQuery("update questionnaires q set q.status=0 where q.id="+questionnairesStepsBo.getQuestionnairesId());
							query.executeUpdate();
						}
				}
				query = session.getNamedQuery("getFormMappingBO").setInteger("questionId", questionsBo.getId());
				FormMappingBo formMappingBo = (FormMappingBo) query.uniqueResult();
				if(formMappingBo == null){
					formMappingBo = new FormMappingBo();
					formMappingBo.setFormId(questionsBo.getFromId());
					formMappingBo.setQuestionId(questionsBo.getId());
					formMappingBo.setActive(true);
					int sequenceNo = 0;
					query = session.createQuery("From FormMappingBo FMBO where FMBO.formId="+questionsBo.getFromId()+" and FMBO.active=1 order by FMBO.sequenceNo DESC");
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
			query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.sequenceNo ="+oldOrderNumber+" and QSBO.active=1");
			questionnairesStepsBo = (QuestionnairesStepsBo)query.uniqueResult();
			if(questionnairesStepsBo != null){
				if (oldOrderNumber < newOrderNumber) {
					updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo-1 where QSBO.questionnairesId="+questionnaireId+" and QSBO.sequenceNo <="+newOrderNumber+" and QSBO.sequenceNo >"+oldOrderNumber+" and QSBO.active=1";
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update QuestionnairesStepsBo q set q.sequenceNo="+ newOrderNumber+" where q.stepId="+questionnairesStepsBo.getStepId()+" and q.active=1");
						count = query.executeUpdate();
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo+1 where QSBO.questionnairesId="+questionnaireId+" and QSBO.sequenceNo >="+newOrderNumber+" and QSBO.sequenceNo <"+oldOrderNumber+" and QSBO.active=1";
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update QuestionnairesStepsBo Q set Q.sequenceNo="+ newOrderNumber+" where Q.stepId="+questionnairesStepsBo.getStepId()+" and Q.active=1");
						count = query.executeUpdate();
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}
				//Reset destination steps in Questionnaire Starts
				if(message.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS)){
					String searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId + "and QSBO.active=1 order by QSBO.sequenceNo ASC";
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
					String questionResponseQuery = "update response_sub_type_value rs,questionnaires_steps q set rs.destination_step_id = NULL "
							+ "where rs.response_type_id=q.instruction_form_id and q.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and q.questionnaires_id="+questionnaireId+" and rs.active=1 and q.active=1";
					query = session.createSQLQuery(questionResponseQuery);
					query.executeUpdate();
				}
				//Reset destination steps in Questionnaire Ends
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
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId,String stepType,SessionObject sessionObject,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
	//	Query query = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		List<QuestionnairesStepsBo> questionnaireStepList = null;
		String activitydetails = "";
		String activity = "";
		StudyVersionBo studyVersionBo = null;
		String searchQuery=null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			query = session.getNamedQuery("getStudyByCustomStudyId").setString("customStudyId", customStudyId);
			query.setMaxResults(1);
			studyVersionBo = (StudyVersionBo)query.uniqueResult();
			
			if(studyVersionBo != null){
				searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.instructionFormId="+stepId+" and QSBO.questionnairesId="+questionnaireId+" and QSBO.stepType='"+stepType+"'";
				questionnairesStepsBo = (QuestionnairesStepsBo) session.createQuery(searchQuery).uniqueResult();
				if(questionnairesStepsBo != null){
					
					questionnairesStepsBo.setActive(false);
					session.saveOrUpdate(questionnairesStepsBo);
					
					query=session.createSQLQuery("CALL deleteQuestionnaireStep(:questionnaireId,:modifiedOn,:modifiedBy,:sequenceNo,:stepId,:steptype)").setInteger("questionnaireId", questionnaireId).
							setString("modifiedOn", FdahpStudyDesignerUtil.getCurrentDateTime()).setInteger("modifiedBy", sessionObject.getUserId()).setInteger("sequenceNo", 0).setInteger("stepId", stepId).setString("steptype", stepType);
					query.executeUpdate();
					
					if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.INSTRUCTION_STEP)){
						activity = FdahpStudyDesignerConstants.INSTRUCTION_ACTIVITY + " was deleted.";
						activitydetails = "Instruction step was deleted. (Study ID = "+customStudyId+")";
					}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTION_STEP)){
						activity = FdahpStudyDesignerConstants.QUESTIONSTEP_ACTIVITY + " was deleted.";
						activitydetails = "Question step was deleted. (Study ID = "+customStudyId+")";
					}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.FORM_STEP)){
						activity = FdahpStudyDesignerConstants.FORMSTEP_ACTIVITY + " was deleted.";
						activitydetails = "Form step was deleted .(Study ID = "+customStudyId+")";
					}
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}else{
				message = deleteQuestionnaireStep(stepId, questionnaireId, stepType, customStudyId, sessionObject, session, transaction);
			}
			//Reset destination steps in Questionnaire Starts
			searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId + " and QSBO.active=1 order by QSBO.sequenceNo ASC";
			
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
			
			String questionResponseQuery = "update response_sub_type_value rs,questionnaires_steps q set rs.destination_step_id = NULL "
					+ "where rs.response_type_id=q.instruction_form_id and q.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and q.questionnaires_id="+questionnaireId+" and rs.active=1 and q.active=1";
			query = session.createSQLQuery(questionResponseQuery);
			query.executeUpdate();
			
			//Reset destination steps in Questionnaire Ends
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - deleteQuestionnaireStep");
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
	        Map<Integer, Boolean> formStatusMap = new HashMap<>();
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
		                          formStatusMap.put(questionaireSteps.getInstructionFormId(), questionaireSteps.getStatus());
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
	            String fromQuery = "select f.form_id,f.question_id,f.sequence_no, q.id, q.question,q.response_type,q.add_line_chart,q.use_stastic_data,q.status,q.use_anchor_date from questions q, form_mapping f where q.id=f.question_id and q.active=1 and f.form_id IN ("+StringUtils.join(formIdList, ",")+") and f.active=1 order by f.form_id";
	            List result = session.createSQLQuery(fromQuery).list();
	            logger.info("@@@@@@@@result size :"+result.size());
	            for(int i=0;i<formIdList.size();i++){
	            	QuestionnaireStepBean fQuestionnaireStepBean = new QuestionnaireStepBean();
	            	TreeMap<Integer, QuestionnaireStepBean> formQuestionMap = new TreeMap<>();
	            	 for (int j=0;j<result.size();j++) {
	 	            	Object[] objects = (Object[]) result.get(j);
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
	 	             fQuestionnaireStepBean.setStatus(formStatusMap.get(formIdList.get(i)));
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
	@SuppressWarnings("unchecked")
	@Override
	public String checkQuestionnaireShortTitle(Integer studyId,String shortTitle,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireShortTitle() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		List<QuestionnaireBo> questionnaireBo = null;
		List<ActiveTaskBo>  taskBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(customStudyId != null && !customStudyId.isEmpty()){
				query = session.createQuery("From QuestionnaireBo QBO where QBO.studyId IN(select id From StudyBo SBO WHERE customStudyId='"+customStudyId+"') and QBO.shortTitle='"+shortTitle+"'");
				questionnaireBo = query.list();
				if(questionnaireBo != null && !questionnaireBo.isEmpty()){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}else{
					queryString = "from ActiveTaskBo where studyId IN(select id From StudyBo SBO WHERE customStudyId='"+customStudyId+"') and shortTitle='"+shortTitle+"'";
					taskBo = session.createQuery(queryString).list();
					
					if(taskBo != null && !taskBo.isEmpty()){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}
			}else{
				query = session.getNamedQuery("checkQuestionnaireShortTitle").setInteger("studyId", studyId).setString("shortTitle", shortTitle);
				questionnaireBo = query.list();
				
				if(questionnaireBo != null && !questionnaireBo.isEmpty()){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}else{
					queryString = "from ActiveTaskBo where studyId="+studyId+" and shortTitle='"+shortTitle+"'";
					taskBo = session.createQuery(queryString).list();
					
					if(taskBo != null && !taskBo.isEmpty()){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}
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
	public QuestionnairesStepsBo getQuestionnaireStep(Integer stepId,String stepType, String questionnaireShortTitle,String customStudyId,Integer questionnaireId) {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStep() - Starts");
		Session session = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(questionnaireId != null){
				query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.instructionFormId="+stepId+" and QSBO.stepType='"+stepType+"' and QSBO.active=1 and QSBO.questionnairesId="+questionnaireId);
			}else{
				query = session.getNamedQuery("getQuestionnaireStep").setInteger("instructionFormId", stepId).setString("stepType", stepType);
			}
			questionnairesStepsBo = (QuestionnairesStepsBo) query.uniqueResult();
			if(null != questionnairesStepsBo && questionnairesStepsBo.getStepType() != null){
				if(StringUtils.isNotEmpty(questionnaireShortTitle)){
				//Duplicate ShortTitle per QuestionnaireStepBo Start 
				BigInteger shortTitleCount = (BigInteger)session.createSQLQuery("select count(*) from questionnaires_steps qs where qs.questionnaires_id  "
						+ "in(select q.id from questionnaires q where q.short_title='"+questionnaireShortTitle+"' and q.active=1 and q.is_live=1 and q.custom_study_id='"+customStudyId+"') "
						+ "and qs.step_short_title = '"+questionnairesStepsBo.getStepShortTitle()+"' and qs.active=1").uniqueResult();
				if(shortTitleCount!=null && shortTitleCount.intValue() > 0)
					questionnairesStepsBo.setIsShorTitleDuplicate(shortTitleCount.intValue());
				else
					questionnairesStepsBo.setIsShorTitleDuplicate(0);
				}else{
					questionnairesStepsBo.setIsShorTitleDuplicate(0);
				}
				//Duplicate ShortTitle per QuestionnaireStepBo End
				if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTION_STEP)){
					QuestionsBo questionsBo= null; 
					query = session.getNamedQuery("getQuestionStep").setInteger("stepId", stepId);
					questionsBo = (QuestionsBo) query.uniqueResult();
					if(questionsBo != null && questionsBo.getId() != null){
						if(StringUtils.isNotEmpty(questionnaireShortTitle)){
						//Duplicate statShortTitle per questionsBo Start 
						if(StringUtils.isNotEmpty(questionsBo.getStatShortName())){
							BigInteger quesionStatshortTitleCount = (BigInteger)session.createSQLQuery("select count(*) From questions QBO,questionnaires_steps QSBO,questionnaires Q where QBO.id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.short_title='"+questionnaireShortTitle+"'"
									+ " and Q.active=1 and Q.is_live=1 and Q.custom_study_id='"+customStudyId+"' and QSBO.step_type='Question' and QBO.stat_short_name='"+questionsBo.getStatShortName()+"' and QBO.active=1").uniqueResult();
						if(quesionStatshortTitleCount!=null && quesionStatshortTitleCount.intValue() > 0)
							questionsBo.setIsStatShortNameDuplicate(quesionStatshortTitleCount.intValue());
						else
							questionsBo.setIsStatShortNameDuplicate(0);
						}
						}else{
							questionsBo.setIsStatShortNameDuplicate(0);
						}
						//Duplicate statShortTitle per questionsBo End 
						
						QuestionReponseTypeBo questionReponseTypeBo = null;
						logger.info("StudyQuestionnaireDAOImpl - getQuestionnaireStep() - questionsResponseTypeId:"+questionsBo.getId());
						query = session.getNamedQuery("getQuestionResponse").setInteger("questionsResponseTypeId", questionsBo.getId());
						query.setMaxResults(1);
						questionReponseTypeBo = (QuestionReponseTypeBo) query.uniqueResult();
							if(questionReponseTypeBo != null && questionReponseTypeBo.getStyle() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getStyle())){
								questionReponseTypeBo.setStyle(questionReponseTypeBo.getStyle());
								if((FdahpStudyDesignerConstants.DATE).equalsIgnoreCase(questionReponseTypeBo.getStyle())){
									if(questionReponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMinDate())){
										questionReponseTypeBo.setMinDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getMinDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE));
									}
									if(questionReponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMaxDate())){
										questionReponseTypeBo.setMaxDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getMaxDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE));
									}
									if(questionReponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getDefaultDate())){
										questionReponseTypeBo.setDefaultDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getDefaultDate(), FdahpStudyDesignerConstants.DB_SDF_DATE, FdahpStudyDesignerConstants.UI_SDF_DATE));
									}
								}else if((FdahpStudyDesignerConstants.DATE_TIME).equalsIgnoreCase(questionReponseTypeBo.getStyle())){
									if(questionReponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMinDate())){
										questionReponseTypeBo.setMinDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getMinDate(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.REQUIRED_DATE_TIME));
									}
									if(questionReponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getMaxDate())){
										questionReponseTypeBo.setMaxDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getMaxDate(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.REQUIRED_DATE_TIME));
									}
									if(questionReponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionReponseTypeBo.getDefaultDate())){
										questionReponseTypeBo.setDefaultDate(FdahpStudyDesignerUtil.getFormattedDate(questionReponseTypeBo.getDefaultDate(), FdahpStudyDesignerConstants.DB_SDF_DATE_TIME, FdahpStudyDesignerConstants.REQUIRED_DATE_TIME));
									}
								}
							}
						questionnairesStepsBo.setQuestionReponseTypeBo(questionReponseTypeBo);
						
						List<QuestionResponseSubTypeBo> questionResponseSubTypeList = null;
						query = session.getNamedQuery("getQuestionSubResponse").setInteger("responseTypeId", questionsBo.getId());
						questionResponseSubTypeList =  query.list();
						if(null != questionResponseSubTypeList && !questionResponseSubTypeList.isEmpty()){
							 for(QuestionResponseSubTypeBo s : questionResponseSubTypeList){
								 if(FdahpStudyDesignerUtil.isNotEmpty(s.getImage())){
									 if(s.getImage().contains("?v=")){
										 String imagePathArr[] = s.getImage().split("\\?");
										 s.setImage(imagePathArr[0]+ "?v=" + new Date().getTime());
									 }else{
										 s.setImage(s.getImage() + "?v=" + new Date().getTime()); 
									 }
								 }
								 if(FdahpStudyDesignerUtil.isNotEmpty(s.getSelectedImage())){
									 if(s.getSelectedImage().contains("?v=")){
										 String imagePathArr[] = s.getSelectedImage().split("\\?");
										 s.setSelectedImage(imagePathArr[0]+ "?v=" + new Date().getTime());
									 }else{
										 s.setSelectedImage(s.getSelectedImage() + "?v=" + new Date().getTime()); 
									 } 
								 }
							 }
						}
						questionnairesStepsBo.setQuestionResponseSubTypeList(questionResponseSubTypeList);
						
					}
					questionnairesStepsBo.setQuestionsBo(questionsBo);
				}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.FORM_STEP)){
					String fromQuery = "select f.form_id,f.question_id,f.sequence_no, q.id, q.question,q.response_type,q.add_line_chart,q.use_stastic_data,q.status,q.use_anchor_date from questions q, form_mapping f where q.id=f.question_id and f.form_id="+stepId+" and f.active=1 order by f.form_id";
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
	 	            	Boolean useAnchorDate = (Boolean) objects[9];
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
	@SuppressWarnings("unchecked")
	@Override
	public String checkQuestionnaireStepShortTitle(Integer questionnaireId,	String stepType, String shortTitle,String questionnaireShortTitle,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireStepShortTitle() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		List<QuestionnairesStepsBo> questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(questionnaireShortTitle != null && !questionnaireShortTitle.isEmpty()){
				query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.questionnairesId IN (select QBO.id From QuestionnaireBo QBO where QBO.shortTitle='"+questionnaireShortTitle+"' and QBO.studyId in(select id From StudyBo SBO WHERE customStudyId='"+customStudyId+"')) and QSBO.stepShortTitle='"+shortTitle+"'");
				questionnairesStepsBo =  query.list();
				if(questionnairesStepsBo != null && !questionnairesStepsBo.isEmpty()){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}else{
					String searchQuery = "select count(*) From questions QBO,form_mapping f,questionnaires_steps QSBO,questionnaires Q where QBO.id=f.question_id "
							+ "and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.short_title='"+questionnaireShortTitle+"' and Q.study_id in(select id From studies SBO WHERE custom_study_id='"+customStudyId+"') and QSBO.step_type='Form' and QBO.short_title='"+shortTitle+"'";
					BigInteger subCount = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
					if(subCount != null && subCount.intValue() > 0){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}	
				}
			}else{
				query = session.getNamedQuery("checkQuestionnaireStepShortTitle").setInteger("questionnaireId", questionnaireId).setString("shortTitle", shortTitle);
				questionnairesStepsBo =  query.list();
				if(questionnairesStepsBo != null && !questionnairesStepsBo.isEmpty()){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}else{
					String searchQuery = "select count(*) From questions QBO,form_mapping f,questionnaires_steps QSBO,questionnaires Q where QBO.id=f.question_id "
							+ "and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.id="+questionnaireId+" and QSBO.step_type='Form' and QBO.short_title='"+shortTitle+"'";
					BigInteger subCount = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
					if(subCount != null && subCount.intValue() > 0){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
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
	public QuestionnairesStepsBo saveOrUpdateFromQuestionnaireStep(QuestionnairesStepsBo questionnairesStepsBo, SessionObject sesObj,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateFromQuestionnaireStep() - Starts");
		Session session = null;
		QuestionnairesStepsBo addOrUpdateQuestionnairesStepsBo = null;
		String activitydetails = "";
		String activity = "";
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
				addOrUpdateQuestionnairesStepsBo.setRepeatableText(questionnairesStepsBo.getRepeatableText());
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
						activity = "Content saved for Form Step.";
						activitydetails = "Content saved for Form Step. (Question Key = "+questionnairesStepsBo.getStepShortTitle()+", Study ID = "+customStudyId+")";
						query = session.createSQLQuery("update questionnaires q set q.status=0 where q.id="+addOrUpdateQuestionnairesStepsBo.getQuestionnairesId());
						query.executeUpdate();
					}else if(questionnairesStepsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_COMPLETE)){
						addOrUpdateQuestionnairesStepsBo.setStatus(true);
						activity = "Question of form step succesfully checked for minimum content completeness.";
						activitydetails = "Question of form step succesfully checked for minimum content completeness and marked 'Done'.(Question Key = "+questionnairesStepsBo.getStepShortTitle()+", Study ID = "+customStudyId+")";
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
			auditLogDAO.saveToAuditLog(session, transaction, sesObj, activity, activitydetails, "StudyQuestionnaireDAOImpl - saveOrUpdateFromQuestionnaireStep");
			
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
					updateQuery = "update FormMappingBo FMBO set FMBO.sequenceNo=FMBO.sequenceNo-1 where FMBO.formId="+formId+" and FMBO.sequenceNo <="+newOrderNumber+" and FMBO.sequenceNo >"+oldOrderNumber+" and FMBO.active=1";
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.getNamedQuery("updateFromQuestionSequenceNo").setInteger("newOrderNumber", newOrderNumber).setInteger("id", formMappingBo.getId());
						count = query.executeUpdate();
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update FormMappingBo FMBO set FMBO.sequenceNo=FMBO.sequenceNo+1 where FMBO.formId="+formId+" and FMBO.sequenceNo >="+newOrderNumber+" and FMBO.sequenceNo <"+oldOrderNumber+" and FMBO.active=1";
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
	public String deleteFromStepQuestion(Integer formId, Integer questionId,SessionObject sessionObject,String customStudyId) {
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		logger.info("StudyQuestionnaireDAOImpl - deleteFromStepQuestion() - Starts");
		FormMappingBo formMappingBo = null;
		String activitydetails = "";
		String activity = "";
		StudyVersionBo studyVersionBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			query = session.getNamedQuery("getStudyByCustomStudyId").setString("customStudyId", customStudyId);
			query.setMaxResults(1);
			studyVersionBo = (StudyVersionBo)query.uniqueResult();
			query = session.getNamedQuery("getFormQuestion").setInteger("formId", formId).setInteger("questionId", questionId);
			formMappingBo = (FormMappingBo) query.uniqueResult();
			if(formMappingBo != null){
				String updateQuery = "update FormMappingBo FMBO set FMBO.sequenceNo=FMBO.sequenceNo-1 where FMBO.formId="+formMappingBo.getFormId()+" and FMBO.active=1 and FMBO.sequenceNo >="+formMappingBo.getSequenceNo();
				query = session.createQuery(updateQuery);
				query.executeUpdate();
				if(studyVersionBo != null ){
					formMappingBo.setActive(false);
					session.saveOrUpdate(formMappingBo);
					String deleteQuery = "Update QuestionsBo QBO set QBO.active=0,QBO.modifiedBy="+sessionObject.getUserId()+",QBO.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.id="+questionId;
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
					
					String deleteResponse = "Update QuestionReponseTypeBo QRBO set QRBO.active=0 where QRBO.questionsResponseTypeId="+questionId;
					query = session.createQuery(deleteResponse);
					query.executeUpdate();
					
					String deleteSubResponse = "Update QuestionResponseSubTypeBo QRSBO set QRSBO.active=0 where QRSBO.responseTypeId="+questionId;
					query = session.createQuery(deleteSubResponse);
					query.executeUpdate();
				}else{
					String deleteQuery = "delete QuestionsBo QBO where QBO.id="+questionId;
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
					
					String deleteResponse = "delete QuestionReponseTypeBo QRBO where QRBO.questionsResponseTypeId="+questionId;
					query = session.createQuery(deleteResponse);
					query.executeUpdate();
					
					String deleteSubResponse = "delete QuestionResponseSubTypeBo QRSBO  where QRSBO.responseTypeId="+questionId;
					query = session.createQuery(deleteSubResponse);
					query.executeUpdate();
					
					session.delete(formMappingBo);
				}
				message = FdahpStudyDesignerConstants.SUCCESS;
			}
			activity = "Question of form step was deleted.";
			activitydetails = "Question of form step was deleted. (Study ID = "+customStudyId+")";
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - deleteFromStepQuestion");
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
	public QuestionnairesStepsBo saveOrUpdateQuestionStep(QuestionnairesStepsBo questionnairesStepsBo, SessionObject sessionObject,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - saveOrUpdateQuestionStep() - Starts");
		Session session = null;
		QuestionnairesStepsBo addOrUpdateQuestionnairesStepsBo = null;
		String activitydetails = "";
		String activity = "";
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
						activity = "Question step saved.";
						activitydetails = "Content saved for question step. (Step Key  = "+questionnairesStepsBo.getStepShortTitle()+", Study ID = "+customStudyId+") ";
						query = session.createSQLQuery("update questionnaires q set q.status=0 where q.id="+addOrUpdateQuestionnairesStepsBo.getQuestionnairesId());
						query.executeUpdate();
					}else if(questionnairesStepsBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_COMPLETE)){
						addOrUpdateQuestionnairesStepsBo.setStatus(true);
						activity = "Question step successfully checked for minimum content completeness.";
						activitydetails = "Question step successfully checked for minimum content completeness and marked 'Done'. (Step Key  = "+questionnairesStepsBo.getStepShortTitle()+", Study ID = "+customStudyId+") ";
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
							String deletQuesry = "Delete From QuestionResponseSubTypeBo QRSTBO where QRSTBO.responseTypeId="+questionsBo.getId();
							session.createQuery(deletQuesry).executeUpdate();
							if(questionnairesStepsBo.getQuestionsBo().getResponseType() == 4 || questionnairesStepsBo.getQuestionsBo().getResponseType() == 3 ||
									questionnairesStepsBo.getQuestionsBo().getResponseType() == 6 || questionnairesStepsBo.getQuestionsBo().getResponseType() == 5){
								int j=0;
								for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionnairesStepsBo.getQuestionResponseSubTypeList()){
									if((questionResponseSubTypeBo.getText() != null && !questionResponseSubTypeBo.getText().isEmpty()) &&
											(questionResponseSubTypeBo.getValue() != null && !questionResponseSubTypeBo.getValue().isEmpty())){
										String fileName;
										if(questionResponseSubTypeBo.getImageFile() != null ){
											if(questionResponseSubTypeBo.getImage() != null && !questionResponseSubTypeBo.getImage().isEmpty()){
												questionResponseSubTypeBo.setImage(questionResponseSubTypeBo.getImage());
											}else{
												fileName = FdahpStudyDesignerUtil.getStandardFileName(FdahpStudyDesignerConstants.QUESTION_STEP_IMAGE+j,questionResponseSubTypeBo.getImageFile().getOriginalFilename(), String.valueOf(questionnairesStepsBo.getQuestionsBo().getId()));
												String imagePath = FdahpStudyDesignerUtil.uploadImageFile(questionResponseSubTypeBo.getImageFile(), fileName, FdahpStudyDesignerConstants.QUESTIONNAIRE);
												questionResponseSubTypeBo.setImage(imagePath);
											}
											
										}
										if(questionResponseSubTypeBo.getSelectImageFile() != null){
											if(questionResponseSubTypeBo.getSelectedImage() != null && !questionResponseSubTypeBo.getSelectedImage().isEmpty()){
												questionResponseSubTypeBo.setSelectedImage(questionResponseSubTypeBo.getSelectedImage());
											}else{
												fileName = FdahpStudyDesignerUtil.getStandardFileName(FdahpStudyDesignerConstants.QUESTION_STEP_SELECTEDIMAGE+j,questionResponseSubTypeBo.getSelectImageFile().getOriginalFilename(), String.valueOf(questionnairesStepsBo.getQuestionsBo().getId()));
												String imagePath = FdahpStudyDesignerUtil.uploadImageFile(questionResponseSubTypeBo.getSelectImageFile(), fileName, FdahpStudyDesignerConstants.QUESTIONNAIRE);
												questionResponseSubTypeBo.setSelectedImage(imagePath);
											}
										}
										questionResponseSubTypeBo.setResponseTypeId(questionsBo.getId());
										questionResponseSubTypeBo.setActive(true);
										session.save(questionResponseSubTypeBo);
									}
									j=j+1;
								}
							}else{
								for(QuestionResponseSubTypeBo questionResponseSubTypeBo : questionnairesStepsBo.getQuestionResponseSubTypeList()){
									questionResponseSubTypeBo.setResponseTypeId(questionsBo.getId());
									questionResponseSubTypeBo.setActive(true);
									session.save(questionResponseSubTypeBo);
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
			
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - saveOrUpdateInstructionsBo");
			
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
				addOrUpdateQuestionsResponseTypeBo.setMinDescription(questionsResponseTypeBo.getMinDescription());
				addOrUpdateQuestionsResponseTypeBo.setMaxDescription(questionsResponseTypeBo.getMaxDescription());
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
					if((FdahpStudyDesignerConstants.DATE).equalsIgnoreCase(questionsResponseTypeBo.getStyle())){
						if(questionsResponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMinDate())){
							addOrUpdateQuestionsResponseTypeBo.setMinDate(FdahpStudyDesignerUtil.getFormattedDate(questionsResponseTypeBo.getMinDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.DB_SDF_DATE));
						}else{
							addOrUpdateQuestionsResponseTypeBo.setMinDate(null);
						}
						if(questionsResponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMaxDate())){
							addOrUpdateQuestionsResponseTypeBo.setMaxDate(FdahpStudyDesignerUtil.getFormattedDate(questionsResponseTypeBo.getMaxDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.DB_SDF_DATE));
						}else{
							addOrUpdateQuestionsResponseTypeBo.setMaxDate(null);
						}
						if(questionsResponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getDefaultDate())){
							addOrUpdateQuestionsResponseTypeBo.setDefaultDate(FdahpStudyDesignerUtil.getFormattedDate(questionsResponseTypeBo.getDefaultDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.DB_SDF_DATE));
						}else{
							addOrUpdateQuestionsResponseTypeBo.setDefaultDate(null);
						}
					}else if((FdahpStudyDesignerConstants.DATE_TIME).equalsIgnoreCase(questionsResponseTypeBo.getStyle())){
						if(questionsResponseTypeBo.getMinDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMinDate())){
							addOrUpdateQuestionsResponseTypeBo.setMinDate(FdahpStudyDesignerUtil.getFormattedDate(questionsResponseTypeBo.getMinDate(), FdahpStudyDesignerConstants.REQUIRED_DATE_TIME, FdahpStudyDesignerConstants.DB_SDF_DATE_TIME));
						}else{
							addOrUpdateQuestionsResponseTypeBo.setMinDate(null);
						}
						if(questionsResponseTypeBo.getMaxDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMaxDate())){
							addOrUpdateQuestionsResponseTypeBo.setMaxDate(FdahpStudyDesignerUtil.getFormattedDate(questionsResponseTypeBo.getMaxDate(), FdahpStudyDesignerConstants.REQUIRED_DATE_TIME, FdahpStudyDesignerConstants.DB_SDF_DATE_TIME));
						}else{
							addOrUpdateQuestionsResponseTypeBo.setMaxDate(null);
						}
						if(questionsResponseTypeBo.getDefaultDate() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getDefaultDate())){
							addOrUpdateQuestionsResponseTypeBo.setDefaultDate(FdahpStudyDesignerUtil.getFormattedDate(questionsResponseTypeBo.getDefaultDate(), FdahpStudyDesignerConstants.REQUIRED_DATE_TIME, FdahpStudyDesignerConstants.DB_SDF_DATE_TIME));
						}else{
							addOrUpdateQuestionsResponseTypeBo.setDefaultDate(null);
						}
					}
				}
				addOrUpdateQuestionsResponseTypeBo.setPlaceholder(questionsResponseTypeBo.getPlaceholder());
				addOrUpdateQuestionsResponseTypeBo.setUnit(questionsResponseTypeBo.getUnit());
				addOrUpdateQuestionsResponseTypeBo.setMaxLength(questionsResponseTypeBo.getMaxLength());
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
				addOrUpdateQuestionsResponseTypeBo.setValidationCondition(questionsResponseTypeBo.getValidationCondition());
				addOrUpdateQuestionsResponseTypeBo.setValidationCharacters(questionsResponseTypeBo.getValidationCharacters());
				addOrUpdateQuestionsResponseTypeBo.setValidationExceptText(questionsResponseTypeBo.getValidationExceptText());
				
				System.out.println("regEx:"+FdahpStudyDesignerUtil.getRegExpression(questionsResponseTypeBo.getValidationCondition(), questionsResponseTypeBo.getValidationCharacters(), questionsResponseTypeBo.getValidationExceptText()));
				addOrUpdateQuestionsResponseTypeBo.setValidationRegex(FdahpStudyDesignerUtil.getRegExpression(questionsResponseTypeBo.getValidationCondition(), questionsResponseTypeBo.getValidationCharacters(), questionsResponseTypeBo.getValidationExceptText()));
				
				String fileName;
				if(questionsResponseTypeBo.getMinImageFile() != null){
					if(questionsResponseTypeBo.getMinImage() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMinImage())){
						addOrUpdateQuestionsResponseTypeBo.setMinImage(questionsResponseTypeBo.getMinImage());
					}else{
						fileName = FdahpStudyDesignerUtil.getStandardFileName(FdahpStudyDesignerConstants.QUESTION_STEP_IMAGE+0,questionsResponseTypeBo.getMinImageFile().getOriginalFilename(), String.valueOf(questionsResponseTypeBo.getQuestionsResponseTypeId()));
						String imagePath = FdahpStudyDesignerUtil.uploadImageFile(questionsResponseTypeBo.getMinImageFile(), fileName, FdahpStudyDesignerConstants.QUESTIONNAIRE);
						addOrUpdateQuestionsResponseTypeBo.setMinImage(imagePath);
					}
				}
				if(questionsResponseTypeBo.getMaxImageFile() != null){
					if(questionsResponseTypeBo.getMaxImage() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getMaxImage())){
						addOrUpdateQuestionsResponseTypeBo.setMaxImage(questionsResponseTypeBo.getMaxImage());
					}else{
						fileName = FdahpStudyDesignerUtil.getStandardFileName(FdahpStudyDesignerConstants.QUESTION_STEP_IMAGE+1,questionsResponseTypeBo.getMaxImageFile().getOriginalFilename(), String.valueOf(questionsResponseTypeBo.getQuestionsResponseTypeId()));
						String imagePath = FdahpStudyDesignerUtil.uploadImageFile(questionsResponseTypeBo.getMaxImageFile(), fileName, FdahpStudyDesignerConstants.QUESTIONNAIRE);
						addOrUpdateQuestionsResponseTypeBo.setMaxImage(imagePath);
					}
				}
				if(questionsResponseTypeBo.getDefaultTime() != null && StringUtils.isNotEmpty(questionsResponseTypeBo.getDefaultTime())){
					addOrUpdateQuestionsResponseTypeBo.setDefaultTime(questionsResponseTypeBo.getDefaultTime());
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
	public String deleteQuestuionnaireInfo(Integer studyId,Integer questionnaireId, SessionObject sessionObject,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestuionnaireInfo() - Starts");
		Session session = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		int count = 0;
		String activitydetails = "";
		String activity = "";
		StudyVersionBo studyVersionBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			query = session.getNamedQuery("getStudyByCustomStudyId").setString("customStudyId", customStudyId);
			query.setMaxResults(1);
			studyVersionBo = (StudyVersionBo)query.uniqueResult();
			
			if(studyVersionBo != null){
				
				query=session.createSQLQuery("CALL deleteQuestionnaire(:questionnaireId,:modifiedOn,:modifiedBy,:studyId)").setInteger("questionnaireId", questionnaireId).
					       setString("modifiedOn", FdahpStudyDesignerUtil.getCurrentDateTime()).setInteger("modifiedBy", sessionObject.getUserId()).setInteger("studyId", studyId);
			    query.executeUpdate();
			    message = FdahpStudyDesignerConstants.SUCCESS;
			    
				/*String deleteQuery = "Update QuestionnaireBo QBO set QBO.active=0,QBO.modifiedBy="+sessionObject.getUserId()+",QBO.modifiedDate='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.studyId="+studyId+" and QBO.id="+questionnaireId+" and QBO.active=1";
				query = session.createQuery(deleteQuery);
				count = query.executeUpdate();
				if(count > 0){
					message = FdahpStudyDesignerConstants.SUCCESS;
					try{
						String deleteInsQuery = "Update instructions IBO , questionnaires_steps QSBO set IBO.active=0,IBO.modified_by="+sessionObject.getUserId()+",IBO.modified_on='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where IBO.id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireId+" and QSBO.active=1 and QSBO.step_type='"+FdahpStudyDesignerConstants.INSTRUCTION_STEP+"' and IBO.active=1";
						query = session.createSQLQuery(deleteInsQuery);
						query.executeUpdate();
						
						String deleteQuesQuery = "Update questions QBO,questionnaires_steps QSBO set QBO.active=0,QBO.modified_by="+sessionObject.getUserId()+",QBO.modified_on='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireId+" and QSBO.active=1 and QSBO.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and QBO.active=1";
						query = session.createSQLQuery(deleteQuesQuery);
						query.executeUpdate();
						
						String deleteFormQuery = "Update questions QBO,form_mapping FMBO,questionnaires_steps QSBO set QBO.active=0,QBO.modified_by="+sessionObject.getUserId()+",QBO.modified_on='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.id=FMBO.question_id and FMBO.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireId+" and QSBO.active=1 and QSBO.step_type='"+FdahpStudyDesignerConstants.FORM_STEP+"' and QBO.active=1";
						query = session.createSQLQuery(deleteFormQuery);
						query.executeUpdate();
						
						String formMappingDelete = "update form_mapping FMBO,questionnaires_steps QSBO set FMBO.active=0 where FMBO.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireId+" and QSBO.active=1 and QSBO.step_type='"+FdahpStudyDesignerConstants.FORM_STEP+"' and FMBO.active=1";
						query = session.createSQLQuery(formMappingDelete);
						query.executeUpdate();
						
						String formDelete = "Update form FBO,questionnaires_steps QSBO set FBO.active=0,FBO.modified_by="+sessionObject.getUserId()+",FBO.modified_on='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where FBO.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireId+" and QSBO.active=1 and QSBO.step_type='"+FdahpStudyDesignerConstants.FORM_STEP+"' and FBO.active=1";
						query = session.createSQLQuery(formDelete);
						query.executeUpdate();
						
						String searchQuery = "Update questionnaires_steps QSBO set QSBO.active=0,QSBO.modified_by="+sessionObject.getUserId()+",QSBO.modified_on='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QSBO.questionnaires_id="+questionnaireId+" and QSBO.active=1";
						query = session.createSQLQuery(searchQuery);
						query.executeUpdate();
						
					}catch(Exception e){
						logger.error("StudyQuestionnaireDAOImpl - sub deleteQuestuionnaireInfo() - Error",e);
					}
				}*/
			 }else{
				 message = deleteQuestuionnaireInfo(studyId, questionnaireId, customStudyId, session, transaction);
			 }
			activity = FdahpStudyDesignerConstants.QUESTIONNAIRE_ACTIVITY + " was deleted.";
			activitydetails = FdahpStudyDesignerConstants.QUESTIONNAIRE_ACTIVITY + " was deleted. (Study ID = "+customStudyId+")";
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - deleteQuestuionnaireInfo");
			
			queryString = "DELETE From NotificationBO where questionnarieId="+questionnaireId+ "AND notificationSent=false";
			session.createQuery(queryString).executeUpdate();
			/*queryString = "select count(*) from questionnaires where study_id in(select id from studies where custom_study_id='"+customStudyId+"' and is_live=0) and active=1 and version =0";
		    BigInteger qsubCount = (BigInteger) session.createSQLQuery(queryString).setMaxResults(1).uniqueResult();
		    
		    queryString = "select count(*) from active_task where study_id in(select id from studies where custom_study_id='"+customStudyId+"' and is_live=0) and active=1 and version =0";
		    BigInteger asubCount = (BigInteger) session.createSQLQuery(queryString).setMaxResults(1).uniqueResult();
		    if((qsubCount != null && qsubCount.intValue() > 0) ||  (asubCount != null && asubCount.intValue() > 0)){
		    	queryString = "DELETE From NotificationBO where"
						+" notificationSubType='"+FdahpStudyDesignerConstants.NOTIFICATION_SUBTYPE_ACTIVITY+"'"
						+" and customStudyId='"+customStudyId+"' and notificationSent=false";
		    	query = session.createQuery(queryString);
				query.executeUpdate();
		    }*/
			
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
	@SuppressWarnings("unchecked")
	@Override
	public String checkFromQuestionShortTitle(Integer questionnaireId,String shortTitle,String questionnaireShortTitle,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireStepShortTitle() - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		List<QuestionnairesStepsBo> questionnairesStepsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(questionnaireShortTitle != null && !questionnaireShortTitle.isEmpty()){
				query = session.createQuery("From QuestionnairesStepsBo QSBO where QSBO.questionnairesId IN (select QBO.id From QuestionnaireBo QBO where QBO.shortTitle='"+questionnaireShortTitle+"' and QBO.studyId in(select id From StudyBo SBO WHERE customStudyId='"+customStudyId+"')) and QSBO.stepShortTitle='"+shortTitle+"'");
				questionnairesStepsBo =  query.list();
				if(questionnairesStepsBo != null && !questionnairesStepsBo.isEmpty()){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}else{
					String searchQuery = "select count(*) From questions QBO,form_mapping f,questionnaires_steps QSBO,questionnaires Q where QBO.id=f.question_id "
							+ "and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.short_title='"+questionnaireShortTitle+"' and Q.study_id in(select id From studies SBO WHERE custom_study_id='"+customStudyId+"') and QSBO.step_type='Form' and QBO.short_title='"+shortTitle+"'";
					BigInteger subCount = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
					if(subCount != null && subCount.intValue() > 0){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}	
				}
			}else{
				query = session.getNamedQuery("checkQuestionnaireStepShortTitle").setInteger("questionnaireId", questionnaireId).setString("shortTitle", shortTitle);
				questionnairesStepsBo =  query.list();
				if(questionnairesStepsBo != null && !questionnairesStepsBo.isEmpty()){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}else{
					String searchQuery = "select count(*) From questions QBO,form_mapping f,questionnaires_steps QSBO,questionnaires Q where QBO.id=f.question_id "
							+ "and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.id="+questionnaireId+" and QSBO.step_type='Form' and QBO.short_title='"+shortTitle+"'";
					BigInteger subCount = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
					if(subCount != null && subCount.intValue() > 0){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
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
	public Boolean isAnchorDateExistsForStudy(Integer studyId,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - isAnchorDateExistsForStudy() - starts");
		boolean isExists = false;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(customStudyId != null && StringUtils.isNotEmpty(customStudyId)){
				String searchQuery = "select count(q.use_anchor_date) from questions q,questionnaires_steps qsq,questionnaires qq  where q.id=qsq.instruction_form_id and qsq.step_type='Question' "
						+ "and qsq.active=1 and qsq.questionnaires_id=qq.id and qq.study_id in(select s.id from studies s where s.custom_study_id='"+customStudyId+"' and s.is_live=0) and qq.active=1 and q.use_anchor_date=1 and q.active=1;";
				BigInteger count = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
				if(count.intValue() > 0){
					isExists = true;
				}else{
					String subQuery = "select count(q.use_anchor_date) from questions q,form_mapping fm,form f,questionnaires_steps qsf,questionnaires qq where q.id=fm.question_id and f.form_id=fm.form_id and f.active=1 "
							+ "and f.form_id=qsf.instruction_form_id and qsf.step_type='Form' and qsf.questionnaires_id=qq.id and study_id in (select s.id from studies s where s.custom_study_id='"+customStudyId+"' and s.is_live=0) and q.use_anchor_date=1 and q.active=1";
					BigInteger subCount = (BigInteger) session.createSQLQuery(subQuery).uniqueResult();
					if(subCount != null && subCount.intValue() > 0){
						isExists = true;
					}
				}
			}else{
				String searchQuery = "select count(q.use_anchor_date) from questions q,questionnaires_steps qsq,questionnaires qq  where q.id=qsq.instruction_form_id and qsq.step_type='Question' "
						+ "and qsq.active=1 and qsq.questionnaires_id=qq.id and qq.study_id="+studyId+" and qq.active=1 and q.use_anchor_date=1 and q.active=1;";
				BigInteger count = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
				if(count.intValue() > 0){
					isExists = true;
				}else{
					String subQuery = "select count(q.use_anchor_date) from questions q,form_mapping fm,form f,questionnaires_steps qsf,questionnaires qq where q.id=fm.question_id and f.form_id=fm.form_id and f.active=1 "
							+ "and f.form_id=qsf.instruction_form_id and qsf.step_type='Form' and qsf.questionnaires_id=qq.id and study_id="+studyId+" and q.use_anchor_date=1 and q.active=1";
					BigInteger subCount = (BigInteger) session.createSQLQuery(subQuery).uniqueResult();
					if(subCount != null && subCount.intValue() > 0){
						isExists = true;
					}
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

	/**
	 * @author Ravinder
	 * @param Integer : StudyId
	 * @return String SUCCESS or FAILUE
	 * 
	 * This method is used to check the stastic short title unique
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String checkStatShortTitle(Integer studyId,String shortTitle,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireStepShortTitle() - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		List<QuestionsBo> questionsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(customStudyId != null && !customStudyId.isEmpty()){
				String serachQuery = "select count(*) from questions qbo,questionnaires_steps qsbo,questionnaires q where qbo.id=qsbo.instruction_form_id and qsbo.questionnaires_id=q.id and q.study_id in(select id From studies SBO WHERE custom_study_id='"+customStudyId+"') and qsbo.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and qbo.stat_short_name='"+shortTitle+"'";
				//query = session.createQuery("From QuestionsBo QBO where QBO.id IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId IN (select id from QuestionnaireBo Q where Q.studyId IN(select id From StudyBo SBO WHERE customStudyId='"+customStudyId+"') ) and QSBO.stepType='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and QSBO.active=1) and QBO.statShortName='"+shortTitle+"'");
				BigInteger count = (BigInteger) session.createSQLQuery(serachQuery).uniqueResult();
				if(count != null && count.intValue() > 0){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}else{
					String searchQuery = "select count(*) From questions QBO,form_mapping f,questionnaires_steps QSBO,questionnaires Q where QBO.id=f.question_id "
							+ "and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.study_id IN(select id From studies SBO WHERE custom_study_id='"+customStudyId+"') and QSBO.step_type='Form' and QBO.stat_short_name='"+shortTitle+"'";
					BigInteger subCount = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
					if(subCount != null && subCount.intValue() > 0){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}else{
						String taskQuery = "from ActiveTaskAtrributeValuesBo where activeTaskId in(select id from ActiveTaskBo where studyId IN(select id From StudyBo SBO WHERE customStudyId='"+customStudyId+"') ) and identifierNameStat='"+shortTitle+"'";
						List<ActiveTaskAtrributeValuesBo> activeTaskAtrributeValuesBos = session.createQuery(taskQuery).list();
						if(activeTaskAtrributeValuesBos != null && !activeTaskAtrributeValuesBos.isEmpty()){
							message = FdahpStudyDesignerConstants.SUCCESS;
						}
					}
				}
			}else{
				query = session.createQuery("From QuestionsBo QBO where QBO.id IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId IN (select id from QuestionnaireBo Q where Q.studyId="+studyId+") and QSBO.stepType='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and QSBO.active=1) and QBO.statShortName='"+shortTitle+"'");
				questionsBo =  query.list();
				if(questionsBo != null && !questionsBo.isEmpty()){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}else{
					//String searchQuuery = "From QuestionsBo QBO where QBO.id IN (select f.questionId from FormMappingBo f where f.formId in (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId IN (select id from QuestionnaireBo Q where Q.studyId IN(select id From StudyBo SBO WHERE customStudyId='"+customStudyId+"') ) and QSBO.stepType='"+FdahpStudyDesignerConstants.FORM_STEP+"')) and QBO.statShortName='"+shortTitle+"'";
					String searchQuuery = "select count(*) From questions QBO,form_mapping f,questionnaires_steps QSBO,questionnaires Q where QBO.id=f.question_id "
							+ "and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id=Q.id and Q.study_id="+studyId+" and QSBO.step_type='Form' and QBO.stat_short_name='"+shortTitle+"'";
					questionsBo = session.createQuery(searchQuuery).list();			
					if(questionsBo != null && !questionsBo.isEmpty()){
						message = FdahpStudyDesignerConstants.SUCCESS;
					}else{
						String taskQuery = "from ActiveTaskAtrributeValuesBo where activeTaskId in(select id from ActiveTaskBo where studyId="+studyId+") and identifierNameStat='"+shortTitle+"'";
						List<ActiveTaskAtrributeValuesBo> activeTaskAtrributeValuesBos = session.createQuery(taskQuery).list();
						if(activeTaskAtrributeValuesBos != null && !activeTaskAtrributeValuesBos.isEmpty()){
							message = FdahpStudyDesignerConstants.SUCCESS;
						}
					}
				}
			}
			
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - checkStatShortTitle() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - checkStatShortTitle() - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * @param Integer : studyId
	 * @return String SUCCESS or FAILURE
	 * 
	 * This method is used to validate the questionnaire have response type scale for android platform 
	 */
	@Override
	public String checkQuestionnaireResponseTypeValidation(Integer studyId, String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireResponseTypeValidation() - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		BigInteger questionCount = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = "select count(*) from questions QBO,questionnaires_steps QSBO,questionnaires Q where QBO.id=QSBO.instruction_form_id"
					+ " and QSBO.questionnaires_id=Q.id and Q.study_id="+studyId+" and Q.active=1 and QSBO.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"'"
					+ " and QSBO.active=1 and QBO.active=1 and QBO.response_type=3";
			BigInteger count = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
			if(count!=null && count.intValue() > 0){	
				message = FdahpStudyDesignerConstants.SUCCESS;
			}else{
				String searchQuuery = "select count(*) from questions q,form_mapping f,questionnaires_steps qs,questionnaires qq where q.id=f.question_id"
						+ " and f.form_id=qs.instruction_form_id and qs.questionnaires_id=qq.id and qq.study_id="+studyId+""
								+ " and qq.active=1 and qs.step_type='Form' and qs.active=1 and f.active=1 and q.response_type=3 and q.active=1";
				questionCount = (BigInteger) session.createSQLQuery(searchQuuery).uniqueResult();
				if(questionCount!=null && questionCount.intValue() > 0){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}	
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - checkQuestionnaireResponseTypeValidation() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - checkQuestionnaireResponseTypeValidation() - Ends");
		return message;
	}
	/**
	 * @author Ravinder
	 * @param Integer : questionnaireId
	 * @param String : frequency
	 * @param String Success or failre
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String validateLineChartSchedule(Integer questionnaireId,String frequency) {
		logger.info("StudyQuestionnaireDAOImpl - validateLineChartSchedule() - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		String timeRange="";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			timeRange = FdahpStudyDesignerUtil.getTimeRangeString(frequency);
			String searchQuery = "select count(*) from questions QBO,questionnaires_steps QSBO where QBO.id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireId+" and QSBO.active=1 and QSBO.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and QBO.active=1 and QBO.add_line_chart='Yes' and QBO.line_chart_timerange not in ('"+timeRange+"')";
			BigInteger count = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
			if(count != null && count.intValue() > 0){
				message = FdahpStudyDesignerConstants.SUCCESS;
			}else{
				String searchSubQuery = "select count(*) from questions QBO,form_mapping f,questionnaires_steps QSBO where QBO.id=f.question_id and f.form_id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireId+" and QSBO.active=1 and QSBO.step_type='"+FdahpStudyDesignerConstants.FORM_STEP+"' and QBO.active=1 and QBO.add_line_chart = 'Yes' and QBO.line_chart_timerange not in ('"+timeRange+"')";
				BigInteger subCount = (BigInteger) session.createSQLQuery(searchSubQuery).uniqueResult();
				if(subCount != null && subCount.intValue() > 0){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - validateLineChartSchedule() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - validateLineChartSchedule() - Ends");
		return message;
	}
	/**
	 * @author Ravinder
	 * @param questionnaireId
	 * @param frequency
	 * @return
	 */
	public String updateLineChartSchedule(Integer questionnaireId,String frequency,SessionObject sessionObject,Session session,Transaction transaction,String customStudyId) {
		logger.info("StudyQuestionnaireDAOImpl - updateLineChartSchedule() - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		String timeRange="";
		Session newSession = null;
		String activity = "";
		String activitydetails = "";
		try{
			if(session == null) {
				newSession = hibernateTemplate.getSessionFactory().openSession();
				transaction = newSession.beginTransaction();
			}
			timeRange = FdahpStudyDesignerUtil.getTimeRangeString(frequency);
			String searchQuery ="update questions QBO,questionnaires_steps QSBO set QBO.status=0,QBO.modified_by="+sessionObject.getUserId()+",QBO.modified_on='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QBO.id=QSBO.instruction_form_id and QSBO.questionnaires_id="+questionnaireId+" and QSBO.step_type='Question' and QSBO.active=1 and QBO.active=1 and QBO.add_line_chart='Yes' and QBO.line_chart_timerange not in ('"+timeRange+"')";
			if(newSession != null){
				newSession.createSQLQuery(searchQuery).executeUpdate();
			}
			else{
				session.createSQLQuery(searchQuery).executeUpdate();
			}
			String formQuery="update questionnaires_steps qs,form_mapping f, questions QBO  set qs.status=0,qs.modified_by="+sessionObject.getUserId()+",qs.modified_on='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"',QBO.status=0,QBO.modified_by="+sessionObject.getUserId()+",QBO.modified_on='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where qs.step_type = 'Form' and qs.instruction_form_id= f.form_id"
					+ " and f.question_id = QBO.id and f.active=1 and QBO.active=1 and QBO.add_line_chart='Yes' "
					+ "and QBO.line_chart_timerange not in ('"+timeRange+"') and qs.questionnaires_id="+questionnaireId+" and qs.active=1";
			if(newSession != null){
				newSession.createSQLQuery(formQuery).executeUpdate();
			}
			else{
				session.createSQLQuery(formQuery).executeUpdate();
			}
			activity = FdahpStudyDesignerConstants.FORMSTEP_ACTIVITY +" saved.";
			activitydetails = "Content saved for Form Step. (Study ID = "+customStudyId+")";
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - updateLineChartSchedule()");
			
			activity = FdahpStudyDesignerConstants.QUESTIONSTEP_ACTIVITY  +" saved.";
			activitydetails = "Content saved for Question Step. (Study ID = "+customStudyId+")";
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - updateLineChartSchedule()");
			
			activity = FdahpStudyDesignerConstants.FORMSTEP_QUESTION_ACTIVITY  +" saved.";
			activitydetails = "Content saved for Question of Form Step. (Study ID = "+customStudyId+")";
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - updateLineChartSchedule()");
			if (session == null)
				transaction.commit();
		}catch(Exception e){
			if(session == null && null != transaction){
				transaction.rollback();
			}
			logger.error("StudyQuestionnaireDAOImpl - updateLineChartSchedule() - ERROR " , e);
		}finally{
			if(null != newSession){
				newSession.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - updateLineChartSchedule() - Ends");
		return message;
	}
	/**
	 * @author Ravinder
	 * @param Integer : formId
	 * @param String : Success or failure
	 * This method is used to validate the stats information for repeatable form in questionnaire
	 * 
	 */
	@Override
	public String validateRepetableFormQuestionStats(Integer formId) {
		logger.info("StudyQuestionnaireDAOImpl - validateRepetableFormQuestionStats() - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			//String searchQuery ="select count(*) from questions q where q.id in (select f.question_id from form_mapping f where f.form_id="+formId+" and f.active=1) and (q.add_line_chart = 'Yes' or q.use_stastic_data='Yes' or q.use_anchor_date=true) and q.active=1";
			String searchQuery ="select count(*) from questions q,form_mapping f where q.id=f.question_id and q.active=1 and f.active=1 and f.form_id="+formId+" and (q.add_line_chart = 'Yes' or q.use_stastic_data='Yes' or q.use_anchor_date=true)";
			BigInteger questionCount = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
			if(questionCount!=null && questionCount.intValue() > 0){
				message = FdahpStudyDesignerConstants.SUCCESS;
			}
		}catch(Exception e){
			logger.error("StudyQuestionnaireDAOImpl - validateRepetableFormQuestionStats() - ERROR " , e);
		}finally{
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - validateRepetableFormQuestionStats() - Ends");
		return message;
	}
	
	public String deleteQuestuionnaireInfo(Integer studyId,Integer questionnaireId,String customStudyId,Session session,Transaction transaction) {
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestuionnaireInfo(session,transction) - starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		int count = 0;
		try{
				String deleteInsQuery = "delete InstructionsBo IBO where IBO.id IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.active=1 and QSBO.stepType='"+FdahpStudyDesignerConstants.INSTRUCTION_STEP+"')";
				query = session.createQuery(deleteInsQuery);
				query.executeUpdate();
				
				String deleteQuesQuery = "delete QuestionsBo QBO where QBO.id IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.active=1 and QSBO.stepType='"+FdahpStudyDesignerConstants.QUESTION_STEP+"')";
				query = session.createQuery(deleteQuesQuery);
				query.executeUpdate();
				
				String deleteResponse = "delete QuestionReponseTypeBo QRBO where QRBO.questionsResponseTypeId IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.active=1 and QSBO.stepType='"+FdahpStudyDesignerConstants.QUESTION_STEP+"')";
				query = session.createQuery(deleteResponse);
				query.executeUpdate();
				
				String deleteSubResponse = "delete QuestionResponseSubTypeBo QRSBO  where QRSBO.responseTypeId IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.active=1 and QSBO.stepType='"+FdahpStudyDesignerConstants.QUESTION_STEP+"')";
				query = session.createQuery(deleteSubResponse);
				query.executeUpdate();
				
				String subQuery = "select FMBO.questionId from FormMappingBo FMBO where FMBO.formId IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.active=1 and QSBO.stepType='"+FdahpStudyDesignerConstants.FORM_STEP+"')";
				query = session.createQuery(subQuery);
				if(query.list() != null && !query.list().isEmpty()){
					
					String deleteFormResponse = "delete QuestionReponseTypeBo QRBO where QRBO.questionsResponseTypeId IN ("+subQuery+")";
					query = session.createQuery(deleteFormResponse);
					query.executeUpdate();
					
					String deleteFormSubResponse = "delete QuestionResponseSubTypeBo QRSBO  where QRSBO.responseTypeId IN ("+subQuery+")";
					query = session.createQuery(deleteFormSubResponse);
					query.executeUpdate();
					
					String deleteFormQuery = "delete QuestionsBo QBO where QBO.id IN ("+subQuery+")";
					query = session.createQuery(deleteFormQuery);
					query.executeUpdate();
				}
				
				String formMappingDelete = "delete FormMappingBo FMBO where FMBO.formId IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.active=1 and QSBO.stepType='"+FdahpStudyDesignerConstants.FORM_STEP+"')";
				query = session.createQuery(formMappingDelete);
				query.executeUpdate();
				
				String formDelete = "delete FormBo FBO where FBO.formId IN (select QSBO.instructionFormId from QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId+" and QSBO.active=1 and QSBO.stepType='"+FdahpStudyDesignerConstants.FORM_STEP+"')";
				query = session.createQuery(formDelete);
				query.executeUpdate();
				
				String searchQuery = "delete QuestionnairesStepsBo QSBO where QSBO.questionnairesId="+questionnaireId;
				query = session.createQuery(searchQuery);
				query.executeUpdate();
				
				String deletecustomFreQuery = "delete from questionnaires_custom_frequencies where questionnaires_id="+questionnaireId;
				query = session.createSQLQuery(deletecustomFreQuery);
				query.executeUpdate();
				
				String deleteFreQuery = "delete from questionnaires_frequencies where questionnaires_id="+questionnaireId;
				query = session.createSQLQuery(deleteFreQuery);
				query.executeUpdate();
				
				String deleteQuery = "delete QuestionnaireBo QBO where QBO.studyId="+studyId+" and QBO.id="+questionnaireId;
				query = session.createQuery(deleteQuery);
				count = query.executeUpdate();
				
				if(count > 0){
					message = FdahpStudyDesignerConstants.SUCCESS;
				}
			
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - deleteQuestuionnaireInfo(session,transction) - ERROR " , e);
		}
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestuionnaireInfo(session,transction) - Ends");
		return message;
	}
	public String deleteQuestionnaireStep(Integer stepId,Integer questionnaireId,String stepType,String customStudyId,SessionObject sessionObject,Session session,Transaction transaction) {
		String message = FdahpStudyDesignerConstants.FAILURE;
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep(session,transction) - starts");
		String activitydetails = "";
		String activity = "";
		String searchQuery = null;
		QuestionnairesStepsBo questionnairesStepsBo = null;
		try{
			searchQuery = "From QuestionnairesStepsBo QSBO where QSBO.instructionFormId="+stepId+" and QSBO.questionnairesId="+questionnaireId+" and QSBO.stepType='"+stepType+"'";
			questionnairesStepsBo = (QuestionnairesStepsBo) session.createQuery(searchQuery).uniqueResult();
			if(questionnairesStepsBo != null){
				String updateQuery = "update QuestionnairesStepsBo QSBO set QSBO.sequenceNo=QSBO.sequenceNo-1,QSBO.modifiedBy="+sessionObject.getUserId()+",QSBO.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where QSBO.questionnairesId="+questionnairesStepsBo.getQuestionnairesId()+" and QSBO.active=1 and QSBO.sequenceNo >="+questionnairesStepsBo.getSequenceNo();
				query = session.createQuery(updateQuery);
				query.executeUpdate();
				
				if(stepType.equalsIgnoreCase(FdahpStudyDesignerConstants.INSTRUCTION_STEP)){
					String deleteQuery = "delete InstructionsBo IBO where IBO.id="+stepId;
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
					activity = FdahpStudyDesignerConstants.INSTRUCTION_ACTIVITY+ " was deleted.";;
					activitydetails = "Instruction Step was deleted.(Study ID ="+customStudyId+")";
					auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - deleteQuestionnaireStep");
					
				}else if(stepType.equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTION_STEP)){
					String deleteQuery = "delete QuestionsBo QBO where QBO.id="+stepId;
					query = session.createQuery(deleteQuery);
					query.executeUpdate();
					activity = FdahpStudyDesignerConstants.QUESTIONSTEP_ACTIVITY + " was deleted.";
					activitydetails = "Question Step was deleted.(Study ID ="+customStudyId+")";
					auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - deleteQuestionnaireStep");
					
					String deleteResponse = "delete QuestionReponseTypeBo QRBO where QRBO.questionsResponseTypeId="+stepId;
					query = session.createQuery(deleteResponse);
					query.executeUpdate();
					
					String deleteSubResponse = "delete QuestionResponseSubTypeBo QRSBO  where QRSBO.responseTypeId="+stepId;
					query = session.createQuery(deleteSubResponse);
					query.executeUpdate();
					
					
				}else if(stepType.equalsIgnoreCase(FdahpStudyDesignerConstants.FORM_STEP)){
					String subQuery = "select FMBO.questionId from FormMappingBo FMBO where FMBO.formId="+stepId;
					query = session.createQuery(subQuery);
					if(query.list() != null && !query.list().isEmpty()){
						String deleteQuery = "delete QuestionsBo QBO where QBO.id IN ("+subQuery+")";
						query = session.createQuery(deleteQuery);
						query.executeUpdate();
						
						String deleteResponse = "delete QuestionReponseTypeBo QRBO where QRBO.questionsResponseTypeId IN ("+subQuery+")";
						query = session.createQuery(deleteResponse);
						query.executeUpdate();
						
						String deleteSubResponse = "delete QuestionResponseSubTypeBo QRSBO  where QRSBO.responseTypeId IN ("+subQuery+")";
						query = session.createQuery(deleteSubResponse);
						query.executeUpdate();
					}
					
					String formMappingDelete = "delete FormMappingBo FMBO where FMBO.formId="+stepId;
					query = session.createQuery(formMappingDelete);
					query.executeUpdate();
					
					String formDelete = "delete FormBo FBO where FBO.formId="+stepId;
					query = session.createQuery(formDelete);
					query.executeUpdate();
					activity = FdahpStudyDesignerConstants.FORMSTEP_ACTIVITY+ " was deleted.";
					activitydetails = "Form Step was deleted.(Study ID ="+customStudyId+")";
					auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyQuestionnaireDAOImpl - deleteQuestionnaireStep");
				}
				session.delete(questionnairesStepsBo);
				message = FdahpStudyDesignerConstants.SUCCESS;
			}
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep(session,transction) - ERROR " , e);
		}
		logger.info("StudyQuestionnaireDAOImpl - deleteQuestionnaireStep(session,transction) - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * @return List of HealthKityKeyInfo
	 * 
	 * This method is used to get the Health Kit key master info
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<HealthKitKeysInfo> getHeanlthKitKeyInfoList() {
		logger.info("StudyQuestionnaireDAOImpl - getQuestionReponseTypeList() - Starts");
		Session session = null;
		List<HealthKitKeysInfo> healthKitKeysInfoList = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getHealthKitKeyInfo");
			healthKitKeysInfoList = query.list();
		} catch (Exception e) {
			logger.error(
					"StudyQuestionnaireDAOImpl - getQuestionReponseTypeList() - ERROR ", e);
		} finally {
			if(session != null){
				session.close();
			}
		}
		logger.info("StudyQuestionnaireDAOImpl - getQuestionReponseTypeList() - Ends");
		return healthKitKeysInfoList;
	}
}
