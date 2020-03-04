package Part2;

public class Tuple implements Comparable<Tuple>
{
    /**Tuple (Left) Tuple Key string*/
    private String Key;

    /**Tuple (Right) Tuple Value int*/
    private Integer Value;

    /**String,int Constructor**/
    public Tuple(String mKey, Integer mValue)
    {
        this.Key = mKey;
        this.Value = mValue;
    }

    /**Tuple (Left) Tuple Key string getter*/
    public String getKey()
    {
        return Key;
    }

    /**Tuple (Right) Tuple Value int getter*/
    public Integer getValue()
    {
        return Value;
    }

    @Override
    public int compareTo(Tuple other)
    {
        /* compareTo should return < 0 if this is supposed to be
         less than other, > 0 if this is supposed to be greater than
         other and 0 if they are supposed to be equal*/
        int mKey = this.getKey().compareTo(other.getKey());
        return mKey == 0 ? this.getKey().compareTo(other.getKey()) : mKey;
    }
}