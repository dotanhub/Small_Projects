import javax.swing.*;

/**
 * Created by Dotan Huberman on 26/6/17.
 */

public class ChangingButton extends JButton{

    private final int row;
    private final int col;

    /**
     * constructor
     * @param r button row
     * @param c button column
     */
    public ChangingButton(final int r, final int c) {
        row = r;
        col = c;
    }
    public int getRow(){
        return row;
    }
    public int getCol(){
        return col;
    }

}