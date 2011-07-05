package com.accenture.geoquiz.datasource;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.accenture.geoquiz.datasource.mapper.PlaceRowMapper;
import com.accenture.geoquiz.model.Place;

@Repository
public class PlaceRepository {
	@Autowired
	private DataSource ds;
	
	public List<Place> getPlaces() {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.query("SELECT * FROM place", new PlaceRowMapper());
	}
	public Place getPlace(int placeId) {
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		return jdbcTemplate.queryForObject("SELECT * FROM place WHERE id=?", new PlaceRowMapper(), placeId);
	}
	public void addPlace(Place p){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("INSERT into place values("+p.getId()+", '"+p.getName()+"')");
	}
	public void removePlace(int id){
		JdbcTemplate jdbcTemplate = new JdbcTemplate(ds);
		jdbcTemplate.execute("DELETE from place where id = " + id);
	}
	
	
}

