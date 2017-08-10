/**
 * MAMAN 12 EX2
 * JPanel Class
 * @author Dotan Huberman
 */

import javax.swing.*;
import java.awt.*;

/**
 * Created by DP27066 on 28/04/2017.
 */
public class XGamePanel extends JPanel{

    private TTTboard board;

    /**
     * constructor for the panel class
     * @param board a board object to show graphically
     */
    XGamePanel(TTTboard board){
        this.board = board;
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth(); //JFrame window width
        int height = getHeight(); //JFrame window height

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));

        /* draw the lines of the matrix */
        g2.drawLine(width / 3, 0, width / 3, height);
        g2.drawLine(width * 2 / 3, 0, width * 2 / 3, height);
        g2.drawLine(0, height / 3, width, height / 3);
        g2.drawLine(0, height * 2 / 3, width, height * 2 / 3);

        /* draw the "X" and "O" according to the current board status */
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getSign(i, j) == Sign.X) {
                    g2.setColor(Color.RED);
                    g2.drawLine(width / 12 + width / 3 * j, height / 12 + height / 3 * i
                            , width * 3 / 12 + width / 3 * j, height * 3 / 12 + height / 3 * i);
                    g2.drawLine(width / 12 + width / 3 * j, height * 3 / 12 + height / 3 * i
                            , width * 3 / 12 + width / 3 * j, height / 12 + height / 3 * i);
                }
                else if (board.getSign(i, j) == Sign.O) {
                    g2.setColor(Color.GREEN);
                    g2.drawOval(width / 12 + width / 3 * j, height / 12 + height / 3 * i
                            , width/6, height/6);
                }
            }
        }
    }
}
