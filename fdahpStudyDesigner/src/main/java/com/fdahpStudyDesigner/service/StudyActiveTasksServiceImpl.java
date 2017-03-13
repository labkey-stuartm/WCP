/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.dao.StudyActiveTasksDAO;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

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
	/**
	 * @author Ronalin
	 * @param ActiveTaskBo
	 * @return ActiveTaskBo
	 * 
	 * This method is used to save the activeTask
	 */
	@Override
	public ActiveTaskBo saveOrUpdateActiveTask(ActiveTaskBo activeTaskBo,SessionObject sessionObject) {
		logger.info("StudyActiveTasksServiceImpl - saveOrUpdateActiveTask() - Starts");
		ActiveTaskBo updateActiveTaskBo = null;
		try{
			if(activeTaskBo != null){
				if(activeTaskBo.getId() != null){
					updateActiveTaskBo = studyActiveTasksDAO.getActiveTaskById(activeTaskBo.getId());
					updateActiveTaskBo.setModifiedBy(sessionObject.getUserId());
					updateActiveTaskBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
				}else{
					updateActiveTaskBo = new ActiveTaskBo();
					updateActiveTaskBo.setCreatedBy(sessionObject.getUserId());
					updateActiveTaskBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
				}
				updateActiveTaskBo = studyActiveTasksDAO.saveOrUpdateActiveTaskInfo(updateActiveTaskBo);
			}
			
		}catch(Exception e){
			logger.error("StudyActiveTasksServiceImpl - saveOrUpdateActiveTask() - Error",e);
		}
		logger.info("StudyActiveTasksServiceImpl - saveOrUpdateActiveTask() - Ends");
		return updateActiveTaskBo;
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
		logger.info("StudyActiveTasksServiceImpl - getActiveTaskById() - Starts");
		ActiveTaskBo activeTask = null;
		try {
			activeTask = studyActiveTasksDAO.getActiveTaskById(ativeTaskId);
		} catch (Exception e) {
			logger.error("StudyActiveTasksServiceImpl - getActiveTaskById() - ERROR ", e);
		}
		logger.info("StudyActiveTasksServiceImpl - getActiveTaskById() - Ends");
		return activeTask;
	}
	
	/**
	 * @author Ronalin
	 * @param Integer : consentInfoId
	 * @return String :SUCCESS or FAILURE
	 *  TThis method used to get the delete the consent information
	 */
	@Override
	public String deleteActiveTask(Integer activeTaskInfoId,Integer studyId) {
		logger.info("StudyServiceImpl - deleteActiveTask() - Starts");
		String message = null;
		try{
			message = studyActiveTasksDAO.deleteActiveTAsk(activeTaskInfoId, studyId);
		}catch(Exception e){
			logger.error("StudyServiceImpl - deleteActiveTask() - Error",e);
		}
		logger.info("StudyServiceImpl - deleteActiveTask() - Ends");
		return message;
	}
}
