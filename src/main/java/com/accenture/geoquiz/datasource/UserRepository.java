package com.accenture.geoquiz.datasource;

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
		return jdbcTemplate.query("SELECT * FROM event_user WHERE event_id=?", new UserRowMapper(), eventId);
	}
	
	public void createUser(User user) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("INSERT into event_user values("+user.getEventId()+", '"+user.getEmail()+"', '"+user.getNickname()+"', '"+user.getPhone()+"', "+user.getAnswered()+", '"+user.getFinishTime()+"')");
	}
	public void updateUser(User user) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("");//update...
	}
}
