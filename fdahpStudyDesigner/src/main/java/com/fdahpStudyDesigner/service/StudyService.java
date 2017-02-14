package com.fdahpStudyDesigner.service;

import java.util.List;

import com.fdahpStudyDesigner.bean.StudyListBean;

public interface StudyService {

	public List<StudyListBean> getStudyList(Integer userId) throws Exception;
}
