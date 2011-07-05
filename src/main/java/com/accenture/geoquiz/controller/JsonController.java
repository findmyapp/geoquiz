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
	 * Simply selects the home view to render by returning its name.
	 *
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String home() {
		logger.info("Welcome home!");
		return "home";
	}
	
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
	 */
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
}

