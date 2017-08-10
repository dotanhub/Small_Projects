/**
 * a class to preform an external merge sort
 * Created by Dotan Huberman on 05/07/2017.
 */
import java.io.*;
import java.util.Random;

public class ExternalSort
{

    /**
     * perform the file external sort
     * @param file a file to sort
     * @param pageSize page size in bytes
     * @param bufferSize amount of pages in the buffer
     * @param gui the gui that called the class for showing messages
     */
    public static void externalSort(File file, int pageSize,int bufferSize, GUI gui)
    {
        int columnsInRecord = 2; //amount of columns in a record
        int columnSize = 5; //column size in bytes
        int recordSize = columnsInRecord*columnSize + columnsInRecord-1; //a record size in bytes (includes spaces)
        int sliceCounter; //how much temp files there are in the current pass

        //perform first pass
        sliceCounter = firstPass(file,pageSize,recordSize,bufferSize,columnsInRecord,columnSize);
        gui.displayMessage("Finished first pass\n********************************************\n");

        int passesNeeded = passesNeeded(sliceCounter,bufferSize); //calculate passes needed for sorting
        int passesCounter = 0;

        //perform the rest of the passes needed
        for (int i =0;i<passesNeeded; i++){
            gui.displayMessage("Started " + (passesCounter+2) + " pass\n********************************************\n");
            secondPass(bufferSize,recordSize,pageSize,sliceCounter,passesCounter,columnsInRecord,columnSize,gui);

            //calculate how much temp files are needed for the next pass
            if (sliceCounter%(bufferSize-1) == 0)
                sliceCounter = sliceCounter/(bufferSize-1);
            else
                sliceCounter = sliceCounter/(bufferSize-1) + 1;

            gui.displayMessage("Finished " + (passesCounter+2) + " pass\n********************************************\n");

            passesCounter++;
        }

        //sorting was finished
        gui.displayMessage("Finished sorting the file - new file name is \"Sorted-File.txt\"");
    }

    /**
     * calculates the amount of passes needed to sort the current file
     * @param slices amount of temp files crated in the first pass
     * @param bufferSize amount of pages in the buffer
     * @return the amount of passes needed to sort the current file
     */
    private static int passesNeeded(int slices, int bufferSize){
        int passes = 0;
        int oneMoreFile;

        while (slices > 1){
            if (slices % (bufferSize - 1) > 0)
                oneMoreFile = 1;
            else
                oneMoreFile = 0;

            slices = (slices/(bufferSize-1)) + oneMoreFile;
            passes++;
        }
        return passes;
    }

    /**
     * perform the first pass of the sorting
     * @param file a file to sort
     * @param pageSize page size in bytes
     * @param recordSize the size of one record
     * @param bufferSize amount of pages in the buffer
     * @param columnsInRecord //amount of columns in a record
     * @param columnSize //column size in bytes
     * @return how much temp files were created in the first pass
     */
    private static int firstPass(File file,int pageSize,int recordSize,int bufferSize,int columnsInRecord,int columnSize){
        new File("temp files").mkdir(); //create a folder for temp files
        String tfile = "temp-file-";
        int sliceCounter = 0;

        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            boolean EOFflag = false; //when its done reading the file, will change to true

            Buffer buffer = new Buffer(pageSize, recordSize, bufferSize);
            while (!EOFflag) {
                // Read records from the file until the buffer is full
                for (int i = 0; i < bufferSize; i++) {
                    for (int j = 0; j < pageSize / recordSize; j++) {
                        String t = br.readLine();

                        //creates a record object from each record read
                        if (t != null) {
                            String[] wordArray = t.split(" ");
                            Record record = new Record(columnsInRecord, columnSize, Integer.parseInt((wordArray[0])));
                            for (int k = 1; k < columnsInRecord; k++) {
                                record.addOtherColumns(wordArray[k]);
                            }

                            //adds the record to the buffer
                            buffer.addRecord(record);
                        } else { //no more lines to read
                            EOFflag = true;
                            break;
                        }
                    }
                    if (EOFflag)
                        break;
                }
                if (buffer.getRecordAmount() == 0)
                    break;

                // Sort the buffer
                buffer.sortRecords();

                // Write the sorted records to temp file
                FileWriter fw = new FileWriter("temp files\\" + tfile + Integer.toString(sliceCounter) + ".txt");
                PrintWriter pw = new PrintWriter(fw);
                for (int k = 0; k < buffer.getRecordAmount(); k++)
                    pw.println(buffer.getRecord(k));

                //clear the buffer
                buffer.clearBuffer();

                sliceCounter++;
                pw.close();
                fw.close();
            }

            br.close();
            fr.close();

        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }

        return sliceCounter; //returns the amount of temp files created
    }

    /**
     * perform a pass after the first pass was performed
     * @param bufferSize amount of pages in the buffer
     * @param recordSize the size of one record
     * @param pageSize page size in bytes
     * @param slices amount of temp files needed to be created
     * @param passesCounter the number of the pass
     * @param columnsInRecord //amount of columns in a record
     * @param columnSize //column size in bytes
     * @param gui the gui that called the class for showing messages
     */
    private static void secondPass(int bufferSize, int recordSize, int pageSize, int slices, int passesCounter, int columnsInRecord,int columnSize,GUI gui) {
        int recordsOnPage = pageSize / recordSize;
        String oldFileTitle;

        if (passesCounter == 0)
            oldFileTitle = "temp-file-";
        else
            oldFileTitle = "temp-file-p" + (passesCounter-1) + "-";

        String tfile = "temp-file-p" + passesCounter + "-";

        int oneMoreFile = 0;// if the amount of temp files is not divided by bufferSize-1 with no leftover, there will be one more file
        if (slices % (bufferSize - 1) > 0)
            oneMoreFile = 1;

        try {
            //for each bunch of buffer-1 temp files
            for (int fileNum = 0; fileNum < (slices / (bufferSize - 1) + oneMoreFile); fileNum++) {

                gui.displayMessage("Pass: " + (passesCounter + 2) + ", start to write temp file number: " + fileNum + "\n");

                FileWriter fw;

                //if there are more files than bufferSize-1 there will be more than one temp file, otherwise its the last pass and create the final sorted file
                if (slices > (bufferSize - 1))
                    fw = new FileWriter("temp files\\" + tfile + Integer.toString(fileNum) + ".txt");
                else
                    fw = new FileWriter("Sorted-File.txt");

                PrintWriter pw = new PrintWriter(fw);

                //create buffered readers for the current buffer-1 temp files
                BufferedReader[] brs = new BufferedReader[bufferSize - 1];

                //create a buffer
                SecondPassBuffer buffer = new SecondPassBuffer(bufferSize, pageSize / recordSize);

                int bunchOfFiles = bufferSize - 1;//the amount of riles in the current bunch of files

                // if its the last files it could be less than bufferSize-1 so it calculates how much files in the bunch
                if (oneMoreFile > 0 && (fileNum == ((slices / (bufferSize - 1)) + oneMoreFile) - 1))
                    bunchOfFiles = slices % (bufferSize - 1);

                // for all the files in the bunch load a page from each file and puts it in the buffer
                for (int i = 0; i < (bunchOfFiles); i++) {
                    brs[i] = new BufferedReader(new FileReader("temp files\\" + oldFileTitle + Integer.toString(fileNum*(bufferSize-1) + i) + ".txt"));
                    loadNextRecords(buffer, brs, i, columnsInRecord, columnSize, recordsOnPage);
                }

                gui.displayMessage((bufferSize - 1) + " pages were read from the disk to the memory\n");

                int writtenPagesCounter=0;//how much pages were written to the new file
                Record smallerRecord; //the smaller record from all the pages in the buffer
                int smallerRecLocation; //the location in the buffer of the smaller record
                int amountOfRecordsInBunch = recordsOnPage * bufferSize * (int)java.lang.Math.pow(bufferSize-1,passesCounter+1); //amount of records in the current bunch

                //loop for all the files in the current bunch of files
                for (int i = 0; i < amountOfRecordsInBunch; i++) {
                    smallerRecord = buffer.getRecord(0);
                    smallerRecLocation = 0;

                    // get the smaller record in the buffer
                    for (int j = 1; j < bunchOfFiles; j++) {
                        if (buffer.getRecord(j).compareTo(smallerRecord) < 0) {
                            smallerRecord = buffer.getRecord(j);
                            smallerRecLocation = j;
                        }
                    }

                    //adds the smaller record to the last page of the buffer
                    buffer.addRecord(smallerRecord, (bufferSize - 1) * recordsOnPage + buffer.getPointerIndex(bufferSize - 1));

                    //move the pointer of the smaller record in the buffer to the next record int its page, if there is no next read the next page from the temp file to the buffer
                    if (buffer.movePointer(smallerRecLocation)) {
                        loadNextRecords(buffer, brs, smallerRecLocation, columnsInRecord, columnSize, recordsOnPage);
                        gui.displayMessage("1 page was read from the disk to the memory\n");
                    }

                    //move the pointer of the last page of the buffer, if there is no more place in the page write the page to the file
                    if (buffer.movePointer(bufferSize - 1)) {
                        for (int k = 0; k < recordsOnPage; k++) {
                            Record recToWrite = buffer.getRecord(bufferSize - 1);
                            if (recToWrite.getPrimary()!=Integer.MAX_VALUE)
                                pw.println(recToWrite);
                            buffer.movePointer(bufferSize - 1);
                        }
                        writtenPagesCounter++;
                        gui.displayMessage("The " + writtenPagesCounter + " page of sorted file " + fileNum + " was written to the disk\n");
                    }
                }
                pw.close();
                fw.close();

                gui.displayMessage("Pass: " + (passesCounter + 2) + ", finished to write temp file number: " + fileNum + "\n");
            }
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * loads the next page to the buffer
     * @param buffer buffer
     * @param brs array of buffered readers
     * @param pageNum number of page in the buffer
     * @param columnsInRecord //amount of columns in a record
     * @param columnSize //column size in bytes
     * @param recordsOnPage //amount of records in one page
     */
    static void loadNextRecords(SecondPassBuffer buffer, BufferedReader[] brs,int pageNum,int columnsInRecord,int columnSize, int recordsOnPage){
        try {

            // for the amount of records in one page read the records and add them to the buffer
            for (int j = 0; j < recordsOnPage; j++) {
                String t = brs[pageNum].readLine();
                if (t != null) {
                    String[] wordArray = t.split(" ");
                    Record record = new Record(columnsInRecord, columnSize, Integer.parseInt((wordArray[0])));
                    for (int k = 1; k < columnsInRecord; k++) {
                        record.addOtherColumns(wordArray[k]);
                    }
                    buffer.addRecord(record, (pageNum * recordsOnPage + j));
                } else //no more lines
                    buffer.addRecord(new Record(columnsInRecord, columnSize, Integer.MAX_VALUE), (pageNum * recordsOnPage + j));//adds a record with maximum value
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * generate an input files according to the user parameters
     * @param n amount of records in the file
     * @param columnAmount amount of columns of each record
     * @return
     */
    static String generateInput(int n,int columnAmount)
    {
        String fileName = "generated-file.txt";
        Random rand = new Random();

        try
        {
            FileWriter fw = new FileWriter(fileName);
            PrintWriter pw = new PrintWriter(fw);

            for (int i = 0; i < n; i++) {
                String str = "";
                str = str + ((rand.nextInt(89999) + 10000));
                for (int j=1; j < columnAmount; j++) {
                    str = str + " " + (rand.nextInt(89999) + 10000);
                }
                pw.println(str);
            }
            pw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileName;
    }
}