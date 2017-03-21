package com.fdahpStudyDesigner.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.web.multipart.MultipartFile;

import com.fdahpStudyDesigner.bo.UserBO;
import com.fdahpStudyDesigner.bo.UserPermissions;

public class fdahpStudyDesignerUtil {
	private static Logger logger = Logger.getLogger(fdahpStudyDesignerUtil.class.getName());

	/* Read Properties file */
	@SuppressWarnings("rawtypes")
	public static HashMap configMap = fdahpStudyDesignerUtil.getAppProperties();
	/**
	 * @return HashMap
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static HashMap getAppProperties(){
		HashMap hm = new HashMap<String, String>();
		logger.warn("Properties Initialization");
		ResourceBundle rb = ResourceBundle.getBundle("messageResource");
		Enumeration<String> keys = rb.getKeys();
		while (keys.hasMoreElements()) {
			String key = keys.nextElement();
			String value = rb.getString(key);
			hm.put(key, value);
		}
		return hm;
	}
	
	/**
	 * check the session for a request 
	 * @author BTC
	 * 
	 * @param request
	 * @return {@link boolean}
	 */
	public static boolean isSession(HttpServletRequest request) {
		logger.info("fdahpStudyDesignerUtil - isSession() :: Starts");
		boolean flag = false;
		try{
			SessionObject sesObj = (SessionObject)request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
			if(sesObj != null){
				flag = true;
			}
		}catch (Exception e) {
			logger.error("fdahpStudyDesignerUtil - isSession() - ERROR ", e);
		}
		logger.info("fdahpStudyDesignerUtil - isSession() :: Ends");
		return flag;
	}

	public static String getTimeDiffInDaysHoursMins(Date dateOne, Date dateTwo) {
		String diff = "";
		long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
		diff = String.format("%d Day(s) %d hour(s) %d min(s)", TimeUnit.MILLISECONDS.toDays(timeDiff), TimeUnit.MILLISECONDS.toHours(timeDiff) - TimeUnit.DAYS.toHours(TimeUnit.MILLISECONDS.toDays(timeDiff)), 
							TimeUnit.MILLISECONDS.toMinutes(timeDiff) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(timeDiff)));
		return diff;
	}
	
	public static String getTimeDiffInMins(Date dateOne, Date dateTwo) {
		String diff = "";
		long timeDiff = Math.abs(dateOne.getTime() - dateTwo.getTime());
		diff = String.format("%d",TimeUnit.MILLISECONDS.toMinutes(timeDiff));
		return diff;
	}

	/**
	 * Get the current user's roles
	 * @author BTC
	 * 
	 * @param request
	 * @return {@link String} , A comma separated roles
	 */
	public static String getSessionUserRole(HttpServletRequest request) {
		logger.info("fdahpStudyDesignerUtil - getSessionUser() :: Starts");
		String userRoles = "";
		try{
			SecurityContext securityContext = SecurityContextHolder.getContext();
			Authentication authentication = securityContext.getAuthentication();
			if (authentication != null) {
				Collection<? extends GrantedAuthority>  authorities = authentication.getAuthorities();
				userRoles = StringUtils.join(authorities.iterator(), ",");
			}
		}catch (Exception e) {
			logger.error("fdahpStudyDesignerUtil - getSessionUser() - ERROR " + e);
		}
		logger.info("fdahpStudyDesignerUtil - getSessionUser() :: Ends");
		return userRoles;
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		logger.info("fdahpStudyDesignerUtil - isEmpty() :: Starts");
		boolean flag = false;
		if(null == str || (null != str && "".equals(str))){
			flag = true;
		}
		logger.info("fdahpStudyDesignerUtil - isEmpty() :: Ends");
		return flag;
	}

	/**
	 * @param str
	 * @return
	 */
	public static boolean isNotEmpty(String str) {
		logger.info("fdahpStudyDesignerUtil - isNotEmpty() :: Starts");
		boolean flag = false;
		if(null != str && !"".equals(str.trim())){
			flag = true;
		}
		logger.info("fdahpStudyDesignerUtil - isNotEmpty() :: Ends");
		return flag;
	}

	/**
	 * @param request
	 * @return
	 */
	public static boolean validateUserSession(HttpServletRequest request){
		boolean flag = false;
		SessionObject sesObj = (SessionObject)request.getSession().getAttribute(fdahpStudyDesignerConstants.SESSION_OBJECT);
		if(null != sesObj){
			flag = true;
		}
		return flag;
	}

	/**
	 * @return String
	 */
	/* Get Current Date and Time */
	public static String getCurrentDateTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = fdahpStudyDesignerConstants.DB_SDF_DATE_TIME;
		formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}

	/* Get Current Date */
	public static String getCurrentDate() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}
	
	public static String getCurrentTime() {
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm:ss");
		formatter.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		String dateNow = formatter.format(currentDate.getTime());
		return dateNow;
	}

	public static Date getCurrentDateTimeAsDate() {
		logger.info("fdahpStudyDesignerUtil - Entry Point: getCurrentDateTimeAsDate() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		Date dateNow = null;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeZone = "UTC";
		try {
			String strDate = new Date() + "";
			if(strDate.indexOf("IST") != -1){
				timeZone = "IST";
			}
			sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
			dateNow = sdf.parse(sdf.format(new Date()));
		} catch (Exception e) {
			logger.error("ERROR: getCurrentDateTimeAsDate(): " + e);
		}
		logger.info("fdahpStudyDesignerUtil - Exit Point: getCurrentDateTimeAsDate() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		return dateNow;
	}

	public static Date getCurrentUtilDateTime() {
		logger.info("fdahpStudyDesignerUtil - Entry Point: getCurrentUtilDateTime() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		Date utilDate = new Date();
		Calendar currentDate = Calendar.getInstance();
		String dateNow = fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.format(currentDate.getTime());
		try {
			utilDate = fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.parse(dateNow);
		} catch (ParseException e) {
			logger.error("fdahpStudyDesignerUtil - getCurrentUtilDateTime() : ",e);
			e.printStackTrace();
		}
		logger.info("fdahpStudyDesignerUtil - Exit Point: getCurrentUtilDateTime() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		return utilDate;
	}

	public static String getEncodedStringByBase64(String plainText) {
		//if(null!=plainText && !"".equals(plainText)){return "";}
		logger.info("fdahpStudyDesignerUtil - Entry Point: getEncodedStringByBase64() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		try {
			// encrypt data on your side using BASE64
			byte[]   bytesEncoded = Base64.getEncoder().encode(plainText.getBytes());
			return new String(bytesEncoded);
		} catch (Exception e) {
			logger.error("fdahpStudyDesignerUtil - getEncodedStringByBase64() : ",e);
			e.printStackTrace();
		}
		logger.info("fdahpStudyDesignerUtil - Exit Point: getEncodedStringByBase64() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		return "";
	}

	public static String getDecodedStringByBase64(String encodedText) {
		logger.info("fdahpStudyDesignerUtil - Entry Point: getDecodedStringByBase64() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		try {
			// Decrypt data on other side, by processing encoded data
			byte[] valueDecoded= Base64.getDecoder().decode(encodedText );
			return  new String(valueDecoded);

		} catch (Exception e) {
			logger.error("fdahpStudyDesignerUtil - getDecodedStringByBase64() : ",e);
			e.printStackTrace();
		}
		logger.info("fdahpStudyDesignerUtil - Exit Point: getDecodedStringByBase64() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		return "";
	}

	public static boolean fieldsValidation(String ... fields) {
		logger.info("fdahpStudyDesignerUtil - Entry Point: formValidation() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		List<String> fieldsList = new ArrayList<String>();
		boolean result = true;
		try {
			for (String field : fields) {
				fieldsList.add(field);
			}
			for(int i=0; i<fieldsList.size(); i++){
				String tempField = (String)fieldsList.get(i);
				tempField = StringUtils.isEmpty(tempField)!=true?tempField.trim():"";
				if(tempField.length()<1){
					return false;
				}
			}
		} catch (Exception e) {
			logger.error("ERROR: fdahpStudyDesignerUtil: formValidation(): " + e);
		}
		logger.info("fdahpStudyDesignerUtil - Exit Point: formValidation() - "+" : "+fdahpStudyDesignerUtil.getCurrentDateTime());
		return result;
	}


	public static String getDate(String date, SimpleDateFormat sdf) {
		logger.info("fdahpStudyDesignerUtil.getDate() :: Starts");
		String postedDate = sdf.format(date);
		logger.info("fdahpStudyDesignerUtil.getDate() :: Ends");
		return postedDate;
	}

	public static String removeLastCommaFromString(String str) {
		if (str.trim().length() > 0 && str.trim().endsWith(",")) {
			str = str.trim();
			str = str.substring(0, str.length() - 1);
			return str;
		} else {
			return str;
		}
	}

	public static Date addDaysToDate(Date date, int days) {
		logger.info("fdahpStudyDesignerUtiltyLinkUtil: addDaysToDate :: Starts");
		try {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			cal.add(Calendar.DATE, days);
			date = cal.getTime();
		} catch (Exception e) {
			logger.error("ERROR: fdahpStudyDesignerUtil.addDaysToDate() ::",e);
			e.printStackTrace();
		}
		logger.info("fdahpStudyDesignerUtil: addDaysToDate :: Ends");
		return date; 
	}

	public static String round(String value){
		logger.info("fdahpStudyDesignerUtil: String round :: Starts");
		String rounded = "0";
		try{
			if(StringUtils.isNotEmpty(value)){
				rounded = String.valueOf(Math.round(Double.parseDouble(value)));
			}
		} catch (Exception e) {
			logger.error("fdahpStudyDesignerUtil: String round() :: ERROR: ",e);
		}
		logger.info("fdahpStudyDesignerUtil: String round :: Ends");
		return rounded;
	}

	public static String round(float value){
		logger.info("fdahpStudyDesignerUtil: float round :: Starts");
		String rounded = "0";
		try{
			rounded = String.valueOf(Math.round(value));
		} catch (Exception e) {
			logger.error("fdahpStudyDesignerUtil: float round() :: ERROR: ",e);
		}
		logger.info("fdahpStudyDesignerUtil: float round :: Ends");
		return rounded;
	}

	public static String round(double value){
		logger.info("fdahpStudyDesignerUtil: double round :: Starts");
		String rounded = "0";
		try{
			rounded = String.valueOf(Math.round(value));
		} catch (Exception e) {
			logger.error("fdahpStudyDesignerUtil: double round() :: ERROR: ",e);
		}
		logger.info("fdahpStudyDesignerUtil: double round :: Ends");
		return rounded;
	}

	public static String formatTime(String inputTime, String inputFormat, String outputFormat){
		logger.info("fdahpStudyDesignerUtil.formatTime() :: Starts");
		String finalTime = "";
		SimpleDateFormat inputSDF = new SimpleDateFormat(inputFormat);
		SimpleDateFormat outputSDF = new SimpleDateFormat(outputFormat);
		if(inputTime != null && !"".equals(inputTime) && !"null".equalsIgnoreCase(inputTime)){
			try {
				finalTime = outputSDF.format(inputSDF.parse(inputTime)).toLowerCase();
			} catch (Exception e) {
				logger.error("fdahpStudyDesignerUtil.formatTime() ::",e);
				e.printStackTrace();
			}
		}
		logger.info("fdahpStudyDesignerUtil.formatTime() :: Ends");
		return finalTime;
	}
	
	public static String getStandardFileName(String actualFileName, String userFirstName, String userLastName) {
		String intial = userFirstName.charAt(0) + "" + userLastName.charAt(0);
		String dateTime = fdahpStudyDesignerConstants.SDF_FILE_NAME_TIMESTAMP.format(new Date());
		String fileName = actualFileName + "_" + intial + "_"+ dateTime;
		return fileName;
	}
	
	public static String uploadImageFile(MultipartFile file, String fileName,String folderName) {
		File serverFile = null;
		String actulName = null;
		if (file != null) {
			try {

				//fileName = fileName+"."+  FilenameUtils.getExtension(file.getOriginalFilename());/*file.getContentType().substring(file.getContentType().indexOf("/")).replace("/", ".");*/
				fileName = fileName+"."+  FilenameUtils.getExtension(file.getOriginalFilename());
				byte[] bytes = file.getBytes();
				String currentPath = configMap.get("fda.currentPath")!= null ? System.getProperty((String) configMap.get("fda.currentPath")): "";
				String rootPath = currentPath.replace('\\', '/')+(String) configMap.get("fda.imgUploadPath");
				File dir = new File(rootPath + File.separator + folderName);
				if (!dir.exists())
					dir.mkdirs();
				serverFile = new File(dir.getAbsolutePath() + File.separator+ fileName);
				BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				logger.info("Server File Location="+ serverFile.getAbsolutePath());
				actulName = fileName;
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		return actulName;
	}
	public static String getFormattedDate(String inputDate, String inputFormat, String outputFormat) {
		String finalDate = "";
		java.sql.Date formattedDate = null; 
		if (inputDate != null && !"".equals(inputDate) && !"null".equalsIgnoreCase(inputDate)){
			try {
				SimpleDateFormat formatter = new SimpleDateFormat(inputFormat);
				formattedDate = new java.sql.Date(formatter.parse(inputDate).getTime());
				formatter = new SimpleDateFormat(outputFormat);
				finalDate = formatter.format(formattedDate);
			} catch (Exception e){
				logger.error("Exception in getFormattedDate(): "+ e);
			}
		}
		return finalDate;
	}


	public static boolean GetEDTdatetimeAsStringCompare(String timeZone, String inputDate ,String inputFormat) {
		final SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
		sdf.setTimeZone(TimeZone.getTimeZone(timeZone));
		boolean flag = false;
		try {
			if (sdf.parse(inputDate).before(new Date())) {
				//System.out.println("The specified date is in the past");
				flag=false;
			}
			if (sdf.parse(inputDate).after(new Date())) {
				//System.out.println("The specified date is in the future");
				flag=true;
			} 
			if (sdf.parse(inputDate).equals(new Date())){
				//System.out.println("The specified date is now");
				flag=true;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static String addHours(String dtStr, int hours) {
		String newdateStr = "";
		try {
			Date dt = fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.parse(dtStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.add(Calendar.HOUR, hours);
			Date newDate = cal.getTime();
			newdateStr = fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.format(newDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return newdateStr; 
	}
	
	/**
	 * @param input
	 * @return String
	 */
	/* getEncodedString(String test) method returns Encoded String */
	public static String getEncryptedString(String input) {
		StringBuffer sb = new StringBuffer();
		logger.info("getEncryptedString start");
		if(input != null){
			/** Add the password salt to input parameter */
			input = input + fdahpStudyDesignerConstants.PASSWORD_SALT;
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
				messageDigest.update(input.getBytes("UTF-8"));
				byte[] digestBytes = messageDigest.digest();
				String hex = null;
				for (int i = 0; i < 8; i++) {
					hex = Integer.toHexString(0xFF & digestBytes[i]);
					if (hex.length() < 2)
						sb.append("0");
					sb.append(hex);
				}
			}
			catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		logger.info("getEncryptedString end");
		return sb.toString();
	}
	/**
	 * @param input
	 * @return {@link BCryptPasswordEncoder}
	 */
	/* getEncodedString(String test) method returns Encoded String */
	public static String getEncryptedPassword(String input) {
		String hashedPassword = null;
		logger.info("getEncryptedString start");
		if(input != null){
			try {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				hashedPassword = passwordEncoder.encode(input);
			}
			catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		logger.info("getEncryptedString end");
		return hashedPassword;
	}
	
	/**
	 * @param input
	 * @return {@link BCryptPasswordEncoder}
	 */
	/* getEncodedString(String test) method returns Encoded String */
	public static Boolean compairEncryptedPassword(String dbEncryptPassword, String uiPassword) {
		Boolean isMatch = false;
		logger.info("getEncryptedString start");
			try {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				isMatch = passwordEncoder.matches(uiPassword, dbEncryptPassword);
			}
			catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		logger.info("getEncryptedString end");
		return isMatch;
	}
	public static String getErrorMessage(HttpServletRequest request, String key) {

		Exception exception = (Exception) request.getSession().getAttribute(key);
		String userLoginFailure = (String) configMap.get("user.login.failure");
		String userInactiveMsg = (String) configMap.get("user.inactive.msg");
		String alreadyLoginMsg =  (String) configMap.get("user.alreadylogin.msg");
		String error = "";
		if (exception instanceof BadCredentialsException) {
			error = userLoginFailure;
		} else if (exception instanceof LockedException) {
			error = exception.getMessage();
		} else if (exception instanceof DisabledException) {
			error = userInactiveMsg;
		} else if (exception instanceof SessionAuthenticationException) {
			error = alreadyLoginMsg;
		} else if (exception instanceof AccountStatusException) {
			error = exception.getMessage()+"!";
		} else {
			error = userLoginFailure;
		}

		return error;
	}

	/**
	 * This method map db user details to spring user details.
	 * @author BTC
	 * 
	 * @param user , Object of {@link UserBO}
	 * @param authorities , List of {@link GrantedAuthority}
	 * @return {@link User}
	 */
	public  static User buildUserForAuthentication(UserBO user,
			List<GrantedAuthority> authorities) {
		return new User(user.getUserEmail(), user.getUserPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), authorities);
	}

	/**
	 * 
	 * @author BTC
	 * 
	 * @param userRoles , {@link Set} of {@link UserPermissions} 
	 * @return {@link List} of {@link GrantedAuthority}
	 */
	public  static List<GrantedAuthority> buildUserAuthority(Set<UserPermissions> userRoles) {

		Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

		// Build user's authorities
		for (UserPermissions userRole : userRoles) {
			setAuths.add(new SimpleGrantedAuthority(userRole.getPermissions()));
		}

		List<GrantedAuthority> result = new ArrayList<GrantedAuthority>(setAuths);

		return result;
	}
	public static String getEncryptedFormat(String input){
		StringBuffer sb = new StringBuffer();
		logger.debug("Password Encryption method==start");
		if(input != null){
			/* Add the password salt to input parameter */
			input = input + fdahpStudyDesignerConstants.ENCRYPT_SALT;
			try {
				MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
				messageDigest.update(input.getBytes("UTF-8"));
				byte[] digestBytes = messageDigest.digest();
				String hex = null;
				for (int i = 0; i < 8; i++) {
					hex = Integer.toHexString(0xFF & digestBytes[i]);
					if (hex.length() < 2)
						sb.append("0");
					sb.append(hex);
				}
			}
			catch (Exception ex) {
				logger.error(ex.getMessage());
			}
		}
		logger.debug("Password Encryption method==end");
		return sb.toString();
	}

	/**
	 * This method used to map the dynamic value to it's static email marker to  create a dynamic email contain 
	 * @author BTC
	 * 
	 * @param emailContentName , the email content name from property file
	 * @param keyValue , the key value pair of email content marker and it's value
	 * @return
	 */
	public static String genarateEmailContent(String emailContentName, Map<String, String> keyValue){

		String dynamicContent = (String) configMap.get(emailContentName);

		if(fdahpStudyDesignerUtil.isNotEmpty(dynamicContent)){
			for (Map.Entry<String, String> entry : keyValue.entrySet()) {
				dynamicContent	= dynamicContent.replace(entry.getKey(), StringUtils.isBlank(entry.getValue()) ? "": entry.getValue());
			}
		}
		return dynamicContent;
	}

	/**
	 * @author BTC
	 * {Description This method is used to add minutes to the input dateTime}}
	 * @param dtStr
	 * @param minutes
	 * @return
	 */
	public static String addMinutes(String dtStr, int minutes) {
		logger.info("fdahpStudyDesignerUtil - Entry Point: addMinutes()");
		String newdateStr = "";
		try {
			Date dt = fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.parse(dtStr);
			Calendar cal = Calendar.getInstance();
			cal.setTime(dt);
			cal.add(Calendar.MINUTE, minutes);
			Date newDate = cal.getTime();
			newdateStr = fdahpStudyDesignerConstants.DB_SDF_DATE_TIME.format(newDate);
		} catch (ParseException e) {
			logger.error("fdahpStudyDesignerUtil - addMinutes() : ", e);
			e.printStackTrace();
		}
		logger.info("fdahpStudyDesignerUtil - Exit Point: addMinutes()");
		return newdateStr; 
	}

	public static Integer getTimeDiffToCurrentTimeInHr(Date date) {
		logger.info("fdahpStudyDesignerUtil - Entry Point: getTimeDiffToCurrentTimeInHr() - "
				+ " : " + fdahpStudyDesignerUtil.getCurrentDateTime());
		Integer diffHours = null;
		float diff = 0.0f;
		try {
			Date dt2 = new Date();
			diff = dt2.getTime() - date.getTime();
			diffHours = Math.round((diff / (60 * 60 * 1000)));
		} catch (Exception e) {
			logger.error("fdahpStudyDesignerUtil - getTimeDiffToCurrentTimeInHr() : ",
					e);
		}
		logger.info("fdahpStudyDesignerUtil - Exit Point: getTimeDiffToCurrentTimeInHr() - ");
		return diffHours;
	}
	
	/**
	 * @author BTC
	 * @param timeZone, currentDateTime
	 * @param userCurrentDateTimeForTimeZone
	 * @return
	 */
	public static String getDateAndTimeBasedOnTimeZone(String timeZone, String dateTime) throws Exception{
		String actualDateTime = null;
		Date fromDate = null;
		if(StringUtils.isNotEmpty(timeZone) && StringUtils.isNotEmpty(timeZone)){
			SimpleDateFormat toDateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			fromDate = toDateFormatter.parse(dateTime);
			toDateFormatter.setTimeZone(TimeZone.getTimeZone(timeZone));
			logger.info(" Date Time in seconds : "+fromDate.getTime());
			actualDateTime = toDateFormatter.format(fromDate.getTime());
		}else{
			actualDateTime = timeZone;
		}
		logger.info(" User Date and Time based on the Time Zone : "+actualDateTime);
		return actualDateTime;
	}
	/**
	 * Comapring  user date with current date 
	 * @param inputDate
	 * @param inputFormat
	 * @return
	 */
	public static boolean  compareDateWithCurrentDateTime(String inputDate, String inputFormat){
		   boolean flag = false;
		   final SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
		    //TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
		    //sdf.setTimeZone(TimeZone.getTimeZone("America/New_York"));
		    try {
             if (new Date().before(sdf.parse(inputDate))) {
                 flag=true;
             } 
             if (new Date().equals(sdf.parse(inputDate))) {
                 flag=true;
             }
			} catch (ParseException e) {
				e.printStackTrace();
			}
		    return flag;
	   }
	
	  /*public static void main(String[] args) {
		String dateTime = "2017-02-14 07:41:21";
		System.out.println(compareDateWithCurrentDateTime(dateTime, "yyyy-MM-dd HH:mm:ss"));
	}*/
}
