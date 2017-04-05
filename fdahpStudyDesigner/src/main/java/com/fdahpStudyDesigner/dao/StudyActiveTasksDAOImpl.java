/**
 * 
 */
package com.fdahpStudyDesigner.dao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import com.fdahpStudyDesigner.bo.ActiveTaskAtrributeValuesBo;
import com.fdahpStudyDesigner.bo.ActiveTaskBo;
import com.fdahpStudyDesigner.bo.ActiveTaskCustomScheduleBo;
import com.fdahpStudyDesigner.bo.ActiveTaskFrequencyBo;
import com.fdahpStudyDesigner.bo.ActiveTaskListBo;
import com.fdahpStudyDesigner.bo.ActiveTaskMasterAttributeBo;
import com.fdahpStudyDesigner.bo.ActivetaskFormulaBo;
import com.fdahpStudyDesigner.bo.StatisticImageListBo;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.StudySequenceBo;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

/**
 * @author Vivek
 *
 */
@Repository
public class StudyActiveTasksDAOImpl implements StudyActiveTasksDAO{

	private static Logger logger = Logger.getLogger(StudyActiveTasksDAOImpl.class.getName());
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	String queryString = "";
	
	public StudyActiveTasksDAOImpl() {
	}
	
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	/**
	 * return  active tasks based on user's Study Id
	 * @author Vivek
	 * 
	 * @param studyId , studyId of the {@link StudyBo}
	 * @return List of {@link ActiveTaskBo}
	 * @exception Exception
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveTaskBo> getStudyActiveTasksByStudyId(String studyId) {
		logger.info("StudyActiveTasksDAOImpl - getStudyActiveTasksByStudyId() - Starts");
		Session session = null;
		List<ActiveTaskBo> activeTasks = null;
		List<ActiveTaskListBo> activeTaskListBos = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if (StringUtils.isNotEmpty(studyId)) {
				query = session.getNamedQuery("ActiveTaskBo.getActiveTasksByByStudyId").setInteger("studyId", Integer.parseInt(studyId));
				activeTasks = query.list();
				
				query = session.createQuery("from ActiveTaskListBo");
				activeTaskListBos = query.list();
				
				if(activeTasks!=null && activeTasks.size()>0 && activeTaskListBos!=null && activeTaskListBos.size()>0){
					for(ActiveTaskBo activeTaskBo:activeTasks){
						if(activeTaskBo.getTaskTypeId()!=null){
							for(ActiveTaskListBo activeTaskListBo:activeTaskListBos){
								if(activeTaskListBo.getActiveTaskListId().intValue() == activeTaskBo.getTaskTypeId().intValue()){
									activeTaskBo.setType(activeTaskListBo.getTaskName());
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(
					"StudyActiveTasksDAOImpl - getStudyActiveTasksByStudyId() - ERROR ", e);
		} finally {
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - getStudyActiveTasksByStudyId() - Ends");
		return activeTasks;
	}

	/**
	 * @author Ronalin
	 * @param Integer :aciveTaskId
	 * @return Object :ActiveTaskBo
	 * 
	 * This method is used to get the ativeTask info object based on consent info id 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public ActiveTaskBo getActiveTaskById(Integer activeTaskId) {
		logger.info("StudyActiveTasksDAOImpl - getActiveTaskById() - Starts");
		ActiveTaskBo activeTaskBo = null;
		Session session = null;
		List<ActiveTaskAtrributeValuesBo> activeTaskAtrributeValuesBos = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			activeTaskBo = (ActiveTaskBo)session.get(ActiveTaskBo.class, activeTaskId);
			if(activeTaskBo!=null){
				query = session.createQuery("from ActiveTaskAtrributeValuesBo where activeTaskId="+activeTaskBo.getId());
				activeTaskAtrributeValuesBos = query.list();
				if(activeTaskAtrributeValuesBos!=null && activeTaskAtrributeValuesBos.size()>0){
					activeTaskBo.setTaskAttributeValueBos(activeTaskAtrributeValuesBos);
				}
				String searchQuery="";
				if(null!=activeTaskBo.getFrequency() && activeTaskBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_MANUALLY_SCHEDULE)){
					searchQuery = "From ActiveTaskCustomScheduleBo ATSBO where ATSBO.activeTaskId="+activeTaskBo.getId();
					query = session.createQuery(searchQuery);
					List<ActiveTaskCustomScheduleBo> activeTaskCustomScheduleBos = query.list();
					activeTaskBo.setActiveTaskCustomScheduleBo(activeTaskCustomScheduleBos);
				}else{
					searchQuery = "From ActiveTaskFrequencyBo ATBO where ATBO.activeTaskId="+activeTaskBo.getId();
					query = session.createQuery(searchQuery);
					if(activeTaskBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_DAILY)){
						List<ActiveTaskFrequencyBo> activeTaskFrequencyBos = query.list();	
						activeTaskBo.setActiveTaskFrequenciesList(activeTaskFrequencyBos);
					}else{
						ActiveTaskFrequencyBo activeTaskFrequencyBo = (ActiveTaskFrequencyBo) query.uniqueResult();
						activeTaskBo.setActiveTaskFrequenciesBo(activeTaskFrequencyBo);
					}
					
				}
			}
		}catch(Exception e){
			logger.error("StudyActiveTasksDAOImpl - getActiveTaskById() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - getActiveTaskById() - Ends");
		return activeTaskBo;
	}

	/**
	 * @author Ronalin
	 * Add/Update the ActiveTaskBo
	 * @param StudyBo , {@link ActiveTaskBo}
	 * @return {@link String}
	 */
	@Override
	public ActiveTaskBo saveOrUpdateActiveTaskInfo(ActiveTaskBo activeTaskBo) {
		logger.info("StudyActiveTasksDAOImpl - saveOrUpdateActiveTaskInfo() - Starts");
		Session session = null;
		StudySequenceBo studySequence = null;
		List<ActiveTaskAtrributeValuesBo> taskAttributeValueBos = new ArrayList<ActiveTaskAtrributeValuesBo>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(activeTaskBo.getId() == null){
				studySequence = (StudySequenceBo) session.getNamedQuery("getStudySequenceByStudyId").setInteger("studyId", activeTaskBo.getStudyId()).uniqueResult();
				if(studySequence != null){
					studySequence.setStudyExcActiveTask(true);
				}else{
					studySequence = new StudySequenceBo();
					studySequence.setStudyExcActiveTask(true);
					studySequence.setStudyId(activeTaskBo.getStudyId());
				}
				session.saveOrUpdate(studySequence);
			}
			if(activeTaskBo.getTaskAttributeValueBos()!=null && activeTaskBo.getTaskAttributeValueBos().size()>0)
				taskAttributeValueBos = activeTaskBo.getTaskAttributeValueBos();
			session.saveOrUpdate(activeTaskBo);
			if(taskAttributeValueBos!=null && taskAttributeValueBos.size()>0){
				for(ActiveTaskAtrributeValuesBo activeTaskAtrributeValuesBo:taskAttributeValueBos){
					   if(activeTaskAtrributeValuesBo.isAddToDashboard()){
						   activeTaskAtrributeValuesBo.setActiveTaskId(activeTaskBo.getId());
						   if(activeTaskAtrributeValuesBo.getAttributeValueId()==null){
							   session.save(activeTaskAtrributeValuesBo);
					        }else{
							   session.update(activeTaskAtrributeValuesBo); 
						   }
					   }
				   }
			}
			
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyActiveTasksDAOImpl - saveOrUpdateActiveTaskInfo() - Error",e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - saveOrUpdateActiveTaskInfo() - Ends");
		return activeTaskBo;
	}

	/**
	 * @author Ronalin
	 * @param Integer : activeTaskInfoId
	 * @return String :SUCCESS or FAILURE
	 *  This method used to get the delete the activeTask information
	 */
	@Override
	public String deleteActiveTask(ActiveTaskBo activeTaskBo) {
		logger.info("StudyActiveTasksDAOImpl - deleteActiveTAsk() - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		try {
			session = hibernateTemplate.getSessionFactory().openSession();
			if(activeTaskBo != null) {
				transaction =session.beginTransaction();
				String deleteQuery = "delete ActiveTaskAtrributeValuesBo where activeTaskId="+activeTaskBo.getId();
				query = session.createQuery(deleteQuery);
				query.executeUpdate();
				if(activeTaskBo.getActiveTaskFrequenciesList() != null && activeTaskBo.getActiveTaskFrequenciesList().size() > 0){
					deleteQuery = "delete from active_task_custom_frequencies where active_task_id="+activeTaskBo.getId();
					query = session.createSQLQuery(deleteQuery);
					query.executeUpdate();
					String deleteQuery2 = "delete from active_task_frequencies where active_task_id="+activeTaskBo.getId();
					query = session.createSQLQuery(deleteQuery2);
					query.executeUpdate();
				}
				if(activeTaskBo.getActiveTaskFrequenciesList() != null){
					ActiveTaskFrequencyBo activeTaskFrequencyBo = activeTaskBo.getActiveTaskFrequenciesBo();
					if(activeTaskFrequencyBo.getFrequencyDate() != null || activeTaskFrequencyBo.getFrequencyTime() != null || activeTaskBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME)){
						if(!activeTaskBo.getFrequency().equalsIgnoreCase(activeTaskBo.getPreviousFrequency())){
							deleteQuery = "delete from active_task_custom_frequencies where active_task_id="+activeTaskBo.getId();
							query = session.createSQLQuery(deleteQuery);
							query.executeUpdate();
							String deleteQuery2 = "delete from active_task_frequencies where active_task_id="+activeTaskBo.getId();
							query = session.createSQLQuery(deleteQuery2);
							query.executeUpdate();
						}
					}
				}
				if(activeTaskBo.getActiveTaskCustomScheduleBo() != null && activeTaskBo.getActiveTaskCustomScheduleBo().size() > 0){
					deleteQuery = "delete from active_task_custom_frequencies where active_task_id="+activeTaskBo.getId();
					query = session.createSQLQuery(deleteQuery);
					query.executeUpdate();
					String deleteQuery2 = "delete from active_task_frequencies where active_task_id="+activeTaskBo.getId();
					query = session.createSQLQuery(deleteQuery2);
					query.executeUpdate();
				}
				
				deleteQuery = "delete ActiveTaskBo where id="+activeTaskBo.getId();
				query = session.createQuery(deleteQuery);
				query.executeUpdate();
				message = fdahpStudyDesignerConstants.SUCCESS;
				transaction.commit();
			}
		} catch (Exception e) {
			transaction.rollback();
			logger.error("StudyActiveTasksDAOImpl - deleteActiveTAsk() - ERROR " , e);
		} finally {
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - deleteActiveTAsk() - Ends");
		return message;
	}

	@Override
	public ActiveTaskBo saveOrUpdateActiveTask(ActiveTaskBo activeTaskBo) {
		logger.info("StudyActiveTasksDAOImpl - saveOrUpdateActiveTask() - Starts");
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			session.saveOrUpdate(activeTaskBo);
			if(activeTaskBo.getType().equalsIgnoreCase(fdahpStudyDesignerConstants.SCHEDULE)){
				if(activeTaskBo != null &&  activeTaskBo.getId() != null){
					if(activeTaskBo.getActiveTaskFrequenciesList() != null && activeTaskBo.getActiveTaskFrequenciesList().size() > 0){
						String deleteQuery = "delete from active_task_custom_frequencies where active_task_id="+activeTaskBo.getId();
						query = session.createSQLQuery(deleteQuery);
						query.executeUpdate();
						String deleteQuery2 = "delete from active_task_frequencies where active_task_id="+activeTaskBo.getId();
						query = session.createSQLQuery(deleteQuery2);
						query.executeUpdate();
						for(ActiveTaskFrequencyBo activeTaskFrequencyBo : activeTaskBo.getActiveTaskFrequenciesList()){
							if(activeTaskFrequencyBo.getFrequencyTime() != null){
								if(activeTaskFrequencyBo.getActiveTaskId() == null){
									activeTaskFrequencyBo.setId(null);
									activeTaskFrequencyBo.setActiveTaskId(activeTaskBo.getId());
								}
								session.saveOrUpdate(activeTaskFrequencyBo);
							}
						} 
					}
					if(activeTaskBo.getActiveTaskFrequenciesList() != null){
						ActiveTaskFrequencyBo activeTaskFrequencyBo = activeTaskBo.getActiveTaskFrequenciesBo();
						if(activeTaskFrequencyBo.getFrequencyDate() != null || activeTaskFrequencyBo.getFrequencyTime() != null || activeTaskBo.getFrequency().equalsIgnoreCase(fdahpStudyDesignerConstants.FREQUENCY_TYPE_ONE_TIME)){
							if(!activeTaskBo.getFrequency().equalsIgnoreCase(activeTaskBo.getPreviousFrequency())){
								String deleteQuery = "delete from active_task_custom_frequencies where active_task_id="+activeTaskBo.getId();
								query = session.createSQLQuery(deleteQuery);
								query.executeUpdate();
								String deleteQuery2 = "delete from active_task_frequencies where active_task_id="+activeTaskBo.getId();
								query = session.createSQLQuery(deleteQuery2);
								query.executeUpdate();
							}
							if(activeTaskFrequencyBo.getActiveTaskId() == null){
								activeTaskFrequencyBo.setActiveTaskId(activeTaskBo.getId());
							}
							if(activeTaskBo.getActiveTaskFrequenciesBo().getFrequencyDate() != null && !activeTaskBo.getActiveTaskFrequenciesBo().getFrequencyDate().isEmpty()){
								activeTaskFrequencyBo.setFrequencyDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(activeTaskBo.getActiveTaskFrequenciesBo().getFrequencyDate())));
							}
							session.saveOrUpdate(activeTaskFrequencyBo);
						}
					}
					if(activeTaskBo.getActiveTaskCustomScheduleBo() != null && activeTaskBo.getActiveTaskCustomScheduleBo().size() > 0){
						String deleteQuery = "delete from active_task_custom_frequencies where active_task_id="+activeTaskBo.getId();
						query = session.createSQLQuery(deleteQuery);
						query.executeUpdate();
						String deleteQuery2 = "delete from active_task_frequencies where active_task_id="+activeTaskBo.getId();
						query = session.createSQLQuery(deleteQuery2);
						query.executeUpdate();
						for(ActiveTaskCustomScheduleBo activeTaskCustomScheduleBo  : activeTaskBo.getActiveTaskCustomScheduleBo()){
							if(activeTaskCustomScheduleBo.getFrequencyStartDate() != null && !activeTaskCustomScheduleBo.getFrequencyStartDate().isEmpty() && activeTaskCustomScheduleBo.getFrequencyEndDate() != null 
									&& !activeTaskCustomScheduleBo.getFrequencyEndDate().isEmpty() && activeTaskCustomScheduleBo.getFrequencyTime() != null){
								if(activeTaskCustomScheduleBo.getActiveTaskId() == null){
									activeTaskCustomScheduleBo.setActiveTaskId(activeTaskBo.getId());
								}
								activeTaskCustomScheduleBo.setFrequencyStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(activeTaskCustomScheduleBo.getFrequencyStartDate())));
								activeTaskCustomScheduleBo.setFrequencyEndDate(new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(activeTaskCustomScheduleBo.getFrequencyEndDate())));
								session.saveOrUpdate(activeTaskCustomScheduleBo);
							}
						}
					}
				}
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.info("StudyActiveTasksDAOImpl - saveOrUpdateActiveTask() - Error", e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - saveOrUpdateActiveTask() - Ends");
		return activeTaskBo;
	}
	/**
	 * @author Ronalin
	 * @return List :ActiveTaskListBos
	 *  This method used to get all type of activeTask
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveTaskListBo> getAllActiveTaskTypes() {
		logger.info("StudyActiveTasksDAOImpl - getAllActiveTaskTypes() - Starts");
		Session session = null;
		List<ActiveTaskListBo> activeTaskListBos = new ArrayList<ActiveTaskListBo>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			query = session.createQuery("from ActiveTaskListBo");
			activeTaskListBos = query.list();
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyActiveTasksDAOImpl - getAllActiveTaskTypes() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - getAllActiveTaskTypes() - Ends");
		return activeTaskListBos;
	}
	
	/**
	 * @author Ronalin
	 * @return List :ActiveTaskMasterAttributeBo
	 *  This method used to get  all the field names based on of activeTaskType
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ActiveTaskMasterAttributeBo> getActiveTaskMasterAttributesByType(String activeTaskType) {
		logger.info("StudyActiveTasksDAOImpl - getActiveTaskMasterAttributesByType() - Starts");
		Session session = null;
		List<ActiveTaskMasterAttributeBo> taskMasterAttributeBos = new ArrayList<ActiveTaskMasterAttributeBo>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			query = session.createQuery(" from ActiveTaskMasterAttributeBo where taskTypeId="+Integer.parseInt(activeTaskType));
			taskMasterAttributeBos = query.list();
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyActiveTasksDAOImpl - getActiveTaskMasterAttributesByType() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - getActiveTaskMasterAttributesByType() - Ends");
		return taskMasterAttributeBos;
	}
	
	/**
	 * @author Ronalin
	 * @return List :StatisticImageListBo
	 *  This method used to get  all  statistic images
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<StatisticImageListBo> getStatisticImages() {
		logger.info("StudyActiveTasksDAOImpl - getStatisticImages() - Starts");
		Session session = null;
		List<StatisticImageListBo> imageListBos = new ArrayList<StatisticImageListBo>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			query = session.createQuery("from StatisticImageListBo");
			imageListBos = query.list();
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyActiveTasksDAOImpl - getStatisticImages() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - getStatisticImages() - Ends");
		return imageListBos;
	}
	
	
	/**
	 * @author Ronalin
	 * @return List :ActivetaskFormulaBo
	 *  This method used to get  all  static formulas
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<ActivetaskFormulaBo> getActivetaskFormulas() {
		logger.info("StudyActiveTasksDAOImpl - getActivetaskFormulas() - Starts");
		Session session = null;
		List<ActivetaskFormulaBo> activetaskFormulaList = new ArrayList<ActivetaskFormulaBo>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction =session.beginTransaction();
			query = session.createQuery("from ActivetaskFormulaBo");
			activetaskFormulaList = query.list();
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("StudyActiveTasksDAOImpl - getActivetaskFormulas() - ERROR " , e);
		}finally{
			session.close();
		}
		logger.info("StudyActiveTasksDAOImpl - getActivetaskFormulas() - Ends");
		return activetaskFormulaList;
	}


	@SuppressWarnings("unchecked")
	@Override
	public boolean validateActiveTaskAttrById(Integer studyId, String activeTaskAttName, String activeTaskAttIdVal, String activeTaskAttIdName)
			throws Exception {
		logger.info("StudyDAOImpl - validateActiveTaskAttrById() - Starts");
		boolean flag = false;
		Session session =null;
		String queryString = "", subString="";
		ActiveTaskBo  taskBo = new ActiveTaskBo();
		List<ActiveTaskAtrributeValuesBo> taskAtrributeValuesBos = new ArrayList<ActiveTaskAtrributeValuesBo>();
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			if(studyId!=null && StringUtils.isNotEmpty(activeTaskAttName) && StringUtils.isNotEmpty(activeTaskAttIdVal)){
				if(activeTaskAttName.equalsIgnoreCase(fdahpStudyDesignerConstants.SHORT_NAME_STATISTIC)){
					if(!activeTaskAttIdName.equals("static"))
					subString = " and attributeValueId!="+activeTaskAttIdName;
					queryString = "from ActiveTaskAtrributeValuesBo where displayNameStat='"+activeTaskAttIdVal+"'"+subString+")";
					taskAtrributeValuesBos = session.createQuery(queryString).list();
					if(taskAtrributeValuesBos==null || taskAtrributeValuesBos.size()==0)
						flag = true;
				}else if(activeTaskAttName.equalsIgnoreCase(fdahpStudyDesignerConstants.SHORT_TITLE)){
					queryString = "from ActiveTaskBo where studyId="+studyId+" and shortTitle='"+activeTaskAttIdVal+"'";
					taskBo = (ActiveTaskBo)session.createQuery(queryString).uniqueResult();
					if(taskBo!=null)
						flag = true;
				}
			}
		}catch(Exception e){
			logger.error("StudyDAOImpl - validateActiveTaskAttrById() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("StudyDAOImpl - validateActiveTaskAttrById() - Starts");
		return flag;
	}
}
