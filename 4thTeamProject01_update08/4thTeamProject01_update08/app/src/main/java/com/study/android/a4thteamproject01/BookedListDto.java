package com.study.android.a4thteamproject01;

public class BookedListDto {
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

    public int getR_index() {
        return r_index;
    }

    public void setR_index(int r_index) {
        this.r_index = r_index;
    }

    public String getR_rsvnumber() {
        return r_rsvnumber;
    }

    public void setR_rsvnumber(String r_rsvnumber) {
        this.r_rsvnumber = r_rsvnumber;
    }

    public String getM_number() {
        return m_number;
    }

    public void setM_number(String m_number) {
        this.m_number = m_number;
    }

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
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

    public String getC_phone() {
        return c_phone;
    }

    public void setC_phone(String c_phone) {
        this.c_phone = c_phone;
    }

    public int getB_party() {
        return b_party;
    }

    public void setB_party(int b_party) {
        this.b_party = b_party;
    }

    public int getCondition_check() {
        return condition_check;
    }

    public void setCondition_check(int condition_check) {
        this.condition_check = condition_check;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public int getRes_payment() {
        return res_payment;
    }

    public void setRes_payment(int res_payment) {
        this.res_payment = res_payment;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
