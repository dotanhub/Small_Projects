import javax.swing.*;

/**
 * Main client class
 * Created by Dotan Huberman on 28/06/2017.
 */
public class MessagesClient {
    public static void main(String[] args){
        Client app = new Client();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.waitForPackets();//waits for packets from the server
    }
}
