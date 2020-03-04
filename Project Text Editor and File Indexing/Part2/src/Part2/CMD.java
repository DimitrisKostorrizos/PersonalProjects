package Part2;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
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

    /**CMD Empty Constructor*/
    public CMD() { }

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
                ArrayList<String> mLineTable = new ArrayList<>(this.FileLines);
                BufferIndexFileWriter mIndexTableFileWriter = new BufferIndexFileWriter(this.Filename);
                ArrayList<Integer> mLineIndex = new ArrayList<>();
                for(int mIndex = 0; mIndex < this.FileLines.size(); mIndex++)
                {
                    mLineIndex.add(mIndex);
                }
                IndexingTable mIndexingTable = new IndexingTable(mLineTable, mLineIndex);
                mIndexTableFileWriter.IndexingTableByteFileWrite(mIndexingTable);
                System.out.println("Index file was created successfully.");
            }

            //Print index file
            if(this.Command.equals("v"))
            {
                ArrayList<String> mLineTable = new ArrayList<>(this.FileLines);
                ArrayList<Integer> mLineIndex = new ArrayList<>();
                for(int mIndex = 0; mIndex < this.FileLines.size(); mIndex++)
                {
                    mLineIndex.add(mIndex);
                }
                IndexingTable mIndexingTable = new IndexingTable(mLineTable, mLineIndex);
                for(int mIndex = 0; mIndex < mLineIndex.size(); mIndex++)
                {
                    System.out.println(mIndexingTable.getTupleVector().get(mIndex).getKey() + " Line: " + mIndexingTable.getTupleVector().get(mIndex).getValue());
                }
            }

            //Print lines of word serial(Linear) search.
            if(this.Command.equals("s"))
            {
                //To add
            }

            //Print lines of word binary search.
            if(this.Command.equals("b"))
            {
                //To add
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
    public void setCommand(String command)
    {
        Command = command;
    }
}
