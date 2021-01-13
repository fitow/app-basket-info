package com.fitow2512.basketinfo.services;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fitow2512.basketinfo.services.dtos.Standings;
import com.fitow2512.basketinfo.services.dtos.Team;
import com.fitow2512.basketinfo.services.utils.JsoupConnection;
import com.fitow2512.basketinfo.utils.ZonedDateTimeUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AsDataService {
	
	private static final String ACB_ID = "ACB";
	private static final String EUROLEAGUE_ID = "Euroliga";
	private static final String URL_ACB_TABLE = "https://resultados.as.com/resultados/baloncesto/acb/clasificacion";
	private static final String URL_EUROLEAGUE_TABLE = "https://resultados.as.com/resultados/baloncesto/euroliga/clasificacion";
	
	public static Standings getAcbStandings() {
		return getStandings(ACB_ID, URL_ACB_TABLE);
	}
	
	public static Standings getEuroleagueStandings() {
		return getStandings(EUROLEAGUE_ID, URL_EUROLEAGUE_TABLE);
	}
	
	public static Standings getStandings(String id, String url) {

		List<Team> listTeams = new ArrayList<Team>();
    	int statusConnectionCode = JsoupConnection.getStatusConnectionCode(url);
        if (statusConnectionCode == 200) {
            Document document = JsoupConnection.getHtmlDocument(url);
            if(document!=null) {
            	Elements tableElements = document.getElementsByClass("clasificacion-total");
            	Elements teamsElements = tableElements.select("tbody").select("tr");
            	if(teamsElements.size()>0) {
            		int item = 0;
            		for(Element element : teamsElements) {
            			Team team = getTeam(element, item);
            			if(team!=null) {
            				listTeams.add(team);	
            			}
            			item++;
            		}
            	}	
            }
        }
        
		return Standings.builder()
				.timeStamp(ZonedDateTimeUtils.getTimeStamp())
				.id(id)
				.teams(listTeams)
        		.build();
	}
	
	private static Team getTeam(Element element, int item) {

 		try {
 			
			String name = element.getElementsByClass("nombre-equipo").text();
			Elements data = element.select("td");
			
         	return Team.builder()
         			.position(item+1)
         			.name(name)
         			.wins(Integer.valueOf(data.get(1).text()))
         			.losses(Integer.valueOf(data.get(2).text()))
         			.build();
 			
 		}catch (Exception e) {
 			log.error("Exception when get team: " + item);
 			return null;
		}
	}
}
