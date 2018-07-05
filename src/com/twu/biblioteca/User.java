package com.twu.biblioteca;

class User {
    String id_number;
    String passwd;
    private String name;
    private String email;
    private String phone;

    User(String id_number, String passwd, String name, String email, String phone) {
        this.id_number = id_number;
        this.passwd = passwd;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    void PrintInfo() {
        System.out.println("Name: " + name + "\nEmail: " + email + "\nPhone:" + phone);

    }
}
