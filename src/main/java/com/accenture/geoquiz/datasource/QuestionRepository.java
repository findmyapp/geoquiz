package com.accenture.geoquiz.datasource;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accenture.geoquiz.datasource.mapper.QuestionRowMapper;
import com.accenture.geoquiz.model.Question;

@Repository
public class QuestionRepository {
	@Autowired
	private DataSource ds;
	
	private static final Logger logger = LoggerFactory.getLogger(QuestionRepository.class);
	
	public Question getQuestion(int questionId, int eventId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.queryForObject("SELECT * FROM event_question AS e, question AS q WHERE e.question_id=q.id AND e.question_id=? AND e.event_id=?", new QuestionRowMapper(true), questionId, eventId);
	}
	public List<Question> getQuestion(int eventId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.query("SELECT * FROM event_question AS e, question AS q WHERE e.question_id=q.id AND e.event_id=?", new QuestionRowMapper(true), eventId);
	}
	public List<Question> getQuestions() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.query("SELECT * FROM question", new QuestionRowMapper(false));
	}
}
