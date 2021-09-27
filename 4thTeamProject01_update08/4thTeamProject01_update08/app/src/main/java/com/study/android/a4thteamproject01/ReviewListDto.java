package com.study.android.a4thteamproject01;

import com.study.android.a4thteamproject01.hyungdoo.Review;

public class ReviewListDto {
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

    public ReviewListDto(String nickname, String star, String image, String reviewcontext){
        this.nickname=nickname;
        this.grade=star;
        this.filename=image;
        this.contents=reviewcontext;
    }

    public ReviewListDto(String r_name, String grade, String filename, String contents, String tdate) {
        this.r_name=r_name;
        this.grade=grade;
        this.filename=filename;
        this.contents=contents;
        this.tdate=tdate;
    }

    public int getR_index() {
        return r_index;
    }

    public void setR_index(int r_index) {
        this.r_index = r_index;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getM_number() {
        return m_number;
    }

    public void setM_number(String m_number) {
        this.m_number = m_number;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getOrifilename() {
        return orifilename;
    }

    public void setOrifilename(String orifilename) {
        this.orifilename = orifilename;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
}
