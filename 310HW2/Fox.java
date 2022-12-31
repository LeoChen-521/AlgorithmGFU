import java.awt.Color;

/**
 * Foxes can display themselves
 */
public class Fox extends FieldOccupant 
{ 
   /**
    * Get the color of the cells that have Foxes.
    * @return the color to use for a cell occupied by a Fox
    */
   @Override
   public Color getDisplayColor()
   {
      return Color.green;
   } // getDisplayColor
   
   
   public String toString()
   {
      return "F";
   }

}