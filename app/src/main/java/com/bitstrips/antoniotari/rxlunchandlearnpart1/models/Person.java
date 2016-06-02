package com.bitstrips.antoniotari.rxlunchandlearnpart1.models;

import com.google.gson.Gson;

/**
 * Created by antonio.tari on 6/2/16.
 */
public class Person {
    private String name;
    private String lastName;
    private int age;

    public Person(final String name, final String lastName, final int age) {
        this.name = name;
        this.lastName = lastName;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public String getLastName() {
        return lastName;
    }

    public int getAge() {
        return age;
    }

    @Override
    public String toString() {
        String toString = new Gson().toJson(this);
        if (toString == null || toString.isEmpty()) {
            toString = super.toString();
        }
        return toString;
    }

    public String getLink() {
        return "http://i4.mirror.co.uk/incoming/article823894.ece/ALTERNATES/s615/Tyrannosaurus%20Rex";
    }
}
