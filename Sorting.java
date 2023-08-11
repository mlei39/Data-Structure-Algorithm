import java.util.Comparator;
import java.util.Random;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.List;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Minkun Lei
 * @version 1.0
 * @userid mlei39
 * @GTID 903705132
 *
 * Collaborators: none
 * Resources: none
 */
public class Sorting {

    /**
     * Implement insertion sort.
     * It should be:
     * in-place
     * stable
     * adaptive
     * Have a worst case running time of:
     * O(n^2)
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("the array or comparator is null");
        }
        for (int i = 1; i < arr.length; i++) {
            int idx = i;
            while (idx != 0 && comparator.compare(arr[idx], arr[idx - 1]) < 0) {
                swap(arr, idx, idx - 1);
                idx--;
            }
        }
    }

    /**
     * this method helps us swap the two target elements
     * @param arr the array of which the elements will be swapped
     * @param idx1 the index of the first element to be swapped
     * @param idx2 the index of the second element to be swapped
     * @param <T> the generic type of the element
     */
    private static <T> void swap(T[] arr, int idx1, int idx2) {
        T temp;
        temp = arr[idx1];
        arr[idx1] = arr[idx2];
        arr[idx2] = temp;
    }

    /**
     * Implement cocktail sort.
     * It should be:
     * in-place
     * stable
     * adaptive
     * Have a worst case running time of:
     * O(n^2)
     * And a best case running time of:
     * O(n)
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("the array or comparator is null");
        }

        int front = 0;
        int back = arr.length - 1;
        boolean swapMade = true;

        while (swapMade) {
            swapMade = false;
            int end = back;
            for (int idx = front; idx < end; idx++) {
                if (comparator.compare(arr[idx], arr[idx + 1]) > 0) {
                    swap(arr, idx, idx + 1);
                    swapMade = true;
                    back = idx;
                }
            }
            if (swapMade) {
                swapMade = false;
                int start = front;
                for (int idx = back; idx > start; idx--) {
                    if (comparator.compare(arr[idx - 1], arr[idx]) > 0) {
                        swap(arr, idx - 1, idx);
                        swapMade = true;
                        front = idx;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * Have a worst case running time of:
     * O(n log n)
     * And a best case running time of:
     * O(n log n)
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new java.lang.IllegalArgumentException("the array or comparator is null");
        }

        if (arr.length <= 1) {
            return;
        }
        int l = arr.length;
        int mid = l / 2;

        T[] left = (T[]) new Object[mid];
        for (int i = 0; i < mid; i++) {
            left[i] = arr[i];
        }

        T[] right = (T[]) new Object[l - mid];     // why can't use Comparable[] ? ? ?
        for (int i = mid; i < l; i++) {
            right[i - mid] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int i = 0;
        int j = 0;

        while (i < left.length && j < right.length) {
            if (comparator.compare(left[i], right[j]) > 0) {
                arr[i + j] = right[j];
                j++;
            } else {
                arr[i + j] = left[i];
                i++;
            }
        }

        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement quick sort.
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     * int pivotIndex = rand.nextInt(b - a) + a;
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     * It should be:
     * in-place
     * unstable
     * not adaptive
     * Have a worst case running time of:
     * O(n^2)
     * And a best case running time of:
     * O(n log n)
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new java.lang.IllegalArgumentException("the array or comparator or rand is null");
        }

        quickHelp(arr, comparator, rand, 0, arr.length - 1);
    }

    /**
     * this helper method implements the actual content of the quickSort method
     * @param arr the arr that will be sorted
     * @param comparator the comparator object that helps us compare elements in the arr
     * @param rand an object that helps us generate a pseudo-random number
     * @param start the first index of the sub-array that will be sorted
     * @param end the last index of the sub-array that will be sorted
     * @param <T> the generic type of the element
     */
    private static <T> void quickHelp(T[] arr, Comparator<T> comparator, Random rand, int start, int end) {
        if (end - start < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T val = arr[pivotIndex];
        swap(arr, start, pivotIndex);

        int i = start + 1;
        int j = end;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], val) <= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], val) >= 0) {
                j--;
            }
            if (i <= j) {
                swap(arr, i, j);
                i++;
                j--;
            }
        }

        swap(arr, start, j);
        quickHelp(arr, comparator, rand, start, j - 1);
        quickHelp(arr, comparator, rand, j + 1, end);
    }


    /**
     * Implement LSD (least significant digit) radix sort.
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     * Have a worst case running time of:
     * O(kn)
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need. The number of iterations
     * can be determined using the number with the largest magnitude.
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new java.lang.IllegalArgumentException("the array passed in is null");
        }

        LinkedList<Integer>[] buckets = new LinkedList[19];

        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        int max = 0;
        for (int j : arr) {
            if (j == Integer.MIN_VALUE || max == Integer.MIN_VALUE) {
                max = Integer.MIN_VALUE;
            } else if (Math.abs(j) > Math.abs(max)) {
                max = j;
            }
        }
        int count = 0;
        while (max != 0) {
            max /= 10;
            count++;
        }

        int div = 1;
        for (int k = 0; k < count; k++) {
            for (int j : arr) {
                int digit = helpDigit(j, div);
                buckets[digit + 9].add(j);
            }
            int idx = 0;
            for (LinkedList<Integer> i : buckets) {
                while (!i.isEmpty()) {
                    arr[idx] = i.remove();
                    idx++;
                }
            }
            div *= 10;
        }
    }

    /**
     * this method helps us find the target digit of the passed in number
     * @param num the number of which we need to find the digit
     * @param div the divisor -- by doing num/div, we can shift the target digit to the ones place
     * @return the target digit of the passed in number
     */
    private static int helpDigit(int num, int div) {
        int digit = num / div;
        return (digit % 10);
    }

    /**
     * Implement heap sort.
     * It should be:
     * out-of-place
     * unstable
     * not adaptive
     * Have a worst case running time of:
     * O(n log n)
     * And a best case running time of:
     * O(n log n)
     * Use java.util.PriorityQueue as the heap. Note that in this
     * PriorityQueue implementation, elements are removed from smallest
     * element to largest element.
     *
     * Initialize the PriorityQueue using its build heap constructor (look at
     * the different constructors of java.util.PriorityQueue).
     *
     * Return an int array with a capacity equal to the size of the list. The
     * returned array should have the elements in the list in sorted order.
     *
     * @param data the data to sort
     * @return the array with length equal to the size of the input list that
     * holds the elements from the list is sorted order
     * @throws java.lang.IllegalArgumentException if the data is null
     */
    public static int[] heapSort(List<Integer> data) {
        if (data == null) {
            throw new IllegalArgumentException("the input list of data is null");
        }
        PriorityQueue<Integer> heap = new PriorityQueue<Integer>(data);
        int[] arr = new int[data.size()];
        for (int i = 0; i < data.size(); i++) {
            arr[i] = heap.remove();
        }
        return arr;
    }
}
