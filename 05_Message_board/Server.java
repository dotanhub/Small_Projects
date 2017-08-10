
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;

/**
 * Created by Dotan Huberman on 28/06/2017.
 */
public class Server extends JFrame implements ActionListener {

    private JTextArea displayArea;
    private JTextField messageField;
    private DatagramSocket socket;
    private ArrayList<RegisteredClient> clientsList;// a list of clients (address and port) that are listening to the server messages

    public Server() {
        super("server");

        //creates GUI
        clientsList = new ArrayList<RegisteredClient>();
        messageField = new JTextField("Type message here");
        messageField.addActionListener(this);
        add(messageField,BorderLayout.NORTH);
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        setSize(500, 400);
        setVisible(true);

        try {
            socket = new DatagramSocket(6666);
        } catch (SocketException socketExeption) {
            socketExeption.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * waits for messages from the clients and performs the wanted operations
     */
    public void waitForPackets() {
        while (true) {
            try {
                byte[] data = new byte[100];
                DatagramPacket receivePacket = new DatagramPacket(data, data.length);

                socket.receive(receivePacket);
                RegisteredClient newClient =
                        new RegisteredClient(receivePacket.getAddress(), receivePacket.getPort());//create a object from the client packet which contains his address and port

                String msgData = new String(receivePacket.getData(),
                        0, receivePacket.getLength());

                switch (msgData) {
                    case "connect"://if the client wants to connect, checks its not already in the list and adds him to the list
                        boolean inTheList = false;
                        for (RegisteredClient reg : clientsList)
                            if (reg.equals(newClient))
                                inTheList = true;
                        if (!inTheList) {
                            clientsList.add(newClient);
                            displayMessage("\nThe client - " +
                                    "Host: " + receivePacket.getAddress() +
                                    "' port: " + receivePacket.getPort() +
                                    " is connected");
                        }
                        break;
                    case "disconnect"://if the client wants to disconnect remove him from the list
                        for (RegisteredClient reg : clientsList)
                            if (reg.equals(newClient)) {
                                clientsList.remove(reg);
                                displayMessage("\nThe client - " +
                                        "Host: " + receivePacket.getAddress() +
                                        "' port: " + receivePacket.getPort() +
                                        " is disconnected");
                                break;
                            }
                        break;
                    default:// if the client sent unknown massage display it to the server user
                        displayMessage("\nThe client - " +
                                "Host: " + receivePacket.getAddress() +
                                "' port: " + receivePacket.getPort() +
                                " sent unknown message");
                }

            } catch (IOException e) {
                displayMessage(e + "\n");
                e.printStackTrace();
            }
        }
    }

    /**
     * sends a wanted message to a wanted client
     * @param message a message
     * @param address wanted address to send to
     * @param port wanted port to send to
     * @throws IOException
     */
    private void sendPacketToClient(String message, InetAddress address, int port) throws IOException {

        byte[] data = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(data,
                data.length, address, port);

        socket.send(sendPacket);
        displayMessage("\nThe message sent to - host: " + address + ", port: " + port);
    }

    /**
     * display a message in the text area
     * @param messageToDisplay message to display
     */
    private void displayMessage(final String messageToDisplay) {
        SwingUtilities.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        displayArea.append(messageToDisplay);
                    }
                }
        );
    }

    /**
     * if the server user press ENTER sends the text in the text area to all the connected clients
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        for (RegisteredClient reg : clientsList) {
            try {
                String message = e.getActionCommand();
                sendPacketToClient(message,reg.getHost(),reg.getPort());
            } catch (IOException e1) {
                displayMessage(e1 + "\n");
                e1.printStackTrace();
            }
        }
    }
}
