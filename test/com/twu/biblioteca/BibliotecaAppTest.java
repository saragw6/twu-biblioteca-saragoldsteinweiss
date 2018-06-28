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

    @Test
    public void SuccessfulCheckoutMessage() {
        BibliotecaApp.books[0].setAvailable(true); //ensure checked in
        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1997\n");

        String successStr = "Thank you! Enjoy the book\n";

        assertEquals(successStr, sysOut.asString());
    }

    @Test
    public void UnsuccessfulCheckoutMessage() {
        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1996\n");

        String successStr = "That book is not available.\n";

        assertEquals(successStr, sysOut.asString());
    }

    @Test
    public void CheckoutUnavailableBookFails() {
        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1997\n");

        String successStr = "That book is not available.\n";

        assertEquals(successStr, sysOut.asString());
    }

    @Test
    public void CheckInMarksBookAvailable() {
        BibliotecaApp.books[0].setAvailable(false); //simulate checkout

        BibliotecaApp.CheckInBook("Harry Potter", "JKR", 1997);
        assert(BibliotecaApp.books[0].isAvailable());
    }

    @Test
    public void HandleCheckInCommand() {
        BibliotecaApp.books[0].setAvailable(false); //simulate checkout

        BibliotecaApp.HandleInput("Check in Harry Potter by JKR in 1997\n");
        assert(BibliotecaApp.books[0].isAvailable());
    }

    @Test
    public void PrintReturnedBooks() {
        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.CheckInBook("Harry Potter", "JKR", 1997);
        BibliotecaApp.ListBooks();

        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";

        assertEquals(bookStr, sysOut.asString());
    }

    @Test
    public void SuccessfulCheckinMessage() {
        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check in Harry Potter by JKR in 1997\n");

        String successStr = "Thank you for returning the book.\n";

        assertEquals(successStr, sysOut.asString());
    }

    @Test
    public void UnsuccessfulCheckinMessage() {
        BibliotecaApp.HandleInput("Check in Harry Potter by JKR in 1996\n");

        String successStr = "That is not a valid book to return.\n";

        assertEquals(successStr, sysOut.asString());
    }
}