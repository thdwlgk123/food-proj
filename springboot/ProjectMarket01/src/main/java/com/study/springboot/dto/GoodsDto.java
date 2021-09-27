package com.study.springboot.dto;

import java.sql.Timestamp;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class GoodsDto {
	private int id;
	@NotNull(message="writer is null.")
	@NotEmpty(message="writer is empty.")
	private String writer;
	@NotNull(message="title is null.")
	@NotEmpty(message="title is empty.")
	private String title;
	@NotNull(message="price is null.")
	@NotEmpty(message="price is empty.")
	private String price;
	@NotNull(message="content is null.")
	@NotEmpty(message="content is empty.")
	@Size(max=4000, message="content text max=4000, 한글 1300자")
	private String content;
	private Timestamp createdate;
	private int hit;
}