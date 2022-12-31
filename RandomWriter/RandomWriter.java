import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class RandomWriter
{ 
   /**
    * A method that produces a random seed, which is k length, from the source
    * text.
    * @param k represents the length of the seed.
    * @param sourceText represents the source text that is provided.
    * @return a k length random seed(k length random sequence of character(s).
    */
   public static String pickRandomSeed(int k, String sourceText)
   {
      // If the length is an invalid number.
      if (k < 0)
      {
         throw new IllegalArgumentException("Invalid input k");
      }
      
      Random randomGenerator = new Random();
      
      // Get a random first character of the seed by picking a random index
      // number of the source text.
      int seedStartingIndex = 
            randomGenerator.nextInt(sourceText.length() - k + 1);
      
      // Return the k length seed(sequence of characters).
      return sourceText.substring(seedStartingIndex, seedStartingIndex + k);
   }
   
   
   /**
    * A method that produce a new seed.
    * @param currentSeed represents the current seed.
    * @param sourceText represents the source text that is provided.
    * @param resultStringBuilder represents the final result StringBuilder.
    * @return A new seed that we need for the next process.
    */
   public static String getNewSeed(String currentSeed, String sourceText, 
                                             StringBuilder resultStringBuilder)
   {
      Random randomGenerator = new Random();
      ArrayList<String> additionalCharList = new ArrayList<>();
      StringBuilder nextSeedStringBuilder = new StringBuilder();
      int ListRandomPickIndex = 0;
      // A for loop that iterates through the source text to find all the same
      // sequence of characters as current seed.
      for (int i = 0; i < sourceText.length() - currentSeed.length() + 1; i++)
      {
         if(sourceText.substring(i).startsWith(currentSeed))
         {
            // If the same sequence of characters as current seed is found, and
            // it is not the last k length sequence
            if (i < sourceText.length() - currentSeed.length())
            {
               additionalCharList.
                        add(sourceText.substring(i + currentSeed.length(), 
                              i + currentSeed.length() + 1));      
            }
            // If it is the last sequence of characters, produces a new seed.
            else
            {
               // Pick a same length new seed randomly by calling the 
               // pickRandomSeed method. Then add it into the 
               // additionalCharList.
               additionalCharList.
                         add(pickRandomSeed(currentSeed.length(), sourceText));
            }
         }
      }
      // Pick a random number of index of the list.
      ListRandomPickIndex = randomGenerator.nextInt(additionalCharList.size());
      
      // Append the current seed without the first into the 
      // nextSeedStringBuilder.
      nextSeedStringBuilder.append(currentSeed.substring(1));
      
      // Get the content of the random index of the list, and append the
      // content into the nextSeedStringBuilder.
      nextSeedStringBuilder.append(additionalCharList.get(ListRandomPickIndex));
      
      // Get the content of the random index of the list, and append the
      // content into the resultStringBuilder.
      resultStringBuilder.append(additionalCharList.get(ListRandomPickIndex));
      
      // Return as a String.
      return nextSeedStringBuilder.toString();
   }
   
   
   /**
    * Process the input text, and produce a new random text.
    * @param args 
    *             represents input string from the command line.
    * @throws FileNotFoundException 
    */
   public static void main(String[] args) throws FileNotFoundException
   {
      Scanner myScanner = new Scanner(System.in);
      File infile;
      String fileName;
      String currentSeed;
      int k;
      int length;
      StringBuilder sourceTextStringBuilder = new StringBuilder();
      StringBuilder resultStringBuilder = new StringBuilder();
      
      // Getting the input from the user.
      System.out.println("Please provide value k: ");
      k = myScanner.nextInt();
      System.out.println("Please provide length: ");
      length = myScanner.nextInt();
      System.out.println("Please provide file name: ");
      fileName = myScanner.next();
      myScanner.close();
      infile = new File(fileName);
      myScanner = new Scanner(infile);
      while (myScanner.hasNextLine())
      {
         sourceTextStringBuilder.append(myScanner.nextLine());
      }
      myScanner.close();

      currentSeed = pickRandomSeed(k, 
            sourceTextStringBuilder.toString());
      
      resultStringBuilder.append(currentSeed);
      
      while (resultStringBuilder.toString().length() < length)
      {
         currentSeed = getNewSeed(currentSeed, 
                    sourceTextStringBuilder.toString(), 
                    resultStringBuilder);
      }
      
      System.out.println("Result text: " 
                           + resultStringBuilder.substring(0,length));
   }
}
