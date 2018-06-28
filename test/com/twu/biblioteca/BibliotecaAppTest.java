package com.twu.biblioteca;

import org.junit.Rule;
import org.junit.Test;

import static com.twu.biblioteca.BibliotecaApp.books;
import static org.junit.Assert.*;

public class BibliotecaAppTest {

    @Rule public SystemOutResource sysOut = new SystemOutResource();

//    @Test
//    public void main() {
//        BibliotecaApp.main(null);
//
//
//        String welcomeStr = "Hello! Welcome to Biblioteca.\n";
//        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";
//        String menuStr = "Enter one of the following commands to get started:\nList Books\n";
//
//        assertEquals(welcomeStr + bookStr + menuStr, sysOut.asString());
//    }

    @Test
    public void ListBooks() {
//        Book hp = new Book("Harry Potter", "JKR",1997);
//        Book lotr = new Book("Lord of the Rings", "JRT", 1954);
//        Book[] books = new Book[]{hp, lotr};

        BibliotecaApp.ListBooks();

        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";

        assertEquals(bookStr, sysOut.asString());
    }

    @Test
    public void ShowMenu() {
        BibliotecaApp.ShowMenu();

        String menuStr = "List Books\n";

        assertEquals(menuStr, sysOut.asString());
    }

    //test "quit" --> exit (this test doesnt work yet)
//    @Test
//    public void QuitCausesExit() {
//        BibliotecaApp.main(null);
//
//        ByteArrayInputStream in = new ByteArrayInputStream("quit\n".getBytes());
//        System.setIn(in);
//
//        assertEquals(null, sysOut.asString());
//
//        // optionally, reset System.in to its original
//        System.setIn(System.in);
//    }

    @Test
    public void InvalidMenuOption() {
        BibliotecaApp.HandleInput("This is an invalid command");

        assertEquals("Select a valid option!\n", sysOut.asString());

    }

    @Test
    public void CheckOutMarksBookUnavailable() {
        BibliotecaApp.CheckOutBook("Harry Potter", "JKR", 1997);
        assert(!BibliotecaApp.books[0].isAvailable());

        BibliotecaApp.books[0].setAvailable(true); //reset
    }

    @Test
    public void OnlyPrintAvailableBooks() {
        BibliotecaApp.CheckOutBook("Harry Potter", "JKR", 1997);
        BibliotecaApp.ListBooks();

        String bookStr = "Lord of the Rings | JRT | 1954\n";

        assertEquals(bookStr, sysOut.asString());
    }

    @Test
    public void HandleCheckOutCommand() {
        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1997\n");
        assert(!BibliotecaApp.books[0].isAvailable());

        BibliotecaApp.books[0].setAvailable(true); //reset
    }


}