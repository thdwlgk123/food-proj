package com.study.android.a4thteamproject01;

public class MenuItem {

    private String r_menu;
    private String menu_price;

    public MenuItem(String r_menu) {
        this.r_menu = r_menu;
    }
    public MenuItem(String r_menu, String menu_price) {
        this.r_menu = r_menu;
        this.menu_price = menu_price;
    }

    public String getR_menu() {
        return r_menu;
    }

    public void setR_menu(String r_menu) {
        this.r_menu = r_menu;
    }

    public String getMenu_price() {
        return menu_price;
    }

    public void setMenu_price(String menu_price) {
        this.menu_price = menu_price;
    }
}
