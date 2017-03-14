/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;
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
}
