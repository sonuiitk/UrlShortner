package com.moneysmart.service;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.moneysmart.URLShortnerApplication;
import com.moneysmart.models.RequestLocation;
import com.moneysmart.models.ShortUrls;

public class DatabaseService {
	
	private final static SessionFactory factory = URLShortnerApplication.getMySQLSessionFactory();
	public static final DatabaseService databaseService = new DatabaseService();
	
	public Long save(Object object) {
		Session session = factory.openSession();
		Transaction tx = null;
		Long id = 0L;
		try {
			tx = session.beginTransaction();
			id = new Long((Long)session.save(object));
			session.flush();
			tx.commit();
		} catch (HibernateException exception) {
			if (tx!=null) tx.rollback();
		} finally {
			session.close();
		}
		System.out.println("returning with id: " + id);
		
		return id;
	}
	
	public ShortUrls getShortUrl(Long id) {
		ShortUrls shortUrl = null;
		Session session = factory.openSession();
		
		try {
			String hql = "FROM ShortUrls where id = :id";
			Query query = session.createQuery(hql)
							.setParameter("id", id);
					
			List<ShortUrls> result = query.list();
			if(result != null && result.size() > 0)
			{
				shortUrl = result.get(0);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return shortUrl;
	}
	
	public List<RequestLocation> getRequestLocationsFromShortUrlId(Long shortUrlId) {
		Session session = factory.openSession();
		List<RequestLocation> result = null;
		try {
			String hql = "FROM RequestLocation where short_url_id = :shortUrlId";
			Query query = session.createQuery(hql)
							.setParameter("shortUrlId", shortUrlId);
					
			result = query.list();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
		}
		
		return result;
	}

}
