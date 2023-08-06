import java.util.ArrayList;

/**
 * Your implementation of a MinHeap.
 *
 * @author Minkun Lei
 * @version 1.0
 * @userid mlei39
 * @GTID 903705132
 *
 * Collaborators: none
 *
 * Resources: none
 */
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     * To initialize the backing array, create a Comparable array and then cast
     * it to a T array.
     */
    public MinHeap() {
        this.backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * size of the passed in ArrayList (not INITIAL_CAPACITY). Index 0 should
     * remain empty, indices 1 to n should contain the data in proper order, and
     * the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("the input arraylist is null");
        }
        this.backingArray = (T[]) new Comparable[2 * data.size() + 1];
        size = data.size();
        for (int i = 1; i <= data.size(); i++) {
            if (data.get(i - 1) == null) {
                throw new IllegalArgumentException("an element in the input arraylist is null");
            }
            backingArray[i] = data.get(i - 1);
        }
        for (int i = data.size() / 2; i > 0; i--) {
            downH(i);
        }
    }

    /**
     * swap two elements in the backingArray.
     * @param idx1 the index of the first element that needs to be swapped
     * @param idx2 the index of the other element that needs to be swapped
     */
    private void swap(int idx1, int idx2) {
        T temp = backingArray[idx1];
        backingArray[idx1] = backingArray[idx2];
        backingArray[idx2] = temp;
    }

    /**
     * This is the downHeap method.
     * If the value of the current element is greater than the child element, then we swap them.
     * This process keep going until we reach the last element that has a child element.
     * @param idx the index of the element that we want to start with.
     */
    private void downH(int idx) {
        if (idx <= size / 2) {
            if (backingArray[idx * 2 + 1] == null) {
                if (backingArray[idx].compareTo(backingArray[idx * 2]) > 0) {
                    swap(idx, idx * 2);
                    downH(idx * 2);
                }
            } else {
                if (backingArray[idx].compareTo(backingArray[idx * 2]) > 0
                        && backingArray[idx].compareTo(backingArray[idx * 2 + 1]) < 0) {
                    swap(idx, idx * 2);
                    downH(idx * 2);
                }
                if (backingArray[idx].compareTo(backingArray[idx * 2]) < 0
                        && backingArray[idx].compareTo(backingArray[idx * 2 + 1]) > 0) {
                    swap(idx, idx * 2 + 1);
                    downH(idx * 2 + 1);
                }
                if (backingArray[idx].compareTo(backingArray[idx * 2]) > 0
                        && backingArray[idx].compareTo(backingArray[idx * 2 + 1]) > 0) {
                    if (backingArray[idx * 2 + 1].compareTo(backingArray[idx * 2]) > 0) {
                        swap(idx, idx * 2);
                        downH(idx * 2);
                    } else {
                        swap(idx, idx * 2 + 1);
                        downH(idx * 2 + 1);
                    }
                }
            }
        }
        return;
    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     * The order property of the heap must be maintained after adding. You can
     * assume that no duplicate data will be passed in.
     * 
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("the data that we try to add to the heap is null");
        }
        if (backingArray.length == size + 1) {
            T[] haha = (T[]) new Comparable[backingArray.length * 2];
            for (int i = 1; i <= size; i++) {
                haha[i] = backingArray[i];
            }
            backingArray = haha;
        }
        backingArray[size + 1] = data;
        size++;
        upH(size);
    }

    /**
     * This is the upHeap method.
     * If the value of the current element is less than the parent element, then we swap them.
     * This process keep going until we reach the root of the heap.
     * @param idx the index of the element that we want to start with.
     */
    private void upH(int idx) {
        while (idx != 1 && backingArray[idx].compareTo(backingArray[idx / 2]) < 0) {
            swap(idx, idx / 2);
            idx /= 2;
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     * The order property of the heap must be maintained after removing.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("the heap is empty, so there is nothing to be removed");
        }
        T temp = backingArray[1];
        backingArray[1] = backingArray[size];
        backingArray[size] = null;
        size--;
        downH(1);
        return temp;
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new java.util.NoSuchElementException("the heap is empty, so there is nothing to be removed");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
