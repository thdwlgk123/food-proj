package com.study.springboot.dto;

import lombok.Data;

@Data
public class PageInfoDto {
	private int totalCount; //총 게시물 갯수
	private int listCount; //페이지당 보여줄 게시물 갯수
	private int totalPage; //총 페이지
	private int curPage; //현재 페이지
	private int pageCount; //하단에 보여줄 페이지 리스트 갯수
	private int startPage; //시작 페이지
	private int endPage; //끝 페이지
	public PageInfoDto(int totalCount, int listCount, int totalPage, int curPage, int pageCount, int startPage,
			int endPage) {
		super();
		this.totalCount = totalCount;
		this.listCount = listCount;
		this.totalPage = totalPage;
		this.curPage = curPage;
		this.pageCount = pageCount;
		this.startPage = startPage;
		this.endPage = endPage;
	}
	
}

