package com.accenture.geoquiz.service.dateformat;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DateFormatCreator {
	
	@Bean(autowire = Autowire.BY_TYPE)
	public DateFormat createDateFormat() {
		return new SimpleDateFormat("yyyy-MM-dd");
	}
}
