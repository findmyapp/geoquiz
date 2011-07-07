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
public class HomeController {
	@Autowired
	private QuizService service;
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	/**
	 * Controller for list of events and questions
	 */
	@RequestMapping(value="/admin/home", method=RequestMethod.GET)
	public ModelAndView getLists() {
		return getLists(new Status());
	}
	private ModelAndView getLists(Status status) {
		ModelAndView data = new ModelAndView("home");
		logger.info("Loading home view data");
		data.addObject("events", service.getEvents());
		data.addObject("questions", service.getQuestions());
		data.addObject("places", service.getPlaces());
		data.addObject("status", status);
		return data;
	}
	@RequestMapping(value="/admin/addPlace", method=RequestMethod.GET)
	public ModelAndView addPlace(
			@RequestParam(required=true) String name) {
		logger.info("Adding place "+name);
		return getLists(service.addPlace(name));
	}
	@RequestMapping(value="/admin/removePlace", method=RequestMethod.GET)
	public ModelAndView removePlace(
			@RequestParam(required=true) int placeId) {
		logger.info("Removing place "+placeId);
		service.removePlace(placeId);
		return new ModelAndView("redirect:home");
	}
	@RequestMapping(value="/admin/editPlace", method=RequestMethod.GET)
	public ModelAndView editPlace(
			@RequestParam(required=true) int id,
			@RequestParam(required=true) String name) {
		logger.info("Renaming place "+id);
		return getLists(service.editPlace(id, name));
	}
	@RequestMapping(value="/admin/addQuestion", method=RequestMethod.GET)
	public ModelAndView addQuestion(
			@RequestParam(required=true) String question,
			@RequestParam(required=true) String answer) {
		logger.info("Adding question "+question);
		return getLists(service.addQuestion(question, answer));
	}
	@RequestMapping(value="/admin/removeQuestion", method=RequestMethod.GET)
	public ModelAndView removeQuestion(
			@RequestParam(required=true) int questionId) {
		logger.info("Removing question "+questionId);
		service.removeQuestion(questionId);
		return new ModelAndView("redirect:home");
	}
	@RequestMapping(value="/admin/editQuestion", method=RequestMethod.GET)
	public ModelAndView removeQuestion(
			@RequestParam(required=true) int id,
			@RequestParam(required=true) String question,
			@RequestParam(required=true) String answer) {
		logger.info("Editing question "+id);
		return getLists(service.editQuestion(id, question, answer));
	}
}
