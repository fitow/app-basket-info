package com.fitow2512.basketinfo.services;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fitow2512.basketinfo.services.dtos.Article;
import com.fitow2512.basketinfo.services.dtos.Articles;
import com.fitow2512.basketinfo.services.utils.JsoupConnection;
import com.fitow2512.basketinfo.utils.ZonedDateTimeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PiratasBasketDataService {
		
	private static final String URL_PIRATAS_BASKET = "https://piratasdelbasket.net/";	
	private static final String URL_PIRATAS_BASKET_TRANSFERS = "https://piratasdelbasket.net/category/rumores-fichajes/";

	public static Articles getNews() {
		return getArticles(URL_PIRATAS_BASKET);
	}
	
	public static Articles getTransfers() {
		return getArticles(URL_PIRATAS_BASKET_TRANSFERS);
	}
	
	private static Articles getArticles(String url) {

		List<Article> listArticles = new ArrayList<Article>();
    	int statusConnectionCode = JsoupConnection.getStatusConnectionCode(url);
        if (statusConnectionCode == 200) {
            Document document = JsoupConnection.getHtmlDocument(url);
            if(document!=null) {
            	Elements elements = document.getElementsByTag("article");
            	if(elements.size()>0) {
            		int item = 0;
            		for(Element element : elements) {
            			Article article = getArticle(element, item);
            			if(article!=null) {
            				listArticles.add(article);	
            			}
            			item++;
            		}
            	}	
            }
        }
        
		return Articles.builder()
        		.timeStamp(ZonedDateTimeUtils.getTimeStamp())
        		.numArticles(listArticles.size())
        		.articles(listArticles)
        		.build();
	}
	
	private static Article getArticle(Element element, int item) {

 		try {
 			
 			Elements entryTitle = element.getElementsByClass("entry-title");
      		String title = entryTitle.get(0).getElementsByTag("a").text();
      		String url = entryTitle.get(0).getElementsByTag("a").attr("href");
         	String date =  element.getElementsByClass("meta-item date").get(0).getElementsByTag("span").text();

         	String content = "";
         	Elements entryContent = !element.getElementsByClass("entry-content").isEmpty()? 
         			element.getElementsByClass("entry-content"):element.getElementsByClass("col-md-12\"");
         	if(!entryContent.isEmpty()) {
         		content = entryContent.get(0).getElementsByTag("p").text();
         	}
         	
         	return Article.builder()
         			.id(item)
         			.title(title)
         			.url(url)
         			.content(content)
         			.date(date)
         			.zdtDate(getArticleDate(date))
         			.build();
 			
 		}catch (Exception e) {
 			log.error("Exception when get article: " + item);
 			return null;
		}
	}
	
	private static ZonedDateTime getArticleDate(String date) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
			LocalDate ldDate = LocalDate.parse(date, formatter);
			return ldDate.atStartOfDay(ZonedDateTimeUtils.getZoneId());
		} catch (Exception ex) {
			log.error("Exception when parser article date");
			return ZonedDateTimeUtils.getTimeStamp();
		}		
	}
}
