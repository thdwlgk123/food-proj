package com.study.android.a4thteamproject01.hyungdoo.sirenList;

public class SingerItem2
{
    private String menu_name;
    private String menu_price;
    private String menu_count;


    public SingerItem2(String name, String price, String count){
        this.menu_name = name;
        this.menu_price = price;
        this.menu_count = count;

    }

    public String getMenu_name(){ return menu_name ;}
    public void setMenu_name(String name) { this.menu_name = name;}
    public String getMenu_price(){ return menu_price;}
    public void setMenu_price(String price) {this.menu_price=price;}
    public String getMenu_count(){ return menu_count;}
    public void setMenu_count(String count) {this.menu_count=count;}

}
