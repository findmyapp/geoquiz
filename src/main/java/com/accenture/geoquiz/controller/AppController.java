package com.accenture.geoquiz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Handles requests for the application home page.
 */
@Controller
public class AppController {

	private static final Logger logger = LoggerFactory.getLogger(AppController.class);

	@RequestMapping(value="/app", method=RequestMethod.GET)
	public ModelAndView startApp(){
		return new ModelAndView("app");
	}
	
}

