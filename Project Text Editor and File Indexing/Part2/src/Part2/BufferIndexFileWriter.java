package Part2;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Arrays;

public class BufferIndexFileWriter
{
    /**Output File Filename*/
    private String Filename;

    /**Default BufferIndexFileWriter Constructor*/
    public BufferIndexFileWriter(String filename)
    {
        Filename = filename;
    }

    public int IndexingTableByteFileWrite(IndexingTable mIndexingTable)
    {
        FileWriter IndexFileWriter;
        try
        {
            IndexFileWriter = new FileWriter(Filename + ".ndx");
            ByteBuffer mByteBuffer = ByteBuffer.allocate(SizeConstants.getBufferSize());
            int mIndexRecordSize = SizeConstants.getMaxWordSize() + 4;
            int mBufferRecordCapacity = SizeConstants.getBufferSize()/mIndexRecordSize;
            int mNumberOfPages = 0;

            for(int index = 0; index < mIndexingTable.getTupleVector().size(); index++)
            {
                String mWord = mIndexingTable.getTupleVector().get(index).getKey();
                Integer mLineCounter = mIndexingTable.getTupleVector().get(index).getValue();

                if(mWord.length() < SizeConstants.getMaxWordSize())
                {
                    int mExtensionLength = SizeConstants.getMaxWordSize() - mWord.length();
                    mWord = mWord.concat(" ".repeat(mExtensionLength));
                }

                mByteBuffer.put(mWord.getBytes());
                mByteBuffer.putInt(mLineCounter);

                if(mByteBuffer.position() == mBufferRecordCapacity * mIndexRecordSize)
                {
                    IndexFileWriter.write(Arrays.toString(mByteBuffer.array()));
                    mByteBuffer = ByteBuffer.wrap(new byte[mByteBuffer.capacity()]);
                    mNumberOfPages++;
                }
            }
            if(mByteBuffer.position() != 0)
            {
                IndexFileWriter.write(Arrays.toString(mByteBuffer.array()));
                mNumberOfPages++;
            }
            IndexFileWriter.flush();
            IndexFileWriter.close();
            return mNumberOfPages;
        }
        catch (IOException ex)
        {
            System.out.println("File: " + this.Filename + " cannot be opened.");
            ex.printStackTrace();
            return 0;
        }
    }
}
