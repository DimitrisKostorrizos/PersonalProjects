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

        /*Create an Instance of the CMD class*/
        CMD mCmd = new CMD();

        /*Read the Command Line Terminal until the word Exit is inserted*/
        boolean mExit = false;
        while(!mExit)
        {
            System.out.println("CMD> ");
            String mInputCommand = CMDScanner.nextLine();
            if(mInputCommand.equals("Exit"))
            {
                mExit = true;
            }
            else
            {
                mCmd.setCommand(mInputCommand);
                mCmd.ExecuteCommand();
            }
        }

        FileScanner InputFileScanner = new FileScanner(InputFilename);
//        IndexingTable InputFileTable = InputFileScanner.OpenFileIndexingTable();
//        BufferIndexFileWriter IndexFileWriter = new BufferIndexFileWriter(InputFilename);
//        IndexFileWriter.BufferPrint(InputFileTable);
        LinkedList<String> InputFileLinkedList = InputFileScanner.OpenFileLinkedList();
    }
}