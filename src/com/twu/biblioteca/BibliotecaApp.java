package com.twu.biblioteca;

public class BibliotecaApp {

    public static void main(String[] args) {

        Book hp = new Book("Harry Potter", "JKR",1997);
        Book lotr = new Book("Lord of the Rings", "JRT", 1954);
        Book[] books = new Book[]{hp, lotr};

        System.out.println("Hello! Welcome to Biblioteca.");
        ListBooks(books);

        ShowMenu();
    }

    static void ListBooks(Book[] books) {
        for(Book book : books) {
            System.out.println(book.title + " | " + book.author + " | " + book.year);
        }
    }

    static void ShowMenu() {
        String menuStr = "Options:\nList Books\n";

        System.out.print(menuStr);
    }
}
