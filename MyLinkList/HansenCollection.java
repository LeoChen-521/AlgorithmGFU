import java.util.NoSuchElementException;

/**
 * An 100% abstract class has not implemented the method yet.
 */
public interface HansenCollection < T >
{
   /**
    * Throw a meaningful exception if the collection is full. Our collection
    * will not allow null objects so you should also throw a pre-defined Java 
    * exception if the caller attempts to add a null object to the collection.
    * Be sure to note this in the Javadoc comments.
    * @param newElement
    *                represents the new element that will be added to the 
    *                array.
    * @throws CollectionFullException
    *                if the array is full, throw CollectionFullException.
    * @throws IllegalArgumentException
    *                if the input is empty then throw IllegalArgumentException.
    */
   public void addElement(T newElement) 
      throws CollectionFullException, IllegalArgumentException;
   
   /**
    * Remove the first matching element found (based on equality) or throw an 
    * exception if a matching object is not present in the collection.
    * @param elementToRemove
    *                represents the element that need to removing.
    * @throws NoSuchElementException
    *                if the array does not have that element
    */
   public void removeElement(T elementToRemove) throws NoSuchElementException;
   
   /**
    * Find the element that we want in the array(Collection).
    * @param elementSought
    *                represents the element that we are looking for.
    * @return the element that we found.
    */
   public T findElement(T elementSought);
   
   /**
    * A method that check if the array(Collection) has the element that the 
    * input says. 
    * @param elementSought
    *                represents the element that the input wants to check.
    * @return false, if the array(Collection) does not have that element,
    *          true, if the array(Collection) has that element.
    */
   public boolean containsElement(T elementSought);
   
   /**
    * A method to determines whether the array(Collection) is full or not
    * @return false, if the array(Collection) is not full,
    *          true, if the array(Collection) is full.
    */
   public boolean isFull();
   
   /**
    * A method to determines whether the array(Collection) is empty or not
    * @return false, if the array(Collection) is not empty,
    *          true, if the array(Collection) is empty.
    */
   public boolean isEmpty();
   
   /**
    * A method to make the array(Collection) empty.
    */
   public void makeEmpty();
   
   /**
    * Get how many indexes have element.
    * @return
    */
   public int size();
}