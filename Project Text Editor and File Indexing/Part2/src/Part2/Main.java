package Part2;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        /** Create a Scanner object to read the filename*/
//        Scanner LocalInputFileReader = new Scanner(System.in);
//        System.out.println("Enter the filename:");
//        String InputFilename = LocalInputFileReader.nextLine();

        FileScanner InputFileScanner = new FileScanner("test.txt");
        IndexingTable InputFileTable = InputFileScanner.OpenFile();
    }
}