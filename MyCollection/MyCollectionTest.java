import org.junit.*;
import static org.junit.Assert.*;
import java.util.NoSuchElementException;



/**
 * Test my implementation of the Hansen Collection class using JUnit.
 * Note that this test harness is a bit weak as it doesn't verify that
 * the collection maintains "sequence semantics"; that's because the
 * Hansen Collection doesn't really specify "sequence semantics"; many
 * of the classes that implement this interface will, and that should be
 * tested as a post-condition...
 */
public class MyCollectionTest
{
   private MyCollection<String> _testCollection; // Collection to test
   private final int TO_STORE = 5; // The number of elements we'll store
   private final String[] _STRINGS = {"this","that","another","and","one more"};


   /**
    * Method called by JUnit before each test
    */
   @Before
   public void setUp() 
   { 
      // Initialize the object we're testing
      _testCollection = new MyCollection<String>(TO_STORE); 
   }


   /**
    * Fill the collection, testing the addElement, isFull, size, and isEmpty methods.
    *
    * Postcondition: collection is filled with String objects 0-to-(TO_STORE-1)
    */
   @Test
   public void testFillCollection() throws Exception
   {
      // Collection should not be full, but should be empty
      testCardinality(0);

      // Add things to the collection. Make sure it shows the correct
      // number of elements and is not empty and is not full.
      for (int i=0; i<TO_STORE; i++)
      {
         // Add a value to the collection
         _testCollection.addElement(_STRINGS[i]);
         // Make sure the empty, full, and size are correct
         testCardinality(i+1);
      }

      // Make sure the empty, full, and size are correct
      testCardinality(TO_STORE);

   } // testFillCollection



   /**
    * Fill the collection, test adding to full
    * Note that we do NOT generally need to catch an expected
    * exception like this. Instead we could have defined the test
    * as @Test(expected = CollectionFullException.class)
    * however, because we want to see if the object is still intact
    * after an exception, we catch it so we can check.
    */
   @Test
   public void testFullCollection() throws Exception
   {
      testFillCollection();

      // If we try to add one more element, it should throw an
      // exception
      try 
      { 
         _testCollection.addElement(""); 
         // Uh-oh, if we got to this point we have a problem, so fail
         // this test
         fail("Should throw exception"); 
      }
      catch (CollectionFullException e) { } // Success

      // Just for fun, let's make sure that it's not empty, is full, and
      // the size is correct
      testCardinality(TO_STORE);

   } // testFullCollection




   /**
    * Make sure null's can't be added
    */
   @Test
   public void testAddNulls() throws Exception
   {
      // If we try to add NULL, it should throw an
      // exception
      try 
      { 
         _testCollection.addElement(null); 
         // Uh-oh, if we got to this point we have a problem, so fail
         // this test
         fail("Should throw exception"); 
      }
      catch (IllegalArgumentException e) { } // Success

      // Make sure it's still empty
      testCardinality(0);
   }



   /**
    * Make sure null's can't be found
    */
   @Test
   public void testContainsNulls() throws Exception
   {
      // If we search for a NULL we should get false 
      assertFalse(_testCollection.containsElement(null)); 
   }



   /**
    * Make sure null's can't be removed
    */
   @Test
   public void testRemoveNulls() throws Exception
   {
      // If we remove try to remove NULL we should get a no such element
      // exception 
      try
      {
         _testCollection.removeElement(null); 
         // Uh-oh, if we got to this point we have a problem, so fail
         // this test
         fail("Should throw exception"); 
      }
      catch (NoSuchElementException e) { } // Success

   } // testNulls


   /**
    * Testing the makeEmpty, isFull, size, and isEmpty methods.
    *
    * Postcondition - collection is empty
    */
   @Test
   public void testMakeEmpty() throws Exception
   {
      // Make sure we can safely call it on an empty collection
      _testCollection.makeEmpty();
      testCardinality(0);

      // Fill the collection
      testFillCollection();

      // Empty the collection and verify that it's empty, not full, with
      // the right number of elements
      _testCollection.makeEmpty();
      testCardinality(0);

   } // testMakeEmpty


   /**
    * Test the containsElement method
    *
    * Postcondition - collection is empty
    */
   @Test
   public void testContainsElement() throws Exception
   {
      // Try to find some things in an empty collection
      assertFalse(_testCollection.containsElement("Not in the collection"));

      // Call testFillCollection since it fills the collection for us
      testFillCollection();

      // Try to find each of the things we added to this collection
      for (int i=0; i<TO_STORE; i++)
      {
         assertTrue(_testCollection.containsElement(_STRINGS[i]));
      }

      // Test to make sure that we find based on equality, not identity
      StringBuffer s = new StringBuffer(_STRINGS[0]);
      assertTrue(_testCollection.containsElement(s.toString())); 

      // Make sure missing things aren't there
      assertFalse(_testCollection.containsElement("Not in the collection"));
      
   } // testContainsElement



   /**
    * Test the findElement method
    *
    * Postcondition - collection is empty
    */
   @Test
   public void testFindElement() throws Exception
   {
      // Try to find some things in an empty collection
      assertNull(_testCollection.findElement("")); 

      // Call testFillCollection since it fills the collection for us
      testFillCollection();

      // Try to find each of the things we added to this collection
      for (int i=0; i<TO_STORE; i++)
      {
         assertEquals(_STRINGS[i],(String)_testCollection.findElement(_STRINGS[i])); 
      }

      // Make sure missing things aren't there
      assertNull(_testCollection.findElement("")); 

      // Test to make sure that we find based on equality, not identity
      StringBuffer s = new StringBuffer(_STRINGS[0]);
      assertSame(_STRINGS[0], (String)_testCollection.findElement(s.toString())); 


   } // testContainsElement


   /**
    * Stress-test the collection to check performance. Normally we DO
    * NOT conduct stress-tests in harnesses, but this is used to alert
    * students who are managing the internal array incorrectly that
    * there is a problem. Don't put "stress tests" in YOUR JUnit
    * tests...
    */
   @Test (timeout=5000) // 5 seconds is more than plenty of time for this test
   public void testStressTest() throws Exception
   {
      final int A_BIG_NUMBER = 10000; // A little larger collection this time
      MyCollection<Integer> bigCollection; // Collection to test
      bigCollection = new MyCollection<Integer>(A_BIG_NUMBER); 
      Integer anInt = new Integer(0);

      // Fill it up
      for (int i=0; i<A_BIG_NUMBER; i++)
      {
         bigCollection.addElement(anInt);
      }
      // Remove all but one
      for (int i=0; i<A_BIG_NUMBER-1; i++)
      {
         bigCollection.removeElement(anInt);
      }
      // Now see how long it takes to see if that element is still in the collection
      for (int i=0; i<A_BIG_NUMBER*1000; i++)
      {
         assertTrue(bigCollection.containsElement(anInt));
      }

   } // testStressTest


   /**
    * Testing the removeElement, isFull, size, and isEmpty methods.
    *
    * Postcondition - collection is empty
    */
   @Test
   public void testRemoveElement() throws Exception
   {
      // If we try to remove from empty, it should throw an
      // exception
      try 
      { 
         _testCollection.removeElement("Not in the collection"); 
         // Uh-oh, if we got to this point we have a problem, so fail
         // this test
         fail("Should throw exception"); 
      }
      catch (NoSuchElementException e) { } // Success
      // Should still be empty
      testCardinality(0);

      // Fill the collection
      testFillCollection();

      // If we try to remove a non-existent element, it should throw an
      // exception
      try 
      { 
         _testCollection.removeElement("Not in the collection"); 
         // Uh-oh, if we got to this point we have a problem, so fail
         // this test
         fail("Should throw exception"); 
      }
      catch (NoSuchElementException e) { } // Success

      // Should still have right number and be full
      testCardinality(TO_STORE);

      // Remove a value to the collection and make sure it's not there
      // but that all others are
      _testCollection.removeElement(_STRINGS[2]);
      // Should have one less
      testCardinality(TO_STORE-1);
      // Verify what's in the collection and what's not
      for (int i=0; i<TO_STORE; i++)
      {
         if (i==2) // #2 should be missing
         {
            assertFalse(_testCollection.containsElement(_STRINGS[i]));
         }
         else // all others should still be there
         {
            assertTrue(_testCollection.containsElement(_STRINGS[i]));
         }
      }

      // Add the value back into the collection and make sure all the
      // elements are there by removing them one-by-one
      _testCollection.addElement(_STRINGS[2]);
      for (int i=0; i<TO_STORE; i++)
      {
         // Remove a value to the collection
         _testCollection.removeElement(_STRINGS[i]);
         // Make sure the size is correct - should be counting down from
         // TO_STORE to 0
         testCardinality(TO_STORE-1-i);
      }

      // Should now be empty, and the size should be 0
      testCardinality(0);

      // If we try to remove an element that was there, it should throw an
      // exception
      try 
      { 
         _testCollection.removeElement(_STRINGS[0]); 
         // Uh-oh, if we got to this point we have a problem, so fail
         // this test
         fail("Should throw exception"); 
      }
      catch (NoSuchElementException e) { } // Success

      // Just for fun, let's make sure that it's empty, not full, and
      // the size is correct
      testCardinality(0);

      // Now put the same element in the collection twice, remove one,
      // and make sure we still find the element
      _testCollection.addElement(_STRINGS[0]);
      _testCollection.addElement(_STRINGS[0]);
      testCardinality(2);
      _testCollection.removeElement(_STRINGS[0]);
      testCardinality(1);
      assertTrue(_testCollection.containsElement(_STRINGS[0]));

   } // testRemoveElement


   /**
    * Helper method to see if isEmpty, isFull, and size are correct
    *
    * @param num is number of elements that should be in the collection
    */
   public void testCardinality(int num)
   {
      assertEquals((num==0),_testCollection.isEmpty());
      assertEquals((num==TO_STORE),_testCollection.isFull());
      assertEquals(num,_testCollection.size());
   }


}
