/**
 * 
 */
package com.fdahpstudydesigner.scheduler;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.fdahpstudydesigner.bean.PushNotificationBean;
import com.fdahpstudydesigner.bo.AuditLogBO;
import com.fdahpstudydesigner.dao.AuditLogDAO;
import com.fdahpstudydesigner.dao.NotificationDAO;
import com.fdahpstudydesigner.util.FdahpStudyDesignerConstants;
import com.fdahpstudydesigner.util.FdahpStudyDesignerUtil;

/**
 * @author Vivek
 *
 */
public class FDASchedulerService {
	private static Logger logger = Logger.getLogger(FDASchedulerService.class
			.getName());
	
	private static final Map<?,?> configMap = FdahpStudyDesignerUtil.getAppProperties();
	
	@Autowired
	AuditLogDAO auditLogDAO; 
	
	@Autowired
	private NotificationDAO notificationDAO;
	
	@Scheduled(cron = "0 0 0 * * ?")
	public void createAuditLogs() {
		logger.info("FDASchedulerService - createAuditLogs - Starts");
		List<AuditLogBO> auditLogs = null;
		StringBuilder logString = null;
		try {
			auditLogs = auditLogDAO.getTodaysAuditLogs();
			if(auditLogs != null && !auditLogs.isEmpty()) {
				logString = new StringBuilder();
				for (AuditLogBO auditLogBO : auditLogs) {
					logString.append(auditLogBO.getAuditLogId()).append("\t");
					logString.append(auditLogBO.getCreatedDateTime()).append("\t");
					logString.append(auditLogBO.getUserBO().getFirstName()).append(" ").append(auditLogBO.getUserBO().getLastName()).append("\t");
					logString.append(auditLogBO.getClassMethodName()).append("\t");
					logString.append(auditLogBO.getActivity()).append("\t");
					logString.append(auditLogBO.getActivityDetails()).append("\n");
				}
			}
			if(logString != null && StringUtils.isNotBlank(logString.toString())) {
				File file = new File((String) configMap.get("fda.logFilePath")+configMap.get("fda.logFileIntials")+" "+FdahpStudyDesignerUtil.getCurrentDate()+".log");
				FileUtils.writeStringToFile(file, logString.toString());
			}
		} catch (Exception e) {
			logger.error("FDASchedulerService - createAuditLogs - ERROR", e);
		}
		logger.info("FDASchedulerService - createAuditLogs - Ends");
	}
	@Scheduled(cron = "0 0/1 * * * ?")
	public void sendPushNotification() {
		logger.info("FDASchedulerService - sendPushNotification - Starts");
		List<PushNotificationBean> pushNotificationBeans;
		String date;
		String time;
		ObjectMapper objectMapper = new ObjectMapper();
		String responseString = "";
		try {
			date = FdahpStudyDesignerUtil.privMinDateTime(new SimpleDateFormat(FdahpStudyDesignerConstants.DB_SDF_DATE).format(new Date()), FdahpStudyDesignerConstants.DB_SDF_DATE, 1);
			time = FdahpStudyDesignerUtil.privMinDateTime(new SimpleDateFormat(FdahpStudyDesignerConstants.UI_SDF_TIME).format(new Date()), FdahpStudyDesignerConstants.UI_SDF_TIME,1);
			pushNotificationBeans = notificationDAO.getPushNotificationList(date, time);
//			if(pushNotificationBeans != null && !pushNotificationBeans.isEmpty()) {
//				JSONArray arrayToJson = new JSONArray(objectMapper.writeValueAsString(pushNotificationBeans));
//				logger.warn("FDASchedulerService - sendPushNotification - LAPKEY DATA " + arrayToJson);
//				JSONObject json = new JSONObject();
//				json.put("notifications",arrayToJson);
//				
//				HttpClient client =  new DefaultHttpClient();
//				HttpResponse response;
//				HttpPost post = new HttpPost(FdahpStudyDesignerUtil.getAppProperties().get("fda.registration.root.url") + FdahpStudyDesignerUtil.getAppProperties().get("push.notification.uri"));
//				post.setHeader("Content-type", "application/json");
//				
//				StringEntity requestEntity = new StringEntity(
//					    json.toString(),ContentType.APPLICATION_JSON );
//				post.setEntity(requestEntity);
//				
//				response = client.execute(post);
//				responseString  = EntityUtils.toString(response.getEntity());
//				JSONObject res = new JSONObject(responseString);
//				String result = (String) res.get("message");
//				if(result == null ||  !result.equalsIgnoreCase(FdahpStudyDesignerConstants.SUCCESS)) {
//					logger.error("FDASchedulerService - sendPushNotification - LAPKEY DATA SEND ERROR: "+ responseString);
//				} else {
//					logger.warn("FDASchedulerService - sendPushNotification - LAPKEY DATA SEND SUCCESS");
//				}
//			}
		} catch (Exception e) {
			logger.error("FDASchedulerService - sendPushNotification - ERROR", e);
		}
		logger.info("FDASchedulerService - sendPushNotification - Ends");
	}

}
