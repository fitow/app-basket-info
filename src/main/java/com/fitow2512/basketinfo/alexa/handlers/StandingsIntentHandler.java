package com.fitow2512.basketinfo.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.amazon.ask.model.Slot;
import com.amazon.ask.model.slu.entityresolution.Resolution;
import com.fitow2512.basketinfo.alexa.localization.LocalizationManager;
import com.fitow2512.basketinfo.services.AsDataService;
import com.fitow2512.basketinfo.services.dtos.Standings;
import com.fitow2512.basketinfo.services.dtos.Team;
import com.fitow2512.basketinfo.utils.StringsUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StandingsIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("StandingsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {

    	boolean isEuroleague = isEuroleague(input);
    	
    	Standings standings = null;
    	if(isEuroleague) {
    		standings = AsDataService.getEuroleagueStandings();
    	}else {
    		standings = AsDataService.getAcbStandings();
    	}

        StringBuilder speechTextBuilder = new StringBuilder()
        		.append(getSpeechText(standings, isEuroleague));
    	
    	StringBuilder textBuilder = new StringBuilder()
    			.append(LocalizationManager.getInstance().getMessage("STANDINGS_INTRO", standings.getId()))
    			.append("\n \n")
    			.append(getTextTable(standings));

        return input.getResponseBuilder()
                .withSpeech(speechTextBuilder.toString())
                .withSimpleCard(LocalizationManager.getInstance().getMessage("STANDINGS_TITLE", standings.getId()), textBuilder.toString())
                .build();
    }
    
    private String getSpeechText(Standings standings, boolean isEuroleague) {
    	
    	StringBuilder speechTextBuilder = new StringBuilder();
    	
    	
    	Team firstTeam = standings.getTeams().get(0);
		speechTextBuilder.append(LocalizationManager.getInstance().getMessage("STANDINGS_FIRST_TEXT", 
				standings.getId(), 
				firstTeam.getName(), 
				firstTeam.getWins(), 
				firstTeam.getLosses()));
    	
    	
    	Team secondTeam = standings.getTeams().get(1);
    	speechTextBuilder.append(LocalizationManager.getInstance().getMessage("STANDINGS_SECOND_TEXT", 
    			secondTeam.getName(), 
    			secondTeam.getWins(), 
    			secondTeam.getLosses()));
    	
    	List<String> listPlayoffTeams = standings.getTeams().subList(2, 8).stream()
    		    .map(Team::getName)
    		    .collect(Collectors.toList());
    	String textPlayoffTeams = StringsUtils.joinList(listPlayoffTeams, ",", "y");
    	speechTextBuilder.append(LocalizationManager.getInstance().getMessage("STANDINGS_PLAYOFF_TEXT", 
    			textPlayoffTeams));
    	
    	
    	if(isEuroleague) {
    		
    		List<String> listRestEuroleagueTeams = standings.getTeams().subList(8, standings.getTeams().size()-1).stream()
        		    .map(Team::getName)
        		    .collect(Collectors.toList());
        	String textRestEuroleagueTeams = StringsUtils.joinList(listRestEuroleagueTeams, ",", "y");
        	String textLastTeam = standings.getTeams().get(standings.getTeams().size()-1).getName();
        	speechTextBuilder.append(LocalizationManager.getInstance().getMessage("STANDINGS_REST_EUROLEAGUE_TEXT", 
        			textRestEuroleagueTeams,
        			textLastTeam));
    	}else {
    		
    		List<String> listRestAcbTeams = standings.getTeams().subList(8, standings.getTeams().size()-2).stream()
        		    .map(Team::getName)
        		    .collect(Collectors.toList());
        	String textRestAcbTeams = StringsUtils.joinList(listRestAcbTeams, ",", "y");
        	String textSecondLastTeam = standings.getTeams().get(standings.getTeams().size()-2).getName();
        	String textLastTeam = standings.getTeams().get(standings.getTeams().size()-1).getName();
        	speechTextBuilder.append(LocalizationManager.getInstance().getMessage("STANDINGS_REST_ACB_TEXT", 
        			textRestAcbTeams,
        			textSecondLastTeam,
        			textLastTeam));
    	}
    	
    	return speechTextBuilder.toString();
    }
    
    private String getTextTable(Standings standings) {
    	
    	StringBuilder textBuilder = new StringBuilder();
    			
    	for(Team team : standings.getTeams()) {
    		textBuilder
				.append(team.getPosition())
				.append(". ")
				.append(team.getName())
				.append(" (")
				.append(team.getWins())
				.append(" - ")
				.append(team.getLosses())
				.append(") \n");
    		
    	}
    	
    	return textBuilder.toString();
    }
    
    private boolean isEuroleague(HandlerInput input) {
    	
    	try {
        	Optional<Slot> opSlot = com.amazon.ask.request.RequestHelper.forHandlerInput(input).getSlot("basketLeagues");
        	if(opSlot.isPresent()) {
        		Slot slot = opSlot.get();
        		if(slot.getResolutions()!=null && slot.getResolutions().getResolutionsPerAuthority()!=null) {
        			List<Resolution> listResolution = slot.getResolutions().getResolutionsPerAuthority();
        			if(!CollectionUtils.isEmpty(listResolution)) {
        				Resolution resolution = listResolution.get(0);
        				if(!CollectionUtils.isEmpty(resolution.getValues())) {
        					return "euroleague".equals(resolution.getValues().get(0).getValue().getId());
        				}
        			}
        		}
        	}
    	}catch (Exception e) {
    		log.error("Exception is Euroleague");
    		
		}
    	
    	return false;
    }
}