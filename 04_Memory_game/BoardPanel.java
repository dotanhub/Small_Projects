import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Dotan Huberman on 26/6/17.
 */

public class BoardPanel extends JPanel implements ActionListener{

    private Board board;
    private ClientController controller;
    private boolean myTurn; //true if this is this client turn

    /**
     * constructor - crate a graphic representation of the board using JButtons and icons on JLabels
     * @param b board instance to create a view from
     * @param cont controller
     * @param myTurn true if it is this client turn
     */
    public BoardPanel(Board b,ClientController cont,boolean myTurn) {
        board = b;
        controller = cont;
        this.myTurn = myTurn;
        int dim=board.getBoardSize();

        setLayout(new GridLayout(dim, dim));
        ChangingButton buttons[][] = new ChangingButton[dim][dim];

        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                //if the card is not yet revealed puts a button
                if (!board.getRevealed(r, c)) {
                    buttons[r][c] = new ChangingButton(r,c);
                    buttons[r][c].addActionListener(this);
                    add(buttons[r][c]);
                } else { //the card is revealed puts an icon
                    ImageIcon image = getImage(board.getNum(r, c));
                    JLabel label = new JLabel();
                    label.setIcon(image);
                    label.setHorizontalAlignment((int)CENTER_ALIGNMENT);
                    label.setVerticalAlignment((int)CENTER_ALIGNMENT);
                    add(label);

                }
            }
        }
    }

    /**
     * returns a image icon according to the image number (name of file)
     * @param num image number (name of file)
     * @return a image icon according to the image number (name of file)
     */
    private ImageIcon getImage(int num){
        String fileName = num + ".jpg";
        ImageIcon imageIcon = new ImageIcon(fileName);
        Image image = imageIcon.getImage();
        Image newImg = image.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH);//image size
        return new ImageIcon(newImg);
    }

    public void actionPerformed(ActionEvent e){
        //if its this client turn, when a button is clicked, send a message to the server on which row and column it was clicked
        if (myTurn) {
            ChangingButton source;
            source = (ChangingButton) e.getSource();
            int row = source.getRow();
            int col = source.getCol();
            controller.sendMessageToServer(row, col);
        }
    }

}
