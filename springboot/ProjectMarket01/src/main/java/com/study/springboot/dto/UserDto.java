package com.study.springboot.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {
	private int number;
	@NotNull(message="c_name is null.")
	@NotEmpty(message="c_name is empty.")
	@Size(min=2, max=15, message="c_name min=2, max=15.")
	private String c_name;
	@NotNull(message="c_id is null.")
	@NotEmpty(message="c_id is empty.")
	@Size(min=6, max=15, message="c_id min=6, max=15.")
	private String c_id;
	@NotNull(message="c_pw is null.")
	@NotEmpty(message="c_pw is empty.")
	@Size(min=6, max=15, message="c_pw min=8, max=15.")
	private String c_pw;
	@NotNull(message="c_phone is null.")
	@NotEmpty(message="c_phone is empty.")
	private String c_phone;
	@NotNull(message="c_email_id is null.")
	@NotEmpty(message="c_email_id is empty.")
	private String c_email_id;
	@NotNull(message="c_email_url is null.")
	@NotEmpty(message="c_email_url is empty.")
	private String c_email_url;
//	private String eMail = eMail_id+"@"+eMail_url;
	private String c_email;
	@NotNull(message="nickname is null.")
	@NotEmpty(message="nickname is empty.")
	@Size(min=2, max=15, message="nickname min=2, max=15.")
	private String nickname;
	private Timestamp tdate;
	private String authority;
	private int enabled;
}
