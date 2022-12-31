// import statements
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.*;
import static org.junit.Assert.*;

/**
 * A test suite designed to test a singly-linked list MyLinkedList using JUnit. 
 * Created to fulfill requirements for CSIS310 Data Structures with Dr. Hansen.
 */
public class MyLinkedListTest
{
    // constant declarations
    private final String[] ELEMENTS = { "a", "b", "c", "d", "e" };
    private final String AN_ELEMENT = "this is an another element";
    private final String NOT_IN_LIST = "this element is not in the list";
    
    // variable declarations
    private MyLinkedList<String> _testList;
    
    
    /**
     * Set-up method called by JUnit prior to each test. Initializes the 
     *      linked list to be tested.
     */
    @Before
    public void setUp()
    {
        _testList = new MyLinkedList<>();
    }
    
    
    /**
     * Test filling the list. Tests the addElement(), size(), isEmpty(), 
     *      and isFull() methods.
     */
    @Test
    public void testFillList() throws Exception
    {
        // test that the list is empty after setup
        testCardinality(0);
        
        // iterate NUMBER_OF_ELEMENTS times, adding an element of STRINGS to
        //      the list each time
        for (int i = 0; i < ELEMENTS.length; i++)
        {
            // attempt to add the next element
            _testList.addElement(ELEMENTS[i]);
            
            // test the cardinality
            testCardinality(i + 1);
        }
        
        // make sure that the elements are in the correct order
        testOrder(ELEMENTS);
    }
    
    
    /**
     * Attempts to append a null element to the list. Fails if appending a null
     *      element is successful or if the size is incorrect afterwards.
     */
    @Test
    public void testAddNull() throws Exception
    {
        // fill the list (so we can see if the size is wrong and by how much)
        testFillList();
        
        // try to append null
        try
        {
            // attempt to append a null element
            _testList.addElement(null);
            
            // if we made it this far, we have failed somehow
           fail("MyLinkedList should not accept null elements.");
        }
        catch (IllegalArgumentException e)
        {
            // success
        }
        
        // test the cardinality
        testCardinality(ELEMENTS.length);
    }
    
    
    /**
     * Attempt to prepend a null element to the list. Fails if prepending a 
     *      null element is successful or if the size is incorrect afterwards.
     */
    @Test
    public void testPrependNull() throws Exception
    {
        // fill the list (so we can see if the size is wrong and by how much)
        testFillList();
        
        // try to prepend null
        try
        {
            // attempt to prepend a null element
            _testList.prependElement(null);
            
            // if we made it this far, we have failed
            fail("MyLinkedList should not accept null elements and instead"
                + " should throw an exception.");
        }
        catch (IllegalArgumentException e)
        {
            // success
        }
        
        // check the cardinality to make sure nothing changed
        testCardinality(ELEMENTS.length);
        
        // test the integrity
        testIntegrity();
    }
    
    
    /**
     * Tests using the insertAfter() method to insert a null element. Fails if
     *      insertion is successful or if the size is incorrect afterwards.
     */
    @Test
    public void testInsertNullAfter() throws Exception
    {
        // fill the list (so we can see if the size is wrong and by how much)
        testFillList();
        
        // attempt to insert a null element after the first element
        try
        {
            _testList.insertAfter(ELEMENTS[0], null);
            
            // if we made it this far, we have failed
            fail("MyLinkedList should not accept null elements and instead "
                + "should throw an exception.");
        }
        catch (IllegalArgumentException e)
        {
            // success
        }
        
        // test cardinality
        testCardinality(ELEMENTS.length);
        
        // attempt to insert a null element after a non-existent element
        try
        {
            _testList.insertAfter(NOT_IN_LIST, null);
            
            // if we've made it this far, we have failed
            fail("MyLinkedList should not accept null elements.");
        }
        catch (IllegalArgumentException e)
        {
            // this is ideal, we have passed
        }
        catch (NoSuchElementException e)
        {
            // it threw this exeption first or did not detect that the new
            //      element was null, so this is a failure
            fail("IllegalArgumentException should be thrown if adding null "
                + "elements is attempted.");
        }
        
        // test the cardinality
        testCardinality(ELEMENTS.length);
    }
    
    
    /**
     * Test that we can make the list empty.
     */
    @Test
    public void testMakeEmpty() throws Exception
    {
        // the list is empty at this point
        // test that we can make an empty list empty
        _testList.makeEmpty();
        testCardinality(0);
        
        // fill the list
        testFillList();
        
        // test making the list empty
        _testList.makeEmpty();
        testCardinality(0);
    }
    
    
    /**
     * Tests the insertAfter() method.
     */
    @Test
    public void testInsertAfter() throws Exception
    {
        // fill the list
        testFillList();
        
        // attempt to insert an element after a non-existent element
        //      (should throw a NoSuchElementException)
        try
        {
            _testList.insertAfter(NOT_IN_LIST, AN_ELEMENT);
            
            // if we make it this far, we have failed
            fail("MyLinkedList should throw a NoSuchElementException if "
                + "insertAfter() passes an existingElement that's not in the "
                + "list.");
        }
        catch (NoSuchElementException e)
        {
            // success
        }
        
        // attempt to insert an element after a null element
        //      (should throw an IllegalArgumentException)
        try
        {
            _testList.insertAfter(null, AN_ELEMENT);
            
            // if we make it this far, we have failed
            fail("MyLinkedList should throw a NoSuchElementException if "
                + "insertAfter() attempts to insert after a null element.");
        }
        catch (NoSuchElementException e)
        {
            // success
        }
        
        // we are going to attempt to insert after the first element
        // declare our expected order to be: 
        //      ELEMENTS[0], AN_ELEMENT, ELEMENTS[1:]
        ArrayList<String> expectedOrder = new ArrayList<>();
        expectedOrder.add(ELEMENTS[0]);
        expectedOrder.add(AN_ELEMENT);
        expectedOrder.addAll(Arrays.asList(
            Arrays.copyOfRange(ELEMENTS, 1, ELEMENTS.length))); 

        // attempt to add AN_ELEMENT after the first element        
        _testList.insertAfter(ELEMENTS[0], AN_ELEMENT);
        
        // check the cardinality
        testCardinality(ELEMENTS.length + 1);
        
        // check the order
        testOrder(expectedOrder.toArray(ELEMENTS));
        
        // reset the list
        testMakeEmpty();
        testFillList();
        
        
        // lastly, test insertion after the last element
        // declare our expected order: ELEMENTS[], AN_ELEMENT
        expectedOrder.clear();
        expectedOrder.addAll(Arrays.asList(ELEMENTS));
        expectedOrder.add(AN_ELEMENT);
        
        // attempt to insert an element after the last element
        _testList.insertAfter(ELEMENTS[ELEMENTS.length - 1], AN_ELEMENT);
        
        // check the cardinality
        testCardinality(ELEMENTS.length + 1);
        
        // test the order
        testOrder(expectedOrder.toArray(ELEMENTS));
    }
    
    
    /**
     * Tests the prependElement() method.
     */
    @Test
    public void testPrepend() throws Exception
    {
        // fill the list by prepending elements to it
        // prepend elements in REVERSE order to make checking order easy
        for (int i = ELEMENTS.length - 1; i >= 0; i--)
        {
            // prepend the element
            _testList.prependElement(ELEMENTS[i]);
            
            // test the cardinality (NUMBER_OF_ELEMENTS - i since we are
            //      looping in reverse order)
            testCardinality(ELEMENTS.length - i);
        }
        
        // check that the elements are in the same order as ELEMENTS
        // (since they were prepended in reverse order)
        testOrder(ELEMENTS);
        
        // test the integrity of the list - can we still add elements?
        testIntegrity();
    }
    
    
    /**
     * Test using the removeElement() method to remove legal elements from the
     *      list.
     */
    @Test
    public void testRemoveElement() throws Exception
    {
        // fill the list
        testFillList();
        
        // attempt to remove the last element
        _testList.removeElement(ELEMENTS[ELEMENTS.length - 1]);
        
        // check cardinality and order
        // our order should be ELEMENTS[:length - 1]
        testCardinality(ELEMENTS.length - 1);
        testOrder(Arrays.copyOfRange(ELEMENTS, 0, ELEMENTS.length - 1));
        
        // reset the list
        testMakeEmpty();
        testFillList();
        
        // attempt to remove the first element
        _testList.removeElement(ELEMENTS[0]);
        
        // check cardinality and order
        // the order should be ELEMENTS[1:]
        testCardinality(ELEMENTS.length - 1);
        testOrder(Arrays.copyOfRange(ELEMENTS, 1, ELEMENTS.length));
    }
    
    
    /**
     * Tests attempting to remove a null element from the list. Is successful
     *      if the class throws a NoSuchElementException.
     */
    @Test
    public void testRemoveNull() throws Exception
    {
        // fill the list
        testFillList();
        
        // attempt to remove a null element
        //      (should throw a NoSuchElementException)
        try
        {
            _testList.removeElement(null);
            
            // if we make it this far, it is a failure
            fail("Attempting to remove a null element should throw a "
                + "NoSuchElementException.");
        }
        catch (NoSuchElementException e)
        {
            // success!
        }
        
        // check the cardinality and order
        testCardinality(ELEMENTS.length);
        testOrder(ELEMENTS);
    }
    
    
    /**
     * Tests removing an element from an empty list. Is successful if the class
     *      throws a NoSuchElementException.
     */
    @Test
    public void testRemoveFromEmptyList() throws Exception
    {
        // attempt to remove from an empty list
        //      (should throw a NoSuchElementException)
        try
        {
            _testList.removeElement(NOT_IN_LIST);
            
            // if we've made it this far, an exception was not thrown and
            //      we have failed
            fail("Attempting to remove an element not in the list should "
                + "throw a NoSuchElementException.");
        }
        catch (NoSuchElementException e)
        {
            // success!
        }
        
        // test the cardinality
        testCardinality(0);
    }
    
    
    /**
     * Test using removeElement() to remove a nonexistent element from the list. 
     *      If the class throws a NoSuchElementException, it is successful.
     */
    @Test
    public void testRemoveNonexistent() throws Exception
    {
        // fill the list
        testFillList();
        
        // attempt to remove a non-existent element
        //      (should throw a NoSuchElementException
        try
        {
            _testList.removeElement(NOT_IN_LIST);
            
            // if we make it this far, it is a failure! we should have thrown
            //      an exception
            fail("Attempting to remove a non-existent element should throw a "
                + "NoSuchElementException.");
        }
        catch (NoSuchElementException e)
        {
            // success!
        }
        
        // check the cardinality and order
        testCardinality(ELEMENTS.length);
        testOrder(ELEMENTS);
    }
    
    
    /**
     * Tests the findElement() method.
     */
    @Test
    public void testFind() throws Exception
    {
        // search for an element in the empty list (should return null)
        assertEquals(null, _testList.findElement(ELEMENTS[0]));
        
        // fill the list
        testFillList();
        
        // test if it contains null (should return null)
        assertEquals(null, _testList.findElement(null));
        
        // test if it contains a non-existent element (should return null)
        assertEquals(null, _testList.findElement(NOT_IN_LIST));
        
        // test if it contains a real element
        assertEquals(ELEMENTS[0], _testList.findElement(ELEMENTS[0]));
    }
    
    
    /**
     * Tests the containsElement() method.
     */
    @Test
    public void testContainsElement() throws Exception
    {
        // fill the list
        testFillList();
        
        // test if it contains null (should be false always)
        assertFalse(_testList.containsElement(null));
        
        // test if it contains an element not in the list
        assertFalse(_testList.containsElement(NOT_IN_LIST));
        
        // test if it contains an element that is a part of the list
        assertTrue(_testList.containsElement(ELEMENTS[0]));
    }
    
    
    /**
     * Tests that the compareTo() method follows the specified rules for
     *      comparing MyLinkedList objects.
     */
    @Test
    public void testCompareTo() throws Exception
    {
        // declare and fill another list to be used for compareTo tests
        MyLinkedList<String> comparableList = new MyLinkedList<>();
        for (int i = 0; i < ELEMENTS.length; i++)
        {
            comparableList.addElement(ELEMENTS[i]);
        }
        
        // test equality
        // first, fill the list
        testFillList();
        
        // test if _testList is equal to our comparableList (expected: 0)
        assertEquals(0, _testList.compareTo(comparableList));
        
        // test greater than and less than by length of the list, while having 
        //      identical elements until the length discrepancy
        // first, append an extra element to _testList
        _testList.addElement(AN_ELEMENT);
        
        // test if _testList is greater than our comparableList (expected: > 0)
        assertTrue(_testList.compareTo(comparableList) > 0);
        
        // test the reverse of the previous test (expected: < 0)
        assertTrue(comparableList.compareTo(_testList) < 0);
        
        // test greater than and less than by element comparison (as in, the 
        //      elements of the lists are different)
        // first, reset the list
        _testList.makeEmpty();
        testFillList();
        
        // now reset comparableList and give it a "less than" element
        comparableList.makeEmpty();
        comparableList.addElement("");
        
        // test if _testList is greater than comparableList (expected: > 0)
        assertTrue(_testList.compareTo(comparableList) > 0);
        
        // test the reverse of the previous test (expected: < 0)
        assertTrue(comparableList.compareTo(_testList) < 0);
    }
    
    
    /**
     * Tests calling the compareTo method on two MyLinkedLists containing
     *      elements that are not comparable. Successful if the class throws
     *      a ClassCastException.
     */
    @Test
    public void testCompareToNonComparable() throws Exception
    {
        // declare two lists containing non-comparable things
        // for kicks and giggles, we will compare two identical lists
        //      containing iterators
        MyLinkedList<Iterator> nonComparable1 = new MyLinkedList<>();
        MyLinkedList<Iterator> nonComparable2 = new MyLinkedList<>();
        nonComparable1.addElement(_testList.iterator());
        nonComparable2.addElement(_testList.iterator());
        
        // attempt to test if they are equal
        //      (should throw a ClassCastException)
        try
        {
            nonComparable1.compareTo(nonComparable2);
            
            // if we get it this far, we have failed!
            fail("Comparing two MyLinkedLists containing elements that are "
                + "not Comparable should throw a ClassCastException");
        }
        catch (ClassCastException e)
        {
            // success!
        }
    }
    
    
    /**
     * Test that the list iterates correctly.
     */
    @Test
    public void testIterable() throws Exception
    {
        // fill the list
        testFillList();
        
        // iterate over the list, checking that each element is where we expect
        int i = 0;
        for (String element : _testList)
        {
            // if the element of the iterator is not equal to the element we
            //      put into the list, we failed the test
            if (!ELEMENTS[i].equals(element))
            {
                fail("The element returned by the iterator did not match"
                    + " the element in the list.");
            }
            i++;
        }
        
        // create an iterator
        Iterator iterator = _testList.iterator();
        
        // test that we cannot remove elements
        try
        {
            // attempt to remove an element
            iterator.next();
            iterator.remove();
            
            // if we did not throw an exception, we have failed!
            fail("The iterator did not throw an UnsupportedOperationException"
                + " when we tried to remove an element.");
        }
        catch (UnsupportedOperationException e)
        {
            // success
        }
    }
    
    
    /**
     * Tests that we can still add elements to the list and they will be added
     *      in the correct place. This test is performed to make sure that
     *      the tail node was updated properly.
     * Assumes that the list contains ELEMENTS in the correct order.
     */
    private void testIntegrity() throws Exception
    {
        // now attempt to add another element, to make sure that the data
        //      structure hasn't been corrupted
        _testList.addElement(AN_ELEMENT);
        
        // declare the order
        ArrayList<String> order = new ArrayList<>();
        order.addAll(Arrays.asList(ELEMENTS));
        order.add(AN_ELEMENT);
        
        // test order and cardinality
        testCardinality(ELEMENTS.length + 1);
        testOrder(order.toArray(ELEMENTS));
    }
    
    
    /**
     * Tests that the list contains the elements in the expected order.
     *      NOTE: DOES NOT test that all elements in expectedOrder are part of
     *      the list. Instead, use testCardinality() to check that the list
     *      is the correct size.
     * 
     * @param expectedOrder an array containing the list of elements in their
     *      expected order
     */
    public void testOrder(String[] expectedOrder)
    {
        // iterate over the list, checking each individual element against the
        //      expected order
        int i = 0;
        for (String element : _testList)
        {
            // if the current element of the list and the current element of
            //      the expectedOrder don't match, we have failed the test
            if (!expectedOrder[i].equals(element))
            {
                fail("The elements were not the expected elements in the "
                    + "expected order.");
            }
            i++;
        }
    }
    
    
    /**
     * Tests if the list is the correct size based on its size() method and
     *      the number of elements it contains.
     * @param size the correct size we are testing the list to be
     */
    public void testCardinality(int size)
    {
        // test if it is empty
        assertEquals((size == 0), _testList.isEmpty());
        
        // test that the size variable is what we expect it to be
        assertEquals(size, _testList.size());
        
        // test that the size variable is the same as the number of 
        //      elements in the list
        // count the number of elements actually in the list
        int sizeCount = 0;
        for (String element : _testList)
        {
            sizeCount++;
        }
        
        // check the number of elements in the list against the list's size
        //      method
        assertEquals(sizeCount, _testList.size());
        
        // the list should never be full
        assertFalse(_testList.isFull());
    }
}
