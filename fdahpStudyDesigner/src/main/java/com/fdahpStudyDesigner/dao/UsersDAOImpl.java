package com.fdahpStudyDesigner.dao;

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

import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.StudyPermissionBO;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.bo.UserPermissions;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;

@Repository
public class UsersDAOImpl implements UsersDAO{
	
	private static Logger logger = Logger.getLogger(UsersDAOImpl.class);
	HibernateTemplate hibernateTemplate;
	private Transaction transaction = null;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}

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
			query = session.createSQLQuery(" SELECT u.user_id,u.first_name,u.last_name,u.email,r.role_name,u.`status` FROM users u,roles r WHERE r.role_id = u.role_id ");
			objList = query.list();
			if(null != objList && objList.size() > 0){
				userList = new ArrayList<UserBO>();
				for(Object[] obj:objList){
					UserBO userBO = new UserBO();
					userBO.setUserId(null != obj[0] ? (Integer)obj[0] : 0);
					userBO.setFirstName(null != obj[1] ? String.valueOf(obj[1]) : "");
					userBO.setLastName(null != obj[2] ? String.valueOf(obj[2]) : "");
					userBO.setUserEmail(null != obj[3] ? String.valueOf(obj[3]) : "");
					userBO.setRoleName(null != obj[4] ? String.valueOf(obj[4]) : "");
					userBO.setEnabled(null != obj[5] ? (Boolean)obj[5] : false);
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
	public String activateOrDeactivateUser(int userId,int userStatus,int loginUser) {
		logger.info("UsersDAOImpl - activateOrDeactivateUser() - Starts");
		String msg = fdahpStudyDesignerConstants.FAILURE;
		Session session = null;
		int count = 0;
		Query query = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(userStatus == 0){
				userStatus = 1;
			}else{
				userStatus = 0;
			}
			query = session.createQuery(" UPDATE UserBO SET enabled = "+userStatus+", modifiedOn = now(), modifiedBy = "+loginUser+" WHERE userId = "+userId );
			count = query.executeUpdate();
			transaction.commit();
			if(count > 0){
				msg = fdahpStudyDesignerConstants.SUCCESS;
			}
		}catch(Exception e){
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
		String msg = fdahpStudyDesignerConstants.FAILURE;
		Query query = null;
		UserBO userBO2 = null;
		Set<UserPermissions> permissionSet = null;
		List<StudyPermissionBO> studyPermissionBOList = null;
		 String[] selectedStudiesList = null;
		 String[] permissionValuesList = null;
		try{
			session = hibernateTemplate.getSessionFactory().openSession();
			transaction = session.beginTransaction();
			if(null == userBO.getUserId()){
				userId = (Integer) session.save(userBO);
			}else{
				session.update(userBO);
				userId = userBO.getUserId();
			}
			
			if(permissions != ""){
				query = session.createQuery(" FROM UserBO UBO where UBO.userId = "+userId);
				userBO2 = (UserBO) query.uniqueResult();
				permissionSet = new HashSet<UserPermissions>(session.createQuery("FROM UserPermissions UPBO WHERE UPBO.permissions IN ("+permissions+")").list());
				userBO2.setPermissionList(permissionSet);
				session.update(userBO2);
			}
			
			if(!selectedStudies.equals("") && !permissionValues.equals("")){
				selectedStudiesList = selectedStudies.split(",");
				permissionValuesList = permissionValues.split(",");
				
				query = session.createSQLQuery(" delete from study_permission where study_id not in (1,2) and user_id ="+userId );
				query.executeUpdate();
				
				/*query = session.createQuery(" FROM StudyPermissionBO UBO where UBO.studyId IN ("+selectedStudies+") AND UBO.userId ="+userId);
				studyPermissionBOList = query.list();*/
				/*for(int i=0;i<selectedStudiesList.length-1;i++){
					
				}*/
				/*for(StudyPermissionBO spBO:studyPermissionBOList){
					query = session.createQuery(" FROM StudyPermissionBO SPBO WHERE SPBO.userId = "+userId+" AND SPBO.studyId = "+spBO.getStudyId());
					studyPermissionBO = (StudyPermissionBO) query.uniqueResult();
					if(studyPermissionBO != null){
						
					}else{
						
					}
					session.save(spBO);
				}*/
			}
			
			transaction.commit();
			msg = fdahpStudyDesignerConstants.SUCCESS;
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
}
