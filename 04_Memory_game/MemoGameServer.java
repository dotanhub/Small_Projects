import javax.swing.*;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Main Server class for Memory game
 * Created by Dotan Huberman on 26/6/17.
 */

public class MemoGameServer {
    public static void main(String[] args)
    {
        ServerSocket srv = null;
        boolean listening = true;

        try {
            srv = new ServerSocket(7777);
            System.out.println("Server's ready");
            Socket socket1 = null;
            Socket socket2 = null;
            int numOfCards = 0;

            //allow the user activating the server to choose how much different cards will be in the game
            while (!(numOfCards == 2 || numOfCards == 8 || numOfCards == 18 || numOfCards == 32 ))
                    numOfCards = Integer.parseInt(JOptionPane.showInputDialog("Enter the wanted number of different cards\n* 2,8,18,32"));

            //for each two clients that connect to the server creates a game thread
            while(listening) {
                socket1 = srv.accept();
                System.out.println("\nFirst player connected.\nWaiting for second player...");
                socket2 = srv.accept();
                System.out.println("\nSecond player connected.\nInitiating game!\n");

                new GameThread(socket1,socket2,numOfCards).start();
            }
        }
        catch(InterruptedIOException e) {
            System.out.println("Time out");
        }
        catch(IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}