package BooksLibrary.Service;

import BooksLibrary.Menu.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Юра on 29.12.2017.
 */
public class CommandManager {

    private static List<String> commands;


    static {
        commands = new ArrayList<String>(5);

        commands.add( "add" );
        commands.add( "remove" );
        commands.add( "edit" );
        commands.add( "all books" );
        commands.add( "exit" );
    }


    public static void checkCommand(String command) {
        int count = 0;
        int indexCom = -1;

        for (int i = 0; i < commands.size(); i++) {
            if ( command.contains( commands.get( i ) ) ) {
                indexCom = i;
                count++;
            }
        }

        if ( count == 0 ) Menu.printMessage( "You have entered an unknown command", "left" );
        else if ( count > 1 ) Menu.printMessage( "You entered the command incorrectly", "left" );
        else
            selectCommand( indexCom, command );
    }


    private static void selectCommand(int index, String command) {
        switch ( index ) {

            case 0:
                parseAdd( index, command );
                break;
            case 1:
                parseRemove( index, command );
                break;
            case 2:
                parseEdit( index, command );
                break;
            case 3:
                parseAllBooks( index, command );
                break;
            default:
                parseExit( index, command );
                break;
        }
    }




    public static void parseExit(int index, String command) {

        command = command.replaceAll( " ", "" ).toLowerCase();

        if ( command.equals( commands.get( index ) ) ) Menu.exit();
        else Menu.printMessage( "You entered the command incorrectly. Maybe you wanted to enter \"exit\"", "left" );
    }


    public static void parseAdd(int index, String command) {
        String[] half = command.split( "\"" );

        if ( half.length > 2 ) Menu.printMessage( "You entered the command incorrectly. Syntax: add {author} {\"book_name\"}", "left" );
        else {
            String[] parts = half[0].split( " " );

            if ( !parts[0].equals( commands.get( index ) ) || parts.length == 1 ) Menu.printMessage( "You entered the command incorrectly. Syntax: add {author} {\"book_name\"}", "left" );
            else {

                StringBuilder sb = new StringBuilder("");
                for (int i = 1; i < parts.length; i++) {
                    sb.append( parts[i] );

                    if (i < parts.length - 1)
                        sb.append(" ");
                }

                String name = half[1];
                String author = sb.toString();

                Menu.addBook( name, author );
            }
        }
    }


    public static void parseRemove(int index, String command) {
        String[] half = command.split( "remove " );

        if ( half.length != 2 ) Menu.printMessage( "You entered the command incorrectly. Syntax: remove {book_name}", "left" );
        else {
            String name = half[1];

            Menu.removeBook( name );
        }
    }


    public static void parseEdit(int index, String command) {
        String[] half = command.split( "edit " );

        if ( half.length != 2 ) Menu.printMessage( "You entered the command incorrectly. Syntax: edit {book_name}", "left" );
        else {
            String name = half[1];

            Menu.editBook( name );
        }
    }


    public static void parseAllBooks(int index, String command) {

        command = command.replaceAll( " ", "" ).toLowerCase();

        if ( command.equals( commands.get( index ).replaceAll( " ", "") ) ) Menu.printAllBooks();
        else Menu.printMessage( "You entered the command incorrectly. maybe you wanted to enter \"all books\"", "left" );
    }
}


