package com.moneysmart.controllers;

import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.moneysmart.URLShortnerApplication;
import com.moneysmart.common.URLValidator;
import com.moneysmart.models.RequestLocation;
import com.moneysmart.models.ShortUrls;
import com.moneysmart.service.AnalyticsService;
import com.moneysmart.service.URLShortnerService;

@Path("/")
public class URLShortnerController {
	
	private static final Logger logger = Logger.getLogger(URLShortnerController.class);
	
	private static URLShortnerService urlShortnerService = URLShortnerService.shortnerService;
	private static AnalyticsService analyticsService = AnalyticsService.anayticsService;

	@GET
	@Produces({MediaType.TEXT_HTML})
	@Path("/")
	public String getHomePage() {
		
		System.out.println("insiderequest");
		
		return 	  "<html>"
				+ 	"<body>"
					+"Enter URl: <form action=\"shorturl\" method=\"post\" enctype=\"application/x-www-form-urlencoded\"><input id=\"_url\" type=\"text\" name=\"_url\" /><input type=\"submit\" /></form>"
				+ "</body>"
				+ "</html>";
		
	}
	
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @POST
    @Produces(MediaType.TEXT_PLAIN)
	@Path("/shorturl")
	public String getShortUrl(@FormParam("_url") String url) {
		String shortUrl = "There was some problem. Please try again.";
        try {
        	if(URLValidator.urlValidator.validateURL(url))
        	{
        		shortUrl = urlShortnerService.shortUrl(url);
        	} else {
        		shortUrl = "Please enter a valid url.";
        	}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return shortUrl;
    }
	
	@GET
	@Produces({MediaType.TEXT_HTML})
	@Path("/analytics")
	public String getAnalyticsHomePage() {
		
		System.out.println("insiderequest");
		
		return 	  "<html>"
				+ 	"<body>"
					+"Enter Short URL: <form action=\"analyticsofurl\" method=\"get\" enctype=\"application/x-www-form-urlencoded\"><input id=\"url\" type=\"text\" name=\"url\" /><input type=\"submit\" /></form>"
				+ "</body>"
				+ "</html>";
		
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/analyticsofurl")
	public Response getAnalyticsOfUrl(@QueryParam("url") String url) {
		logger.info("Processing url: " + url);
		String randomId = url.substring(url.indexOf(URLShortnerApplication.properties.getProperty("URL_SHORTNER_DOMAIN")) + URLShortnerApplication.properties.getProperty("URL_SHORTNER_DOMAIN").length());
		try {
			ShortUrls shortUrl = urlShortnerService.getLongURLObjectFromID(randomId);
			List<RequestLocation> requestLocations = analyticsService.getRequestLocationFromShortUrl(shortUrl);
			
			System.out.println("Size is: " + requestLocations);
			
			if(requestLocations != null && requestLocations.size() > 0) {
				return Response.ok(requestLocations, MediaType.APPLICATION_JSON).build();
			} else {
				return Response.ok("No analytics data found!", MediaType.APPLICATION_JSON).build();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.noContent().build();
		
	}
	
	@GET
	@Path("/{id}")
	public Response redirectToUrl(@PathParam("id") String id, @Context HttpServletRequest requestContext) {
		System.out.println("Processing id: " + id);
		String errorMessage = "Please try again later";
		try {
			ShortUrls shortUrl = urlShortnerService.getLongURLObjectFromID(id);
			RequestLocation requestLocation = analyticsService.getRequestInfoFromRequest(requestContext);
			if(requestLocation != null)
			{
				analyticsService.saveRequestLocationObject(requestLocation, shortUrl);
			}
			
			if(shortUrl != null)
			{
				String longUrl = shortUrl.getUrl();
				if(longUrl != null && !longUrl.equals(""))
				{
					return Response.seeOther(new URI(longUrl)).build();
				} else {
					errorMessage = "No Url found";
				}
			} else {
				errorMessage = "No Url found";
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return Response.ok(errorMessage, MediaType.APPLICATION_JSON).build();
	}

}
