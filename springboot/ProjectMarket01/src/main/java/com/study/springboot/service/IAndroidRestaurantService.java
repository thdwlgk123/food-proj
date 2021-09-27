package com.study.springboot.service;

import java.util.Map;

public interface IAndroidRestaurantService {
	public String getlikeList();
	public String getrecentList();
	public String checkReservation(String r_rsvnumber);
	public String noCheckReservation(String r_rsvnumber);
	public String getsearchList(String searchword);
	public String getMenuList(String mnumber, String storename);
	public String getRestaurantInfo(String mnumber, String storename);
	public int checkLike(String m_number, String r_name, String c_id);
	public int doLike(String m_number, String r_name, String c_id);
	public int undoLike(String m_number, String r_name, String c_id);
	public String getLikeList(String c_id);
//	=====주말지하
	public String getReviewList(String m_number, String r_name);
	
//	======호범
	public int getJoinResult(Map<String, String> map);
	public int getLoginResult(Map<String, String> map);
	public int andCheckId(Map<String, String>map);
	public int andCheckNickName(Map<String, String>map);
	public String getCustomerResList(String c_id);
	public String getReservationList(String c_id);
	public String getMyInfo(String c_id);
	public String getMyProfile(String c_id);
	public String getModifyData(String c_id);
	public int setUpdateMyProfile(Map<String, String> map);
	public String getMyReview(String c_id);
	public String getRestaurantInfoMg(String m_id);
	public int setStoreUpdate(Map<String, String> map);
	public int setUpdateNickname(String nickname, String c_id);
	
	public String getCustomerSiren(String c_id);

}
