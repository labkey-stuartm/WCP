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
import com.fdahpStudyDesigner.bo.NotificationHistoryBO;
import com.fdahpStudyDesigner.service.NotificationService;
import com.fdahpStudyDesigner.util.SessionObject;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerConstants;
import com.fdahpStudyDesigner.util.fdahpStudyDesignerUtil;

@Controller
public class NotificationController {
	
private static Logger logger = Logger.getLogger(NotificationController.class);
	
	@Autowired
	private NotificationService notificationService;

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
				notificationList = notificationService.getNotificationList(0,"");
				map.addAttribute("notificationList", notificationList);
				mav = new ModelAndView("notificationListPage", map);
			}
		}catch(Exception e){
			logger.error("NotificationController - viewNotificationList() - ERROR ", e);
		}
		logger.info("NotificationController - viewNotificationList() - ends");
		return mav;
	}
	
	@RequestMapping("/adminNotificationView/getNotificationToView.do")
	public ModelAndView getNotificationToView(HttpServletRequest request){
		logger.info("NotificationController - getNotification - Starts");
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		NotificationBO notificationBO = null;
		List<NotificationHistoryBO> notificationHistoryList = null;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
				String notificationId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("notificationId"))?"":request.getParameter("notificationId");
				String chkRefreshflag = fdahpStudyDesignerUtil.isEmpty(request.getParameter("chkRefreshflag"))?"":request.getParameter("chkRefreshflag");
				String actionType = fdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType"))?"":request.getParameter("actionType");
				if(!"".equals(chkRefreshflag)){
					if(!"".equals(notificationId)){
						notificationBO = notificationService.getNotification(Integer.parseInt(notificationId));
						notificationHistoryList = notificationService.getNotificationHistoryList(Integer.parseInt(notificationId));
						/*if(notificationBO !=null && fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getNotificationSentDateTime())){
							String[] dateTime =null;
							notificationBO.setNotificationSentDateTime(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getNotificationSentDateTime())?String.valueOf(fdahpStudyDesignerConstants.UI_SDF_DATE_TIME_AMPM.format(fdahpStudyDesignerConstants.DB_SDF_DATE_TIME_AMPM.parse(notificationBO.getNotificationSentDateTime()))):"");
							String dateAndTime = notificationBO.getNotificationSentDateTime();
							dateTime = dateAndTime.split(" ");
							String date = dateTime[0].toString(); // 8/29/2011
							String time = dateTime[1].toString() + " " + dateTime[2].toString(); // 11:16:12 AM
							notificationBO.setNotificationSentDate(date);
							notificationBO.setNotificationSentTime(time);
							
						}*/
						if("view".equals(actionType)){
							notificationBO.setActionPage("view");
						}
						/*map.addAttribute("notificationBO", notificationBO);*/
					}
					map.addAttribute("notificationBO", notificationBO);
					map.addAttribute("notificationHistoryList", notificationHistoryList);
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
	
	@RequestMapping("/adminNotificationEdit/getNotificationToEdit.do")
	public ModelAndView getNotificationToEdit(HttpServletRequest request){
		logger.info("NotificationController - getNotificationToEdit - Starts");
		ModelAndView mav = new ModelAndView();
		ModelMap map = new ModelMap();
		NotificationBO notificationBO = null;
		List<NotificationHistoryBO> notificationHistoryNoDateTime = null;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
				String notificationId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("notificationId"))?"":request.getParameter("notificationId");
				String notificationText = fdahpStudyDesignerUtil.isEmpty(request.getParameter("notificationText"))?"":request.getParameter("notificationText");
				String chkRefreshflag = fdahpStudyDesignerUtil.isEmpty(request.getParameter("chkRefreshflag"))?"":request.getParameter("chkRefreshflag");
				String actionType = fdahpStudyDesignerUtil.isEmpty(request.getParameter("actionType"))?"":request.getParameter("actionType");
				if(!"".equals(chkRefreshflag)){
					if(!"".equals(notificationId)){
						notificationBO = notificationService.getNotification(Integer.parseInt(notificationId));
						//notificationHistoryList = notificationService.getNotificationHistoryList(Integer.parseInt(notificationId));
						notificationHistoryNoDateTime = notificationService.getNotificationHistoryListNoDateTime(Integer.parseInt(notificationId));
						if("edit".equals(actionType)){
							notificationBO.setActionPage("edit");
						}else{
							if(notificationBO.isNotificationSent()){
								notificationBO.setScheduleDate("");
								notificationBO.setScheduleTime("");
							}
							notificationBO.setActionPage("resend");
						}
					}else if(!"".equals(notificationText) && "".equals(notificationId)){
						notificationBO = new NotificationBO();
						notificationBO.setNotificationText(notificationText);
						notificationBO.setActionPage("addOrCopy");
					}else if("".equals(notificationText) && "".equals(notificationId)){
						notificationBO = new NotificationBO();
						notificationBO.setActionPage("addOrCopy");
					}
					map.addAttribute("notificationHistoryNoDateTime", notificationHistoryNoDateTime);
					map.addAttribute("notificationBO", notificationBO);
					mav = new ModelAndView("createOrUpdateNotification",map);
				}
				else {
					mav = new ModelAndView("redirect:/adminNotificationView/viewNotificationList.do");
				}
			}
		}catch(Exception e){
			logger.error("NotificationController - getNotificationToEdit - ERROR", e);

		}
		logger.info("NotificationController - getNotificationToEdit - Ends");
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/adminNotificationEdit/saveOrUpdateNotification.do")
	public ModelAndView saveOrUpdateNotification(HttpServletRequest request, NotificationBO notificationBO){
		logger.info("NotificationController - saveOrUpdateNotification - Starts");
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
		Integer notificationId = 0;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != sessionObject){
				String notificationType = fdahpStudyDesignerConstants.GATEWAYLEVEL;
				String currentDateTime = fdahpStudyDesignerUtil.isEmpty(request.getParameter("currentDateTime"))?"":request.getParameter("currentDateTime");
				String buttonType = fdahpStudyDesignerUtil.isEmpty(request.getParameter("buttonType"))?"":request.getParameter("buttonType");
				if("notImmediate".equals(currentDateTime)){
					notificationBO.setScheduleDate(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleDate())?String.valueOf(fdahpStudyDesignerConstants.DB_SDF_DATE.format(fdahpStudyDesignerConstants.UI_SDF_DATE.parse(notificationBO.getScheduleDate()))):"");
					notificationBO.setScheduleTime(fdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleTime())?String.valueOf(fdahpStudyDesignerConstants.DB_SDF_TIME.format(fdahpStudyDesignerConstants.SDF_TIME.parse(notificationBO.getScheduleTime()))):"");
					notificationBO.setNotificationScheduleType("notImmediate");
				} else if("immediate".equals(currentDateTime)){
					notificationBO.setScheduleDate(fdahpStudyDesignerUtil.getCurrentDate());
					notificationBO.setScheduleTime(fdahpStudyDesignerUtil.getCurrentTime());
					notificationBO.setNotificationScheduleType("immediate");
				} else{
					notificationBO.setScheduleDate("");
					notificationBO.setScheduleTime("");
					notificationBO.setNotificationScheduleType("0");
				}
				if(notificationBO.getNotificationId() == null){
					notificationBO.setCreatedBy(sessionObject.getUserId());
					notificationBO.setCreatedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
				}else{
					notificationBO.setModifiedBy(sessionObject.getUserId());
					notificationBO.setModifiedOn(fdahpStudyDesignerUtil.getCurrentDateTime());
				}
				notificationId = notificationService.saveOrUpdateOrResendNotification(notificationBO, notificationType, buttonType, sessionObject);
				if(!notificationId.equals(0)){
					if(notificationBO.getNotificationId() == null && "add".equalsIgnoreCase(buttonType)){
							request.getSession().setAttribute("sucMsg", propMap.get("save.notification.success.message"));
					}else if(notificationBO.getNotificationId() != null && "update".equalsIgnoreCase(buttonType)){
							request.getSession().setAttribute("sucMsg", propMap.get("update.notification.success.message"));
					}else {
						request.getSession().setAttribute("sucMsg", propMap.get("resend.notification.success.message"));
					}
				}else{
					if(notificationBO.getNotificationId() == null && "add".equalsIgnoreCase(buttonType)){
						request.getSession().setAttribute("errMsg", propMap.get("save.notification.error.message"));
					}else if(notificationBO.getNotificationId() != null && "update".equalsIgnoreCase(buttonType)){
						request.getSession().setAttribute("errMsg", propMap.get("resend.notification.error.message"));
					}else {
						request.getSession().setAttribute("errMsg", propMap.get("update.notification.error.message"));
					}
				}
				mav = new ModelAndView("redirect:/adminNotificationView/viewNotificationList.do");
			}
		}catch(Exception e){
			logger.error("NotificationController - saveOrUpdateNotification - ERROR", e);

		}
		logger.info("NotificationController - saveOrUpdateNotification - Ends");
		return mav;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping("/adminNotificationEdit/deleteNotification.do")
	public ModelAndView deleteNotification(HttpServletRequest request){
		logger.info("NotificationController - deleteNotification - Starts");
		String message = fdahpStudyDesignerConstants.FAILURE;
		ModelAndView mav = new ModelAndView();
		HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			String notificationId = fdahpStudyDesignerUtil.isEmpty(request.getParameter("notificationId"))?"":request.getParameter("notificationId");
			if(null != notificationId){
					String notificationType = fdahpStudyDesignerConstants.GATEWAYLEVEL;
					message = notificationService.deleteNotification(Integer.parseInt(notificationId), sessionObject, notificationType);
					if(message.equals(fdahpStudyDesignerConstants.SUCCESS)){
						request.getSession().setAttribute("sucMsg", propMap.get("delete.notification.success.message"));
					}else{
						request.getSession().setAttribute("errMsg", propMap.get("delete.notification.error.message"));
					}
					mav = new ModelAndView("redirect:/adminNotificationView/viewNotificationList.do");
			}
		}catch(Exception e){
			logger.error("NotificationController - deleteNotification - ERROR", e);

		}
		return mav;
	}
	
	/*@RequestMapping("/adminNotificationEdit/resendNotification.do")
	public void resendNotification(HttpServletRequest request, HttpServletResponse response, String notificationIdToResend) throws IOException{
		logger.info("NotificationController - resendNotification - Starts");
		JSONObject jsonobject = new JSONObject();
		PrintWriter out = null;
		String message = fdahpStudyDesignerConstants.FAILURE;
		Integer notificationResendId = 0;
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(null != notificationIdToResend){
				notificationResendId = notificationService.resendNotification(Integer.parseInt(notificationIdToResend));
			}
		}catch(Exception e){
			logger.error("NotificationController - resendNotification - ERROR", e);

		}
		logger.info("NotificationController - resendNotification - Ends");
		jsonobject.put("message", message);
		response.setContentType("application/json");
		out = response.getWriter();
		out.print(jsonobject);
	}*/
	
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
