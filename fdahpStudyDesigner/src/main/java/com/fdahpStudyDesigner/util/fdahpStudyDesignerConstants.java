package com.fdahpStudyDesigner.util;

import java.text.SimpleDateFormat;
/**
 * @author 
 *
 */
public class fdahpStudyDesignerConstants {

	public static final String SESSION_OBJECT = "sessionObject";
	public static final SimpleDateFormat UI_SDF_DATE = new SimpleDateFormat("MM-dd-yyyy");
	public static final SimpleDateFormat UI_SDF_DATE_TIME = new SimpleDateFormat("MM-dd-yyyy HH:mm");
	public static final SimpleDateFormat DB_SDF_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat DB_SDF_DATE = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat UI_DISPLAY_DATE = new SimpleDateFormat("EEE, MMM dd, yyyy"); 
	public static final SimpleDateFormat UI_SDF_TIME = new SimpleDateFormat("HH:mm");
	public static final SimpleDateFormat DB_SDF_TIME = new SimpleDateFormat("HH:mm:ss");
	public static final SimpleDateFormat HR_SDF_TIME = new SimpleDateFormat("HH");
	public static final SimpleDateFormat SDF_FILE_NAME_TIMESTAMP = new SimpleDateFormat("MMddyyyyHHmmss");

	public static final boolean STATUS_ACTIVE = true;
	public static final boolean STATUS_EXPIRED = false;
	
	public static final String SUCCESS = "SUCCESS";
	public static final String MAILFAILURE = "MAILFAILURE";
	public static final String FAILURE = "FAILURE";
	public static final String PASSWORD_SALT = "BTCSoft";
	public static final String WARNING = "WARNING";
	public static final String LOGOUPLOAD = "LOGOUPLOAD";
	
	public static final String NOTIFICATION_VIEWED = "Y";
	public static final String NOTIFICATION_NOT_VIEWED = "N";
	
	
	public static final String ACTIVE = "active";
	public static final String DEACTIVE = "deactive";
	public static final String PENDING_TO_DEACTIVATE = "pending";
	public static final int ACTIVE_STATUS = 1;
	public static final int DEACTIVE_STATUS = 0;
	public static final String ENCRYPT_SALT = "StudieGatewayApp" ;
	public static final String GET_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String GET_DATE = "yyyy-MM-dd";
	
	public static final String ACTUAL_DATE = "yyyy-MM-dd";
	public static final String REQUIRED_DATE = "MM/dd/yyyy";
	public static final String REQUIRED_DATE_TIME = "MM/dd/yyyy HH:mm";
	public static final String REQUIRED_DATE_TIME_FOR_DATE_DIFF = "MM/dd/yyyy HH:mm:ss";
	public static final String INPUT_TIME = "HH:mm:ss";
	public static final String REQUIRED_TIME = "HH:mm";
	
	
	public static final String SERVER_TIME_ZONE="America/New_York";
	
	public static final Integer ROLE_SUPERADMIN = 1;
	public static final Integer ROLE_MANAGE_STUDIES = 2;
	public static final Integer ROLE_MANAGE_REPO = 3;
	public static final Integer ROLE_MANAGE_APP_WIDE_NOTIFICATION = 4;
	public static final Integer ROLE_MANAGE_USERS = 5;

	
	public static final String REDIRECT_SESSION_PARAM_NAME ="sessionUserId=";
    public static final SimpleDateFormat PW_DATE_FORMAT = new SimpleDateFormat("MMddyy");
    public static final String DEFAULT = "default";
    
    public static final String REFERENCE_TYPE_CATEGORIES = "categories";
    public static final String REFERENCE_TYPE_RESEARCH_SPONSORS = "Research Sponsors";
    public static final String REFERENCE_TYPE_DATA_PARTNER = "Data Partner";
    
    //Folder Name
    public static final String STUDTYLOGO="studylogo";
    
    //Button Name
    public static final String SAVE_BUTTON = "save";
    public static final String COMPLETED_BUTTON = "completed";
    
    
}
