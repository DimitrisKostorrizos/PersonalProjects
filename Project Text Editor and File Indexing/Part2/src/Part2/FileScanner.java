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

    /**Local File Filename Setter*/
    protected void setFilename(String filename)
    {
        Filename = filename;
    }

    /**Local File Filename Getter*/
    protected String getFilename()
    {
        return Filename;
    }

    /**Local File Filename Open*/
    protected IndexingTable OpenFile()
    {
        //Try to open the local file Filename
        try 
        {
            File LocalInputFile = new File(this.Filename);
            Scanner LocalInputFileReader = new Scanner(LocalInputFile);
            IndexingTable FileDictionary;
            FileDictionary= ReadFile(LocalInputFileReader);
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

    private IndexingTable ReadFile(Scanner mLocalInputFileReader)
    {
        int mLineCounter = 1;
        ArrayList<String> mWordsArrayList = new ArrayList<>();
        ArrayList<Integer> mLineCountersArrayList = new ArrayList<>();
        while (mLocalInputFileReader.hasNextLine())
        {
            String mFileLine = mLocalInputFileReader.nextLine();
            String[] mSplitWords = mFileLine.split("[ ,.]+");
            for(String word:mSplitWords)
            {
                if(word.length() >= SizeConstants.getMinWordSize())
                {
                    if(word.length() > SizeConstants.getMaxWordSize())
                    {
                        word = word.substring(0,SizeConstants.getMaxWordSize());
                    }
                    System.out.println(word + mLineCounter);
                    mWordsArrayList.add(word);
                    mLineCountersArrayList.add(mLineCounter);
                }
            }
            mLineCounter++;
        }
        return new IndexingTable(mWordsArrayList, mLineCountersArrayList);
    }
}