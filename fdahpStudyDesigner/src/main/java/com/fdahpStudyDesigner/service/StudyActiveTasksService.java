/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.ActiveTaskListBo;
import com.fdahpStudyDesigner.bo.ActiveTaskMasterAttributeBo;
import com.fdahpStudyDesigner.bo.ActivetaskFormulaBo;
import com.fdahpStudyDesigner.bo.StatisticImageListBo;
import com.fdahpStudyDesigner.util.SessionObject;

/**
 * @author Vivek
 *
 */
public interface StudyActiveTasksService {

	public List<ActiveTaskBo> getStudyActiveTasksByStudyId(String studyId);
	
	public ActiveTaskBo saveOrUpdateActiveTask(ActiveTaskBo activeTaskBo);
	public ActiveTaskBo getActiveTaskById(Integer activeTaskId);
	public ActiveTaskBo saveOrUpdateActiveTask(ActiveTaskBo activeTaskBo,SessionObject sessionObject);
	public String deleteActiveTask(Integer activeTaskInfoId,Integer studyId);
	public List<ActiveTaskListBo> getAllActiveTaskTypes();
	public List<ActiveTaskMasterAttributeBo> getActiveTaskMasterAttributesByType(String activeTaskType);
	public List<StatisticImageListBo> getStatisticImages();
	public List<ActivetaskFormulaBo> getActivetaskFormulas();
	public boolean validateActiveTaskAttrById(Integer studyId, String activeTaskName, String activeTaskAttIdVal, String activeTaskAttIdName) throws Exception;
}
