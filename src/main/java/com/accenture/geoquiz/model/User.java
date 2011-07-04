package com.accenture.geoquiz.model;

public class User {
	
	private int eventId;
	private String nickname;
	private String email;
	private String phone;
	
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
	
	// legg til spørsmål som er besvart/ikke besvart
}
