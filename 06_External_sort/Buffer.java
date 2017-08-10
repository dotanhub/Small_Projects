import java.util.ArrayList;

/**
 * a buffer object for the first pass
 * Created by Dotan Huberman on 05/07/2017.
 */
public class Buffer {
    private int pageSize; // page size in bytes
    private int recordSize; //record size in bytes
    private int bufferSize; //amount of pages in the buffer
    private int recordsInPage; //amount of records in one page
    ArrayList<Record> bufferRecords; //the records in the buffer
    int currentRecordAmount; //the current records amount in the buffer

    /**
     * constructor
     * @param pageSize page size in bytes
     * @param recordSize record size in bytes
     * @param bufferSize amount of pages in the buffer
     */
    public Buffer(int pageSize,int recordSize,int bufferSize ) {
        this.pageSize = pageSize;
        this.recordSize = recordSize;
        this.currentRecordAmount = 0;
        this.bufferSize = bufferSize;
        this.recordsInPage = pageSize/recordSize;
        bufferRecords = new ArrayList<>();
    }

    /**
     * add a record to the buffer
     * @param record
     */
    public void addRecord(Record record){
        bufferRecords.add(record);
        currentRecordAmount++;
    }

    /**
     * clears the buffer from records
     */
    public void clearBuffer(){
        bufferRecords.clear();
        currentRecordAmount = 0;
    }

    /**
     * get a record according to its location(index) in the buffer
     * @param recordNumber the index of the record in the buffer
     * @return a record according to its location(index) in the buffer
     */
    public Record getRecord(int recordNumber){
        return new Record(bufferRecords.get(recordNumber));
    }

    /**
     * get the current records amount in the buffer
     * @return get the current records amount in the buffer
     */
    public int getRecordAmount() {
        return currentRecordAmount;
    }

    /**
     * sorts the records in the buffer according to the record index (ascending order)
     */
    public void sortRecords(){
        java.util.Collections.sort(bufferRecords);
    }
}