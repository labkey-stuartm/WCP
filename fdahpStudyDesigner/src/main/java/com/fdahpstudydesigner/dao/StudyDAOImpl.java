package com.fdahpstudydesigner.dao;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fdahpstudydesigner.bean.DynamicBean;
import com.fdahpstudydesigner.bean.DynamicFrequencyBean;
import com.fdahpstudydesigner.bean.StudyIdBean;
import com.fdahpstudydesigner.bean.StudyListBean;
import com.fdahpstudydesigner.bean.StudyPageBean;
import com.fdahpstudydesigner.bo.ActiveTaskAtrributeValuesBo;
import com.fdahpstudydesigner.bo.ActiveTaskBo;
import com.fdahpstudydesigner.bo.ActiveTaskCustomScheduleBo;
import com.fdahpstudydesigner.bo.ActiveTaskFrequencyBo;
import com.fdahpstudydesigner.bo.Checklist;
import com.fdahpstudydesigner.bo.ComprehensionTestQuestionBo;
import com.fdahpstudydesigner.bo.ComprehensionTestResponseBo;
import com.fdahpstudydesigner.bo.ConsentBo;
import com.fdahpstudydesigner.bo.ConsentInfoBo;
import com.fdahpstudydesigner.bo.ConsentMasterInfoBo;
import com.fdahpstudydesigner.bo.EligibilityBo;
import com.fdahpstudydesigner.bo.FormBo;
import com.fdahpstudydesigner.bo.FormMappingBo;
import com.fdahpstudydesigner.bo.InstructionsBo;
import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.QuestionReponseTypeBo;
import com.fdahpstudydesigner.bo.QuestionResponseSubTypeBo;
import com.fdahpstudydesigner.bo.QuestionnaireBo;
import com.fdahpstudydesigner.bo.QuestionnaireCustomScheduleBo;
import com.fdahpstudydesigner.bo.QuestionnairesFrequenciesBo;
import com.fdahpstudydesigner.bo.QuestionnairesStepsBo;
import com.fdahpstudydesigner.bo.QuestionsBo;
import com.fdahpstudydesigner.bo.ReferenceTablesBo;
import com.fdahpstudydesigner.bo.ResourceBO;
import com.fdahpstudydesigner.bo.StudyBo;
import com.fdahpstudydesigner.bo.StudyPageBo;
import com.fdahpstudydesigner.bo.StudyPermissionBO;
import com.fdahpstudydesigner.bo.StudySequenceBo;
import com.fdahpstudydesigner.bo.StudyVersionBo;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

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
	@Autowired
	private AuditLogDAO auditLogDAO;
	
	public StudyDAOImpl() {
		// Unused
	}
	
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
	public List<StudyListBean> getStudyList(Integer userId) {
		logger.info("StudyDAOImpl - getStudyList() - Starts");
		Session session = null;
		List<StudyListBean> studyListBeans = null;
		String name = "";
		List<ReferenceTablesBo> referenceTablesBos = null;
		StudyBo liveStudy = null;
		StudyBo studyBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				query = session.createQuery("select new com.fdahpstudydesigner.bean.StudyListBean(s.id,s.customStudyId,s.name,s.category,s.researchSponsor,user.firstName, user.lastName,p.viewPermission,s.status,s.createdOn)"
						+ " from StudyBo s,StudyPermissionBO p, UserBO user"
						+ " where s.id=p.studyId"
						/*+ " and p.delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE*/
						+ " and user.userId = s.createdBy"
						+ " and s.version=0"
						+ " and p.userId=:impValue"
						+ " order by s.createdOn desc");
				query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, userId);
				studyListBeans = query.list();
				if(studyListBeans != null && !studyListBeans.isEmpty()){
					for(StudyListBean bean:studyListBeans){
							if(StringUtils.isNotEmpty(name))
								bean.setProjectLeadName(name);
							if(StringUtils.isNotEmpty(bean.getCategory()) && StringUtils.isNotEmpty(bean.getResearchSponsor())){
								query = session.createQuery("from ReferenceTablesBo where id in("+bean.getCategory()+","+bean.getResearchSponsor()+")");
								referenceTablesBos =query.list();
								if(referenceTablesBos!=null && !referenceTablesBos.isEmpty()){
									bean.setCategory(referenceTablesBos.get(0).getValue());
									bean.setResearchSponsor(referenceTablesBos.get(1).getValue());
								}
							}
							if(StringUtils.isNotEmpty(bean.getCustomStudyId())){
								liveStudy = (StudyBo) session.createQuery("from StudyBo where customStudyId='"+bean.getCustomStudyId()+"' and live=1").uniqueResult();
								if(liveStudy!=null){
									bean.setLiveStudyId(liveStudy.getId());
								}else{
									bean.setLiveStudyId(null);
								}
							}
							//if is there any change in study then edit with dot will come 
							if(bean.getId()!=null && bean.getLiveStudyId()!=null){
								studyBo = (StudyBo) session.createQuery("from StudyBo where id="+bean.getId()).uniqueResult();
								if(studyBo.getHasStudyDraft()==1)
									bean.setFlag(true);
							}
							/*studyBo.setHasActivityDraft(0);
							   studyBo.setHasConsentDraft(0);
							   studyBo.setHasStudyDraft(0);*/
							
					}
				}
			}
			
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getStudyList() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getStudyList() - Ends");
		return studyListBeans;
	}
	
	/**
	 * return study List based on user 
	 * @author Pradyumn
	 * 
	 * @param userId of the user
	 * @return the Study list
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyListBean> getStudyListByUserId(Integer userId) {
		logger.info("StudyDAOImpl - getStudyListByUserId() - Starts");
		Session session = null;
		List<StudyListBean> studyListBeans = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				query = session.createQuery("select new com.fdahpstudydesigner.bean.StudyListBean(s.id,s.customStudyId,s.name,p.viewPermission)"
						+ " from StudyBo s,StudyPermissionBO p"
						+ " where s.id=p.studyId"
						+ " and s.version = 0"
						+ " and p.userId=:impValue");
				query.setParameter("impValue", userId);
				studyListBeans = query.list();
			}
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getStudyListByUserId() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getStudyListByUserId() - Ends");
		return studyListBeans;
	}
	
	/**
	 * return all active study List based
	 * @author Pradyumn
	 * 
	 * @return the Study list
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyBo> getAllStudyList() {
		logger.info("StudyDAOImpl - getAllStudyList() - Starts");
		Session session = null;
		List<StudyBo> studyBOList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
				query = session.createQuery(" FROM StudyBo SBO WHERE SBO.version = 0 ");
				studyBOList = query.list();
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getAllStudyList() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getAllStudyList() - Ends");
		return studyBOList;
	}
	
	/**
	 * @author Ronalin
	 * Add/Update the Study
	 * @param StudyBo , {@link StudyBo}
	 * @return {@link String}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveOrUpdateStudy(StudyBo studyBo, SessionObject sessionObject){
		logger.info("StudyDAOImpl - saveOrUpdateStudy() - Starts");
		Session session = null;
		String message = FdahpStudyDesignerConstants.SUCCESS;
		StudyPermissionBO studyPermissionBO = null;
		Integer studyId = null, userId = null;
		StudySequenceBo studySequenceBo = null;
		StudyBo dbStudyBo = null;
		List<NotificationBO> notificationBO = null;
		String activitydetails = "";
		String activity = "";
		List<Integer> userSuperAdminList = null;
		try{
			userId = studyBo.getUserId();
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			
			if(studyBo.getId() == null){
				studyBo.setCreatedBy(studyBo.getUserId());
				studyBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
				studyId = (Integer) session.save(studyBo);
				
				studyPermissionBO = new StudyPermissionBO();
				studyPermissionBO.setUserId(userId);
				studyPermissionBO.setStudyId(studyId);
				studyPermissionBO.setViewPermission(true);
				session.save(studyPermissionBO);
				
				//give permission to all super admin Start
				query = session.createSQLQuery("Select upm.user_id from user_permission_mapping upm where upm.permission_id = "+FdahpStudyDesignerConstants.ROLE_SUPERADMIN);
				userSuperAdminList = query.list();
				if(userSuperAdminList!=null && !userSuperAdminList.isEmpty()){
					for(Integer superAdminId: userSuperAdminList){
						studyPermissionBO = new StudyPermissionBO();
						studyPermissionBO.setUserId(superAdminId);
						studyPermissionBO.setStudyId(studyId);
						studyPermissionBO.setViewPermission(true);
						session.save(studyPermissionBO);
					}
				}
				//give permission to all super admin End
				
				studySequenceBo = new StudySequenceBo();
				studySequenceBo.setStudyId(studyId);
				session.save(studySequenceBo);
			}else{
				dbStudyBo = (StudyBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_LIST_BY_ID).setInteger("id", studyBo.getId()).uniqueResult();
				if(dbStudyBo!=null){
					dbStudyBo.setCustomStudyId(studyBo.getCustomStudyId());
					dbStudyBo.setName(studyBo.getName());
					dbStudyBo.setFullName(studyBo.getFullName());
					dbStudyBo.setCategory(studyBo.getCategory());
					dbStudyBo.setResearchSponsor(studyBo.getResearchSponsor());
					dbStudyBo.setDataPartner(studyBo.getDataPartner());
					dbStudyBo.setTentativeDuration(studyBo.getTentativeDuration());
					dbStudyBo.setTentativeDurationWeekmonth(studyBo.getTentativeDurationWeekmonth());
					dbStudyBo.setDescription(studyBo.getDescription());
					dbStudyBo.setStudyTagLine(studyBo.getStudyTagLine());
					dbStudyBo.setStudyWebsite(studyBo.getStudyWebsite());
					dbStudyBo.setInboxEmailAddress(studyBo.getInboxEmailAddress());
					dbStudyBo.setType(studyBo.getType());
					dbStudyBo.setThumbnailImage(studyBo.getThumbnailImage());
					dbStudyBo.setModifiedBy(studyBo.getUserId());
					dbStudyBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
					session.update(dbStudyBo);
					
				}
				
				
				//studyPermissionList = studyBo.getStudyPermissions();
				//Adding new study permissions to the user
				/*if(null != studyPermissionList && studyPermissionList.size() > 0){
					for(StudyListBean spBO:studyPermissionList){
						if(spBO.getProjectLead()!=null){
						    StudyPermissionBO bo = (StudyPermissionBO) session.createQuery("from StudyPermissionBO"
								               + " where studyId= "+spBO.getId()
								               +" and userId= "+userId+""
								               +" and delFlag="+FdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE
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
								+" and delFlag="+FdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE);
						query.executeUpdate();
					}
				}*/
				
			}
			studySequenceBo = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId()).uniqueResult();
			if(studySequenceBo!=null){
				if(!studySequenceBo.isBasicInfo() && StringUtils.isNotEmpty(studyBo.getButtonText()) 
						&& studyBo.getButtonText().equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
						studySequenceBo.setBasicInfo(true);
						activity = "Study marked as completed";
						activitydetails = studyBo.getCustomStudyId()+" -- Study created and marked as completed";
				}else if(StringUtils.isNotEmpty(studyBo.getButtonText()) 
						&& studyBo.getButtonText().equalsIgnoreCase(FdahpStudyDesignerConstants.SAVE_BUTTON)){
					activity = "Study saved";
					activitydetails = studyBo.getCustomStudyId()+" -- Study saved but not marked as completed and not eligible to pushish / launch the study";
					studySequenceBo.setBasicInfo(false);
				}
				session.update(studySequenceBo);
			}
			message = auditLogDAO.updateDraftToEditedStatus(session, transaction, studyBo.getUserId(), FdahpStudyDesignerConstants.DRAFT_STUDY, studyBo.getId());
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, activity, activitydetails, "StudyDAOImpl - saveOrUpdateSubAdmin");
			
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateSubAdmin() - ERROR",e);
		}finally{
			if(null != session && session.isOpen()){
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
	@SuppressWarnings({ "unchecked"})
	@Override
	public HashMap<String, List<ReferenceTablesBo>> getreferenceListByCategory() {
		logger.info("StudyDAOImpl - getreferenceListByCategory() - Starts");
		Session session = null;
		List<ReferenceTablesBo> allReferenceList = null;
		List<ReferenceTablesBo> categoryList = new ArrayList<>();
		List<ReferenceTablesBo> researchSponserList = new ArrayList<>();
		List<ReferenceTablesBo> dataPartnerList = new ArrayList<>();
		HashMap<String, List<ReferenceTablesBo>> referenceMap = new HashMap<>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query  = session.createQuery("from ReferenceTablesBo order by category asc,id asc");
			allReferenceList = query.list();
			if (allReferenceList != null && !allReferenceList.isEmpty()) {
				for (ReferenceTablesBo referenceTablesBo : allReferenceList) {
					if (StringUtils.isNotEmpty(referenceTablesBo.getCategory())) {
						switch (referenceTablesBo.getCategory()) {

						case FdahpStudyDesignerConstants.REFERENCE_TYPE_CATEGORIES:
							categoryList.add(referenceTablesBo);
							break;
						case FdahpStudyDesignerConstants.REFERENCE_TYPE_RESEARCH_SPONSORS:
							researchSponserList.add(referenceTablesBo);
							break;
						case FdahpStudyDesignerConstants.REFERENCE_TYPE_DATA_PARTNER:
							dataPartnerList.add(referenceTablesBo);
							break;

						default:
							break;
						}
					}
				}
				referenceMap = new HashMap<>();
				if(!categoryList.isEmpty())
					referenceMap.put(FdahpStudyDesignerConstants.REFERENCE_TYPE_CATEGORIES, categoryList);
				if(!researchSponserList.isEmpty())
					referenceMap.put(FdahpStudyDesignerConstants.REFERENCE_TYPE_RESEARCH_SPONSORS, researchSponserList);
				if(!dataPartnerList.isEmpty())
					referenceMap.put(FdahpStudyDesignerConstants.REFERENCE_TYPE_DATA_PARTNER, dataPartnerList);
			}
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getreferenceListByCategory() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
	public StudyBo getStudyById(String studyId, Integer userId) {
		logger.info("StudyDAOImpl - getStudyById() - Starts");
		Session session = null;
		StudyBo studyBo = null;
		StudySequenceBo studySequenceBo = null;
		StudyPermissionBO permissionBO = null;
		StudyVersionBo studyVersionBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = (StudyBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_LIST_BY_ID).setInteger("id", Integer.parseInt(studyId)).uniqueResult();
				studySequenceBo = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, Integer.parseInt(studyId)).uniqueResult();
				permissionBO = (StudyPermissionBO) session.getNamedQuery("getStudyPermissionById")
						.setInteger(FdahpStudyDesignerConstants.STUDY_ID, Integer.parseInt(studyId))
						.setInteger("userId", userId)
						.uniqueResult();
				if(studySequenceBo!=null)
					studyBo.setStudySequenceBo(studySequenceBo);
				if(permissionBO!=null)
					studyBo.setViewPermission(permissionBO.isViewPermission());
				if(studyBo!=null){
					query = session.getNamedQuery("getStudyByCustomStudyId").setString("customStudyId", studyBo.getCustomStudyId());
					query.setMaxResults(1);
					studyVersionBo = (StudyVersionBo)query.uniqueResult();
					if(studyVersionBo!=null){
						studyVersionBo.setStudyLVersion(" V"+studyVersionBo.getStudyVersion());
						studyVersionBo.setConsentLVersion(" (V"+studyVersionBo.getConsentVersion()+")");
						studyVersionBo.setActivityLVersion(" (V"+studyVersionBo.getActivityVersion()+")");
						studyBo.setStudyVersionBo(studyVersionBo);
					}
				}
					
			}
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getStudyList() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
			
			
			query = session.createQuery(" UPDATE StudyPermissionBO SET delFlag = "+FdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_ACTIVE
					+" WHERE userId = "+userId+" and studyId="+studyId
					+" and delFlag="+FdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE);
			count = query.executeUpdate();
			transaction.commit();
			if(count > 0){
				delFag = FdahpStudyDesignerConstants.STATUS_ACTIVE;
			}
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteStudyPermissionById() - ERROR",e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - deleteStudyPermissionById() - Ends");
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
							+" and delFlag="+FdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE).uniqueResult();
					if(studyPermissionBO == null){
						studyPermissionBO = new StudyPermissionBO();
						studyPermissionBO.setDelFlag(FdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE);
						studyPermissionBO.setStudyId(Integer.valueOf(studyId));
						studyPermissionBO.setUserId(Integer.valueOf(perUserId));
						session.save(studyPermissionBO);
						count = 1;
					}
				}
			}
			if(count > 0){
				delFag = FdahpStudyDesignerConstants.STATUS_ACTIVE;
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - addStudyPermissionByuserIds() - ERROR",e);
		}finally{
			if(null != session && session.isOpen()){
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
		 * @param studyId of the StudyBo, Integer userId
		 * @return the Study page  list
		 * @exception Exception
	*/
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyPageBo> getOverviewStudyPagesById(String studyId, Integer userId){
		logger.info("StudyDAOImpl - getOverviewStudyPagesById() - Starts");
		Session session = null;
		List<StudyPageBo> studyPageBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(studyId)){
				query = session.createQuery("from StudyPageBo where studyId="+studyId);
				studyPageBo = query.list();
			}
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getOverviewStudyPagesById() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getOverviewStudyPagesById() - Ends");
		return studyPageBo;
	}

	
	/**
	 * @author Ronalin
	 * Add/Update the Study Overview Pages
	 * @param studyPageBean {@link StudyPageBean}
	 * @return {@link String}
	 */
	@Override
	public String saveOrUpdateOverviewStudyPages(StudyPageBean studyPageBean, SessionObject sesObj) {
		logger.info("StudyDAOImpl - saveOrUpdateOverviewStudyPages() - Starts");
		Session session = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		int titleLength = 0;
		StudySequenceBo studySequence = null;
		StudyBo studyBo = null;
		String activitydetails = "";
		String activity = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotEmpty(studyPageBean.getStudyId())){
				studyBo = (StudyBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_LIST_BY_ID).setInteger("id", Integer.parseInt(studyPageBean.getStudyId())).uniqueResult();
				if(studyBo != null){
					studyBo.setMediaLink(studyPageBean.getMediaLink());
					session.update(studyBo);
				}
				// fileArray based on pageId will save/update into particular location
				titleLength =  studyPageBean.getTitle().length;
				if(titleLength>0){
				//delete the pages whatever deleted from front end
				String pageIdArr=null;
				for(int j = 0; j < studyPageBean.getPageId().length; j++){
					if(FdahpStudyDesignerUtil.isNotEmpty(studyPageBean.getPageId()[j])){
						if(j == 0)
							pageIdArr = studyPageBean.getPageId()[j];
						else
							pageIdArr = pageIdArr + ","+studyPageBean.getPageId()[j];
					}
				}
				if(pageIdArr != null)
					session.createQuery("delete from StudyPageBo where studyId="+studyPageBean.getStudyId()+" and pageId not in("+pageIdArr+")").executeUpdate();
				else 
					session.createQuery("delete from StudyPageBo where studyId="+studyPageBean.getStudyId()).executeUpdate();
						for(int i=0;i<titleLength;i++){
							StudyPageBo studyPageBo = null;
							if(FdahpStudyDesignerUtil.isNotEmpty(studyPageBean.getPageId()[i]))
								studyPageBo = (StudyPageBo) session.createQuery("from StudyPageBo SPB where SPB.pageId="+studyPageBean.getPageId()[i]).uniqueResult();
								
							if(studyPageBo == null)
								studyPageBo = new StudyPageBo();
							
							if(FdahpStudyDesignerUtil.isNotEmpty(studyPageBean.getPageId()[i])) {
								studyPageBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								studyPageBo.setModifiedBy(studyPageBean.getUserId());
							} else {
								studyPageBo.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								studyPageBo.setCreatedBy(studyPageBean.getUserId());
							}
							studyPageBo.setStudyId(FdahpStudyDesignerUtil.isEmpty(studyPageBean.getStudyId()) ? 0 : Integer.parseInt(studyPageBean.getStudyId()));
							studyPageBo.setTitle(FdahpStudyDesignerUtil.isEmpty(studyPageBean.getTitle()[i])?null:studyPageBean.getTitle()[i]);
							studyPageBo.setDescription(FdahpStudyDesignerUtil.isEmpty(studyPageBean.getDescription()[i])?null:studyPageBean.getDescription()[i]);
							studyPageBo.setImagePath(FdahpStudyDesignerUtil.isEmpty(studyPageBean.getImagePath()[i])?null:studyPageBean.getImagePath()[i]);
							session.saveOrUpdate(studyPageBo);
						}
						studySequence = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, Integer.parseInt(studyPageBean.getStudyId())).uniqueResult();
						if(studySequence != null) {
							if(studyPageBean.getActionType() != null && studyPageBean.getActionType().equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON) && !studySequence.isOverView()) {
								studySequence.setOverView(true);
							}else if(studyPageBean.getActionType() != null && studyPageBean.getActionType().equalsIgnoreCase(FdahpStudyDesignerConstants.SAVE_BUTTON)){
								studySequence.setOverView(false);
							}
							session.update(studySequence);
						}
						message = auditLogDAO.updateDraftToEditedStatus(session, transaction, studyPageBean.getUserId(), FdahpStudyDesignerConstants.DRAFT_STUDY, Integer.parseInt(studyPageBean.getStudyId()));
						if(studyPageBean.getActionType().equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
							activity = "Study overview done";
							activitydetails = studyBo.getCustomStudyId()+" -- Study overview marked as completed";
						}else{
							activity = "Study overview saved";
							activitydetails = studyBo.getCustomStudyId()+" -- Study overview but not marked as completed and cannot process to publish / launch the study";
						}
						auditLogDAO.saveToAuditLog(session, transaction, sesObj, activity, activitydetails, "StudyDAOImpl - saveOrUpdateOverviewStudyPages");
						//						message = FdahpStudyDesignerConstants.SUCCESS;						
				}
				
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateOverviewStudyPages() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
		String message = FdahpStudyDesignerConstants.FAILURE;
		int count= 0; 
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotEmpty(studyId) && StringUtils.isNotEmpty(pageId)){
					query = session.createQuery("delete from StudyPageBo where studyId="+studyId+" and pageId="+pageId);
					count = query.executeUpdate();
					if(count>0)
						message = FdahpStudyDesignerConstants.SUCCESS;
			
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteOverviewStudyPageById() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
	public Integer saveOverviewStudyPageById(String studyId) {
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
			if(null != session && session.isOpen()){
				session.close();
			}
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
	@SuppressWarnings("unchecked")
	@Override
	public boolean validateStudyId(String customStudyId) {
		logger.info("StudyDAOImpl - validateStudyId() - Starts");
		boolean flag = false;
		Session session =null;
		List<StudyBo> studyBos = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			studyBos =  (List<StudyBo>) session.getNamedQuery("getStudyBycustomStudyId").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, customStudyId).list();
			if(studyBos!=null && !studyBos.isEmpty())
				flag = true;
		}catch(Exception e){
			logger.error("StudyDAOImpl - validateStudyId() - ERROR",e);
		}finally{
			if(null != session && session.isOpen()){
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
			String searchQuery = "From ConsentInfoBo CIB where CIB.studyId="+studyId+" and CIB.active=1 order by CIB.sequenceNo asc";
			query = session.createQuery(searchQuery);
			consentInfoList = query.list();
			System.out.println("consentInfoList:"+consentInfoList.size());
		}catch(Exception e){
			logger.error("StudyDAOImpl - getConsentInfoList() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId,SessionObject sessionObject,String customStudyId) {
		logger.info("StudyDAOImpl - deleteConsentInfo() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			List<ConsentInfoBo> consentInfoList = null;
			String searchQuery = "From ConsentInfoBo CIB where CIB.studyId="+studyId+" and CIB.active=1 order by CIB.sequenceNo asc";
			//String updateQuery = ""
			consentInfoList = session.createQuery(searchQuery).list();
			if(consentInfoList != null && !consentInfoList.isEmpty()){
				boolean isValue=false;
				for(ConsentInfoBo consentInfoBo : consentInfoList){
					if(consentInfoBo.getId().equals(consentInfoId)){
						isValue=true;
					}
					if(isValue && !consentInfoBo.getId().equals(consentInfoId)){
						consentInfoBo.setSequenceNo(consentInfoBo.getSequenceNo()-1);
						consentInfoBo.setModifiedBy(sessionObject.getUserId());
						consentInfoBo.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						session.update(consentInfoBo);
					}
				}
				StudySequenceBo studySequence = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyId).uniqueResult();
				if(studySequence != null){
					if(consentInfoList.size() == 1){
						studySequence.setConsentEduInfo(false);
					}
					if(studySequence.iseConsent()){
						studySequence.seteConsent(false);
					}
					session.saveOrUpdate(studySequence);
				}
			}
			String deleteQuery = "Update ConsentInfoBo CIB set CIB.active=0,CIB.modifiedBy="+sessionObject.getUserId()+",CIB.modifiedOn='"+FdahpStudyDesignerUtil.getCurrentDateTime()+"' where CIB.id="+consentInfoId;
			query = session.createQuery(deleteQuery);
			count = query.executeUpdate();
			if(count > 0){
				message = FdahpStudyDesignerConstants.SUCCESS;
			}
			auditLogDAO.saveToAuditLog(session, transaction, sessionObject, "ConsentInfo", customStudyId+" -- ConsentInfo deleted","StudyDAOImpl - deleteConsentInfo");
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteConsentInfo() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Query query = null;
		int count = 0;
		ConsentInfoBo consentInfoBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			String updateQuery ="";
			query = session.createQuery("From ConsentInfoBo CIB where CIB.studyId="+studyId+" and CIB.sequenceNo ="+oldOrderNumber+" and CIB.active=1");
			consentInfoBo = (ConsentInfoBo)query.uniqueResult();
			if(consentInfoBo != null){
				if (oldOrderNumber < newOrderNumber) {
					updateQuery = "update ConsentInfoBo CIBO set CIBO.sequenceNo=CIBO.sequenceNo-1 where CIBO.studyId="+studyId+" and CIBO.sequenceNo <="+newOrderNumber+" and CIBO.sequenceNo >"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update ConsentInfoBo C set C.sequenceNo="+ newOrderNumber+" where C.id="+consentInfoBo.getId());
						count = query.executeUpdate();
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update ConsentInfoBo CIBO set CIBO.sequenceNo=CIBO.sequenceNo+1 where CIBO.studyId="+studyId+" and CIBO.sequenceNo >="+newOrderNumber+" and CIBO.sequenceNo <"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update ConsentInfoBo C set C.sequenceNo="+ newOrderNumber+" where C.id="+consentInfoBo.getId());
						count = query.executeUpdate();
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - reOrderConsentInfoList() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - reOrderConsentInfoList() - Ends");
		return message;
	}

	@Override
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo, SessionObject sesObj,String customStudyId) {
		logger.info("StudyDAOImpl - saveOrUpdateConsentInfo() - Starts");
		Session session = null;
		StudySequenceBo studySequence = null;
		String activitydetails = "";
		String activity = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(consentInfoBo.getType() != null){
				studySequence = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, consentInfoBo.getStudyId()).uniqueResult();
				if(consentInfoBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_SAVE)){
					consentInfoBo.setStatus(false);
					if(studySequence != null){
						studySequence.setConsentEduInfo(false);
						if(studySequence.iseConsent()){
							studySequence.seteConsent(false);
						}
					}else{
						studySequence = new StudySequenceBo();
						studySequence.setConsentEduInfo(false);
						studySequence.setStudyId(consentInfoBo.getStudyId());
						
					}
				}else if(consentInfoBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_COMPLETE)){
					consentInfoBo.setStatus(true);
					if(studySequence.iseConsent()){
						studySequence.seteConsent(false);
					}
				}
				session.saveOrUpdate(studySequence);
			}
			session.saveOrUpdate(consentInfoBo);
			if(consentInfoBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_COMPLETE)){
				activity = "Consent section done";
				activitydetails = customStudyId+" -- Consent section done and eligible for mark as completed action";
			}else{
				activity = "Consent section saved";
				activitydetails = customStudyId+" -- Consent section saved but not eligible for mark as completed action untill unless it is DONE";
			}
			auditLogDAO.saveToAuditLog(session, transaction, sesObj, activity, activitydetails, "StudyDAOImpl - saveOrUpdateConsentInfo");
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateConsentInfo() - Error",e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			consentInfoBo = (ConsentInfoBo) session.get(ConsentInfoBo.class, consentInfoId);
			if(consentInfoBo!=null){
				consentInfoBo.setDisplayTitle(StringUtils.isEmpty(consentInfoBo.getDisplayTitle())?"":consentInfoBo.getDisplayTitle().replaceAll("&#34;", "\"").replaceAll("&#39;", "\'").replaceAll(")", "\\)").replaceAll("(", "\\("));
				consentInfoBo.setBriefSummary(StringUtils.isEmpty(consentInfoBo.getBriefSummary())?"":consentInfoBo.getBriefSummary().replaceAll("&#34;", "\"").replaceAll("&#39;", "\'").replaceAll(")", "\\)").replaceAll("(", "\\("));
				consentInfoBo.setElaborated(StringUtils.isEmpty(consentInfoBo.getElaborated())?"":consentInfoBo.getElaborated().replaceAll("&#34;", "\"").replaceAll("&#39;", "\'").replaceAll(")", "\\)").replaceAll("(", "\\("));
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - reOrderConsentInfoList() - Error",e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
		int count = 1;
		ConsentInfoBo consentInfoBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery("From ConsentInfoBo CIB where CIB.studyId="+studyId+" and CIB.active=1 order by CIB.sequenceNo DESC");
			query.setMaxResults(1);
			consentInfoBo = ((ConsentInfoBo) query.uniqueResult());
			if(consentInfoBo != null){
				count = consentInfoBo.getSequenceNo()+1;
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - consentInfoOrder() - Error",e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
			if(null != session && session.isOpen()){
				session.close();
			}
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
	@SuppressWarnings("unchecked")
	@Override
	public ComprehensionTestQuestionBo getComprehensionTestQuestionById(Integer questionId) {
		logger.info("StudyDAOImpl - getComprehensionTestQuestionById() - Starts");
		ComprehensionTestQuestionBo comprehensionTestQuestionBo = null;
		Session session = null;
		List<ComprehensionTestResponseBo> comprehensionTestResponsList = null;
		//Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			//String searchQuery = "From ComprehensionTestQuestionBo CTQBO where CTQBO.id="+questionId;
			comprehensionTestQuestionBo = (ComprehensionTestQuestionBo) session.get(ComprehensionTestQuestionBo.class, questionId);
			if(null!= comprehensionTestQuestionBo){
				String searchQuery = "From ComprehensionTestResponseBo CRBO where CRBO.id="+comprehensionTestQuestionBo.getId();
				query = session.createQuery(searchQuery);
				comprehensionTestResponsList = query.list();
				comprehensionTestQuestionBo.setResponseList(comprehensionTestResponsList);
			}
			//query = session.createQuery(searchQuery);
			//comprehensionTestQuestionBo = (ComprehensionTestQuestionBo) query.uniqueResult();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getComprehensionTestQuestionById() - Error",e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
	@SuppressWarnings("unchecked")
	@Override
	public String deleteComprehensionTestQuestion(Integer questionId,Integer studyId) {
		logger.info("StudyDAOImpl - deleteComprehensionTestQuestion() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		String searchQuery = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			List<ComprehensionTestQuestionBo> comprehensionTestQuestionList = null;
			searchQuery = "From ComprehensionTestQuestionBo CTQBO where CTQBO.studyId="+studyId+" order by CTQBO.sequenceNo asc";
			comprehensionTestQuestionList = session.createQuery(searchQuery).list();
			if(comprehensionTestQuestionList != null && !comprehensionTestQuestionList.isEmpty()){
				boolean isValue = false;
				for(ComprehensionTestQuestionBo comprehensionTestQuestionBo : comprehensionTestQuestionList){
					if(comprehensionTestQuestionBo.getId().equals(questionId)){
						isValue=true;
					}
					if(isValue && !comprehensionTestQuestionBo.getId().equals(questionId)){
						comprehensionTestQuestionBo.setSequenceNo(comprehensionTestQuestionBo.getSequenceNo()-1);
						session.update(comprehensionTestQuestionBo);
					}
				}
			
			}
			String deleteQuery = "delete ComprehensionTestQuestionBo CTQBO where CTQBO.id="+questionId;
			query = session.createQuery(deleteQuery);
			count = query.executeUpdate();
			if(count > 0){
				message = FdahpStudyDesignerConstants.SUCCESS;
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteComprehensionTestQuestion() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
			logger.error("StudyDAOImpl - deleteComprehensionTestQuestion() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
		StudySequenceBo studySequence=null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(comprehensionTestQuestionBo.getId() == null){
				studySequence = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, comprehensionTestQuestionBo.getStudyId()).uniqueResult();
				if(studySequence != null){
					studySequence.setComprehensionTest(true);
				}else{
					studySequence = new StudySequenceBo();
					studySequence.setComprehensionTest(true);
					studySequence.setStudyId(comprehensionTestQuestionBo.getStudyId());
					
				}
				session.saveOrUpdate(studySequence);
			}
			session.saveOrUpdate(comprehensionTestQuestionBo);
				if(comprehensionTestQuestionBo.getId() != null && comprehensionTestQuestionBo.getResponseList() != null && !comprehensionTestQuestionBo.getResponseList().isEmpty()){
					for(ComprehensionTestResponseBo comprehensionTestResponseBo : comprehensionTestQuestionBo.getResponseList()){
						if(comprehensionTestResponseBo.getComprehensionTestQuestionId() == null){
							comprehensionTestResponseBo.setComprehensionTestQuestionId(comprehensionTestQuestionBo.getId());
						}
						session.saveOrUpdate(comprehensionTestResponseBo);
					}
				}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateComprehensionTestQuestion() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
		ComprehensionTestQuestionBo comprehensionTestQuestionBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery("From ComprehensionTestResponseBo CTRBO where CTRBO.studyId="+studyId+" and order by CTRBO.sequenceNo desc");
			query.setMaxResults(1);
			comprehensionTestQuestionBo = (ComprehensionTestQuestionBo) query.uniqueResult();
			if(comprehensionTestQuestionBo != null){
				count = comprehensionTestQuestionBo.getSequenceNo()+1;
			}else{
				count = count + 1;
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - comprehensionTestQuestionOrder() - Error",e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
		String message = FdahpStudyDesignerConstants.FAILURE;
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
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}else if(oldOrderNumber > newOrderNumber){
					updateQuery = "update ComprehensionTestQuestionBo CTB set CTB.sequenceNo=CTB.sequenceNo+1 where CTB.studyId="+studyId+" and CTB.sequenceNo >="+newOrderNumber+" and CTB.sequenceNo <"+oldOrderNumber;
					query = session.createQuery(updateQuery);
					count = query.executeUpdate();
					if (count > 0) {
						query = session.createQuery("update ComprehensionTestQuestionBo CTB set CTB.sequenceNo="+ newOrderNumber+" where CTB.id="+comprehensionTestQuestionBo.getId());
						count = query.executeUpdate();
						message = FdahpStudyDesignerConstants.SUCCESS;
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - reOrderComprehensionTestQuestion() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
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
				query = session.getNamedQuery("getEligibiltyByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, Integer.parseInt(studyId));
				eligibilityBo = (EligibilityBo) query.uniqueResult();
			}
		} catch (Exception e) {
			logger.error(
					"StudyDAOImpl - getStudyEligibiltyByStudyId() - ERROR ", e);
		} finally {
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getStudyEligibiltyByStudyId() - Ends");
		return eligibilityBo;
	}
	
	/**
	 * Save or update eligibility of study
	 * @author Vivek
	 * 
	 * @param eligibilityBo , {@link EligibilityBo}
	 * @return {@link String} , the status FdahpStudyDesignerConstants.SUCCESS or FdahpStudyDesignerConstants.FAILURE
	 * @exception Exception
	 */
	@Override
	public String saveOrUpdateStudyEligibilty(EligibilityBo eligibilityBo, SessionObject sesObj,String customStudyId) {
		logger.info("StudyDAOImpl - saveOrUpdateStudyEligibilty() - Starts");
		String result = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		StudySequenceBo studySequence = null;
		EligibilityBo eligibilityBoUpdate = null;
		Boolean updateFlag = false;
		String activitydetails = "";
		String activity = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(null != eligibilityBo){
				if(eligibilityBo.getId() != null){
					eligibilityBoUpdate = (EligibilityBo) session.getNamedQuery("getEligibiltyById").setInteger("id", eligibilityBo.getId()).uniqueResult();
					eligibilityBoUpdate.setEligibilityMechanism(eligibilityBo.getEligibilityMechanism());
					eligibilityBoUpdate.setInstructionalText(eligibilityBo.getInstructionalText());
					eligibilityBoUpdate.setModifiedOn(eligibilityBo.getModifiedOn());
					eligibilityBoUpdate.setModifiedBy(eligibilityBo.getModifiedBy());
					updateFlag = true;
				} else {
					eligibilityBoUpdate = eligibilityBo;
				}
				
				studySequence = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, eligibilityBo.getStudyId()).uniqueResult();
				if(studySequence != null) {
					if(eligibilityBo.getActionType() != null && ("mark").equals(eligibilityBo.getActionType()) && !studySequence.isEligibility()){
						studySequence.setEligibility(true);
					} else if(eligibilityBo.getActionType() != null && !("mark").equals(eligibilityBo.getActionType())){
						studySequence.setEligibility(false);
					}
					session.saveOrUpdate(eligibilityBoUpdate);
				}
				if(("mark").equals(eligibilityBo.getActionType())){
					activity = "Study eligibility done";
					activitydetails = customStudyId+" -- Study overview marked as completed";
				}else{
					activity = "Study eligibility saved";
					activitydetails = customStudyId+" -- Study eligibility saved but not marked as completed and cannot process to publish / launch the study";
				}
				auditLogDAO.saveToAuditLog(session, transaction, sesObj, activity, activitydetails, "StudyDAOImpl - saveOrUpdateStudyEligibilty");
				result = auditLogDAO.updateDraftToEditedStatus(session, transaction, (updateFlag ? eligibilityBo.getModifiedBy(): eligibilityBo.getCreatedBy()), FdahpStudyDesignerConstants.DRAFT_STUDY, eligibilityBo.getStudyId());
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateStudyEligibilty() - ERROR ", e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - saveOrUpdateStudyEligibilty() - Ends");
		return result;
	}
	
	/*------------------------------------Added By Vivek End---------------------------------------------------*/
	
	/**
	 * Save or update settings and admins of study
	 * @author Ronalin
	 * 
	 * @param studyBo , {@link studyBo}
	 * @return {@link String} , the status FdahpStudyDesignerConstants.SUCCESS or FdahpStudyDesignerConstants.FAILURE
	 * @exception Exception
	 */
	public String saveOrUpdateStudySettings(StudyBo studyBo, SessionObject sesObj) {
		logger.info("StudyDAOImpl - saveOrUpdateStudySettings() - Starts");
		String result = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		StudySequenceBo studySequence = null;
		StudyBo study = null;
		String activitydetails = "";
		String activity = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
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
				    	study.setModifiedBy(studyBo.getUserId());
				    	study.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
				    	session.saveOrUpdate(study);
				    	
						// setting true to setting admins
					    	if(StringUtils.isNotEmpty(studyBo.getButtonText()) && studyBo.getButtonText().equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON) && !studySequence.isSettingAdmins()){
								studySequence.setSettingAdmins(true);
							}else if(StringUtils.isNotEmpty(studyBo.getButtonText()) 
									&& studyBo.getButtonText().equalsIgnoreCase(FdahpStudyDesignerConstants.SAVE_BUTTON)){
								studySequence.setSettingAdmins(false);
							}
					    	session.update(studySequence);
					}
				} 
				result = auditLogDAO.updateDraftToEditedStatus(session, transaction, studyBo.getUserId(), FdahpStudyDesignerConstants.DRAFT_STUDY, studyBo.getId());
				if(study != null){
				if(studyBo.getButtonText().equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
					activity = "Study settings done";
					activitydetails = study.getCustomStudyId()+" -- Study setting marked as completed";
				}else{
					activity = "Study settings saved";
					activitydetails = study.getCustomStudyId()+" -- Study setting saved but not marked as completed and cannot process to publish or launch the study ";
				}
				}
				auditLogDAO.saveToAuditLog(session, transaction, sesObj, activity, activitydetails, "StudyDAOImpl - saveOrUpdateStudySettings");
			}
			transaction.commit();
		} catch (Exception e) {
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateStudySettings() - ERROR ", e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - saveOrUpdateStudySettings() - Ends");
		return result;
	}
	/**
	 * @author Ravinder
	 * @return List : ConsentMasterInfoBo List
	 * This method is used get consent master data
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentMasterInfoBo> getConsentMasterInfoList() {
		logger.info("StudyDAOImpl - getConsentMasterInfoList() - Starts");
		Session session = null;
		List<ConsentMasterInfoBo> consentMasterInfoList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
				query = session.createQuery("From ConsentMasterInfoBo CMIB");
				consentMasterInfoList = query.list();
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getConsentMasterInfoList() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getConsentMasterInfoList() - Ends");
		return consentMasterInfoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ConsentInfoBo> getConsentInfoDetailsListByStudyId(String studyId) {
		logger.info("INFO: StudyDAOImpl - getConsentInfoDetailsListByStudyId() :: Starts");
		Session session = null;
		Query query = null;
		List<ConsentInfoBo> consentInfoBoList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery(" from ConsentInfoBo CIBO where CIBO.studyId="+studyId+" and CIBO.active=1 ORDER BY CIBO.sequenceNo ");
			consentInfoBoList = query.list();
			if( null != consentInfoBoList && consentInfoBoList.size() > 0){
				for(ConsentInfoBo consentInfoBo : consentInfoBoList){
					//consentInfoBo.setDisplayTitle(consentInfoBo.getDisplayTitle().replaceAll("&#34;", "\"").replaceAll("&#39;", "\'").replaceAll(")", "\\)").replaceAll("(", "\\("));
					//consentInfoBo.setElaborated(consentInfoBo.getElaborated().replaceAll("&#34;", "\"").replaceAll("&#39;", "\'").replaceAll(")", "\\)").replaceAll("(", "\\(").replaceAll("em>", "i>").replaceAll("<a", "<a style='text-decoration:underline;color:blue;'"));
					//consentInfoBo.setElaborated(consentInfoBo.getElaborated().replace("\"", "\\\""));
					consentInfoBo.setDisplayTitle(consentInfoBo.getDisplayTitle().replaceAll("<", "&#60;").replaceAll(">", "&#62;").replaceAll("/", "&#47;").replaceAll("'", "&#39;").replaceAll("\"", "&#34;"));
					consentInfoBo.setElaborated(consentInfoBo.getElaborated().replaceAll("&#39;", "&quot;").replaceAll("&#34;", "'").replaceAll("em>", "i>").replaceAll("<a", "<a target='_blank' style='text-decoration:underline;color:blue;'"));
				}
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - getConsentInfoDetailsListByStudyId() - ERROR", e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("INFO: StudyDAOImpl - getConsentInfoDetailsListByStudyId() :: Ends");
		return consentInfoBoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ConsentBo saveOrCompleteConsentReviewDetails(ConsentBo consentBo, SessionObject sesObj,String customStudyId) {
		logger.info("INFO: StudyDAOImpl - saveOrCompleteConsentReviewDetails() :: Starts");
		Session session = null;
		StudySequenceBo studySequence=null;
		List<ConsentInfoBo> consentInfoList = null;
		String content = "";
		String activitydetails = "";
		String activity = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			//check whether the consentinfo is saved for this study or not, if not update
			if(consentBo.getId() != null){
				consentBo.setModifiedOn(FdahpStudyDesignerUtil.getFormattedDate(FdahpStudyDesignerUtil.getCurrentDateTime(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss"));
				consentBo.setModifiedBy(sesObj.getUserId());
			}else{
				consentBo.setCreatedOn(FdahpStudyDesignerUtil.getFormattedDate(FdahpStudyDesignerUtil.getCurrentDateTime(), "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm:ss"));
				consentBo.setCreatedBy(sesObj.getUserId());
			}
			
			//get the review content based on the version, studyId and visual step
			if(consentBo.getConsentDocType().equalsIgnoreCase("Auto")){
				query = session.createQuery(" from ConsentInfoBo CIBO where CIBO.studyId="+consentBo.getStudyId()+" and CIBO.active=1");
				consentInfoList = query.list();
				if(consentInfoList != null && consentInfoList.size() > 0){
					for(ConsentInfoBo consentInfo : consentInfoList){
						content += "<span style=&#34;font-size:20px;&#34;><strong>"
								+consentInfo.getDisplayTitle()
								+"</strong></span><br/>"
								+"<span style=&#34;display: block; overflow-wrap: break-word; width: 100%;&#34;>"
								+consentInfo.getElaborated()
								+"</span><br/>";
					}
					consentBo.setConsentDocContent(content);
				}
			}
			
			if(consentBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_SAVE)){
				studySequence = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, consentBo.getStudyId()).uniqueResult();
				if(studySequence != null){
					studySequence.seteConsent(false);
				}else{
					studySequence = new StudySequenceBo();
					studySequence.seteConsent(false);
					studySequence.setStudyId(consentBo.getStudyId());
					
				}
				session.saveOrUpdate(studySequence);
			}
			session.saveOrUpdate(consentBo);
			if(consentBo.getType().equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_TYPE_SAVE)){
				activity = "Study consentReview saved";
				activitydetails = customStudyId+" -- Study consentReview saved but not marked as completed and cannot process to publish or launch the study ";
			}else{
				activity = "Study consentReview marked as completed";
				activitydetails = customStudyId+" -- Study consentReview marked as completed";
			}
			auditLogDAO.saveToAuditLog(session, transaction, sesObj, activity, activitydetails, "StudyDAOImpl - saveOrCompleteConsentReviewDetails");
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrCompleteConsentReviewDetails() :: ERROR", e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("INFO: StudyDAOImpl - saveOrCompleteConsentReviewDetails() :: Ends");
		return consentBo;
	}
	@Override
	public ConsentBo getConsentDetailsByStudyId(String studyId) {
		logger.info("INFO: StudyDAOImpl - getConsentDetailsByStudyId() :: Starts");
		ConsentBo consentBo = null;
		Session session = null;
		Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery("from ConsentBo CBO where CBO.studyId="+studyId+"");
			consentBo = (ConsentBo) query.uniqueResult();
		}catch(Exception e){
			logger.error("StudyDAOImpl - saveOrCompleteConsentReviewDetails() :: ERROR", e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("INFO: StudyDAOImpl - getConsentDetailsByStudyId() :: Ends");
		return consentBo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceBO> getResourceList(Integer studyId) {
		logger.info("StudyDAOImpl - getResourceList() - Starts");
		List<ResourceBO> resourceBOList = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyId+" AND RBO.status = 1 ORDER BY RBO.createdOn DESC ";
			query = session.createQuery(searchQuery);
			resourceBOList = query.list();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getResourceList() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getResourceList() - Ends");
		return resourceBOList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ResourceBO> resourcesSaved(Integer studyId) {
		logger.info("StudyDAOImpl - resourcesSaved() - Starts");
		List<ResourceBO> resourceBOList = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyId+" AND RBO.action = 0 AND RBO.status = 1 AND RBO.studyProtocol = 0";
			query = session.createQuery(searchQuery);
			resourceBOList = query.list();
		}catch(Exception e){
			logger.error("StudyDAOImpl - resourcesSaved() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - resourcesSaved() - Ends");
		return resourceBOList;
	}
	
	@Override
	public String deleteResourceInfo(Integer resourceInfoId,boolean resourceVisibility) {
		logger.info("StudyDAOImpl - deleteResourceInfo() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int resourceCount = 0;
		Query resourceQuery = null;
		Query notificationQuery = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			String deleteQuery = " UPDATE ResourceBO RBO SET status = "+ false +" WHERE id = "+resourceInfoId;
			resourceQuery = session.createQuery(deleteQuery);
			resourceCount = resourceQuery.executeUpdate();
			
			if(!resourceVisibility && resourceCount > 0){
				String deleteNotificationQuery = " UPDATE NotificationBO NBO set NBO.notificationStatus = 1 WHERE NBO.resourceId = " +resourceInfoId;
				notificationQuery = session.createQuery(deleteNotificationQuery);
				notificationQuery.executeUpdate();
			}
				transaction.commit();
				message = FdahpStudyDesignerConstants.SUCCESS;
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - deleteResourceInfo() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - deleteResourceInfo() - Ends");
		return message;
	}
	
	@Override
	public ResourceBO getResourceInfo(Integer resourceInfoId) {
		logger.info("StudyDAOImpl - getResourceInfo() - Starts");
		ResourceBO resourceBO = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getResourceInfo").setInteger("resourceInfoId", resourceInfoId);
			resourceBO = (ResourceBO) query.uniqueResult();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getResourceInfo() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getResourceInfo() - Ends");
		return resourceBO;
	}
	
	@Override
	public Integer saveOrUpdateResource(ResourceBO resourceBO){
		logger.info("StudyDAOImpl - saveOrUpdateResource() - Starts");
		Session session = null;
		Integer resourceId = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(null == resourceBO.getId()){
				resourceId = (Integer) session.save(resourceBO);
			}else{
				session.update(resourceBO);
				resourceId = resourceBO.getId();
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrUpdateResource() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - saveOrUpdateResource() - Ends");
		return resourceId;
	}
	
	@Override
	public String markAsCompleted(int studyId,String markCompleted, boolean flag, SessionObject sesObj,String customStudyId) {
		logger.info("StudyDAOImpl - markAsCompleted() - Starts");
		String msg = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		String activity = "";
		String activityDetails = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(markCompleted.equals(FdahpStudyDesignerConstants.NOTIFICATION)){
				query = session.createQuery(" UPDATE StudySequenceBo SET miscellaneousNotification = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				if(flag){
					activity = "Study level notification";
					activityDetails = customStudyId+" -- All the notification has been DONE and it is marked as completed , notification will be triggered to user once the study is launch ";
				}
			}else if(markCompleted.equals(FdahpStudyDesignerConstants.RESOURCE)){
				if(flag){
					activity = "Resource completed";
					activityDetails = customStudyId+" -- All the resources has been DONE and it is marked as completed.";
				}else{
					activity = "Resource saved";
					activityDetails = customStudyId+" -- Resource content saved successfully";
				}
				query = session.createQuery(" UPDATE StudySequenceBo SET miscellaneousResources = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_STUDY, studyId);
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.CONESENT)){
				query = session.createQuery(" UPDATE StudySequenceBo SET consentEduInfo = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				if(flag){
					activity = "Study consent section";
					activityDetails = customStudyId+" -- All the consent has been DONE and it is marked as completed";
				}
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_CONSCENT, studyId);
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.CONESENT_REVIEW)){
				query = session.createQuery(" UPDATE StudySequenceBo SET eConsent = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_CONSCENT, studyId);
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.CHECK_LIST)){
				query = session.createQuery(" UPDATE StudySequenceBo SET checkList = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTIVETASK_LIST)){
				query = session.createQuery(" UPDATE StudySequenceBo SET studyExcActiveTask = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				if(flag){
					activity = "ActiveTask";
					activityDetails = customStudyId+" -- All the ActiveTask has been DONE and it is marked as completed";
				}
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_ACTIVETASK, studyId);
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTIONNAIRE)){
				query = session.createQuery(" UPDATE StudySequenceBo SET studyExcQuestionnaries = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				if(flag){
					activity = FdahpStudyDesignerConstants.QUESTIONNAIRE_ACTIVITY;
					activityDetails = customStudyId+" -- "+FdahpStudyDesignerConstants.QUESTIONNAIRELIST_MARKED_AS_COMPLETED;
				}
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_QUESTIONNAIRE, studyId);
			}
			if(count > 0){
				msg = FdahpStudyDesignerConstants.SUCCESS;
			}
			if(sesObj!=null && FdahpStudyDesignerUtil.isNotEmpty(activity) && FdahpStudyDesignerUtil.isNotEmpty(activityDetails)){
				auditLogDAO.saveToAuditLog(session, transaction, sesObj, activity, activityDetails, "StudyDAOImpl - markAsCompleted");
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - markAsCompleted() - ERROR",e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - markAsCompleted() - Ends");
		return msg;
	}
	
	@Override
	public String saveResourceNotification(NotificationBO notificationBO,boolean notiFlag){
		logger.info("StudyDAOImpl - saveResourceNotification() - Starts");
		Session session = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(!notiFlag){
				session.save(notificationBO);
			}else{
				session.update(notificationBO);
			}
			transaction.commit();
			message = FdahpStudyDesignerConstants.SUCCESS;
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveResourceNotification() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - saveResourceNotification() - Ends");
		return message;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NotificationBO> getSavedNotification(Integer studyId) {
		logger.info("StudyDAOImpl - getSavedNotification() - Starts");
		List<NotificationBO> notificationSavedList = null;
		Session session = null;
		String searchQuery = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			searchQuery = " FROM NotificationBO NBO WHERE NBO.studyId="+studyId+" AND NBO.notificationAction = 0 AND NBO.notificationType='ST' AND NBO.notificationSubType='Announcement' ";
			query = session.createQuery(searchQuery);
			notificationSavedList = query.list();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getSavedNotification() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getSavedNotification() - Ends");
		return notificationSavedList;
	}
	
	@Override
	public StudyBo getStudyLiveStatusByCustomId(String customStudyId) {
		logger.info("StudyDAOImpl - getStudyLiveStatusByCustomId() - Starts");
		StudyBo studyLive = null;
		Session session = null;
		String searchQuery = "";
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			searchQuery = "FROM StudyBo SBO WHERE SBO.customStudyId = '"+customStudyId+"' AND SBO.live = 1";
			query = session.createQuery(searchQuery);
			studyLive = (StudyBo) query.uniqueResult();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getStudyLiveStatusByCustomId() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getStudyLiveStatusByCustomId() - Ends");
		return studyLive;
	}
	
	@Override
	public Checklist getchecklistInfo(Integer studyId) {
		logger.info("StudyDAOImpl - getchecklistInfo() - Starts");
		Checklist checklist = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getchecklistInfo").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyId);
			checklist = (Checklist) query.uniqueResult();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getchecklistInfo() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getchecklistInfo() - Ends");
		return checklist;
	}
	
	@Override
	public Integer saveOrDoneChecklist(Checklist checklist) {
		logger.info("StudyDAOImpl - saveOrDoneChecklist() - Starts");
		Session session = null;
		Integer checklistId = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(checklist.getChecklistId() == null){
				checklistId = (Integer) session.save(checklist);
			}else{
				session.update(checklist);
				checklistId = checklist.getChecklistId();
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - saveOrDoneChecklist() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - saveOrDoneChecklist() - Ends");
		return checklistId;
	}

    /**
     * validate Study Action 
     */
	@SuppressWarnings("unchecked")
	@Override
	public String validateStudyAction(String studyId, String buttonText) {
		logger.info("StudyDAOImpl - validateStudyAction() - Ends");
		String message = FdahpStudyDesignerConstants.SUCCESS;
		Session session = null;
		boolean	enrollementFlag = false;
		boolean	studyActivityFlag = false;
		boolean	activityFlag = false;
		StudySequenceBo studySequenceBo = null;
		StudyBo studyBo = null ;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(buttonText) && StringUtils.isNotEmpty(studyId)){
			
			studyBo = (StudyBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_LIST_BY_ID).setInteger("id", Integer.parseInt(studyId)).uniqueResult();
			studySequenceBo = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId()).uniqueResult();
            
			if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH) || buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES)){
				
				//1-all validation mark as completed
				if(studySequenceBo!=null){
					String studyActivity = "";
					studyActivity = getErrorBasedonAction(studySequenceBo);
					if(StringUtils.isNotEmpty(studyActivity) && !(FdahpStudyDesignerConstants.SUCCESS).equalsIgnoreCase(studyActivity))
					    return studyActivity;
					else
                      studyActivityFlag	=true;
				}
				
				//2-enrollment validation
					if(studyActivityFlag && StringUtils.isNotEmpty(studyBo.getEnrollingParticipants()) && studyBo.getEnrollingParticipants().equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
						enrollementFlag = true;
					}
				//3-The study must have at least one 'activity' added. This could be a questionnaire or active task. 
				if(!enrollementFlag){
					if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH))
					  message = FdahpStudyDesignerConstants.LUNCH_ENROLLMENT_ERROR_MSG;
					else
						message = FdahpStudyDesignerConstants.PUBLISH_ENROLLMENT_ERROR_MSG;	
					return message;
				}else{
					//4-Date validation
					message = validateDateForStudyAction(studyBo, buttonText);
					return message ; 
				}
			}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PUBLISH)){
				if(studySequenceBo!=null){	
					     if(!studySequenceBo.isBasicInfo()){
						   message = FdahpStudyDesignerConstants.BASICINFO_ERROR_MSG;
						   return message;
					    }else if(!studySequenceBo.isSettingAdmins()){
					    	message = FdahpStudyDesignerConstants.SETTING_ERROR_MSG;
					    	return message;
					    }else if(!studySequenceBo.isOverView()){
					    	message = FdahpStudyDesignerConstants.OVERVIEW_ERROR_MSG;
					    	return message;
					    }else if(!studySequenceBo.isConsentEduInfo()){
					    	message = FdahpStudyDesignerConstants.CONSENTEDUINFO_ERROR_MSG;
					    	return message;
					    }/*else if(!studySequenceBo.isComprehensionTest()){
					    	message = FdahpStudyDesignerConstants.COMPREHENSIONTEST_ERROR_MSG;
					    	return message;
					    }*/else if(!studySequenceBo.iseConsent()){
					    	message = FdahpStudyDesignerConstants.ECONSENT_ERROR_MSG;
					    	return message;
					    }else if(StringUtils.isNotEmpty(studyBo.getEnrollingParticipants()) && studyBo.getEnrollingParticipants().equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
					    	    message = FdahpStudyDesignerConstants.PRE_PUBLISH_ENROLLMENT_ERROR_MSG;
								return message;
					    }
				}
			}
			}else{
				message = "Action is missing";
			}
			
		}catch(Exception e){
			logger.error("StudyDAOImpl - validateStudyAction() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - validateStudyAction() - Ends");
		return message;
	}
	
	public String getErrorBasedonAction(StudySequenceBo studySequenceBo){
		String message = FdahpStudyDesignerConstants.SUCCESS;
		if(studySequenceBo!=null){
			if(!studySequenceBo.isBasicInfo()){
			   message = FdahpStudyDesignerConstants.BASICINFO_ERROR_MSG;
			   return message;
		    }else if(!studySequenceBo.isSettingAdmins()){
		    	message = FdahpStudyDesignerConstants.SETTING_ERROR_MSG;
		    	return message;
		    }else if(!studySequenceBo.isOverView()){
		    	message = FdahpStudyDesignerConstants.OVERVIEW_ERROR_MSG;
		    	return message;
		    }else if(!studySequenceBo.isEligibility()){
		    	message = FdahpStudyDesignerConstants.ELIGIBILITY_ERROR_MSG;
		    	return message;
		    }else if(!studySequenceBo.isConsentEduInfo()){
		    	message = FdahpStudyDesignerConstants.CONSENTEDUINFO_ERROR_MSG;
		    	return message;
		    }/*else if(!studySequenceBo.isComprehensionTest()){
		    	message = FdahpStudyDesignerConstants.COMPREHENSIONTEST_ERROR_MSG;
		    	return message;
		    }*/else if(!studySequenceBo.iseConsent()){
		    	message = FdahpStudyDesignerConstants.ECONSENT_ERROR_MSG;
		    	return message;
		    }else if(!studySequenceBo.isStudyExcQuestionnaries()){
		    	message = FdahpStudyDesignerConstants.STUDYEXCQUESTIONNARIES_ERROR_MSG;
		    	return message;
		    }else if(!studySequenceBo.isStudyExcActiveTask()){
		    	message = FdahpStudyDesignerConstants.STUDYEXCACTIVETASK_ERROR_MSG;
		    	return message;
		    }else if(!studySequenceBo.isMiscellaneousResources()){
		    	message = FdahpStudyDesignerConstants.RESOURCES_ERROR_MSG;
		    	return message;
		    }else if(!studySequenceBo.isCheckList()){
		    	message = FdahpStudyDesignerConstants.CHECKLIST_ERROR_MSG;
		    	return message;
		    }
        }
		return message;
	}


	@SuppressWarnings("unchecked")
	@Override
	public String updateStudyActionOnAction(String studyId, String buttonText, SessionObject sesObj) {
		logger.info("StudyDAOImpl - updateStudyActionOnAction() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		StudyBo studyBo = null;
		List<Object> objectList = null;
		String activitydetails = "";
		String activity = "";
		StudyBo liveStudy = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotEmpty(studyId) && StringUtils.isNotEmpty(buttonText)){
				studyBo = (StudyBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_LIST_BY_ID).setInteger("id", Integer.parseInt(studyId)).uniqueResult();
				if(studyBo!=null){
					if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PUBLISH)){
						studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_PRE_PUBLISH);
						studyBo.setStudyPreActiveFlag(true);
						session.update(studyBo);
						message = FdahpStudyDesignerConstants.SUCCESS;
						activity = "Study publish";
						activitydetails = studyBo.getCustomStudyId()+" -- Study published successfully";
						//notification sent to gateway
						NotificationBO notificationBO = new NotificationBO();
						notificationBO = new NotificationBO();
						notificationBO.setStudyId(studyBo.getId());
						notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
						notificationBO.setNotificationType(FdahpStudyDesignerConstants.NOTIFICATION_GT);
						notificationBO.setNotificationSubType(FdahpStudyDesignerConstants.NOTIFICATION_SUBTYPE_ANNOUNCEMENT);
						notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
						notificationBO.setNotificationStatus(false);
						notificationBO.setCreatedBy(sesObj.getUserId());
						notificationBO.setNotificationText(FdahpStudyDesignerConstants.NOTIFICATION_UPCOMING_OR_ACTIVE_TEXT);
						notificationBO.setScheduleDate(FdahpStudyDesignerUtil.getCurrentDate());
						notificationBO.setScheduleTime(FdahpStudyDesignerUtil.getCurrentTime());
						notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
						notificationBO.setNotificationDone(true);
						session.save(notificationBO);
					}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UNPUBLISH)){
						studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_PRE_LAUNCH);
						studyBo.setStudyPreActiveFlag(false);
						session.update(studyBo);
						message = FdahpStudyDesignerConstants.SUCCESS;
						activity = "Study unpublish";
						activitydetails = studyBo.getCustomStudyId()+" -- Study unpublished successfully";
					}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH) 
							|| buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES)){
						studyBo.setStudyPreActiveFlag(false);
						studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_ACTIVE);	
						studyBo.setStudylunchDate(FdahpStudyDesignerUtil.getCurrentDateTime());
						session.update(studyBo);
						//getting Questionnaries based on StudyId
						query = session.createQuery("select ab.id"
													+ " from QuestionnairesFrequenciesBo a,QuestionnaireBo ab"
													+ " where a.questionnairesId=ab.id"
													+" and ab.studyId=:impValue"
													+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"'"
													+" and a.isLaunchStudy=1");
						query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, Integer.valueOf(studyId));
					    objectList = query.list();
					    if(objectList!=null && !objectList.isEmpty()){
					    	for(Object obj: objectList){
					    		Integer questionaryId = (Integer)obj;
					    		if(questionaryId!=null){
					    			query = session.getNamedQuery("updateQuestionnaireStartDate").setString("studyLifetimeStart", studyBo.getStudylunchDate()).setInteger("id", questionaryId);
					    			query.executeUpdate();
					    		}
					    	}
					    }
					    
					  //getting activeTasks based on StudyId
						 query = session.createQuery("select ab.id"
									+ " from ActiveTaskFrequencyBo a,ActiveTaskBo ab"
									+ " where a.activeTaskId=ab.id"
									+" and ab.studyId=:impValue"
									+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"'"
									+" and a.isLaunchStudy=1");
						query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, Integer.valueOf(studyId));
						objectList = query.list();
					    if(objectList!=null && !objectList.isEmpty()){
					    	for(Object obj: objectList){
					    		Integer activeTaskId = (Integer)obj;
					    		if(activeTaskId!=null){
					    			query = session.getNamedQuery("updateFromActiveTAskStartDate").setString("activeTaskLifetimeStart", studyBo.getStudylunchDate()).setInteger("id", activeTaskId);
									query.executeUpdate();
					    		}
					    	}
					    }
					    message = FdahpStudyDesignerConstants.SUCCESS;
					    //StudyDraft version creation
					   message = this.studyDraftCreation(studyBo, session);
					   if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH)){
						 //notification text --   
						   activity = "Study launch";
					         activitydetails = studyBo.getCustomStudyId()+" -- Study launched successfully";
					       //notification sent to gateway    
					         NotificationBO notificationBO = new NotificationBO();
								notificationBO = new NotificationBO();
								notificationBO.setStudyId(studyBo.getId());
								notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
								notificationBO.setNotificationType(FdahpStudyDesignerConstants.NOTIFICATION_GT);
								notificationBO.setNotificationSubType(FdahpStudyDesignerConstants.NOTIFICATION_SUBTYPE_ANNOUNCEMENT);
								notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
								notificationBO.setNotificationStatus(false);
								notificationBO.setCreatedBy(sesObj.getUserId());
								notificationBO.setNotificationText(FdahpStudyDesignerConstants.NOTIFICATION_UPCOMING_OR_ACTIVE_TEXT);
								notificationBO.setScheduleDate(FdahpStudyDesignerUtil.getCurrentDate());
								notificationBO.setScheduleTime(FdahpStudyDesignerUtil.getCurrentTime());
								notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								notificationBO.setNotificationDone(true);
								session.save(notificationBO);
						}else{
							//notification text -- 
							activity = "Study update";
							activitydetails = studyBo.getCustomStudyId()+" -- Study updated successfully";
						}
					}else{
						liveStudy = (StudyBo) session.getNamedQuery("getStudyLiveVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId()).uniqueResult();
						if(liveStudy!=null){
							liveStudy.setStudyPreActiveFlag(false);
							if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PAUSE)){
								//notification text -- 
								activity = "Study pause";
								activitydetails = studyBo.getCustomStudyId()+" -- Study paused successfully";
								NotificationBO notificationBO = new NotificationBO();
								notificationBO = new NotificationBO();
								notificationBO.setStudyId(liveStudy.getId());
								notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
								notificationBO.setNotificationType(FdahpStudyDesignerConstants.NOTIFICATION_ST);
								notificationBO.setNotificationSubType(FdahpStudyDesignerConstants.STUDY_EVENT);
								notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
								notificationBO.setNotificationStatus(false);
								notificationBO.setCreatedBy(sesObj.getUserId());
								notificationBO.setNotificationText(FdahpStudyDesignerConstants.NOTIFICATION_PAUSE_TEXT.replace("$customId", studyBo.getCustomStudyId()));
								notificationBO.setScheduleDate(FdahpStudyDesignerUtil.getCurrentDate());
								notificationBO.setScheduleTime(FdahpStudyDesignerUtil.getCurrentTime());
								notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								notificationBO.setNotificationDone(true);
								session.save(notificationBO);
								
							  liveStudy.setStatus(FdahpStudyDesignerConstants.STUDY_PAUSED);
							  studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_PAUSED);
							  studyBo.setStudyPreActiveFlag(false);
						   }else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_RESUME)){
							 //notification text --
							   activity = "Study resume";
								activitydetails = studyBo.getCustomStudyId()+" -- Study resumed successfully";
								NotificationBO notificationBO = new NotificationBO();
								notificationBO = new NotificationBO();
								notificationBO.setStudyId(liveStudy.getId());
								notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
								notificationBO.setNotificationType(FdahpStudyDesignerConstants.NOTIFICATION_ST);
								notificationBO.setNotificationSubType(FdahpStudyDesignerConstants.STUDY_EVENT);
								notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
								notificationBO.setNotificationStatus(false);
								notificationBO.setCreatedBy(sesObj.getUserId());
								notificationBO.setNotificationText(FdahpStudyDesignerConstants.NOTIFICATION_RESUME_TEXT.replace("$customId", studyBo.getCustomStudyId()));
								notificationBO.setScheduleDate(FdahpStudyDesignerUtil.getCurrentDate());
								notificationBO.setScheduleTime(FdahpStudyDesignerUtil.getCurrentTime());
								notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								notificationBO.setNotificationDone(true);
								session.save(notificationBO);
							    liveStudy.setStatus(FdahpStudyDesignerConstants.STUDY_ACTIVE);
							    studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_ACTIVE);
							    studyBo.setStudyPreActiveFlag(false);
						   }else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_DEACTIVATE)){
							 //notification text -- 
							   liveStudy.setStatus(FdahpStudyDesignerConstants.STUDY_DEACTIVATED);
							   activity = "Study deactive";
							   activitydetails = studyBo.getCustomStudyId()+" -- Study deactivated successfully";
							   NotificationBO notificationBO = new NotificationBO();
								notificationBO = new NotificationBO();
								notificationBO.setStudyId(liveStudy.getId());
								notificationBO.setCustomStudyId(studyBo.getCustomStudyId());
								notificationBO.setNotificationType(FdahpStudyDesignerConstants.NOTIFICATION_ST);
								notificationBO.setNotificationSubType(FdahpStudyDesignerConstants.STUDY_EVENT);
								notificationBO.setNotificationScheduleType(FdahpStudyDesignerConstants.NOTIFICATION_IMMEDIATE);
								notificationBO.setNotificationStatus(false);
								notificationBO.setCreatedBy(sesObj.getUserId());
								notificationBO.setNotificationText(FdahpStudyDesignerConstants.NOTIFICATION_DEACTIVATE_TEXT.replace("$customId", studyBo.getCustomStudyId()));
								notificationBO.setScheduleDate(FdahpStudyDesignerUtil.getCurrentDate());
								notificationBO.setScheduleTime(FdahpStudyDesignerUtil.getCurrentTime());
								notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
								notificationBO.setNotificationDone(true);
								session.save(notificationBO); 
							   studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_DEACTIVATED);
							   studyBo.setStudyPreActiveFlag(false);
							   session.update(studyBo);
						    }
							session.update(liveStudy);
							message = FdahpStudyDesignerConstants.SUCCESS;
						}	
					}	
					if(message.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS))
						session.update(studyBo);
				}
				auditLogDAO.saveToAuditLog(session, transaction, sesObj, activity, activitydetails, "StudyDAOImpl - updateStudyActionOnAction");
				
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyDAOImpl - updateStudyActionOnAction() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - updateStudyActionOnAction() - Ends");
		return message;
	}
	
	
	@SuppressWarnings("unchecked")
	public String validateDateForStudyAction(StudyBo studyBo, String buttonText){
		boolean resourceFlag = true;
		boolean resourceAnchorFlag = true;
		boolean	activitiesFalg = true;
		boolean	questionarriesFlag = true;
		boolean notificationFlag = true;
		List<DynamicBean> dynamicList = null;
		List<DynamicFrequencyBean> dynamicFrequencyList = null;
		List<NotificationBO> notificationBOs = null;
		List<ResourceBO> resourceBOList = null;
		String searchQuery  = "";
		Session session = null;
		String message = FdahpStudyDesignerConstants.SUCCESS;
		boolean isExists = false;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			//anchor date need to be done (only custom date need to do)
			
			if(!buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES)){
				//getting based on custom statrt date resource list 
				searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyBo.getId()+" AND RBO.status = 1 AND RBO.startDate IS NOT NULL ORDER BY RBO.createdOn DESC ";
				query = session.createQuery(searchQuery);
				resourceBOList = query.list();
				if(resourceBOList!=null && !resourceBOList.isEmpty()){
					for(ResourceBO resourceBO:resourceBOList){
						boolean flag = false;
						String currentDate = FdahpStudyDesignerUtil.getCurrentDate();
						if(currentDate.equalsIgnoreCase(resourceBO.getStartDate())){
							flag = true;
						}else{
							flag = FdahpStudyDesignerUtil.compareDateWithCurrentDateResource(resourceBO.getStartDate(), "yyyy-MM-dd");
						}
						if(!flag){
							resourceFlag = false;
							break;
						}
					}
				}
			}
			if(!resourceFlag){
				message = FdahpStudyDesignerConstants.RESOURCE_DATE_ERROR_MSG;
				return message;
			}else{
				//Ancordate Checking 
				searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyBo.getId()+" AND RBO.resourceType = 1 AND RBO.status = 1 ";
				query = session.createQuery(searchQuery);
				resourceBOList = query.list();
				if(resourceBOList!=null && !resourceBOList.isEmpty()){
					
					searchQuery = "select count(q.use_anchor_date) from questions q where q.id in ((select qsq.instruction_form_id from questionnaires_steps qsq where qsq.step_type='"+FdahpStudyDesignerConstants.QUESTION_STEP+"' and qsq.active=1 and qsq.questionnaires_id in "
							+ "(select qq.id from questionnaires qq where qq.study_id="+studyBo.getId()+" and qq.active=1))) and q.use_anchor_date=1 and q.active=1";
					BigInteger count = (BigInteger) session.createSQLQuery(searchQuery).uniqueResult();
					if(count.intValue() > 0){
						isExists = true;
					}else{
						String subQuery = "select count(q.use_anchor_date) from questions q where q.id in (select fm.question_id from form_mapping fm where fm.form_id in"
										 +"( select f.form_id from form f where f.active=1 and f.form_id in (select qsf.instruction_form_id from questionnaires_steps qsf where qsf.step_type='"+FdahpStudyDesignerConstants.FORM_STEP+"' and qsf.questionnaires_id in "
										 +"(select qf.id from questionnaires qf where qf.study_id="+studyBo.getId()+")))) and q.use_anchor_date=1";
						BigInteger subCount = (BigInteger) session.createSQLQuery(subQuery).uniqueResult();
						if(subCount != null && subCount.intValue() > 0){
							isExists = true;
						}
					}
				}else{
					isExists = true;
				}
				if(!isExists)
					resourceAnchorFlag = false;
			 
			} 
			//Ancordate Checking 
			if(!resourceAnchorFlag){
				message = FdahpStudyDesignerConstants.RESOURCE_ANCHOR_ERROR_MSG;
				return message;
		    }else{
		    	//getting activeTasks based on StudyId
				 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicBean(a.frequencyDate, a.frequencyTime)"
							+ " from ActiveTaskFrequencyBo a,ActiveTaskBo ab"
							+ " where a.activeTaskId=ab.id"
							+" and ab.active IS NOT NULL"
							+" and ab.active=1"
							+" and ab.studyId=:impValue"
							+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"'"
							+" and a.isLaunchStudy=false"
							+" and a.frequencyDate IS NOT NULL"
							+" and a.frequencyTime IS NOT NULL");
				query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, studyBo.getId());
				dynamicList = query.list();
				 if(dynamicList!=null && !dynamicList.isEmpty()){

				 for(DynamicBean obj:dynamicList){
						 if(obj.getDateTime()!=null && !FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(obj.getDateTime()+" "+obj.getTime(), "yyyy-MM-dd HH:mm:ss")){
							 activitiesFalg = false;
							break;
						 }	
					}
				 }
				 
				 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicBean(ab.activeTaskLifetimeStart, a.frequencyTime)"
							+ " from ActiveTaskFrequencyBo a,ActiveTaskBo ab"
							+ " where a.activeTaskId=ab.id"
							+" and ab.active IS NOT NULL"
							+" and ab.active=1"
							+" and ab.studyId=:impValue"
							+" and ab.frequency not in('"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"','"
							+FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE+"')"
							+" and ab.activeTaskLifetimeStart IS NOT NULL"
							+" and a.frequencyTime IS NOT NULL");
				query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, studyBo.getId());
				dynamicList = query.list();
				 if(dynamicList!=null && !dynamicList.isEmpty()){
				 for(DynamicBean obj:dynamicList){
						 if(obj.getDateTime()!=null && !FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(obj.getDateTime()+" "+obj.getTime(), "yyyy-MM-dd HH:mm:ss")){
							 activitiesFalg = false;
							break;
						 }	
					}
				 }
				 
				 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicFrequencyBean(a.frequencyStartDate, a.frequencyTime)"
							+ " from ActiveTaskCustomScheduleBo a,ActiveTaskBo ab"
							+ " where a.activeTaskId=ab.id"
							+" and ab.active IS NOT NULL"
							+" and ab.active=1"
							+" and ab.studyId=:impValue"
							+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE+"'"
							+" and a.frequencyStartDate IS NOT NULL"
							+" and a.frequencyTime IS NOT NULL");
				query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, studyBo.getId());
				dynamicFrequencyList = query.list();
				 if(dynamicFrequencyList!=null && !dynamicFrequencyList.isEmpty()){
				 for(DynamicFrequencyBean obj:dynamicFrequencyList){
					 if(obj.getStartDate()!=null && obj.getTime()!=null){
						 String dateTime = obj.getStartDate()+" "+obj.getTime();
						 if(!FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(dateTime, "yyyy-MM-dd HH:mm:ss")){
							 activitiesFalg = false;
							break;
						 }	
					 }
					}
				 }
		    }
			if(!activitiesFalg){
					message = FdahpStudyDesignerConstants.ACTIVETASK_DATE_ERROR_MSG;
					return message;
			}else{
				//getting Questionnaries based on StudyId
				 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicBean(a.frequencyDate, a.frequencyTime)"
							+ " from QuestionnairesFrequenciesBo a,QuestionnaireBo ab"
							+ " where a.questionnairesId=ab.id"
							+" and ab.active=1"
							+" and ab.studyId=:impValue"
							+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"'"
							+" and a.frequencyDate IS NOT NULL"
							+" and a.frequencyTime IS NOT NULL");
				query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, studyBo.getId());
				dynamicList = query.list();
				 if(dynamicList!=null && !dynamicList.isEmpty()){
				 for(DynamicBean obj:dynamicList){
						 if(obj.getDateTime()!=null && !FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(obj.getDateTime()+" "+obj.getTime(), "yyyy-MM-dd HH:mm:ss")){
							 questionarriesFlag = false;
							break;
						 }	
					}
				 }
				 
				 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicBean(ab.studyLifetimeStart, a.frequencyTime)"
							+ " from QuestionnairesFrequenciesBo a,QuestionnaireBo ab"
							+ " where a.questionnairesId=ab.id"
							+" and ab.active=1"
							+" and ab.studyId=:impValue"
							+" and ab.frequency not in('"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"','"
							+FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE+"')"
							+" and ab.studyLifetimeStart IS NOT NULL"
							+" and a.frequencyTime IS NOT NULL");
				query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, studyBo.getId());
				dynamicList = query.list();
				 if(dynamicList!=null && !dynamicList.isEmpty()){
				 for(DynamicBean obj:dynamicList){
						 if(obj.getDateTime()!=null && !FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(obj.getDateTime()+" "+obj.getTime(), "yyyy-MM-dd HH:mm:ss")){
							 questionarriesFlag = false;
							break;
						 }	
					}
				 }
				 
				 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicFrequencyBean(a.frequencyStartDate, a.frequencyTime)"
							+ " from QuestionnaireCustomScheduleBo a,QuestionnaireBo ab"
							+ " where a.questionnairesId=ab.id"
							+" and ab.active=1"
							+" and ab.studyId=:impValue"
							+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE+"'"
							+" and a.frequencyStartDate IS NOT NULL"
							+" and a.frequencyTime IS NOT NULL");
				query.setParameter(FdahpStudyDesignerConstants.IMP_VALUE, studyBo.getId());
				dynamicFrequencyList = query.list();
				 if(dynamicFrequencyList!=null && !dynamicFrequencyList.isEmpty()){
				 for(DynamicFrequencyBean obj:dynamicFrequencyList){
					 if(obj.getStartDate()!=null && obj.getTime()!=null){
						 String dateTime = obj.getStartDate()+" "+obj.getTime();
						 if(!FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(dateTime, "yyyy-MM-dd HH:mm:ss")){
							 questionarriesFlag = false;
							break;
						 }	
					 }
					}
				 }
			}
		if(!questionarriesFlag){
			message = FdahpStudyDesignerConstants.QUESTIONNARIES_ERROR_MSG;
			return message;
		}else{
			//getting based on statrt date notification list 
			searchQuery = " FROM NotificationBO RBO WHERE RBO.studyId="+studyBo.getId()
					+" AND RBO.scheduleDate IS NOT NULL AND RBO.scheduleTime IS NOT NULL"
					+ " AND RBO.notificationType='ST' AND RBO.notificationSubType='Announcement' AND RBO.notificationScheduleType='notImmediate' "
					+ " AND RBO.notificationSent=0 AND RBO.notificationStatus=0 ";
			query = session.createQuery(searchQuery);
			notificationBOs = query.list();
			if(notificationBOs!=null && !notificationBOs.isEmpty()){
				for(NotificationBO notificationBO:notificationBOs){
						//String sceduleDateTime = FdahpStudyDesignerUtil.getFormattedDate(notificationBO.getScheduleDate()+" "+notificationBO.getScheduleTime(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
						if(!FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(notificationBO.getScheduleDate()+" "+notificationBO.getScheduleTime(), "yyyy-MM-dd HH:mm:ss")){
							notificationFlag = false;
							break;
						   
						}
				}
			}
		}
		if(!notificationFlag){
			message = FdahpStudyDesignerConstants.NOTIFICATION_ERROR_MSG;
			
			return message;
		}
		}catch(Exception e){
			logger.error("StudyDAOImpl - updateStudyActionOnAction() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		
		return message; 
	}
	
	/**
	 * Study Draft related data created
	 * @param studyBo
	 */
	@SuppressWarnings({ "unchecked", "unused" })
	public String studyDraftCreation(StudyBo studyBo, Session session){
		logger.info("StudyDAOImpl - studyDraftCreation() - Starts");
		List<StudyPageBo> studyPageBo = null;
		List<StudyPermissionBO> studyPermissionList = null;
		EligibilityBo eligibilityBo = null;
		List<ResourceBO> resourceBOList = null;
		StudyVersionBo studyVersionBo = null;
		StudyVersionBo  newstudyVersionBo = null;
		boolean flag = true;
		String message = FdahpStudyDesignerConstants.FAILURE;
		List<QuestionnaireBo> questionnaires = null;
		List<ActiveTaskBo> activeTasks = null;
		String searchQuery = "";
		QuestionReponseTypeBo questionReponseTypeBo = null;
		try{
			/*if(session!= null) {
				transaction = session.beginTransaction();
			}*/
			if(studyBo!=null){
				//if already lunch if study hasStudyDraft()==1 , then update and create draft version , otherwise not
				query = session.getNamedQuery("getStudyByCustomStudyId").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
				query.setMaxResults(1);
				studyVersionBo = (StudyVersionBo)query.uniqueResult();
				if(studyVersionBo!=null && (studyBo.getHasStudyDraft().equals(0))){
					flag = false;
				}
				if(flag){
				//version update in study_version table 
				if(studyVersionBo!=null){
					//update all studies to archive (live as 2)
					query = session.getNamedQuery("updateStudyVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
					query.executeUpdate();
					
					newstudyVersionBo = SerializationUtils.clone(studyVersionBo);
					newstudyVersionBo.setStudyVersion(studyVersionBo.getStudyVersion() + 0.1f);
					if(studyBo.getHasConsentDraft().equals(1)){
						newstudyVersionBo.setConsentVersion(studyVersionBo.getConsentVersion() + 0.1f);
					}
					/*if(studyBo.getHasActivityDraft().equals(1)){
						newstudyVersionBo.setActivityVersion(studyVersionBo.getActivityVersion() + 0.1f);
					}*/
					newstudyVersionBo.setVersionId(null);
					session.save(newstudyVersionBo);
				}else{
					newstudyVersionBo = new StudyVersionBo();
					newstudyVersionBo.setCustomStudyId(studyBo.getCustomStudyId());
					newstudyVersionBo.setActivityVersion(1.0f);
					newstudyVersionBo.setConsentVersion(1.0f);
					newstudyVersionBo.setStudyVersion(1.0f);
					session.save(newstudyVersionBo);
				}
				
				//create new Study and made it archive
				StudyBo studyDreaftBo = SerializationUtils.clone(studyBo);
				if(newstudyVersionBo!=null){
				 studyDreaftBo.setVersion(newstudyVersionBo.getStudyVersion());
				 studyDreaftBo.setLive(1);
				}
				studyDreaftBo.setId(null);
				session.save(studyDreaftBo);
				
				//Study Permission
				studyPermissionList = session.createQuery("from StudyPermissionBO where studyId="+studyBo.getId()).list();
				if(studyPermissionList!=null){
					for(StudyPermissionBO permissionBO:studyPermissionList){
						StudyPermissionBO studyPermissionBO = SerializationUtils.clone(permissionBO);
						studyPermissionBO.setStudyId(studyDreaftBo.getId());
						studyPermissionBO.setStudyPermissionId(null);
						session.save(studyPermissionBO);
					}
				}
				
				//Sequence
				StudySequenceBo studySequence = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId()).uniqueResult();
				StudySequenceBo newStudySequenceBo = SerializationUtils.clone(studySequence);
				newStudySequenceBo.setStudyId(studyDreaftBo.getId());
				newStudySequenceBo.setStudySequenceId(null);
				session.save(newStudySequenceBo);
				
				//Over View
				query = session.createQuery("from StudyPageBo where studyId="+studyBo.getId());
				studyPageBo = query.list();	
				if(studyPageBo!=null && !studyPageBo.isEmpty()){
					for(StudyPageBo pageBo:studyPageBo){
						StudyPageBo subPageBo = SerializationUtils.clone(pageBo);
						subPageBo.setStudyId(studyDreaftBo.getId());
						subPageBo.setPageId(null);
						session.save(subPageBo);
					}
				}
				
				//Eligibility
				query = session.getNamedQuery("getEligibiltyByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
				eligibilityBo = (EligibilityBo) query.uniqueResult();
				if(eligibilityBo!=null){
					EligibilityBo bo = SerializationUtils.clone(eligibilityBo);
					bo.setStudyId(studyDreaftBo.getId());
					bo.setId(null);
					session.save(bo);
				}
				
				//resources
				searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyBo.getId()+" AND RBO.status = 1 ORDER BY RBO.createdOn DESC ";
				query = session.createQuery(searchQuery);
				resourceBOList = query.list();
				if(resourceBOList!=null && !resourceBOList.isEmpty()){
					for(ResourceBO bo:resourceBOList){
						ResourceBO resourceBO = SerializationUtils.clone(bo);
						resourceBO.setStudyId(studyDreaftBo.getId());
						resourceBO.setId(null);
						session.save(resourceBO);
					}
				}
				//If Questionnaire updated flag -1 then update
				if(studyVersionBo==null  || (studyBo.getHasQuestionnaireDraft()!=null && studyBo.getHasQuestionnaireDraft().equals(1))){
					
				//update all Questionnaires to archive (live as 2)
				query = session.getNamedQuery("updateStudyQuestionnaireVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
				query.executeUpdate();	
					
				//Questionarries
				query = session.getNamedQuery("getQuestionariesByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
				questionnaires = query.list();
				if(questionnaires!=null && !questionnaires.isEmpty()){
					for(QuestionnaireBo questionnaireBo: questionnaires){
						Float questionnarieversion =  questionnaireBo.getVersion();
						QuestionnaireBo newQuestionnaireBo = SerializationUtils.clone(questionnaireBo);
						newQuestionnaireBo.setId(null);
						newQuestionnaireBo.setStudyId(studyDreaftBo.getId());
						if(studyVersionBo == null){
							newQuestionnaireBo.setVersion(1.0f);
							questionnaireBo.setVersion(1.0f);
			    		}else if(questionnaireBo.getIsChange()!=null && questionnaireBo.getIsChange().equals(1)){
			    			if(questionnarieversion.equals(0f)){
			    				questionnaireBo.setVersion(1.0f);
			    				newQuestionnaireBo.setVersion(1.0f);
			    			}else{
			    				newQuestionnaireBo.setVersion(questionnaireBo.getVersion() + 0.1f);
			    				questionnaireBo.setVersion(questionnaireBo.getVersion() + 0.1f);
			    			}
			    		}else{
			    			newQuestionnaireBo.setVersion(questionnaireBo.getVersion());
			    			questionnaireBo.setVersion(questionnaireBo.getVersion());
			    		}
						//newQuestionnaireBo.setVersion(newstudyVersionBo.getActivityVersion());
						newQuestionnaireBo.setLive(1);
						newQuestionnaireBo.setCustomStudyId(studyBo.getCustomStudyId());
						session.save(newQuestionnaireBo);
						
						/**Schedule Purpose creating draft Start **/
						if(StringUtils.isNotEmpty(questionnaireBo.getFrequency())){
							if(questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE)){
								searchQuery = "From QuestionnaireCustomScheduleBo QCSBO where QCSBO.questionnairesId="+questionnaireBo.getId();
								List<QuestionnaireCustomScheduleBo> questionnaireCustomScheduleList= session.createQuery(searchQuery).list();
							    if(questionnaireCustomScheduleList!=null && !questionnaireCustomScheduleList.isEmpty()){
							    	for(QuestionnaireCustomScheduleBo customScheduleBo: questionnaireCustomScheduleList){
							    		QuestionnaireCustomScheduleBo newCustomScheduleBo = SerializationUtils.clone(customScheduleBo);
							    		newCustomScheduleBo.setQuestionnairesId(newQuestionnaireBo.getId());
							    		newCustomScheduleBo.setId(null);
							    		session.save(newCustomScheduleBo);
							    	}
							    }
							}else{
								searchQuery = "From QuestionnairesFrequenciesBo QFBO where QFBO.questionnairesId="+questionnaireBo.getId();
								List<QuestionnairesFrequenciesBo> questionnairesFrequenciesList = session.createQuery(searchQuery).list();
								if(questionnairesFrequenciesList!=null && !questionnairesFrequenciesList.isEmpty()){
									for(QuestionnairesFrequenciesBo questionnairesFrequenciesBo: questionnairesFrequenciesList){
										QuestionnairesFrequenciesBo newQuestionnairesFrequenciesBo = SerializationUtils.clone(questionnairesFrequenciesBo);
										newQuestionnairesFrequenciesBo.setQuestionnairesId(newQuestionnaireBo.getId());
										newQuestionnairesFrequenciesBo.setId(null);
										session.save(newQuestionnairesFrequenciesBo);
									}
								}
							}
						}
						/** Schedule Purpose creating draft End **/
						
						/**  Content purpose creating draft Start **/
						
						List<Integer> destinationList = new ArrayList<>();
						Map<Integer, Integer> destionationMapList = new HashMap<>();
						
						List<QuestionnairesStepsBo> existedQuestionnairesStepsBoList  = null;
						List<QuestionnairesStepsBo> newQuestionnairesStepsBoList = new ArrayList<>();
						List<QuestionResponseSubTypeBo> existingQuestionResponseSubTypeList = new ArrayList<>();
						List<QuestionResponseSubTypeBo> newQuestionResponseSubTypeList = new ArrayList<>();
						query = session.getNamedQuery("getQuestionnaireStepSequenceNo").setInteger("questionnairesId", questionnaireBo.getId());
						existedQuestionnairesStepsBoList = query.list();
						if(existedQuestionnairesStepsBoList!=null && !existedQuestionnairesStepsBoList.isEmpty()){
							   for(QuestionnairesStepsBo questionnairesStepsBo:existedQuestionnairesStepsBoList){
								   Integer destionStep = questionnairesStepsBo.getDestinationStep();
								   if(destionStep.equals(0)){
									   destinationList.add(-1);
								   }else{
									   for(int i=0;i<existedQuestionnairesStepsBoList.size();i++){
										   if(existedQuestionnairesStepsBoList.get(i).getStepId() != null 
												   && destionStep.equals(existedQuestionnairesStepsBoList.get(i).getStepId())){
											   destinationList.add(i);
											   break;
										   }
									   } 
								   }
								   destionationMapList.put(questionnairesStepsBo.getSequenceNo(), questionnairesStepsBo.getStepId());
							   }
							for(QuestionnairesStepsBo questionnairesStepsBo:existedQuestionnairesStepsBoList){
								if(StringUtils.isNotEmpty(questionnairesStepsBo.getStepType())){
									QuestionnairesStepsBo newQuestionnairesStepsBo = SerializationUtils.clone(questionnairesStepsBo);
									 newQuestionnairesStepsBo.setQuestionnairesId(newQuestionnaireBo.getId());
									 newQuestionnairesStepsBo.setStepId(null);
									 session.save(newQuestionnairesStepsBo);
									if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.INSTRUCTION_STEP)){
										InstructionsBo instructionsBo =(InstructionsBo)session.getNamedQuery("getInstructionStep").setInteger("id", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
										  if(instructionsBo!=null){
											  InstructionsBo newInstructionsBo = SerializationUtils.clone(instructionsBo);
											  newInstructionsBo.setId(null);
											  session.save(newInstructionsBo);
											  
											  //updating new InstructionId
											  newQuestionnairesStepsBo.setInstructionFormId(newInstructionsBo.getId());
										  }
									}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTION_STEP)){
										QuestionsBo  questionsBo= (QuestionsBo)session.getNamedQuery("getQuestionStep").setInteger("stepId", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
										  if(questionsBo!=null){
											  //Question response subType 
											  List<QuestionResponseSubTypeBo> questionResponseSubTypeList = session.getNamedQuery("getQuestionSubResponse").setInteger("responseTypeId", questionsBo.getId()).list();
											  
											  //Question response Type 
											  questionReponseTypeBo = (QuestionReponseTypeBo) session.getNamedQuery("getQuestionResponse").setInteger("questionsResponseTypeId", questionsBo.getId()).uniqueResult();
											  
											  QuestionsBo newQuestionsBo = SerializationUtils.clone(questionsBo);
											  newQuestionsBo.setId(null);
											  session.save(newQuestionsBo);
											  
											//Question response Type 
											  if(questionReponseTypeBo!=null){
												  QuestionReponseTypeBo newQuestionReponseTypeBo =  SerializationUtils.clone(questionReponseTypeBo);
												  newQuestionReponseTypeBo.setResponseTypeId(null);
												  newQuestionReponseTypeBo.setQuestionsResponseTypeId(newQuestionsBo.getId());
												  session.save(newQuestionReponseTypeBo);
											  }
											  
											  //Question response subType 
											  if(questionResponseSubTypeList!= null && !questionResponseSubTypeList.isEmpty()){
												  existingQuestionResponseSubTypeList.addAll(questionResponseSubTypeList);
												  
												  for(QuestionResponseSubTypeBo questionResponseSubTypeBo: questionResponseSubTypeList){
													  QuestionResponseSubTypeBo newQuestionResponseSubTypeBo = SerializationUtils.clone(questionResponseSubTypeBo);
													  newQuestionResponseSubTypeBo.setResponseSubTypeValueId(null);
													  newQuestionResponseSubTypeBo.setResponseTypeId(newQuestionsBo.getId());
													  newQuestionResponseSubTypeBo.setDestinationStepId(null);
													  session.save(newQuestionResponseSubTypeBo);
													  newQuestionResponseSubTypeList.add(newQuestionResponseSubTypeBo);
												  }
											  }
											  
											  //updating new InstructionId
											  newQuestionnairesStepsBo.setInstructionFormId(newQuestionsBo.getId());
										  }
									}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.FORM_STEP)){
										  FormBo  formBo= (FormBo)session.getNamedQuery("getFormBoStep").setInteger("stepId", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
										  if(formBo!=null){
											  FormBo newFormBo = SerializationUtils.clone(formBo);
											  newFormBo.setFormId(null);
											  session.save(newFormBo);
											
											  List<FormMappingBo> formMappingBoList = session.getNamedQuery("getFormByFormId").setInteger("formId", formBo.getFormId()).list(); 
											  if(formMappingBoList!=null && !formMappingBoList.isEmpty()){
												  for(FormMappingBo formMappingBo : formMappingBoList){
													  FormMappingBo newMappingBo = SerializationUtils.clone(formMappingBo);
													  newMappingBo.setFormId(newFormBo.getFormId());
													  newMappingBo.setId(null);
													  
													  
													  
													  QuestionsBo  questionsBo= (QuestionsBo)session.getNamedQuery("getQuestionByFormId").setInteger("formId", formMappingBo.getQuestionId()).uniqueResult();
													  if(questionsBo!=null){
														  //Question response subType 
														  List<QuestionResponseSubTypeBo> questionResponseSubTypeList = session.getNamedQuery("getQuestionSubResponse").setInteger("responseTypeId", questionsBo.getId()).list();
														  
														  //Question response Type 
														  questionReponseTypeBo = (QuestionReponseTypeBo) session.getNamedQuery("getQuestionResponse").setInteger("questionsResponseTypeId", questionsBo.getId()).uniqueResult();
														  
														  QuestionsBo newQuestionsBo = SerializationUtils.clone(questionsBo);
														  newQuestionsBo.setId(null);
														  session.save(newQuestionsBo);
														  
														//Question response Type 
														  if(questionReponseTypeBo!=null){
															  QuestionReponseTypeBo newQuestionReponseTypeBo =  SerializationUtils.clone(questionReponseTypeBo);
															  newQuestionReponseTypeBo.setResponseTypeId(null);
															  newQuestionReponseTypeBo.setQuestionsResponseTypeId(newQuestionsBo.getId());
															  session.save(newQuestionReponseTypeBo);
														  }
														  
														  //Question response subType 
														  if(questionResponseSubTypeList!= null && !questionResponseSubTypeList.isEmpty()){
															 // existingQuestionResponseSubTypeList.addAll(questionResponseSubTypeList);
															  for(QuestionResponseSubTypeBo questionResponseSubTypeBo: questionResponseSubTypeList){
																  QuestionResponseSubTypeBo newQuestionResponseSubTypeBo = SerializationUtils.clone(questionResponseSubTypeBo);
																  newQuestionResponseSubTypeBo.setResponseSubTypeValueId(null);
																  newQuestionResponseSubTypeBo.setResponseTypeId(newQuestionsBo.getId());
																  session.save(newQuestionResponseSubTypeBo);
																  //newQuestionResponseSubTypeList.add(newQuestionResponseSubTypeBo);
															  }
														  }
														  
														  //adding questionId
														  newMappingBo.setQuestionId(newQuestionsBo.getId());
														  session.save(newMappingBo);
													  }
													  
												  }
											  }
											  //updating new formId
											  newQuestionnairesStepsBo.setInstructionFormId(newFormBo.getFormId());
											  
										  }
									}
									session.update(newQuestionnairesStepsBo);
									newQuestionnairesStepsBoList.add(newQuestionnairesStepsBo);
								}
							}
						}
						if (destinationList != null
										&& !destinationList.isEmpty()) {
									for (int i = 0; i < destinationList.size(); i++) {
										int desId = 0;
										if (destinationList.get(i) != -1) {
											desId = newQuestionnairesStepsBoList
													.get(destinationList.get(i))
													.getStepId();
										}
										newQuestionnairesStepsBoList.get(i)
												.setDestinationStep(desId);
										session.update(newQuestionnairesStepsBoList
												.get(i));
									}
						}
						List<Integer> sequenceSubTypeList = new ArrayList<>();
						List<Integer> destinationResList = new ArrayList<>();
						if(existingQuestionResponseSubTypeList!=null && !existingQuestionResponseSubTypeList.isEmpty()){
							for(QuestionResponseSubTypeBo questionResponseSubTypeBo:existingQuestionResponseSubTypeList){
								if(questionResponseSubTypeBo.getDestinationStepId()==null){
									sequenceSubTypeList.add(null);
								}else if(questionResponseSubTypeBo.getDestinationStepId()!=null &&
										questionResponseSubTypeBo.getDestinationStepId().equals(0)){
									    sequenceSubTypeList.add(-1);
								}else{
									if(existedQuestionnairesStepsBoList!=null && !existedQuestionnairesStepsBoList.isEmpty()){
										for(QuestionnairesStepsBo questionnairesStepsBo: existedQuestionnairesStepsBoList){
											if(questionResponseSubTypeBo.getDestinationStepId()!=null 
													&& questionResponseSubTypeBo.getDestinationStepId().equals(questionnairesStepsBo.getStepId())){
												sequenceSubTypeList.add(questionnairesStepsBo.getSequenceNo());
												break;
											}
										}
										
									}
								}
							}
						}
						if (sequenceSubTypeList != null
								&& !sequenceSubTypeList.isEmpty()) {
								for (int i = 0; i < sequenceSubTypeList.size(); i++) {
									Integer desId = null;
									if(sequenceSubTypeList.get(i)==null){
										desId = null;
									}else if(sequenceSubTypeList.get(i).equals(-1)){
										desId = 0;
									}else{
										for(QuestionnairesStepsBo questionnairesStepsBo: newQuestionnairesStepsBoList){
											if (sequenceSubTypeList.get(i).equals(questionnairesStepsBo.getSequenceNo())){
												 desId = questionnairesStepsBo.getStepId();
												 break;
											}
									    }
									}
								destinationResList.add(desId);	
							}
							for (int i = 0; i < destinationResList.size(); i++) {
								newQuestionResponseSubTypeList.get(i)
										.setDestinationStepId(destinationResList.get(i));
								session.update(newQuestionResponseSubTypeList
										.get(i));
							}
				        }
						/**  Content purpose creating draft End **/
					   }
						//Executing draft version to 0 
				    	session.createQuery("UPDATE QuestionnaireBo set live=0, isChange = 0 where studyId="+studyBo.getId()).executeUpdate();
					}//If Questionarries updated flag -1 then update End
				
				   }//In Questionnarie change or not 
				
				//In ActiveTask change or not Start 
				if(studyVersionBo == null || (studyBo.getHasActivetaskDraft()!=null && studyBo.getHasActivetaskDraft().equals(1))){
					//update all ActiveTasks to archive (live as 2)
					query = session.getNamedQuery("updateStudyActiveTaskVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
					query.executeUpdate();
					
				    //ActiveTasks
					query = session.getNamedQuery("ActiveTaskBo.getActiveTasksByByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
					        activeTasks = query.list();
				    if(activeTasks!=null && !activeTasks.isEmpty()){
				    	for(ActiveTaskBo activeTaskBo:activeTasks){
				    		Float activeTaskversion =  activeTaskBo.getVersion();
				    		ActiveTaskBo newActiveTaskBo = SerializationUtils.clone(activeTaskBo);
				    		newActiveTaskBo.setId(null);
				    		newActiveTaskBo.setStudyId(studyDreaftBo.getId());
				    		if(studyVersionBo == null){
				    		    newActiveTaskBo.setVersion(1.0f);
				    		    activeTaskBo.setVersion(1.0f);
				    		}else if(activeTaskBo.getIsChange()!=null && activeTaskBo.getIsChange().equals(1)){
				    			if(activeTaskversion.equals(0f)){
				    				activeTaskBo.setVersion(1.0f);
				    				newActiveTaskBo.setVersion(1.0f);
				    			}else{
				    			newActiveTaskBo.setVersion(activeTaskBo.getVersion() + 0.1f);
				    			activeTaskBo.setVersion(activeTaskBo.getVersion() + 0.1f);
				    			}
				    		}else{
				    			newActiveTaskBo.setVersion(activeTaskBo.getVersion());
				    			activeTaskBo.setVersion(activeTaskBo.getVersion());
				    		}
				    		newActiveTaskBo.setLive(1);
				    		newActiveTaskBo.setCustomStudyId(studyBo.getCustomStudyId());
				    		session.save(newActiveTaskBo);
				    		
				    		/**Schedule Purpose creating draft Start **/
							if(StringUtils.isNotEmpty(activeTaskBo.getFrequency())){
								if(activeTaskBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE)){
									searchQuery = "From ActiveTaskCustomScheduleBo QCSBO where QCSBO.activeTaskId="+activeTaskBo.getId();
									List<ActiveTaskCustomScheduleBo> activeTaskCustomScheduleList= session.createQuery(searchQuery).list();
								    if(activeTaskCustomScheduleList!=null && !activeTaskCustomScheduleList.isEmpty()){
								    	for(ActiveTaskCustomScheduleBo customScheduleBo: activeTaskCustomScheduleList){
								    		ActiveTaskCustomScheduleBo newCustomScheduleBo = SerializationUtils.clone(customScheduleBo);
								    		newCustomScheduleBo.setActiveTaskId(newActiveTaskBo.getId());
								    		newCustomScheduleBo.setId(null);
								    		session.save(newCustomScheduleBo);
								    	}
								    }
								}else{
									searchQuery = "From ActiveTaskFrequencyBo QFBO where QFBO.activeTaskId="+activeTaskBo.getId();
									List<ActiveTaskFrequencyBo> activeTaskFrequenciesList = session.createQuery(searchQuery).list();
									if(activeTaskFrequenciesList!=null && !activeTaskFrequenciesList.isEmpty()){
										for(ActiveTaskFrequencyBo activeTaskFrequenciesBo: activeTaskFrequenciesList){
											ActiveTaskFrequencyBo newFrequenciesBo = SerializationUtils.clone(activeTaskFrequenciesBo);
											newFrequenciesBo.setActiveTaskId(newActiveTaskBo.getId());
											newFrequenciesBo.setId(null);
											session.save(newFrequenciesBo);
										}
									}
								}
							}
							/** Schedule Purpose creating draft End **/
							
							/** Content Purpose creating draft Start **/
							query = session.getNamedQuery("getAttributeListByActiveTAskId").setInteger("activeTaskId", activeTaskBo.getId());
							List<ActiveTaskAtrributeValuesBo> activeTaskAtrributeValuesBoList= query.list();
							if(activeTaskAtrributeValuesBoList!=null && !activeTaskAtrributeValuesBoList.isEmpty()){
							  for(ActiveTaskAtrributeValuesBo activeTaskAtrributeValuesBo: activeTaskAtrributeValuesBoList){
								  ActiveTaskAtrributeValuesBo newActiveTaskAtrributeValuesBo = SerializationUtils.clone(activeTaskAtrributeValuesBo);
								  newActiveTaskAtrributeValuesBo.setActiveTaskId(newActiveTaskBo.getId());
								  newActiveTaskAtrributeValuesBo.setAttributeValueId(null);
								  session.save(newActiveTaskAtrributeValuesBo);
							  }
								
							}
							/** Content Purpose creating draft End **/
				    	}
				    	//Executing draft version to 0 
				    	session.createQuery("UPDATE ActiveTaskBo set live=0, isChange = 0 where studyId="+studyBo.getId()).executeUpdate();
				    }//Active TAsk End
				} //In ActiveTask change or not 
				//Activities End
				if(studyVersionBo == null || studyBo.getHasConsentDraft().equals(1)){
					//update all consentBo to archive (live as 2)
					query = session.getNamedQuery("updateStudyConsentVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
					query.executeUpdate();
					
					//update all consentInfoBo to archive (live as 2)
					query = session.getNamedQuery("updateStudyConsentInfoVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
					query.executeUpdate();
					
					//If Consent updated flag -1 then update
					query = session.getNamedQuery("getConsentByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
					List<ConsentBo> consentBoList = query.list();
					if(consentBoList!=null && !consentBoList.isEmpty()){
						for(ConsentBo consentBo: consentBoList){
							ConsentBo newConsentBo = SerializationUtils.clone(consentBo);
							newConsentBo.setId(null);
							newConsentBo.setStudyId(studyDreaftBo.getId());
							newConsentBo.setVersion(newstudyVersionBo.getConsentVersion());
							newConsentBo.setLive(1);
							newConsentBo.setCustomStudyId(studyBo.getCustomStudyId());
							session.save(newConsentBo);
						}
					}
					query = session.getNamedQuery("getConsentInfoByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
					List<ConsentInfoBo> consentInfoBoList = query.list();
					if(consentInfoBoList!=null && !consentInfoBoList.isEmpty()){
						for(ConsentInfoBo consentInfoBo:consentInfoBoList){
							ConsentInfoBo newConsentInfoBo = SerializationUtils.clone(consentInfoBo);
							newConsentInfoBo.setId(null);
							newConsentInfoBo.setStudyId(studyDreaftBo.getId());
							newConsentInfoBo.setVersion(newstudyVersionBo.getConsentVersion());
							newConsentInfoBo.setCustomStudyId(studyBo.getCustomStudyId());
							newConsentInfoBo.setLive(1);
							session.save(newConsentInfoBo);
						}
					}
				}
				//updating the edited study to draft 
				if(studyDreaftBo!=null && studyDreaftBo.getId()!=null){
				   studyBo.setVersion(0f);
				  // studyBo.setHasActivityDraft(0);
				   studyBo.setHasActivetaskDraft(0);
				   studyBo.setHasQuestionnaireDraft(0);
				   studyBo.setHasConsentDraft(0);
				   studyBo.setHasStudyDraft(0);
				   studyBo.setLive(0);
				   session.update(studyBo);
				  
				   //Updating Notification and Resources
				   session.createQuery("UPDATE NotificationBO set customStudyId='"+studyBo.getCustomStudyId()+"' where studyId="+studyBo.getId()).executeUpdate();
				   session.createQuery("UPDATE Checklist set customStudyId='"+studyBo.getCustomStudyId()+"' where studyId="+studyBo.getId()).executeUpdate();
				}
				
				
				
				message = FdahpStudyDesignerConstants.SUCCESS;
				}
				
			  }
			//transaction.commit();
		}catch(Exception e){
			//transaction.rollback();
			logger.error("StudyDAOImpl - studyDraftCreation() - ERROR " , e);
		}
		logger.info("StudyDAOImpl - studyDraftCreation() - Ends");
		
		return message;
	}
	/*public String studyDraftCreation(StudyBo studyBo, Session session){
		logger.info("StudyDAOImpl - studyDraftCreation() - Starts");
		List<StudyPageBo> studyPageBo = null;
		List<StudyPermissionBO> studyPermissionList = null;
		EligibilityBo eligibilityBo = null;
		List<ResourceBO> resourceBOList = null;
		StudyVersionBo studyVersionBo = null;
		StudyVersionBo  newstudyVersionBo = null;
		boolean flag = true;
		String message = FdahpStudyDesignerConstants.FAILURE;
		List<QuestionnaireBo> questionnaires = null;
		List<ActiveTaskBo> activeTasks = null;
		String searchQuery = "";
		QuestionReponseTypeBo questionReponseTypeBo = null;
		try{
			if(session!= null) {
				transaction = session.beginTransaction();
			}
			if(studyBo!=null){
				//if already lunch if study hasStudyDraft()==1 , then update and create draft version , otherwise not
				query = session.getNamedQuery("getStudyByCustomStudyId").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
				query.setMaxResults(1);
				studyVersionBo = (StudyVersionBo)query.uniqueResult();
				if(studyVersionBo!=null && (studyBo.getHasStudyDraft().equals(0))){
					flag = false;
				}
				if(flag){
				//version update in study_version table 
				if(studyVersionBo!=null){
					//update all studies to archive (live as 2)
					query = session.getNamedQuery("updateStudyVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
					query.executeUpdate();
					
					newstudyVersionBo = SerializationUtils.clone(studyVersionBo);
					newstudyVersionBo.setStudyVersion(studyVersionBo.getStudyVersion() + 0.1f);
					if(studyBo.getHasConsentDraft().equals(1)){
						newstudyVersionBo.setConsentVersion(studyVersionBo.getConsentVersion() + 0.1f);
					}
					if(studyBo.getHasActivityDraft().equals(1)){
						newstudyVersionBo.setActivityVersion(studyVersionBo.getActivityVersion() + 0.1f);
					}
					newstudyVersionBo.setVersionId(null);
					session.save(newstudyVersionBo);
				}else{
					newstudyVersionBo = new StudyVersionBo();
					newstudyVersionBo.setCustomStudyId(studyBo.getCustomStudyId());
					newstudyVersionBo.setActivityVersion(1.0f);
					newstudyVersionBo.setConsentVersion(1.0f);
					newstudyVersionBo.setStudyVersion(1.0f);
					session.save(newstudyVersionBo);
				}
				
				//create new Study and made it archive
				StudyBo studyDreaftBo = SerializationUtils.clone(studyBo);
				if(newstudyVersionBo!=null){
				 studyDreaftBo.setVersion(newstudyVersionBo.getStudyVersion());
				 studyDreaftBo.setLive(1);
				}
				studyDreaftBo.setId(null);
				session.save(studyDreaftBo);
				
				//Study Permission
				studyPermissionList = session.createQuery("from StudyPermissionBO where studyId="+studyBo.getId()).list();
				if(studyPermissionList!=null){
					for(StudyPermissionBO permissionBO:studyPermissionList){
						StudyPermissionBO studyPermissionBO = SerializationUtils.clone(permissionBO);
						studyPermissionBO.setStudyId(studyDreaftBo.getId());
						studyPermissionBO.setStudyPermissionId(null);
						session.save(studyPermissionBO);
					}
				}
				
				//Sequence
				StudySequenceBo studySequence = (StudySequenceBo) session.getNamedQuery(FdahpStudyDesignerConstants.STUDY_SEQUENCE_BY_ID).setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId()).uniqueResult();
				StudySequenceBo newStudySequenceBo = SerializationUtils.clone(studySequence);
				newStudySequenceBo.setStudyId(studyDreaftBo.getId());
				newStudySequenceBo.setStudySequenceId(null);
				session.save(newStudySequenceBo);
				
				//Over View
				query = session.createQuery("from StudyPageBo where studyId="+studyBo.getId());
				studyPageBo = query.list();	
				if(studyPageBo!=null && !studyPageBo.isEmpty()){
					for(StudyPageBo pageBo:studyPageBo){
						StudyPageBo subPageBo = SerializationUtils.clone(pageBo);
						subPageBo.setStudyId(studyDreaftBo.getId());
						subPageBo.setPageId(null);
						session.save(subPageBo);
					}
				}
				
				//Eligibility
				query = session.getNamedQuery("getEligibiltyByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
				eligibilityBo = (EligibilityBo) query.uniqueResult();
				if(eligibilityBo!=null){
					EligibilityBo bo = SerializationUtils.clone(eligibilityBo);
					bo.setStudyId(studyDreaftBo.getId());
					bo.setId(null);
					session.save(bo);
				}
				
				//resources
				searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyBo.getId()+" AND RBO.status = 1 ORDER BY RBO.createdOn DESC ";
				query = session.createQuery(searchQuery);
				resourceBOList = query.list();
				if(resourceBOList!=null && !resourceBOList.isEmpty()){
					for(ResourceBO bo:resourceBOList){
						ResourceBO resourceBO = SerializationUtils.clone(bo);
						resourceBO.setStudyId(studyDreaftBo.getId());
						resourceBO.setId(null);
						session.save(resourceBO);
					}
				}
				
				//If Activities updated flag -1 then update
				if(studyVersionBo == null || studyBo.getHasActivityDraft().equals(1)) {
				//update all Questionnaires to archive (live as 2)
				query = session.getNamedQuery("updateStudyQuestionnaireVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
				query.executeUpdate();
				
				//update all ActiveTasks to archive (live as 2)
				query = session.getNamedQuery("updateStudyActiveTaskVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
				query.executeUpdate();
					
				//Questionarries
				query = session.getNamedQuery("getQuestionariesByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
				questionnaires = query.list();
				if(questionnaires!=null && !questionnaires.isEmpty()){
					for(QuestionnaireBo questionnaireBo: questionnaires){
					    
						QuestionnaireBo newQuestionnaireBo = SerializationUtils.clone(questionnaireBo);
						newQuestionnaireBo.setId(null);
						newQuestionnaireBo.setStudyId(studyDreaftBo.getId());
						newQuestionnaireBo.setVersion(newstudyVersionBo.getActivityVersion());
						newQuestionnaireBo.setLive(1);
						newQuestionnaireBo.setCustomStudyId(studyBo.getCustomStudyId());
						session.save(newQuestionnaireBo);
						
						*//**Schedule Purpose creating draft Start **//*
						if(StringUtils.isNotEmpty(questionnaireBo.getFrequency())){
							if(questionnaireBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE)){
								searchQuery = "From QuestionnaireCustomScheduleBo QCSBO where QCSBO.questionnairesId="+questionnaireBo.getId();
								List<QuestionnaireCustomScheduleBo> questionnaireCustomScheduleList= session.createQuery(searchQuery).list();
							    if(questionnaireCustomScheduleList!=null && !questionnaireCustomScheduleList.isEmpty()){
							    	for(QuestionnaireCustomScheduleBo customScheduleBo: questionnaireCustomScheduleList){
							    		QuestionnaireCustomScheduleBo newCustomScheduleBo = SerializationUtils.clone(customScheduleBo);
							    		newCustomScheduleBo.setQuestionnairesId(newQuestionnaireBo.getId());
							    		newCustomScheduleBo.setId(null);
							    		session.save(newCustomScheduleBo);
							    	}
							    }
							}else{
								searchQuery = "From QuestionnairesFrequenciesBo QFBO where QFBO.questionnairesId="+questionnaireBo.getId();
								List<QuestionnairesFrequenciesBo> questionnairesFrequenciesList = session.createQuery(searchQuery).list();
								if(questionnairesFrequenciesList!=null && !questionnairesFrequenciesList.isEmpty()){
									for(QuestionnairesFrequenciesBo questionnairesFrequenciesBo: questionnairesFrequenciesList){
										QuestionnairesFrequenciesBo newQuestionnairesFrequenciesBo = SerializationUtils.clone(questionnairesFrequenciesBo);
										newQuestionnairesFrequenciesBo.setQuestionnairesId(newQuestionnaireBo.getId());
										newQuestionnairesFrequenciesBo.setId(null);
										session.save(newQuestionnairesFrequenciesBo);
									}
								}
							}
						}
						*//** Schedule Purpose creating draft End **//*
						
						*//**  Content purpose creating draft Start **//*
						
						List<Integer> destinationList = new ArrayList<>();
						Map<Integer, Integer> destionationMapList = new HashMap<>();
						
						List<QuestionnairesStepsBo> existedQuestionnairesStepsBoList  = null;
						List<QuestionnairesStepsBo> newQuestionnairesStepsBoList = new ArrayList<>();
						List<QuestionResponseSubTypeBo> existingQuestionResponseSubTypeList = new ArrayList<>();
						List<QuestionResponseSubTypeBo> newQuestionResponseSubTypeList = new ArrayList<>();
						query = session.getNamedQuery("getQuestionnaireStepSequenceNo").setInteger("questionnairesId", questionnaireBo.getId());
						existedQuestionnairesStepsBoList = query.list();
						if(existedQuestionnairesStepsBoList!=null && !existedQuestionnairesStepsBoList.isEmpty()){
							   for(QuestionnairesStepsBo questionnairesStepsBo:existedQuestionnairesStepsBoList){
								   Integer destionStep = questionnairesStepsBo.getDestinationStep();
								   if(destionStep.equals(0)){
									   destinationList.add(-1);
								   }else{
									   for(int i=0;i<existedQuestionnairesStepsBoList.size();i++){
										   if(existedQuestionnairesStepsBoList.get(i).getStepId() != null 
												   && destionStep.equals(existedQuestionnairesStepsBoList.get(i).getStepId())){
											   destinationList.add(i);
											   break;
										   }
									   } 
								   }
								   destionationMapList.put(questionnairesStepsBo.getSequenceNo(), questionnairesStepsBo.getStepId());
							   }
							for(QuestionnairesStepsBo questionnairesStepsBo:existedQuestionnairesStepsBoList){
								if(StringUtils.isNotEmpty(questionnairesStepsBo.getStepType())){
									QuestionnairesStepsBo newQuestionnairesStepsBo = SerializationUtils.clone(questionnairesStepsBo);
									 newQuestionnairesStepsBo.setQuestionnairesId(newQuestionnaireBo.getId());
									 newQuestionnairesStepsBo.setStepId(null);
									 session.save(newQuestionnairesStepsBo);
									if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.INSTRUCTION_STEP)){
										InstructionsBo instructionsBo =(InstructionsBo)session.getNamedQuery("getInstructionStep").setInteger("id", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
										  if(instructionsBo!=null){
											  InstructionsBo newInstructionsBo = SerializationUtils.clone(instructionsBo);
											  newInstructionsBo.setId(null);
											  session.save(newInstructionsBo);
											  
											  //updating new InstructionId
											  newQuestionnairesStepsBo.setInstructionFormId(newInstructionsBo.getId());
										  }
									}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTION_STEP)){
										QuestionsBo  questionsBo= (QuestionsBo)session.getNamedQuery("getQuestionStep").setInteger("stepId", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
										  if(questionsBo!=null){
											  //Question response subType 
											  List<QuestionResponseSubTypeBo> questionResponseSubTypeList = session.getNamedQuery("getQuestionSubResponse").setInteger("responseTypeId", questionsBo.getId()).list();
											  
											  //Question response Type 
											  questionReponseTypeBo = (QuestionReponseTypeBo) session.getNamedQuery("getQuestionResponse").setInteger("questionsResponseTypeId", questionsBo.getId()).uniqueResult();
											  
											  QuestionsBo newQuestionsBo = SerializationUtils.clone(questionsBo);
											  newQuestionsBo.setId(null);
											  session.save(newQuestionsBo);
											  
											//Question response Type 
											  if(questionReponseTypeBo!=null){
												  QuestionReponseTypeBo newQuestionReponseTypeBo =  SerializationUtils.clone(questionReponseTypeBo);
												  newQuestionReponseTypeBo.setResponseTypeId(null);
												  newQuestionReponseTypeBo.setQuestionsResponseTypeId(newQuestionsBo.getId());
												  session.save(newQuestionReponseTypeBo);
											  }
											  
											  //Question response subType 
											  if(questionResponseSubTypeList!= null && !questionResponseSubTypeList.isEmpty()){
												  existingQuestionResponseSubTypeList.addAll(questionResponseSubTypeList);
												  
												  for(QuestionResponseSubTypeBo questionResponseSubTypeBo: questionResponseSubTypeList){
													  QuestionResponseSubTypeBo newQuestionResponseSubTypeBo = SerializationUtils.clone(questionResponseSubTypeBo);
													  newQuestionResponseSubTypeBo.setResponseSubTypeValueId(null);
													  newQuestionResponseSubTypeBo.setResponseTypeId(newQuestionsBo.getId());
													  newQuestionResponseSubTypeBo.setDestinationStepId(null);
													  session.save(newQuestionResponseSubTypeBo);
													  newQuestionResponseSubTypeList.add(newQuestionResponseSubTypeBo);
												  }
											  }
											  
											  //updating new InstructionId
											  newQuestionnairesStepsBo.setInstructionFormId(newQuestionsBo.getId());
										  }
									}else if(questionnairesStepsBo.getStepType().equalsIgnoreCase(FdahpStudyDesignerConstants.FORM_STEP)){
										  FormBo  formBo= (FormBo)session.getNamedQuery("getFormBoStep").setInteger("stepId", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
										  if(formBo!=null){
											  FormBo newFormBo = SerializationUtils.clone(formBo);
											  newFormBo.setFormId(null);
											  session.save(newFormBo);
											
											  List<FormMappingBo> formMappingBoList = session.getNamedQuery("getFormByFormId").setInteger("formId", formBo.getFormId()).list(); 
											  if(formMappingBoList!=null && !formMappingBoList.isEmpty()){
												  for(FormMappingBo formMappingBo : formMappingBoList){
													  FormMappingBo newMappingBo = SerializationUtils.clone(formMappingBo);
													  newMappingBo.setFormId(newFormBo.getFormId());
													  newMappingBo.setId(null);
													  
													  
													  
													  QuestionsBo  questionsBo= (QuestionsBo)session.getNamedQuery("getQuestionByFormId").setInteger("formId", formMappingBo.getQuestionId()).uniqueResult();
													  if(questionsBo!=null){
														  //Question response subType 
														  List<QuestionResponseSubTypeBo> questionResponseSubTypeList = session.getNamedQuery("getQuestionSubResponse").setInteger("responseTypeId", questionsBo.getId()).list();
														  
														  //Question response Type 
														  questionReponseTypeBo = (QuestionReponseTypeBo) session.getNamedQuery("getQuestionResponse").setInteger("questionsResponseTypeId", questionsBo.getId()).uniqueResult();
														  
														  QuestionsBo newQuestionsBo = SerializationUtils.clone(questionsBo);
														  newQuestionsBo.setId(null);
														  session.save(newQuestionsBo);
														  
														//Question response Type 
														  if(questionReponseTypeBo!=null){
															  QuestionReponseTypeBo newQuestionReponseTypeBo =  SerializationUtils.clone(questionReponseTypeBo);
															  newQuestionReponseTypeBo.setResponseTypeId(null);
															  newQuestionReponseTypeBo.setQuestionsResponseTypeId(newQuestionsBo.getId());
															  session.save(newQuestionReponseTypeBo);
														  }
														  
														  //Question response subType 
														  if(questionResponseSubTypeList!= null && !questionResponseSubTypeList.isEmpty()){
															 // existingQuestionResponseSubTypeList.addAll(questionResponseSubTypeList);
															  for(QuestionResponseSubTypeBo questionResponseSubTypeBo: questionResponseSubTypeList){
																  QuestionResponseSubTypeBo newQuestionResponseSubTypeBo = SerializationUtils.clone(questionResponseSubTypeBo);
																  newQuestionResponseSubTypeBo.setResponseSubTypeValueId(null);
																  newQuestionResponseSubTypeBo.setResponseTypeId(newQuestionsBo.getId());
																  session.save(newQuestionResponseSubTypeBo);
																  //newQuestionResponseSubTypeList.add(newQuestionResponseSubTypeBo);
															  }
														  }
														  
														  //adding questionId
														  newMappingBo.setQuestionId(newQuestionsBo.getId());
														  session.save(newMappingBo);
													  }
													  
												  }
											  }
											  //updating new formId
											  newQuestionnairesStepsBo.setInstructionFormId(newFormBo.getFormId());
											  
										  }
									}
									session.update(newQuestionnairesStepsBo);
									newQuestionnairesStepsBoList.add(newQuestionnairesStepsBo);
								}
							}
						}
						if (destinationList != null
										&& !destinationList.isEmpty()) {
									for (int i = 0; i < destinationList.size(); i++) {
										int desId = 0;
										if (destinationList.get(i) != -1) {
											desId = newQuestionnairesStepsBoList
													.get(destinationList.get(i))
													.getStepId();
										}
										newQuestionnairesStepsBoList.get(i)
												.setDestinationStep(desId);
										session.update(newQuestionnairesStepsBoList
												.get(i));
									}
						}
						List<Integer> sequenceSubTypeList = new ArrayList<>();
						List<Integer> destinationResList = new ArrayList<>();
						if(existingQuestionResponseSubTypeList!=null && !existingQuestionResponseSubTypeList.isEmpty()){
							for(QuestionResponseSubTypeBo questionResponseSubTypeBo:existingQuestionResponseSubTypeList){
								if(questionResponseSubTypeBo.getDestinationStepId()==null){
									sequenceSubTypeList.add(null);
								}else if(questionResponseSubTypeBo.getDestinationStepId()!=null &&
										questionResponseSubTypeBo.getDestinationStepId().equals(0)){
									    sequenceSubTypeList.add(-1);
								}else{
									if(existedQuestionnairesStepsBoList!=null && !existedQuestionnairesStepsBoList.isEmpty()){
										for(QuestionnairesStepsBo questionnairesStepsBo: existedQuestionnairesStepsBoList){
											if(questionResponseSubTypeBo.getDestinationStepId()!=null 
													&& questionResponseSubTypeBo.getDestinationStepId().equals(questionnairesStepsBo.getStepId())){
												sequenceSubTypeList.add(questionnairesStepsBo.getSequenceNo());
												break;
											}
										}
										
									}
								}
							}
						}
						if (sequenceSubTypeList != null
								&& !sequenceSubTypeList.isEmpty()) {
								for (int i = 0; i < sequenceSubTypeList.size(); i++) {
									Integer desId = null;
									if(sequenceSubTypeList.get(i)==null){
										desId = null;
									}else if(sequenceSubTypeList.get(i).equals(-1)){
										desId = 0;
									}else{
										for(QuestionnairesStepsBo questionnairesStepsBo: newQuestionnairesStepsBoList){
											if (sequenceSubTypeList.get(i).equals(questionnairesStepsBo.getSequenceNo())){
												 desId = questionnairesStepsBo.getStepId();
												 break;
											}
									    }
									}
								destinationResList.add(desId);	
							}
							for (int i = 0; i < destinationResList.size(); i++) {
								newQuestionResponseSubTypeList.get(i)
										.setDestinationStepId(destinationResList.get(i));
								session.update(newQuestionResponseSubTypeList
										.get(i));
							}
				        }
						*//**  Content purpose creating draft End **//*
					   }
					}//If Questionarries updated flag -1 then update End
				
				    //ActiveTasks
					query = session.getNamedQuery("ActiveTaskBo.getActiveTasksByByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
					        activeTasks = query.list();
				    if(activeTasks!=null && !activeTasks.isEmpty()){
				    	for(ActiveTaskBo activeTaskBo:activeTasks){
				    		
				    		ActiveTaskBo newActiveTaskBo = SerializationUtils.clone(activeTaskBo);
				    		newActiveTaskBo.setId(null);
				    		newActiveTaskBo.setStudyId(studyDreaftBo.getId());
				    		newActiveTaskBo.setVersion(newstudyVersionBo.getActivityVersion());
				    		newActiveTaskBo.setLive(1);
				    		newActiveTaskBo.setCustomStudyId(studyBo.getCustomStudyId());
				    		session.save(newActiveTaskBo);
				    		
				    		*//**Schedule Purpose creating draft Start **//*
							if(StringUtils.isNotEmpty(activeTaskBo.getFrequency())){
								if(activeTaskBo.getFrequency().equalsIgnoreCase(FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE)){
									searchQuery = "From ActiveTaskCustomScheduleBo QCSBO where QCSBO.activeTaskId="+activeTaskBo.getId();
									List<ActiveTaskCustomScheduleBo> activeTaskCustomScheduleList= session.createQuery(searchQuery).list();
								    if(activeTaskCustomScheduleList!=null && !activeTaskCustomScheduleList.isEmpty()){
								    	for(ActiveTaskCustomScheduleBo customScheduleBo: activeTaskCustomScheduleList){
								    		ActiveTaskCustomScheduleBo newCustomScheduleBo = SerializationUtils.clone(customScheduleBo);
								    		newCustomScheduleBo.setActiveTaskId(newActiveTaskBo.getId());
								    		newCustomScheduleBo.setId(null);
								    		session.save(newCustomScheduleBo);
								    	}
								    }
								}else{
									searchQuery = "From ActiveTaskFrequencyBo QFBO where QFBO.activeTaskId="+activeTaskBo.getId();
									List<ActiveTaskFrequencyBo> activeTaskFrequenciesList = session.createQuery(searchQuery).list();
									if(activeTaskFrequenciesList!=null && !activeTaskFrequenciesList.isEmpty()){
										for(ActiveTaskFrequencyBo activeTaskFrequenciesBo: activeTaskFrequenciesList){
											ActiveTaskFrequencyBo newFrequenciesBo = SerializationUtils.clone(activeTaskFrequenciesBo);
											newFrequenciesBo.setActiveTaskId(newActiveTaskBo.getId());
											newFrequenciesBo.setId(null);
											session.save(newFrequenciesBo);
										}
									}
								}
							}
							*//** Schedule Purpose creating draft End **//*
							
							*//** Content Purpose creating draft Start **//*
							query = session.getNamedQuery("getAttributeListByActiveTAskId").setInteger("activeTaskId", activeTaskBo.getId());
							List<ActiveTaskAtrributeValuesBo> activeTaskAtrributeValuesBoList= query.list();
							if(activeTaskAtrributeValuesBoList!=null && !activeTaskAtrributeValuesBoList.isEmpty()){
							  for(ActiveTaskAtrributeValuesBo activeTaskAtrributeValuesBo: activeTaskAtrributeValuesBoList){
								  ActiveTaskAtrributeValuesBo newActiveTaskAtrributeValuesBo = SerializationUtils.clone(activeTaskAtrributeValuesBo);
								  newActiveTaskAtrributeValuesBo.setActiveTaskId(newActiveTaskBo.getId());
								  newActiveTaskAtrributeValuesBo.setAttributeValueId(null);
								  session.save(newActiveTaskAtrributeValuesBo);
							  }
								
							}
							*//** Content Purpose creating draft End **//*
							
				    	}
				    }//Active TAsk End
				//Activities End
				if(studyVersionBo == null || studyBo.getHasConsentDraft().equals(1)){
					//update all consentBo to archive (live as 2)
					query = session.getNamedQuery("updateStudyConsentVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
					query.executeUpdate();
					
					//update all consentInfoBo to archive (live as 2)
					query = session.getNamedQuery("updateStudyConsentInfoVersion").setString(FdahpStudyDesignerConstants.CUSTOM_STUDY_ID, studyBo.getCustomStudyId());
					query.executeUpdate();
					
					//If Consent updated flag -1 then update
					query = session.getNamedQuery("getConsentByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
					List<ConsentBo> consentBoList = query.list();
					if(consentBoList!=null && !consentBoList.isEmpty()){
						for(ConsentBo consentBo: consentBoList){
							ConsentBo newConsentBo = SerializationUtils.clone(consentBo);
							newConsentBo.setId(null);
							newConsentBo.setStudyId(studyDreaftBo.getId());
							newConsentBo.setVersion(newstudyVersionBo.getConsentVersion());
							newConsentBo.setLive(1);
							newConsentBo.setCustomStudyId(studyBo.getCustomStudyId());
							session.save(newConsentBo);
						}
					}
					query = session.getNamedQuery("getConsentInfoByStudyId").setInteger(FdahpStudyDesignerConstants.STUDY_ID, studyBo.getId());
					List<ConsentInfoBo> consentInfoBoList = query.list();
					if(consentInfoBoList!=null && !consentInfoBoList.isEmpty()){
						for(ConsentInfoBo consentInfoBo:consentInfoBoList){
							ConsentInfoBo newConsentInfoBo = SerializationUtils.clone(consentInfoBo);
							newConsentInfoBo.setId(null);
							newConsentInfoBo.setStudyId(studyDreaftBo.getId());
							newConsentInfoBo.setVersion(newstudyVersionBo.getConsentVersion());
							newConsentInfoBo.setCustomStudyId(studyBo.getCustomStudyId());
							newConsentInfoBo.setLive(1);
							session.save(newConsentInfoBo);
						}
					}
				}
				}
				//updating the edited study to draft 
				if(studyDreaftBo!=null && studyDreaftBo.getId()!=null){
				   studyBo.setVersion(0f);
				   studyBo.setHasActivityDraft(0);
				   studyBo.setHasConsentDraft(0);
				   studyBo.setHasStudyDraft(0);
				   studyBo.setLive(0);
				   session.update(studyBo);
				  
				   //Updating Notification and Resources
				   session.createQuery("UPDATE NotificationBO set customStudyId='"+studyBo.getCustomStudyId()+"' where studyId="+studyBo.getId()).executeUpdate();
				   session.createQuery("UPDATE Checklist set customStudyId='"+studyBo.getCustomStudyId()+"' where studyId="+studyBo.getId()).executeUpdate();
				   
				}
				
				
				
				message = FdahpStudyDesignerConstants.SUCCESS;
				}
				
			  }
			//transaction.commit();
		}catch(Exception e){
			//transaction.rollback();
			logger.error("StudyDAOImpl - studyDraftCreation() - ERROR " , e);
		}
		logger.info("StudyDAOImpl - studyDraftCreation() - Ends");
		
		return message;
	}*/
	
	@SuppressWarnings("unchecked")
	public List<StudyVersionBo> getStudyVersionInfo(String customStudyId, Session session) {
		logger.info("StudyDAOImpl - getStudyVersionInfo() - Starts");
		List<StudyVersionBo> studyVersionBos = null;
		try{
			query = session.getNamedQuery("getStudyVersionsByCustomStudyId").setString("customStudyId", customStudyId);
			studyVersionBos = query.list();
		}catch(Exception e){
			logger.error("StudyDAOImpl - getStudyVersionInfo() - ERROR " , e);
		}
		logger.info("StudyDAOImpl - getStudyVersionInfo() - Ends");
		return studyVersionBos;
	}
	
	@Override
	public NotificationBO getNotificationByResourceId(Integer resourseId) {
			logger.info("StudyDAOImpl - getNotificationByResourceId() - Starts");
			Session session = null;
			String queryString = null;
			NotificationBO notificationBO = null;
			try{
				session = hibernateTemplate.getSessionFactory().openSession();
				queryString = " FROM NotificationBO NBO WHERE NBO.resourceId = "+resourseId;
				query = session.createQuery(queryString);
				notificationBO = (NotificationBO) query.uniqueResult();
			}catch(Exception e){
				logger.error("StudyDAOImpl - getNotificationByResourceId - ERROR",e);
			}finally{
				if(null != session && session.isOpen()){
					session.close();
				}
			}
			logger.info("StudyDAOImpl - getNotificationByResourceId - Ends");
			return notificationBO;
		}
	
	/**
	 * return Study vesion on customStudyid
	 * @author Ronalin
	 * 
	 * @return StudyVesionBo
	 * @exception Exception
	 */
	@Override
	public StudyIdBean getLiveVersion(String customStudyId) {
		logger.info("StudyDAOImpl - getLiveVersion() - Starts");
		Session session = null;
		StudyVersionBo studyVersionBo = null;
		//Integer activityStudyId = null;
		Integer consentStudyId = null;
		StudyIdBean studyIdBean = new StudyIdBean();
		Integer activetaskStudyId = null;
		Integer questionnarieStudyId = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(customStudyId)){
				query = session.getNamedQuery("getStudyByCustomStudyId").setString("customStudyId", customStudyId);
				query.setMaxResults(1);
				studyVersionBo = (StudyVersionBo)query.uniqueResult();
				if(studyVersionBo!=null){
					queryString = "SELECT s.study_id FROM active_task s where s.custom_study_id='"+customStudyId+"' and is_live =1";
					activetaskStudyId = (Integer)session.createSQLQuery(queryString).setMaxResults(1).uniqueResult();
					
					queryString = "SELECT s.study_id FROM questionnaires s where s.custom_study_id='"+customStudyId+"' and is_live =1";
					questionnarieStudyId = (Integer)session.createSQLQuery(queryString).setMaxResults(1).uniqueResult();
					
					
					queryString = "SELECT s.study_id FROM consent s where s.custom_study_id='"+customStudyId+"' and round(s.version, 1) ="+ studyVersionBo.getConsentVersion();
					consentStudyId = (Integer)session.createSQLQuery(queryString).setMaxResults(1).uniqueResult();
					
					if(consentStudyId ==null){
					  queryString = "SELECT s.study_id FROM consent_info s where s.custom_study_id='"+customStudyId+"' and round(s.version, 1) ="+ studyVersionBo.getConsentVersion();
					   consentStudyId = (Integer)session.createSQLQuery(queryString).setMaxResults(1).uniqueResult();
					}
					
					//studyIdBean.setActivityStudyId(activityStudyId);
					studyIdBean.setActivetaskStudyId(activetaskStudyId);
					studyIdBean.setQuestionnarieStudyId(questionnarieStudyId);
					studyIdBean.setConsentStudyId(consentStudyId);
				}
			}
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getLiveVersion() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getLiveVersion() - Ends");
		return studyIdBean;
    }
	@Override
	public List<ResourceBO> resourcesWithAnchorDate(Integer studyId) {
		logger.info("StudyDAOImpl - resourcesWithAnchorDate() - Starts");
		List<ResourceBO> resourceList = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyId+" AND RBO.resourceType = 1 AND RBO.status = 1 ";
			query = session.createQuery(searchQuery);
			resourceList = query.list();
		}catch(Exception e){
			logger.error("StudyDAOImpl - resourcesWithAnchorDate() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - resourcesWithAnchorDate() - Ends");
		return resourceList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String validateActivityComplete(String studyId, String action) {
		logger.info("StudyDAOImpl - validateActivityComplete() - Starts");
		Session session = null;
		boolean questionnarieFlag = true;
		boolean activeTaskEmpty = false;
		boolean questionnarieEmpty = false;
		boolean activeTaskFlag = true;
		List<ActiveTaskBo> completedactiveTasks = null;
		List<QuestionnaireBo> completedquestionnaires = null;
		StudySequenceBo studySequence = null;
		String message = FdahpStudyDesignerConstants.SUCCESS;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(action)){
				//For checking activetask or question done or not
				query = session.getNamedQuery("ActiveTaskBo.getActiveTasksByByStudyIdDone").setInteger(FdahpStudyDesignerConstants.STUDY_ID, Integer.parseInt(studyId));
				completedactiveTasks = query.list();
				query = session.getNamedQuery("getQuestionariesByStudyIdDone").setInteger(FdahpStudyDesignerConstants.STUDY_ID, Integer.parseInt(studyId));
				completedquestionnaires = query.list();
				studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", Integer.parseInt(studyId)).uniqueResult();
				if((completedactiveTasks!=null && !completedactiveTasks.isEmpty())){
		    		for(ActiveTaskBo activeTaskBo : completedactiveTasks){
						if(!activeTaskBo.isAction()){
							activeTaskFlag = false;
							break;
						}
					}
		    	}else{
		    		activeTaskEmpty = true;
		    	}
		    	if(completedquestionnaires!=null && !completedquestionnaires.isEmpty()){
		    		for(QuestionnaireBo questionnaireBo : completedquestionnaires){
						if(questionnaireBo.getStatus() != null && !questionnaireBo.getStatus()){
							questionnarieFlag = false;
							break;
						}
					}
				}else{
					questionnarieEmpty = true;
		    	}
		    	//questionnarieFlag, activeTaskFlag  will be true, then only will allow to mark as complete
				if(action.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTIVITY_TYPE_QUESTIONNAIRE)){
					if(!questionnarieFlag){
						message = FdahpStudyDesignerConstants.MARK_AS_COMPLETE_DONE_ERROR_MSG;
					}else if(studySequence.isStudyExcActiveTask() && (questionnarieEmpty && activeTaskEmpty)){
						   message = FdahpStudyDesignerConstants.ACTIVEANDQUESSIONAIREEMPTY_ERROR_MSG;
					}
				}else{
					if(!activeTaskFlag){
						message = FdahpStudyDesignerConstants.MARK_AS_COMPLETE_DONE_ERROR_MSG;
					}else if(studySequence.isStudyExcQuestionnaries() && (questionnarieEmpty && activeTaskEmpty)){
						   message = FdahpStudyDesignerConstants.ACTIVEANDQUESSIONAIREEMPTY_ERROR_MSG;
					}
				}
			}else{
				message = FdahpStudyDesignerConstants.ACTIVEANDQUESSIONAIREEMPTY_ERROR_MSG;
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - resourcesWithAnchorDate() - ERROR " , e);
		}finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - validateActivityComplete() - Ends");
		return message;
	}
	
	
	
	
}
