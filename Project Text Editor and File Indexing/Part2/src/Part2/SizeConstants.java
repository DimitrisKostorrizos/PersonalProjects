package Part2;

public final class SizeConstants
{
    /**Max Buffer(Disk Page) Size*/
    private static int BufferSize;

    /**Minimum Word Length Size*/
    private static int MinWordSize;

    /**Maximum Word Length Size*/
    private static int MaxWordSize;

    /**Maximum File Line Length Size*/
    private static int MaxFileLineSize;

    /**Default Class Constructor*/
    public SizeConstants(int mBufferSize, int mMinWordSize, int mMaxWordSize, int mMaxFileLineSize)
    {
        BufferSize = mBufferSize;
        MinWordSize = mMinWordSize;
        MaxWordSize = mMaxWordSize;
        MaxFileLineSize = mMaxFileLineSize;
    }

    /**Max Buffer(Disk Page) Size getter*/
    public static int getBufferSize()
    {
        return BufferSize;
    }

    /**Minimum Word Length Size getter*/
    public static int getMinWordSize()
    {
        return MinWordSize;
    }

    /**Maximum Word Length Size getter*/
    public static int getMaxWordSize()
    {
        return MaxWordSize;
    }

    /**Maximum File Line Length Size getter*/
    public static int getMaxFileLineSize()
    {
        return MaxFileLineSize;
    }

    /**Max Buffer(Disk Page) Size setter*/
    public static void setBufferSize(int bufferSize)
    {
        BufferSize = bufferSize;
    }

    /**Minimum Word Length Size setter*/
    public static void setMinWordSize(int minWordSiz)
    {
        MinWordSize = minWordSiz;
    }

    /**Maximum Word Length Size setter*/
    public static void setMaxWordSize(int maxWordSize)
    {
        MaxWordSize = maxWordSize;
    }

    /**Maximum File Line Length Size setter*/
    public static void setMaxFileLineSize(int maxFileLineSize)
    {
        MaxFileLineSize = maxFileLineSize;
    }
}
