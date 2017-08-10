import javax.swing.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


/**
 * Created by Dotan Huberman on 26/6/17.
 */

public class ClientController {

    Socket socket = null;
    private ObjectOutputStream outToServer;
    private ObjectInputStream inFromServer;

    private BoardView view;
    private Board board;

    private boolean myTurn;
    private int myScore;
    private int opponentScore;
    private boolean gameOver;

    /**
     * constructor
     * @param host host name to connect to
     * @param port port name to connect to
     */
    public ClientController(String host,int port){
        gameOver = false;
        myTurn = false;
        view = null;

        try{
            socket = new Socket(host, port);
            outToServer = new ObjectOutputStream(socket.getOutputStream());
            inFromServer = new ObjectInputStream(socket.getInputStream());
        } catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    /**
     * sends a message to the server that contains a row and column numbers
     * @param row
     * @param col
     */
    public void sendMessageToServer(int row,int col){
        MessageToServer msg = new MessageToServer(row,col);
        try {
            outToServer.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * receive a board object from the server and updates the board instance
     */
    public void getBoardFromServer() {
        if (!socket.isConnected()){
            JOptionPane.showMessageDialog(null,"Connection to the server is lost, closing game");
            System.exit(0);
        }
        try {
            this.board = (Board)inFromServer.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * receive a message from the server and updates the client game state
     */
    public void getMessageToClient(){

        if (!socket.isConnected()){
            JOptionPane.showMessageDialog(null,"Connection to the server is lost, closing game");
            System.exit(0);
        }
        MessageToClient msg = new MessageToClient(); //todo check if not neccecery to create new instance
        try {
            msg = (MessageToClient) inFromServer.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        myTurn = msg.isMyTurn();
        myScore = msg.getMyScore();
        opponentScore = msg.getOpponentScore();
        gameOver = msg.isGameOver();
    }

    /**
     * updates the view according to the current board object, if there is no view creates a new one
     */
    public void updateView(){
        if (view == null)
            view = new BoardView(board,this,myTurn);
        else
            view.updateBoardView(board,this,myTurn,myScore,opponentScore);
    }

    /**
     * close the connection to the server
     */
    public void closeConnection() {
        view.closeWindow();
        try {
            outToServer.close();
            inFromServer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * display a summarized message to the user of the game and asks if he wants to play another game, returns true if the user wants to play another game
     * @return true if the user wants to play another game
     */
    public boolean anotherGame(){
        int whoWon;
        if (myScore>opponentScore)
            whoWon = 1;
        else if (myScore<opponentScore)
            whoWon = 2;
        else
            whoWon = 0;
        int choice = view.showEndGameMessage(whoWon);

        if (choice == 0)
            return false;
        else
            return true;
    }

    public boolean isMyTurn() {
        return myTurn;
    }

    public boolean isGameOver(){
        return gameOver;
    }

}

