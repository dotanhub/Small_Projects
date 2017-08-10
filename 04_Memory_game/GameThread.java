import java.io.*;
import java.net.Socket;

/**
 * a GameThread class that contains the logic for each two player game
 * Created by Dotan Huberman on 26/6/17.
 */

public class GameThread extends Thread {
    private Socket socket1 = null;
    private Socket socket2 = null;

    private ObjectOutputStream outToClient1;
    private ObjectInputStream inFromClient1;
    private ObjectOutputStream outToClient2;
    private ObjectInputStream inFromClient2;

    private Board board;
    boolean playerOneTurn = true;

    private boolean gameOver = false;

    private int playerScore1 = 0;
    private int playerScore2 = 0;

    /**
     * constructor
     * @param s1 first socket
     * @param s2 second socket
     * @param numOfCards the amount of different cards wanted in the board
     */
    public GameThread(Socket s1, Socket s2,int numOfCards) {
        socket1 = s1;
        socket2 = s2;
        board = new Board(numOfCards);

        try {
            outToClient1 = new ObjectOutputStream(socket1.getOutputStream());
            inFromClient1 = new ObjectInputStream(socket1.getInputStream());
            outToClient2 = new ObjectOutputStream(socket2.getOutputStream());
            inFromClient2 = new ObjectInputStream(socket2.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {

            //sends the clients the startup game state
            outToClient1.writeObject(new MessageToClient(true,playerScore1,playerScore2,gameOver));
            outToClient2.writeObject(new MessageToClient(false,playerScore2,playerScore1,gameOver));
            transmitBoardToClients();

            while (!gameOver) {
                if (playerOneTurn) {

                    //receive client actions
                    playerTurn(inFromClient1);
                    if (!gameOver) {
                        //send the clients the new game state
                        outToClient1.writeObject(new MessageToClient(false, playerScore1, playerScore2,gameOver));
                        outToClient2.writeObject(new MessageToClient(true, playerScore2, playerScore1,gameOver));
                    }
                }
                else{

                    //receive client actions
                    playerTurn(inFromClient2);
                    if (!gameOver) {
                        //send the clients the new game state
                        outToClient2.writeObject(new MessageToClient(false, playerScore2, playerScore1,gameOver));
                        outToClient1.writeObject(new MessageToClient(true, playerScore1, playerScore2,gameOver));
                    }
                }
            }

            //send the clients the new game state (game is over)
            outToClient2.writeObject(new MessageToClient(false, playerScore2, playerScore1,gameOver));
            outToClient1.writeObject(new MessageToClient(false, playerScore1, playerScore2,gameOver));

            socket1.close();
            socket2.close();
            inFromClient1.close();
            inFromClient2.close();
            outToClient1.close();
            outToClient2.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * send the current Board object to the clients
     */
    private void transmitBoardToClients(){
        try {
            outToClient1.writeObject(new Board(board));
            outToClient2.writeObject(new Board(board));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * contains the logic of a single client turn
     * @param inFromClient the input stream of the wanted client
     */
    private void playerTurn(ObjectInputStream inFromClient){
        MessageToServer msg;

        try {
            //wait for first card selected by the client
            msg = (MessageToServer)inFromClient.readObject();
            int row1 = msg.getRow();
            int col1 = msg.getCol();
            board.reveal(row1, col1);

            //send updated board to clients
            transmitBoardToClients();

            //wait for second card selected by the client
            msg = (MessageToServer)inFromClient.readObject();
            int row2 = msg.getRow();
            int col2 = msg.getCol();
            board.reveal(row2, col2);

            //send updated board to clients
            transmitBoardToClients();

            //if the cards are the same adds a point to client's score, otherwise flip them back after 3 seconds
            if (!(board.isCorrect(row1, col1, row2, col2))) {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                board.unReveal(row1, col1);
                board.unReveal(row2, col2);
            } else {
                if (playerOneTurn) {
                    playerScore1++;
                }
                else{
                    playerScore2++;
                }
            }

            //send updated board to clients
            transmitBoardToClients();

            playerOneTurn = !playerOneTurn;

            //check if game is over
            if ((playerScore1+playerScore2)==board.getImgAmount()) {
                gameOver = true;
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch(ClassNotFoundException e){ //todo fix it!
             e.printStackTrace();
        }
    }
}



