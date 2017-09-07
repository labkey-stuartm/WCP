/**
 * 
 */
package com.fdahpstudydesigner.dao;

import java.util.List;

import com.fdahpstudydesigner.bean.ActiveStatisticsBean;
import com.fdahpstudydesigner.bo.ActiveTaskBo;
import com.fdahpstudydesigner.bo.ActiveTaskListBo;
import com.fdahpstudydesigner.bo.ActiveTaskMasterAttributeBo;
import com.fdahpstudydesigner.bo.ActivetaskFormulaBo;
import com.fdahpstudydesigner.bo.StatisticImageListBo;
import com.fdahpstudydesigner.util.SessionObject;

/**
 * @author Vivek
 * 
 */
public interface StudyActiveTasksDAO {

	public List<ActiveTaskBo> getStudyActiveTasksByStudyId(String studyId,Boolean isLive);
	public ActiveTaskBo getActiveTaskById(Integer activeTaskId, String customStudyId);
	public ActiveTaskBo saveOrUpdateActiveTaskInfo(ActiveTaskBo activeTaskBo, SessionObject sesObj,String customStudyId);
	public String deleteActiveTask(ActiveTaskBo activeTaskBo, SessionObject sesObj,String customStudyId);
	
	public ActiveTaskBo saveOrUpdateActiveTask(ActiveTaskBo addActiveTaskeBo, String customStudyId);
	public List<ActiveTaskListBo> getAllActiveTaskTypes(String platformType);
	public List<ActiveTaskMasterAttributeBo> getActiveTaskMasterAttributesByType(String activeTaskType);
	public List<StatisticImageListBo> getStatisticImages();
	public List<ActivetaskFormulaBo> getActivetaskFormulas();
	public boolean validateActiveTaskAttrById(Integer studyId, String activeTaskName, String activeTaskAttIdVal, String activeTaskAttIdName, String customStudyId);
	public List<ActiveStatisticsBean> validateActiveTaskStatIds(String customStudyId, List<ActiveStatisticsBean> activeStatisticsBeans);
}
