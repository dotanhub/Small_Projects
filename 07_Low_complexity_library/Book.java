
/**
 * Write a description of class Person here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Book
{
    // instance variables - replace the example below with your own
    private String code;
    private Person owner;

    /**
     * Constructor for objects of class Person
     */
    public Book(String code,Person owner)
    {
        this.code = code;
        this.owner = owner;
    }

    /**
     * An example of a method - replace this comment with your own
     *
     * @return     the sum of x and y 
     */
    public String getCode()
    {
        return code;
    }

    public void setBookCode(String code)
    {
        this.code = code;
    }

    /**
     *
     * @return
     */
    public Person getOwner() {
        return owner;
    }
}
