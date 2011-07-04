package com.accenture.geoquiz.model;

public class Question {
	
	private int id;
	private String question;
	private String answer;
	private String postDescription;
	private String activationCode;
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getQuestion() {
		return question;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getAnswer() {
		return answer;
	}
	public void setPostDescription(String postDescription) {
		this.postDescription = postDescription;
	}
	public String getPostDescription() {
		return postDescription;
	}
	public void setActivationCode(String activationCode) {
		this.activationCode = activationCode;
	}
	public String getActivationCode() {
		return activationCode;
	}
}
