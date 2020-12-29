package com.fitow2512.basketinfo.alexa.handlers;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.handler.RequestHandler;
import com.amazon.ask.model.LaunchRequest;
import com.amazon.ask.model.Response;
import com.fitow2512.basketinfo.alexa.localization.LocalizationManager;

import java.util.Optional;

import static com.amazon.ask.request.Predicates.requestType;

public class LaunchRequestHandler implements RequestHandler {

    @Override
    public boolean canHandle(HandlerInput input) {
        return input.matches(requestType(LaunchRequest.class));
    }

    @Override
    public Optional<Response> handle(HandlerInput input) {
    	String titleText = LocalizationManager.getInstance().getMessage("WELCOME_TITLE");
    	String speechText = LocalizationManager.getInstance().getMessage("WELCOME_MSG");
        return input.getResponseBuilder()
                .withSpeech(speechText)
                .withSimpleCard(titleText, speechText)
                .withReprompt(speechText)
                .build();
    }

}