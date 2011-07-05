package com.accenture.geoquiz.datasource;

import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accenture.geoquiz.datasource.mapper.EventRowMapper;
import com.accenture.geoquiz.model.Event;

@Repository
public class EventRepository {
	@Autowired
	private DataSource ds;
	
	private static final Logger logger = LoggerFactory.getLogger(EventRepository.class);
	
	public Event getEvent(int eventId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.queryForObject("SELECT * FROM event AS e, place AS p WHERE e.id=? AND e.place_id=p.id", new EventRowMapper(), eventId);
	}
	public List<Event> getEvents() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.query("SELECT * FROM event AS e, place AS p WHERE e.place_id=p.id", new EventRowMapper());
	}
	public List<Event> getOpenEvents() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.query("SELECT * FROM event AS e, place AS p WHERE e.place_id=p.id AND e.is_open='true'", new EventRowMapper());
	}
}
