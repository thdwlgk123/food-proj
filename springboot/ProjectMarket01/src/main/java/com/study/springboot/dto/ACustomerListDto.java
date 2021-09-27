package com.study.springboot.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class ACustomerListDto {
	private int c_index;			// 회원번호
	private String c_name;			// 이름
	private String c_id;			// 아이디
	private String c_pw;			// 패스워드
	private String c_phone;			// 전화번호
	private String c_eMail;			// 이메일
	private String marketcheck;		// 개인정보 동의
	private String sns_id;			// SNS ID(e_mail)
	private String nickname;		// 닉네임(별명)
	private String black;			// 정지 여부
	private String userprofile;		// 프로필사진(URL)
	private Timestamp tdata;		// 회원가입일
	private String authority;		// 권한
	private String r_name;		// 권한
	private int enabled;			// 활성상태
}
