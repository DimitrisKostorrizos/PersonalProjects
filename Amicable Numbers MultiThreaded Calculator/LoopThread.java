import java.util.Arrays;
import java.util.List;

/**
 * The thread method definition
 */
public class LoopThread implements Runnable
{
    /**
     * The thread payload
     */
    private final List<int[]> Combinations;

    /**
     * The number of found amicable numbers
     */
    private int FoundAmicableNumbers = 0;

    /**
     * Default constructor
     * @param payload The thread payload
     */
    public LoopThread(List<int[]> payload)
    {
        // Set the payload
        this.Combinations = payload;
    }

    /**
     * The execution definition of the thread
     */
    @Override
    public void run()
    {
        // For every combination...
        for (int[] combination : this.Combinations)
        {
            // If the number are amicable...
            if (CheckAmicableNumbers(combination[0], combination[1]))
            {
                // Count the amicable combination
                this.FoundAmicableNumbers += 1;

                // Print the message
                System.out.println("Thread with id : " + Thread.currentThread().getId() +
                        " found that " + combination[0] + " and " + combination[1] + " are amicable.");
            }
        }

        // Get the first value
        String firstValue = Arrays.toString(this.Combinations.get(0));

        // Get the last combination
        String lastValue = Arrays.toString(this.Combinations.get(this.Combinations.size() - 1));

        // Print the message
        System.out.println("Thread with id : " + Thread.currentThread().getId() +
                ". Range: (" + firstValue + ") - (" + lastValue +")" + ". Found combinations: " + this.FoundAmicableNumbers + ".");
    }

    /**
     * Check whether the the two numbers are amicable
     * @param firstNumber The first number
     * @param secondNumber The second number
     * @return True if the number are amicable
     */
    private static boolean CheckAmicableNumbers(int firstNumber, int secondNumber)
    {
        // Initialize the sums for the two numbers
        int firstNumberSum = 0, secondNumberSum = 0;

        // For the first number...
        for (int index = 1; index <= firstNumber; index++)
        {
            // If the index divides perfectly the number...
            if (firstNumber % index == 0)
                // Add the index
                firstNumberSum += index;
        }

        // For the second number...
        for (int index = 1; index <= secondNumber; index++)
        {
            // If the index divides perfectly the number...
            if (secondNumber % index == 0)
                // Add the index
                secondNumberSum += index;
        }

        // Return whether the numbers are amicable
        return firstNumberSum == secondNumberSum;
    }
}