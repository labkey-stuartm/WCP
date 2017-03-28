package com.fdahpStudyDesigner.controller;


import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.service.DashBoardAndProfileService;
import com.fdahpStudyDesigner.service.LoginService;
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
	
	@Autowired
	private LoginService loginService;

	/*DashBoard Start*/
	
	/**
	 * Navigate to  FDA admin dash board page
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
	
	/*MyAccount Starts*/
	
	/**
	 * Kanchana
	 * 
	 * Method to view user Details
	 * @param request
	 * @return
	 */
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
	
	/**
	 * Kanchana
	 * 
	 * Updating User Details
	 * @param request
	 * @param userBO
	 * @return
	 */
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
						request.getSession().setAttribute("sucMsg",	"Your profile has been successfully updated.");
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
	
	/**
	 * Kanchana
	 * 
	 * updating user password 
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping("/adminDashboard/changePassword.do")
	public void changePassword(HttpServletRequest request, HttpServletResponse response){
		logger.info("DashBoardAndProfileController - changePassword() - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = "";
		int userId = 0;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
				userId =  sessionObject.getUserId();
				String newPassword = null != request.getParameter("newPassword") && !"".equals(request.getParameter("newPassword")) ? request.getParameter("newPassword"):"";
				String oldPassword = null != request.getParameter("oldPassword") && !"".equals(request.getParameter("oldPassword")) ? request.getParameter("oldPassword"):"";
				message = loginService.changePassword(userId, newPassword, oldPassword);
				jsonobject.put("message", message);
				response.setContentType("application/json");
				out = response.getWriter();
				out.print(jsonobject);
			}
			}catch (Exception e) {
				message = fdahpStudyDesignerConstants.FAILURE;
				jsonobject.put("message",message);
				response.setContentType("application/json");
				out.print(jsonobject);
				logger.error("DashBoardAndProfileController - changePassword() - ERROR " , e);
			}
			logger.info("DashBoardAndProfileController - changePassword() - Ends");
	}
	
	/*MyAccount Ends*/
	
	/**
	 * Kanchana
	 * 
	 * @param request
	 * @param response
	 * @param email
	 */
	@RequestMapping("/isEmailValid.do")
	public void isEmailValid(HttpServletRequest request, HttpServletResponse response, String email){
		logger.info("DashBoardAndProfileController - isEmailValid() - Starts ");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			if(fdahpStudyDesignerUtil.isNotEmpty(email)){
				message = dashBoardAndProfileService.isEmailValid(email);
			}
			jsonobject.put("message", message);
			response.setContentType("application/json");
			out = response.getWriter();
			out.print(jsonobject);
		}catch (Exception e) {
			message = fdahpStudyDesignerConstants.FAILURE;
			response.setContentType("application/json");
			out.print(jsonobject);
			logger.error("DashBoardAndProfileController - isEmailValid() - ERROR " + e);
		}
		logger.info("DashBoardAndProfileController - isEmailValid() - Ends ");
	}
}
