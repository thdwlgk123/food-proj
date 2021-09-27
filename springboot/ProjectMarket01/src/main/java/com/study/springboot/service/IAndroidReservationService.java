package com.study.springboot.service;

import java.util.Map;

public interface IAndroidReservationService {
	public String getRsvMemInfo(String c_id);
	public int insertReservationInfo(Map<String, Object> map);
	public String getRsvInfo(String r_rsvnumber);
}
