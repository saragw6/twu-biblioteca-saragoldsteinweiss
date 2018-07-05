package com.twu.biblioteca;

class LibraryItem {
    String title;
    Integer year;
    Boolean available;
    String borrower_id;

    Boolean isAvailable() {
        return available;
    }

    void setAvailable(Boolean availability) {
        available = availability;
    }

    void setBorrower(String id_num) { borrower_id = id_num; }

}
