/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.QuestionnaireBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.dao.StudyActiveTasksDAO;

/**
 * @author Vivek
 *
 */
@Service
public class StudyActiveTasksServiceImpl implements StudyActiveTasksService{

	private static Logger logger = Logger.getLogger(StudyActiveTasksServiceImpl.class);
	
	@Autowired
	private StudyActiveTasksDAO studyActiveTasksDAO;

	/**
	 * return active tasks based on user's Study Id
	 * @author Vivek
	 * 
	 * @param studyId , studyId of the {@link StudyBo}
	 * @return List of {@link ActiveTaskBo}
	 * @exception Exception
	 */
	@Override
	public List<ActiveTaskBo> getStudyActiveTasksByStudyId(String studyId) {
		logger.info("StudyActiveTasksServiceImpl - getStudyActiveTasksByStudyId() - Starts");
		List<ActiveTaskBo> activeTasks = null;
		try {
			activeTasks = studyActiveTasksDAO.getStudyActiveTasksByStudyId(studyId);
		} catch (Exception e) {
			logger.error("StudyActiveTasksServiceImpl - getStudyActiveTasksByStudyId() - ERROR ", e);
		}
		logger.info("StudyActiveTasksServiceImpl - getStudyActiveTasksByStudyId() - Ends");
		return activeTasks;
	}
}
