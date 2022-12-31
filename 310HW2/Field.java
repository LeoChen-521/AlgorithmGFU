/**
 *  The Field class defines an object that models a field full of foxes and
 *  hounds.
 */
public class Field 
{   
   private FieldOccupant[][] _fieldOccupants;
   /**
    *  Creates an empty field of given width and height.
    *
    *  @param width of the field.
    *  @param height of the field.
    */
   public Field (int width, int height) 
   {
      _fieldOccupants = new FieldOccupant[width][height];
   }

   
   /**
    *  Get the width of the field.
    *  @return the width of the field.
    */
   public int getWidth() 
   {
       return _fieldOccupants.length;
   }


   /**
    *  Get the height of the field.
    *  @return the height of the field.
    */
   public int getHeight() 
   {
      return _fieldOccupants[0].length;
   }


   /**
    *  Place an occupant in cell (x, y). Add a occupant into the two-dimension 
    *  array.
    *
    *  @param x is the x-coordinate of the cell to place a mammal in.
    *  @param y is the y-coordinate of the cell to place a mammal in.
    *  @param toAdd is the occupant to place.
    */
   public void setOccupantAt(int x, int y, FieldOccupant toAdd) 
   {
      _fieldOccupants[x][y] = toAdd;
   }


   /**
    *  Get the occupant of the cell.
    *  @param x is the x-coordinate of the cell whose contents are queried.
    *  @param y is the y-coordinate of the cell whose contents are queried.
    *
    *  @return occupant of the cell (or null if unoccupied)
    */
   public FieldOccupant getOccupantAt(int x, int y) 
   {
      return _fieldOccupants[x][y];
   }


   /**
    *  Define any variables associated with a Field object here.  These
    *  variables MUST be private.
    */

}