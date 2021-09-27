package com.study.springboot.dto;

import lombok.Data;

@Data
public class AReservationDto {
	private int r_index;			// 예약 시퀀스 넘버
	private String r_rsvnumber;		// 예약고유번호
	private String m_number;		// 음식점 고유번호
	private String r_name;			// 음식점 이름
	private String c_id;			// 예약한 회원 아이디
	private String nickname;		// 예약한 회원 닉네임
	private String c_phone;			// 예약한 회원 전화번호
	private int b_party;			// 예약 인원
	private int condition_check;	// 예약 상태
	private String reject;			// 취소 사유
	private int res_payment;		// 결제 상태
	private String request;			// 고객 요청사항
	private String tdate;			// 예약일
	private String time;			// 예약 시간
}
