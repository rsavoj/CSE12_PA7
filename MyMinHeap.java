import java.lang.reflect.Array;
import java.util.ArrayList;
//can we import
import java.util.Collection;

/**
 * TODO: Add your file header
 * Name:
 * ID:
 * Email:
 * Sources used: Put "None" if you did not have any external help
 * Some example of sources used would be Tutors, Zybooks, and Lecture Slides
 * 
 * 2-4 sentence file description here
 */

// Your import statements

/**
 * TODO: Add class header
 */
public class MyMinHeap<E extends Comparable<E>> implements MinHeapInterface <E>{
    //the root of the tree will be at index 0
    public ArrayList<E> data;
    public static final int ROOT = 0;

    
    public MyMinHeap(){
        data = new ArrayList<>();
    }
    public MyMinHeap(Collection<? extends E> collection){
       if(collection == null || collection.contains(null)){
           throw new NullPointerException();
       }
        data = new ArrayList<>(collection);



    }
    protected void swap(int from, int to){
        //stores the data in the from location in a temporary variable
        E temp = data.get(from);
        //switches the two varibales 
        data.set(from, data.get(to));
        data.set(to, temp);

    }
    protected int getParentIdx(int index){
        return (index+1)/2 -1;
    }
    protected int getLeftChildIdx(int index){
        if(index == ROOT){
            return 1;
        }
        return 2*(index+1);
    }
    protected int getRightChildIdx(int index){
        if(index == ROOT){
            return 2;
        }
        return 2*(index+1);
    }

    protected int getMinChildIdx(int index){
        boolean noLeft = false;
        boolean noRight = false;
        int right = getRightChildIdx(index);
        int left = getLeftChildIdx(index);
        
        //checks if there is a left child
        if(left >= data.size()  || data.get(left)==null){
            noLeft = true;
        }
        //checks if there is a right child 
        if(right >= data.size()  || data.get(right)==null){
            noRight = true;
        }
        //checks if element at index is a left node
        if(noRight && noLeft){
            return -1;
        }
        //returns the left child if there is no right child
        else if(noRight){
            return left;
        }
        //returns the right child index if there is no left child
        else if(noLeft){
            return right;
        }
        else{
            int compare = data.get(right).compareTo(data.get(left));
            //we are assuming no elements are the same so compare to will
            //never return 0

            //checks if right is larger than left and returns left
            if(compare == 1 || compare == 0){
                return left;
            }
            //returns right if left is not larger than right 
            return right;
        }

    }
    protected void percolateUp(int index){
        int  parent = getParentIdx(index);
        E parentData = data.get(parent);
        if(parentData == null ){
            return;
        }
        int comparison = parentData.compareTo(data.get(index));
        if(comparison == -1 || comparison == 0){
            return;
        }
        swap(index, parent);
        percolateUp(parent);

    }
    protected void percolateDown(int index){
        int child = getMinChildIdx(index);
        if(child == -1){
            return;
        }
        int compare = data.get(index).compareTo(data.get(child));
        if(compare == -1 || compare == 0 ){
            return;
        }
        swap(index, child);
        percolateDown(index);
    }
    protected E deleteIndex(int index){
       E temp = data.get(index);
       int lastEle = data.size()-1;
        if(index == lastEle){
            data.remove(lastEle);
        
            return temp;
       }
       else{
        swap(index, lastEle);
        data.remove(lastEle);
        percolateDown(index);
        return temp;
       }
        
    }
    @Override
    public void insert(E element) {
        
        
    }
    @Override
    public E getMin() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public E remove() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public int size() {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public void clear() {
        // TODO Auto-generated method stub
        
    }
  
   

}