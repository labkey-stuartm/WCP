/**
 * 
 */
package com.fdahpStudyDesigner.dao;

import java.util.List;

import com.fdahpStudyDesigner.bo.ActiveTaskBo;

/**
 * @author Vivek
 *
 */
public interface StudyActiveTasksDAO {

	public List<ActiveTaskBo> getStudyActiveTasksByStudyId(String studyId);
}
