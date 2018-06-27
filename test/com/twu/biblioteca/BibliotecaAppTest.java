package com.twu.biblioteca;

import org.junit.Rule;
import org.junit.Test;

import static org.junit.Assert.*;

public class BibliotecaAppTest {

    @Rule public SystemOutResource sysOut = new SystemOutResource();

    @Test
    public void main() {
        BibliotecaApp.main(null);

        assertEquals("Hello! Welcome to Biblioteca.\n", sysOut.asString());
    }

    @Test
    public void ListBooks() {
        String[] books = {"Harry Potter", "Lord of the Rings"};

        BibliotecaApp.ListBooks(books);

        assertEquals("Harry Potter\nLord of the Rings\n", sysOut.asString());
    }
}