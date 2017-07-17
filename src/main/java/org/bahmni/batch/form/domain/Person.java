package org.bahmni.batch.form.domain;

import java.util.Date;

public class Person {
    private Integer id;
    private String name;
    private Date birthDate;
    private Integer age;
    private String gender;
    private String village;
    private String district;
    private String state;

    public Person(){

    }

    public Person(Integer id, String name, Date birthDate, Integer age, String gender, String village, String district, String state) {
        this.id = id;
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.gender = gender;
        this.village = village;
        this.district = district;
        this.state = state;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getVillage() {
        return village;
    }

    public void setVillage(String village) {
        this.village = village;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
