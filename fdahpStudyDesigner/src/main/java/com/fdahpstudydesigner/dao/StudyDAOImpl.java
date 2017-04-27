package com.fdahpstudydesigner.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;

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
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(userId!= null && userId != 0){
				query = session.createQuery("select new com.fdahpstudydesigner.bean.StudyListBean(s.id,s.customStudyId,s.name,s.category,s.researchSponsor,p.projectLead,p.viewPermission,s.status,s.createdOn)"
						+ " from StudyBo s,StudyPermissionBO p"
						+ " where s.id=p.studyId"
						/*+ " and p.delFlag="+fdahpStudyDesignerConstants.DEL_STUDY_PERMISSION_INACTIVE*/
						+ " and s.version=0"
						+ " and p.userId=:impValue"
						+ " order by s.createdOn desc");
				query.setParameter("impValue", userId);
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
	 * @author Ronalin
	 * Add/Update the Study
	 * @param StudyBo , {@link StudyBo}
	 * @return {@link String}
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String saveOrUpdateStudy(StudyBo studyBo){
		logger.info("StudyDAOImpl - saveOrUpdateStudy() - Starts");
		Session session = null;
		String message = FdahpStudyDesignerConstants.SUCCESS;
		StudyPermissionBO studyPermissionBO = null;
		Integer studyId = null, userId = null;
		StudySequenceBo studySequenceBo = null;
		StudyBo dbStudyBo = null;
		List<NotificationBO> notificationBO = null;
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
				
				studySequenceBo = new StudySequenceBo();
				studySequenceBo.setStudyId(studyId);
				session.save(studySequenceBo);
			}else{
				dbStudyBo = (StudyBo) session.createQuery("from StudyBo where id="+studyBo.getId()).uniqueResult();
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
				
				/*updated by Kanchana - when ever customStudyId is changed in basic info need to be updated in NotificationBo as it is Foreign Key
				in the table*/
				if(dbStudyBo != null){
					queryString = "from NotificationBO NBO where NBO.studyId = "+dbStudyBo.getId()+"";
					query = session.createQuery(queryString);
					notificationBO = query.list();
				}
				if(notificationBO!=null && !notificationBO.isEmpty()){
					for (NotificationBO notificationBOUpdate:notificationBO) {
						notificationBOUpdate.setCustomStudyId(dbStudyBo.getCustomStudyId());
					}
					
				}
			}
			studySequenceBo = (StudySequenceBo) session.createQuery("from StudySequenceBo where studyId="+studyBo.getId()).uniqueResult();
			if(studySequenceBo!=null){
				if(!studySequenceBo.isBasicInfo() && StringUtils.isNotEmpty(studyBo.getButtonText()) 
						&& studyBo.getButtonText().equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON)){
						studySequenceBo.setBasicInfo(true);
				}else if(StringUtils.isNotEmpty(studyBo.getButtonText()) 
						&& studyBo.getButtonText().equalsIgnoreCase(FdahpStudyDesignerConstants.SAVE_BUTTON)){
					studySequenceBo.setBasicInfo(false);
				}
				session.update(studySequenceBo);
			}
			message = auditLogDAO.updateDraftToEditedStatus(session, transaction, studyBo.getUserId(), FdahpStudyDesignerConstants.DRAFT_STUDY, studyBo.getId());
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
		HashMap<String, List<ReferenceTablesBo>> referenceMap = new HashMap<String, List<ReferenceTablesBo>>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query  = session.createQuery("from ReferenceTablesBo order by category asc,id asc");
			allReferenceList = query.list();
			if (allReferenceList != null && allReferenceList.size() > 0) {
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
				referenceMap = new HashMap<String, List<ReferenceTablesBo>>();
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
		List<StudyVersionBo> studyVersionBos = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(studyId)){
				studyBo = (StudyBo) session.createQuery("from StudyBo where id="+studyId).uniqueResult();
				studySequenceBo = (StudySequenceBo) session.createQuery("from StudySequenceBo where studyId="+studyId).uniqueResult();
				permissionBO = (StudyPermissionBO) session.createQuery("from StudyPermissionBO where studyId="+studyId+" and userId="+userId).uniqueResult();
				if(studySequenceBo!=null)
					studyBo.setStudySequenceBo(studySequenceBo);
				if(permissionBO!=null)
					studyBo.setViewPermission(permissionBO.isViewPermission());
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
	public String saveOrUpdateOverviewStudyPages(StudyPageBean studyPageBean) {
		logger.info("StudyDAOImpl - saveOrUpdateOverviewStudyPages() - Starts");
		Session session = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		int titleLength = 0;
		StudySequenceBo studySequence = null;
		StudyBo studyBo = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotEmpty(studyPageBean.getStudyId())){
				studyBo = (StudyBo) session.getNamedQuery("StudyBo.getStudiesById").setInteger("id", Integer.parseInt(studyPageBean.getStudyId())).uniqueResult();
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
//							if(FdahpStudyDesignerUtil.isNotEmpty(studyPageBean.getPageId()[i]))
//								studyPageBo = (StudyPageBo) session.createQuery("from StudyPageBo SPB where SPB.pageId="+studyPageBean.getPageId()[i]).uniqueResult();
								
//							if(studyPageBo == null)
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
							/*}else{
								studyPageBo.setTitle(studyPageBean.getTitle()[i].equals(FdahpStudyDesignerConstants.IMG_DEFAULT)?null:studyPageBean.getTitle()[i]);
								studyPageBo.setDescription(studyPageBean.getDescription()[i].equals(FdahpStudyDesignerConstants.IMG_DEFAULT)?null:studyPageBean.getDescription()[i]);
								studyPageBo.setImagePath(studyPageBean.getImagePath()[i].equals(FdahpStudyDesignerConstants.IMG_DEFAULT)?null:studyPageBean.getImagePath()[i]);
								session.update(studyPageBo);
							}*/
						}
						studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", Integer.parseInt(studyPageBean.getStudyId())).uniqueResult();
						if(studySequence != null) {
							if(studyPageBean.getActionType() != null && studyPageBean.getActionType().equalsIgnoreCase(FdahpStudyDesignerConstants.COMPLETED_BUTTON) && !studySequence.isOverView()) {
								studySequence.setOverView(true);
							}else if(studyPageBean.getActionType() != null && studyPageBean.getActionType().equalsIgnoreCase(FdahpStudyDesignerConstants.SAVE_BUTTON)){
								studySequence.setOverView(false);
							}
							session.update(studySequence);
						}
						message = auditLogDAO.updateDraftToEditedStatus(session, transaction, studyPageBean.getUserId(), FdahpStudyDesignerConstants.DRAFT_STUDY, Integer.parseInt(studyPageBean.getStudyId()));
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
<<<<<<< HEAD
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
=======
>>>>>>> df25c191d454a63be04c0bd59c4d36009c4272ed
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
	public String deleteConsentInfo(Integer consentInfoId,Integer studyId,SessionObject sessionObject) {
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
				StudySequenceBo studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", studyId).uniqueResult();
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
	public ConsentInfoBo saveOrUpdateConsentInfo(ConsentInfoBo consentInfoBo) {
		logger.info("StudyDAOImpl - saveOrUpdateConsentInfo() - Starts");
		Session session = null;
		StudySequenceBo studySequence = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(consentInfoBo.getType() != null){
				studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", consentInfoBo.getStudyId()).uniqueResult();
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
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			List<ComprehensionTestQuestionBo> comprehensionTestQuestionList = null;
			String searchQuery = "From ComprehensionTestQuestionBo CTQBO where CTQBO.studyId="+studyId+" order by CTQBO.sequenceNo asc";
			comprehensionTestQuestionList = session.createQuery(searchQuery).list();
			if(comprehensionTestQuestionList != null && comprehensionTestQuestionList.size() > 0){
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
				studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", comprehensionTestQuestionBo.getStudyId()).uniqueResult();
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
				query = session.getNamedQuery("getEligibiltyByStudyId").setInteger("studyId", Integer.parseInt(studyId));
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
	public String saveOrUpdateStudyEligibilty(EligibilityBo eligibilityBo) {
		logger.info("StudyDAOImpl - saveOrUpdateStudyEligibilty() - Starts");
		String result = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		StudySequenceBo studySequence = null;
		EligibilityBo eligibilityBoUpdate = null;
		Boolean updateFlag = false;
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
				
				studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", eligibilityBo.getStudyId()).uniqueResult();
				if(studySequence != null) {
					if(eligibilityBo.getActionType() != null && ("mark").equals(eligibilityBo.getActionType()) && !studySequence.isEligibility()){
						studySequence.setEligibility(true);
					} else if(eligibilityBo.getActionType() != null && !("mark").equals(eligibilityBo.getActionType())){
						studySequence.setEligibility(false);
					}
					session.saveOrUpdate(eligibilityBoUpdate);
				}
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
	 * return study list 
	 * @author Pradyumn
	 * @return study list
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StudyBo> getStudies(int userId){
		logger.info("StudyDAOImpl - getStudies() - Starts");
		Session session = null;
		List<StudyBo> studyBOList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
				query = session.createQuery(" FROM StudyBo SBO ");
				studyBOList = query.list();
		} catch (Exception e) {
			logger.error("StudyDAOImpl - getStudies() - ERROR " , e);
		} finally{
			if(null != session && session.isOpen()){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - getStudies() - Ends");
		return studyBOList;
	}
	
	/**
	 * Save or update settings and admins of study
	 * @author Ronalin
	 * 
	 * @param studyBo , {@link studyBo}
	 * @return {@link String} , the status FdahpStudyDesignerConstants.SUCCESS or FdahpStudyDesignerConstants.FAILURE
	 * @exception Exception
	 */
	public String saveOrUpdateStudySettings(StudyBo studyBo) {
		logger.info("StudyDAOImpl - saveOrUpdateStudySettings() - Starts");
		String result = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		StudySequenceBo studySequence = null;
		StudyBo study = null;
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
					consentInfoBo.setElaborated(consentInfoBo.getElaborated().replace("'", "&#39;"));
					//consentInfoBo.setElaborated(consentInfoBo.getElaborated().replace("\"", "\\\""));
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
	public ConsentBo saveOrCompleteConsentReviewDetails(ConsentBo consentBo, SessionObject sesObj) {
		logger.info("INFO: StudyDAOImpl - saveOrCompleteConsentReviewDetails() :: Starts");
		Session session = null;
		StudySequenceBo studySequence=null;
		List<ConsentInfoBo> consentInfoList = null;
		String content = "";
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
				studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", consentBo.getStudyId()).uniqueResult();
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
			String searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyId+" AND RBO.action = 0 AND RBO.status = 1 ";
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
	public String deleteResourceInfo(Integer resourceInfoId) {
		logger.info("StudyDAOImpl - deleteResourceInfo() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			String deleteQuery = " UPDATE ResourceBO RBO SET status = "+ false +" WHERE id = "+resourceInfoId;
			query = session.createQuery(deleteQuery);
			count = query.executeUpdate();
			transaction.commit();
			if(count > 0){
				message = FdahpStudyDesignerConstants.SUCCESS;
			}
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
	public String markAsCompleted(int studyId,String markCompleted, boolean flag, SessionObject sesObj) {
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
					activityDetails = "All the notification has been DONE and it is marked as completed , notification will be triggered to user once the study is launch ";
				}
			}else if(markCompleted.equals(FdahpStudyDesignerConstants.RESOURCE)){
				if(flag){
					activity = "Resource completed";
					activityDetails = "All the resources has been DONE and it is marked as completed.";
				}else{
					activity = "Resource saved";
					activityDetails = "Resource content saved successfully";
				}
				query = session.createQuery(" UPDATE StudySequenceBo SET miscellaneousResources = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_STUDY, studyId);
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.CONESENT)){
				query = session.createQuery(" UPDATE StudySequenceBo SET consentEduInfo = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_CONCENT, studyId);
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.CONESENT_REVIEW)){
				query = session.createQuery(" UPDATE StudySequenceBo SET eConsent = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_CONCENT, studyId);
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.CHECK_LIST)){
				query = session.createQuery(" UPDATE StudySequenceBo SET checkList = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTIVETASK_LIST)){
				query = session.createQuery(" UPDATE StudySequenceBo SET studyExcActiveTask = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_ACTIVITY, studyId);
			}else if(markCompleted.equalsIgnoreCase(FdahpStudyDesignerConstants.QUESTIONNAIRE)){
				query = session.createQuery(" UPDATE StudySequenceBo SET studyExcQuestionnaries = "+flag+" WHERE studyId = "+studyId );
				count = query.executeUpdate();
				auditLogDAO.updateDraftToEditedStatus(session, transaction, sesObj.getUserId(), FdahpStudyDesignerConstants.DRAFT_ACTIVITY, studyId);
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
	public String saveResourceNotification(NotificationBO notificationBO){
		logger.info("StudyDAOImpl - saveResourceNotification() - Starts");
		Session session = null;
		String message = FdahpStudyDesignerConstants.FAILURE;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.save(notificationBO);
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
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			String searchQuery = " FROM NotificationBO NBO WHERE NBO.studyId="+studyId+" AND NBO.notificationAction = 0 AND NBO.notificationType='ST' AND NBO.notificationSubType='all' ";
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
	public Checklist getchecklistInfo(Integer studyId) {
		logger.info("StudyDAOImpl - getchecklistInfo() - Starts");
		Checklist checklist = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getchecklistInfo").setInteger("studyId", studyId);
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
		boolean	enrollementFlag = false, studyActivityFlag = false, activityFlag = false;
		StudySequenceBo studySequenceBo = null;
		StudyBo studyBo = null ;
		List<ActiveTaskBo> activeTasks = null;
		List<QuestionnaireBo> questionnaires = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(StringUtils.isNotEmpty(buttonText) && StringUtils.isNotEmpty(studyId)){
			
			studyBo= (StudyBo) session.createQuery(" FROM StudyBo RBO WHERE RBO.id="+studyId+"").uniqueResult();	
			studySequenceBo= (StudySequenceBo) session.createQuery(" FROM StudySequenceBo RBO WHERE RBO.studyId="+studyId+"").uniqueResult();
            
			query = session.getNamedQuery("ActiveTaskBo.getActiveTasksByByStudyIdDone").setInteger("studyId", Integer.parseInt(studyId));
			activeTasks = query.list();
			query = session.getNamedQuery("getQuestionariesByStudyIdDone").setInteger("studyId", Integer.parseInt(studyId));
			questionnaires = query.list();
			
			if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH)){
				
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
					if(studyActivityFlag && studyBo!=null && StringUtils.isNotEmpty(studyBo.getEnrollingParticipants()) && studyBo.getEnrollingParticipants().equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
						enrollementFlag = true;
					}
				//3-The study must have at least one 'activity' added. This could be a questionnaire or active task. 
				if(!enrollementFlag){
					message = FdahpStudyDesignerConstants.LUNCH_ENROLLMENT_ERROR_MSG;
					return message;
				}else{
					if((activeTasks!=null && !activeTasks.isEmpty())){
			    		activityFlag = true;
			    	}
			    	if(!activityFlag && questionnaires!=null && !questionnaires.isEmpty())
				    	activityFlag = true;
				}
				if(!activityFlag){
					message = FdahpStudyDesignerConstants.ACTIVEANDQUESSIONAIREEMPTY_ERROR_MSG;
					return message;
				}else{
					//3-Date validation
					message = validateDateForStudyAction(studyBo);
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
					    }else if(studyBo!=null && StringUtils.isNotEmpty(studyBo.getEnrollingParticipants()) && studyBo.getEnrollingParticipants().equalsIgnoreCase(FdahpStudyDesignerConstants.YES)){
					    	    message = FdahpStudyDesignerConstants.PRE_PUBLISH_ENROLLMENT_ERROR_MSG;
								return message;
					    }
				}
			}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES)){
				String studyActivity = "";
				studyActivity = getErrorBasedonAction(studySequenceBo);
				if(StringUtils.isNotEmpty(studyActivity) && !studyActivity.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS))
				    return studyActivity;
				else{
					//3-Date validation
					message = validateDateForStudyAction(studyBo);
					if(message.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS)){
						if((activeTasks!=null && !activeTasks.isEmpty())){
				    		activityFlag = true;
				       }else{
							if((activeTasks!=null && !activeTasks.isEmpty())){
					    		activityFlag = true;
					    	}
					    	if(!activityFlag && questionnaires!=null && !questionnaires.isEmpty())
						    	activityFlag = true;
					    }
						if(!activityFlag){
								message = FdahpStudyDesignerConstants.ACTIVEANDQUESSIONAIREEMPTY_ERROR_MSG;
								return message;
						}
					}else
					   return message ;
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
		    }/*else if(!studySequenceBo.isStudyExcQuestionnaries()){
		    	message = FdahpStudyDesignerConstants.STUDYEXCQUESTIONNARIES_ERROR_MSG;
		    	return message;
		    }*/else if(!studySequenceBo.isStudyExcActiveTask()){
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
	public String updateStudyActionOnAction(String studyId, String buttonText) {
		logger.info("StudyDAOImpl - updateStudyActionOnAction() - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		StudyBo studyBo = null;
		List<Object> objectList = null;
		StudyBo liveStudy = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(StringUtils.isNotEmpty(studyId) && StringUtils.isNotEmpty(buttonText)){
				studyBo = (StudyBo) session.createQuery("from StudyBo where id="+studyId).uniqueResult();
				if(studyBo!=null){
					if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PUBLISH)){
						studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_PRE_PUBLISH);
						studyBo.setStudyPreActiveFlag(true);
						session.update(studyBo);
						message = FdahpStudyDesignerConstants.SUCCESS;
					}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UNPUBLISH)){
						studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_PRE_LAUNCH);
						studyBo.setStudyPreActiveFlag(false);
						session.update(studyBo);
						message = FdahpStudyDesignerConstants.SUCCESS;
					}else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH) || buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_UPDATES)){
						studyBo.setStudyPreActiveFlag(false);
						if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_LUNCH))
						  studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_ACTIVE);
						else
							studyBo.setStatus(FdahpStudyDesignerConstants.STUDY_ACTIVE);	
						studyBo.setStudylunchDate(FdahpStudyDesignerUtil.getCurrentDateTime());
						session.update(studyBo);
						//getting Questionnaries based on StudyId
						query = session.createQuery("select ab.id"
													+ " from QuestionnairesFrequenciesBo a,QuestionnaireBo ab"
													+ " where a.questionnairesId=ab.id"
													+" and ab.studyId=:impValue"
													+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"'"
													+" and a.isLaunchStudy='true'");
						query.setParameter("impValue", Integer.valueOf(studyId));
					    objectList = query.list();
					    if(objectList!=null && !objectList.isEmpty()){
					    	for(Object obj: objectList){
					    		Integer questionaryId = (Integer)obj;
					    		if(questionaryId!=null){
					    			query = session.createQuery("UPDATE QuestionnaireBo SET studyLifetimeStart='"+studyBo.getStudylunchDate()+"' where id="+questionaryId);
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
									+" and a.isLaunchStudy='true'"
									+" and ab.activeTaskLifetimeStart IS NOT NULL");
						query.setParameter("impValue", Integer.valueOf(studyId));
						objectList = query.list();
					    if(objectList!=null && !objectList.isEmpty()){
					    	for(Object obj: objectList){
					    		Integer activeTaskId = (Integer)obj;
					    		if(activeTaskId!=null){
					    			query = session.createQuery("UPDATE ActiveTaskBo SET activeTaskLifetimeStart='"+studyBo.getStudylunchDate()+"' where id="+activeTaskId);
					    			query.executeUpdate();
					    		}
					    	}
					    }
					    //StudyDraft version creation
					    message = this.studyDraftCreation(studyBo, session);
					}else{
						liveStudy = (StudyBo) session.getNamedQuery("getStudyLiveVersion").setString("customStudyId", studyBo.getCustomStudyId()).uniqueResult();
						if(liveStudy!=null){
							liveStudy.setStudyPreActiveFlag(false);
							if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_PAUSE)){
							  liveStudy.setStatus(FdahpStudyDesignerConstants.STUDY_PAUSED);
						   }else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_RESUME)){
							   liveStudy.setStatus(FdahpStudyDesignerConstants.STUDY_ACTIVE);
						   }else if(buttonText.equalsIgnoreCase(FdahpStudyDesignerConstants.ACTION_DEACTIVATE)){
							   liveStudy.setStatus(FdahpStudyDesignerConstants.STUDY_DEACTIVATED);
						    }
							session.update(liveStudy);
							message = FdahpStudyDesignerConstants.SUCCESS;
						}	
					}	
					if(message.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS))
						session.update(studyBo);
				}
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
	public String validateDateForStudyAction(StudyBo studyBo){
		boolean resourceFlag = true;
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
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			//anchor date need to be done (only custom date need to do)
			
			//getting based on custom statrt date resource list 
			searchQuery = " FROM ResourceBO RBO WHERE RBO.studyId="+studyBo.getId()+" AND RBO.status = 1 AND RBO.startDate IS NOT NULL ORDER BY RBO.createdOn DESC ";
			query = session.createQuery(searchQuery);
			resourceBOList = query.list();
			if(resourceBOList!=null && !resourceBOList.isEmpty()){
				for(ResourceBO resourceBO:resourceBOList){
					if(!FdahpStudyDesignerUtil.compareDateWithCurrentDateResource(resourceBO.getStartDate(), "yyyy-MM-dd")){
						resourceFlag = false;
						break;
					}
				}
			}
			if(!resourceFlag){
				message = FdahpStudyDesignerConstants.RESOURCE_DATE_ERROR_MSG;
				return message;
			}else{
			//getting activeTasks based on StudyId
			 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicBean(ab.activeTaskLifetimeStart)"
						+ " from ActiveTaskFrequencyBo a,ActiveTaskBo ab"
						+ " where a.activeTaskId=ab.id"
						+" and ab.studyId=:impValue"
						+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"'"
						+" and a.isLaunchStudy=false"
						+" and ab.activeTaskLifetimeStart IS NOT NULL");
			query.setParameter("impValue", studyBo.getId());
			dynamicList = query.list();
			 if(dynamicList!=null && !dynamicList.isEmpty()){

			 for(DynamicBean obj:dynamicList){
					 if(obj.getDateTime()!=null && !FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(obj.getDateTime(), "yyyy-MM-dd")){
						 activitiesFalg = false;
						break;
					 }	
				}
			 }
			 
			 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicBean(ab.activeTaskLifetimeStart)"
						+ " from ActiveTaskFrequencyBo a,ActiveTaskBo ab"
						+ " where a.activeTaskId=ab.id"
						+" and ab.studyId=:impValue"
						+" and ab.frequency not in('"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"','"
						+FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE+"')"
						+" and ab.activeTaskLifetimeStart IS NOT NULL");
			query.setParameter("impValue", studyBo.getId());
			dynamicList = query.list();
			 if(dynamicList!=null && !dynamicList.isEmpty()){
			 for(DynamicBean obj:dynamicList){
					 if(obj.getDateTime()!=null && !FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(obj.getDateTime(), "yyyy-MM-dd")){
						 activitiesFalg = false;
						break;
					 }	
				}
			 }
			 
			 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicFrequencyBean(a.frequencyStartDate, a.frequencyTime)"
						+ " from ActiveTaskCustomScheduleBo a,ActiveTaskBo ab"
						+ " where a.activeTaskId=ab.id"
						+" and ab.studyId=:impValue"
						+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE+"'"
						+" and a.frequencyStartDate IS NOT NULL"
						+" and a.frequencyTime IS NOT NULL");
			query.setParameter("impValue", studyBo.getId());
			dynamicFrequencyList = query.list();
			 if(dynamicFrequencyList!=null && !dynamicFrequencyList.isEmpty()){
			 for(DynamicFrequencyBean obj:dynamicFrequencyList){
				 if(obj.getStartDate()!=null && obj.getTime()!=null){
					 String dateTime = FdahpStudyDesignerUtil.getFormattedDate(obj.getStartDate()+" "+obj.getTime(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
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
				 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicBean(ab.studyLifetimeStart)"
							+ " from QuestionnairesFrequenciesBo a,QuestionnaireBo ab"
							+ " where a.questionnairesId=ab.id"
							+" and ab.active=1"
							+" and ab.studyId=:impValue"
							+" and ab.frequency='"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"'"
							+" and a.isLaunchStudy=false"
							+" and ab.studyLifetimeStart IS NOT NULL");
				query.setParameter("impValue", studyBo.getId());
				dynamicList = query.list();
				 if(dynamicList!=null && !dynamicList.isEmpty()){
				 for(DynamicBean obj:dynamicList){
						 if(obj.getDateTime()!=null && !FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(obj.getDateTime(), "yyyy-MM-dd")){
							 questionarriesFlag = false;
							break;
						 }	
					}
				 }
				 
				 query = session.createQuery("select new com.fdahpstudydesigner.bean.DynamicBean(ab.studyLifetimeStart)"
							+ " from QuestionnairesFrequenciesBo a,QuestionnaireBo ab"
							+ " where a.questionnairesId=ab.id"
							+" and ab.active=1"
							+" and ab.studyId=:impValue"
							+" and ab.frequency not in('"+FdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME+"','"
							+FdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE+"')"
							+" and ab.studyLifetimeStart IS NOT NULL");
				query.setParameter("impValue", studyBo.getId());
				dynamicList = query.list();
				 if(dynamicList!=null && !dynamicList.isEmpty()){
				 for(DynamicBean obj:dynamicList){
						 if(obj.getDateTime()!=null && !FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(obj.getDateTime(), "yyyy-MM-dd")){
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
				query.setParameter("impValue", studyBo.getId());
				dynamicFrequencyList = query.list();
				 if(dynamicFrequencyList!=null && !dynamicFrequencyList.isEmpty()){
				 for(DynamicFrequencyBean obj:dynamicFrequencyList){
					 if(obj.getStartDate()!=null && obj.getTime()!=null){
						 String dateTime = FdahpStudyDesignerUtil.getFormattedDate(obj.getStartDate()+" "+obj.getTime(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
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
					+ " AND notificationType='ST' AND RBO.notificationSent='false' AND RBO.notificationStatus='false' ";
			query = session.createQuery(searchQuery);
			notificationBOs = query.list();
			if(notificationBOs!=null && !notificationBOs.isEmpty()){
				for(NotificationBO notificationBO:notificationBOs){
						String sceduleDateTime = FdahpStudyDesignerUtil.getFormattedDate(notificationBO.getScheduleDate()+" "+notificationBO.getScheduleTime(), "yyyy-MM-dd HH:mm", "yyyy-MM-dd HH:mm:ss");
						if(!FdahpStudyDesignerUtil.compareDateWithCurrentDateTime(sceduleDateTime, "yyyy-MM-dd HH:mm:ss")){
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
	@SuppressWarnings("unchecked")
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
		try{
			/*if(session!= null) {
				transaction = session.beginTransaction();
			}*/
			if(studyBo!=null){
				//if already lunch if study hasStudyDraft()==1 , then update and create draft version , otherwise not
				query = session.getNamedQuery("getStudyByCustomStudyId").setString("customStudyId", studyBo.getCustomStudyId());
				query.setMaxResults(1);
				studyVersionBo = (StudyVersionBo)query.uniqueResult();
				if(studyVersionBo!=null && (studyBo.getHasStudyDraft().equals(0))){
					flag = false;
				}
				if(flag){
				//version update in study_version table 
				if(studyVersionBo!=null){
					//update all studies to archive (live as 2)
					query = session.getNamedQuery("updateStudyVersion").setString("customStudyId", studyBo.getCustomStudyId());
					query.executeUpdate();
					
					newstudyVersionBo = SerializationUtils.clone(studyVersionBo);
					newstudyVersionBo.setStudyVersion(studyVersionBo.getStudyVersion() + 0.1f);
					if(studyBo.getHasConsentDraft() == 1){
						newstudyVersionBo.setConsentVersion(studyVersionBo.getConsentVersion() + 0.1f);
					}
					if(studyBo.getHasActivityDraft() == 1){
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
				StudySequenceBo studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", studyBo.getId()).uniqueResult();
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
				query = session.getNamedQuery("getEligibiltyByStudyId").setInteger("studyId", studyBo.getId());
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
					
				//Questionarries
				query = session.getNamedQuery("getQuestionariesByStudyId").setInteger("studyId", studyBo.getId());
				questionnaires = query.list();
				if(questionnaires!=null && !questionnaires.isEmpty()){
					for(QuestionnaireBo questionnaireBo: questionnaires){
					    
						QuestionnaireBo newQuestionnaireBo = SerializationUtils.clone(questionnaireBo);
						newQuestionnaireBo.setId(null);
						newQuestionnaireBo.setStudyId(studyDreaftBo.getId());
						newQuestionnaireBo.setVersion(studyDreaftBo.getVersion());
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
					    
						List<QuestionnairesStepsBo> questionnairesStepsBoList = null;
						
						//Find out instructionList through Questionnaire id
						query = session.getNamedQuery("getQuestionnaireStepsByType").setInteger("questionnairesId", questionnaireBo.getId()).setString("stepType", FdahpStudyDesignerConstants.INSTRUCTION_STEP);
						questionnairesStepsBoList = query.list();
						 if(questionnairesStepsBoList!=null && !questionnairesStepsBoList.isEmpty()){
						  for(QuestionnairesStepsBo questionnairesStepsBo : questionnairesStepsBoList){
							  QuestionnairesStepsBo newQuestionnairesStepsBo = SerializationUtils.clone(questionnairesStepsBo);
							  newQuestionnairesStepsBo.setQuestionnairesId(newQuestionnaireBo.getId());
							  newQuestionnairesStepsBo.setStepId(null);
							  session.save(newQuestionnairesStepsBo);
							  
							  InstructionsBo instructionsBo =(InstructionsBo)session.getNamedQuery("getInstructionStep").setInteger("id", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
							  if(instructionsBo!=null){
								  InstructionsBo newInstructionsBo = SerializationUtils.clone(instructionsBo);
								  newInstructionsBo.setId(null);
								  session.save(newInstructionsBo);
								  
								  //updating new InstructionId
								  newQuestionnairesStepsBo.setInstructionFormId(newInstructionsBo.getId());
								  session.update(newQuestionnairesStepsBo);
							  }
							  
						  }
						 }
						
						//Find out questionsList through Questionnaire id
						query = session.getNamedQuery("getQuestionnaireStepsByType").setInteger("questionnairesId", questionnaireBo.getId()).setString("stepType", FdahpStudyDesignerConstants.QUESTION_STEP);
						questionnairesStepsBoList = query.list();
						if(questionnairesStepsBoList!=null && !questionnairesStepsBoList.isEmpty()){
								  for(QuestionnairesStepsBo questionnairesStepsBo : questionnairesStepsBoList){
									  QuestionnairesStepsBo newQuestionnairesStepsBo = SerializationUtils.clone(questionnairesStepsBo);
									  newQuestionnairesStepsBo.setQuestionnairesId(newQuestionnaireBo.getId());
									  newQuestionnairesStepsBo.setStepId(null);
									  session.save(newQuestionnairesStepsBo);
									  
									  QuestionsBo  questionsBo= (QuestionsBo)session.getNamedQuery("getQuestionStep").setInteger("stepId", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
									  if(questionsBo!=null){
										  QuestionsBo newQuestionsBo = SerializationUtils.clone(questionsBo);
										  newQuestionsBo.setId(null);
										  session.save(newQuestionsBo);
										  
										  //updating new InstructionId
										  newQuestionnairesStepsBo.setInstructionFormId(newQuestionsBo.getId());
										  session.update(newQuestionnairesStepsBo);
									  }
								  }
						}
						
						//Find out formList through Questionnaire id
						query = session.getNamedQuery("getQuestionnaireStepsByType").setInteger("questionnairesId", questionnaireBo.getId()).setString("stepType", FdahpStudyDesignerConstants.FORM_STEP);
						questionnairesStepsBoList = query.list();
						if(questionnairesStepsBoList!=null && !questionnairesStepsBoList.isEmpty()){
								  for(QuestionnairesStepsBo questionnairesStepsBo : questionnairesStepsBoList){
									  QuestionnairesStepsBo newQuestionnairesStepsBo = SerializationUtils.clone(questionnairesStepsBo);
									  newQuestionnairesStepsBo.setQuestionnairesId(newQuestionnaireBo.getId());
									  newQuestionnairesStepsBo.setStepId(null);
									  session.save(newQuestionnairesStepsBo);
									  
									  FormBo  formBo= (FormBo)session.getNamedQuery("getFormBoStep").setInteger("stepId", questionnairesStepsBo.getInstructionFormId()).uniqueResult();
									  if(formBo!=null){
										  FormBo newFormBo = SerializationUtils.clone(formBo);
										  formBo.setFormId(null);
										  session.save(newFormBo);
										  
										
										  List<FormMappingBo> formMappingBoList = session.getNamedQuery("getFormByFormId").setInteger("formId", formBo.getFormId()).list(); 
										  if(formMappingBoList!=null && !formMappingBoList.isEmpty()){
											  for(FormMappingBo formMappingBo : formMappingBoList){
												  FormMappingBo newMappingBo = SerializationUtils.clone(formMappingBo);
												  newMappingBo.setFormId(newFormBo.getFormId());
												  newMappingBo.setId(null);
												  
												  QuestionsBo  questionsBo= (QuestionsBo)session.getNamedQuery("getQuestionByFormId").setInteger("formId", formMappingBo.getQuestionId()).uniqueResult();
												  if(questionsBo!=null){
													  QuestionsBo newQuestionsBo = SerializationUtils.clone(questionsBo);
													  session.save(newQuestionsBo);
													  
													  //adding questionId
													  newMappingBo.setQuestionId(newQuestionsBo.getId());
													  session.save(newMappingBo);
												  }
												  
											  }
										  }
										  //updating new formId
										  newQuestionnairesStepsBo.setInstructionFormId(newFormBo.getFormId());
										  session.update(newQuestionnairesStepsBo);
									  }
								  }
							}
						/**  Content purpose creating draft End **/
					   }
					}//If Questionarries updated flag -1 then update End
				
				    //ActiveTasks
					query = session.getNamedQuery("ActiveTaskBo.getActiveTasksByByStudyId").setInteger("studyId", studyBo.getId());
					        activeTasks = query.list();
				    if(activeTasks!=null && !activeTasks.isEmpty()){
				    	for(ActiveTaskBo activeTaskBo:activeTasks){
				    		
				    		ActiveTaskBo newActiveTaskBo = SerializationUtils.clone(activeTaskBo);
				    		newActiveTaskBo.setId(null);
				    		newActiveTaskBo.setVersion(studyDreaftBo.getVersion());
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
				    }//Active TAsk End
				//Activities End
				if(studyVersionBo == null || studyBo.getHasConsentDraft().equals(1)){
				  //If Consent updated flag -1 then update
					query = session.getNamedQuery("getConsentByStudyId").setInteger("studyId", studyBo.getId());
					List<ConsentBo> consentBoList = query.list();
					if(consentBoList!=null && !consentBoList.isEmpty()){
						for(ConsentBo consentBo: consentBoList){
							ConsentBo newConsentBo = SerializationUtils.clone(consentBo);
							newConsentBo.setId(null);
							newConsentBo.setStudyId(studyDreaftBo.getId());
							newConsentBo.setVersion(studyDreaftBo.getVersion());
							session.save(newConsentBo);
						}
					}
					query = session.getNamedQuery("getConsentInfoByStudyId").setInteger("studyId", studyBo.getId());
					List<ConsentInfoBo> consentInfoBoList = query.list();
					if(consentInfoBoList!=null && !consentInfoBoList.isEmpty()){
						for(ConsentInfoBo consentInfoBo:consentInfoBoList){
							ConsentInfoBo newConsentInfoBo = SerializationUtils.clone(consentInfoBo);
							newConsentInfoBo.setId(null);
							newConsentInfoBo.setStudyId(studyDreaftBo.getId());
							newConsentInfoBo.setVersion(studyDreaftBo.getVersion());
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
	
}
