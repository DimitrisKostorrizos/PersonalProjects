package CommandLine;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        String InputFilename = "test.txt";

        /*Initialise the Size Constants object*/
        new SizeConstants(128, 5, 20, 80);

        /* Create a Scanner object to read the the Command Line*/
        Scanner CMDScanner = new Scanner(System.in);

        CMD mCmd;
        if(InputFilename.isEmpty())
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



        /*Read the Command Line Terminal*/
        while(true)
        {
            System.out.println("CMD> ");
            String mInputCommand = CMDScanner.nextLine();
            mCmd.setCommand(mInputCommand);
            mCmd.ExecuteCommand();
        }


    }
}