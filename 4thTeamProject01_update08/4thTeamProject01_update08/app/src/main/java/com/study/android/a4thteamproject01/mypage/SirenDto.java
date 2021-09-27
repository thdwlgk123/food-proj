package com.study.android.a4thteamproject01.mypage;

public class SirenDto {

    private int r_index;
    private String m_number;
    private String r_name;
    private String c_id;
    private String c_name;
    private String c_phone;
    private String condition_check;
    private String reject;
    private String res_payment;
    private String r_menu;
    private String request;
    private String r_rsvnumber;
    private String tdate;

    public int getR_index() {
        return r_index;
    }

    public String getM_number() {
        return m_number;
    }

    public String getR_name() {
        return r_name;
    }

    public String getC_id() {
        return c_id;
    }

    public String getC_name() {
        return c_name;
    }

    public String getC_phone() {
        return c_phone;
    }

    public String getCondition_check() {
        return condition_check;
    }

    public String getReject() {
        return reject;
    }

    public String getRes_payment() {
        return res_payment;
    }

    public String getR_menu() {
        return r_menu;
    }

    public String getRequest() {
        return request;
    }

    public String getR_rsvnumber() {
        return r_rsvnumber;
    }

    public String getTdate() {
        return tdate;
    }

    public void setR_index(int r_index) {
        this.r_index = r_index;
    }

    public void setM_number(String m_number) {
        this.m_number = m_number;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public void setC_id(String c_id) {
        this.c_id = c_id;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public void setC_phone(String c_phone) {
        this.c_phone = c_phone;
    }

    public void setCondition_check(String condition_check) {
        this.condition_check = condition_check;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public void setRes_payment(String res_payment) {
        this.res_payment = res_payment;
    }

    public void setR_menu(String r_menu) {
        this.r_menu = r_menu;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public void setR_rsvnumber(String r_rsvnumber) {
        this.r_rsvnumber = r_rsvnumber;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    @Override
    public String toString() {
        return "SirenDto{" +
                "r_index=" + r_index +
                ", m_number='" + m_number + '\'' +
                ", r_name='" + r_name + '\'' +
                ", c_id='" + c_id + '\'' +
                ", c_name='" + c_name + '\'' +
                ", c_phone='" + c_phone + '\'' +
                ", condition_check='" + condition_check + '\'' +
                ", reject='" + reject + '\'' +
                ", res_payment=" + res_payment +
                ", r_menu='" + r_menu + '\'' +
                ", request='" + request + '\'' +
                ", r_rsvnumber='" + r_rsvnumber + '\'' +
                ", tdate='" + tdate + '\'' +
                '}';
    }

    public SirenDto(int r_index, String m_number, String r_name, String c_id, String c_name, String c_phone, String condition_check, String reject, String res_payment, String r_menu, String request, String r_rsvnumber, String tdate) {
        this.r_index = r_index;
        this.m_number = m_number;
        this.r_name = r_name;
        this.c_id = c_id;
        this.c_name = c_name;
        this.c_phone = c_phone;
        this.condition_check = condition_check;
        this.reject = reject;
        this.res_payment = res_payment;
        this.r_menu = r_menu;
        this.request = request;
        this.r_rsvnumber = r_rsvnumber;
        this.tdate = tdate;
    }
}
