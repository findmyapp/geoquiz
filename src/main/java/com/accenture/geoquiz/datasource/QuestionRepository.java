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
	public void addQuestion(String question, String answer) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		int id = 1234;//Maa ordne auto_increment
		jdbcTemplate.execute("INSERT into question values("+id+", '"+question+"', '"+answer+"')");
	}
	public void removeQuestion(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("DELETE from question where id = " + id);
	}
	public void addEventQuestion(int eventId, int questionId, String desc, String aCode) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("INSERT into event_question values("+questionId+", "+eventId+", '"+desc+"', '"+aCode+"')");
	}
	public void removeEventQuestion(int eventId, int questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("DELETE from event_question where event_id = "+eventId+" AND question_id = "+questionId);
	}
}
