package Part2;
import java.util.LinkedList;
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

        CMD mCmd = new CMD();
        if(InputFilename.isEmpty())
        {
            /*Create an Instance of the CMD class*/
            mCmd = new CMD(InputFilename);
        }
        else
        {
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