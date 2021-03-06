package com.fitow2512.basketinfo.services.utils;

import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JsoupConnection {

	public static int getStatusConnectionCode(String url) {
		try {
			Response response = SSLHelper.getConnection(url).userAgent("Mozilla/5.0").timeout(100000).ignoreHttpErrors(true).execute();		
			return response.statusCode();
		} catch (Exception ex) {
			log.error("Exception when get status code: " + ex.getMessage());
			return 500;
		}		
	}
	
	public static Document getHtmlDocument(String url) {
		try {
			return SSLHelper.getConnection(url).userAgent("Mozilla/5.0").timeout(100000).get();
	    } catch (Exception ex) {
			log.error("Exception when get web HTML" + ex.getMessage());
			return null;
	    }
	}
}
