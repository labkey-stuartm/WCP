package com.fdahpstudydesigner.dao;

import com.fdahpstudydesigner.bo.MasterDataBO;
import com.fdahpstudydesigner.bo.UserBO;

public interface DashBoardAndProfileDAO {
	
	public String updateProfileDetails(UserBO userBO ,int userId);
	
	public String isEmailValid(String email);
	
	public MasterDataBO getMasterData(String type);
}
