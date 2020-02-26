package Part2;

public class Tuple implements Comparable<Tuple>
{
    /**Tuple (Left) Tuple key string*/
    private String key;

    /**Tuple (Right) Tuple Value int*/
    private int value;

    /**String,int Constructor**/
    public Tuple(String key, int value) 
    {
        this.key = key;
        this.value = value;
    }

    /**Tuple (Left) Tuple key string getter*/
    public String getkey()
    {
        return key;
    }

    /**Tuple (Right) Tuple Value int getter*/
    public int getValue()
    {
        return value;
    }

    /**Tuple (Left) Tuple key string setter*/
    public void setkey(String key)
    {
        key = key;
    }

    /**Tuple (Right) Tuple Value int setter*/
    public void setValue(int value)
    {
        this.value = value;
    }

    @Override
    public int compareTo(Tuple other)
    {
        /** compareTo should return < 0 if this is supposed to be
         less than other, > 0 if this is supposed to be greater than
         other and 0 if they are supposed to be equal*/
        int mkey = this.getkey().compareTo(other.getkey());
        return mkey == 0 ? this.getkey().compareTo(other.getkey()) : mkey;
    }
}