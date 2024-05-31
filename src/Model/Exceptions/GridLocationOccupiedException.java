package Model.Exceptions;

public class GridLocationOccupiedException extends Exception
{
    public GridLocationOccupiedException(String errorMessage, Throwable errorRoot)
    {
        super(errorMessage, errorRoot);
    }

    public GridLocationOccupiedException(String errorMessage)
    {
        super(errorMessage);
    }
}