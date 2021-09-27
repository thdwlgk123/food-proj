package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.NoticeDto;
import com.study.springboot.dto.NoticeListDto;
import com.study.springboot.dto.SirenListDto;
import com.study.springboot.dto.TokenDto;
import com.study.springboot.dto.UserInfoDto;

@Mapper
public interface ItNoticeDao {
	public List<NoticeDto> noticeList (int nStart, int nEnd);
	public List<NoticeDto> searchNoticeList (int nStart, int nEnd, String category, String content);
	public List<NoticeListDto> noticeTitle();
	public List<NoticeListDto> content_view(String bId);
	public List<NoticeDto> searchReject(String sPlace);
	public List<NoticeDto> searchRequest(String sPlace);
	public List<TokenDto> tokenList();
	public int noticeHit(String bhit, String bId);
	public int noticeCount();
	public int searchNoticeCount(String category, String content);
	public int bookedCount();
	public int noticeTitleCount();
	public NoticeDto noticeView(String c_id);
	public  int modifyContent_view(Map<String, String> map);
	//지하
	public List<NoticeListDto> noticelistDao(int nStart, int nEnd);
	public int noticeWrite(Map<String, String> map);
	public int noticeListCount();
	public List<SirenListDto> sirenlistDao();
}
