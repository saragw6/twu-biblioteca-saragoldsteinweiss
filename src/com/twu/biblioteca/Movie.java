package com.twu.biblioteca;

class Movie {
    String title;
    Integer year;
    String director;
    Integer rating; //use special class for MovieRating?
    Boolean available;

    Movie(String title, Integer year, String director, Integer rating) {
        this.title = title;
        this.director = director;
        this.year = year;
        this.rating = rating;
        this.available = true;
    }

    Boolean isAvailable() {
        return available;
    }

    void setAvailable(Boolean availability) {
        available = availability;
    }
}
