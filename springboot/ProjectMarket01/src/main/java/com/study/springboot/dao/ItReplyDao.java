package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.ReplyDto;

@Mapper
public interface ItReplyDao {
	public List<ReplyDto> replyView_N(String nId);
	public int writeReply(Map<String, String> map);
	public int deleteReply(String sid);
}
