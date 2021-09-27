package com.study.springboot.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;
@Data
public class NoticeListDto {
	private int bid;
	private String bname;
	@NotNull(message="title is null")
	@NotEmpty(message="title is null")
	private String btitle;
	@NotEmpty(message="content is null")
	@NotNull(message="content is null")
	private String bcontent;
	private Timestamp bdate;
	private String fileName;
	private int bhit;
	private int bgroup;
	private int bstep;
	private int bindent;
	private long bhour;
}
