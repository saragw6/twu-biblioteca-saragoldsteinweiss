package com.twu.biblioteca;

import com.sun.org.apache.xpath.internal.operations.Bool;

class Book {
    String title;
    String author;
    Integer year;
    Boolean available;

    Book(String title, String author, Integer year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.available = true;
    }

    Boolean isAvailable() {
        return available;
    }

    void setAvailable(Boolean availability) {
        available = availability;
    }
}
