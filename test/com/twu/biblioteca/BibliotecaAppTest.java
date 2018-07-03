package com.twu.biblioteca;

import org.junit.Rule;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.lang.System;

import static org.junit.Assert.*;

public class BibliotecaAppTest {

    @Rule public SystemOutResource sysOut = new SystemOutResource();

    @Test
    public void PrintIntro() {
        BibliotecaApp.PrintIntro();

        String welcomeStr = "Hello! Welcome to Biblioteca.\n";
        String menuStr = "Enter one of the following commands to get started:\nList Books\nCheck out {book} by {author} in {year published}\nCheck in {book} by {author} in {year published}\nQuit\n";

        assertEquals(welcomeStr + menuStr, sysOut.asString());
    }

    @Test
    public void ListBooks() {
        BibliotecaApp.ListBooks();

        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";

        assertEquals(bookStr, sysOut.asString());
    }

    @Test
    public void ShowMenu() {
        BibliotecaApp.ShowMenu();

        String menuStr = "List Books\nCheck out {book} by {author} in {year published}\nCheck in {book} by {author} in {year published}\nQuit\n";

        assertEquals(menuStr, sysOut.asString());
    }

    @Test
    public void HandleInputReturnsFalseOnQuit() {
        assertFalse(BibliotecaApp.HandleInput("quit"));
    }

    @Test
    public void InvalidMenuOption() {
        BibliotecaApp.HandleInput("This is an invalid command");

        assertEquals("Select a valid option!\n", sysOut.asString());

    }

    @Test
    public void CheckOutMarksBookUnavailable() {
        //BibliotecaApp.CheckOutBook("harry potter", "jkr", 1997);
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, false);
        assert(!BibliotecaApp.books[0].isAvailable());

        BibliotecaApp.books[0].setAvailable(true); //reset
    }

    @Test
    public void OnlyPrintAvailableBooks() {
        //BibliotecaApp.CheckOutBook("harry potter", "jkr", 1997);
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, false);
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

        //BibliotecaApp.CheckInBook("harry potter", "jkr", 1997);
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, true);

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
        //BibliotecaApp.CheckInBook("harry potter", "jkr", 1997);
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, true);
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