
/**
 * This file contains all the public tests (visible on Gradescope)
 * Use this as a guide to write tests to verify your MyMinHeap and 
 * MyPriorityQueue implementation 
 */
import org.junit.*;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * This class contains public test cases for MyMinHeap and MyPriorityQueue
 */
public class PublicTester {

    // ===================== MyMinHeap Public Tests =====================

    /**
     * Helper method to initialize all instance variables of MyDeque
     * 
     * @param meanHeap The heap to initialize
     * @param data     The data array
     */
    static void initMinHeap(MyMinHeap<Integer> heap, ArrayList<Integer> data) {
        heap.data = new ArrayList<>(data);
    }

    /** Test the MyMinHeap default constructor without any input parameter */
    @Test
    public void testMinHeapConstructorNon() {
        MyMinHeap<Integer> minHeap = new MyMinHeap<>();
        assertEquals("Data ArrayList should be empty", 0, minHeap.data.size());
    }

    /** Test the MyMinHeap constructor with input data data */
    @Test
    public void testMinHeapConstructorDataList() {
        ArrayList<Integer> inputList = new ArrayList<Integer>(
            Arrays.asList(
                    new Integer[] { 5, 4, 1, 7, 2, 9, 6, 3 }
            )
        );
        MyMinHeap<Integer> minHeap = new MyMinHeap<>(inputList);
        Integer[] expected = { 1, 2, 5, 3, 4, 9, 6, 7 };
        for (int i = 0; i < 8; i++) {
            assertEquals(
                    "MinHeap order should be implemented",
                    expected[i],
                    minHeap.data.get(i));
        }
    }

    /** Test the MyMinHeap swap function */
    @Test
    public void testMinHeapSwap() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 3, 1, 2 }
            )
        );
        initMinHeap(heap, startingList);
        heap.swap(0, 1);
        Integer[] expected = { 1, 3, 2 };
        for (int i = 0; i < 3; i++) {
            assertEquals(
                    "Heap after swapping should have elements swapped. ",
                    expected[i],
                    heap.data.get(i));
        }
    }

    /** Test the MyMinHeap getParentIdx function when the heap is empty */
    @Test
    public void testMinHeapGetParentIdx() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] {}
            )
        );
        initMinHeap(heap, startingList);
        assertEquals("Parent index of 1", 0, heap.getParentIdx(1));
        assertEquals("Data ArrayList should still be empty", 0, heap.data.size());
    }

    /** Test the MyMinHeap getLeftChildIdx function when the heap is empty */
    @Test
    public void testMinHeapGetLeftChildIdx() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] {}
            )
        );
        initMinHeap(heap, startingList);
        assertEquals("Left child index of 0", 1, heap.getLeftChildIdx(0));
        assertEquals("Data List should still be empty", 0, heap.data.size());
    }

    /** Test the MyMinHeap getRightChildIdx function when the heap is empty */
    @Test
    public void testMinHeapGetRightChildIdx() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] {}
            )
        );
        initMinHeap(heap, startingList);
        assertEquals("Right child index of 0", 2, heap.getRightChildIdx(0));
        assertEquals("Data List should still be empty", 0, heap.data.size());
    }

    /** Test the MyMinHeap getMinChildIdx function */
    @Test
    public void testMinHeapGetMinChildIdx() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 5, 3, 2 }
            )
        );
        initMinHeap(heap, startingList);
        assertEquals("Minimum child index of 0", 2, heap.getMinChildIdx(0));
        Integer[] expected = { 5, 3, 2 };
        for (int i = 0; i < 3; i++) {
            assertEquals(
                    "Heap after getting minimum child index should be unchanged. ",
                    expected[i],
                    heap.data.get(i));
        }
    }

    /** Test the MyMinHeap percolateUp function */
    @Test
    public void testMinHeapPercolateUp() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 1, 4, 4, 2, 2 }
            )
        );
        initMinHeap(heap, startingList);
        heap.percolateUp(3);
        Integer[] expected = { 1, 2, 4, 4, 2 };
        for (int i = 0; i < 5; i++) {
            assertEquals(
                    "Heap after getting minimum child index should be unchanged. ",
                    expected[i],
                    heap.data.get(i));
        }
    }

    /** Test the MyMinHeap percolateDown function */
    @Test
    public void testMinHeapPercolateDown() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 8, 4, 3, 5, 2 }
            )
        );
        initMinHeap(heap, startingList);
        heap.percolateDown(0);
        Integer[] expected = { 3, 4, 8, 5, 2 };
        for (int i = 0; i < 5; i++) {
            assertEquals(
                "Heap after getting minimum child index should be unchanged. ", 
                expected[i], 
                heap.data.get(i)
            );
        }
    }

    /** Test the MyMinHeap deleteIndex function */
    @Test
    public void testMinHeapDeleteIndex() {
        MyMinHeap<Character> heap = new MyMinHeap<>();
        ArrayList<Character> startingList = new ArrayList<Character>(
            Arrays.asList(
                new Character[] { 'A', 'D', 'D', 'E', 'Z', 'X', 'G' }
            )
        );
        heap.data = new ArrayList<>(startingList);
        heap.deleteIndex(0);
        Character[] expected = { 'D', 'E', 'D', 'G', 'Z', 'X' };
        for (int i = 0; i < 6; i++) {
            assertEquals(
                "Heap after deleting index 0. ", 
                expected[i], 
                heap.data.get(i)
            );
        }
    }

    /** Test the MyMinHeap insert function */
    @Test
    public void testMinHeapInsert() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 11, 12, 13 }
            )
        );
        initMinHeap(heap, startingList);
        heap.insert(11);
        Integer[] expected = { 11, 11, 13, 12 };
        for (int i = 0; i < 4; i++) {
            assertEquals(
                "Heap after inserting 11. ", 
                expected[i], 
                heap.data.get(i)
            );
        }
    }

    /** Test the MyMinHeap getMin function */
    @Test
    public void testMinHeapGetMin() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 13, 18, 18 }
            )
        );
        initMinHeap(heap, startingList);
        assertEquals("Minimum element", new Integer(13), heap.getMin());
        Integer[] expected = { 13, 18, 18 };
        for (int i = 0; i < 3; i++) {
            assertEquals(
                "Heap after getMin should not be changed. ", 
                expected[i], 
                heap.data.get(i)
            );
        }
    }

    /** Test the MyMinHeap remove function */
    @Test
    public void testMinHeapRemove() {
        MyMinHeap<Integer> heap = new MyMinHeap<>(); 
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[]{13,18,18,25,18,19}
            )
        );
        heap.data = new ArrayList<>(startingList);
        Integer[] expected = {18,18,18,25,19};
        assertEquals("Minimum element", new Integer(13), heap.remove());
        assertEquals("Smaller size element", 5, heap.data.size());
        for (int i = 0; i < 5; i++) {
            assertEquals(
                "Heap after getMin should not be changed. ", 
                expected[i], 
                heap.data.get(i)
            );
        }
    }

    /** Test the MyMinHeap size function */
    @Test
    public void testMinHeapSize() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 13, 18, 18, 25, 18, 19 }
            )
        );
        initMinHeap(heap, startingList);
        assertEquals("Minimum element", 6, heap.size());
        Integer[] expected = { 13, 18, 18, 25, 18, 19 };
        for (int i = 0; i < 6; i++) {
            assertEquals(
                "Heap after size() should not be changed. ", 
                expected[i], 
                heap.data.get(i)
            );
        }
    }

    /** Test the MyMinHeap clear function */
    @Test
    public void testMinHeapClear() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 13, 18, 18, 25, 18, 19 }
            )
        );
        initMinHeap(heap, startingList);
        heap.clear();
        assertEquals("Data List should be empty", 0, heap.data.size());

    }

    // ===================== MyPriority Public Tests =====================

    /** Test the MyPriorityQueue No-arg constructor*/
    @Test
    public void testPriorityQueueNonConstructor() {
        MyMinHeap heap = new MyMinHeap();
        MyPriorityQueue pq = new MyPriorityQueue();
        assertTrue(
            "Priority queue's underlying heap is not null. ", pq.heap != null
        );
        assertEquals("Heap after no-arg constructor.", heap.data, pq.heap.data);

    }

    /** Test the MyPriorityQueue Collection-arg constructor*/
    @Test
    public void testPriorityCollectionConstructor() {
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 5, 4, 1, 7, 2, 9, 6, 3 }
            )
        );
        MyMinHeap<Integer> heap = new MyMinHeap<>(
            new ArrayList<>(startingList)
        );
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>(
            new ArrayList<>(startingList)
        );
        assertTrue(
            "Priority queue's underlying heap is not null. ", pq.heap != null
        );

        for (int i = 0; i < 8; i++) {
            assertEquals(
                "Heap after constructor builds the priority queue. ", 
                heap.data.get(i), 
                pq.heap.data.get(i)
            );
        }
    }

    /** Test the MyPriorityQueue push function */
    @Test
    public void testPriorityQueuePush() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 11, 12, 13 }
            )
        );        pq.heap = new MyMinHeap<>();
        pq.heap.data = new ArrayList<>(startingList);
        heap.data = new ArrayList<>(startingList);
        pq.push(11);
        heap.insert(11);
        assertTrue(
            "Priority queue's underlying heap is not null. ", pq.heap != null
        );
        for (int i = 0; i < 3; i++) {
            assertEquals(
                "Priority queue should be unchanged. ", 
                heap.data.get(i), 
                pq.heap.data.get(i)
            );
        }
    }

    /** Test the MyPriorityQueue peek function*/
    @Test
    public void testPriorityQueuePeek() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 13, 18, 18 }            
            )
        );        pq.heap = new MyMinHeap<>();
        pq.heap.data = new ArrayList<>(startingList);
        heap.data = new ArrayList<>(startingList);
        assertTrue(
            "Priority queue's underlying heap is not null. ", pq.heap != null
        );
        assertEquals(
            "Minimum element of [13,18,18]. ",
            heap.getMin(), 
            pq.peek()
        );
        
        for (int i = 0; i < 3; i++) {
            assertEquals(
                "Priority queue should be unchanged. ", 
                heap.data.get(i), 
                pq.heap.data.get(i)
            );
        }
    }

    /** Test the MyPriorityQueue Pop function*/
    @Test
    public void testPriorityQueuePop() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 13, 18, 18, 25, 18, 19 }
            )
        );
        pq.heap = new MyMinHeap<>();
        pq.heap.data = new ArrayList<>(startingList);
        heap.data = new ArrayList<>(startingList);
        assertTrue(
            "Priority queue's underlying heap is not null. ", pq.heap != null
        );
        assertEquals(
            "Highest priority (removed) element of [13,18,18,25,18,19]. ",
            heap.remove(), 
            pq.pop()
        );
        assertEquals(
            "Smaller size after the removal",
            5, 
            pq.heap.data.size()
        );
        
        for (int i = 0; i < 5; i++) {
            assertEquals(
                "Priority queue should be unchanged. ", 
                heap.data.get(i), 
                pq.heap.data.get(i)
            );
        }
    }

    /** Test the MyPriorityQueue size function*/
    @Test
    public void testPriorityQueueSize() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 13, 18, 18, 25, 18, 19 }
            )
        );
        pq.heap = new MyMinHeap<>();
        pq.heap.data = new ArrayList<>(startingList);
        heap.data = new ArrayList<>(startingList);

        assertTrue(
            "Priority queue's underlying heap is not null. ", pq.heap != null
        );
        assertEquals(
            "Length of [13,18,18,25,18,19]. ",
            heap.size(), 
            pq.getLength()
        );

        
        for (int i = 0; i < 5; i++) {
            assertEquals(
                "Priority queue should be unchanged. ", 
                heap.data.get(i), 
                pq.heap.data.get(i)
            );
        }
    }

    /** Test the MyPriorityQueue Clear function*/
    @Test
    public void testPriorityQueueClear() {
        MyMinHeap<Integer> heap = new MyMinHeap<>();
        MyPriorityQueue<Integer> pq = new MyPriorityQueue<>();
        ArrayList<Integer> startingList = new ArrayList<Integer>(
            Arrays.asList(
                new Integer[] { 13, 18, 18, 25, 18, 19 }
            )
        );
        pq.heap = new MyMinHeap<>();
        pq.heap.data = new ArrayList<>(startingList);
        heap.data = new ArrayList<>(startingList);
        pq.clear();
        heap.clear();

        assertTrue(
            "Priority queue's underlying heap is not null. ", pq.heap != null
        );
        assertEquals(
            "Length of [13,18,18,25,18,19]. ",
            0, 
            pq.heap.data.size()
        );
    }

    // ===================== Ticket Public Tests =====================
    /** Test the Ticket compareTo function
     *  When the priority is
     *  ENVIRONMENT_SETUP > DEBUGGING > CONCEPT_DOUBTS > OTHERS
    */
    @Test
    public void testTicketOrder1() {
        HashMap<String, Integer> priorityMap = new HashMap<>();
        priorityMap.put(Ticket.ENVIRONMENT_SETUP, Autograder.FIRST_PRIORITY);
        priorityMap.put(Ticket.DEBUGGING, Autograder.SECOND_PRIORITY);
        priorityMap.put(Ticket.CONCEPT_DOUBTS, Autograder.THIRD_PRIORITY);
        priorityMap.put(Ticket.OTHERS, Autograder.FOURTH_PRIORITY);
        Ticket.setOrderMap(priorityMap);
        Ticket tk1 = new Ticket("Chris", Ticket.ENVIRONMENT_SETUP);
        Ticket tk2 = new Ticket("Bruce", Ticket.CONCEPT_DOUBTS);
        Ticket tk3 = new Ticket("Alice", Ticket.DEBUGGING);

        // time should not matter in this case
        tk1.setCreatedAt(new Long(3));
        tk2.setCreatedAt(new Long(2));
        tk3.setCreatedAt(new Long(1));

        assertTrue(
            "tk1 should come before tk2", tk1.compareTo(tk2) < 0
        );
        assertTrue(
            "tk1 should come before tk3", tk1.compareTo(tk3) < 0
        );
        assertTrue(
            "tk2 should come after tk3", tk2.compareTo(tk3) > 0
        );
    }

    /** Test the Ticket compareTo function
     *  When the priority is
     *  DEBUGGING > CONCEPT_DOUBTS > ENVIRONMENT_SETUP > OTHERS
    */
    @Test
    public void testTicketOrder2() {
        HashMap<String, Integer> priorityMap = new HashMap<>();
        priorityMap.put(Ticket.DEBUGGING, Autograder.FIRST_PRIORITY);
        priorityMap.put(Ticket.CONCEPT_DOUBTS, Autograder.SECOND_PRIORITY);
        priorityMap.put(Ticket.ENVIRONMENT_SETUP, Autograder.THIRD_PRIORITY);
        priorityMap.put(Ticket.OTHERS, Autograder.FOURTH_PRIORITY);
        Ticket.setOrderMap(priorityMap);
        Ticket tk1 = new Ticket("Chris", Ticket.ENVIRONMENT_SETUP);
        Ticket tk2 = new Ticket("Bruce", Ticket.CONCEPT_DOUBTS);
        Ticket tk3 = new Ticket("Alice", Ticket.DEBUGGING);

        // time should not matter in this case
        tk1.setCreatedAt(new Long(3));
        tk2.setCreatedAt(new Long(2));
        tk3.setCreatedAt(new Long(1));


        assertTrue(
            "tk1 should come before tk2", tk1.compareTo(tk2) > 0
        );
        assertTrue(
            "tk1 should come before tk3", tk1.compareTo(tk3) > 0
        );
        assertTrue(
            "tk3 should come before tk2", tk3.compareTo(tk2) < 0
        );
    }

    /** Test the Ticket compareTo function
     *  When the priority are the same
    */
    @Test
    public void testTicketOrder3() {

        // priority should not matter in this case
        HashMap<String, Integer> priorityMap = new HashMap<>();
        priorityMap.put(Ticket.DEBUGGING, Autograder.FIRST_PRIORITY);
        priorityMap.put(Ticket.CONCEPT_DOUBTS, Autograder.SECOND_PRIORITY);
        priorityMap.put(Ticket.ENVIRONMENT_SETUP, Autograder.THIRD_PRIORITY);
        priorityMap.put(Ticket.OTHERS, Autograder.FOURTH_PRIORITY);
        Ticket.setOrderMap(priorityMap);

        Ticket tk1 = new Ticket("Chris", Ticket.ENVIRONMENT_SETUP);
        Ticket tk2 = new Ticket("Bruce", Ticket.ENVIRONMENT_SETUP);
        Ticket tk3 = new Ticket("Alice", Ticket.ENVIRONMENT_SETUP);
        Ticket tkE = new Ticket("Sophia", Ticket.ENVIRONMENT_SETUP);

        tk1.setCreatedAt(new Long(3));
        tk2.setCreatedAt(new Long(2));
        tk3.setCreatedAt(new Long(1));
        tkE.setCreatedAt(new Long(1));

        assertTrue(
            "tk1 should come after tk2", tk1.compareTo(tk2) > 0
        );
        assertTrue(
            "tk3 should come before tk1", tk3.compareTo(tk1) < 0
        );
        assertTrue(
            "tkE should be the same order of tk3", tkE.compareTo(tk3) == 0
        );
    }

    /** Test the Ticket compareTo function
     *  When the priority are the same
    */
    @Test
    public void testTicketOrder4() {

        // priority should not matter in this case
        HashMap<String, Integer> priorityMap = new HashMap<>();
        priorityMap.put(Ticket.DEBUGGING, Autograder.FIRST_PRIORITY);
        priorityMap.put(Ticket.CONCEPT_DOUBTS, Autograder.FIRST_PRIORITY);
        priorityMap.put(Ticket.ENVIRONMENT_SETUP, Autograder.FIRST_PRIORITY);
        priorityMap.put(Ticket.OTHERS, Autograder.FIRST_PRIORITY);
        Ticket.setOrderMap(priorityMap);

        Ticket tk1 = new Ticket("Chris", Ticket.ENVIRONMENT_SETUP);
        Ticket tk2 = new Ticket("Bruce", Ticket.DEBUGGING);
        Ticket tk3 = new Ticket("Alice", Ticket.CONCEPT_DOUBTS);
        Ticket tkE = new Ticket("Sophia", Ticket.OTHERS);

        //Time should matter
        tk1.setCreatedAt(new Long(3));
        tk2.setCreatedAt(new Long(2));
        tk3.setCreatedAt(new Long(1));
        tkE.setCreatedAt(new Long(1));

        assertTrue(
            "tk1 should come after tk2", tk1.compareTo(tk2) > 0
        );
        assertTrue(
            "tk3 should come before tk1", tk3.compareTo(tk1) < 0
        );
        assertTrue(
            "tkE should be the same order of tk3", tkE.compareTo(tk3) == 0
        );
    }
}
