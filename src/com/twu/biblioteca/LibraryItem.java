package com.twu.biblioteca;

class LibraryItem {
    String title;
    Integer year;
    Boolean available;
    User borrower;

    Boolean isAvailable() {
        return available;
    }

    void setAvailable(Boolean availability) {
        available = availability;
    }

}
