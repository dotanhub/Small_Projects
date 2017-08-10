import javax.swing.*;

/**
 * Created by Dotan Huberman on 26/6/17.
 */

public class BoardView{

    JFrame f;
    BoardPanel p;

    /**
     * constructor for the View of the board
     * @param b board instance
     * @param cont controller
     * @param myTurn true if it is this client turn
     */
    public BoardView(Board b,ClientController cont,boolean myTurn) {
        f = new JFrame("Memory Game");

        if (myTurn)
            f.setTitle("Memory Game - My Turn");
        else
            f.setTitle("Memory Game - Opponent's turn");
        f.setSize(600,600);
        p = new BoardPanel(b,cont,myTurn);
        f.add(p);
        f.setVisible(true);
    }

    /**
     * update the board by crating a new JPanel of the new board
     * @param b board instance
     * @param cont controller
     * @param myTurn true if it is this client turn
     * @param myScore this client score
     * @param opponentScore other client score
     */
    public void updateBoardView(Board b,ClientController cont,boolean myTurn,int myScore,int opponentScore){
        BoardPanel p1;
        p1 = new BoardPanel(b,cont,myTurn);
        f.add(p1);
        p1.revalidate();
        p1.repaint();
        f.remove(p);
        p = p1;
        if (myTurn)
            f.setTitle("Memory Game - My Turn   |||   " + "My score: " + myScore + " / Opponent score:" + opponentScore);
        else
            f.setTitle("Memory Game - Opponent's turn   |||   " + "My score: " + myScore + " / Opponent score:" + opponentScore);
    }

    /**
     * shows a dialog message when a game is over that asks if the player wants another game
     * @param whoWon 0 - draw, 1 - this client won, 2 - the other client won
     * @return 0 - if the player wants to exit, 1 - if the player wants another game
     */
    public int showEndGameMessage(int whoWon){
        Object[] options = { "Exit", "Play Again" };
        String str;
        if (whoWon == 1)
            str = "YOU WIN";
        else if (whoWon == 2)
            str = "YOU LOSE";
        else
            str = "ITS A DRAW";

        return JOptionPane.showOptionDialog(null, str + "\nDo you want to play another game?",
                "Game Over",JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,null, options, options[0]);
    }

    /**
     * close the game GUI
     */
    public void closeWindow(){
        f.setVisible(false);
        f.dispose();
    }
}

