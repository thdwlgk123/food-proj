package com.study.android.a4thteamproject01;

public class PostResult {

    private int r_index;
    private String m_number;
    private String r_name;
    private String gu_name;
    private String s_name;
    private String t_name;
    private String h_number;
    private String h_name;
    private String coordinates_x;
    private String coordinates_y;
    private String r_number;
    private String r_adress2;
    private String r_adress1;
    private String r_menu;
    private String r_image;
    private String tdate;
    private int resId;
    private int countreview;
    private String gradeavg;

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

    public String getR_name() {
        return r_name;
    }

    public void setR_name(String r_name) {
        this.r_name = r_name;
    }

    public String getGu_name() {
        return gu_name;
    }

    public void setGu_name(String gu_name) {
        this.gu_name = gu_name;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getT_name() {
        return t_name;
    }

    public void setT_name(String t_name) {
        this.t_name = t_name;
    }

    public String getH_number() {
        return h_number;
    }

    public void setH_number(String h_number) {
        this.h_number = h_number;
    }

    public String getH_name() {
        return h_name;
    }

    public void setH_name(String h_name) {
        this.h_name = h_name;
    }

    public String getCoordinates_x() {
        return coordinates_x;
    }

    public void setCoordinates_x(String coordinates_x) {
        this.coordinates_x = coordinates_x;
    }

    public String getCoordinates_y() {
        return coordinates_y;
    }

    public void setCoordinates_y(String coordinates_y) {
        this.coordinates_y = coordinates_y;
    }

    public String getR_number() {
        return r_number;
    }

    public void setR_number(String r_number) {
        this.r_number = r_number;
    }

    public String getR_adress2() {
        return r_adress2;
    }

    public void setR_adress2(String r_adress2) {
        this.r_adress2 = r_adress2;
    }

    public String getR_adress1() {
        return r_adress1;
    }

    public void setR_adress1(String r_adress1) {
        this.r_adress1 = r_adress1;
    }

    public String getR_menu() {
        return r_menu;
    }

    public void setR_menu(String r_menu) {
        this.r_menu = r_menu;
    }

    public String getR_image() {
        return r_image;
    }

    public void setR_image(String r_image) {
        this.r_image = r_image;
    }

    public String getTdate() {
        return tdate;
    }

    public void setTdate(String tdate) {
        this.tdate = tdate;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public int getCountreview() {
        return countreview;
    }

    public void setCountreview(int countreview) {
        this.countreview = countreview;
    }

    public String getGradeavg() {
        return gradeavg;
    }

    public void setGradeavg(String gradeavg) {
        this.gradeavg = gradeavg;
    }

    // toString()을 Override 해주지 않으면 객체 주소값을 출력함
    @Override
    public String toString() {
        return "PostResult{" +
                "r_index=" + r_index +
                ", m_number=" + m_number +
                ", r_name=" + r_name +
                ", gu_name=" + gu_name +
                ", s_name=" + s_name +
                ", t_name=" + t_name +
                ", h_number=" + h_number +
                ", h_name=" + h_name +
                ", coordinates_x=" + coordinates_x +
                ", coordinates_y=" + coordinates_y +
                ", r_number=" + r_number +
                ", r_adress2=" + r_adress2 +
                ", r_adress1=" + r_adress1 +
                ", r_menu=" + r_menu +
                ", r_image=" + r_image +
                ", tdate=" + tdate +
                ", countreview="+countreview+
                ", gradeavg="+gradeavg+"}";
    }
}
