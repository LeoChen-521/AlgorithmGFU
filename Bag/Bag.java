import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * A class with an unordered collection that can hold multiple instances of the
 * same Object.
 * @param <T> Element to add into the "bag."
 */
public class Bag<T> {

	/**
     * Construct an empty bag
     */
    public Bag() 
    {
    	
    }


    /**
     * Return true if the element sought is present 
     *
     * @param element to search for
     * @return true if the element is in the Bag 
     */
    public boolean contains(T element) 
    {
    	return multiset.containsKey(element);
    }


    /**
     * Return a count of the number of instances of the element
     *
     * @param element to search for
     * @return number of instances of the element in the Bag 
     */
    public int countOf(T element) 
    {
    	return multiset.containsKey(element)? multiset.get(element).get() : 0;
    }


    /**
     * Add the element to the Bag
     *
     * @param element to add to the Bag
     */
    public void add(T element) 
    {
      // Add the count of element by 1 if element already exists.
    	if(multiset.containsKey(element)) 
    	{
    		multiset.put(element, new AtomicInteger(multiset.get(element).
    		                                 incrementAndGet()));
    	}
    	
    	// Add element with count of 1, if element does not exist.
    	else
    	{
    		multiset.put(element, new AtomicInteger(1));
    		_size++;
    	}  	
    }


    /**
     * Remove at most one instance from the Bag 
     *
     * @param element to remove from the bag 
     * @return true if an element was removed, false otherwise
     */
    public boolean remove(T element) 
    {
    	boolean removed = false;
    	
    	// Handle the situation that element exists.
    	if(multiset.containsKey(element)) 
    	{
    	   // Remove element if there is only 1 instance of the elements.
    		if(multiset.get(element).get() == 1)
    		{
    			multiset.remove(element);
    			removed = true;
        		_size--;
    		}
    		// Decrease the count of instance of the element by one if there 
    		//are more that one instance existing.
    		else
    		{
    		   multiset.
    		      put(element, new AtomicInteger(multiset.get(element).
    		                             decrementAndGet()));  
    		}
    		
    				
    	}
    	return removed;
    }


    /**
     * Return true if the Bag is empty
     *
     * @return true if the Bag is empty
     */
    public boolean isEmpty() 
    {
    	return _size == 0;
    }


    /**
     * Return a count of the number of elements in the Bag
     *
     * @return number of elements in the Bag
     */
    public int size() 
    {
    	return _size;
    }
    
    Map<T, AtomicInteger> multiset = new HashMap<>();
    int _size = 0;
}