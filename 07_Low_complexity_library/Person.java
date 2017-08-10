/**
 * person object class.
 *
 * @author Dotan Huberman
 */
public class Person {

    final private int MAX_BOOK_AMOUNT; //maximum books allowed for one person
    private int id; //person ID
    private String name; // person last name
    private int numOfBooks; // amount of books a person have
    private String[] books; // an array of books codes that the person has

    /**
     * constructor
     * @param id person ID
     * @param name person last name
     */
    public Person(int id, String name) {
        this.id = id;
        this.name = name;
        this.numOfBooks = 0;
        MAX_BOOK_AMOUNT = 10;
        books = new String[MAX_BOOK_AMOUNT];
    }

    /**
     * getID
     * @return person ID
     */
    public int getId() {
        return id;
    }

    /**
     * get a certain book that a person has on a specific index in the books array
     * @param index index
     * @return book code in the specific index
     */
    public String getBook(int index){
        return books[index];
    }

    /**
     * returns the number of books a person has
     * @return the number of books a person has
     */
    public int getNumOfBooks(){
        return numOfBooks;
    }

    /**
     * adds a book code to the book list
     * @param book book code
     * @return true - if the book was added, false - otherwise
     */
    public boolean addBook(String book){
        if (numOfBooks < MAX_BOOK_AMOUNT){ //checks if the person already has the max allowed books
            books[numOfBooks] = book; // if not adds the book to the list
            numOfBooks++;
            return true;
        }
        else {
            System.out.println("the subscriber already have 10 book, the operation canceled");
            return false;
        }
    }

    /**
     * removes a book from the book list by its book code
     * @param book book code
     * @return true if the book was deleted, false otherwise
     */
    public boolean removeBook(String book){
        boolean flag = false; // if the book will be deleted will change to true
        for (int i = 0;i < numOfBooks;i++) // search all the book list
            if (books[i].equals(book)){ // if the book was found delete it by moving the last book to its place
                if (i == numOfBooks -1)
                    books[i] = null;
                else
                    books[i] = books[numOfBooks - 1];
            numOfBooks--;
            flag = true;
            }
        if (!flag)// if the book was not found
            System.out.println("the subscriber doesn't have the current book");
        return flag;
    }

    /**
     * returns a String list of all the books a person has
     * @return
     */
    public String printBookList(){
        String bookList = "The subscriber: " + id + " " + name + ", holds the books - ";
        for (int i=0;i<numOfBooks;i++)
            bookList = bookList + books[i] + ", ";
        return bookList.substring(0, bookList.length() - 2); //removes the last 2 chars of the string - " ,".
    }
}
