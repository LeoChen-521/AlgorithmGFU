/**
 * A class to represent a U.S. telephone number.
 *
 * @version 1.0
 */
public class USTelephoneNumber
{
   // Global Constants array to hold those String type toll free area codes.
   private static final String[] AREA_CODES = 
                                   {"800","866","877","880","881","882","888"};
   
   // A Global Constant to hold the length of a full US telephone number.
   private static final int      FULL_DIGIT_NUM_LENGTH = 10;
   
   // Instance Variables.
   private String _usTelephoneNumber;
   
   
   /**
    * This is the first constructor method that accepts and uses three numbers,
    * which are area code, exchange, and extension to create an US telephone
    * number.
    * 
    * @param areaCode
    *             represents 3-digits area code.
    * @param exchange
    *             represents 3-digits exchange.
    * @param extension
    *             represents 4-digits extension.
    */
   public USTelephoneNumber(int areaCode, int exchange, int extension)
   {
      StringBuilder sb = new StringBuilder();
      sb.append(areaCode);
      sb.append(exchange);
      sb.append(extension);
      _usTelephoneNumber = sb.toString();
   }
   
   
   /**
    * This is the second constructor method that accepts and uses two numbers, 
    * which are exchange, and extension to create an US telephone number.
    * 
    * @param exchange
    *             represents 3-digits exchange.
    * @param extension
    *             represents 4-digits extension.
    */
   public USTelephoneNumber(int exchange, int extension)
   {
      StringBuilder sb = new StringBuilder();
      sb.append(exchange);
      sb.append(extension);
      _usTelephoneNumber = sb.toString();
   }
   
   
   /**
    * This is the third constructor method that accepts and uses a string of 
    * letters and numbers to create an US telephone number.
    * 
    * @param lettersAndnumbers
    *             represents a US telephone number that has a mix of letters 
    *             and numbers.
    */
   public USTelephoneNumber(String lettersAndNumbers)
   {
      _usTelephoneNumber = lettersAndNumbers;
   }
   
   
   /**
    * A method that determines if the number is provided toll-free.
    * 
    * @return a boolean type result. If true, this US telephone number is 
    * toll-free; if false, this US telephone number is not toll-free.
    */
   public boolean isTollFree()
   {
      boolean res = false;
      for(String areaCode : AREA_CODES)
      {
         int phoneNumberLength = _usTelephoneNumber.length();
         if(_usTelephoneNumber.startsWith(areaCode) && phoneNumberLength == 
               FULL_DIGIT_NUM_LENGTH)
         {
            res = true;
         }
      }
      return res;
   }
}