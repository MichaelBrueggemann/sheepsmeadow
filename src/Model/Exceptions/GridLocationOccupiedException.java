/*
Copyright (C) 2024 Michael Brüggemann

This file is part of Sheepsmeadow.

Sheepsmeadow is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Sheepsmeadow is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Sheepsmeadow. If not, see <https://www.gnu.org/licenses/>.
*/

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