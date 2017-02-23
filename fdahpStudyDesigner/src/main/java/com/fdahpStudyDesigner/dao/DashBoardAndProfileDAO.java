package com.fdahpStudyDesigner.dao;

import com.fdahpStudyDesigner.bo.UserBO;

public interface DashBoardAndProfileDAO {
	
	public String updateProfileDetails(UserBO userBO ,int userId);
}
