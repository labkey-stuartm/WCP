package com.fdahpStudyDesigner.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.dao.StudyDAO;

/**
 * 
 * @author Ronalin
 *
 */
@Service
public class StudyServiceImpl implements StudyService{

	private static Logger logger = Logger.getLogger(StudyServiceImpl.class);
	private StudyDAO studyDAO;

	/**
	 * Setting DI
	 * @param studyDAO
	 */
    @Autowired
	public void setStudyDAO(StudyDAO studyDAO) {
		this.studyDAO = studyDAO;
	}





    /**
	 * return study List based on user 
	 * @author Ronalin
	 * 
	 * @param userId of the user
	 * @return the Study list
	 * @exception Exception
	 */
	@Override
	public List<StudyListBean> getStudyList(Integer userId) throws Exception {
		logger.info("StudyServiceImpl - getStudyList() - Starts");
		List<StudyListBean> studyBos = null;
		try {
			if(userId!=null && userId!=0){
				studyBos  = studyDAO.getStudyList(userId);
			} 
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyList() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getStudyList() - Ends");
		return studyBos;
	}
	
	

	
}
