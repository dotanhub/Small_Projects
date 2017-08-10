/**
 * MAMAN 11 EX1
 * Creates and manipulates Word object
 * @author Dotan Huberman
 */

public class Word {

    private char[] word;            // the word
    private char[] coveredWord;     // the hidden (with "_" letters) word
    private char[] lettersLeft = {'א','ב','ג','ד','ה','ו','ז','ח','ט','י','כ',
                                  'ל','מ','נ','ס','ע','פ','צ','ק','ר','ש','ת'};

    /**
     * Constructor of Word, gets a word and initialize a covered word with "_" letters
     * @param stringWord the word to hide
     */
    public Word(String stringWord){
        word = new char[stringWord.length()];
        coveredWord = new char[stringWord.length()];

        /* converts the input word to a char array and make a covered word char array */
        for (int i=0; i<stringWord.length(); i++){
            word[i] = stringWord.charAt(i);
            coveredWord[i] = '_';
        }
    }

    /**
     * returns the covered word in its current state
     * @return the covered word in its current state
     */
    public String getCoveredWord(){

        /* transform the current covered word from char array to string */
        String coveredWordString = "";
        for (int i=0; i<coveredWord.length; i++)
            coveredWordString = coveredWordString + coveredWord[i] + " ";
        return coveredWordString;
    }

    /**
     * returns true if the covered word has revealed, false otherwise
     * @return true if the covered word has revealed, false otherwise
     */
    public boolean isSolved(){
        for (int i=0; i<coveredWord.length; i++)
            if (coveredWord[i] == '_')
                return false;
        return true;
    }

    /**
     * checks and updates the covered word and the list of letters left if the letter is found in the word
     * @param letter a letter(char)
     */
    public void checkLetter(char letter){
        updateCoveredWord(letter);
        updateLettersLeft(letter);

        /* checks if the letter has a final letter in hebrew, if yes updates the covered word again with the final letter */
        if (hasFinalLetter(letter)) {
            letter--;
            updateCoveredWord(letter);
        }
        /* checks if the letter is a final letter in hebrew, if yes updates the covered word again with the final letter */
        if (isFinalLetter(letter)) {
            letter++;
            updateCoveredWord(letter);
        }
    }

    /**
     * updates the covered word if it contains the letter
     * @param letter a letter(char)
     */
    private void updateCoveredWord(char letter){
        for (int i=0; i<word.length; i++)
            if (word[i] == letter)
                coveredWord[i] = letter;
    }

    /**
     * put "x" instead of the letter if the left letters list contains it
     * @param letter
     */
    private void updateLettersLeft(char letter){
        for (int i=0; i<lettersLeft.length; i++)
            if ((lettersLeft[i] == letter) || ((lettersLeft[i] == letter+1)))
                lettersLeft[i] = 'x';
    }

    /**
     * checks if the letter has a also a final letter in hebrew
     * @param letter a letter
     * @return
     */
    private boolean hasFinalLetter(char letter){
        return ((letter == 'כ') || (letter == 'נ') || (letter == 'צ') || (letter == 'פ') || (letter == 'מ'));
    }

    /**
     * checks if the letter is a final letter in hebrew
     * @param letter a letter
     * @return
     */
    private boolean isFinalLetter(char letter){
        return ((letter == 'ך') || (letter == 'ן') || (letter == 'ץ') || (letter == 'ף') || (letter == 'ם'));
    }

    /**
     * returns a string of all the letters that weren't tried by the user separated by comma
     * @return a string of all the letters that weren't tried by the user
     */
    public String getLettersLeft(){
        String lettersLeftString = "האותיות שנשארו הן: ";
        for (int i=0; i<lettersLeft.length; i++)
            if (lettersLeft[i] != 'x')
                lettersLeftString = lettersLeftString + lettersLeft[i] + ",";
        return lettersLeftString.substring(0,lettersLeftString.length()-1);    // delete the last comma
    }
}

