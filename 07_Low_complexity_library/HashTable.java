/**
 * a class to crate a hash table.
 * 
 * @author Dotan Huberman
 */
public class HashTable
{
    final private int TABLE_SIZE; // will hold the size of the hash table
    private int size; // holds the amount of table cells that are occupied
    private HashNode[] table; // the hash table

    /**
     * constructor to create a hash table of a wanted size
     * @param tableSize wanted hash table size
     */
    public HashTable(int tableSize) {
        TABLE_SIZE = tableSize;
        size = 0;
        table = new HashNode[TABLE_SIZE];
        for (int i = 0; i < TABLE_SIZE; i++)
            table[i] = null;
    }

    /**
     * returns a hash value for a given string
     * @param st a string to get hash value for
     * @return hash value of the string
     */
    private int HashFunc(String st) {
        int hashVal = st.hashCode(); // auto generate hash code
        hashVal %= TABLE_SIZE;
        if (hashVal < 0) // if the hash code is smaller than 0 we need to make it positive
            hashVal += TABLE_SIZE;
        return hashVal;
    }

    /**
     * returns a subscriber ID which has a certain book
     * @param key book code
     * @return a subscriber ID
     */
    public String getOwnerId(String key) {
        int hash = (HashFunc(key) % TABLE_SIZE);

        while (table[hash] != null && !table[hash].getKey().equals(key)) // checks if the current cell has the right book code
            hash = (hash + 1) % TABLE_SIZE; // if not will move to a next hash table cell
        if (table[hash] == null) // if the cell has null the book is not borrowed
            return "The book is not borrowed";
        else // found the book and return its owner ID
            return Integer.toString(table[hash].getPerson().getId());
    }

    /**
     * returns a String list of all the books a person has
     * @param key a subscriber ID
     * @return a String list of all the books a person has
     */
    public String getBooks(String key) {
        int hash = (HashFunc(key) % TABLE_SIZE);

        while ((table[hash] != null && !table[hash].getKey().equals(key)) || table[hash]==null) // checks if the current cell has the right subscriber ID
            hash = (hash + 1) % TABLE_SIZE; // if not will move to a next hash table cell
        return table[hash].getPerson().printBookList();
    }

    /**
     * returns a node of a person (subscriber)
     * @param key a subscriber ID
     * @return a node of a person (subscriber)
     */
    public HashNode getNode(String key){
        int hash = (HashFunc(key) % TABLE_SIZE);
        while ((table[hash] != null && !table[hash].getKey().equals(key)) || table[hash]==null) // checks if the current cell has the right subscriber ID
            hash = (hash + 1) % TABLE_SIZE; // if not will move to a next hash table cell
        return table[hash];
    }

    /**
     * adds a book to the subscriber book list and returns the person node
     * @param key a subscriber ID
     * @param bookCode book code
     * @return the wanted person node or null in case it was not added
     */
    public HashNode insertBook(String key, String bookCode) {
        boolean added; // if true the book adding was succeeded
        int hash = (HashFunc(key) % TABLE_SIZE);
        while ((table[hash] != null && !table[hash].getKey().equals(key)) || table[hash]==null)// checks if the current cell has the right subscriber ID
            hash = (hash + 1) % TABLE_SIZE;// if not will move to a next hash table cell
        added = table[hash].getPerson().addBook(bookCode); // adds the book to the person book list and if succeeded will put true in added
        if (added)
            return table[hash];
        else
            return null;
    }

    /**
     * remove a book from a person list and returns the person node if succeeded
     * @param key subscriber ID
     * @param bookCode book code
     * @return the person node if succeeded otherwise null
     */
    public HashNode removeBook(String key, String bookCode) {
        int hash = (HashFunc(key) % TABLE_SIZE);
        Boolean removed;
        while ((table[hash] != null && !table[hash].getKey().equals(key)) || table[hash]==null) // checks if the current cell has the right subscriber ID
            hash = (hash + 1) % TABLE_SIZE; // if not will move to a next hash table cell
        removed = table[hash].getPerson().removeBook(bookCode); // removes the book from person book list and if succeeded will put true in removed
        if (removed)
            return table[hash];
        else
            return null;
    }

    /**
     * adds new subscriber(node) to the hash table
     * @param key subscriber ID
     * @param id subscriber ID
     * @param name subscriber name
     */
    public void insertNewSubscriber(String key,int id, String name) {
        if (size == TABLE_SIZE) {
            System.out.println("Hash Overflow");
            return;
        }
        int hash = (HashFunc(key) % TABLE_SIZE);
        while (table[hash] != null) // find an empty cell
            hash = (hash + 1) % TABLE_SIZE;
        if (table[hash] == null){
            table[hash] = new HashNode(key, id, name); //adds a new node
            size++;
        }
    }

    /**
     * removes a subscriber from subscribers table and from all the cells in the books table
     * @param key subscriber ID
     * @param booksTable the books table for removing all the subscriber books from there
     */
    public void removeSubscriber(String key, HashTable booksTable) {
        int hash = (HashFunc(key) % TABLE_SIZE);
        while ((table[hash] != null && !table[hash].getKey().equals(key)) || table[hash]==null) // checks if the current cell has the right subscriber ID
            hash = (hash + 1) % TABLE_SIZE; // if not will move to a next hash table cell
        for (int i = table[hash].getPerson().getNumOfBooks()-1;i>=0;i--) // deletes the subscriber book one by one from the books table
            booksTable.removeBookOwner(table[hash].getPerson().getBook(i));
        table[hash] = null; // deletes the subscriber entry from the subscribers table (there will be no link to the node so it will be deleted)
    }

    /**
     * insert a link to a person for a specific book in the books table
     * @param key book code
     * @param owner a node of a subscriber
     */
    public void insertBookOwner(String key, HashNode owner) {
        if (owner != null){
            if (size == TABLE_SIZE)
                System.out.println("Hash Overflow");
            int hash = (HashFunc(key) % TABLE_SIZE);
            while (table[hash] != null) // find an empty cell
                hash = (hash + 1) % TABLE_SIZE;
            if (table[hash] == null) {
                table[hash] = new HashNode(key, owner.getPerson());//adds a pointer to the wanted person that has the boos
                size++;
            }
        }
    }

    /**
     * removes the pointer of the person from a wanted book
     * @param key book code
     */
    public void removeBookOwner(String key) {
        int hash = (HashFunc(key) % TABLE_SIZE);
        while (table[hash] != null && !table[hash].getKey().equals(key)) // finds the right book cell
            hash = (hash + 1) % TABLE_SIZE;
        if (table[hash] == null)
            System.out.println("The following book is not borrowed");
        else
            table[hash] = null; // removes the pointer
    }

    /**
     * moves the node to the correct cell according to the number of book a subscriber has
     * @param node subscriber node
     * @param action the wanted action for the node ("up" - a book was added, "down" - a book was removed, "delete" - delete the node from the table)
     */
    public void moveNode(HashNode node, String action){
        if (node != null){
            int hash = node.getPerson().getNumOfBooks(); // the current number of books a subscriber has
            // removing the node from its previous position (linked list) in the number of books table
            if ((node.getPrev() != null) && (node.getNext() != null)) { // in case the node have a previous and next node it will remove it and connect the prev and next
                node.getPrev().setNext(node.getNext());
                node.getNext().setPrev(node.getPrev());
            }
            else if ((node.getPrev() == null) && (node.getNext() != null)) // in case the node have only next node it will remove it and set its next previous to null
                node.getNext().setPrev(null);
            else if ((node.getPrev() != null) && (node.getNext() == null)) { // in case the node have only previous node it will remove it and set its previous next to null. and need to update the pointer of the table
                if (action.equals("up"))  // if a book was added will update the pointer of the previous number of books (current number -1) in the table to the node's previous node
                    table[hash - 1] = node.getPrev();

                else if (action.equals("down"))  // if a book was removed will update the pointer of the previous number of books (current number +1) in the table to the node's previous node
                    table[hash + 1] = node.getPrev();

                else  // if action is "delete" - will update the pointer of the current number of books to the node's previous node, by that the node was deleted from the table
                    table[hash] = node.getPrev();
                node.getPrev().setNext(null); // sets the next of the previous node (who is now the head of the stack) to null
            }
            else { // in case the node is the only node in the current number of books (it's prev and next are null), will delete the node according to the wanted action
                if (action.equals("up"))
                    table[hash - 1] = null; // if a book was added will update the pointer of the previous number of books (current number -1) to null
                else if (action.equals("down"))
                    table[hash + 1] = null; // if a book was removed will update the pointer of the previous number of books (current number +1) to null
                else {
                    table[hash] = null; // if action is "delete" - will update the pointer of the current number of books to null
                }
            }
            if (action.equals("delete")) // in case we want to delete the node will end the method so it would not be added to another place
                return;
            //add the node to its new position (linked list) in the number of books table
            if (table[hash] == null) { // if the node is the only node in the new number of books it will add it there alone
                table[hash] = node;
                node.setNext(null);
                node.setPrev(null);
            }
            else { // if there are already nodes in the new number of books, will add the node to the stack and update the linked list
                table[hash].setNext(node);
                node.setPrev(table[hash]);
                table[hash] = node;
            }
        }
    }

    /**
     * prints all the subscribers IDs how have the most number of books
     */
    public void printMostBooks(){
        HashNode entry; // a node to move in the linked list of nodes
        int i = 9;// the index of the number of books table (9 means 10 books [the most possible])
        while ((table[i] == null) && (i>0))// will move the index of the table until it reached the list who have the most books
            i--;
        if (i==0) // if the index is 0 no body has books
            System.out.println("Nobody has books");
        else {
            entry = table[i]; // start from the head of the stack
            while (entry != null) { // while there are still nodes
                System.out.print(entry.getPerson().getId() + " ,"); // prints the ID of the subscriber
                entry = entry.getPrev(); // move to the previous mode
            }
        System.out.println();
        }
    }

    /**
     * a method to print the current books in the books hash table
     */
    public void printBooksHashTable() {
        System.out.println("\nBooks :");
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[i] != null) {
                System.out.print("\nBucket " + (i + 1) + " : ");
                System.out.print(table[i].getPerson().getId());
            }
        }
    }

    /**
     * a method to print the subscriber and its books in the subscribers hash table
     */
    public void printPersonsHashTable() {
        System.out.println("\nSubscribers Table:");
        for (int i = 0; i < TABLE_SIZE; i++) {
            if (table[i] != null) {
                System.out.print("\nBucket " + (i + 1) + " : ");
                System.out.print(table[i].getPerson().printBookList() + " (total: " + table[i].getPerson().getNumOfBooks() + ")");
            }
        }
    }
}

