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
        String command;
        Scanner scanner = new Scanner(System.in);

//        Book hp = new Book("Harry Potter", "JKR",1997);
//        Book lotr = new Book("Lord of the Rings", "JRT", 1954);
//        Book[] books = new Book[]{hp, lotr};

        System.out.println("Hello! Welcome to Biblioteca.");
        //ListBooks(books);

        System.out.println("Enter one of the following commands to get started:");
        ShowMenu();

        command = scanner.nextLine();
        while (!command.equals("quit") && !command.equals("Quit\n")) {
            HandleInput(command);
            command = scanner.nextLine();
        }
    }

    static void HandleInput(String command) {
        //eventually use objects for menu items that have callbacks stored in them?
        if (command.equals("List Books")) {
            ListBooks();
        } else if (command.contains("Check out")) {
            Pattern pattern = Pattern.compile("Check out ((?:\\w|\\s)*) by ((?:\\w|\\s)*) in (\\d*)");
            Matcher matcher = pattern.matcher(command);
            while (matcher.find()) {
//                System.out.println("group 1: " + matcher.group(1));
//                System.out.println("group 2: " + matcher.group(2));
//                System.out.println("group 3: " + matcher.group(3));
                Boolean success = CheckOutBook(matcher.group(1), matcher.group(2), Integer.parseInt(matcher.group(3)));
                if (success) {
                    System.out.println("Thank you! Enjoy the book");
                } else {
                    System.out.println("That book is not available.");
                }
            }

            //Check out ((?:\w|\s)*) by ((?:\w|\s)*) in (\d*)
        } else {
            System.out.println("Select a valid option!");
        }
    }

    //retest
    static void ListBooks() {
        for(Book book : books) {
            if (book.available) {
                System.out.println(book.title + " | " + book.author + " | " + book.year);
            }
        }
    }

    //refactor
    static void ShowMenu() {
        String menuStr = "List Books\n";
        System.out.print(menuStr);
    }

    static Boolean CheckOutBook(String title, String author, Integer year) {
        Boolean success = false;
        for(Book book : books) {
            if (book.title.equals(title) && book.author.equals(author) && book.year.equals(year)) {
//                book.setAvailable(false);
                success = true;
                book.available = false;
            }
        }
        return success;
    }
}
