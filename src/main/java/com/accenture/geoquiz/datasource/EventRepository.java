package com.accenture.geoquiz.datasource;

import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.hsqldb.types.Types;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlUpdate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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
	public void updateEvent(int eventId, String title, Date date, int placeId, Boolean open) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.update("UPDATE event SET title=?, event_date=?, place_id=?, is_open=? WHERE id=?", title, date, placeId, open, eventId);
	}
	/**
	 * Creates a new event and returns the eventId
	 */
	public int createEvent(String title, Date date, int placeId, Boolean open) {
		SqlUpdate su = new SqlUpdate();
		su.setDataSource(ds);
		su.setSql("INSERT into event (title, event_date, place_id, is_open) VALUES (?, ?, ?, ?)");
		su.setReturnGeneratedKeys(true);
		su.setTypes(new int[]{Types.VARCHAR, Types.DATE, Types.INTEGER, Types.BOOLEAN});
		su.compile();
		KeyHolder keyHolder = new GeneratedKeyHolder();
		su.update(new Object[]{title, date, placeId, open}, keyHolder);
		return keyHolder.getKey().intValue();//eventid
	}
}
