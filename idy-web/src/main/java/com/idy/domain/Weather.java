package com.idy.domain;

import lombok.Data;

import org.springframework.stereotype.Repository;

@Repository
public @Data class Weather {

	private WeatherInfo weatherInfo;
	
}
