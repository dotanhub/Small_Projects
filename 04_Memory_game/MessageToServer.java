import java.io.Serializable;

/**
 * Created by DP27066 on 27/06/2017.
 */
public class MessageToServer implements Serializable{

    private int row;
    private int column;

    /**
     * constructor for messageToServer object
     * @param row row
     * @param column column
     */
    public MessageToServer(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return column;
    }
}
