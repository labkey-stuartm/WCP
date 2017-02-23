package com.fdahpStudyDesigner.controller;


import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.service.DashBoardAndProfileService;
import com.fdahpStudyDesigner.service.StudyService;
import com.fdahpStudyDesigner.service.UsersService;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

/**
 * @author Ronalin
 *
 */
@Controller
public class DashBoardAndProfileController {
private static Logger logger = Logger.getLogger(DashBoardAndProfileController.class.getName());
	
	@Autowired
	private DashBoardAndProfileService dashBoardAndProfileService;
	
	@Autowired
	private UsersService usersService;
	
	@Autowired
	private StudyService studyService;

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
	
	@RequestMapping("/adminDashboard/viewUserDetails.do")
	public ModelAndView viewUserDetails(HttpServletRequest request){
		logger.info("DashBoardAndProfileController - viewUserDetails - Starts");
		ModelMap map = new ModelMap();
		ModelAndView mav = new ModelAndView();
		UserBO userBO = null;
		List<StudyListBean> studyAndPermissionList = null;
		RoleBO roleBO = null;
		String sucMsg = "";
		String errMsg = "";
		try{
			HttpSession session = request.getSession();
			SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(userSession != null){
			if(null != request.getSession().getAttribute("sucMsg")){
				sucMsg = (String) request.getSession().getAttribute("sucMsg");
				map.addAttribute("sucMsg", sucMsg);
				request.getSession().removeAttribute("sucMsg");
			}
			if(null != request.getSession().getAttribute("errMsg")){
				errMsg = (String) request.getSession().getAttribute("errMsg");
				map.addAttribute("errMsg", errMsg);
				request.getSession().removeAttribute("errMsg");
			}
				if(!"".equals(userSession.getUserId())){
					userBO = usersService.getUserDetails(Integer.valueOf(userSession.getUserId()));
					roleBO = usersService.getUserRole(userBO.getRoleId());
					if(null != roleBO){
						userBO.setRoleName(roleBO.getRoleName());
					}
					if(null != userBO){
						studyAndPermissionList = studyService.getStudyList(userBO.getUserId());
					}
				}
				map.addAttribute("studyAndPermissionList", studyAndPermissionList);
				map.addAttribute("userBO", userBO);
				mav = new ModelAndView("myAccount", map);
		}
	
		}catch(Exception e){
			logger.error("DashBoardAndProfileController - viewUserDetails - ERROR", e);
		}
		logger.info("DashBoardAndProfileController - viewUserDetails - Ends");
		return mav;
	}
	
	@SuppressWarnings({"unused" })
	@RequestMapping("/adminDashboard/updateUserDetails.do")
	public ModelAndView updateProfileDetails(HttpServletRequest request, UserBO userBO){
		logger.info("DashBoardAndProfileController - Entry Point: updateProfileDetails()");
		ModelAndView mav = new ModelAndView();
		UserBO user = null;
		Integer userId = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
				HttpSession session = request.getSession();
				SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
				if(null != userSession){
					userBO.setModifiedBy(userSession.getUserId());
					userBO.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
					userId = userSession.getUserId();
					message = dashBoardAndProfileService.updateProfileDetails(userBO ,userId);
					if(message.equals(fdahpStudyDesignerConstants.SUCCESS)){
						userSession.setFirstName(fdahpStudyDesignerUtil.isEmpty(userBO.getFirstName()) ? userSession.getFirstName() : userBO.getFirstName());
						userSession.setLastName(fdahpStudyDesignerUtil.isEmpty(userBO.getLastName()) ? userSession.getLastName() : userBO.getLastName());
						userSession.setEmail(fdahpStudyDesignerUtil.isEmpty(userBO.getUserEmail()) ? userSession.getEmail() : userBO.getUserEmail());
						request.getSession(false).setAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT,userSession);
					}
					if (fdahpStudyDesignerConstants.SUCCESS.equals(message)) {
						request.getSession().setAttribute("sucMsg",	"Your Profile Updated Successfully!!");
					} else  {
						request.getSession().setAttribute("errMsg",	"Sorry, there was an error encountered and your request could not be processed. Please try again.");
					}
					mav = new ModelAndView("redirect:/adminDashboard/viewUserDetails.do");
			}
		}catch (Exception e) {
			logger.error("DashBoardAndProfileController:  updateProfileDetails()' = ", e);
		}
		logger.info("DashBoardAndProfileController - Exit Point: updateProfileDetails()");
		return mav;
	}
}
