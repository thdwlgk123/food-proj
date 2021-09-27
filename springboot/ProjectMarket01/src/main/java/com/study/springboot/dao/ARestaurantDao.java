package com.study.springboot.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.AGoodButtonDto;
import com.study.springboot.dto.ARestaurantListDto;
import com.study.springboot.dto.AReviewDto;

@Mapper
public interface ARestaurantDao {
	ArrayList<ARestaurantListDto> likeSelect();
	ArrayList<ARestaurantListDto> timeorderSelect();
	ArrayList<ARestaurantListDto> searchSelect(String searchword);
	int noCheckReservation(String r_rsvnumber);
	int checkReservation(String r_rsvnumber);
	ArrayList<ARestaurantListDto> getrestaurantInfo(String mnumber, String storename);
	public String getMenuListDao(String mnumber, String storename);
	public int checkLikeDao(String c_id, String r_name, String m_number);
	public int doLikeDao(String c_id, String r_name, String m_number);
	public int undoLikeDao(String c_id, String r_name, String m_number);
	ArrayList<AGoodButtonDto> getLikeListDao(String c_id);
	ARestaurantListDto selectLikeListDao(String m_number, String r_name);
//	===========주말지하
	ArrayList<AReviewDto> getReviewListDao(String m_number, String r_name);
	public int countReviewDao(String m_number, String r_name);
	public List<String> getReviewGrade(String m_number, String r_name);

}
