package com.fdahpStudyDesigner.controller;

import java.io.IOException;
import java.io.PrintWriter;
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

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.service.ManageUsersService;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Controller
public class ManageUsersController {
	
	private static Logger logger = Logger.getLogger(ManageUsersController.class.getName());
	private ManageUsersService manageUsersService;

	@Autowired
	public void setManageUsersService(ManageUsersService manageUsersService) {
		this.manageUsersService = manageUsersService;
	}
	@RequestMapping("/adminUsers/getUserList.do")
	public ModelAndView getUserList(HttpServletRequest request){
		logger.info("ManageUsersController - getUserList() - Starts");
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		List<UserBO> userList = null;
		String sucMsg = "";
		String errMsg = "";
		try{
			if(fdahpStudyDesignerUtil.isSession(request)){
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
				userList = manageUsersService.getUserList();
				map.addAttribute("userList", userList);
				mav = new ModelAndView("",map);
			}
		}catch(Exception e){
			logger.error("ManageUsersController - getUserList() - ERROR",e);
		}
		logger.info("ManageUsersServiceImpl - getUserList() - Ends");
		return mav;
	}
	
	@RequestMapping("/adminUsers/activateOrDeactivateUser.do")
	public void activateOrDeactivateUser(HttpServletRequest request,HttpServletResponse response,String userId,String userStatus) throws IOException{
		logger.info("ManageUsersController - activateOrDeactivateUser() - Starts");
		String msg = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			HttpSession session = request.getSession();
			SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != userSession){
				msg = manageUsersService.activateOrDeactivateUser(Integer.valueOf(userId), Integer.valueOf(userStatus), userSession.getUserId());
			}
		}catch(Exception e){
			logger.error("ManageUsersController - activateOrDeactivateUser() - ERROR",e);
		}
		logger.info("ManageUsersServiceImpl - activateOrDeactivateUser() - Ends");
		jsonobject.put("message", msg);
		response.setContentType("application/json");
		out= response.getWriter();
		out.print(jsonobject);
	}
}
