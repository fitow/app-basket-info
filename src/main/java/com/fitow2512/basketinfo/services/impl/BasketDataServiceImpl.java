package com.fitow2512.basketinfo.services.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitow2512.basketinfo.services.BasketDataService;
import com.fitow2512.basketinfo.services.dtos.Article;
import com.fitow2512.basketinfo.services.dtos.Articles;
import com.fitow2512.basketinfo.services.utils.JsoupConnection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BasketDataServiceImpl implements BasketDataService {
	
	private static final String URL_PIRATAS_BASKET = "https://piratasdelbasket.net/";	
	private static final String URL_PIRATAS_BASKET_TRANSFERS = "https://piratasdelbasket.net/category/rumores-fichajes/";

	@Autowired
	private JsoupConnection jsoupConnection;

	@Override
	public Articles getNews() {

		List<Article> listArticles = new ArrayList<Article>();
    	int statusConnectionCode = jsoupConnection.getStatusConnectionCode(URL_PIRATAS_BASKET);
        if (statusConnectionCode == 200) {
            Document document = jsoupConnection.getHtmlDocument(URL_PIRATAS_BASKET);
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
        		.timeStamp(getTimeStamp())
        		.numArticles(listArticles.size())
        		.articles(listArticles)
        		.build();
	}
	
	@Override
	public Articles getTransfers() {

		List<Article> listArticles = new ArrayList<Article>();
    	int statusConnectionCode = jsoupConnection.getStatusConnectionCode(URL_PIRATAS_BASKET_TRANSFERS);
        if (statusConnectionCode == 200) {
            Document document = jsoupConnection.getHtmlDocument(URL_PIRATAS_BASKET_TRANSFERS);
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
        		.timeStamp(getTimeStamp())
        		.numArticles(listArticles.size())
        		.articles(listArticles)
        		.build();
	}
	
	private Article getArticle(Element element, int item) {

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

	private ZonedDateTime getTimeStamp() {
		return ZonedDateTime.of(LocalDateTime.now(), ZoneId.of("Europe/Madrid"));		
	}
	
	private ZonedDateTime getArticleDate(String date) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMMM, yyyy");
			LocalDate ldDate = LocalDate.parse(date, formatter);
			return ldDate.atStartOfDay(ZoneId.of("Europe/Madrid"));
		} catch (Exception ex) {
			log.error("Exception when parser article date");
			return this.getTimeStamp();
		}		
	}
}
