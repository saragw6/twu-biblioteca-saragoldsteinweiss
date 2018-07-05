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
        String menuStr = "Enter one of the following commands to get started:\nList Books\nList Movies\nCheck out {book} by {author} in {year published}\nCheck in {book} by {author} in {year published}\nQuit\n";

        assertEquals(welcomeStr + menuStr, sysOut.asString());
    }

    @Test
    public void ListBooks() {
        BibliotecaApp.ListBooks();

        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";

        assertEquals(bookStr, sysOut.asString());
    }

    @Test
    public void ListMovies() {
        for (Movie movie : BibliotecaApp.movies) {
            movie.setAvailable(true);
        }
        BibliotecaApp.ListMovies();

        String movieStr = "The Wizard of Oz | 1939 | Victor Fleming | 8\nIncredibles 2 | 2018 | Brad Bird | 8\n";

        assertEquals(movieStr, sysOut.asString());
    }

    @Test
    public void ShowMenu() {
        BibliotecaApp.ShowMenu();

        String menuStr = "List Books\nList Movies\nCheck out {book} by {author} in {year published}\nCheck in {book} by {author} in {year published}\nQuit\n";

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
        BibliotecaApp.LogIn("123-4567", "password1");

        //BibliotecaApp.CheckOutBook("harry potter", "jkr", 1997);
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, false);
        assert(!BibliotecaApp.books[0].isAvailable());

        BibliotecaApp.books[0].setAvailable(true); //reset
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckOutMarksMovieUnavailable() {
        BibliotecaApp.LogIn("123-4567", "password1");

        //BibliotecaApp.CheckOutBook("harry potter", "jkr", 1997);
        BibliotecaApp.MovieTransaction("the wizard of oz", 1939, false);
        assert(!BibliotecaApp.movies[0].isAvailable());

        BibliotecaApp.movies[0].setAvailable(true); //reset
        BibliotecaApp.LogOut();
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
    public void OnlyPrintAvailableMovies() {
        //BibliotecaApp.CheckOutBook("harry potter", "jkr", 1997);
        BibliotecaApp.MovieTransaction("the wizard of oz", 1939, false);
        BibliotecaApp.ListMovies();

        String movieStr = "Incredibles 2 | 2018 | Brad Bird | 8\n";

        assertEquals(movieStr, sysOut.asString());
    }

    @Test
    public void HandleCheckOutBookCommand() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1997\n");
        assert(!BibliotecaApp.books[0].isAvailable());

        BibliotecaApp.books[0].setAvailable(true); //reset
        BibliotecaApp.LogOut();
    }

    @Test
    public void HandleCheckOutMovieCommand() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.HandleInput("Check out The Wizard of Oz (1939)\n");
        assert(!BibliotecaApp.movies[0].isAvailable());

        BibliotecaApp.movies[0].setAvailable(true); //reset
        BibliotecaApp.LogOut();
    }

    @Test
    public void SuccessfulCheckoutBookMessage() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(true); //ensure checked in
        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1997\n");

        String successStr = "Thank you! Enjoy the book\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void SuccessfulCheckoutMovieMessage() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(true); //ensure checked in
        BibliotecaApp.HandleInput("Check out The Wizard of Oz (1939)\n");

        String successStr = "Thank you! Enjoy the movie\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void UnsuccessfulCheckoutBookMessage() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1996\n");

        String successStr = "That book is not available.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void UnsuccessfulCheckoutMovieMessage() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.HandleInput("Check out The Wizard of Oz (1939)\n");

        String successStr = "That movie is not available.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckoutUnavailableBookFails() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1997\n");

        String failStr = "That book is not available.\n";

        assertEquals(failStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckoutUnavailableMovieFails() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check out The Wizard of Oz (1939)\n");

        String failStr = "That movie is not available.\n";

        assertEquals(failStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckInMarksBookAvailable() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout

        //BibliotecaApp.CheckInBook("harry potter", "jkr", 1997);
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, true);

        assert(BibliotecaApp.books[0].isAvailable());
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckInMarksMovieAvailable() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout

        BibliotecaApp.MovieTransaction("the wizard of oz", 1939, true);

        assert(BibliotecaApp.movies[0].isAvailable());
        BibliotecaApp.LogOut();
    }

    @Test
    public void HandleCheckInBookCommand() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout

        BibliotecaApp.HandleInput("Check in Harry Potter by JKR in 1997\n");
        assert(BibliotecaApp.books[0].isAvailable());
        BibliotecaApp.LogOut();
    }

    @Test
    public void HandleCheckInMovieCommand() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout

        BibliotecaApp.HandleInput("Check in The Wizard of Oz (1939)\n");
        assert(BibliotecaApp.movies[0].isAvailable());
        BibliotecaApp.LogOut();
    }

    @Test
    public void PrintReturnedBooks() {
        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, true);

        BibliotecaApp.ListBooks();

        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";

        assertEquals(bookStr, sysOut.asString());
    }

    @Test
    public void PrintReturnedMovies() {
        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout
        BibliotecaApp.MovieTransaction("the wizard of oz", 1939, true);

        BibliotecaApp.ListMovies();

        String movieStr = "The Wizard of Oz | 1939 | Victor Fleming | 8\nIncredibles 2 | 2018 | Brad Bird | 8\n";

        assertEquals(movieStr, sysOut.asString());
    }

    @Test
    public void SuccessfulCheckinBookMessage() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check in Harry Potter by JKR in 1997\n");

        String successStr = "Thank you for returning the book.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void SuccessfulCheckinMovieMessage() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check in The Wizard of Oz (1939)\n");

        String successStr = "Thank you for returning the movie.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void UnsuccessfulCheckinBookMessage() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.HandleInput("Check in Harry Potter by JKR in 1996\n");

        String successStr = "That is not a valid book to return.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void UnsuccessfulCheckinMovieMessage() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.HandleInput("Check in The Wizard of Oz (1939)\n");

        String successStr = "That is not a valid movie to return.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void LogInSetsCurrentUser() {
        BibliotecaApp.LogIn("123-4567", "password1");

        assert(BibliotecaApp.currentUser.id_number.equals("123-4567"));
    }

    @Test
    public void HandleLogInCommandWithSuccessMsg() {
        ByteArrayInputStream idAndPasswd = new ByteArrayInputStream("123-4567\npassword1".getBytes());

        System.setIn(idAndPasswd);

        BibliotecaApp.HandleInput("Log in");

        String output = "Enter id number:\nEnter password:\nLogin successful\n";


        assert(BibliotecaApp.currentUser.id_number.equals("123-4567"));
        assertEquals(output, sysOut.asString());
    }

    @Test
    public void HandleLogInCommandWithFailMsg() {
        BibliotecaApp.currentUser = null;
        ByteArrayInputStream idAndPasswd = new ByteArrayInputStream("123-4568\npassword1".getBytes());

        System.setIn(idAndPasswd);

        BibliotecaApp.HandleInput("Log in");

        String output = "Enter id number:\nEnter password:\nLogin failed, please try again.\n";


        assertNull(BibliotecaApp.currentUser);
        assertEquals(output, sysOut.asString());
    }

    @Test
    public void HandleLogOutCommandWithSuccessMsg() {
        BibliotecaApp.LogIn("123-4567", "password1");

        BibliotecaApp.HandleInput("Log out");

        String output = "Logout successful\n";

        assertNull(BibliotecaApp.currentUser);
        assertEquals(output, sysOut.asString());
    }

    @Test
    public void HandleLogOutCommandWithFailMsg() {
        BibliotecaApp.currentUser = null;

        BibliotecaApp.HandleInput("Log out");

        String output = "No user had logged in\n";

        assertNull(BibliotecaApp.currentUser);
        assertEquals(output, sysOut.asString());
    }

    @Test
    public void CheckInRequireLogin() {
        BibliotecaApp.currentUser = null;
        BibliotecaApp.HandleInput("Check in Harry Potter by JKR in 1996\n");
        String output = "You must log in to check in an item\n";

        assertEquals(output, sysOut.asString());
    }

    @Test
    public void CheckOutRequireLogin() {
        BibliotecaApp.currentUser = null;
        BibliotecaApp.HandleInput("Check out Harry Potter by JKR in 1996\n");
        String output = "You must log in to check out an item\n";

        assertEquals(output, sysOut.asString());
    }

    @Test
    public void CheckOutNotesBorrower() {
        BibliotecaApp.LogIn("123-4567", "password1");
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, false);
        assertEquals("123-4567", BibliotecaApp.books[0].borrower_id);
    }

    @Test
    public void CheckInRemovesBorrower() {
        BibliotecaApp.LogIn("123-4567", "password1");
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, false);
        BibliotecaApp.BookTransaction("harry potter", "jkr", 1997, true);
        assertNull(BibliotecaApp.books[0].borrower_id);
    }

}