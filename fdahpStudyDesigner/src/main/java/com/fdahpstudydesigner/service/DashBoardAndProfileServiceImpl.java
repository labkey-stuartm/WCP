package com.fdahpstudydesigner.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.dao.AuditLogDAO;
import com.fdahpstudydesigner.dao.DashBoardAndProfileDAO;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.SessionObject;


/**
 * 
 * @author Kanchana
 *
 */
@Service
public class DashBoardAndProfileServiceImpl implements DashBoardAndProfileService{
	
	private static Logger logger = Logger.getLogger(DashBoardAndProfileServiceImpl.class);
	
	@Autowired
	private DashBoardAndProfileDAO dashBoardAndProfiledao;
	
	@Autowired
	private AuditLogDAO auditLogDAO;
	
	
	/**
	 * Kanchana
	 * Updating User Details
	 */
	@Override
	public String updateProfileDetails(UserBO userBO, int userId,SessionObject userSession) {
		logger.info("DashBoardAndProfileServiceImpl - updateProfileDetails - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		String activity = "";
		String activityDetail = ""; 
		try{
				message = dashBoardAndProfiledao.updateProfileDetails(userBO, userId);
				if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
					activity = "Admin details updated";
					activityDetail = "Admin changes in his own details is successfully updated";
					auditLogDAO.saveToAuditLog(null, userSession, activity, activityDetail ,"DashBoardAndProfileDAOImpl - updateProfileDetails()");
				}
		}catch(Exception e){
			logger.error("DashBoardAndProfileServiceImpl - updateProfileDetails() - Error",e);
		}
		logger.info("DashBoardAndProfileServiceImpl - updateProfileDetails - Starts");
		return message;
	}
	
	/**
	 * Kanchana
	 * Validating UserEmail
	 */
	public String isEmailValid(String email) {
		return dashBoardAndProfiledao.isEmailValid(email);
	}
}
