package com.fitow2512.basketinfo.alexa.interceptors.response;

import java.util.Optional;

import com.amazon.ask.dispatcher.request.handler.HandlerInput;
import com.amazon.ask.dispatcher.request.interceptor.ResponseInterceptor;
import com.amazon.ask.model.Response;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LogResponseInterceptor implements ResponseInterceptor {

    @Override
    public void process(HandlerInput input, Optional<Response> output) {
        log.info(output.toString());
    }
}