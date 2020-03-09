package CommandLine;

/**A pair that contains a string(Left) and Integer(Right) value named Tuple*/
public class Tuple implements Comparable<Tuple>
{
    /**Left string value of the tuple*/
    private String Key;

    /**Right integer value of the tuple*/
    private Integer Value;

    /**Default tuple constructor
     * @param key = the string left value of the Tuple
     * @param value = the integer right value of the Tuple**/
    public Tuple(String key, Integer value)
    {
        this.Key = key;
        this.Value = value;
    }

    /**Getter for the string part of the Tuple object*/
    public String getLeftValue()
    {
        return Key;
    }

    /**Getter for the integer part of the Tuple object*/
    public Integer getRightValue()
    {
        return Value;
    }

    /**Implementing the IComparable method compareTo,
     * @param tupleObject = a Tuple object to compare to this Tuple
     * so that the tuples can be sorted alphabetically based on the key property*/
    @Override
    public int compareTo(Tuple tupleObject)
    {
        /*Method compareTo should return < 0 if this is supposed to be
         less than tupleObject, > 0 if this is supposed to be greater than
         tupleObject and 0 if they are supposed to be equal*/
        int key = this.getLeftValue().compareTo(tupleObject.getLeftValue());
        return key == 0 ? this.getLeftValue().compareTo(tupleObject.getLeftValue()) : key;
    }
}