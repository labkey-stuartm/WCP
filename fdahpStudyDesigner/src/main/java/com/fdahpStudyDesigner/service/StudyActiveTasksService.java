/**
 * 
 */
package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;

/**
 * @author Vivek
 *
 */
public interface StudyActiveTasksService {

	public List<ActiveTaskBo> getStudyActiveTasksByStudyId(String studyId);
}
