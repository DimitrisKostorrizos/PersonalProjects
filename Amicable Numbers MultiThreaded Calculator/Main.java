import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        // Initialize a scanner for the command line
        Scanner commandLineScanner = new Scanner(System.in);

        // Print the message
        System.out.println("Enter N.");

        // Read the number
        int N = commandLineScanner.nextInt();

        // Get the unique combinations
        ArrayList<int[]> combinations = GetUniqueCombinations(N);

        // Print the message
        System.out.println("Enter the number of threads.");

        // Read the number of threads
        int numberOfThreads = commandLineScanner.nextInt();

        // Get the payload size for each thread
        int payload = Math.floorDiv(combinations.size(), numberOfThreads);

        // Get the residual
        int residual = Math.floorMod(combinations.size(), payload);

        // For every thread...
        for (int index = 0; index < numberOfThreads; index++)
        {
            // Calculate the starting index
            int startingIndex = index * payload;

            // Calculate the ending index
            int endingIndex = startingIndex + payload;

            // If there is residual payload...
            if(index == numberOfThreads - 1 && residual != 0)
                // Add it to the last thread
                endingIndex += residual;

            // Initialize the thread with the corresponding payload
            Thread thread = new Thread(new LoopThread(combinations.subList(startingIndex, endingIndex)));

            // Execute the thread
            thread.start();
        }
    }

    /**
     * Calculate and return the unique combinations for the number between 1 and N
     * @param N The maximum number
     * @return An array list that contains the unique combinations
     */
    private static ArrayList<int[]> GetUniqueCombinations(int N)
    {
        //Declare an array list for the results
        ArrayList<int[]> result = new ArrayList<>();

        // For every number...
        for (int index = 1; index <= N; index++)
            // For every number, except the current number...
            for(int secondaryIndex = index + 1; secondaryIndex <= N; secondaryIndex++)
                // Add the combination
                result.add(new int[]{index, secondaryIndex});

        // Return the result
        return result;
    }
}