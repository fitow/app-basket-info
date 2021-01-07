package com.fitow2512.basketinfo.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fitow2512.basketinfo.alexa.localization.LocalizationManager;
import com.fitow2512.basketinfo.services.AsDataService;
import com.fitow2512.basketinfo.services.dtos.Standings;
import com.fitow2512.basketinfo.services.dtos.Team;

public class StandingsIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("StandingsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
    	String titleText = LocalizationManager.getInstance().getMessage("STANDINGS_TITLE");
        String introText = LocalizationManager.getInstance().getMessage("STANDINGS_INTRO");
        
        String playoffText = LocalizationManager.getInstance().getMessage("STANDINGS_PLAYOFF_TEXT");
        String restText = LocalizationManager.getInstance().getMessage("STANDINGS_REST_TEXT");
        String droppedText = LocalizationManager.getInstance().getMessage("STANDINGS_DROPPED_TEXT");
        String teamText = LocalizationManager.getInstance().getMessage("STANDINGS_TEAM_TEXT");

        	
        Standings standings = AsDataService.getAcbStandings();
        
        StringBuilder speechTextBuilder = new StringBuilder()
    			.append(introText)
    	
    	StringBuilder textBuilder = new StringBuilder()
    			.append(introText)
    			.append("\n \n");
    	
    	for(Team team : standings.getTeams()) {
    		
    		speechTextBuilder
				.append(item)
				.append(") ")
				.append(title)
				.append(". ")
				.append(content)
				.append(". ");
    		
    		textBuilder
				.append(team.getPosition())
				.append(") ")
				.append(team.getName())
				.append(" (")
				.append(team.getWins())
				.append(" - ")
				.append(team.getLosses())
				.append(") \n");
    		
    	}
    	
        
        
        return input.getResponseBuilder()
                .withSpeech(speechTextBuilder.toString())
                .withSimpleCard(titleText, textBuilder.toString())
                .build();
    }

}