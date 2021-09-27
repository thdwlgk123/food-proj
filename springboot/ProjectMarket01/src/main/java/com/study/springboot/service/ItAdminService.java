package com.study.springboot.service;

import java.util.List;
import java.util.Map;

import com.study.springboot.dto.NoticeListDto;
import com.study.springboot.dto.SirenListDto;
import com.study.springboot.dto.TokenDto;
import com.study.springboot.dto.UserDto;
import com.study.springboot.dto.UserInfoDto;

public interface ItAdminService {
	public List<NoticeListDto> noticeList(int nPage);
	public List<NoticeListDto> noticeTitle();
	public List<TokenDto> tokenList();
	public List<NoticeListDto> content_view(String bId);
	public List<UserInfoDto> myPageInfo(String c_id);
	public int modifyContent_view(Map<String, String> map);
	public String noticeWrite(Map<String, String> map);
	public String getUserNickname(String userid);
	public int noticeListCount(); 
	////08/16 지하추가
	public List<SirenListDto> sirenList();
}
