package com.example.personal_diary_2022551022;

public class ContactVO {
    String phone;
    String name;
    String company;
    String title;
    String email;

    public ContactVO() {
    }
    public ContactVO(String name, String phone, String email, String company, String title) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.company = company;
        this.title = title;
    }


    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
