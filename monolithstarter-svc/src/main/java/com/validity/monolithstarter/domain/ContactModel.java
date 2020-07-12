package com.validity.monolithstarter.domain;

public class ContactModel {

    private int id;
    private String firstname;
    private String lastname;
    private String company;
    private String email;
    private String address1;
    private String address2;
    private String zip;
    private String city;
    private String statelong;
    private String state;
    private String phone;

    public ContactModel(int id, String firstname, String lastname,
                        String company, String email,
                        String address1, String address2, String zip,
                        String city, String statelong, String state,
                        String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.company = company;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.zip = zip;
        this.city = city;
        this.statelong = statelong;
        this.state = state;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return
                id + ',' +
                firstname + ',' +
                lastname + ',' +
                company + ',' +
                email + ',' +
                address1 + ',' +
                address2 + ',' +
                zip + ',' +
                city + ',' +
                statelong + ',' +
                state + ',' +
                phone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatelong() {
        return statelong;
    }

    public void setStatelong(String statelong) {
        this.statelong = statelong;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
