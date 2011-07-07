package com.accenture.geoquiz.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.geoquiz.model.*;
import com.accenture.geoquiz.service.QuizService;
import com.google.gson.Gson;

/**
 * Handles requests for the application home page.
 */
@Controller
public class JsonController {
	
	@Autowired
	private Gson gson;
	@Autowired
	private QuizService service;
	
	private static final Logger logger = LoggerFactory.getLogger(JsonController.class);

	/**
	 * List all open events
	 */
	@RequestMapping(value="/events", method=RequestMethod.GET)
	public ModelAndView getOpenEvents() {
		logger.info("List all open events");
		List<Event> events = service.getOpenEvents();
		return new ModelAndView("json", "events", gson.toJson(events));
	}
	/**
	 * Get unhashed event (including questions)
	 * This method should probably not be available in the json api
	@RequestMapping(value="/backend/event", method=RequestMethod.GET)
	public ModelAndView getEvent(
			@RequestParam(required=true) int eventId) {
		logger.info("getting quiz for event :"+eventId);
		Event event = service.getEvent(eventId);
		return new ModelAndView("json", "events", gson.toJson(event));
	}
	/**
	 * Get hashed event (including questions)
	 */
	@RequestMapping(value="/event", method=RequestMethod.GET)
	public ModelAndView getHashedEvent(
			@RequestParam(required=true) int eventId) {
		logger.info("getting quiz for event :"+eventId);
		Event event = service.getHashedEvent(eventId);
		return new ModelAndView("json", "events", gson.toJson(event));
	}
	/**
	 * Send in answers
	 */
	@RequestMapping(value="/respond", method=RequestMethod.GET)
	public ModelAndView submitAnswers(
			@RequestParam(required=true) String user,
			@RequestParam(required=true) String answers) {
		logger.info("Recieved answers");
		int correct = service.submitAnswers(user, answers);
		return new ModelAndView("json", "correct", 1);
	}
	public ModelAndView createUser(
			@RequestParam(required=true) String user) {
		logger.info("Request to create a new user");
		ModelAndView data = service.createUser(user);
		data.setViewName("json");
		return data;
		
	}
}

