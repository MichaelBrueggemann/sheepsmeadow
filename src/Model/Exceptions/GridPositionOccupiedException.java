package Model.Exceptions;

public class GridPositionOccupiedException extends Exception
{
    public GridPositionOccupiedException(String errorMessage, Throwable errorRoot)
    {
        super(errorMessage, errorRoot);
    }
}