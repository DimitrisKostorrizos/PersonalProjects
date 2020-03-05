package Part2;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class FileScanner
{
    /**Local File Filename*/
    private String Filename;

    /**String Constructor*/
    protected FileScanner(String filename)
    {
        Filename = filename;
    }

    /**Local File Filename Open
     Try to open the local file Filename*/
    protected IndexingTable FileToIndexingTable()
    {
        try
        {
            File LocalInputFile = new File(this.Filename);
            Scanner LocalInputFileReader = new Scanner(LocalInputFile);
            IndexingTable FileDictionary;
            FileDictionary = CreateFileIndexingTable(LocalInputFileReader);
            LocalInputFileReader.close();
            return FileDictionary;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File: " + this.Filename + " cannot be opened.");
            e.printStackTrace();
            return null;
        }
    }

    private IndexingTable CreateFileIndexingTable(Scanner mLocalInputFileReader)
    {
        int mLineCounter = 1;
        ArrayList<String> mWordsArrayList = new ArrayList<>();
        ArrayList<Integer> mLineCountersArrayList = new ArrayList<>();
        while (mLocalInputFileReader.hasNextLine())
        {
            String mFileLine = mLocalInputFileReader.nextLine();
            String[] mSplitWords = mFileLine.split("[ ,.?!]+");
            for(String word:mSplitWords)
            {
                if(word.length() >= SizeConstants.getMinWordSize())
                {
                    if(word.length() > SizeConstants.getMaxWordSize())
                    {
                        word = word.substring(0,SizeConstants.getMaxWordSize());
                    }
                    mWordsArrayList.add(word);
                    mLineCountersArrayList.add(mLineCounter);
                }
            }
            mLineCounter++;
        }
        return new IndexingTable(mWordsArrayList, mLineCountersArrayList);
    }

    protected LinkedList<String> FileToLinkedList()
    {
        try
        {
            File LocalInputFile = new File(this.Filename);
            Scanner LocalInputFileReader = new Scanner(LocalInputFile);
            LinkedList<String> FileList = CreateFileLinkedList(LocalInputFileReader);
            LocalInputFileReader.close();
            return FileList;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File: " + this.Filename + " cannot be opened.");
            e.printStackTrace();
            return null;
        }
    }

    private LinkedList<String> CreateFileLinkedList(Scanner mLocalInputFileReader)
    {
        LinkedList<String> mFileLinkedList = new LinkedList<>();
        while (mLocalInputFileReader.hasNextLine())
        {
            String mFileLine = mLocalInputFileReader.nextLine();
            if(mFileLine.length() > SizeConstants.getMaxFileLineSize())
            {
                mFileLine = mFileLine.substring(0,SizeConstants.getMaxFileLineSize());
            }
            mFileLinkedList.add(mFileLine);
        }
        return mFileLinkedList;
    }
}