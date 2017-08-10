import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Dotan Huberman on 26/6/17.
 */

public class Board implements Serializable { //todo not sure why implements Serializable

    private int boardSize; //amount of rows and columns in the board (amount of cards = boardSize*boardSize)
    private int imgAmount; //the amount of different cards wanted in the board
    private int[][] board; //matrix contains 2 of each picture name
    private boolean[][] isRevealed; //a matrix by the size of the board that says if a card was flipped or not

    /**
     * constructor for Board object - creates a random matrix that contains 2 of each picture name
     *
     * @param imgAmount the amount of different cards wanted in the board
     */
    public Board(int imgAmount) {
        this.imgAmount = imgAmount;
        boardSize = (int) Math.sqrt(imgAmount * 2);
        board = new int[boardSize][boardSize];
        isRevealed = new boolean[boardSize][boardSize];

        ArrayList<Integer> imgList = new ArrayList<>();
        for (int i = 0; i < imgAmount; i++) {
            imgList.add(i);
            imgList.add(i);
        }
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++) {
                int randNum = (int) (Math.random() * (imgList.size()));
                board[i][j] = imgList.remove(randNum);
            }
    }

    /**
     * copy constructor
     *
     * @param oldBoard the wanted board to be copied
     */
    public Board(Board oldBoard) {
        this.boardSize = oldBoard.boardSize;
        this.imgAmount = oldBoard.imgAmount;
        this.board = new int[boardSize][boardSize];
        this.isRevealed = new boolean[boardSize][boardSize];

        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                this.board[i][j] = oldBoard.board[i][j];
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                this.isRevealed[i][j] = oldBoard.isRevealed[i][j];
    }

    /**
     * change a card to flipped state
     *
     * @param row card row
     * @param col card column
     */
    public void reveal(int row, int col) {
        this.isRevealed[row][col] = true;
    }

    /**
     * change a card back to not revealed state
     *
     * @param row card row
     * @param col ard column
     */
    public void unReveal(int row, int col) {
        this.isRevealed[row][col] = false;
    }

    /**
     * returns true if the two card are equal
     *
     * @param row1 first card row
     * @param col1 first card column
     * @param row2 second card row
     * @param col2 second car column
     * @return true if the two card are equal
     */
    public boolean isCorrect(int row1, int col1, int row2, int col2) {
        return board[row1][col1] == board[row2][col2];
    }

    /**
     * returns the image number (file name)
     *
     * @param row card row
     * @param col card column
     * @return the image number (file name)
     */
    public int getNum(int row, int col) {
        return board[row][col];
    }

    /**
     * returns true if the card is flipped
     *
     * @param row card row
     * @param col card column
     * @return true if the card is flipped
     */
    public boolean getRevealed(int row, int col) {
        return isRevealed[row][col];
    }

    public int getImgAmount() {
        return imgAmount;
    }

    public int getBoardSize() {
        return boardSize;
    }

}