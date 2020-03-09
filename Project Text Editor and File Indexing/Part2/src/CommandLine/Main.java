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
//        if(args.length != 1)
//        {
//            System.out.println("Enter only a txt filename to continue.");
//            exit(0);
//        }

        //Get the filename from the command line argument array
        //String InputFilename = args[0];

        String InputFilename = "test.txt";
        boolean validFile;

        //Check if the file exists and can be opened
        try
        {
            new Scanner(new File(InputFilename));
            validFile = true;
        }
        catch (FileNotFoundException e)
        {
            validFile = false;
            e.printStackTrace();
        }

        //Initialise the Size Constants object
        new SizeConstants(128, 5, 20, 80);

        //Create a Scanner object to read from the the Command Line
        Scanner CMDScanner = new Scanner(System.in);

        //Create a CMD, a Command Line Terminal object
        CMD mCmd;

        if(validFile)
        {
            /*Create an Instance of the CMD class*/
            mCmd = new CMD(InputFilename);
        }
        else
        {
            /*Create an Instance of the FilEScanner Class to read the Input */
            FileScanner InputFileScanner = new FileScanner(InputFilename);

            /*Create an Instance of the CMD class*/
            mCmd = new CMD(InputFilename, InputFileScanner.FileToLinkedList());
        }



        //Read the Command Line Terminal
        while(true)
        {
            System.out.println("CMD> ");

            //Read the inserted command
            String mInputCommand = CMDScanner.nextLine();

            //Set the last inserted command
            mCmd.setCommand(mInputCommand);

            //Execute the last inserted command
            mCmd.ExecuteCommand();
        }
    }
}