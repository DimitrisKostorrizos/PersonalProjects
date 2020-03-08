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
     * @param tupleStrings = Array list that contain the string keys for the tuple*/
    public IndexingTable(ArrayList<String> tupleStrings, ArrayList<Integer> tupleValues, boolean sorted)
    {
        for (int index = 0; index < tupleStrings.size(); index++)
        {
            TupleVector.add(new Tuple(tupleStrings.get(index),tupleValues.get(index)));
        }

        if(sorted)
        {
            TupleVector.sort(Tuple::compareTo);
        }
    }

    /**Vector that contains String,Int tuples getter*/
    public Vector<Tuple> getTupleVector()
    {
        return TupleVector;
    }
}
