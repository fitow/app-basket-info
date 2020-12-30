package com.fitow2512.basketinfo.alexa.servlet;

import com.amazon.ask.Skill;
import com.amazon.ask.Skills;
import com.amazon.ask.servlet.SkillServlet;
import com.fitow2512.basketinfo.alexa.handlers.*;
import com.fitow2512.basketinfo.alexa.interceptors.request.LocalizationRequestInterceptor;
import com.fitow2512.basketinfo.alexa.interceptors.request.LogRequestInterceptor;
import com.fitow2512.basketinfo.alexa.interceptors.response.LogResponseInterceptor;

@SuppressWarnings("serial")
public class AlexaServlet extends SkillServlet {

    public AlexaServlet() {
        super(getSkill());
    }

    @SuppressWarnings("unchecked")
	private static Skill getSkill() {
        return Skills.standard()
                .addRequestHandlers(
                        new CancelandStopIntentHandler(),
                        new HelloWorldIntentHandler(),
                        new NewsIntentHandler(),
                        new TransfersIntentHandler(),
                        new HelpIntentHandler(),
                        new LaunchRequestHandler(),
                        new SessionEndedRequestHandler(),
                        new FallbackIntentHandler(),
                        new ErrorHandler())
                .addExceptionHandler(new MyExceptionHandler())
                .addRequestInterceptors(
                        new LogRequestInterceptor(),
                        new LocalizationRequestInterceptor())
                .addResponseInterceptors(new LogResponseInterceptor())
                // Add your skill id below
                //.withSkillId("[unique-value-here]")
                .build();
    }

}