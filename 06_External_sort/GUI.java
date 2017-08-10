import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.lang.reflect.InvocationTargetException;


/**
 * a gui for the external merge sort app
 * Created by Dotan Huberman on 05/07/2017.
 */
public class GUI extends JFrame implements ActionListener {
    private JPanel upperLayout;
    private JTextField enterField1;
    private JTextField enterField2;
    private JTextField enterField3;
    private JButton selectFile;
    private JButton generateFile;
    private JButton changeSettings;
    private JTextArea displayArea;

    private int pageSize = 110;
    private int bufferSize = 4;

    public GUI() {
        super("MAMAN 14");

        setTitle("External Merge Sort - by Dotan Huberman");
        enterField1 = new JTextField("press to generate a file for sorting");
        enterField1.setEditable(false);
        enterField1.setBackground(Color.white);
        enterField2 = new JTextField("press to change page size and buffer size");
        enterField2.setEditable(false);
        enterField2.setBackground(Color.white);
        enterField3 = new JTextField("press to choose a file to sort");
        enterField3.setEditable(false);
        enterField3.setBackground(Color.white);

        generateFile = new JButton("Generate File");
        changeSettings = new JButton("Change Settings");
        selectFile = new JButton("Select File");

        generateFile.addActionListener(this);
        changeSettings.addActionListener(this);
        selectFile.addActionListener(this);

        upperLayout = new JPanel();
        upperLayout.setLayout(new GridLayout(3, 2));
        upperLayout.add(enterField1);
        upperLayout.add(generateFile);
        upperLayout.add(enterField2);
        upperLayout.add(changeSettings);
        upperLayout.add(enterField3);
        upperLayout.add(selectFile);
        add(upperLayout, BorderLayout.NORTH);

        displayArea = new JTextArea();
        add(new JScrollPane(displayArea), BorderLayout.CENTER);

        setSize(500, 400);

        setVisible(true);
    }

    /**
     * display a message in the text area
     * @param messageToDisplay message to display
     */
    public void displayMessage(final String messageToDisplay) {
        try {
            SwingUtilities.invokeAndWait(
                    new Runnable() {
                        @Override
                        public void run() {
                            displayArea.append(messageToDisplay);
                        }
                    }
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    /**
     * get a file from the computer
     * @return a file from the computer
     */
    private File getFile()
    {
        File currentDir = new File(System.getProperty("user.dir"));
        JFileChooser fc = new JFileChooser(currentDir);
        fc.showOpenDialog(null);
        currentDir = fc.getSelectedFile();
        return fc.getSelectedFile();
    }

    /**
     * when a button is pressed perform the wanted action
     * @param e action event
     */
    public void actionPerformed(ActionEvent e) {
        GUI gui = this;

        if (e.getSource() == generateFile){
            int recordsToGenerate;
            int columnsAmount;
            recordsToGenerate = Integer.parseInt(JOptionPane.showInputDialog("how much records do you want to generate?"));
            columnsAmount = Integer.parseInt(JOptionPane.showInputDialog("how many columns would be in each record? (2 for current default settings)"));
            ExternalSort.generateInput(recordsToGenerate,columnsAmount);
        }

        if (e.getSource() == changeSettings){
            pageSize = Integer.parseInt(JOptionPane.showInputDialog("What is the size of the page? (in bytes),(multiplication of 11 for current record size)"));
            bufferSize = Integer.parseInt(JOptionPane.showInputDialog("how many pages has the buffer?"));
        }

        if (e.getSource() == selectFile) {
            Thread sortThread = new Thread() {
                public void run() {
                    File f = getFile();
                    if (f != null) {
                        ExternalSort.externalSort(f, pageSize, bufferSize, gui);
                    }
                }
            };
            sortThread.start();
        }
    }
}


