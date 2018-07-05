package com.twu.biblioteca;

import javax.swing.text.StyledEditorKit;
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

    static User currentUser;

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
        //eventually use objects for menu items that have callbacks stored in them?
        if (command.equals("quit")) {
            return false;
        } else if (command.equals("list books")) {
            ListBooks();
        } else if (command.contains("check out")) {
            if (currentUser == null) {
                System.out.println("You must log in to check out an item");
            } else {
                Pattern bookPattern = Pattern.compile("check out ((?:\\w|\\s)*) by ((?:\\w|\\s)*) in (\\d*)");
                Matcher bookMatcher = bookPattern.matcher(command);
                Pattern moviePattern = Pattern.compile("check out ((?:\\w|\\s)*) [(]((?:\\d)*)[)]");
                Matcher movieMatcher = moviePattern.matcher(command);
                if (bookMatcher.find()) {
                    Boolean success = BookTransaction(bookMatcher.group(1), bookMatcher.group(2), Integer.parseInt(bookMatcher.group(3)), false);
                    if (success) {
                        System.out.println("Thank you! Enjoy the book");
                    } else {
                        System.out.println("That book is not available.");
                    }
                } else if (movieMatcher.find()) {
                    Boolean success = MovieTransaction(movieMatcher.group(1), Integer.parseInt(movieMatcher.group(2)), false);
                    if (success) {
                        System.out.println("Thank you! Enjoy the movie");
                    } else {
                        System.out.println("That movie is not available.");
                    }
                }
            }
        } else if (command.contains("check in")) {
            if (currentUser == null) {
                System.out.println("You must log in to check in an item");
            } else {
                Pattern bookPattern = Pattern.compile("check in ((?:\\w|\\s)*) by ((?:\\w|\\s)*) in (\\d*)");
                Matcher bookMatcher = bookPattern.matcher(command);
                Pattern moviePattern = Pattern.compile("check in ((?:\\w|\\s)*) [(]((?:\\d)*)[)]");
                Matcher movieMatcher = moviePattern.matcher(command);
                if (bookMatcher.find()) {
                    Boolean success = BookTransaction(bookMatcher.group(1), bookMatcher.group(2), Integer.parseInt(bookMatcher.group(3)), true);
                    if (success) {
                        System.out.println("Thank you for returning the book.");
                    } else {
                        System.out.println("That is not a valid book to return.");
                    }
                } else if (movieMatcher.find()) {
                    //Boolean success = false; //temp
                    Boolean success = MovieTransaction(movieMatcher.group(1), Integer.parseInt(movieMatcher.group(2)), true);
                    if (success) {
                        System.out.println("Thank you for returning the movie.");
                    } else {
                        System.out.println("That is not a valid movie to return.");
                    }
                }
            }
        } else if (command.equals("log in")) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter id number:");
            String id_num = scanner.nextLine();
            System.out.println("Enter password:");
            String pw = scanner.nextLine();
            Boolean success = LogIn(id_num, pw);
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

    //refactor:
    static Boolean BookTransaction(String title, String author, Integer year, Boolean checkin) {
        for(Book book : books) {
            if (book.title.toLowerCase().equals(title)
                    && book.author.toLowerCase().equals(author)
                    && book.year.equals(year) && (book.available != checkin)) {
                book.available = checkin;
                book.borrower_id = checkin ? null : currentUser.id_number;
                return true;
            }
        }
        return false;
    }

    static Boolean MovieTransaction(String title, Integer year, Boolean checkin) {
        for(Movie movie : movies) {
            if (movie.title.toLowerCase().equals(title)
                    && movie.year.equals(year) && (movie.available != checkin)) {
                movie.available = checkin;
                movie.borrower_id = checkin ? null : currentUser.id_number;
                return true;
            }
        }
        return false;
    }

    static Boolean LogIn(String id_number, String passwd) {
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
