package Part2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
            PrintWriter FileWriter = new PrintWriter(new BufferedWriter(IndexFileWriter,SizeConstants.getBufferSize()));
            for(int index = 0; index < mIndexingTable.getTupleVector().size(); index++)
            {
                String text = mIndexingTable.toString();

            }
            String text = "test";
            //Complete the buffer writer
            FileWriter.write(text);
        }
        catch (IOException ex)
        {
            System.out.println("File: " + this.Filename + " cannot be opened.");
            ex.printStackTrace();
        }
    }
}
