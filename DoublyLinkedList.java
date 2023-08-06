/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (data == null) {
            throw new IllegalArgumentException("The parameter data is null");
        }
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The target index is out of the bound of the LinkedList");
        }

        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            DoublyLinkedListNode<T> node = new DoublyLinkedListNode<>(data);
            DoublyLinkedListNode<T> curr;

            if (index < size / 2.0) {
                curr = head;
                for (int i = 0; i < (index - 1); i++) {
                    curr = curr.getNext();
                }
                node.setPrevious(curr);
                node.setNext(curr.getNext());
                curr.setNext(node);
                node.getNext().setPrevious(node);

            } else {
                curr = tail;
                for (int i = size - 1; i > index; i--) {
                    curr = curr.getPrevious();
                }
                node.setPrevious(curr.getPrevious());
                node.setNext(curr);
                curr.setPrevious(node);
                node.getPrevious().setNext(node);
            }
            size += 1;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The parameter data is null");
        }

        DoublyLinkedListNode<T> node = new DoublyLinkedListNode<>(data); //why I need <T> ????????????????????????
        if (head == null) {
            head = node;
            tail = node;
        } else {
            node.setPrevious(null);
            node.setNext(head);
            head.setPrevious(node);
            head = node;
        }
        size += 1;
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The parameter data is null");
        }
        DoublyLinkedListNode<T> node = new DoublyLinkedListNode<>(data);
        if (head == null) {
            head = node;
        } else {
            node.setPrevious(tail);
            node.setNext(null);
            tail.setNext(node);
        }
        tail = node;
        size += 1;
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The target index is out of the bound of the LinkedList");
        }

        if (index == 0) {
            return removeFromFront();
        } else if (index == size - 1) {
            return removeFromBack();
        } else {
            T store;

            if (index < size / 2.0) {
                DoublyLinkedListNode<T> curr = head;
                for (int i = 0; i < (index - 1); i++) {
                    curr = curr.getNext();
                }
                store = curr.getNext().getData();
                curr.setNext(curr.getNext().getNext());
                curr.getNext().setPrevious(curr);

            } else {
                DoublyLinkedListNode<T> curr = tail;
                for (int i = size - 1; i > (index + 1); i--) {
                    curr = curr.getPrevious();
                }
                store = curr.getPrevious().getData();
                curr.setPrevious(curr.getPrevious().getPrevious());
                curr.getPrevious().setNext(curr);
            }

            size -= 1;
            return store;
        }
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("The list is empty and we cannot find any element in it");
        }

        T store;
        store = head.getData();
        head.getNext().setPrevious(null);
        head = head.getNext();
        size -= 1;
        return store;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException("The list is empty and we cannot find any element in it");
        }

        T store;
        store = tail.getData();
        tail.getPrevious().setNext(null);
        tail = tail.getPrevious();
        size -= 1;
        return store;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The target index is out of the bound of the LinkedList");
        }

        T store;

        DoublyLinkedListNode<T> curr;
        if (index < size / 2.0) {
            curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }

        } else {
            curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.getPrevious();
            }
        }
        store = curr.getData();

        return store;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return (head == null);
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The parameter data is null");
        }

        DoublyLinkedListNode<T> curr = tail;
        while (curr != null && !data.equals(curr.getData())) {
            curr = curr.getPrevious();
        }

        if (curr == null) {
            throw new java.util.NoSuchElementException("The list is empty and we cannot find any element in it");
        }

        if (curr.getPrevious() == null) {
            return removeFromFront();
        } else if (curr.getNext() == null) {
            return removeFromBack();
        } else {
            curr.getPrevious().setNext(curr.getNext());
            curr.getNext().setPrevious(curr.getPrevious());
            size -= 1;
        }

        return curr.getData();
    }

    /**
     * Returns an array representation of the linked list. If the list is
     * size 0, return an empty array.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        T[] array = (T[]) new Object[size]; //??????????????????????????????/
        if (size == 0) {
            return array;
        }
        DoublyLinkedListNode<T> curr = head;
        int i = 0;
        while (curr != null) {
            array[i] = curr.getData();
            curr = curr.getNext();
            i++;
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
