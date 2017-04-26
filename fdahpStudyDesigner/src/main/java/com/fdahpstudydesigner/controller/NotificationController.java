package com.fdahpstudydesigner.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fdahpstudydesigner.bo.NotificationBO;
import com.fdahpstudydesigner.bo.NotificationHistoryBO;
import com.fdahpstudydesigner.service.NotificationService;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;
import com.fdahpstudydesigner.util.SessionObject;

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
				if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.SUC_MSG)){
					sucMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.SUC_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.SUC_MSG, sucMsg);
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.SUC_MSG);
				}
				if(null != request.getSession().getAttribute(FdahpStudyDesignerConstants.ERR_MSG)){
					errMsg = (String) request.getSession().getAttribute(FdahpStudyDesignerConstants.ERR_MSG);
					map.addAttribute(FdahpStudyDesignerConstants.ERR_MSG, errMsg);
					request.getSession().removeAttribute(FdahpStudyDesignerConstants.ERR_MSG);
				}
				notificationList = notificationService.getNotificationList(0,"");
				map.addAttribute("notificationList", notificationList);
				mav = new ModelAndView("notificationListPage", map);
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
		List<NotificationHistoryBO> notificationHistoryNoDateTime = null;
		try{
				String notificationId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID))?"":request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID);
				String chkRefreshflag = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.CHKREFRESHFLAG))?"":request.getParameter(FdahpStudyDesignerConstants.CHKREFRESHFLAG);
				String ACTION_TYPE = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.ACTION_TYPE))?"":request.getParameter(FdahpStudyDesignerConstants.ACTION_TYPE);
				if(!"".equals(chkRefreshflag)){
					if(!"".equals(notificationId)){
						notificationBO = notificationService.getNotification(Integer.parseInt(notificationId));
						notificationHistoryNoDateTime = notificationService.getNotificationHistoryListNoDateTime(Integer.parseInt(notificationId));
						if("view".equals(ACTION_TYPE)){
							notificationBO.setActionPage("view");
						}
					}
					map.addAttribute("notificationBO", notificationBO);
					map.addAttribute("notificationHistoryNoDateTime", notificationHistoryNoDateTime);
					mav = new ModelAndView("createOrUpdateNotification",map);
				}
				else {
					mav = new ModelAndView("redirect:viewNotificationList.do");
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
				String notificationId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID))?"":request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID);
				String notificationText = FdahpStudyDesignerUtil.isEmpty(request.getParameter("notificationText"))?"":request.getParameter("notificationText");
				String chkRefreshflag = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.CHKREFRESHFLAG))?"":request.getParameter(FdahpStudyDesignerConstants.CHKREFRESHFLAG);
				String ACTION_TYPE = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.ACTION_TYPE))?"":request.getParameter(FdahpStudyDesignerConstants.ACTION_TYPE);
				if(!"".equals(chkRefreshflag)){
					if(!"".equals(notificationId)){
						notificationBO = notificationService.getNotification(Integer.parseInt(notificationId));
						notificationHistoryNoDateTime = notificationService.getNotificationHistoryListNoDateTime(Integer.parseInt(notificationId));
						if("edit".equals(ACTION_TYPE)){
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
					mav = new ModelAndView(FdahpStudyDesignerConstants.REDIRECTTONOTIFICATIONLIST);
				}
		}catch(Exception e){
			logger.error("NotificationController - getNotificationToEdit - ERROR", e);

		}
		logger.info("NotificationController - getNotificationToEdit - Ends");
		return mav;
	}
	
	@RequestMapping("/adminNotificationEdit/saveOrUpdateNotification.do")
	public ModelAndView saveOrUpdateOrResendNotification(HttpServletRequest request, NotificationBO notificationBO){
		logger.info("NotificationController - saveOrUpdateOrResendNotification - Starts");
		ModelAndView mav = new ModelAndView();
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		Integer notificationId = 0;
		try{
				HttpSession session = request.getSession();
				SessionObject sessionObject = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
				String notificationType = FdahpStudyDesignerConstants.GATEWAYLEVEL;
				String currentDateTime = FdahpStudyDesignerUtil.isEmpty(request.getParameter("currentDateTime"))?"":request.getParameter("currentDateTime");
				String buttonType = FdahpStudyDesignerUtil.isEmpty(request.getParameter("buttonType"))?"":request.getParameter("buttonType");
				if("notImmediate".equals(currentDateTime)){
					notificationBO.setScheduleDate(FdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleDate())?String.valueOf(FdahpStudyDesignerUtil.getFormattedDate(notificationBO.getScheduleDate(), FdahpStudyDesignerConstants.UI_SDF_DATE, FdahpStudyDesignerConstants.DB_SDF_DATE)):"");
					notificationBO.setScheduleTime(FdahpStudyDesignerUtil.isNotEmpty(notificationBO.getScheduleTime())?String.valueOf(FdahpStudyDesignerUtil.getFormattedDate(notificationBO.getScheduleTime(), FdahpStudyDesignerConstants.SDF_TIME, FdahpStudyDesignerConstants.DB_SDF_TIME)):"");
					notificationBO.setNotificationScheduleType("notImmediate");
				} else if("immediate".equals(currentDateTime)){
					notificationBO.setScheduleDate(FdahpStudyDesignerUtil.getCurrentDate());
					notificationBO.setScheduleTime(FdahpStudyDesignerUtil.getCurrentTime());
					notificationBO.setNotificationScheduleType("immediate");
				} else{
					notificationBO.setScheduleDate("");
					notificationBO.setScheduleTime("");
					notificationBO.setNotificationScheduleType("0");
				}
				if(notificationBO.getNotificationId() == null){
					notificationBO.setCreatedBy(sessionObject.getUserId());
					notificationBO.setCreatedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
				}else{
					notificationBO.setModifiedBy(sessionObject.getUserId());
					notificationBO.setModifiedOn(FdahpStudyDesignerUtil.getCurrentDateTime());
				}
				notificationId = notificationService.saveOrUpdateOrResendNotification(notificationBO, notificationType, buttonType, sessionObject);
				if(!notificationId.equals(0)){
					if(notificationBO.getNotificationId() == null && "add".equalsIgnoreCase(buttonType)){
							request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, propMap.get("save.notification.success.message"));
					}else if(notificationBO.getNotificationId() != null && "update".equalsIgnoreCase(buttonType)){
							request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, propMap.get("update.notification.success.message"));
					}else {
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, propMap.get("resend.notification.success.message"));
					}
				}else{
					if(notificationBO.getNotificationId() == null && "add".equalsIgnoreCase(buttonType)){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, propMap.get("save.notification.error.message"));
					}else if(notificationBO.getNotificationId() != null && "update".equalsIgnoreCase(buttonType)){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, propMap.get("resend.notification.error.message"));
					}else {
						request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, propMap.get("update.notification.error.message"));
					}
				}
				mav = new ModelAndView(FdahpStudyDesignerConstants.REDIRECTTONOTIFICATIONLIST);
		}catch(Exception e){
			logger.error("NotificationController - saveOrUpdateOrResendNotification - ERROR", e);

		}
		logger.info("NotificationController - saveOrUpdateOrResendNotification - Ends");
		return mav;
	}
	
	@RequestMapping("/adminNotificationEdit/deleteNotification.do")
	public ModelAndView deleteNotification(HttpServletRequest request){
		logger.info("NotificationController - deleteNotification - Starts");
		String message = FdahpStudyDesignerConstants.FAILURE;
		ModelAndView mav = new ModelAndView();
		Map<String, String> propMap = FdahpStudyDesignerUtil.getAppProperties();
		try{
			HttpSession session = request.getSession();
			SessionObject sessionObject = (SessionObject) session.getAttribute(FdahpStudyDesignerConstants.SESSION_OBJECT);
			String notificationId = FdahpStudyDesignerUtil.isEmpty(request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID))?"":request.getParameter(FdahpStudyDesignerConstants.NOTIFICATIONID);
			if(null != notificationId){
					String notificationType = FdahpStudyDesignerConstants.GATEWAYLEVEL;
					message = notificationService.deleteNotification(Integer.parseInt(notificationId), sessionObject, notificationType);
					if(message.equals(FdahpStudyDesignerConstants.SUCCESS)){
						request.getSession().setAttribute(FdahpStudyDesignerConstants.SUC_MSG, propMap.get("delete.notification.success.message"));
					}else{
						request.getSession().setAttribute(FdahpStudyDesignerConstants.ERR_MSG, propMap.get("delete.notification.error.message"));
					}
					mav = new ModelAndView(FdahpStudyDesignerConstants.REDIRECTTONOTIFICATIONLIST);
			}
		}catch(Exception e){
			logger.error("NotificationController - deleteNotification - ERROR", e);

		}
		return mav;
	}
	
}
