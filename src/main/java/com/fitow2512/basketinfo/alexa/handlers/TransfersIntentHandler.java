package com.fitow2512.basketinfo.alexa.handlers;

import static com.amazon.ask.request.Predicates.intentName;

import java.util.List;
import java.util.Optional;

import org.springframework.util.CollectionUtils;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fitow2512.basketinfo.alexa.localization.LocalizationManager;
import com.fitow2512.basketinfo.services.PiratasBasketDataService;
import com.fitow2512.basketinfo.services.dtos.Article;
import com.fitow2512.basketinfo.services.dtos.Articles;


public class TransfersIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("TransfersIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
    	
    	String titleText = LocalizationManager.getInstance().getMessage("TRANSFERS_TITLE");
    	
    	Articles articles = PiratasBasketDataService.getTransfers();
    	
    	if(articles!=null && !CollectionUtils.isEmpty(articles.getArticles())) {
    		
        	String introText = LocalizationManager.getInstance().getMessage("TRANSFERS_INTRO");
      
        	StringBuilder speechTextBuilder = new StringBuilder()
        			.append(introText);
        	
        	StringBuilder textBuilder = new StringBuilder()
        			.append(introText)
        			.append("\n \n");
        	
        	List<Article> listArticles = articles.getArticles().subList(0, 4);
        	for(int i=0; i<listArticles.size(); i++) {
        		
        		int item = i+1;
        		String title = listArticles.get(i).getTitle();
        		String content = listArticles.get(i).getContent();
        		
        		speechTextBuilder
    				.append(item)
    				.append(") ")
    				.append(title)
    				.append(". ")
    				.append(content)
    				.append(". ");
        		
        		textBuilder
    				.append(item)
    				.append(") ")
    				.append(title)
    				.append(".\n");
        		
        	}
      
        	textBuilder
    			.append("\n \n")	
    			.append(LocalizationManager.getInstance().getMessage("SOURCE_PIRATAS_MSG"));
        	
            return input.getResponseBuilder()
                    .withSpeech(speechTextBuilder.toString())
                    .withSimpleCard(titleText, textBuilder.toString())
                    .build();
            
    	}else {
    		
    		String noDataText = LocalizationManager.getInstance().getMessage("TRANSFERS_NO_DATA");
            return input.getResponseBuilder()
                    .withSpeech(noDataText)
                    .withSimpleCard(titleText, noDataText)
                    .build();
    	}
    }

}