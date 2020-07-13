package com.validity.monolithstarter.domain;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.springframework.beans.factory.annotation.Autowired;

public class ContactModel {

    private int id;
    private String firstName;
    private String lastName;
    private String company;
    private String email;
    private String address1;
    private String address2;
    private String zip;
    private String city;
    private String stateLong;
    private String state;
    private String phone;

    public ContactModel(int id, String firstName, String lastName,
                        String company, String email,
                        String address1, String address2, String zip,
                        String city, String stateLong, String state,
                        String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.zip = zip;
        this.city = city;
        this.stateLong = stateLong;
        this.state = state;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return
                firstName + ',' +
                lastName + ',' +
                company + ',' +
                email + ',' +
                address1 + ',' +
                address2 + ',' +
                zip + ',' +
                city + ',' +
                stateLong + ',' +
                state + ',' +
                phone;
    }

    /**
     * Adds each of the fields into a
     * @param node
     * @return
     */
    public ObjectNode getNode(ObjectNode node) {

        node.put("id", this.getId());
        node.put("first_name", this.getFirstName());
        node.put("last_name", this.getLastName());
        node.put("company", this.getCompany());
        node.put("email", this.getEmail());
        node.put("address1", this.getAddress1());
        node.put("address2", this.getAddress2());
        node.put("zip", this.getZip());
        node.put("city", this.getCity());
        node.put("state_long", this.getStateLong());
        node.put("state", this.getState());
        node.put("phone", this.getPhone());

        return node;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getStateLong() {
        return stateLong;
    }

    public void setStateLong(String stateLong) {
        this.stateLong = stateLong;
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
