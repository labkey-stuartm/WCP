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
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.service.NotificationServiceImpl;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Controller
public class NotificationController {
	
private static Logger logger = Logger.getLogger(NotificationController.class);
	
	private NotificationServiceImpl notificationServiceImpl;

	@Autowired
	public void setNotificationServiceImpl(NotificationServiceImpl notificationServiceImpl) {
		this.notificationServiceImpl = notificationServiceImpl;
	}
	
	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	/**
	 * Displaying list of HI's based on CreatedDate
	 * @param request : HttpServletRequest
	 * @return mav
	 */
	@RequestMapping(value ="/viewNotificationList.do")
	public ModelAndView viewNotificationList(HttpServletRequest request){
		logger.info("NotificationController - viewNotificationList() - Starts");
		ModelMap map = new ModelMap();
		ModelAndView mav = new ModelAndView("login", map);
		String sucMsg = "";
		String errMsg = "";
		List<NotificationBO> notificationList = null;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
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
				notificationList = notificationServiceImpl.getNotificationList();
				map.addAttribute("notificationList", notificationList);
				mav = new ModelAndView("acuityHiList", map);
			}
		}catch(Exception e){
			logger.error("NotificationController - viewNotificationList() - ERROR ", e);
		}
		logger.info("NotificationController - viewNotificationList() - ends");
		return mav;
	}
	
	@RequestMapping("/getNotification.do")
	public ModelAndView getNotification(HttpServletRequest request){
		logger.info("NotificationController - getNotification - Starts");
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		NotificationBO notificationBO = null;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
				String notificationId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("notificationId")) == true?"":request.getParameter("notificationId");
				String chkRefreshflag = fdahpStudyDesignerUtil.isEmpty(request.getParameter("chkRefreshflag")) == true?"":request.getParameter("chkRefreshflag");
				if(!"".equals(chkRefreshflag)){
					if(!"".equals(notificationId)){
						notificationBO = notificationServiceImpl.getNotification(Integer.parseInt(notificationId));
						map.addAttribute("notificationBO", notificationBO);
					}
					mav = new ModelAndView("createOrUpdateNotification",map);
				}
				else {
					mav = new ModelAndView("redirect:viewNotificationList.do");
				}
			}
		}catch(Exception e){
			logger.error("NotificationController - getNotification - ERROR",e);

		}
		logger.info("NotificationController - getNotification - Ends");
		return mav;
	}
	
	@RequestMapping("/saveOrUpdateNotification.do")
	public ModelAndView saveOrUpdateNotification(HttpServletRequest request, NotificationBO notificationBO){
		logger.info("NotificationController - saveOrUpdateNotification - Starts");
		ModelAndView mav = new ModelAndView();
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
				message = notificationServiceImpl.saveOrUpdateNotification(notificationBO);
			}
		}catch(Exception e){
			logger.error("NotificationController - saveOrUpdateNotification - ERROR", e);

		}
		logger.info("NotificationController - saveOrUpdateNotification - Ends");
		return mav;
	}
}
