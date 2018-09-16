package com.moneysmart.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.moneysmart.URLShortnerApplication;


public class URLValidator {
    public static final URLValidator urlValidator = new URLValidator();
    //private static final String URL_REGEX = "^(http:\\/\\/www\\.|https:\\/\\/www\\.|http:\\/\\/|https:\\/\\/)?[a-z0-9]+([\\-\\.]{1}[a-z0-9]+)*\\.[a-z]{2,5}(:[0-9]{1,5})?(\\/.*)?$";
    private static final String URL_REGEX = URLShortnerApplication.properties.getProperty("URL_VALIDATOR_REGEX");

    private static final Pattern URL_PATTERN = Pattern.compile(URL_REGEX);

    private URLValidator() {
    }

    public boolean validateURL(String url) {
        Matcher m = URL_PATTERN.matcher(url);
        return m.matches();
    }
    
    public static void main(String[] args) {
		URLValidator v = new URLValidator();
		System.out.println(v.validateURL("https://www.bankerbay.cksdndkgndgkdngdkgndfgom.edu-hellow.sddkgn.comby"));
	}
}
