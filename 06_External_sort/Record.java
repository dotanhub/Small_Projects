/**
 * Record class represents a record in a database
 * Created by Dotan Huberman on 05/07/2017.
 */
public class Record implements Comparable<Record>{

    private int columnsInRecord; //amount of columns in a record
    private int columnSize; //column size in bytes
    private int primary; //the record index
    private String otherColumns; //a string of all other columns except the index

    /**
     * constructor
     * @param columnsInRecord amount of columns in a record
     * @param columnSize column size in bytes
     * @param primary the record index
     */
    public Record(int columnsInRecord, int columnSize, int primary) {
        this.columnsInRecord = columnsInRecord;
        this.columnSize = columnSize;
        this.primary = primary;
        this.otherColumns = "";
    }

    /**
     * copy constructor
     * @param oldRecord the record to copy
     */
    public Record(Record oldRecord){
        this.columnsInRecord = oldRecord.columnsInRecord;
        this.columnSize = oldRecord.columnSize;
        this.primary = oldRecord.primary;
        this.otherColumns = oldRecord.otherColumns;
    }

    /**
     * get the record index
     * @return the record index
     */
    public int getPrimary() {
        return primary;
    }

    public void addOtherColumns(String str){
        otherColumns = otherColumns + " " +str;
    }

    /**
     * compares record objects according to the record index
     * @param o record to compare to
     * @return <0 if is smaller, =0 if is bigger, >0 if is bigger
     */
    @Override
    public int compareTo(Record o) {
        int comparePrimary = ((Record) o).getPrimary();
        return this.primary - comparePrimary;
    }

    /**
     * return a string representation of a record
     * @return a string representation of a record
     */
    public String toString(){
        return primary + otherColumns;
    }
}
