import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.Set;

/**
 * A WebSpider program accepts a starting URL as a command-line parameter, and 
 * computes the number of the web pages that are reachable from the given 
 * starting page and share the same host and the number pages linked to that 
 * are "external" pages.
 */
public class WebSpider 
{
     /**
      * A main method that computes the number of the web pages that are 
      * reachable from the given starting page and share the same host and the 
      * number pages linked to that are "external" pages.
      * 
      * @param args[0] URL to open
      *
      * @throws MalformedURLException on a badly formed URL
      * @throws IOException on network error
      */
      public static void main(String[] args) 
      {
         // Local variables.
         HTMLLinks webPage;
         int webPageCount = 0;
         int externalPageCount = 0;
         
         // This is a queue to store URLs that need to open.
         Deque<String> urlsToOpen = new ArrayDeque<>();
         
         // Store visited URLs and can be used to check whether an URL is 
         // visited or not.
         Set<String> visitedURLs = new HashSet<>();
         
         // Start computing. Throw exceptions if there is IO issue or there is
         // no command line argument.
         try 
         {
            // Add the starting command line argument into the queue as our 
            // first URL that need to open.
            urlsToOpen.add(args[0]);
            
            // Add the starting command line into the Set as our first visited
            // URL.
            visitedURLs.add(args[0]);
            
            // If the queue is not empty.(If there is a command line).
            while(!urlsToOpen.isEmpty()) 
            {
               // create a web page as a collection of the URLs that were
               // removed from the queue.
               webPage = new HTMLLinks(urlsToOpen.remove());
               
               // Walk through the web page(collection)'s each URL.
               for (URL link : webPage) 
               {
                  // If the link is not visited, count it as the web page that
                  // is reachable from the given starting page and share the 
                  // same host or the external page, then mark it as visited.
                  if (!visitedURLs.contains(link.toString())) 
                  {
                     visitedURLs.add(link.toString());
                     
                     // If the links are reachable from the given starting page
                     // and share the same host, add them into the queue.
                     // Because they do start with the same characters as the 
                     // host.
                     if(link.toString().startsWith(args[0])) 
                     {
                        urlsToOpen.add(link.toString());
                        
                        // Increment web page count.
                        webPageCount ++;
                     }
                     
                     // Handle the external web pages(hosted elsewhere). 
                     // Because they do not start with the same characters as 
                     // the host.
                     else
                     {
                        // Increment the external page count.
                        externalPageCount++;
                     }
                  }
               }              
            }           
         } 
         
         // Handle the errors.Throw exceptions if there is IO issue or there is
         // no command line argument.
         catch (IOException e) 
         {
            System.err.println(e.getMessage());
         } 
         catch (ArrayIndexOutOfBoundsException e) 
         {
            System.err.println("No URL provided");
         }
         
         // Print out the results.
         System.out.println(webPageCount + " web pages");
         System.out.println(externalPageCount + " external pages referenced");
         
      }
}