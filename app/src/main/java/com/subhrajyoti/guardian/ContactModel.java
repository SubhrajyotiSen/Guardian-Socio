package com.subhrajyoti.guardian;

public class ContactModel {

    private String name;
    private String phone;

    public ContactModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public ContactModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
