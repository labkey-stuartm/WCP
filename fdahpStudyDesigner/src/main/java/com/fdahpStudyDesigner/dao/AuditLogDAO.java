package com.fdahpStudyDesigner.dao;

import java.util.List;

import org.hibernate.Session;

import com.fdahpStudyDesigner.bo.AuditLogBO;
import com.fdahpStudyDesigner.util.SessionObject;


public interface AuditLogDAO {

	public String saveToAuditLog(Session session, SessionObject sessionObject, String activity, String activityDetails, String classsMethodName);
	public List<AuditLogBO> getTodaysAuditLogs();
}
