package com.fdahpStudyDesigner.dao;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ManageUsersDAOImpl implements ManageUsersDAO{
	
	private static Logger logger = Logger.getLogger(ManageUsersDAOImpl.class);
	HibernateTemplate hibernateTemplate;
	private Query query = null;
	private Transaction transaction = null;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.hibernateTemplate = new HibernateTemplate(sessionFactory);
	}
	

}
