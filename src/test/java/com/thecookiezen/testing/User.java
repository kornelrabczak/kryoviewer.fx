package com.thecookiezen.testing;

public class User {
    private int id;
    private String name;
    private String address;

    public User(int i, String john, String miami) {
        id = i;
        name = john;
        address = miami;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
