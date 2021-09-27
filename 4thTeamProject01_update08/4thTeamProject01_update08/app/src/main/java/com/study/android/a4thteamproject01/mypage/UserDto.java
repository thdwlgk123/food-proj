package com.study.android.a4thteamproject01.mypage;

import java.sql.Timestamp;

public class UserDto {
    private int c_index;
    private String c_name;
    private String c_id;
    private String c_pw;
    private String c_phone;
    private String c_email;
    private String marketcheck;
    private String auth;
    private String sns_id;
    private String token;
    private String nickname;
    private String black;
    private Timestamp tdate;
    private String userProfile;

    public UserDto(int c_index, String c_name, String c_id, String c_pw,
                   String c_phone, String c_email, String marketcheck, String auth,
                   String sns_id, String token, String nickname, String black,
                   Timestamp tdate, String userProfile) {
        this.c_index = c_index;
        this.c_name = c_name;
        this.c_id = c_id;
        this.c_pw = c_pw;
        this.c_phone = c_phone;
        this.c_email = c_email;
        this.marketcheck = marketcheck;
        this.auth = auth;
        this.sns_id = sns_id;
        this.token = token;
        this.nickname = nickname;
        this.black = black;
        this.tdate = tdate;
        this.userProfile = userProfile;
    }

    public UserDto(String nickname, String userProfile) {
        this.nickname = nickname;
        this.userProfile = userProfile;
    }

    public UserDto(String c_name, String c_id, String c_phone, String c_email) {
        this.c_name = c_name;
        this.c_id = c_id;
        this.c_phone = c_phone;
        this.c_email = c_email;
        this.nickname = nickname;
    }

    public String getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(String userProfile) {
        this.userProfile = userProfile;
    }

    public int getC_index() {
        return c_index;
    }

    public void setC_index(int r_index) {
        this.c_index = r_index;
    }

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public String getC_id() {
        return c_id;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public String getC_pw() {
        return c_pw;
    }

    public void setC_pw(String c_pw) {
        this.c_pw = c_pw;
    }

    public String getC_phone() {
        return c_phone;
    }

    public void setC_phone(String c_phone) {
        this.c_phone = c_phone;
    }

    public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_eMail) {
        this.c_email = c_email;
    }

    public String getMarketcheck() {
        return marketcheck;
    }

    public void setMarketcheck(String marketcheck) {
        this.marketcheck = marketcheck;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getSns_id() {
        return sns_id;
    }

    public void setSns_id(String sns_id) {
        this.sns_id = sns_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getBlack() {
        return black;
    }

    public void setBlack(String black) {
        this.black = black;
    }

    public Timestamp getTdate() {
        return tdate;
    }

    public void setTdate(Timestamp tdate) {
        this.tdate = tdate;
    }

    @Override
    public String toString() {
        return "PostResult{" +
                "r_index=" + c_index +
                ", c_name=" + c_name +
                ", c_id=" + c_id +
                ", c_pw=" + c_pw +
                ", c_phone=" + c_phone +
                ", c_eMail=" + c_email +
                ", marketcheck=" + marketcheck +
                ", auth=" + auth +
                ", sns_id=" + sns_id +
                ", token=" + token +
                ", nickname=" + nickname +
                ", black=" + black +
                ", tdate=" + tdate + "}";
    }

}
