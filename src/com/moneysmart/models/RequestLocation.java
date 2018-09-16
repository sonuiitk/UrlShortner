package com.moneysmart.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "request_location_info", catalog = "moneysmart")
public class RequestLocation {
	
	private Long id;
	private String countryName;
	private String countryCode;
	private String cityName;
	private String latitude;
	private String longitude;
	private String userAgent;
	private String deviceOS;
	private String browser;
	private Date createdDate;
	private Long shortUrlId;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "country_name", nullable = true)
	public String getCountryName() {
		return countryName;
	}
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
	
	@Column(name = "country_code", nullable = true)
	public String getCountryCode() {
		return countryCode;
	}
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}
	
	@Column(name = "city_name", nullable = true)
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	@Column(name = "latitude", nullable = true)
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	@Column(name = "longitude", nullable = true)
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	@Column(name = "user_agent", nullable = true)
	public String getUserAgent() {
		return userAgent;
	}
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	@Column(name = "device_os", nullable = true)
	public String getDeviceOS() {
		return deviceOS;
	}
	public void setDeviceOS(String deviceOS) {
		this.deviceOS = deviceOS;
	}
	
	@Column(name = "device_browser", nullable = true)
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = true, length = 0)
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	@Column(name="short_url_id", nullable=true)
	public Long getShortUrlId() {
		return shortUrlId;
	}
	public void setShortUrlId(Long shortUrlId) {
		this.shortUrlId = shortUrlId;
	}

}
