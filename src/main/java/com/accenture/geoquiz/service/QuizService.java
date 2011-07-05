package com.accenture.geoquiz.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
	
	public List<Place> getPlaces() {
		return placeData.getPlaces();
	}
	public Place getPlace(int id) {
		return placeData.getPlace(id);
	}
	public int submitEvent(int eventId, String title, Timestamp date, int placeId, Boolean open) {
		Event e = new Event();
		e.setId(eventId);
		e.setTitle(title);
		e.setEventDate(date);
		Place p = new Place();
		p.setId(placeId);
		e.setPlace(p);
		if (open != null) {
			e.setOpen(open);
		} else {
			e.setOpen(false);
		}
		if (eventId == -1) {
			//create new event
		}
		else {
			//update old event
		}
		return eventId;
	}
	public void addPlace(String name) {
		Place p = new Place();
		p.setName(name);
		p.setId(1234); //Need dynamic id
		placeData.addPlace(p);
	}
	public void removePlace(int id) {
		//Place p = new Place();
		//p.setId(id);
		placeData.removePlace(id);
	}
	public void addQuestion(String question, String answer) {
		//add
		questionData.addQuestion(question, answer);
	}
	public void removeQuestion(int id) {
		//remove
		questionData.removeQuestion(id);
	}
}
