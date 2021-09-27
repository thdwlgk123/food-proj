package com.study.springboot.service;

import java.util.List;
import java.util.Map;

import com.study.springboot.dto.GoodsDto;
import com.study.springboot.dto.NoticeDto;
import com.study.springboot.dto.NoticeListDto;
import com.study.springboot.dto.PageInfoDto;
import com.study.springboot.dto.ReplyDto;
import com.study.springboot.dto.UserDto;

public interface ItProjectService {

//	public List<UserDto> list();
	public int join(Map<String, String> map);
	public int deleteNotice(String bId);
	public int updateInfo(Map<String, String> map);
	public int checkid(Map<String, String> map);
	public int checkNick(Map<String, String> map);
	public String checkPwd(String c_id);
	public int insertPwd(String password, String c_id);
	public UserDto userSelect(String c_id);
	public List<NoticeDto> noticeList(int curPage);
	public List<NoticeDto> searchNoticeList(int curPage, String category, String content);
	public List<PageInfoDto> noticeCount(int curPage);
	public List<PageInfoDto> searchNoticeCount(int curPage, String category, String content);
	public int noticeTitleCount();
	public NoticeDto noticeView(String c_id);
	public int goodsArticleCount();
	public List<GoodsDto> goodsList(Map<String, Integer> map);
	public int articleCount();
	public GoodsDto goodsView(String id);
	public int writeGoods(Map<String, String> map);
	public List<ReplyDto> replyView_N(String nId);
	public int writeReply(Map<String, String> map);
	public List<NoticeDto> searchReject(String sPlace);
	public List<NoticeDto> searchRequest(String sPlace);
	public int deleteReply(String sid);
//	public int delete(@Param("_id")String id);
//	public int count();

}
