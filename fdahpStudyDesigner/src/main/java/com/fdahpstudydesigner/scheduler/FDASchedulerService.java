/**
 * 
 */
package com.fdahpstudydesigner.scheduler;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.fdahpstudydesigner.bo.AuditLogBO;
import com.fdahpstudydesigner.dao.AuditLogDAO;
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
					logString.append(auditLogBO.getAuditLogId()+"\t");
					logString.append(auditLogBO.getCreatedDateTime()+"\t");
					logString.append(auditLogBO.getUserBO().getFirstName()+" "+auditLogBO.getUserBO().getLastName()+"\t");
					logString.append(auditLogBO.getClassMethodName()+"\t");
					logString.append(auditLogBO.getActivity()+"\t");
					logString.append(auditLogBO.getActivityDetails()+"\n");
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

}
