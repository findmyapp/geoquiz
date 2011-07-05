package com.accenture.geoquiz.controller.admin;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.accenture.geoquiz.controller.JsonController;
import com.accenture.geoquiz.model.Event;
import com.accenture.geoquiz.model.Place;
import com.accenture.geoquiz.model.Question;
import com.accenture.geoquiz.service.QuizService;
import com.google.gson.Gson;

@Controller
public class HomeController {
	@Autowired
	private Gson gson;
	@Autowired
	private QuizService service;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Controller for list of events and questions
	 */
	@RequestMapping(value="/admin/home", method=RequestMethod.GET)
	public ModelAndView getLists() {
		ModelAndView data = new ModelAndView("home");
		logger.info("Loading home view data");
		data.addObject("events", service.getEvents());
		data.addObject("questions", service.getQuestions());
		data.addObject("places", service.getPlaces());
		return data;
	}
	@RequestMapping(value="/admin/addPlace", method=RequestMethod.GET)
	public ModelAndView addPlace(
			@RequestParam(required=true) String name) {
		logger.info("Adding place "+name);
		service.addPlace(name);
		return new ModelAndView("redirect:home");
	}
	@RequestMapping(value="/admin/removePlace", method=RequestMethod.GET)
	public ModelAndView removePlace(
			@RequestParam(required=true) int placeId) {
		logger.info("Removing place "+placeId);
		service.removePlace(placeId);
		return new ModelAndView("redirect:home");
	}
	@RequestMapping(value="/admin/addQuestion", method=RequestMethod.GET)
	public ModelAndView addQuestion(
			@RequestParam(required=true) String question,
			@RequestParam(required=true) String answer) {
		logger.info("Adding question "+question);
		service.addQuestion(question, answer);
		return new ModelAndView("redirect:home");
	}
	@RequestMapping(value="/admin/removeQuestion", method=RequestMethod.GET)
	public ModelAndView removeQuestion(
			@RequestParam(required=true) int questionId) {
		logger.info("Removing question "+questionId);
		service.removeQuestion(questionId);
		return new ModelAndView("redirect:home");
	}
	
	
}
