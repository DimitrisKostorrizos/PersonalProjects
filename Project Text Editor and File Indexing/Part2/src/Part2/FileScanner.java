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