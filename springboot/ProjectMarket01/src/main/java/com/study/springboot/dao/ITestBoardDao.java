package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.TestBoardDto;
@Mapper
public interface ITestBoardDao {
	public ArrayList<TestBoardDto> testSelect(String latitude ,String longitude);
	public Integer sirenOrder(String m_number ,String r_name, String c_id,
							String c_name, String c_phone, String res_payment, String r_menu,
							String userRequest,String r_rsvnum);
	public Integer review(String m_number ,String r_name, String c_id,
			String contents,String nickname, String grade, String file, String oriFile);
	public Integer checkReservation(String r_rsvnumber);
}
