package Part2;

import java.util.ArrayList;
import java.util.Vector;

public class IndexingTable
{
    /**Vector that contains String,Int tuples*/
    private Vector<Tuple> TupleVector = new Vector<>();

    /**String ArrayList, int ArrayList Constructor, boolean sort*/
    public IndexingTable(ArrayList<String> mTupleKeys, ArrayList<Integer> tupleValues, boolean sorted)
    {
        for (int index = 0; index < mTupleKeys.size(); index++)
        {
            TupleVector.add(new Tuple(mTupleKeys.get(index),tupleValues.get(index)));
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
