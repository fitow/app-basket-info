package com.fitow2512.basketinfo.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Optional;

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

        String standingId = standings.getId();
    	String titleText = LocalizationManager.getInstance().getMessage("STANDINGS_TITLE", standingId);
        String introText = LocalizationManager.getInstance().getMessage("STANDINGS_INTRO", standingId);
        
        StringBuilder speechTextBuilder = new StringBuilder()
    			.append(introText);
    	
    	StringBuilder textBuilder = new StringBuilder()
    			.append(introText)
    			.append("\n \n");
    	
    	for(Team team : standings.getTeams()) {
    		
    		String name = team.getName();
    		Integer position = team.getPosition();
    		Integer wins = team.getWins();
    		Integer losses = team.getLosses();
    		
    		if(position==1) {
    			String playoffText = LocalizationManager.getInstance().getMessage("STANDINGS_PLAYOFF_TEXT");
        		speechTextBuilder
    				.append(playoffText);
    		}
    		
    		if(position==9) {
    			String restText = LocalizationManager.getInstance().getMessage("STANDINGS_REST_TEXT");
        		speechTextBuilder
    				.append(restText);
    		}
    		
    		if(!isEuroleague && position==standings.getTeams().size()-1) {
    			String droppedText = LocalizationManager.getInstance().getMessage("STANDINGS_DROPPED_TEXT");
        		speechTextBuilder
    				.append(droppedText);
    		}
    		
    		String teamText = LocalizationManager.getInstance().getMessage("STANDINGS_TEAM_TEXT", position, name, wins, losses);
    		speechTextBuilder
				.append(teamText);
    		
    		textBuilder
				.append(position)
				.append(") ")
				.append(name)
				.append(" (")
				.append(wins)
				.append(" - ")
				.append(losses)
				.append(") \n");
    		
    	}
         
        return input.getResponseBuilder()
                .withSpeech(speechTextBuilder.toString())
                .withSimpleCard(titleText, textBuilder.toString())
                .build();
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