/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.ActiveTaskAtrributeValuesBo;
import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.ActiveTaskListBo;
import com.fdahpStudyDesigner.bo.ActiveTaskMasterAttributeBo;
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
	
	@Override
	public ActiveTaskBo saveOrUpdateActiveTask(ActiveTaskBo activeTaskBo) {
		logger.info("StudyQuestionnaireServiceImpl - saveORUpdateQuestionnaire - Starts");
		ActiveTaskBo addActiveTaskeBo = null;
		try{
			if(null != activeTaskBo){
				if(activeTaskBo.getId() != null){
					addActiveTaskeBo = studyActiveTasksDAO.getActiveTaskById(activeTaskBo.getId());
				}else{
					addActiveTaskeBo = new ActiveTaskBo();
				}
				if(activeTaskBo.getStudyId() != null){
					addActiveTaskeBo.setStudyId(activeTaskBo.getStudyId());
				}
				if(activeTaskBo.getActiveTaskLifetimeStart() != null && !activeTaskBo.getActiveTaskLifetimeStart().isEmpty()){
					addActiveTaskeBo.setActiveTaskLifetimeStart(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(activeTaskBo.getActiveTaskLifetimeStart())));
				}
				if(activeTaskBo.getActiveTaskLifetimeEnd()!= null && !activeTaskBo.getActiveTaskLifetimeEnd().isEmpty()){
					addActiveTaskeBo.setActiveTaskLifetimeEnd(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(activeTaskBo.getActiveTaskLifetimeEnd())));
				}
				if(activeTaskBo.getFrequency() != null){
					addActiveTaskeBo.setFrequency(activeTaskBo.getFrequency());
				}
				if(activeTaskBo.getTitle() != null){
					addActiveTaskeBo.setTitle(activeTaskBo.getTitle());
				}
				if(activeTaskBo.getCreatedDate() != null){
					addActiveTaskeBo.setCreatedDate(activeTaskBo.getCreatedDate());
				}
				if(activeTaskBo.getCreatedBy() != null){
					addActiveTaskeBo.setCreatedBy(activeTaskBo.getCreatedBy());
				}
				if(activeTaskBo.getModifiedDate() != null){
					addActiveTaskeBo.setModifiedDate(activeTaskBo.getModifiedDate());
				}
				if(activeTaskBo.getModifiedBy() != null){
					addActiveTaskeBo.setModifiedBy(activeTaskBo.getModifiedBy());
				}
				if(activeTaskBo.getRepeatActiveTask() != null){
					addActiveTaskeBo.setRepeatActiveTask(activeTaskBo.getRepeatActiveTask());
				}
				if(activeTaskBo.getDayOfTheWeek() != null){
					addActiveTaskeBo.setDayOfTheWeek(activeTaskBo.getDayOfTheWeek());
				}
				if(activeTaskBo.getType() != null){
					addActiveTaskeBo.setType(activeTaskBo.getType());
				}
				if(!activeTaskBo.getFrequency().equalsIgnoreCase(activeTaskBo.getPreviousFrequency())){
					addActiveTaskeBo.setActiveTaskCustomScheduleBo(activeTaskBo.getActiveTaskCustomScheduleBo());
					addActiveTaskeBo.setActiveTaskFrequenciesList(activeTaskBo.getActiveTaskFrequenciesList());
					addActiveTaskeBo.setActiveTaskFrequenciesBo(activeTaskBo.getActiveTaskFrequenciesBo());
				}else{
					if(activeTaskBo.getActiveTaskCustomScheduleBo() != null && activeTaskBo.getActiveTaskCustomScheduleBo().size() > 0){
						addActiveTaskeBo.setActiveTaskCustomScheduleBo(activeTaskBo.getActiveTaskCustomScheduleBo());
					}
					if(activeTaskBo.getActiveTaskFrequenciesList() != null && activeTaskBo.getActiveTaskFrequenciesList().size() > 0){
						addActiveTaskeBo.setActiveTaskFrequenciesList(activeTaskBo.getActiveTaskFrequenciesList());
					}
					if(activeTaskBo.getActiveTaskFrequenciesBo()!= null){
						addActiveTaskeBo.setActiveTaskFrequenciesBo(activeTaskBo.getActiveTaskFrequenciesBo());
					}
				}
				if(activeTaskBo.getPreviousFrequency() != null){
					addActiveTaskeBo.setPreviousFrequency(activeTaskBo.getPreviousFrequency());
				}
				addActiveTaskeBo = studyActiveTasksDAO.saveOrUpdateActiveTask(addActiveTaskeBo);
			}
		}catch(Exception e){
			logger.error("StudyActiveTaskServiceImpl - saveORUpdateQuestionnaire - Error",e);
		}
		logger.info("StudyQuestionnaireServiceImpl - saveORUpdateQuestionnaire - Ends");
		return addActiveTaskeBo;
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
		List<ActiveTaskAtrributeValuesBo> updatedTaskAttributeValueBos = new ArrayList<ActiveTaskAtrributeValuesBo>();
		try{
			if(activeTaskBo != null){
				if(activeTaskBo.getId() != null){
					updateActiveTaskBo = studyActiveTasksDAO.getActiveTaskById(activeTaskBo.getId());
					updateActiveTaskBo.setModifiedBy(sessionObject.getUserId());
					updateActiveTaskBo.setModifiedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
				}else{
					updateActiveTaskBo = new ActiveTaskBo();
					updateActiveTaskBo.setStudyId(activeTaskBo.getStudyId());
					updateActiveTaskBo.setTaskTypeId(activeTaskBo.getTaskTypeId());
					updateActiveTaskBo.setCreatedBy(sessionObject.getUserId());
					updateActiveTaskBo.setCreatedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
					updateActiveTaskBo.setDisplayName(StringUtils.isEmpty(activeTaskBo.getDisplayName())?"":activeTaskBo.getDisplayName());
					updateActiveTaskBo.setShortTitle(StringUtils.isEmpty(activeTaskBo.getShortTitle())?"":activeTaskBo.getShortTitle());
					updateActiveTaskBo.setInstruction(StringUtils.isEmpty(activeTaskBo.getInstruction())?"":activeTaskBo.getInstruction());
					updateActiveTaskBo.setTaskAttributeValueBos(activeTaskBo.getTaskAttributeValueBos());
				}
				updateActiveTaskBo.setStudyId(activeTaskBo.getStudyId());
				updateActiveTaskBo.setTaskTypeId(activeTaskBo.getTaskTypeId());
				updateActiveTaskBo.setDisplayName(StringUtils.isEmpty(activeTaskBo.getDisplayName())?"":activeTaskBo.getDisplayName());
				updateActiveTaskBo.setShortTitle(StringUtils.isEmpty(activeTaskBo.getShortTitle())?"":activeTaskBo.getShortTitle());
				updateActiveTaskBo.setInstruction(StringUtils.isEmpty(activeTaskBo.getInstruction())?"":activeTaskBo.getInstruction());
				updateActiveTaskBo.setTaskAttributeValueBos(activeTaskBo.getTaskAttributeValueBos());
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
	
	/**
	 * @author Ronalin
	 * @return List :ActiveTaskListBos
	 *  This method used to get all type of activeTask
	 */
	@Override
	public List<ActiveTaskListBo> getAllActiveTaskTypes(){
		logger.info("StudyActiveTasksServiceImpl - getAllActiveTaskTypes() - Starts");
		List<ActiveTaskListBo> activeTaskListBos = new ArrayList<ActiveTaskListBo>();
		try {
			activeTaskListBos = studyActiveTasksDAO.getAllActiveTaskTypes();
		} catch (Exception e) {
			logger.error("StudyActiveTasksServiceImpl - getAllActiveTaskTypes() - ERROR ", e);
		}
		logger.info("StudyActiveTasksServiceImpl - getAllActiveTaskTypes() - Ends");
		return activeTaskListBos;
	}
	
	/**
	 * @author Ronalin
	 * @return List :ActiveTaskMasterAttributeBo
	 *  This method used to get  all the field names based on of activeTaskType
	 */
	@Override
	public List<ActiveTaskMasterAttributeBo> getActiveTaskMasterAttributesByType(String activeTaskType) {
		logger.info("StudyActiveTasksServiceImpl - getActiveTaskMasterAttributesByType() - Starts");
		List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<ActiveTaskMasterAttributeBo>();
		try {
			taskMasterAttributeBos = studyActiveTasksDAO.getActiveTaskMasterAttributesByType(activeTaskType);
		} catch (Exception e) {
			logger.error("StudyActiveTasksServiceImpl - getActiveTaskMasterAttributesByType() - ERROR ", e);
		}
		logger.info("StudyActiveTasksServiceImpl - getActiveTaskMasterAttributesByType() - Ends");
		return taskMasterAttributeBos;
	}
}
