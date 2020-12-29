package com.fitow2512.basketinfo.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fitow2512.basketinfo.alexa.localization.LocalizationManager;
import com.fitow2512.basketinfo.services.BasketDataService;
import com.fitow2512.basketinfo.services.dtos.Articles;

@Component
public class NewsIntentHandler implements RequestHandler {

	@Autowired
	private BasketDataService basketDataService;
	
    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("NewsIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
    	String titleText = LocalizationManager.getInstance().getMessage("NEWS_TITLE");
    	
    	Articles articles = basketDataService.getNews();
    	String speechText = new StringBuilder()
    			.append(articles.getArticles().get(0).getTitle())
    			.append(". ")
    			.append(articles.getArticles().get(0).getContent())
    			.toString();
    	
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(titleText, speechText)
                .build();
    }

}