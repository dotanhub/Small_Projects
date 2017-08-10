import javax.swing.*;

/**
 * Main Server class
 * Created by Dotan Huberman on 28/06/2017.
 */
public class MessagesServer {
    public static void main(String[] args){
        Server app = new Server();
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.waitForPackets();
    }
}
