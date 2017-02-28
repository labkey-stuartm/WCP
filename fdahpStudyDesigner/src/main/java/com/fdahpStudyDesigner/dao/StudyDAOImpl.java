package com.fdahpStudyDesigner.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.ComprehensionTestQuestionBo;
import com.fdahpStudyDesigner.bo.ComprehensionTestResponseBo;
import com.fdahpStudyDesigner.bo.ConsentInfoBo;
import com.fdahpStudyDesigner.bo.EligibilityBo;
import com.fdahpStudyDesigner.bo.ReferenceTablesBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudyPageBo;
import com.fdahpStudyDesigner.bo.StudyPermissionBO;
import com.fdahpStudyDesigner.bo.StudySequenceBo;
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
	/************************************Added By Ronalin Start*************************************************/
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
		String name = "";
		List<ReferenceTablesBo> referenceTablesBos = null; 
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				query = session.createQuery("select new com.fdahpStudyDesigner.bean.StudyListBean(s.id,s.customStudyId,s.name,s.category,s.researchSponsor,p.projectLead,p.viewPermission,s.status)"
						+ " from StudyBo s,StudyPermissionBO p"
						+ " where s.id=p.studyId"
						/*+ " and p.delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE*/
						+ " and p.userId=:impValue"
						+ " order by s.id");
				query.setParameter("impValue", userId);
				StudyListBeans = query.list();
				if(StudyListBeans!=null && StudyListBeans.size()>0){
					for(StudyListBean bean:StudyListBeans){
							/*query = session.createSQLQuery("select CONCAT(u.first_name,' ',u.last_name) AS name" 
                                                           +" from users u where u.user_id in(select s.project_lead"
                                                           +" from study_permission s where s.study_id="+bean.getId()
                                                           +" and s.project_lead IS NOT NULL"
                                                           + " and s.delFlag IS NOT NULL ");
							name = (String) query.uniqueResult();*/
							if(StringUtils.isNotEmpty(name))
								bean.setProjectLeadName(name);
							if(StringUtils.isNotEmpty(bean.getCategory()) && StringUtils.isNotEmpty(bean.getResearchSponsor())){
								query = session.createQuery("from ReferenceTablesBo where id in("+bean.getCategory()+","+bean.getResearchSponsor()+")");
								referenceTablesBos =query.list();
								if(referenceTablesBos!=null && referenceTablesBos.size()>0){
									bean.setCategory(referenceTablesBos.get(0).getValue());
									bean.setResearchSponsor(referenceTablesBos.get(1).getValue());
								}
							}
					}
				}
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
		Integer projectLead = null;
		StudySequenceBo studySequenceBo = null;
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
				
				studySequenceBo = new StudySequenceBo();
				studySequenceBo.setBasicInfo(studyBo.getStudySequenceBo().isBasicInfo());
				session.save(studySequenceBo);
			}else{
				studyBo.setModifiedBy(studyBo.getUserId());
				studyBo.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
				session.update(studyBo);
				
				studyPermissionList = studyBo.getStudyPermissions();
				//Adding new study permissions to the user
				/*if(null != studyPermissionList && studyPermissionList.size() > 0){
					for(StudyListBean spBO:studyPermissionList){
						if(spBO.getProjectLead()!=null){
						    StudyPermissionBO bo = (StudyPermissionBO) session.createQuery("from StudyPermissionBO"
								               + " where studyId= "+spBO.getId()
								               +" and userId= "+userId+""
								               +" and delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE
								               +" and s.project_lead IS NOT NULL").uniqueResult();
							if(bo!=null){
								bo.setProjectLead(projectLead);
								session.update(bo);
							}
						}else{
							spBO.setProjectLead(projectLead);
						}
						query = session.createQuery(" UPDATE StudyPermissionBO SET viewPermission = "+spBO.isViewPermission()
						        +" and projectLead ='"+spBO.getProjectLead()+"'" 
								+" WHERE userId = "+userId+" and studyId="+spBO.getId()
								+" and delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE);
						query.executeUpdate();
					}
				}*/
				
				if(studyBo.getStudySequenceBo()!=null){
					studySequenceBo = studyBo.getStudySequenceBo();
					session.update(studySequenceBo);
				}
				
			}
			transaction.commit();
			message = fdahpStudyDesignerConstants.SUCCESS;
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateSubAdmin() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
			logger.info("StudyDAOImpl - saveOrUpdateSubAdmin() - Ends");
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
		List<ReferenceTablesBo> categoryList = new ArrayList<ReferenceTablesBo>();
		List<ReferenceTablesBo> researchSponserList = new ArrayList<ReferenceTablesBo>();
		List<ReferenceTablesBo> dataPartnerList = new ArrayList<ReferenceTablesBo>();
		HashMap<String, List<ReferenceTablesBo>> referenceMap = new HashMap<String, List<ReferenceTablesBo>>();
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
		StudySequenceBo studySequenceBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = (StudyBo) session.createQuery("from StudyBo where id="+studyId).uniqueResult();
				studySequenceBo = (StudySequenceBo) session.createQuery("from StudySequenceBo where studyId="+studyId).uniqueResult();
				if(studySequenceBo!=null)
					studyBo.setStudySequenceBo(studySequenceBo);
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
			logger.error("StudyDAOImpl - deleteStudyPermissionById() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - deleteStudyPermissionById() - Starts");
		return delFag;
	}
	
	/**
	 * return false or true of deleting record of studyPermission based on studyId and userId
	 * @author Ronalin
	 * 
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean addStudyPermissionByuserIds(Integer userId, String studyId, String userIds) {
		logger.info("StudyDAOImpl - addStudyPermissionByuserIds() - Starts");
		boolean delFag = false;
		Session session =null;
		int count = 0;
		String permUserIds[];
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			permUserIds = userIds.split(",");
			if(permUserIds!=null && permUserIds.length>0){
				for(String perUserId : permUserIds){
					StudyPermissionBO studyPermissionBO = (StudyPermissionBO) session.createQuery("from StudyPermissionBO "
							+ "where studyId="+studyId+ " and userId = "+perUserId
							+" and delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE).uniqueResult();
					if(studyPermissionBO == null){
						studyPermissionBO = new StudyPermissionBO();
						studyPermissionBO.setDelFlag(fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE);
						studyPermissionBO.setStudyId(Integer.valueOf(studyId));
						studyPermissionBO.setUserId(Integer.valueOf(perUserId));
						session.save(studyPermissionBO);
						count = 1;
					}
				}
			}
			if(count > 0){
				delFag = fdahpStudyDesignerConstants.STATUS_ACTIVE;
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - addStudyPermissionByuserIds() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - deleteStudyPermissionById() - Starts");
		return delFag;
	}


	 /**
		 * return study overview pageList based on studyId 
		 * @author Ronalin
		 * 
		 * @param studyId of the StudyBo
		 * @return the Study page  list
		 * @exception Exception
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId)
			throws Exception {
		logger.info("StudyDAOImpl - getOverviewStudyPagesById() - Starts");
		Session session = null;
		List<StudyPageBo> StudyPageBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(studyId)){
				query = session.createQuery("from StudyPageBo where studyId="+studyId);
				StudyPageBo = query.list();
			}
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getOverviewStudyPagesById() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getOverviewStudyPagesById() - Ends");
		return StudyPageBo;
	}

	
	/**
	 * @author Ronalin
	 * Add/Update the Study Overview Pages
	 * @param studyId ,pageIds,titles,descs,files {@link StudyBo}
	 * @return {@link String}
	 */
	@Override
	public String saveOrUpdateOverviewStudyPages(String studyId,String pageIds, String titles, String descs,List<MultipartFile> files) {
		logger.info("StudyDAOImpl - saveOrUpdateOverviewStudyPages() - Starts");
		Session session = null;
		StudyPageBo studyPageBo = null;
		String pageIdArray[], titleArray[], descArray[], fileArray[];
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotEmpty(studyId)){
				pageIdArray = pageIds.split(",");
				titleArray = titles.split(",");
				descArray = descs.split(",");
				
				// fileArray based on pageId will save/update into particular location
				
				if(pageIdArray!=null && pageIdArray.length>0){
					for(int i=0;i<pageIdArray.length;i++){
						studyPageBo = (StudyPageBo) session.createQuery("from StudyPageBo where pageId="+pageIdArray[i]).uniqueResult();
						studyPageBo.setTitle(titleArray[i]);
						studyPageBo.setDescription(descArray[i]);
						//studyPageBo.setImagePath(files); we have look into the image after getting html
						session.update(studyPageBo);
					}
					message = fdahpStudyDesignerConstants.SUCCESS;
				}
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateOverviewStudyPages() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - saveOrUpdateOverviewStudyPages() - Ends");
		return message;
	}

	/**
	 * @author Ronalin
	 * delete the Study Overview Page By Page Id
	 * @param studyId ,pageId
	 * @return {@link String}
	 */
	@Override
	public String deleteOverviewStudyPageById(String studyId, String pageId) {
		logger.info("StudyDAOImpl - deleteOverviewStudyPageById() - Starts");
		Session session = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		int count= 0; 
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotEmpty(studyId) && StringUtils.isNotEmpty(pageId)){
					query = session.createQuery("delete from StudyPageBo where studyId="+studyId+" and pageId="+pageId);
					count = query.executeUpdate();
					if(count>0)
						message = fdahpStudyDesignerConstants.SUCCESS;
			
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteOverviewStudyPageById() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - deleteOverviewStudyPageById() - Ends");
		return message;
	}

	/**
	 * @author Ronalin
	 * save the Study Overview Page By PageId
	 * @param studyId
	 * @return {@link Integer}
	 */
	public Integer saveOverviewStudyPageById(String studyId) throws Exception {
		String message = fdahpStudyDesignerConstants.FAILURE;
		Integer pageId= 0; 
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotEmpty(studyId)){
				StudyPageBo studyPageBo = new StudyPageBo();
				studyPageBo.setStudyId(Integer.parseInt(studyId));
				pageId = (Integer) session.save(studyPageBo);
			
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteOverviewStudyPageById() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - deleteOverviewStudyPageById() - Ends");
		
		return pageId;
	}
	
	/**
	 * return false or true of validating study Custom id
	 * @author Ronalin
	 * 
	 * @return boolean
	 * @exception Exception
	 */
	@Override
	public boolean validateStudyId(String customStudyId) {
		logger.info("StudyDAOImpl - validateStudyId() - Starts");
		boolean flag = false;
		Session session =null;
		StudyBo studyBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			studyBo = (StudyBo) session.createQuery("from StudyBo where customStudyId='"+customStudyId+"'").uniqueResult();
			if(studyBo!=null)
				flag = true;
		}catch(Exception e){
			logger.error("StudyDAOImpl - validateStudyId() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - validateStudyId() - Starts");
		return flag;
	}
	/************************************Added By Ronalin End*************************************************/


	/**
	 * @author Ravinder
	 * @param Integer : studyId
	 * @return List :ConsentInfoList
	 *  This method used to get the consent info list of an study
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentInfoBo> getConsentInfoList(Integer studyId) {
		logger.info("StudyDAOImpl - getConsentInfoList() - Starts");
		List<ConsentInfoBo> consentInfoList = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = "From ConsentInfoBo CIB where CIB.studyId="+studyId+" order by CIB.sequenceNo asc";
			query = session.createQuery(searchQuery);
			consentInfoList = query.list();
			System.out.println("consentInfoList:"+consentInfoList.size());
		}catch(Exception e){
			logger.error("StudyDAOImpl - getConsentInfoList() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getConsentInfoList() - Ends");
		return consentInfoList;
	}

	/**
	 * @author Ravinder
	 * @param Integer : consentInfoId
	 * @return String :SUCCESS or FAILURE
	 *  This method used to get the delete the consent information
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId) {
		logger.info("StudyDAOImpl - deleteConsentInfo() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			List<ConsentInfoBo> consentInfoList = null;
			String searchQuery = "From ConsentInfoBo CIB where CIB.studyId="+studyId+" order by CIB.sequenceNo asc";
			consentInfoList = session.createQuery(searchQuery).list();
			if(consentInfoList != null && consentInfoList.size() > 0){
				boolean isValue=false;
				for(ConsentInfoBo consentInfoBo : consentInfoList){
					if(consentInfoBo.getId().equals(consentInfoId)){
						isValue=true;
					}
					if(isValue && !consentInfoBo.getId().equals(consentInfoId)){
						consentInfoBo.setSequenceNo(consentInfoBo.getSequenceNo()-1);
						session.update(consentInfoBo);
					}
				}
			}
			String deleteQuery = "delete ConsentInfoBo CIB where CIB.id="+consentInfoId;
			query = session.createQuery(deleteQuery);
			count = query.executeUpdate();
			if(count > 0){
				message = fdahpStudyDesignerConstants.SUCCESS;
				transaction.commit();
			}
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteConsentInfo() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - deleteConsentInfo() - Ends");
		return message;
	}

	/**
	 * @author Ravinder
	 * @param Integer studyId
	 * @param int oldOrderNumber
	 * @param int newOrderNumber
	 * @return String SUCCESS or FAILURE
	 * 
	 * This method is used to update the order of an consent info
	 */
	@Override
	public String reOrderConsentInfoList(Integer studyId, int oldOrderNumber,int newOrderNumber) {
		logger.info("StudyDAOImpl - reOrderConsentInfoList() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Query query = null;
		int count = 0;
		ConsentInfoBo consentInfoBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String updateQuery ="";
			query = session.createQuery("From ConsentInfoBo CIB where CIB.studyId="+studyId+" and CIB.sequenceNo ="+oldOrderNumber);
			consentInfoBo = (ConsentInfoBo)query.uniqueResult();
			if(consentInfoBo != null){
				if (oldOrderNumber < newOrderNumber) {
					updateQuery = "update ConsentInfoBo CIBO set CIBO.order=CIBO.order-1 where CIBO.studyId="+studyId+" and CIBO.sequenceNo <="+newOrderNumber+" and CIBO.sequenceNo >"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update ConsentInfoBo C set C.sequenceNo="+ newOrderNumber+" where C.id="+consentInfoBo.getId());
						count = query.executeUpdate();
						message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update ConsentInfoBo CIBO set CIBO.sequenceNo=CIBO.sequenceNo+1 where CIBO.studyId="+studyId+" and CIBO.sequenceNo >="+newOrderNumber+" and CIBO.sequenceNo <"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update ConsentInfoBo C set C.sequenceNo="+ newOrderNumber+" where C.id="+consentInfoBo.getId());
						count = query.executeUpdate();
						message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}
				transaction.commit();
			}
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - reOrderConsentInfoList() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - reOrderConsentInfoList() - Ends");
		return message;
	}

	@Override
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo) {
		logger.info("StudyDAOImpl - saveOrUpdateConsentInfo() - Starts");
		Session session = null;
		StudySequenceBo studySequence = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(consentInfoBo.getId() == null){
				studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", consentInfoBo.getStudyId()).uniqueResult();
				if(studySequence != null){
					studySequence.setConsentEduInfo(true);
				}else{
					studySequence = new StudySequenceBo();
					studySequence.setConsentEduInfo(true);
					studySequence.setStudyId(consentInfoBo.getStudyId());
					
				}
				session.saveOrUpdate(studySequence);
			}
			session.saveOrUpdate(consentInfoBo);
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateConsentInfo() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - saveOrUpdateConsentInfo() - Ends");
		return consentInfoBo;
	}

	/**
	 * @author Ravinder
	 * @param Integer :ConsentInfoId
	 * @return Object :ConsentInfoBo
	 * 
	 * This method is used to get the consent info object based on consent info id 
	 */
	@Override
	public ConsentInfoBo getConsentInfoById(Integer consentInfoId) {
		logger.info("StudyDAOImpl - reOrderConsentInfoList() - Starts");
		ConsentInfoBo consentInfoBo = null;
		Session session = null;
		//Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			//String searchQuery = "From ConsentInfoBo CIB where CIB.id="+consentInfoId;
			consentInfoBo = (ConsentInfoBo) session.get(ConsentInfoBo.class, consentInfoId);
			//query = session.createQuery(searchQuery);
			//consentInfoBo = (ConsentInfoBo) query.uniqueResult();
		}catch(Exception e){
			logger.error("StudyDAOImpl - reOrderConsentInfoList() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - reOrderConsentInfoList() - Ends");
		return consentInfoBo;
	}

	/**
	 * @author Ravinder
	 * @param studyId
	 * @return int count
	 * 
	 * This method is used to get the last order of an consent info of an study
	 */
	@Override
	public int consentInfoOrder(Integer studyId) {
		logger.info("StudyDAOImpl - consentInfoOrder() - Starts");
		Session session = null;
		Integer count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createSQLQuery("select sequence_no from consent_info where study_id="+studyId+" order by sequence_no desc LIMIT 1");
			count = ((Integer) query.uniqueResult());
		}catch(Exception e){
			logger.error("StudyDAOImpl - consentInfoOrder() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - consentInfoOrder() - Ends");
		return count;
	}
	/**
	 * @author Ravinder
	 * @param Integer : studyId
	 * @return List : ComprehensionTestQuestions
	 * 
	 * This method is used to get the ComprehensionTest Questions
	 */ 
	@SuppressWarnings("unchecked")
	@Override
	public List<ComprehensionTestQuestionBo> getComprehensionTestQuestionList(Integer studyId) {
		logger.info("StudyDAOImpl - getComprehensionTestQuestionList() - Starts");
		Session session = null;
		List<ComprehensionTestQuestionBo> comprehensionTestQuestionList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery("From ComprehensionTestQuestionBo CTQBO where CTQBO.studyId="+studyId+" order by CTQBO.sequenceNo asc");
			comprehensionTestQuestionList = query.list();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getComprehensionTestQuestionList() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getComprehensionTestQuestionList() - Ends");
		return comprehensionTestQuestionList;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer :QuestionId
	 * @return Object : ComprehensionTestQuestionBo
	 * 
	 * This method is used to get the ComprehensionTestQuestion of an study
	 */
	@Override
	public ComprehensionTestQuestionBo getComprehensionTestQuestionById(Integer questionId) {
		logger.info("StudyDAOImpl - getComprehensionTestQuestionById() - Starts");
		ComprehensionTestQuestionBo comprehensionTestQuestionBo = null;
		Session session = null;
		//Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			//String searchQuery = "From ComprehensionTestQuestionBo CTQBO where CTQBO.id="+questionId;
			comprehensionTestQuestionBo = (ComprehensionTestQuestionBo) session.get(ComprehensionTestQuestionBo.class, questionId);
			//query = session.createQuery(searchQuery);
			//comprehensionTestQuestionBo = (ComprehensionTestQuestionBo) query.uniqueResult();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getComprehensionTestQuestionById() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getComprehensionTestQuestionById() - Ends");
		return comprehensionTestQuestionBo;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer  :questionId
	 * @return String : SUCCESS or FAILURE
	 * 
	 * This method is used to delete the Comprehension Test Question in a study
	 * 
	 */
	@Override
	public String deleteComprehensionTestQuestion(Integer questionId) {
		logger.info("StudyDAOImpl - deleteComprehensionTestQuestion() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.getTransaction();
			String deleteQuery = "delete ComprehensionTestQuestionBo CTQBO where CTQBO.id="+questionId;
			query = session.createQuery(deleteQuery);
			count = query.executeUpdate();
			if(count > 0){
				message = fdahpStudyDesignerConstants.SUCCESS;
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteComprehensionTestQuestion() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - deleteComprehensionTestQuestion() - Ends");
		return message;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer : comprehensionQuestionId
	 * @param List : ComprehensionTestResponseBo List
	 * 
	 * This method is used to get the ComprehensionTestQuestion response of an study
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ComprehensionTestResponseBo> getComprehensionTestResponseList(Integer comprehensionQuestionId) {
		logger.info("StudyDAOImpl - deleteComprehensionTestQuestion() - Starts");
		Session session = null;
		List<ComprehensionTestResponseBo> comprehensionTestResponseList =null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery("From ComprehensionTestResponseBo CTRBO where CTRBO.comprehensionTestQuestionId="+comprehensionQuestionId);
			comprehensionTestResponseList = query.list();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteComprehensionTestQuestion() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - deleteComprehensionTestQuestion() - Ends");
		return comprehensionTestResponseList;
	}
	
	/**
	 * @author Ravinder
	 * @param Object : ComprehensionTestQuestionBo
	 * @return Object  :ComprehensionTestQuestionBo
	 * 
	 * This method is used to add the ComprehensionTestQuestion to the study
	 */
	@Override
	public ComprehensionTestQuestionBo saveOrUpdateComprehensionTestQuestion(ComprehensionTestQuestionBo comprehensionTestQuestionBo) {
		logger.info("StudyDAOImpl - saveOrUpdateComprehensionTestQuestion() - Starts");
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(comprehensionTestQuestionBo);
			if(comprehensionTestQuestionBo != null && comprehensionTestQuestionBo.getId() != null){
				if(comprehensionTestQuestionBo.getResponseList() != null && comprehensionTestQuestionBo.getResponseList().size()  >0){
					for(ComprehensionTestResponseBo comprehensionTestResponseBo : comprehensionTestQuestionBo.getResponseList()){
						if(comprehensionTestResponseBo.getComprehensionTestQuestionId() != null){
							comprehensionTestResponseBo.setComprehensionTestQuestionId(comprehensionTestQuestionBo.getId());
						}
						session.saveOrUpdate(comprehensionTestResponseBo);
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateComprehensionTestQuestion() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - saveOrUpdateComprehensionTestQuestion() - Ends");
		return null;
	}
	
	/**
	 * @author Ravinder
	 * @param studyId
	 * @return int count
	 * 
	 * This method is used to get the last order of an comprehension Test Question of an study
	 */
	@Override
	public int comprehensionTestQuestionOrder(Integer studyId) {
		logger.info("StudyDAOImpl - comprehensionTestQuestionOrder() - Starts");
		Session session = null;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery("select CTRBO.sequenceNo From ComprehensionTestResponseBo CTRBO where CTRBO.studyId="+studyId+" and order by CTRBO.sequenceNo desc CTRBO.limit 1");
			count = (int) query.uniqueResult();
			count = count + 1;
		}catch(Exception e){
			logger.error("StudyDAOImpl - comprehensionTestQuestionOrder() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - comprehensionTestQuestionOrder() - Ends");
		return count;
	}
	
	/**
	 * @author Ravinder
	 * @param Integer studyId
	 * @param int oldOrderNumber
	 * @param int newOrderNumber
	 * @return String SUCCESS or FAILURE
	 * 
	 * This method is used to update the order of an Comprehension Test Question
	 */
	@Override
	public String reOrderComprehensionTestQuestion(Integer studyId,	int oldOrderNumber, int newOrderNumber) {
		logger.info("StudyDAOImpl - reOrderComprehensionTestQuestion() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Query query = null;
		int count = 0;
		ComprehensionTestQuestionBo comprehensionTestQuestionBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String updateQuery ="";
			query = session.createQuery("From ComprehensionTestQuestionBo CTB where CTB.studyId="+studyId+" and CTB.sequenceNo ="+oldOrderNumber);
			comprehensionTestQuestionBo = (ComprehensionTestQuestionBo)query.uniqueResult();
			if(comprehensionTestQuestionBo != null){
				if (oldOrderNumber < newOrderNumber) {
					updateQuery = "update ComprehensionTestQuestionBo CTB set CTB.sequenceNo=CTB.sequenceNo-1 where CTB.studyId="+studyId+" and CTB.sequenceNo <="+newOrderNumber+" and CTB.sequenceNo >"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update ComprehensionTestQuestionBo CTB set CTB.sequenceNo="+ newOrderNumber+" where CTB.id="+comprehensionTestQuestionBo.getId());
						count = query.executeUpdate();
						message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update ComprehensionTestQuestionBo CTB set CTB.order=CTB.order+1 where CTB.studyId="+studyId+" and CTB.sequenceNo >="+newOrderNumber+" and CTB.sequenceNo <"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update ComprehensionTestQuestionBo CTB set CTB.sequenceNo="+ newOrderNumber+" where CTB.id="+comprehensionTestQuestionBo.getId());
						count = query.executeUpdate();
						message = fdahpStudyDesignerConstants.SUCCESS;
					}
				}
				transaction.commit();
			}
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - reOrderComprehensionTestQuestion() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyDAOImpl - reOrderComprehensionTestQuestion() - Ends");
		return message;
	}
	
	/*------------------------------------Added By Vivek Start---------------------------------------------------*/
	
	/**
	 * return  eligibility based on user's Study Id
	 * @author Vivek
	 * 
	 * @param studyId, studyId of the {@link StudyBo}
	 * @return {@link EligibilityBo}
	 * @exception Exception
	 */
	@Override
	public EligibilityBo getStudyEligibiltyByStudyId(String studyId) {
		logger.info("StudyDAOImpl - getStudyEligibiltyByStudyId() - Starts");
		Session session = null;
		EligibilityBo eligibilityBo = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if (StringUtils.isNotEmpty(studyId)) {
				query = session.getNamedQuery("getEligibiltyByStudyId").setInteger("studyId", Integer.parseInt(studyId));
				eligibilityBo = (EligibilityBo) query.uniqueResult();
			}
		} catch (Exception e) {
			logger.error(
					"StudyDAOImpl - getStudyEligibiltyByStudyId() - ERROR ", e);
		} finally {
			session.close();
		}
		logger.info("StudyDAOImpl - getStudyEligibiltyByStudyId() - Ends");
		return eligibilityBo;
	}
	
	/**
	 * Save or update eligibility of study
	 * @author Vivek
	 * 
	 * @param eligibilityBo , {@link EligibilityBo}
	 * @return {@link String} , the status AcuityLinkConstants.SUCCESS or AcuityLinkConstants.FAILURE
	 * @exception Exception
	 */
	@Override
	public String saveOrUpdateStudyEligibilty(EligibilityBo eligibilityBo) {
		
		logger.info("StudyDAOImpl - saveOrUpdateStudyEligibilty() - Starts");
		String result = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Transaction tran = null;
		StudySequenceBo studySequence = null;
		EligibilityBo eligibilityBoUpdate = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			tran = session.beginTransaction();
			if(null != eligibilityBo){
				if(eligibilityBo.getId() != null){
					eligibilityBoUpdate = (EligibilityBo) session.getNamedQuery("getEligibiltyById").setInteger("id", eligibilityBo.getId()).uniqueResult();
					eligibilityBoUpdate.setEligibilityMechanism(eligibilityBo.getEligibilityMechanism());
					eligibilityBoUpdate.setInstructionalText(eligibilityBo.getInstructionalText());
				} else {
					eligibilityBoUpdate = eligibilityBo;
					studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", eligibilityBo.getStudyId()).uniqueResult();
					if(studySequence != null){
						studySequence.setEligibility(true);
						session.update(studySequence);
					}
				}
				session.saveOrUpdate(eligibilityBoUpdate);
				result = fdahpStudyDesignerConstants.SUCCESS;
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateStudyEligibilty() - ERROR ", e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - saveOrUpdateStudyEligibilty() - Ends");
		return result;
	}
	
	/*------------------------------------Added By Vivek End---------------------------------------------------*/
	/**
	 * return study list 
	 * @author Pradyumn
	 * @return study list
	 */
	@Override
	public List<StudyBo> getStudies(int userId){
		logger.info("StudyDAOImpl - getStudies() - Starts");
		Session session = null;
		List<StudyBo> studyBOList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
				query = session.createQuery(" FROM StudyBo SBO WHERE SBO.id NOT IN ( SELECT SPBO.studyId FROM StudyPermissionBO SPBO WHERE SPBO.userId = "+userId+")");
				studyBOList = query.list();
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getStudies() - ERROR " , e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - getStudies() - Ends");
		return studyBOList;
	}
	
	/**
	 * Save or update settings and admins of study
	 * @author Ronalin
	 * 
	 * @param studyBo , {@link studyBo}
	 * @return {@link String} , the status AcuityLinkConstants.SUCCESS or AcuityLinkConstants.FAILURE
	 * @exception Exception
	 */
	public String saveOrUpdateStudySettings(StudyBo studyBo) {
		logger.info("StudyDAOImpl - saveOrUpdateStudySettings() - Starts");
		String result = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Transaction tran = null;
		StudySequenceBo studySequence = null;
		StudyBo study = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			tran = session.beginTransaction();
			if(null != studyBo){
				if(studyBo.getId() != null){
					study = (StudyBo) session.createQuery("from StudyBo where id="+studyBo.getId()).uniqueResult();
					studySequence = (StudySequenceBo) session.createQuery("from StudySequenceBo where studyId="+studyBo.getId()).uniqueResult();
				    if(study!=null && studySequence!=null){
				    	study.setPlatform(studyBo.getPlatform());
				    	study.setAllowRejoin(studyBo.getAllowRejoin());
				    	study.setEnrollingParticipants(studyBo.getEnrollingParticipants());
				    	study.setRetainParticipant(studyBo.getRetainParticipant());
				    	study.setAllowRejoin(studyBo.getAllowRejoin());
				    	study.setAllowRejoinText(studyBo.getAllowRejoinText());
				    	session.saveOrUpdate(study);
				    	//setting true to setting admins
				    	studySequence.setSettingAdmins(true);
				    	session.saveOrUpdate(studySequence);
				    }
				} 
				result = fdahpStudyDesignerConstants.SUCCESS;
			}
			tran.commit();
		} catch (Exception e) {
			tran.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateStudySettings() - ERROR ", e);
		} finally{
			session.close();
		}
		logger.info("StudyDAOImpl - saveOrUpdateStudySettings() - Ends");
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId) throws Exception {
		logger.info("INFO: StudyDAOImpl - getConsentInfoDetailsListByStudyId() :: Starts");
		Session session = null;
		Query query = null;
		List<ConsentInfoBo> consentInfoBoList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery(" from ConsentInfoBo CIBO where CIBO.studyId="+studyId+" ORDER BY CIBO.sequenceNo ");
			consentInfoBoList = query.list();
			if( null != consentInfoBoList && consentInfoBoList.size() > 0){
				for(ConsentInfoBo consentInfoBo : consentInfoBoList){
					consentInfoBo.setElaborated(consentInfoBo.getElaborated().replace("'", "&#39;"));
					consentInfoBo.setElaborated(consentInfoBo.getElaborated().replace("\"", "'"));
					if( StringUtils.isNotEmpty(consentInfoBo.getConsentItemType()) && !consentInfoBo.getConsentItemType().equalsIgnoreCase(fdahpStudyDesignerConstants.CONSENT_TYPE_CUSTOM)){
						switch (consentInfoBo.getDisplayTitle()) {
						case "overview": consentInfoBo.setDisplayTitle("Overview");
										 break;
						case "dataGathering": consentInfoBo.setDisplayTitle("Data Gathering");
						 				 break;
						case "privacy": consentInfoBo.setDisplayTitle("Privacy");
						 				 break;
						case "dataUse": consentInfoBo.setDisplayTitle("Data Use");
						 				 break;
						case "timeCommitment": consentInfoBo.setDisplayTitle("Time Commitment");
						 				 break;
						case "studySurvey": consentInfoBo.setDisplayTitle("Study Survey");
						 				 break;
						case "studyTasks": consentInfoBo.setDisplayTitle("Study Tasks");
						 				 break;
						case "withdrawing": consentInfoBo.setDisplayTitle("Withdrawing");
						 				 break;
						case "customService": consentInfoBo.setDisplayTitle("Custom Service");
						 				 break;
						}
					}
				}
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - getConsentInfoDetailsListByStudyId() - ERROR", e);
		}finally{
			if( session != null){
				session.close();
			}
		}
		logger.info("INFO: StudyDAOImpl - getConsentInfoDetailsListByStudyId() :: Ends");
		return consentInfoBoList;
	}
	
}
