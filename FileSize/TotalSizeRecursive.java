package TotalSizeRecursive;

import java.io.File;

public class TotalSizeRecursive
{
   // Instance variables.
   private static long size = 0L;
   private static boolean allReadable = true;

   /**
    * The main method that calculates and prints the size of that file system
    * object.
    * @param args represents the file pathname.
    */
   public static void main(String[] args)
   {
      // Start time.
      long startTime = System.currentTimeMillis();
      
      // Handle the situation that the input length is not 1 because we want
      // only one input.
      if (args.length != 1)
      {
         System.out.println("Wrong number of input");
         System.exit(1);
      }
      
      // Use the java File class to create a File instance that matches that 
      // filename.
      File givenFile = new File(args[0]);
      
      // Use recursion to calculate the total size.
      preOrderTraversal(givenFile);
      
      System.out.println(size + " bytes");
      
      // If there are any unreadable files, notify the user at the end that 
      // this occurred
      if (!allReadable)
      {
         System.out.println("Some files/directories were unreadable");
      }
      
      // End time.
      long endTime = System.currentTimeMillis();
      long totalTime = endTime - startTime;
      System.out.println("Timing for this program: " + totalTime);
   }
   
   /**
    * Recursion method to calculate the currentFile's size.
    * @param currentFile represents current file that need to be calculated.
    */
   public static void preOrderTraversal(File currentFile)
   {
      // If it is unreadable or hidden, then do not calculate in the total.
      if (!currentFile.canRead() || currentFile.isHidden())
      {
         allReadable = false;
      }
      // If the current file is a file (not a directory, then increment the 
      // size by adding the length of the file.
      else if (currentFile.isFile())
      {
         size += currentFile.length();
      }
      // If the current file is a directory, calculate the child files' size.
      else
      {
         for (File childFile : currentFile.listFiles())
         {
            preOrderTraversal(childFile);
         }
      }
   }

}
