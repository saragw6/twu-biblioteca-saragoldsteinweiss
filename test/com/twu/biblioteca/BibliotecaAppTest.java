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
        String menuStr = "Enter one of the following commands to get started:\nList Books\nList Movies\n" +
                "Check out book {book} ({year published})\nCheck in book {book} ({year published})\n" +
                "Check out movie {movie} ({year published})\nCheck in movie {movie} ({year published})\n" +
                "Log in\nLog out\nShow user info\nQuit\n";

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

        String menuStr = "Enter one of the following commands to get started:\nList Books\nList Movies\n" +
                "Check out book {book} ({year published})\nCheck in book {book} ({year published})\n" +
                "Check out movie {movie} ({year published})\nCheck in movie {movie} ({year published})\n" +
                "Log in\nLog out\nShow user info\nQuit\n";

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
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.HandleInput("check out book harry potter (1997)");
        assert(!BibliotecaApp.books[0].isAvailable());

        BibliotecaApp.books[0].setAvailable(true); //reset
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckOutMarksMovieUnavailable() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.HandleInput("check out movie the wizard of oz (1939)");
        assert(!BibliotecaApp.movies[0].isAvailable());

        BibliotecaApp.movies[0].setAvailable(true); //reset
        BibliotecaApp.LogOut();
    }

    @Test
    public void OnlyPrintAvailableBooks() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");
        BibliotecaApp.books[0].available = false; //simulate checkout
        BibliotecaApp.ListBooks();

        String bookStr = "Lord of the Rings | JRT | 1954\n";

        assertEquals(bookStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void OnlyPrintAvailableMovies() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");
        BibliotecaApp.HandleInput("Check out movie the wizard of oz (1939)\n");

        BibliotecaApp.ListMovies();

        String successStr = "Thank you! Enjoy the movie\n";
        String movieStr = "Incredibles 2 | 2018 | Brad Bird | 8\n";

        assertEquals(successStr + movieStr, sysOut.asString());
        BibliotecaApp.LogOut();

    }

    @Test
    public void HandleCheckOutBookCommand() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.HandleInput("Check out book Harry Potter (1997)\n");
        assert(!BibliotecaApp.books[0].isAvailable());

        BibliotecaApp.books[0].setAvailable(true); //reset
        BibliotecaApp.LogOut();
    }

    @Test
    public void HandleCheckOutMovieCommand() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.HandleInput("Check out movie The Wizard of Oz (1939)\n");
        assert(!BibliotecaApp.movies[0].isAvailable());

        BibliotecaApp.movies[0].setAvailable(true); //reset
        BibliotecaApp.LogOut();
    }

    @Test
    public void SuccessfulCheckoutBookMessage() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(true); //ensure checked in
        BibliotecaApp.HandleInput("Check out book Harry Potter (1997)\n");

        String successStr = "Thank you! Enjoy the book\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void SuccessfulCheckoutMovieMessage() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(true); //ensure checked in
        BibliotecaApp.HandleInput("Check out movie The Wizard of Oz (1939)\n");

        String successStr = "Thank you! Enjoy the movie\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void UnsuccessfulCheckoutBookMessage() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.HandleInput("Check out book Harry Potter (1996)\n");

        String successStr = "That book is not available.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void UnsuccessfulCheckoutMovieMessage() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.HandleInput("Check out movie The Wizard of Oz (1939)\n");

        String successStr = "That movie is not available.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckoutUnavailableBookFails() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check out book Harry Potter (1997)\n");

        String failStr = "That book is not available.\n";

        assertEquals(failStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckoutUnavailableMovieFails() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check out movie The Wizard of Oz (1939)\n");

        String failStr = "That movie is not available.\n";

        assertEquals(failStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckInMarksBookAvailable() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout

        BibliotecaApp.HandleInput("Check in book Harry Potter (1997)\n");

        assert(BibliotecaApp.books[0].isAvailable());
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckInMarksMovieAvailable() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout

        BibliotecaApp.HandleInput("Check in movie The Wizard of Oz (1939)\n");

        assert(BibliotecaApp.movies[0].isAvailable());
        BibliotecaApp.LogOut();
    }

    @Test
    public void HandleCheckInBookCommand() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout

        BibliotecaApp.HandleInput("Check in book Harry Potter (1997)\n");
        assert(BibliotecaApp.books[0].isAvailable());
        BibliotecaApp.LogOut();
    }

    @Test
    public void HandleCheckInMovieCommand() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout

        BibliotecaApp.HandleInput("Check in movie The Wizard of Oz (1939)\n");
        assert(BibliotecaApp.movies[0].isAvailable());
        BibliotecaApp.LogOut();
    }

    @Test
    public void PrintReturnedBooks() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("check in book harry potter (1997)");

        BibliotecaApp.ListBooks();

        String successStr = "Thank you for returning the book.\n";
        String bookStr = "Harry Potter | JKR | 1997\nLord of the Rings | JRT | 1954\n";

        assertEquals(successStr + bookStr, sysOut.asString());
        BibliotecaApp.LogOut();

    }

    @Test
    public void PrintReturnedMovies() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");
        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check in movie The Wizard of Oz (1939)\n");

        BibliotecaApp.ListMovies();

        String successStr = "Thank you for returning the movie.\n";
        String movieStr = "The Wizard of Oz | 1939 | Victor Fleming | 8\nIncredibles 2 | 2018 | Brad Bird | 8\n";

        assertEquals(successStr + movieStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void SuccessfulCheckinBookMessage() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.books[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check in book Harry Potter (1997)\n");

        String successStr = "Thank you for returning the book.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void SuccessfulCheckinMovieMessage() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.movies[0].setAvailable(false); //simulate checkout
        BibliotecaApp.HandleInput("Check in movie The Wizard of Oz (1939)\n");

        String successStr = "Thank you for returning the movie.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void UnsuccessfulCheckinBookMessage() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.HandleInput("Check in book Harry Potter (1996)\n");

        String successStr = "That is not a valid book to return.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void UnsuccessfulCheckinMovieMessage() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

        BibliotecaApp.HandleInput("Check in movie The Wizard of Oz (1939)\n");

        String successStr = "That is not a valid movie to return.\n";

        assertEquals(successStr, sysOut.asString());
        BibliotecaApp.LogOut();
    }

    @Test
    public void LogInSetsCurrentUser() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

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
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");

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
    public void CheckInRequireLoginWithCredentials() {
        BibliotecaApp.currentUser = null;
        BibliotecaApp.HandleInput("Check in book Harry Potter (1996)\n");
        String output = "You must log in to check in an item\n";

        assertEquals(output, sysOut.asString());
    }

    @Test
    public void CheckOutRequireLoginWithCredentials() {
        BibliotecaApp.currentUser = null;
        BibliotecaApp.HandleInput("Check out book Harry Potter (1996)\n");
        String output = "You must log in to check out an item\n";

        assertEquals(output, sysOut.asString());
    }

    @Test
    public void CheckOutNotesBorrower() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");
        BibliotecaApp.HandleInput("Check out book Harry Potter (1997)\n");
        assertEquals("123-4567", BibliotecaApp.books[0].borrower_id);
        BibliotecaApp.LogOut();
    }

    @Test
    public void CheckInRemovesBorrower() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");
        BibliotecaApp.HandleInput("Check out book Harry Potter (1996)\n");
        BibliotecaApp.HandleInput("Check in book Harry Potter (1996)\n");
        assertNull(BibliotecaApp.books[0].borrower_id);
        BibliotecaApp.LogOut();
    }

    @Test
    public void PrintUserInfo() {
        BibliotecaApp.users[0].PrintInfo();
        String output = "Name: Seth\nEmail: seth@gmail.com\nPhone:855-555-0956\n";
        assertEquals(output, sysOut.asString());
    }

    @Test
    public void HandleInputUserInfo() {
        BibliotecaApp.LogInWithCredentials("123-4567", "password1");
        BibliotecaApp.HandleInput("Show user info");

        String output = "Name: Seth\nEmail: seth@gmail.com\nPhone:855-555-0956\n";
        assertEquals(output, sysOut.asString());

        BibliotecaApp.LogOut();

    }

    @Test
    public void UserInfoFailMsg() {
        BibliotecaApp.currentUser = null;
        BibliotecaApp.HandleInput("Show user info");

        String output = "Please log in to view user info\n";
        assertEquals(output, sysOut.asString());

    }

}