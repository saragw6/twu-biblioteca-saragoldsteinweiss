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
        //ListBooks(books);

        System.out.println("Enter one of the following commands to get started:");
        ShowMenu();
    }

    static Boolean HandleInput(String command) {
        command = command.toLowerCase();
        //eventually use objects for menu items that have callbacks stored in them?
        if(command.equals("quit")) {
            return false;
        } else if (command.equals("list books")) {
            ListBooks();
        } else if (command.contains("check out")) {
            Pattern pattern = Pattern.compile("check out ((?:\\w|\\s)*) by ((?:\\w|\\s)*) in (\\d*)");
            Matcher matcher = pattern.matcher(command);
            while (matcher.find()) {
                Boolean success = BookTransaction(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)), false);
                if (success) {
                    System.out.println("Thank you! Enjoy the book");
                } else {
                    System.out.println("That book is not available.");
                }
            }
        } else if (command.contains("check in")) {
            Pattern pattern = Pattern.compile("check in ((?:\\w|\\s)*) by ((?:\\w|\\s)*) in (\\d*)");
            Matcher matcher = pattern.matcher(command);
            while (matcher.find()) {
                Boolean success = BookTransaction(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)), true);
                if (success) {
                    System.out.println("Thank you for returning the book.");
                } else {
                    System.out.println("That is not a valid book to return.");
                }
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

    //refactor?
    static void ShowMenu() {
        String menuStr = "List Books\nCheck out {book} by {author} in {year published}\nCheck in {book} by {author} in {year published}\nQuit\n";
        System.out.print(menuStr);
    }

    static Boolean BookTransaction(String title, String author, Integer year, Boolean checkin) {
        for(Book book : books) {
            if (book.title.toLowerCase().equals(title)
                    && book.author.toLowerCase().equals(author)
                    && book.year.equals(year) && (book.available != checkin)) {
//                book.setAvailable(false);
                book.available = checkin;
                return true;
            }
        }
        return false;
    }

}
