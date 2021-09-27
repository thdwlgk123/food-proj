package com.study.springboot.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ReplyDto {
	private int id;
	@NotNull(message="userId is null.")
	@NotEmpty(message="userId is empty.")
	private String userId;
	@NotNull(message="writer is null.")
	@NotEmpty(message="writer is empty.")
	private String writer;
	private String viewPart_id;
	@NotNull(message="reply_content is null.")
	@NotEmpty(message="reply_content is empty.")
	private String reply_content;
	private Timestamp createdate;
	private Timestamp modifydate;
}
