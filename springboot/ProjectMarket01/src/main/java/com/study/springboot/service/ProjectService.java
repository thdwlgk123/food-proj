package com.study.springboot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.springboot.dao.ItGoodsDao;
import com.study.springboot.dao.ItNoticeDao;
import com.study.springboot.dao.ItReplyDao;
import com.study.springboot.dao.ItUserDao;
import com.study.springboot.dto.GoodsDto;
import com.study.springboot.dto.NoticeDto;
import com.study.springboot.dto.NoticeListDto;
import com.study.springboot.dto.PageInfoDto;
import com.study.springboot.dto.ReplyDto;
import com.study.springboot.dto.UserDto;

@Service
public class ProjectService implements ItProjectService {
	
	@Autowired
	ItUserDao dao;
	
	@Autowired
	ItNoticeDao notice;
	
	@Autowired
	ItGoodsDao goods;
	
	@Autowired
	ItReplyDao reply;

//	@Override
//	public List<UserDto> list() {
//		return dao.list();
//	}
//
	@Override
	public UserDto userSelect(String c_id) {
		return dao.userSelect(c_id);
	}
	
	@Override
	public int join(Map<String, String> map) {
		
		int nResult = dao.join(map);
		return nResult;
	}
	
	public List<NoticeListDto> modifyContent_view(String bId){
		System.out.println("Service:  modifyContent_view");
		List<NoticeListDto> list=dao.modifyContent_view(bId);
		System.out.println(list.toString());
		return list;
	}
	
	@Override
	public int deleteNotice(String bId) {
		
		int nResult = dao.deleteNotice(bId);
		return nResult;
	}
	@Override
	public int updateInfo(Map<String, String> map) {
		
		int nResult = dao.updateInfo(map);
		return nResult;
	}
	
	@Override
	public int checkid(Map<String, String> map) {
		
		int nResult = dao.checkid(map);
		System.out.println(map);
		return nResult;
	}
	
	@Override
	public int checkNick(Map<String, String> map) {
		
		int nResult = dao.checkNick(map);
		return nResult;
	}
	@Override
	public String checkPwd(String c_id) {
		
		String nResult = dao.checkPwd(c_id);
		return nResult;
	}
	@Override
	public int insertPwd(String password, String c_id) {
		
		int nResult = dao.insertPwd(password, c_id);
		return nResult;
	}
	

	@Override
	public List<NoticeDto> noticeList(int curPage) {
		int listCount = 5;	// 한 페이지당 보여줄 게시물 수
		int nStart = (curPage -1) * listCount + 1;
		int nEnd = (curPage -1) * listCount + listCount;
		System.out.println("Service:  noticelist");
		List<NoticeDto> list=notice.noticeList(nStart, nEnd);
		return list;
	}
	
	@Override
	public List<NoticeDto> searchNoticeList(int curPage, String category, String content) {
		int listCount = 5;	// 한 페이지당 보여줄 게시물 수
		int nStart = (curPage -1) * listCount + 1;
		int nEnd = (curPage -1) * listCount + listCount;
		System.out.println("Service:  searchNoticeList");
		List<NoticeDto> list=notice.searchNoticeList(nStart, nEnd, category, content);
		System.out.println(list);
		return list;
	}
	
	@Override
	public List<NoticeDto> searchReject(String sPlace) {
		return notice.searchReject(sPlace);
	}
	
	@Override
	public List<NoticeDto> searchRequest(String sPlace) {
		return notice.searchRequest(sPlace);
	}
	
	@Override
	public List<PageInfoDto> noticeCount(int curPage) {
		int nTotalCount = notice.noticeCount();// 총 게시물 수 
		System.out.println("총 게시물 수  noticeCount : " + nTotalCount);
		int listCount = 5;	// 한 페이지당 보여줄 게시물 수
		int pageCount = 2; // 하다넹 보여줄 페이지 수
		
		int totalPage = nTotalCount / listCount;
		if(nTotalCount % listCount > 0)
			totalPage++;
		//현재 페이지
		int myCurPage = curPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		//시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		//끝 페이지
		int endPage = startPage + pageCount -1;
		if(endPage > totalPage)
			endPage = totalPage;
		List<PageInfoDto> listPage = new ArrayList<PageInfoDto>();
		PageInfoDto pageInfo = new PageInfoDto(nTotalCount,listCount,totalPage
								,curPage, pageCount, startPage, endPage);
		listPage.add(0, pageInfo);

		
		
	
		System.out.println("listPage : " + listPage);
		return listPage;
	}
	
	@Override
	public List<PageInfoDto> searchNoticeCount(int curPage, String category, String content) {
		
		int nTotalCount = notice.searchNoticeCount(category, content);// 총 게시물 수 
		System.out.println("총 게시물 수  : " + nTotalCount);
		int listCount = 5;	// 한 페이지당 보여줄 게시물 수
		int pageCount = 2; //   보여줄 페이지 수
		
		int totalPage = nTotalCount / listCount;
		if(nTotalCount % listCount > 0)
			totalPage++;
		//현재 페이지
		int myCurPage = curPage;
		if(myCurPage > totalPage)
			myCurPage = totalPage;
		if(myCurPage < 1)
			myCurPage = 1;
		
		//시작 페이지
		int startPage = ((myCurPage - 1) / pageCount) * pageCount + 1;
		
		//끝 페이지
		int endPage = startPage + pageCount -1;
		if(endPage > totalPage)
			endPage = totalPage;
		List<PageInfoDto> listPage = new ArrayList<PageInfoDto>();
		PageInfoDto pageInfo = new PageInfoDto(nTotalCount,listCount,totalPage
								,curPage, pageCount, startPage, endPage);
		listPage.add(0, pageInfo);

		
		
	
		System.out.println("listPage : " + listPage);
		return listPage;
	}
	
	@Override
	public int noticeTitleCount() {
		int nTotalCount = notice.noticeTitleCount();
		return nTotalCount;
	}
	
	@Override
	public NoticeDto noticeView(String c_id) {
		return notice.noticeView(c_id);
	}
	
	@Override
	public int goodsArticleCount() {
		return goods.goodsArticleCount();
	}
	
	@Override
	public List<GoodsDto> goodsList(Map<String, Integer> map) {
		return goods.goodsList(map);
	}
	
	@Override
	public int articleCount() {
		return goods.articleCount();
	}
	
	@Override
	public GoodsDto goodsView(String id) {
		return goods.goodsView(id);
	}
	
	@Override
	public int writeGoods(Map<String, String> map) {
		
		int nResult = goods.writeGoods(map);
		return nResult;
	}
	
	@Override
	public List<ReplyDto> replyView_N(String nId) {
		System.out.println(nId);
		return reply.replyView_N(nId);
	}
	
	@Override
	public int writeReply(Map<String, String> map) {
		
		int nResult = reply.writeReply(map);
		return nResult;
	}
	
	@Override
	public int deleteReply(String sid) {
		int nResult = reply.deleteReply(sid);
		return nResult;
	}
//	@Override
//	public int deleteUser(String id) {
//		int nResult = dao.deleteUser(id);
//		return nResult;
//	}
//
//	@Override
//	public int count() {
//		int nTotalCount = dao.articleCount();
//		return nTotalCount;
//	}



}
