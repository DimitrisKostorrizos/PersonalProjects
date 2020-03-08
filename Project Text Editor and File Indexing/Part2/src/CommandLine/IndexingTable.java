package CommandLine;

import java.util.ArrayList;
import java.util.Vector;

/**A vector that contains Tuple objects, to implement an Indexing Table*/
public class IndexingTable
{
    /**Vector that contains Tuple objects*/
    private Vector<Tuple> TupleVector = new Vector<>();

    /**Default constructor to create a sorted or unsorted indexing table object
     * @param tupleStrings = Array list that contain the string keys for the tuple
     * @param tupleValues = Array list that contain the integer values for the tuple
     * @param  sorted = Boolean option to sort or not the indexing table object alphabetically*/
    public IndexingTable(ArrayList<String> tupleStrings, ArrayList<Integer> tupleValues, boolean sorted)
    {
        //Add the Tuple objects into the TupleVector
        for (int index = 0; index < tupleStrings.size(); index++)
        {
            TupleVector.add(new Tuple(tupleStrings.get(index),tupleValues.get(index)));
        }

        //Sort the indexing table if sorted is true
        if(sorted)
        {
            TupleVector.sort(Tuple::compareTo);
        }
    }

    /**Getter for the vector that implements the indexing table*/
    public Vector<Tuple> getTupleVector()
    {
        return TupleVector;
    }
}
