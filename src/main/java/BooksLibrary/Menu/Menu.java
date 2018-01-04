package BooksLibrary.Menu;

import BooksLibrary.Database.BookDAO;
import BooksLibrary.Entity.Book;
import BooksLibrary.Service.CommandManager;

import java.util.List;
import java.util.Scanner;

/**
 * Created by Юра on 29.12.2017.
 */
public class Menu {

    private static Scanner input = null;
    private static boolean run = true;

    public static void start() {
        printMessage("Welcome to The Library\n", "center");
        printMessage( "List of all commands:\n"
                + "\n* add - adds new book to the library. Syntax: add {author} {\"book_name\"}"
                + "\n* remove - removes the book from the library. Syntax: remove {book_name}"
                + "\n* edit - allows you to change the name of the book. Syntax: edit {book_name}"
                + "\n* all books - prints all books to console."
                + "\n* exit - stops program work.", "left" );

        input = new Scanner( System.in );


        while ( run ) {
            readCommands();
        }
    }


    public static void printMessage(String message, String align) {
        if ( align.equals("center"))
            System.out.println( "\t\t\t" + message );
        else if ( align.equals( "left" ) ) {
            System.out.println( message );
        }

    }


    private static void readCommands() {
        System.out.print("\nPlease enter your command: ");
        String command = input.nextLine();

        CommandManager.checkCommand( command );
    }


    public static void addBook(String name, String author) {
        Book book = new Book( name, author );

        if ( BookDAO.checkAvailability( book.getName(), book.getAuthor() ) )
            printMessage( "This book is already in the library", "left" );
        else {
            if ( BookDAO.addBook( book ) )
                printMessage( book.toString() + " was added", "left");
            else
                printMessage( "Error. " + book.toString() + " was not added", "left");
        }
    }


    public static void removeBook(String name) {
        Book book = getBookByName( name );

        if ( book != null) {
            if ( BookDAO.removeBook( book ) )
                printMessage( book.toString() + " was removed", "left");
            else
                printMessage( "Error. " + book.toString() + " was not removed", "left");
        }
    }


    public static void editBook(String name) {
        Book book = getBookByName( name );

        System.out.print("Enter new name: ");
        String newName = input.nextLine();
        while ( newName.isEmpty() ) {
            printMessage( "new name is empty. Try again:", "left");
            System.out.print("Enter new name: ");
            newName = input.nextLine();
        }

        if ( BookDAO.checkAvailability( newName, book.getAuthor() ) )
            printMessage( "This book is already in the library", "left" );
        else {
            if ( BookDAO.editBook( book, newName ) )
                printMessage( book.toString() + " was renamed", "left");
            else
                printMessage( "Error. " + book.toString() + " was not renamed", "left");
        }
    }

    private static Book getBookByName(String name) {
        List<Book> books = BookDAO.getBooksByName( name );

        if ( books.isEmpty() ) {
            printMessage( "book with this name are not in the library", "left");
            return null;
        }
        else if ( books.size() == 1 ) return books.get( 0 );
        else {
            String message = "";
            for (int i = 0; i < books.size(); i++) {
                message += "\n\t" + i + ". " + books.get( i ).getAuthor() + "\"" + books.get( i ).getName() + "\"";
            }

            printMessage( "we have few books with such name please choose one by typing a number of book:"
                    + message
                    + "\nor \"back\" to go back", "left");

            int index = makeChoice( books );
            return books.get( index );
        }
    }

    private static int makeChoice(List<Book> books) {
        System.out.print("$ ");
        String choice = input.nextLine();
        int index = -1;

        if ( choice.equals( "back" ) ) { printMessage( "", "left" ); }
        else {
            try {
                index = Integer.parseInt( choice );
            } catch (NumberFormatException e) {
                printMessage( "invalid input. Try again:", "left" );

                return makeChoice( books );
            }

            if ( index < 0 || index > books.size() - 1 ) {
                printMessage( "invalid input. Try again:", "left" );

                return makeChoice( books );
            }
        }

        return index;
    }


    public static void printAllBooks() {
        List<Book> books = BookDAO.getAllBooks();

        if ( books.isEmpty() ) printMessage( "There are no books in the library", "left");
        else {
            String message = "Our books :";
            for (int i = 0; i < books.size(); i++) {
                message += "\n\t\t" + (i + 1) + ". " + books.get( i ).getAuthor() + "\"" + books.get( i ).getName() + "\"";
            }

            printMessage( message, "left" );
        }
    }

    public static void exit() {
        printMessage( "Thank you for using our Library\nGood bye ;-)", "left" );

        input.close();
        run = false;
    }
}


