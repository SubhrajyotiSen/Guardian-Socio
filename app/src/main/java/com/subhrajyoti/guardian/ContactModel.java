package com.subhrajyoti.guardian;

public class ContactModel {

    private String name;
    private String phone;
    private int id;

    public ContactModel(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public ContactModel(String name, String phone, int id) {
        this.name = name;
        this.phone = phone;
        this.id = id;
    }

    public ContactModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
