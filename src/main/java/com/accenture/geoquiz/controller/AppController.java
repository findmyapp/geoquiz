package com.accenture.geoquiz.controller;


import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
	
	@RequestMapping(method=RequestMethod.GET, value="/cache.manifest")
    public ResponseEntity<String> getManifest() {
      String content = "CACHE MANIFEST\n" +
      		"CACHE:\n" +
      		"http://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js\n" +
      		"resources/app/sencha-touch.js\n" +
      		"resources/app/models/sha1.js\n" +
      		"resources/app/models/utils.js\n" +
      		"resources/app/models/storage.js\n" +
      		"resources/app/hr.js\n" +
      		"resources/css/sencha-touch.css\n" +
      		"resources/css/my.css\n\n" +
      		"NETWORK:\n" +
      		"events\n" +
      		"createUser\n" +
      		"respond";
      HttpHeaders responseHeaders = new HttpHeaders();
      responseHeaders.setContentType(MediaType.parseMediaType("text/cache-manifest"));
      return new ResponseEntity<String>(content, responseHeaders, HttpStatus.CREATED);
    }
	
}

