package com.fdahpStudyDesigner.service;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudyPageBo;
import com.fdahpStudyDesigner.dao.StudyDAO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

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



	/**
	 * return reference List based on category
	 * @author Ronalin
	 * 
	 * @return the reference table List
	 * @exception Exception
	 */
	@Override
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory() {
		logger.info("StudyServiceImpl - getreferenceListByCategory() - Starts");
		HashMap<String, List<ReferenceTablesBo>> referenceMap = null;
		try {
			referenceMap  = studyDAO.getreferenceListByCategory();
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyList() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getreferenceListByCategory() - Ends");
		return referenceMap;
	}




	/**
	 * return Study based on id
	 * @author Ronalin
	 * 
	 * @return StudyBo
	 * @exception Exception
	 */
	@Override
	public StudyBo getStudyById(String studyId) {
		logger.info("StudyServiceImpl - getStudyById() - Starts");
		StudyBo studyBo = null;
		try {
			studyBo  = studyDAO.getStudyById(studyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudyById() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getStudyById() - Ends");
		return studyBo;
	}

	/**
	 * @author Ronalin
	 * Add/Update the Study
	 * @param StudyBo , {@link StudyBo}
	 * @return {@link String}
	 */
	@Override
	public String saveOrUpdateStudy(StudyBo studyBo) throws Exception {
		logger.info("StudyServiceImpl - saveOrUpdateStudy() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try {
			if(StringUtils.isNotEmpty(studyBo.getType())){
				if(studyBo.getType().equalsIgnoreCase(fdahpStudyDesignerConstants.STUDY_TYPE_GT)){
					studyBo.setType(fdahpStudyDesignerConstants.STUDY_TYPE_GT);
				}else if(studyBo.getType().equalsIgnoreCase(fdahpStudyDesignerConstants.STUDY_TYPE_SD)){
					studyBo.setType(fdahpStudyDesignerConstants.STUDY_TYPE_SD);
				}
			}
			if(studyBo.getStudyPermissions()!=null && studyBo.getStudyPermissions().size()>0){
			}
			message = studyDAO.saveOrUpdateStudy(studyBo);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - saveOrUpdateStudy() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - saveOrUpdateStudy() - Ends");
		return message;
	}




	/**
	 * return false or true of deleting record of studyPermission based on studyId and userId
	 * @author Ronalin
	 * 
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean deleteStudyPermissionById(Integer userId, String studyId) {
		logger.info("StudyServiceImpl - deleteStudyPermissionById() - Starts");
		boolean delFlag = false;
		try {
			delFlag = studyDAO.deleteStudyPermissionById(userId, studyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - deleteStudyPermissionById() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - deleteStudyPermissionById() - Ends");
		return delFlag;
	}




	/**
	 * return false or true of adding record of studyPermission based on studyId and userId
	 * @author Ronalin
	 * 
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds) throws Exception {
		logger.info("StudyServiceImpl - addStudyPermissionByuserIds() - Starts");
		boolean delFlag = false;
		try {
			delFlag = studyDAO.addStudyPermissionByuserIds(userId, studyId, userIds);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - addStudyPermissionByuserIds() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - addStudyPermissionByuserIds() - Ends");
		return delFlag;
	}

	 /**
		 * return study overview pageList based on studyId 
		 * @author Ronalin
		 * 
		 * @param studyId of the StudyBo
		 * @return the Study list
		 * @exception Exception
	*/
	@Override
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId) throws Exception {
		logger.info("StudyServiceImpl - getOverviewStudyPagesById() - Starts");
		List<StudyPageBo> studyPageBos = null;
		try {
			 studyPageBos = studyDAO.getOverviewStudyPagesById(studyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getOverviewStudyPagesById() - ERROR " , e);
		}
		return studyPageBos;
	}

	/**
	 * @author Ronalin
	 * delete the Study Overview Page By Page Id
	 * @param studyId ,pageId
	 * @return {@link String}
	 */
	@Override
	public String deleteOverviewStudyPageById(String studyId, String pageId) throws Exception {
		logger.info("StudyServiceImpl - deleteOverviewStudyPageById() - Starts");
		String message = "";
		try {
			message = studyDAO.deleteOverviewStudyPageById(studyId, pageId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - deleteOverviewStudyPageById() - ERROR " , e);
		}
		return message;
	}




	/**
	 * @author Ronalin
	 * save the Study Overview Page By PageId
	 * @param studyId
	 * @return {@link Integer}
	 */
	@Override
	public Integer saveOverviewStudyPageById(String studyId) throws Exception {
		logger.info("StudyServiceImpl - saveOverviewStudyPageById() - Starts");
		Integer pageId = 0;
		try {
			pageId = studyDAO.saveOverviewStudyPageById(studyId);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - saveOverviewStudyPageById() - ERROR " , e);
		}
		return pageId;
	}




	/**
	 * @author Ronalin
	 * Add/Update the Study Overview Pages
	 * @param studyId ,pageIds,titles,descs,files {@link StudyBo}
	 * @return {@link String}
	 */
	@Override
	public String saveOrUpdateOverviewStudyPages(String studyId, String pageIds, String titles, String descs,
			List<MultipartFile> files) {
		logger.info("StudyServiceImpl - saveOrUpdateOverviewStudyPages() - Starts");
		String message = "";
		try {
			message = studyDAO.saveOrUpdateOverviewStudyPages(studyId, pageIds, titles, descs, files);
		} catch (Exception e) {
			logger.error("StudyServiceImpl - saveOrUpdateOverviewStudyPages() - ERROR " , e);
		}
		return message;
	}
	
	/**
	 * return study list
	 * @author Pradyumn
	 * @return the study list
	 */
	@Override
	public List<StudyBo> getStudies(){
		logger.info("StudyServiceImpl - getStudies() - Starts");
		List<StudyBo> studyBOList = null;
		try {
			studyBOList  = studyDAO.getStudies();
		} catch (Exception e) {
			logger.error("StudyServiceImpl - getStudies() - ERROR " , e);
		}
		logger.info("StudyServiceImpl - getStudies() - Ends");
		return studyBOList;
	}
}
