package com.norberth.test;

import java.math.BigInteger;

public class UserDetails {

    private String name;
    private BigInteger age;
    private String email;
    private String password;
    private String surname;

    public UserDetails(String name, BigInteger age, String email, String password, String surname) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return "UserDetails{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", surname='" + surname + '\'' +
                '}';
    }
}
