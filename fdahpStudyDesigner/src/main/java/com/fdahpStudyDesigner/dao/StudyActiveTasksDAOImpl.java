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
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.StudyBo;
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
}
