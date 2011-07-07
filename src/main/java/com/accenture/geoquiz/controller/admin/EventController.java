package com.accenture.geoquiz.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.geoquiz.model.Status;
import com.accenture.geoquiz.service.QuizService;

@Controller
public class EventController {
	@Autowired
	private QuizService service;
	
	private static final Logger logger = LoggerFactory.getLogger(EventController.class);
	
	/**
	 * Controller for detailed view of events and questions
	 */
	@RequestMapping(value="/admin/event", method=RequestMethod.GET)
	public ModelAndView getLists(
			@RequestParam(required=true) int eventId) {
		return getLists(eventId, new Status());
	}
	private ModelAndView getLists(int eventId, Status status) {
		logger.info("Detailed view of event "+eventId);
		ModelAndView data = new ModelAndView("event");
		data.addObject("event", service.getEvent(eventId));
		data.addObject("places", service.getPlaces());
		data.addObject("status", status);
		data.addObject("responders", service.getUser(eventId));
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
		Status status = service.submitEvent(eventId, title, date, placeId, open);
		return getLists(status.getId(), status);
	}

}
