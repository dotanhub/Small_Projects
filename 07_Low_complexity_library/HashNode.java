/**
 * a class to create a node object for the hash table
 * 
 * @author Dotan Huberman
 */
public class HashNode
{
    private String key; // node key
    private HashNode next; // next node
    private HashNode prev; // previous node
    private Person person; // person object

    /**
     * Constructor for objects of class HashNode with an ID key
     * @param key wanted key(ID)
     * @param id person ID
     * @param name person last name
     */
    public HashNode(String key, int id, String name) {
        person = new Person(id, name);
        this.key = key;
        this.next = null;
        this.prev = null;
    }

    /**
     * Constructor for objects of class HashNode with a book code key
     * @param key wanted key(book code)
     * @param owner the person that borrowed the book
     */
    public HashNode(String key, Person owner) {
        person = owner;
        this.key = key;
        this.next = null;
        this.prev = null;
    }

    /**
     * returns the node key
     * @return the node key
     */
    public String getKey()
    {
        return key;
    }

    /**
     * returns the person object in the node
     * @return the person object in the node
     */
    public Person getPerson() {
        return person;
    }

    /**
     * returns the next node
     * @return the next node
     */
    public HashNode getNext()
    {
        return next;
    }

    /**
     * sets the next node
     * @param next wanted next node to be set
     */
    public void setNext(HashNode next)
    {
        this.next = next;
    }

    /**
     * returns the previous node
     * @return the previous node
     */
    public HashNode getPrev() {
        return prev;
    }

    /**
     * sets the previous node
     * @param prev wanted previous node to be set
     */
    public void setPrev(HashNode prev) {
        this.prev = prev;
    }
}
