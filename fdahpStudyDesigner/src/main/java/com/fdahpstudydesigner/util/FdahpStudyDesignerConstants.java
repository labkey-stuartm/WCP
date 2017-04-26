package com.fdahpstudydesigner.util;

/**
 * @author 
 *
 */
public class FdahpStudyDesignerConstants {

	public static final String SESSION_OBJECT = "sessionObject";
	
	public static final String UI_SDF_DATE = "MM/dd/yyyy";
	public static final String UI_SDF_DATE_FORMAT = "dd/MM/yyyy";

	public static final String UI_SDF_DATE_TIME = "MM-dd-yyyy HH:mm";
	public static final String DB_SDF_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DB_SDF_DATE = "yyyy-MM-dd";
	public static final String UI_SDF_DATE_TIME_AMPM = "MM-dd-yyyy h:mm a";
	public static final String UI_DISPLAY_DATE = "EEE, MMM dd, yyyy"; 
	public static final String UI_SDF_TIME = "HH:mm";
	public static final String SDF_TIME = "h:mm a";
	public static final String DB_SDF_TIME = "HH:mm:ss";
	public static final String HR_SDF_TIME = "HH";
	public static final String SDF_FILE_NAME_TIMESTAMP = "MMddyyyyHHmmss";

	public static final boolean STATUS_ACTIVE = true;
	public static final boolean STATUS_EXPIRED = false;
	
	public static final String SUCCESS = "SUCCESS";
	public static final String SELECTEDNOTIFICATIONPAST = "SELECTEDNOTIFICATIONPAST";
	public static final String MAILFAILURE = "MAILFAILURE";
	public static final String FAILURE = "FAILURE";
	public static final String FDA_SALT = "BTCSoft";
	public static final String WARNING = "WARNING";
	public static final String LOGOUPLOAD = "LOGOUPLOAD";
	
	public static final String NOTIFICATION_VIEWED = "Y";
	public static final String NOTIFICATION_NOT_VIEWED = "N";
	
	public static final String STUDYLEVEL = "Study level";
	public static final String GATEWAYLEVEL = "Gateway level";
	
	public static final String ACTIVE = "active";
	public static final String DEACTIVE = "deactive";
	public static final String PENDING_TO_DEACTIVATE = "pending";
	public static final int ACTIVE_STATUS = 1;
	public static final int DEACTIVE_STATUS = 0;
	public static final String ENCRYPT_SALT = "StudieGatewayApp" ;
	public static final String GET_DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	
	public static final String REQUIRED_DATE_TIME = "MM/dd/yyyy HH:mm";
	public static final String REQUIRED_DATE_TIME_FOR_DATE_DIFF = "MM/dd/yyyy HH:mm:ss";
	public static final String INPUT_TIME = "HH:mm:ss";
	public static final String REQUIRED_TIME = "HH:mm";
	
	
	public static final String SERVER_TIME_ZONE="America/New_York";
	
	public static final Integer ROLE_SUPERADMIN = 1;
	public static final Integer ROLE_MANAGE_STUDIES = 2;
	public static final Integer ROLE_MANAGE_REPO = 3;
	public static final Integer ROLE_MANAGE_APP_WIDE_NOTIFICATION_VIEW = 4;
	public static final Integer ROLE_MANAGE_USERS_EDIT = 5;
	public static final Integer ROLE_MANAGE_APP_WIDE_NOTIFICATION_EDIT = 6;
	public static final Integer ROLE_MANAGE_USERS_VIEW = 7;
	public static final Integer ROLE_CREATE_MANAGE_STUDIES = 8;

	
	public static final String REDIRECT_SESSION_PARAM_NAME ="sessionUserId=";
	public static final String PW_DATE_FORMAT = "MMddyy";
    public static final String DEFAULT = "default";
    
    public static final String REFERENCE_TYPE_CATEGORIES = "Categories";
    public static final String REFERENCE_TYPE_RESEARCH_SPONSORS = "Research Sponsors";
    public static final String REFERENCE_TYPE_DATA_PARTNER = "Data Partner";
    
    
    public static final String STUDY_TYPE_GT = "GT"; //study type gateway
    public static final String STUDY_TYPE_SD = "SD"; //study type standalone
    public static final String STUDY_PLATFORM_TYPE_IOS = "I"; //platform supported ios
    public static final String STUDY_PLATFORM_TYPE_ANDROID = "A"; //platform supported android 
    
    
    //steps
	public static final Integer SEQUENCE_NO_1=1;
	public static final Integer SEQUENCE_NO_2=2;
	public static final Integer SEQUENCE_NO_3=3;
	public static final Integer SEQUENCE_NO_4=4;
	public static final Integer SEQUENCE_NO_5=5;
	public static final Integer SEQUENCE_NO_6=6;
	public static final Integer SEQUENCE_NO_7=7;
	public static final Integer SEQUENCE_NO_8=8;
	public static final Integer SEQUENCE_NO_9=9;
	public static final Integer SEQUENCE_NO_10=10;
    
    //Folder Name
    public static final String STUDTYLOGO="studylogo";
    public static final String STUDTYPAGES="studypages";
    public static final String RESOURCEPDFFILES="studyResources";
    
    //Button Name
    public static final String SAVE_BUTTON = "save";
    public static final String COMPLETED_BUTTON = "completed";
    
    //Study permission del Falg
    public static final Integer DEL_STUDY_PERMISSION_ACTIVE = 1;
    public static final Integer DEL_STUDY_PERMISSION_INACTIVE = 0;
    
    //Study eligibility mechanism
    public static final Integer ID_VALIDATION_ONLY = 1;
	public static final Integer ID_VALIDATION_AND_ELIGIBILITY_TEST = 2;
	public static final Integer ELIGIBILITY_TEST = 3;
	
    public static final String VIEW_PAGE = "VIEW_PAGE";
    public static final String ADD_PAGE = "ADD_PAGE";
    public static final String EDIT_PAGE = "EDIT_PAGE";
    
    //Study Status
    public static final String STUDY_PRE_LAUNCH = "Pre-launch";
    public static final String STUDY_PRE_PUBLISH = "Pre-launch(Published)";
    public static final String STUDY_ACTIVE = "Active";
    public static final String STUDY_PUBLISH = "Published";
    public static final String STUDY_PAUSED = "Paused";
    public static final String STUDY_DEACTIVATED = "Deactivated";
    
    //Consent related constants
    public static final String CONSENT_TYPE_RESEARCHKIT = "ResearchKit";
    public static final String CONSENT_TYPE_CUSTOM = "Custom";
    
    //action type
    public static final String ACTION_TYPE_SAVE = "save";
    public static final String ACTION_TYPE_UPDATE = "update";
    public static final String ACTION_TYPE_COMPLETE = "complete";
    
    public static final String IMG_DEFAULT = "imgDefault";
    
    public static final String INSTRUCTION_STEP = "Instruction";
    public static final String QUESTION_STEP = "Question";
    public static final String FORM_STEP = "Form";
    //questionaire frequency schedule
    public static final String FREQUENCY_TYPE_ONE_TIME = "One time";
    public static final String FREQUENCY_TYPE_WITHIN_A_DAY = "Within a day";
    public static final String FREQUENCY_TYPE_DAILY = "Daily";
    public static final String FREQUENCY_TYPE_WEEKLY = "Weekly";
    public static final String FREQUENCY_TYPE_MONTHLY = "Monthly";
    public static final String FREQUENCY_TYPE_MANUALLY_SCHEDULE = "Manually Schedule";

    public static final String SCHEDULE="schedule";
    public static final String CONTENT="content";

    
    public static final String RESOURCE = "resource";
    public static final String NOTIFICATION = "notification";
    public static final String CONESENT = "consent";
    public static final String CONESENT_REVIEW = "consentreview";
    public static final String CHECK_LIST = "checkList";
    public static final String ACTIVETASK_LIST = "activeTaskList";
    public static final String QUESTIONNAIRE = "questionnaire";
    
    public static final String FETAL_KICK_COUNTER = "Fetal Kick Counter";
    public static final String TOWER_OF_HANOI = "Tower Of Hanoi";
    public static final String SPATIAL_SPAN_MEMORY = "Spatial Span Memory";
    
    //task time range options
    public static final String DAYS_OF_THE_CURRENT_WEEK = "Days of the current week";
    public static final String DAYS_OF_THE_CURRENT_MONTH = "Days of the current month";
    public static final String MULTIPLE_TIMES_A_DAY = "24 hours of current day";
    public static final String WEEKS_OF_THE_CURRENT_MONTH = "Weeks of the current month";
    public static final String MONTHS_OF_THE_CURRENT_YEAR  = "Months of the current year";
    public static final String RUN_BASED = "Run-based";

    //Active task attribute name
    public static final String SHORT_TITLE = "shortTitle";
    public static final String SHORT_NAME_STATISTIC = "identifierNameStat";
    
    public static final String SUC_MSG = "sucMsg";
    public static final String ERR_MSG = "errMsg";

    public static final String SD_DATE_FORMAT = "yyyy-MM-dd";
    
    //Action buttons
    public static final String ACTION_PUBLISH = "publishId";
    public static final String ACTION_UNPUBLISH = "unpublishId";
    public static final String ACTION_LUNCH = "lunchId";
    public static final String ACTION_UPDATES = "updatesId";
    public static final String ACTION_RESUME = "resumeId";
    public static final String ACTION_PAUSE = "pauseId";
    public static final String ACTION_DEACTIVATE = "deactivateId";
    
    //Action Failure message
    public static final String RESOURCE_DATE_ERROR_MSG = "One or more of the study Resources has a period of visibility with a start date that has already expired. Please correct the date and try again.";
    public static final String ACTIVETASK_DATE_ERROR_MSG = "One or more of the study's active tasks is scheduled to start on a date that has already expired. Please correct the dates and try again.";
    public static final String QUESTIONNARIES_ERROR_MSG = "One or more of the study's questionnaires for the study is scheduled for a date that has already expired. Please correct the dates and try again.";
    public static final String NOTIFICATION_ERROR_MSG = "One or more of the study's  notifications for the study is scheduled for a date that has already expired. Please correct the dates and try again.";
    public static final String PRE_PUBLISH_ENROLLMENT_ERROR_MSG = "You are attempting to publish the study as an UPCOMING one. Please ensure you set the Participant Enrollment Allowed setting to No in the Settings section and try again.";
    public static final String BASICINFO_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String SETTING_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String OVERVIEW_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String ELIGIBILITY_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String CONSENTEDUINFO_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String COMPREHENSIONTEST_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String ECONSENT_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String STUDYEXCQUESTIONNARIES_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String STUDYEXCACTIVETASK_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String RESOURCES_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String CHECKLIST_ERROR_MSG = "Required sections are not marked as Completed";
    public static final String LUNCH_ENROLLMENT_ERROR_MSG = "You are attempting to Launch the study. Please ensure you set the Participant Enrollment Allowed setting to Yes in the Settings section and try again.";
    public static final String ACTIVEANDQUESSIONAIREEMPTY_ERROR_MSG = "The study must have at least one activity before you can proceed with this action. Please add one or more activities (questionnaires or active tasks) and try again.";
    
    public static final String YES = "Yes";
	public static final String NO = "No";
	
	public static final String FDA_ENV_LOCAL = "local";
	public static final String FDA_ENV_UATL = "uat";
	public static final String FDA_ENV_PROD = "prod";
	
	public static final String INSTRUCTION_ADDED_SUCCESSFULLY = "Instruction added successfully.";
	public static final String INSTRUCTION_UPDATED_SUCCESSFULLY = "Instruction Updated successfully.";
	public static final String INSTRUCTION_ID= "instructionId";
	public static final String FORM_ID= "formId";
	public static final String QUESTION_ID= "questionId";
	
	//Update version draft update flag
	public static final String DRAFT_STUDY= "draftStudy";
	public static final String DRAFT_ACTIVITY= "draftActivity";
	public static final String DRAFT_CONCENT= "draftConcent";
	
	public static final String STUDY_ID = "studyId";
	public static final String PERMISSION = "permission";
	public static final String CUSTOM_STUDY_ID = "customStudyId";
	public static final String CONSENT_ID = "consentId";
	public static final String STUDY_BO = "studyBo";
	public static final String MESSAGE = "message";
	public static final String APPLICATION_JSON = "application/json";
	public static final String BUTTON_TEXT = "buttonText";
	public static final String COMPLETE_STUDY_SUCCESS_MESSAGE = "complete.study.success.message";
	public static final String SAVE_STUDY_SUCCESS_MESSAGE = "save.study.success.message";
	public static final String VIEW_SETTING_AND_ADMINS = "viewSettingAndAdmins";
	public static final String OVERVIEW_STUDY_PAGE = "overviewStudyPage";
	public static final String CONSENT_INFO_LIST_PAGE = "consentInfoListPage";
	public static final String CONSENT_INFO_LIST = "consentInfoList";
	public static final String OLD_ORDER_NUMBER = "oldOrderNumber";
	public static final String NEW_ORDER_NUMBER = "newOrderNumber";
	public static final String CONSENT_INFO_ID = "consentInfoId";
	public static final String CONSENT_INFO_PAGE = "consentInfoPage";
	public static final String ACTION_TYPE = "actionType";
	public static final String ACTION_PAGE = "actionPage";
	public static final String COMPREHENSION_QUESTION_ID = "comprehensionQuestionId";
	public static final String UNABLE_TO_MARK_AS_COMPLETE = "Unable to mark as complete.";
	public static final String RESOURCE_INFO_ID = "resourceInfoId";
	public static final String STUDY_PROTOCOL = "studyProtocol";
	public static final String ACTION = "action";
	public static final String IS_STUDY_PROTOCOL = "isstudyProtocol";
	public static final String ACTION_ON = "actionOn";
	
	//Notification flags
	public static final String NOTIFICATIONID = "notificationId";
	public static final String CHKREFRESHFLAG = "chkRefreshflag";
	public static final String REDIRECTTONOTIFICATIONLIST = "redirect:/adminNotificationView/viewNotificationList.do";
	public static final String ADDORCOPY = "addOrCopy";
	public static final String RESEND = "resend";
	
	private FdahpStudyDesignerConstants() {
		// Do nothing
	}
}