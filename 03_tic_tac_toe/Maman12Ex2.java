/**
 * MAMAN 12 EX2
 * Main game class
 * @author Dotan Huberman
 */

import javax.swing.*;
import java.util.Random;

import static java.lang.System.exit;

/**
 * Created by DP27066 on 28/04/2017.
 */

enum Sign{EMPTY,X,O}
enum Difficulty{EASY,MEDIUM,HARD}
enum Winner{NON,PLAYER,COMPUTER}

public class Maman12Ex2 {

    public static void main(String[] args) {

        Random rand = new Random();
        int choice = 0;
        Difficulty difficulty = Difficulty.EASY;
        Sign playerSign = Sign.EMPTY;

        /* as long as the user is choosing to keep playing the game will restart */
        while (choice == 0) {

            /* the user choose and set difficulty */
            int difInt = JOptionPane.showOptionDialog(null,
                    "Choose Difficulty",
                    "Tic-Tac-Toe",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"Easy", "Medium", "Hard"},
                    "default");
            switch (difInt) {
                case 0: {
                    difficulty = Difficulty.EASY;
                    break;
                }
                case 1: {
                    difficulty = Difficulty.MEDIUM;
                    break;
                }
                case 2:
                    difficulty = Difficulty.HARD;
            }

            /* the user choose and set his sign (X or O) */
            int signInt = JOptionPane.showOptionDialog(null,
                    "Choose Sign: X or O",
                    "Tic-Tac-Toe",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new String[]{"X", "O"},
                    "default");
            switch (signInt) {
                case 0: {
                    playerSign = Sign.X;
                    break;
                }
                case 1:
                    playerSign = Sign.O;
            }

            /* create a new board object */
            TTTboard board = new TTTboard(difficulty, playerSign);

            JFrame window = new JFrame();
            XGamePanel gamePanel = new XGamePanel(board);

            Winner winner = Winner.NON;
            int start = rand.nextInt(2); // determine randomly who will start

            while (winner == Winner.NON) {
                int row;
                int col;

                window.add(gamePanel);
                window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                window.setTitle("Tic-Tac-Toe");
                window.setSize(700, 700);
                window.setVisible(true);

                /*  if the computer starts will activate the computer move before the player */
                if (start == 0) {
                    board.compNextMove();
                    window.repaint();
                }

                /* if after the computer move he didnt win and there are moves left, gets and activate the player move */
                if (board.whoWins() == Winner.NON && board.getMoves() < 9){
                    do {
                        String input = "";
                        /* shows the user a window to choose row */
                        while (!((input.length() == 1) && (input.charAt(0) >= '1' && input.charAt(0) <= '3'))) {
                            input = JOptionPane.showInputDialog(null, "Enter row (1-3)",
                                    "Tic-Tac-Toe", JOptionPane.QUESTION_MESSAGE);
                        }
                        row = Integer.parseInt(input);

                        input = "";

                        /* shows the user a window to choose column */
                        while (!((input.length() == 1) && (input.charAt(0) >= '1' && input.charAt(0) <= '3'))) {
                            input = JOptionPane.showInputDialog(null, "Enter column (1-3)",
                                    "Tic-Tac-Toe", JOptionPane.QUESTION_MESSAGE);
                        }
                        col = Integer.parseInt(input);

                        /* if the user choose a filled row a message will pop-up and he will have to choose again */
                        if (board.getSign(row-1,col-1) != Sign.EMPTY)
                            JOptionPane.showMessageDialog(null, "THIS CELL IS NOT EMPTY",
                                    "Tic-Tac-Toe", JOptionPane.OK_OPTION,
                                    null);
                    }
                    while (board.getSign(row-1,col-1) != Sign.EMPTY);

                    board.playerNextMove(row - 1, col - 1);
                }

                /* if the player starts will activate the computer move after the player */
                if (start == 1) {
                    board.compNextMove();
                }

                window.repaint();

                winner = board.whoWins();

                /* if someone wins will tell the user and stop the current game*/
                if (winner != Winner.NON) {
                    String win;
                    if (winner == Winner.COMPUTER)
                        win = "computer";
                    else
                        win = "player";
                    JOptionPane.showMessageDialog(null, "THE WINNER IS: " + win,
                            "Tic-Tac-Toe", JOptionPane.OK_OPTION,
                            null);
                    break;
                }

                /* if no more moves are left will call a draw and stop the current game */
                if (board.getMoves() > 8) {
                    JOptionPane.showMessageDialog(null, "ITS A DRAW",
                            "Tic-Tac-Toe", JOptionPane.OK_OPTION,
                            null);
                    break;
                }

            }

            /* after the current game is finished ask the player to start over or exit */
            choice = JOptionPane.showOptionDialog(null, "Do you want to play another game?",
                    "Tic-Tac-Toe", JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Yes", "No"}, "default");
            window.setVisible(false);
        }
        exit(0);
    }
}


