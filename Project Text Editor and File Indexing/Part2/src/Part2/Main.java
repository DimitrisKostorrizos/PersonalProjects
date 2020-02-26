package Part2;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        SizeConstants mGlobalSizeConstants = new SizeConstants(128, 5, 20);
        /** Create a Scanner object to read the filename*/
//        Scanner LocalInputFileReader = new Scanner(System.in);
//        System.out.println("Enter the filename:");
//        String InputFilename = LocalInputFileReader.nextLine();

        FileScanner InputFileScanner = new FileScanner("test.txt");
        IndexingTable InputFileTable = InputFileScanner.OpenFile();
        BufferIndexFileWriter IndexFileWriter = new BufferIndexFileWriter("test");
        IndexFileWriter.BufferPrint(InputFileTable);
    }
}