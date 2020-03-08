package Part2;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.System.exit;

public class CMD
{
    /**CMD Inserted Character*/
    private String Command;

    /**CMD File Line Index*/
    private int LineIndex;

    /**CMD File Lines Linked List*/
    private LinkedList<String> FileLines;

    /**CMD Line Number Printing*/
    private boolean mPrintLineNumbers = false;

    /**CMD Input Filename*/
    private String Filename;

    /**CMD No File Constructor*/
    public CMD(String mFilename)
    {
        this.LineIndex = -1;
        this.FileLines = new LinkedList<>();
        this.Filename = mFilename;
    }

    /**CMD Input File Constructor*/
    public CMD(String mFilename, LinkedList<String> mFileLines)
    {
        this.LineIndex = mFileLines.size() - 1;
        this.FileLines = mFileLines;
        this.Filename = mFilename;
    }

    /**CMD Execute Command Method*/
    protected void ExecuteCommand()
    {
        if(this.ValidateCommand())
        {
            //Set Line Pointer to First Line
            if(this.Command.equals("^"))
            {
                this.LineIndex = this.FileLines.isEmpty()? -1: 0;
                System.out.println("Pointer set to first line.");
            }

            //Set Line Pointer to Last Line
            if(this.Command.equals("$"))
            {
                this.LineIndex = this.FileLines.isEmpty()? -1: this.FileLines.size() - 1;
                System.out.println("Pointer set to last line.");
            }

            //Set Line Pointer to Previous Position
            if(this.Command.equals("-"))
            {
                if(this.FileLines.isEmpty())
                {
                    this.LineIndex = -1;
                }
                else
                {
                    this.LineIndex = this.LineIndex == 0? 0: this.LineIndex - 1;
                }
                System.out.println("Pointer set to previous line.");
            }

            //Set Line Pointer to Next Position
            if(this.Command.equals("+"))
            {
                this.LineIndex = (this.FileLines.size() - 1) == this.LineIndex ? this.LineIndex: this.LineIndex + 1;
                System.out.println("Pointer set to next line.");
            }

            //Add new line after current line
            if(this.Command.equals("a"))
            {
                System.out.println("Type the text for the new line:");
                Scanner CMDCurrentLineScanner = new Scanner(System.in);
                String mInputLine = CMDCurrentLineScanner.nextLine();
                this.FileLines.add(this.LineIndex + 1, mInputLine);
                this.LineIndex++;
                System.out.println("New line entered successfully.");
            }

            //Add new line before current line
            if(this.Command.equals("t"))
            {
                System.out.println("Type the text for the new line:");
                Scanner CMDCurrentLineScanner = new Scanner(System.in);
                String mInputLine = CMDCurrentLineScanner.nextLine();
                if(this.FileLines.isEmpty())
                {
                    this.FileLines.add(mInputLine);
                }
                else
                {
                    this.FileLines.add(this.LineIndex, mInputLine);
                }
                System.out.println("New line entered successfully.");
            }

            //Delete current line
            if(this.Command.equals("d"))
            {
                if(this.FileLines.isEmpty())
                {
                    this.LineIndex = -1;
                    System.out.println("No lines to be deleted.");
                }
                else
                {
                    this.FileLines.remove(this.LineIndex);
                    this.LineIndex = this.LineIndex == 0? 0: this.LineIndex - 1;
                    System.out.println("Current line deleted successfully.");
                }
            }

            //Print all lines
            if(this.Command.equals("l"))
            {
                int mIndex = 0;
               for(String mLine : this.FileLines)
               {
                   if(this.mPrintLineNumbers)
                   {
                       System.out.println(mIndex + 1 + ")   " + mLine);
                       mIndex++;
                   }
                   else
                   {
                       System.out.println(mLine);
                   }
               }
            }

            //Toggle whether line numbers are displayed when printing all lines
            if(this.Command.equals("n"))
            {
                if(this.mPrintLineNumbers)
                {
                    this.mPrintLineNumbers = false;
                    System.out.println("Line numbers won't be displayed when printing all lines.");
                }
                else
                {
                    this.mPrintLineNumbers = true;
                    System.out.println("Line numbers will be displayed when printing all lines.");
                }
            }

            //Print current line
            if(this.Command.equals("p"))
            {
                if(this.FileLines.isEmpty())
                {
                    System.out.println("No line to print.");
                }
                else
                {
                    System.out.println(this.FileLines.get(this.LineIndex));
                }
            }

            //Quit without save
            if(this.Command.equals("q"))
            {
                System.out.println("Exiting without save.");
                exit(0);
            }

            //Write file to disk
            if(this.Command.equals("w"))
            {
                try
                {
                    File LocalOutputFile = new File(this.Filename);
                    FileWriter LocalOutputFileWriter = new FileWriter(LocalOutputFile);
                    for(String mLine : this.FileLines)
                    {
                        LocalOutputFileWriter.write(mLine + "\n");
                    }
                    System.out.println("File was written successfully.");
                    LocalOutputFileWriter.close();
                }
                catch (IOException e)
                {
                    System.out.println("File: " + this.Filename + " cannot be opened.");
                    e.printStackTrace();
                }
            }

            //Exit with save
            if(this.Command.equals("x"))
            {
                try
                {
                    File LocalOutputFile = new File(this.Filename);
                    FileWriter LocalOutputFileWriter = new FileWriter(LocalOutputFile);
                    for(String mLine : this.FileLines)
                    {
                        LocalOutputFileWriter.write(mLine + "\n");
                    }
                    System.out.println("Exiting with save.");
                    LocalOutputFileWriter.close();
                    exit(0);
                }
                catch (IOException e)
                {
                    System.out.println("File: " + this.Filename + " cannot be opened.");
                    e.printStackTrace();
                }
            }

            //Print current line number
            if(this.Command.equals("="))
            {
                if(this.FileLines.isEmpty())
                {
                    System.out.println("Current line number: 0");
                }
                else
                {
                    System.out.println("Current line number: " + (this.LineIndex + 1));
                }
            }

            //Print number of lines and characters
            if(this.Command.equals("#"))
            {
                int mLines = this.FileLines.size();
                int mCharacters = 0;
                for(String mLine : this.FileLines)
                {
                    mCharacters += mLine.length();
                }
                System.out.println(mLines + " Lines, " + mCharacters + " characters.");
            }

            //Create index file
            if(this.Command.equals("c"))
            {
                ArrayList<String> mWordList = new ArrayList<>();
                ArrayList<Integer> mLineIndex = new ArrayList<>();
                int mLineCounter = 1;
                for(String mLine : this.FileLines)
                {
                    String[] mSplitWords = mLine.split("[ ,.?!]+");
                    for(String word:mSplitWords)
                    {
                        if(word.length() >= SizeConstants.getMinWordSize())
                        {
                            if(word.length() > SizeConstants.getMaxWordSize())
                            {
                                word = word.substring(0,SizeConstants.getMaxWordSize());
                            }
                            mWordList.add(word);
                            mLineIndex.add(mLineCounter);
                        }
                    }
                    mLineCounter++;
                }
                BufferIndexFileWriter mIndexTableFileWriter = new BufferIndexFileWriter(this.Filename);
                IndexingTable mIndexingTable = new IndexingTable(mWordList, mLineIndex, true);
                int mNumberOfPages = mIndexTableFileWriter.IndexingTableByteFileWrite(mIndexingTable);
                System.out.print("Index file was created successfully.");
                System.out.println("Data pages of size " + SizeConstants.getBufferSize() + " bytes: " + mNumberOfPages);
            }

            //Print index file
            if(this.Command.equals("v"))
            {
                ArrayList<String> mWordList = new ArrayList<>(this.FileLines);
                ArrayList<Integer> mLineIndex = new ArrayList<>();
                for(int mIndex = 0; mIndex < this.FileLines.size(); mIndex++)
                {
                    mLineIndex.add(mIndex + 1);
                }
                IndexingTable mIndexingTable = new IndexingTable(mWordList, mLineIndex, false);
                for(int mIndex = 0; mIndex < mLineIndex.size(); mIndex++)
                {
                    System.out.println(mIndexingTable.getTupleVector().get(mIndex).getKey() + " Line: " + mIndexingTable.getTupleVector().get(mIndex).getValue());
                }
            }

            //Print lines of word serial(Linear) search.
            if(this.Command.equals("s"))
            {
                System.out.println("Type the word that you want to search:");
                Scanner CMDCurrentLineScanner = new Scanner(System.in);
                String mInputWord = CMDCurrentLineScanner.nextLine();

                ArrayList<Integer> mMatchingPositions = new ArrayList<>();

                int mLineCounter = LinearReadIndexFile(this.Filename, mMatchingPositions, mInputWord);

                if(mMatchingPositions.isEmpty())
                {
                    System.out.println("The word: " + mInputWord + " ,has not been found.");
                }
                else
                {
                    System.out.print("The word: " + mInputWord + " ,has been found on lines: ");
                    for(Integer position :  mMatchingPositions)
                    {
                        System.out.print(position + ",");
                    }
                    System.out.println(".");
                }
                System.out.println("Disk Accesses: " + mLineCounter);
            }

            //Print lines of word binary search.
            if(this.Command.equals("b"))
            {
                System.out.println("Type the word that you want to search:");
                Scanner CMDCurrentLineScanner = new Scanner(System.in);
                String mInputWord = CMDCurrentLineScanner.nextLine();

                ArrayList<Integer> mMatchingPositions = new ArrayList<>();

                int mLineCounter = BinaryReadIndexFile(this.Filename, mMatchingPositions, mInputWord);

                if(mMatchingPositions.isEmpty())
                {
                    System.out.println("The word: " + mInputWord + " ,has not been found.");
                }
                else
                {
                    System.out.print("The word: " + mInputWord + " ,has been found on lines: ");
                    for(Integer position :  mMatchingPositions)
                    {
                        System.out.print(position + ",");
                    }
                    System.out.println(".");
                }
                System.out.println("Disk Accesses: " + mLineCounter);
            }
        }
        else
        {
            System.out.println("Invalid Command.");
        }
    }

    /**Check whether the inserted character is valid*/
    protected boolean ValidateCommand()
    {
        ArrayList<String> ValidCommands = new ArrayList<>();
        ValidCommands.add("^");
        ValidCommands.add("$");
        ValidCommands.add("-");
        ValidCommands.add("+");
        ValidCommands.add("a");
        ValidCommands.add("t");
        ValidCommands.add("d");
        ValidCommands.add("l");
        ValidCommands.add("n");
        ValidCommands.add("p");
        ValidCommands.add("q");
        ValidCommands.add("w");
        ValidCommands.add("x");
        ValidCommands.add("=");
        ValidCommands.add("#");
        ValidCommands.add("c");
        ValidCommands.add("v");
        ValidCommands.add("s");
        ValidCommands.add("b");
        return ValidCommands.contains(this.Command);
    }

    /**CMD Inserted String setter*/
    protected void setCommand(String command)
    {
        Command = command;
    }

    /**Search byte page for the word*/
    private int LinearSearchBytePage(String word, byte[] bytePage, ArrayList<Integer> matchingPositions)
    {
        if(word.length() > SizeConstants.getMaxWordSize() || word.length() < SizeConstants.getMinWordSize())
        {
            return -1;
        }

        int mRecordSize = (SizeConstants.getMaxWordSize() + 4);

        for(int mIndex = 0; mIndex < bytePage.length; mIndex = mIndex + mRecordSize)
        {
            String mWord = new String(Arrays.copyOfRange(bytePage, mIndex, mIndex + SizeConstants.getMaxWordSize())).replaceAll("\\s+","");

            if(mWord.equals(word))
            {
                matchingPositions.add(ByteBuffer.wrap(Arrays.copyOfRange(bytePage, mIndex + SizeConstants.getMaxWordSize(), mIndex + mRecordSize)).getInt());
            }
        }
        return  0;
    }

    /**LinearReadIndexFile*/
    private int LinearReadIndexFile(String filename, ArrayList<Integer> matchingPositions, String word)
    {
        try
        {
            File LocalInputFile = new File(filename + ".ndx");
            Scanner LocalInputFileReader = new Scanner(LocalInputFile);

            int mLineCounter = 0;
            int mWordStatus = 0;
            while(LocalInputFileReader.hasNext() && mWordStatus == 0)
            {
                ByteBuffer mBytePageBuffer = ByteBuffer.wrap(StringToByteArrayTranslator(LocalInputFileReader.nextLine(), SizeConstants.getBufferSize()));

                mWordStatus = LinearSearchBytePage(word,mBytePageBuffer.array(), matchingPositions);
                mLineCounter++;
            }
            LocalInputFileReader.close();
            return mLineCounter;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File: " + this.Filename + " cannot be opened.");
            e.printStackTrace();
            return 0;
        }
    }

    /**LinearReadIndexFile*/
    private int BinaryReadIndexFile(String filename, ArrayList<Integer> matchingPositions, String word)
    {
        try
        {
            File LocalInputFile = new File(filename + ".ndx");
            Scanner LocalInputFileReader = new Scanner(LocalInputFile);

            //Get the files lines number.
            int mMaxFileLines = 0;
            while(LocalInputFileReader.hasNext())
            {
                LocalInputFileReader.nextLine();
                mMaxFileLines++;
            }

            int mBottomFileLines = 0;
            int mTopFileLines = mMaxFileLines;
            int mMiddlePoint;
            int mDataPageAccessesCounter = 0;
            int mWordStatus;

            while (mBottomFileLines <= mTopFileLines)
            {
                LocalInputFileReader = new Scanner(LocalInputFile);
                mMiddlePoint = (mBottomFileLines + mTopFileLines)/2;
                SkipLines(LocalInputFileReader, mMiddlePoint);

                ByteBuffer mBytePageBuffer = ByteBuffer.wrap(StringToByteArrayTranslator(LocalInputFileReader.nextLine(), SizeConstants.getBufferSize()));

                mWordStatus = BinarySearchBytePage(word,mBytePageBuffer.array(), matchingPositions);
                mDataPageAccessesCounter++;

                if (mWordStatus == 0 || mWordStatus == -1)
                {
                    // found it
                    break;
                }
                else if (mWordStatus == 2)
                {
                    // line comes before searchValue
                    mBottomFileLines = mMiddlePoint + 1;
                }
                else if (mWordStatus == 1)
                {
                    // line comes after searchValue
                    mTopFileLines = mMiddlePoint - 1;
                }
            }
            LocalInputFileReader.close();
            return mDataPageAccessesCounter;
        }
        catch (FileNotFoundException e)
        {
            System.out.println("File: " + this.Filename + " cannot be opened.");
            e.printStackTrace();
            return 0;
        }
    }

    private byte[] StringToByteArrayTranslator(String stringByteArray, int byteArraySize)
    {
        String[] mStringBytesArray = stringByteArray.split("[\\[ \\],]+");
        mStringBytesArray = Arrays.copyOfRange(mStringBytesArray, 1, mStringBytesArray.length);
        byte[] mByteArray = new byte[byteArraySize];
        for(int mIndex = 0; mIndex < byteArraySize; mIndex++)
        {
            mByteArray[mIndex] = Byte.parseByte(mStringBytesArray[mIndex]);
        }
        return mByteArray;
    }

    public static void SkipLines(Scanner s,int lineNum)
    {
        for(int index = 0; index < lineNum;index++)
        {
            if(s.hasNextLine())
            {
                s.nextLine();
            }
        }
    }

    private int BinarySearchBytePage(String word, byte[] bytePage, ArrayList<Integer> matchingPositions)
    {
        if(word.length() > SizeConstants.getMaxWordSize() || word.length() < SizeConstants.getMinWordSize())
        {
            return -1;
        }

        int mRecordSize = (SizeConstants.getMaxWordSize() + 4);
        String mWord = "";
        boolean mFoundAtFirst = false;
        boolean mFoundAtLast = false;

        for(int mIndex = 0; mIndex < bytePage.length; mIndex = mIndex + mRecordSize)
        {
            mWord = new String(Arrays.copyOfRange(bytePage, mIndex, mIndex + SizeConstants.getMaxWordSize())).replaceAll("\\s+","");

            if(mWord.equals(word))
            {
                matchingPositions.add(ByteBuffer.wrap(Arrays.copyOfRange(bytePage, mIndex + SizeConstants.getMaxWordSize(), mIndex + mRecordSize)).getInt());
                if(mIndex == 0)
                {
                    mFoundAtFirst = true;
                }
            }
        }

        if(mWord.equals(word))
        {
            mFoundAtLast = true;
        }

        if(mFoundAtFirst)
        {
            return 1;
        }
        else if(mFoundAtLast)
        {
            return 2;
        }
        if(matchingPositions.isEmpty())
        {
            return 3;
        }
        else
        {
            return  0;
        }
    }
}
