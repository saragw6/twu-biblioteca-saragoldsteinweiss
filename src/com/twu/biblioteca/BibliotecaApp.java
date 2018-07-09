package com.twu.biblioteca;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
//import org.apache.commons.lang;

public class BibliotecaApp {
    //static String[] options = new String[]{"List Books"}; //case sensitivity?
    private static Book hp = new Book("Harry Potter", "JKR",1997);
    private static Book lotr = new Book("Lord of the Rings", "JRT", 1954);
    static Book[] books = new Book[]{hp, lotr};

    private static Movie oz = new Movie("The Wizard of Oz", 1939, "Victor Fleming", 8);
    private static Movie incredibles = new Movie("Incredibles 2", 2018, "Brad Bird", 8);
    static Movie[] movies = new Movie[]{oz, incredibles};

    private static User seth = new User("123-4567", "password1", "Seth", "seth@gmail.com", "855-555-0956");
    private static User basha = new User ("987-6543", "password2", "Basha", "basha@yahoo.com", "508-555-0995");
    static User[] users = new User[]{seth, basha};

    //static LibraryItem[] items = new LibraryItem[]{hp, lotr, oz, incredibles};
    static User currentUser;

    private static Pattern pattern = Pattern.compile("check (in|out) (book|movie) ((?:\\w|\\s)*) [(]((?:\\d)*)[)]");

    public static void main(String[] args) {
        PrintIntro();

        String command;
        Boolean readInput = true;
        Scanner scanner = new Scanner(System.in);

        while (readInput) {
            command = scanner.nextLine();
            readInput = HandleInput(command);
        }
    }

    static void PrintIntro() {
        System.out.println("Hello! Welcome to Biblioteca.");
        ShowMenu();
    }

    static Boolean HandleInput(String command) {
        command = command.toLowerCase();
        Matcher matcher = pattern.matcher(command);
        //eventually use objects for menu items that have callbacks stored in them?
        if (command.equals("quit")) {
            return false;
        } else if (command.equals("list books")) {
            ListBooks();
        } else if (matcher.find()) {
            if (currentUser == null) {
                System.out.println("You must log in to check " + matcher.group(1) + " an item");
            } else {
                Boolean checkin = matcher.group(1).equals("in");
                Boolean success = LibraryTransaction(checkin, matcher.group(2).equals("book"), matcher.group(3), Integer.parseInt(matcher.group(4)));
                System.out.println(GetTransactionString(success, checkin, matcher.group(2)));
            }
        } else if (command.equals("log in")) {
            Boolean success = LogIn();
            String msg = success ? "Login successful" : "Login failed, please try again.";
            System.out.println(msg);
        } else if (command.equals("log out")){
            Boolean success = LogOut();
            String msg = success ? "Logout successful" : "No user had logged in";
            System.out.println(msg);
        } else if (command.equals("show user info")) {
            if (currentUser == null) {
                System.out.println("Please log in to view user info");
            } else {
                currentUser.PrintInfo();
            }
        } else {
            System.out.println("Select a valid option!");
        }
        return true;
    }

    static void ListBooks() {
        for(Book book : books) {
            if (book.available) {
                System.out.println(book.title + " | " + book.author + " | " + book.year);
            }
        }
    }

    static void ListMovies() {
        for(Movie movie : movies) {
            if (movie.available) {
                System.out.println(movie.title + " | " + movie.year + " | " + movie.director + " | " + movie.rating);
            }
        }
    }

    //refactor?
    static void ShowMenu() {
        String menuStr = "Enter one of the following commands to get started:\nList Books\nList Movies\nCheck out {book} by {author} in {year published}\nCheck in {book} by {author} in {year published}\nLog in\nLog out\nShow user info\nQuit\n";
        System.out.print(menuStr);
    }

    static Boolean LibraryTransaction(Boolean checkin, Boolean isBook, String title, Integer year) {
        LibraryItem[] items = isBook ? books : movies;
        for (LibraryItem item : items) {
            if (item.title.toLowerCase().equals(title)
                    && item.year.equals(year) && (item.available != checkin)) {
                item.available = checkin;
                item.borrower_id = checkin ? null : currentUser.id_number;
                return true;
            }
        }
        return false;
    }

    private static String GetTransactionString(Boolean success, Boolean checkin, String itemType) {
        if (success) {
            return checkin ? "Thank you for returning the " + itemType + "." : "Thank you! Enjoy the " + itemType;
        }
        return checkin ? "That is not a valid " + itemType + " to return." :  "That " + itemType + " is not available.";
    }

    private static Boolean LogIn() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter id number:");
        String id_number = scanner.nextLine();
        System.out.println("Enter password:");
        String passwd = scanner.nextLine();
        return LogInWithCredentials(id_number, passwd);
    }

    static Boolean LogInWithCredentials(String id_number, String passwd) {
        //is this the best way to search?
        Boolean success = false;
        for (User user : users) {
            if (user.id_number.equals(id_number) && user.passwd.equals(passwd)) {
                currentUser = user;
                success = true;
            }
        }

        return  success;
    }

    static Boolean LogOut() {
        Boolean success = currentUser != null;
        currentUser = null;
        return success;
    }

}
