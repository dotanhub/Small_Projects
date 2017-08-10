/**
 * MAMAN 11 EX1
 * Creates a WordList objects and generates a random word
 * @author Dotan Huberman
 */

import java.util.Random;

public class WordList {
    private String[] list;

    /**
     * Constructor of WordList - gets a list of words and keeps them in an array
     * @param strings a list of words
     */
    public WordList(String ... strings) {
        list = new String[strings.length];
        for (int i=0; i<strings.length; i++)
            list[i] = strings[i];
    }

    /**
     * generate a random word from the word list
     * @return a random word from the word list
     */
    public String randomWord(){
        Random rand = new Random();
        return list[rand.nextInt(list.length)];
    }
}
