package com.fitow2512.basketinfo.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fitow2512.basketinfo.alexa.localization.LocalizationManager;
import com.fitow2512.basketinfo.services.BasketDataService;
import com.fitow2512.basketinfo.services.dtos.Articles;


public class NewsIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("NewsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
    	String titleText = LocalizationManager.getInstance().getMessage("NEWS_TITLE");
    	String introText = LocalizationManager.getInstance().getMessage("NEWS_INTRO");
    	String sourceText = LocalizationManager.getInstance().getMessage("SOURCE_PIRATAS_MSG");
    	
    	Articles articles = BasketDataService.getNews();
    	StringBuilder speechTextBuilder = new StringBuilder()
    			.append(introText);
    	
    	StringBuilder textBuilder = new StringBuilder()
    			.append(introText)
    			.append("\n\n");
    	
    	int item = 1;
    	while (item < 6) {
    		
    		speechTextBuilder
				.append(item)
				.append(") ")
				.append(articles.getArticles().get(item).getTitle())
				.append(". ")
				.append(articles.getArticles().get(item).getContent())
				.append(". ");
    		
    		textBuilder
				.append(item)
				.append(") ")
				.append(articles.getArticles().get(item).getTitle())
				.append(".\n");
    		
    		item++;
		}

    	textBuilder
    		.append("\n\n")	
    		.append(sourceText);
	
    	
        return input.getResponseBuilder()
                .withSpeech(speechTextBuilder.toString())
                .withSimpleCard(titleText, textBuilder.toString())
                .build();
    }

}