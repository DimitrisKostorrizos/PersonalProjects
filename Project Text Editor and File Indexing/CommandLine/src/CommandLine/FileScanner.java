package CommandLine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**Class that reads a file and creates a Linked List object that contains the file lines*/
public class FileScanner
{
    /**Local file's filename*/
    private String Filename;

    /**Default constructor for the FileScanner object
     * @param filename = local file's filename*/
    protected FileScanner(String filename)
    {
        Filename = filename;
    }

    /**Method that returns the Linked List object that contains the file lines
     * @return the Linked List that contains the file lines in ascending order, or null if the file cannot be read*/
    protected LinkedList<String> FileToLinkedList()
    {
        //Try to open the local file
        try
        {
            //Create the Scanner object for the local file
            Scanner localInputFileScanner = new Scanner(new File(this.Filename));
            
            //Call the CreateFileLinkedList method that creates the Linked List object
            LinkedList<String> fileList = CreateFileLinkedList(localInputFileScanner);
            
            //Close the File Scanner
            localInputFileScanner.close();
            
            //Return the File Linked List
            return fileList;
        }
        catch (FileNotFoundException e)
        {
            //Catch the FileNotFoundException and inform the user
            System.out.println("File: " + this.Filename + " cannot be opened.");
            e.printStackTrace();
            return null;
        }
    }

    /**Default constructor for the FileScanner object
     * @param localInputFileScanner = local file scanner
     * @return the Linked List that contains the file lines in ascending order*/
    private LinkedList<String> CreateFileLinkedList(Scanner localInputFileScanner)
    {
        //Create a linked List object for the lines of the file
        LinkedList<String> fileLinesLinkedList = new LinkedList<>();

        //Read the file, line by line until the last line
        while (localInputFileScanner.hasNextLine())
        {
            //Get the current line
            String fileLine = localInputFileScanner.nextLine();

            //Cut the file line if the length exceeds MaxFileLineSize value
            if(fileLine.length() > SizeConstants.getMaxFileLineSize())
            {
                //Get the valid part of the file line
                fileLine = fileLine.substring(0,SizeConstants.getMaxFileLineSize());
            }

            //Add the valid file line in the Linked List
            fileLinesLinkedList.add(fileLine);
        }

        //Return the LinkedList that contain the file lines
        return fileLinesLinkedList;
    }
}