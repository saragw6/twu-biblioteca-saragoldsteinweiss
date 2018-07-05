package com.twu.biblioteca;

class Movie extends LibraryItem {
    String director;
    Integer rating; //use special class for MovieRating?

    Movie(String title, Integer year, String director, Integer rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.available = true;
    }
}
