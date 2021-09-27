package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.NoticeListDto;
import com.study.springboot.dto.UserDto;
import com.study.springboot.dto.UserInfoDto;

@Mapper
public interface ItUserDao {
//	public List<UserDto> userList();
	public UserDto userSelect(String c_id);
	public int join(Map<String, String> map);
	public int deleteNotice(String bId);
	public List<NoticeListDto> modifyContent_view(String bId);
	public int updateInfo(Map<String, String> map);
	public int checkid(Map<String, String> map);
	public int checkNick(Map<String, String> map);
	public String checkPwd(String c_id);
	public int insertPwd(String password, String c_id);
	public List<UserInfoDto> myPageInfo(String c_id);
	public String getNickname(String userid);
}
