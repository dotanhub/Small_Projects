/**
 * MAMAN 11 EX2
 * Main GameOfLife class - GUI
 * @author Dotan Huberman
 */

import javax.swing.JOptionPane;
import javax.swing.JFrame;
import static java.lang.System.exit;

public class GameOfLife {

    private GameOfLife() {
        gui();
    }

    private void gui(){
        final int ROWS_AMOUNT = 10; //the wanted amount of rows in the life matrix (1-60)
        final int COLUMNS_AMOUNT = 10; //the wanted amount of columns in the life matrix (1-60)

        LifeMatrix mat = new LifeMatrix(ROWS_AMOUNT,COLUMNS_AMOUNT);
        JFrame window = new JFrame();
        GamePanel gamePanel = new GamePanel(mat);

        window.add(gamePanel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle("משחק החיים");

        /* set the window size according to the number of rows and columns */
        window.setSize( Math.min(700,700*COLUMNS_AMOUNT/ROWS_AMOUNT),Math.min(700,700*ROWS_AMOUNT/COLUMNS_AMOUNT));
        window.setVisible(true);

        int choice = JOptionPane.YES_OPTION;

        while (choice == JOptionPane.YES_OPTION){
            choice = JOptionPane.showConfirmDialog(null,"האם ברצונך להמשיך (לעבור לדור הבא)?" ,"משחק החיים",
                                                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            mat.nextGen(); // changes the life matrix to its next generation form
            window.repaint();
        }
        window.setVisible(false);
    }

    public static void main(String[] args) {
        new GameOfLife();
        exit(0);
    }
}
