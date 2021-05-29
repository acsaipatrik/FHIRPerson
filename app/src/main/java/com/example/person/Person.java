package com.example.person;

public class Person {



    private String id;
    private String name;
    private String telecom;
    private String gender;
    private String birthDate;
    private String address;
    private String active;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelecom() {
        return telecom;
    }

    public void setTelecom(String telecom) {
        this.telecom = telecom;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Person(String name, String telecom, String address,String birthDate, String gender, String active) {
        this.name = name;
        this.telecom = telecom;
        this.gender = gender;
        this.birthDate = birthDate;
        this.address = address;
        this.active=active;
    }

    public Person() {
    }
}
