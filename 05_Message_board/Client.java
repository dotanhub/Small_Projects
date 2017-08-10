import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Dotan Huberman on 28/06/2017.
 */
public class Client extends JFrame implements ActionListener{
    private JPanel upperLayout;
    private JTextField enterField;
    private JButton connect;
    private JButton disconnect;
    private JButton clear;
    private JTextArea displayArea;
    private DatagramSocket socket;
    private String hostName;

    public Client(){
        super("Client");

        //layout creation
        enterField = new JTextField("press to connect the server: ");
        enterField.setEditable(false);
        enterField.setBackground(Color.white);
        upperLayout = new JPanel();
        upperLayout.setLayout(new GridLayout(1,3));
        upperLayout.add(enterField);
        connect = new JButton("connect");
        connect.addActionListener(this);
        disconnect = new JButton("disconnect");
        disconnect.addActionListener(this);
        upperLayout.add(connect);
        upperLayout.add(disconnect);
        add(upperLayout,BorderLayout.NORTH);
        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);
        clear = new JButton("clear");
        clear.addActionListener(this);
        add(clear,BorderLayout.SOUTH);

        setSize(500,400);

        //asks the user what is the name of the host
        hostName = JOptionPane.showInputDialog("Write the wanted host name:\nhint: localhost");

        setVisible(true);

        try{
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * waits for packets and display a message from the server when gets it
     */
    public void waitForPackets(){
        while (true){
            try{
                byte[] data = new byte[100];
                DatagramPacket receivePacket = new DatagramPacket(data,data.length);

                socket.receive(receivePacket);
                String msgData = new String(receivePacket.getData(),
                        0,receivePacket.getLength());
                displayMessage("\n[" +
                        new SimpleDateFormat("dd.MM.yyyy, HH:mm:ss").format(new Date()) +
                        "]: " + msgData);
            }catch (IOException e){
                displayMessage(e + "\n");
                e.printStackTrace();
            }
        }
    }

    /**
     * display a message in the text area
     * @param messageToDisplay message to display
     */
    private void displayMessage(final String messageToDisplay){
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
     * when a button is pressed perform the wanted action - connect to server/disconect from server/clear text area
     * @param e action event
     */
    public void actionPerformed(ActionEvent e){
        //if the clear button is pressed - clears the screen
        if (e.getSource().equals(clear)){
            displayArea.setText(null);
        }
        else { //connect/disconnect is pressed - send a message with the wanted action to the server and display a message
            try {
                String message = e.getActionCommand();

                byte[] data = message.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(data,
                        data.length, InetAddress.getByName(hostName), 6666);

                socket.send(sendPacket);
                displayArea.append("\n" + message + "ed with the server\n");
                displayArea.setCaretPosition(displayArea.getText().length());
            } catch (IOException e1) {
                displayMessage(e1 + "\n");
                e1.printStackTrace();
            }
        }
    }
}
