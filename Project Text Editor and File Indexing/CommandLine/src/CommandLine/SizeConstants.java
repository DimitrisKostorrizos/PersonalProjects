package CommandLine;

/**Final class that contains the constant values for the Command Line execution*/
public final class SizeConstants
{
    /**Max buffer(Disk Page) size*/
    private static int BufferSize;

    /**Minimum word length size*/
    private static int MinWordSize;

    /**Maximum word length size*/
    private static int MaxWordSize;

    /**Maximum file line length size*/
    private static int MaxFileLineSize;

    /**Default constructor to create a SizeConstants object 
     * @param bufferSize = buffer size in bytes
     * @param minWordSize = minimum word length in ASCII characters
     * @param maxWordSize = maximum word length in ASCII characters
     * @param maxFileLineSize = maximum line length that can be read from a file in ASCII characters
     * that contains the constant valued and is globally available*/
    public SizeConstants(int bufferSize, int minWordSize, int maxWordSize, int maxFileLineSize)
    {
        BufferSize = bufferSize;
        MinWordSize = minWordSize;
        MaxWordSize = maxWordSize;
        MaxFileLineSize = maxFileLineSize;
    }

    /**Getter for the buffer size property*/
    public static int getBufferSize()
    {
        return BufferSize;
    }

    /**Getter for minimum word length size*/
    public static int getMinWordSize()
    {
        return MinWordSize;
    }

    /**Getter for maximum word length size*/
    public static int getMaxWordSize()
    {
        return MaxWordSize;
    }

    /**Getter for maximum file line length size*/
    public static int getMaxFileLineSize()
    {
        return MaxFileLineSize;
    }
}
