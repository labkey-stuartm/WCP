package com.fdahpstudydesigner.service;

import com.fdahpstudydesigner.bo.MasterDataBO;
import com.fdahpstudydesigner.bo.UserBO;
import com.fdahpstudydesigner.util.SessionObject;

public interface DashBoardAndProfileService {

	public String updateProfileDetails(UserBO userBO, int userId,SessionObject userSession);
	
	public String isEmailValid(String email);
	
	public MasterDataBO getMasterData(String type);

}
