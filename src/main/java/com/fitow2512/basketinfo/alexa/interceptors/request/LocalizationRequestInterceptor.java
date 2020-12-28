package com.fitow2512.basketinfo.alexa.interceptors.request;

import java.util.Locale;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.RequestInterceptor;
import com.fitow2512.basketinfo.alexa.localization.LocalizationManager;

public class LocalizationRequestInterceptor implements RequestInterceptor {

    @Override
    public void process(HandlerInput input) {
        String localeString = input.getRequestEnvelope().getRequest().getLocale();
        Locale locale = new Locale.Builder().setLanguageTag(localeString).build();
        LocalizationManager.getInstance(locale);
    }
}