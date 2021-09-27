package com.study.springboot.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Data
@Alias("notice")
public class NoticeDto {
	private int number;
	private String r_rsvnumber;
	private String m_number;
	@NotNull(message="r_name is null.")
	@NotEmpty(message="r_name is empty.")
	private String r_name;
	@NotNull(message="c_id is null.")
	@NotEmpty(message="c_id is empty.")
	@Size(min=6, max=15, message="c_id min=6, max=15.")
	private String c_id;
	@NotNull(message="nickname is null.")
	@NotEmpty(message="nickname is empty.")
	@Size(min=2, max=15, message="nickname min=2, max=15.")
	private String nickname;
	@NotNull(message="c_phone is null.")
	@NotEmpty(message="c_phone is empty.")
	private String c_phone;
	private int b_party;
	private int condition_check;
	private String reject;
	private int res_payment;
	private String request;
	private String tdate;
	private String time;
}
