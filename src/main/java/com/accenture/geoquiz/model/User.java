package com.accenture.geoquiz.model;

public class User {
	
	private int id;
	private String nickname;
	private String email;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
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
	
	// legg til spørsmål som er besvart/ikke besvart
}
