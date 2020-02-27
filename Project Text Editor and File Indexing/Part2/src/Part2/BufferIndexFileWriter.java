package Part2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

public class BufferIndexFileWriter
{
    /**Output File Filename*/
    private String Filename;

    /**Output File Filename getter*/
    public String getFilename()
    {
        return Filename;
    }

    /**Output File Filename setter*/
    public void setFilename(String filename)
    {
        Filename = filename;
    }

    /**Default BufferIndexFileWriter Constructor*/
    public BufferIndexFileWriter(String filename)
    {
        Filename = filename;
    }

    public void BufferPrint(IndexingTable mIndexingTable)
    {
        FileWriter IndexFileWriter;
        try
        {
            IndexFileWriter = new FileWriter(Filename + ".txt.ndx");
            BufferedWriter FileWriter = new BufferedWriter(IndexFileWriter,SizeConstants.getBufferSize());
            for(int index = 0; index < mIndexingTable.getTupleVector().size(); index++)
            {
                String mWord = mIndexingTable.getTupleVector().get(index).getkey();
                Integer mLineCounter = mIndexingTable.getTupleVector().get(index).getValue();

                String mLine = mWord.concat("  ").concat(String.valueOf(mLineCounter));
                byte[] mLineBytes = mLine.getBytes();
                for (byte mLineByte : mLineBytes)
                {
                    FileWriter.write(String.format("%8s", Integer.toBinaryString(mLineByte & 0xFF)).replace(' ', '0'));
                }
            }
            FileWriter.flush();
            FileWriter.close();
            IndexFileWriter.close();
        }
        catch (IOException ex)
        {
            System.out.println("File: " + this.Filename + " cannot be opened.");
            ex.printStackTrace();
        }
    }
}
