package com.moneysmart.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;

import com.moneysmart.common.DeviceInfo;
import com.moneysmart.common.GeoIpLocation;
import com.moneysmart.models.RequestLocation;
import com.moneysmart.models.ShortUrls;

public class AnalyticsService {
	
	public static final AnalyticsService anayticsService = new AnalyticsService();
	
	private AnalyticsService() {
		
	}
	
	public RequestLocation getRequestInfoFromRequest(HttpServletRequest requestContext) {
		String IpAddress = requestContext.getRemoteAddr();
		String userAgent = requestContext.getHeader("User-Agent");
		
		RequestLocation requestLocation = new RequestLocation();
		requestLocation = GeoIpLocation.geoIpLocation.getLocation(IpAddress, requestLocation);
		requestLocation = DeviceInfo.deviceInfo.getDeviceInfo(userAgent, requestLocation);
		
		return requestLocation;
	}
	
	public void saveRequestLocationObject(RequestLocation requestLocation, ShortUrls shortUrl) {
		requestLocation.setShortUrlId(shortUrl.getId());
		requestLocation.setCreatedDate(Calendar.getInstance(TimeZone.getTimeZone("GMT")).getTime());
		DatabaseService.databaseService.save(requestLocation);
	}
	
	public List<RequestLocation> getRequestLocationFromShortUrl(ShortUrls shortUrl) {
		List<RequestLocation> requestLocations = null;
		if(shortUrl != null) {
			requestLocations = DatabaseService.databaseService.getRequestLocationsFromShortUrlId(shortUrl.getId());
		}
		
		return requestLocations;
	}

}
