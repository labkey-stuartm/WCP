package com.fdahpStudyDesigner.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudyPermissionBO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

/**
 * 
 * @author Ronalin
 *
 */
@Repository
public class StudyDAOImpl implements StudyDAO{

	private static Logger logger = Logger.getLogger(StudyDAOImpl.class.getName());
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	String queryString = "";
	public StudyDAOImpl() {
	}
	
	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

	/**
	 * return study List based on user 
	 * @author Ronalin
	 * 
	 * @param userId of the user
	 * @return the Study list
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyListBean> getStudyList(Integer userId) throws Exception {
		logger.info("StudyDAOImpl - getStudyList() - Starts");
		Session session = null;
		List<StudyListBean> StudyListBeans = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				query = session.createQuery("select new com.fdahpStudyDesigner.bean.StudyListBean(s.id,s.customStudyId,s.name,s.category,s.researchSponsor,p.projectLead,p.viewPermission)"
						+ " from StudyBo s,StudyPermissionBO p"
						+ " where s.id=p.studyId"
						+ " and p.delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE
						+ " and p.userId=:impValue");
				query.setParameter("impValue", userId);
				StudyListBeans = query.list();
			}
			
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getStudyList() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getStudyList() - Ends");
		return StudyListBeans;
	}
	
	/**
	 * @author Ronalin
	 * Add/Update the Study
	 * @param StudyBo , {@link StudyBo}
	 * @return {@link String}
	 */
	@Override
	public String saveOrUpdateStudy(StudyBo studyBo){
		logger.info("StudyDAOImpl - saveOrUpdateStudy() - Starts");
		Session session = null;
		String message = fdahpStudyDesignerConstants.SUCCESS;
		StudyPermissionBO studyPermissionBO = null;
		Integer studyId = null, userId = null;
		List<StudyListBean> studyPermissionList = null;
		try{
			userId = studyBo.getUserId();
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			if(studyBo.getId() == null){
				studyBo.setCreatedBy(studyBo.getUserId());
				studyBo.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
				studyId = (Integer) session.save(studyBo);
				
				studyPermissionBO = new StudyPermissionBO();
				studyPermissionBO.setUserId(userId);
				studyPermissionBO.setStudyId(studyId);
				studyPermissionBO.setDelFlag(fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE);
				session.save(studyPermissionBO);
			}else{
				studyBo.setModifiedBy(studyBo.getUserId());
				studyBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
				session.update(studyBo);
				
				studyPermissionList = studyBo.getStudyPermissions();
				//Adding new study permissions to the user
				if(null != studyPermissionList && studyPermissionList.size() > 0){
					for(StudyListBean spBO:studyPermissionList){
						query = session.createQuery(" UPDATE StudyPermissionBO SET viewPermission = "+spBO.isViewPermission()
						        +" and projectLead ='"+spBO.getProjectLead()+"'" 
								+" WHERE userId = "+userId+" and studyId="+spBO.getId()
								+" and delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE);
						query.executeUpdate();
					}
				}
				
				
			}
			transaction.commit();
			message = fdahpStudyDesignerConstants.SUCCESS;
		}catch(Exception e){
			transaction.rollback();
			logger.error("HIASPManageUsersDAOImpl - saveOrUpdateSubAdmin() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
			logger.info("HIASPManageUsersDAOImpl - saveOrUpdateSubAdmin() - Ends");
			return message;
	}

	/**
	 * return reference List based on category
	 * @author Ronalin
	 * 
	 * @return the reference List
	 * @exception Exception
	 */
	@SuppressWarnings({ "unchecked", "unused", "null" })
	@Override
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory() {
		logger.info("StudyDAOImpl - getreferenceListByCategory() - Starts");
		Session session = null;
		List<ReferenceTablesBo> allReferenceList = null;
		List<ReferenceTablesBo> categoryList = null;
		List<ReferenceTablesBo> researchSponserList = null;
		List<ReferenceTablesBo> dataPartnerList = null;
		HashMap<String, List<ReferenceTablesBo>> referenceMap = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query  = session.createQuery("from ReferenceTablesBo order by category asc,id asc");
			allReferenceList = query.list();
			if (allReferenceList != null && allReferenceList.size() > 0) {
				for (ReferenceTablesBo referenceTablesBo : allReferenceList) {
					if (StringUtils.isNotEmpty(referenceTablesBo.getCategory())) {
						switch (referenceTablesBo.getCategory()) {

						case fdahpStudyDesignerConstants.REFERENCE_TYPE_CATEGORIES:
							categoryList.add(referenceTablesBo);
							break;
						case fdahpStudyDesignerConstants.REFERENCE_TYPE_RESEARCH_SPONSORS:
							researchSponserList.add(referenceTablesBo);
							break;
						case fdahpStudyDesignerConstants.REFERENCE_TYPE_DATA_PARTNER:
							dataPartnerList.add(referenceTablesBo);
							break;

						default:
							break;
						}
					}
				}
				referenceMap = new HashMap<String, List<ReferenceTablesBo>>();
				if(categoryList!=null && categoryList.size()>0)
					referenceMap.put(fdahpStudyDesignerConstants.REFERENCE_TYPE_CATEGORIES, categoryList);
				if(researchSponserList!=null && researchSponserList.size()>0)
					referenceMap.put(fdahpStudyDesignerConstants.REFERENCE_TYPE_RESEARCH_SPONSORS, researchSponserList);
				if(dataPartnerList!=null && dataPartnerList.size()>0)
					referenceMap.put(fdahpStudyDesignerConstants.REFERENCE_TYPE_DATA_PARTNER, dataPartnerList);
			}
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getreferenceListByCategory() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getreferenceListByCategory() - Ends");
		return referenceMap;
	}

	/**
	 * return get study by Id
	 * @author Ronalin
	 * 
	 * @return the StudyBo
	 * @exception Exception
	 */
	@Override
	public StudyBo getStudyById(String studyId) {
		logger.info("StudyDAOImpl - getStudyById() - Starts");
		Session session = null;
		StudyBo studyBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = (StudyBo) session.createQuery("from StudyBo where id="+studyId).uniqueResult();
			}
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getStudyList() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getStudyById() - Ends");
		return studyBo;
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
		logger.info("StudyDAOImpl - deleteStudyPermissionById() - Starts");
		boolean delFag = false;
		Session session =null;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			
			query = session.createQuery(" UPDATE StudyPermissionBO SET delFlag = "+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_ACTIVE
					+" WHERE userId = "+userId+" and studyId="+studyId
					+" and delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE);
			count = query.executeUpdate();
			transaction.commit();
			if(count > 0){
				delFag = fdahpStudyDesignerConstants.STATUS_ACTIVE;
			}
		}catch(Exception e){
			transaction.rollback();
			logger.error("HIASPManageUsersDAOImpl - deleteStudyPermissionById() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - deleteStudyPermissionById() - Starts");
		return delFag;
	}
}
