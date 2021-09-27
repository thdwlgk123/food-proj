package com.study.springboot.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.springboot.dao.ItNoticeDao;
import com.study.springboot.dao.ItUserDao;
import com.study.springboot.dto.NoticeListDto;
import com.study.springboot.dto.SirenListDto;
import com.study.springboot.dto.TokenDto;
import com.study.springboot.dto.UserDto;
import com.study.springboot.dto.UserInfoDto;

@Service
public class AdminService implements ItAdminService{
	@Autowired
	ItNoticeDao notice;
	@Autowired
	ItUserDao user;
	
	public List<NoticeListDto> noticeList(int nPage){
		int listCount = 5;	// 한 페이지당 보여줄 게시물 수
		int nStart = (nPage -1) * listCount + 1;
		int nEnd = (nPage -1) * listCount + listCount;
		System.out.println("Service:  noticelist");
		List<NoticeListDto> list=notice.noticelistDao(nStart, nEnd);

		return list;
	}
	public List<NoticeListDto> noticeTitle(){
		System.out.println("Service:  noticeTitle");
		List<NoticeListDto> list=notice.noticeTitle();
		System.out.println(list.toString());
		return list;
	}
	public List<UserInfoDto> myPageInfo(String c_id){
		System.out.println("Service:  myPageInfo");
		System.out.println("Service:  " + c_id);
		List<UserInfoDto> list=user.myPageInfo(c_id);
		System.out.println(list.toString());
		return list;
	}
	
	public List<TokenDto> tokenList(){
		System.out.println("Service:  TokenList");
		List<TokenDto> list=notice.tokenList();
		return list;
	}
	public List<NoticeListDto> content_view(String bId){
		System.out.println("Service:  content_view");
		List<NoticeListDto> list=notice.content_view(bId);
		int hit = list.get(0).getBhit() + 1;
		notice.noticeHit(String.valueOf(hit),String.valueOf(list.get(0).getBid()));
		System.out.println(list.toString());
		return list;
	}
	public int noticeListCount() {
		System.out.println("service: noticelistcount");
		return notice.noticeListCount();
	}
	//공지사항 입력
	@Override
	public String noticeWrite(Map<String, String> map) {
		
		int nResult = notice.noticeWrite(map);
		System.out.println("noticeWrite : " + nResult);
		
		String json = "";
		return json;
	}
	@Override
	public int modifyContent_view(Map<String, String> map) {
		
		int nResult = notice.modifyContent_view(map);
		System.out.println("noticeWrite : " + nResult);
		
		return nResult;
	}
	//관리자 닉네임 가져오기
	public String getUserNickname(String userid) {
		System.out.println("service : getUserNickname, "+userid);
		String nickname=user.getNickname(userid);
		System.out.println("get nickname: "+nickname);
		return nickname;
	}
	
	//사이렌리스트 가져오기
	public List<SirenListDto> sirenList(){
		System.out.println("Serivce: sirenLIst");
		List<SirenListDto> sirenlist=notice.sirenlistDao();
		System.out.println("sirenlist: "+sirenlist);
		return sirenlist;
	}
}
