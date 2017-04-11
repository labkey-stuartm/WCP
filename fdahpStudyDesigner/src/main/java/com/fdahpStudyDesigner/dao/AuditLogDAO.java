package com.fdahpStudyDesigner.dao;


public interface AuditLogDAO {

	public String saveToAuditLog(Integer userId, String activity, String activityDetails, String classMethodName,String createdDateTime);
	
}
