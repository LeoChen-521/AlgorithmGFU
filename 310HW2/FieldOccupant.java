import java.awt.Color;

/**
 * Abstract parent class for objects that can occupy a cell in the Field. So we
 * can use the method of this class in the classes that inherent this class. 
 */
public abstract class FieldOccupant
{ 
   /**
    * Get the color of an occupant in the cell.
    * @return the color to use for a cell containing a particular kind
    *         of occupant
    */
   abstract public Color getDisplayColor();
}