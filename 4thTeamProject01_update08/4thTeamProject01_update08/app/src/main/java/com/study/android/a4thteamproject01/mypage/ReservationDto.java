package com.study.android.a4thteamproject01.mypage;

public class ReservationDto {
    private int r_index;
    private String m_number;
    private String r_rsvnumber;
    private String r_name;
    private String c_id;
    private String nickname;
    private String c_phone;
    private int b_party;
    private int condition_check;
    private String reject;
    private int res_payment;
    private String request;
    private String tdate;
    private String time;

    public ReservationDto(String m_number, String r_rsvnumber, String r_name, String c_phone, int b_party, int condition_check, String reject, String request, String tdate, String time) {
        this.m_number = m_number;
        this.r_rsvnumber = r_rsvnumber;
        this.r_name = r_name;
        this.c_phone = c_phone;
        this.b_party = b_party;
        this.condition_check = condition_check;
        this.reject = reject;
        this.request = request;
        this.tdate = tdate;
        this.time = time;
    }

    public ReservationDto(String r_rsvnumber, String r_name, int b_party, int condition_check, String request, String reject, String tdate, String time) {
        this.r_rsvnumber = r_rsvnumber;
        this.r_name = r_name;
        this.b_party = b_party;
        this.condition_check = condition_check;
        this.reject = reject;
        this.request = request;
        this.tdate = tdate;
        this.time = time;
    }

    public ReservationDto(String r_rsvnumber, String r_name, String m_number, String nickname, int b_party, int condition_check, String request, String tdate, String time) {
        this.r_rsvnumber = r_rsvnumber;
        this.r_name = r_name;
        this.m_number = m_number;
        this.nickname = nickname;
        this.b_party = b_party;
        this.condition_check = condition_check;
        this.request = request;
        this.tdate = tdate;
        this.time = time;
    }


    public int getR_index() {
        return r_index;
    }

    public void setR_index(int r_index) {
        this.r_index = r_index;
    }

    public String getM_number() {
        return m_number;
    }

    public void setM_number(String m_number) {
        this.m_number = m_number;
    }

    public String getR_rsvnumber() {
        return r_rsvnumber;
    }

    public void setR_rsvnumber(String r_rsvnumber) {
        this.r_rsvnumber = r_rsvnumber;
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

    @Override
    public String toString() {
        return "ReservationDto{" +
                "r_index=" + r_index +
                ", m_number='" + m_number + '\'' +
                ", r_rsvnumber='" + r_rsvnumber + '\'' +
                ", r_name='" + r_name + '\'' +
                ", c_id='" + c_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", c_phone='" + c_phone + '\'' +
                ", b_party=" + b_party +
                ", condition_check=" + condition_check +
                ", reject='" + reject + '\'' +
                ", res_payment=" + res_payment +
                ", request='" + request + '\'' +
                ", tdate='" + tdate + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
