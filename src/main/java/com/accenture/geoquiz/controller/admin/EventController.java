package com.accenture.geoquiz.controller.admin;

import java.sql.Timestamp;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.geoquiz.model.Event;
import com.accenture.geoquiz.service.QuizService;
import com.google.gson.Gson;

@Controller
public class EventController {
	@Autowired
	private Gson gson;
	@Autowired
	private QuizService service;
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	/**
	 * Controller for detailed view of events and questions
	 */
	@RequestMapping(value="/admin/event", method=RequestMethod.GET)
	public ModelAndView getLists(
			@RequestParam(required=true) int eventId) {
		logger.info("Detailed view of event "+eventId);
		ModelAndView data = new ModelAndView("event");
		data.addObject("event", service.getEvent(eventId));
		data.addObject("places", service.getPlaces());
		return data;
	}
	/**
	 * Controller for updating/saving events
	 */
	@RequestMapping(value="/admin/eventSubmit", method=RequestMethod.GET)
	public ModelAndView submitEvent(
			@RequestParam(required=true) int eventId,
			@RequestParam(required=true) String title,
			@RequestParam(required=true) String date,
			@RequestParam(required=false) Boolean open,
			@RequestParam(required=true) int placeId) {
		//logger.info(date);
		int id = service.submitEvent(eventId, title, null, placeId, open);
		//should redirect to /admin/event
		return new ModelAndView("redirect:event?eventId="+id);
	}

}
