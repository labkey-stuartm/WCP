package com.fdahpStudyDesigner.dao;

import org.hibernate.Session;

import com.fdahpStudyDesigner.util.SessionObject;


public interface AuditLogDAO {

	public String saveToAuditLog(Session session, SessionObject sessionObject, String activity, String activityDetails);
	
}
