package com.fdahpstudydesigner.dao;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

import com.fdahpstudydesigner.bo.RoleBO;
import com.fdahpstudydesigner.bo.StudyPermissionBO;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.bo.UserPermissions;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.SessionObject;

@Repository
public class UsersDAOImpl implements UsersDAO{
	
	private static Logger logger = Logger.getLogger(UsersDAOImpl.class);
	HibernateTemplate hibernateTemplate;
	private Transaction transaction = null;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	
	@Autowired
	private AuditLogDAO auditLogDAO;

	@SuppressWarnings("unchecked")
	@Override
	public List<UserBO> getUserList() {
		logger.info("UsersDAOImpl - getUserList() - Starts");
		Session session = null;
		List<UserBO> userList = null;
		List<Object[]> objList = null;
		Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createSQLQuery(" SELECT u.user_id,u.first_name,u.last_name,u.email,r.role_name,u.status,"
					+ "u.password FROM users u,roles r WHERE r.role_id = u.role_id and u.user_id "
					+ "not in (select upm.user_id from user_permission_mapping upm where "
					+ "upm.permission_id = (select up.permission_id from user_permissions up "
					+ "where up.permissions ='ROLE_SUPERADMIN')) ORDER BY u.user_id DESC ");
			objList = query.list();
			if(null != objList && !objList.isEmpty()){
				userList = new ArrayList<>();
				for(Object[] obj:objList){
					UserBO userBO = new UserBO();
					userBO.setUserId(null != obj[0] ? (Integer)obj[0] : 0);
					userBO.setFirstName(null != obj[1] ? String.valueOf(obj[1]) : "");
					userBO.setLastName(null != obj[2] ? String.valueOf(obj[2]) : "");
					userBO.setUserEmail(null != obj[3] ? String.valueOf(obj[3]) : "");
					userBO.setRoleName(null != obj[4] ? String.valueOf(obj[4]) : "");
					userBO.setEnabled(null != obj[5] ? (Boolean)obj[5] : false);
					userBO.setUserPassword(null != obj[6] ? String.valueOf(obj[6]) : "");
					userBO.setUserFullName(userBO.getFirstName()+" "+userBO.getLastName());
					userList.add(userBO);
				}
			}
		}catch(Exception e){
			logger.error("UsersDAOImpl - getUserList() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getUserList() - Ends");
		return userList;
	}

	@Override
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser,SessionObject userSession) {
		logger.info("UsersDAOImpl - activateOrDeactivateUser() - Starts");
		String msg = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		Query query = null;
		Boolean forceLogout = false;
		String activity = "";
		String activityDetail = ""; 
		UserBO userBO = null;
		int userStatusNew;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			userBO = getUserDetails(userId);
			transaction = session.beginTransaction();
			if(userStatus == 0){
				userStatusNew = 1;
				forceLogout = false;
				activity = "User activated";
				activityDetail = "User named "+userBO.getFirstName()+" "+userBO.getLastName()+" is activated";
			}else{
				userStatusNew = 0;
				forceLogout = true;
				activity = "User deactivated";
				activityDetail = "User named "+userBO.getFirstName()+" "+userBO.getLastName()+" is deactivated";
			}
			query = session.createQuery(" UPDATE UserBO SET enabled = "+userStatusNew+", modifiedOn = now(), modifiedBy = "+loginUser+",forceLogout = "+forceLogout+" WHERE userId = "+userId );
			count = query.executeUpdate();
			if(count > 0){
				auditLogDAO.saveToAuditLog(session, transaction, userSession, activity, activityDetail ,"UsersDAOImpl - activateOrDeactivateUser()");
				msg = FdahpStudyDesignerConstants.SUCCESS;
			}
			transaction.commit();
		}catch(Exception e){
			transaction.rollback();
			logger.error("UsersDAOImpl - activateOrDeactivateUser() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - activateOrDeactivateUser() - Ends");
		return msg;
	}

	@Override
	public UserBO getUserDetails(int userId) {
		logger.info("UsersDAOImpl - getUserDetails() - Starts");
		Session session = null;
		UserBO userBO = null;
		Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getUserById").setInteger("userId", userId);
			userBO = (UserBO) query.uniqueResult();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getUserDetails() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getUserDetails() - Ends");
		return userBO;
	}

	@Override
	public RoleBO getUserRole(int roleId) {
		logger.info("UsersDAOImpl - getUserRole() - Starts");
		Session session = null;
		RoleBO roleBO = null;
		Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.getNamedQuery("getUserRoleByRoleId").setInteger("roleId", roleId);
			roleBO = (RoleBO) query.uniqueResult();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getUserRole() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getUserRole() - Ends");
		return roleBO;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String addOrUpdateUserDetails(UserBO userBO,String permissions,String selectedStudies,String permissionValues) {
		logger.info("UsersDAOImpl - addOrUpdateUserDetails() - Starts");
		Session session = null;
		Integer userId = 0;
		String msg = FdahpStudyDesignerConstants.FAILURE;
		Query query = null;
		UserBO userBO2 = null;
		Set<UserPermissions> permissionSet = null;
		StudyPermissionBO studyPermissionBO = null;
		String[] selectedStudy = null;
		String[] permissionValue = null;
		boolean updateFlag = false;
		
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(null == userBO.getUserId()){
				userId = (Integer) session.save(userBO);
			}else{
				session.update(userBO);
				userId = userBO.getUserId();
				updateFlag = true;
			}
			
			query = session.createQuery(" FROM UserBO UBO where UBO.userId = "+userId);
			userBO2 = (UserBO) query.uniqueResult();
			if(!permissions.isEmpty()){
				permissionSet = new HashSet<UserPermissions>(session.createQuery("FROM UserPermissions UPBO WHERE UPBO.permissions IN ("+permissions+")").list());
				userBO2.setPermissionList(permissionSet);
				session.update(userBO2);
			}else{
				userBO2.setPermissionList(null);
				session.update(userBO2);
			}
			
			if(updateFlag && "".equals(selectedStudies)){
				query = session.createSQLQuery(" delete from study_permission where user_id ="+userId );
				query.executeUpdate();
			}
			
			if(!"".equals(selectedStudies) && !"".equals(permissionValues)){
				selectedStudy = selectedStudies.split(",");
				permissionValue = permissionValues.split(",");
				
				if(updateFlag){
					query = session.createSQLQuery(" delete from study_permission where study_id not in ("+ selectedStudies +") and user_id ="+userId );
					query.executeUpdate();
				}
				
				for(int i=0;i<selectedStudy.length;i++){
					query = session.createQuery(" FROM StudyPermissionBO UBO where UBO.studyId = "+ selectedStudy[i] +" AND UBO.userId ="+userId);
					studyPermissionBO = (StudyPermissionBO) query.uniqueResult();
					if(null != studyPermissionBO){
						studyPermissionBO.setViewPermission("1".equals(permissionValue[i]) ? true : false);
						session.update(studyPermissionBO);
					}else{
						studyPermissionBO = new StudyPermissionBO();
						studyPermissionBO.setStudyId(Integer.parseInt(selectedStudy[i]));
						studyPermissionBO.setViewPermission("1".equals(permissionValue[i]) ? true : false);
						studyPermissionBO.setUserId(userId);
						session.save(studyPermissionBO);
					}
				}
			}
			transaction.commit();
			msg = FdahpStudyDesignerConstants.SUCCESS;
		}catch(Exception e){
			transaction.rollback();
			logger.error("UsersDAOImpl - addOrUpdateUserDetails() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - addOrUpdateUserDetails() - Ends");
		return msg;
	}
	
	@Override
	public String forceLogOut(SessionObject userSession) {
		logger.info("UsersDAOImpl - forceLogOut() - Starts");
		String msg = FdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		Query query = null;
		Integer count = 0;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			query = session.createQuery(" UPDATE UserBO SET forceLogout = true WHERE userId = "+userSession.getUserId());
			count = query.executeUpdate();
			transaction.commit();
			if(count > 0){
				msg = FdahpStudyDesignerConstants.SUCCESS;
			}
		}catch(Exception e){
			transaction.rollback();
			logger.error("UsersDAOImpl - forceLogOut() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - forceLogOut() - Ends");
		return msg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<RoleBO> getUserRoleList() {
		logger.info("UsersDAOImpl - getUserRoleList() - Starts");
		List<RoleBO> roleBOList = null;
		Query query = null;
		Session session = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery(" FROM RoleBO RBO ");
			roleBOList = query.list();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getUserRoleList() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getUserRoleList() - Ends");
		return roleBOList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Integer> getPermissionsByUserId(Integer userId){
		logger.info("UsersDAOImpl - getPermissionsByUserId() - Starts");
		Session session = null;
		Query query = null;
		List<Integer> permissions = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createSQLQuery(" SELECT UPM.permission_id FROM user_permission_mapping UPM WHERE UPM.user_id = "+userId+"");
			permissions = query.list();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getPermissionsByUserId() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getPermissionsByUserId() - Ends");
		return permissions;
	}

	@Override
	public List<String> getSuperAdminList() {
		logger.info("UsersDAOImpl - getSuperAdminList() - Starts");
		Session session = null;
		List<String> userSuperAdminList = null;
		Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createSQLQuery("Select u.email from users u where u.user_id in (select upm.user_id from user_permission_mapping upm where upm.permission_id = 1)");
			userSuperAdminList = query.list();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getSuperAdminList() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getSuperAdminList() - Ends");
		return userSuperAdminList;
	}
	
	@Override
	public UserBO getSuperAdminNameByEmailId(String emailId) {
		logger.info("UsersDAOImpl - getSuperAdminNameByEmailId() - Starts");
		Session session = null;
		UserBO userBo = null;
		Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createQuery(" from UserBO where userEmail = '"+emailId+"'");
			userBo = (UserBO) query.uniqueResult();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getSuperAdminNameByEmailId() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getSuperAdminNameByEmailId() - Ends");
		return userBo;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<String> getActiveUserEmailIds() {
		logger.info("UsersDAOImpl - getActiveUserEmailIds() - Starts");
		Session session = null;
		List<String> emails = null;
		Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			query = session.createSQLQuery(" SELECT u.email "
					+ "FROM users u,roles r WHERE r.role_id = u.role_id and u.user_id "
					+ "not in (select upm.user_id from user_permission_mapping upm where "
					+ "upm.permission_id = (select up.permission_id from user_permissions up "
					+ "where up.permissions ='ROLE_SUPERADMIN')) ORDER BY u.user_id DESC ");
			emails = query.list();
		}catch(Exception e){
			logger.error("UsersDAOImpl - getActiveUserEmailIds() - ERROR",e);
		}finally{
			if(null != session){
				session.close();
			}
		}
		logger.info("UsersDAOImpl - getActiveUserEmailIds() - Ends");
		return emails;
	}
	
	
}
