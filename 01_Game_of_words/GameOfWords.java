/**
 * MAMAN 11 EX1
 * Main game class - GUI
 * @author Dotan Huberman
 */

import javax.swing.*;
import static java.lang.System.exit;

public class GameOfWords {

    public GameOfWords() {
        gui();
    }

    public void gui(){

        Object[] options = { "התחל מחדש", "צא" };   // options for buttons
        int choice = 0;                             // the chosen button (0 - restart the game)

        /* as long as the user is choosing to keep playing the game will restart */
        while (choice == 0) {
            String input;
            WordList wordList = new WordList("מם","קוף","שובך","קוץ","חבצלות","זר","ורד","הדס",
                    "דלת","גמל","בית","אוהל");      // creates a new WordList object

            Word word = new Word(wordList.randomWord());  // generates a random word from WordList
            int numOfGuess=0;

            /* keeps the user guessing letters until the covered word is revealed */
            while (!word.isSolved()) {

                /* shows the user a window with the covered word and list of the letters not guessed */
                input = JOptionPane.showInputDialog(null,"נחש אות מהמילה \n"
                                + word.getCoveredWord() + "\n" + word.getLettersLeft(),
                        "משחק ניחושי המילים", JOptionPane.QUESTION_MESSAGE);

                /* if the letter is valid of the hebrew letters checks and updates the word and list of left letters */
                if ((input.length() == 1) && (input.charAt(0) >= 'א' && input.charAt(0) <= 'ת'))
                    word.checkLetter(input.charAt(0));
                numOfGuess++; //for displaying number of guesses when the game is over
            }

            /* shows a window with the word and the number of guesses, and option to restart the game or exit */
            choice = JOptionPane.showOptionDialog(null, "המילה היא: "
                            + word.getCoveredWord() + "\n" + "מספר הניחושים הוא: " + numOfGuess,
                    "משחק ניחושי המילים", JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
        }
    }

    public static void main(String[] args) {
        new GameOfWords();
        exit(0);
    }
}
