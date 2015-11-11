package com.idy.domain;

import lombok.Data;

import org.springframework.stereotype.Repository;

@Repository
public @Data class WeatherInfo {

	private String city;
	
	private String cityid;
	
	private String temp;
	
	private String temp1;
	
	private String temp2;
	
	private String weather;
	
	private String img1;
	
	private String img2;
	
	private String ptime;
	
	private String WD;
	
	private String WS;
	
	private String WSE;
	
	private String SD;
	
	private String time;
	
	private String isRadar;
	
	private String Radar;
	
	private String njd;
	
	private String qy;
}
