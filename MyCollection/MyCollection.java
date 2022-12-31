import java.util.NoSuchElementException;

/**
 * A templated Java class named MyCollection that provides basic Collection
 * type behavior.
 *
 */
public class MyCollection< T > implements HansenCollection< T >
{  
   //Instance variables.
   private T[] myCollectionArray;
   private int _currentIndex;
   private final int _maxSize;
   
   
   /**
    * A parameterized constructor that accepts an int maxSize to size my array.
    * This should be the only constructor that a user of my class can call.
    * The collection will not shrink or grow from its initial size.
    * 
    * @param maxSize
    *      represents the number that the user uses to give the array a length.
    */
   @SuppressWarnings("unchecked")
   public MyCollection(int maxSize)
   {
      _maxSize = maxSize;
      myCollectionArray = (T[]) new Object[maxSize];
   }
   
   
   /**
    * Throw a meaningful exception if the collection is full. Our collection
    * will not allow null objects so you should also throw a pre-defined Java 
    * exception if the caller attempts to add a null object to the collection.
    */
   @Override
   public void addElement(T newElement)
         throws CollectionFullException, IllegalArgumentException
   {
      //If the input is null, then throw CollectionFullException.
      if (newElement == null)
      {
         throw new IllegalArgumentException();
      }
      //If the array that we have is full already, throw 
      //IllegalArgumentException
      else if (isFull())
      {
         throw new CollectionFullException();
      }
      
      //Add the new element to the current index.
      //Next time, add the new element to the next index(current index again).
      myCollectionArray[_currentIndex++] = (T) newElement;
   }
   
   
   /**
    * remove the first matching element found (based on equality) or throw an 
    * exception if a matching object is not present in the collection.
    */
   @Override
   public void removeElement(T elementToRemove)
         throws NoSuchElementException
   {
      //Local variables.
      int removeIndex = -1;
      int index = 0;
      int i = 0;
      
      //Throw exception if the array does not have the element that need
      //removing.
      if (!containsElement(elementToRemove))
      {
         throw new NoSuchElementException();
      }
      
      //Search each index, if the current index does not equal to the element
      //that need removing, then look at the next index. If equals, get the 
      //number of the index that need to removing.
      while (removeIndex == -1 && index != _maxSize)
      {
         if (!elementToRemove.equals(myCollectionArray[index]))
         {
            index++;
         }
         else
         {
            removeIndex = index;
         }
      }
      
      //Replace the remove index by the next index. Empty the last index by
      //making it be null.
      for (i = removeIndex; i < _maxSize - 1; i++)
      {
         myCollectionArray[i] = myCollectionArray[i + 1];
      }
      myCollectionArray[i] = null;
      _currentIndex--;
   }
   
   
   /**
    * return a single matching element found (based on equality) or null if a
    * matching object is not present in the collection.
    */
   @Override
   public T findElement(T elementSought)
   {
      //Local variables.
      int index = 0;
      T elementFound = null;
      
      //Check if the array has that elementSought, Search each index, if the 
      //current index does not equal to the elementSought then look at the next
      //index. If equals, make the element in that index be the element that
      //we found.
      if (containsElement(elementSought))
      {
         while (index != _maxSize && 
               !elementSought.equals(myCollectionArray[index]))
         {
            index++;
         }
         
         if (index != _maxSize)
         {
            elementFound = myCollectionArray[index];
         }
      }
      return elementFound;
   }

   
   /**
    * A method that determines whether the array has the certain element or
    * not.
    */
   @Override
   public boolean containsElement(T elementSought)
   {
      boolean hasElement = false;
      
      if (elementSought != null)
      {
         //Use the public boolean equals(Object o) method (defined for Object) 
         //to test for object equality
         for (T t : myCollectionArray)
         {
            if (elementSought.equals(t))
            {
               hasElement = true;
            }
         }
      }
      return hasElement;
   }

   
   /**
    * A method to determines whether the array is full or not.
    */
   @Override
   public boolean isFull()
   {
      return _currentIndex == _maxSize ? true : false;
   }

   
   /**
    * A method to determines whether the array is empty or not.
    */
   @Override
   public boolean isEmpty()
   {
      return _currentIndex == 0 ? true : false;
   }

   
   /**
    * A method to make the array empty by constructing a same length new 
    * array and resetting the cursor back to the number 0 index.
    */
   @SuppressWarnings("unchecked")
   @Override
   public void makeEmpty()
   {
      myCollectionArray = (T[]) new Object[_maxSize];
      _currentIndex = 0;
   }
   
   
   /**
    * Get how many indexes have element.
    */
   @Override
   public int size()
   {
      return _currentIndex;
   } 
}