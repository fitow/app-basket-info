package com.fitow2512.basketinfo.alexa.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.Response;
import com.fitow2512.basketinfo.alexa.localization.LocalizationManager;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.intentName;

public class ScoresIntentHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(intentName("ScoresIntent"));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
    	String titleText = LocalizationManager.getInstance().getMessage("SCORES_TITLE");
        String speechText = LocalizationManager.getInstance().getMessage("SCORES_INTRO");
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(titleText, speechText)
                .build();
    }

}