package com.fdahpStudyDesigner.util;


/**
 * @author Vivek
 * 
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;




public class EmailNotification {
	
	
	private static Logger logger = Logger.getLogger(EmailNotification.class.getName());

	@SuppressWarnings("unchecked")	
	public static HashMap<String, String> propMap = fdahpStudyDesignerUtil.configMap;

	/**
	 * @param subjectProprtyName
	 * @param content
	 * @param toMail
	 * @param ccMailList
	 * @param bccMailList
	 * @return boolean
	 * @throws Exception
	 */
	public  static boolean sendEmailNotification(String subjectProprtyName, String content, String toMail, List<String> ccMailList, List<String> bccMailList ) throws Exception {
		logger.info("EmailNotification - Starts: sendLinkToEmail() - Input arg are ServletContext ,  Email = "+toMail+" Subject = "+propMap.get(subjectProprtyName)+" contents ="+content+" : ");
		boolean sentMail = false;
		try {
			Mail  mail = new Mail();
	        if(toMail != null){
	        	toMail = toMail.trim();
	        	mail.setToemail(toMail.toLowerCase());
	        }
	        mail.setFromEmailAddress(propMap.get("from.email.address"));        
	        mail.setFromEmailPassword(propMap.get("from.email.password"));        
	        mail.setSmtp_Hostname(propMap.get("smtp.hostname"));        
	        mail.setSmtp_portvalue(propMap.get("smtp.portvalue"));        
	        mail.setSslFactory(propMap.get("sslfactory.value"));
	        mail.setCcEmail(StringUtils.join(ccMailList, ','));
	        mail.setBccEmail(StringUtils.join(bccMailList, ','));
	        mail.setSubject(propMap.get(subjectProprtyName));
			mail.setMessageBody(content);			
	        mail.sendemail();
	        sentMail = true;
		} catch (Exception e) {
			sentMail = false;
			logger.error("EmailNotification.sendEmailNotification() :: ERROR ", e);
			throw new Exception("Exception in EmailNotification.sendEmailNotification() "+ e.getMessage(), e);
		}
		logger.info("EmailNotification - Ends: sendLinkToEmail() - returning  a List value"+" : ");
        return sentMail;
    }
	
	/**
	 * @param subjectProprtyName
	 * @param content
	 * @param toMail
	 * @param ccMailList
	 * @param bccMailList
	 * @param attachmentPath
	 * @return boolean
	 * @throws Exception
	 */
	public static boolean sendMailWithAttachment(String subjectProprtyName, String content, String toMail, List<String> ccMailList, List<String> bccMailList, String attachmentPath) throws Exception {
		logger.info("EmailNotification - Starts: sendLinkToEmail() - Input arg are ServletContext ,  Email = "+toMail+" Subject = "+propMap.get(subjectProprtyName)+" contents ="+content+" : ");
		boolean sentMail = false;
		try {
			Mail  mail = new Mail();
	        if(toMail != null){
	        	toMail = toMail.trim();
	        	mail.setToemail(toMail.toLowerCase());
	        }
	        mail.setFromEmailAddress(propMap.get("from.email.address"));        
	        mail.setFromEmailPassword(propMap.get("from.email.password"));        
	        mail.setSmtp_Hostname(propMap.get("smtp.hostname"));        
	        mail.setSmtp_portvalue(propMap.get("smtp.portvalue"));        
	        mail.setSslFactory(propMap.get("sslfactory.value"));
	        mail.setCcEmail(StringUtils.join(ccMailList, ','));
	        mail.setBccEmail(StringUtils.join(bccMailList, ','));
	        mail.setSubject(propMap.get(subjectProprtyName));
			mail.setMessageBody(content);
			mail.setAttachmentPath(attachmentPath);
	        mail.sendMailWithAttachment();
	        sentMail = true;
		} catch (Exception e) {
			sentMail = false;
			logger.error("EmailNotification.sendEmailNotification() :: ERROR ", e);
			throw new Exception("Exception in EmailNotification.sendEmailNotification() "+ e.getMessage(), e);
		}
		logger.info("EmailNotification - Ends: sendLinkToEmail() - returning  a List value"+" : ");
        return sentMail;
    }
	
	/**
	 * @param subjectProprtyName
	 * @param content
	 * @param toMailList
	 * @param ccMailList
	 * @param bccMailList
	 * @return boolean
	 * @throws Exception
	 */
	public  static boolean sendEmailNotificationToMany(String subjectProprtyName, String content, List<String> toMailList, List<String> ccMailList, List<String> bccMailList ) throws Exception {
		logger.info("EmailNotification - Starts: sendEmailNotificationToMany() - Input arg are ServletContext ");
		boolean sentMail = false;
		List<String> toMailListNew = new ArrayList<String>();
		try {
			Mail  mail = new Mail();
	        if(toMailList != null && toMailList.size() > 0){
	        	for(String mailId : toMailList){
	        		mailId = mailId.trim();
	        		toMailListNew.add(mailId.toLowerCase());
	        		logger.info("EmailNotification - Starts: sendEmailNotificationToMany() - Input arg are ServletContext ,  Email = "+mailId+" Subject = "+propMap.get(subjectProprtyName)+" contents ="+content+" : ");
	        	}
	        	mail.setToemail(StringUtils.join(toMailListNew, ','));
	        }
	        mail.setFromEmailAddress(propMap.get("from.email.address"));        
	        mail.setFromEmailPassword(propMap.get("from.email.password"));        
	        mail.setSmtp_Hostname(propMap.get("smtp.hostname"));        
	        mail.setSmtp_portvalue(propMap.get("smtp.portvalue"));        
	        mail.setSslFactory(propMap.get("sslfactory.value"));
	        mail.setCcEmail(StringUtils.join(ccMailList, ','));
	        mail.setBccEmail(StringUtils.join(bccMailList, ','));
	        mail.setSubject(propMap.get(subjectProprtyName));
			mail.setMessageBody(content);			
	        mail.sendemail();
	        sentMail = true;
		} catch (Exception e) {
			sentMail = false;
			logger.error("EmailNotification.sendEmailNotificationToMany() :: ERROR ", e);
			throw new Exception("Exception in EmailNotification.sendEmailNotificationToMany() "+ e.getMessage(), e);
		}
		logger.info("EmailNotification - Ends: sendEmailNotificationToMany() - returning  a List value"+" : ");
        return sentMail;
    }
}
