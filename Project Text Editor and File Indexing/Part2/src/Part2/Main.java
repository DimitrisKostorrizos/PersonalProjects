package Part2;
import java.util.LinkedList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        SizeConstants mGlobalSizeConstants = new SizeConstants(128, 5, 20, 80);
        /** Create a Scanner object to read the filename*/
//        Scanner LocalInputFileReader = new Scanner(System.in);
//        System.out.println("Enter the filename:");
//        String InputFilename = LocalInputFileReader.nextLine();
        String InputFilename = "test.txt";

        FileScanner InputFileScanner = new FileScanner(InputFilename);
//        IndexingTable InputFileTable = InputFileScanner.OpenFileIndexingTable();
//        BufferIndexFileWriter IndexFileWriter = new BufferIndexFileWriter(InputFilename);
//        IndexFileWriter.BufferPrint(InputFileTable);
        LinkedList<String> InputFileLinkedList = InputFileScanner.OpenFileLinkedList();
    }
}