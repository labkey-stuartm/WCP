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

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.ConsentInfoBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudySequenceBo;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

/**
 * @author Vivek
 *
 */
@Repository
public class StudyActiveTasksDAOImpl implements StudyActiveTasksDAO{

	private static Logger logger = Logger.getLogger(StudyActiveTasksDAOImpl.class.getName());
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	String queryString = "";
	
	public StudyActiveTasksDAOImpl() {
	}
	
	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	/**
	 * return  active tasks based on user's Study Id
	 * @author Vivek
	 * 
	 * @param studyId , studyId of the {@link StudyBo}
	 * @return List of {@link ActiveTaskBo}
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveTaskBo> getStudyActiveTasksByStudyId(String studyId) {
		logger.info("StudyActiveTasksDAOImpl - getStudyActiveTasksByStudyId() - Starts");
		Session session = null;
		List<ActiveTaskBo> activeTasks = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if (StringUtils.isNotEmpty(studyId)) {
				query = session.getNamedQuery("ActiveTaskBo.getActiveTasksByByStudyId").setInteger("studyId", Integer.parseInt(studyId));
				activeTasks = query.list();
			}
		} catch (Exception e) {
			logger.error(
					"StudyActiveTasksDAOImpl - getStudyActiveTasksByStudyId() - ERROR ", e);
		} finally {
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - getStudyActiveTasksByStudyId() - Ends");
		return activeTasks;
	}

	/**
	 * @author Ronalin
	 * @param Integer :aciveTaskId
	 * @return Object :ActiveTaskBo
	 * 
	 * This method is used to get the ativeTask info object based on consent info id 
	 */
	@Override
	public ActiveTaskBo getActiveTaskById(Integer ativeTaskId) {
		logger.info("StudyActiveTasksDAOImpl - getActiveTaskById() - Starts");
		ActiveTaskBo activeTaskBo = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			activeTaskBo = (ActiveTaskBo)session.get(ActiveTaskBo.class, ativeTaskId);
		}catch(Exception e){
			logger.error("StudyActiveTasksDAOImpl - getActiveTaskById() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - getActiveTaskById() - Ends");
		return activeTaskBo;
	}

	/**
	 * @author Ronalin
	 * Add/Update the ActiveTaskBo
	 * @param StudyBo , {@link ActiveTaskBo}
	 * @return {@link String}
	 */
	@Override
	public ActiveTaskBo saveOrUpdateActiveTaskInfo(ActiveTaskBo activeTaskBo) {
		logger.info("StudyActiveTasksDAOImpl - saveOrUpdateActiveTaskInfo() - Starts");
		Session session = null;
		StudySequenceBo studySequence = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(activeTaskBo.getId() == null){
				studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", activeTaskBo.getStudyId()).uniqueResult();
				if(studySequence != null){
					studySequence.setStudyExcActiveTask(true);
				}else{
					studySequence = new StudySequenceBo();
					studySequence.setStudyExcActiveTask(true);
					studySequence.setStudyId(activeTaskBo.getStudyId());
				}
				session.saveOrUpdate(studySequence);
			}
			session.saveOrUpdate(activeTaskBo);
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyActiveTasksDAOImpl - saveOrUpdateActiveTaskInfo() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - saveOrUpdateActiveTaskInfo() - Ends");
		return activeTaskBo;
	}

	/**
	 * @author Ronalin
	 * @param Integer : activeTaskInfoId
	 * @return String :SUCCESS or FAILURE
	 *  This method used to get the delete the activeTask information
	 */
	@Override
	public String deleteActiveTAsk(Integer activeTaskInfoId,Integer studyId) {
		logger.info("StudyActiveTasksDAOImpl - deleteActiveTAsk() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			String deleteQuery = "delete ActiveTaskBo where id="+activeTaskInfoId+" and studyId="+studyId;
			query = session.createQuery(deleteQuery);
			count = query.executeUpdate();
			if(count > 0){
				message = fdahpStudyDesignerConstants.SUCCESS;
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyActiveTasksDAOImpl - deleteActiveTAsk() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - deleteActiveTAsk() - Ends");
		return message;
	}
}
