
// Your import statements
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Name: Roya Savoj
 * ID: A16644834
 * Email: rsavoj@ucsd.edu
 * Sources used: Zybooks, and Lecture Slides
 * 
 * The myMinHeap class constructs a simple heap object. Users can add and 
 * remove items to the heap. The heap follows the rule where the parents are 
 * always greater than the children and the tree is filled at the bottom from
 * left to right.
 */

/**
 * The MyMinHeap class has an underlying arrayList data structure. The root
 * of the tree is at 0 and all other elements are added after.
 */
public class MyMinHeap<E extends Comparable<E>> implements MinHeapInterface<E> {
    // the root of the tree will be at index 0
    public ArrayList<E> data;
    public static final int ROOT = 0;

    /**
     * Creates a MyMinHeap object with an empty heap
     * 
     * @return Student name
     */
    public MyMinHeap() {
        data = new ArrayList<>();
    }

    /**
     * Creates a MyMinHeap object with a collection of data. The data is
     * sorted according to the percolate Down heap sort algorithum
     * 
     * @param collection the collection of data to be added to the array
     */
    public MyMinHeap(Collection<? extends E> collection) {
        // checks if the collection is null or any elements in the collection
        // are null
        if (collection == null || collection.contains(null)) {
            throw new NullPointerException();
        }
        // instanciates the data arrayList in MyMinHeap
        data = new ArrayList<>(collection);

        // performs the percolate down function for every element in the data
        // array list
        for (int i = 0; i < data.size(); i++) {
            percolateDown(i);
        }
    }

    /**
     * Helper method that swaps to elements in the data arrayList
     * 
     * @param from the index of the first element to swap
     * @param to   the index of the second element to swap
     */
    protected void swap(int from, int to) {

        // stores the data in the from location in a temporary variable
        E temp = data.get(from);

        // switches the two varibales
        data.set(from, data.get(to));
        data.set(to, temp);

    }

    /**
     * Returns the parents index of a given child
     * 
     * @param index the index of the child whose parent will be found
     * @return The index of the parent
     */
    protected int getParentIdx(int index) {
        return (index + 1) / 2 - 1;
    }

    /**
     * Returns the left child index given the parent
     * 
     * @param index the index of the parent
     * @return The index of the left child
     */
    protected int getLeftChildIdx(int index) {
        return (2 * (index)) + 1;
    }

    /**
     * Returns the right child index given the parent
     * 
     * @param index the index of the parent
     * @return The index of the right child
     */
    protected int getRightChildIdx(int index) {
        return 2 * (index) + 2;
    }

    /**
     * Returns the index of the smaller child
     * 
     * @param index the index of the parent
     * @return The index of the smaller child
     */
    protected int getMinChildIdx(int index) {
        boolean noLeft = false;
        boolean noRight = false;
        int right = getRightChildIdx(index);
        int left = getLeftChildIdx(index);

        // checks if there is a left child
        if (left >= data.size() || data.get(left) == null) {
            noLeft = true;
        }
        // checks if there is a right child
        if (right >= data.size() || data.get(right) == null) {
            noRight = true;

        }
        // checks if element at index is a left node
        if (noRight && noLeft) {
            return -1;
        }

        // returns the left child if there is no right child
        else if (noRight) {
            return left;
        }
        // returns the right child index if there is no left child
        else if (noLeft) {
            return right;
        }

        else {
            int compare = data.get(right).compareTo(data.get(left));
            // we are assuming no elements are the same so compare to will
            // never return 0

            // checks if left is larger than right and returns right
            if (compare < 0) {
                return right;
            }
            // returns left if left is not larger than right
            return left;
        }

    }

    /**
     * Item at index will be swaped with the parent until the parent is less
     * than or equal to the item
     * 
     * @param index the index of the item that will be moved up
     */
    protected void percolateUp(int index) {
        int parent = getParentIdx(index);
        // checks if we are at the root and returns
        if (parent < 0) {
            return;
        }
        // stores the parent data
        E parentData = data.get(parent);

        // stores the comparison
        int comparison = parentData.compareTo(data.get(index));
        // if child is greater than or equal to the parent the recursive method
        // terminates
        if (comparison <= 0) {
            return;
        }
        // otherwise the elements will be swapped and the next recursive method
        // will be called
        swap(index, parent);
        percolateUp(parent);

    }

    /**
     * Swaps the element with its smallest child until it is less than its
     * parent
     * 
     * @param index the index of the element that is swapped
     */
    protected void percolateDown(int index) {
        int child = getMinChildIdx(index);
        // checks if an element has no children to swap with
        if (child == -1) {
            return;
        }
        // compare the smallest child and the element
        int compare = data.get(index).compareTo(data.get(child));

        // returns if conditions for heap are already met
        if (compare <= 0) {
            return;
        }
        // otherwise swaps and recursivly calls the method
        swap(index, child);
        percolateDown(child);
    }

    /**
     * Deleates and element at a given index
     * 
     * @param index the index of the element that is deleated
     * @return The element that is deleated
     */
    protected E deleteIndex(int index) {
        // stores the element that will be deleated
        E temp = data.get(index);
        int lastEle = data.size() - 1;

        // if element is the last element it is removed
        if (index == lastEle) {
            data.remove(lastEle);

            return temp;
        } else {

            // swaps the last element with the index to be removed and removes the
            // element
            swap(index, lastEle);
            data.remove(lastEle);

            // makes calls to helper method so that we can ensure the heap still
            // obeys the rukes
            percolateDown(index);
            percolateUp(index);

            return temp;
        }

    }

    /**
     * Inserts an element to the lefmost open leaf node of the MyMinHeap object
     * 
     * @param element the element to be inserted
     * @return The index of the left child
     */
    @Override
    public void insert(E element) {
        // if element is null it cannot be added
        if (element == null) {
            throw new NullPointerException();
        }
        // adds the element to the End of the arrayList
        data.add(element);
        // calls percolate up to ensure the data structure is maintianed
        percolateUp(data.size() - 1);
    }

    /**
     * Returns the minimum element in the MyMinHeap(ROOT)
     * 
     * @return The minimum element in the MyMinHeap
     */
    @Override
    public E getMin() {
        // checks if the root is empty
        if (data.size() == ROOT) {
            return null;
        }
        return data.get(ROOT);

    }

    /**
     * Removes an element ar the root
     * 
     * @return element that was at the root
     */
    @Override
    public E remove() {
        if (data.size() == ROOT) {
            return null;
        }
        // calls deleat index for the element at the root
        return deleteIndex(ROOT);
    }

    /**
     * Returns the number of elements in MyMinHeap
     * 
     * @return Returns the number of elements in MyMinHeap
     */
    @Override
    public int size() {
        return data.size();
    }

    /**
     * removes all the elements from the MyMinHeap data Structure
     */
    @Override
    public void clear() {
        data.clear();

    }

}