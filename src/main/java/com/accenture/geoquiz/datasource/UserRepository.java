package com.accenture.geoquiz.datasource;

import java.sql.Timestamp;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accenture.geoquiz.datasource.mapper.UserRowMapper;
import com.accenture.geoquiz.model.User;

@Repository
public class UserRepository {
	@Autowired
	private DataSource ds;
	
	private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	public User getUser(String email, int eventId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.queryForObject("SELECT * FROM event_user WHERE event_id=? AND email=?", new UserRowMapper(), eventId, email);
	}
	
	public List<User> getUser(int eventId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.query("SELECT * FROM event_user WHERE event_id=? ORDER BY answered, finish_time", new UserRowMapper(), eventId);
	}
	public void createUser(int eventId, String email, String nick, String phone, int answered, Timestamp finishTime) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.update("INSERT into event_user values(?, ?, ?, ?, ?, ?)", new UserRowMapper(), eventId, email, nick, phone, answered, finishTime);
	}
	public void updateAnswered(int eventId, String email, int answered, Timestamp finishTime) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.update("UPDATE event_user SET answered=?, finish_time=? WHERE event_id=? AND email=?", answered, finishTime, eventId, email);
	}
}
