package org.bahmni.batch.form.domain;

import java.util.Date;

public class Person {
    private String identifier;
    private String name;
    private Date birthDate;
    private Integer age;
    private String gender;

    public Person(String identifier, String name, Date birthDate, Integer age, String gender) {
        this.identifier = identifier;
        this.name = name;
        this.birthDate = birthDate;
        this.age = age;
        this.gender = gender;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
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
}
