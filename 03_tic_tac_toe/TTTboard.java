/**
 * MAMAN 12 EX2
 * TTTboard Class (Tic Tac Toe board)
 * @author Dotan Huberman
 */

public class TTTboard {

    private Sign[][] board = new Sign[3][3]; // a matrix of 3X3 with the signs: "X", "O", EMPTY
    private int moves; //amount of moves performed
    private Difficulty difficulty;
    private Sign playerSign;
    private Sign compSign;

    /**
     * constructor for TTTboard class
     * @param difficulty the wanted game difficulty
     * @param playerSign the player (not computer) sign
     */
    public TTTboard(Difficulty difficulty,Sign playerSign) {
        for (int i=0;i<3;i++)
            for (int j=0;j<3;j++)
                board[i][j] = Sign.EMPTY;

        this.moves = 0;
        this.difficulty = difficulty;
        this.playerSign = playerSign;

        if (playerSign == Sign.X)
            this.compSign = Sign.O;
        else this.compSign = Sign.X;
    }

    /**
     * return the sign of the wanted cell
     * @param row wanated cell row
     * @param col wanted cell column
     * @return the sign of the wanted cell
     */
    public Sign getSign(int row, int col){
        return board[row][col];
    }

    /**
     * sets a wanted sign to a specific cell
     * @param row cell row
     * @param col cell column
     * @param sign wanted sign to set
     */
    public void setSign(int row, int col, Sign sign){
        board[row][col] = sign;
        moves++;
    }

    /**
     * return the amount of moves played
     * @return the amount of moves played
     */
    public int getMoves(){
        return moves;
    }

    /**
     * return who wins (COMPUTER, PLAYER, NON)
     * @return who wins (COMPUTER, PLAYER, NON)
     */
    public Winner whoWins(){

        /* transfer the matrix to an array */
        Sign[] flatBoard = flattenBoard();

        /* check if someone has won */
        if (CompPlayer.compWon(flatBoard))
            return Winner.COMPUTER;
        else if (CompPlayer.playerWon(flatBoard))
            return  Winner.PLAYER;
        else
            return Winner.NON;
    }

    /**
     * sets the next move of the player on the board
     * @param row cell row
     * @param col cell column
     */
    public void playerNextMove(int row, int col){
        setSign(row,col,playerSign);
    }

    /**
     * sets the next move of the computer on the board
     */
    public void compNextMove(){
        int flatNextMove;

        /* transfer the matrix to an array */
        Sign[] flatBoard = flattenBoard();

        /* choose best next move according to difficulty */
        flatNextMove = CompPlayer.nextMove(flatBoard,moves,playerSign,difficulty);

        /* set the sign in the correct place in the matrix */
        for (int i=0;i<3;i++)
            for (int j=0;j<3;j++){
                flatNextMove--;
                if (flatNextMove == 0)
                    setSign(i,j,compSign);
            }
    }

    /**
     * return one dimentional array of the board matrix
     * @return one dimentional array of the board matrix
     */
    private Sign[] flattenBoard(){
        Sign[] flatBoard = new Sign[10]; // an array to save the board as one dimensional array
        int count = 1; //counter to help the transition from matrix to array

        /* transfer the matrix to array */
        for (int i=0;i<3;i++)
            for (int j=0;j<3;j++)
                flatBoard[count++] = board[i][j];

        return flatBoard;
    }
}
