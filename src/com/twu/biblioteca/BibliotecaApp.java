package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {

        System.out.println("Hello! Welcome to Biblioteca.");
    }

    static void ListBooks(String[] books) {
        for(String book : books) {
            System.out.println(book);
        }
    }
}
