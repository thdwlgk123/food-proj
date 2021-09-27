package com.study.springboot.dao;

import java.util.ArrayList;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ACustomerListDto;
import com.study.springboot.dto.AReservationDto;
import com.study.springboot.dto.ARestaurantListDto;
import com.study.springboot.dto.AReviewListDto;
import com.study.springboot.dto.ASirenDto;

@Mapper
public interface AMembershipDao {
	public int getJoinResult(Map<String, String> map);
	public int getLoginResult(Map<String, String> map);
	ArrayList<AReservationDto> getCustomerResList(String c_id);
	ArrayList<AReservationDto> getReservationList(String r_name);
	ArrayList<ACustomerListDto> getMyInfo(String c_id);
	ArrayList<ACustomerListDto> getMyProfile(String c_id);
	public int setUpdateMyProfile(Map<String, String> map);
	public int andCheckId(Map<String,String>map);
	public int andCheckNickName(Map<String,String>map);
	ArrayList<AReviewListDto> getMyReview(String c_id);
	ArrayList<ARestaurantListDto> getrestaurantInfoMg(String m_id);
	public int setStoreUpdate(Map<String, String> map);
	public int setUpdateNickname(String nickname, String c_id);
	ArrayList<ACustomerListDto> searchR_name(String c_id);
	ArrayList<ASirenDto> getCustomersirenList(String c_id);

}
