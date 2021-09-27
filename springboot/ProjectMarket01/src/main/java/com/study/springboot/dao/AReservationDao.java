package com.study.springboot.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ACustomerListDto;
import com.study.springboot.dto.AReservationDto;
@Mapper
public interface AReservationDao {
	public int insertRsvDao(Map<String, Object> map);
	ArrayList<ACustomerListDto> getRsvMemInfoDao(String c_id);
	ArrayList<AReservationDto> getRsvInfoDao(String r_rsvnumber);
}
