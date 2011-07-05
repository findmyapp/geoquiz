package com.accenture.geoquiz.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.accenture.geoquiz.model.Place;

public class PlaceRowMapper implements RowMapper<Place> {
	public Place mapRow(ResultSet rs, int arg1) throws SQLException {
		Place p = new Place();
		p.setId(rs.getInt("id"));
		p.setName(rs.getString("name"));
		return p;
	}
}
