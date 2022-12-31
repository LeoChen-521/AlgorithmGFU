import java.io.File;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 * A class that accepts a file pathname and then calculates and prints
 * the size of that file system object.
 */
public class TotalSize
{  
   /**
    * The main method that calculates and prints the size of that file system
    * object.
    * @param args represents the file pathname.
    */
   public static void main(String[] args)
   {
      // Start time.
      long startTime = System.currentTimeMillis();
      // Local variables.
      Deque<File> myFileTree = new ArrayDeque<File>();
      long size = 0L;
      boolean allReadable = true;
      
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
      
      // Store the given file in the deque.
      myFileTree.push(givenFile);
      
      // Traverse all the files whose root file is the given file to calculate
      // the total size. Because the deque can store all file objects in order.
      while (!myFileTree.isEmpty())
      {
         // Pop out the top element in the deque to calculate its size.
         File currentFile = myFileTree.pop();
         
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
         // If the current file is a directory, push the child files of the 
         // current file into the deque.
         else
         {
            for (File childFile : currentFile.listFiles())
            {
               myFileTree.push(childFile);
            }
         }
      }
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
}
