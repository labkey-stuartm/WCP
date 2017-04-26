package com.fdahpstudydesigner.dao;

import java.util.List;

import org.hibernate.Session;

import com.fdahpstudydesigner.bo.AuditLogBO;
import com.fdahpstudydesigner.util.SessionObject;


public interface AuditLogDAO {

	public String saveToAuditLog(Session session, SessionObject sessionObject, String activity, String activityDetails, String classsMethodName);
	public List<AuditLogBO> getTodaysAuditLogs();
	public String updateDraftToEditedStatus(Session session, SessionObject sessionObject, String actionType, Integer studyId);
}
