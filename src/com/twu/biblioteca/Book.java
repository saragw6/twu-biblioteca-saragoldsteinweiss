package com.twu.biblioteca;

class Book extends LibraryItem {
    String author;

    Book(String title, String author, Integer year) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.available = true;
    }
}
