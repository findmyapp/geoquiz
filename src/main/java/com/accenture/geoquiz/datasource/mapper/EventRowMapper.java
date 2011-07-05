package com.accenture.geoquiz.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.accenture.geoquiz.model.Event;
import com.accenture.geoquiz.model.Place;

public class EventRowMapper implements RowMapper<Event> {
	public Event mapRow(ResultSet rs, int arg1) throws SQLException {
		Event event = new Event();
		event.setId(rs.getInt("id"));
		event.setTitle(rs.getString("title"));
		event.setEventDate(rs.getTimestamp("event_date"));
		Place place = new Place();
		place.setId(rs.getInt("place_id"));
		place.setName(rs.getString("name"));
		event.setPlace(place);
		return event;
	}
}
