package CommandLine;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import static java.lang.System.exit;

/**Main project class*/
public class Main
{
    /**Project's main function
     * @param args = args[0] is the file filename*/
    public static void main(String[] args)
    {
        //Check there is only one argument
        if(args.length != 1)
        {
            System.out.println("Enter only one txt filename to continue.");
            exit(0);
        }

        //Get the filename from the command line argument array
        String inputFilename = args[0];

        //Flag for the inserted file status
        boolean validFile;

        //Check if the file exists and can be opened
        try
        {
            new Scanner(new File(inputFilename));
            validFile = true;
        }
        catch (FileNotFoundException e)
        {
            validFile = false;
        }

        //Initialise the Size Constants static object
        new SizeConstants(128, 5, 20, 80);

        //Create a Scanner object to read from the the Command Line
        Scanner cmdScanner = new Scanner(System.in);

        //Create a CMD, a Command Line Terminal object
        CMD cmd;

        //Check whether the file exists and can be opened
        if(!validFile)
        {
            //If not, create an instance of the CMD class and an empty txt file
            cmd = new CMD(inputFilename);
        }
        else
        {
            //if yes, create an instance of the FileScanner Class to read the Input
            FileScanner inputFileScanner = new FileScanner(inputFilename);

            //Create an instance of the CMD class and open the inserted txt file
            cmd = new CMD(inputFilename, inputFileScanner.FileToLinkedList());
        }



        //Read the Command Line Terminal
        while(true)
        {
            System.out.println("CMD> ");

            //Read the inserted command
            String inputCommand = cmdScanner.nextLine();

            //Set the last inserted command
            cmd.setCommand(inputCommand);

            //Execute the last inserted command
            cmd.ExecuteCommand();
        }
    }
}