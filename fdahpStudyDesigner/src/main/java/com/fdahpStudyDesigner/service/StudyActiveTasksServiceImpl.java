/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.ActiveTaskListBo;
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
//		try{
//			if(null != activeTaskBo){
//				if(activeTaskBo.getId() != null){
//					addQuestionnaireBo = studyQuestionnaireDAO.getQuestionnaireById(activeTaskBo.getId());
//				}else{
//					addQuestionnaireBo = new QuestionnaireBo();
//				}
//				if(activeTaskBo.getStudyId() != null){
//					addQuestionnaireBo.setStudyId(activeTaskBo.getStudyId());
//				}
//				if(activeTaskBo.getStudyLifetimeStart() != null && !activeTaskBo.getStudyLifetimeStart().isEmpty()){
//					addQuestionnaireBo.setStudyLifetimeStart(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(activeTaskBo.getStudyLifetimeStart())));
//				}
//				if(activeTaskBo.getStudyLifetimeEnd()!= null && !activeTaskBo.getStudyLifetimeEnd().isEmpty()){
//					addQuestionnaireBo.setStudyLifetimeEnd(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(activeTaskBo.getStudyLifetimeEnd())));
//				}
//				if(activeTaskBo.getFrequency() != null){
//					addQuestionnaireBo.setFrequency(activeTaskBo.getFrequency());
//				}
//				if(activeTaskBo.getTitle() != null){
//					addQuestionnaireBo.setTitle(activeTaskBo.getTitle());
//				}
//				if(activeTaskBo.getCreatedDate() != null){
//					addQuestionnaireBo.setCreatedDate(activeTaskBo.getCreatedDate());
//				}
//				if(activeTaskBo.getCreatedBy() != null){
//					addQuestionnaireBo.setCreatedBy(activeTaskBo.getCreatedBy());
//				}
//				if(activeTaskBo.getModifiedDate() != null){
//					addQuestionnaireBo.setModifiedDate(activeTaskBo.getModifiedDate());
//				}
//				if(activeTaskBo.getModifiedBy() != null){
//					addQuestionnaireBo.setModifiedBy(activeTaskBo.getModifiedBy());
//				}
//				if(activeTaskBo.getRepeatQuestionnaire() != null){
//					addQuestionnaireBo.setRepeatQuestionnaire(activeTaskBo.getRepeatQuestionnaire());
//				}
//				if(activeTaskBo.getDayOfTheWeek() != null){
//					addQuestionnaireBo.setDayOfTheWeek(activeTaskBo.getDayOfTheWeek());
//				}
//				if(activeTaskBo.getType() != null){
//					addQuestionnaireBo.setType(activeTaskBo.getType());
//				}
//				if(!activeTaskBo.getFrequency().equalsIgnoreCase(activeTaskBo.getPreviousFrequency())){
//					addQuestionnaireBo.setQuestionnaireCustomScheduleBo(activeTaskBo.getQuestionnaireCustomScheduleBo());
//					addQuestionnaireBo.setQuestionnairesFrequenciesList(activeTaskBo.getQuestionnairesFrequenciesList());
//					addQuestionnaireBo.setQuestionnairesFrequenciesBo(activeTaskBo.getQuestionnairesFrequenciesBo());
//				}else{
//					if(activeTaskBo.getQuestionnaireCustomScheduleBo() != null && activeTaskBo.getQuestionnaireCustomScheduleBo().size() > 0){
//						addQuestionnaireBo.setQuestionnaireCustomScheduleBo(activeTaskBo.getQuestionnaireCustomScheduleBo());
//					}
//					if(activeTaskBo.getQuestionnairesFrequenciesList() != null && activeTaskBo.getQuestionnairesFrequenciesList().size() > 0){
//						addQuestionnaireBo.setQuestionnairesFrequenciesList(activeTaskBo.getQuestionnairesFrequenciesList());
//					}
//					if(activeTaskBo.getQuestionnairesFrequenciesBo()!= null){
//						addQuestionnaireBo.setQuestionnairesFrequenciesBo(activeTaskBo.getQuestionnairesFrequenciesBo());
//					}
//				}
//				if(activeTaskBo.getPreviousFrequency() != null){
//					addQuestionnaireBo.setPreviousFrequency(activeTaskBo.getPreviousFrequency());
//				}
//				addQuestionnaireBo = studyQuestionnaireDAO.saveORUpdateQuestionnaire(addQuestionnaireBo);
//			}
//		}catch(Exception e){
//			logger.error("StudyQuestionnaireServiceImpl - saveORUpdateQuestionnaire - Error",e);
//		}
//		logger.info("StudyQuestionnaireServiceImpl - saveORUpdateQuestionnaire - Ends");
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
		try{
			if(activeTaskBo != null){
				if(activeTaskBo.getId() != null){
					updateActiveTaskBo = studyActiveTasksDAO.getActiveTaskById(activeTaskBo.getId());
					updateActiveTaskBo.setModifiedBy(sessionObject.getUserId());
					updateActiveTaskBo.setModifiedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
				}else{
					updateActiveTaskBo = new ActiveTaskBo();
					updateActiveTaskBo.setCreatedBy(sessionObject.getUserId());
					updateActiveTaskBo.setCreatedDate(fdahpStudyDesignerUtil.getCurrentDateTime());
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
}
