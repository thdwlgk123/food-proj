package com.study.springboot.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.GoodsDto;

@Mapper
public interface ItGoodsDao {
	public int goodsArticleCount();
	public List<GoodsDto> goodsList(Map<String, Integer> map);
	public int articleCount();
	public GoodsDto goodsView(String id);
	public int writeGoods(Map<String, String> map);
}
