import java.awt.Color;

/**
 * Hounds can display themselves
 */
public class Hound extends FieldOccupant 
{ 
   // Instance variable.
   private int _starveTime;
   
   
   /**
    * Create a new hound that has a full default starve time. So we make the 
    * state of the hound equals to the default starve time.
    */
   public Hound()
   {
      _starveTime = DEFAULT_STARVE_TIME;
   }
   
   
   /**
    * A method that can count the starve time down. When there is no fox around
    * the hound, then we should make the hound get hungry. By using subtract
    * one each time. 
    */
   public void getHungry()
   {
      _starveTime--;
   }
   
   
   /**
    * A boolean Method that determine if the hound died or not. If the starve
    * time has been counted down four times, the number of default starve time 
    * should be less than 0.
    * @return the boolean result.
    */
   public boolean isDie()
   {
      return _starveTime < 0;
   }
   
   
   /**
    * Get the color of the cells that have hounds.
    * @return the color to use for a cell occupied by a Hound
    */
   @Override
   public Color getDisplayColor()
   {
      return Color.red;
   } // getDisplayColor

   
   public String toString()
   {
      return "H";
   }

   // The default starve time for Hounds
   public static final int DEFAULT_STARVE_TIME = 3;
}