/**
 * a buffer object from the second pass and forward
 * Created by Dotan Huberman on 07/07/2017.
 */
public class SecondPassBuffer {
    private Record[] buffer; //an array of all the records in the buffer
    private int[] pointerPlace; //an array of indexes, each cell represent a page in the buffer with an index to a record in the current page
    private int bufferSize; //amount of pages in the buffer
    private int recordsInPage; //amount of records in one page

    /**
     * constructor
     * @param bufferSize amount of pages in the buffer
     * @param recordsInPage amount of records in one page
     */
    public SecondPassBuffer(int bufferSize,int recordsInPage) {
        this.bufferSize = bufferSize;
        this.recordsInPage = recordsInPage;
        buffer = new Record[bufferSize*recordsInPage];
        pointerPlace = new int[bufferSize];
    }

    /**
     * add record to the buffer
     * @param record wanted record to add
     * @param index wanted index int the buffer to add the record to
     */
    public void addRecord(Record record, int index){
        buffer[index] = record;
    }

    /**
     * get a record according to a page number and the pointer of the wanted page in the buffer
     * @param pageNum a page number in the buffer
     * @return a record according to a page number and the pointer of the wanted page in the buffer
     */
    public Record getRecord(int pageNum){
        return new Record(buffer[pageNum*recordsInPage + pointerPlace[pageNum]]);
    }

    /**
     * get a pointer index in the wanted page number of the buffer
     * @param pageNum a page number in the buffer
     * @return a pointer index in the wanted page number of the buffer
     */
    public int getPointerIndex(int pageNum){
        return pointerPlace[pageNum];
    }

    /**
     * moves the pointer of the wanted page in the buffer
     * if the pointer exceeds the page moves it back to the first record in the page and returns true, false otherwise
     * returns true if the
     * @param pageNum a page number in the buffer
     * @return true if the pointer exceeds the page
     */
    public boolean movePointer(int pageNum){
        pointerPlace[pageNum]++;
        if (pointerPlace[pageNum] >= recordsInPage) { //return true if the pointer is exceeded and gets it back to 0
            pointerPlace[pageNum] = 0;
            return true;
        }
        else
            return false;
    }

}
