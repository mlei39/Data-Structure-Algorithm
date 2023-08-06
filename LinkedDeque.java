/**
 * Your implementation of a LinkedDeque.
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
public class LinkedDeque<T> {

    // Do not add new instance variables or modify existing ones.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the front of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addFirst(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The parameter data is null");
        } else if (head == null) {
            head = new LinkedNode<>(data);
            tail = head;
            size++;
        } else {
            LinkedNode<T> node = new LinkedNode<>(data);
            node.setNext(head);
            node.getNext().setPrevious(node);
            head = node;
            size++;
        }
    }

    /**
     * Adds the element to the back of the deque.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the deque
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addLast(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The parameter data is null");
        } else if (head == null) {
            head = new LinkedNode<>(data);
            tail = head;
            size++;
        } else {
            LinkedNode<T> node = new LinkedNode<>(data);
            node.setPrevious(tail);
            node.getPrevious().setNext(node);
            tail = node;
            size++;
        }
    }

    /**
     * Removes and returns the first element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeFirst() {
        if (head == null) {
            throw new java.util.NoSuchElementException("This deque is empty and we cannot find any element in it");
        } else if (head.getNext() == null) {
            LinkedNode<T> temp = head;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        } else {
            LinkedNode<T> temp = head;
            head = head.getNext();
            head.setPrevious(null);
            size--;
            return temp.getData();
        }
    }

    /**
     * Removes and returns the last element of the deque.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T removeLast() {
        if (head == null) {
            throw new java.util.NoSuchElementException("This deque is empty and we cannot find any element in it");
        } else if (tail.getPrevious() == null) {
            LinkedNode<T> temp = tail;
            head = null;
            tail = null;
            size--;
            return temp.getData();
        } else {
            LinkedNode<T> temp = tail;
            tail = tail.getPrevious();
            tail.setNext(null);
            size--;
            return temp.getData();
        }
    }

    /**
     * Returns the first data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the front of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getFirst() {
        if (head == null) {
            throw new java.util.NoSuchElementException("This deque is empty and we cannot find any element in it");
        } else {
            return head.getData();
        }
    }

    /**
     * Returns the last data of the deque without removing it.
     *
     * Must be O(1).
     *
     * @return the data located at the back of the deque
     * @throws java.util.NoSuchElementException if the deque is empty
     */
    public T getLast() {
        if (head == null) {
            throw new java.util.NoSuchElementException("This deque is empty and we cannot find any element in it");
        } else {
            return tail.getData();
        }
    }

    /**
     * Returns the head node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY THIS METHOD!
        return head;
    }

    /**
     * Returns the tail node of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return node at the head of the deque
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY THIS METHOD!
        return tail;
    }

    /**
     * Returns the size of the deque.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the deque
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
