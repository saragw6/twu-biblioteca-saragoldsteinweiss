package com.twu.biblioteca;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class BibliotecaAppTest {

    @Rule public SystemOutResource sysOut = new SystemOutResource();

    @Test
    public void main() {
        BibliotecaApp.main(null);


        String welcomeStr = "Hello! Welcome to Biblioteca.\n";
        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";

        assertEquals(welcomeStr + bookStr, sysOut.asString());
    }

    @Test
    public void ListBooks() {
        Book hp = new Book("Harry Potter", "JKR",1997);
        Book lotr = new Book("Lord of the Rings", "JRT", 1954);
        Book[] books = new Book[]{hp, lotr};

        BibliotecaApp.ListBooks(books);

        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";

        assertEquals(bookStr, sysOut.asString());
    }
}