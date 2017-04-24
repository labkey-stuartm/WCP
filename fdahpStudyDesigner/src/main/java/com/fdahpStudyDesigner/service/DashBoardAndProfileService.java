package com.fdahpStudyDesigner.service;

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.util.SessionObject;

public interface DashBoardAndProfileService {

	public String updateProfileDetails(UserBO userBO, int userId,SessionObject userSession);
	
	public String isEmailValid(String email);
}
