package com.accenture.geoquiz.service;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.geoquiz.controller.admin.EventQuestionController;
import com.accenture.geoquiz.datasource.*;
import com.accenture.geoquiz.model.*;

@Service
public class QuizService {
	@Autowired
	private EventRepository eventData;
	@Autowired
	private QuestionRepository questionData;
	@Autowired
	private UserRepository userData;
	@Autowired
	private PlaceRepository placeData;
	@Autowired
	private DateFormat dateFormat;
	@Autowired
	private WhiteListService whiteService;
	
	private static final Logger logger = LoggerFactory.getLogger(EventQuestionController.class);
	
	public List<Event> getEvents() {
		return eventData.getEvents();
	}
	public List<Event> getOpenEvents() {
		return eventData.getOpenEvents();
	}
	public Event getEvent(int eventId) {//no hash on answers
		Event event;
		if (eventId != -1) {
			event = eventData.getEvent(eventId);
			event.setQuestions(questionData.getQuestion(eventId));
		}
		else {//for when requesting new events
			event = new Event();
			event.setId(-1);
			event.setPlace(new Place());
			event.setTitle("");
			event.setQuestions(new ArrayList<Question>());
			event.setEventDate(new Timestamp(0));
		}
		return event;
	}
	public Event getHashedEvent(int eventId) {//hash on answers
		Event event = eventData.getEvent(eventId);
		List<Question> questions = questionData.getQuestion(eventId);
		for (int i = 0; i < questions.size(); i++) {
			String s = questions.get(i).getAnswer().toLowerCase();
			questions.get(i).setAnswer(HashService.getHash(s));
			s = questions.get(i).getActivationCode().toLowerCase();
			questions.get(i).setActivationCode(HashService.getHash(s));
		}
		event.setQuestions(questions);
		return event;
	}
	
	public List<User> getUser(int eventId) {
		return userData.getUser(eventId);
	}
	public List<Question> getQuestions() {
		return questionData.getQuestions();
	}
	public List<Question> getUnusedQuestions(int eventId) {
		return questionData.getUnusedQuestions(eventId);
	}
	
	public List<Place> getPlaces() {
		return placeData.getPlaces();
	}
	public Place getPlace(int id) {
		return placeData.getPlace(id);
	}
	public Status submitEvent(int eventId, String title, String date, int placeId, Boolean open) {
		Date eventDate = null;
		Status status = new Status();
		try {
			eventDate = (Date) dateFormat.parse(date);
			if (open == null) {
				open = false;
			}
			if (!whiteService.isValid(title)) {
				logger.info("title was invalid, "+title);
				status.setNotification("title had invalid characters");
				status.setError(true);
			}
			else if (eventId == -1) {//create new event
				eventId = eventData.createEvent(title, eventDate, placeId, open);
				status.setNotification("Event created!");
			}
			else {//update old event
				eventData.updateEvent(eventId, title, eventDate, placeId, open);
				status.setNotification("Event "+title+" updated!");
			}
		} catch (ParseException e) {
			logger.info("date was in wrong format, "+date);
			e.printStackTrace();
			status.setNotification("date was in wrong format");
			status.setError(true);
		}
		
		status.setId(eventId);
		return status;
	}
	public Status addPlace(String name) {
		Status status = new Status();
		if (whiteService.isValid(name)) {
			placeData.addPlace(name);
		}
		else {
			logger.info("name was invalid, "+name);
			status.setError(true);
			status.setNotification("Illegal characters in name");
		}
		return status;
	}
	public void removePlace(int id) {
		placeData.removePlace(id);
	}
	public Status editPlace(int id, String name) {
		Status status = new Status();
		if (whiteService.isValid(name)) {
			placeData.editPlace(id, name);
			status.setNotification("Place "+name+" edited");
		}
		else {
			logger.info("name was invalid, "+name);
			status.setNotification("Illegal characters in space name");
			status.setError(true);
		}
		return status;
	}
	public Status addQuestion(String question, String answer) {
		Status status = new Status();
		if (!whiteService.isValid(question)) {
			logger.info("question had invalid characters, "+question);
			status.setError(true);
			status.setNotification("Illegal characters in question");
		}
		else if (!whiteService.isValid(answer)) {
			logger.info("answer had invalid characters, "+answer);
			status.setError(true);
			status.setNotification("Illegal characters in answer");
		}
		else {
			questionData.addQuestion(question, answer);
		}
		return status;
	}
	public void removeQuestion(int id) {
		questionData.removeQuestion(id);
	}
	public Status editQuestion(int id, String question, String answer) {
		Status status = new Status();
		if (!whiteService.isValid(question)) {
			logger.info("question had invalid characters, "+question);
			status.setError(true);
			status.setNotification("Illegal characters in question");
		}
		else if (!whiteService.isValid(answer)) {
			logger.info("answer had invalid characters, "+answer);
			status.setError(true);
			status.setNotification("Illegal characters in question");
		}
		else {
			questionData.editQuestion(id, question, answer);
			status.setNotification("Question edited!");
		}
		return status;
	}
	public Status addEventQuestion(int eventId, int questionId, String description, String activationCode) {
		Status status = new Status();
		if (!whiteService.isValid(description)) {
			logger.info("question had invalid characters, "+description);
			status.setError(true);
			status.setNotification("Illegal characters in question");
			status.setId(eventId);
		}
		else if (!whiteService.isValid(activationCode)) {
			logger.info("answer had invalid characters, "+activationCode);
			status.setError(true);
			status.setNotification("Illegal characters in activation code");
			status.setId(eventId);
		}
		else {
			questionData.addEventQuestion(eventId, questionId, description, activationCode);
			status.setId(eventId);
		}
		return status;
	}
	public void removeEventQuestion(int eventId, int questionId) {
		questionData.removeEventQuestion(eventId, questionId);
	}
}
