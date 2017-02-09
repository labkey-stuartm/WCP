package com.fdahpStudyDesigner.controller;


import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

/**
 * @author Ronalin
 *
 */
@Controller
public class DashBoardAndProfileController {
private static Logger logger = Logger.getLogger(DashBoardAndProfileController.class.getName());
	
	
	@SuppressWarnings("unchecked")
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	/*DashBoard Start*/
	
	/**
	 * Navigate to  Acuity admin dash board page
	 * @author Ronalin
	 *  
	 * @param request , {@link HttpServletRequest}
	 * @return {@link ModelAndView} , dashBoardPage page
	 */
	@RequestMapping("/adminDashboard/viewDashBoard.do")
	public ModelAndView getAdminDashboard(HttpServletRequest request){
		logger.info("DashBoardAndProfileController - getAdminDashboard - Starts");
		ModelAndView mav = new ModelAndView();
		try{
			mav = new ModelAndView("fdaAdminDashBoardPage");
		}catch(Exception e){
			logger.error("DashBoardAndProfileController - getAdminDashboard - ERROR",e);
		}
		logger.info("DashBoardAndProfileController - getAdminDashboard - Ends");
		return mav;
	}
}
