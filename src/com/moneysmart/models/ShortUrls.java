package com.moneysmart.models;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "short_urls", catalog = "moneysmart")
public class ShortUrls {
	
	private Long id;
	private String url;
	private String urlMD5;
	private Date createdAt;
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = true)
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name = "url", nullable = false)
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	@Column(name = "url_md5", nullable = false)
	public String getUrlMD5() {
		return urlMD5;
	}
	public void setUrlMD5(String urlMD5) {
		this.urlMD5 = urlMD5;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = true, length = 0)
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
	
}
