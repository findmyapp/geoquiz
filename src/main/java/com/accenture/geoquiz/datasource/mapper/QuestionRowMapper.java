package com.accenture.geoquiz.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.accenture.geoquiz.model.Question;

public class QuestionRowMapper implements RowMapper<Question> {
	private boolean linkedToEvent;
	public QuestionRowMapper(boolean linkedToEvent) {
		this.linkedToEvent = linkedToEvent;
	}
	
	public Question mapRow(ResultSet rs, int arg1) throws SQLException {
		Question question = new Question();
		question.setId(rs.getInt("id"));
		if (linkedToEvent) {
			question.setPostDescription(rs.getString("description"));
			question.setActivationCode(rs.getString("activation_code"));
		}
		question.setQuestion(rs.getString("question"));
		question.setAnswer(rs.getString("answer"));
		//setNum?
		return question;
	}
}
