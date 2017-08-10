import java.io.Serializable;

/**
 * Created by Dotan Huberman on 26/6/17.
 */

public class MessageToClient implements Serializable{
    private boolean myTurn;
    private int myScore;
    private int opponentScore;
    private boolean gameOver;

    /**
     * empty constructor for MessageToClient object
     */
    public MessageToClient() {
        this.myTurn = false;
        this.myScore = 0;
        this.opponentScore = 0;
        this.gameOver = false;
    }

    /**
     * constructor for MessageToClient object
     * @param myTurn true if it is this client turn
     * @param myScore the score of this client
     * @param opponentScore the score of the other client
     * @param gameOver true if the game is over
     */
    public MessageToClient(boolean myTurn, int myScore, int opponentScore, boolean gameOver) {
        this.myTurn = myTurn;
        this.myScore = myScore;
        this.opponentScore = opponentScore;
        this.gameOver = gameOver;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getOpponentScore() {
        return opponentScore;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
