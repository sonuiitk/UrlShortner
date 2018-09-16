package com.moneysmart.common;

import java.io.File;
import java.io.IOException;

import com.maxmind.geoip.Location;
import com.maxmind.geoip.LookupService;
import com.moneysmart.URLShortnerApplication;
import com.moneysmart.models.RequestLocation;

public class GeoIpLocation {
	
	public static final GeoIpLocation geoIpLocation = new GeoIpLocation();
	private static File file = null;
	
	private GeoIpLocation() {
		file = new File(URLShortnerApplication.properties.getProperty("GEO_IP_FILE_LOCATION"));
		System.out.println("FIle is:: " + file);
	}
	
	public RequestLocation getLocation(String ipAddress, RequestLocation requestLocation) {

		if(requestLocation == null) {
			requestLocation = new RequestLocation();
		}

		try {

		requestLocation = new RequestLocation();
		if(file == null) {
			file = new File(URLShortnerApplication.properties.getProperty("GEO_IP_FILE_LOCATION"));
		}
		LookupService lookup = new LookupService(file,LookupService.GEOIP_MEMORY_CACHE);
		System.out.println("Ip: " + ipAddress);
		Location locationServices = lookup.getLocation(ipAddress);
		
		if(locationServices != null) {
			requestLocation.setCountryCode(locationServices.countryCode);
			requestLocation.setCountryName(locationServices.countryName);
			requestLocation.setCityName(locationServices.city);
			requestLocation.setLatitude(String.valueOf(locationServices.latitude));
			requestLocation.setLongitude(String.valueOf(locationServices.latitude));
		}

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}

		return requestLocation;

	}

}
