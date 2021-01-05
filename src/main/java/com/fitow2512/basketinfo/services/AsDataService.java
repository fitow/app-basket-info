package com.fitow2512.basketinfo.services;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fitow2512.basketinfo.services.dtos.Articles;
import com.fitow2512.basketinfo.services.utils.JsoupConnection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsDataService {
		
	private static final String URL_ACB_TABLE = "https://resultados.as.com/resultados/baloncesto/acb/clasificacion";
	private static final String URL_EUROLEAGUE_TABLE = "https://resultados.as.com/resultados/baloncesto/euroliga/clasificacion";
	
	
	public static Articles getAcbTable() {

    	int statusConnectionCode = JsoupConnection.getStatusConnectionCode(URL_EUROLEAGUE_TABLE);
        if (statusConnectionCode == 200) {
            Document document = JsoupConnection.getHtmlDocument(URL_EUROLEAGUE_TABLE);
            if(document!=null) {
            	Elements table = document.getElementsByClass("clasificacion-total");
            	Elements teams = table.get(0).getElementsByClass("zone-top-1");
            	if(teams.size()>0) {
            		int item = 0;
            		for(Element team : teams) {
                    	Elements data = team.getElementsByClass("cont-nombre-equipo");
                    	
//            			Article article = getArticle(element, item);
//            			if(article!=null) {
//            				listArticles.add(article);	
//            			}
            			item++;
            		}
            	}	
            }
        }
        
		return Articles.builder()
        		.build();
	}
}
