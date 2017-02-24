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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bean.StudyListBean;
import com.fdahpStudyDesigner.bo.RoleBO;
import com.fdahpStudyDesigner.bo.StudyBo;
import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.service.StudyService;
import com.fdahpStudyDesigner.service.UsersService;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Controller
public class UsersController {
	
	private static Logger logger = Logger.getLogger(UsersController.class.getName());
	
	@Autowired
	private UsersService usersService;

	@Autowired
	private StudyService studyService;
	
	@RequestMapping("/adminUsersView/getUserList.do")
	public ModelAndView getUserList(HttpServletRequest request){
		logger.info("UsersController - getUserList() - Starts");
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
				userList = usersService.getUserList();
				map.addAttribute("userList", userList);
				mav = new ModelAndView("userListPage",map);
			}
		}catch(Exception e){
			logger.error("UsersController - getUserList() - ERROR",e);
		}
		logger.info("UsersController - getUserList() - Ends");
		return mav;
	}
	
	@RequestMapping("/adminUsersEdit/activateOrDeactivateUser.do")
	public void activateOrDeactivateUser(HttpServletRequest request,HttpServletResponse response,String userId,String userStatus) throws IOException{
		logger.info("UsersController - activateOrDeactivateUser() - Starts");
		String msg = fdahpStudyDesignerConstants.FAILURE;
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		try{
			HttpSession session = request.getSession();
			SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != userSession){
				msg = usersService.activateOrDeactivateUser(Integer.valueOf(userId), Integer.valueOf(userStatus), userSession.getUserId());
			}
		}catch(Exception e){
			logger.error("UsersController - activateOrDeactivateUser() - ERROR",e);
		}
		logger.info("UsersController - activateOrDeactivateUser() - Ends");
		jsonobject.put("message", msg);
		response.setContentType("application/json");
		out= response.getWriter();
		out.print(jsonobject);
	}
	
	@RequestMapping("/adminUsersEdit/addOrEditUserDetails.do")
	public ModelAndView addOrEditUserDetails(HttpServletRequest request){
		logger.info("UsersController - addOrEditUserDetails() - Starts");
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		UserBO userBO = null;
		List<StudyListBean> studyBOs = null;
		List<RoleBO> roleBOList = null;
		List<StudyBo> studyBOList = null;
		String actionPage = "";
		List<Integer> permissions = null;
		try{
			if(fdahpStudyDesignerUtil.isSession(request)){
				String userId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("userId")) == true ? "" : request.getParameter("userId");
				if(!"".equals(userId)){
					actionPage = fdahpStudyDesignerConstants.EDIT_PAGE;
					userBO = usersService.getUserDetails(Integer.valueOf(userId));
					if(null != userBO){
						studyBOs = studyService.getStudyList(userBO.getUserId());
						permissions = usersService.getPermissionsByUserId(userBO.getUserId());
					}
				}else{
					actionPage = fdahpStudyDesignerConstants.ADD_PAGE;
				}
				roleBOList = usersService.getUserRoleList();
				studyBOList = studyService.getStudies();
				map.addAttribute("actionPage", actionPage);
				map.addAttribute("userBO", userBO);
				map.addAttribute("permissions", permissions);
				map.addAttribute("roleBOList", roleBOList);
				map.addAttribute("studyBOList", studyBOList);
				map.addAttribute("studyBOs", studyBOs);
				mav = new ModelAndView("addOrEditUserPage",map);
			}
		}catch(Exception e){
			logger.error("UsersController - addOrEditUserDetails() - ERROR",e);
		}
		logger.info("UsersController - addOrEditUserDetails() - Ends");
		return mav;
	}
	
	@RequestMapping("/adminUsersView/viewUserDetails.do")
	public ModelAndView viewUserDetails(HttpServletRequest request){
		logger.info("UsersController - viewUserDetails() - Starts");
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		UserBO userBO = null;
		List<StudyListBean> studyBOs = null;
		List<RoleBO> roleBOList = null;
		List<StudyBo> studyBOList = null;
		String actionPage = fdahpStudyDesignerConstants.VIEW_PAGE;
		try{
			if(fdahpStudyDesignerUtil.isSession(request)){
				String userId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("userId")) == true ? "" : request.getParameter("userId");
				if(!"".equals(userId)){
					userBO = usersService.getUserDetails(Integer.valueOf(userId));
					if(null != userBO){
						studyBOs = studyService.getStudyList(userBO.getUserId());
					}
				}
				roleBOList = usersService.getUserRoleList();
				studyBOList = studyService.getStudies();
				map.addAttribute("actionPage", actionPage);
				map.addAttribute("userBO", userBO);
				map.addAttribute("roleBOList", roleBOList);
				map.addAttribute("studyBOList", studyBOList);
				map.addAttribute("studyBOs", studyBOs);
				mav = new ModelAndView("addOrEditUserPage",map);
			}
		}catch(Exception e){
			logger.error("UsersController - viewUserDetails() - ERROR",e);
		}
		logger.info("UsersController - viewUserDetails() - Ends");
		return mav;
	}
	
	@RequestMapping("/adminUsersEdit/addOrUpdateUserDetails.do")
	public ModelAndView addOrUpdateUserDetails(HttpServletRequest request,UserBO userBO, BindingResult result){
		logger.info("UsersController - addOrUpdateUserDetails() - Starts");
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		String msg = "";
		String permissions = "";
		int count = 1;
		try{
			HttpSession session = request.getSession();
			SessionObject userSession = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != userSession){
				String manageUsers = fdahpStudyDesignerUtil.isEmpty(request.getParameter("manageUsers")) == true ? "" : request.getParameter("manageUsers");
				String manageNotifications = fdahpStudyDesignerUtil.isEmpty(request.getParameter("manageNotifications")) == true ? "" : request.getParameter("manageNotifications");
				String manageStudies = fdahpStudyDesignerUtil.isEmpty(request.getParameter("manageStudies")) == true ? "" : request.getParameter("manageStudies");
				String addingNewStudy = fdahpStudyDesignerUtil.isEmpty(request.getParameter("addingNewStudy")) == true ? "" : request.getParameter("addingNewStudy");
				if(null == userBO.getUserId()){
					userBO.setCreatedBy(userSession.getUserId());
					userBO.setCreatedOn(userSession.getCreatedDate());
				}else{
					userBO.setModifiedBy(userSession.getUserId());
					userBO.setModifiedOn(userSession.getCreatedDate());
				}
				if(!"".equals(manageUsers)){
					if("0".equals(manageUsers)){
						permissions += count > 1 ?(",'ROLE_MANAGE_USERS_VIEW'"):"'ROLE_MANAGE_USERS_VIEW'";
						count++;
					}else if("1".equals(manageUsers)){
						permissions += count > 1 ?(",'ROLE_MANAGE_USERS_VIEW'"):"'ROLE_MANAGE_USERS_VIEW'";
						count++;
						permissions += count > 1 ?(",'ROLE_MANAGE_USERS_EDIT'"):"'ROLE_MANAGE_USERS_EDIT'";
					}
				}
				if(!"".equals(manageNotifications)){
					if("0".equals(manageNotifications)){
						permissions += count > 1 ?(",'ROLE_MANAGE_APP_WIDE_NOTIFICATION_VIEW'"):"'ROLE_MANAGE_APP_WIDE_NOTIFICATION_VIEW'";
						count++;
					}else if("1".equals(manageNotifications)){
						permissions += count > 1 ?(",'ROLE_MANAGE_APP_WIDE_NOTIFICATION_VIEW'"):"'ROLE_MANAGE_APP_WIDE_NOTIFICATION_VIEW'";
						count++;
						permissions += count > 1 ?(",'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT'"):"'ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT'";
					}
				}
				if(!"".equals(manageStudies)){
					if("1".equals(manageStudies)){
						permissions += count > 1 ?(",'ROLE_MANAGE_STUDIES'"):"'ROLE_MANAGE_STUDIES'";
						count++;
						if(!"".equals(addingNewStudy) && "1".equals(addingNewStudy)){
								permissions += count > 1 ?(",'ROLE_CREATE_MANAGE_STUDIES'"):"'ROLE_CREATE_MANAGE_STUDIES'";
						}
					}
				}
				msg = usersService.addOrUpdateUserDetails(userBO,permissions);
			}
		}catch(Exception e){
			logger.error("UsersController - addOrUpdateUserDetails() - ERROR",e);
		}
		logger.info("UsersController - addOrUpdateUserDetails() - Ends");
		return mav;
	}
}
