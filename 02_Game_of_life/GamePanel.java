/**
 * MAMAN 11 EX2
 * GamePanel class - creates a JPanel that will show the life matrix graphics
 * @author Dotan Huberman
 */

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel {

    private LifeMatrix matrix;

    /**
     * constructor of GamePanel object that is a Jpanel object
     * @param mat LifeMatrix object
     */
    GamePanel(LifeMatrix mat){
        this.matrix = mat;
    }

    /**
     * crates the graphics of the life matrix
     * @param g a Graphics object
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth(); //JFrame window width
        int height = getHeight(); //JFrame window height

        /* go through all the cells and if it has life adds a filled square, if not a hollow square */
        for (int i=0;i<matrix.getRows();i++){
            for (int j=0; j<matrix.getCols(); j++) {
                if (matrix.getSite(i,j)) { // cell has life
                    g.setColor(Color.green);
                    /* puts a filled square according to the size of the window and amount of rows and columns */
                    g.fillRect(5 + j * width/matrix.getCols(), 5 + i * height/matrix.getRows(),
                            width/matrix.getCols() - 10, height/matrix.getRows() - 10);
                }
                else{ // cell has no life
                    g.setColor(Color.black);
                    /* puts an unfilled square according to the size of the window and amount of rows and columns */
                    g.drawRect(5 + j * width/matrix.getCols(), 5 + i * height/matrix.getRows(),
                            width/matrix.getCols() - 10, height/matrix.getRows() - 10);
                }
            }
        }
    }
}

