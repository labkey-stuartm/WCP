package com.fdahpStudyDesigner.service;

import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.dao.DashBoardAndProfileDAO;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Service
public class DashBoardAndProfileServiceImpl implements DashBoardAndProfileService{
	
	private static Logger logger = Logger.getLogger(DashBoardAndProfileServiceImpl.class);
	
	@Autowired
	private DashBoardAndProfileDAO dashBoardAndProfiledao;
	
	@SuppressWarnings("unchecked")
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	@Override
	public String updateProfileDetails(UserBO userBO, int userId) {
		logger.info("DashBoardAndProfileServiceImpl - updateProfileDetails - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
				message = dashBoardAndProfiledao.updateProfileDetails(userBO, userId);
		}catch(Exception e){
			logger.error("DashBoardAndProfileServiceImpl - updateProfileDetails() - Error",e);
		}
		logger.info("DashBoardAndProfileServiceImpl - updateProfileDetails - Starts");
		return message;
	}
}
