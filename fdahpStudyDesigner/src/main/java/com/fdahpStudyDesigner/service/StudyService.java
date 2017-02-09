package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bo.StudyBo;

public interface StudyService {

	public List<StudyBo> getStudyList(Integer userId) throws Exception;
}
