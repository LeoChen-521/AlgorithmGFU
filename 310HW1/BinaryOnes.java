/**
 * A Class to count the number of 1's in a binary representation of a number.
 * 
 * @version 1.0
 */
public class BinaryOnes
{
   /**
    * A recursive method that returns the number of 1's in the binary 
    * representation of a number.
    * 
    * @param number 
    *             represents a decimal number that ready to be counted.
    * @return the number of 1's in the binary representation of that decimal
    * number.
    */
   public static int numberOfOnes(long number)
   {   
      // Local variable.
      int res = 0;
      
      // determine whether the number is 0 or not.
      if(number == 0)
      {
         res = 0;
      }
      
      // determine whether the non-zero number is even or not.
      else if(number%2 == 0)
      {
         res = numberOfOnes(number/2);
      }
      
      // now the non-zero number should be odd.
      else
      {
         res = numberOfOnes(number/2) + 1;
      }
      return res;
   }
}
