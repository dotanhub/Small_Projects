/**
 * MAMAN 11 EX2
 * Creates and manipulates LifeMatrix object
 * @author Dotan Huberman
 */

import javax.swing.*;
import java.awt.*;
import java.util.Random;

class LifeMatrix extends JPanel{

    public enum SiteState{BIRTH, EXISTENCE, DEATH} // will be used for next generation life state

    private boolean[][] matrix; // the life matrix - true for "life", false for "no life"
    private int rows; // rows amount in the matrix
    private int cols; // columns amount in the matrix


    /**
     * Constructor for random life matrix
     * @param rows rows amount in the matrix
     * @param columns columns amount in the matrix
     */
    LifeMatrix(int rows, int columns) {

        Random rand = new Random();
        matrix = new boolean[rows][columns];

        this.rows = rows;
        this.cols = columns;

        /* for each cell puts random state of life */
        for (int i=0; i<rows; i++)
            for (int j=0; j<columns; j++)
                matrix[i][j] = rand.nextBoolean();
    }

    /**
     * constructor for a pre-state life matrix (all the cells will be in the same wanted state)
     * @param rows rows amount in the matrix
     * @param columns columns amount in the matrix
     * @param life the wanted life state of the cell - true for "life", false for "no life"
     */
    private LifeMatrix(int rows, int columns, boolean life) {
        matrix = new boolean[rows][columns];
        this.rows = rows;
        this.cols = columns;

        /* for each cell puts the wanted state of life */
        for (int i=0; i<rows; i++)
            for (int j=0; j<columns; j++)
                matrix[i][j] = life;
    }

    /**
     * copies a life matrix data into another life matrix
     * @param lifeMatrix the wanted life matrix to be copied to this life matrix
     */
    private void copyFullMatrix(LifeMatrix lifeMatrix) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = lifeMatrix.getSite(i, j);
            }
        }
    }

    /**
     * sets a single cell data to the wanted life state
     * @param row row cell index
     * @param col column cell index
     * @param life the wanted life state of the cell - true for "life", false for "no life"
     */
    private void setSite(int row, int col, boolean life){
        matrix[row][col] = life;
    }

    /**
     * gets a single cell life state
     * @param row row cell index
     * @param col column cell index
     * @return the current life state of the cell - true for "life", false for "no life"
     */
    boolean getSite(int row, int col){
        return matrix[row][col];
    }

    /**
     * get the amount of rows in the matrix
     * @return the amount of rows in the matrix
     */
    int getRows() {
        return rows;
    }

    /**
     * get the amount of columns in the matrix
     * @return the amount of columns in the matrix
     */
    int getCols() {
        return cols;
    }

    /**
     * advances the life matrix to its next generation state
     */
    void nextGen() {
        LifeMatrix nextGenMatrix = new LifeMatrix(rows, cols, false); // a new life matrix for the new generation
        SiteState siteState; // a next generation cell life state

        /* for each cell determine it next generation life state and put it in the next generation matrix */
        for (int i = 0; i < rows; i++) {
            System.out.print("\n");
            for (int j = 0; j < cols; j++) {
                siteState = geneticRule(i,j); // determine the next generation life state according to genetics rules
                switch (siteState){
                    case BIRTH:
                    case EXISTENCE: {
                        nextGenMatrix.setSite(i, j, true);
                        break;
                    }
                    case DEATH: {
                        nextGenMatrix.setSite(i, j, false);
                        break;
                    }
                }
            }
        }
        this.copyFullMatrix(nextGenMatrix); // copies the next generation matrix to the current matrix
    }

    /**
     * returns the next generation cell life state according to genetics rules
     * @param row row cell index
     * @param col column cell index
     * @return the next generation cell life state according to genetics rules (BIRTH, DEATH, EXISTENCE)
     */
    private SiteState geneticRule(int row, int col){
        if (!matrix[row][col]) { // if there is no life in the current cell
            if (liveNeighbours(row, col) == 3) // the necessary amount of neighbours that have life for the BIRTH state
                return SiteState.BIRTH;
            else
                return SiteState.DEATH;
        } else { // it is a live site
            if ((liveNeighbours(row, col) <= 1) || (liveNeighbours(row, col) >= 4)) // the necessary amount of neighbours that have life for the DEATH state
                return SiteState.DEATH;
            else // it has 2-3 live neighbours
                return SiteState.EXISTENCE;
        }
    }

    /**
     * returns the amount of neighbours cells that have life
     * @param row row cell index
     * @param col column cell index
     * @return the amount of neighbours cells that have life
     */
    private int liveNeighbours(int row, int col) {
        int counter = 0;

        /* go through all the neighbours cells without the cell itself */
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                /* checks if the current cell in the loop is not the wanted cell or out of matrix bound.*/
                /* if the checks are good, in case there is life in the current cell increase the counter */
                if ((!(i==0 && j==0)) && (!isEdge(row+i,col+j)) && (matrix[row+i][col+j]))
                    counter++;
            }
        }
        return counter;
    }

    /**
     * returns true if the wanted cell is an edge (out of matrix bounds), false otherwise
     * @param row row cell index
     * @param col column cell index
     * @return true if the wanted cell is an edge (out of matrix bounds), false otherwise
     */
    private boolean isEdge(int row, int col){
        return ((row<0) || (row>=rows) || (col<0) || (col>=cols));
    }
}
