package com.example.personal_diary_2022551022;

public class ContactItem {
    String name;
    String detail;

    public ContactItem(String name, String phone) {
        this.name = name;
        this.detail = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
