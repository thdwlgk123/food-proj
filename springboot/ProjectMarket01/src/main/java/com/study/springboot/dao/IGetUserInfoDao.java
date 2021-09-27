package com.study.springboot.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.study.springboot.dto.TestBoardDto;
import com.study.springboot.dto.UserInfoDto;
@Mapper
public interface IGetUserInfoDao {
	public ArrayList<UserInfoDto> getUserinfo(String c_id);
}
