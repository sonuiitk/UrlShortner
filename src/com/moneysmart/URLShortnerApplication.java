package com.moneysmart;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

public class URLShortnerApplication extends HttpServlet{
	
	private static final long serialVersionUID = 1L;
	public static Properties properties = null;
	private static SessionFactory mySqlSessionFactory = null;
	private static Logger logger = Logger.getLogger(URLShortnerApplication.class);
	
	public synchronized void init() {
		
		System.out.println("Initializing");
		InputStream fis = URLShortnerApplication.class.getClassLoader().getResourceAsStream("urlshortner.properties");
		properties = new Properties();
        try {
			properties.load(fis);
		} catch (IOException e1) {
			e1.printStackTrace();
			logger.error("Error in loading properties file forum.properties");
		} finally {
			try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
        
        
        
        System.out.println("ENding");
		
        InputStream mySQLPropsStream = URLShortnerApplication.class.getClassLoader().getResourceAsStream("hibernate-mysql.properties");
		Properties mySQLadsProp = new Properties();
		try {
			mySQLadsProp.load(mySQLPropsStream);
		} catch (IOException e) {
			logger.error("Could not load properties due to:" + e.getMessage()
					+ " at:" + new Date());
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("Exception caught while loading properties from DB");
		} 
		
		try {
			mySQLPropsStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		mySqlSessionFactory = ((AnnotationConfiguration) new AnnotationConfiguration()
				.addProperties(mySQLadsProp))
				.addAnnotatedClass(com.moneysmart.models.ShortUrls.class)
				.addAnnotatedClass(com.moneysmart.models.RequestLocation.class)
				.buildSessionFactory();
		
		return ;
	}
	
	public static synchronized SessionFactory getMySQLSessionFactory() {
		return mySqlSessionFactory;
	}
}
