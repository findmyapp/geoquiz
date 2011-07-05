package com.accenture.geoquiz.datasource.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.accenture.geoquiz.model.User;


public class UserRowMapper implements RowMapper<User>{
	public User mapRow(ResultSet rs, int arg1) throws SQLException {
		User user = new User();
		user.setEventId(rs.getInt("event_id"));
		user.setEmail(rs.getString("email"));
		user.setNickname(rs.getString("nickname"));
		user.setPhone(rs.getString("phone"));
		user.setAnswered(rs.getInt("answered"));
		user.setFinishTime(rs.getTimestamp("finish_time"));
		return user;
	}
}
