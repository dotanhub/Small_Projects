/**
 * MAMAN 12 EX2
 * compPlayer Class
 * @author Dotan Huberman
 */

/**
 * LOGIC EXPLANATION:
 * this class relate the board as a one dimensional array the cells of the matrix represented as follows:
 * -----
 * -123-
 * -456-
 * -789-
 * -----
 * the parameter lines is an array of 3-cells arrays, each one represents a row in the matrix (horizontal, vertical, or diagonal)
 * the parameter linesState is an array of 2-cells array (for lines indexes 0-7) -
 * index 0 cell is the current line state (how many cells have the player/computer in the line):
 * -3 - the player have three cells
 * -2 - the player have two cells
 * -1 - the player have one cell
 * 0 - the line is empty
 * 1 - the computer have one cell
 * 2 - the computer have two cells
 * 3 - the computer have three cells
 * index 1 cell is the recommended next move cell in the current line (-1 if the line is full)
 *
 * for each difficulty first the algorithm checks and update all the lines states (checkAllLines method)
 * and then according to the difficulty runs an algorithm to choose the best move
 */

public class CompPlayer {

    final private static int [][] lines = {{1,2,3},{4,5,6},{7,8,9},{1,4,7},{2,5,8},{3,6,9},{1,5,9},{3,5,7}};
    private static int [][] linesState = new int[8][2];
    private static Sign compSign;
    private static Sign playerSign;

    /**
     * return a location (on one dimensional array that represents a board) for the best next move according to difficulty
     * @param board a one dimensional array that represents a Tic Tac Toe board
     * @param moves how much moves played so far
     * @param sign the current player (not computer) sign (X or O)
     * @param difficulty the wanted difficulty (easy, medium, hard)
     * @return a location (on one dimensional array that represents a board) for the best next move according to difficulty
     */
    public static int nextMove(Sign[] board,int moves,Sign sign, Difficulty difficulty){
        playerSign = sign;
        if (playerSign == Sign.X)
            compSign = Sign.O;
        else compSign = Sign.X;

        /* calls the right method according to difficulty */
        if (difficulty == Difficulty.EASY)
            return nextMoveEasy(board);
        else if (difficulty == Difficulty.MEDIUM)
            return nextMoveMedium(board);
        else
            return  nextMoveHard(board,moves);
    }

    /**
     * return a location (on one dimensional array that represents a board) for the best next move in medium level
     * @param board a one dimensional array that represents a Tic Tac Toe board
     * @return a location (on one dimensional array that represents a board) for the best next move in easy level(not so best ;))
     */
    private static int nextMoveEasy(Sign[] board){

        /* checks all the horizontal, vertical, or diagonal rows */
        checkAllLines(board);

        /* checks that a line is not full and returns an empty cell location*/
        for (int i=0;i<8;i++){
            if (linesState[i][1] != -1)
                return linesState[i][1];
        }
        return 0;
    }

    /**
     * return a location (on one dimensional array that represents a board) for the best next move in medium level
     * @param board a one dimensional array that represents a Tic Tac Toe board
     * @return a location (on one dimensional array that represents a board) for the best next move in medium level
     */
    private static int nextMoveMedium(Sign[] board){

        /* checks all the horizontal, vertical, or diagonal rows */
        checkAllLines(board);

        /* if i have two filled cells in a row will choose the third cell */
        for (int i=0;i<8;i++) {
            if ((linesState[i][0] == 2) && (linesState[i][1] != -1))
                return linesState[i][1];
        }

        /* if the player have two filled cells in a row choose the third */
        for (int i=0;i<8;i++) {
            if ((linesState[i][0] == -2) && (linesState[i][1] != -1))
                return linesState[i][1];
        }

        /* if there is an empty cell choose it */
        for (int i=0;i<8;i++){
            if (linesState[i][1] != -1)
                return linesState[i][1];
        }
        return 0;
    }

    /**
     * return a location (on one dimensional array that represents a board) for the best next move in hard level
     * @param board a one dimensional array that represents a Tic Tac Toe board
     * @return a location (on one dimensional array that represents a board) for the best next move in hard level (impossible to win)
     */
    private static int nextMoveHard(Sign[] board,int moves){

        /* checks all the horizontal, vertical, or diagonal rows */
        checkAllLines(board);

        /* if only one cell is filled and the center cell is empty choose the center */
        if ((moves == 1) && (board[5] == Sign.EMPTY))
            return 5;

        /* if i have two filled cells in a row will choose the third cell */
        for (int i=0;i<8;i++) {
            if ((linesState[i][0] == 2) && (linesState[i][1] != -1))
                return linesState[i][1];
        }

        /* if the player have two filled cells in a row choose the third */
        for (int i=0;i<8;i++) {
            if ((linesState[i][0] == -2) && (linesState[i][1] != -1))
                return linesState[i][1];
        }

        /* if three cells are filled on board and the player have 2 corners and i have the center choose side cell */
        if ((moves == 3) && (board[5] == compSign) && (board[1] == playerSign) && (board[9] == playerSign))
            return 2;
        if ((moves == 3) && (board[5] == compSign) && (board[3] == playerSign) && (board[7] == playerSign))
            return 2;

        /* if three cells are filled and the center is mine choose one of the cells in one of the player rows with priority to a corner */
        if ((moves == 3) && (board[5] == compSign)) {
            if ((linesState[0][0] == -1) & (linesState[3][0] == -1) & board[1] == Sign.EMPTY)
                return 1;
            if ((linesState[0][0] == -1) & (linesState[5][0] == -1) & board[3] == Sign.EMPTY)
                return 3;
            if ((linesState[2][0] == -1) & (linesState[5][0] == -1) & board[9] == Sign.EMPTY)
                return 9;
            if ((linesState[2][0] == -1) & (linesState[3][0] == -1) & board[7] == Sign.EMPTY)
                return 7;
            }

        /* if there is an empty corner which its lines are empty choose that corner */
        if ((linesState[0][0] == 0) & (linesState[3][0] == 0))
            return 1;
        if ((linesState[0][0] == 0) & (linesState[5][0] == 0))
            return 3;
        if ((linesState[2][0] == 0) & (linesState[5][0] == 0))
            return 9;
        if ((linesState[2][0] == 0) & (linesState[3][0] == 0))
            return 7;

        /* if the center is empty choose it */
        if (board[5] == Sign.EMPTY)
            return 5;

        /* if there are three empty cells in a row choose a corner of the row */
        for (int i=0;i<8;i++) {
            if (linesState[i][0] == 0)
                return linesState[i][1];
        }

        /* if there is an empty cell choose it */
        for (int i=0;i<8;i++){
            if (linesState[i][1] != -1)
                return linesState[i][1];
        }
        return 0;
    }

    /**
     * return an array of two numbers - the first is the line state, the second is the next recommended location (-1 if the row is full)
     * @param lineNum a number of line(row) of three cells in the board
     * @param line a three numbers array that holds the positions(indexes) of three cells in a row
     * @param board a one dimensional array that represents a Tic Tac Toe board
     * @return an array of two numbers - the first is the line state, the second is the next recommended location (-1 if the row is full)
     */
    private static int[] checkLine(int lineNum, int[] line, Sign[] board){
        int compSum = 0; // sum of computer filled cells in a row
        int playerSum = 0; // sum of computer filled cells in a row
        int[] lineState = new int[2];
        boolean freeLoc[] = {true,true,true}; // determine which cells in the raw are empty (true for empty)

        /* checks all the cells in the row and updates how many are filled and by whom */
        for (int i=0;i<3;i++) {
            if (board[line[i]] == compSign) {
                compSum++;
                freeLoc[i] = false;
            } else if (board[line[i]] == playerSign) {
                playerSum++;
                freeLoc[i] = false;
            }
        }

        /* according to the amount of filled cells updates the line state */
        if (compSum == 3)
            lineState[0] = 3;
        else if (playerSum == 3)
            lineState[0] = -3;
        else if (compSum == 2)
            lineState[0] = 2;
        else if (playerSum == 2)
            lineState[0] = -2;
        else if (playerSum == 1)
            lineState[0] = -1;
        else if (compSum == 1)
            lineState[0] = 1;
        else
            lineState[0] = 0;

        /* according to the current filled cells updates the recommended next position */
        if (freeLoc[0])
            lineState[1] = lines[lineNum][0];
        else if (freeLoc[2])
            lineState[1] = lines[lineNum][2];
        else if (freeLoc[1])
            lineState[1] = lines[lineNum][1];
        else
            lineState[1] = -1;

        return lineState;
    }

    /**
     * checks and updates all the line states
     * @param board a one dimensional array that represents a Tic Tac Toe board
     */
    private static void checkAllLines(Sign[] board){
        for (int i=0;i<8;i++)
            linesState[i] = checkLine(i,lines[i], board);
    }

    /**
     * return true if the computer won
     * @param board a one dimensional array that represents a Tic Tac Toe board
     * @return true if the computer won
     */
    public static boolean compWon(Sign[] board) {
        /* checks all the horizontal, vertical, or diagonal rows */
        for (int i=0;i<8;i++) {
            linesState[i] = checkLine(i, lines[i], board);
            if (linesState[i][0] == 3)
                return true;
        }
        return false;
    }

    /**
     * return true if the player won
     * @param board a one dimensional array that represents a Tic Tac Toe board
     * @return true if the player won
     */
    public static boolean playerWon(Sign[] board){

        /* checks all the horizontal, vertical, or diagonal rows */
        for (int i=0;i<8;i++) {
            linesState[i] = checkLine(i, lines[i], board);
            if (linesState[i][0] == -3)
                return true;
        }
        return false;
    }
}
