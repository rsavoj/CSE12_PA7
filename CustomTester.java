
/**
 * Name: Roya Savoj
 * ID: A16644834
 * Email: rsavoj@ucsd.edu
 * Sources used: Zybooks, and Lecture Slides
 * 
 * The custom tester file implements tests which test methods from the 
 * MyMinHeap and the MyPriorityQueue class. The purpose of this file is to test
 * for cases which are not in the public tester class
 */

import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * This file uses J unit test methods from index locations not covered in the
 * public tester and tests which are expected to cause errors
 */
public class CustomTester {

    /**
     * This is a constructor with no agurments which creates a MyMinHeap
     * with no elements
     */
    @Test
    public void testMyMinHeapConstructor() {
        MyMinHeap<Character> heap = new MyMinHeap<>();
        ArrayList<Character> startingList = new ArrayList<Character>(
                Arrays.asList(
                        new Character[] { null, 'D', 'D', null, 'Z', 'X', 'G' }));
        // trys to create a MyMinHeap using a colllection with null values
        try {
            heap.data = new ArrayList<>(startingList);
        } catch (NullPointerException e) {
            System.out.println("You cannot make a MyMinHeap object with a" +
                    " list containing a null value");
        }
        // trys to create a MyMinHeap using a null constructor
        try {
            heap.data = new ArrayList<>(null);
        } catch (NullPointerException e) {
            System.out.println("You cannot make a MyMinHeap null arguments");
        }

    }

    /**
     * Test the getMinChildIdx method when the parent has no children or one
     * child
     */
    @Test
    public void testGetMinChildIdx() {
        // basic heap data
        MyMinHeap<Character> heap = new MyMinHeap<>();
        ArrayList<Character> startingList = new ArrayList<Character>(
                Arrays.asList(
                        new Character[] { 'A', 'B', 'C', 'D', }));
        heap.data = new ArrayList<>(startingList);

        // Checks get min child index at a leaf node
        // index of 2 has a value of C and no children it is a left node
        assertEquals("If a node has no children get min childIndex should " +
                "return -1 ", -1, heap.getMinChildIdx(2));

        // Checks get min child index when there is only one child
        assertEquals("If a node has one child get min childIndex should " +
                "return the child index ", 3, heap.getMinChildIdx(1));

    }

    /**
     * Test the percolateUp method when the node being percolated is at the
     * root or is a leaf node
     */
    @Test
    public void testPercolateUp() {

        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
                Arrays.asList(
                        new Integer[] { 23, 4, 5, 6, 7, 3, 9 }));
        ArrayList<Integer> finalList1 = new ArrayList<Integer>(
                Arrays.asList(
                        new Integer[] { 23, 4, 5, 6, 7, 3, 9 }));
        initMinHeap(heap, startingList);

        // test percolate up from the root
        heap.percolateUp(0);
        // list should not change there is no position for the root to swap with
        for (int i = 0; i < finalList1.size(); i++) {
            assertEquals("elements are not changed", heap.data.get(i),
                    finalList1.get(i));
        }
        // test percolate up on a leaf node that will not move
        heap.percolateUp(6);
        for (int i = 0; i < finalList1.size(); i++) {
            assertEquals("elements are not changed", heap.data.get(i),
                    finalList1.get(i));
        }

        // test percolate from a leaf node that moves through list

        // expected output when the node at index 5 is percolated
        ArrayList<Integer> finalList2 = new ArrayList<Integer>(
                Arrays.asList(
                        new Integer[] { 3, 4, 23, 6, 7, 5, 9 }));
        heap.percolateUp(5);
        for (int i = 0; i < finalList2.size(); i++) {
            assertEquals("elements are not changed", heap.data.get(i),
                    finalList2.get(i));
        }

    }

    /**
     * Test the percolateDown method when the node being percolated is at the
     * root or is a leaf node
     */
    @Test
    public void testPercolateDown() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
                Arrays.asList(
                        new Integer[] { 23, 4, 5, 40, 39, 3, 9 }));
        ArrayList<Integer> finalList1 = new ArrayList<Integer>(
                Arrays.asList(
                        new Integer[] { 23, 4, 5, 40, 39, 3, 9 }));
        ArrayList<Integer> finalList2 = new ArrayList<Integer>(
                Arrays.asList(
                        new Integer[] { 4, 23, 5, 40, 39, 3, 9 }));
        initMinHeap(heap, startingList);

        // test percolate down at leaf node
        heap.percolateDown(6);

        // list should not change there is no position for the leaf to swap with
        for (int i = 0; i < finalList1.size(); i++) {
            assertEquals("elements are not changed", heap.data.get(i),
                    finalList1.get(i));
        }

        // test percolate down root
        heap.percolateDown(0);

        // checks data matches the expected value
        for (int i = 0; i < finalList2.size(); i++) {
            assertEquals("root is percolated correctly", heap.data.get(i),
                    finalList2.get(i));
        }
    }

    /**
     * Test the deleteIndex method when deleating from a left node or from the
     * middle of the heap.
     */
    @Test
    public void testDeleteIndex() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
                Arrays.asList(
                        new Integer[] { 1, 7, 2, 40, 39, 6, 28 }));

        initMinHeap(heap, startingList);
        // test deleate index at a leaf node end of the list
        heap.deleteIndex(6);

        // the expected array output
        ArrayList<Integer> firstComparison = new ArrayList<Integer>(
                Arrays.asList(new Integer[] { 1, 7, 2, 40, 39, 6 }));

        // checks that the heap matches the expected output
        for (int i = 0; i < firstComparison.size(); i++) {
            assertEquals("root is percolated correctly", heap.data.get(i),
                    firstComparison.get(i));
        }

        // test deleate index at a leaf node not at the end of the list
        heap.deleteIndex(3);

        // the expected array output
        ArrayList<Integer> secondComparison = new ArrayList<Integer>(
                Arrays.asList(new Integer[] { 1, 6, 2, 7, 39 }));
        for (int i = 0; i < secondComparison.size(); i++) {
            assertEquals("root is percolated correctly", heap.data.get(i),
                    secondComparison.get(i));
        }

    }

    /**
     * Test the deleteIndex method when the index is in the middle of the heap
     */
    @Test
    public void testDeleteIndex2() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
                Arrays.asList(
                        new Integer[] { 3, 4, 5, 6, 7, 8, 9 }));
        initMinHeap(heap, startingList);
        // test deleate index in the middle of the heap
        heap.deleteIndex(2);

        // expected output
        ArrayList<Integer> secondComparison = new ArrayList<Integer>(
                Arrays.asList(new Integer[] { 3, 4, 8, 6, 7, 9 }));

        // checks the expected output matches
        for (int i = 0; i < secondComparison.size(); i++) {
            assertEquals("element is percolated correctly", heap.data.get(i),
                    secondComparison.get(i));
        }
    }

    /**
     * Test the insert method when the element is null
     */
    @Test
    public void testInsert() {
        // inserting a null element
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
                Arrays.asList(new Integer[] { 3, 4, 5, 6, 7, 8, 9 }));
        initMinHeap(heap, startingList);
        // Test insert with a null element
        try {
            heap.insert(null);
            fail();
        } catch (NullPointerException e) {
            System.out.println("cannot insert a null element into a heap");
        }

    }

    /**
     * Test the insert method when the element is percolated to the root and
     * when the element is not percolated at all.
     */
    @Test
    public void testInsert2() {
        // element does not need to be percolated
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
                Arrays.asList(new Integer[] { 3, 4, 5, 6, 7, 8, 9 }));
        initMinHeap(heap, startingList);

        // Test insert with an element that will not be percolated
        heap.insert(7);

        // expected output
        ArrayList<Integer> listAfterInsertOne = new ArrayList<Integer>(
                Arrays.asList(new Integer[] { 3, 4, 5, 6, 7, 8, 9, 7 }));

        // checks actual matches the expected output
        for (int i = 0; i < listAfterInsertOne.size(); i++) {
            assertEquals("array matches the expected",
                    listAfterInsertOne.get(i), heap.data.get(i));
        }

        // test insert with an element that will be moved to the root
        heap.insert(0);

        // expected output
        ArrayList<Integer> listAfterInsertTwo = new ArrayList<Integer>(
                Arrays.asList(new Integer[] { 0, 3, 5, 4, 7, 8, 9, 7, 6 }));

        // checks actual matches the expected output
        for (int i = 0; i < listAfterInsertTwo.size(); i++) {
            assertEquals("array matches the expected",
                    listAfterInsertTwo.get(i), heap.data.get(i));
        }
    }

    /**
     * Test remove when the heap is empty
     */
    @Test
    public void testRemove() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        assertEquals("test remove on an empty heap", null, heap.remove());
    }

    /**
     * Test getMin when the heap is empty
     */
    @Test
    public void testGetMin() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        assertEquals("test remove on an empty heap", null, heap.getMin());
    }

    /**
     * Helper method to initialize all instance variables of MyDeque
     * 
     * @param meanHeap The heap to initialize
     * @param data     The data array
     */
    private static void initMinHeap(MyMinHeap<Integer> heap, ArrayList<Integer> data) {
        heap.data = new ArrayList<>(data);
    }
}