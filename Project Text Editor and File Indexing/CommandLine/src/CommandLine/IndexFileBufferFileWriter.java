package CommandLine;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

/**Class for the creation of the indexing file based on the indexing table object*/
public class IndexFileBufferFileWriter
{
    /**Filename property for the indexing file filename*/
    private String Filename;

    /**Default constructor for the index file using a byte buffer FileWriter object
     * @param  filename = filename for the index file*/
    public IndexFileBufferFileWriter(String filename)
    {
        Filename = filename;
    }

    /**Method that writes and IndexingTable object to a byte array file
     * @param indexingTable = sorted IndexingTable that will be written in the file
     * @return the number of Data Pages that the file contains, or 0 if the file cannot be created/updated*/
    public int IndexingTableByteFileWrite(IndexingTable indexingTable)
    {
        //Create a FileWriter object
        FileWriter indexFileFileWriter;
        try
        {
            //Try to open the file in order to write in it
            indexFileFileWriter = new FileWriter(Filename + ".ndx");
            
            //Allocate BufferSize byte size ByteBuffer object
            ByteBuffer mByteBuffer = ByteBuffer.allocate(SizeConstants.getBufferSize());
            
            //Index entry size in ASCII characters
            int indexEntrySize = SizeConstants.getMaxWordSize() + 4;
            
            //Number of full index entry that a buffer can contain
            int bufferIndexEntryCapacity = SizeConstants.getBufferSize()/indexEntrySize;
            
            //Number of data pages that the file contains
            int numberOfDataPages = 0;

            //Iterate over the whole indexing table
            for(int index = 0; index < indexingTable.getTupleVector().size(); index++)
            {
                //Get the current index entry string value
                String indexEntryWord = indexingTable.getTupleVector().get(index).getLeftValue();

                //Get the current index entry integer value
                Integer indexEntryLine = indexingTable.getTupleVector().get(index).getRightValue();

                //Extend the word to its maximum length if the words length is less than the MaxWordSize
                if(indexEntryWord.length() < SizeConstants.getMaxWordSize())
                {
                    //Calculate the number of space character that must be add at the end of the word
                    int extensionLength = SizeConstants.getMaxWordSize() - indexEntryWord.length();
                    
                    //Concatenate the extra spaces at the end of the word
                    indexEntryWord = indexEntryWord.concat(" ".repeat(extensionLength));
                }

                //Convert the word to bytes and add these bytes into the ByteBuffer
                mByteBuffer.put(indexEntryWord.getBytes());

                //Convert the line number to bytes and add these bytes into the ByteBuffer
                mByteBuffer.putInt(indexEntryLine);

                //If the ByteBuffer contains the max amount of index entries , write them into the file
                if(mByteBuffer.position() == bufferIndexEntryCapacity * indexEntrySize)
                {
                    //Write the buffer into the FileWriter and add a newline character
                    indexFileFileWriter.write(Arrays.toString(mByteBuffer.array()) + "\n");

                    //Reset the ByteBuffer with 0 in every position
                    mByteBuffer = ByteBuffer.wrap(new byte[mByteBuffer.capacity()]);

                    //Count the added data page
                    numberOfDataPages++;
                }
            }

            //If there are remaining index entries in the buffer, write the into the file
            if(mByteBuffer.position() != 0)
            {
                //Write the buffer into the FileWriter
                indexFileFileWriter.write(Arrays.toString(mByteBuffer.array()));

                //Count the added data page
                numberOfDataPages++;
            }

            //Flush the FileWriter content into the file
            indexFileFileWriter.flush();

            //Close the FileWriter object
            indexFileFileWriter.close();

            //Return the number of data pages that were written into the file
            return numberOfDataPages;
        }
        catch (IOException ex)
        {
            //Catch the IOException and inform the user
            System.out.println("File: " + this.Filename + " cannot be opened.");
            ex.printStackTrace();
            return 0;
        }
    }
}
