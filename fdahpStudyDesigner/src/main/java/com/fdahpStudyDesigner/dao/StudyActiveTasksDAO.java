/**
 * 
 */
package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.ActiveTaskListBo;
import com.fdahpStudyDesigner.bo.ActiveTaskMasterAttributeBo;
import com.fdahpStudyDesigner.bo.StatisticImageListBo;

/**
 * @author Vivek
 *
 */
public interface StudyActiveTasksDAO {

	public List<ActiveTaskBo> getStudyActiveTasksByStudyId(String studyId);
	public ActiveTaskBo getActiveTaskById(Integer ativeTaskId);
	public ActiveTaskBo saveOrUpdateActiveTaskInfo(ActiveTaskBo activeTaskBo);
	public String deleteActiveTAsk(Integer activeTaskInfoId,Integer studyId);
	
	public ActiveTaskBo saveOrUpdateActiveTask(ActiveTaskBo addActiveTaskeBo);
	public List<ActiveTaskListBo> getAllActiveTaskTypes();
	public List<ActiveTaskMasterAttributeBo> getActiveTaskMasterAttributesByType(String activeTaskType);
	public List<StatisticImageListBo> getStatisticImages();
}
