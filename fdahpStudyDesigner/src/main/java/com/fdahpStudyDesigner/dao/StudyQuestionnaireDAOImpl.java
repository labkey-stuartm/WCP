/**
 * 
 */
package com.fdahpStudyDesigner.dao;

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

import com.fdahpStudyDesigner.bo.InstructionsBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.QuestionnairesStepsBo;
import com.fdahpStudyDesigner.bo.StudyBo;
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
		logger.info("StudyDAOImpl - getStudyQuestionnairesByStudyId() - Starts");
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
					"StudyDAOImpl - getStudyQuestionnairesByStudyId() - ERROR ", e);
		} finally {
			session.close();
		}
		logger.info("StudyDAOImpl - getStudyQuestionnairesByStudyId() - Ends");
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
		logger.info("StudyDAOImpl - getInstructionsBo() - Starts");
		Session session = null;
		InstructionsBo instructionsBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			instructionsBo = (InstructionsBo) session.get(InstructionsBo.class, instructionId);
		}catch (Exception e) {
			logger.error("StudyDAOImpl - getInstructionsBo() - ERROR ", e);
		} finally {
			session.close();
		}
		logger.info("StudyDAOImpl - getInstructionsBo - Ends");
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
		logger.info("StudyDAOImpl - getInstructionsBo() - Starts");
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
			logger.info("StudyDAOImpl - getInstructionsBo() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getInstructionsBo() - Starts");
		return instructionsBo;
	}
}
