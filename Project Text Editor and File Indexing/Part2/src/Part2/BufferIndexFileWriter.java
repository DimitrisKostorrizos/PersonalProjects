package Part2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;

public class BufferIndexFileWriter
{
    /**Output File Filename*/
    private String Filename;

    /**Default BufferIndexFileWriter Constructor*/
    public BufferIndexFileWriter(String filename)
    {
        Filename = filename;
    }

    public void IndexingTableByteFileWrite(IndexingTable mIndexingTable)
    {
        FileWriter IndexFileWriter;
        try
        {
            IndexFileWriter = new FileWriter(Filename + ".ndx");
            ByteBuffer FileWriter = ByteBuffer.allocate(SizeConstants.getBufferSize());
            for(int index = 0; index < mIndexingTable.getTupleVector().size(); index++)
            {
                String mWord = mIndexingTable.getTupleVector().get(index).getKey();
                Integer mLineCounter = mIndexingTable.getTupleVector().get(index).getValue();

                if(mWord.length() < SizeConstants.getMaxWordSize())
                {
                    int mExtensionLength = SizeConstants.getMaxWordSize() - mWord.length();
                    mWord = mWord.concat(" ".repeat(mExtensionLength));
                }

                String mLine = mWord.concat(String.valueOf(mLineCounter));
                FileWriter.put(mLine.getBytes());
//                for (byte mLineByte : mLineBytes)
//                {
//                    FileWriter.put(String.format("%8s", Integer.toBinaryString(mLineByte & 0xFF)).replace(' ', '0'));
//                }
            }
//            FileWriter.flush();
//            FileWriter.close();
            IndexFileWriter.close();
        }
        catch (IOException ex)
        {
            System.out.println("File: " + this.Filename + " cannot be opened.");
            ex.printStackTrace();
        }
    }
}
