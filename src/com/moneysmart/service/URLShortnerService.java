package com.moneysmart.service;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.TimeZone;

import com.moneysmart.URLShortnerApplication;
import com.moneysmart.common.UniqueIdGenerator;
import com.moneysmart.models.ShortUrls;

public class URLShortnerService {

	public static final URLShortnerService shortnerService = new URLShortnerService();
	
	public String shortUrl(String url) throws Exception {
        String shortenedUrl = shortnerService.shortenURL(URLShortnerApplication.properties.getProperty("URL_SHORTNER_DOMAIN"), url);
        return shortenedUrl;
        
	}
	
	public String shortenURL(String localURL, String url) {
        Long id = createShortUrlObject(url);
        String shortenedURL = null ;
        if(id > 0)
        {
	        String uniqueID = UniqueIdGenerator.uniqueIdGenerator.createUniqueID(id);
	        shortenedURL = localURL + uniqueID;
        }
        return shortenedURL;
    }
	
	public Long createShortUrlObject(String url) {
		Long dbId = 0L;
		ShortUrls shortUrl = new ShortUrls();
		shortUrl.setUrl(url);
		shortUrl.setCreatedAt(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
		try {
			byte[] bytesOfMessage = url.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] thedigest = md.digest(bytesOfMessage);
			StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < thedigest.length; ++i) {
	          sb.append(Integer.toHexString((thedigest[i] & 0xFF) | 0x100).substring(1,3));
	        }
			String md5Str = sb.toString();
			shortUrl.setUrlMD5(md5Str);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		dbId = (Long) DatabaseService.databaseService.save(shortUrl);
		
		return dbId;
		
	}

    public ShortUrls getLongURLObjectFromID(String uniqueID) throws Exception {
        Long dictionaryKey = UniqueIdGenerator.uniqueIdGenerator.getDictionaryKeyFromUniqueID(uniqueID);
        ShortUrls shortUrl = DatabaseService.databaseService.getShortUrl(dictionaryKey);
        
        
        
        return shortUrl;
    }
}
