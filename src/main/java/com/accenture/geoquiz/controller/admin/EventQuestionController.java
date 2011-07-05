package com.accenture.geoquiz.controller.admin;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.geoquiz.service.QuizService;
import com.google.gson.Gson;

@Controller
public class EventQuestionController {
	@Autowired
	private Gson gson;
	@Autowired
	private QuizService service;
	
	private static final Logger logger = LoggerFactory.getLogger(EventQuestionController.class);
	
	/**
	 * Controller to link questions to posts and events
	 */
	@RequestMapping(value="/admin/eventQuestion", method=RequestMethod.GET)
	public ModelAndView showQuestion(
			@RequestParam(required=true) int eventId) {
		logger.info("show new question for event "+eventId);
		ModelAndView data = new ModelAndView("eventQuestion");
		data.addObject("eventId", eventId);
		data.addObject("questions", service.getQuestions());
		return data;
	}
	/**
	 * Controller to save questions to events
	 */
	@RequestMapping(value="/admin/addEventQuestion", method=RequestMethod.GET)
	public ModelAndView saveQuestion(
			@RequestParam(required=true) int eventId,
			@RequestParam(required=true) int questionId,
			@RequestParam(required=true) String description,
			@RequestParam(required=true) String activationCode) {
		logger.info("add the new question to event "+eventId);
		service.addEventQuestion(eventId, questionId, description, activationCode);
		return new ModelAndView("redirect:event?eventId="+eventId);
	}
	
	/**
	 * Controller to remove questions from events
	 */
	@RequestMapping(value="/admin/removeEventQuestion", method=RequestMethod.GET)
	public ModelAndView removeQuestion(
			@RequestParam(required=true) int eventId,
			@RequestParam(required=true) int questionId) {
		logger.info("removing eventquestion");
		service.removeEventQuestion(eventId, questionId);
		return new ModelAndView("redirect:event?eventId="+eventId);
	}
}
