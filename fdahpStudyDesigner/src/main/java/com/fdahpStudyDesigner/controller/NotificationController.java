package com.fdahpStudyDesigner.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpStudyDesigner.bo.NotificationBO;
import com.fdahpStudyDesigner.service.NotificationService;
import com.fdahpStudyDesigner.service.NotificationServiceImpl;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Controller
public class NotificationController {
	
private static Logger logger = Logger.getLogger(NotificationController.class);
	
	@Autowired
	private NotificationService notificationService;

	@SuppressWarnings("unchecked")	
	HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
	
	@RequestMapping("/adminNotificationView/viewNotificationList.do")
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
				/*if(null != request.getSession().getAttribute("sucMsg")){
					sucMsg = (String) request.getSession().getAttribute("sucMsg");
					map.addAttribute("sucMsg", sucMsg);
					request.getSession().removeAttribute("sucMsg");
				}
				if(null != request.getSession().getAttribute("errMsg")){
					errMsg = (String) request.getSession().getAttribute("errMsg");
					map.addAttribute("errMsg", errMsg);
					request.getSession().removeAttribute("errMsg");
				}*/
				notificationList = notificationService.getNotificationList("");
				map.addAttribute("notificationList", notificationList);
				mav = new ModelAndView("notificationListPage", map);
			}
		}catch(Exception e){
			logger.error("NotificationController - viewNotificationList() - ERROR ", e);
		}
		logger.info("NotificationController - viewNotificationList() - ends");
		return mav;
	}
	
	@RequestMapping("/adminNotificationView/getNotification.do")
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
				String notificationText = fdahpStudyDesignerUtil.isEmpty(request.getParameter("notificationText")) == true?"":request.getParameter("notificationText");
				String chkRefreshflag = fdahpStudyDesignerUtil.isEmpty(request.getParameter("chkRefreshflag")) == true?"":request.getParameter("chkRefreshflag");
				if(!"".equals(chkRefreshflag)){
					if(!"".equals(notificationId)){
						notificationBO = notificationService.getNotification(Integer.parseInt(notificationId));
						/*map.addAttribute("notificationBO", notificationBO);*/
					}else if(!"".equals(notificationText) && "".equals(notificationId)){
						notificationBO = new NotificationBO();
						notificationBO.setNotificationText(notificationText);
					}
					map.addAttribute("notificationBO", notificationBO);
					mav = new ModelAndView("createOrUpdateNotification",map);
				}
				else {
					mav = new ModelAndView("redirect:viewNotificationList.do");
				}
			}
		}catch(Exception e){
			logger.error("NotificationController - getNotification - ERROR", e);

		}
		logger.info("NotificationController - getNotification - Ends");
		return mav;
	}
	
	@RequestMapping("/adminNotificationEdit/saveOrUpdateNotification.do")
	public ModelAndView saveOrUpdateNotification(HttpServletRequest request, NotificationBO notificationBO){
		logger.info("NotificationController - saveOrUpdateNotification - Starts");
		ModelAndView mav = new ModelAndView();
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
				message = notificationService.saveOrUpdateNotification(notificationBO);
			}
		}catch(Exception e){
			logger.error("NotificationController - saveOrUpdateNotification - ERROR", e);

		}
		logger.info("NotificationController - saveOrUpdateNotification - Ends");
		return mav;
	}
	
	@RequestMapping("/adminNotificationEdit/deleteNotification.do")
	public void deleteNotification(HttpServletRequest request, HttpServletResponse response, String notificationIdForDelete, String scheduledDate, String scheduledTime) throws IOException{
		logger.info("NotificationController - deleteNotification - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		Boolean dateTimeStatus = false;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != notificationIdForDelete){
				String scheduledDateAndTime = scheduledDate+" "+scheduledTime;
				dateTimeStatus = fdahpStudyDesignerUtil.compareDateWithCurrentDateTime(scheduledDateAndTime, "MM-dd-yyyy HH:mm");
				if(dateTimeStatus){
					message = notificationService.deleteNotification(Integer.parseInt(notificationIdForDelete));
				}
				else {
					message = fdahpStudyDesignerConstants.SELECTEDNOTIFICATIONPAST;
				}
			}
		}catch(Exception e){
			logger.error("NotificationController - deleteNotification - ERROR", e);

		}
		logger.info("NotificationController - deleteNotification - Ends");
		jsonobject.put("message", message);
		response.setContentType("application/json");
		out = response.getWriter();
		out.print(jsonobject);
	}
	
	@RequestMapping("/adminNotificationEdit/resendNotification.do")
	public void resendNotification(HttpServletRequest request, HttpServletResponse response, String notificationIdToResend) throws IOException{
		logger.info("NotificationController - resendNotification - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != notificationIdToResend){
					message = notificationService.resendNotification(Integer.parseInt(notificationIdToResend));
			}
		}catch(Exception e){
			logger.error("NotificationController - resendNotification - ERROR", e);

		}
		logger.info("NotificationController - resendNotification - Ends");
		jsonobject.put("message", message);
		response.setContentType("application/json");
		out = response.getWriter();
		out.print(jsonobject);
	}
	
	/*@RequestMapping("/adminNotificationView/reloadNotificationList.do")
	public void reloadNotificationList(HttpServletRequest request, HttpServletResponse response) throws Exception{
		logger.info("NotificationController - reloadNotificationList() - Starts");
		String sucMsg = "";
		String errMsg = "";
		List<NotificationBO> notificationList = null;
		JSONObject jsonobject = new JSONObject();
		JSONObject jsonString = new JSONObject();
		JSONArray jsonArr = new JSONArray();
		PrintWriter out = null;
		OutputStream  listJson = new ByteArrayOutputStream();
		String message = fdahpStudyDesignerConstants.FAILURE;
		final ObjectMapper mapper = new ObjectMapper();
		String dataJsone = "";
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
				notificationList = notificationService.getNotificationList();
				mapper.writeValue(listJson, notificationList);
				dataJsone = ((ByteArrayOutputStream) listJson).toByteArray();
				//List<List<String>> data1 = new ArrayList<List<String>>();
				if(notificationList != null && !notificationList.isEmpty()){
					for (NotificationBO notificationBO : notificationList) {
						List<String> arr = new ArrayList<String>();
						JSONArray jsonArr1 = new JSONArray();
						jsonArr1.put(notificationBO.getNotificationText());
						String actions = "";
						if(!notificationBO.isNotificationSent()){
							actions = actions  + "<a href='javascript:void(0)' class='sprites_icon edit-g notificationDetails' notificationid='"+notificationBO.getNotificationId()+"'></a>";
							actions = actions+"<a href='javascript:void(0)' class='notificationDetails' notificationtext='"+notificationBO.getNotificationText()+"'>Copy</a>";
							actions = actions+"<button class=''>Resend</button>";
						} else {
							actions = actions  + "<a href='javascript:void(0)' class='sprites_icon edit-g'></a>";
							actions = actions+"<a href='javascript:void(0)' class='notificationDetails' notificationtext='"+notificationBO.getNotificationText()+"'>Copy</a>";
							actions = actions+"<button class='resendNotification' notificationidtoresend='"+notificationBO.getNotificationId()+"'>Resend</button>";
						}
						//arr.add(actions+"\"");
						jsonArr1.put(actions);
						//data1.add(arr);
						jsonArr.put(jsonArr1);
					}
				}
				jsonString.put("data",jsonArr);
			}
			message = fdahpStudyDesignerConstants.SUCCESS;
		}catch(Exception e){
			logger.error("NotificationController - reloadNotificationList() - ERROR ", e);
		}
		logger.info("NotificationController - reloadNotificationList() - ends");
		jsonobject.put("jsonList", jsonString);
		jsonobject.put("message", message);
		response.setContentType("application/json");
		out = response.getWriter();
		out.print(jsonString);
	}*/
}
