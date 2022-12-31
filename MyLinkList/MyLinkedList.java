import java.util.Iterator;
import java.util.NoSuchElementException;


/**
 * This class is a singly-list class.
 *
 * @param <T> 
 * 			represents elements' generic type.
 */
public class MyLinkedList<T>
			implements HansenCollection<T>, 
					Comparable<MyLinkedList<T>>, Iterable<T>
{	
	/**
	 * The 0-arg constructor that creates a new LinkedList. 
	 */
	public MyLinkedList()
	{
	   
	}
	
	
	/**
    * A method that adds a new element to the LinkedList.
    */
   @Override
   public void addElement(T newElement)
         throws CollectionFullException, 
            IllegalArgumentException 
   {  
      //If newElement is null:
      //Throw IllegalArgumentException
      if (newElement == null)
      {
         throw new IllegalArgumentException();
      }
      
      //If _count equals to 0:
      //Set _head to a new ListNode whose contents is 
      //newElement and next node is null
      //Set tail to head since there is only one node now.
      if (isEmpty())
      {
         _head = new ListNode<T>(newElement, null);
         _tail = _head;
      }
      else
      {
         //Set current tail's next node as a new ListNode 
         //whose contents is newElement and next is null.
         _tail.setNext(new ListNode<T>(newElement, null));
         //Set tail to the new node.
         _tail = _tail.getNext();
      }
      //Increase _count by 1
      _count++;
   }

   
   /**
    * A method that finds a existing element and remove it.
    */
   @Override
   public void removeElement(T elementToRemove)
         throws NoSuchElementException 
   {     
      //Set a previousNode to _head
      ListNode<T> previousNode = null;
      //Set a currentNode to _head's next node
      ListNode<T> currentNode = _head;
      
      //While currentNode is not null and findFirst 
      //equals to false
      while (currentNode != null && !currentNode.getContents().
            equals(elementToRemove))
      {
         //Set previousNode to previousNode's next node
         previousNode = currentNode;
         //Set currentNode to currentNode's next node
         currentNode = currentNode.getNext();
      }
      //If findFirst equals to false, 
      //throw new NoSuchElementException  
      if (currentNode == null)
      {
         throw new NoSuchElementException();
      }
      else if (currentNode.equals(_head))
      {
         if (_count == 1)
         {
            makeEmpty();
         }
         else
         {
            _head = currentNode.getNext();
            _count--;
         }
      }
      //else currentNode's contents equals to 
      //elementToRemove:
      //Set previousNode's next node to 
      //currentNode's next node
      //Set findFirst to true
      else
      {
         previousNode.
         setNext(currentNode.getNext());
         
         //Set tail to the previous node if the 
         //node deleted is the last node.
         if (currentNode.getNext() == null)
         {
            _tail = previousNode;
         }
         //Decrease _count by 1
         _count--;
      }
   }

   
   /**
    * A method that finds an existing element.
    */
   @Override
   public T findElement(T elementSought) 
   {
      //Set a currentNode to _head
      ListNode<T> currentNode = _head;
      //While currentNode is not null and elementFound is null:
      while (currentNode != null && !currentNode.getContents().
                                    equals(elementSought))
      {
         //Set currentNode to currentNode's next node
         currentNode = currentNode.getNext();
      }
      //Return elementFound 
      return currentNode != null ? currentNode.getContents() : null;
   }

   
   /**
    * A method that checks whether the LinkedList has an certain 
    * element or not.
    */
   @Override
   public boolean containsElement(T elementSought) 
   {
      //Set a currentNode to _head
      ListNode<T> currentNode = _head;
      //While currentNode is not null and currentNode's contents is not 
      //elementSought yet  
      while (currentNode != null && !currentNode.getContents().
                  equals(elementSought))
      {
         //Set currentNode to currentNode's next node
         currentNode = currentNode.getNext();
      }
      //Return whether the currentNode is null or not.
      return currentNode != null;
   }

   
   /**
    * A method that checks whether the LinkedList is full or not.
    */
   @Override
   public boolean isFull() 
   {
      //Return false;
      return false;
   }

   
   /**
    * A method that checks whether the LinkedList is empty or not.
    */
   @Override
   public boolean isEmpty() 
   {
      //Return _count == 0;
      return _count == 0;
   }

   
   /**
    * A method that makes the LinkedList empty.
    */
   @Override
   public void makeEmpty() 
   {
      //Set _head to null
      _head = null;
      _tail = null;
      _count = 0;
   }

   
   /**
    * A method that gets the number of nodes in a LinkedList.
    */
   @Override
   public int size() 
   {
      //return _count
      return _count;
   }
	
	/**
	 * A method that adds a new element to the head of the list.
	 * @param newElement 
	 * 			represents the new element need adding.
	 * @throws IllegalArgumentException
	 * 			if the new element is null.
	 */
	public void prependElement(T newElement)
		throws IllegalArgumentException 
	{
		//If newElement equals to null
		//Throw IllegalArgumentException
		if (newElement == null)
		{
			throw new IllegalArgumentException();
		}
		
		//Set _head to a new ListNode whose contents is 
		//newElement and next node is _head
		_head = new ListNode<T>(newElement, _head);
		
		//Set tail to head if there is only one node in the list.
		if (_head.getNext() ==
		      null)
		{
			_tail = _head;
		}
		//Increase _count by 1
		_count++;	
	}
	
	
	/**
	 * A method that adds a new object right after an existing object.
	 * @param existingObject
	 * 			represents the object that want to be 
	 * 			added new object behind it.
	 * @param newObject
	 * 			represents the object that need to adding 
	 * 			behind the existing object.
	 * @throws NoSuchElementException
	 * 			if cannot find the object(existingObject) that
	 * 			 want to be added new object behind it.
	 * @throws IllegalArgumentException
	 * 			if the new object is null.
	 */
	public void insertAfter(T existingObject, T newObject)
			throws NoSuchElementException,
				IllegalArgumentException
	{
		//If newObject is null:
		//Throw IllegalArgumentException
		if (newObject == null)
		{
			throw new IllegalArgumentException();
		}
		
		//Set a currentNode to _head
		ListNode<T> currentNode = _head;
		
		//While currentNode is not null and currentNode's 
		//contents is not identical to existingObject:
		while (currentNode != null 
				&& currentNode.getContents() != existingObject)
		{
			//Set currentNode to currentNode's next node.
			currentNode = currentNode.getNext();
		}
		
		//If currentNode equals to null:
		//Throw NoSuchElementException
		if (currentNode == null)
		{
			throw new NoSuchElementException();
		}
		//Set currentNode's next node to a new ListNode whose 
		//contents is newObject and next node is currentNode's
		//next node 
		ListNode<T> newNext = new ListNode<T>(newObject, currentNode.getNext());
		
		//Set tail to the new ListNode if the new ListNode is the last
      //node.
      if (_tail.equals(currentNode))
      {
         _tail = newNext;
      }
		
		currentNode.setNext(newNext);
		
		//Increase _count by 1
		_count++;
	}
	
	
	/**
	 * A method that compares two LinkedList to determine which one is 
	 * greater.
	 */
	@SuppressWarnings({"unchecked"})
	public int compareTo(MyLinkedList<T> o)
	{
		//Set a compareResult to 0
		int compareResult = 0;
		//Set a minSize to the minimum value of _count and o's _count
		int minSize = Math.min(_count, o._count);
		
		//	Set a currentNode1 to _head
		//	Set a currentNode2 to o's _head
		ListNode<T> currentNode1 = _head;
		ListNode<T> currentNode2 = o._head;
		
		//Set an integer index to 0
		int index = 0;
		
		//While index is smaller than minSize and 
		//alwaysEqual equals to true: 
		while (index < minSize && 
		      ((Comparable) currentNode1.getContents()).
            compareTo(currentNode2.
            getContents()) == 0)
		{
			//Set currentNode1 to currentNode1's next node
			currentNode1 = currentNode1.getNext();
			//Set currentNode2 to currentNode2's next node
			currentNode2 = currentNode2.getNext();
			//Increase index by 1
			index++;
		}
		
		//If alwaysEqual equals to true:
		//Set compareResult to _count minus 
		//o's _count
		if (index == minSize)
		{
			compareResult = _count - o._count;
		}
		else
		{
		   //Set compareResult to the value of
         //currentNode1's contents compared 
         //with currentNode2's contents
         compareResult = 
            ((Comparable) currentNode1.
            getContents()).
            compareTo(currentNode2.
            getContents());
		}
		//Return compareResult
		return compareResult;
	}

	
	/**
	 * A method allows this class to implement the Iterable interface and
	 * returns an iterator over the list.
	 */
	public Iterator <T> iterator()
	{
		Iterator<T> myIterator = new Iterator<T>() 
		{
			ListNode<T> currentNode = _head;

			/**
			 * A method that checks whether there is a next node.
			 */
            @Override
            public boolean hasNext() 
            {
            	//Return currentNode is not equal to null.
                return currentNode != null;
            }

            
            /**
             * A method that returns the next node of a currentNode.
             */
            @Override
            public T next() 
            {
            	//If currentNode's next node == null 
            	//Throw NoSuchElementException
            	//Return currentNode's next node
            	
            	if (currentNode == null)
            	{
            		throw new NoSuchElementException();
            	}
            	
            	T next = currentNode.getContents();
            	
            	currentNode = currentNode.getNext();
               return next;
            }

            
            /**
             * A method that tells the remove method cannot be invoked.
             */
            @Override
            public void remove() 
            {
                //Throw UnsupportedOperationException
            	throw new UnsupportedOperationException();
            }
        };
        //Return myIterator
		return myIterator;
	}
	

	
	
	/**
    * An inner-class for use by a Linked List to hold the contents of the list.
    * Note that this class and its methods are not public nor private, their
    * visibility is within the "package" and that includes any other classes
    * defined in the same file. This class definition can appear INSIDE the
    * definition of a Linked List class. 
    *
    * Note: use a type variable to match the type variable used in the 
    * enclosing class rather than CHANGE_ME 
    *
    * @author David M. Hansen
    * @version 2.1
    * @param <CHANGE_ME> type of object contained by this node
    */
   class ListNode <T>
   {
      // Constructors

      /**
       * Create a new ListNode holding the given object and pointing to the 
       * given node as the next node in the list
       *
       * @param objectToHold the object to store in this node
       * @param nextNode the node this node should point to. Can be null
       */
      ListNode(T objectToHold, ListNode<T> nextNode) 
      { 
         _contents = objectToHold;
         _next = nextNode;
      }


      /**
       * Create a new ListNode holding the given object. No next node
       *
       * @param objectToHold the object to store in this node
       */
      ListNode(T objectToHold) 
      { 
         // Use the more general constructor passing null as the next
         // node
         this(objectToHold, null);
      }


      // Accessors

      /**
       * Return the object held by this node
       *
       * @return Object held by this node
       */
      T getContents() 
      { 
         return _contents;
      }

      /**
       * Return the next node
       *
       * @return the next node
       */
      ListNode<T> getNext() 
      { 
         return _next;
      }


      // Mutators

      /**
       * Set the node this node is linked to 
       *
       * @param nextNode the node to point to as our next node. Can be null.
       */
      void setNext(ListNode<T> nextNode)
      { 
         _next = nextNode;
      }


      // Private attributes 
      private T _contents; // The object held by this node
      private ListNode<T> _next; // A reference to the next node

   } // ListNode
   
	
	//Instance variables.
	private ListNode <T> _head;
	private ListNode <T> _tail;
	private int _count;
}
