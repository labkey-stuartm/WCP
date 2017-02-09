package com.fdahpStudyDesigner.dao;

/**
 * 
 * @author Ronalin
 *
 */
import java.util.List;

import com.fdahpStudyDesigner.bo.StudyBo;

public interface StudyDAO {

	public List<StudyBo> getStudyList(Integer userId) throws Exception;
}
