package com.study.springboot.dto;

import lombok.Data;

@Data
public class AReviewListDto {
	private int r_index;
    private String r_name;
    private String m_number;
    private String c_id;
    private String nickname;
    private String tdate;
    private String filename;
    private String contents;
    private String orifilename;
    private String grade;
}
