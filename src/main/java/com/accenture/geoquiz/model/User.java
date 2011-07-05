package com.accenture.geoquiz.model;

import java.sql.Timestamp;

public class User {
	
	private int eventId;
	private String nickname;
	private String email;
	private String phone;
	private int answered;
	private Timestamp finishTime;
	
	public void setEventId(int id) {
		this.eventId = id;
	}
	public int getEventId() {
		return eventId;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getNickname() {
		return nickname;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setAnswered(int n) {
		answered = n;
	}
	public int getAnswered() {
		return answered;
	}
	public void setFinishTime(Timestamp stamp) {
		finishTime = stamp;
	}
	public Timestamp getFinishTime() {
		return finishTime;
	}
}
