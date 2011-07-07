package com.accenture.geoquiz.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

@Service
public class WhiteListService {
	private Pattern pattern;
	
	public WhiteListService() {
		pattern = Pattern.compile("^[a-zA-Z0-9æøåÆØÅ \\.\\<\\>\\+\\*\\(\\)\\-/=\\?,]*$");
	}
	
	public Boolean isValid(String s) {
		Matcher m = pattern.matcher(s);
		return m.matches();
	}

}
