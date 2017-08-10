/**
 * @author Dotan Huberman
 * @version (11/03/2017)
 */
import java.util.*;
import java.lang.*;
 public class Main
{
    public static void main(String[] argv)
    {
        final int HASH_SIZE = 10000; // maximum hash table size
        final int MAX_BOOKS = 10; // the maximum books allowed for a subscriber
        
        Scanner scan = new Scanner(System.in);
        HashTable booksTable = new HashTable(HASH_SIZE); //creates a new hash table with book code as a key
        HashTable subTable = new HashTable(HASH_SIZE); //creates a new hash table with ID as a key
        HashTable booksAmount = new HashTable(MAX_BOOKS); //creates a new hash table with number of books as a key
        HashNode entry; // a HashNode to save a pointer to HashNodes
        boolean mainFlag = true;// determine if the interface loop is over
       
        System.out.println("MAMAN 18 - Library\n_____________________");
        do  //Perform library operations  
        {    
            System.out.println("\nPlease write the wanted operation: (to Exit write @exit@, to print the current occupied cells in the tables write print)");
            String str; //will hold the user full operation command
            String first = ""; // the first word in the input string
            String second = ""; // the second word in the input string
            String third = ""; // the third word in the input string
            String forth = ""; // the forth word in the input string
            int numOfSpaces = 0; // the input string spaces counter
            str = scan.nextLine();
            for (int i = 0 ; i < str.length() ; i++) // moves char by char of the user command, counts the spaces amount and separates the words to the different variables
            {
                if (str.charAt(i) == ' ')
                    numOfSpaces++; 
                else
                {
                    if (numOfSpaces == 0)
                        first = first + str.charAt(i);
                    if (numOfSpaces == 1)
                        second = second + str.charAt(i);
                    if (numOfSpaces == 2)
                        third = third + str.charAt(i);
                    if (numOfSpaces == 3)
                        forth = forth + str.charAt(i);  
                }
            }
            if (first.charAt(0) == '+') // if the first word is + will insert new subscriber to the subscribers table
                subTable.insertNewSubscriber(third, Integer.parseInt(third),second);
            else if (first.charAt(0) == '-') { // if the first word is - will remove a subscriber from the subscribers table
                booksAmount.moveNode(subTable.getNode(third), "delete"); // removes the node from the books amount table
                subTable.removeSubscriber(third, booksTable); // remove the subscriber and his books from both subscribers table and books table
            }
            else if (numOfSpaces == 0) // checks if there are no spaces in the input it means its a query
            {
                if (first.charAt(0) == '!') { //query to print all the subscribers with the most books
                    System.out.println("\nThe subscribers who have the most books are:\n");
                    booksAmount.printMostBooks(); // print all the subscribers with the most books
                }
                else if (first.charAt(0) >= '1' && first.charAt(0) <= '9') // checks if the query starts with a number it means it is a subscriber ID
                    System.out.println(subTable.getBooks(first)); // prints all the books codes a subscriber has
                else if (first.equals("print")) // if the input is print
                {
                    booksTable.printBooksHashTable(); // prints the current occupied cell in the book table
                    subTable.printPersonsHashTable(); // prints the current occupied cell in the subscribers table
                }
                else if (!first.equals("exit")) // if the input is a string and not a number or "exit" it means its a book code
                    System.out.println("The Book: " + first + ", is borrowed by ID:" + booksTable.getOwnerId(first)); // prints the book code and it borrower
            }        
            else // there are more than 0 spaces
            {
                if (forth.charAt(0) == '+') // the first char is + , we want to add a book
                {
                    entry = subTable.insertBook(second, third); // adds a book to a subscriber book list through the subscribers table
                    booksAmount.moveNode(entry, "up"); // moves the subscriber node in the book amount table to a higher by 1 cell
                    booksTable.insertBookOwner(third, entry); // adds a node to books table with the person who borrowed the book
                }
                if (forth.charAt(0) == '-') // the first char is - , we want to remove a book
                {
                    booksTable.removeBookOwner(third); // removes a book from the subscriber book list through the subscribers table
                    entry = subTable.removeBook(second,third); // moves the subscriber node in the book amount table to a lower by 1 cell
                    booksAmount.moveNode(entry, "down"); // removes a node from the books table with the person who borrowed the book
                }
            }
            if (first.equals("exit"))
            {
                mainFlag = false;
            }    
        }while (mainFlag);
        System.out.println("Thank You, Good Bye!");
    }
}