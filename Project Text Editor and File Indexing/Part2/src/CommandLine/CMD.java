package CommandLine;

import java.io.*;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

import static java.lang.System.exit;

/**Class that implements the command line terminal*/
public class CMD
{
    /**Last inserted command*/
    private String Command;

    /**Current line index, if -1 the file cannot be opened*/
    private int LineIndex;

    /**Linked List that contains the file lines*/
    private LinkedList<String> FileLines;

    /**Boolean flag, if true, the line numbers are printed*/
    private boolean mPrintLineNumbers = false;

    /**Filename of that file that will be opened*/
    private String Filename;

    /**Default constructor when the file cannot be opened*/
    public CMD(String mFilename)
    {
        //Set the line index to -1, if the file cannot be opened
        this.LineIndex = -1;
        this.FileLines = new LinkedList<>();
        this.Filename = mFilename;
    }

    /**Default constructor when the file can be opened*/
    public CMD(String mFilename, LinkedList<String> mFileLines)
    {
        //Set the line index to the last line of the file
        this.LineIndex = mFileLines.size() - 1;
        this.FileLines = mFileLines;
        this.Filename = mFilename;
    }

    /**Method that executes the inserted command*/
    protected void ExecuteCommand()
    {
        //Check if the inserted command is valid
        if(this.ValidateCommand())
        {
            //Set line index to first fine
            if(this.Command.equals("^"))
            {
                //-1 if the Linked List is empty, 0 if at least one line exists
                this.LineIndex = this.FileLines.isEmpty()? -1: 0;
                System.out.println("Pointer set to first line.");
            }

            //Set line index to last line
            if(this.Command.equals("$"))
            {
                //-1 if the Linked List is empty, size - 1 if at least one line exists
                this.LineIndex = this.FileLines.isEmpty()? -1: this.FileLines.size() - 1;
                System.out.println("Pointer set to last line.");
            }

            //Set line index to previous position
            if(this.Command.equals("-"))
            {
                //Check whether the Linked List is empty
                if(this.FileLines.isEmpty())
                {
                    this.LineIndex = -1;
                }
                else
                {
                    //0 if the line index is already at the first line, else decrease it
                    this.LineIndex = this.LineIndex == 0? 0: this.LineIndex - 1;
                }
                System.out.println("Pointer set to previous line.");
            }

            //Set line index to next position
            if(this.Command.equals("+"))
            {
                //size - 1 if the line index is already at the last line, else increase it
                this.LineIndex = (this.FileLines.size() - 1) == this.LineIndex ? this.LineIndex: this.LineIndex + 1;
                System.out.println("Pointer set to next line.");
            }

            //Add new line after current line
            if(this.Command.equals("a"))
            {
                System.out.println("Type the text for the new line:");
                
                //Initialize a Scanner object to read for the command line
                Scanner CMDCurrentLineScanner = new Scanner(System.in);

                //Read the entered line
                String inputLine = CMDCurrentLineScanner.nextLine();

                //Check if the inserted line length is greater than the MaxFileLineSize
                if(inputLine.length() > SizeConstants.getMaxFileLineSize())
                {
                    //Cut the inserted line to the maximum size
                    inputLine = inputLine.substring(0, SizeConstants.getMaxFileLineSize());
                    System.out.println("The inserted line is too big. Only a portion of it will be inserted");
                }
                this.FileLines.add(this.LineIndex + 1, inputLine);
                
                //Set the line index to the new line
                this.LineIndex++;
                System.out.println("New line entered successfully.");
            }

            //Add new line before current line
            if(this.Command.equals("t"))
            {
                System.out.println("Type the text for the new line:");

                //Initialize a Scanner object to read for the command line
                Scanner CMDCurrentLineScanner = new Scanner(System.in);

                //Read the entered line
                String inputLine = CMDCurrentLineScanner.nextLine();

                //Check if the inserted line length is greater than the MaxFileLineSize
                if(inputLine.length() > SizeConstants.getMaxFileLineSize())
                {
                    //Cut the inserted line to the maximum size
                    inputLine = inputLine.substring(0, SizeConstants.getMaxFileLineSize());
                    System.out.println("The inserted line is too big. Only a portion of it will be inserted");
                }

                //Check whether the Linked List is empty
                if(this.FileLines.isEmpty())
                {
                    //Add the inserted line at the first position
                    this.FileLines.add(inputLine);
                }
                else
                {
                    //Add the inserted line at the current position, and shift the current line to next position
                    this.FileLines.add(this.LineIndex, inputLine);
                }
                System.out.println("New line entered successfully.");
            }

            //Delete current line
            if(this.Command.equals("d"))
            {
                //Check whether the Linked List is empty
                if(this.FileLines.isEmpty())
                {
                    this.LineIndex = -1;
                    System.out.println("No lines to be deleted.");
                }
                else
                {
                    //Delete the current line
                    this.FileLines.remove(this.LineIndex);

                    //Set the line index
                    this.LineIndex = this.LineIndex == 0? 0: this.LineIndex - 1;
                    System.out.println("Current line deleted successfully.");
                }
            }

            //Print all lines
            if(this.Command.equals("l"))
            {
                //Line number index
                int index = 0;

                //Iterate over the file lines
                for(String mLine : this.FileLines)
                {
                    //If printing of the line number is enabled
                    if(this.mPrintLineNumbers)
                    {
                        System.out.println(index + 1 + ")   " + mLine);
                        index++;
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
                //If printing of the line number is enabled
                if(this.mPrintLineNumbers)
                {
                    //Disable the line number printing
                    this.mPrintLineNumbers = false;
                    System.out.println("Line numbers won't be displayed when printing all lines.");
                }
                else
                {
                    //Enable the line number printing
                    this.mPrintLineNumbers = true;
                    System.out.println("Line numbers will be displayed when printing all lines.");
                }
            }

            //Print current line
            if(this.Command.equals("p"))
            {
                //Check whether the Linked List is empty
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
                IndexFileBufferFileWriter mIndexTableFileWriter = new IndexFileBufferFileWriter(this.Filename);
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
                for(int index = 0; index < this.FileLines.size(); index++)
                {
                    mLineIndex.add(index + 1);
                }
                IndexingTable mIndexingTable = new IndexingTable(mWordList, mLineIndex, false);
                for(int index = 0; index < mLineIndex.size(); index++)
                {
                    System.out.println(mIndexingTable.getTupleVector().get(index).getLeftValue() + " Line: " + mIndexingTable.getTupleVector().get(index).getRightValue());
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
                    System.out.println("The word: " + mInputWord + " has not been found.");
                }
                else
                {
                    System.out.print("The word: " + mInputWord + " has been found on lines: ");
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
                    System.out.println("The word: " + mInputWord + " has not been found.");
                }
                else
                {
                    System.out.print("The word: " + mInputWord + " has been found on lines: ");
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

    /**Method that checks whether the inserted character is valid*/
    protected boolean ValidateCommand()
    {
        //Create a new ArrayList object and add the valid commands
        ArrayList<String> validCommands = new ArrayList<>();
        validCommands.add("^");
        validCommands.add("$");
        validCommands.add("-");
        validCommands.add("+");
        validCommands.add("a");
        validCommands.add("t");
        validCommands.add("d");
        validCommands.add("l");
        validCommands.add("n");
        validCommands.add("p");
        validCommands.add("q");
        validCommands.add("w");
        validCommands.add("x");
        validCommands.add("=");
        validCommands.add("#");
        validCommands.add("c");
        validCommands.add("v");
        validCommands.add("s");
        validCommands.add("b");
        
        //Check if the inserted command is valid
        return validCommands.contains(this.Command);
    }

    /**Setter for the command property*/
    protected void setCommand(String command)
    {
        Command = command;
    }

    /**Search byte page for the word, during linear search
     * @param wordToBeSearched = word to be searched
     * @param bytePage = data page in bytes
     * @param matchingPositions  = ArrayList that contains the lines that the word to be searched is present
     * @return true if the search must be continued, false if the word to be searched will not be found in the file*/
    private boolean LinearSearchBytePage(String wordToBeSearched, byte[] bytePage, ArrayList<Integer> matchingPositions)
    {
        //If the wordToBeSearched's length is less than MinWordSize or greater than MaxWordSize, then the search fails automatically
        if(wordToBeSearched.length() > SizeConstants.getMaxWordSize() || wordToBeSearched.length() < SizeConstants.getMinWordSize())
        {
            return false;
        }

        //Get the index entry size in ASCII characters
        int indexEntrySize = (SizeConstants.getMaxWordSize() + 4);

        //Search for every entry in the byte page
        for(int index = 0; index < bytePage.length; index = index + indexEntrySize)
        {
            //Get only the word from the data page and convert the bytes to ASCII characters
            String dataPageEntryWord = new String(Arrays.copyOfRange(bytePage, index, index + SizeConstants.getMaxWordSize())).replaceAll("\\s+","");

            //Compare the index entry word and the word to be searched
            if(dataPageEntryWord.equals(wordToBeSearched))
            {
                //Get the line number from the index entry and add it to the ArrayList
                matchingPositions.add(ByteBuffer.wrap(Arrays.copyOfRange(bytePage, index + SizeConstants.getMaxWordSize(), index + indexEntrySize)).getInt());
            }
        }
        return true;
    }

    /**Read the index file and execute linear search for the word to be searched
     * @param filename = the index file filename
     * @param matchingPositions = ArrayList that contains the lines that the word to be searched is present
     * @param wordToBeSearched = word to be searched
     * @return the number of data page accesses that were needed during the search, or 0 if the file cannot be opened*/
    private int LinearReadIndexFile(String filename, ArrayList<Integer> matchingPositions, String wordToBeSearched)
    {
        try
        {        
            //Try to open the index file using a Scanner
            Scanner indexFileFileScanner = new Scanner(new File(filename + ".ndx"));

            //Counter for the data page accesses
            int dataPageCounter = 0;
            
            //Flag for the search process status
            boolean searchStatus = true;
            
            //If the word to be searched length is valid, scan every data page in the file
            while(indexFileFileScanner.hasNext() && searchStatus)
            {
                //Fetch the data page into the ByteBuffer
                ByteBuffer bytePageBuffer = ByteBuffer.wrap(StringToByteArrayTranslator(indexFileFileScanner.nextLine(), SizeConstants.getBufferSize()));

                //Search for the word to be searched in the data page
                searchStatus = LinearSearchBytePage(wordToBeSearched,bytePageBuffer.array(), matchingPositions);

                //Count the data page access
                dataPageCounter++;
            }
            
            //Close the FileScanner
            indexFileFileScanner.close();
            
            //Return the number of data page accesses
            return dataPageCounter;
        }
        catch (FileNotFoundException e)
        {
            //Catch the FileNotFoundException and inform the user
            System.out.println("File: " + this.Filename + " cannot be opened.");
            e.printStackTrace();
            return 0;
        }
    }

    /**Read the index file and execute binary search for the word to be searched
     * @param filename = the index file filename
     * @param matchingPositions = ArrayList that contains the lines that the word to be searched is present
     * @param wordToBeSearched = word to be searched
     * @return the number of data page accesses that were needed during the search, or 0 if the file cannot be opened*/
    private int BinaryReadIndexFile(String filename, ArrayList<Integer> matchingPositions, String wordToBeSearched)
    {
        try
        {
            //Initialize the file object
            File LocalInputFile = new File(filename + ".ndx");

            //Try to open the index file using a Scanner
            Scanner indexFileFileScanner = new Scanner(LocalInputFile);

            //Get the files lines number
            int mMaxFileLines = 0;
            while(indexFileFileScanner.hasNext())
            {
                //Read every line in the file
                indexFileFileScanner.nextLine();
                mMaxFileLines++;
            }

            //Index to the bottom part of the search area of the file
            int bottomFilePageIndex = 0;
            
            //Index to the top part of the search area of the file
            int topFilePageIndex = mMaxFileLines;

            //Index to the middle data page of the search area of the file
            int middleFilePageIndex;
            
            //Counter for the data page accesses
            int dataPageAccessesCounter = 0;
            
            //Status of the search process
            int searchStatus;

            //Search while there are data pages to access, if topFilePageIndex is greater than bottomFilePageIndex, the search fails automatically
            while (bottomFilePageIndex <= topFilePageIndex)
            {
                //Reinitialize the FileScanner object
                indexFileFileScanner = new Scanner(LocalInputFile);
                
                //Find the middle data page index
                middleFilePageIndex = (bottomFilePageIndex + topFilePageIndex)/2;

                //Skip the lines until the middle point
                SkipLines(indexFileFileScanner, middleFilePageIndex);

                //Fetch the data page from the file
                ByteBuffer bytePageBuffer = ByteBuffer.wrap(StringToByteArrayTranslator(indexFileFileScanner.nextLine(), SizeConstants.getBufferSize()));

                //Execute the binary search in the page
                searchStatus = BinarySearchBytePage(wordToBeSearched,bytePageBuffer.array(), matchingPositions);

                //Count the data page access
                dataPageAccessesCounter++;

                //If the word's to be searched length is invalid or the word is found in the page, but is neither the first or the last word in the page
                if (searchStatus == 0 || searchStatus == -1)
                {
                    //Complete the binary search
                    break;
                }
                else if (searchStatus == 2)
                {
                    //If the search must be continued in the top part of the search area of the file
                    bottomFilePageIndex = middleFilePageIndex + 1;
                }
                else if (searchStatus == 1)
                {
                    //If the search must be continued in the bottom part of the search area of the file
                    topFilePageIndex = middleFilePageIndex - 1;
                }
            }
            //Close the FileScanner object
            indexFileFileScanner.close();

            //Return the number of the data page file accesses
            return dataPageAccessesCounter;
        }
        catch (FileNotFoundException e)
        {
            //Catch the FileNotFoundException and inform the user
            System.out.println("File: " + this.Filename + " cannot be opened.");
            e.printStackTrace();
            return 0;
        }
    }

    /**Convert a string data page from the index file to a byte array
     * @param stringByteArray = String that contains the data page
     * @param byteArraySize = Data page size in bytes
     * @return the byte array representation of the data page*/
    private byte[] StringToByteArrayTranslator(String stringByteArray, int byteArraySize)
    {
        //Remove the characters:[],space for the byte array string
        String[] stringDataPageBytes = stringByteArray.split("[\\[ \\],]+");
        
        //Get only the bytes from the data page string
        stringDataPageBytes = Arrays.copyOfRange(stringDataPageBytes, 1, stringDataPageBytes.length);
        
        //Create an empty byte array
        byte[] byteArray = new byte[byteArraySize];
        for(int index = 0; index < byteArraySize; index++)
        {
            //Parse the string byte to byte and add it to byte array
            byteArray[index] = Byte.parseByte(stringDataPageBytes[index]);
        }
        
        //Return the byte array
        return byteArray;
    }

    /**Method that skips lines from a FileScanner object
     * @param fileScanner = File Scanner object
     * @param lineNumber = Number of lines to be skipped*/
    public static void SkipLines(Scanner fileScanner,int lineNumber)
    {
        for(int index = 0; index < lineNumber;index++)
        {
            if(fileScanner.hasNextLine())
            {
                //Skip the line
                fileScanner.nextLine();
            }
        }
    }

    /**Search byte page for the word, during binary search
     * @param wordToBeSearched = word to be searched
     * @param bytePage = data page in bytes
     * @param matchingPositions  = ArrayList that contains the lines that the word to be searched is present
     * @return true if the search must be continued, false if the word to be searched will not be found in the file*/
    private int BinarySearchBytePage(String wordToBeSearched, byte[] bytePage, ArrayList<Integer> matchingPositions)
    {
        //If the wordToBeSearched's length is less than MinWordSize or greater than MaxWordSize, then the search fails automatically
        if(wordToBeSearched.length() > SizeConstants.getMaxWordSize() || wordToBeSearched.length() < SizeConstants.getMinWordSize())
        {
            return -1;
        }

        //Get the index entry size in ASCII characters
        int indexEntrySize = (SizeConstants.getMaxWordSize() + 4);

        //Initialize the String representation of the word in the data page entry
        String dataPageEntryWord = "";

        //Initialize the String representation of the last non blank(space filled) word in the page
        String lastNonBlankWord = "";

        //Flag, true if the word to be searched is matched to the first entry of the data page
        boolean foundAtFirstEntry = false;

        //Flag, true if the word to be searched is matched to the first entry of the data page
        boolean foundAtLastEntry = false;

        //Search for every entry in the byte page
        for(int index = 0; index < bytePage.length; index = index + indexEntrySize)
        {
            //Get only the word from the data page and convert the bytes to ASCII characters
            dataPageEntryWord = new String(Arrays.copyOfRange(bytePage, index, index + SizeConstants.getMaxWordSize())).replaceAll("\\s+","");

            //Compare the index entry word and the word to be searched
            if(dataPageEntryWord.equals(wordToBeSearched))
            {
                //Get the line number from the index entry and add it to the ArrayList
                matchingPositions.add(ByteBuffer.wrap(Arrays.copyOfRange(bytePage, index + SizeConstants.getMaxWordSize(), index + indexEntrySize)).getInt());
                if(index == 0)
                {
                    //Mark that the word to be searched is matched to the first entry of the data page
                    foundAtFirstEntry = true;
                }
            }

            //Check if the word in the data page entry is not blank
            if(!dataPageEntryWord.trim().isBlank())
            {
                //Store the last non blank data page entry word
                lastNonBlankWord = dataPageEntryWord;
            }
        }

        //Compare the last index entry word and the word to be searched
        if(dataPageEntryWord.equals(wordToBeSearched))
        {
            //Mark that the word to be searched is matched to the last entry of the data page
            foundAtLastEntry = true;
        }

        //If the ArrayList is empty, the word to be searched wasn't found in the data page
        if(matchingPositions.isEmpty())
        {
            //Check if the word to be searched is alphabetically before or after the last non blank word
            if(wordToBeSearched.compareTo(lastNonBlankWord) < 0)
            {
                //Search the bottom part of the search area
                return 1;
            }
            else if(wordToBeSearched.compareTo(lastNonBlankWord) > 0)
            {
                //Search the top part of the search area
                return 2;
            }
        }
        else
        {
            //Search for other occurrences of the word to be searched
            if(foundAtFirstEntry)
            {
                //Search the bottom part of the search area
                return 1;
            }
            else if(foundAtLastEntry)
            {
                //Search the top part of the search area
                return 2;
            }
        }
        //Continue the binary search normally
        return  0;
    }
}
