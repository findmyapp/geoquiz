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
		return jdbcTemplate.query("SELECT * FROM event_question AS e, question AS q WHERE e.question_id=q.id AND e.event_id=? ORDER BY e.post_num ASC", new QuestionRowMapper(true), eventId);
	}
	public List<Question> getQuestions() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.query("SELECT * FROM question", new QuestionRowMapper(false));
	}
	public List<Question> getUnusedQuestions(int eventId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.query("SELECT * FROM question WHERE id NOT IN (SELECT question_id FROM event_question WHERE event_id=?)", new QuestionRowMapper(false), eventId);
	}
	public void addQuestion(String question, String answer) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.update("INSERT into question (question, answer) values(?, ?)", question, answer);
		//jdbcTemplate.execute("INSERT into question (question, answer) values('"+question+"', '"+answer+"')");
	}
	public void removeQuestion(int id) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.update("DELETE from question WHERE id=?", id);
		//jdbcTemplate.execute("DELETE from question where id = " + id);
	}
	public void editQuestion(int id, String question, String answer) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.update("UPDATE question SET question=?, answer=? WHERE id=?", question, answer, id);
	}
	public void addEventQuestion(int eventId, int questionId, String desc, String aCode) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.update("INSERT into event_question values(?, ?, ?, ?, (SELECT COUNT(*) FROM event_question WHERE event_id = ?))", questionId, eventId, desc, aCode, eventId);
		//jdbcTemplate.execute("INSERT into event_question values("+questionId+", "+eventId+", '"+desc+"', '"+aCode+"')");
	}
	public void removeEventQuestion(int eventId, int questionId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.update("DELETE from event_question WHERE event_id=? AND question_id=?", eventId, questionId);
		//jdbcTemplate.execute("DELETE from event_question where event_id = "+eventId+" AND question_id = "+questionId);
	}
	public void swapQuestion(int eventId, int questionIdA, int questionIdB) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		int numA = jdbcTemplate.queryForInt("SELECT post_num from event_question WHERE event_id=? AND question_id=?", eventId, questionIdA);
		jdbcTemplate.update("UPDATE event_question SET post_num=(SELECT post_num FROM event_question WHERE event_id=? AND question_id=?) WHERE event_id=? AND question_id=?", eventId, questionIdB, eventId, questionIdA);
		jdbcTemplate.update("UPDATE event_question SET post_num=? WHERE event_id=? AND question_id=?", numA, eventId, questionIdB);
	}
}
